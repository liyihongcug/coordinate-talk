/**
 * 此程序为开源程序，遵循GPLv3版本发布，并受其保护。
 * (GPLv3 http://www.gnu.org/licenses/gpl.html)
 * Copyright 2011 by H!Guo
 */
package org.guohai.android.cta;

import android.content.*;
import android.provider.*;
import android.app.*;
import android.os.Bundle;
import android.widget.TextView;

import org.guohai.android.cta.bll.*;

/**
 * Main views 
 * @author H!Guo
 *
 */
public class CoordinateTalk extends Activity {

    private TextView textCoordinate;
    private GPSUtilities gps;
    private boolean gpsIsOpen = false;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViews();
        gps = new GPSUtilities(getApplicationContext());
        init();
        if(gpsIsOpen){
	        if(gps.getLocation()){
	        	textCoordinate.setText("维度：" +  gps.Latitude+ "\n经度" + gps.Longitude);
	        }
        }
        else{
        	textCoordinate.setText("不打开GPS设置，本程序的某些功能可能无法正常执行！");
        }
        	
    }
    
    /** Find all views */
    private void findViews(){
    	textCoordinate = (TextView)findViewById(R.id.coordinate);
    }
    
    /** 初始化 */
    private void init(){
        if(gps.GPSDeviceIsOpen()){
        	textCoordinate.setText("true");
        	gpsIsOpen=true;
        }
        else{
        	new AlertDialog.Builder(CoordinateTalk.this)
        		.setTitle(R.string.setting_gps_title)
        		.setMessage(R.string.setting_gps_info)
        		.setPositiveButton(R.string.gps_setting,
        				new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
					    		Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
					    		startActivityForResult(intent,0);
							}
						})
				.setNegativeButton(R.string.jump_gps_setting, null)

        		.show();

        }
    }
    
}
