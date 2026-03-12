#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define MX 20
#define MX_STACK 1000

struct Stack {
    int* data;
    int cap, top;
};

void InitStack(struct Stack* s, int n)
{
    s->data = (int*)malloc(n * sizeof(int));
    s->cap = n;
    s->top = 0;
}

int StackEmpty(struct Stack* s)
{
    return s->top == 0;
}

void Push(struct Stack* s, int x)
{
    if (s->top == s->cap)
    {
        int new_cap = s->cap * 2;
        int* prom = realloc(s->data, new_cap * sizeof(int));
        s->data = prom;
        s->cap = new_cap;
    }
    s->data[s->top] = x;
    ++s->top;
}
int Pop(struct Stack* s)
{
    if (StackEmpty(s)) printf("empty");
    --s->top;
    return s->data[s->top];
}

int main()
{
    struct Stack s;
    InitStack(&s, MX_STACK);
    while (1)
    {
        char st[MX];
        fgets(st, MX, stdin);
        st[strcspn(st, "\n")] = '\0';
        char* op = strtok(st, " ");
        int x = 0;
        if (strcmp(op, "CONST") == 0) Push(&s, atoi(strtok(NULL, " ")));
        else if (strcmp(op, "ADD") == 0) Push(&s, Pop(&s) + Pop(&s));
        else if (strcmp(op, "SUB") == 0) Push(&s, Pop(&s) - Pop(&s));
        else if (strcmp(op, "MUL") == 0) Push(&s, Pop(&s) * Pop(&s));
        else if (strcmp(op, "DIV") == 0) Push(&s, Pop(&s) / Pop(&s));
        else if (strcmp(op, "MAX") == 0)
        {
            int a, b;
            a = Pop(&s);
            b = Pop(&s);
            Push(&s, (a > b) ? a : b);
        }
        else if (strcmp(op, "MIN") == 0)
        {
            int a, b;
            a = Pop(&s);
            b = Pop(&s);
            Push(&s, (a < b) ? a : b);
        }
        else if (strcmp(op, "NEG") == 0) Push(&s, -Pop(&s));
        else if (strcmp(op, "DUP") == 0)
        {
            int a;
            a = Pop(&s);
            Push(&s, a);
            Push(&s, a);
        }
        else if (strcmp(op, "SWAP") == 0)
        {
            int a, b;
            a = Pop(&s);
            b = Pop(&s);
            Push(&s, a);
            Push(&s, b);
        }
        else if (strcmp(op, "END") == 0)
        {
            printf("%d", Pop(&s));
            break;
        }
    }
    free(s.data);
    return 0;
}