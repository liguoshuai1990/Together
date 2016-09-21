package models

import (
	"github.com/astaxie/beego"
	"TogetherServer/models/msg_driver"
	"strings"
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
func ResvTogetherRequest(f msg_dirver.MsgCallback) error {
	return ListenMsg(f)
}

/* Group请求消息:  服务端发给用户的消息，请求用户加入组一起玩
 *  消息携带内容:
 *  1、组信息 (携带组topic、组ID)
 *  2、组成员
 */
func SendGroupRequest(groupInfo string) error {
	return SendMsg("Together.ResvGroupMsg", "hello " + groupInfo)
}
func ResvGroupRequest(f msg_dirver.MsgCallback) error {
	return ListenMsg(f)
}

func GetMsgDriverClient() []msg_dirver.MsgDriver {
	drivers := strings.Split(beego.AppConfig.String("msgDriver"), ",")
	msgDrivers := make([]msg_dirver.MsgDriver, 0)
	for _, driver := range drivers {
		switch driver {
			case "Fcm": {
				msgDrivers = append(msgDrivers, new(msg_dirver.Fcm))
				break
			}
			case "Mqtt": {
				msgDrivers = append(msgDrivers, new(msg_dirver.Mqtt))
				break
			}
		}
	}
	return msgDrivers
}

func SendMsg(topic, msgData string) error {
	for _, msgClient := range GetMsgDriverClient() {
		msgClient.SendMsg(topic, msgData)
	}
	return nil
}

func ListenMsg(f msg_dirver.MsgCallback) error {
	for _, msgClient := range GetMsgDriverClient() {
		msgClient.ListenMsg(f)
	}
	return nil
}