/**
 * 此程序为开源程序，遵循GPLv3版本发布，并受其保护。
 * (GPLv3 http://www.gnu.org/licenses/gpl.html)
 * Copyright 2011 by H!Guo
 */
package org.guohai.android.cta.bll;
/*
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
*/
import java.util.List;

import android.content.*;
import android.location.*;
import android.os.*;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.widget.Toast;

import org.guohai.android.cta.model.GsmCellLocationInfo;
import org.guohai.android.cta.model.LocationInfo;
/**
 * Get GPS Corrdinate 
 * @author H!Guo 
 */
public class GPSUtilities {
	private static final String TAG="CoordinateTalk";
	
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
			Latitude = location.getLatitude()==0?Latitude:location.getLatitude();
			Longitude = location.getLongitude()==0?Longitude:location.getLongitude();
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
		GsmCellLocationInfo gcli = new GsmCellLocationInfo();
		
		gcli.CellId=gcl.getCid();//取小区号 
		gcli.LocationAreaCode= gcl.getLac();//取LAC
		gcli.MobileCountryCode = Integer.valueOf(tm.getNetworkOperator().substring(0, 3));//取国家代码
		gcli.MobileNetworkCode = Integer.valueOf(tm.getNetworkOperator().substring(3, 5));//取运营商
		
		List<NeighboringCellInfo> listNbInfo = tm.getNeighboringCellInfo();
		if(listNbInfo.size()>0){
			gcli.NbCellId = listNbInfo.get(0).getCid();//取邻居小区号
			gcli.NbLocationAreaCode = listNbInfo.get(0).getLac();//取邻居LAC
		}
		Log.i(TAG,"call_id="+gcli.CellId+",location_area_code="+gcli.LocationAreaCode+",mobile_country_code="+gcli.MobileCountryCode+",mobile_network_code="+gcli.MobileNetworkCode);
		HttpRest httpRest = new HttpRest();

		location = httpRest.FromGSMGetLocation(gcli, context);
		//Toast.makeText(context, "Latitude:"+location.Latitude+",Longitude:"+location.Longitude+"NBCID"+gcli.NbCellId, Toast.LENGTH_SHORT).show();
		Latitude = location.Latitude;
		Longitude = location.Longitude;
		return location;
	}
}
