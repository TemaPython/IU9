#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>

struct Elem {
    struct Elem* prev, * next;
    int v;
};

void InitDoubleLinkedList(struct Elem* l)
{
    l->prev = l;
    l->next = l;
}

void InsertAfter(struct Elem* x, struct Elem* y)
{
    struct Elem* z = x->next;
    x->next = y;
    y->prev = x;
    y->next = z;
    z->prev = y;
}

void Insert(struct Elem* x, struct Elem* y)
{
    x->prev->next = x->next;
    x->next->prev = x->prev;
    InsertAfter(y, x);
}

void InsertionSort(struct Elem* l)
{
    struct Elem* x = l->next->next;
    while (x != l)
    {
        struct Elem* z = x->next;
        struct Elem* y = x->prev;
        while (y != l && y->v > x->v) y = y->prev;
        Insert(x, y);
        x = z;
    }
}

int main()
{
    int n;
    scanf("%d", &n);
    struct Elem l;
    InitDoubleLinkedList(&l);

    for (int i = 0; i < n; ++i)
    {
        int x;
        scanf("%d", &x);
        struct Elem* el = malloc(sizeof(struct Elem));
        el->v = x;
        InsertAfter(l.prev, el);
    }

    InsertionSort(&l);

    struct Elem* cur = l.next;
    while (cur != &l) {
        printf("%d ", cur->v);
        cur = cur->next;
    }

    cur = l.next;
    while (cur != &l) {
        struct Elem* next = cur->next;
        free(cur);
        cur = next;
    }
}