package com.sonerik.neuralnetworksandroid.logic.perceptron

import groovy.transform.CompileStatic

@CompileStatic
class Node {

    List<Edge> outgoingEdges = []
    List<Edge> incomingEdges = []

    List<Double> lastInput
    Double lastOutput
    Double error

    Node() {
//        addBias()
    }

    private static Double activationFunction(Double x) {
        1.0d / (1.0d + Math.exp(-x))
    }

    protected void addBias() {
        incomingEdges << new Edge(new BiasNode(), this)
    }

    double evaluate(List<Double> inputs) {
//        if (lastOutput) return lastOutput

        lastInput = []
        def weightedSum = 0

        incomingEdges.each {
            def theInput = it.source.evaluate(inputs)
            lastInput << theInput
            weightedSum += it.weight * theInput
        }

        lastOutput = activationFunction(weightedSum)
    }

    double getError(double desire) {
//        if (error) return error

        assert lastOutput

        if (!outgoingEdges) { // Output node
            error = desire - lastOutput
        } else {
            error = outgoingEdges.collect {
                def targetError = it.target.getError(desire)
                it.weight * targetError
//                it.weight * it.target.getError(desire)
            }.sum() as Double
        }
    }

    void updateWeights(double learningRate) {
        if (error && lastOutput && lastInput) {
            incomingEdges.eachWithIndex { Edge edge, int i ->
                edge.weight += learningRate * lastOutput * (1d - lastOutput) * error * lastInput[i]
            }
            outgoingEdges.each {
                it.target.updateWeights(learningRate)
            }
            error = null
            lastInput = null
            lastOutput = null
        }
    }

}