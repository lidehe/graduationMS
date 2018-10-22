<%@page import="factory.ServiceFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="domain.Teacher"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>毕业设计管理系统</title>
<!-- jQuery -->
<script src="../Javascript/jQuery/jquery-1.7.2.min.js"></script>


<link rel="stylesheet" href="../CSS/jwc-style.css" />
<link rel="shortcut icon" href="../student/Images/student.ico" />

<!-- The Main CSS File -->
<!-- Flot -->
<script src="../Javascript/Flot/jquery.flot.js"></script>
<script src="../Javascript/Flot/jquery.flot.resize.js"></script>
<script src="../Javascript/Flot/jquery.flot.pie.js"></script>
<!-- DataTables -->
<script src="../Javascript/DataTables/jquery.dataTables.min.js"></script>
<!-- ColResizable -->
<script src="../Javascript/ColResizable/colResizable-1.3.js"></script>
<!-- jQuryUI -->
<script src="../Javascript/jQueryUI/jquery-ui-1.8.21.min.js"></script>
<!-- Uniform -->
<script src="../Javascript/Uniform/jquery.uniform.js"></script>
<!-- Tipsy -->
<script src="../Javascript/Tipsy/jquery.tipsy.js"></script>
<!-- Elastic -->
<script src="../Javascript/Elastic/jquery.elastic.js"></script>
<!-- ColorPicker -->
<script src="../Javascript/ColorPicker/colorpicker.js"></script>
<!-- SuperTextarea -->
<script src="../Javascript/SuperTextarea/jquery.supertextarea.min.js"></script>
<!-- UISpinner -->
<script src="../Javascript/UISpinner/ui.spinner.js"></script>
<!-- MaskedInput -->
<script src="../Javascript/MaskedInput/jquery.maskedinput-1.3.js"></script>
<!-- ClEditor -->
<script src="../Javascript/ClEditor/jquery.cleditor.js"></script>
<!-- Full Calendar -->
<script src="../Javascript/FullCalendar/fullcalendar.js"></script>
<!-- Color Box -->
<script src="../Javascript/ColorBox/jquery.colorbox.js"></script>
<!-- Kanrisha Script  jwc-dataForm -->
<script src="../Javascript/jwc-dataForm.js"></script>
<script src="../Javascript/workControll.js"></script>
<script src="../Javascript/sideBar.js"></script>

<script src="../Javascript/menuBar.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
	<!-- Top Panel -->

	<%
		Teacher teacherH = (Teacher) session.getAttribute("teacher");
	%>
	<div class="top_panel">
		<div class="wrapper">
			<div class="user">
				<img src="../../student/Images/user_avatar.png" alt="user_avatar"
					class="user_avatar" />
				<%
					if (teacherH != null) {
				%>
				<span class="label" id="chPassword" title="修改密码"> <span class="greeting"
					style="color: #21c4e0;"><%=teacherH.getName()%>老师</span>，您好
				</span>
				<%
					}
				%>
				<span class="dangQianShenFen" title="当前身份">&nbsp;&nbsp;[<%
					if (request.getSession().getAttribute("js") == null)
						out.print("其他身份");
					else
						out.print(request.getSession().getAttribute("js"));
				%>]
				</span>
				<%@ include file="../../common/jsp/js.jspf"%>
			</div>
			<div class="top_links">
				<a class="logoutB" href="../../login.do?method=logout" title="退出"><span
					class="logoutI">&nbsp;</span>退出</a>
			</div>
		</div>
	</div>
	

	<div class="wrapper contents_wrapper">
		<aside class="sidebar">
			<ul class="tab_nav">
				<!-- <li class="active_tab i_32_dashboard"><a href="initial.jsp" -->
				<li id="initial" class="i_32_tables"><a
					href="../work_initial/initial.jsp"> <span class="tab_label">系统初始化</span></a></li>
				<li id="import" class="i_32_tables"><a
					href="../work_import/template.jsp"> <span class="tab_label">信息导入</span></a></li>
				<li id="roleSet" class="i_32_tables"><a
					href="../work_roleSet/roleSet.jsp"> <span class="tab_label">人员设置</span>
				</a></li>
				<li id="scoreTable" class="i_32_tables"><a
					href="../work_scoreTable/pypsb.jsp"> <span class="tab_label">成绩表设置</span>
				</a></li>
				<li id="notice" class="i_32_tables"><a
					href="../work_notice/notice.jsp"> <span class="tab_label">校园公告</span>
				</a></li>
				<li id="ways" class="i_32_tables"><a
					href="../work_ways/ways.jsp"> <span class="tab_label">毕业方式</span>
				</a></li>
				<li id="paperCheck" class="i_32_tables"><a
					href="../work_paperCheck/checkRule.jsp"> <span
						class="tab_label">抽检论文</span>
				</a></li>
				<li id="appraising" class="i_32_tables"><a
					href="../work_appraising/countSet.jsp"> <span class="tab_label">论文评优</span>
				</a></li>
				<li id="report" class="i_32_tables"><a
					href="../work_report/midReport.jsp"> <span
						class="tab_label">工作总结</span>
				</a></li>
				<li id="teacherAndStudentInfor" class="i_32_tables"><a
					href="../work_teacherAndStudentInfor/teacherInfor.jsp"> <span
						class="tab_label">师生信息</span>
				</a></li>
				<li id="statistics" class="i_32_tables"><a
					href="../work_statistics/statistics.jsp"> <span
						class="tab_label">统计信息</span>
				</a></li>
				<li id="history" class="i_32_tables"><a
					href="../work_history/history.jsp"> <span class="tab_label">历史数据</span>
				</a></li>
			</ul>
		</aside>