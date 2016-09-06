package routers

import (
	"github.com/astaxie/beego"
)

func init() {

	beego.GlobalControllerRouter["Together/TogetherServer/controllers/msgController:MsgController"] = append(beego.GlobalControllerRouter["Together/TogetherServer/controllers/msgController:MsgController"],
		beego.ControllerComments{
			Method: "Post",
			Router: `/start`,
			AllowHTTPMethods: []string{"post"},
			Params: nil})

}
