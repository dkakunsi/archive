/* File : stack.h */
/* Definisi stack yang diimplementasi dengan tabel kontigu dan alokasi dinamik */

#include<stdio.h>
#include "stack.h"

int main() {
    Stack S;
    infotype X;
    
    CreateEmpty(&S, 4);
    printf("TOP %d\n", Top(S));
    printf("MAX %d\n", MaxEl(S));
    printf("EMPTY %d\n", IsEmpty(S));

    Push(&S, 1);
    printf("TOP: %d\n", Top(S));
    Push(&S, 2);
    printf("TOP: %d\n", Top(S));
    Push(&S, 3);
    printf("TOP: %d\n", Top(S));
    Push(&S, 4);
    printf("TOP: %d\n", Top(S));
    printf("FULL %d\n", IsFull(S));
    Push(&S, 4);
    printf("TOP: %d\n", Top(S));
    Push(&S, 4);
    printf("TOP: %d\n", Top(S));

    Iterate(S);

    Pop(&S, &X);
    printf("VAL %d\n", X);
    printf("TOP %d\n", Top(S));

    Pop(&S, &X);
    printf("VAL %d\n", X);
    printf("TOP %d\n", Top(S));

    Pop(&S, &X);
    printf("VAL %d\n", X);
    printf("TOP %d\n", Top(S));

    Iterate(S);

    Pop(&S, &X);
    printf("VAL %d\n", X);
    printf("TOP %d\n", Top(S));
    printf("EMPTY %d\n", IsEmpty(S));

    return 0;
}
