package com.sonerik.neuralnetworksandroid.fragments

import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arasthel.swissknife.SwissKnife
import com.arasthel.swissknife.annotations.InjectView
import com.sonerik.neuralnetworksandroid.R
import groovy.transform.CompileStatic

@CompileStatic
public class OutputFragment extends Fragment {

    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView

    @Override
    View onCreateView(LayoutInflater inflater,
                      @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState)
        def v = inflater.inflate(R.layout.fragment_table, container, false)
        SwissKnife.inject(this, v)
        return v
    }

    @Override
    void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = new LinearLayoutManager(activity)
    }
}
