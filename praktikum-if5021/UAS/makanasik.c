#include <stdio.h>
#include <string.h>
#include <stdlib.h>

typedef struct {
	char val[255];
} String;

void combine(String arr[], String data[], int numOfElem, int size, int base, int depth);
int hitungKombinasi(int n, int r);
int hitungFaktorial(int i);
void cetakKombinasi(String data[], int numOfElem);

int main() {
	String arr[100], *data, input;
	int numOfElem, arrSize;
	
	arrSize = 0;
	scanf("%s", &input.val);
	while(strcmp(input.val, "***")) {
		arr[arrSize] = input;
		arrSize++;
		
		scanf("%s", &input.val);
	}
	
	if (arrSize > 0) {
		scanf("%d", &numOfElem);
		
		data = (String*) malloc(sizeof(String) * numOfElem);
		combine(arr, data, numOfElem, 0, arrSize, 0);
		
		printf("JUMLAH KOMBINASI= %d\n", hitungKombinasi(arrSize, numOfElem));
		free(data);
	} else {
		printf("Data Kosong\n");
	}
	
	return 0;
}

void combine(String arr[], String data[], int numOfElem, int base, int size, int depth) {
	int i;

	for (i = base; i < size; i++) {
		data[depth] = arr[i];

		if (depth == numOfElem-1) {
			cetakKombinasi(data, numOfElem);
		} else {
			combine(arr, data, numOfElem, i+1, size, depth+1);
		}
	}
}

void cetakKombinasi(String data[], int numOfElem) {
	int i;
	
	for (i = 0; i < numOfElem; i++) {
		printf("%s ", data[i]);
	}
	printf("\n");
}

int hitungKombinasi(int n, int r) {
	int nFak, rFak, iFak;
	
	nFak = hitungFaktorial(n);
	rFak = hitungFaktorial(r);
	iFak = hitungFaktorial(n-r);
	
	return nFak / (rFak * iFak);
}

int hitungFaktorial(int i) {
	int fak = 1;
	for (i; i > 0; i--) {
		fak *= i;
	}
}
