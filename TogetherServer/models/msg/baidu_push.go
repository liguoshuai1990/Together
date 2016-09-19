package msg

import (
	"github.com/ChangjunZhao/BaiduPushSDK-golang"
	"encoding/json"
	"fmt"
	"github.com/astaxie/beego"
	"time"
)

type BaiduPush struct {}

func (m *BaiduPush)SendMsg(clientId string, MsgData string) error {
	// 新建客户端
	client := push.NewClient(beego.AppConfig.String("baiduApiKey"), beego.AppConfig.String("baiduSecretKey"))
	// 推送通知到指定客户端
	notification := &push.AndroidNotification{Title: "测试通知",
		Description: "测试通知描述",
		NotificationBuilderId: 0,
		NotificationBasicStyle: 7,
	}
	message, _ := json.Marshal(notification)
	request := &push.PushMsgToAllRequest{
		MsgType: 1,
		Message: string(message),
		MsgExpires: 18000,
		SendTime: time.Now().Unix() + 70,
	}
	response, err := client.PushMsgToAllDevice(*request)
	beego.Error("Recv PushMsg Responce", response, err)
	if err == nil {
		fmt.Println(response.MsgId)
	} else {
		fmt.Println(err)
	}
	return nil
}
func (m *BaiduPush)ListenMsg(listerId string, f MsgCallback) error {
	return nil
}