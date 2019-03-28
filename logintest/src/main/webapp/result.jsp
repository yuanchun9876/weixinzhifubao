<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div><h2>登录成功</h2></div>
	<div>用户openid: ${user.openid }</div>
	<div>用户昵称: ${user.nickname }</div>
	<div><img  style="vertical-align:top;"  width = "100" src="${user.headimgurl }"></div>
</body>
</html>