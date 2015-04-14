package com.sonerik.neuralnetworksandroid.fragments

import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.arasthel.swissknife.SwissKnife
import com.arasthel.swissknife.annotations.InjectView
import com.sefford.circularprogressdrawable.CircularProgressDrawable
import com.sonerik.neuralnetworksandroid.App
import com.sonerik.neuralnetworksandroid.R
import com.sonerik.neuralnetworksandroid.events.NetworkStudyProgressEvent
import com.squareup.otto.Subscribe
import groovy.transform.CompileStatic

@CompileStatic
public class LearningProgressFragment extends Fragment {

    @InjectView(R.id.txt_epochs_passed)
    TextView txtEpochsPassed
    @InjectView(R.id.txt_average_error)
    TextView txtAverageError
    @InjectView(R.id.progress_view)
    View progressView

    CircularProgressDrawable progressDrawable

    @Override
    View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView inflater, container, savedInstanceState
        def v = inflater.inflate R.layout.fragment_learning_progress, container, false
        SwissKnife.inject this, v

        progressDrawable = new CircularProgressDrawable.Builder()
                .setRingColor(resources.getColor(R.color.colorPrimary))
                .setCenterColor(resources.getColor(R.color.colorPrimaryDark))
                .setRingWidth(16)
                .setInnerCircleScale(0.8f)
                .create()

        progressView.background = progressDrawable

        return v
    }

    @Override
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        App.bus.register(this)
    }

    @Override
    void onDestroy() {
        super.onDestroy()
        App.bus.unregister(this)
    }

    @Subscribe
    void onProgressUpdate(NetworkStudyProgressEvent e) {
        progressDrawable.progress = (e.epochsPassed / (double) e.maxEpochs) as float
        txtEpochsPassed.text = String.format("%d/%d", e.epochsPassed, e.maxEpochs)
        txtAverageError.text = String.format("%.10f", e.averageError)
    }

}
