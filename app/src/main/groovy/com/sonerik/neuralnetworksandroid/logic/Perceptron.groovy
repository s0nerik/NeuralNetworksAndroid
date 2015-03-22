package com.sonerik.neuralnetworksandroid.logic

import groovy.transform.CompileStatic

@CompileStatic
class Perceptron {

    List<List<Double>> patterns

    List<Double> enters = (0..<(patterns[0].size() - 1)).collect { 0.0 as Double }
    List<Double> weights = enters.collect { Math.random() * 0.2 + 0.1 as Double }

    double learningRate = 0.115d
    int maxEpochs = 100
    int hiddenLayers = 1

    Perceptron(List patterns) {
        this.patterns = patterns
    }

    double calculateExit(List<Double> enters) {
        def exit = 0.0d
        enters.eachWithIndex { enter, int i ->
            exit += enter * weights[i]
        }
        return (exit >= 0.5)? 1.0d : 0.0d
    }

    int study() {
        def epochs = 0
        while(true) {
            def lastWeights = weights.clone()
            def epochError = studyEpoch()
            epochs++
            if (lastWeights.equals(weights) || epochError == 0 || epochs > maxEpochs) break
        }
        epochs
    }

    double studyEpoch() {
        def globalError = 0.0d
        patterns.each { pattern ->
            enters = pattern[0..-2] // Don't include last element as it contains answer
            def exit = calculateExit(enters)
            def error = pattern[-1] - exit
            globalError += Math.abs(error as double)
            weights.eachWithIndex { weight, int i ->
                weights[i] += learningRate * error * enters[i]
            }
        }

        globalError
    }

    String test() {
        def epochsPassed = study()
        def result = """\
Epochs passed: ${epochsPassed}
Weights: ${weights}
Results:
${String.format("%10s %10s %10s", 'Expected', 'Got', 'Verdict')}
"""
        patterns.each { List it ->
            def expected = it[-1]
            def got = calculateExit(it[0..-2])
            def verdict = got == expected ? "OK" : "NOT OK"
            result += String.format("%10f %10f %10s\n", expected, got, verdict)
        }

        result
    }

}
