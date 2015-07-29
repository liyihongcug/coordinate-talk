# 通过GPS坐标解析物理地址 #
POST URL

http://api.zfj.mobi/?gsm-GpsToAddress

POST参数

latitude=维度

longitude=经度

imei=终端串号

返回结果

{"state":"1","message":"\u4e2d\u56fd\u5317\u4eac\u5e02\u6d77\u6dc0\u533a\u4e2d\u5173\u6751\u4e1c\u8def15\u53f7 \u90ae\u653f\u7f16\u7801: 100083"}

# 通过GSM基站信息解析出gps坐标 #
POST URL

http://api.zfj.mobi/?gsm-GsmToGps

POST参数

cid=CellId

lac=LocationAreaCode

mcc=MobileCountryCode

mnc=MobileNetworkCode

imei=终端串号

返回结果

{"state":"1","message":{"latitude":39.991962,"longitude":116.19586}}

# 获取当前坐标附近的消息 #
POST URL

http://api.zfj.mobi/?message-GetLocalMessage

POST参数

latitude=维度

longitude=经度

imei=终端串号

返回结果

{"state":"1","message":[{"uid":"1","nick\_name":"\u5c0f\u6d77","info":"12545"},{"uid":"1","nick\_name":"\u5c0f\u6d77","info":"11111223"}]}
//最多返回15条消息