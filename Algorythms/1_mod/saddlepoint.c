#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

int main()
{
	int n, m;
	scanf("%d %d", &n, &m);
	int* max_n = (int*)malloc(n * sizeof(int));
	int* min_m = (int*)malloc(m * sizeof(int));
	int* idx_n = (int*)malloc(n * sizeof(int));
	int* idx_m = (int*)malloc(n * sizeof(int));
	for (int i = 0; i < n; ++i)
		for (int j = 0; j < m; ++j)
		{
			int x;
			scanf("%d", &x);
			if (j == 0) max_n[i] = x;
			if (i == 0) min_m[j] = x;
			if (x >= max_n[i])
			{
				max_n[i] = x;
				idx_n[i] = i;
				idx_m[i] = j;
			}
			min_m[j] = (x < min_m[j]) ? x : min_m[j];
		}
	bool none_saddle = true;
	for (int i = 0; i < n; ++i)
		for (int j = 0; j < m; ++j)
		{
			if (max_n[i] == min_m[j])
			{
				printf("%d %d", idx_n[i], idx_m[i]);
				none_saddle = false;
				break;
			}
		}
	if (none_saddle) printf("none");
	free(max_n);
	free(min_m);
	free(idx_n);
	free(idx_m);
	return 0;
}