/**
 * 此程序为开源程序，遵循GPLv3版本发布，并受其保护。
 * (GPLv3 http://www.gnu.org/licenses/gpl.html)
 * Copyright 2011 by H!Guo
 */
package org.guohai.android.cta;

import java.util.List;

import android.content.*;
import android.provider.*;
import android.app.*;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.*;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.*;

import org.guohai.android.cta.bll.*;
import org.guohai.android.cta.model.MessageInfo;
import org.guohai.android.cta.model.ParseGpsInfo;
import org.guohai.android.cta.utility.GPSUtilities;
import org.guohai.android.cta.utility.Tools;

/**
 * Main views 
 * @author H!Guo 
 *
 */
public class CoordinateTalk extends Activity {
	private static final String TAG="CoordinateTalk";

    private TextView textCoordinate;
    private Button btnRefer;
    private Button btnTest;
    private EditText editMessage;
    private Button btnWhere;
    private TextView textAddress;
    private GPSUtilities gps;
    private static final int MENU_CONFIG=0;
    private static final int MENU_HELP=2;
    private static final int MENU_EXIT=3;
    
    private Handler mMainHandler;
    private static boolean theanState=false;
    private ChildThread childThread;
    
    //private boolean gpsIsOpen = false;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViews();
        gps = new GPSUtilities(getApplicationContext());
        inita();
          	
    }
    
    public void onDestory(){
    	super.onDestroy();
    	//mChildHandler.getLooper().quit();
    	theanState=false;
    	childThread.destroy();
    }
    
    /** 当程序重新开始的时候 */
    @Override
    protected void onResume(){
    	super.onResume();
    	gps.getLocation();
    }
    
    /** 当程序暂停的时候 */
    @Override
    protected void onPause(){
    	super.onPause();
    	gps.pauseGetLocation();
    }
    
    /** Find all views */
    private void findViews(){
    	textCoordinate = (TextView)findViewById(R.id.coordinate);
    	textAddress = (TextView)findViewById(R.id.my_address);
    	editMessage = (EditText) findViewById(R.id.editText1);
    	btnRefer = (Button)findViewById(R.id.button_refer);
    	btnWhere = (Button)findViewById(R.id.where_am_i);
    	btnTest = (Button)findViewById(R.id.button_test);
    	btnTest.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//gps.CellularPhone();
				TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
				GsmCellLocation gcl = (GsmCellLocation) tm.getCellLocation();
				String a=gcl.getCid()+","+gcl.getLac()+"|";
				List<NeighboringCellInfo> listNbInfo = tm.getNeighboringCellInfo();
				int count=listNbInfo.size();
				for(int i=0;i<count;i++){
					a+= listNbInfo.get(i).getCid()+","+listNbInfo.get(i).getLac()+","+listNbInfo.get(i).getRssi()+"|";//取邻居小区号
				}
				Toast.makeText(CoordinateTalk.this, a, Toast.LENGTH_LONG).show();
			}
    		
    	});
    	btnWhere.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				PostSiteData httpRest = new PostSiteData();
				ParseGpsInfo parseInfo = new ParseGpsInfo();
				parseInfo.Latitude = gps.Latitude;
				parseInfo.Longitude = gps.Longitude;
				parseInfo.SendAccount = Tools.GetPhoneImei(getApplicationContext());
				if(parseInfo.Latitude==0 || parseInfo.Longitude==0){
	    			Toast.makeText(CoordinateTalk.this, "定位中..", Toast.LENGTH_SHORT).show();
	    			return ;
				}
				String Result = httpRest.GetParse(parseInfo);
				textAddress.setText(Result);
			}
    		
    	});
    	
    	btnRefer.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.v(TAG, "维");
				PostSiteData httpRest = new PostSiteData();
	    		MessageInfo message =new MessageInfo();
	    		message.Altitude = gps.Altitude;
	    		message.Latitude = gps.Latitude;
	    		message.Longitude = gps.Longitude;
	    		message.Note = editMessage.getText().toString();
	    		if(message.Note.length()==0){
	    			Toast.makeText(CoordinateTalk.this, "发送内容不能为空", Toast.LENGTH_SHORT).show();
	    			return ;
	    		}
	    		message.SendAccount=Tools.GetPhoneImei(getApplicationContext());
				if(message.Latitude==0 || message.Longitude==0){
	    			Toast.makeText(CoordinateTalk.this, "定位中..", Toast.LENGTH_SHORT).show();
	    			return ;
				}
	    		String Result = httpRest.AddMessage(message);
	    		Log.i(TAG,"["+Result+"]");
	    		if(Result.equals("200")){
	    			Log.i(TAG,Result+"1111");
	    			Toast.makeText(CoordinateTalk.this, "发送成功", Toast.LENGTH_SHORT).show();
	    			editMessage.setText("");
	    		}
	    		//textCoordinate.setText( httpRest.AddMessage(message));
			}
        });
    }
    
    /** 创建菜单 */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    	
    	menu.add(0,MENU_CONFIG,0,R.string.menu_config)
    		.setIcon(R.drawable.menu_sys_opt);
    	//menu.add(0,MENU_SWAP_ACCOUNT,0,R.string.menu_swapaccount)
		//	.setIcon(R.drawable.menu_logout);
    	menu.add(0,MENU_HELP,0,R.string.menu_help)
			.setIcon(R.drawable.help_menu_icon);
    	menu.add(0,MENU_EXIT,0,R.string.menu_Exit)
			.setIcon(R.drawable.exit_menu_icon);
		return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	super.onOptionsItemSelected(item);
    	switch(item.getItemId()){
    	case MENU_CONFIG:
    		Intent intent = new Intent(); 
    		intent.setClass(CoordinateTalk.this, Config.class);
    		startActivity(intent);
    		break;
    	case MENU_HELP:
    		new AlertDialog.Builder(CoordinateTalk.this)     
    		.setTitle("About Coordinate Talk")     
    		.setMessage("your device imei\n"+Tools.GetPhoneImei(getApplicationContext()))     
    		.show();
    		break;
    	case MENU_EXIT:
    		this.finish();
    		break;
    	}
    	return true;
    }
    
    /** 初始化 */
    private void inita(){
    	textCoordinate.setText("维度：" +  gps.Latitude+ "\n经度：" + gps.Longitude+"\n高度："+gps.Altitude);
    	//接收子线程消息
    	mMainHandler = new Handler(){
    		@Override
    		public void handleMessage(Message msg){
    			textCoordinate.setText((String)msg.obj);
    		}
    	};
    	
        if(gps.GPSDeviceIsOpen()){
        	//textCoordinate.setText("true");
        	//gpsIsOpen=true;
	        if(gps.getLocation()){
	        	//textCoordinate.setText("维度：" +  gps.Latitude+ "\n经度" + gps.Longitude);
	        	theanState = true;
	        	childThread = new ChildThread();
	        	childThread.start();
	        }
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

        	textCoordinate.setText("不打开GPS设置，本程序的某些功能可能无法正常执行！");
        }
    }
    
    class ChildThread extends Thread{
    	public void run(){
    	    //gps.CellularPhone();
    	    
    		Log.i(TAG,"Thread ChildThread run!");
    		while(theanState){

    			try {
    				
        			Message toMain = mMainHandler.obtainMessage();
        			toMain.obj = "维度：" +  gps.Latitude+ "\n经度：" + gps.Longitude+"\n高度："+gps.Altitude;
        			mMainHandler.sendMessage(toMain);
					
						sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();

					}

    		}
    	}
    }
}
