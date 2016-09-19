package msg

import (
	"github.com/astaxie/beego"
)

/* Together请求消息： 用户发送到服务端的消息，请求加入组一起玩
 *  消息携带内容:
 *  1、用户信息
 *  2、组信息 (首次发送携带组topic，其余为组ID)
 *  3、组成员
 */
func SendTogetherRequest() error {
	return SendMsg("Together/with", "hello")
}
func ResvTogetherRequest(f MsgCallback) error {
	return ListenMsg("Together/with", f)
}

/* Group请求消息:  服务端发给用户的消息，请求用户加入组一起玩
 *  消息携带内容:
 *  1、组信息 (携带组topic、组ID)
 *  2、组成员
 */
func SendGroupRequest(groupInfo string) error {
	return SendMsg("Together.ResvGroupMsg", "hello " + groupInfo)
}
func ResvGroupRequest(f MsgCallback) error {
	return ListenMsg("Together.ResvGroupMsg", f)
}

func GetMsgDriverClient() MsgDriver {
	switch beego.AppConfig.String("msgDriver") {
		case "Gcm": {
			return new(Fcm)
		}
		case "Mqtt": {
			return new(Mqtt)
		}
		case "BaiduPush": {
			return new(BaiduPush)
		}
		default: {
			beego.Error("Mast Config msgDriver.")
		}
	}
	return new(BaiduPush)
}

func SendMsg(topic, msgData string) error {
	msgClient := GetMsgDriverClient()
	return msgClient.SendMsg(topic, msgData)
}

func ListenMsg(listerId string, f MsgCallback) error {
	msgClient := GetMsgDriverClient()
	return msgClient.ListenMsg(listerId, f)
}