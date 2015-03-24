package com.sonerik.neuralnetworksandroid.logic.perceptron

import groovy.transform.CompileStatic

@CompileStatic
class InputNode extends Node {

    int index

    InputNode() {}

    InputNode(int index) {
        this.index = index
    }

    @Override
    Double evaluate(List<Double> inputs) {
        inputs[index]
    }

    @Override
    void updateWeights(Double learningRate) {
        outgoingEdges.each {
            it.target.updateWeights(learningRate)
        }
    }

    @Override
    Double getError(Double desiredValue) {
        outgoingEdges.each {
            it.target.getError(desiredValue)
        }
        0d
    }

}