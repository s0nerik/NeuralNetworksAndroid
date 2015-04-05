#include <numeric>
#include <cmath>
#include "Node.h"

static double Node::activationFunction(double x) {
    return 1.0 / (1.0 + exp(-x));
}

static double Node::activationFunctionDerivative(double x) {
    return x * (1.0 - x);
}

double Node::evaluate(std::vector<double> & inputs) {
    /* Run activation function on a weighted sum of all inputs. */

    if (visited) return lastOutput;

    double weightedSum = 0;

    int i = 0;
    for (auto edge : incomingEdges) {
        if (auto edgePtr = edge.lock()) {
            double theInput = edgePtr->_source->evaluate(inputs);
            lastInput[i] = theInput;
            weightedSum += edgePtr->_weight * theInput;
        }
        i++;
    }

    lastOutput = activationFunction(weightedSum);
    return lastOutput;
}

double Node::getError(double desiredValue) {
    /* Get the error for a given node in the network. If the node is an
       output node, desiredValue will be used to compute the error. For an input node, we
       simply ignore the error. */

    if (visited) return error;

    if (outgoingEdges.size() == 0) { // Output node
        error = desiredValue - lastOutput;
    } else { // Normal node
        error = std::accumulate(outgoingEdges.begin(), outgoingEdges.end(), 0, [](Edge& e1, Edge& e2) {
            return e1._weight * e2._target->getError(desiredValue);
        });
    }

    return error;
}

void Node::updateWeights(double learningRate) {
    /* Update the weights of a node, and all of its successor nodes.
       Assume self is not an InputNode. If the error, lastOutput, and
       lastInput are null, then this node has already been updated. */

    if (!visited) {
        int i = 0;
        for (auto edge : incomingEdges) {
            if (auto edgePtr = edge.lock()) {
                edgePtr->_weight += learningRate * activationFunctionDerivative(lastOutput) * error * lastInput[i];
            }
            i++;
        }
//            incomingEdges.each {
//                it._source.updateWeights(learningRate)
//            }

        for (auto edge : outgoingEdges) {
            if (auto edgePtr = edge.lock()) {
                edgePtr->_target->updateWeights(learningRate);
            }
        }

        visited = true;
    }
}