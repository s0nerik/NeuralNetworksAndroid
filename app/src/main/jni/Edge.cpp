#include <stdlib.h>
#include "Edge.h"

Edge::Edge(std::shared_ptr<Node> source, std::shared_ptr<Node> target) {
    _source = source;
    _target = target;
    _weight = rand() / (double) RAND_MAX; // Random number in 0..1 range
    source->outgoingEdges.push_back(shared_from_this());
    target->incomingEdges.push_back(shared_from_this());
}