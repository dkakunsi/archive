/*
 * Deddy Ch. Kakunsi
 * Program Password
 */

#include<stdio.h>
#include<string.h>

void proses();

int main() {
	int n, i;
	scanf("%d", &n);
	
	for (i = 0; i < n; i++) {
		proses();
	}

	return 0;
}

void proses() {
	char kata[100];
	int i, j, length = 0, unik = 1;

	/* inisialisasi array menjadi nol */
	for (i = 0; i < 100; i++) {
		kata[i] = '\0';
	}
	
	scanf("%s", &kata);
	length = strlen(kata);
	if (length > 1) {
		
		i = 0;
		while (unik && i < length) {

			j = i + 1;
			while (unik && j < length) {
				if (kata[i] == kata[j]) {
					unik = 0; /* change sentinel for both loop */
				}
				
				j++; /* increment sentinel for inner loop */
			}
			
			i++; /* increment sentinel for outer loop */
		}
	
		printf("%d\n", unik);
	}
}
