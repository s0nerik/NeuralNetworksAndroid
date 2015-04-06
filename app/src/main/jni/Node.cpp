#include <numeric>
#include <cmath>
#include "Node.h"
#include "Edge.h"

Node::~Node() {
    for (auto edge : outgoingEdges) {
        delete edge;
    }

    for (auto edge : incomingEdges) {
        delete edge;
    }
}

double Node::activationFunction(double x) {
    return 1.0 / (1.0 + exp(-x));
}

double Node::activationFunctionDerivative(double x) {
    return x * (1.0 - x);
}

void Node::evaluate() {
    /* Run activation function on a weighted sum of all inputs. */

    double weightedSum = 0;
    for (auto edge : incomingEdges) {
        weightedSum += edge->_weight * edge->_source->output;
    }

    output = activationFunction(weightedSum);
}

void Node::propagateError(double desiredValue) {
    error = 0;
    for (auto edge : outgoingEdges) {
        error += edge->_weight * edge->_target->error;
    }
}

void Node::updateWeights(double learningRate) {
    /* Update the weights of a node, and all of its successor nodes.
       Assume self is not an InputNode. If the error, output, and
       lastInput are null, then this node has already been updated. */

    int i = 0;
    for (auto edge : incomingEdges) {
        edge->_weight += learningRate * activationFunctionDerivative(output) * error * lastInput[i];
        i++;
    }
//            incomingEdges.each {
//                it._source.updateWeights(learningRate)
//            }

    for (auto edge : outgoingEdges) {
        edge->_target->updateWeights(learningRate);
    }
}
