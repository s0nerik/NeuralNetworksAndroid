package com.sonerik.neuralnetworksandroid.logic.perceptron

import groovy.transform.CompileStatic

@CompileStatic
class BiasNode extends InputNode {

    @Override
    Double evaluate(List<Double> inputs) {
        1.0d
    }
}