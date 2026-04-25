package dot

import (
	"fmt"
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
