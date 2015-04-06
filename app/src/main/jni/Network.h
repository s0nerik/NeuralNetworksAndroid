#ifndef JNI_NETWORK_H
#define JNI_NETWORK_H


#include "InputNode.h"
#include "Layer.h"
#include "OutputNode.h"

class Network {

public:
    std::vector<Edge*> edges;

    std::shared_ptr<Layer<InputNode>> inputLayer;
    std::vector<std::shared_ptr<Layer<Node>>> hiddenLayers;
    std::shared_ptr<Layer<OutputNode>> outputLayer;

    Network(size_t numInputs, size_t numHiddenLayers, size_t numInEachLayer);

    double evaluate(std::vector<double>& inputs);

    void propagateError(double desired);

    void updateWeights(double learningRate);

    int train(std::vector<std::vector<double>>& inputs,
              std::vector<double>& expectedOutputs,
              double learningRate = 0.115,
              int maxEpochs = 10000,
              double maxError = 0.1);

    static std::pair<std::vector<std::vector<double>>, std::vector<double>> separatePatterns(std::vector<std::vector<double>>& patterns);

    static std::vector<double> test(std::vector<std::vector<double>> patterns,
                                    double learningRate = 0.115,
                                    int maxEpochs = 10000,
                                    double maxError = 0.1,
                                    int hiddenLayers = 2,
                                    int nodesEachLayer = 6);

};


#endif //JNI_NETWORK_H
