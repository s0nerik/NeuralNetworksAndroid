package com.sonerik.neuralnetworksandroid.logic.perceptron

import groovy.transform.CompileStatic

@CompileStatic
class Node {

    List<Edge> outgoingEdges = []
    List<Edge> incomingEdges = []

    List<Double> lastInput = []
    Double lastOutput
    Double error

    private static double activationFunction(double x) {
        return 1.0d / (1.0d + Math.exp(-x))
    }

    private static double activationFunctionDerivative(double x) {
        return x * (1.0d - x)
    }

    void addBias(BiasNode biasNode) {
        new Edge(biasNode, this)
    }

    double evaluate(List<Double> inputs) {
        /* Run activation function on a weighted sum of all inputs. */

        if (lastOutput) return lastOutput

        double weightedSum = 0d

        incomingEdges.eachWithIndex { Edge it, int i ->
            double theInput = it.source.evaluate(inputs)
            lastInput[i] = theInput
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

        if (!outgoingEdges) { // Output node
            error = desiredValue - lastOutput
        } else { // Normal node
            error = outgoingEdges.sum { Edge it -> // Weighted sum of all errors from nodes in next layer
                it.weight * it.target.getError(desiredValue)
            } as double
        }

        return error
    }

    void updateWeights(double learningRate) {
        /* Update the weights of a node, and all of its successor nodes.
           Assume self is not an InputNode. If the error, lastOutput, and
           lastInput are null, then this node has already been updated. */

        if (error && lastOutput) {
            incomingEdges.eachWithIndex { Edge edge, int i ->
                edge.weight += learningRate * activationFunctionDerivative(lastOutput) * error * lastInput[i]
            }
//            incomingEdges.each {
//                it.source.updateWeights(learningRate)
//            }

            outgoingEdges.target*.updateWeights(learningRate)

            error = null
            lastOutput = null
        }
    }

}