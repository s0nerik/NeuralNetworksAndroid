package com.sonerik.neuralnetworksandroid.fragments

import android.graphics.Color
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.arasthel.swissknife.SwissKnife
import com.arasthel.swissknife.annotations.InjectView
import com.sonerik.neuralnetworksandroid.R
import com.sonerik.neuralnetworksandroid.adapters.TableAdapter
import groovy.transform.CompileStatic
import me.grantland.widget.AutofitHelper

@CompileStatic
public class TableFragment extends Fragment {

    List<List> tableData

    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView

    @InjectView(R.id.tableTitles)
    View tableTitles

    static TableFragment newFragment(ArrayList<List> table) {
        def fragment = new TableFragment()
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
        def v = inflater.inflate(R.layout.fragment_table, container, false)
        SwissKnife.inject(this, v)

        tableTitles.children.each { View textView ->
            AutofitHelper.create(textView as TextView);
        }

        return v
    }

    @Override
    void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = new LinearLayoutManager(activity)
        recyclerView.adapter = new TableAdapter(activity, tableData[1..-1])
        addHeader(tableData[0])
    }

    private void addHeader(List items) {
        tableData[0].eachWithIndex{ s, int i ->
            def view = tableTitles.findViewById((int) R.id["text${i}"]) as TextView
            view.visibility = View.VISIBLE
            view.text = s.toString()
            view.textColor = Color.WHITE
        }
        tableTitles.visibility = View.VISIBLE
        tableTitles.backgroundResource = R.color.colorAccent
    }

}
