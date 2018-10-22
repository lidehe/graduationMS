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
<script src="../Javascript/sideBar.js"></script>
<script src="../../common/js/tips.js"></script>

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
				<input type="hidden" id="thisYuanxiId" value="<%=teacherH.getYx()%>"/>
				<input type="hidden" id="loginTeacherNumber" value="<%=teacherH.getNumber()%>"/>
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
			<!-- <li id="proManage" class="i_32_tables"><a href="proManage.jsp"> <span class="tab_label">专业管理</span></a></li> -->
				
			<li id="roleSet" class="i_32_tables"><a href="../work_roleSet/roleSet.jsp"> <span class="tab_label">职务设置</span>
			</a></li>
<!-- 			<li id="teacherDistribut" class="i_32_tables"><a href="teacherDistribut.jsp"> <span class="tab_label">分配教师</span>
			</a></li>
 -->			<li id="studentDistribut" class="i_32_tables"><a href="../work_studentDistribut/studentDistribut.jsp"> <span class="tab_label">匹配指导师</span>
			</a></li>
			<li id="group" class="i_32_tables"><a href="../work_group/addGroup.jsp"> <span class="tab_label">答辩组管理</span>
			</a></li>
			<li id="recommend" class="i_32_tables"><a href="../work_recommend/recommend.jsp"> <span class="tab_label">论文推荐</span>
			</a></li>
			<li id="notice" class="i_32_tables"><a
					href="../work_notice/notice.jsp"> <span class="tab_label">本院公告</span>
				</a></li>
			<li id="report" class="i_32_tables"><a href="../work_report/midReport.jsp"> <span class="tab_label">小结/总结</span>
			</a></li>
			<li id="finalScore" class="i_32_tables"><a href="../work_finalScore/score.jsp"> <span class="tab_label">毕设成绩</span>
			</a></li>
			<li id="statistics" class="i_32_tables"><a href="../work_statistics/statistics.jsp"> <span class="tab_label">统计信息</span>
			</a></li>
			</ul>
		</aside>
	