/**
 * 此程序为开源程序，遵循GPLv3版本发布，并受其保护。
 * (GPLv3 http://www.gnu.org/licenses/gpl.html)
 * Copyright 2011 by H!Guo
 */
package org.guohai.android.cta.bll;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.guohai.android.cta.model.LocationInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.*;
import android.location.*;
import android.os.*;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.widget.Toast;
/**
 * Get GPS Corrdinate 
 * @author H!Guo 
 */
public class GPSUtilities {
	
	private Context context;
	public double Latitude;//维度
	public double Longitude;//经度
	public double Altitude;//
	private LocationManager locationManager;
	
	/** 构造函数 */
	public GPSUtilities(Context parm){
		context = parm;
	}
	
	/** 检查设备是否开启 */
	public boolean GPSDeviceIsOpen(){
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		if(locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)){
			return true;
		}
		return false;
	}
	
	/** 开始监听 */
	public boolean getLocation(){
		//取位置管理服务
		
		//locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		if(null == locationManager){
			return false;
		}
		//查找服务信息

		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
		return true;
	}
	
	/** 暂停监听卫星坐标 */
	public void pauseGetLocation(){
		if(null != locationManager){
			locationManager.removeUpdates(mLocationListener);
		}
	}
	
	/** 实现GPS监听 */
	public LocationListener mLocationListener = new LocationListener(){
		/** GPS数据被更新 */
		public void onLocationChanged(Location location){
			Latitude = location.getLatitude();
			Longitude = location.getLongitude();
			Altitude= location.getAltitude();
		}
		
		public void onProviderDisabled(String provider){
			
		}
		
		public void onProviderEnabled(String provider){
			
		}
		
		public void onStatusChanged(String provider,int status,Bundle extras){
			switch(status){
			case LocationProvider.AVAILABLE:
				Log.d("GPS","AVAILABLE");
				break;
			}
		}
	};

	/** 通过手机定位 */
	public LocationInfo CellularPhone(){
		LocationInfo location = new LocationInfo();
		TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		GsmCellLocation gcl = (GsmCellLocation) tm.getCellLocation();
		int cid=gcl.getCid();
		int lac= gcl.getLac();
		int mcc = Integer.valueOf(tm.getNetworkOperator().substring(0, 3));
		int mnc = Integer.valueOf(tm.getNetworkOperator().substring(3, 5));
		Toast.makeText(context, "call_id="+cid+",location_area_code="+lac+",mobile_country_code="+mcc+",mobile_network_code="+mnc, Toast.LENGTH_SHORT).show();
		JSONObject holder = new JSONObject();
		/*
		try {
			holder.put("version", "1.1.0");
			holder.put("host", "maps.google.com");
			holder.put("request_address", true);
			JSONArray array = new JSONArray();
			JSONObject data = new JSONObject();
			data.put("cell_id", cid);
			data.put("location_area_code", lac);// 4474
			data.put("mobile_country_code", mcc);// 460
			data.put("mobile_network_code", mnc);// 0
			array.put(data);
			holder.put("cell_towers", array);
			
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost("http://www.google.com/loc/json");
			
			StringEntity se = new StringEntity(holder.toString());
			post.setEntity(se);

			HttpResponse resp = client.execute(post);

			HttpEntity entity = resp.getEntity();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
			StringBuffer sb = new StringBuffer();
			String result  = br.readLine();
			
			while(result !=null){
				sb.append(result);
				result = br.readLine();
			}
			//Toast.makeText(context, sb.toString(), Toast.LENGTH_SHORT).show();

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		return location;
	}
}
