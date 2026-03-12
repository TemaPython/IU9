#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
struct Date
{
    int Day, Month, Year;
};

void DistributionSort(int m, struct Date* dates, int n)
{
	struct Date* output = (struct Date*)malloc(n * sizeof(struct Date));
	int* count = (int*)calloc(m, sizeof(int));
	int j = 0;
    while (j < n)
    {
        int idx;
        if (m == 31) idx = dates[j].Day - 1;
		if (m == 12) idx = dates[j].Month - 1;
		if (m == 61) idx = dates[j].Year - 1970;
		count[idx] = count[idx] + 1;
        ++j;
    }
    int i = 1;
    while (i < m)
    {
        count[i] = count[i] + count[i - 1];
		++i;
    }
	j = n - 1;
    while (j >= 0)
    {
        int k;
        if (m == 31) k = dates[j].Day - 1;
        if (m == 12) k = dates[j].Month - 1;
        if (m == 61) k = dates[j].Year - 1970;
		count[k] = count[k] - 1;
        output[count[k]] = dates[j];
		--j;
    }
    for (int i = 0; i < n; ++i)
        dates[i] = output[i];
	free(count);
	free(output);
}

void RadixSort(struct Date* dates, int n)
{
    DistributionSort(31, dates,n);
    DistributionSort(12, dates, n);
    DistributionSort(61, dates, n);
}

int main()
{
    int n;
    scanf("%d", &n);
    struct Date* dates = (struct Date*)malloc(n * sizeof(struct Date));
    for (int i = 0; i < n; ++i)
        scanf("%4d %2d %2d", &dates[i].Year, &dates[i].Month, &dates[i].Day);
    RadixSort(dates, n);
    for (int i = 0; i < n; ++i)
        printf("%04d %02d %02d  \n", dates[i].Year, dates[i].Month, dates[i].Day);
    free(dates);
}