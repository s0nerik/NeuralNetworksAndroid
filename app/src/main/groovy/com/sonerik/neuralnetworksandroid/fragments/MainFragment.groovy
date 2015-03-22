package com.sonerik.neuralnetworksandroid.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arasthel.swissknife.SwissKnife
import com.arasthel.swissknife.annotations.InjectView
import com.arasthel.swissknife.annotations.OnClick
import com.sonerik.neuralnetworksandroid.App
import com.sonerik.neuralnetworksandroid.R
import com.sonerik.neuralnetworksandroid.adapters.TableAdapter
import groovy.transform.CompileStatic

@CompileStatic
public class MainFragment extends Fragment {

    List tableData

    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView

    @Override
    View onCreateView(LayoutInflater inflater,
                      @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState)
        def v = inflater.inflate(R.layout.fragment_main, container, false)
        SwissKnife.inject(this, v)
        return v
    }

    @Override
    void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = new LinearLayoutManager(activity)
    }

    @OnClick(R.id.loadFromCsv)
    void onLoadFromCsvClicked() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(Environment.externalStorageDirectory.path);
        intent.setDataAndType(uri, "text/csv");
        startActivityForResult(Intent.createChooser(intent, "Open folder"), 1337);
    }

    @Override
    void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 1337) {
            if (resultCode == Activity.RESULT_OK && intent) {
                def csv = new File(intent.data?.path)?.readLines()?.collect { String it ->
                    it.replaceAll(" ", "").split(",")
                }

                tableData = csv

                Log.d(App.LOG_TAG, """
                       |onActivityResult: ${requestCode}
                       |data: ${intent.data}
                       |csv: ${csv}
                       """.stripMargin())
            } else {
                tableData = Collections.nCopies(600, 0..6 as List)
            }

            recyclerView.adapter = new TableAdapter(activity, tableData)
        }
    }
}
