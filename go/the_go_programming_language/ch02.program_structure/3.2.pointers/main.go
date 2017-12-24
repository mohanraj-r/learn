package main

import (
	"flag"
	"fmt"
)

func f() *int {
	v := 1
	return &v
}

var n = flag.Bool("n", false, "omit newline")

func main() {
	fmt.Println(n, *n, &n)

	flag.Parse()

	fmt.Println(n, *n, &n)

	fmt.Println(f() == f())
}
