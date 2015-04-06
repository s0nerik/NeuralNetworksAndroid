#include "OutputNode.h"

double OutputNode::getError(double desiredValue) {
    error = desiredValue - lastOutput;
    return error;
}