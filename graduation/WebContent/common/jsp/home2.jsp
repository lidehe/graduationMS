<%@page import="domain.Notice"%>
<%@page import="domain.Student"%>
<%@page import="java.util.List"%>
<%@page import="factory.ServiceFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";//因为用了服务器端跳转；所以这边要重定向一下path；保证js和css可以正确载入

	Student student = (Student) request.getSession().getAttribute("student");
	List<Notice> noticesX = ServiceFactory.getStudentServiceInstance().getXiNotice();
	List<Notice> noticesYx = ServiceFactory.getStudentServiceInstance().getYxNotice(student);
	List<Notice> noticesZy = ServiceFactory.getStudentServiceInstance().getZyNotice(student);
	List<Notice> noticesZd = ServiceFactory.getStudentServiceInstance().getZdNotice(student);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link rel="shortcut icon" href="zdls/Images/student.ico" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>主页</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<!-- The Main CSS File -->
<link rel="stylesheet" href="zdls/CSS/style.css" />

<link rel="stylesheet" href="zdls/CSS/zdls.css" />

<!-- jQuery -->
<script src="zdls/Javascript/jQuery/jquery-1.7.2.min.js"></script>

<!-- Flot -->
<script src="zdls/Javascript/Flot/jquery.flot.js"></script>
<script src="zdls/Javascript/Flot/jquery.flot.resize.js"></script>
<script src="zdls/Javascript/Flot/jquery.flot.pie.js"></script>
<!-- DataTables -->
<script src="zdls/Javascript/DataTables/jquery.dataTables.min.js"></script>
<!-- ColResizable -->
<script src="zdls/Javascript/ColResizable/colResizable-1.3.js"></script>
<!-- jQuryUI -->
<script src="zdls/Javascript/jQueryUI/jquery-ui-1.8.21.min.js"></script>
<!-- Uniform -->
<script src="zdls/Javascript/Uniform/jquery.uniform.js"></script>
<!-- Tipsy -->
<script src="zdls/Javascript/Tipsy/jquery.tipsy.js"></script>
<!-- Elastic -->
<script src="zdls/Javascript/Elastic/jquery.elastic.js"></script>
<!-- ColorPicker -->
<script src="zdls/Javascript/ColorPicker/colorpicker.js"></script>
<!-- SuperTextarea -->
<script src="zdls/Javascript/SuperTextarea/jquery.supertextarea.min.js"></script>
<!-- UISpinner -->
<script src="zdls/Javascript/UISpinner/ui.spinner.js"></script>
<!-- MaskedInput -->
<script src="zdls/Javascript/MaskedInput/jquery.maskedinput-1.3.js"></script>
<!-- ClEditor -->
<script src="zdls/Javascript/ClEditor/jquery.cleditor.js"></script>
<!-- Full Calendar -->
<script src="zdls/Javascript/FullCalendar/fullcalendar.js"></script>
<!-- Color Box -->
<script src="zdls/Javascript/ColorBox/jquery.colorbox.js"></script>
<!-- Kanrisha Script -->
<script src="zdls/Javascript/kanrisha.js"></script>

<link rel="stylesheet" type="text/css" href="common/css/tips.css">
<script src="common/js/tips.js"></script>
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
	color: #8CB6C0;
} /* 鼠标悬浮在上的链接  */
a:active {
	color: #000000
} /* 鼠标点中激活链接    */
</style>
</head>

<body style="margin-top: 0;">
	<%@ include file="./password.jspf"%>

	<!-- 头部导航 -->
	<div class="top_panel">
		<div class="wrapper">
			<div class="user">
				<img src="student/Images/user_avatar.png" alt="user_avatar"
					class="user_avatar" /> <span class="label" id="chPassword"
					title="修改密码"><span style="color: #21c4e0;"><%=student.getName()%></span>，你好</span>

			</div>
			<div class="top_links">
				<a class="logoutB" style="font-weight: bold;"
					href="login.do?method=logout" title="退出">退出</a>
			</div>
		</div>
	</div>


	<div class="wrapper contents_wrapper">




		<div class="contents" style="float: none; margin: 0 auto;">
			<!-- 		中间内容区 -->
			<div class="grid_wrapper">
				<div class="g_3">
					<div class="widget_header">
						<h4 class="widget_header_title wwIcon i_16_tabs">校公告</h4>
					</div>
					<div class="widget_contents">
						<%
							for (int i = 0, l = noticesX.size(); i < l; i++) {
						%>
						<div class="line_grid" style="border: none; padding: 3px 10px;">
							<a target="_blank"
								href="home.do?meth=detail&id=<%=noticesX.get(i).getId()%>"
								title="<%=noticesX.get(i).getTitle()%>"> <%
 	if (noticesX.get(i).getTitle().length() > 8)
 			out.print(noticesX.get(i).getTitle().substring(0, 7) + "…");
 		else
 			out.print(noticesX.get(i).getTitle());
 %>
							</a>
						</div>
						<%
							}
						%>
						<%
							if (noticesX.size() == 0) {
						%>
						<div class="line_grid" style="border: none; padding: 3px 10px;">
							<span class="label">暂无公告</span>
						</div>
						<%
							}
						%>
					</div>
				</div>
				<div class="g_3">
					<div class="widget_header">
						<h4 class="widget_header_title wwIcon i_16_tabs">院公告</h4>
					</div>
					<div class="widget_contents">
						<%
							for (int i = 0, l = noticesYx.size(); i < l; i++) {
						%>
						<div class="line_grid" style="border: none; padding: 3px 10px;">
							<a target="_blank"
								href="home.do?meth=detail&id=<%=noticesYx.get(i).getId()%>"
								title="<%=noticesYx.get(i).getTitle()%>"> <%
 	if (noticesYx.get(i).getTitle().length() > 8)
 			out.print(noticesYx.get(i).getTitle().substring(0, 7) + "…");
 		else
 			out.print(noticesYx.get(i).getTitle());
 %>
							</a>
						</div>
						<%
							}
						%>
						<%
							if (noticesYx.size() == 0) {
						%>
						<div class="line_grid" style="border: none; padding: 3px 10px;">
							<span class="label">暂无公告</span>
						</div>
						<%
							}
						%>
					</div>
				</div>
				<div class="g_3">
					<div class="widget_header">
						<h4 class="widget_header_title wwIcon i_16_tabs">专业公告</h4>
					</div>
					<div class="widget_contents">
						<%
							for (int i = 0, l = noticesZy.size(); i < l; i++) {
						%>
						<div class="line_grid" style="border: none; padding: 3px 10px;">
							<a target="_blank"
								href="home.do?meth=detail&id=<%=noticesZy.get(i).getId()%>"
								title="<%=noticesZy.get(i).getTitle()%>"> <%
 	if (noticesZy.get(i).getTitle().length() > 8)
 			out.print(noticesZy.get(i).getTitle().substring(0, 7) + "…");
 		else
 			out.print(noticesZy.get(i).getTitle());
 %>
							</a>
						</div>
						<%
							}
						%>
						<%
							if (noticesZy.size() == 0) {
						%>
						<div class="line_grid" style="border: none; padding: 3px 10px;">
							<span class="label">暂无公告</span>
						</div>
						<%
							}
						%>
					</div>
				</div>
				<div class="g_3">
					<div class="widget_header">
						<h4 class="widget_header_title wwIcon i_16_tabs">指导老师发布</h4>
					</div>
					<div class="widget_contents">
						<%
							for (int i = 0, l = noticesZd.size(); i < l; i++) {
						%>
						<div class="line_grid" style="border: none; padding: 3px 10px;">
							<a target="_blank"
								href="home.do?meth=detail&id=<%=noticesZd.get(i).getId()%>"
								title="<%=noticesZd.get(i).getTitle()%>"> <%
 	if (noticesZd.get(i).getTitle().length() > 8)
 			out.print(noticesZd.get(i).getTitle().substring(0, 7) + "…");
 		else
 			out.print(noticesZd.get(i).getTitle());
 %>
							</a>
						</div>
						<%
							}
						%>
						<%
							if (noticesZd.size() == 0) {
						%>
						<div class="line_grid" style="border: none; padding: 3px 10px;">
							<span class="label">暂无公告</span>
						</div>
						<%
							}
						%>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 底部版权区 -->
	<footer>
		<div class="wrapper">
			<span class="copyright"> &copy; Copyright &copy; 20xx.L All
				rights reserved. </span>
		</div>
	</footer>


</body>
</html>