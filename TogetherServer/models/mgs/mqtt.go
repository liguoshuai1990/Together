package msg

import (
	MQTT "github.com/eclipse/paho.mqtt.golang"
	"time"
	"github.com/astaxie/beego"
)

func clientOptions() *MQTT.ClientOptions {
	ops := MQTT.NewClientOptions().AddBroker("tcp://192.168.56.101:1883")
	ops.SetAutoReconnect(true)
	ops.SetCleanSession(true)
	ops.SetOnConnectHandler(func(MQTT.Client) {
		beego.Error("mqtt connect")
	})
	ops.SetConnectionLostHandler(func(c MQTT.Client, err error) {
		beego.Error("mqtt disconnect ", err)
	})
	return ops
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

func Publish(topic, msgData string) error {
	client, err := publishClient()
	if err != nil {
		return err
	}
	token := client.Publish(topic, byte(0), false, msgData)
	token.Wait()
	client.Disconnect(250)
	return nil
}

func Subscribe(topic string, f MQTT.MessageHandler) error {
	client, err := subscribeClient(f)
	if err != nil {
		return err
	}
	token := client.Subscribe(topic, 0, nil)
	if token.Wait() && token.Error() != nil {
		return token.Error()
	}
	for {
		time.Sleep(3 * time.Second)
		beego.Error(token.Error(), token.Wait(), client.IsConnected())
	}
	if token := client.Unsubscribe(topic); token.Wait() && token.Error() != nil {
		return token.Error()
	}
	client.Disconnect(250)
	return nil
}