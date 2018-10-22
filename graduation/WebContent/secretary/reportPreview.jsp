<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="shortcut icon" href="student/Images/student.ico" />
<script src="secretary/Javascript/jQuery/jquery-1.7.2.min.js"></script>
<title>工作报告查看</title>
</head>
<script type="text/javascript">
$(function() {
	$(document).ready(function() {
		 //设置3秒后自动刷新，因为加载文档延迟
	  /*   setInterval(function() {
		   time-=1;
		  if(time>0){
		  	$("#countDown").text(time);
		  }else if(time==0){
			alert("都倒计时完了还没跳转?")
		  }else if(time<0){
			$("#countdownPanel").hide();
		  }
	    }, 1000) */
	}
	)
	})
</script>
<body>
	<%
		String yuanReportName = (String) request.getSession().getAttribute("yuanReportName");
	%>
	<embed width="100%" height="1200px" name="plugin" id="plugin"
		src="OfficeFile/<%=yuanReportName%>" type="application/pdf"
		internalinstanceid="3" title="" /> 
</body>
</html>