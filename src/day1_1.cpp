#include <cstdio>
#include <iostream>
#include <iomanip>
#include <bitset>

//#define DEBUG

#ifdef DEBUG
#define d(str) do {cout << (str) << endl;} while (false)
#else
#define d(str) do {} while (false)
#endif

using namespace std;

int main(int argc, char const *argv[]) {
    string input = "91212129";
    
    int sum = 0;
    unsigned long len = input.length();
    for (int i = 0; i < len; ++i) {
        if (input[i] == input[(i + 1) % len]) {
            sum += input[i] - '0';
            d(i);
            
        }
    }
    
    cout << sum << endl;
    
    return 0;
}