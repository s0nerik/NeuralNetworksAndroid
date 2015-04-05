#include <iostream>
#include "Network.h"

using namespace std;

int main() {

    std::vector<std::vector<double>> patterns {
            {0, 0, 0, 1},
            {0, 0, 1, 0},
            {0, 1, 0, 1},
            {0, 1, 1, 0},
            {1, 0, 0, 1},
            {1, 0, 1, 0},
            {1, 1, 0, 1},
            {1, 1, 1, 0}
    };

    for (auto result : Network::test(patterns)) {
        cout << result << endl;
    }

    return 0;
}