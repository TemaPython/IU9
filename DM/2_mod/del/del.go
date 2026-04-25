package main

import (
	"2_mod/dot"
	"fmt"
	"math"
	"strconv"
)

func main() {
	var n int
	fmt.Scan(&n)
	dels1 := make([]int, 0)
	dels2 := make([]int, 0)
	for i := 1; i <= int(math.Round(math.Sqrt(float64(n))))+1; i++ {
		if n%i == 0 && i <= n/i {
			if i < n/i {
				dels2 = append(dels2, n/i)
			}
			dels1 = append([]int{i}, dels1...)
		}
	}
	dels2 = append(dels2, dels1...)

	fmt.Println(dels2)

	g := &dot.Graph{}
	for _, i := range dels2 {
		g.AddV(strconv.Itoa(i))
	}

	for i := 0; i < len(dels2); i++ {
		for j := i + 1; j < len(dels2); j++ {
			if dels2[i]%dels2[j] == 0 {
				flag := true
				for _, k := range dels2[i+1 : j] {
					if k%dels2[j] == 0 && dels2[i]%k == 0 {
						flag = false
					}
				}
				if flag {
					g.AddE(strconv.Itoa(dels2[i]), strconv.Itoa(dels2[j]))
				}
			}
		}
	}
	g.Print()
}
