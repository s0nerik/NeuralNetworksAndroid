#ifndef JNI_OUTPUTNODE_H
#define JNI_OUTPUTNODE_H


#include "Node.h"

class OutputNode : public Node {

public:
    virtual void calculateError(double desiredValue) override;

};


#endif //JNI_OUTPUTNODE_H
