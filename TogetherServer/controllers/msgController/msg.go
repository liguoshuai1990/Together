package msgController

import (
	"github.com/astaxie/beego"
	"Together/TogetherServer/models/mgs"
	"net/http"
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

// @Title start resv together message
// @Description start resv together message
// @Success 200 body is success
// @Failure 403 body
// @router /start [post]
func (m *MsgController) Post() {
	err := msg.ResvTogetherRequest(resvTogetherMessage)
	if err != nil {
		http.Error(m.Ctx.ResponseWriter, err.Error(), 500)
	} else {
		m.Data["json"] = map[string]string{"topic": "Together/with"}
	}
	m.ServeJSON()
}