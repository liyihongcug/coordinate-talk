/**
 * 此程序为开源程序，遵循GPLv3版本发布，并受其保护。
 * (GPLv3 http://www.gnu.org/licenses/gpl.html)
 * Copyright 2011 by H!Guo
 */
package org.guohai.android.cta.bll;

import android.content.Context;
import android.telephony.TelephonyManager;

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
}
