#include "Layer.h"

template <typename T>
Layer<T>::Layer(size_t numNodes) {
    for (int i = 0; i < numNodes; ++i) {
        _nodes.push_back(std::make_shared<T>(T()));
    }
}

void Layer::evaluate() {
    for (auto node : _nodes) {
        node->evaluate();
    }
}

void Layer::propagateError(double desiredValue) {
    for (auto node : _nodes) {
        node->propagateError(desiredValue);
    }
}
