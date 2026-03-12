#include <stdio.h>

unsigned long peak(unsigned long nel,
		int (*less)(unsigned long i, unsigned long j))
{
	if (nel == 1) return 0;
	for (unsigned long i = 1; i + 1 < nel; ++i)
	{
		if (!less(i, i - 1) && !less(i, i + 1)) return i;
	}
	if (!less(0, 1)) return 0;
	if (!less(nel - 1, nel - 2)) return nel - 1;
	return 0;
}
