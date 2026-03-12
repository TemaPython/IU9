#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>

int main()
{ 
	int n;
	long long int x0, p, p_der;
	p = p_der = 0;

	scanf("%d %lld", &n, &x0);

	for (n; n >= 0; --n)
	{
		long long int ai;
		scanf("%lld", &ai);

		p_der = p_der * x0 + p;
		p = p * x0 + ai;
	}

	printf("%lld %lld", p, p_der);
	return 0;
}