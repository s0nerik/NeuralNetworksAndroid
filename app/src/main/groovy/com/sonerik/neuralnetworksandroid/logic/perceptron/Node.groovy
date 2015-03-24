package com.sonerik.neuralnetworksandroid.logic.perceptron

import groovy.transform.CompileStatic

@CompileStatic
class Node {

    List<Edge> outgoingEdges = []
    List<Edge> incomingEdges = []

    List<Double> lastInput
    Double lastOutput
    Double error

    private static Double activationFunction(Double x) {
        1.0d / (1.0d + Math.exp(-x))
    }

    void addBias(BiasNode biasNode) {
        incomingEdges << new Edge(biasNode, this)
    }

    Double evaluate(List<Double> inputs) {
        /* Run activation function on a weighted sum of all inputs. */

//        if (lastOutput) return lastOutput

        lastInput = []
        Double weightedSum = 0d

        incomingEdges.each {
            Double theInput = it.source.evaluate(inputs)
            lastInput << theInput
            weightedSum += it.weight * theInput
        }

        lastOutput = activationFunction(weightedSum)
    }

    Double getError(Double desiredValue) {
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
            }.sum() as Double
        }
    }

    void updateWeights(Double learningRate) {
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