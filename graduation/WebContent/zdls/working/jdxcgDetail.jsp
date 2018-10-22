<%@page import="java.text.SimpleDateFormat"%>
<%@page import="domain.Jdxcgs"%>
<%@page import="domain.Student"%>
<%@page import="java.util.List"%>
<%@page import="factory.ServiceFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";//因为用了服务器端跳转；所以这边要重定向一下path；保证js和css可以正确载入
	int sideInd = 3;//侧边栏的序号，需要确定的赋值；与sidebar对应

	Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
	String xuehao = request.getParameter("number");
	Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount(xuehao);
	List<Jdxcgs> jdxcgs2 = ServiceFactory.getTeacherServiceInstance().teacherStudentJdxcgss(xuehao);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link rel="shortcut icon" href="zdls/Images/student.ico" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title><%=student.getName()%>同学的阶段成果详情</title>
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




		<div class="contents" style="margin: 0 auto; float: none;">
			<!-- 		中间内容区 -->
			<div class="grid_wrapper">

				<div class="g_6 contents_header">
					<h3 class="i_16_dashboard tab_label"><%=student.getName()%>同学的阶段成果详情
					</h3>
					<div>
						<span class="label">查看学生提交的文件，并予以一些指导</span>
					</div>
				</div>
				<div class="g_6 contents_options">
					
				</div>

				<div class="g_12 separator">
					<span></span>
				</div>

				<!-- 内容主要区域 -->
				<div class="g_12">
					<div class="widget_contents noPadding">
						<div class="line_grid">
							<div class="g_6">
								<span class="label">阶段性成果：</span>
							</div>
							<div class="g_6">
								<a target="_blank"
									href="tjdxcg.do?meth=preview&number=<%=request.getParameter("number")%>"><input
									type="button" value="阅览" class="submitIt simple_buttons" /></a>
									<a target="_blank"
									href="tjdxcg.do?meth=load&number=<%=request.getParameter("number")%>"><input
									type="button" value="下载" class="submitIt simple_buttons" /></a>
							</div>
						</div>
						<%
							for (int x = 0, y = (jdxcgs2 == null ? 0 : jdxcgs2.size()); x < y; x++) {
						%>
						<div class="line_grid">
							<div class="g_6">
								<span class="label"><%=jdxcgs2.get(x).getFileName() %>：
								</span>
							</div>
							<div class="g_6">
								<a target="_blank"
									href="preview.do?meth=jdxcgs&id=<%=jdxcgs2.get(x).getId()%>"><input
									type="button" value="阅览" class="submitIt simple_buttons" /></a>
									<a target="_blank"
									href="preview.do?meth=jdxcgsLoad&id=<%=jdxcgs2.get(x).getId()%>"><input
									type="button" value="下载" class="submitIt simple_buttons" /></a>
							</div>
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
	<script src="zdls/Javascript/tjdxcg.js"></script>

</body>
</html>