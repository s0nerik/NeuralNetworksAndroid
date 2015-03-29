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
    double evaluate(List<Double> inputs) {
        inputs[index]
    }

    @Override
    void updateWeights(double learningRate) {
        outgoingEdges.each {
            it.target.updateWeights(learningRate)
        }
    }

    @Override
    double getError(double desiredValue) {
        outgoingEdges.each {
            it.target.getError(desiredValue)
        }
        0d
    }

}