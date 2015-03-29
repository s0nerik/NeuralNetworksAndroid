package com.sonerik.neuralnetworksandroid.logic.perceptron

import groovy.transform.CompileStatic

@CompileStatic
class Edge {
    Node source
    Node target
    double weight

    Edge(Node source, Node target) {
        this.source = source
        this.target = target
        this.weight = Math.random() // Random number in 0..1 range
        source.outgoingEdges << this
        target.incomingEdges << this
    }
}
