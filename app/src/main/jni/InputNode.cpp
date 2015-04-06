#include "InputNode.h"
#include "Edge.h"

InputNode::InputNode(double input) {
    _input = input;
}

void InputNode::evaluate() {
    output = _input;
}

void InputNode::updateWeights(double learningRate) {
    for (auto edge : outgoingEdges) {
        edge->_target->updateWeights(learningRate);
    }
}

double InputNode::propagateError(double desiredValue) {
    return 0;
}