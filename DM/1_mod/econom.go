package main

import (
	"bufio"
	"fmt"
	"os"
	"unicode"
)

var rune_slice []rune
var polish map[string]int

func get(rune_slice []rune, start int) (sim string, end int) {
	for i := start; i < len(rune_slice); i++ {
		var k rune = rune_slice[i]
		if k == '(' || k == ')' || unicode.IsSpace(k) {
			continue
		}
		return string(k), i + 1
	}
	return "", -1
}

func oper(rune_slice []rune, start, count int) (res string, cnt, end int) {
	a, b_start := get(rune_slice, start)
	if a == "$" || a == "@" || a == "#" {
		b, c_start, count := oper(rune_slice, b_start, count)
		c, c_end, count := oper(rune_slice, c_start, count)
		ar := "(" + a + b + c + ")"
		_, ok := polish[ar]
		if !ok {
			polish[ar] = 1
			return ar, c_end, count + 1
		}
		return ar, c_end, count
	}
	return a, b_start, count
}

func main() {
	reader := bufio.NewReader(os.Stdin)
	line, _ := reader.ReadString('\n')
	rune_slice := []rune(line)
	polish = map[string]int{}
	_, _, count := oper(rune_slice, 0, 0)
	fmt.Println(count)
}
