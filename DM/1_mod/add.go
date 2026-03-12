package main

import (
	"fmt"
)

func max(n, m int) int {
	if n >= m {
		return n
	}
	return m
}

func add(a, b []int32, p int32) []int32 {
	var lna int = len(a)
	var lnb int = len(b)
	var ln int = max(lna, lnb)
	var c []int32 = make([]int32, ln, ln+1)
	var x int32
	var d int32 = 0
	var i int
	for i = 0; i < ln; i++ {
		x = 0
		if i >= lna {
			x += 0
		} else {
			x += a[i]
		}
		if i >= lnb {
			x += 0
		} else {
			x += b[i]
		}
		x += d
		if x >= p {
			d = 1
			c[i] = x - p
		} else {
			c[i] = x
			d = 0
		}
	}
	if d > 0 {
		c = append(c, d)
	}
	return c
}

func main() {
	var a []int32 = []int32{0, 1, 1}
	var b []int32 = []int32{0, 1}
	var p int32 = 2
	for _, i := range add(a, b, p) {
		fmt.Println(i)
	}
}
