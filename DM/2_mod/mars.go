package main

import (
	"bufio"
	"fmt"
	"os"
)

func getIdV(n int) []int {
	x := make([]int, 0)
	for i := 0; n > 0; n >>= 1 {
		if n&1 == 1 {
			x = append(x, i)
		}
		i += 1
	}
	return x
}

type matrix [][]int

func (m matrix) isClique(x []int) bool {
	for _, i := range x {
		for _, j := range x {
			if m[i][j] == 0 {
				return false
			}
		}
	}
	return true
}

func lex(a, b []int) bool {
	for i := 0; i < len(a); i++ {
		if a[i] < b[i] {
			return true
		} else if a[i] > b[i] {
			return false
		}
	}
	return false
}

func main() {
	var (
		n int
		u string
	)
	reader := bufio.NewReader(os.Stdin)
	fmt.Fscan(reader, &n)
	var m matrix = make([][]int, n)
	for i := 0; i < n; i++ {
		m[i] = make([]int, n)
		for j := 0; j < n; j++ {
			fmt.Fscan(reader, &u)
			if u == "-" {
				m[i][j] = 1
			} else {
				m[i][j] = 0
			}
		}
	}
	maxC := make([]int, 0)
	for i := 1; i < (1 << n); i++ {
		a, b := getIdV(i), getIdV((1<<n)-1-i)

		if len(a) <= n/2 && m.isClique(a) && m.isClique(b) {
			if len(a) > len(maxC) || (len(a) == len(maxC) && lex(a, maxC)) {
				maxC = a
			}
		}
	}
	if len(maxC) == 0 {
		fmt.Println("No solution")
		return
	}
	for _, i := range maxC {
		fmt.Print(i+1, " ")
	}
}
