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

void Layer::updateWeights(double learningRate) {
    for (auto node : _nodes) {
        node->updateWeights(learningRate);
    }
}

template <typename T>
void Layer::connect(std::shared_ptr<Layer> other) {
    for (auto source : _nodes) {
        for (auto target : other->_nodes) {
            auto edge = std::make_shared<Edge>(Edge(source, target));
            source->outgoingEdges.push_back(edge);
            target->incomingEdges.push_back(edge);
        }
    }
}
