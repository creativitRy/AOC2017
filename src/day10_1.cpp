#include <cstdio>
#include <iostream>
#include <vector>
#include <sstream>
#include <fstream>

#define DEBUG

#ifdef DEBUG
#define d(str) do {cout << str << endl;} while (false)
#else
#define d(str) do {} while (false)
#endif

using namespace std;

static const int SIZE = 256;

int main(int argc, char const *argv[]) {
    ifstream fin("day10_input.in");
    
    int list[SIZE];
    for (int i = 0; i < SIZE; ++i) {
        list[i] = i;
    }
    
    string line;
    fin >> line;
    vector<int> nums;
    
    unsigned long splitIndex = 0;
    unsigned long lineLen = line.length();
    while (splitIndex < lineLen) {
        unsigned long iNew = line.find(',', splitIndex);
        if (iNew > lineLen)
            iNew = lineLen;
        string num = line.substr(splitIndex, iNew - splitIndex);
        if (num.length() == 0)
            break;
        
        stringstream ss(num);
        int temp;
        ss >> temp;
        nums.insert(nums.end(), temp);
        
        splitIndex = iNew + 1;
    }
    //for (int len : nums)
    //    cout << len << " ";
    //cout << endl;
    
    int pos = 0;
    int skipSize = 0;
    
    for (int len : nums) {
        vector<int> flip;
        for (int i = 0; i < len; ++i) {
            flip.insert(flip.begin(), list[(i + pos) % SIZE]);
        }
        for (int i = 0; i < len; ++i) {
            list[(i + pos) % SIZE] = flip[i];
        }
        pos = (pos + len + skipSize) % SIZE;
        skipSize++;
    }
    
    cout << list[0] * list[1] << endl;
    
    fin.close();
    return 0;
}