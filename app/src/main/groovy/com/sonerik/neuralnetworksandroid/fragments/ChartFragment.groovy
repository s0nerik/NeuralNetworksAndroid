package com.sonerik.neuralnetworksandroid.fragments

import android.graphics.Color
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arasthel.swissknife.SwissKnife
import com.arasthel.swissknife.annotations.InjectView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.sonerik.neuralnetworksandroid.R
import groovy.transform.CompileStatic

@CompileStatic
public class ChartFragment extends Fragment {

    List expected
    List got

    @InjectView(R.id.chart)
    LineChart chart

    static ChartFragment newFragment(ArrayList expected, ArrayList got) {
        def fragment = new ChartFragment()
        def bundle = new Bundle()
        bundle.putSerializable "expected", expected
        bundle.putSerializable "got", got
        fragment.arguments = bundle
        return fragment
    }

    @Override
    void onCreate(Bundle savedInstanceState) {
        super.onCreate savedInstanceState
        expected = arguments.getSerializable("expected") as List
        got = arguments.getSerializable("got") as List
    }

    @Override
    View onCreateView(LayoutInflater inflater,
                      @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView inflater, container, savedInstanceState
        def v = inflater.inflate R.layout.fragment_chart, container, false
        SwissKnife.inject this, v
        return v
    }

    @Override
    void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated view, savedInstanceState
        def expectedLine = createLineDataSet(expected, "Expected", Color.BLACK)
        def gotLine = createLineDataSet(got, "Got", Color.RED, true)

        List names = new ArrayList<String>();
        expected.eachWithIndex { Object entry, int i ->
            names << (i as String)
        }

        chart.data = new LineData(names, [expectedLine, gotLine])

        chart.pinchZoom = true

        chart.invalidate()
    }

    private static LineDataSet createLineDataSet(List values, String name, int color, boolean dashed = false) {
        List<Entry> entries = new ArrayList<>()
        int i = 0
        values.each {
            entries << new Entry(it as float, i)
            i++
        }

        LineDataSet set = new LineDataSet(entries, name);
        // set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
        if (dashed) set.enableDashedLine(10f, 5f, 0f);
        set.drawCircles = false
        set.setColor(color);
        set.setCircleColor(color);
        set.setLineWidth(1f);
        set.setCircleSize(2f);
        set.setDrawCircleHole(false);
        set.setValueTextSize(9f);
        set.setFillAlpha(65);
        set.setFillColor(color);

        set
    }

}
