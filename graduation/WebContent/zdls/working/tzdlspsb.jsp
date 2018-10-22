<%@page import="domain.Sxcjx"%>
<%@page import="domain.Zdlspsbx"%>
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
	List<Student> students = ServiceFactory.getTeacherServiceInstance().getTeachersStudentOrderXuehao(teacher);
	List<Zdlspsbx> zdlspsbxs = ServiceFactory.getTeacherServiceInstance().getZdlspsb();
	List<Sxcjx> sxcjxs = ServiceFactory.getTeacherServiceInstance().getSxcjx();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link rel="shortcut icon" href="zdls/Images/student.ico" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>指导老师评审成绩</title>
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
					<h3 class="i_16_dashboard tab_label">指导老师评审成绩</h3>
					<div>
						<span class="label">录入指导老师评审成绩，作为学生最后成绩的一部分</span>
					</div>
				</div>
				<div class="g_6 contents_options">
					
				</div>

				<div class="g_12 separator">
					<span></span>
				</div>

				<!-- 内容主要区域 -->
				<div class="g_12" id="table_wTabs">
					<div class="widget_header wwOptions">
						<h4 class="widget_header_title wwIcon i_16_forms">录入学生的成绩</h4>
						<ul class="w_Tabs">
							<li><a href="#table_wTabs-1" title="指导老师评审表">指导老师评审表</a></li>
							<li><a href="#table_wTabs-2" title="实习成绩评审表">实习成绩评审表</a></li>
						</ul>
					</div>
					<div style="width:830px;" class="widget_contents noPadding">
						<div class="line_grid">
							<div class="g_3">
								<span class="label">学生：</span>
							</div>
							<div class="g_9">
								<select class="simple_form" id="studentNum">
									<option value="0" selected="selected">--请选择--</option>
									<%
										for (int i = 0, l = students.size(); i < l; i++) {
									%>
									<option value="<%=students.get(i).getNumber()%>"><%=students.get(i).getName()%></option>
									<%
										}
									%>
								</select>
								<div class="ajaxLoad" title="载入学生数据">&nbsp;&nbsp;</div>
							</div>
						</div>
						<div class="line_grid line_border_no">
							<div class="g_3">
								<span class="label">成绩表：</span>
							</div>
						</div>



						<div id="table_wTabs-1">
							<form id="zdlspsbForm">
								<%
									for (int i = 0, l = zdlspsbxs.size(); i < l; i++) {
								%>
								<%
									if (zdlspsbxs.get(i).getStatus() == 0) {
								%>
								<div class="line_grid line_border_no">
									<div class="g_9">
										<span class="label"><%=zdlspsbxs.get(i).getText()%></span>
									</div>
									<div class="g_3">
										<input class="simple_field zdlspsb" name="zdlspsb<%=i%>"
											placeholder="满分<%=zdlspsbxs.get(i).getMf()%>" type="text" />
									</div>
								</div>
								<%
									} else {
								%>
								<div class="line_grid line_border_no">
									<div class="g_2">
										<span class="label"><%=zdlspsbxs.get(i).getText()%></span>
									</div>
									<div class="g_10">
										<textarea maxlength="400" class="simple_field elastic zdlspsb"
											name="zdlspsb<%=i%>"></textarea>
									</div>
								</div>
								<%
									}
								%>
								<%
									}
								%>
								<div class="line_grid line_border_no">
									<div class="g_9">
										<span class="label">是否参加首次答辩</span>
									</div>
									<div class="g_3">
										<select class="simple_form" id="isdb" name="isdb">
											<option value="Web Designer" value="1" selected="selected">是</option>
											<option value="Web Developer" value="0">否</option>
										</select>
									</div>
								</div>


								<div class="line_grid">
									<div class="g_3">
										<span class="label">确定：</span>
									</div>
									<div class="g_9">
										<div class="simple_buttons">
											<div id="zdlspsbB">提&nbsp;交</div>
											<div class="ajaxAnimation">&nbsp;&nbsp;&nbsp;</div>
										</div>
									</div>
								</div>
							</form>
						</div>



						<div id="table_wTabs-2">
							<form id="sxcjpsbForm">
								<%
									for (int i = 0, l = sxcjxs.size(); i < l; i++) {
								%>
								<%
									if (sxcjxs.get(i).getStatus() == 0) {
								%>
								<div class="line_grid line_border_no">
									<div class="g_9">
										<span class="label"><%=sxcjxs.get(i).getText()%></span>
									</div>
									<div class="g_3">
										<input class="simple_field sxcj" name="sxcj<%=i%>"
											placeholder="满分<%=sxcjxs.get(i).getMf()%>" type="text" />
									</div>
								</div>
								<%
									} else {
								%>
								<div class="line_grid line_border_no">
									<div class="g_2">
										<span class="label"><%=sxcjxs.get(i).getText()%></span>
									</div>
									<div class="g_10">
										<textarea maxlength="400" class="simple_field elastic sxcj"
											name="sxcj<%=i%>"></textarea>
									</div>
								</div>
								<%
									}
								%>
								<%
									}
								%>


								<div class="line_grid">
									<div class="g_3">
										<span class="label">确定：</span>
									</div>
									<div class="g_9">
										<div class="simple_buttons">
											<div id="sxcjpsbB">提&nbsp;交</div>
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
	</div>

	<!-- 底部版权区 -->
	<%@ include file="../common/footer.jspf"%>

	<!-- 页面操作js -->
	<script src="zdls/Javascript/zdlspsb.js"></script>

</body>
</html>