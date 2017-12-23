#include <cstdio>
#include <iostream>
#include <math.h>

#define DEBUG

#ifdef DEBUG
#define d(str) do {cout << str << endl;} while (false)
#else
#define d(str) do {} while (false)
#endif

using namespace std;

bool isPrime(int n) {
    if (n == 1)
        return false;
    if (n == 2)
        return true;
    
    if (n % 2 == 0 || n % 3 == 0)
        return false;
    
    int temp = (int) sqrt(n);
    for (int i = 5; i <= temp; i += 6)
        if (n % i == 0 || n % (i + 2) == 0)
            return false;
    
    return true;
}

int main(int argc, char const *argv[]) {
    // WARNING: hardcoded to my input
    
    int h = 0;
    int b = 65 * 100 + 100000;
    int c = b + 17000;
    while (true) {
        if (!isPrime(b))
            ++h;
        
        if (b == c)
            break;
        
        b += 17;
    }
    
    cout << h << endl;
    
    return 0;
}
