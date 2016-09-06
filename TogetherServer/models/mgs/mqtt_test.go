package msg

import (
	"testing"
	"github.com/astaxie/beego"
	MQTT "github.com/eclipse/paho.mqtt.golang"
)

func Test_MqttPublish(t *testing.T) {
	t.Log(Publish("my/topic", "hello"))
}

func Test_MqttSubscribe(t *testing.T) {
	t.Log(Subscribe("my/topic", func(client MQTT.Client, msg MQTT.Message) {
		beego.Error("TOPIC: ", msg.Topic())
		beego.Error("MSG: ", string(msg.Payload()))
	}))
}