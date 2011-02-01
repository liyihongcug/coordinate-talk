/**
 * Copyright 2011 H!guo
 */
package org.guohai.android.cta;

import android.content.*;
import android.provider.*;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.guohai.android.cta.bll.*;

/**
 * Main views 
 * @author H!Guo
 *
 */
public class CoordinateTalk extends Activity {

    private TextView textCoordinate;
    private GPSUtilities gps;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViews();
        gps = new GPSUtilities(getApplicationContext());
        init();
        if(gps.getLocation()){
        	textCoordinate.setText("维度：" +  gps.Latitude+ "\n经度" + gps.Longitude);
        }
        	
    }
    
    /** Find all views */
    private void findViews(){
    	textCoordinate = (TextView)findViewById(R.id.coordinate);
    }
    
    /** 初始化 */
    private void init(){
        if(gps.openGPSSettings()){
        	textCoordinate.setText("true");
        }
        else{
        	Toast.makeText(this, R.string.open_gps, Toast.LENGTH_SHORT).show();
    		Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
    		startActivityForResult(intent,0);
        }
    }
    
}
