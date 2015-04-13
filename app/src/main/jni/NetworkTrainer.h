#ifndef NEURALNETBACKPROP_NETWORKTRAINER_H
#define NEURALNETBACKPROP_NETWORKTRAINER_H

#include <vector>
#include "Callback.h"

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

    ~NetworkTrainer() { delCallback(); }
    void delCallback() { delete _callback; _callback = 0; }
    void setCallback(Callback *cb) { delCallback(); _callback = cb; }

private:
    Callback *_callback = 0;
};


#endif //NEURALNETBACKPROP_NETWORKTRAINER_H
