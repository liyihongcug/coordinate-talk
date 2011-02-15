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

import android.util.Log;

public class HttpRest {
	
	private static final String TAG="CoordinateTalk";
	
	/** HTTPPost数据 */
	public static String HttpPostClient(String url, List<NameValuePair> pairs)
	{
		//create http client
		HttpClient client = new DefaultHttpClient();
		//create post request
		HttpPost httpPost = new HttpPost(url);	
		
		HttpEntity entity;
		try {
			entity = new UrlEncodedFormEntity(pairs,HTTP.UTF_8);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "UnsupportedEncodingException";
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
				return reString;
			}				
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "失败了IOException";
		}
		return "失败了Exception";
	}
}
