#include <cstdio>
#include <iostream>
#include <set>
#include <iomanip>
#include <bitset>
#include <fstream>
#include <algorithm>

//#define DEBUG

#ifdef DEBUG
#define d(str) do {cout << str << endl;} while (false)
#else
#define d(str) do {} while (false)
#endif

using namespace std;

int main(int argc, char const *argv[]) {
    ifstream fin("day4_input.in");
    
    int ans = 0;
    string s;
    while (getline(fin, s)) {
        unsigned long len = s.length();
        if (len == 0)
            break;
        
        set<string> st;
        d(st.size());
        
        unsigned long i = 0;
        while (i < len) {
            unsigned long iNew = s.find(' ', i);
            if (iNew >= len)
                iNew = len - 1;
            string word = s.substr(i, iNew - i);
            if (word.length() == 0)
                continue;
            
            sort(word.begin(), word.end());
            
            d(word << " " << i << " " << iNew);
    
            unsigned long prev = st.size();
            st.insert(word);
            if (prev == st.size())
                goto outer;
            
            i = iNew + 1;
        }
        ans++;
        
        outer:;
        d("");
    }
    
    cout << ans << endl;
    fin.close();
    return 0;
}