package org.guohai.android.cta.bll;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.guohai.android.cta.R;
import org.guohai.android.cta.model.ParseGpsInfo;
import org.guohai.android.cta.utility.ErrorInfoParse;
import org.guohai.android.cta.utility.HttpRest;
import org.guohai.android.cta.utility.Tools;
import org.guohai.android.cta.model.GsmCellLocationInfo;
import org.guohai.android.cta.model.LocationInfo;
import org.guohai.android.cta.model.MessageInfo;
import org.guohai.android.cta.model.ResultInfo;

import android.util.Log;
import android.content.Context;

public class PostSiteData {
	private static final String TAG="CoordinateTalk";

	/**
	 * 反向解析地址
	 * @param parm 位置GPS信息
	 * @Context context 
	 * @return 返回解析后的地址
	 */
	public String GetParse(ParseGpsInfo parm,Context context){		
		
		List <NameValuePair> dataList = new ArrayList <NameValuePair>();
		//拼装参数
		dataList.add(new BasicNameValuePair("SendAccount",parm.SendAccount));
		dataList.add(new BasicNameValuePair("Latitude", Double.toString(parm.Latitude)));
		dataList.add(new BasicNameValuePair("Longitude",Double.toString(parm.Longitude)));
		
		ResultInfo data = HttpRest.HttpPostClient("http://android.guohai.org/api/?fun=parse", dataList);
		
		
		if(0<=data.State){
			return data.Message;
		}else{
			return ErrorInfoParse.GetErrorMessage(context, data.State);
		}		
	}
	
	
	/**
	 * 通过基站解析坐标
	 * @param parm 基站信息
	 * @Context context 
	 * @return
	 */
	public LocationInfo FromGSMGetLocation(GsmCellLocationInfo parm,Context context)
	{
		LocationInfo location = new LocationInfo();		
		
		Log.i(TAG,"call_id="+parm.CellId+",location_area_code="+parm.LocationAreaCode+",mobile_country_code="+parm.MobileCountryCode+",mobile_network_code="+parm.MobileNetworkCode);
		List <NameValuePair> dataList = new ArrayList <NameValuePair>();
		dataList.add(new BasicNameValuePair("cid",Integer.toString(parm.CellId)));
		dataList.add(new BasicNameValuePair("lac", Integer.toString(parm.LocationAreaCode)));
		dataList.add(new BasicNameValuePair("mcc",Integer.toString(parm.MobileCountryCode)));
		dataList.add(new BasicNameValuePair("mnc",Integer.toString(parm.MobileNetworkCode)));
		dataList.add(new BasicNameValuePair("imei",Tools.GetPhoneImei(context)));
		
		ResultInfo data = HttpRest.HttpPostClient("http://android.guohai.org/api/?fun=gsm", dataList);
		
		if(data == null)
		{
			return location;
		}
		
		String[] sArray = data.Message.split(",");
		location.Latitude = Double.parseDouble(sArray[0]);
		location.Longitude = Double.parseDouble(sArray[1]);
		return location;	
	}
	
	
	/**
	 * 增加消息
	 * @param message
	 * @return
	 */
	public ResultInfo AddMessage(MessageInfo message,Context context)
	{		
		List <NameValuePair> dataList = new ArrayList <NameValuePair>();
		dataList.add(new BasicNameValuePair("Note",message.Note));
		dataList.add(new BasicNameValuePair("SendAccount",message.SendAccount));
		dataList.add(new BasicNameValuePair("Latitude", Double.toString(message.Latitude)));
		dataList.add(new BasicNameValuePair("Longitude",Double.toString(message.Longitude)));
		dataList.add(new BasicNameValuePair("Altitude",Double.toString(message.Altitude)));
		
		ResultInfo data = HttpRest.HttpPostClient("http://android.guohai.org/api/?fun=add", dataList);
		Log.i(TAG,"["+data.State+data.Message+"]");
		data.Message=context.getString(R.string.error_info_send_message_succeed);
		return data;

	}
	
	
}
