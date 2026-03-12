#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define MAX_STR 1000001
#define MAX 26
void DistributionSort(char* st, char* stout, int n)
{
	int count[MAX] = { 0 };
	int j = 0;
	while (j < n)
	{
		int idx = st[j] - 'a';
		count[idx] = count[idx] + 1;
		++j;
	}
	int i = 1;
	while (i < MAX)
	{
		count[i] = count[i] + count[i - 1];
		++i;
	}
	j = n - 1;
	while (j >= 0)
	{
		int k = st[j] - 'a';
		int i = count[k] - 1;
		count[k] = i;
		stout[i] = st[j];
		--j;
	}
	stout[n] = '\0';
}

int main()
{
	char st[MAX_STR];
	fgets(st, MAX_STR, stdin);
	st[strcspn(st, "\n")] = '\0';
	int n = strlen(st);
	char* stout = (char*)malloc((n + 1) * sizeof(char));
	DistributionSort(st, stout, n);
	printf("%s", stout);
	free(stout);
	return 0;
}