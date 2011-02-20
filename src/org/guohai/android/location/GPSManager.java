package org.guohai.android.location;

import org.guohai.android.cta.model.LocationInfo;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;

public class GPSManager implements ILocationManager {

private static final String TAG="CoordinateTalk";
	
	private Context context;	
	private LocationInfo info;	
	private LocationManager locationManager;
	
	/** 构造函数 */
	public GPSManager(Context parm,LocationInfo info){
		context = parm;
		this.info = info;
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
	}		
	
	/** 检查设备是否开启 */
	@Override
	public boolean IsOpen() {		
		if(locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)){
			return true;
		}
		return false;
	}
	
	/** 开始监听 */
	@Override
	public boolean StartLocation() {
		//取位置管理服务
		Log.i(TAG,"getLocation Start");	
		if(null == locationManager){
			return false;
		}
		//查找服务信息

		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
		return true;
	}

	/** 暂停监听卫星坐标 */
	@Override
	public void PauseGetLocation() {
		if(null != locationManager){
			locationManager.removeUpdates(mLocationListener);
		}		
	}
	

	/** 实现GPS监听 */
	private LocationListener mLocationListener = new LocationListener(){
		/** GPS数据被更新 */
		public void onLocationChanged(Location location){		
			info.Latitude = location.getLatitude()==0?info.Latitude:location.getLatitude();
			info.Longitude = location.getLongitude()==0?info.Longitude:location.getLongitude();
			info.Altitude= location.getAltitude();
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
	

}
