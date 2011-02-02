/**
 * 此程序为开源程序，遵循GPLv3版本发布，并受其保护。
 * (GPLv3 http://www.gnu.org/licenses/gpl.html)
 * Copyright 2011 by H!Guo
 */
package org.guohai.android.cta.bll;

import android.content.*;
import android.location.*;

/**
 * Get GPS Corrdinate 
 * @author H!Guo
 */
public class GPSUtilities {
	
	private Context context;
	public double Latitude;
	public double Longitude;
	
	/** 构造函数 */
	public GPSUtilities(Context parm){
		context = parm;
	}
	
	/** 检查设备是否开启 */
	public boolean GPSDeviceIsOpen(){
		LocationManager alm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		if(alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)){
			return true;
		}
		return false;
	}
	
	/** 取坐标 */
	public boolean getLocation(){
		//取位置管理服务
		LocationManager locationManager;
		locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		//查找服务信息
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		//try{
			String provider = locationManager.getBestProvider(criteria, true);
			Location location = locationManager.getLastKnownLocation(provider);
			if(null != location){
				Latitude = location.getLatitude();
				Longitude= location.getLongitude();
			}
		//}
			//locationManager.requestLocationUpdates(provider, 100*1000, 500, listener)
			return true;
	}
}
