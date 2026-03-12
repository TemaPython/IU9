#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>

union Int32 {
    int x;
    unsigned char bytes[4];
};

void DistributiionSort(union Int32* arr, int n, int pos)
{
    int count[256] = { 0 };
	union Int32* output = (union Int32*)malloc(n * sizeof(union Int32));
    int j = 0;
    while (j < n)
    {
        unsigned char key = arr[j].bytes[pos];
        if (pos == 3) key ^= 0x80;
        ++count[key];
        ++j;
    }
    int i = 1;
    while (i < 256)
    {
        count[i] += count[i - 1];
        ++i;
    }
    j = n - 1;
    while (j >= 0)
    {
        unsigned char key = arr[j].bytes[pos];
        if (pos == 3) key ^= 0x80;
        --count[key];
        output[count[key]] = arr[j];
        --j;
    }
    for (int i = 0; i < n; ++i) arr[i] = output[i];
    free(output);
}

void RadixSort(union Int32* arr, int n)
{
    for (int pos = 0; pos < 4; ++pos) DistributiionSort(arr, n, pos);
}

int main()
{
    int n;
	scanf("%d", &n);
	union Int32* arr = (union Int32*)malloc(n * sizeof(union Int32));
    for (int i = 0; i < n; i++)
    {
		scanf("%d", &arr[i].x);
    }
    RadixSort(arr, n);
    for (int i = 0; i < n; ++i)
    {
		printf("%d ", arr[i].x);
    }
    free(arr);
    return 0;
}