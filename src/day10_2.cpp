#include <cstdio>
#include <iomanip>
#include <iostream>
#include <fstream>
#include "util/knothash.h"

#define DEBUG

#ifdef DEBUG
#define d(str) do {cout << str << endl;} while (false)
#else
#define d(str) do {} while (false)
#endif

using namespace std;

int main(int argc, char const *argv[]) {
    ifstream fin("day10_input.in");
    
    string line;
    fin >> line;
    
    cout << knotHash(line) << endl;
    
    fin.close();
    return 0;
}