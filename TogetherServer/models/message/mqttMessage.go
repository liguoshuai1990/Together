package message

import (
	MQTT "github.com/eclipse/paho.mqtt.golang"
	"strconv"
	"github.com/astaxie/beego"
	"time"
)

//define a function for the default message handler
var f MQTT.MessageHandler = func(client MQTT.Client, msg MQTT.Message) {
	beego.Error("TOPIC: ", msg.Topic())
	beego.Error("MSG: ", string(msg.Payload()))
}

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

func subscribeClient() (MQTT.Client, error) {
	opts := clientOptions()
	opts.SetClientID("go-simple")
	opts.SetDefaultPublishHandler(f)
	client := MQTT.NewClient(opts)
	if token := client.Connect(); token.Wait() && token.Error() != nil {
		return nil, token.Error()
	}
	return client, nil
}

func MqttPublish() error {
	client, err := publishClient()
	if err != nil {
		return err
	}
	for i := 0; i < 10; i++ {
		token := client.Publish("test/topic", byte(0), false, "hello world~!" + strconv.Itoa(i))
		token.Wait()
	}
	client.Disconnect(250)
	return nil
}

func MqttSubscribe() error {
	client, err := subscribeClient()
	if err != nil {
		return err
	}
	//subscribe to the topic /go-mqtt/sample and request messages to be delivered
	//at a maximum qos of zero, wait for the receipt to confirm the subscription
	if token := client.Subscribe("go-mqtt/sample", 0, nil); token.Wait() && token.Error() != nil {
		return token.Error()
	}
	//client.Disconnect(250)
	time.Sleep(30 * time.Second)
	//unsubscribe from /go-mqtt/sample
	if token := client.Unsubscribe("go-mqtt/sample"); token.Wait() && token.Error() != nil {
		return token.Error()
	}
	client.Disconnect(250)
	return nil
}