package com.sonerik.neuralnetworksandroid.fragments

import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arasthel.swissknife.SwissKnife
import com.arasthel.swissknife.annotations.InjectView
import com.astuetz.PagerSlidingTabStrip
import com.sonerik.neuralnetworksandroid.R
import com.sonerik.neuralnetworksandroid.adapters.OutputPagerAdapter
import groovy.transform.CompileStatic

@CompileStatic
public class OutputFragment extends Fragment {

    List tableData

    @InjectView(R.id.tabs)
    PagerSlidingTabStrip tabs

    @InjectView(R.id.pager)
    ViewPager pager

    static OutputFragment newFragment(ArrayList<List> table) {
        def fragment = new OutputFragment()
        def bundle = new Bundle()
        bundle.putSerializable "table", table
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
        def v = inflater.inflate R.layout.fragment_output, container, false
        SwissKnife.inject this, v
        return v
    }

    @Override
    void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated view, savedInstanceState
        def adapter = new OutputPagerAdapter(childFragmentManager, tableData)
        pager.adapter = adapter
        tabs.viewPager = pager
    }
}
