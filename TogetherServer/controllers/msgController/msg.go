package msgController

import (
	"github.com/astaxie/beego"
	"Together/TogetherServer/models/mgs"
	"time"
)


// Operations about Msg
type MsgController struct {
	beego.Controller
}

func resvTogetherMessage(topic, msg string)  {
	/* 添加用户信息 */

	/* 添加组信息(包括组成员) */

	beego.Error("TOPIC: ", topic)
	beego.Error("MSG: ", msg)
}


func resvGroupMessage(topic, msg string)  {
	beego.Error("TOPIC: ", topic)
	beego.Error("MSG: ", msg)
}

func ResvTogetherMsg() {
	for {
		err := msg.ResvTogetherRequest(resvTogetherMessage)
		if err == nil {
			break
		}
		beego.Error("ResvTogetherMsg: ", err)
		time.Sleep(3 * time.Second)
	}
}
