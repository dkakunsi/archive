/* File : stack.h */
/* Definisi stack yang diimplementasi dengan tabel kontigu dan alokasi dinamik */

#include<stdio.h>
#include "queue.h"

void test1();
void test2();

int main() {
	int i;
	printf("PILIH: ");
	scanf("%d", &i);
	
	if (i == 1) {
		test1();
	} else if(i = 2) {
		test2();
	}
	
    return 0;
}

void test1() {
    Queue Q;
    infotype X;
    
    CreateEmpty(&Q, 4);
    printf("Head %d\n", Head(Q));
    printf("Info Head %d\n", InfoHead(Q));
    printf("Tail %d\n", Tail(Q));
    printf("Info Tail %d\n", InfoTail(Q));
    printf("Max %d\n", MaxEl(Q));
    printf("Empty %d\n", IsEmpty(Q));
    printf("Full %d\n", IsFull(Q));
    printf("\n");
	
	printf(">>>>>>>>>>>>>");
	Iterate(Q);
	printf("\n");

    Add(&Q, 1);
	printf("ADD >>>>>>\n");
    printf("Head %d\n", Head(Q));
    printf("Info Head %d\n", InfoHead(Q));
    printf("Tail %d\n", Tail(Q));
    printf("Info Tail %d\n", InfoTail(Q));
    printf("NbElmt %d\n", NbElmt(Q));
    printf("Empty %d\n", IsEmpty(Q));
    printf("Full %d\n", IsFull(Q));
    printf("\n");

    Add(&Q, 2);
	printf("ADD >>>>>>\n");
    printf("Head %d\n", Head(Q));
    printf("Info Head %d\n", InfoHead(Q));
    printf("Tail %d\n", Tail(Q));
    printf("Info Tail %d\n", InfoTail(Q));
    printf("NbElmt %d\n", NbElmt(Q));
    printf("Empty %d\n", IsEmpty(Q));
    printf("Full %d\n", IsFull(Q));
    printf("\n");
    
    Add(&Q, 3);
	printf("ADD >>>>>>\n");
    printf("Head %d\n", Head(Q));
    printf("Info Head %d\n", InfoHead(Q));
    printf("Tail %d\n", Tail(Q));
    printf("Info Tail %d\n", InfoTail(Q));
    printf("NbElmt %d\n", NbElmt(Q));
    printf("Empty %d\n", IsEmpty(Q));
    printf("Full %d\n", IsFull(Q));
    printf("\n");

    Add(&Q, 4);
	printf("ADD >>>>>>\n");
    printf("Head %d\n", Head(Q));
    printf("Info Head %d\n", InfoHead(Q));
    printf("Tail %d\n", Tail(Q));
    printf("Info Tail %d\n", InfoTail(Q));
    printf("NbElmt %d\n", NbElmt(Q));
    printf("Empty %d\n", IsEmpty(Q));
    printf("Full %d\n", IsFull(Q));
    printf("\n");

    printf("Empty %d\n", IsEmpty(Q));
    printf("Full %d\n", IsFull(Q));
    printf("\n");

    Add(&Q, 4);
	printf("ADD >>>>>>\n");
    printf("Head %d\n", Head(Q));
    printf("Info Head %d\n", InfoHead(Q));
    printf("Tail %d\n", Tail(Q));
    printf("Info Tail %d\n", InfoTail(Q));
    printf("NbElmt %d\n", NbElmt(Q));
    printf("Empty %d\n", IsEmpty(Q));
    printf("Full %d\n", IsFull(Q));
    printf("\n");

    Add(&Q, 4);
	printf("ADD >>>>>>\n");
    printf("Head %d\n", Head(Q));
    printf("Info Head %d\n", InfoHead(Q));
    printf("Tail %d\n", Tail(Q));
    printf("Info Tail %d\n", InfoTail(Q));
    printf("NbElmt %d\n", NbElmt(Q));
    printf("Empty %d\n", IsEmpty(Q));
    printf("Full %d\n", IsFull(Q));
    printf("\n");

    Add(&Q, 4);
	printf("ADD >>>>>>\n");
    printf("Head %d\n", Head(Q));
    printf("Info Head %d\n", InfoHead(Q));
    printf("Tail %d\n", Tail(Q));
    printf("Info Tail %d\n", InfoTail(Q));
    printf("NbElmt %d\n", NbElmt(Q));
    printf("Empty %d\n", IsEmpty(Q));
    printf("Full %d\n", IsFull(Q));
    printf("\n");
    
	printf(">>>>>>>>>>\n");
    Iterate(Q);
    printf("\n");

	printf("Elemen ke-1: %d\n", Peek(Q, 1));
	printf("Elemen ke-2: %d\n", Peek(Q, 2));
	printf("Elemen ke-3: %d\n", Peek(Q, 3));
    printf("\n");

    Del(&Q, &X);
	printf("DEL >>>>>>\n");
    printf("Head %d\n", Head(Q));
    printf("Info Head %d\n", InfoHead(Q));
    printf("Tail %d\n", Tail(Q));
    printf("Info Tail %d\n", InfoTail(Q));
    printf("NbElmt %d\n", NbElmt(Q));
    printf("Empty %d\n", IsEmpty(Q));
    printf("Full %d\n", IsFull(Q));
    printf("Val %d\n", X);
    printf("\n");

	printf(">>>>>>>>>>\n");
    Iterate(Q);
    printf("\n");

    Del(&Q, &X);
	printf("DEL >>>>>>\n");
    printf("Head %d\n", Head(Q));
    printf("Info Head %d\n", InfoHead(Q));
    printf("Tail %d\n", Tail(Q));
    printf("Info Tail %d\n", InfoTail(Q));
    printf("NbElmt %d\n", NbElmt(Q));
    printf("Empty %d\n", IsEmpty(Q));
    printf("Full %d\n", IsFull(Q));
    printf("Val %d\n", X);
    printf("\n");

	printf(">>>>>>>>>>\n");
    Iterate(Q);
    printf("\n");

    Del(&Q, &X);
	printf("DEL >>>>>>\n");
    printf("Head %d\n", Head(Q));
    printf("Info Head %d\n", InfoHead(Q));
    printf("Tail %d\n", Tail(Q));
    printf("Info Tail %d\n", InfoTail(Q));
    printf("NbElmt %d\n", NbElmt(Q));
    printf("Empty %d\n", IsEmpty(Q));
    printf("Full %d\n", IsFull(Q));
    printf("Val %d\n", X);
    printf("\n");
    
	printf(">>>>>>>>>>\n");
    Iterate(Q);
    printf("\n");

    Del(&Q, &X);
	printf("DEL >>>>>>\n");
    printf("Head %d\n", Head(Q));
    printf("Info Head %d\n", InfoHead(Q));
    printf("Tail %d\n", Tail(Q));
    printf("Info Tail %d\n", InfoTail(Q));
    printf("NbElmt %d\n", NbElmt(Q));
    printf("Empty %d\n", IsEmpty(Q));
    printf("Full %d\n", IsFull(Q));
    printf("Val %d\n", X);

    Iterate(Q);
    printf("\n");

    Del(&Q, &X);
	printf("DEL >>>>>>\n");
    printf("Head %d\n", Head(Q));
    printf("Info Head %d\n", InfoHead(Q));
    printf("Tail %d\n", Tail(Q));
    printf("Info Tail %d\n", InfoTail(Q));
    printf("NbElmt %d\n", NbElmt(Q));
    printf("Empty %d\n", IsEmpty(Q));
    printf("Full %d\n", IsFull(Q));
    printf("Val %d\n", X);
    printf("\n");

    Del(&Q, &X);
	printf("DEL >>>>>>\n");
    printf("Head %d\n", Head(Q));
    printf("Info Head %d\n", InfoHead(Q));
    printf("Tail %d\n", Tail(Q));
    printf("Info Tail %d\n", InfoTail(Q));
    printf("NbElmt %d\n", NbElmt(Q));
    printf("Empty %d\n", IsEmpty(Q));
    printf("Full %d\n", IsFull(Q));
    printf("Val %d\n", X);
    printf("\n");
}

void test2() {
    Queue Q;
    infotype X;
    
    CreateEmpty(&Q, 4);
    printf("Head %d\n", Head(Q));
    printf("Info Head %d\n", InfoHead(Q));
    printf("Tail %d\n", Tail(Q));
    printf("Info Tail %d\n", InfoTail(Q));
    printf("NbElmt %d\n", NbElmt(Q));
    printf("Max %d\n", MaxEl(Q));
    printf("Empty %d\n", IsEmpty(Q));
    printf("\n");
	
	printf("Elemen ke-1: %d\n", Peek(Q, 1));

    Add(&Q, 1);
	printf("ADD >>>>>>\n");
    printf("Head %d\n", Head(Q));
    printf("Info Head %d\n", InfoHead(Q));
    printf("Tail %d\n", Tail(Q));
    printf("Info Tail %d\n", InfoTail(Q));
    printf("NbElmt %d\n", NbElmt(Q));
    printf("\n");

    Add(&Q, 2);
	printf("ADD >>>>>>\n");
    printf("Head %d\n", Head(Q));
    printf("Info Head %d\n", InfoHead(Q));
    printf("Tail %d\n", Tail(Q));
    printf("Info Tail %d\n", InfoTail(Q));
    printf("NbElmt %d\n", NbElmt(Q));
    printf("\n");
    
    Add(&Q, 3);
	printf("ADD >>>>>>\n");
    printf("Head %d\n", Head(Q));
    printf("Info Head %d\n", InfoHead(Q));
    printf("Tail %d\n", Tail(Q));
    printf("Info Tail %d\n", InfoTail(Q));
    printf("NbElmt %d\n", NbElmt(Q));
    printf("\n");

    Add(&Q, 4);
	printf("ADD >>>>>>\n");
    printf("Head %d\n", Head(Q));
    printf("Info Head %d\n", InfoHead(Q));
    printf("Tail %d\n", Tail(Q));
    printf("Info Tail %d\n", InfoTail(Q));
    printf("NbElmt %d\n", NbElmt(Q));
    printf("\n");

    printf("Full %d\n", IsFull(Q));
    printf("\n");
    
	printf(">>>>>>>>>>\n");
    Iterate(Q);
    printf("\n");

    Del(&Q, &X);
	printf("DEL >>>>>>\n");
    printf("Val %d\n", X);
    printf("Head %d\n", Head(Q));
    printf("Info Head %d\n", InfoHead(Q));
    printf("Tail %d\n", Tail(Q));
    printf("Info Tail %d\n", InfoTail(Q));
    printf("NbElmt %d\n", NbElmt(Q));
    printf("\n");

    Del(&Q, &X);
	printf("DEL >>>>>>\n");
    printf("Val %d\n", X);
    printf("Head %d\n", Head(Q));
    printf("Info Head %d\n", InfoHead(Q));
    printf("Tail %d\n", Tail(Q));
    printf("Info Tail %d\n", InfoTail(Q));
    printf("NbElmt %d\n", NbElmt(Q));
    printf("\n");

    Add(&Q, 1);
	printf("ADD >>>>>>\n");
    printf("Head %d\n", Head(Q));
    printf("Info Head %d\n", InfoHead(Q));
    printf("Tail %d\n", Tail(Q));
    printf("Info Tail %d\n", InfoTail(Q));
    printf("NbElmt %d\n", NbElmt(Q));
    printf("\n");

	printf("Elemen ke-1: %d\n", Peek(Q, 1));
	printf("Elemen ke-2: %d\n", Peek(Q, 2));
	printf("Elemen ke-3: %d\n", Peek(Q, 3));
    printf("\n");
    
    printf(">>>>>>>>>>>>>>>>>>>\n");
    Iterate(Q);
}
