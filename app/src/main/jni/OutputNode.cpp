#include "OutputNode.h"

void OutputNode::propagateError(double desiredValue) {
    error = desiredValue - output;
    return error;
}