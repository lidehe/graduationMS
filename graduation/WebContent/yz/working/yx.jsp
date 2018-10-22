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

	int sideInd = 1;
	Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
	Yuanxi yuanxi = ServiceFactory.getTeacherServiceInstance().getYuanxiByTeacher(teacher);
	List<Student> students = ServiceFactory.getTeacherServiceInstance().getYuanxiStudents(teacher.getYx());
	HashMap<Integer, String> answers = new HashMap<Integer, String>();
	answers.put(0, "还未答辩");
	answers.put(1, "延迟答辩");
	answers.put(2, "初次不及格");
	answers.put(3, "不及格");
	answers.put(4, "通过答辩");
	Gzzj gzzj = ServiceFactory.getTeacherServiceInstance().getYxGzzj(teacher.getYx());
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
						<span class="label">本院系的学生详情和工作总结</span>
					</div>
				</div>
				<div class="g_6 contents_options"></div>

				<%-- <div class="g_12 separator">
					<span></span>
				</div>

				<div class="g_12">
					<div class="widget_header">
						<h4 class="widget_header_title wwIcon i_16_forms">
							<%
								if (gzzj != null && gzzj.getStatus() == 1) {
							%>
							重复提交将覆盖原文件！
							<%
								} else {
							%>
							在答辩工作完成后提交(pdf)文件！<%
								}
							%>(不超过20M)
						</h4>
					</div>
					<div class="widget_contents noPadding">
						<div class="line_grid">
							<form id="jdxcgwj" enctype="multipart/form-data">
								<div class="g_4">
									<span class="label">工作总结：</span>
								</div>
								<div class="g_4">
									<input type="file" id="wj" name="wj" accept="application/pdf"
										class="simple_form" />
								</div>
								<div class="g_2" style="text-align: center;">
									<div class="simple_buttons">
										<%
											if (gzzj != null && gzzj.getStatus() == 1) {
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
								<div class="g_2" style="text-align: center;">
									<%
										if (gzzj != null && gzzj.getStatus() == 1) {
									%>
									<div class="simple_buttons">
										<div onclick="window.open('yzp.do?meth=gzzjP');">查&nbsp;看</div>
									</div>
									<%
										}
									%>
								</div>
							</form>
						</div>

					</div>
				</div> --%>



				<div class="g_12 separator">
					<span></span>
				</div>

				<!-- 内容主要区域 -->
				<div class="g_12">
					<div class="widget_header wwOptions">
						<h4 class="widget_header_title wwIcon i_16_data">本院学生数据</h4>
					</div>
					<div class="widget_contents noPadding" id="tableContent">
						<table class="datatable tables">
							<thead>
								<tr>
									<th>班级</th>
									<th>学号</th>
									<th>姓名</th>
									<th>状态</th>
									<th>详情</th>
								</tr>
							</thead>
							<tbody>
								<%
									for (int i = 0, l = students.size(); i < l; i++) {
								%>
								<tr>
									<td><%=students.get(i).getBj()%></td>
									<td><%=students.get(i).getNumber()%></td>
									<td><%=students.get(i).getName()%></td>
									<td><%=answers.get(students.get(i).getAnswer())%></td>
									<td style="text-align: center;"><div
											class="simple_buttons">
											<input type="hidden" value="<%=students.get(i).getNumber()%>"
												class="hiddenVal" />
											<div onclick="Detail(this)">详情</div>
										</div></td>
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
	<script type="text/javascript" src="yz/Javascript/yz.js"></script>

</body>
</html>