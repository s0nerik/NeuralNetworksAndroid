#ifndef NEURALNETBACKPROP_NETWORKTRAINER_H
#define NEURALNETBACKPROP_NETWORKTRAINER_H

#include <vector>

struct LearningResult;

class NetworkTrainer {
public:
    LearningResult trainNetwork(
            std::vector<unsigned>& topology,
            std::vector<std::vector<double>>& trainingSets,
            std::vector<std::vector<double>>& expectedOutputs,
            int maxEpochs,
            double acceptableError,
            double learningRate = 0.15,
            double momentum = 0.5
    );
};


#endif //NEURALNETBACKPROP_NETWORKTRAINER_H
