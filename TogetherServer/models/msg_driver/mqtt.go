package msg_dirver

import (
	MQTT "github.com/eclipse/paho.mqtt.golang"
	//"time"
	"github.com/astaxie/beego"
)

func clientOptions() *MQTT.ClientOptions {
	opts := MQTT.NewClientOptions().AddBroker(beego.AppConfig.String("mqttServer"))
	opts.SetAutoReconnect(true)
	opts.SetCleanSession(true)
	return opts
}

func publishClient() (MQTT.Client, error) {
	opts := clientOptions()
	opts.SetClientID("TogitherClient")
	client := MQTT.NewClient(opts)
	if token := client.Connect(); token.Wait() && token.Error() != nil {
		return nil, token.Error()
	}
	return client, nil
}

func subscribeClient(topic string, f MQTT.MessageHandler) (MQTT.Client, error) {
	opts := clientOptions()
	opts.SetClientID("TogitherServer")
	opts.SetDefaultPublishHandler(f)
	opts.SetOnConnectHandler(func(client MQTT.Client) {
		beego.Info("mqtt connect")
		subscribe(topic, client)
	})
	opts.SetConnectionLostHandler(func(client MQTT.Client, err error) {
		beego.Error("mqtt disconnect ", err)
	})
	client := MQTT.NewClient(opts)
	if token := client.Connect(); token.Wait() && token.Error() != nil {
		return nil, token.Error()
	}
	return client, nil
}

func subscribe(topic string, client MQTT.Client) error {
	token := client.Subscribe(topic, 0, nil)
	if token.Wait() && token.Error() != nil {
		return token.Error()
	}
	return nil
}

func MqttSubscribe(topic string, f MQTT.MessageHandler) error {
	client, err := subscribeClient(topic, f)
	if err != nil {
		return err
	}
	return subscribe(topic, client)
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

type Mqtt struct {
}

func (m *Mqtt)SendMsg(clientId string, MsgData string) error {
	return MqttPublish("Together/Group", MsgData)
}
func (m *Mqtt)ListenMsg(f MsgCallback) error {
	return MqttSubscribe("Together/with", func(client MQTT.Client, msg MQTT.Message) {
		f(string(msg.Payload()))
	})
}