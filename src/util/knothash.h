#include <cstdio>
#include <iomanip>
#include <iostream>
#include <vector>
#include <sstream>

using namespace std;

static const int SIZE = 256;

string knotHash(const string &input) {
    int list[SIZE];
    for (int i = 0; i < SIZE; ++i) {
        list[i] = i;
    }
    
    vector<int> nums;
    unsigned long lineLen = input.length();
    for (int i = 0; i < lineLen; ++i) {
        nums.insert(nums.end(), input[i]);
    }
    nums.insert(nums.end(), 17);
    nums.insert(nums.end(), 31);
    nums.insert(nums.end(), 73);
    nums.insert(nums.end(), 47);
    nums.insert(nums.end(), 23);
    
    int pos = 0;
    int skipSize = 0;
    for (int round = 0; round < 64; ++round) {
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
    }
    
    stringstream ans;
    for (int i = 0; i < 16; ++i) {
        int x = list[i * 16];
        for (int j = 1; j < 16; ++j) {
            x ^= list[i * 16 + j];
        }
        stringstream ss;
        ss << hex << x;
        string s = ss.str();
        if (s.length() == 1)
            ans << 0;
        ans << s;
    }
    
    return ans.str();
}
