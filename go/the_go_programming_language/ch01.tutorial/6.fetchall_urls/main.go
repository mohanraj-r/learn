package main

import (
	"fmt"
	"io"
	"io/ioutil"
	"net/http"
	"os"
	"strings"
	"time"
)

// fetch given url and return time elapsed
func fetch(url string, ch chan<- string) {
	start := time.Now()

	if !strings.HasPrefix(url, "http://") {
		url = "http://" + url
	}

	resp, err := http.Get(url)
	if err != nil {
		ch <- fmt.Sprint(err)
		return
	}

	nbytes, err := io.Copy(ioutil.Discard, resp.Body)
	resp.Body.Close()
	if err != nil {
		ch <- fmt.Sprintf("err while reading url %s: %v", url, err)
		return
	}

	elapsed := time.Since(start).Seconds()
	ch <- fmt.Sprintf("%.2fs %7d bytes %s %s", elapsed, nbytes, resp.Status, url)
}

// fetch given urls using go routines
func fetchall(urls []string) {
	ch := make(chan string)
	for _, url := range urls {
		go fetch(url, ch)
	}

	for range urls {
		fmt.Println(<-ch)
	}
}

func main() {
	fetchall(os.Args[1:])
}
