package com.wx.auth.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;

/**
 * Servlet implementation class RedirectUrlServlet
 */
@WebServlet("/returnUrl")
public class ZfbRedirectUrlServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("/returnUrl");
	    //获取支付宝GET过来反馈信息
	    Map<String,String> params = new HashMap<String,String>();
	    Map requestParams = request.getParameterMap();
	    for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
	        String name = (String) iter.next();
	        String[] values = (String[]) requestParams.get(name);
	        String valueStr = "";
	        for (int i = 0; i < values.length; i++) {
	            valueStr = (i == values.length - 1) ? valueStr + values[i]
	                    : valueStr + values[i] + ",";
	        }
	        //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
	        valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
	        System.out.println("name:"+name);
	        System.out.println("valueStr:" + valueStr);
	        System.out.println();
	        params.put(name, valueStr);
	    }
	    
	    PrintWriter out = response.getWriter();
	    
	    //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
	    //支付宝用户号
	    String app_id = new String(request.getParameter("app_id").getBytes("ISO-8859-1"),"UTF-8");
	    out.write(app_id + "\n");
	    
	    //获取用户信息授权
	    String auth_user = new String(request.getParameter("scope").getBytes("ISO-8859-1"),"UTF-8");
	    out.write(auth_user + "\n");
	    
	    //获的第三方登录用户授权auth_code
	    String auth_code = new String(request.getParameter("app_auth_code").getBytes("ISO-8859-1"),"UTF-8");
	    out.write(auth_code + "\n");
	        
	    
	    String privateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCIFUFi/Eiif5KVu0O6KoQCFgIm/uy4BonpmpVSKuO59yS640bmny9hQwbKhW/gKIycsCToekEeln0KZHoYWa1LAeMtobvhR054r0t77I/p9ZjDOXLH9TKf972IULQoJfQfgPe2QYFuR+KHLDi2bsajlvB7r2YEJAv9H5Ni5v3f1MB/YmdvT5C13cQrsbPtG8Hd4F3z0CqUND/0beE4JTOcczt+BJz8tC80X7K8Z7fgLRuwoghc0D+DkoqksgUjAB2ZlLN4rOeljfKR20kA6GP9+0AdjzK4XcEG6h1P/yoO7xfiKnQ17StXWsyDpwHdaYdGf6gHcmk/NjTMfXE3JnvDAgMBAAECggEAF8PGgHfYY3Bt8/IZGMjV9ttLEu5P/q89pGsXiprs3AwaAP0x45KHHrAq3nFBIV9TdwWX3HWFK8dSHN+BJwF6LvAscUqeWfAu9wp93y1n0I7QV7+PmPXsGl2UXtrhK2W9ZUcLe2ujI9Qv7jU9zjY4QFdate7inPSCl3jNL2dxjIYDqQtPSpvK3NUwVB3rI0CRIb2BECb6wMp7Mwg9MjkV3su6+A0R90Eh81lYpwMt7ORBAkmiN7JgBmWISgZb2odtfwTT9IxaSoQJkrLMzyr6SLvDJ8NeFaifh6161lZR1M3FA1O3Tzl4MyEguFUSgnZekWt8oy2qdyEtU+J9AGP7CQKBgQDg1uewiIqegBKwGHyLrKqBSZr1SzDLtRQgovTYzEd3nSeSvLUyYc22jo4Jo9OOLD3QoGRo7KdvJAdSxuHSNsEqtDVApvZ8SGAjhA5k1P9238lgK7Dyp9lWyvCneEr2WUhJsX+6E9QZALNFxciwK6YfgqsbulFYIvt9bGdpm+G6rwKBgQCa8Vq+SN9xBZbj/+vI9fyHO3qanR/gQJlsYujvIqZfMhoTL6wbwEwPu1SI5pFpvYyE30kWEd2elmZRfM/SRh0VVR2H8smoyf29AkuBGuSIM2BKYbIZernv4BwHjQOgXqwFb8AS+se7j2qqsumvfkC7UEbYselyKz8F6E9zVTDFLQKBgAJZnLBWrQQ9GHTovJyFIH8bctgvggIPEXfJ3D9L209fwCMjf81YAltvIALvyG1K/xZoJsSF/LYHrV/rHj8hPWGh4pCbbiYcZ4zXe4MISudGLYeozT+sC3SByIQnmLj8c+n5Rfl+7Y+ZV0Zaa9+ThTf9GKbAisseCnzkHiyXaYOXAoGARPMamor7Q+Rk3VlJp8gvdE53pM4/HSAiJiVt2tzkhIBbMiaIxGCyeF24pKNWGmQLYCWs9HFG2ge7yI9uO/iPgKq71X+pVb9tdurKhs1c3W9fbjpDcys2CxUD5ChQcNEwY8rzAn8u2rxbj1LGwx2/2JtEq7GgXPn5tbtHf99tMJECgYBI3kpF1WtnVwutWvX8v6IiT17zJcYONkaYG6GojEWe+BC9zQ804Ukkvl/bQdz9PXBjt+Tr+yb1Zk+EuWU3EbKXzAq8VwpDsYXFR33n5Smf1bwVLF9+xJEDMydaCUVmzqfJC9yXLeotEQ9lEkl4ObEsvR9T18i4fNEOj9Po7oNW1A==";
	    String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqEd9+W/IzUB1+55gsZdP0N9fq02i80A+Z7zemvE2UBU8FLlMGdwYiyB5VyeDVCqOCZN8E9j6w2n4MHlcBKvta60SbO9BmlZ3qKGtIPCnN2ugbGLFyXvro4OVXTqppQbqTqz9nYQj/egEXDXJOjQWWfpBIKGdkNlzM7NAPDaW3NwkjE08dMWCDs3a41KYBLsNdUpIx5G15Ap1g54Zfq/CHcBB6Rt9K8gnG7NAGKGNBsXy1GE/L49ii/TCj+LtxVAYVsDPh7llnOOdBOzTWMZvTEPNMMCrKCsf/SssZbadElDfvXM4/Q4Bw7SB2gYGUcregfKdSkn0u4iX86H4gOJOQQIDAQAB";

	    
	    //使用auth_code换取接口access_token及用户userId
	     //AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do","应用APPID",privateKey,"json","UTF-8",publicKey,"RSA2");//正常环境下的网关
	     AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do","2016092700608850",privateKey,"json","UTF-8",publicKey,"RSA2");//沙箱下的网关
	     
	     
	    //获取用户信息授权
	       AlipaySystemOauthTokenRequest requestLogin2 = new AlipaySystemOauthTokenRequest();
	       requestLogin2.setCode(auth_code);
	       requestLogin2.setGrantType("authorization_code");
	       try {
	    AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(requestLogin2);
	    out.write("<br/>AccessToken:"+oauthTokenResponse.getAccessToken() + "\n");
	    
	      //调用接口获取用户信息
	    AlipayClient alipayClientUser = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do", "2016092700608850", privateKey, "json", "UTF-8", publicKey, "RSA2"); 
	    AlipayUserInfoShareRequest requestUser = new AlipayUserInfoShareRequest();
	    try {
	        AlipayUserInfoShareResponse userinfoShareResponse = alipayClient.execute(requestUser, oauthTokenResponse.getAccessToken());
	        out.write("<br/>UserId:" + userinfoShareResponse.getUserId() + "\n");//用户支付宝ID
	        out.write("UserType:" + userinfoShareResponse.getUserType() + "\n");//用户类型
	        out.write("UserStatus:" + userinfoShareResponse.getUserStatus() + "\n");//用户账户动态
	        out.write("Email:" + userinfoShareResponse.getEmail() + "\n");//用户Email地址
	        out.write("IsCertified:" + userinfoShareResponse.getIsCertified() + "\n");//用户是否进行身份认证
	        out.write("IsStudentCertified:" + userinfoShareResponse.getIsStudentCertified() + "\n");//用户是否进行学生认证
	    } catch (AlipayApiException e) {
	        //处理异常
	        e.printStackTrace();
	    }
	    } catch (AlipayApiException e) {
	        //处理异常
	        e.printStackTrace();
	    }
	}

}
