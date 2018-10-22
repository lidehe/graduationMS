<%@page import="domain.Byfs"%>
<%@page import="domain.Zhuanye"%>
<%@page import="domain.Kt"%>
<%@page import="java.util.List"%>
<%@page import="factory.ServiceFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";//因为用了服务器端跳转；所以这边要重定向一下path；保证js和css可以正确载入
	int sideInd = 0;//侧边栏的序号，需要确定的赋值；与sidebar对应

	Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
	List<Kt> kts = ServiceFactory.getTeacherServiceInstance().getTeacherAllKts(teacher);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link rel="shortcut icon" href="zdls/Images/student.ico" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>本届课题</title>
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
<%@ include file="../../common/jsp/ZdNotice.jspf" %>
<%@ include file="../../common/jsp/password.jspf" %>

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
						<span class="label">申报课题，并管理学生申报的课题</span>
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
						<h4 class="widget_header_title wwIcon i_16_tables">课题状况</h4>
					</div>
					<div class="widget_contents noPadding">
						<table class="tables">
							<thead>
								<tr>
									<th style="width:100px;">标题</th>
									<th>描述</th>
									<th style="min-width:66px;">负责人审核</th>
									<th style="width:68px;">审核</th>
									<th  style="width:87px;">限制人数</th>
									<th style="width:68px;">操作</th>
								</tr>
							</thead>
							<tbody>
								<%
									for (int i = 0, l = kts.size(); i < l; i++) {
								%>
								<tr>
									<td><%=kts.get(i).getName()%></td>
									<td><%=kts.get(i).getText()%></td>
									<td>
										<%
											if (kts.get(i).getFzrsh() == 0) {
										%>暂未审核<%
											} else if (kts.get(i).getFzrsh() == 1) {
										%>已通过<%
											} else {
										%>被拒绝<%
											}
										%>
									</td>
									<td>
										<%
											if (kts.get(i).getShuxing() == 0) {
													if (kts.get(i).getSh() == 0) {
										%><div class="simple_buttons">
											<input type="hidden" class="hiddenId"
												value="<%=kts.get(i).getId()%>" />
											<div class="shenheB">通&nbsp;过</div>
											<div class="createGajax">&nbsp;&nbsp;&nbsp;</div>
										</div> <%
 	} else {
 %>已通过<%
 	}
 		} else {
 %>您申报的<%
 	}
 %>
									</td>
									<td style="text-align:center;"><select class="simple_form xzrsSelector">
											<option value="1"
												<% if(kts.get(i).getXzrs() == 1) out.print("selected=\"selected\""); %>>1人</option>
											<option value="2"
												<% if(kts.get(i).getXzrs() == 2) out.print("selected=\"selected\""); %>>2人</option>
											<option value="3"
												<% if(kts.get(i).getXzrs() == 3) out.print("selected=\"selected\""); %>>3人</option>
											<option value="4"
												<% if(kts.get(i).getXzrs() == 4) out.print("selected=\"selected\""); %>>4人</option>
											<option value="5"
												<% if(kts.get(i).getXzrs() == 5) out.print("selected=\"selected\""); %>>5人</option>
											<option value="6"
												<% if(kts.get(i).getXzrs() == 6) out.print("selected=\"selected\""); %>>6人</option>
											<option value="7"
												<% if(kts.get(i).getXzrs() == 7) out.print("selected=\"selected\""); %>>7人</option>
											<option value="8"
												<% if(kts.get(i).getXzrs() == 8) out.print("selected=\"selected\""); %>>8人</option>
											<option value="9"
												<% if(kts.get(i).getXzrs() == 9) out.print("selected=\"selected\""); %>>9人</option>
									</select></td>
									<td><div class="simple_buttons">
											<input type="hidden" class="hiddenId"
												value="<%=kts.get(i).getId()%>" />
											<div class="deleteB">删&nbsp;除</div>
											<div class="createGajax">&nbsp;&nbsp;&nbsp;</div>
										</div></td>
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
				<%
					List<Zhuanye> zys = ServiceFactory.getTeacherServiceInstance().zdlsGetStudentAllZy(teacher);
					List<Byfs> byfs = ServiceFactory.getTeacherServiceInstance().zdlsGetStudentAllByfs(teacher);
				%>


				<div class="g_12">
					<div class="widget_header">
						<h4 class="widget_header_title wwIcon i_16_cHorizontal">此处申报新的课题！</h4>
					</div>
					<div class="widget_contents noPadding">
						<form id="ktForm">
							<div class="line_grid">
								<div class="g_3">
									<span class="label">所属专业</span>
								</div>
								<div class="g_9">
									<select class="simple_form" id="ktzy" name="ktzy">
										<%
											for (int m = 0, n = zys.size(); m < n; m++) {
										%>
										<option value="<%=zys.get(m).getId()%>"><%=zys.get(m).getName()%></option>
										<%
											}
										%>
									</select>
								</div>
							</div>
							<div class="line_grid">
								<div class="g_3">
									<span class="label">所属毕业方式</span>
								</div>
								<div class="g_9">
									<select class="simple_form" id="ktbyfs" name="ktbylx">
										<%
											for (int m = 0, n = byfs.size(); m < n; m++) {
										%>
										<option value="<%=byfs.get(m).getId()%>"><%=byfs.get(m).getName()%></option>
										<%
											}
										%>
									</select>
								</div>
							</div>
							<div class="line_grid">
								<div class="g_3">
									<span class="label">课题名称</span>
								</div>
								<div class="g_9">
									<input name="kName" id="kName" class="simple_field tooltip"
										placeholder="名称" type="text" title="课题名称限制在40个汉字内" />
								</div>
							</div>

							<div class="line_grid">
								<div class="g_3">
									<span class="label">课题描述</span>
								</div>
								<div class="g_9">
									<textarea name="kText" id="kText" class="simple_field tooltip"
										title="课题描述限制在100个汉字内"></textarea>
								</div>
							</div>
							
							<div class="line_grid">
								<div class="g_3">
									<span class="label">限制人数</span>
								</div>
								<div class="g_9">
									<select  class="simple_form" id="xzrsForm" name="Kxzrs">
											<option value="1">1人</option>
											<option value="2">2人</option>
											<option value="3" selected="selected">3人</option>
											<option value="4">4人</option>
											<option value="5">5人</option>
											<option value="6">6人</option>
											<option value="7">7人</option>
											<option value="8">8人</option>
											<option value="9">9人</option>
									</select>
								</div>
							</div>

							<div class="line_grid">
								<div class="g_3">
									<span class="label">确认添加</span>
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



			</div>
		</div>
	</div>

	<!-- 底部版权区 -->
	<%@ include file="../common/footer.jspf"%>

	<!-- 页面操作js -->
	<script src="zdls/Javascript/tkt.js"></script>
</body>
</html>