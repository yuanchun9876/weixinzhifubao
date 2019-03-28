package com.wx.auth.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wx.auth.util.AuthUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class CallBackServlet
 */
@WebServlet("/callBack")
public class WxCallBackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String code = request.getParameter("code");
		// code作为换取access_token的票据，每次用户授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期。
		System.out.println("code:" + code);
		// https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?"
				+ "appid=" + AuthUtil.APPID
				+ "&secret=" + AuthUtil.APPSECRET
				+ "&code=" + code
				+ "&grant_type=authorization_code";
		
		JSONObject jsonObject = AuthUtil.doGetJson(url);
		String openid = jsonObject.getString("openid");
		String token = jsonObject.getString("access_token");
		
		
		String infoUrl = "https://api.weixin.qq.com/sns/userinfo?"
				+ "access_token=" + token
				+ "&openid=" + openid
				+ "&lang=zh_CN";
		
		JSONObject userObject = AuthUtil.doGetJson(infoUrl);
		System.out.println(userObject);
		//{"openid":"o7aMz5v3g_emex__smRbO7JndA9o","nickname":"苑春","sex":1,"language":"zh_CN","city":"哈尔滨","province":"黑龙江","country":"中国","headimgurl":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTL1A9YkRw7PtvG9wTPPkof0Y7QKNI1rSyrFdZLqkUX0UoSVSe07BlfIia8liaUI0pbBKeG2oAiayhIfw/132","privilege":[]}
		
		//String msgUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + token;
		
		
		request.setAttribute("user", userObject);
		request.getRequestDispatcher("/result.jsp").forward(request, response);
	}

}
