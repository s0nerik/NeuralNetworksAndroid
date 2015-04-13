#include "Layer.h"
#include "Edge.h"

template <class T>
Layer<T>::Layer(size_t numNodes) {
    for (int i = 0; i < numNodes; ++i) {
        _nodes.push_back(std::make_shared<T>(T()));
    }
}

template <class T>
void Layer<T>::evaluate(std::vector<double>& inputs) {
    for (auto node : _nodes) {
        node->evaluate(inputs);
    }
}

template <class T>
void Layer<T>::calculateErrors(double desiredValue) {
    for (auto node : _nodes) {
        node->calculateError(desiredValue);
    }
}

template <class T>
void Layer<T>::updateWeights(double learningRate) {
    for (auto node : _nodes) {
        node->updateWeights(learningRate);
    }
}

template <class T>
void Layer<T>::connect(std::shared_ptr<Layer> other) {
    for (auto source : _nodes) {
        for (auto target : other->_nodes) {
            auto edge = std::make_shared<Edge>(Edge(source, target));
            source->outgoingEdges.push_back(edge);
            target->incomingEdges.push_back(edge);
        }
    }
}