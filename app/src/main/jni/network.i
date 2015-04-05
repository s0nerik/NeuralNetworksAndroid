/* File : example.i */
%module network

%{
#include "Network.h"
%}

/* Let's just grab the original header file here */
%include "Network.h"
