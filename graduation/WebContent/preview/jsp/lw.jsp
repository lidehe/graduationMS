<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
	+ path + "/";//因为用了服务器端跳转；所以这边要重定向一下path；保证js和css可以正确载入
%>
<html>
<head>
<link rel="shortcut icon" href="student/Images/student.ico" />
<title>查看成果</title>
</head>
<body>
<embed width="100%" height="100%" name="plugin" id="plugin"
		src="preview.do?meth=lwLoad"
		type="application/pdf" internalinstanceid="3" title=""/>
</body>
</html>