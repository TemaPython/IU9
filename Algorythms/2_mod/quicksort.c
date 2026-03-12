#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>

void SelectSort(int* arr, int low, int high)
{
	int j = high;
	while (j > low)
	{
		int i = j - 1;
		int k = j;
		while (i >= low)
		{
			if (arr[i] > arr[k]) k = i;
			--i;
		}
		int prom = arr[k];
		arr[k] = arr[j];
		arr[j] = prom;
		--j;
	}
}

int Partition(int* arr, int low, int high)
{
	int i = low, j = low;
	while (j < high)
	{
		if (arr[j] < arr[high])
		{
			int prom = arr[i];
			arr[i] = arr[j];
			arr[j] = prom;
			++i;
		}
		++j;
	}
	int prom = arr[i];
	arr[i] = arr[high];
	arr[high] = prom;
	return i;
}

void QuickSortRec(int* arr, int low, int high, int m)
{
	if (high - low < m) SelectSort(arr, low, high);
	else if (low < high)
	{
		int q = Partition(arr, low, high);
		QuickSortRec(arr, low, q - 1, m);
		QuickSortRec(arr, q + 1, high, m);
	}
}

void QuickSort(int* arr, int n, int m)
{
	QuickSortRec(arr, 0, n - 1, m);
}

int main()
{
	int n, m;
	scanf("%d %d", &n, &m);
	int* arr = (int*)malloc(n * sizeof(int));
	for (int i = 0; i < n; ++i) scanf("%d", &arr[i]);
	QuickSort(arr, n, m);
	for (int i = 0; i < n; ++i) printf("%d ", arr[i]);
	free(arr);
}