/* Program driver untuk array.h
 * By: Deddy Christoper Kakunsi
 * Last modified: 21 September 2016
 */

#include<stdio.h>
#include "array.h"

void printBoolean(boolean bool);
TabInt getTI1();
TabInt getTI2();

/* test MakeEmpty & IsEmpty */
void testMakeEmpty();
/* test baca-tulis */
void testBacaTulis();
/* test aritmatika */
void testAritmatika();
/* test apakah sama */
void testIsEquals();
/* test searching */
void testSearching();
/* test nilai ekstream */
void testNilaiEkstream();
/* test sorting */
void testSortDesc();
/* test copy */
void testCopy();
/* test inverse */
void testInverseTab();
/* test simetris */
void testSimetris();
/* test tambah element */
void testTambahElem();
/* test kurangi element */
void testKurangElem();

int main() {

	//printBoolean(true);
	//printBoolean(false);
	
	//testMakeEmpty();
	//testBacaTulis();
	//testAritmatika();
	//testIsEquals();
	//testSearching();
	//testNilaiEkstream();
	//testSortDesc();
	//testCopy();
	//testInverseTab();
	//testSimetris();
	testTambahElem();
	testKurangElem();
	
	printf("\n");
	return 0;	
}

void printBoolean(boolean bool) {
	if (bool) {
		printf("TRUE");
	} else {
		printf("FALSE");
	}
}

TabInt getTI1() {
	TabInt TI;
	int i;
	
	Neff(TI) = 5;
	Elmt(TI, 0) = 1;
	Elmt(TI, 1) = 2;
	Elmt(TI, 2) = 3;
	Elmt(TI, 3) = 2;
	Elmt(TI, 4) = 1;
	
	return TI;
}

TabInt getTI2() {
	TabInt TI;
	int i;
	
	Neff(TI) = 5;
	for (i = 0; i < NbElmt(TI); i++) {
		Elmt(TI, i) = NbElmt(TI) - i;
	}
	
	return TI;
}

void testMakeEmpty() {
	TabInt TI;
	
	MakeEmpty(&TI);
	printBoolean(IsEmpty(TI));
}

void testBacaTulis() {
	TabInt TI;
	
	BacaIsi(&TI);
	TulisIsi(TI);
}

void testAritmatika() {
	TabInt TI1, TI2, TI3;

	printf("\n======== PENJUMLAHAN ========\n");
	TI1 = getTI1();
	TI2 = getTI2();
	TI3 = PlusTab(TI1, TI2);
	TulisIsi(TI3);

	printf("\n======== PENGURANGAN ========\n");
	TI1 = getTI1();
	TI2 = getTI2();
	TI3 = MinusTab(TI1, TI2);
	TulisIsi(TI3);

	printf("\n======== PERKALIAN ========\n");
	TI1 = getTI1();
	TI3 = KaliKons(TI1, 10);
	TulisIsi(TI3);
}

void testIsEquals() {
	TabInt TI1, TI2;
	
	printf("\n======== EQUALS TRUE ========\n");
	TI1 = getTI1();
	TI2 = getTI1();
	printBoolean(IsEQ(TI1, TI2));
	
	printf("\n======== EQUALS FALSE ========\n");
	TI1 = getTI1();
	TI2 = getTI2();
	printBoolean(IsEQ(TI1, TI2));
}

void testSearching() {
	TabInt TI;
	ElType val;

	TI = getTI1();
	printf("\n======== SEARCH INDEX FOUND ========\n");
	val = 2;
	printf("%d\n", Search(TI, val));

	printf("\n======== SEARCH INDEX NOT FOUND ========\n");
	val = 100;
	printf("%d\n", Search(TI, val));

	printf("\n======== SEARCH BOOLEAN FOUND ========\n");
	val = 2;
	printBoolean(SearchB(TI, val));

	printf("\n======== SEARCH BOOLEAN NOT FOUND ========\n");
	val = 100;
	printBoolean(SearchB(TI, val));
}

void testNilaiEkstream() {
	TabInt TI;

	TI = getTI2();
	printf("\n===== MAX: 5 =====\n");
	printBoolean(ValMax(TI) == 5);
	
	TI = getTI2();
	printf("\n===== MIN: 1 =====\n");
	printBoolean(ValMin(TI) == 1);

	TI = getTI1();
	printf("\n===== INDEX MAX: 2 =====\n");
	printBoolean(IdxMaxTab(TI) == 2);
	//printf("%d", IdxMaxTab(TI));

	TI = getTI1();
	printf("\n===== INDEX MIN: 0 =====\n");
	printBoolean(IdxMinTab(TI) == 0);
	//printf("%d", IdxMinTab(TI));
}

void testSortDesc() {
	TabInt T;
	
	T = getTI1();
	printf("\n===== SORT ASCENDING =====\n");
	TulisIsi(T);
	printf("\n");
	SortDesc(&T, true);
	TulisIsi(T);
	
	T = getTI1();
	printf("\n===== SORT DESCENDING =====\n");
	TulisIsi(T);
	printf("\n");
	SortDesc(&T, false);
	TulisIsi(T);
}

void testCopy() {
	TabInt T, Tcopy;
	
	printf("\n===== COPY TABLE =====\n");
	T = getTI1();
	TulisIsi(T);
	printf("\n");
	
	CopyTab(T, &Tcopy);
	TulisIsi(T);
}

void testInverseTab() {
	TabInt T, Ti;

	printf("\n===== TEST INVERSE =====\n");
	T = getTI2();
	TulisIsi(T);
	printf("\n");

	Ti = InverseTab(T);
	TulisIsi(Ti);
	printf("\n");
}

void testSimetris() {
	TabInt T;

	printf("\n===== TEST SIMETRIS: TRUE =====\n");
	T = getTI1();
	printBoolean(IsSimetris(T));

	printf("\n===== TEST SIMETRIS: FALSE =====\n");
	T = getTI2();
	printBoolean(IsSimetris(T));
}

void testTambahElem() {
	TabInt T;
	
	printf("\n===== TAMBAH ELEMEN DI AKHIR =====\n");
	T = getTI2();
	TulisIsi(T);
	printf("\n");
	AddAsLastEl(&T, 9);
	TulisIsi(T);
	printf("\n");

	printf("\n===== TAMBAH ELEMEN PADA INDEX 2 =====\n");
	T = getTI2();
	TulisIsi(T);
	printf("\n");
	AddEli(&T, 9, 2);
	TulisIsi(T);
	printf("\n");
}

void testKurangElem() {
	TabInt T;
	ElType val;
	
	printf("\n===== KURANGI ELEMEN DI AKHIR =====\n");
	T = getTI2();
	TulisIsi(T);
	printf("\n");

	DelLastEl(&T, &val);
	TulisIsi(T);
	printf("\n");
	printf("%d\n", val);

	printf("\n===== KURANGI ELEMEN PADA INDEX 2 =====\n");
	T = getTI2();
	TulisIsi(T);
	printf("\n");

	DelEli(&T, 2, &val);
	TulisIsi(T);
	printf("\n");
	printf("%d\n", val);
}
