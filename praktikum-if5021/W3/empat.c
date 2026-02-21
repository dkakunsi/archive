#include<stdio.h>
#include<stdlib.h>

typedef struct Himpunan {
	int T[100];
	int N;
} Himpunan;

int isNotFound(int X, Himpunan H);
Himpunan bacaHimpunan(Himpunan H, int input);
void cetakHimpunan(Himpunan H);

int main() {
	Himpunan hipA, hipB, hipC;
	int input, index;
	int i, j, notFound;
	
	/* baca himpunan A */
	hipA.N = 0; /* himpunan A dianggap kosong di awal pembacaan */
	scanf("%d", &input);
	if (input> 0) {
		hipA = bacaHimpunan(hipA, input);
	}
	
	/* baca himpunan B */
	hipB.N = 0; /* himpunan B dianggap kosong di awal pembacaan */
	scanf("%d", &input);
	if (input > 0) {
		hipB = bacaHimpunan(hipB, input);
	}

	/* hitung selisih */
	hipC.N = 0; /* himpunan C dianggap kosong di awal pembacaan */
	i = 0;
	for (i = 0; i < hipA.N; i++) {

		notFound = 1;
		j = 0;
		for (j = 0; j < hipB.N; j++) {
			if (hipA.T[i] == hipB.T[j]) {
				notFound = 0;
			}
		}
		
		if (notFound) {
			hipC.T[hipC.N] = hipA.T[i];
			hipC.N++;
		}
	}

	cetakHimpunan(hipA);
	printf("\n");
	cetakHimpunan(hipB);
	printf("\n");
	cetakHimpunan(hipC);

	printf("\n");
	return 0;
}

/* searching */
int isNotFound(int X, Himpunan H) {
	int i = 0;

	while (i < H.N) {
		if (H.T[i] == X)
			return 0;
		i++;
	}
	
	return 1;
}

/* input adalah untuk nilai yang diinput di awal */
Himpunan bacaHimpunan(Himpunan H, int input) {
	while (input > 0 && H.N <= 100) {

		if (isNotFound(input, H)) {
			H.T[H.N] = input;
			H.N++;
		}
		
		scanf("%d", &input);
	}
	
	return H;
}

void cetakHimpunan(Himpunan H) {
	int i, isTidakAda = 1;
	
	printf("[");
	for (i = 0; i < H.N; i++) {
		if (isTidakAda) {
			printf("%d", H.T[i]);
			isTidakAda = 0;
		} else {
			printf(",%d", H.T[i]);
		}
	}
	printf("]");
}
