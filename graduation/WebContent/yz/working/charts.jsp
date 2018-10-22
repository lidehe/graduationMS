<%@page import="utils.FloatUtil"%>
<%@page import="domain.Gzzj"%>
<%@page import="domain.Student"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="domain.Yuanxi"%>
<%@page import="java.util.List"%>
<%@page import="factory.ServiceFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";//因为用了服务器端跳转；所以这边要重定向一下path；保证js和css可以正确载入

	int sideInd = 0;
	Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
	Yuanxi yuanxi = ServiceFactory.getTeacherServiceInstance().getYuanxiByTeacher(teacher);
	int zrs = ServiceFactory.getTeacherServiceInstance().getYxZrs(yuanxi);
	int xt = ServiceFactory.getTeacherServiceInstance().getYxXt(yuanxi);
	int jsrws = ServiceFactory.getTeacherServiceInstance().getyxJsRws(yuanxi);
	int tjktbg = ServiceFactory.getTeacherServiceInstance().getYxTjKtbg(yuanxi);
	int tjlw = ServiceFactory.getTeacherServiceInstance().getYxTjLw(yuanxi);
	int db = ServiceFactory.getTeacherServiceInstance().getYxDb(yuanxi);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link rel="shortcut icon" href="yz/Images/student.ico" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title><%=yuanxi.getName()%></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<!-- The Main CSS File -->
<link rel="stylesheet" href="yz/CSS/normalize.css" />
<link rel="stylesheet" href="yz/CSS/tipsy.css" />
<link rel="stylesheet" href="yz/CSS/colorpicker.css" />
<link rel="stylesheet" href="yz/CSS/jquery.cleditor.css" />
<link rel="stylesheet" href="yz/CSS/fullcalendar.css" />
<link rel="stylesheet" href="yz/CSS/colorbox.css" />
<link rel="stylesheet" href="yz/CSS/style.css" />

<link rel="stylesheet" href="yz/CSS/cj.css" />

<!-- jQuery -->
<script src="yz/Javascript/jQuery/jquery-1.7.2.min.js"></script>

<!-- Flot -->
<script src="yz/Javascript/Flot/jquery.flot.js"></script>
<script src="yz/Javascript/Flot/jquery.flot.resize.js"></script>
<script src="yz/Javascript/Flot/jquery.flot.pie.js"></script>
<!-- DataTables -->
<script src="yz/Javascript/DataTables/jquery.dataTables.min.js"></script>
<!-- ColResizable -->
<script src="yz/Javascript/ColResizable/colResizable-1.3.js"></script>
<!-- jQuryUI -->
<script src="yz/Javascript/jQueryUI/jquery-ui-1.8.21.min.js"></script>
<!-- Uniform -->
<script src="yz/Javascript/Uniform/jquery.uniform.js"></script>
<!-- Tipsy -->
<script src="yz/Javascript/Tipsy/jquery.tipsy.js"></script>
<!-- Elastic -->
<script src="yz/Javascript/Elastic/jquery.elastic.js"></script>
<!-- ColorPicker -->
<script src="yz/Javascript/ColorPicker/colorpicker.js"></script>
<!-- SuperTextarea -->
<script src="yz/Javascript/SuperTextarea/jquery.supertextarea.min.js"></script>
<!-- UISpinner -->
<script src="yz/Javascript/UISpinner/ui.spinner.js"></script>
<!-- MaskedInput -->
<script src="yz/Javascript/MaskedInput/jquery.maskedinput-1.3.js"></script>
<!-- ClEditor -->
<script src="yz/Javascript/ClEditor/jquery.cleditor.js"></script>
<!-- Full Calendar -->
<script src="yz/Javascript/FullCalendar/fullcalendar.js"></script>
<!-- Color Box -->
<script src="yz/Javascript/ColorBox/jquery.colorbox.js"></script>
<!-- Kanrisha Script -->
<script src="yz/Javascript/kanrisha.js"></script>

<link rel="stylesheet" type="text/css" href="common/css/tips.css">
<script src="common/js/tips.js"></script>
</head>

<body>
	<%@ include file="../../common/jsp/YxNotice.jspf"%>
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
					<h3 class="i_16_dashboard tab_label"><%=yuanxi.getName()%></h3>
					<div>
						<span class="label">本院系的学生信息统计</span>
					</div>
				</div>
				<div class="g_6 contents_options">
					
				</div>

				<div class="g_12 separator">
					<span></span>
				</div>

				<div class="g_12">
					<div class="widget_header">
						<h4 class="widget_header_title wwIcon i_16_progress">学生阶段信息统计</h4>
					</div>
					<div class="widget_contents noPadding">
						<div class="line_grid">
							<div class="g_3">
								<span class="label">本院系总人数（<%=zrs%>）
								</span>
							</div>
							<div class="g_9">
								<div class="progress zrsProgress scBlue"></div>
							</div>
						</div>
						<div class="line_grid">
							<div class="g_3">
								<span class="label">已选题人数（<%=xt%>）
								</span>
							</div>
							<div class="g_9">
								<div class="progress pwXtAnimate scBlue"></div>
								<div class="field_notice">
									Value: <span class="must paValue1">0%</span>
								</div>
							</div>
						</div>
						<div class="line_grid">
							<div class="g_3">
								<span class="label">已接收任务书人数（<%=jsrws%>）
								</span>
							</div>
							<div class="g_9">
								<div class="progress pwRwsAnimate scBlue"></div>
								<div class="field_notice">
									Value: <span class="must paValue2">0%</span>
								</div>
							</div>
						</div>
						<div class="line_grid">
							<div class="g_3">
								<span class="label">已提交开题报告人数（<%=tjktbg%>）
								</span>
							</div>
							<div class="g_9">
								<div class="progress pwKtbgAnimate scBlue"></div>
								<div class="field_notice">
									Value: <span class="must paValue3">0%</span>
								</div>
							</div>
						</div>
						<div class="line_grid">
							<div class="g_3">
								<span class="label">已提交论文人数（<%=tjlw%>）
								</span>
							</div>
							<div class="g_9">
								<div class="progress pwLwAnimate scBlue"></div>
								<div class="field_notice">
									Value: <span class="must paValue4">0%</span>
								</div>
							</div>
						</div>
						<div class="line_grid">
							<div class="g_3">
								<span class="label">已答辩人数（<%=db%>）
								</span>
							</div>
							<div class="g_9">
								<div class="progress pwDbAnimate scBlue"></div>
								<div class="field_notice">
									Value: <span class="must paValue5">0%</span>
								</div>
							</div>
						</div>

					</div>
				</div>
				<div class="g_12 separator">
					<span></span>
				</div>
				<div class="g_12">
					<div class="widget_header">
						<h4 class="widget_header_title wwIcon i_16_pie">本院系成绩比例饼图</h4>
					</div>
					<div class="widget_contents">
						<div class="cj_pie_charts"></div>
					</div>
				</div>




			</div>
		</div>
	</div>

	<!-- 底部版权区 -->
	<%@ include file="../common/footer.jspf"%>

	<!-- 页面操作js -->
	<script src="yz/Javascript/charts.js"></script>
	<script>
		$(function() {
			getYxCj(
	<%=yuanxi.getId()%>
		);
		
		<%if (zrs == 0) {%>
		Xt("0%");
		Rws("0%");
		Ktbg("0%");
		Lw("0%");
		Db("0%");
		<%} else {%>
		Xt("<%=FloatUtil.floatSaveTwoD(xt * 100 / zrs) + "%"%>");
		Rws("<%=FloatUtil.floatSaveTwoD(jsrws * 100 / zrs) + "%"%>");
		Ktbg("<%=FloatUtil.floatSaveTwoD(tjktbg * 100 / zrs) + "%"%>");
		Lw("<%=FloatUtil.floatSaveTwoD(tjlw * 100 / zrs) + "%"%>");
		Db("<%=FloatUtil.floatSaveTwoD(db * 100 / zrs) + "%"%>");
	<%}%>
		});
	</script>

</body>
</html>