package msg_dirver

import (
	fcm "github.com/google/go-gcm"
	"github.com/astaxie/beego"
	"fmt"
)

func getSenderId() string {
	return beego.AppConfig.String("fcmSenderId")
}

func fcmListen(h fcm.MessageHandler) error {
	err := fcm.Listen(getSenderId(), beego.AppConfig.String("fcmApiKey"), h, nil)
	if err != nil {
		beego.Error("fcm.Listen Error.", err)
	}
	return err
}

func FcmSubscribe(h fcm.MessageHandler) error {
	go fcmListen(h)
	beego.Info("Fcm Subscribe ")
	return nil
}

func FcmPublish(clientId string, MsgData string) error {
	m := fcm.XmppMessage{}
	m.Data = fcm.Data{"messageData": MsgData}
	m.To = "/topics/" + clientId
	_, _, err := fcm.SendXmpp(getSenderId(), beego.AppConfig.String("fcmApiKey"), m)
	return err
}

type Fcm struct {}

func (m *Fcm)SendMsg(clientId string, MsgData string) error {
	return FcmPublish(clientId, MsgData)
}
func (m *Fcm)ListenMsg(f MsgCallback) error {
	return FcmSubscribe(func (cm fcm.CcsMessage) error{
		beego.Info("Received Message: %+v", cm)
		data, ok := cm.Data["my_message"]
		if ok && data != nil {
			f(fmt.Sprint(data))
		}
		return nil
	})
}