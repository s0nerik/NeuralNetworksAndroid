#ifndef NEURALNETBACKPROP_NET_H
#define NEURALNETBACKPROP_NET_H

#include "Layer.h"

class Net
{
public:
    Net(const std::vector<unsigned> &topology);
    void feedForward(const std::vector<double> &inputVals);
    void backProp(const std::vector<double> &targetVals);
    void getResults(std::vector<double> &resultVals) const;
    double getError() const;
    double getRecentAverageError(void) const { return m_recentAverageError; }

private:
    std::vector<Layer> m_layers; // m_layers[layerNum][neuronNum]
    double m_error;
    double m_recentAverageError;
    static double m_recentAverageSmoothingFactor;
};

#endif //NEURALNETBACKPROP_NET_H
