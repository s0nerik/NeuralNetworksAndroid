#ifndef NEURALNETBACKPROP_CALLBACK_H
#define NEURALNETBACKPROP_CALLBACK_H

#include <iostream>

class Callback {
public:
    virtual ~Callback() { std::cout << "Callback::~Callback()" << std:: endl; }
    virtual void epochPassed(int epoch, double recentAverageError) {
        std::cout << "Passed " << epoch << " epoch. Recent average error: " << recentAverageError << "." << std::endl;
    }
};

#endif //NEURALNETBACKPROP_CALLBACK_H
