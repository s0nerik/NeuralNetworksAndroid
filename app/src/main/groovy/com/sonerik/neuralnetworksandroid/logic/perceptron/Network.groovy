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

        // Assign bias nodes as inputs for all hidden layers
        hiddenLayers.each { List<Node> nodes ->
            def biasNode = new BiasNode()
            nodes.each { Node node ->
//                node.addBias(new BiasNode())
                node.addBias(biasNode)
//                new Edge(biasNode, node)
//                new Edge(biasNode, node)
            }
        }

        // Assign BiasNode as input for output node
        network.outputNode.addBias(new BiasNode())
//        new Edge(new BiasNode(), network.outputNode)

        // Assign last hidden layer nodes as inputs for output node
        hiddenLayers[-1].each { new Edge(it, network.outputNode) }

        network
    }

    static List<String> test(List<List<Double>> patterns) {
        def network = make(patterns[0].size() - 1, 1, patterns[0].size() - 1)

//        def network = make(3, 1, 3)
//        patterns = [   [0, 0, 0, 1],
//                       [0, 0, 1, 0],
//                       [0, 1, 0, 1],
//                       [0, 1, 1, 0],
//                       [1, 0, 0, 1],
//                       [1, 0, 1, 0],
//                       [1, 1, 0, 1],
//                       [1, 1, 1, 0]].collect { it.collect { it as double } }

        def factor = patterns.max { it[-1] }[-1] // Last item in array with max expected answer

        // Little hack to handle values other than in -1..1 :)
        patterns = patterns.collectNested { double it -> it / factor }

        network.train(patterns)

        def output = []
        output << String.format("| %-72s | %-10s | %-10s | %-10s |", "Input", "Output", "Error", "Expected")
        patterns.each {
            output << String.format(
                    "| %-72s | %-10.4f | %-10.4f | %-10.4f |\n",
                    it[0..-2].collect{ String.format("%-10.4f", it*factor) }.toString(),
                    (network.evaluate(it[0..-2]))*factor,
                    (it[-1] - network.evaluate(it[0..-2]))*factor,
                    it[-1]*factor
            )
        }

        output
    }
}