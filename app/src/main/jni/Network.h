#ifndef JNI_NETWORK_H
#define JNI_NETWORK_H


#include "InputNode.h"

class Network {

public:
    std::vector<std::weak_ptr<InputNode>> inputNodes;
    std::vector<std::weak_ptr<Edge>> edges;
    std::shared_ptr<Node> outputNode;

    Network(int numInputs, int numHiddenLayers, int numInEachLayer);

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
                                    int maxEpochs = 100,
                                    double maxError = 0.1,
                                    int hiddenLayers = 2,
                                    int nodesEachLayer = 6);

};


#endif //JNI_NETWORK_H
