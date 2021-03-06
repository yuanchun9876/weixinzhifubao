package com.wx.auth.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wx.auth.util.AuthUtil;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/wxLogin")
public class WxLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String backUrl = "http://2420i5m224.wicp.vip/logintest/callBack";
		// System.out.println(URLEncoder.encode(backUrl));
		//https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?"		      
				+ "appid=" + AuthUtil.APPID
				+ "&redirect_uri=" + URLEncoder.encode(backUrl)
				+ "&response_type=code"
				+ "&scope=" + "snsapi_userinfo"
				+ "&state=STATE"
				+ "#wechat_redirect";
		
		response.sendRedirect(url);
		
	}

}
