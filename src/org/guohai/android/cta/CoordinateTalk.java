/**
 * 此程序为开源程序，遵循GPLv3版本发布，并受其保护。
 * (GPLv3 http://www.gnu.org/licenses/gpl.html)
 * Copyright 2011 by CTA Group
 */
package org.guohai.android.cta;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.*;
import android.app.*;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

import android.widget.*;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.*;

import org.guohai.android.cta.bll.*;

import org.guohai.android.cta.model.LocationInfo;
import org.guohai.android.cta.model.MessageInfo;
import org.guohai.android.cta.model.ParseGpsInfo;
import org.guohai.android.cta.model.ResultInfo;
import org.guohai.android.location.*;
import org.guohai.android.cta.utility.Tools;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private ListView listLocal;
    private Button btnCamera;
    private ImageView imageCapture;  
    private static final int TAKE_PICTURE = 1;
    private LocationInfo locationInfo;
    private List<ILocationManager> LocationManagers;
    private LinearLayout layout;
    
    private static final int MENU_CONFIG=0;
    private static final int MENU_REGISTER_ACCOUNT=1;
    private static final int MENU_HELP=2;
    private static final int MENU_EXIT=3;  
    //接收子线程消息
    private Handler mMainHandler;
    private Context context;
    
    /** 
     * Called when the activity is first created. 
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {        
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        findViews();
        bindEvent();       
        inita();          
        context=this;
        Tools.AutoBackground(this, layout, R.drawable.background_h, R.drawable.background_w);
    }    
    
    
    
    /**
     *  Find all views
     */
    private void findViews(){
    	textCoordinate = (TextView)findViewById(R.id.coordinate);
    	textAddress = (TextView)findViewById(R.id.my_address);
    	editMessage = (EditText) findViewById(R.id.editText1);
    	btnRefer = (Button)findViewById(R.id.button_refer);
    	btnWhere = (Button)findViewById(R.id.where_am_i);
    	btnTest = (Button)findViewById(R.id.button_test);
    	listLocal=(ListView)findViewById(R.id.listlocalMessage);
    	
    	btnCamera = (Button) this.findViewById(R.id.button_camera);
    	imageCapture = (ImageView) findViewById(R.id.imageCapture);
    	layout = (LinearLayout)findViewById(R.id.main);
    }
    
    
    private void bindEvent() {
    	btnRefer.setOnClickListener(btnReferBMI);
    	btnWhere.setOnClickListener(btnWhereBMI);
    	btnTest.setOnClickListener(btnTestBMI);
    	btnCamera.setOnClickListener(btnCameraBMI);
    }
    
    	/** 接收 Activity 返回 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i(TAG,"进入了onActivityResult");
		if (requestCode == TAKE_PICTURE) {
			if (resultCode == RESULT_OK) {
				//if (data != null) {
					BitmapFactory.Options options = new BitmapFactory.Options();  
					options.inSampleSize = 4;
				
					Bitmap bitmap = BitmapFactory.decodeFile( Environment.getExternalStorageDirectory().toString().concat("/ctadata/test.jpg"), options ); 
					//Bitmap bm = (Bitmap) data.getExtras().get("data");
					Bitmap.createScaledBitmap(bitmap, 320, 240, true);
					imageCapture.setImageBitmap(bitmap); // Display image
				//}
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
    /** 相机按钮 */
    private Button.OnClickListener btnCameraBMI = new Button.OnClickListener(){
    		@Override
			public void onClick(View v) {
				// startActivityForResult(new
				// Intent("android.media.action.IMAGE_CAPTURE"), TAKE_PICTURE);
				
				File dir = new File(Environment.getExternalStorageDirectory().toString().concat("/ctadata"));
				if(!dir.exists() && !dir.isDirectory()){
					Log.d(TAG,dir.mkdirs()?"Create dir.":"Can't create dir.");
				}
				Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				Uri uri = Uri.fromFile(new File(dir.getAbsolutePath().concat("/test.jpg")));
				Log.d(TAG, uri.toString());
				i.putExtra(MediaStore.EXTRA_OUTPUT, uri);
				startActivityForResult(i, TAKE_PICTURE);
			}
    };
    
    /** 测试按钮事件 */
    private Button.OnClickListener btnTestBMI = new Button.OnClickListener()
    {
    	public void onClick(View v)
        {
    		//gps.CellularPhone();
    		/*
    		TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
			GsmCellLocation gcl = (GsmCellLocation) tm.getCellLocation();
			String a=gcl.getCid()+","+gcl.getLac()+"|";
			List<NeighboringCellInfo> listNbInfo = tm.getNeighboringCellInfo();
			int count=listNbInfo.size();
			for(int i=0;i<count;i++){
				a+= listNbInfo.get(i).getCid()+","+listNbInfo.get(i).getLac()+","+listNbInfo.get(i).getRssi()+"|";//取邻居小区号
			}
			Toast.makeText(CoordinateTalk.this, a, Toast.LENGTH_LONG).show();
			*/
       	 if(locationInfo.Latitude!=0){
        	 
	          	//临时代码绑定LIST数据
	          	ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
	          	PostSiteData httpRest = new PostSiteData();
	          	String json = httpRest.GetCurrentLocationMessage(locationInfo, context);
	           	try {
	      			//JSONObject jsonObjSplit= new JSONObject(json);
	      			JSONArray ja =new JSONArray(json);
	      			for(int i =0 ;i<ja.length();i++){
	      				JSONObject jo = (JSONObject)ja.get(i);
	      	    		HashMap<String, String> map = new HashMap<String, String>();
	      	    		map.put("ItemName", jo.getString("username"));
	      	        	map.put("ItemMessage", jo.getString("note"));
	      	        	mylist.add(map);
	      			}
	      	    	SimpleAdapter mSchedule = new SimpleAdapter(context,mylist,R.layout.list_local_message_item,new String[]{"ItemName","ItemMessage"},new int[]{R.id.ItemName,R.id.ItemMessage}		);
	      	    	listLocal.setAdapter(mSchedule);
	      			
	      		} catch (JSONException e) {
	      			// TODO Auto-generated catch block
	      			e.printStackTrace();
	      		}
	          	//临时代码结束 
     	}
        }    	
    };    
    /** 定位按钮事件 */
    private Button.OnClickListener btnWhereBMI = new Button.OnClickListener()
    {
    	public void onClick(View v)
        {
    		PostSiteData httpRest = new PostSiteData();
			ParseGpsInfo parseInfo = new ParseGpsInfo();
			parseInfo.Latitude = locationInfo.Latitude;
			parseInfo.Longitude = locationInfo.Longitude;
			parseInfo.SendAccount = Tools.GetPhoneImei(getApplicationContext());
			if(parseInfo.Latitude==0 || parseInfo.Longitude==0){
    			Toast.makeText(CoordinateTalk.this, "定位中..", Toast.LENGTH_SHORT).show();
    			return ;
			}
			String Result = httpRest.GetParse(parseInfo,getApplicationContext());
			textAddress.setText(Result);
        }        
    };    
    /** 发送按钮事件 */
    private Button.OnClickListener btnReferBMI = new Button.OnClickListener()
    {
        public void onClick(View v)
        {
        	PostSiteData httpRest = new PostSiteData();
        	MessageInfo message =new MessageInfo();
    		message.Altitude = locationInfo.Altitude;
    		message.Latitude = locationInfo.Latitude;
    		message.Longitude = locationInfo.Longitude;
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
			ResultInfo result = httpRest.AddMessage(message,getApplicationContext());
    		Log.i(TAG,"["+result.State+result.Message+"]");
    		if(0<=result.State){
    			Toast.makeText(CoordinateTalk.this, result.Message, Toast.LENGTH_SHORT).show();
    			editMessage.setText("");
    		}else{
    			Toast.makeText(CoordinateTalk.this, result.Message, Toast.LENGTH_SHORT).show();
    		}
        }
    };
    
    /** 创建菜单 */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    	
    	menu.add(0,MENU_CONFIG,0,R.string.menu_config)
    		.setIcon(R.drawable.menu_sys_opt);
    	menu.add(0,MENU_REGISTER_ACCOUNT,0,R.string.menu_register_account)
			.setIcon(R.drawable.menu_logout);
    	menu.add(0,MENU_HELP,0,R.string.menu_help)
			.setIcon(R.drawable.help_menu_icon);
    	menu.add(0,MENU_EXIT,0,R.string.menu_Exit)
			.setIcon(R.drawable.exit_menu_icon);
		return true;
    }
    
    /**
     * 菜单事件
     */
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


    	locationInfo = new LocationInfo();    
    	GSMManager gsm = new GSMManager(getApplicationContext(),locationInfo);
    	GPSManager gps = new GPSManager(getApplicationContext(),locationInfo);    	    	
    	LocationManagers = new ArrayList <ILocationManager>();
    	LocationManagers.add(gsm);
    	LocationManagers.add(gps);
    	
    	//接收子线程消息    
    	mMainHandler = new Handler();
    	
    	openHandler();
    	textCoordinate.setText("维度：" +  locationInfo.Latitude+ "\n经度：" + locationInfo.Longitude+"\n高度："+locationInfo.Altitude);

        if(!gps.IsOpen())
        {
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
    
    public void onDestory(){
    	super.onDestroy();    	
    	CloseHandler();
    }
    
    protected void onSopt()
    {
    	super.onStop();    	
    	CloseHandler();
    }
    
    /** 当程序重新开始的时候 */
    @Override
    protected void onResume(){
    	super.onResume();
    	//openHandler(); 
    }
    
    /** 当程序暂停的时候 */
    @Override
    protected void onPause(){
    	super.onPause();    
    	CloseHandler();
    }
    
    /** 子程序运行方法 */
    private Runnable runnable = new Runnable() {  
         public void run() { 
        	 //Log.i(TAG,"Thread ChildThread run!");	         
	         textCoordinate.setText("维度：" +  locationInfo.Latitude+ "\n经度：" + locationInfo.Longitude+"\n高度："+locationInfo.Altitude);        	        	 
        	 mMainHandler.postDelayed(runnable, 1000);   

          }  
    }; 
    
    /** 创建Handler */
    private void openHandler()
    {    
    	for (ILocationManager lm : LocationManagers) {
			if(lm.IsOpen())
			{
				lm.StartLocation();
			}
		}    
 	    mMainHandler.postDelayed(runnable, 1000); 
    }
    
    /** 关闭Handler */
    private void CloseHandler()
    { 
    	for (ILocationManager lm : LocationManagers) {
			if(lm.IsOpen())
			{
				lm.PauseGetLocation();
			}
		} 
    	mMainHandler.removeCallbacks(runnable);    	   	
    } 
}
