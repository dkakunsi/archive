#include <iostream>
#include <vector>
#include <iterator>

using namespace std;

typedef struct {
	char c;
	int count;
	int updated;
} Data;

typedef struct {
	int max;
	int min;
} MaxMin;

void bacaChar(int N, vector<char> * VC);
void hitung(vector<char> VC, vector<Data> * VD, MaxMin * mm);
void findData(vector<Data> VD, Data *data);
void pushData(vector<Data> * VD, Data data, MaxMin * mm);
void cetak(vector<Data> VD, MaxMin mm);

int main() {
    vector<char> VC;
	vector<Data> VD;
	MaxMin mm;
    int N;
	
	cin >> N;
	
	bacaChar(N, &VC);
    hitung(VC, &VD, &mm);
	cetak(VD, mm);
	cout << endl;

    return 0;
}

/* baca karakter dan masukkan ke dalam vector karakter */
void bacaChar(int N, vector<char> * VC) {
	char c;
	int T;

    while (VC->size() < N) {
		cin >> c;
		/* pengecekan c apakah valid, jika tidak keluar dari perulangan */
		if (!((c >= 'a' && c <= 'y') || (c >= 'A' && c <= 'Y')))
			break;

		cin >> T;
		/* T yang diinput harus > 0 dan space vektor harus mencukupi penambahan sejumlah T-buah karakter */
		if (T <= 0 || (N - VC->size()) < T)
			break;

		if ((c != 'z' && c != 'Z')) {
			for (; T > 0; T--)
				VC->push_back(c);
		}
    }
	
	/* Jika masih tersedia space kosong dalam vector, isi dengan karakter 'Z'*/
	while(VC->size() < N) {
		VC->push_back('Z');
	}
}

void hitung(vector<char> VC, vector<Data> * VD, MaxMin * mm) {
	vector<char>::iterator iter;
	Data data;
	
	data.c = '-';
	data.count = 0;
	data.updated = 0;
	mm->min = 1000;
	mm->max = 0;

	for (iter = VC.begin(); iter != VC.end(); iter++) {

		/* jika data char saat ini sama dengan karakter yang ditunjuk pointer, tambahkan jumlah count data */
		if (data.c == *iter) {
			data.count++;
		} else {
			/* jika jumlah data lebih dari nol. State data sebelumnya harus di-push ke vector data */
			if (data.count > 0)
				pushData(VD, data, mm);

			/* re-inisialisasi untuk data char baru */
			data.c = *iter;
			data.count = 0;
			data.updated = 0;

			findData(*VD, &data);
			data.count++;
		}
	}

	/* push data terakhir ke dalam vector data */
	if (data.count > 0)
		pushData(VD, data, mm);
}

/* mencari data terakhir yang pernah diproses sebelumnya */
void findData(vector<Data> VD, Data *data) {
	vector<Data>::iterator iter;
	Data tmp;
	
	iter = VD.begin();
	while(iter != VD.end()) {
		tmp = *iter;
		if (tmp.c == data->c) {
			*data = tmp;
			data->updated = 1;
			break;
		}
		iter++;
	}
}

/* push data ke dalam vector */
/* jika karakter yang sama pernah diproses, ganti data yang lama dengan yang baru */
void pushData(vector<Data> * VD, Data data, MaxMin *mm) {
	vector<Data> second;
	vector<Data>::iterator iter;
	Data tmp;

	/* jika data updated = 1, maka terdapat data yang sama di dalam vector data dan harus diorganisir lagi */
	if (data.updated) {
		for (iter = VD->begin(); iter != VD->end(); iter++) {
			tmp = *iter;
			
			/* jika data yang sama ditemukan. maka push data baru, data lama (tmp) diabaikan */
			if (tmp.c == data.c) {
				second.push_back(data);
			} else {
				second.push_back(tmp);
			}
		}
		
		*VD = second;
	} else {
		VD->push_back(data);
	}
	
	/* reset nilai maximum dan minimum */
	mm->max = data.count > mm->max ? data.count : mm->max;
	mm->min = data.count < mm->min ? data.count : mm->min;
}

void cetak(vector<Data> VD, MaxMin mm) {
	vector<Data>::iterator iter;
	Data data;
	
	for (iter = VD.begin(); iter != VD.end(); iter++) {
		data = *iter;
		cout << data.c << " " << data.count << endl;
	}
	
	cout << "Max =";
	for (iter = VD.begin(); iter != VD.end(); iter++) {
		data = *iter;
		if (data.count == mm.max)
			cout << " " << data.c;
	}
	
	cout << endl << "Min =";
	for (iter = VD.begin(); iter != VD.end(); iter++) {
		data = *iter;
		if (data.count == mm.min)
			cout << " " << data.c;
	}
}
