#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>

void shellsort(unsigned long nel,
    int (*compare)(unsigned long i, unsigned long j),
    void (*swap)(unsigned long i, unsigned long j))
{
	int prev, next;
	prev = next = 1;
	while (prev + next <= nel)
	{
		int prom = next;
		next += prev;
		prev = prom;
	}
	while (prev != 0)
	{
		int j = nel - 1;
		while (j > 0)
		{
			int k = j;
			int i = j - 1;
			while (i >= 0)
			{
				if (compare(i, k) == 1) k = i;
				i = i - next;
			}
			swap(j, k);
			--j;
		}
		int prom = next;
		next = prev;
		prev = prom - prev;
	}
}