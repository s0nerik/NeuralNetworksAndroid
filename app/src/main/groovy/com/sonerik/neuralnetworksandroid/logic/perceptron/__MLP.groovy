//package com.sonerik.neuralnetworksandroid.logic.perceptron
//
//import android.util.Log
//import com.sonerik.neuralnetworksandroid.App
//
//class __MLP {
//
//    __Layer[] layers;
//
//    __MLP(int inputSize, int[] layersSize) {
//        layers = new __Layer[layersSize.length]
//        for (int i = 0; i < layersSize.length; i++) {
//            int inSize = i == 0 ? inputSize : layersSize[i - 1]
//            layers[i] = new __Layer(inSize, layersSize[i], r)
//        }
//    }
//
//    List<Double> run(List<Double> input) {
//        List<Double> actIn = input
//        for (int i = 0; i < layers.length; i++) {
//            actIn = layers[i].run(actIn)
//        }
//        return actIn
//    }
//
//    void train(List<Double> input, List<Double> targetOutput, Double learningRate, Double momentum) {
//        List<Double> calcOut = run(input)
//        List<Double> error = new float[calcOut.length]
//        for (int i = 0; i < error.length; i++) {
//            error[i] = targetOutput[i] - calcOut[i] // negative error
//        }
//        for (int i = layers.length - 1; i >= 0; i--) {
//            error = layers[i].train(error, learningRate, momentum)
//        }
//    }
//
//    static void main(String[] args) throws Exception {
//        def train = [[0d, 0d], [0d, 1d], [1d, 0d], [1d, 1d]]
//        def res = [[0d], [1d], [1d], [0d]]
//        __MLP mlp = new __MLP(2, [2, 1])
//        mlp.layers[1].isSigmoid = false
//        Random r = new Random()
//        500.times { int e ->
//
//            res.each {
//                int idx = r.nextInt(res.size());
//                mlp.train(train[idx], res[idx], 0.3d, 0.6d);
//            }
//
//            if ((e + 1) % 100 == 0) {
//                System.out.println();
//                (res.size() as Integer).times { int i ->
//                    List<Double> t = train[i];
//                    Log.d(App.LOG_TAG, String.format("%d epoch\n", e + 1))
//                    Log.d(App.LOG_TAG, String.format("%.1d, %.1d --> %.3d\n", t[0], t[1], mlp.run(t)[0]))
//                }
//            }
//        }
//    }
//}