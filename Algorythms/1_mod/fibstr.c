#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

char* fibstr(int n)
{
    if (n == 1) {
        char* st = malloc(2);
        strcpy(st, "a");
        return st;
    }
    if (n == 2) {
        char* st = malloc(2);
        strcpy(st, "b");
        return st;
    }
    char** s = (char**)malloc(n * sizeof(char*));
    s[0] = malloc(2);
    s[1] = malloc(2);
    strcpy(s[0], "a");
    strcpy(s[1], "b");
    for (int i = 2; i < n; ++i)
    {
        s[i] = malloc(strlen(s[i - 1]) + strlen(s[i - 2]) + 1);
        strcpy(s[i], s[i - 2]);
        strcat(s[i], s[i - 1]);
    }
    int ln = strlen(s[n - 1]);
    char* st = (char*)malloc(ln + 1);
    strcpy(st, s[n - 1]);
    for (int i = 0; i < n; ++i)
        free(s[i]);
    free(s);
    return st;
}

int main()
{
    int n;
    scanf("%d", &n);
    char* s = fibstr(n);
    printf("%s\n", s);
    free(s);
    return 0;
}