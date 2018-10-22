<%@page import="factory.ServiceFactory"%>
<%@page import="domain.Notice"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";//因为用了服务器端跳转；所以这边要重定向一下path；保证js和css可以正确载入

	List<Notice> noticesX = ServiceFactory.getStudentServiceInstance().getXiNotice();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>登录</title>
<link rel="shortcut icon" href="student/Images/student.ico" />
<script src="./common/js/tips.js"></script>
<script type="text/javascript" src="./common/js/jquery-1.11.3.js"></script>
<script type="text/javascript" src="./common/js/login.js"></script>
<style>
a {
	text-decoration: none;
}

a:link {
	color: #000000;
	text-decoration: none;
} /* 未被访问的链接     */
a:visited {
	color: #000000
} /* 已被访问过的链接    */
a:hover {
	text-decoration: none;
	color: #EEE8CA;
} /* 鼠标悬浮在上的链接  */
a:active {
	color: #000000
} /* 鼠标点中激活链接    */
</style>
</head>

<body
	style="background-color: #39588C; min-height: 663px; min-width: 1277px; width: 100%; height: 100%; padding: 0; margin: 0;">
	<div
		style="background: url(./common/img/login.png) 0 0 no-repeat no-repeat; height: 663px; width: 1277px; background-size: 100% 100%; position: absolute; left: 50%; top: 50%; margin-left: -638px; margin-top: -331px;">
		<div
			style="position: absolute; left: 129px; top: 427px; width: 431px; height: 122px; padding: 10px; overflow: auto;">
			<ul style="margin-top:0;margin-bottom:0;">
				<%
					for (int i = 0, l = noticesX.size(); i < l; i++) {
				%>
				<li><a style="display:inline-block;width:100%;" title="<%=noticesX.get(i).getTitle()%>" target="_blank"
					href="home.do?meth=detail&id=<%=noticesX.get(i).getId()%>"> <%
 	if (noticesX.get(i).getTitle().length() > 27)
 			out.print(noticesX.get(i).getTitle().substring(0, 26) + "…");
 		else
 			out.print(noticesX.get(i).getTitle());
 %>
				</a></li>
				<%
					}
				%>
				<%
					if (noticesX.size() == 0) {
				%><li>暂无公告</li>
				<%
					}
				%>

			</ul>
		</div>
		<div style="position: absolute; left: 808px; top: 427px">
			<input id="username" type="text" placeholder="工号/学号" class="Inp"
				style="background: rgba(0, 0, 0, 0); padding-left: 44px; height: 36px; border: 0; left: 0; position: absolute; outline: 0; width: 159px;">
			<br> <input id="userpwd" type="password" placeholder="密码"
				class="Inp"
				style="background: rgba(0, 0, 0, 0); padding-left: 44px; height: 35px; border: 0; position: absolute; left: 0; outline: 0; top: 45px; width: 159px;">
			<input type="button"
				style="position: absolute; top: 86px; height: 24px; border: 0; outline: 0; width: 203px; background-color: #39588C; color: white;" />
			<input type="button" id="loginb" class="Inp"
				style="position: absolute; top: 115px; left: 1px; height: 24px; border: 0; outline: 0; width: 91px; background-color: #39588C; cursor: pointer; color: white;"
				value="登录"> <input type="button" id="cancel" class="Inp"
				style="position: absolute; top: 115px; left: 111px; height: 24px; border: 0; outline: 0; width: 91px; background-color: #39588C; cursor: pointer; color: white;"
				value="取消">
		</div>
	</div>
</body>

</html>