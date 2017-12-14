#include <cstdio>
#include <iostream>
#include <iomanip>
#include <bitset>
#include <fstream>
#include "util/knothash.h"

#define DEBUG

#ifdef DEBUG
#define d(str) do {cout << str << endl;} while (false)
#else
#define d(str) do {} while (false)
#endif

using namespace std;

string hexToBin(const char hex) {
    // I'm sorry lol
    switch (hex) {
        case '0' :
            return "0000";
        case '1' :
            return "0001";
        case '2' :
            return "0010";
        case '3' :
            return "0011";
        case '4' :
            return "0100";
        case '5' :
            return "0101";
        case '6' :
            return "0110";
        case '7' :
            return "0111";
        case '8' :
            return "1000";
        case '9' :
            return "1001";
        case 'a' :
            return "1010";
        case 'b' :
            return "1011";
        case 'c' :
            return "1100";
        case 'd' :
            return "1101";
        case 'e' :
            return "1110";
        case 'f' :
            return "1111";
    }
}

int region(string bins[], int r, int c) {
    if (r < 0 || c < 0 || r >= 128 || c >= 128)
        return 0;
    if (bins[r][c] == '0')
        return 0;
    bins[r][c] = '0';
    region(bins, r - 1, c);
    region(bins, r + 1, c);
    region(bins, r, c - 1);
    region(bins, r, c + 1);
    return 1;
}

int main(int argc, char const *argv[]) {
    ifstream fin("day14_1.in");
    
    string line;
    fin >> line;
    
    string bins[128];
    for (int i = 0; i < 128; ++i) {
        string hash = knotHash(line + "-" + to_string(i));
        string binary;
        binary.reserve(128);
        for (char h : hash) {
            binary += hexToBin(h);
        }
        bins[i] = binary;
    }
    
    unsigned int ans = 0;
    for (int i = 0; i < 128; i++) {
        for (int j = 0; j < 128; j++) {
            ans += region(bins, i, j);
        }
    }
    
    cout << ans << endl;
    
    fin.close();
    return 0;
}