<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.alipay.api.*"%>
<%@ page import="com.alipay.api.request.*"%>
<%@ page import="com.alipay.api.response.*"%>

<html>
  <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>支付宝页面跳转同步通知页面</title>
  </head>
  <body>
<%
	System.out.println("页面跳转");


    //获取支付宝GET过来反馈信息
    Map<String,String> params = new HashMap<String,String>();
    Map requestParams = request.getParameterMap();
    for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
        String name = (String) iter.next();
        System.out.println("name:"+name);
        String[] values = (String[]) requestParams.get(name);
        System.out.println("valuesArray:" + Arrays.toString(values));
        String valueStr = "";
        for (int i = 0; i < values.length; i++) {
            valueStr = (i == values.length - 1) ? valueStr + values[i]
                    : valueStr + values[i] + ",";
        }
        //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
        valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
        System.out.println("valueStr:" + valueStr);
        System.out.println();
        params.put(name, valueStr);
    }
    
    //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
    //支付宝用户号
    String app_id = new String(request.getParameter("app_id").getBytes("ISO-8859-1"),"UTF-8");
    out.write(app_id + "\n");

    //获取第三方登录授权
    String alipay_app_auth = new String(request.getParameter("source").getBytes("ISO-8859-1"),"UTF-8");
    out.write(alipay_app_auth + "\n");
   
    //第三方授权code
    String app_auth_code = new String(request.getParameter("app_auth_code").getBytes("ISO-8859-1"),"UTF-8"); //获的第三方登录用户授权app_auth_code
    out.write(app_auth_code + "\n");
   
    //http://example.com/doc/toAuthPage.html?app_id=2015101400446982&app_auth_code=ca34ea491e7146cc87d25fca24c4cD11
    
    /*
    String privateKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDDki4QKzNmBS1CgfrZmLUx8ksyRRGrqOInwkhDhZyu8vk5yJtC/eEqQcp9wzU7Aph/E67sJ2C1uzgtiNA7d0Q/ycj32Of4IJOgeDGphxxzURiI/iaoIiXbd4VBJ9HcTF9S6unY2TLXm5yFz9WO160XczZVciNSG89IG1y9CWRV03DKOu0IKB89v3MfQ5e9qDJEqba68iLt3D0OeakP/7HPebRsx62qfqmZsYWnif6+sUKdhTP2ehoztrnYnJ6HB3VQXWeNqxfdaJ1txfvGtV7nChPp1tQ80pcwTAiyD5TxNoeOfZJm5nZJ48GPWwvfwE8tAI/eD8YqgAcmNLEDIqQfAgMBAAECggEAYUeaQBKtG2chzz5GX+xU3QZPny66DnAQJoS+W0/f0yorDDIcAOO2F8XoCkyKctLktfk9flxL/7mjxQqTwbCeszgVDivHnyXdKOoAkfoSDc5d7O62ndV2jUdTckhzXChlFiKhtWq8noDaB7mM2eOwRsTZVwFSTmpk3dI2fBVq4kFNFubON1Kz6JI9/9gnDXsoywpoasKN3dpoKBPwv9n0xlUwYhvWaer/6JOs2057P2UJr7zAlnMdLmq47qA8qy1MJTbN9tkqEEOFC7hRjeTUi5HStbrFT034u71AhqXxVb5BALYGDbARVdEETLjOkfTYrByv5Ilet+mKEd5Vf3gWQQKBgQD+qYUjhGZS3aenqSsf6OMsYigDyhphD2B8obX/i0G7aNHeX4Pk6YlvyyvyaHY8zJSI5HLwUx7uP9C+Uc8Ol3s/fhjaUFShc/4Zlutg/V8oWT7IUHtPMuhlAQ8KZY4rgdoikXaBMqJRMtaXh26Qd3MrDbiV0miftCxazW8GdtuGPwKBgQDEmTESDUWF1GcO+Q8TozcsDH68HJ2b13XuuOwFJiOlZyKKoRaD8JdKIOsyjXKN/8YxT2nirO5pF49CU5WZdUrc5dZ9kVcdcxojGKS9Hj2LGqCAiiZ67mtUm0OmjtwVLr/o4z75fYijeidOUFnlSJBnrI3IM2UYvaVSLumXPzAqIQKBgQCAumA6m+q9aJ4e+jx6XNa40MU5ZSgAeC1l0riU/Ge1vD6aEd4yEHsQr7r/Rh6+jQtoR1JyeRHhjRCPo/rufVQnvC4D7Sdala+Ub917oZhAZRx/VW9/kVQ3+lkIAF3lUDZb0vv8QwQZ14DyB9uz2y20TbNIyJtqX0EshTviIKLHMQKBgQCob6toEPDQoVMOs/nj1EGPvsDSQB4Uh/vlZI/qNWvy6TLh3PPiZL3p59w6eo9783iFhMluSMUWSvYVWHFigeh663viJySg+Z2aFiIGWaNWy6BZoIMpbZ2NZl0GlX0ftOb9C71HmfL4RM7qPNNYKtx1xpPOw6HJGapLiB/f0mZcoQKBgQCAsSy7s8g1gg8+5Ka90x62/2rafTplAsRHhwvwtWf2rXPSMWf0rqlaQEZLdMT8DUNn/Xd06qhwA1WvctK46av8Km9EHkgeWMtidivZVPNajxKEbBuz6c0N4XRcSnfXFlv7INzQZsTF6WCnw5oV1jMlPiYsfMEurwVNUPeKhS802g==";
    String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAw5IuECszZgUtQoH62Zi1MfJLMkURq6jiJ8JIQ4WcrvL5OcibQv3hKkHKfcM1OwKYfxOu7Cdgtbs4LYjQO3dEP8nI99jn+CCToHgxqYccc1EYiP4mqCIl23eFQSfR3ExfUurp2Nky15uchc/VjtetF3M2VXIjUhvPSBtcvQlkVdNwyjrtCCgfPb9zH0OXvagyRKm2uvIi7dw9DnmpD/+xz3m0bMetqn6pmbGFp4n+vrFCnYUz9noaM7a52Jyehwd1UF1njasX3WidbcX7xrVe5woT6dbUPNKXMEwIsg+U8TaHjn2SZuZ2SePBj1sL38BPLQCP3g/GKoAHJjSxAyKkHwIDAQAB";
    
    //使用auth_code换取接口access_token及用户userId
     //AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do","应用APPID",privateKey,"json","UTF-8",publicKey,"RSA2");//正常环境下的网关
     AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do","2016092700608850",privateKey,"json","UTF-8",publicKey,"RSA");//沙箱下的网关
     
     
    AlipayOpenAuthTokenAppRequest requestLogin1 = new AlipayOpenAuthTokenAppRequest();
    requestLogin1.setBizContent("{" +
        "\"grant_type\":\"authorization_code\"," +
        "\"code\":\""+ app_auth_code +"\"" +
        "}");
    
    //第三方授权
    AlipayOpenAuthTokenAppResponse responseToken = alipayClient.execute(requestLogin1);
    if(responseToken.isSuccess()){
        out.write("<br/>调用成功" + "\n");
        
        out.write(responseToken.getAuthAppId() + "\n");
        out.write(responseToken.getAppAuthToken() + "\n");
        out.write(responseToken.getUserId() + "\n");
        
    } else {
        out.write("调用失败" + "\n");
    }
    */
%>
  </body>
</html>