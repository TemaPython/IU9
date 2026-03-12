package main

import (
	"fmt"
)

type rational struct {
	num int
	dem int
}

func abs(a int) int {
	if a >= 0 {
		return a
	}
	return -a
}

func gcd(a, b int) int {
	a, b = abs(a), abs(b)
	for {
		if a == 0 || b == 0 {
			return a + b
		} else if a > b {
			a %= b
		} else {
			b %= a
		}
	}
}

func swap(st1, st2, ln int) {
	for i := 0; i < ln; i++ {
		matrix[st1*(n+1)+i], matrix[st2*(n+1)+i] = matrix[st2*(n+1)+i], matrix[st1*(n+1)+i]
	}
}

func optimize(a rational) rational {
	var nod int = gcd(a.num, a.dem)
	return rational{a.num / nod, a.dem / nod}
}

func ratPlus(a, b rational) rational {
	var nok int = (a.dem * b.dem) / gcd(a.dem, b.dem)
	return optimize(rational{a.num*(nok/a.dem) + b.num*
		(nok/b.dem), nok})
}

func ratMinus(a, b rational) rational {
	return ratPlus(a, rational{-b.num, b.dem})
}

func ratDel(a, b rational) rational {
	if a.num > 0 {
		return ratMult(rational{a.dem, a.num}, b)
	}
	return ratMult(rational{-a.dem, -a.num}, b)
}

func ratMult(a, b rational) rational {
	return optimize(rational{a.num * b.num, a.dem * b.dem})
}

func gaussMinus(row1, row2 int) bool {
	var a rational
	var b rational
	for i := row1; i < n; i++ {
		a = matrix[row1*(n+1)+i]
		if a.num == 0 {
			continue
		}
		b = matrix[row2*(n+1)+i]
		break
	}
	if a.num == 0 {
		return true
	}
	var k rational = ratDel(a, b)
	for j := 0; j < n+1; j++ {
		matrix[row2*(n+1)+j] = ratMinus(matrix[row2*(n+1)+j], ratMult(matrix[row1*(n+1)+j], k))
	}
	return false
}

var n int
var matrix []rational

func main() {
	fmt.Scan(&n)
	matrix = make([]rational, n*(n+1))
	var x int
	for i := 0; i < n; i++ {
		for j := 0; j < n+1; j++ {
			fmt.Scan(&x)
			matrix[i*(n+1)+j] = rational{x, 1}
		}
	}

	var row int
	for i := 0; i < n-1; i++ {
		row = i

		for ; ; row++ {
			if matrix[row*(n+1)+row].num != 0 {
				swap(i, row, n+1)
				break
			}
		}

		for j := i + 1; j < n; j++ {
			err := gaussMinus(i, j)
			if err {
				fmt.Println("No solution")
				return
			}
		}
	}

	for i := n - 1; i >= 1; i-- {
		for j := i - 1; j >= 0; j-- {
			err := gaussMinus(i, j)
			if err {
				fmt.Println("No solution")
				return
			}
		}
	}

	var y rational
	for i := 0; i < n; i++ {
		for j := 0; j < n; j++ {
			y = matrix[i*(n+1)+j]
			if y.num != 0 {
				y = ratDel(y, matrix[i*(n+1)+n])
				fmt.Printf("%d/%d ", y.num, y.dem)
				break
			} else if j == n-1 {
				fmt.Println("No solution")
				return
			}
		}
		fmt.Print("\n")
	}

}
