#include "OutputNode.h"

void OutputNode::calculateError(double desiredValue) {
    error = desiredValue - output;
}