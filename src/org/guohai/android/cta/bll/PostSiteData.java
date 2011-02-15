package org.guohai.android.cta.bll;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.guohai.android.cta.model.ParseGpsInfo;
import org.guohai.android.cta.utility.HttpRest;
import org.guohai.android.cta.model.GsmCellLocationInfo;
import org.guohai.android.cta.model.LocationInfo;
import org.guohai.android.cta.model.MessageInfo;
import android.util.Log;
import android.content.Context;

public class PostSiteData {
	private static final String TAG="CoordinateTalk";
	/** 反向解析 地址  */
	public String GetParse(ParseGpsInfo parm){		
		
		List <NameValuePair> dataList = new ArrayList <NameValuePair>();
		dataList.add(new BasicNameValuePair("SendAccount",parm.SendAccount));
		dataList.add(new BasicNameValuePair("Latitude", Double.toString(parm.Latitude)));
		dataList.add(new BasicNameValuePair("Longitude",Double.toString(parm.Longitude)));
		
		String data = HttpRest.HttpPostClient("http://android.guohai.org/api/?fun=parse", dataList);
		
		if(data == null)
		{
			return "失败了over";
		}
		return data;		
	}
	
	
	/**
	 * POST数据
	 * @param parm
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
		dataList.add(new BasicNameValuePair("imei",""));
		
		String data = HttpRest.HttpPostClient("http://android.guohai.org/api/?fun=gsm", dataList);
		
		if(data == null)
		{
			return location;
		}
		
		String[] sArray = data.split(",");
		location.Latitude = Double.parseDouble(sArray[0]);
		location.Longitude = Double.parseDouble(sArray[1]);
		return location;	
	}
	
	
	/** 增加一   */
	public String AddMessage(MessageInfo message)
	{		
		List <NameValuePair> dataList = new ArrayList <NameValuePair>();
		dataList.add(new BasicNameValuePair("Note",message.Note));
		dataList.add(new BasicNameValuePair("SendAccount",message.SendAccount));
		dataList.add(new BasicNameValuePair("Latitude", Double.toString(message.Latitude)));
		dataList.add(new BasicNameValuePair("Longitude",Double.toString(message.Longitude)));
		dataList.add(new BasicNameValuePair("Altitude",Double.toString(message.Altitude)));
		
		String data = HttpRest.HttpPostClient("http://android.guohai.org/api/?fun=add", dataList);		
		if(data == null)
		{
			return "失败了over";
		}
		return "200";
	}
	
	
}
