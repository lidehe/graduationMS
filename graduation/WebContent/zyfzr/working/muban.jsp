<%@page import="factory.ServiceFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
	+ path + "/";//因为用了服务器端跳转；所以这边要重定向一下path；保证js和css可以正确载入
	int sideInd = 0;//侧边栏的序号，需要确定的赋值；与sidebar对应
	

	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link rel="shortcut icon" href="zyfzf/Images/student.ico" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>本届课题</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<!-- The Main CSS File -->
<link rel="stylesheet" href="zyfzr/CSS/style.css" />

<link rel="stylesheet" href="zyfzr/CSS/zyfzr.css" />

<!-- jQuery -->
<script src="zyfzr/Javascript/jQuery/jquery-1.7.2.min.js"></script>

<!-- Flot -->
<script src="zyfzr/Javascript/Flot/jquery.flot.js"></script>
<script src="zyfzr/Javascript/Flot/jquery.flot.resize.js"></script>
<script src="zyfzr/Javascript/Flot/jquery.flot.pie.js"></script>
<!-- DataTables -->
<script src="zyfzr/Javascript/DataTables/jquery.dataTables.min.js"></script>
<!-- ColResizable -->
<script src="zyfzr/Javascript/ColResizable/colResizable-1.3.js"></script>
<!-- jQuryUI -->
<script src="zyfzr/Javascript/jQueryUI/jquery-ui-1.8.21.min.js"></script>
<!-- Uniform -->
<script src="zyfzr/Javascript/Uniform/jquery.uniform.js"></script>
<!-- Tipsy -->
<script src="zyfzr/Javascript/Tipsy/jquery.tipsy.js"></script>
<!-- Elastic -->
<script src="zyfzr/Javascript/Elastic/jquery.elastic.js"></script>
<!-- ColorPicker -->
<script src="zyfzr/Javascript/ColorPicker/colorpicker.js"></script>
<!-- SuperTextarea -->
<script src="zyfzr/Javascript/SuperTextarea/jquery.supertextarea.min.js"></script>
<!-- UISpinner -->
<script src="zyfzr/Javascript/UISpinner/ui.spinner.js"></script>
<!-- MaskedInput -->
<script src="zyfzr/Javascript/MaskedInput/jquery.maskedinput-1.3.js"></script>
<!-- ClEditor -->
<script src="zyfzr/Javascript/ClEditor/jquery.cleditor.js"></script>
<!-- Full Calendar -->
<script src="zyfzr/Javascript/FullCalendar/fullcalendar.js"></script>
<!-- Color Box -->
<script src="zyfzr/Javascript/ColorBox/jquery.colorbox.js"></script>
<!-- Kanrisha Script -->
<script src="zyfzr/Javascript/kanrisha.js"></script>
</head>

<body>

	<!-- 头部导航 -->
	<%@ include file="../common/header.jspf"%>


	<div class="wrapper contents_wrapper">

		<!-- 左边侧栏 -->
		<%@ include file="../common/sidebar.jspf"%>


		<div class="contents">
			<!-- 		中间内容区 -->
			<div class="grid_wrapper">

				<div class="g_6 contents_header">
					<h3 class="i_16_dashboard tab_label">课题管理</h3>
					<div>
						<span class="label">上传课题，并管理学生申报的课题</span>
					</div>
				</div>
				<div class="g_6 contents_options">
					
				</div>

				<div class="g_12 separator">
					<span></span>
				</div>

				<!-- 内容主要区域 -->
				

			</div>
		</div>
	</div>

	<!-- 底部版权区 -->
	<%@ include file="../common/footer.jspf"%>
	
<!-- 页面操作js -->

</body>
</html>