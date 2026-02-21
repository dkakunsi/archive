/* File : stack.h */
/* Definisi stack yang diimplementasi dengan tabel kontigu dan alokasi dinamik */

#include<stdio.h>
#include "queue.h"

int main() {
    Queue Q;
    int SIZE;
    int M, N, i;
	infotype val;
	char line[10];
    
    /* baca SIZE */
	fgets(line, sizeof(line), stdin);
	sscanf(line, "%d", &SIZE);

    CreateEmpty(&Q, SIZE);
	N = -1;
    while(N != 0) {

        /* baca N */
		fgets(line, sizeof(line), stdin);
		sscanf(line, "%d %d", &N, &M);

        if (N > 0) {
			
			i = N;
            while(i > 0) {
                if (IsFull(Q)) {
                    printf("PENUH\n");
                    break;
                }

                Add(&Q, M);
                i--;
            }
        } else if(N < 0) {

			i = N;
            while(i < 0) {
                if (IsEmpty(Q)) {
                    printf("KOSONG\n");
                    break;
                }

                Del(&Q, &val);
				i++;
            }
        } else {
			if (IsEmpty(Q)) {
				printf("KOSONG\n");
			} else {
				Iterate(Q);
			}
        }
    }

    return 0;
}
