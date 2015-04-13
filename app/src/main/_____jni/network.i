/* File : example.i */
%module network

%{
#include "Network.h"
%}

%include "std_vector.i"
// Instantiate templates used by example
namespace std {
   %template(VectorOfUnsigned) vector<unsigned>;
   %template(VectorOfDouble) vector<double>;
   %template(VectorOfVectorOfDouble) vector<vector<double>>;
}

/* Let's just grab the original header file here */
%include "Network.h"
