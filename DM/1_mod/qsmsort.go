package main

import "fmt"

type Task struct {
	start int
	end   int
}

type Stack struct {
	st  []Task
	cnt int
}

func (s *Stack) Init() {
	s = &Stack{make([]Task, 0), 0}
}

func (s *Stack) IsEmpty() bool {
	return s.cnt == 0
}

func (s *Stack) Push(x Task) {
	s.st = append(s.st, x)
	s.cnt++
}

func (s *Stack) Pop() Task {
	if s.cnt != 0 {
		s.cnt--
		item := s.st[s.cnt]
		s.st = s.st[:s.cnt]
		return item
	}
	return Task{}
}

func qssort(n int,
	less func(i, j int) bool,
	swap func(i, j int)) {
	s := Stack{}
	s.Init()
	s.Push(Task{0, n - 1})
	for !s.IsEmpty() {
		curTask := s.Pop()
		low := curTask.start
		i := low
		high := curTask.end
		if low >= high {
			continue
		}
		for i < high {
			if less(i, high) {
				swap(i, low)
				low++
			}
			i++
		}
		swap(low, high)
		s.Push(Task{curTask.start, low - 1})
		s.Push(Task{low + 1, high})
	}
}

func main() {
	fmt.Println("ok")
	s := Stack{}
	s.Init()

}
