#include <stdio.h>
#include <math.h>
#include <stdbool.h>
#define N 46341


int main()
{
    int x;
    scanf("%d", &x);

    int numbers[N] = {0};

    for (int i = 2; i * i <= N; ++i)
        if (numbers[i] == 0)
            for (int k = i * i; k <= N; k += i)
                numbers[k] = 1; 

    if (x == 0) {
        printf("Ошибка\n");
        return 0;
    }
    if (x == 1 || x == -1) {
        printf("1\n");
        return 0;
    }
    if (x == -2147483648) {
        printf("2\n");
        return 0;
    }

    x = (x > 0)? x: -x; 

    int is_prime = 0;
    if (x <= N) {
        is_prime = (numbers[x] == 0) ? 1 : 0;
    }
    for (int i = 2; i <= N && i * i <= x; i++) {
        if (numbers[i] == 0 && x % i == 0) {
            is_prime = 1;
            break;
        }
    }


    if (is_prime == 0) 
    {
        printf("%d", x);
        return 0;
    }
          
    int sopr = x;
    int max_pr = 1;
    for (int i = 2; i*i <= sopr; ++i)
        if ((numbers[i] == 0) && (sopr % i == 0))
        {
            max_pr = i;
            while (sopr % i == 0)
                sopr /= i;
        }

    if (sopr > 1) max_pr = sopr;
    printf("%d", max_pr);
    return 0;
}