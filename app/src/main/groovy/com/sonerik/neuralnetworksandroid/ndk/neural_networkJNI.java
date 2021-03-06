/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.sonerik.neuralnetworksandroid.ndk;

public class neural_networkJNI {
  public final static native long new_VectorOfUnsigned__SWIG_0();
  public final static native long new_VectorOfUnsigned__SWIG_1(long jarg1);
  public final static native long VectorOfUnsigned_size(long jarg1, VectorOfUnsigned jarg1_);
  public final static native long VectorOfUnsigned_capacity(long jarg1, VectorOfUnsigned jarg1_);
  public final static native void VectorOfUnsigned_reserve(long jarg1, VectorOfUnsigned jarg1_, long jarg2);
  public final static native boolean VectorOfUnsigned_isEmpty(long jarg1, VectorOfUnsigned jarg1_);
  public final static native void VectorOfUnsigned_clear(long jarg1, VectorOfUnsigned jarg1_);
  public final static native void VectorOfUnsigned_add(long jarg1, VectorOfUnsigned jarg1_, long jarg2);
  public final static native long VectorOfUnsigned_get(long jarg1, VectorOfUnsigned jarg1_, int jarg2);
  public final static native void VectorOfUnsigned_set(long jarg1, VectorOfUnsigned jarg1_, int jarg2, long jarg3);
  public final static native void delete_VectorOfUnsigned(long jarg1);
  public final static native long new_VectorOfDouble__SWIG_0();
  public final static native long new_VectorOfDouble__SWIG_1(long jarg1);
  public final static native long VectorOfDouble_size(long jarg1, VectorOfDouble jarg1_);
  public final static native long VectorOfDouble_capacity(long jarg1, VectorOfDouble jarg1_);
  public final static native void VectorOfDouble_reserve(long jarg1, VectorOfDouble jarg1_, long jarg2);
  public final static native boolean VectorOfDouble_isEmpty(long jarg1, VectorOfDouble jarg1_);
  public final static native void VectorOfDouble_clear(long jarg1, VectorOfDouble jarg1_);
  public final static native void VectorOfDouble_add(long jarg1, VectorOfDouble jarg1_, double jarg2);
  public final static native double VectorOfDouble_get(long jarg1, VectorOfDouble jarg1_, int jarg2);
  public final static native void VectorOfDouble_set(long jarg1, VectorOfDouble jarg1_, int jarg2, double jarg3);
  public final static native void delete_VectorOfDouble(long jarg1);
  public final static native long new_VectorOfVectorOfDouble__SWIG_0();
  public final static native long new_VectorOfVectorOfDouble__SWIG_1(long jarg1);
  public final static native long VectorOfVectorOfDouble_size(long jarg1, VectorOfVectorOfDouble jarg1_);
  public final static native long VectorOfVectorOfDouble_capacity(long jarg1, VectorOfVectorOfDouble jarg1_);
  public final static native void VectorOfVectorOfDouble_reserve(long jarg1, VectorOfVectorOfDouble jarg1_, long jarg2);
  public final static native boolean VectorOfVectorOfDouble_isEmpty(long jarg1, VectorOfVectorOfDouble jarg1_);
  public final static native void VectorOfVectorOfDouble_clear(long jarg1, VectorOfVectorOfDouble jarg1_);
  public final static native void VectorOfVectorOfDouble_add(long jarg1, VectorOfVectorOfDouble jarg1_, long jarg2, VectorOfDouble jarg2_);
  public final static native long VectorOfVectorOfDouble_get(long jarg1, VectorOfVectorOfDouble jarg1_, int jarg2);
  public final static native void VectorOfVectorOfDouble_set(long jarg1, VectorOfVectorOfDouble jarg1_, int jarg2, long jarg3, VectorOfDouble jarg3_);
  public final static native void delete_VectorOfVectorOfDouble(long jarg1);
  public final static native void delete_Callback(long jarg1);
  public final static native void Callback_epochPassed(long jarg1, Callback jarg1_, int jarg2, double jarg3);
  public final static native void Callback_epochPassedSwigExplicitCallback(long jarg1, Callback jarg1_, int jarg2, double jarg3);
  public final static native long new_Callback();
  public final static native void Callback_director_connect(Callback obj, long cptr, boolean mem_own, boolean weak_global);
  public final static native void Callback_change_ownership(Callback obj, long cptr, boolean take_or_release);
  public final static native long NetworkTrainer_trainNetwork__SWIG_0(long jarg1, NetworkTrainer jarg1_, long jarg2, VectorOfUnsigned jarg2_, long jarg3, VectorOfVectorOfDouble jarg3_, long jarg4, VectorOfVectorOfDouble jarg4_, int jarg5, double jarg6, double jarg7, double jarg8);
  public final static native long NetworkTrainer_trainNetwork__SWIG_1(long jarg1, NetworkTrainer jarg1_, long jarg2, VectorOfUnsigned jarg2_, long jarg3, VectorOfVectorOfDouble jarg3_, long jarg4, VectorOfVectorOfDouble jarg4_, int jarg5, double jarg6, double jarg7);
  public final static native long NetworkTrainer_trainNetwork__SWIG_2(long jarg1, NetworkTrainer jarg1_, long jarg2, VectorOfUnsigned jarg2_, long jarg3, VectorOfVectorOfDouble jarg3_, long jarg4, VectorOfVectorOfDouble jarg4_, int jarg5, double jarg6);
  public final static native void delete_NetworkTrainer(long jarg1);
  public final static native void NetworkTrainer_delCallback(long jarg1, NetworkTrainer jarg1_);
  public final static native void NetworkTrainer_setCallback(long jarg1, NetworkTrainer jarg1_, long jarg2, Callback jarg2_);
  public final static native long new_NetworkTrainer();
  public final static native void LearningResult_trainingSetOutputs_set(long jarg1, LearningResult jarg1_, long jarg2, VectorOfVectorOfDouble jarg2_);
  public final static native long LearningResult_trainingSetOutputs_get(long jarg1, LearningResult jarg1_);
  public final static native void LearningResult_epochsPassed_set(long jarg1, LearningResult jarg1_, int jarg2);
  public final static native int LearningResult_epochsPassed_get(long jarg1, LearningResult jarg1_);
  public final static native void LearningResult_averageError_set(long jarg1, LearningResult jarg1_, double jarg2);
  public final static native double LearningResult_averageError_get(long jarg1, LearningResult jarg1_);
  public final static native long new_LearningResult();
  public final static native void delete_LearningResult(long jarg1);

  public static void SwigDirector_Callback_epochPassed(Callback jself, int epoch, double recentAverageError) {
    jself.epochPassed(epoch, recentAverageError);
  }

  private final static native void swig_module_init();
  static {
    swig_module_init();
  }
}
