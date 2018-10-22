<%@page import="domain.Lwcg"%>
<%@page import="domain.Byfs"%>
<%@page import="domain.Lw"%>
<%@page import="domain.Jdxcg"%>
<%@page import="domain.Student"%>
<%@page import="java.util.List"%>
<%@page import="factory.ServiceFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";//因为用了服务器端跳转；所以这边要重定向一下path；保证js和css可以正确载入
	int sideInd = 4;//侧边栏的序号，需要确定的赋值；与sidebar对应

	Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
	List<Student> students = ServiceFactory.getTeacherServiceInstance().getTeachersStudentOrderXuehao(teacher);
	List<Lwcg> lws = ServiceFactory.getTeacherServiceInstance().teacherGetStudentLwcgsOrderXuehao(teacher);
	List<Byfs> byfs = ServiceFactory.getTeacherServiceInstance().teacherGeaAllStudentByfs(students);

	List<Lw> lwss = ServiceFactory.getTeacherServiceInstance().getTeachersStudentLwsOrderXuehao(teacher);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link rel="shortcut icon" href="zdls/Images/student.ico" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>学生初稿</title>
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
					<h3 class="i_16_dashboard tab_label">学生提交的初稿</h3>
					<div>
						<span class="label">查看学生成果的初稿，并予以一些意见</span>
					</div>
				</div>
				<div class="g_6 contents_options"></div>

				<div class="g_12 separator">
					<span></span>
				</div>

				<!-- 内容主要区域 -->
				<div class="g_12">
					<div class="widget_header">
						<h4 class="widget_header_title wwIcon i_16_tables">初稿</h4>
					</div>
					<div class="widget_contents noPadding">
						<table class="tables">
							<thead>
								<tr>
									<th>学生学号</th>
									<th>学生姓名</th>
									<th>初稿文件</th>
									<th>阅览</th>
								</tr>
							</thead>
							<tbody>
								<%
									for (int i = 0, j = 0, k = lws.size(), l = students.size(); i < l; i++) {
								%>
								<tr>
									<td><%=students.get(i).getNumber()%></td>
									<td><%=students.get(i).getName()%></td>
									<%
										if (j < k && students.get(i).getNumber().equals(lws.get(j).getXuehao())
													&& lws.get(j).getStatus() != 0) {
									%>

									<td style="text-align: center;">已上传</td>
									<td style="text-align: center;"><div
											class="simple_buttons">
											<a
												href="tlwcg.do?meth=preview&number=<%=students.get(i).getNumber()%>"
												target="_blank">&nbsp;&nbsp;查&nbsp;看&nbsp;&nbsp; </a>
										</div>
										<div class="simple_buttons">
											<a
												href="tlwcg.do?meth=load&number=<%=students.get(i).getNumber()%>"
												target="_blank">&nbsp;&nbsp;下&nbsp;载&nbsp;&nbsp; </a>
										</div></td>
									<%
										j++;
											} else {
									%>
									<td style="text-align: center;" colspan="2">
										<%
											if (byfs.get(i).getIsPass3() == 0) {
										%> 该同学不参与此阶段工作 <%
											} else {
										%> 该同学暂未上传文件 <%
											}
										%>
									</td>
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


				<div class="g_12 separator">
					<span></span>
				</div>


				<div class="g_12">
					<div class="widget_header">
						<h4 class="widget_header_title wwIcon i_16_tables">学生的最终成果</h4>
					</div>
					<div class="widget_contents noPadding">
						<table class="tables">
							<thead>
								<tr>
									<th>学生学号</th>
									<th>学生姓名</th>
									<th>最终成果文件</th>
									<th>详情</th>
									<th>优秀论文</th>
								</tr>
							</thead>
							<tbody>
								<%
									for (int i = 0, j = 0, k = lwss.size(), l = students.size(); i < l; i++) {
								%>
								<tr>
									<td><%=students.get(i).getNumber()%></td>
									<td><%=students.get(i).getName()%></td>
									<%
										if (j < k && students.get(i).getNumber().equals(lwss.get(j).getXuehao())
													&& lwss.get(j).getStatus() == 1) {
									%><td style="text-align: center;">已上传（<%=ServiceFactory.getTeacherServiceInstance().countStudentLwWj(students.get(i).getNumber())%>）
									</td>
									<td style="text-align: center;"><div
											class="simple_buttons">
											<a
												href="lw.do?meth=detail&number=<%=students.get(i).getNumber()%>"
												target="_blank">&nbsp;&nbsp;详&nbsp;情&nbsp;&nbsp;</a>
										</div></td>
									<%
										j++;
											} else {
									%>
									<td style="text-align: center;" colspan="2">暂未上传文件</td>
									<%
										}
									%>
									<td style="text-align: center;">
										<%
											if (ServiceFactory.getTeacherServiceInstance().isYxlw(students.get(i))) {
													if (students.get(i).getIsgroup() == 0) {
										%> 已推荐个人 <%
											} else {
										%> 已推荐小组 <%
											}
												} else {
													if (students.get(i).getIsgroup() == 0) {
										%>
										<div class="simple_buttons">
											<input type="hidden" class="hiddenVal"
												value="<%=students.get(i).getNumber()%>" />
											<div id="gyxB">推荐个人优秀</div>
											<div class="ajaxAnimation">&nbsp;&nbsp;&nbsp;</div>
										</div> <%
 	} else {
 %>
										<div class="simple_buttons">
											<input type="hidden" class="hiddenVal"
												value="<%=students.get(i).getNumber()%>" />
											<div id="zyxB">推荐小组优秀</div>
											<div class="ajaxAnimation">&nbsp;&nbsp;&nbsp;</div>
										</div> <%
 	}
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
	<script src="zdls/Javascript/tyx.js"></script>
</body>
</html>