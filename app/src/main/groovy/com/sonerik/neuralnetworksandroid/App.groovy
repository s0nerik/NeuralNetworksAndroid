package com.sonerik.neuralnetworksandroid

import android.app.Application
import groovy.transform.CompileStatic

@CompileStatic
public class App extends Application {

    public static final String LOG_TAG = "NeuralNetworks"

    @Override
    void onCreate() {
        super.onCreate()

    }
}
