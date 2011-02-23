package org.guohai.android.cta.location;

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
	private Timer timer;  
	private long delay = 10;
	private long period = 60000;
	private TelephonyManager tm;
	
	/** 构造函数 */
	public GSMManager(Context parm,LocationInfo info){
		Log.d(TAG, "GSMManager执行了");
		context = parm;
		this.setInfo(info);
		timer = new Timer();
		tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
	}
	
	public GSMManager(Context parm,LocationInfo info,long delay,long period){
		context = parm;
		this.setInfo(info);
		this.delay = delay;
		this.period = period;
		timer = new Timer();
		tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
	}
	
	
	@Override
	public boolean IsOpen() {

		if(tm != null)
		{
			int NetType =tm.getNetworkType();
			Log.d(TAG,"当前网络类型是"+NetType);
			if(NetType==TelephonyManager.NETWORK_TYPE_EDGE || 
					NetType == TelephonyManager.NETWORK_TYPE_GPRS ){
				return true;
			}
			else {
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean StartLocation() {
		if(IsOpen())
		{
			//timer.schedule(task, delay, period);
			CellularPhone();
		}
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
		Log.i(TAG,"CellularPhone:call_id="+gcli.CellId+",location_area_code="+gcli.LocationAreaCode+",mobile_country_code="+gcli.MobileCountryCode+",mobile_network_code="+gcli.MobileNetworkCode);
		
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
