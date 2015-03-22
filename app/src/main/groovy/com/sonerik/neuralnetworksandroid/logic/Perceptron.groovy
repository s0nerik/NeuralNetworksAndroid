package com.sonerik.neuralnetworksandroid.logic

class Perceptron {

//    def patterns = new JsonSlurper().parse(new File("input.json")).patterns
//    def statement = new File('input.txt').readLines().find {!it.startsWith("#")}
//    def patterns = new StatementParser().generateTable(statement)

    List patterns

    def enters = (0..<(patterns[0].size() - 1)).collect { 0.0 }
    def weights = enters.collect { Math.random() * 0.2 + 0.1 }

    def learningRate = 0.115
    def maxEpochs = 10000
    def hiddenLayers = 1

    def Perceptron(def patterns, double learningRate, int maxEpochs, int hiddenLayers) {
        this.patterns = patterns
        this.learningRate = learningRate
        this.maxEpochs = maxEpochs
        this.hiddenLayers = hiddenLayers
    }

    def calculateExit(List<Integer> enters) {
        def exit = 0.0
        enters.eachWithIndex { enter, i ->
            exit += enter * weights[i]
        }
        return (exit >= 0.5)? 1 : 0
    }

    def study() {
        def epochs = 0
        while(true) {
            def lastWeights = weights.clone()
            def epochError = studyEpoch()
            epochs++
            if (lastWeights.equals(weights) || epochError == 0 || epochs > maxEpochs) break
        }
        epochs
    }

    def studyEpoch() {
        def globalError = 0.0
        patterns.each { List<Integer> pattern ->
            enters = pattern[0..-2] // Don't include last element as it contains answer
            def exit = calculateExit(enters)
            def error = pattern[-1] - exit
            globalError += Math.abs(error)
            weights.eachWithIndex { weight, i ->
                weights[i] += learningRate * error * enters[i]
            }
        }

        globalError
    }

    def test() {
        def epochsPassed = study()

        println "Epochs passed: ${epochsPassed}"
        println "Weights: ${weights}"

        println "Results:"
        println String.format("%10s %10s %10s", 'Expected', 'Got', 'Verdict')

        patterns.each { List it ->
            def expected = it[-1]
            def got = calculateExit(it[0..-2])
            def verdict = got == expected ? "OK" : "NOT OK"
            println String.format("%10d %10d %10s", expected, got, verdict)
        }
    }

}
