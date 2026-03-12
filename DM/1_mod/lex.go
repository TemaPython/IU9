package main

import (
	"fmt"
	"math/rand"
)

type AssocArray interface {
	Assign(s string, x int)
	Lookup(s string) (x int, exists bool)
}

type SkipNode struct {
	s    string
	x    int
	next []*SkipNode
}

type SkipList struct {
	head     *SkipNode
	maxLevel int
}

type Tree struct {
	s           string
	x           int
	balance     int
	parent      *Tree
	left, right *Tree
}

type TreeAVL struct {
	root *Tree
}

func (p *SkipList) Skip(s string) []*SkipNode {
	m := p.maxLevel
	i := m - 1
	cur := p.head
	ind := make([]*SkipNode, m)
	for i >= 0 {
		for cur.next[i] != nil && cur.next[i].s < s {
			cur = cur.next[i]
		}
		ind[i] = cur
		i--
	}
	return ind
}

func (p *SkipList) Assign(s string, x int) {
	ind := p.Skip(s)
	m := p.maxLevel
	elem := &SkipNode{s, x, make([]*SkipNode, m)}
	r := rand.Intn(33) * 2
	i := 0
	for i < m && r%2 == 0 {
		elem.next[i] = ind[i].next[i]
		ind[i].next[i] = elem
		i++
		r /= 2
	}
	for i < m {
		elem.next[i] = nil
		i++
	}
}

func (p *SkipList) Lookup(s string) (x int, exists bool) {
	ind := p.Skip(s)
	next := ind[0].next[0]
	if next == nil || next.s != s {
		return -1, false
	}
	return next.x, true
}

func makeSkipList() AssocArray {
	maxLevel := 5
	var skipList AssocArray = &SkipList{
		maxLevel: maxLevel,
		head:     &SkipNode{next: make([]*SkipNode, maxLevel)}}
	return skipList
}

func (p *TreeAVL) Assign(s string, x int) {
	y := &Tree{s: s, x: x}
	if p.root == nil {
		p.root = y
	} else {
		z := p.root
		for {
			if s == z.s {
				z.x = y.x
			}
			if s < z.s {
				if z.left == nil {
					z.left = y
					y.parent = z
					break
				}
				z = z.left
			} else {
				if z.right == nil {
					z.right = y
					y.parent = z
					break
				}
				z = z.right
			}
		}
	}

	y.balance = 0
	for {
		r := y.parent
		if r == nil {
			break
		}
		if y == r.left {
			r.balance -= 1
			if r.balance == 0 {
				break
			}
			if r.balance == -2 {
				if y.balance == 1 {
					p.RotateLeft(y)
				}
				p.RotateRight(r)
				break
			}
		} else {
			r.balance += 1
			if r.balance == 0 {
				break
			}
			if r.balance == 2 {
				if y.balance == -1 {
					p.RotateRight(y)
				}
				p.RotateLeft(r)
				break
			}
		}
		y = r
	}
}
func (p *TreeAVL) Lookup(s string) (x int, exists bool) {
	z := p.root
	for z != nil && z.s != s {
		if z.s > s {
			z = z.left
		} else {
			z = z.right
		}
	}
	if z == nil {
		return -1, false
	}
	return z.x, true
}

func (p *TreeAVL) ReplaceNode(a, b *Tree) {
	if a == p.root {
		p.root = b
		if b != nil {
			b.parent = nil
		}
	} else {
		t := a.parent
		if b != nil {
			b.parent = t
		}
		if t.left == a {
			t.left = b
		} else {
			t.right = b
		}
	}
}

func (p *TreeAVL) RotateLeft(a *Tree) {
	b := a.right
	if b == nil {
		return
	}
	p.ReplaceNode(a, b)
	c := b.left
	if c != nil {
		c.parent = a
	}
	a.right = c
	a.parent = b
	b.left = a
	a.balance -= 1
	if b.balance > 0 {
		a.balance -= b.balance
	}
	b.balance -= 1
	if a.balance < 0 {
		b.balance += a.balance
	}
}

func (p *TreeAVL) RotateRight(a *Tree) {
	b := a.left
	if b == nil {
		return
	}
	p.ReplaceNode(a, b)
	c := b.right
	if c != nil {
		c.parent = a
	}
	a.left = c
	a.parent = b
	b.right = a
	a.balance += 1
	if b.balance < 0 {
		a.balance -= b.balance
	}
	b.balance += 1
	if a.balance >= 0 {
		b.balance += a.balance
	}
}

func makeAVL() AssocArray {
	var tree AssocArray = &TreeAVL{}
	return tree
}

func isSpace(s rune) bool {
	return s == rune(' ') || s == rune('\n') ||
		s == rune('\t') || s == rune('\r')
}

func lex(sentence string, array AssocArray) []int {
	i := 1
	res := make([]int, 0)
	runes := make([]rune, 0)
	for _, s := range sentence + " " {
		if isSpace(s) {
			if len(runes) == 0 {
				continue
			}
			x, exists := array.Lookup(string(runes))
			if !exists {
				array.Assign(string(runes), i)
				x = i
				i++
			}
			res = append(res, x)
			runes = runes[:0]
		} else {
			runes = append(runes, s)
		}
	}
	return res
}

func main() {

	skiplist := makeSkipList()
	tree := makeAVL()
	s := "a b a d c a d"
	fmt.Println(lex(s, skiplist))
	fmt.Println(lex(s, tree))
	var x int
	tree.Assign("jr", 97617)
	x, _ = tree.Lookup("jr")
	fmt.Println(x)
	tree.Assign("jr", 97617)
	x, _ = tree.Lookup("jr")
	fmt.Println(x)
	tree.Assign("jr", 97618)
	x, _ = tree.Lookup("jr")
	fmt.Println(x)
}
