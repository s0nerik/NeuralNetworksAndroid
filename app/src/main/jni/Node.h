#ifndef JNI_NODE_H
#define JNI_NODE_H

#include <vector>
#include <memory>

class Edge;

class Node : public std::enable_shared_from_this<Node> {

public:
    std::vector<Edge*> outgoingEdges;
    std::vector<Edge*> incomingEdges;

    double output;
    double error = 0;

    virtual ~Node();

    static double activationFunction(double x);

    static double activationFunctionDerivative(double x);

    virtual void evaluate();
    virtual double propagateError(double desiredValue);
    virtual void updateWeights(double learningRate);
};


#endif //JNI_NODE_H
