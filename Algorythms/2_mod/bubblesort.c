#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>

void bubblesort(unsigned long nel,
    int (*compare)(unsigned long i, unsigned long j),
    void (*swap)(unsigned long i, unsigned long j))
{
    int left = 0;
    int right = nel - 1;
    while (left <= right)
    {
        for (int i = left; i < right; ++i)
        {
            if (compare(i, i + 1) == 1) swap(i, i + 1);
        }
        --right;
        for (int i = right; i > left; --i)
        {
            if (compare(i - 1, i) == 1) swap(i - 1, i);
        }
        ++left;
    }
}