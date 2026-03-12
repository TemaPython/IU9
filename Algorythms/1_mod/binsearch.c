#include <stdio.h>

unsigned long binsearch(unsigned long nel, int (*compare)(unsigned long i))
{
    int left = 0;
    int right = nel - 1;
    while (left <= right)
    {
        int mid = (left + right) / 2;
        if (compare(mid) == 1) right = mid - 1;
        else if (compare(mid) == -1) left = mid + 1;
        else return mid;
    }
    return nel;
}