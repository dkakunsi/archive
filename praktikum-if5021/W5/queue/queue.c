/* File : queue.h */
/* Definisi queue yang diimplementasi dengan tabel kontigu dan alokasi dinamik */
/* Implementasi queue dengan circular buffer */

#include<stdio.h>
#include<stdlib.h>
#include "queue.h"

void CreateEmpty (Queue * Q, int Max) {
	Q->T = (infotype*) malloc(sizeof(infotype) * Max);
    if (Q->T != NULL) {
        MaxEl(*Q) = Max;
    } else {
        MaxEl(*Q) = 0;
    }

	Head(*Q) = Nil;
	Tail(*Q) = Nil;
}

void DeAlokasi (Queue * Q) {
	if (Q->T)
		free(Q->T);

    MaxEl(*Q) = 0;
	Head(*Q) = Nil;
	Tail(*Q) = Nil;
}

boolean IsEmpty (Queue Q) {
    return (Head(Q) == Nil) && (Tail(Q) == Nil);
}

boolean IsFull (Queue Q) {
    return NbElmt(Q) == MaxEl(Q);
}

int NbElmt (Queue Q) {
    if (IsEmpty(Q))
        return 0;
    
    if (Head(Q) > Tail(Q))
        return Tail(Q) - Head(Q) + MaxEl(Q) + 1;
    return Tail(Q) - Head(Q) + 1;
	
	/* ditambahkan 1 dibelakang karena index mulai dari 0 */
}

void Add (Queue * Q, infotype X) {
    if (IsFull(*Q))
		return;
	
	if (IsEmpty(*Q)) {
		Head(*Q) = 0;
		Tail(*Q) = 0;
	} else {
		Tail(*Q)++;
		if (Tail(*Q) == MaxEl(*Q))
			Tail(*Q) = 0;
	}
	
	InfoTail(*Q) = X;
}

void Del (Queue * Q, infotype * X) {
	
    if (IsEmpty(*Q))
		return;

	*X = InfoHead(*Q);
	Head(*Q)++;

	/* Jika setelah Del, NbElmt menghasilkan nilai maksimal,
	 * maka disimpulkan bahwa Queue kosong (lihat definisi NbElmt) */
	if (IsFull(*Q)) {
		Head(*Q) = Nil;
		Tail(*Q) = Nil;
	} else if (Head(*Q) == MaxEl(*Q)) {
		Head(*Q) = 0;
	}
}

infotype Peek (Queue Q, address i) {
    address tmp;
    infotype result;
	
	if (IsEmpty(Q))
		return 0;
    
	tmp = Head(Q); /* simpan state Head */

	Head(Q) += (i - 1); /* -1 karena index mulai dari 0 */
	if (Head(Q) >= MaxEl(Q))
		Head(Q) %= MaxEl(Q);

	result = InfoHead(Q);
	Head(Q) = tmp; /* restore state Head */
	
	return result;
}

void Iterate (Queue Q) {
    int idx;

	if (IsEmpty(Q))
		return;

    for (idx = 1; idx <= NbElmt(Q); idx++)
        printf("%ld\n", Peek(Q, idx));
	//printf("\n");
}
