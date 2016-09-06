
# 交互消息
## Together请求消息
  用户发送到服务端的消息，请求加入组一起玩
###  消息携带内容:
 * 用户信息
 * 组信息 (首次发送携带组topic，其余为组ID)
 * 组成员

## Group请求消息:  
   服务端发给用户的消息，请求用户加入组一起玩
###  消息携带内容:
 * 组信息 (携带组topic、组ID)
 * 组成员


                     Group
 server   <--------------------------------   client

                    Together
 client   -------------------------------->   server
 



# 后续扩展：
 * 舒服的人机交互界面
 * together 扩展为 evething together，比如顺风车等
 * together群发限制条件
 * together带有筛选功能
 * together带有屏蔽功能
 * 聊天功能，或者接入微信等
 * 第三方软件嵌入功能

