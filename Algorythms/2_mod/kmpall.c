#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void Prefix(char* s, int* pi, int n)
{
	int i = 1;
	int t = 0;
	pi[0] = 0;
	while (i < n)
	{
		while (t > 0 && s[i] != s[t]) t = pi[t - 1];
		if (s[i] == s[t]) ++t;
		pi[i] = t;
		++i;
	}
	//for (int i = 0; i < n; ++i) printf("%d", pi[i]);
}

void KMPSubst(char* s, char* t)
{
	int n = strlen(s);
	int l = strlen(t);
	int* pi = (int*)malloc(n * sizeof(int));
	Prefix(s, pi, n);
	int q = 0;
	int k = 0;
	while (k < l)
	{
		while (q > 0 && s[q] != t[k]) q = pi[q - 1];
		if (s[q] == t[k]) ++q;
		if (q == n) 
		{
			k = k - n + 1;
			printf("%d ", k);
			q = 0;
		}
		++k;
	}
	free(pi);
}

int main(int argc, char* argv[])
{
	char* s	= argv[1];
	char* t = argv[2];
	KMPSubst(s, t);
	return 0;
}