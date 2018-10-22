<%@page import="java.sql.Date"%>
<%@page import="domain.Kt"%>
<%@page import="java.util.List"%>
<%@page import="domain.Student"%>
<%@page import="factory.ServiceFactory"%>
<%@page import="domain.Byfs"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";//因为用了服务器端跳转；所以这边要重定向一下path；保证js和css可以正确载入
	int sideInd = 1;//侧边栏的序号，需要确定的赋值；与sidebar对应

	Student student = (Student) session.getAttribute("student");
	int byfsId = student.getType();
	Byfs byfs = ServiceFactory.getStudentServiceInstance().getStudentByfs(byfsId);

	List<Kt> kts = ServiceFactory.getStudentServiceInstance().getTeacherKt(student);
	String tipsMessage = byfs.getSan();
	if (student.getKt() != 0) {
		Kt kt = ServiceFactory.getStudentServiceInstance().getStudentKt(student.getKt());
		tipsMessage = byfs.getSi() + kt.getName();
	}
	List<Integer> numbers = ServiceFactory.getTeacherServiceInstance().getTeacherKtStudents(kts);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link rel="shortcut icon" href="student/Images/student.ico" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title><%=byfs.getYi()%></title>
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
<script
	src="student/Javascript/SuperTextarea/jquery.supertextarea.min.js"></script>
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
					<h3 class="i_16_dashboard tab_label"><%=byfs.getYi()%>
						<%
							if (byfs.getStart1() != null && byfs.getEnd1() != null)
								out.print("(" + new Date(byfs.getStart1().getTime())+ "~" + new Date(byfs.getEnd1().getTime()) + ")");
						%>
					</h3>
					<div>
						<span class="label"><%=byfs.getEr()%></span>
					</div>
				</div>
				<div class="g_6 contents_options">
					<!-- <div class="simple_buttons">
						<div class="bwIcon i_16_help">Help</div>
					</div> -->
				</div>

				<div class="g_12 separator">
					<span></span>
				</div>

				<!-- 内容主要区域 -->
				<div class="g_12">
					<div class="widget_header">
						<h4 class="widget_header_title wwIcon i_16_tables">
							你可以从以下列表中选择</h4>
						<span class="tipsMessage"><%=tipsMessage%></span>
					</div>
					<div class="widget_contents noPadding">
						<table class="tables">
							<thead>
								<tr>
									<th>描述</th>
									<th>名称</th>
									<th>可选状态</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<%
									for (int i = 0, l = kts.size(); i < l; i++) {
								%>
								<tr>
									<td><%=kts.get(i).getText()%></td>
									<td><%=kts.get(i).getName()%></td>
									<td>
										<%
											if (numbers.get(i) >= kts.get(i).getXzrs()) {
													out.print("人数已满");
												} else if (kts.get(i).getFzrsh() != 1) {
													out.print("还未通过");
												} else
													out.print("审核通过");
										%>
									</td>
									<td style="text-align: center"><input type="hidden"
										class="hiddenVal" value="<%=kts.get(i).getId()%>" /> <%
 	if (kts.get(i).getFzrsh() == 1 && student.getKt() == 0 && numbers.get(i) < kts.get(i).getXzrs()) {
 %> <input type="button" value="选 择"
										class="submitIt simple_buttons chooseKt"
										onclick="chooseKt(this)"> <%
 	} else {
 %> <input type="button" value="选 择"
										class="submitIt simple_buttons UnchooseKt"> <%
 	}
 %></td>
								</tr>
								<%
									}
								%>
							</tbody>
						</table>
					</div>
				</div>

				<%
					if (student.getKt() == 0) {
				%>
				<div class="g_12 separator">
					<span></span>
				</div>
				<div class="g_12">
					<div class="widget_header cwhToggle">
						<h4 class="widget_header_title wwIcon i_16_cHorizontal">点击此处申报自主题目！</h4>
					</div>
					<div class="widget_contents  cwClose noPadding">
						<form id="ktForm">
							<div class="line_grid">
								<div class="g_3">
									<span class="label">题目名称</span>
								</div>
								<div class="g_9">
									<input name="name" id="kName" class="simple_field tooltip"
										placeholder="名称" type="text" title="题目名称限制在40个汉字内" />
								</div>
							</div>

							<div class="line_grid">
								<div class="g_3">
									<span class="label">题目描述</span>
								</div>
								<div class="g_9">
									<textarea name="text" id="kText" class="simple_field tooltip"
										title="题目描述限制在100个汉字内"></textarea>
								</div>
							</div>

							<div class="line_grid">
								<div class="g_3">
									<span class="label">确认申报</span>
								</div>
								<div class="g_9">
									<div class="simple_buttons">
										<div id="ktButton">提&nbsp;交</div>
										<div class="ajaxAnimation">&nbsp;&nbsp;&nbsp;</div>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
				<%
					}
				%>


			</div>
		</div>
	</div>

	<!-- 底部版权区 -->
	<%@ include file="../common/footer.jspf"%>

	<!-- 页面操作js -->
	<script src="student/Javascript/kt.js"></script>

</body>
</html>