#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>

int main()
{
	int len_a, len_b;
	scanf("%d", &len_a);
	unsigned int a = 0, b = 0;
	for (int i = 0; i < len_a; ++i)
	{
		int bit;
		scanf("%d", &bit);
		a = a | (1 << bit);
	}
	scanf("%d", &len_b);
	for (int i = 0; i < len_b; ++i)
	{
		int bit;
		scanf("%d", &bit);
		b = b | (1 << bit);
	}
	unsigned int c = a & b;
	for (int i = 0; c > 0; ++i)
	{
		if (c & 1) printf("%d ", i);
		c >>= 1;
	}
	return 0;
}