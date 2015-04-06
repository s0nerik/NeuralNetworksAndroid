#ifndef JNI_BIASNODE_H
#define JNI_BIASNODE_H


#include "InputNode.h"

class BiasNode : InputNode {

    void evaluate(std::vector<double>& inputs) override;

};


#endif //JNI_BIASNODE_H
