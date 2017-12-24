// prints duplicated lines and count
package main

import (
	"bufio"
	"fmt"
	"os"
)

var counts = make(map[string]int)

// count duplicate lines from given source
func countLines(f *os.File, counts map[string]int) {
	input := bufio.NewScanner(f)
	for input.Scan() {
		counts[input.Text()]++
	}

	for line, count := range counts {
		if count > 1 {
			fmt.Printf("%d \t %s \n", count, line)
		}
	}
}

// output duplicate lines and count
func dup1() {
	countLines(os.Stdin, counts)
}

// output duplicate lines and count
//	reading from files provided as cli args, defaulting to stdin if no args are given
func dup2() {
	files := os.Args[1:]
	if len(files) == 0 {
		dup1()
	} else {
		for _, fname := range files {
			f, err := os.Open(fname)

			if err != nil {
				fmt.Fprintf(os.Stderr, "3.dup: %v \n", err)
				continue
			}

			countLines(f, counts)
			f.Close()
		}
	}

}

func main() {
	//dup1()
	dup2()
}
