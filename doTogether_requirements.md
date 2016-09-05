
交互消息
a. 用户注册消息
   参数：User

b. TogetherRequest消息
   参数： UserInfo TogetherInfo RequestInfo
   
c. TogetherWith消息
   参数： GroupInfo TogetherInfo WithInfo
   
d. TogetherResponce消息
   参数： UserInfo TogetherInfo ResponceInfo


			
1、客户端
   a. 发送用户注册消息
   b. 发送TogetherRequest消息
   c. 接收TogetherWith消息
   d. 发送TogetherResponce消息

2、服务端
   a. 接收用户注册消息
   b. 接收TogetherRequest消息
   c. 发送TogetherWith消息
   d. 接收TogetherResponce消息
   e. 将User加入Group，并通知Group的每一个人


                Together Request
client1 --------------------------------> server



           Together with client1 group?
client2 <-------------------------------- server

           Together with client1 group?
client3 <-------------------------------- server

           Together with client1 group?
client4 <-------------------------------- server



           Together Responce (yes)
client2 --------------------------------> server

           client2 join your group
client1 <-------------------------------- server



           Together Responce (no)
client3 --------------------------------> server



           Together Responce (yes)
client4 --------------------------------> server

           client4 join your group
client1 <-------------------------------- server

           client4 join your group
client2 <-------------------------------- server


   
后续扩展：



1、舒服的人机交互界面

2、play together 扩展为 do anything together，比如顺风车等

2、do together群发限制条件

3、do together带有筛选功能

4、do together带有屏蔽功能

5、聊天功能，或者接入微信等

6、第三方软件嵌入功能
