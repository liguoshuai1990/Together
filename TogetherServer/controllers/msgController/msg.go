package msgController

import (
	"github.com/astaxie/beego"
	"TogetherServer/models/msg"
	"time"
)


// Operations about Msg
type MsgController struct {
	beego.Controller
}

func resvTogetherMessage(msgData string)  {
	/* 添加用户信息 */

	/* 添加组信息(包括组成员) */
	beego.Error("MSG: ", msgData)


	/* 询问用户是否加入组 */
	if msgData != "" {
		msg.SendGroupRequest(msgData)
	}

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
