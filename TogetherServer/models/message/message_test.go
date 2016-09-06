package Message

import "testing"

func Test_MqttPublish(t *testing.T) {
	t.Log(MqttPublish())
}

func Test_MqttSubscribe(t *testing.T) {
	t.Log(MqttSubscribe())
}