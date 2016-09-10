package main

import (
	_ "TogetherServer/docs"
	_ "TogetherServer/routers"
	"github.com/astaxie/beego"
	"TogetherServer/controllers/msgController"
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
