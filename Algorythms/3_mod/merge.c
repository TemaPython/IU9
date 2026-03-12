#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct Item {
	int index;
	int k;
	int v;
};

struct PriorityQueue {
	struct Item* heap;
	int cap;
	int count;
};

void Swap(struct Item* a, struct Item* b)
{
	struct Item prom = *a;
	*a = *b;
	*b = prom;
}

void Heapify(int i, int n, struct Item* heap)
{
	while (1)
	{
		int l = 2 * i + 1;
		int r = l + 1;
		int j = i;

		if (l < n && heap[l].k < heap[j].k) j = l;
		if (r < n && heap[r].k < heap[j].k) j = r;

		if (i == j) break;
		Swap(&heap[i], &heap[j]);
		i = j;
	}
}

void InitPriorityQueue(struct PriorityQueue* q, int n)
{
	q->heap = (struct Item*)malloc(n * sizeof(struct Item));
	q->cap = n;
	q->count = 0;
}

int QueueEmpty(struct PriorityQueue* q)
{
	return q->count == 0;
}

void Insert(struct PriorityQueue* q, struct Item ptr)
{
	int i = q->count;
	if (i == q->cap) {
		exit(1);
	}
	q->count = i + 1;
	q->heap[i] = ptr;

	while (i > 0 && q->heap[(i - 1) / 2].k > q->heap[i].k)
	{
		int p = (i - 1) / 2;
		Swap(&q->heap[p], &q->heap[i]);
		i = p;
	}
}

struct Item ExtractMin(struct PriorityQueue* q)
{
	struct Item ptr = q->heap[0];
	--q->count;
	if (q->count > 0)
	{
		q->heap[0] = q->heap[q->count];
		Heapify(0, q->count, q->heap);
	}
	return ptr;
}

int main()
{
	int k;
	scanf("%d", &k);
	getchar();
	char buf[1000000];
	fgets(buf, sizeof(buf), stdin);
	buf[strcspn(buf, "\n")] = '\0';

	int n = 0;
	int* ni = (int*)malloc(k * sizeof(int));
	char* tok = strtok(buf, " ");
	for (int i = 0; i < k; ++i)
	{
		ni[i] = atoi(tok);
		tok = strtok(NULL, " ");
		n += ni[i];
	}

	int** a = (int**)malloc(k * sizeof(int*));
	for (int i = 0; i < k; ++i)
	{
		a[i] = (int*)malloc(ni[i] * sizeof(int));
		fgets(buf, sizeof(buf), stdin);
		buf[strcspn(buf, "\n")] = '\0';
		char* p = strtok(buf, " ");
		for (int j = 0; j < ni[i]; ++j)
		{
			a[i][j] = atoi(p);
			p = strtok(NULL, " ");
		}
	}

	struct PriorityQueue q;
	InitPriorityQueue(&q, k);

	for (int i = 0; i < k; ++i)
	{
		struct Item t = { i, a[i][0], 0 };
		Insert(&q, t);
	}

	int* res = (int*)malloc(n * sizeof(int));
	int pos = 0;

	while (!QueueEmpty(&q))
	{
		struct Item m = ExtractMin(&q);
		res[pos] = m.k;
		++pos;

		if (m.v + 1 < ni[m.index])
		{
			struct Item next = { m.index, a[m.index][m.v + 1], m.v + 1 };
			Insert(&q, next);
		}
	}

	for (int i = 0; i < n; ++i)
	{
		printf("%d ", res[i]);
	}

	for (int i = 0; i < k; ++i) free(a[i]);
	free(a);
	free(ni);
	free(res);
	free(q.heap);
	return 0;
}
