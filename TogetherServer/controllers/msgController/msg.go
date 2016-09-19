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

type TogetherReqest struct {

}

// @Title createUser
// @Description create users
// @Param	body		body 	TogetherReqest	true		"body for TogetherReqest content"
// @Success 200 {string} Success
// @Failure 403 body is empty
// @router /sendTogetherReqest [post]
func (m *MsgController) Post() {
	/* 接收 TogetherReqest 消息 */
	//togetherReqest := TogetherReqest{}
	//json.Unmarshal(m.Ctx.Input.RequestBody, &togetherReqest)

	beego.Error("TogetherReqest: Recv Post Message", string(m.Ctx.Input.RequestBody))

	/* 发送 GroupWith 消息 */
	msg.SendGroupRequest(string(m.Ctx.Input.RequestBody))

	m.Data["json"] = map[string]string{"Success": string(m.Ctx.Input.RequestBody)}
	m.ServeJSON()
}