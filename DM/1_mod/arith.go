package main

import (
	"fmt"
	"os"
	"strconv"
)

type Lexem struct {
	Tag
	Image string
}

type Tag int

const (
	END    Tag = -1
	ERROR  Tag = 1 << iota // Неправильная лексема
	NUMBER                 // Целое число
	VAR                    // Имя переменной
	PLUS                   // Знак +
	MINUS                  // Знак -
	MUL                    // Знак *
	DIV                    // Знак /
	LPAREN                 // Левая круглая скобка
	RPAREN                 // Правая круглая скобка
)

func isSpace(s rune) bool {
	return s == rune(' ') || s == rune('\n') ||
		s == rune('\t') || s == rune('\r')
}

func isDig(r rune) bool {
	return r >= '0' && r <= '9'
}

func isLet(r rune) bool {
	return (r >= 'A' && r <= 'Z') || (r >= 'a' && r <= 'z')
}

func isNum(r string) bool {
	for _, s := range []rune(r) {
		if !isDig(s) {
			return false
		}
	}
	return true
}

func lexer(expr string, lexems chan Lexem) {
	defer close(lexems)
	runes := make([]rune, 0)
	var x string
	for _, s := range expr + " " {
		if isDig(s) || isLet(s) {
			runes = append(runes, s)
			continue
		} else if len(runes) != 0 {
			x = string(runes)
			if isNum(x) {
				lexems <- Lexem{NUMBER, x}
			} else {
				lexems <- Lexem{VAR, x}
			}
			runes = runes[:0]
		}
		if isSpace(s) {
			continue
		}
		switch s {
		case rune('+'):
			lexems <- Lexem{PLUS, "+"}
		case rune('-'):
			lexems <- Lexem{MINUS, "-"}
		case rune('*'):
			lexems <- Lexem{MUL, "*"}
		case rune('/'):
			lexems <- Lexem{DIV, "/"}
		case rune('('):
			lexems <- Lexem{LPAREN, "("}
		case rune(')'):
			lexems <- Lexem{RPAREN, ")"}
		default:
			lexems <- Lexem{ERROR, string(s)}
		}
	}
}

type Token struct {
	lexems chan Lexem
	cur    Lexem
	vars   map[string]int
}

func (t *Token) nextToken() {
	tok, opened := <-t.lexems
	if !opened {
		t.cur = Lexem{END, ""}
		return
	}
	t.cur = tok
}

func parser(expr string) int {
	lexemCh := make(chan Lexem)
	go lexer(expr, lexemCh)
	t := &Token{lexems: lexemCh, vars: make(map[string]int)}
	t.nextToken()

	return E(t)
}

func E(t *Token) int {
	switch t.cur.Tag {
	case NUMBER:
		fallthrough
	case VAR:
		fallthrough
	case LPAREN:
		fallthrough
	case MINUS:
		return EPrime(T(t), t)
	default:
		panic("error")
	}
}

func EPrime(left int, t *Token) int {
	switch t.cur.Tag {
	case PLUS:
		t.nextToken()
		return EPrime(left+T(t), t)
	case MINUS:
		t.nextToken()
		return EPrime(left-T(t), t)
	case RPAREN:
		fallthrough
	case END:
		return left
	default:
		panic("error")
	}
}

func T(t *Token) int {
	switch t.cur.Tag {
	case MINUS:
		fallthrough
	case VAR:
		fallthrough
	case LPAREN:
		fallthrough
	case NUMBER:
		return TPrime(F(t), t)
	default:
		panic("error")
	}
}

func TPrime(left int, t *Token) int {
	switch t.cur.Tag {
	case MUL:
		t.nextToken()
		return TPrime(left*F(t), t)
	case DIV:
		t.nextToken()
		return TPrime(left/F(t), t)
	case PLUS:
		fallthrough
	case MINUS:
		fallthrough
	case RPAREN:
		fallthrough
	case END:
		return left
	default:
		panic("error")
	}
}

func F(t *Token) int {
	switch t.cur.Tag {
	case MINUS:
		t.nextToken()
		return -F(t)
	case NUMBER:
		number, _ := strconv.Atoi(t.cur.Image)
		t.nextToken()
		return number
	case VAR:
		x, ok := t.vars[t.cur.Image]
		if !ok {
			fmt.Fscan(os.Stdin, &x)
			t.vars[t.cur.Image] = x
		}
		t.nextToken()
		return x
	case LPAREN:
		t.nextToken()
		sub := E(t)
		if t.cur.Tag != RPAREN {
			panic("error")
		}
		t.nextToken()
		return sub
	default:
		panic("error")
	}
}

func main() {
	expr := os.Args[1]
	defer func() {
		if r := recover(); r != nil {
			fmt.Println("error")
		}
	}()
	res := parser(expr)
	fmt.Println(res)
}
