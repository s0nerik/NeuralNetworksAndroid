#include "InputNode.h"

InputNode::InputNode(int index) {
    _index = index;
}

double InputNode::evaluate(std::vector<double>& inputs) {
    return inputs[_index];
}

void InputNode::updateWeights(double learningRate) {
    for (auto edge : outgoingEdges) {
        if (auto edgePtr = edge.lock()) {
            edgePtr->_target->updateWeights(learningRate);
        }
    }
}

double InputNode::getError(double desiredValue) {
    for (auto edge : outgoingEdges) {
        if (auto edgePtr = edge.lock()) {
            edgePtr->_target->getError(desiredValue);
        }
    }
    return 0;
}