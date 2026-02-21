/* File : stack.h */
/* Definisi stack yang diimplementasi dengan tabel kontigu dan alokasi dinamik */

#include<stdio.h>
#include<stdlib.h>
#include "stack.h"

void CreateEmpty (Stack *S, int size) {
    S->T = (infotype*) malloc(sizeof(infotype) * size);
    MaxEl(*S) = size;
    Top(*S) = Nil;
}

void DeAlokasi (Stack *S) {
    free(S->T);
    Top(*S) = Nil;
    MaxEl(*S) = 0;
}


boolean IsEmpty (Stack S) {
    return Top(S) == Nil;
}

boolean IsFull (Stack S) {
    return Top(S) == MaxEl(S) - 1;
}

void Push (Stack * S, infotype X) {
    if (!IsFull(*S)) {
        if (Top(*S) == Nil) {
            Top(*S) = 0;
        } else {
            Top(*S)++;
        }
        
        InfoTop(*S) = X;
    }
}

void Pop (Stack * S, infotype* X) {
    if (!IsEmpty(*S)) {
        *X = InfoTop(*S);
        Top(*S)--;
    }
}

infotype Peek (Stack S, address i) {
    address tmp, idx;
    infotype result;
    
    if (!IsEmpty(S)) {
        tmp = Top(S);
        Top(S) = i;
        
        result = InfoTop(S);
        Top(S) = tmp;
        
        return result;
    }
    
    return Nil;
}

void Iterate (Stack S) {
    int idx;
    
    for (idx = Top(S); idx >= 0; idx--) {
        printf("%ld\n", Peek(S, idx));
    }
}
