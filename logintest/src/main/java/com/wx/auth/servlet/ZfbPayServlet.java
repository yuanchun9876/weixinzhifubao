package com.wx.auth.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;

/**
 * Servlet implementation class ZfbPayServlet
 */
@WebServlet("zfbPay")
public class ZfbPayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 获得初始化的AlipayClient
		String privateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCIFUFi/Eiif5KVu0O6KoQCFgIm/uy4BonpmpVSKuO59yS640bmny9hQwbKhW/gKIycsCToekEeln0KZHoYWa1LAeMtobvhR054r0t77I/p9ZjDOXLH9TKf972IULQoJfQfgPe2QYFuR+KHLDi2bsajlvB7r2YEJAv9H5Ni5v3f1MB/YmdvT5C13cQrsbPtG8Hd4F3z0CqUND/0beE4JTOcczt+BJz8tC80X7K8Z7fgLRuwoghc0D+DkoqksgUjAB2ZlLN4rOeljfKR20kA6GP9+0AdjzK4XcEG6h1P/yoO7xfiKnQ17StXWsyDpwHdaYdGf6gHcmk/NjTMfXE3JnvDAgMBAAECggEAF8PGgHfYY3Bt8/IZGMjV9ttLEu5P/q89pGsXiprs3AwaAP0x45KHHrAq3nFBIV9TdwWX3HWFK8dSHN+BJwF6LvAscUqeWfAu9wp93y1n0I7QV7+PmPXsGl2UXtrhK2W9ZUcLe2ujI9Qv7jU9zjY4QFdate7inPSCl3jNL2dxjIYDqQtPSpvK3NUwVB3rI0CRIb2BECb6wMp7Mwg9MjkV3su6+A0R90Eh81lYpwMt7ORBAkmiN7JgBmWISgZb2odtfwTT9IxaSoQJkrLMzyr6SLvDJ8NeFaifh6161lZR1M3FA1O3Tzl4MyEguFUSgnZekWt8oy2qdyEtU+J9AGP7CQKBgQDg1uewiIqegBKwGHyLrKqBSZr1SzDLtRQgovTYzEd3nSeSvLUyYc22jo4Jo9OOLD3QoGRo7KdvJAdSxuHSNsEqtDVApvZ8SGAjhA5k1P9238lgK7Dyp9lWyvCneEr2WUhJsX+6E9QZALNFxciwK6YfgqsbulFYIvt9bGdpm+G6rwKBgQCa8Vq+SN9xBZbj/+vI9fyHO3qanR/gQJlsYujvIqZfMhoTL6wbwEwPu1SI5pFpvYyE30kWEd2elmZRfM/SRh0VVR2H8smoyf29AkuBGuSIM2BKYbIZernv4BwHjQOgXqwFb8AS+se7j2qqsumvfkC7UEbYselyKz8F6E9zVTDFLQKBgAJZnLBWrQQ9GHTovJyFIH8bctgvggIPEXfJ3D9L209fwCMjf81YAltvIALvyG1K/xZoJsSF/LYHrV/rHj8hPWGh4pCbbiYcZ4zXe4MISudGLYeozT+sC3SByIQnmLj8c+n5Rfl+7Y+ZV0Zaa9+ThTf9GKbAisseCnzkHiyXaYOXAoGARPMamor7Q+Rk3VlJp8gvdE53pM4/HSAiJiVt2tzkhIBbMiaIxGCyeF24pKNWGmQLYCWs9HFG2ge7yI9uO/iPgKq71X+pVb9tdurKhs1c3W9fbjpDcys2CxUD5ChQcNEwY8rzAn8u2rxbj1LGwx2/2JtEq7GgXPn5tbtHf99tMJECgYBI3kpF1WtnVwutWvX8v6IiT17zJcYONkaYG6GojEWe+BC9zQ804Ukkvl/bQdz9PXBjt+Tr+yb1Zk+EuWU3EbKXzAq8VwpDsYXFR33n5Smf1bwVLF9+xJEDMydaCUVmzqfJC9yXLeotEQ9lEkl4ObEsvR9T18i4fNEOj9Po7oNW1A==";
		String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqEd9+W/IzUB1+55gsZdP0N9fq02i80A+Z7zemvE2UBU8FLlMGdwYiyB5VyeDVCqOCZN8E9j6w2n4MHlcBKvta60SbO9BmlZ3qKGtIPCnN2ugbGLFyXvro4OVXTqppQbqTqz9nYQj/egEXDXJOjQWWfpBIKGdkNlzM7NAPDaW3NwkjE08dMWCDs3a41KYBLsNdUpIx5G15Ap1g54Zfq/CHcBB6Rt9K8gnG7NAGKGNBsXy1GE/L49ii/TCj+LtxVAYVsDPh7llnOOdBOzTWMZvTEPNMMCrKCsf/SssZbadElDfvXM4/Q4Bw7SB2gYGUcregfKdSkn0u4iX86H4gOJOQQIDAQAB";

		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do","2016092700608850", privateKey, "json", "UTF-8", publicKey, "RSA2");// 沙箱下的网关

		AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();// 创建API对应的request
		alipayRequest.setReturnUrl("http://domain.com/CallBack/return_url.jsp");
		alipayRequest.setNotifyUrl("http://domain.com/CallBack/notify_url.jsp");// 在公共参数中设置回跳和通知地址
		alipayRequest.setBizContent("{" 
				+ "    \"out_trade_no\":\"2016092700608850\"," // 字符串信息可自主生成
				+ "    \"total_amount\":88.88,"
				+ "    \"subject\":\"购买套餐\"," 
				+ "    \"seller_id\":\"2088123456789012\","
				+ "    \"product_code\":\"QUICK_WAP_WAY\""
				+ "  }");// 填充业务参数
		
		try {
			
			String form = alipayClient.pageExecute(alipayRequest).getBody(); // 调用SDK生成表单
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(form);// 直接将完整的表单html输出到页面
			response.getWriter().flush();
			
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		



	}

}
