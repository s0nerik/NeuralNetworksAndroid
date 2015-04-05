#ifndef JNI_BIASNODE_H
#define JNI_BIASNODE_H


#include "InputNode.h"

class BiasNode : InputNode {

    double evaluate(std::vector<double>& inputs);

};


#endif //JNI_BIASNODE_H
