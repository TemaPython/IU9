package main

import (
	"fmt"
)

func encode(utf32 []rune) []byte {
	var utf8 []byte = make([]byte, 0)
	for _, i := range utf32 {
		x := encodeOne(i)
		for j := len(x) - 1; j >= 0; j-- {
			utf8 = append(utf8, x[j])
		}
	}
	return utf8
}

func decode(utf8 []byte) []rune {
	var bytes int = 0
	var utf32 []rune = make([]rune, 0)
	var x rune = -1
	for _, i := range utf8 {
		if bytes <= 0 {
			if x != -1 {
				utf32 = append(utf32, x)
			}
			x = 0
			bytes = checkBytes(i)
			if bytes == 0 {
				x += rune(takeNBits(rune(i), 7-bytes))
			} else {
				x = x + (rune(takeNBits(rune(i), 7-bytes)) << ((bytes - 1) * 6))
			}
		} else {
			x = x + (rune(takeNBits(rune(i), 6)) << ((bytes - 1) * 6))
		}
		bytes--
	}
	if x != -1 {
		utf32 = append(utf32, x)
	}
	return utf32
}

func checkBytes(a byte) int {
	var i int
	for i = 7; i > 1; i-- {
		if !(a&(1<<i) == (1 << i)) {
			break
		}
	}
	return 7 - i
}

func takeNBits(a rune, n int) byte {
	var x byte = 0
	for i := 0; i < n; i++ {
		x += byte(a & (1 << i))
	}
	return x

}

func encodeOne(a rune) []byte {
	var bits int
	var start rune = 1
	for bits = 1; bits < 36; bits++ {
		start = start << 1
		if !(a > start) {
			break
		}
	}
	var bytes []byte = make([]byte, 0)
	if bits <= 7 {
		return []byte{byte(a)}
	} else {
		for {
			if bits > 8-(len(bytes)+2) {
				bytes = append(bytes, takeNBits(a, 6)+(1<<7))
				a = a >> 6
				bits -= 6
				continue
			}
			var x byte = 0
			for i := 0; i < len(bytes)+1; i++ {
				x += 1 << (8 - (i + 1))
			}
			bytes = append(bytes, takeNBits(a, bits)+x)
			return bytes
		}
	}

}

func main() {
	fmt.Println("test")
	input1 := ([]rune)("≥Ο的G₿😅З因√😂")
	input2 := ([]byte)("≥Ο的G₿😅З因√😂")
	fmt.Println(encode(input1))
	fmt.Println(decode(input2))
	fmt.Println(input1)
}
