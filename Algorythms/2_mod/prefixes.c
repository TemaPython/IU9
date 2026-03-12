#include <stdio.h>
#include <string.h>
#include <stdlib.h>
void Prefix(char* st)
{
	int t;
	int i = 1;
	int n = (int)strlen(st);
	int* pi = (int*)malloc(n * sizeof(int));
	pi[0] = t = 0;
	while (i < n)
	{
		while (t > 0 && st[t] != st[i]) t = pi[t - 1];
		if (st[t] == st[i]) ++t;
		pi[i] = t;
		++i;
		int x = pi[i - 1];
		int p = i - x;
		if (x > 0 && i % p == 0)
			printf("%d %d\n", i, i / p);
	}
	free(pi);
}

int main(int argc, char* argv[])
{
	Prefix(argv[1]);
}