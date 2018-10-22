<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>毕业设计管理系统</title>
</head>
<body>
<% String reportName=(String)request.getSession().getAttribute("reportName"); %>

<embed width="100%" height="1200px" name="plugin" id="plugin"
		src="OfficeFile/<%= reportName%>"
		type="application/pdf" internalinstanceid="3" title=""/>
</body>
</html>