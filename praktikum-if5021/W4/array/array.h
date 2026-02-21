/* MODUL TABEL INTEGER */
/* Berisi definisi dan semua primitif pemrosesan tabel integer */
/* Penempatan elemen selalu rapat kiri */
/* Versi I : dengan banyaknya elemen didefinisikan secara eksplisit, 
   memori tabel statik */

#ifndef ARRAY_H
#define ARRAY_H

#include "boolean.h"

/*  Kamus Umum */
#define NMax 100
/* Ukuran maksimum array, sekaligus indeks maksimum */
#define IdxUndef -999 
/* Indeks tak terdefinisi*/

/* Definisi elemen dan koleksi objek */
typedef int IdxType;  /* type indeks */
typedef int ElType;   /* type elemen tabel */
typedef struct { 
	ElType TI[NMax]; /* memori tempat penyimpan elemen (container) */
	int Neff; /* >=0, banyaknya elemen efektif */
} TabInt;
/* Indeks yang digunakan [0..NMax-1] */
/* Jika T adalah TabInt, cara deklarasi dan akses: */
/* Deklarasi : T : TabInt */
/* Maka cara akses: 
   T.Neff  untuk mengetahui banyaknya elemen 
   T.TI    untuk mengakses seluruh nilai elemen tabel 
   T.TI[i] untuk mengakses elemen ke-i */
/* Definisi : 
  Tabel kosong: T.Neff = 0
  Definisi elemen pertama : T.TI[i] dengan i=0 
  Definisi elemen terakhir yang terdefinisi: T.TI[i] dengan i=T.Neff */
  
/* ********** SELEKTOR ********** */
#define Neff(T)		(T).Neff
/* selektor nilai efektif TI */
#define TI(T)		(T).TI
/* selektor TI */
#define Elmt(T,i)	(T).TI[(i)]
/* selektor elemen TI */

/* ********** KONSTRUKTOR **********
   Konstruktor : create tabel kosong
   I.S. T sembarang
   F.S. Terbentuk tabel T kosong dengan kapasitas IdxMax-IdxMin+1 */
void MakeEmpty (TabInt * T);

/* *** Banyaknya elemen ***
   Mengirimkan banyaknya elemen efektif tabel
   Mengirimkan nol jika tabel kosong */
int NbElmt (TabInt T);

/* ********** TEST KOSONG/PENUH **********
   *** Test tabel kosong ***
   Mengirimkan true jika tabel T kosong, mengirimkan false jika tidak
   *** Test tabel penuh *** */
boolean IsEmpty (TabInt T);

/* Mengirimkan true jika tabel T penuh, mengirimkan false jika tidak */
boolean IsFull (TabInt T);

/* ********** BACA dan TULIS dengan INPUT/OUTPUT device ********** */
/* *** Mendefinisikan isi tabel dari pembacaan ***
   I.S. sembarang
 F.S. Tabel T terdefinisi
 Proses : membaca banyaknya elemen T dan mengisi nilainya
 1. Baca banyaknya elemen diakhiri enter, misalnya N
    Pembacaan diulangi sampai didapat N yang benar yaitu 0 <= N <= NMax
    Jika N tidak valid, tidak diberikan pesan kesalahan
 2. Jika 0 < N <= NMax; Lakukan N kali: Baca elemen mulai dari indeks 
      IdxMin satu per satu diakhiri enter
    Jika N = 0; hanya terbentuk T kosong */
void BacaIsi (TabInt * T);

/* Proses : Menuliskan isi tabel dengan traversal, tabel ditulis di antara kurung siku; 
   antara dua elemen dipisahkan dengan separator "koma", tanpa tambahan karakter di depan,
   di tengah, atau di belakang, termasuk spasi dan enter
   I.S. T boleh kosong
   F.S. Jika T tidak kosong: [e1,e2,...,en]
   Contoh : jika ada tiga elemen bernilai 1, 20, 30 akan dicetak: [1,20,30]
   Jika tabel kosong : menulis [] */
void TulisIsi (TabInt T);

/* ********** OPERATOR ARITMATIKA ********** */
/* *** Aritmatika tabel : Penjumlahan, pengurangan, perkalian, ... ***
   Prekondisi : T1 dan T2 berukuran sama dan tidak kosong
   Mengirimkan  T1+T2, yaitu setiap elemen T1 dan T2 pada indeks yang sama dijumlahkan */
TabInt PlusTab (TabInt T1, TabInt T2);

/* Prekondisi : T1 dan T2 berukuran sama dan tidak kosong
   Mengirimkan T1-T2, yaitu setiap elemen T1 dikurangi elemen T2 pada indeks yang sama */
TabInt MinusTab (TabInt T1, TabInt T2);

/* Prekondisi : Tin tidak kosong
   Mengirimkan tabel dengan setiap elemen Tin dikalikan c */
TabInt KaliKons (TabInt Tin, ElType c);

/* ********** OPERATOR RELASIONAL ********** */
/* *** Operasi pembandingan tabel : < =, > ***
   Mengirimkan true jika T1 sama dengan T2 yaitu jika ukuran T1 = T2 dan semua elemennya sama */
boolean IsEQ (TabInt T1, TabInt T2);

/* ********** SEARCHING ********** */
/* ***  Perhatian : Tabel boleh kosong!! *** */
/* Search apakah ada elemen tabel T yang bernilai X
   Jika ada, menghasilkan indeks i terkecil, dengan elemen ke-i = X
   Jika tidak ada, mengirimkan IdxUndef
   Menghasilkan indeks tak terdefinisi (IdxUndef) jika tabel T kosong
   Skema Search bebas */
IdxType Search (TabInt T, ElType X);

/* Search apakah ada elemen tabel T yang bernilai X
   Jika ada, menghasilkan true, jika tidak ada menghasilkan false
   Skema Search bebas */
boolean SearchB (TabInt T, ElType X);

/* ********** NILAI EKSTREM ********** */
/* Prekondisi : Tabel T tidak kosong
   Mengirimkan nilai maksimum tabel */
ElType ValMax (TabInt T);

/* Prekondisi : Tabel T tidak kosong
   Mengirimkan nilai minimum tabel
   *** Mengirimkan indeks elemen bernilai ekstrem *** */
ElType ValMin (TabInt T); 

/* Prekondisi : Tabel T tidak kosong
   Mengirimkan indeks i terkecil dengan nilai elemen merupakan nilai maksimum pada tabel */
IdxType IdxMaxTab (TabInt T);

/* Prekondisi : Tabel T tidak kosong
   Mengirimkan indeks i terkecil dengan nilai elemen merupakan nilai minimum pada tabel */
IdxType IdxMinTab (TabInt T);

/* ********** OPERASI LAIN ********** */
/* I.S. Tin terdefinisi, Tout sembarang
   F.S. Tout berisi salinan dari Tin (elemen dan ukuran identik)
 Proses : Menyalin isi Tin ke Tout */
void CopyTab (TabInt Tin, TabInt * Tout);

/* Menghasilkan tabel dengan urutan tempat yang terbalik, yaitu :
   elemen pertama menjadi terakhir,
   elemen kedua menjadi elemen sebelum terakhir, dst..
   Tabel kosong menghasilkan tabel kosong */
TabInt InverseTab (TabInt T);

/* Menghasilkan true jika tabel simetrik
   Tabel disebut simetrik jika:
        elemen pertama = elemen terakhir,
        elemen kedua = elemen sebelum terakhir, dan seterusnya
   Tabel kosong adalah tabel simetris */
boolean IsSimetris (TabInt T);

/* ********** SORTING ********** */
/* I.S. T boleh kosong, asc terdefinisi: true jika T akan diurutkan menaik, false jika T akan diurutkan menurun
   F.S. T elemennya terurut menurun dengan Maximum Sort
   Proses : mengurutkan T sehingga elemennya menaik/menurun tergantung nilai asc
   Algoritma bebas (tuliskan sebagai komentar pada array.c) */
void SortDesc (TabInt * T, boolean asc);

/* ********** MENAMBAH ELEMEN ********** */
/* *** Menambahkan elemen terakhir ***
   Proses: Menambahkan X sebagai elemen terakhir tabel
   I.S. Tabel T boleh kosong, tetapi tidak penuh
   F.S. X adalah elemen terakhir T yang baru */
void AddAsLastEl (TabInt * T, ElType X);

/* Menambahkan X sebagai elemen ke-i tabel tanpa mengganggu kontiguitas 
   terhadap elemen yang sudah ada
   I.S. Tabel tidak kosong dan tidak penuh
        i adalah indeks yang valid.
   F.S. X adalah elemen ke-i T yang baru
   Proses : Geser elemen ke-i+1 s.d. terakhir
            Isi elemen ke-i dengan X */
void AddEli (TabInt * T, ElType X, IdxType i);

/* ********** MENGHAPUS ELEMEN ********** */
/* Proses : Menghapus elemen terakhir tabel
   I.S. Tabel tidak kosong
   F.S. X adalah nilai elemen terakhir T sebelum penghapusan,
        Banyaknya elemen tabel berkurang satu
        Tabel T mungkin menjadi kosong */
void DelLastEl (TabInt * T, ElType * X);

/* Menghapus elemen ke-i tabel tanpa mengganggu kontiguitas
   I.S. Tabel tidak kosong, i adalah indeks efektif yang valid
   F.S. X adalah nilai elemen ke-i T sebelum penghapusan
        Banyaknya elemen tabel berkurang satu
        Tabel T mungkin menjadi kosong
   Proses : Geser elemen ke-i+1 s.d. elemen terakhir
            Kurangi elemen efektif tabel */
void DelEli (TabInt * T, IdxType i, ElType * X);

#endif