<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page language="java" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix='fn' uri="http://java.sun.com/jsp/jstl/functions" %>
  <%
String path = request.getContextPath();
String port = (request.getServerPort()==80?"":String.valueOf(request.getServerPort()));
String basePath = request.getScheme()+"://"+request.getServerName()+(port==""?"":(":"+port))+path+"/";
String staticPath = "/static/";%>
<c:set var="basePath" value="<%=basePath%>"/>
<c:set var="staticPath" value="<%=staticPath %>"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="${basePath }login" method="post">
		<input type="text" name="username">
		<input type="password" name="password">
		<input type="checkbox" name="rememberMe" />记住我<br>
		<input type="submit" />
	</form>
	<p>${error}</p><img src="${staticPath}2.jpg">
</body>
</body>
</html>