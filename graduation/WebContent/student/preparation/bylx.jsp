<%@page import="java.sql.Date"%>
<%@page import="domain.Student"%>
<%@page import="java.util.List"%>
<%@page import="factory.ServiceFactory"%>
<%@page import="domain.Byfs"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";//因为用了服务器端跳转；所以这边要重定向一下path；保证js和css可以正确载入
	int sideInd = 0;//侧边栏的序号，

	Student student = (Student) session.getAttribute("student");
	int byfsId = student.getType();
	String byfsMessage = "您当前没有选择毕业方式";
	if (byfsId != -1) {
		byfsMessage = "您当前选择的别用方式是： "
				+ ServiceFactory.getStudentServiceInstance().getStudentByfs(byfsId).getName();
	}
	List<Byfs> byfsList = ServiceFactory.getStudentServiceInstance().getAllByfs();

	int isGroup = student.getIsgroup();

	Byfs baseByfs = ServiceFactory.getStudentServiceInstance().getStudentByfs(-1);
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link rel="shortcut icon" href="student/Images/student.ico" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>毕业类型</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<!-- The Main CSS File -->
<link rel="stylesheet" href="student/CSS/normalize.css" />
<link rel="stylesheet" href="student/CSS/tipsy.css" />
<link rel="stylesheet" href="student/CSS/colorpicker.css" />
<link rel="stylesheet" href="student/CSS/jquery.cleditor.css" />
<link rel="stylesheet" href="student/CSS/fullcalendar.css" />
<link rel="stylesheet" href="student/CSS/colorbox.css" />
<link rel="stylesheet" href="student/CSS/style.css" />
<link rel="stylesheet" href="student/CSS/student.css" />

<!-- jQuery -->
<script src="student/Javascript/jQuery/jquery-1.7.2.min.js"></script>

<!-- Flot -->
<script src="student/Javascript/Flot/jquery.flot.js"></script>
<script src="student/Javascript/Flot/jquery.flot.resize.js"></script>
<script src="student/Javascript/Flot/jquery.flot.pie.js"></script>
<!-- DataTables -->
<script src="student/Javascript/DataTables/jquery.dataTables.min.js"></script>
<!-- ColResizable -->
<script src="student/Javascript/ColResizable/colResizable-1.3.js"></script>
<!-- jQuryUI -->
<script src="student/Javascript/jQueryUI/jquery-ui-1.8.21.min.js"></script>
<!-- Uniform -->
<script src="student/Javascript/Uniform/jquery.uniform.js"></script>
<!-- Tipsy -->
<script src="student/Javascript/Tipsy/jquery.tipsy.js"></script>
<!-- Elastic -->
<script src="student/Javascript/Elastic/jquery.elastic.js"></script>
<!-- ColorPicker -->
<script src="student/Javascript/ColorPicker/colorpicker.js"></script>
<!-- SuperTextarea -->
<script
	src="student/Javascript/SuperTextarea/jquery.supertextarea.min.js"></script>
<!-- UISpinner -->
<script src="student/Javascript/UISpinner/ui.spinner.js"></script>
<!-- MaskedInput -->
<script src="student/Javascript/MaskedInput/jquery.maskedinput-1.3.js"></script>
<!-- ClEditor -->
<script src="student/Javascript/ClEditor/jquery.cleditor.js"></script>
<!-- Full Calendar -->
<script src="student/Javascript/FullCalendar/fullcalendar.js"></script>
<!-- Color Box -->
<script src="student/Javascript/ColorBox/jquery.colorbox.js"></script>
<!-- Kanrisha Script -->
<script src="student/Javascript/kanrisha.js"></script>

<link rel="stylesheet" type="text/css" href="common/css/tips.css">
<script src="common/js/tips.js"></script>
</head>

<body>
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
					<h3 class="i_16_dashboard tab_label">
						毕业类型<%="(" + new Date(byfsB.getStart1().getTime()) + "之前)"%></h3>
					<div>
						<span class="label">与指导老师商量后确定自己的毕业类型，并在这里确认。</span>
					</div>
				</div>
				<div class="g_6 contents_options">
					<!-- <div class="simple_buttons">
						<div class="bwIcon i_16_help">Help</div>
					</div> -->
				</div>

				<div class="g_12 separator">
					<span></span>
				</div>

				<!--  毕业方式展示框-->
				<div class="g_12">
					<div class="widget_header">
						<h4 class="widget_header_title wwIcon i_16_checkbox">与指导老师商量后，直接选择你的毕业方式</h4>
						<span class="tipsMessage"><%=byfsMessage%></span>
					</div>
					<div class="widget_contents noPadding twCheckbox">
						<table class="tables">
							<thead>
								<tr>
									<th width="80%">毕业方式</th>
									<th width="20%">选择</th>
								</tr>
							</thead>
							<tbody>
								<%
									for (int i = 0, l = byfsList.size(); i < l; i++) {
								%>
								<tr>
									<td><%=byfsList.get(i).getName()%></td>
									<td style="text-align:center;"><input value="<%=byfsList.get(i).getId()%>"
										type="hidden" class="byfsCheckbox" name="byfs" />
										<div class="simple_buttons" style="width:66px;">
											<div class="choose">选&nbsp;择</div>
											<div class="chooseAjax">&nbsp;&nbsp;&nbsp;</div>
										</div></td>
								</tr>
								<%
									}
								%>
							</tbody>
						</table>
					</div>
				</div>

				<div class="g_12 separator">
					<span></span>
				</div>
				<!-- 小组区域 -->
				<%
					if (isGroup == 0) {//就是没有组小队
				%>
				<div class="g_12">
					<div class="widget_header">
						<h4 class="widget_header_title wwIcon i_16_tables">创建毕设小组，与同学一起完成任务</h4>
						<div
							style="float: right; display: inline-block; height: 33px; padding-top: 5px;">
							<div class="simple_buttons">
								<div id="createG">创&nbsp;建</div>
								<div class="createGajax">&nbsp;&nbsp;&nbsp;</div>
							</div>
						</div>
					</div>
				</div>
				<%
					} else {//已经创建了小组，可以添加组员了
						List<Student> groupStudents = ServiceFactory.getStudentServiceInstance()
								.getGroupStudents(student.getNumber());
				%>
				<div class="g_12">
					<div class="widget_header">
						<h4 class="widget_header_title wwIcon i_16_tables">小组成员</h4>
						<div
							style="float: right; display: inline-block; height: 33px; padding-top: 5px;">
							<input class="simple_field1" type="text" placeholder="输入完整学号"
								id="groupXH">&nbsp;
							<div class="simple_buttons">
								<div id="addGB">添&nbsp;加</div>
								<div class="addGBajax">&nbsp;&nbsp;&nbsp;</div>
							</div>
						</div>
					</div>
					<div class="widget_contents noPadding">
						<table class="tables">
							<thead>
								<tr>
									<th>学号</th>
									<th>姓名</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody id="groupTable">
								<%
									for (int i = 0, l = groupStudents.size(); i < l; i++) {
								%>
								<tr>
									<td><%=groupStudents.get(i).getNumber()%></td>
									<td><%=groupStudents.get(i).getName()%></td>
									<td style="text-align: center"><input type="hidden"
										class="hiddenVal"
										value="<%=groupStudents.get(i).getNumber()%>" /> <%
 	if (!groupStudents.get(i).getNumber().equals(student.getNumber())) {
 %> <input type="button" value="移 除"
										class="submitIt simple_buttons removeGB"
										onclick="removeGroup(this)"> <%
 	}
 %></td>
								</tr>
								<%
									}
								%>
							</tbody>
						</table>
					</div>
				</div>
				<%
					}
				%>

			</div>
		</div>
	</div>

	<!-- 底部版权区 -->
	<%@ include file="../common/footer.jspf"%>

	<!-- 页面操作js -->
	<script src="student/Javascript/byfs.js"></script>
</body>
</html>