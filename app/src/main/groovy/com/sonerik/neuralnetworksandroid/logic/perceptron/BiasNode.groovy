package com.sonerik.neuralnetworksandroid.logic.perceptron

import groovy.transform.CompileStatic

@CompileStatic
class BiasNode extends InputNode {

    @Override
    double evaluate(List<Double> inputs) {
        1.0d
    }
}