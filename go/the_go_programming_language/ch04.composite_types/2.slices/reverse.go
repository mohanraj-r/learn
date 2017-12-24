package main

import "fmt"

// reverse reverses a slice of ints in place.
func reverse(s []int) {
	for i, j := 0, len(s)-1; i < j; i, j = i+1, j-1 {
		s[i], s[j] = s[j], s[i]
	}
}

func main() {
	for _, s := range [][]int{{0}, {0,1}, {0,1,2},  {0,1,2,3}}{
		fmt.Print(s)
		reverse(s)
		fmt.Println(s)
	}
}
