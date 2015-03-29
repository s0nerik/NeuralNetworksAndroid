package com.sonerik.neuralnetworksandroid.logic.perceptron

import groovy.transform.CompileStatic

@CompileStatic
class Node {

    List<Edge> outgoingEdges = []
    List<Edge> incomingEdges = []

    List<Double> lastInput
    Double lastOutput
    Double error

    private static double activationFunction(double x) {
        return 1.0d / (1.0d + Math.exp(-x))
    }

    void addBias(BiasNode biasNode) {
        incomingEdges << new Edge(biasNode, this)
    }

    double evaluate(List<Double> inputs) {
        /* Run activation function on a weighted sum of all inputs. */

//        if (lastOutput) return lastOutput

        lastInput = []
        double weightedSum = 0d

        incomingEdges.each {
            double theInput = it.source.evaluate(inputs)
            lastInput << theInput
            weightedSum += it.weight * theInput
        }

        lastOutput = activationFunction(weightedSum)
        return lastOutput
    }

    double getError(double desiredValue) {
        /* Get the error for a given node in the network. If the node is an
           output node, desiredValue will be used to compute the error. For an input node, we
           simply ignore the error. */

        if (error) return error

        assert lastOutput

        if (!outgoingEdges) { // Output node
            error = desiredValue - lastOutput
        } else {
            error = outgoingEdges.collect {
                it.weight * it.target.getError(desiredValue)
            }.sum() as double
        }

        return error
    }

    void updateWeights(double learningRate) {
        /* Update the weights of a node, and all of its successor nodes.
           Assume self is not an InputNode. If the error, lastOutput, and
           lastInput are null, then this node has already been updated. */

        if (error && lastOutput && lastInput) {
            incomingEdges.eachWithIndex { Edge edge, int i ->
                edge.weight += learningRate * lastOutput * (1d - lastOutput) * error * lastInput[i]
            }
            outgoingEdges.each {
                it.target.updateWeights(learningRate)
            }
            error = null
            lastOutput = null
            lastInput = null
        }
    }

}