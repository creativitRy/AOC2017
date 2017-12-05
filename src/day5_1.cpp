#include <cstdio>
#include <iostream>
#include <vector>
#include <sstream>
#include <iomanip>
#include <bitset>
#include <fstream>

#define DEBUG

#ifdef DEBUG
#define d(str) do {cout << (str) << endl;} while (false)
#else
#define d(str) do {} while (false)
#endif

using namespace std;

int main(int argc, char const *argv[]) {
    ifstream fin("day5_input.in");
    
    vector<int> v;
    
    string s;
    while (getline(fin, s)) {
        unsigned long len = s.length();
        if (len == 0)
            break;
        
        stringstream st(s);
        
        int x;
        st >> x;
        v.insert(v.end(), x);
    }
    
    unsigned int i = 0;
    unsigned int count = 0;
    while (i >= 0 && i < v.size()) {
        int ii = v[i];
        v[i]++;
        i += ii;
        count++;
    }
    
    cout << count << endl;
    
    fin.close();
    return 0;
}