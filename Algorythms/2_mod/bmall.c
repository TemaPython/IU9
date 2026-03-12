#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#define ALPHABET_SIZE 256


void Suffix(char* s, int* sigma)
{
	int n = strlen(s);
	int i = n - 1;
	sigma[i] = i;
	int j = n - 2;
	while (j >= 0)
	{
		while (i < n - 1 && s[i] != s[j]) i = sigma[i + 1];
		if (s[i] == s[j]) --i;
		sigma[j] = i;
		--j;
	}
}

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

void Delta2(char* s, int* delta)
{
	int n = strlen(s);
	int* sigma = (int*)malloc(n * sizeof(int));
	Suffix(s, sigma);
	int i = 0;
	int t = sigma[0];
	while (i < n)
	{
		while (t < i) t = sigma[t + 1];
		delta[i] = -i + t + n;
		++i;
	}
	i = 0;
	while (i < n - 1)
	{
		t = i;
		while (t < n - 1)
		{
			t = sigma[t + 1];
			if (s[i] != s[t]) delta[t] = -(i + 1) + n;
		}
		++i;
	}
	free(sigma);
}

void BMSubst(char* s, int size, char* t)
{
	int ns = strlen(s);
	int nt = strlen(t);
	int* delta1 = (int*)malloc(size * sizeof(int));
	Delta1(s, size, delta1);
	int* delta2 = (int*)malloc(ns * sizeof(int));
	Delta2(s, delta2);
	int k = ns - 1;
	while (k < nt)
	{
		int i = ns - 1;
		while (t[k] == s[i])
		{
			if (i == 0)
			{
				printf("%d ", k);
				break;

			}
			--i;
			--k;
		}
		k += (delta1[(unsigned char)t[k]] > delta2[i]) ?
			delta1[(unsigned char)t[k]] : delta2[i];
	}
	free(delta1);
	free(delta2);
}


int main(int argc, char* argv[])
{
	char* s = argv[1];
	char* t = argv[2];
	BMSubst(s, ALPHABET_SIZE, t);
	return 0;
}