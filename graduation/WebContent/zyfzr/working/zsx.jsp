<%@page import="domain.middle.Sxcjfs"%>
<%@page import="domain.Sxcjx"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="domain.Sxcj"%>
<%@page import="domain.Student"%>
<%@page import="factory.ServiceFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";//因为用了服务器端跳转；所以这边要重定向一下path；保证js和css可以正确载入
	int sideInd = 2;//侧边栏的序号，需要确定的赋值；与sidebar对应

	Teacher zyfzr = (Teacher) request.getSession().getAttribute("teacher");
	List<Student> students = ServiceFactory.getTeacherServiceInstance()
			.getZyStudentOrderByNumber(zyfzr.getFzr());
	List<Sxcjfs> sxcjs = ServiceFactory.getTeacherServiceInstance().getZyStudentSxcjOrderByNumber(students);

	List<Sxcjx> sxcjxs = ServiceFactory.getTeacherServiceInstance().getSxcjx();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link rel="shortcut icon" href="zyfzr/Images/student.ico" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>学生实习成绩</title>
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
	<%@ include file="../../common/jsp/ZyNotice.jspf"%>
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
					<h3 class="i_16_dashboard tab_label">学生实习成绩</h3>
					<div>
						<span class="label">审核指导老师填写过的学生的实习成绩</span>
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
						<h4 class="widget_header_title wwIcon i_16_data">本专业学生</h4>
					</div>
					<div class="widget_contents noPadding">
						<table class="datatable tables">
							<thead>
								<tr>
									<th>班级</th>
									<th>学号</th>
									<th>指导老师</th>
									<th>姓名</th>
									<th>分数</th>
									<th>详情</th>
								</tr>
							</thead>
							<tbody>
								<%
									for (int i = 0, j = 0, k = sxcjs.size(), l = students.size(); i < l; i++) {
								%>
								<tr>
									<td><%=students.get(i).getBj()%></td>
									<td><%=students.get(i).getNumber()%></td>
									<td><%=students.get(i).getTeacher()%></td>
									<td><span class="stuName"><%=students.get(i).getName()%></span></td>
									<%
										if (j < k && students.get(i).getNumber().equals(sxcjs.get(j).getXuehao())) {
									%>
									<td><%=sxcjs.get(j).getFs()%></td>
									<td style="text-align: center;"><input type="hidden"
										class="hiddenVal" value="<%=students.get(i).getNumber()%>" />
										<div class="simple_buttons">
											<div onclick="detailSx(this)" class="detailSx">详&nbsp;情</div>
										</div></td>
									<%
										j++;
											} else {
									%>
									<td style="text-align: center;">指导老师暂未评分</td>
									<td>&nbsp;</td>
									<%
										}
									%>
								</tr>
								<%
									}
								%>
							</tbody>
						</table>
					</div>
				</div>

				<div id="sxcjM" class="g_12 separator">
					<span></span>
				</div>

				<div class="g_12">
					<div class="widget_header">
						<h4 class="widget_header_title wwIcon i_16_forms">实习成绩表</h4>
						<span class="tipsMessage"></span>
					</div>
					<div class="widget_contents noPadding">
						<div class="line_grid line_border_no">
							<div class="g_3">
								<span class="label">成绩表：</span>
							</div>
						</div>
						<div class="line_grid">
							<form id="sxcjpsbForm">
								<input type="hidden" id="CheckedNumber" name="xuehao" value="" />
								<%
									for (int i = 0, l = sxcjxs.size(); i < l; i++) {
								%>
								<%
									if (sxcjxs.get(i).getStatus() == 0) {
								%>
								<div class="line_grid">
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
								<div class="line_grid">
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
							</form>
						</div>
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
					</div>
				</div>


			</div>
		</div>
	</div>

	<!-- 底部版权区 -->
	<%@ include file="../common/footer.jspf"%>

	<!-- 页面操作js -->
	<script src="zyfzr/Javascript/sxcj.js"></script>

</body>
</html>