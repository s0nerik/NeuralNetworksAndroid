package com.sonerik.neuralnetworksandroid.logic.perceptron

import groovy.transform.CompileStatic

@CompileStatic
class Network {

    List<InputNode> inputNodes
    Node outputNode

    double evaluate(List<Double> inputs) {
        assert inputNodes.max {it.index}.index < inputs.size()

        outputNode.evaluate(inputs)
    }

    void propagateError(double desired) {
        inputNodes.each {
            it.getError(desired)
        }
    }

    void updateWeights(double learningRate) {
        inputNodes.each {
            it.updateWeights(learningRate)
        }
    }

    void train(List<List<Double>> patterns, double learningRate = 0.115d, int maxIterations = 10000) {
        while (maxIterations > 0) {
            patterns.each {
                def output = evaluate(it[0..-2])
                propagateError(it[-1])
                updateWeights(learningRate)

                maxIterations--
            }
        }
    }

    static Network make(int numInputs, int numHiddenLayers, int numInEachLayer) {
        def network = new Network()
        network.outputNode = new Node()

        // Create input nodes
        network.inputNodes = (0..<numInputs).collect { new InputNode(it) }

        // Create nodes in hidden layers
        def hiddenLayers = (0..<numHiddenLayers).collect { (0..<numInEachLayer).collect {
            new Node()
        }}

        // Assign inputs to the first hidden layer
        network.inputNodes.each { InputNode inputNode ->
            hiddenLayers[0].each { Node node ->
                new Edge(inputNode, node)
            }
        }

        // Assign hidden layer nodes as inputs for other hidden layers
        (numHiddenLayers - 1).times { int i ->
            hiddenLayers[i].each { Node node1 ->
                hiddenLayers[i + 1].each { Node node2 ->
                    new Edge(node1, node2)
                }
            }
        }

        // Assign last hidden layer nodes as inputs for output node
        hiddenLayers[-1].each { new Edge(it, network.outputNode) }

        network
    }

    static List<String> test() {
        def network = make(3, 1, 3)
        List<List<Double>> labeledExamples = [ [0, 0, 0, 1],
                                               [0, 0, 1, 0],
                                               [0, 1, 0, 1],
                                               [0, 1, 1, 0],
                                               [1, 0, 0, 1],
                                               [1, 0, 1, 0],
                                               [1, 1, 0, 1],
                                               [1, 1, 1, 0]].collect { it.collect { it as double } }
        network.train(labeledExamples)

        def output = []
        labeledExamples.each {
            output << String.format(
                    "Error for %s is %10.4f. Output was: %10.4f\n",
                    it[0..-2].toString(),
                    it[-1] - network.evaluate(it[0..-2]),
                    network.evaluate(it[0..-2])
            )
        }

        output
    }
}