#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>

int main()
{
	int n;
	scanf("%d", &n);
	int* arr = (int*)malloc(n * sizeof(int));
	for (int i = 0; i < n; ++i) scanf("%d", &arr[i]);
	int count = 0;
	int total = 1 << n;
	for (int podmn = 1; podmn < total; ++podmn)
	{
		int x = 0;
		for (int i = 0; i < n; ++i)
		{
			if (podmn & (1 << i))
				x += arr[i];
		}
		if (x > 0 && (x & (x - 1)) == 0) ++count;
	}
	printf("%d", count);
	free(arr);
	return 0;
}