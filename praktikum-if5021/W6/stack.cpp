#include <iostream>
#include <stack>
#include <iterator>

using namespace std;

int main() {
    int N, i, jumlahGenap;
    int valAwal, valAkhir, valFibo, valTop;
    stack<int> SI;
    stack<int> SI_Cetak;
    
    valAwal = 1;
    valAkhir = 1;

    cin >> N;
    for (i = 1; i <= N; i++) {
        if (i == 1 || i == 2) {
            SI.push(1);
        } else {
            valFibo = valAwal + valAkhir;
            valAwal = valAkhir;
            valAkhir = valFibo;
            
            SI.push(valFibo);
        }
    }

    jumlahGenap = 0;
    while (SI.size() != 0) {
        valTop = SI.top();
        SI_Cetak.push(valTop);
        
        if (valTop % 2 == 0)
            jumlahGenap++;
        SI.pop();
    }
    
    while(SI_Cetak.size() != 0) {
        cout << SI_Cetak.top() << endl;
        SI_Cetak.pop();
    }

    cout << "STACK KOSONG" << endl;
    cout << jumlahGenap << endl;
    
    return 0;
}
