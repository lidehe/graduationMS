<%@page import="java.sql.Date"%>
<%@page import="domain.Jdxcgs"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page import="domain.Jdxcg"%>
<%@page import="domain.Student"%>
<%@page import="factory.ServiceFactory"%>
<%@page import="domain.Byfs"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";//因为用了服务器端跳转；所以这边要重定向一下path；保证js和css可以正确载入
	int sideInd = 3;//侧边栏的序号，需要确定的赋值；与sidebar对应

	Student student = (Student) session.getAttribute("student");
	int byfsId = student.getType();
	Byfs byfs = ServiceFactory.getStudentServiceInstance().getStudentByfs(byfsId);

	Jdxcg jdxcg = ServiceFactory.getStudentServiceInstance().getStudentJdxcg(student);
	List<Jdxcgs> jdxcgss = ServiceFactory.getStudentServiceInstance().getJdxcgs(student);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link rel="shortcut icon" href="student/Images/student.ico" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title><%=byfs.getBa()%></title>
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
					<h3 class="i_16_dashboard tab_label"><%=byfs.getBa()%>
						<%
							if (byfs.getStart3() != null && byfs.getEnd3() != null)
								out.print("(" + new Date(byfs.getStart3().getTime()) + "~" + new Date(byfs.getEnd3().getTime()) + ")");
						%>
					</h3>
					<div>
						<span class="label"><%=byfs.getJiu()%></span>
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

				<!-- 内容主要区域 -->
				<div class="g_12">
					<div class="widget_header">
						<h4 class="widget_header_title wwIcon i_16_forms">
							请按照指导老师要求提交阶段性成果，不超过20M的PDF文件！
						</h4>
					</div>
					<div class="widget_contents noPadding">
						<%
							if (jdxcg != null && jdxcg.getStatus() != 0) {
						%>
						<div class="line_grid">
							<div class="g_3">
								<span class="label">阶段性成果：</span>
							</div>
							<div class="g_9">
								<%
									if (jdxcg != null && jdxcg.getStatus() == 1) {
								%>
								<a target="_blank" title="阅览" href="preview.do?meth=jdxcg"><%=jdxcg.getFileName()%></a>
								&nbsp;&nbsp;&nbsp;&nbsp;<a target="_blank" href="jdxcgLoad">下载</a>
								<%
									}
								%>
							</div>
						</div>
						<%
							}
						%>
						<div class="line_grid">
							<form id="jdxcgwj" enctype="multipart/form-data">
								<div class="g_3">
									<span class="label">上传文件：</span>
								</div>
								<div class="g_6">
									<input type="file" id="wj" name="wj" accept="application/pdf"
										class="simple_form" />
								</div>
								<div class="g_3" style="text-align: center;">
									<div class="simple_buttons">
										<%
											if (jdxcg != null && jdxcg.getStatus() == 1) {
										%>
										<div id="jdxcgB">覆&nbsp;盖</div>
										<%
											} else {
										%>
										<div id="jdxcgB">上&nbsp;传</div>
										<%
											}
										%>
										<div class="addGBajax">&nbsp;&nbsp;&nbsp;</div>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>


				<%
					if (jdxcg != null && jdxcg.getStatus() == 1) {
				%>
				<div class="g_12 separator">
					<span></span>
				</div>
				<div class="g_12">
					<div class="widget_header">
						<h4 class="widget_header_title wwIcon i_16_forms">阶段性成果附件，每个不超过20M，最多三个</h4>
					</div>
					<div class="widget_contents noPadding newLine">

						<%
							for (int i = 0, l = jdxcgss.size(); i < l; i++) {
						%>
						<div class="line_grid">
							<form class="jdxcgswj" enctype="multipart/form-data">
								<input type="hidden" name="jId"
									value="<%=jdxcgss.get(i).getId()%>" />
								<div class="g_4">
									<span class="label"><%=jdxcgss.get(i).getFileName()%></span>
								</div>
								<div class="g_4">
									<input type="file" name="wj"
										accept=".pdf,.PDF,.zip,.ZIP,.rar,.RAR,.doc,.DOC,.docx,.DOCX"
										class="simple_form fjWj" />
								</div>
								<div class="g_2" style="text-align: center;">
									<div class="simple_buttons">
										<div onclick="uploadJdxcgs(this)" class="jdxcgB1">覆&nbsp;盖</div>
										<div class="addGBajax">&nbsp;&nbsp;&nbsp;</div>
									</div>
								</div>
								<div class="g_2" style="text-align: center;">
									<div class="simple_buttons">
										<div
											onclick="window.open('preview.do?meth=jdxcgsLoad&id=<%=jdxcgss.get(i).getId()%>');">查&nbsp;看</div>
									</div>
								</div>
							</form>
						</div>
						<%
							}
						%>

						<div class="line_grid">
							<form class="jdxcgswj" enctype="multipart/form-data">
								<div class="g_4">
									<span class="label">新附件： </span>
								</div>
								<div class="g_4">
									<input type="file" name="wj"
										accept=".pdf,.PDF,.zip,.ZIP,.rar,.RAR,.doc,.DOC,.docx,.DOCX"
										class="simple_form fjWj" />
								</div>
								<div class="g_2" style="text-align: center;">
									<div class="simple_buttons">
										<div onclick="uploadJdxcgs(this)" class="jdxcgB1">上&nbsp;传</div>
										<div class="addGBajax">&nbsp;&nbsp;&nbsp;</div>
									</div>
								</div>
								<div class="g_2"></div>
							</form>
						</div>

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
	<script src="student/Javascript/jdxcg.js"></script>
</body>
</html>