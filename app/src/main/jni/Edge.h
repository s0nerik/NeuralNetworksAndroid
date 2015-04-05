#ifndef JNI_EDGE_H
#define JNI_EDGE_H


#include <memory>

class Node;

class Edge : public std::enable_shared_from_this<Edge> {

public:
    std::shared_ptr<Node> _source;
    std::shared_ptr<Node> _target;
    double _weight;

    Edge(std::shared_ptr<Node> source, std::shared_ptr<Node> target);
};


#endif //JNI_EDGE_H
