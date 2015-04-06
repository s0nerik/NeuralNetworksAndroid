#ifndef JNI_LAYER_H
#define JNI_LAYER_H


#include <memory>
#include "Node.h"

class Layer {

public:
    std::vector<std::shared_ptr<Node>> _nodes;

    Layer(size_t numNodes);

    void evaluate();

    void calculateErrors(double desiredValue);

    void updateWeights(double learningRate);

    void connect(std::shared_ptr<Layer> other);

    void assignInputs(std::vector<double>& inputs);

};


#endif //JNI_LAYER_H
