package com.sonerik.neuralnetworksandroid

import android.app.Application
import com.squareup.otto.Bus
import groovy.transform.CompileStatic

@CompileStatic
public class App extends Application {

    public static final String LOG_TAG = "NeuralNetworks"

    static Bus bus

//    static { System.loadLibrary("NetworkTrainer") }

    @Override
    void onCreate() {
        super.onCreate()
        bus = new Bus()
    }
}
