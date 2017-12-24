package main

import (
	"fmt"
	"io"
	"net/http"
	"os"
	"strings"
)

// fetch given urls and print body
func fetch(urls []string) {
	for _, url := range urls {
		if !strings.HasPrefix(url, "http://") {
			url = "http://" + url
		}

		resp, err := http.Get(url)
		if err != nil {
			fmt.Println(err)
			os.Exit(1)
		}

		//b, err := ioutil.ReadAll(resp.Body)
		//resp.Body.Close()
		//if err != nil {
		//	fmt.Println(err)
		//}
		//
		//fmt.Printf("%s", b)

		_, err = io.Copy(os.Stdout, resp.Body)
		resp.Body.Close()
		fmt.Println(resp.Status)
		if err != nil {
			fmt.Println(err)
			os.Exit(1)
		}
	}
}

func main() {
	fetch(os.Args[1:])
}
