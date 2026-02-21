/* Program: implementasi point
 * By: Deddy Christoper Kakunsi
 * Last Modified: 23 September 2016
 */

#include<stdio.h>
#include "point.h"
 
int main() {
	POINT P;

	P = MakePOINT(5, 4);
	TulisPOINT(P);
	printf("\n");

	Mirror(&P, true);
	TulisPOINT(P);
	printf("\n\n");

	P = MakePOINT(5, 4);
	TulisPOINT(P);
	printf("\n");

	Mirror(&P, false);
	TulisPOINT(P);
	printf("\n\n");

	P = MakePOINT(1, 1);
	TulisPOINT(P);
	printf("\n");

	Putar(&P, 90);
	TulisPOINT(P);
	printf("\n");

	return 0;
}