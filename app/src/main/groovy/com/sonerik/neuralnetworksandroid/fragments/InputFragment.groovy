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
import com.sonerik.neuralnetworksandroid.logic.perceptron.Network
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

        List<List<Double>> inputs = data.collect { it[0..-2] as List<Double> }
        List<Double> expectedOutputs = data.collect { it[-1] as Double }

        fragmentManager.beginTransaction().add(android.R.id.content, new LearningProgressFragment(), "progress").commit();

        testNetwork inputs, expectedOutputs
    }

    @OnBackground
    void testNetwork(List<List<Double>> inputs, List<Double> expectedOutputs) {
        def prefs = Prefs.with(activity)

        def network = Network.make(inputs as double[][], inputs.size(), 1d)
        def outputs = network.study(expectedOutputs as double[])

        def resultsTable = []
        resultsTable[0] = ["#", "Expected", "Got", "Error", "Correctness", "Status"]
        for (int i = 0; i < outputs.size(); i++) {
            double error = Math.abs(outputs[i] - expectedOutputs[i])
            def correctPercent = 100d - Math.abs(1d - Math.abs((outputs[i]) / (expectedOutputs[i]))) * 100d
            resultsTable << [i,
                             String.format("%7.3f", expectedOutputs[i]),
                             String.format("%7.3f", outputs[i]),
                             String.format("%7.3f", error),
                             "${String.format("%6.3f", correctPercent)}%",
                             correctPercent >= 95? "OK" : "WRONG" ]
        }

        networkTestFinished(resultsTable)
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
    void networkTestFinished(List table) {
        Log.d App.LOG_TAG, "networkTestFinished"
        fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("progress")).commit();
        App.bus.post new NetworkStudyOverEvent(inputTable: tableData, outputTable: table)
    }

}
