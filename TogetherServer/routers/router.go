// @APIVersion 1.0.0
// @Title beego Test API
// @Description beego has a very cool tools to autogenerate documents for your API
// @Contact astaxie@gmail.com
// @TermsOfServiceUrl http://beego.me/
// @License Apache 2.0
// @LicenseUrl http://www.apache.org/licenses/LICENSE-2.0.html
package routers

import (
	"TogetherServer/controllers"
	"github.com/astaxie/beego"
	"TogetherServer/controllers/userController"
	"TogetherServer/controllers/msgController"
)

func init() {
	beego.Router("/", &controllers.MainController{})
	ns := beego.NewNamespace("/v1",
		beego.NSNamespace("/object",
			beego.NSInclude(
				&controllers.ObjectController{},
			),
		),
		beego.NSNamespace("/user",
			beego.NSInclude(
				&userController.UserController{},
			),
		),
		beego.NSNamespace("/msg",
			beego.NSInclude(
				&msgController.MsgController{},
			),
		),
	)
	beego.AddNamespace(ns)
}
