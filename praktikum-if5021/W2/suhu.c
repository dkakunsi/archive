/* 
 * Deddy Ch. Kakunsi 
 * Program suhu
 */

#include<stdio.h>
#include<stdlib.h>

int main() {
	int *umur;
	int n, i = 0;
	int terbesar = NULL, terkecil = NULL, total = 0;
	int _bawahNol = 0, _nol = 0, _normal = 0, _atasNormal = 0;

	scanf("%d", &n);
	while (n <= 31 && i < n) {
		umur = (int *) malloc(sizeof(int) * n);
		scanf("%d", &umur[i]);
		
		if (terbesar < umur[i]) {
			terbesar = umur[i];
		} else if (terkecil > umur[i]) {
			terkecil = umur[i];
		}
		
		if (umur[i] < 0) {
			_bawahNol++;
		} else if (umur[i] == 0) {
			_nol++;
		} else if (umur[i] > 0 && umur[i] < 35) {
			_normal++;
		} else {
			_atasNormal++;
		}
		
		total += umur[i];
		
		i++; /* increase sentinel */
	}
	
	printf("%d\n", terbesar);
	printf("%d\n", terkecil);
	printf("%.2f\n", (float)total/n);
	printf("%d\n", _bawahNol);
	printf("%d\n", _nol);
	printf("%d\n", _normal);
	printf("%d\n", _atasNormal);

	free(umur);
	
	return 0;
}
