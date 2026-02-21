#include<stdio.h>
#include<stdlib.h>

int cetak(int i, int isTidakAda);

int main() {
	int N, X, i, jumlahFaktorialX, *bilangan;
	int isTidakAda;

	scanf("%d", &N);
	while(N <= 0) {
		printf("Harus > 0\n");
		scanf("%d", &N);
	}

	bilangan = (int*) malloc(sizeof(int) * N);
	
	i = 0;
	while (i < N) {
		scanf("%d", &bilangan[i]);
		i++;
	}
	
	/* baca nilai X */
	scanf("%d", &X);
	
	/* cetak sama */
	i = 0;
	isTidakAda = 1;
	while (i < N) {
		if (bilangan[i] == X) {
			isTidakAda = cetak(i + 1, isTidakAda);
		}
		
		i++;
	}
	
	if (isTidakAda) {
		printf("%d tidak ada", X);
	}
	printf("\n");
	
	/* cetak faktorial */
	i = 0;
	jumlahFaktorialX = 0;
	isTidakAda = 1;
	while (i < N) {
		if (X % bilangan[i] == 0) {
			isTidakAda = cetak(bilangan[i], isTidakAda);
			jumlahFaktorialX++;
		}
		
		i++;
	}
	
	if (!isTidakAda) {
		printf("\n");
	}

	/* cetak jumlah faktorial */
	printf("%d", jumlahFaktorialX);

	printf("\n");
	free(bilangan);
	return 0;
}

int cetak(int i, int isTidakAda) {
	if (isTidakAda) {
		printf("%d", i);
		isTidakAda = 0;
	} else {
		printf(";%d", i);
	}
	
	return isTidakAda;
}
