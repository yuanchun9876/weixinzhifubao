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
 * Servlet implementation class ZfbLoginServlet
 */
@WebServlet("/zfbLogin")
public class ZfbLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String backUrl = "http://2420i5m224.wicp.vip/logintest/returnUrl";
		// System.out.println(URLEncoder.encode(backUrl));
		
		String url = "https://openauth.alipaydev.com/oauth2/appToAppAuth.htm?"
				+ "app_id=2016092700608850"
				+ "&scope=auth_user"
				+ "&redirect_uri=" + URLEncoder.encode(backUrl) ;
		System.out.println("//-:"+url);
		response.sendRedirect(url);
	}

}
