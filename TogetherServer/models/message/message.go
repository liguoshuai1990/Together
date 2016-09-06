package message

import (
	"github.com/astaxie/beego"
	MQTT "github.com/eclipse/paho.mqtt.golang"
)

/* Together请求消息： 用户发送到服务端的消息，请求加入组一起玩
 *  消息携带内容:
 *  1、用户信息
 *  2、组信息 (首次发送携带组topic，其余为组ID)
 *  3、组成员
 */
func SendTogetherRequest()  {
	
}
func ResvTogetherRequest()  {

}

/* Group请求消息:  服务端发给用户的消息，请求用户加入组一起玩
 *  消息携带内容:
 *  1、组信息 (携带组topic、组ID)
 *  2、组成员
 */
func SendGroupRequest()  {
	"go-mqtt/sample"
}
func resvGroupMessage(topic, msg string)  {
	beego.Error("TOPIC: ", topic)
	beego.Error("MSG: ", msg)
}
func ResvGroupRequest()  {
	ResvMessage("Together/with", resvGroupMessage)
}

func SendMessage(topic, msgData string)  {
	MqttPublish(topic, msgData)
}

type ResvMsgFunc func(topic, msg string)

func ResvMessage(topic string, f ResvMsgFunc)  {
	MqttSubscribe(topic, func(client MQTT.Client, msg MQTT.Message) {
		f(msg.Topic(), string(msg.Payload()))
	})
}