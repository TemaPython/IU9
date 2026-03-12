#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>

struct Stack {
    struct Task* data;
    int cap, top;
};

struct Task {
    int low, high;
};

void InitStack(struct Stack* s, int n)
{
    s->data = (struct Task*)malloc(n * sizeof(struct Task));
    s->cap = n;
    s->top = 0;
}

int StackEmpty(struct Stack* s)
{
    return s->top == 0;
}

void Push(struct Stack* s, struct Task x)
{
    if (s->top == s->cap) printf("overflow");
    s->data[s->top] = x;
    ++s->top;
}
struct Task Pop(struct Stack* s)
{
    if (StackEmpty(s)) printf("empty");
    --s->top;
    return s->data[s->top];
}

int Partition(int* arr, int low, int high)
{
    int i = low, j = low;
    while (j < high)
    {
        if (arr[j] < arr[high])
        {
            int prom = arr[i];
            arr[i] = arr[j];
            arr[j] = prom;
            ++i;
        }
        ++j;
    }
    int prom = arr[i];
    arr[i] = arr[high];
    arr[high] = prom;
    return i;
}

void QuickSort(int* arr, int n)
{
    struct Stack s;
    InitStack(&s, 2 * n);
    struct Task x = { 0, n - 1 };
    Push(&s, x);
    while (!StackEmpty(&s))
    {
        x = Pop(&s);
        if (x.high <= x.low) continue;
        int k = Partition(arr, x.low, x.high);
        struct Task first = { x.low, k - 1 };
        struct Task second = { k + 1, x.high };
        Push(&s, second);
        Push(&s, first);
    }
    free(s.data);
}
int main()
{
    int n;
    scanf("%d", &n);
    int* arr = (int*)malloc(n * sizeof(int));
    for (int i = 0; i < n; ++i) scanf("%d", &arr[i]);
    QuickSort(arr, n);
    for (int i = 0; i < n; ++i) printf("%d ", arr[i]);
    free(arr);
    return 0;
}