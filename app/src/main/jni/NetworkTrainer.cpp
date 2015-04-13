#include "NetworkTrainer.h"
#include "LearningResult.h"
#include "Net.h"

using namespace std;

LearningResult NetworkTrainer::trainNetwork(
        vector<unsigned>& topology,
        vector<vector<double>>& trainingSets,
        vector<vector<double>>& expectedOutputs,
        int maxEpochs,
        double acceptableError,
        double learningRate,
        double momentum
) {
    Neuron::setLearningRate(learningRate);
    Neuron::setMomentum(momentum);

    Net net(topology);

    vector<double> results;
    int epoch;
    for (epoch = 0; epoch < maxEpochs; epoch++) {
        int trainingSetIndex = 0;
        for (vector<double> s : trainingSets) {
            // Get new input data and feed it forward:
            net.feedForward(s);

            // Collect the net's actual output results:
            net.getResults(results);

            // Train the net what the outputs should have been:
            net.backProp(expectedOutputs[trainingSetIndex]);

            trainingSetIndex++;
        }

        auto recentAverageError = net.getRecentAverageError();

        if ( _callback && (epoch % 100 == 0) ) {
            _callback->epochPassed(epoch, recentAverageError);
        }

        // If average error is acceptable we can stop learning
        if (recentAverageError <= acceptableError) {
            break;
        }
    }

    LearningResult result;
    result.averageError = net.getRecentAverageError();
    result.epochsPassed = epoch;

    for (vector<double> s : trainingSets) {
        // Get new input data and feed it forward:
        net.feedForward(s);

        // Collect the net's actual output results:
        net.getResults(results);

        result.trainingSetOutputs.push_back(results);
    }

    return result;
}