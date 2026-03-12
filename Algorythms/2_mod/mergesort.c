#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>

void InsertSort(int* arr, int left, int right)
{
	int i = 1;
	while (i < right - left + 1)
	{
		int elem = arr[left + i];
		int loc = left + i - 1;
		while ((loc >= left) && (abs(arr[loc]) > abs(elem)))
		{
			arr[loc + 1] = arr[loc];
			--loc;
		}
		arr[loc + 1] = elem;
		++i;
	}
}
void Merge(int* arr, int k, int l, int m)
{
	int* tarr = (int*)malloc((m - k + 1) * sizeof(int));
	int i = k;
	int j = l + 1;
	int h = 0;
	while (h < m - k + 1)
	{
		if ((j <= m) && ((i == l + 1) || abs(arr[j]) < abs(arr[i]))) tarr[h] = arr[j++];
		else tarr[h] = arr[i++];
		++h;
	}
	for (int i = 0; i < h; ++i) arr[k + i] = tarr[i];
	free(tarr);
}
void MergeSortRec(int* arr, int low, int high)
{
	if (high - low >= 5)
	{	
		int med = (low + high) / 2;
		MergeSortRec(arr, low, med);
		MergeSortRec(arr, med + 1, high);
		Merge(arr, low, med, high);
	}
	else if (low < high) InsertSort(arr, low, high);
}
int main()
{
	int n;
	scanf("%d", &n);
	int* arr = (int*)malloc(n * sizeof(int));
	for (int i = 0; i < n; ++i) scanf("%d", &arr[i]);
	MergeSortRec(arr, 0, n - 1);
	for (int i = 0; i < n; ++i) printf("%d\n", arr[i]);
	free(arr);
}