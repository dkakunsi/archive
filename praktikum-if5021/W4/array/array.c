/* Implementasi ADT Array
 * By: Deddy Christoper Kakunsi
 * Last modified: 21 September 2016
 */

#include<stdio.h>
#include "array.h"
#include "boolean.h"

void MakeEmpty (TabInt * T) {
	Neff(*T) = 0;
}

int NbElmt (TabInt T) {
	return Neff(T);
}

boolean IsEmpty (TabInt T) {
	return (NbElmt(T) == 0);
}

boolean IsFull (TabInt T) {
	return (NbElmt(T) == NMax); 
}

void BacaIsi (TabInt * T) {
	int N = IdxUndef, i;
	
	while (N < 0 || N > NMax)
		scanf("%d", &N);
	
	if (N > 0) {
		for (i = 0; i < N; i++)
			scanf("%d", &Elmt(*T, i));
		Neff(*T) = N;
	} else {
		MakeEmpty(T);
	}
}

void TulisIsi (TabInt T) {
	int i;
	boolean first;
	
	printf("[");
	first = true;
	if (!IsEmpty(T)) {
		for (i = 0; i < NbElmt(T); i++) {
			if (first) {
				printf("%d", Elmt(T, i));
				first = false;
			} else {
				printf(",%d", Elmt(T, i));
			}
		}
	}
	printf("]\n");
}

TabInt PlusTab (TabInt T1, TabInt T2) {
	TabInt T3;
	int i;
	
	Neff(T3) = NbElmt(T1);
	for (i = 0; i < NbElmt(T3); i++)
		Elmt(T3, i) = Elmt(T1, i) + Elmt(T2, i);
	return T3;
}

TabInt MinusTab (TabInt T1, TabInt T2) {
	TabInt T3;
	int i;
	
	Neff(T3) = NbElmt(T1);
	for (i = 0; i < NbElmt(T3); i++)
		Elmt(T3, i) = Elmt(T1, i) - Elmt(T2, i);
	return T3;
}

TabInt KaliKons (TabInt Tin, ElType c) {
	TabInt Tout;
	int i;
	
	Neff(Tout) = NbElmt(Tin);
	for (i = 0; i < NbElmt(Tout); i++)
		Elmt(Tout, i) = Elmt(Tin, i) * c;
	return Tout;
}

boolean IsEQ (TabInt T1, TabInt T2) {
	int i;
	
	if (NbElmt(T1) != NbElmt(T2))
		return false;

	i = 0;
	while (i < NbElmt(T1)) {
		if (Elmt(T1, i) != Elmt(T2, i))
			return false;
		i++;
	}
	
	return true;
}

IdxType Search (TabInt T, ElType X) {
	IdxType found = IdxUndef;
	int i;
	
	i = 0;
	while (i < NbElmt(T) && found == IdxUndef) {
		if (Elmt(T, i) == X)
			found = i;
		i++;
	}
	
	return found;
}

boolean SearchB (TabInt T, ElType X) {
	boolean found = false;
	int i;
	
	i = 0;
	while (i < NbElmt(T) && found == false) {
		if (Elmt(T, i) == X)
			found = true;
		i++;
	}
	
	return found;
}

ElType ValMax (TabInt T) {
	ElType res = IdxUndef;
	
	if (NbElmt(T) > 0) {
		SortDesc(&T, false); /* Descending */
		res = Elmt(T, 0);
	}
	
	return res;
}

ElType ValMin (TabInt T) {
	ElType res = IdxUndef;

	if (NbElmt(T) > 0) {
		SortDesc(&T, true); /* Ascending */
		res = Elmt(T, 0);
	}
	
	return res;
}

IdxType IdxMaxTab (TabInt T) {
	IdxType res = IdxUndef;
	ElType max;
	
	if (NbElmt(T) > 0) {
		max = ValMax(T);
		res = Search(T, max);
	}
	
	return res;
}

IdxType IdxMinTab (TabInt T) {
	IdxType res = IdxUndef;
	ElType min;

	if (NbElmt(T) > 0) {
		min = ValMin(T);
		res = Search(T, min);
	}
	
	return res;
}

void CopyTab (TabInt Tin, TabInt * Tout) {
	int i;
	
	Neff(*Tout) = Neff(Tin);
	for (i = 0; i < Neff(Tin); i++)
		Elmt(*Tout, i) = Elmt(Tin, i);
}

TabInt InverseTab (TabInt T) {
	TabInt Ti;
	int i;

	Neff(Ti) = NbElmt(T);
	for (i = 0; i < NbElmt(Ti); i++)
		Elmt(Ti, (NbElmt(Ti) - 1) - i) = Elmt(T, i);
	return Ti;
}

boolean IsSimetris (TabInt T) {
	TabInt T2 = InverseTab(T);
	return IsEQ(T, T2);
}

void SortDesc (TabInt * T, boolean asc) {
	int i, j;
	int tmp;
	
	if (asc) {
		for (i = 0; i < Neff(*T); i++) {
			for (j = i + 1; j < Neff(*T); j++) {
				if (Elmt(*T, i) > Elmt(*T, j)) {
					tmp = Elmt(*T, i);
					Elmt(*T, i) = Elmt(*T, j);
					Elmt(*T, j) = tmp;
				}
			}
		}
	} else {
		for (i = 0; i < Neff(*T); i++) {
			for (j = i + 1; j < Neff(*T); j++) {
				if (Elmt(*T, i) < Elmt(*T, j)) {
					tmp = Elmt(*T, i);
					Elmt(*T, i) = Elmt(*T, j);
					Elmt(*T, j) = tmp;
				}
			}
		}
	}
	
}

void AddAsLastEl (TabInt * T, ElType X) {
	if (NbElmt(*T) < NMax) {
		Elmt(*T, NbElmt(*T)) = X;
		Neff(*T)++;
	}
}

void AddEli (TabInt * T, ElType X, IdxType i) {
	int j;
	
	if (NbElmt(*T) > 0 && NbElmt(*T) < NMax) {

		/* geser data dari belakang */
		for (j = NbElmt(*T) - 1; j >= i; j--)
			Elmt(*T, j + 1) = Elmt(*T, j);

		Elmt(*T, i) = X;
		Neff(*T)++;
	}
}

void DelLastEl (TabInt * T, ElType * X) {
	if (NbElmt(*T) > 0) {
		Neff(*T)--;
		*X = Elmt(*T, NbElmt(*T));
	}
}

void DelEli (TabInt * T, IdxType i, ElType * X) {
	int j;
	
	if (NbElmt(*T) > 0) {
		Neff(*T)--;
		*X = Elmt(*T, i);

		/* geser data ke depan */
		for (j = i; j < NbElmt(*T); j++)
			Elmt(*T, j) = Elmt(*T, j + 1);
	}
}
