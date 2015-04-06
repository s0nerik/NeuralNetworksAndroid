#include "OutputNode.h"

double OutputNode::propagateError(double desiredValue) {
    error = desiredValue - output;
    return error;
}