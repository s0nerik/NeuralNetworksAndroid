#ifndef JNI_INPUTNODE_H
#define JNI_INPUTNODE_H


#include "Node.h"

class InputNode : public Node {

public:
    int _index;

    InputNode(){};
    InputNode(int index);

    void evaluate(std::vector<double>& inputs) override;

    void updateWeights(double learningRate) override;

    void calculateError(double desiredValue) override;

};


#endif //JNI_INPUTNODE_H
