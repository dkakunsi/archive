#include<stdio.h>
#include<stdlib.h>

typedef struct Data {
	int angka;
	int sisa;
} Data;

Data ambilAngka(int pembilang, int penyebut);

int main() {
	int bilangan, hasil;
	int hasilMax = 0, bilanganMax = 0;
	int i, x = 3;
	Data data[x];

	scanf("%d", &bilangan);
	if (bilangan < 10000) {
		
		while (bilangan < 10000) {
			hasil = 0;

			data[0] = ambilAngka(bilangan, 1000); /* memuat angka ribuan dan sisa ratusan */
			data[1] = ambilAngka(data[0].sisa, 100); /* memuat angka ratusan dan sisa puluhan */
			data[2] = ambilAngka(data[1].sisa, 10); /* memuat angka puluhan dan sisa satuan */

			/* menjumlahkan setiap digit */
			for (i = 0; i < x; i++) {
				hasil += data[i].angka;
			}
			hasil += data[x-1].sisa; /* satuan diambil dari sisa puluhan */
			
			/* cek hasil untuk menentukan bilangan dengan jumlah digit terbesar*/
			if (hasilMax < hasil) {
				hasilMax = hasil;
				bilanganMax = bilangan;
			}
			printf("%d\n", hasil);

			/* baca lagi */
			scanf("%d", &bilangan);
		}
		
		printf("%d", bilanganMax);
	} else {
		printf("Tidak ada data");
	}

	printf("\n");
	return 0;
}

Data ambilAngka(int pembilang, int penyebut) {
	Data data;
	
	pembilang = abs(pembilang);
	
	data.angka = pembilang / penyebut;
	data.sisa = pembilang - (data.angka * penyebut);
	
	return data;
}
