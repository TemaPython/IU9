#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define MX 20
#define BUFER 4

struct Queue {
	int* data;
	int cap;
	int count;
	int head;
	int tail;
};

void InitQueue(struct Queue* q, int n)
{
	q->data = (int*)malloc(n * sizeof(int));
	q->cap = n;
	q->count = 0;
	q->head = 0;
	q->tail = 0;
}

void QueueEmpty(struct Queue* q)
{
	if (q->count == 0) printf("true\n");
	else printf("false\n");
}

void Enqueue(struct Queue* q, int x)
{
	if (q->count == q->cap)
	{
		int new_cap = q->cap * 2;
		int* new_data = malloc(new_cap * sizeof(int));
		for (int i = 0; i < q->count; ++i)
		{
			int idx = (q->head + i) % q->cap;
			new_data[i] = q->data[idx];
		}
		free(q->data);
		q->data = new_data;
		q->cap = new_cap;
		q->head = 0;
		q->tail = q->count;
	}
	q->data[q->tail] = x;
	++q->tail;
	if (q->tail == q->cap) q->tail = 0;
	++q->count;
}

int Dequeue(struct Queue* q)
{
	int x = q->data[q->head];
	++q->head;
	if (q->head == q->cap) q->head = 0;
	--q->count;
	return x;
}

int main()
{
	struct Queue q;
	InitQueue(&q, BUFER);
	while (1)
	{
		char st[MX];
		fgets(st, MX, stdin);
		st[strcspn(st, "\n")] = '\0';
		char* op = strtok(st, " ");
		if (strcmp(op, "ENQ") == 0) Enqueue(&q, atoi(strtok(NULL, " ")));
		else if (strcmp(op, "DEQ") == 0) printf("%d\n", Dequeue(&q));
		else if (strcmp(op, "EMPTY") == 0) QueueEmpty(&q);
		else if (strcmp(op, "END") == 0) break;
	}
	free(q.data);
}