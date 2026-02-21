package main

import (
	"encoding/json"
	"fmt"
	"log"
	"net/http"
	"os"

	"github.com/go-git/go-git/v5"
)

func main() {
	handleRequests()
}

func handleRequests() {
	http.HandleFunc("/clone", clone)
	log.Fatal(http.ListenAndServe(":4000", nil))
}

type response struct {
	Message string
}

func clone(w http.ResponseWriter, r *http.Request) {
	var result, err = git.PlainClone("./tmp", false, &git.CloneOptions{
		URL:      "https://github.com/dkakunsi/java_lab",
		Progress: os.Stdout,
	})

	var resp response
	if err != nil {
		log.Fatal(err)

		resp = response{
			Message: "Clone is successful",
		}
	} else {
		log.Default().Print(result.TreeObjects())

		resp = response{
			Message: "Clone is successful",
		}
	}

	var respJson, _ = json.Marshal(resp)
	fmt.Fprint(w, string(respJson))
	fmt.Println("Endpoint Hit: homePage")

}
