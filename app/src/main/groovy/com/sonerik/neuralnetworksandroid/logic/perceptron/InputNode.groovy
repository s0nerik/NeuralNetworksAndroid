package com.sonerik.neuralnetworksandroid.logic.perceptron

import groovy.transform.CompileStatic

@CompileStatic
class InputNode extends Node {

    int index

    InputNode() {}

    InputNode(int index) {
        this.index = index
    }

//    @Override
//    protected void addBias() {}

    @Override
    double evaluate(List<Double> inputs) {
        inputs[index]
//        lastOutput = inputs[index]
    }

    @Override
    void updateWeights(double learningRate) {
        outgoingEdges.each {
            it.target.updateWeights(learningRate)
        }
    }

    @Override
    double getError(double desire) {
        outgoingEdges.each {
            it.target.getError(desire)
        }
        0d
    }

}