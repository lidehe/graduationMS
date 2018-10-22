<%@page import="domain.Lw"%>
<%@page import="domain.Lws"%>
<%@page import="domain.Ktbg"%>
<%@page import="domain.Rws"%>
<%@page import="domain.Kt"%>
<%@page import="domain.Byfs"%>
<%@page import="domain.Student"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="domain.Pyrz"%>
<%@page import="domain.Yxlw"%>
<%@page import="domain.Yuanxi"%>
<%@page import="java.util.List"%>
<%@page import="factory.ServiceFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";//因为用了服务器端跳转；所以这边要重定向一下path；保证js和css可以正确载入


	Student student = ServiceFactory.getStudentServiceInstance()
			.getStudentByAccount(request.getParameter("number"));
	Byfs byfs = ServiceFactory.getStudentServiceInstance().getStudentByfs(student.getType());
	Kt kt = ServiceFactory.getStudentServiceInstance().getStudentKt(student.getKt());
	Rws rws = ServiceFactory.getStudentServiceInstance().getKtRwsByStudent(student);
	Ktbg ktbg = ServiceFactory.getStudentServiceInstance().getStudentKtbg(student);
	Lw lw = ServiceFactory.getStudentServiceInstance().getStudentLw(student);
	List<Lws> lws = ServiceFactory.getTeacherServiceInstance().getStudentLws(student.getNumber());
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link rel="shortcut icon" href="pyry/Images/student.ico" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title><%=student.getName() %>同学的详情</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<!-- The Main CSS File -->
<link rel="stylesheet" href="dcld/CSS/normalize.css" />
<link rel="stylesheet" href="dcld/CSS/tipsy.css" />
<link rel="stylesheet" href="dcld/CSS/colorpicker.css" />
<link rel="stylesheet" href="dcld/CSS/jquery.cleditor.css" />
<link rel="stylesheet" href="dcld/CSS/fullcalendar.css" />
<link rel="stylesheet" href="dcld/CSS/colorbox.css" />
<link rel="stylesheet" href="pyry/CSS/style.css" />

<link rel="stylesheet" href="pyry/CSS/cj.css" />

<!-- jQuery -->
<script src="pyry/Javascript/jQuery/jquery-1.7.2.min.js"></script>

<!-- Flot -->
<script src="pyry/Javascript/Flot/jquery.flot.js"></script>
<script src="pyry/Javascript/Flot/jquery.flot.resize.js"></script>
<script src="pyry/Javascript/Flot/jquery.flot.pie.js"></script>
<!-- DataTables -->
<script src="pyry/Javascript/DataTables/jquery.dataTables.min.js"></script>
<!-- ColResizable -->
<script src="pyry/Javascript/ColResizable/colResizable-1.3.js"></script>
<!-- jQuryUI -->
<script src="pyry/Javascript/jQueryUI/jquery-ui-1.8.21.min.js"></script>
<!-- Uniform -->
<script src="pyry/Javascript/Uniform/jquery.uniform.js"></script>
<!-- Tipsy -->
<script src="pyry/Javascript/Tipsy/jquery.tipsy.js"></script>
<!-- Elastic -->
<script src="pyry/Javascript/Elastic/jquery.elastic.js"></script>
<!-- ColorPicker -->
<script src="pyry/Javascript/ColorPicker/colorpicker.js"></script>
<!-- SuperTextarea -->
<script src="pyry/Javascript/SuperTextarea/jquery.supertextarea.min.js"></script>
<!-- UISpinner -->
<script src="pyry/Javascript/UISpinner/ui.spinner.js"></script>
<!-- MaskedInput -->
<script src="pyry/Javascript/MaskedInput/jquery.maskedinput-1.3.js"></script>
<!-- ClEditor -->
<script src="pyry/Javascript/ClEditor/jquery.cleditor.js"></script>
<!-- Full Calendar -->
<script src="pyry/Javascript/FullCalendar/fullcalendar.js"></script>
<!-- Color Box -->
<script src="pyry/Javascript/ColorBox/jquery.colorbox.js"></script>
<!-- Kanrisha Script -->
<script src="pyry/Javascript/kanrisha.js"></script>

<link rel="stylesheet" type="text/css" href="common/css/tips.css">
<script src="common/js/tips.js"></script>
</head>

<body>
<%@ include file="../../common/jsp/password.jspf" %>

	<!-- 头部导航 -->
	<%@ include file="../common/header.jspf"%>


	<div class="wrapper contents_wrapper">




		<div class="contents" style="margin: 0 auto; float: none;">
			<!-- 		中间内容区 -->
			<div class="grid_wrapper">


				<div class="g_6 contents_header">
					<h3 class="i_16_dashboard tab_label">查看学生详细内容</h3>
					<div>
						<span class="label">对学生的论文进行评优打分</span>
					</div>
				</div>
				<div class="g_6 contents_options">
					
				</div>

				<div class="g_12 separator">
					<span></span>
				</div>

				<!-- 内容主要区域 -->
				<div class="g_12">
					<div class="widget_header">
						<h4 class="widget_header_title wwIcon i_16_forms">学生的详细内容</h4>
					</div>
					<div class="widget_contents noPadding">
						<div class="line_grid">
							<div class="g_3">
								<span class="label">课题：</span>
							</div>
							<div class="g_9">
								<span class="label"> <%
 	if (kt != null) {
 %><%=kt.getName()%> <%
 	} else {
 %>该同学还没选择课题！<%
 	}
 %>
								</span>
							</div>
						</div>
						<div class="line_grid">
							<div class="g_3">
								<span class="label">任务书：</span>
							</div>
							<div class="g_9">
								<%
									if (rws != null && rws.getStatus() == 1) {
								%>
								<a target="_blank"
									href="dcldp.do?meth=rwsP&number=<%=request.getParameter("number")%>"><input
									type="button" value="预览" class="submitIt simple_buttons" /></a>
									<a target="_blank"
									href="dcldp.do?meth=rwsL&number=<%=request.getParameter("number")%>"><input
									type="button" value="下载" class="submitIt simple_buttons" /></a>
								<%
									} else {
								%><span class="label">指导老师暂未上传任务书！</span>
								<%
									}
								%>
							</div>
						</div>
						<div class="line_grid">
							<div class="g_3">
								<span class="label">开题报告：</span>
							</div>
							<div class="g_9">
								<%
									if (byfs.getIsPass1() == 0) {
								%>
								<span class="label">该同学不参与此阶段任务！</span>
								<%
									} else {
										if (ktbg != null && ktbg.getStatus() == 1) {
								%>
								<a target="_blank"
									href="dcldp.do?meth=ktbgP&number=<%=request.getParameter("number")%>"><input
									type="button" value="预览" class="submitIt simple_buttons" /></a>
									<a target="_blank"
									href="dcldp.do?meth=ktbgL&number=<%=request.getParameter("number")%>"><input
									type="button" value="下载" class="submitIt simple_buttons" /></a>
								<%
									} else {
								%><span class="label">该同学没有上传开题报告！</span>
								<%
									}
									}
								%>
							</div>
						</div>
						<div class="line_grid">
							<div class="g_3">
								<span class="label">最终成果：</span>
							</div>
							<div class="g_9">
								<%
									if (lw != null && lw.getStatus() != 0) {
								%>
								<a target="_blank"
									href="dcldp.do?meth=lwP&number=<%=request.getParameter("number")%>"><input
									type="button" value="预览" class="submitIt simple_buttons" /></a>
									<a target="_blank"
									href="dcldp.do?meth=lwL&number=<%=request.getParameter("number")%>"><input
									type="button" value="下载" class="submitIt simple_buttons" /></a>
								<%
									} else {
								%><span class="label">该同学没有上传最终成果！</span>
								<%
									}
								%>
							</div>
						</div>
						<%
							if (lw != null && lw.getStatus() == 1 && lws != null && lws.size() > 0) {
						%>
						<div class="line_grid">
							<%
								for (int x = 0, y = lws.size(); x < y; x++) {
							%>
							<div class="g_3">
								<span class="label">附件-<%=lws.get(x).getFileName()%>：
								</span>
							</div>
							<div class="g_9">
								<a target="_blank"
									href="dcldp.do?meth=lwsP&id=<%=lws.get(x).getId()%>"><input
									type="button" value="预览/下载" class="submitIt simple_buttons" /></a>
							</div>
							<%
								}
							%>
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
	<%@ include file="../common/footer.jspf"%>

	<!-- 页面操作js -->
</body>
</html>