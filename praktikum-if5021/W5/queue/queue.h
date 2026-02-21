/* File : queue.h */
/* Definisi queue yang diimplementasi dengan tabel kontigu dan alokasi dinamik */
/* Implementasi queue dengan circular buffer */

#ifndef _QUEUE_h
#define _QUEUE_h

#include "boolean.h"

/* Konstanta */
#define Nil -99
/* Nil adalah indeks tak terdefinisi */

/* Definisi elemen dan address */
typedef long infotype;
typedef int address;   /* indeks tabel */

/* Versi I : tabel dinamik, Head dan Tail eksplisit, ukuran disimpan */
typedef struct { infotype * T;  /* tabel penyimpan elemen */
                 address HEAD;  /* alamat penghapusan */
                 address TAIL;  /* alamat penambahan */
                 int MaxEl;     /* Max elemen queue */
               } Queue;
/* Definisi Queue kosong: HEAD=Nil; TAIL=Nil. */
/* Definisi container tabel Queue dari indeks 0..MaxEl-1 */

/* ********* AKSES (Selektor) ********* */
/* Jika Q adalah Queue, maka akses elemen : */
#define Head(Q) (Q).HEAD
#define Tail(Q) (Q).TAIL
#define InfoHead(Q) (Q).T[(Q).HEAD]
#define InfoTail(Q) (Q).T[(Q).TAIL]
#define MaxEl(Q) (Q).MaxEl

/* ********* Prototype ********* */
/* *** Kreator *** */
void CreateEmpty (Queue * Q, int Max);
/* I.S. sembarang */
/* F.S. Sebuah Q kosong terbentuk dan salah satu kondisi sbb: */
/* Jika alokasi berhasil, Tabel memori dialokasi berukuran Max */
/* atau : jika alokasi gagal, Q kosong dg MaxEl=0 */
/* Proses : Melakukan alokasi, membuat sebuah Q kosong */
/* *** Destruktor *** */
void DeAlokasi (Queue * Q);
/* Proses: Mengembalikan memori Q */
/* I.S. Q pernah dialokasi */
/* F.S. Q menjadi tidak terdefinisi lagi, MaxEl(Q) diset 0 */

/* *** Predikat Penting *** */
boolean IsEmpty (Queue Q);
/* Mengirim true jika Q kosong: lihat definisi di atas */
boolean IsFull (Queue Q);
/* Mengirim true jika tabel penampung elemen Q sudah penuh */
/* yaitu mengandung elemen sebanyak MaxEl */

/* *** Banyaknya elemen queue *** */
int NbElmt (Queue Q);
/* Mengirimkan banyaknya elemen queue. 
   Mengirimkan 0 jika Q kosong */

/* *** Primitif Add/Delete *** */
void Add (Queue * Q, infotype X);
/* Proses: Menambahkan X pada Q dengan aturan FIFO */
/* I.S. Q mungkin kosong, tabel penampung elemen Q TIDAK penuh */
/* F.S. X menjadi TAIL yang baru, TAIL "maju" */
/* Jika Tail(Q)=MaxEl maka Tail(Q) diset ke-0 */
void Del (Queue * Q, infotype * X);
/* Proses: Menghapus X pada Q dengan aturan FIFO */
/* I.S. Q tidak mungkin kosong */
/* F.S. X = nilai elemen HEAD pd I.S.,HEAD "maju";
        Q mungkin kosong */
		
/* *** Operasi Tambahan *** */
infotype Peek (Queue Q, address i); 
/* Prekondisi: Q tidak kosong */
/* Menghasilkan nilai Q pada indeks ke-i tanpa mengubah state dari Queue */
void Iterate (Queue Q);
/* Menuliskan isi Q dari Head ke Tail dengan menggunakan Peek */
/* Tidak menuliskan apa pun jika Q kosong */
/* I.S.: Q terdefinisi */
/* F.S. Isi Q tertulis ke layar dari Head ke Tail. Setiap elemen dituliskan sebagai satu baris,
        diakhiri sebuah enter. */

#endif