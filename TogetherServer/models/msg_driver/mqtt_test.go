package msg_dirver

import (
	"testing"
	"github.com/astaxie/beego"
	MQTT "github.com/eclipse/paho.mqtt.golang"
)

func Test_MqttPublish(t *testing.T) {
	t.Log(MqttPublish("my/topic", "hello"))
}

func Test_MqttSubscribe(t *testing.T) {
	t.Log(MqttSubscribe("my/topic", func(client MQTT.Client, msg MQTT.Message) {
		beego.Error("TOPIC: ", msg.Topic())
		beego.Error("MSG: ", string(msg.Payload()))
	}))
}