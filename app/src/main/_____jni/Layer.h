#ifndef JNI_LAYER_H
#define JNI_LAYER_H


#include <memory>
#include "Node.h"

template <class T>
class Layer {

public:
    std::vector<std::shared_ptr<T>> _nodes;

    Layer(size_t numNodes);

    void evaluate(std::vector<double>& inputs);

    void calculateErrors(double desiredValue);

    void updateWeights(double learningRate);

    void connect(std::shared_ptr<Layer> other);

};


#endif //JNI_LAYER_H
