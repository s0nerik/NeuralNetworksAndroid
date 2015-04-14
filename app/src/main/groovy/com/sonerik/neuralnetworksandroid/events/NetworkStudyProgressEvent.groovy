package com.sonerik.neuralnetworksandroid.events
import groovy.transform.CompileStatic

@CompileStatic
class NetworkStudyProgressEvent {
    int epochsPassed
    int maxEpochs
    double averageError
}