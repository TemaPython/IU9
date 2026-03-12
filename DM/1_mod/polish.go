package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"unicode"
)

var rune_slice []rune

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

func oper(rune_slice []rune, start int) (res, end int) {
	a, b_start := get(rune_slice, start)
	if a == "+" || a == "-" || a == "*" {
		b, c_start := oper(rune_slice, b_start)
		c, c_end := oper(rune_slice, c_start)
		if a == "+" {
			return b + c, c_end
		} else if a == "-" {
			return b - c, c_end
		} else if a == "*" {
			return b * c, c_end
		}
	}
	number, _ := strconv.Atoi(a)
	return number, b_start
}

func main() {
	reader := bufio.NewReader(os.Stdin)
	line, _ := reader.ReadString('\n')
	rune_slice := []rune(line)
	res, _ := oper(rune_slice, 0)
	fmt.Println(res)
}
