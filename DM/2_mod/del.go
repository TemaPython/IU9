package main

import (
	"fmt"
	"math"
	"strconv"
	"strings"
)

func сontains(slice []string, x string) bool {
	for _, v := range slice {
		if v == x {
			return true
		}
	}
	return false
}

type Graph struct {
	v []string
	e []string
}

func (g *Graph) AddV(x string) {
	if сontains(g.v, x) {
		panic(x + " already in graph")
	}
	g.v = append(g.v, x)
}

func (g *Graph) AddE(x, y string) {
	if !сontains(g.v, x) || !сontains(g.v, y) {
		panic(x + " or " + y + " not all vertices in the graph. no edge added")
	}
	edge, rev := x+"--"+y, y+"--"+x
	if сontains(g.e, edge) || сontains(g.e, rev) {
		panic(edge + " already in graph")
	}
	g.e = append(g.e, edge)
}

func (g *Graph) Print() {
	s := "graph {\n    "
	s += strings.Join(g.v, "\n    ")
	s += "\n    "
	s += strings.Join(g.e, "\n    ")
	s += "\n}"
	fmt.Println(s)
}

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

	g := &Graph{}
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
