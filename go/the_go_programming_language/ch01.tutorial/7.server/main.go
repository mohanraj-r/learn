package main

import (
	"fmt"
	"log"
	"net/http"
	"sync"
)

var count int
var mu sync.RWMutex

func counter(w http.ResponseWriter, _ *http.Request) {
	mu.RLock()
	fmt.Fprintf(w, "Count : %d\n", count)
	mu.Unlock()
}

func handler(w http.ResponseWriter, r *http.Request) {
	mu.Lock()
	count++
	mu.Unlock()

	fmt.Fprintf(w, "URL Path = %q\n", r.URL.Path)
}

func main() {
	http.HandleFunc("/", handler)
	http.HandleFunc("/count", counter)
	log.Fatal(http.ListenAndServe("localhost:8080", nil))
}
