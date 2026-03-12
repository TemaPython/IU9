#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define MAX 1001

struct Elem {
	struct Elem* next;
	char* word;
};

void InitSingleLinkedList(struct Elem** l)
{
	*l = NULL;
}

void InsertAfter(struct Elem* x, struct Elem* y)
{
	struct Elem* z = x->next;
	x->next = y;
	y->next = z;
}

void Swap(struct Elem* x, struct Elem* y)
{
	char* prom = y->word;
	y->word = x->word;
	x->word = prom;
}

int compare(char* a, char* b)
{
	return strlen(a) > strlen(b);
}

struct Elem* bsort(struct Elem* list)
{
	if (!list)
		return NULL;

	int isswap = 1;
	while (isswap)
	{
		isswap = 0;
		struct Elem* x = list;
		while (x->next != NULL)
		{
			if (compare(x->word, x->next->word))
			{
				Swap(x, x->next);
				isswap = 1;
			}
			x = x->next;
		}
	}
	return list;
}

int main()
{
	char st[MAX];
	fgets(st, MAX, stdin);
	st[strcspn(st, "\n")] = '\0';
	char* word = strtok(st, " \n\t");
	struct Elem* l;
	InitSingleLinkedList(&l);
	struct Elem* tail = NULL;

	int n = 0;
	while (word)
	{
		struct Elem* el = malloc(sizeof(struct Elem));
		el->word = word;
		el->next = NULL;
		if (l == NULL) {
			l = el;
			tail = el;
		}
		else {
			InsertAfter(tail, el);
			tail = el;
		}
		word = strtok(NULL, " \n\t");
		++n;
	}

	struct Elem* x = bsort(l);
	for (int i = 0; i < n; ++i)
	{
		printf("%s ", x->word);
		x = x->next;
	}

	x = l;
	for (int i = 0; i < n; ++i)
	{
		struct Elem* z = x->next;
		free(x);
		x = z;
	}
	return 0;
}