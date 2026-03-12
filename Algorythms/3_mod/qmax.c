#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define MX 20
#define BUFER 100000

struct Item {
	int x;
	int curmax;
};

struct DoubleStack {
	struct Item* data;
	int cap;
	int top1;
	int top2;
};

int mx(int a, int b)
{
	return (a > b) ? a : b;
}

void InitDoubleStack(struct DoubleStack* s, int n)
{
	s->data = (struct Item*)malloc(n * sizeof(struct Item));
	s->cap = n;
	s->top1 = 0;
	s->top2 = n - 1;
}

int StackEmpty1(struct DoubleStack* s)
{
	return s->top1 == 0;
}

int StackEmpty2(struct DoubleStack* s)
{
	return s->top2 == s->cap - 1;
}

void Push1(struct DoubleStack* s, int x)
{
	if (s->top1 > s->top2) printf("overflow");
	s->data[s->top1].curmax  = (StackEmpty1(s)) ? x : mx(x, s->data[s->top1 - 1].curmax);
	s->data[s->top1].x = x;
	++s->top1;
}

void Push2(struct DoubleStack* s, int x)
{
	if (s->top1 > s->top2) printf("overflow");
	s->data[s->top2].curmax = (StackEmpty2(s)) ? x : mx(x, s->data[s->top2 + 1].curmax);
	s->data[s->top2].x = x;
	--s->top2;
}

int Pop1(struct DoubleStack* s)
{
	if (StackEmpty1(s)) printf("empty");
	--s->top1;
	return s->data[s->top1].x;
}

int Pop2(struct DoubleStack* s)
{
	if (StackEmpty2(s)) printf("empty");
	++s->top2;
	return s->data[s->top2].x;
}

int Max1(struct DoubleStack* s)
{
	return s->data[s->top1 - 1].curmax;
}

int Max2(struct DoubleStack* s)
{
	return s->data[s->top2 + 1].curmax;
}

void InitQueueOnStack(struct DoubleStack* s, int n)
{
	InitDoubleStack(s, n);
}

void QueueEmpty(struct DoubleStack* s)
{
	if (StackEmpty1(s) && StackEmpty2(s)) printf("true\n");
	else printf("false\n");
}

void Enqueue(struct DoubleStack* s, int x)
{
	Push1(s, x);
}

int Dequeue(struct DoubleStack* s)
{
	if (StackEmpty2(s))
		while (!StackEmpty1(s)) Push2(s, Pop1(s));
	return Pop2(s);
}

int Maximum(struct DoubleStack* s)
{
	if (StackEmpty1(s)) return Max2(s);
	else if (StackEmpty2(s)) return Max1(s);
	else return mx(Max1(s), Max2(s));
}

int main()
{
	struct DoubleStack s;
	InitQueueOnStack(&s, BUFER);
	while (1)
	{
		char st[MX];
		fgets(st, MX, stdin);
		st[strcspn(st, "\n")] = '\0';
		char* op = strtok(st, " ");
		if (strcmp(op, "ENQ") == 0) Enqueue(&s, atoi(strtok(NULL, " ")));
		else if (strcmp(op, "DEQ") == 0) printf("%d\n", Dequeue(&s));
		else if (strcmp(op, "MAX") == 0) printf("%d\n", Maximum(&s));
		else if (strcmp(op, "EMPTY") == 0) QueueEmpty(&s);
		else if (strcmp(op, "END") == 0) break;
	}
	free(s.data);
}