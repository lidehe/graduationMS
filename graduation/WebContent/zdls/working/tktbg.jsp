<%@page import="domain.Kt"%>
<%@page import="domain.Byfs"%>
<%@page import="domain.Ktbg"%>
<%@page import="domain.Student"%>
<%@page import="java.util.List"%>
<%@page import="factory.ServiceFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";//因为用了服务器端跳转；所以这边要重定向一下path；保证js和css可以正确载入
	int sideInd = 2;//侧边栏的序号，需要确定的赋值；与sidebar对应

	Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
	List<Student> students = ServiceFactory.getTeacherServiceInstance().getTeachersStudentOrderXuehao(teacher);
	List<Ktbg> ktbgs = ServiceFactory.getTeacherServiceInstance().getTeachersStudentKtbgOrderXuehao(teacher);
	List<Byfs> byfs = ServiceFactory.getTeacherServiceInstance().teacherGeaAllStudentByfs(students);
	List<Kt> kts = ServiceFactory.getTeacherServiceInstance().getTeacherAllKts(teacher);
	HashMap<Integer, String> maps = new HashMap<Integer, String>();
	for (int i = 0, l = kts.size(); i < l; i++) {
		maps.put(kts.get(i).getId(), kts.get(i).getName());
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link rel="shortcut icon" href="zdls/Images/student.ico" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>开题报告</title>
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
				<!-- Simple Table -->
				<div class="g_12">
					<div class="widget_header">
						<h4 class="widget_header_title wwIcon i_16_tables">学生的开题报告</h4>
					</div>
					<div class="widget_contents noPadding">
						<table class="tables">
							<thead>
								<tr>
									<th>学生学号</th>
									<th>学生姓名</th>
									<th>学生的课题</th>
									<th>查看</th>
									<th>审核</th>
								</tr>
							</thead>
							<tbody>
								<%
									for (int i = 0, j = 0, k = ktbgs.size(), l = students.size(); i < l; i++) {
								%>
								<tr>
									<td><%=students.get(i).getNumber()%></td>
									<td><%=students.get(i).getName()%></td>
									<td>
										<%
											if (students.get(i).getKt() == 0)
													out.print("暂未选择课题");
												else
													out.print(maps.get(students.get(i).getKt()));
										%>
									</td>
									<%
										if (j < k && students.get(i).getNumber().equals(ktbgs.get(j).getXuehao())
													&& ktbgs.get(j).getStatus() == 1) {
									%>
									<td style="text-align: center;"><a
										href="tktbg.do?meth=preview&number=<%=students.get(i).getNumber()%>"
										target="_blank"><input type="button" value="阅  览"
											class="submitIt simple_buttons" /></a> &nbsp;&nbsp;<a
										href="tktbg.do?meth=load&number=<%=students.get(i).getNumber()%>"
										target="_blank"><input type="button" value="下  载"
											class="submitIt simple_buttons" /></a></td>
									<%
										if (ktbgs.get(j).getSh() == 1) {
									%>
									<td style="text-align: center;">已通过</td>
									<%
										} else {
									%>
									<td style="text-align: center;"><div
											class="simple_buttons">
											<input type="hidden" class="hiddenId"
												value="<%=ktbgs.get(j).getId()%>" />
											<div id="shenheB">通&nbsp;过</div>
											<div class="createGajax">&nbsp;&nbsp;&nbsp;</div>
										</div></td>
									<%
										}
												j++;
											} else {
												if (byfs.get(i) != null && byfs.get(i).getIsPass1() == 0) {
									%>
									<td style="text-align: center;" colspan="2">该同学不参与此阶段工作</td>
									<%
										} else {
									%>
									<td style="text-align: center;" colspan="2">该同学暂未上传文件</td>
									<%
										}
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




			</div>
		</div>
	</div>

	<!-- 底部版权区 -->
	<%@ include file="../common/footer.jspf"%>

	<!-- 页面操作js -->
	<script type="text/javascript" src="zdls/Javascript/tktbg.js"></script>

</body>
</html>