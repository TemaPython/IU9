#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#define MAX 1000

int compare(const void* a, const void* b)
{
	const char* sa = *(const char* const*)a;
	const char* sb = *(const char* const*)b;
	int cnt1, cnt2;
	cnt1 = cnt2 = 0;
	for (int i = 0; i < strlen(sa); ++i)
		if (sa[i] == 'a') ++cnt1;
	for (int i = 0; i < strlen(sb); ++i)
		if (sb[i] == 'a') ++cnt2;
	return (cnt1 - cnt2);
}

void Heapify(void* base, size_t nel, size_t width, size_t i,
	int (*compare)(const void*, const void*))
{
	char* arr = (char*)base;
	while (1)
	{
		size_t l = 2 * i + 1;
		size_t r = l + 1;
		size_t j = i;
		char* pi = arr + j * width;
		char* pl, *pr;
		if (l < nel) {
			pl = arr + l * width;
			if (compare(pi, pl) < 0) {
				j = l;
				pi = pl;
			}
		}
		if (r < nel) {
			pr = arr + r * width;
			if (compare(pi, pr) < 0) {
				j = r;
				pi = pr;
			}
		}
		if (i == j) break;
		char prom;
		char* a = arr + i * width;
		char* b = arr + j * width;
		for (size_t k = 0; k < width; ++k) {
			prom = a[k];
			a[k] = b[k];
			b[k] = prom;
		}
		i = j;
	}
}

void hsort(void* base, size_t nel, size_t width,
	int (*compare)(const void* a, const void* b))
{
	if (nel < 2) return;

	for (size_t i = nel / 2; i > 0; --i)
		Heapify(base, nel, width, i - 1, compare);

	for (size_t i = nel - 1; i > 0; --i) {
		char* arr = (char*)base;
		char prom;
		char* a = arr;
		char* b = arr + i * width;
		for (size_t k = 0; k < width; ++k) {
			prom = a[k];
			a[k] = b[k];
			b[k] = prom;
		}
		Heapify(base, i, width, 0, compare);
	}
}
int main(int argc, char* argv[])
{
	int n;
	scanf("%d", &n);
	char** arr = malloc(n * sizeof(char*));
	for (int i = 0; i < n; ++i) {
		char st[MAX];
		scanf("%s", st);
		arr[i] = malloc(strlen(st) + 1);
		strcpy(arr[i], st);
	}
	hsort(arr, n, sizeof(char*), compare);
	for (int i = 0; i < n; ++i) printf("%s\n", arr[i]);
	for (int i = 0; i < n; ++i) free(arr[i]);
	free(arr);
	return 0;
}