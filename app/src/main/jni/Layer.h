#ifndef JNI_LAYER_H
#define JNI_LAYER_H


#include <memory>
#include "Node.h"

template <typename T>
class Layer {

public:
    std::vector<std::shared_ptr<Node>> _nodes;

    Layer(size_t numNodes);

    void evaluate();

    void propagateError(double desiredValue);

};


#endif //JNI_LAYER_H