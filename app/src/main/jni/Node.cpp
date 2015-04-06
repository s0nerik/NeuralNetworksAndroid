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

double Node::evaluate(std::vector<double> & inputs) {
    /* Run activation function on a weighted sum of all inputs. */

    if (!shouldCalculateOutput) return lastOutput;

    double weightedSum = 0;

    lastInput.reserve(incomingEdges.size());
    int i = 0;
    for (auto edge : incomingEdges) {
        double theInput = edge->_source->evaluate(inputs);
        lastInput[i] = theInput;
        weightedSum += edge->_weight * theInput;
        i++;
    }

    shouldCalculateOutput = false;

    lastOutput = activationFunction(weightedSum);
    return lastOutput;
}

double Node::getError(double desiredValue) {
    /* Get the error for a given node in the network. If the node is an
       output node, desiredValue will be used to compute the error. For an input node, we
       simply ignore the error. */

    if (!shouldCalculateError) return error;

    if (outgoingEdges.size() == 0) { // Output node
        error = desiredValue - lastOutput;
    } else { // Normal node
        error = 0;
        for (auto edge : outgoingEdges) {
            error += edge->_weight * edge->_target->getError(desiredValue);
        }
//        error = std::accumulate(outgoingEdges.begin(), outgoingEdges.end(), (double) 0, [desiredValue](auto e1, auto e2) {
//            return e1.lock()->_weight * e2.lock()->_target->getError(desiredValue);
//        });
    }

    shouldCalculateError = false;

    return error;
}

void Node::updateWeights(double learningRate) {
    /* Update the weights of a node, and all of its successor nodes.
       Assume self is not an InputNode. If the error, lastOutput, and
       lastInput are null, then this node has already been updated. */

    if (shouldUpdateWeight && !shouldCalculateOutput && !shouldCalculateError) {
        int i = 0;
        for (auto edge : incomingEdges) {
            edge->_weight += learningRate * activationFunctionDerivative(lastOutput) * error * lastInput[i];
            i++;
        }
//            incomingEdges.each {
//                it._source.updateWeights(learningRate)
//            }

        for (auto edge : outgoingEdges) {
            edge->_target->updateWeights(learningRate);
        }

        shouldUpdateWeight = false;
        shouldCalculateOutput = true;
        shouldCalculateError = true;
    }
}
