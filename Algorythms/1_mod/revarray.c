#include <stdio.h>

void revarray(void* base, size_t nel, size_t width)
{
    char* left = (char*)base;
    char* right = (char*)base + (nel-1) * width;
    while (left < right)
    {
        for (size_t i = 0; i < width; ++i)
        {
            char moment = left[i];
            left[i] = right[i];
            right[i] = moment;
        }
        left += width;
        right -= width;
    }
}

int main(void)
{
    int array[5] = { 1, 2, 3, 4, 5 };
    int* ptr = array;
    revarray((void*)ptr, 5, sizeof(int));
    for (int* ptr = array; ptr <= &array[4]; ptr++) printf("%d ", *ptr);
    return 0;
}