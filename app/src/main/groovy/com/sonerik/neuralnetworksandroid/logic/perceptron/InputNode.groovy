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
        return inputs[index]
    }

    @Override
    void updateWeights(double learningRate) {
        outgoingEdges.target*.updateWeights(learningRate)
    }

    @Override
    double getError(double desiredValue) {
        outgoingEdges.target*.getError(desiredValue)
        return 0d
    }

}