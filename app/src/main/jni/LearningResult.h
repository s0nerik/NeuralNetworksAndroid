#ifndef NEURALNETBACKPROP_LEARNINGRESULT_H
#define NEURALNETBACKPROP_LEARNINGRESULT_H

struct LearningResult {
    std::vector<std::vector<double>> trainingSetOutputs;
    int epochsPassed;
    double averageError;
};

#endif //NEURALNETBACKPROP_LEARNINGRESULT_H
