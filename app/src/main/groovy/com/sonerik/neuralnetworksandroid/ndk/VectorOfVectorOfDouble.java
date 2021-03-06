/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.sonerik.neuralnetworksandroid.ndk;

public class VectorOfVectorOfDouble {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected VectorOfVectorOfDouble(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(VectorOfVectorOfDouble obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        neural_networkJNI.delete_VectorOfVectorOfDouble(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public VectorOfVectorOfDouble() {
    this(neural_networkJNI.new_VectorOfVectorOfDouble__SWIG_0(), true);
  }

  public VectorOfVectorOfDouble(long n) {
    this(neural_networkJNI.new_VectorOfVectorOfDouble__SWIG_1(n), true);
  }

  public long size() {
    return neural_networkJNI.VectorOfVectorOfDouble_size(swigCPtr, this);
  }

  public long capacity() {
    return neural_networkJNI.VectorOfVectorOfDouble_capacity(swigCPtr, this);
  }

  public void reserve(long n) {
    neural_networkJNI.VectorOfVectorOfDouble_reserve(swigCPtr, this, n);
  }

  public boolean isEmpty() {
    return neural_networkJNI.VectorOfVectorOfDouble_isEmpty(swigCPtr, this);
  }

  public void clear() {
    neural_networkJNI.VectorOfVectorOfDouble_clear(swigCPtr, this);
  }

  public void add(VectorOfDouble x) {
    neural_networkJNI.VectorOfVectorOfDouble_add(swigCPtr, this, VectorOfDouble.getCPtr(x), x);
  }

  public VectorOfDouble get(int i) {
    return new VectorOfDouble(neural_networkJNI.VectorOfVectorOfDouble_get(swigCPtr, this, i), false);
  }

  public void set(int i, VectorOfDouble val) {
    neural_networkJNI.VectorOfVectorOfDouble_set(swigCPtr, this, i, VectorOfDouble.getCPtr(val), val);
  }

}
