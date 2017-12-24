// outputs cmd line args
package main

import (
	"fmt"
	"os"
	"strings"
)

func main() {
	// 1.
	var s, sep string
	for i := 1; i < len(os.Args); i++ {
		s += sep + os.Args[i]
		sep = " "
	}

	// 2. looping over args directly
	for _, arg := range os.Args[1:] {
		s += sep + arg
		sep = " "
	}

	// 3.
	s = strings.Join(os.Args[1:], " ")

	fmt.Println(s)
}
