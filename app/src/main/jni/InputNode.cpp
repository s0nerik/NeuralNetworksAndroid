#include "InputNode.h"
#include "Edge.h"

InputNode::InputNode(double input) {
    _input = input;
}

void InputNode::evaluate() {
    output = _input;
}

void InputNode::updateWeights(double learningRate) {}

void InputNode::calculateError(double desiredValue) {}