/**
 * 此程序为开源程序，遵循GPLv3版本发布，并受其保护。
 * (GPLv3 http://www.gnu.org/licenses/gpl.html)
 * Copyright 2011 by H!Guo
 */
package org.guohai.android.cta.utility;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

/**
 * small tools 
 * @author H!Guo 
 */
public class Tools {
	/** Get phone imei */
	public static String GetPhoneImei(Context parm){
		TelephonyManager telephonyManager =(TelephonyManager) parm.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}
	
	/**
	 * 获取屏幕方向
	 * @param activity
	 * @return
	 */
	public static int ScreenOrient(Activity activity){
		int orient = activity.getRequestedOrientation();
		if(orient!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE && orient != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
			WindowManager windowManager = activity.getWindowManager();
			Display display = windowManager.getDefaultDisplay();
			int screenWidth = display.getWidth();
			int screenHeight = display.getHeight();
			orient = screenWidth<screenHeight?ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
		}
		return orient;
	}
	
	/**
	 * 设备背景
	 * @param activity
	 * @param view
	 * @param Background_v
	 * @param Background_h
	 */
	public static void AutoBackground(Activity activity,View view,int Background_v,int Background_h){
		int orient = ScreenOrient(activity);
		if(orient == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
			view.setBackgroundResource(Background_v);
		}else{
			view.setBackgroundResource(Background_h);
		}
		
	}
}
