<%@page import="java.util.ArrayList"%>
<%@page import="domain.Pypsbx"%>
<%@page import="domain.Dbpsbx"%>
<%@page import="domain.Sxcjx"%>
<%@page import="domain.Zdlspsbx"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="domain.Pypsb"%>
<%@page import="domain.Dbpsb"%>
<%@page import="domain.Zdlspsb"%>
<%@page import="domain.Sxcj"%>
<%@page import="domain.Lw"%>
<%@page import="domain.Jdxcg"%>
<%@page import="domain.Ktbg"%>
<%@page import="domain.Rws"%>
<%@page import="domain.Kt"%>
<%@page import="domain.Byfs"%>
<%@page import="domain.Student"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="domain.Pyrz"%>
<%@page import="domain.Yxlw"%>
<%@page import="domain.Yuanxi"%>
<%@page import="java.util.List"%>
<%@page import="factory.ServiceFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";//因为用了服务器端跳转；所以这边要重定向一下path；保证js和css可以正确载入

	Student student = ServiceFactory.getStudentServiceInstance()
			.getStudentByAccount(request.getParameter("number"));
	Byfs byfs = ServiceFactory.getStudentServiceInstance().getStudentByfs(student.getType());
	Kt kt = ServiceFactory.getStudentServiceInstance().getStudentKt(student.getKt());
	Rws rws = ServiceFactory.getStudentServiceInstance().getKtRwsByStudent(student);
	Ktbg ktbg = ServiceFactory.getStudentServiceInstance().getStudentKtbg(student);
	Jdxcg jdxcg = ServiceFactory.getStudentServiceInstance().getStudentJdxcg(student);
	Lw lw = ServiceFactory.getStudentServiceInstance().getStudentLw(student);

	List<Zdlspsbx> zdlspsbxs = ServiceFactory.getTeacherServiceInstance().getZdlspsb();
	List<Zdlspsb> zdlspsbs = ServiceFactory.getTeacherServiceInstance().getTeacherZdlspsb(student.getTeacher(),
			student.getNumber());
	List<Sxcjx> sxcjxs = ServiceFactory.getTeacherServiceInstance().getSxcjx();
	List<Sxcj> sxcjs = ServiceFactory.getTeacherServiceInstance().getSxcjpsb(student.getTeacher(),
			student.getNumber());
	List<Dbpsbx> dbpsbxs = ServiceFactory.getTeacherServiceInstance().getDbpsbxOrderId();
	List<Pypsbx> pypsbxs = ServiceFactory.getTeacherServiceInstance().getPypsbxOrderId();
	List<Teacher> dbls = new ArrayList<Teacher>();
	List<Teacher> pyls = new ArrayList<Teacher>();
	List<Dbpsb> dbs = ServiceFactory.getTeacherServiceInstance().getStudentDbpsbs(student,
			dbpsbxs.get(0).getId());
	List<Pypsb> pys = ServiceFactory.getTeacherServiceInstance().getStudentPypsbs(student,
			pypsbxs.get(0).getId());
	for (Dbpsb dbpsb : dbs) {
		dbls.add(ServiceFactory.getTeacherServiceInstance().getTeacherByAccount(dbpsb.getGonghao()));
	}
	for (Pypsb pypsb : pys) {
		pyls.add(ServiceFactory.getTeacherServiceInstance().getTeacherByAccount(pypsb.getGonghao()));
	}

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link rel="shortcut icon" href="yz/Images/student.ico" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title><%=student.getName()%>学生的详情</title>
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


		<div class="contents" style="margin: 0 auto; float: none;">
			<!-- 		中间内容区 -->
			<div class="grid_wrapper">


				<div class="g_6 contents_header">
					<h3 class="i_16_dashboard tab_label">查看学生详细内容</h3>
					<div>
						<span class="label">您可以对学生得分情况进行一些操作</span>
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
						<h4 class="widget_header_title wwIcon i_16_forms">学生的详细内容</h4>
					</div>
					<div class="widget_contents noPadding">
						<div class="line_grid">
							<div class="g_3">
								<span class="label">课题：</span>
							</div>
							<div class="g_9">
								<span class="label"> <%
 	if (kt != null) {
 %><%=kt.getName()%> <%
 	} else {
 %>该同学还没选择课题！<%
 	}
 %>
								</span>
							</div>
						</div>
						<div class="line_grid">
							<div class="g_3">
								<span class="label">任务书：</span>
							</div>
							<div class="g_9">
								<%
									if (rws != null && rws.getStatus() == 1) {
								%>
								<a target="_blank"
									href="yzp.do?meth=rwsP&number=<%=request.getParameter("number")%>"><input
									type="button" value="预览" class="submitIt simple_buttons" /></a>
									<a target="_blank"
									href="yzp.do?meth=rwsL&number=<%=request.getParameter("number")%>"><input
									type="button" value="下载" class="submitIt simple_buttons" /></a>
								<%
									} else {
								%><span class="label">指导老师暂未上传任务书！</span>
								<%
									}
								%>
							</div>
						</div>
						<div class="line_grid">
							<div class="g_3">
								<span class="label">开题报告：</span>
							</div>
							<div class="g_9">
								<%
									if (byfs.getIsPass1() == 0) {
								%>
								<span class="label">该同学不参与此阶段任务！</span>
								<%
									} else {
										if (ktbg != null && ktbg.getStatus() == 1) {
								%>
								<a target="_blank"
									href="yzp.do?meth=ktbgP&number=<%=request.getParameter("number")%>"><input
									type="button" value="预览" class="submitIt simple_buttons" /></a>
									<a target="_blank"
									href="yzp.do?meth=ktbgL&number=<%=request.getParameter("number")%>"><input
									type="button" value="下载" class="submitIt simple_buttons" /></a>
								<%
									} else {
								%><span class="label">该同学没有上传开题报告！</span>
								<%
									}
									}
								%>
							</div>
						</div>
						<div class="line_grid">
							<div class="g_3">
								<span class="label">阶段成果：</span>
							</div>
							<div class="g_9">
								<%
									if (byfs.getIsPass2() == 0) {
								%>
								<span class="label">该同学不参与此阶段任务！</span>
								<%
									} else {
										if (jdxcg != null && jdxcg.getStatus() == 1) {
								%>
								<a target="_blank"
									href="yzp.do?meth=jdxcgP&number=<%=request.getParameter("number")%>"><input
									type="button" value="预览" class="submitIt simple_buttons" /></a>
									<a target="_blank"
									href="yzp.do?meth=jdxcgL&number=<%=request.getParameter("number")%>"><input
									type="button" value="下载" class="submitIt simple_buttons" /></a>
								<%
									} else {
								%><span class="label">该同学没有上传阶段性成果！</span>
								<%
									}
									}
								%>
							</div>
						</div>
						<div class="line_grid">
							<div class="g_3">
								<span class="label">最终成果：</span>
							</div>
							<div class="g_9">
								<%
									if (lw != null && lw.getStatus() != 0) {
								%>
								<a target="_blank"
									href="yzp.do?meth=lwP&number=<%=request.getParameter("number")%>"><input
									type="button" value="预览" class="submitIt simple_buttons" /></a>
									<a target="_blank"
									href="yzp.do?meth=lwL&number=<%=request.getParameter("number")%>"><input
									type="button" value="下载" class="submitIt simple_buttons" /></a>
								<%
									} else {
								%><span class="label">该同学没有上传最终成果！</span>
								<%
									}
								%>
							</div>
						</div>
					</div>
				</div>

				<div class="g_12 separator">
					<span></span>
				</div>

				<div class="g_12" id="table_wTabs">
					<div class="widget_header wwOptions">
						<h4 class="widget_header_title wwIcon i_16_tabs">学生成绩</h4>
						<ul class="w_Tabs">
							<li><a href="#table_wTabs-1" title="Tab 1">实习成绩表</a></li>
							<li><a href="#table_wTabs-2" title="Tab 2">指导老师评审表</a></li>
							<li><a href="#table_wTabs-3" title="Tab 3">答辩评审表</a></li>
							<li><a href="#table_wTabs-4" title="Tab 4">评阅评审表</a></li>
						</ul>
					</div>
					<div style="width:830px;" class="widget_contents noPadding">
						<div id="table_wTabs-1">
							<form id="sxcjpsbForm">
								<input type="hidden" name="number"
									value="<%=student.getNumber()%>" />
								<%
									if (sxcjs != null && sxcjs.size() > 0) {
										for (int i = 0, l = sxcjxs.size(); i < l; i++) {
											if (sxcjxs.get(i).getStatus() == 0) {
								%>
								<div class="line_grid">
									<div class="g_9">
										<span class="label"><%=sxcjxs.get(i).getText()%></span>
									</div>
									<div class="g_3">
										<input class="simple_field sxcj" name="sxcj<%=i%>"
											placeholder="满分<%=sxcjxs.get(i).getMf()%>"
											value="<%=sxcjs.get(i).getFs()%>" type="text" />
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
										<textarea maxlength="400" class="simple_field elastic sxcj" name="sxcj<%=i%>"><%=sxcjs.get(i).getText()%></textarea>
									</div>
								</div>
								<%
									}
										}
									} else {
										for (int i = 0, l = sxcjxs.size(); i < l; i++) {
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
										<textarea maxlength="400" class="simple_field elastic sxcj" name="sxcj<%=i%>"></textarea>
									</div>
								</div>
								<%
									}
										}
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
						<div id="table_wTabs-2">
							<form id="zdlspsbForm">
								<input type="hidden" name="number"
									value="<%=student.getNumber()%>" />
								<%
									if (zdlspsbs != null && zdlspsbs.size() > 0) {
										for (int i = 0, l = zdlspsbxs.size(); i < l; i++) {
											if (zdlspsbxs.get(i).getStatus() == 0) {
								%>
								<div class="line_grid">
									<div class="g_9">
										<span class="label"><%=zdlspsbxs.get(i).getText()%></span>
									</div>
									<div class="g_3">
										<input class="simple_field zdlspsb" name="zdlspsb<%=i%>"
											placeholder="满分<%=zdlspsbxs.get(i).getMf()%>"
											value="<%=zdlspsbs.get(i).getFs()%>" type="text" />
									</div>
								</div>
								<%
									} else {
								%>
								<div class="line_grid">
									<div class="g_2">
										<span class="label"><%=zdlspsbxs.get(i).getText()%></span>
									</div>
									<div class="g_10">
										<textarea maxlength="400" class="simple_field elastic zdlspsb"
											name="zdlspsb<%=i%>"><%=zdlspsbs.get(i).getText()%></textarea>
									</div>
								</div>
								<%
									}
										}
									} else {
										for (int i = 0, l = zdlspsbxs.size(); i < l; i++) {
											if (zdlspsbxs.get(i).getStatus() == 0) {
								%>
								<div class="line_grid">
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
								<div class="line_grid">
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
										}
									}
								%>
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
						<div id="table_wTabs-3">
							<form id="dbpsbForm">
								<input type="hidden" name="number"
									value="<%=student.getNumber()%>" />
								<div class="line_grid">
									<div class="g_3">
										<span class="label">选择：</span>
									</div>
									<div class="g_9">
										<select class="simple_form" name="gonghao" id="dbpsbSelect">
											<option value="nul" selected="selected">--请选择--</option>
											<%
												for (int i = 0, l = dbls.size(); i < l; i++) {
											%>
											<option value="<%=dbls.get(i).getNumber()%>"><%=dbls.get(i).getName()%>老师</option>
											<%
												}
											%>
										</select>
									</div>
								</div>
								<%
									for (int i = 0, l = dbpsbxs.size(); i < l; i++) {
								%>
								<%
									if (dbpsbxs.get(i).getStatus() == 0) {
								%>
								<div class="line_grid">
									<div class="g_9">
										<span class="label"><%=dbpsbxs.get(i).getText()%></span>
									</div>
									<div class="g_3">
										<input class="simple_field dbpsb" name="dbpsb<%=i%>"
											placeholder="满分<%=dbpsbxs.get(i).getMf()%>" type="text" />
									</div>
								</div>
								<%
									} else {
								%>
								<div class="line_grid">
									<div class="g_2">
										<span class="label"><%=dbpsbxs.get(i).getText()%></span>
									</div>
									<div class="g_10">
										<textarea maxlength="400" class="simple_field elastic dbpsb"
											name="dbpsb<%=i%>"></textarea>
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
											<div id="dbsbB">提&nbsp;交</div>
											<div class="ajaxAnimation">&nbsp;&nbsp;&nbsp;</div>
										</div>
									</div>
								</div>
							</form>
						</div>
						<div id="table_wTabs-4">
							<form id="pypsbForm">
								<input type="hidden" id="stuNumber" name="number"
									value="<%=student.getNumber()%>" />
								<div class="line_grid">
									<div class="g_3">
										<span class="label">选择：</span>
									</div>
									<div class="g_9">
										<select class="simple_form" id="pypsbSelect" name="gonghao">
											<option value="nul" selected="selected">--请选择--</option>
											<%
												for (int i = 0, l = pyls.size(); i < l; i++) {
											%>
											<option value="<%=pyls.get(i).getNumber()%>"><%=pyls.get(i).getName()%>老师</option>
											<%
												}
											%>
										</select>
									</div>
								</div>
								<%
									for (int i = 0, l = pypsbxs.size(); i < l; i++) {
								%>
								<%
									if (pypsbxs.get(i).getStatus() == 0) {
								%>
								<div class="line_grid">
									<div class="g_9">
										<span class="label"><%=pypsbxs.get(i).getText()%></span>
									</div>
									<div class="g_3">
										<input class="simple_field pypsb" name="pypsb<%=i%>"
											placeholder="满分<%=pypsbxs.get(i).getMf()%>" type="text" />
									</div>
								</div>
								<%
									} else {
								%>
								<div class="line_grid">
									<div class="g_2">
										<span class="label"><%=pypsbxs.get(i).getText()%></span>
									</div>
									<div class="g_10">
										<textarea maxlength="400" class="simple_field elastic pypsb"
											name="pypsb<%=i%>"></textarea>
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
											<div id="pypsbB">提&nbsp;交</div>
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
	<script src="yz/Javascript/detail.js"></script>
</body>
</html>