#ifndef JNI_NODE_H
#define JNI_NODE_H

#include <vector>
#include <memory>

class Edge;

class Node : public std::enable_shared_from_this<Node> {

public:
    std::vector<std::shared_ptr<Edge>> outgoingEdges;
    std::vector<std::shared_ptr<Edge>> incomingEdges;
    std::vector<double> lastInput;
    double lastOutput;
    double error;
    bool visited;

    static double activationFunction(double x);

    static double activationFunctionDerivative(double x);

    virtual double evaluate(std::vector<double>& inputs);
    virtual double getError(double desiredValue);
    virtual void updateWeights(double learningRate);
};


#endif //JNI_NODE_H
