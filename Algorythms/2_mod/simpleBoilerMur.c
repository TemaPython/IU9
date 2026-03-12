#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#define ALPHABET_SIZE 256

void Delta1(char* s, int size, int* delta)
{
	int n = strlen(s);
	int a = 0;
	while (a < size) delta[a++] = n;
	int j = 0;
	while (j < n)
	{
		delta[(unsigned char)s[j]] = n - j - 1;
		++j;
	}
}

int SimpleBMSubst(char* s, int size, char* t)
{
	int* delta = (int*)malloc(size * sizeof(int));
	Delta1(s, size, delta);
	int ns = strlen(s);
	int nt = strlen(t);
	int k = ns - 1;
	while (k < nt)
	{
		int i = ns - 1;
		while (t[k] == s[i])
		{
			if (i == 0) 
			{
				free(delta);
				return k;
			}
			--i;
			--k;
		}
		int x = delta[(unsigned char)t[k]];
		if (x < 1) x = 1;
		k += x;
	}
	free(delta);
	return nt;
}

int main()
{
	char t[] = "which finally halts   at that point";
	char s[] = "at that";
	int k = SimpleBMSubst(s, ALPHABET_SIZE, t);
	printf("%d", k);
	return 0;
}