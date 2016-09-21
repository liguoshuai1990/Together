package msg_dirver

/* 消息中间件接口
 * 发送消息: (接收者， 消息体)
 * 侦听消息: (侦听者， 接收消息后的处理函数)
 */

type MsgCallback func(MsgData string)

type MsgDriver interface {
	SendMsg(clientId string, MsgData string) error
	ListenMsg(f MsgCallback) error
}
