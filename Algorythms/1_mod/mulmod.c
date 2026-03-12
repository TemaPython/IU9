#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>

unsigned long long int polynom(unsigned long long int a,
	unsigned long long int b, unsigned long long int m)
{
	if (b == 0) return 0;
	unsigned long long int pol = (polynom(a, b / 2, m) * (2 % m)) % m;
	unsigned long long int ost = ((a % m) * ((b % 2) % m)) % m;
	return (pol + ost) % m;
}
int main()
{
	unsigned long long int a, b, m;
	scanf("%llu %llu %llu", &a, &b, &m);
	unsigned long long int p = polynom(a, b, m) % m;
	printf("%llu", p);
	return 0;
}