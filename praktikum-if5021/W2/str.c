// Deddy Ch. Kakunsi

#include<stdio.h>
#include<string.h>

int main() {
	char kata[100];
	char store[100];
	int i, j, s = 0, counter;
	int write = 1;

	for (i = 0; i < 100; i++) {
		kata[i] = 0;
		store[i] = 0;
	}
	
	scanf("%s", &kata);
	
	for (i = 0; i < sizeof(kata); i++) {
		if (kata[i] == 0)
			continue;
		
		write = 1;
		for (j = 0; j < sizeof(store); j++) {
			if (kata[i] == store[j])
				write = 0;
		}

		if (write) {
			store[s] = kata[i];
			s++;
		}		
	}

	/* Mencetak */
	for (i = 0; i < sizeof(store); i++) {
		if (store[i] == 0)
			continue;
		
		counter = 0;
		for (j = 0; j < sizeof(kata); j++) {
			if (store[i] == kata[j])
				counter++;
		}
		
		printf("%c %d\n", store[i], counter);
	}
	
	return 0;
}
