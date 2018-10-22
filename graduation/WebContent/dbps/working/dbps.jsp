<%@page import="domain.Pypsbx"%>
<%@page import="domain.Dbpsbx"%>
<%@page import="domain.Dbzl"%>
<%@page import="domain.Student"%>
<%@page import="java.util.List"%>
<%@page import="factory.ServiceFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";//因为用了服务器端跳转；所以这边要重定向一下path；保证js和css可以正确载入

	Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");

	List<Dbzl> dbs = ServiceFactory.getTeacherServiceInstance().teacherGetAllDbz(teacher);

	List<Dbpsbx> dbpsbxs = ServiceFactory.getTeacherServiceInstance().getDbpsbxOrderId();
	List<Pypsbx> pypsbxs = ServiceFactory.getTeacherServiceInstance().getPypsbxOrderId();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link rel="shortcut icon" href="dbps/Images/student.ico" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>答辩评审</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<!-- The Main CSS File -->
<link rel="stylesheet" href="dbps/CSS/normalize.css" />
<link rel="stylesheet" href="dbps/CSS/tipsy.css" />
<link rel="stylesheet" href="dbps/CSS/colorpicker.css" />
<link rel="stylesheet" href="dbps/CSS/jquery.cleditor.css" />
<link rel="stylesheet" href="dbps/CSS/fullcalendar.css" />
<link rel="stylesheet" href="dbps/CSS/colorbox.css" />
<link rel="stylesheet" href="dbps/CSS/style.css" />

<link rel="stylesheet" href="dbps/CSS/dbps.css" />

<!-- jQuery -->
<script src="dbps/Javascript/jQuery/jquery-1.7.2.min.js"></script>

<!-- Flot -->
<script src="dbps/Javascript/Flot/jquery.flot.js"></script>
<script src="dbps/Javascript/Flot/jquery.flot.resize.js"></script>
<script src="dbps/Javascript/Flot/jquery.flot.pie.js"></script>
<!-- DataTables -->
<script src="dbps/Javascript/DataTables/jquery.dataTables.min.js"></script>
<!-- ColResizable -->
<script src="dbps/Javascript/ColResizable/colResizable-1.3.js"></script>
<!-- jQuryUI -->
<script src="dbps/Javascript/jQueryUI/jquery-ui-1.8.21.min.js"></script>
<!-- Uniform -->
<script src="dbps/Javascript/Uniform/jquery.uniform.js"></script>
<!-- Tipsy -->
<script src="dbps/Javascript/Tipsy/jquery.tipsy.js"></script>
<!-- Elastic -->
<script src="dbps/Javascript/Elastic/jquery.elastic.js"></script>
<!-- ColorPicker -->
<script src="dbps/Javascript/ColorPicker/colorpicker.js"></script>
<!-- SuperTextarea -->
<script src="dbps/Javascript/SuperTextarea/jquery.supertextarea.min.js"></script>
<!-- UISpinner -->
<script src="dbps/Javascript/UISpinner/ui.spinner.js"></script>
<!-- MaskedInput -->
<script src="dbps/Javascript/MaskedInput/jquery.maskedinput-1.3.js"></script>
<!-- ClEditor -->
<script src="dbps/Javascript/ClEditor/jquery.cleditor.js"></script>
<!-- Full Calendar -->
<script src="dbps/Javascript/FullCalendar/fullcalendar.js"></script>
<!-- Color Box -->
<script src="dbps/Javascript/ColorBox/jquery.colorbox.js"></script>
<!-- Kanrisha Script -->
<script src="dbps/Javascript/kanrisha.js"></script>

<link rel="stylesheet" type="text/css" href="common/css/tips.css">
<script src="common/js/tips.js"></script>
</head>

<body>
	<%@ include file="../../common/jsp/password.jspf"%>

	<!-- 头部导航 -->
	<%@ include file="../common/header.jspf"%>


	<div class="wrapper contents_wrapper">




		<div class="contents" style="margin: 0 auto; float: none;">
			<!-- 		中间内容区 -->
			<div class="grid_wrapper">


				<div class="g_6 contents_header">
					<h3 class="i_16_dashboard tab_label">答辩评审组录入成绩</h3>
					<div>
						<span class="label">与在场的老师商讨好负责的表格，不要填串。</span>
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
						<h4 class="widget_header_title wwIcon i_16_tabs">
							本答辩组成员：<span class="dbzcy"></span>
						</h4>
					</div>
					<div style="width:830px;" class="widget_contents noPadding">
						<div class="line_grid">
							<div class="g_3">
								<span class="label">答辩评审组：</span>
							</div>
							<div class="g_9">
								<select class="simple_form" id="dbpsz">
								<option value="0" selected="selected">--请选择--</option>
									<%
										for (int i = 0, l = dbs.size(); i < l; i++) {
									%>
									<option value="<%=dbs.get(i).getId()%>"><%="评审组" + (i + 1)%></option>
									<%
										}
									%>
								</select>
								<div class="ajaxLoad1" title="载入答辩组数据">&nbsp;&nbsp;</div>
							</div>
						</div>
						<div class="line_grid">
							<div class="g_3">
								<span class="label">学生：</span>
							</div>
							<div class="g_9">
								<select class="simple_form" id="studentNum">
									<option value="0" selected="selected">--请选择--</option>
								</select>
								<div class="ajaxLoad2" title="载入学生数据">&nbsp;&nbsp;</div>
							</div>
						</div>
					</div>
				</div>
				<div class="g_12 separator">
					<span></span>
				</div>




				<div class="g_12" id="table_wTabs">
					<div class="widget_header wwOptions">
						<h4 class="widget_header_title wwIcon i_16_forms ">录入学生对应的成绩</h4>
						<ul class="w_Tabs">
							<li><a href="#table_wTabs-1" title="答辩评审表">答辩评审表</a></li>
							<li><a href="#table_wTabs-2" title="评阅评审表">评阅评审表</a></li>
						</ul>
					</div>
					<div class="widget_contents noPadding">
						<div id="table_wTabs-1">
							<div class="widget_contents noPadding">
								<div class="line_grid line_border_no">
									<div class="g_3">
										<span class="label">答辩成绩表：</span>
									</div>
								</div>
								<form id="dbpsbForm">
									<%
										for (int i = 0, l = dbpsbxs.size(); i < l; i++) {
									%>
									<%
										if (dbpsbxs.get(i).getStatus() == 0) {
									%>
									<div class="line_grid">
										<div class="g_9">
											<span class="label"><%=dbpsbxs.get(i).getText()%></span>
										</div>
										<div class="g_3">
											<input class="simple_field dbpsb" name="dbpsb<%=i%>"
												placeholder="满分<%=dbpsbxs.get(i).getMf()%>" type="text" />
										</div>
									</div>
									<%
										} else {
									%>
									<div class="line_grid">
										<div class="g_2">
											<span class="label"><%=dbpsbxs.get(i).getText()%></span>
										</div>
										<div class="g_10">
											<textarea maxlength="400" class="simple_field elastic dbpsb"
												name="dbpsb<%=i%>"></textarea>
										</div>
									</div>
									<%
										}
									%>
									<%
										}
									%>
								</form>

								<div class="line_grid">
									<div class="g_3">
										<span class="label">确定：</span>
									</div>
									<div class="g_9">
										<div class="simple_buttons">
											<div id="dbsbB">提&nbsp;交</div>
											<div class="ajaxAnimation">&nbsp;&nbsp;&nbsp;</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div id="table_wTabs-2">
							<div class="widget_contents noPadding">
								<div class="line_grid line_border_no">
									<div class="g_3">
										<span class="label">评阅成绩表：</span>
									</div>
								</div>
								<form id="pypsbForm">
									<%
										for (int i = 0, l = pypsbxs.size(); i < l; i++) {
									%>
									<%
										if (pypsbxs.get(i).getStatus() == 0) {
									%>
									<div class="line_grid">
										<div class="g_9">
											<span class="label"><%=pypsbxs.get(i).getText()%></span>
										</div>
										<div class="g_3">
											<input class="simple_field pypsb" name="pypsb<%=i%>"
												placeholder="满分<%=pypsbxs.get(i).getMf()%>" type="text" />
										</div>
									</div>
									<%
										} else {
									%>
									<div class="line_grid">
										<div class="g_2">
											<span class="label"><%=pypsbxs.get(i).getText()%></span>
										</div>
										<div class="g_10">
											<textarea maxlength="400" class="simple_field elastic pypsb"
												name="pypsb<%=i%>"></textarea>
										</div>
									</div>
									<%
										}
									%>
									<%
										}
									%>
								</form>

								<div class="line_grid">
									<div class="g_3">
										<span class="label">确定：</span>
									</div>
									<div class="g_9">
										<div class="simple_buttons">
											<div id="pypsbB">提&nbsp;交</div>
											<div class="ajaxAnimation">&nbsp;&nbsp;&nbsp;</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>






			</div>
		</div>
	</div>

	<!-- 底部版权区 -->
	<%@ include file="../common/footer.jspf"%>

	<!-- 页面操作js -->
	<script src="dbps/Javascript/dbps.js"></script>

</body>
</html>