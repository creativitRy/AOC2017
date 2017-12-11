#include <cstdio>
#include <iostream>
#include <vector>
#include <fstream>

#define DEBUG

#ifdef DEBUG
#define d(str) do {cout << str << endl;} while (false)
#else
#define d(str) do {} while (false)
#endif

using namespace std;

struct Hex {
    int x, y, z;
    
    Hex(int xx, int yy, int zz) {
        x = xx;
        y = yy;
        z = zz;
    }
    
    Hex operator+(string const &dir) {
        Hex hex(x, y, z);
        if (dir == "n") {
            hex.x--;
            hex.y++;
        } else if (dir == "ne") {
            hex.y++;
            hex.z--;
        } else if (dir == "se") {
            hex.x++;
            hex.z--;
        } else if (dir == "s") {
            hex.x++;
            hex.y--;
        } else if (dir == "sw") {
            hex.y--;
            hex.z++;
        } else {
            hex.x--;
            hex.z++;
        }
        return hex;
    }
    
    int distance() {
        return max(max(abs(x), abs(y)), abs(z));
    }
};

int main(int argc, char const *argv[]) {
    ifstream fin("day11_input.in");
    
    string line;
    fin >> line;
    vector<string> dirs;
    
    unsigned long splitIndex = 0;
    unsigned long lineLen = line.length();
    while (splitIndex < lineLen) {
        unsigned long iNew = line.find(',', splitIndex);
        if (iNew > lineLen)
            iNew = lineLen;
        string dir = line.substr(splitIndex, iNew - splitIndex);
        if (dir.length() == 0)
            break;
        
        dirs.insert(dirs.end(), dir);
        splitIndex = iNew + 1;
    }
    
    Hex hex(0, 0, 0);
    int maxDist = 0;
    for (const auto &dir : dirs) {
        hex = hex + dir;
        maxDist = max(maxDist, hex.distance());
    }
    cout << maxDist << endl;
    
    fin.close();
    return 0;
}