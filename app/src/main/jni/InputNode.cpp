#include "InputNode.h"
#include "Edge.h"

InputNode::InputNode(int index) {
    _index = index;
}

void InputNode::evaluate(std::vector<double>& inputs) {
    output = inputs[_index];
}

void InputNode::updateWeights(double learningRate) {}

void InputNode::calculateError(double desiredValue) {}