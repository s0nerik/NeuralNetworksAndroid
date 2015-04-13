#ifndef NEURALNETBACKPROP_NEURON_H
#define NEURALNETBACKPROP_NEURON_H

#include <cstdlib>
#include <vector>
#include "Connection.h"

class Neuron;
typedef std::vector<Neuron> Layer;

class Neuron
{
public:
    Neuron(unsigned numOutputs, unsigned myIndex);
    void setOutputVal(double val) { m_outputVal = val; }
    double getOutputVal(void) const { return m_outputVal; }
    void feedForward(const Layer &prevLayer);
    void calcOutputGradients(double targetVal);
    void calcHiddenGradients(const Layer &nextLayer);
    void updateInputWeights(Layer &prevLayer);
    static void setMomentum(double momentum) {
        Neuron::momentum = momentum;
    }
    static void setLearningRate(double learningRate) {
        Neuron::learningRate = learningRate;
    }

private:
    static double learningRate;   // [0.0..1.0] overall net training rate
    static double momentum; // [0.0..n] multiplier of last weight change (momentum)
    static double transferFunction(double x);
    static double transferFunctionDerivative(double x);
    static double randomWeight(void) { return rand() / double(RAND_MAX); }
    double sumDOW(const Layer &nextLayer) const;
    double m_outputVal;
    std::vector<Connection> m_outputWeights;
    unsigned m_myIndex;
    double m_gradient;
};


#endif //NEURALNETBACKPROP_NEURON_H
