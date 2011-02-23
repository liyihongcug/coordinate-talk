package org.guohai.android.cta.location;

public interface ILocationManager {
	/** 检查设备是否开启 */
	public boolean IsOpen();
	
	/** 开始获取坐标 */
	public boolean StartLocation();
	
	/** 暂停获取坐标 */
	public void PauseGetLocation();		
}
