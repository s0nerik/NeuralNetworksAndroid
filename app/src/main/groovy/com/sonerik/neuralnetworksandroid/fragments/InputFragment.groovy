package com.sonerik.neuralnetworksandroid.fragments
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arasthel.swissknife.SwissKnife
import com.arasthel.swissknife.annotations.InjectView
import com.arasthel.swissknife.annotations.OnBackground
import com.arasthel.swissknife.annotations.OnClick
import com.arasthel.swissknife.annotations.OnUIThread
import com.software.shell.fab.ActionButton
import com.sonerik.neuralnetworksandroid.App
import com.sonerik.neuralnetworksandroid.R
import com.sonerik.neuralnetworksandroid.events.NetworkStudyOverEvent
import com.sonerik.neuralnetworksandroid.events.NetworkStudyProgressEvent
import com.sonerik.neuralnetworksandroid.ndk.*
import groovy.transform.CompileStatic
import me.alexrs.prefs.lib.Prefs

@CompileStatic
public class InputFragment extends Fragment {

    @InjectView(R.id.action_button)
    ActionButton fab

    List<List> tableData

    static InputFragment newFragment(ArrayList<List> table) {
        def fragment = new InputFragment()
        def bundle = new Bundle()
        bundle.putSerializable("table", table)
        fragment.arguments = bundle
        return fragment
    }

    @Override
    void onCreate(Bundle savedInstanceState) {
        super.onCreate savedInstanceState
        tableData = arguments.getSerializable("table") as List
    }

    @Override
    View onCreateView(LayoutInflater inflater,
                      @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView inflater, container, savedInstanceState
        def v = inflater.inflate R.layout.fragment_input, container, false
        SwissKnife.inject this, v
        return v
    }

    @Override
    void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated view, savedInstanceState
        childFragmentManager.beginTransaction()
                .replace(R.id.container, TableFragment.newFragment(tableData as ArrayList))
                .commit()
        fab.hide()
    }

    @Override
    void onResume() {
        super.onResume()
        new Handler().postDelayed(new Runnable() {
            @Override
            void run() {
                fab.show()
            }
        }, 500)
    }

    @OnClick(R.id.action_button)
    void onFabClicked() {
        Log.d App.LOG_TAG, "Let's learn!"

        List<List> data = tableData[1..-1].collectNested { it as double }
        def factor = data.flatten().max() as double
        data = data.collectNested { double it -> it / factor }

        List<List> inputs = data.collect { it[0..-2] }
        List<List> expectedOutputs = data.collect { it[-1..-1] }

        fragmentManager.beginTransaction().add(android.R.id.content, new LearningProgressFragment(), "progress").commit();

        testNetwork inputs, expectedOutputs, factor
    }

    @OnBackground
    void testNetwork(List<List> inputs, List<List> expectedOutputs, double factor) {
        def prefs = Prefs.with(activity)

        NetworkTrainer t = new NetworkTrainer();
        t.setCallback(new LearningProgressCallback())

        def trainingSets = new VectorOfVectorOfDouble();
        inputs.each {
            def set = new VectorOfDouble();
            it.each {
                set.add(it as double)
            }
            trainingSets.add(set);
        }

        def expected = new VectorOfVectorOfDouble();
        expectedOutputs.each {
            def set = new VectorOfDouble();
            it.each {
                set.add(it as double)
            }
            expected.add(set);
        }

        def hiddenLayers = prefs.getInt("hiddenLayers", 1)
        def nodesEachLayer = prefs.getInt("nodesEachLayer", 6)

        def top = [inputs[0].size(), *([nodesEachLayer] * hiddenLayers), expectedOutputs[0].size()]

        def topology = new VectorOfUnsigned();
        top.each { topology.add(it as long) }

        def result = t.trainNetwork(
                topology,
                trainingSets,
                expected,
                prefs.getInt("maxEpochs", 10000),
                prefs.getFloat("maxError", 0.01f) / factor,
                prefs.getFloat("learningRate", 0.15f) as double,
                prefs.getFloat("momentum", 0.5f) as double,
        )

        def avgError = result.averageError;
        def epochsPassed = result.epochsPassed;

        def outputs = []

        for (int i = 0; i < result.trainingSetOutputs.size(); i++) {
            def set = result.trainingSetOutputs.get(i)
            def line = []
            for (int j = 0; j < set.size(); j++) {
                line << set.get(j)
            }
            outputs << line
        }

        networkTestFinished()
    }

    private class LearningProgressCallback extends Callback {
        int maxEpochs = Prefs.with(activity as Context).getInt("maxEpochs", 10000)

        @Override
        void epochPassed(int epoch, double recentAverageError) {
            Log.d(App.LOG_TAG, "Epoch: ${epoch}, avg error: ${recentAverageError}")
            postUpdateEvent(epoch, recentAverageError, maxEpochs)
        }
    }

    @OnUIThread
    void postUpdateEvent(int epoch, double recentAverageError, int maxEpochs) {
        App.bus.post(new NetworkStudyProgressEvent(epochsPassed: epoch, averageError: recentAverageError, maxEpochs: maxEpochs))
    }

    @OnUIThread
    void networkTestFinished() {
        Log.d App.LOG_TAG, "networkTestFinished"
        fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("progress")).commit();
        App.bus.post new NetworkStudyOverEvent(data: tableData)
    }

}
