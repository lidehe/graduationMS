<%@page import="domain.Rws"%>
<%@page import="domain.Kt"%>
<%@page import="java.util.List"%>
<%@page import="factory.ServiceFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";//因为用了服务器端跳转；所以这边要重定向一下path；保证js和css可以正确载入
	int sideInd = 1;//侧边栏的序号，需要确定的赋值；与sidebar对应

	Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
	List<Kt> kts = ServiceFactory.getTeacherServiceInstance().getTeacherAllKts(teacher);
	List<Integer> numbers = ServiceFactory.getTeacherServiceInstance().getTeacherKtStudents(kts);
	List<Rws> rws = ServiceFactory.getTeacherServiceInstance().getTeacherAllRws(teacher);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link rel="shortcut icon" href="zdls/Images/student.ico" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>任务书</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<!-- The Main CSS File -->
<link rel="stylesheet" href="zdls/CSS/normalize.css" />
<link rel="stylesheet" href="zdls/CSS/tipsy.css" />
<link rel="stylesheet" href="zdls/CSS/colorpicker.css" />
<link rel="stylesheet" href="zdls/CSS/jquery.cleditor.css" />
<link rel="stylesheet" href="zdls/CSS/fullcalendar.css" />
<link rel="stylesheet" href="zdls/CSS/colorbox.css" />
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
</head>

<body>
	<%@ include file="../../common/jsp/ZdNotice.jspf"%>
	<%@ include file="../../common/jsp/password.jspf"%>

	<!-- 头部导航 -->
	<%@ include file="../common/header.jspf"%>


	<div class="wrapper contents_wrapper">

		<!-- 左边侧栏 -->
		<%@ include file="../common/sidebar.jspf"%>


		<div class="contents">
			<!-- 		中间内容区 -->
			<div class="grid_wrapper">

				<div class="g_6 contents_header">
					<h3 class="i_16_dashboard tab_label">任务书管理</h3>
					<div>
						<span class="label">根据学生选择情况，上传课题任务书</span>
					</div>
				</div>
				<div class="g_6 contents_options"></div>

				<div class="g_12 separator">
					<span></span>
				</div>

				<!-- 内容主要区域 -->


				<!-- Simple Table -->
				<div class="g_12">
					<div class="widget_header">
						<h4 class="widget_header_title wwIcon i_16_tables">任务书状况</h4>
					</div>
					<div class="widget_contents noPadding">
						<table class="tables">
							<thead>
								<tr>
									<th>课题标题</th>
									<th style="min-width:52px;">选择人数</th>
									<th style="min-width:170px;">选取文件</th>
									<th style="min-width:63px;">操作</th>
									<th>查看</th>
								</tr>
							</thead>
							<tbody>
								<%
									for (int i = 0, j = 0, u = 0, k = rws.size(), l = kts.size(); i < l; i++) {
								%>
								<tr>
									<td><%=kts.get(i).getName()%></td>
									<td><%=numbers.get(i)%></td>
									<td><form class="rwswj" enctype="multipart/form-data">
											<input name="ktid" type="hidden"
												value="<%=kts.get(i).getId()%>" class="ktid" /> <input
												type="file" name="wj" accept="application/pdf"
												class="simple_form rwsInput" />
										</form></td>
									<td style="text-align: center;">
										<div class="simple_buttons">
											<%
												if (j < k && kts.get(i).getId() == rws.get(j).getKt() && rws.get(j).getStatus() == 1) {
														j++;
											%>
											<div class="uploadB">覆 盖</div>
											<%
												} else {
											%>
											<div class="uploadB">上 传</div>
											<%
												}
											%>
											<div class="createGajax">&nbsp;&nbsp;&nbsp;</div>
										</div>
									</td>
									<td class="rwsText" style="text-align: center;">
										<%
											if (u < k && kts.get(i).getId() == rws.get(u).getKt() && rws.get(u).getStatus() == 1) {
										%> <a target="_blank"
										href="preview.do?meth=rwsP&id=<%=rws.get(u).getId()%>"><input
											type="button" value="查看" class="submitIt simple_buttons" /></a>
										<a target="_blank"
										href="preview.do?meth=rwsLoad&id=<%=rws.get(u).getId()%>"><input
											type="button" value="下载" class="submitIt simple_buttons" /></a>
										<%
											u++;
												} else {
										%> 暂未上传 <%
											}
										%>
									</td>
								</tr>
								<%
									}
								%>
							</tbody>
						</table>
					</div>
				</div>


			</div>
		</div>
	</div>

	<!-- 底部版权区 -->
	<%@ include file="../common/footer.jspf"%>

	<!-- 页面操作js -->
	<script src="zdls/Javascript/trws.js"></script>

</body>
</html>