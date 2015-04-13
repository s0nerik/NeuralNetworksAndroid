%module(directors="1") neural_network

%{
#include "Callback.h"
#include "NetworkTrainer.h"
#include "LearningResult.h"
%}

%include "std_vector.i"
// Instantiate templates used by example
namespace std {
   %template(VectorOfUnsigned) vector<unsigned>;
   %template(VectorOfDouble) vector<double>;
   %template(VectorOfVectorOfDouble) vector<vector<double>>;
}

%feature("director") Callback;

%include "Callback.h"
%include "NetworkTrainer.h"
%include "LearningResult.h"
