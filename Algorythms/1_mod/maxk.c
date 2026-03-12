#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>

int main()
{
	int n, k, maxsum, sumnow;
	sumnow = maxsum = 0;
	scanf("%d", &n);
	int* arr = (int*)malloc(n * sizeof(int));
	for (int i = 0; i < n; ++i)
	{
		scanf("%d", &arr[i]);
	}
	scanf("%d", &k);
	int left = arr[0];
	for (int i = 0;i < n; ++i)
	{
		if (i < k)
		{
			sumnow += arr[i];
			maxsum += arr[i];
		}
		else
		{
			maxsum = (sumnow > maxsum) ? sumnow : maxsum;
			sumnow = sumnow - left + arr[i];
			left = arr[i - k + 1];
		}
	}
	maxsum = (sumnow > maxsum) ? sumnow : maxsum;
	free(arr);
	printf("%d", maxsum);
}