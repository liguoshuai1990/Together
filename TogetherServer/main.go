package main

import (
	_ "Together/TogetherServer/docs"
	_ "Together/TogetherServer/routers"
	"github.com/astaxie/beego"
	"Together/TogetherServer/controllers/msgController"
)

func StartRecvMsg() {
	msgController.ResvTogetherMsg()
}

func main() {
	if beego.BConfig.RunMode == "dev" {
		beego.BConfig.WebConfig.DirectoryIndex = true
		beego.BConfig.WebConfig.StaticDir["/swagger"] = "swagger"
	}
	go StartRecvMsg()
	beego.Run()
}
