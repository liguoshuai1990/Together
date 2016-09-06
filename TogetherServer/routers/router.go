// @APIVersion 1.0.0
// @Title beego Test API
// @Description beego has a very cool tools to autogenerate documents for your API
// @Contact astaxie@gmail.com
// @TermsOfServiceUrl http://beego.me/
// @License Apache 2.0
// @LicenseUrl http://www.apache.org/licenses/LICENSE-2.0.html
package routers

import (
	"Together/TogetherServer/controllers"
	"github.com/astaxie/beego"
	"Together/TogetherServer/controllers/userController"
	"Together/TogetherServer/controllers/msgController"
)

func init() {
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
