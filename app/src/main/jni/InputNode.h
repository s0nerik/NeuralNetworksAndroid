#ifndef JNI_INPUTNODE_H
#define JNI_INPUTNODE_H


#include "Node.h"

class InputNode : public Node {

public:
    double _input;

    InputNode(){};

    InputNode(double input);

    void evaluate() override;

    void updateWeights(double learningRate) override;

    double propagateError(double desiredValue) override;

};


#endif //JNI_INPUTNODE_H
