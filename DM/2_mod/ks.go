package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

func contains[T int | string](slice []T, x T) bool {
	for _, v := range slice {
		if v == x {
			return true
		}
	}
	return false
}

type Graph struct {
	v    []string
	e    []string
	vIdx map[string]int
	eIdx map[string][]int
}

func (g *Graph) AddV(x string) {
	if _, ok := g.vIdx[x]; ok {
		panic(x + " already in graph")
	}
	g.vIdx[x] = len(g.v)
	g.v = append(g.v, x)
}

func (g *Graph) AddColorV(x, c string) {
	i, ok := g.vIdx[x]
	if !ok {
		return
	}
	g.v[i] = x + " [color = " + c + "]"
}

func (g *Graph) AddE(x, y string) {
	if _, ok := g.vIdx[x]; !ok {
		panic(x + " not in graph")
	}
	if _, ok := g.vIdx[y]; !ok {
		panic(y + " not in graph")
	}
	edge := x + " -- " + y
	idx := len(g.e)
	g.eIdx[edge] = append(g.eIdx[edge], idx)
	g.e = append(g.e, edge)
}

func (g *Graph) AddColorE(x, y string, c string) {
	edge, rev := x+" -- "+y, y+" -- "+x
	for _, i := range g.eIdx[edge] {
		g.e[i] = edge + " [color = " + c + "]"
	}
	for _, i := range g.eIdx[rev] {
		g.e[i] = rev + " [color = " + c + "]"
	}
}

func (g *Graph) Print() {
	s := "graph {\n    "
	s += strings.Join(g.v, "\n    ")
	s += "\n    "
	s += strings.Join(g.e, "\n    ")
	s += "\n}"
	fmt.Println(s)
}

type IncidenceList struct {
	inc map[int][]int
}

func (l *IncidenceList) AddV(x int) {
	_, ok := l.inc[x]
	if ok {
		panic("vertex already in list")
	}
	l.inc[x] = make([]int, 0)
}

func (l *IncidenceList) AddE(u, v int) {
	x1, ok1 := l.inc[u]
	x2, ok2 := l.inc[v]
	if !ok1 || !ok2 {
		panic("vertex not in list")
	}
	l.inc[u] = append(x1, v)
	l.inc[v] = append(x2, u)
}

type Sets struct {
	y map[int]*Set
}

type Set struct {
	parent int
	countV int
	countE int
	minV   int
}

func (s *Sets) AddV(x int) {
	_, ok := s.y[x]
	if ok {
		panic("vertex already in list")
	}
	s.y[x] = &Set{parent: x, countV: 1, countE: 0, minV: x}
}

func (s *Sets) Find(x int) int {
	i, ok := s.y[x]
	if !ok {
		panic("vertex not in list")
	}
	if i.parent == x {
		return x
	}
	root := s.Find(i.parent)
	i.parent = root
	return root
}

func (s *Sets) Union(x, y int) {
	i := s.Find(x)
	j := s.Find(y)
	k, _ := s.y[i]
	f, _ := s.y[j]
	if s.Find(x) == s.Find(y) {
		f.countE++
		return
	}
	if k.countV < f.countV {
		k, f = f, k
		i, j = j, i
	}
	k.parent = j
	f.countV += k.countV
	f.countE += k.countE + 1
	if k.minV < f.minV {
		f.minV = k.minV
	}
}

func main() {
	var (
		n int
		m int
		u int
		v int
		l *IncidenceList = &IncidenceList{make(map[int][]int)}
		s *Sets          = &Sets{make(map[int]*Set)}
		g *Graph         = &Graph{vIdx: make(map[string]int),
			eIdx: make(map[string][]int)}
	)
	reader := bufio.NewReader(os.Stdin)
	fmt.Fscan(reader, &n)
	fmt.Fscan(reader, &m)
	for i := 0; i < n; i++ {
		l.AddV(i)
		s.AddV(i)
		g.AddV(strconv.Itoa(i))
	}
	for i := 0; i < m; i++ {
		fmt.Fscan(reader, &u, &v)
		l.AddE(u, v)
		g.AddE(strconv.Itoa(u), strconv.Itoa(v))
		s.Union(u, v)
	}

	max := *s.y[s.Find(0)]
	for i := 1; i < n; i++ {
		k := *s.y[s.Find(i)]
		if k.parent == max.parent {
			continue
		}
		if k.countV > max.countV {
			max = k
		}
		if k.countV == max.countV {
			if k.countE > max.countE {
				max = k
			}
			if k.countE == max.countE {
				if k.minV < max.minV {
					max = k
				}
			}
		}
	}
	p := max.parent
	for i := 0; i < n; i++ {
		if s.Find(i) == p {
			g.AddColorV(strconv.Itoa(i), "red")
		}
	}
	for u, neighbors := range l.inc {
		for _, v := range neighbors {
			if u < v && s.Find(u) == p && s.Find(v) == p {
				g.AddColorE(strconv.Itoa(u), strconv.Itoa(v), "red")
			}
		}
	}
	g.Print()
}
