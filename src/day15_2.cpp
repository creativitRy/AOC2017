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
    for (int i = 0; i < 5000000; ++i) {
        while (true) {
            a = mod(a * aa);
            if ((a & 3) == 0) // if a is divisible by 4
                break;
        }
        while (true) {
            b = mod(b * bb);
            if ((b & 7) == 0) // if b is divisible by 8
                break;
        }
        
        if ((a & 0xffff) == (b & 0xffff))
            ans++;
    }
    cout << ans << endl;
    
    fin.close();
    return 0;
}