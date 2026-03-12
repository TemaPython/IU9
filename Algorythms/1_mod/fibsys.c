#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>

int main()
{
	long long int x;
	scanf("%lld", &x);
	long long int cnt, prev, next;
	prev = next = 1;
	cnt = 2;
	while (prev + next <= x)
	{
		long long int prom = next;
		next += prev;
		prev = prom;
		++cnt;
	}
	int* arr = (int*)calloc(cnt, sizeof(int));
	long long int i = cnt;
	while (x > 0)
	{
		if (next <= x)
		{
			arr[i - 1] = 1;
			x -= next;
		}
		long long int prom = next;
		next = prev;
		prev = prom - prev;
		--i;
	}
	for (--cnt; cnt > 0; --cnt) printf("%d", arr[cnt]);
	free(arr);
	return 0;
}