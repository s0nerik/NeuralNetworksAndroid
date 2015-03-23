//package com.sonerik.neuralnetworksandroid.logic.perceptron
//
//class __Layer {
//
//    List<Double> output = []
//    List<Double> input = []
//    List<Double> weights = []
//    List<Double> dweights = []
//
//    Integer inputsNum
//    Integer outputsNum
//    Integer weightsNum
//
//    boolean isSigmoid = true
//
//    __Layer(int inputsNum, int outputsNum) {
//        this.inputsNum = inputsNum
//        this.outputsNum = outputsNum
//        weightsNum = (1 + inputsNum) * outputsNum
//        initWeights()
//    }
//
//    void initWeights() {
//        weightsNum.times {
//            weights << (Math.random() - 0.5d) * 4d;
//        }
//    }
//
////    List<Double> run(List<Double> inputs) {
////        input = inputs
////        input[-1] = 1
////        int offs = 0
////        Collections.fill(output, 0)
////
////        outputsNum.each { int i ->
////            inputsNum.each { int j ->
////                output[i] += weights[offs + j] * input[j]
////            }
////            if (isSigmoid) {
////                output[i] = 1d / (1d + Math.exp(-output[i]))
////            }
////            offs += inputsNum
////        }
////        output
////    }
//
//    List<Double> train(List<Double> error, Double learningRate, Double momentum) {
//        int offs = 0
//        List<Double> nextError
//        outputsNum.each { int i ->
//            def d = error[i]
//            if (isSigmoid) {
//                d *= output[i] * (1 - output[i])
//            }
//            inputsNum.each { int j ->
//                def idx = offs + j
//                nextError[j] += weights[idx] * d
//                def dw = input[j] * d * learningRate
//                weights[idx] += dweights[idx] * momentum + dw
//                dweights[idx] = dw
//            }
//            offs += inputsNum
//        }
//        nextError
//    }
//}