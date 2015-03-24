package com.sonerik.neuralnetworksandroid.logic.perceptron

import groovy.transform.CompileStatic

@CompileStatic
class Edge {
    Node source
    Node target
    Double weight

    Edge(Node source, Node target) {
        this.source = source
        this.target = target
        this.weight = 1d - Math.random() * 2d // Random number in -1..1 range
        source.outgoingEdges << this
        target.incomingEdges << this
    }
}
