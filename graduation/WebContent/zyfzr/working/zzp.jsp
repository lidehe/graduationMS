<%@page import="domain.Zf"%>
<%@page import="domain.Zpjl"%>
<%@page import="domain.Student"%>
<%@page import="factory.ServiceFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";//因为用了服务器端跳转；所以这边要重定向一下path；保证js和css可以正确载入
	int sideInd = 3;//侧边栏的序号，需要确定的赋值；与sidebar对应

	Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
	Zpjl zpjl = ServiceFactory.getTeacherServiceInstance().zyfzrGetZpjl(teacher);

	List<Student> students = ServiceFactory.getTeacherServiceInstance()
			.getZyStudentOrderByNumber(teacher.getFzr());
	List<Zf> zfs = ServiceFactory.getTeacherServiceInstance().getZyStudentZfOrderByNumber(teacher.getFzr());
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link rel="shortcut icon" href="zyfzr/Images/student.ico" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>工作总评</title>
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
					<h3 class="i_16_dashboard tab_label">学生成绩</h3>
					<div>
						<span class="label">进行分数计算，查看分数统计</span>
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
						<h4 class="widget_header_title wwIcon i_16_forms">计算分数</h4>
					</div>
					<div class="widget_contents noPadding">
						<div class="line_grid">
							<div class="g_3">
								<span class="label">计算：</span>
							</div>
							<div class="g_9">
								<div class="simple_buttons">
									<div id="huiF">确&nbsp;定</div>
									<div class="ajaxAnimation">&nbsp;&nbsp;&nbsp;</div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="g_12 separator">
					<span></span>
				</div>
				<div class="g_12">
					<div class="widget_header wwOptions">
						<h4 class="widget_header_title wwIcon i_16_data">本专业学生成绩表</h4>
					</div>
					<div class="widget_contents noPadding">
						<table class="datatable tables">
							<thead>
								<tr>
									<th>班级</th>
									<th>学号</th>
									<th>姓名</th>
									<th>指导老师</th>
									<th>总成绩</th>
									<th>论文分数</th>
								</tr>
							</thead>
							<tbody>
								<%
									for (int i = 0, j = 0, k = zfs.size(), l = students.size(); i < l; i++) {
								%>
								<tr>
									<td><%=students.get(i).getBj()%></td>
									<td><%=students.get(i).getNumber()%></td>
									<td><%=students.get(i).getName()%></td>
									<td><%=students.get(i).getTeacher()%></td>
									<%
										if (j < k && zfs.get(j).getXuehao().equals(students.get(i).getNumber())) {
									%>
									<td><%=zfs.get(j).getZf()%></td>
									<td><%=zfs.get(j).getLw()%></td>
									<%
										j++;
											} else {
									%>
									<td style="text-align: center;">该学生尚未汇分</td>
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






			</div>
		</div>
	</div>

	<!-- 底部版权区 -->
	<%@ include file="../common/footer.jspf"%>

	<!-- 页面操作js -->
	<script src="zyfzr/Javascript/zzp.js"></script>
</body>
</html>