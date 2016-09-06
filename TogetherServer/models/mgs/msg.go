package msg

import (
	MQTT "github.com/eclipse/paho.mqtt.golang"
)

type ResvMsgFunc func(topic, msg string)

/* Together请求消息： 用户发送到服务端的消息，请求加入组一起玩
 *  消息携带内容:
 *  1、用户信息
 *  2、组信息 (首次发送携带组topic，其余为组ID)
 *  3、组成员
 */
func SendTogetherRequest() error {
	return SendMessage("Together/with", "hello")
}
func ResvTogetherRequest(f ResvMsgFunc) error {
	return ResvMessage("Together/with", f)
}

/* Group请求消息:  服务端发给用户的消息，请求用户加入组一起玩
 *  消息携带内容:
 *  1、组信息 (携带组topic、组ID)
 *  2、组成员
 */
func SendGroupRequest() error {
	return SendMessage("Group/Jion", "hello")
}
func ResvGroupRequest(f ResvMsgFunc) error {
	return ResvMessage("Group/Jion", f)
}

func SendMessage(topic, msgData string) error {
	return Publish(topic, msgData)
}

func ResvMessage(topic string, f ResvMsgFunc) error {
	return Subscribe(topic, func(client MQTT.Client, msg MQTT.Message) {
		f(msg.Topic(), string(msg.Payload()))
	})
}