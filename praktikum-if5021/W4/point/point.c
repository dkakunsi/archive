/* Program: implementasi point
 * By: Deddy Christoper Kakunsi
 * Last Modified: 23 September 2016
 */

#include<stdio.h>
#include<math.h>
#include "point.h"
#include "boolean.h"
 
POINT MakePOINT (float X, float Y) {
	POINT P;
	Absis(P) = X;
	Ordinat(P) = Y;
	
	return P;
}

void BacaPOINT (POINT * P) {
	scanf("%f %f", &Absis(*P), &Ordinat(*P));
}

void TulisPOINT (POINT P) {
	printf("(%.2f,%.2f)", Absis(P), Ordinat(P));
}

boolean EQ (POINT P1, POINT P2) {
	return (Absis(P1) == Absis(P2) && Ordinat(P1) == Ordinat(P2));
}

boolean NEQ (POINT P1, POINT P2) {
	return !(EQ(P1, P2));
}

boolean IsOrigin (POINT P) {
	return Absis(P) == 0 && Ordinat(P) == 0;
}

boolean IsOnSbX (POINT P) {
	return Ordinat(P) == 0;
}

boolean IsOnSbY (POINT P) {
	return Absis(P) == 0;
}

int Kuadran (POINT P) {
	int kuadran;
	
	if (IsOrigin(P)) {
		kuadran = 0;
	} else {
		if (Absis(P) > 0) { /* X > 0 */
			if (Ordinat(P) > 0) { /* Y > 0 */
				kuadran = 1;
			} else { /* Y < 0 */
				kuadran = 4;
			}
		} else { /* X < 0 */
			if (Ordinat(P) > 0) { /* Y > 0 */
				kuadran = 2;
			} else { /* Y < 0 */
				kuadran = 3;
			}
		}
	}
		
	return kuadran;
}

/* *** KELOMPOK OPERASI LAIN TERHADAP TYPE *** */                           
float Jarak (POINT P1, POINT P2) {
	float eX, eY;
	
	eX = pow(Absis(P2) - Absis(P1), 2);
	eY = pow(Ordinat(P2) - Ordinat(P1), 2);
	return sqrt(eX + eY);
}

void Geser (POINT *P, float deltaX, float deltaY) {
	Absis(*P) += deltaX;
	Ordinat(*P) += deltaY;
}

void Mirror (POINT *P, boolean SbX) {
	if (SbX) {
		Ordinat(*P) = -Ordinat(*P);
	} else {
		Absis(*P) = -Absis(*P);
	}
}

void Putar (POINT *P, float Sudut) {
	float s, c, radian, x, y;
	
	x = Absis(*P);
	y = Ordinat(*P);
	radian = -Sudut / 180 * 3.14;
	s = sin(radian);
	c = cos(radian);
	
	Absis(*P) = x * c - y * s;
	Ordinat(*P) = x * s + y * c;
}
