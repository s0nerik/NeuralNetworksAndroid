package com.sonerik.neuralnetworksandroid.logic.perceptron

import groovy.transform.CompileStatic
import org.la4j.LinearAlgebra
import org.la4j.Matrix
import org.la4j.Vector
import org.la4j.matrix.dense.Basic2DMatrix
import org.la4j.vector.dense.BasicVector

@CompileStatic
class Network {

    Matrix matrix
    Vector weights
    double[][] samples
    double r

    double h(int xRow, int cRow) {
        double power = 0d
        for (int i = 0; i < samples[0].length; i++) {
            power += Math.pow(samples[xRow][i] - samples[cRow][i], 2)
        }
        power = -power / Math.pow(r, 2)

        Math.exp(power as double)
    }

    double[] study(double[] outs) {
        for (int i = 0; i < matrix.rows(); i++) {
            for (int j = 0; j < matrix.columns(); j++) {
                matrix.set(i, j, h(i, j))
            }
        }

        def aInverted = (matrix.transpose() * matrix).withInverter(LinearAlgebra.InverterFactory.SMART).inverse()
        weights = aInverted * matrix.transpose() * new BasicVector(outs)

        return (weights * matrix as BasicVector).toArray()
    }

    static Network make(double[][] samples, int neuronsInHiddenLayer, double r) {
        double[][] data = new double[samples.length][neuronsInHiddenLayer]
        new Network(samples: samples, matrix: new Basic2DMatrix(data), r: r)
    }
}