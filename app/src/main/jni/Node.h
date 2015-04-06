#ifndef JNI_NODE_H
#define JNI_NODE_H

#include <vector>
#include <memory>

class Edge;

class Node : public std::enable_shared_from_this<Node> {

public:
    std::vector<std::shared_ptr<Edge>> outgoingEdges;
    std::vector<std::shared_ptr<Edge>> incomingEdges;

    double output;
    double error = 0;

//    virtual ~Node();

    static double activationFunction(double x);

    static double activationFunctionDerivative(double x);

    virtual void evaluate();
    virtual void propagateError(double desiredValue);
    virtual void updateWeights(double learningRate);
};


#endif //JNI_NODE_H
