# 错误码返回 #
//发送消息成功

{"state":1003,"message":"SendMessageSucceed"}

//解析基站信息成功

{"state":1002,"message":"ParseBaseStationSucceed"}

//解析坐标成功

{"state":1001,"message":"ParseCoordinateSucceed"}

//成功

{"state":1,"message":"succeed"}

//POST数据时应用程序内产生错误

{"state":-1001,"message":"posterror"}

//坐标解析失败

{"state":-1002,"message":"CoordinateParseFailure"}

//坐标解析失败

{"state":-1003,"message":"SendMessageFailure"}

# 错误码格式 #
格式：{"state":1,"message":"succeed"}
> JOSN格式封装，
state为状态数据，正数成功，负数失败。
message为描述信息。