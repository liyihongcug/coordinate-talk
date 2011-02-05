/**
 * 此程序为开源程序，遵循GPLv3版本发布，并受其保护。
 * (GPLv3 http://www.gnu.org/licenses/gpl.html)
 * Copyright 2011 by H!Guo
 */
package org.guohai.android.cta.bll;

import android.content.*;
import android.location.*;
import android.os.*;
/**
 * Get GPS Corrdinate 
 * @author H!Guo
 */
public class GPSUtilities {
	
	private Context context;
	public double Latitude;//维度
	public double Longitude;//经度
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
	
	/** 取坐标 */
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
	
	/** 实现GPS监听 */
	public LocationListener mLocationListener = new LocationListener(){
		/** GPS数据被更新 */
		public void onLocationChanged(Location location){
			Latitude = location.getLatitude();
			Longitude = location.getLongitude();
		}
		
		public void onProviderDisabled(String provider){
			
		}
		
		public void onProviderEnabled(String provider){
			
		}
		
		public void onStatusChanged(String provider,int status,Bundle extras){
			
		}
	};
}
