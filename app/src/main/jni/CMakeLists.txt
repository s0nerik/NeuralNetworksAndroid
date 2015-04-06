cmake_minimum_required(VERSION 3.1)
project(jni)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++1y")

set(SOURCE_FILES main.cpp)
add_executable(jni ${SOURCE_FILES} Network.cpp Network.h Node.cpp Node.h Edge.cpp Edge.h InputNode.cpp InputNode.h BiasNode.cpp BiasNode.h Layer.cpp Layer.h OutputNode.cpp OutputNode.h)