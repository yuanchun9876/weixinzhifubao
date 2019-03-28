package com.wx.auth.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;

public class AuthUtil {

	public static final String APPID = "wx3e174649819a43f1"; // "wx396f8e5c4ed751da";
	public static final String APPSECRET = "4e94f7fafddd94c8dd500d4643537a6c"; //"592cc45679d6883a7bf9524e2c8ae580";
	
	public static JSONObject doGetJson(String url) throws ClientProtocolException, IOException {
		
		JSONObject jsonObject = null;
		
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = client.execute(httpGet);
		HttpEntity entity = response.getEntity();
		if(entity != null) {
			String result = EntityUtils.toString(entity, "UTF-8");
			jsonObject = JSONObject.fromObject(result);
		}
		return jsonObject;
	}
	
}
