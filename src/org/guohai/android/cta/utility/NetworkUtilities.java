package org.guohai.android.cta.utility;

import java.util.List;


import org.guohai.android.cta.model.GsmCellLocationInfo;

import android.content.Context;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;


public class NetworkUtilities {

	
	private static final String TAG="CoordinateTalk";
	
	/** 通过手机基站定位 */
	public static GsmCellLocationInfo CellularPhone(Context context){
		
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

		return gcli;
	}
}
