package com.sonerik.neuralnetworksandroid.fragments
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
import com.sonerik.neuralnetworksandroid.logic.perceptron.Network
import groovy.transform.CompileStatic

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
        super.onCreate(savedInstanceState)
        tableData = arguments.getSerializable("table") as List
    }

    @Override
    View onCreateView(LayoutInflater inflater,
                      @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState)
        def v = inflater.inflate(R.layout.fragment_input, container, false)
        SwissKnife.inject(this, v)
        return v
    }

    @Override
    void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState)
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
        Log.d(App.LOG_TAG, "Let's learn!")

        def data = tableData[1..-1].collect { List it ->
            it.collect { s ->
                s as Double
            }
        }

        testNetwork(data)
    }

    @OnBackground
    void testNetwork(List<List<Double>> input) {
        Network.test(input).each {
            Log.d(App.LOG_TAG, it)
        }
        networkTestFinished()
    }

    @OnUIThread
    void networkTestFinished() {
        Log.d(App.LOG_TAG, "networkTestFinished")
    }

}
