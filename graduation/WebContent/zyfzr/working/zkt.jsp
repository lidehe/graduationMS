<%@page import="domain.Kt"%>
<%@page import="factory.ServiceFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";//因为用了服务器端跳转；所以这边要重定向一下path；保证js和css可以正确载入
	int sideInd = 0;//侧边栏的序号，需要确定的赋值；与sidebar对应

	Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
	List<Kt> kts = ServiceFactory.getTeacherServiceInstance().zyfzrGetAllKts(teacher.getFzr());
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link rel="shortcut icon" href="zyfzr/Images/student.ico" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>审核课题</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<!-- The Main CSS File -->
<link rel="stylesheet" href="zyfzr/CSS/normalize.css" />
<link rel="stylesheet" href="zyfzr/CSS/tipsy.css" />
<link rel="stylesheet" href="zyfzr/CSS/colorpicker.css" />
<link rel="stylesheet" href="zyfzr/CSS/jquery.cleditor.css" />
<link rel="stylesheet" href="zyfzr/CSS/fullcalendar.css" />
<link rel="stylesheet" href="zyfzr/CSS/colorbox.css" />
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

<link rel="stylesheet" type="text/css" href="common/css/tips.css">
<script src="common/js/tips.js"></script>
</head>

<body>
<%@ include file="../../common/jsp/ZyNotice.jspf" %>
<%@ include file="../../common/jsp/password.jspf" %>

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
						<span class="label">审核指导老师和学生申报的课题</span>
					</div>
				</div>
				<div class="g_6 contents_options">
					
				</div>

				<div class="g_12 separator">
					<span></span>
				</div>

				<!-- 内容主要区域 -->
				<div class="g_12">
					<div class="widget_header wwOptions">
						<h4 class="widget_header_title wwIcon i_16_data">课题状况</h4>
					</div>
					<div class="widget_contents noPadding">
						<table class="datatable tables">
							<thead>
								<tr>
									<th>标题</th>
									<th>描述</th>
									<th>指导老师</th>
									<th>审核</th>
								</tr>
							</thead>
							<tbody>
								<%
									for (int i = 0, l = kts.size(); i < l; i++) {
								%>
								<tr>
									<td><%=kts.get(i).getName()%></td>
									<td><%=kts.get(i).getText()%></td>
									<td><%=kts.get(i).getGonghao()%></td>
									<td style="text-align: center;">
										<%
											if (kts.get(i).getFzrsh() == 0) {
										%><div class="simple_buttons">
											<input type="hidden" class="hiddenId"
												value="<%=kts.get(i).getId()%>" />
											<div onclick="shenheK(this)" class="shenheB">通&nbsp;过</div>
										</div>
										<div class="simple_buttons">
											<input type="hidden" class="hiddenId"
												value="<%=kts.get(i).getId()%>" />
											<div onclick="refuseK(this)" class="refuseB">拒&nbsp;绝</div>
										</div> <%
 	} else if (kts.get(i).getFzrsh() == 1) {
 %> 已通过 <%
 	} else if (kts.get(i).getFzrsh() == 2) {
 %> 已拒绝<%
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
	<script type="text/javascript" src="zyfzr/Javascript/zkt.js"></script>
</body>
</html>