package main

import (
	"image"
	"image/color"
	"image/gif"
	"io"
	"math"
	"math/rand"
	"os"
)

var palette = []color.Color{color.RGBA{51, 153, 102, 1}, color.Black}

const (
	//whiteIndex = 0
	blackIndex = 1
)

// Writes out a lissajous gif
func gifs(out io.Writer) {

	const (
		numFrames = 64
		size      = 100
		cycles    = 5
		res       = 0.001
		delay     = 8
	)

	freq := rand.Float64() * 3.0
	anim := gif.GIF{LoopCount: numFrames}

	for i, phase := 0.0, 0.0; i < numFrames; i, phase = i+1, phase+0.1 {
		rect := image.Rect(0, 0, 2*size+1, 2*size+1)
		img := image.NewPaletted(rect, palette)

		for t := 0.0; t < cycles*2*math.Pi; t += res {
			x := math.Sin(t)
			y := math.Sin(t*freq + phase)
			img.SetColorIndex(size+int(x*size+0.5), size+int(y*size+0.5), blackIndex)
		}

		anim.Delay = append(anim.Delay, delay)
		anim.Image = append(anim.Image, img)
	}

	gif.EncodeAll(out, &anim)

}

func main() {
	gifs(os.Stdout)
}
