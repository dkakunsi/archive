/* Program: Driver jam
 * By: Deddy Christoper Kakunsi
 * Last Modified: 23 September 2016
 */
 
#include<stdio.h>
#include "boolean.h"
#include "jam.h"

int main() {
	int N, i;
	JAM min, max, J1, J2, JAw, JAk, J0;
	
	scanf("%d", &N); /* baca jumlah input */
	/* inisialisasi variabel */
	J0 = MakeJAM(0, 0, 0);
	min = J0;
	max = J0;

	/* looping sebanyak N kali*/
	i = 1;
	while (i <= N) {
		/* cetak nomor record */
		printf("[%d]\n", i);
		
		/* baca jam ke-1 & ke-2 */
		BacaJAM(&J1);
		BacaJAM(&J2);

		if (JGT(J1, J2)) {
			/* karena J1 > J2, maka J1 adalah jam akhir, J2 adalah jam awal */
			JAk = J1; JAw = J2;
		} else {
			/* karena J1 < J2, maka J1 adalah jam awal, J2 adalah jam akhir */
			JAk = J2; JAw = J1;
		}

		/* cetak durasi antara akhir dan awal */
		printf("%ld\n", Durasi(JAw, JAk));

		/* jika jam minimum = J0, berarti ini adalah looping ke-1 (i=1)
		 * jam awal otomatis menjadi jam minimum */
		/* jika bukan looping ke-1
		 * jika jam awal < jam minimum, maka jam awal adalah jam minimum */
		if ((JEQ(min, J0)) || JLT(JAw, min))
			min = JAw;
		
		/* jika jam akhir lebih besar dari jam maximum, maka jam akhir adalah jam maksimum */
		if (JGT(JAk, max))
			max = JAk;
		
		/* increase sentinel */
		i++;
	}
	
	/* cetak jam minimum & maksimum */
	TulisJAM(min);
	printf("\n");
	TulisJAM(max);
	printf("\n");
	
	return 0;
}
