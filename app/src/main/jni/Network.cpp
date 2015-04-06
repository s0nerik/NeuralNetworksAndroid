#include <algorithm>
#include <assert.h>
#include "Network.h"
#include "Edge.h"
#include "Layer.h"

Network::Network(size_t numInputs, size_t numHiddenLayers, size_t numInEachLayer) {

    // Create input layer
    inputLayer = std::make_shared(Layer<InputNode>(numInputs));

    // Create output layer
    outputLayer = std::make_shared(Layer<OutputNode>(1));

    // Create hidden layers
    for (int i = 0; i < numHiddenLayers; ++i) {
        hiddenLayers.push_back(std::make_shared(Layer<Node>(numInEachLayer)));
    }

    // Connect input layer with first hidden layer
    inputLayer->connect(hiddenLayers[0]);

    // Connect hidden layers
    for (int i = 0; i < hiddenLayers.size() - 1; ++i) {
        hiddenLayers[i]->connect(hiddenLayers[i + 1]);
    }

    // Connect last hidden layer with output layer
    hiddenLayers[hiddenLayers.size() - 1]->connect(outputLayer);

//    // Assign bias nodes as inputs for all hidden layers
//    hiddenLayers.each { List<Node> nodes ->
//                def biasNode = new BiasNode()
//        nodes*.addBias(biasNode)
//    }
//
//    // Assign BiasNode as input for output node
//    network.outputNode.addBias(new BiasNode())
}

double Network::evaluate(std::vector<double>& inputs) {

    return outputNode->evaluate(inputs);
}

void Network::propagateError(double desired) {
    for (auto node : inputNodes) {
        node->propagateError(desired);
    }
}

void Network::updateWeights(double learningRate) {
    for (auto node : inputNodes) {
        node->updateWeights(learningRate);
    }
}

int Network::train(std::vector<std::vector<double>>& inputs,
                   std::vector<double>& expectedOutputs,
                   double learningRate,
                   int maxEpochs,
                   double maxError) {
    std::vector<double> outputs;
    int epochsPassed = 0;

//    assert(std::max_element(inputNodes.begin(), inputNodes.end(), [](InputNode& node1, InputNode& node2) {
//        return node1._index < node2._index;
//    })->_index < inputs.size());

    while (epochsPassed < maxEpochs) {
        for (int i = 0; i < inputs.size(); ++i) {
            outputs.push_back(evaluate(inputs[i]));
            propagateError(expectedOutputs[i]);
            updateWeights(learningRate);
        }

        epochsPassed++;

        outputs.clear();
    }

    return epochsPassed;
}

std::vector<double> Network::test(std::vector<std::vector<double>> patterns,
                                  double learningRate,
                                  int maxEpochs,
                                  double maxError,
                                  int hiddenLayers,
                                  int nodesEachLayer) {

    Network network((int) (patterns[0].size() - 1), hiddenLayers, nodesEachLayer);

//        def network = make(3, 1, 3)
//        patterns = [   [0, 0, 0, 1],
//                       [0, 0, 1, 0],
//                       [0, 1, 0, 1],
//                       [0, 1, 1, 0],
//                       [1, 0, 0, 1],
//                       [1, 0, 1, 0],
//                       [1, 1, 0, 1],
//                       [1, 1, 1, 0]].collect { it.collect { it as Double } }

    // Calculate factor
    double factor = 0;
    for (auto pattern : patterns) {
        for (auto value : pattern) {
            if (value > factor) {
                factor = value;
            }
        }
    }

    // Little hack to handle values other than in -1..1 :)
    for (auto pattern : patterns) {
        for (int i = 0; i < pattern.size(); ++i) {
            pattern[i] /= factor;
        }
    }

    auto data = separatePatterns(patterns);

    auto epochs = network.train(data.first, data.second, learningRate, maxEpochs, maxError);

    std::vector<double> output;

    // Write output scaled to factor
    for (auto input : data.first) {
        auto out = network.evaluate(input);
        output.push_back(out * factor);
    }

//        output << "Finished training. It took ${epochs} epochs."
//        output << String.format("| %-72s | %-10s | %-10s | %-10s |", "Input", "Output", "Error", "Expected")
//        patterns.each {
//        output << String.format(
//                "| %-72s | %-10.4f | %-10.4f | %-10.4f |",
//                it[0..-2].collect{ String.format("%-10.4f", it*factor) }.toString(),
//                (network.evaluate(it[0..-2]))*factor,
//                (it[-1] - network.evaluate(it[0..-2]))*factor,
//                it[-1]*factor
//        )

    return output;
}

std::pair<std::vector<std::vector<double>>, std::vector<double>> Network::separatePatterns(std::vector<std::vector<double>>& patterns) {
    // Create inputs vector
    std::vector<std::vector<double>> inputs;
    for (auto line : patterns) {
        inputs.push_back(std::vector<double>(&line[0], &line[line.size() - 1]));
    }

    // Create expected outputs vector
    std::vector<double> expectedOutputs;
    for (auto line : patterns) {
        expectedOutputs.push_back(line[line.size() - 1]);
    }

    return std::make_pair(inputs, expectedOutputs);
}