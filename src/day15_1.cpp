#include <cstdio>
#include <iostream>
#include <iomanip>
#include <bitset>
#include <fstream>

#define DEBUG

#ifdef DEBUG
#define d(str) do {cout << str << endl;} while (false)
#else
#define d(str) do {} while (false)
#endif

using namespace std;

#define ulong unsigned long long

ulong mod(ulong num) {
    return num % 2147483647;
}

int main(int argc, char const *argv[]) {
    ifstream fin("day15_input.in");
    
    string line;
    ulong a;
    ulong b;
    fin >> line >> line >> line >> line >> a;
    fin >> line >> line >> line >> line >> b;
    
    ulong aa = 16807;
    ulong bb = 48271;
    
    ulong ans = 0;
    for (int i = 0; i < 40000000; ++i) {
        a = mod(a * aa);
        b = mod(b * bb);
        
        if ((a & 0xffff) == (b & 0xffff))
            ans++;
    }
    cout << ans << endl;
    
    fin.close();
    return 0;
}