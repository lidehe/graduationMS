<%@page import="domain.Student"%>
<%@page import="factory.ServiceFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
	+ path + "/";//因为用了服务器端跳转；所以这边要重定向一下path；保证js和css可以正确载入
	
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link rel="shortcut icon" href="student/Images/student.ico" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>师生交流</title>
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
<script src="student/Javascript/SuperTextarea/jquery.supertextarea.min.js"></script>
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
<%@ include file="../../common/jsp/password.jspf" %>

	<!-- 头部导航 -->
	<%@ include file="../common/header.jspf"%>


	<div class="wrapper contents_wrapper">

		


		<div class="contents" style="margin:0 auto;float:none;">
			<!-- 		中间内容区 -->
			<div class="grid_wrapper">


				<div class="g_6 contents_header">
					<h3 class="i_16_dashboard tab_label">师生交流记录</h3>
					<div>
						<span class="label">向指导老师提出问题</span>
					</div>
				</div>

				<div class="g_12 separator">
					<span></span>
				</div>
				
				<!-- 内容主要区域 -->
				<div class="g_12">
					<!-- chats -->
					<div class="widget_header">
						<h4 class="widget_header_title wwIcon i_16_chats">交流记录</h4>
					</div>
					<div class="widget_contents noPadding">
						<div class="myDiv" style="min-height: 400px;"></div>
						<div class="dtPagination pCenter">
							<a id="previous" href="javascript:voie(0);">前一页</a> <a id="next"
								href="javascript:voie(0);">后一页</a>
						</div>
					</div>
				</div>
				
				<div class="g_12 separator">
					<span></span>
				</div>


				<div class="g_12">
					<div class="widget_header">
						<h4 class="widget_header_title wwIcon i_16_cHorizontal">在此处回复指导老师！</h4>
					</div>
					<div class="widget_contents noPadding">
						<form id="zdForm">
							<div class="line_grid">
								<div class="g_3">
									<span class="label">内容</span>
								</div>
								<div class="g_9">
									<textarea name="text" id="Ltext" class="simple_field tooltip"
										title="内容限制在400个字以内"></textarea>
								</div>
							</div>

							<div class="line_grid">
								<div class="g_3">
									<span class="label">确认发送</span>
								</div>
								<div class="g_9">
									<div class="simple_buttons">
										<div id="LwButton">提&nbsp;交</div>
										<div class="ajaxAnimation">&nbsp;&nbsp;&nbsp;</div>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
				
				
				
				

			</div>
		</div>
	</div>

	<!-- 底部版权区 -->
	<%@ include file="../common/footer.jspf"%>
	
<!-- 页面操作js -->
<script src="student/Javascript/ssjl.js"></script>

</body>
</html>