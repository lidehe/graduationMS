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
<link rel="shortcut icon" href="dcld/Images/student.ico" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>全校信息统计</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<!-- The Main CSS File -->
<link rel="stylesheet" href="dcld/CSS/normalize.css" />
<link rel="stylesheet" href="dcld/CSS/tipsy.css" />
<link rel="stylesheet" href="dcld/CSS/colorpicker.css" />
<link rel="stylesheet" href="dcld/CSS/jquery.cleditor.css" />
<link rel="stylesheet" href="dcld/CSS/fullcalendar.css" />
<link rel="stylesheet" href="dcld/CSS/colorbox.css" />
<link rel="stylesheet" href="dcld/CSS/style.css" />

<link rel="stylesheet" href="dcld/CSS/dcld.css" />


<!-- jQuery -->
<script src="dcld/Javascript/jQuery/jquery-1.7.2.min.js"></script>

<!-- Flot -->
<script src="dcld/Javascript/Flot/jquery.flot.js"></script>
<script src="dcld/Javascript/Flot/jquery.flot.resize.js"></script>
<script src="dcld/Javascript/Flot/jquery.flot.pie.js"></script>
<!-- DataTables -->
<script src="dcld/Javascript/DataTables/jquery.dataTables.min.js"></script>
<!-- ColResizable -->
<script src="dcld/Javascript/ColResizable/colResizable-1.3.js"></script>
<!-- jQuryUI -->
<script src="dcld/Javascript/jQueryUI/jquery-ui-1.8.21.min.js"></script>
<!-- Uniform -->
<script src="dcld/Javascript/Uniform/jquery.uniform.js"></script>
<!-- Tipsy -->
<script src="dcld/Javascript/Tipsy/jquery.tipsy.js"></script>
<!-- Elastic -->
<script src="dcld/Javascript/Elastic/jquery.elastic.js"></script>
<!-- ColorPicker -->
<script src="dcld/Javascript/ColorPicker/colorpicker.js"></script>
<!-- SuperTextarea -->
<script src="dcld/Javascript/SuperTextarea/jquery.supertextarea.min.js"></script>
<!-- UISpinner -->
<script src="dcld/Javascript/UISpinner/ui.spinner.js"></script>
<!-- MaskedInput -->
<script src="dcld/Javascript/MaskedInput/jquery.maskedinput-1.3.js"></script>
<!-- ClEditor -->
<script src="dcld/Javascript/ClEditor/jquery.cleditor.js"></script>
<!-- Full Calendar -->
<script src="dcld/Javascript/FullCalendar/fullcalendar.js"></script>
<!-- Color Box -->
<script src="dcld/Javascript/ColorBox/jquery.colorbox.js"></script>
<!-- Kanrisha Script -->
<script src="dcld/Javascript/kanrisha.js"></script>

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
					<h3 class="i_16_dashboard tab_label">信息统计</h3>
					<div>
						<span class="label">可以看到各个院系的进度统计信息，和成绩统计信息</span>
					</div>
				</div>
				<div class="g_6 contents_options">
					
				</div>

				<div class="g_12 separator">
					<span></span>
				</div>

				<!-- 内容主要区域 -->
				<%@ include file="../../common/jsp/xiaoTable.jspf"%>






			</div>
		</div>
	</div>

	<!-- 底部版权区 -->
	<%@ include file="../common/footer.jspf"%>

	<!-- 页面操作js -->

</body>
</html>