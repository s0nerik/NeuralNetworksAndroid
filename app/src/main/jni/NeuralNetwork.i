%module neural_network

%{
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

%include "NetworkTrainer.h"
%include "LearningResult.h"
