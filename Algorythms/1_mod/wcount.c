#include <stdio.h>
#include <string.h>
#define MAX 1001

int wcount(char* s)
{
	int n = strlen(s);
	int k = 0;
	if (n == 0) return k;
	if (s[0] != ' ') ++k;
	int i = 1;
	while (i < n)
	{
		if (s[i] == ' ' || s[i] == '\0')
		{
			++i;
			continue;
		}
		if (i != 0 && s[i - 1] == ' ') ++k;
		++i;
	}
	return k;
}

int main()
{
	char s[MAX];
	fgets(s, MAX, stdin);
	s[strcspn(s, "\n")] = '\0';
	int k = wcount(s);
	printf("%d\n", k);
	return 0;
}