#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define MX 30
#define STR_LEN 11

struct SkipList {
	int k;
	char v[STR_LEN];
	struct SkipList** next;
	int* span;
};

void InitSkipList(int m, struct SkipList* l)
{
	int i = 0;
	l->next = (struct SkipList**)malloc(m * sizeof(struct SkipList*));
	l->span = (int*)malloc(m * sizeof(int));
	while (i < m)
	{
		l->next[i] = NULL;
		l->span[i] = 0;
		++i;
	}
}

struct SkipList* Succ(struct SkipList* x)
{
	return x->next[0];
}
void Skip(struct SkipList* l, int m, int k, struct SkipList** p, int* pos)
{
	struct SkipList* x = l;
	int current_pos = 0;
	int i = m - 1;
	while (i >= 0)
	{
		while (x->next[i] != NULL && x->next[i]->k < k)
		{
			current_pos += x->span[i];
			x = x->next[i];
		}
		p[i] = x;
		pos[i] = current_pos;
		--i;
	}
}

int Rank(struct SkipList* l, int m, int k)
{
	struct SkipList* x = l;
	int current_rank = 0;
	int i = m - 1;
	while (i >= 0)
	{
		while (x->next[i] != NULL && x->next[i]->k < k)
		{
			current_rank += x->span[i];
			x = x->next[i];
		}
		--i;
	}
	return current_rank;
}

char* Lookup(struct SkipList* l, int m, int k)
{
	struct SkipList** p = (struct SkipList**)malloc(m * sizeof(struct SkipList*));
	int* pos = (int*)malloc(m * sizeof(int));
	Skip(l, m, k, p, pos);
	struct SkipList* x = Succ(p[0]);
	free(p);
	free(pos);
	return x->v;
}

void Insert(struct SkipList* l, int m, int k, char* v)
{
	struct SkipList** p = (struct SkipList**)malloc(m * sizeof(struct SkipList*));
	int* pos = (int*)malloc(m * sizeof(int));
	Skip(l, m, k, p, pos);
	struct SkipList* x = (struct SkipList*)malloc(sizeof(struct SkipList));
	x->k = k;
	strncpy(x->v, v, STR_LEN - 1);
	x->v[STR_LEN - 1] = '\0';
	x->next = (struct SkipList**)malloc(m * sizeof(struct SkipList*));
	x->span = (int*)malloc(m * sizeof(int));
	int r = rand();
	int i = 0;
	while (i < m && (i == 0 || r % 2 == 0))
	{
		x->next[i] = p[i]->next[i];
		x->span[i] = (p[i]->span[i] == 0) ? 0 : (p[i]->span[i] - (pos[0] - pos[i]));
		p[i]->next[i] = x;
		p[i]->span[i] = (pos[0] - pos[i]) + 1;
		i++;
		r /= 2;
	}
	while (i < m)
	{
		x->next[i] = NULL;
		x->span[i] = 0;
		if (p[i]->next[i] != NULL) p[i]->span[i]++;
		i++;
	}
	free(p);
	free(pos);
}

void Delete(struct SkipList* l, int m, int k)
{
	struct SkipList** p = (struct SkipList**)malloc(m * sizeof(struct SkipList*));
	int* pos = (int*)malloc(m * sizeof(int));
	Skip(l, m, k, p, pos);
	struct SkipList* x = Succ(p[0]);
	int i = 0;
	while (i < m)
	{
		if (p[i]->next[i] == x)
		{
			p[i]->span[i] += x->span[i] - 1;
			p[i]->next[i] = x->next[i];
		}
		else if (p[i]->next[i] != NULL)
		{
			p[i]->span[i]--;
		}
		i++;
	}
	free(x->next);
	free(x->span);
	free(x);
	free(p);
	free(pos);
}

void FreeSkipList(struct SkipList* l, int m)
{
	struct SkipList* now = l->next[0];
	while (now != NULL)
	{
		struct SkipList* next = now->next[0];
		free(now->next);
		free(now->span);
		free(now);
		now = next;
	}
	free(l->next);
	free(l->span);
	free(l);
}

int main()
{
	int m = 10;
	struct SkipList* l = (struct SkipList*)malloc(sizeof(struct SkipList));
	InitSkipList(m, l);

    while (1)
    {
        char st[MX];
        fgets(st, MX, stdin);
        st[strcspn(st, "\n")] = '\0';
        char* op = strtok(st, " ");
        int x = 0;
		if (strcmp(op, "INSERT") == 0)
		{
			char* k = strtok(NULL, " ");
			char* v = strtok(NULL, " ");
			Insert(l, m, atoi(k), v);
		}
		else if (strcmp(op, "LOOKUP") == 0)
			printf("%s\n", Lookup(l, m, atoi(strtok(NULL, " "))));
		else if (strcmp(op, "DELETE") == 0)
			Delete(l, m, atoi(strtok(NULL, " ")));
		else if (strcmp(op, "RANK") == 0)
			printf("%d\n", Rank(l, m, atoi(strtok(NULL, " "))));
		else if (strcmp(op, "END") == 0) break;
    }
	FreeSkipList(l, m);
	return 0;
}