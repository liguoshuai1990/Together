package msg

import (
	fcm "github.com/google/go-gcm"
	"github.com/astaxie/beego"
)

func getSenderId() string {
	return beego.AppConfig.String("fcmSenderId")
}

func FcmSubscribe(h fcm.MessageHandler) error {
	return fcm.Listen(getSenderId(), beego.AppConfig.String("fcmApiKey"), h, nil)
}

func FcmPublish(clientId string, MsgData string) error {
	m := fcm.XmppMessage{}
	m.Data = fcm.Data{"data": MsgData}
	m.To = clientId
	_, _, err := fcm.SendXmpp(getSenderId(), beego.AppConfig.String("fcmApiKey"), m)
	return err
}

type Fcm struct {}

func (m *Fcm)SendMsg(clientId string, MsgData string) error {
	return FcmPublish(clientId, MsgData)
}
func (m *Fcm)ListenMsg(listerId string, f MsgCallback) error {
	return FcmSubscribe(func (cm fcm.CcsMessage) error{
		beego.Error("Received Message: %+v", cm)
		return nil
	})
}