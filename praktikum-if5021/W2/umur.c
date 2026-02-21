// Deddy Ch. Kakunsi

#include<stdio.h>

int* sort(int *arr, int size);
void cetakUmurTengah(int *arr, int size);

int main() {
	int jumlahSiswa = 0;
	int *umurSiswa;
	int i;

	while(jumlahSiswa <=0 ) {
		scanf("%d", &jumlahSiswa);
	}
	
	umurSiswa = malloc(sizeof(int) * jumlahSiswa);
	
	for (i = 0; i < jumlahSiswa; i++) {
		scanf("%d", &umurSiswa[i]);
	}
	
	umurSiswa = sort(umurSiswa, jumlahSiswa);
	cetakUmurTengah(umurSiswa, jumlahSiswa);

	printf("\n");
	return 0;
}

int* sort(int *arr, int size) {
	int i, j, tmp;
	
	for (i = 0; i < size; i++) {
		for (j = i+1; j < size; j++) {
			if (arr[i] < arr[j]) {
				tmp = arr[i];
				arr[i] = arr[j];
				arr[j] = tmp;
			}
		} 
	}
	
	return arr;
}

void cetakUmurTengah(int *arr, int size) {
	int i, jumlah = 0;
	
	for (i = 0; i < size; i++) {
		if (arr[i] == arr[size/2]) {
			jumlah++;
		}
	}
	
	printf("%d\n", arr[size/2]);
	printf("%d", jumlah);
}

