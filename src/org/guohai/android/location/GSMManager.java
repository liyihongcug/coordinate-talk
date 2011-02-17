package org.guohai.android.location;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.guohai.android.cta.bll.PostSiteData;
import org.guohai.android.cta.model.GsmCellLocationInfo;
import org.guohai.android.cta.model.LocationInfo;

import android.content.Context;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

public class GSMManager  implements ILocationManager {
	
	private static final String TAG="CoordinateTalk";
	
	private Context context;		
	private LocationInfo info;	
	private Timer timer = new Timer();  
	private long delay = 1000;
	private long period = 5000;
	private TelephonyManager tm;
	
	/** 构造函数 */
	public GSMManager(Context parm,LocationInfo info){
		context = parm;
		this.setInfo(info);
		tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
	}
	
	public GSMManager(Context parm,LocationInfo info,long delay,long period){
		context = parm;
		this.setInfo(info);
		this.delay = delay;
		this.period = period;
		tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
	}
	
	
	@Override
	public boolean IsOpen() {	
		if(tm.getNetworkType() == 1)
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean StartLocation() {
		timer.cancel();		
		timer.scheduleAtFixedRate(task, delay, period);
		return true;
	}

	@Override
	public void PauseGetLocation() {
		timer.cancel();		
	}

	TimerTask task = new TimerTask() {  
	      public void run() {  
	    	  CellularPhone();	   
	     } 
	 }; 

	/** 通过手机基站定位 */
	private void CellularPhone(){		
		if(tm == null)
			return;
		
		if(!IsOpen())
			return;		
		
		GsmCellLocationInfo gcli = new GsmCellLocationInfo();		
		GsmCellLocation gcl = (GsmCellLocation) tm.getCellLocation();		
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
		
	    PostSiteData httpRest = new PostSiteData();
	    setInfo(httpRest.FromGSMGetLocation(gcli, context));	
	}

	public void setInfo(LocationInfo info) {
		this.info = info;
	}

	public LocationInfo getInfo() {
		return info;
	}
	
}
