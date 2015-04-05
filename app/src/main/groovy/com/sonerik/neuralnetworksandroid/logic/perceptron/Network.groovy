package com.sonerik.neuralnetworksandroid.logic.perceptron

import groovy.transform.CompileStatic

@CompileStatic
class Network {

    List<InputNode> inputNodes
    Node outputNode

    double evaluate(List<Double> inputs) {
        outputNode.evaluate(inputs)
    }

    void propagateError(double desired) {
        inputNodes*.getError(desired)
    }

    void updateWeights(double learningRate) {
        inputNodes*.updateWeights(learningRate)
    }

    int train(List<List<Double>> patterns, double learningRate = 0.115d, int maxEpochs = 10000, double maxError = 0.1d) {
        List<Double> outputs = []
        int epochsPassed = 0

        def inputs = patterns.collect { it[0..-2] }

        assert inputNodes.max {it.index}.index < inputs.size()

        while (epochsPassed < maxEpochs) {
            patterns.eachWithIndex { List<Double> pattern, int i ->
                outputs << evaluate(inputs[i])
                propagateError(pattern[-1])
                updateWeights(learningRate)
            }

            epochsPassed++

//            if (outputs.max() < maxError) break

            outputs = []
        }
        return epochsPassed
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
            nodes*.addBias(biasNode)
        }

        // Assign BiasNode as input for output node
        network.outputNode.addBias(new BiasNode())

        // Assign last hidden layer nodes as inputs for output node
        hiddenLayers[-1].each { new Edge(it, network.outputNode) }

        network
    }

    static List<String> test(List<List<Double>> patterns,
                             double learningRate = 0.115d,
                             int maxEpochs = 100,
                             double maxError = 0.1d,
                             int hiddenLayers = 2i,
                             int nodesEachLayer = 6i) {
        def network = make(patterns[0].size() - 1, hiddenLayers, nodesEachLayer)

//        def network = make(3, 1, 3)
//        patterns = [   [0, 0, 0, 1],
//                       [0, 0, 1, 0],
//                       [0, 1, 0, 1],
//                       [0, 1, 1, 0],
//                       [1, 0, 0, 1],
//                       [1, 0, 1, 0],
//                       [1, 1, 0, 1],
//                       [1, 1, 1, 0]].collect { it.collect { it as Double } }

        def factor = patterns.max { it[-1] }[-1] // Last item in array with max expected answer

        // Little hack to handle values other than in -1..1 :)
        patterns = patterns.collectNested { double it -> it / factor }

        def epochs = network.train(patterns, learningRate, maxEpochs, maxError)

        def output = []
        output << "Finished training. It took ${epochs} epochs."
        output << String.format("| %-72s | %-10s | %-10s | %-10s |", "Input", "Output", "Error", "Expected")
        patterns.each {
            output << String.format(
                    "| %-72s | %-10.4f | %-10.4f | %-10.4f |",
                    it[0..-2].collect{ String.format("%-10.4f", it*factor) }.toString(),
                    (network.evaluate(it[0..-2]))*factor,
                    (it[-1] - network.evaluate(it[0..-2]))*factor,
                    it[-1]*factor
            )
        }

        output
    }
}