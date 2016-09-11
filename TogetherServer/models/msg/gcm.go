package msg

import (
	"github.com/google/go-gcm"
	"github.com/astaxie/beego"
)

func GcmSubscribe(h gcm.MessageHandler) error {
	return gcm.Listen(beego.AppConfig.String("gcmSenderId"), beego.AppConfig.String("gcmApiKey"), h, nil)
}

func GcmPublish(clientId string, MsgData string) error {
	m := gcm.XmppMessage{}
	m.Data = gcm.Data{"data": MsgData}
	m.To = clientId
	_, _, err := gcm.SendXmpp(beego.AppConfig.String("gcmSenderId"), beego.AppConfig.String("gcmApiKey"), m)
	return err
}

type Gcm struct {

}

func (m *Gcm)SendMsg(clientId string, MsgData string) error {
	return GcmPublish(clientId, MsgData)
}
func (m *Gcm)ListenMsg(listerId string, f MsgCallback) error {
	return GcmSubscribe(func (cm gcm.CcsMessage) error{
		beego.Error("Received Message: %+v", cm)
		return nil
	})
}