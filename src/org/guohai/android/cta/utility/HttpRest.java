/**
 * 此程序为开源程序，遵循GPLv3版本发布，并受其保护。
 * (GPLv3 http://www.gnu.org/licenses/gpl.html)
 * Copyright 2011 by CTA Group
 */
package org.guohai.android.cta.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.guohai.android.cta.model.ResultInfo;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class HttpRest {
	
	private static final String TAG="CoordinateTalk";
	
	/**
	 * HTTPPost数据
	 * @param url POST地址
	 * @param pairs 参数列表
	 * @return 成功返回JOSN参数，失败返回null
	 */
	public static ResultInfo HttpPostClient(String url, List<NameValuePair> pairs)
	{
		//create http client
		HttpClient client = new DefaultHttpClient();
		//create post request
		HttpPost httpPost = new HttpPost(url);	
		//return value
		ResultInfo result = new ResultInfo();
		//init eroor value
		result.State=-1000;
		result.Message="posterror";
		
		HttpEntity entity;
		try {
			entity = new UrlEncodedFormEntity(pairs,HTTP.UTF_8);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG,e.getMessage());		
			return result;
		}
		httpPost.setEntity(entity);
		
		//向服务器POST数据 
		try {
			HttpResponse response = client.execute(httpPost);			
			if(response.getStatusLine().getStatusCode()==200){
				HttpEntity entityHtml = response.getEntity();
				BufferedReader  reader = new BufferedReader(new InputStreamReader(entityHtml.getContent(),"UTF-8"));
				String line = null;
				String reString = "";
				while((line = reader.readLine())!=null){
					reString += line;
				}
				if(entityHtml!=null){
					entityHtml.consumeContent();
				}
				Log.i(TAG,reString);
				JSONObject jsonObj;
				
				try {
					jsonObj = new JSONObject(reString);
					result.State =jsonObj.getInt("state");
					result.Message = jsonObj.getString("message");
					return result;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.e(TAG, e.getMessage());
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.e(TAG, e.getMessage());
				}
				return result;
			}				
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG,e.getMessage());	
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG,e.getMessage());	
		}
		return result;
	}
}
