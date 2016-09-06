package message

import (
	MQTT "github.com/eclipse/paho.mqtt.golang"
	"github.com/astaxie/beego"
	"time"
)

func clientOptions() *MQTT.ClientOptions {
	return MQTT.NewClientOptions().AddBroker("tcp://192.168.56.101:1883")
}

func publishClient() (MQTT.Client, error) {
	opts := clientOptions()
	opts.SetClientID("TogitherServer")
	client := MQTT.NewClient(opts)
	if token := client.Connect(); token.Wait() && token.Error() != nil {
		return nil, token.Error()
	}
	return client, nil
}

func subscribeClient(f MQTT.MessageHandler) (MQTT.Client, error) {
	opts := clientOptions()
	opts.SetClientID("TogitherServer")
	opts.SetDefaultPublishHandler(f)
	client := MQTT.NewClient(opts)
	if token := client.Connect(); token.Wait() && token.Error() != nil {
		return nil, token.Error()
	}
	return client, nil
}

func MqttPublish(topic, msgData string) error {
	client, err := publishClient()
	if err != nil {
		return err
	}
	token := client.Publish(topic, byte(0), false, msgData)
	token.Wait()
	client.Disconnect(250)
	return nil
}

func MqttSubscribe(topic string, f MQTT.MessageHandler) error {
	client, err := subscribeClient(f)
	if err != nil {
		return err
	}
	if token := client.Subscribe(topic, 0, nil); token.Wait() && token.Error() != nil {
		return token.Error()
	}
	time.Sleep(30 * time.Second)
	if token := client.Unsubscribe(topic); token.Wait() && token.Error() != nil {
		return token.Error()
	}
	client.Disconnect(250)
	return nil
}