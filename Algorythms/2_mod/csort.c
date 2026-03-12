#define _CRT_SECURE_NO_WARNIN
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#define MAX 1000

void csort(char* src, char* dest)
{
	int i = 0;
	int words[MAX];
	int words_len[MAX];
	int count_words = 0;
	while (src[i] != '\0')
	{
		while (src[i] == ' ') ++i;
		if (src[i] == '\0')
			break;
		int start = i;
		int len = 0;
		while (src[i] != ' ' && src[i] != '\0')
		{
			i++;
			len++;
		}
		words[count_words] = start;
		words_len[count_words] = len;
		++count_words;
	}
	int* count = (int*)calloc(count_words, sizeof(int));
	int j = 0;
	while (j < count_words - 1)
	{
		int i = j + 1;
		while (i < count_words)
		{
			if (words_len[i] < words_len[j]) count[j] = count[j] + 1;
			else count[i] = count[i] + 1;
			++i;
		}
		++j;
	}
	int* pos = (int*)malloc(count_words * sizeof(int));
	for (int i = 0; i < count_words; ++i) pos[count[i]] = i;
	int cur = 0;
	for (int i = 0; i < count_words; ++i)
	{
		int j = words[pos[i]];
		int k = 0;
		while (k < words_len[pos[i]])
		{
			dest[cur++] = src[j + k];
			++k;
		}
		if (i + 1 != count_words)
		{
			dest[cur] = ' ';
			++cur;
		}
	}
	dest[cur] = '\0';
	free(pos);
	free(count);
}
int main()
{
	char str[MAX];
	char vspom[MAX];
	fgets(str, MAX, stdin);
	str[strcspn(str, "\n")] = '\0';
	csort(str, vspom);
	printf("%s\n", vspom);
	return 0;
}