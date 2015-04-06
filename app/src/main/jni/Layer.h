#ifndef JNI_LAYER_H
#define JNI_LAYER_H


#include <memory>
#include "Node.h"

template <typename T>
class Layer {

public:
    std::vector<std::shared_ptr<T>> _nodes;

    Layer(size_t numNodes);

    void evaluate();

    void propagateError(double desiredValue);

    void updateWeights(double learningRate);

    void connect(std::shared_ptr<Layer> other);

    void assignInputs(std::vector<double>& inputs);

};


#endif //JNI_LAYER_H
