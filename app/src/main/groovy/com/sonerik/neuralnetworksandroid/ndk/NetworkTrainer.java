/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.sonerik.neuralnetworksandroid.ndk;

public class NetworkTrainer {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected NetworkTrainer(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(NetworkTrainer obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        neural_networkJNI.delete_NetworkTrainer(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public LearningResult trainNetwork(VectorOfUnsigned topology, VectorOfVectorOfDouble trainingSets, VectorOfVectorOfDouble expectedOutputs, int maxEpochs, double acceptableError, double learningRate, double momentum) {
    return new LearningResult(neural_networkJNI.NetworkTrainer_trainNetwork__SWIG_0(swigCPtr, this, VectorOfUnsigned.getCPtr(topology), topology, VectorOfVectorOfDouble.getCPtr(trainingSets), trainingSets, VectorOfVectorOfDouble.getCPtr(expectedOutputs), expectedOutputs, maxEpochs, acceptableError, learningRate, momentum), true);
  }

  public LearningResult trainNetwork(VectorOfUnsigned topology, VectorOfVectorOfDouble trainingSets, VectorOfVectorOfDouble expectedOutputs, int maxEpochs, double acceptableError, double learningRate) {
    return new LearningResult(neural_networkJNI.NetworkTrainer_trainNetwork__SWIG_1(swigCPtr, this, VectorOfUnsigned.getCPtr(topology), topology, VectorOfVectorOfDouble.getCPtr(trainingSets), trainingSets, VectorOfVectorOfDouble.getCPtr(expectedOutputs), expectedOutputs, maxEpochs, acceptableError, learningRate), true);
  }

  public LearningResult trainNetwork(VectorOfUnsigned topology, VectorOfVectorOfDouble trainingSets, VectorOfVectorOfDouble expectedOutputs, int maxEpochs, double acceptableError) {
    return new LearningResult(neural_networkJNI.NetworkTrainer_trainNetwork__SWIG_2(swigCPtr, this, VectorOfUnsigned.getCPtr(topology), topology, VectorOfVectorOfDouble.getCPtr(trainingSets), trainingSets, VectorOfVectorOfDouble.getCPtr(expectedOutputs), expectedOutputs, maxEpochs, acceptableError), true);
  }

  public NetworkTrainer() {
    this(neural_networkJNI.new_NetworkTrainer(), true);
  }

}
