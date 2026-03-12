#include <stdio.h>

static int broken_bit(unsigned char a, unsigned char b)
{
	for (int i = 0; i < 8; ++i)
	{
		int bit_a = (a >> i) & 1;
		int bit_b = (b >> i) & 1;
		if (bit_a != bit_b) return i;
	}
	return -1;
}
int strdiff(char *a, char *b)
{
	int i = 0;
	for (i; a[i] != '\0' || b[i] != '\0'; ++i)
	{
		unsigned char a_bits = (a[i] != '\0') ? (unsigned char)a[i] : 0;
		unsigned char b_bits = (b[i] != '\0') ? (unsigned char)b[i] : 0;
		int broked = broken_bit(a_bits, b_bits);
		if (broked != -1) return i * 8 + broked;
	}
	return -1;
}

int main()
{
	char *a = "The quick brown fox jumps over the lazy dog";
	char *b = "";

	int res = strdiff(a, b);
	printf("%d", res);
	return 0;
}