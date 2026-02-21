#include<stdio.h>

int main() {
	int t, b, k, permit = 0;
	
	scanf("%d", &t);
	scanf("%d", &b);
	scanf("%d", &k);
	
	if (k == 1) {
		/*wahana 1*/
		if (t <= 100 && b <= 150) {
			permit = 1;
		}
	} else if (k == 2) {
		/*wahana 2*/
		if (b > 150 && t < 180) {
			permit = 1;
		} else {
			if (t > 100) {
				permit = 1;
			} else if (t < 100 && b > 30) {
				permit = 1;
			}
		}
	} else if (k == 3) {
		if (b <= 150) {
			if (t > 100) {
				permit = 1;
			}
		} else {
			if (t > 100 && t < 180) {
				permit = 1;
			}
		}
	} else {
		if (b <= 150) {
			if (t > 100) {
				permit = 1;
			}
		} else {
			if (t > 180) {
				permit = 1;
			}
		}
	}

	if (permit) {
		printf("TRUE");
	} else {
		printf("FALSE");
	}

	printf("\n");
	return 0;
}
