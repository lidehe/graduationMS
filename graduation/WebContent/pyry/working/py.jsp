<%@page import="domain.Yxlwz"%>
<%@page import="utils.FloatUtil"%>
<%@page import="domain.Pybd"%>
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

	Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
	List<Yuanxi> yx = ServiceFactory.getTeacherServiceInstance().getAllYx();
	HashMap<Integer, String> yxs = new HashMap<Integer, String>();
	for (int i = 0, l = yx.size(); i < l; i++) {
		yxs.put(yx.get(i).getId(), yx.get(i).getName());
	}
	List<Yxlw> lws = ServiceFactory.getTeacherServiceInstance().getYxlwsOrderXuehaoInBd(teacher);
	List<Pyrz> rzs = ServiceFactory.getTeacherServiceInstance().getTeacherPyrz(teacher);
	List<Yxlwz> zlws = ServiceFactory.getTeacherServiceInstance().getZyxlwsOrderXuehaoInBd(teacher);
	List<Pyrz> zrzs = ServiceFactory.getTeacherServiceInstance().getTeacherZpyrz(teacher);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link rel="shortcut icon" href="pyry/Images/student.ico" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>论文评优</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<!-- The Main CSS File -->
<link rel="stylesheet" href="pyry/CSS/normalize.css" />
<link rel="stylesheet" href="pyry/CSS/tipsy.css" />
<link rel="stylesheet" href="pyry/CSS/colorpicker.css" />
<link rel="stylesheet" href="pyry/CSS/jquery.cleditor.css" />
<link rel="stylesheet" href="pyry/CSS/fullcalendar.css" />
<link rel="stylesheet" href="pyry/CSS/colorbox.css" />
<link rel="stylesheet" href="pyry/CSS/style.css" />

<link rel="stylesheet" href="pyry/CSS/cj.css" />

<!-- jQuery -->
<script src="pyry/Javascript/jQuery/jquery-1.7.2.min.js"></script>

<!-- Flot -->
<script src="pyry/Javascript/Flot/jquery.flot.js"></script>
<script src="pyry/Javascript/Flot/jquery.flot.resize.js"></script>
<script src="pyry/Javascript/Flot/jquery.flot.pie.js"></script>
<!-- DataTables -->
<script src="pyry/Javascript/DataTables/jquery.dataTables.min.js"></script>
<!-- ColResizable -->
<script src="pyry/Javascript/ColResizable/colResizable-1.3.js"></script>
<!-- jQuryUI -->
<script src="pyry/Javascript/jQueryUI/jquery-ui-1.8.21.min.js"></script>
<!-- Uniform -->
<script src="pyry/Javascript/Uniform/jquery.uniform.js"></script>
<!-- Tipsy -->
<script src="pyry/Javascript/Tipsy/jquery.tipsy.js"></script>
<!-- Elastic -->
<script src="pyry/Javascript/Elastic/jquery.elastic.js"></script>
<!-- ColorPicker -->
<script src="pyry/Javascript/ColorPicker/colorpicker.js"></script>
<!-- SuperTextarea -->
<script src="pyry/Javascript/SuperTextarea/jquery.supertextarea.min.js"></script>
<!-- UISpinner -->
<script src="pyry/Javascript/UISpinner/ui.spinner.js"></script>
<!-- MaskedInput -->
<script src="pyry/Javascript/MaskedInput/jquery.maskedinput-1.3.js"></script>
<!-- ClEditor -->
<script src="pyry/Javascript/ClEditor/jquery.cleditor.js"></script>
<!-- Full Calendar -->
<script src="pyry/Javascript/FullCalendar/fullcalendar.js"></script>
<!-- Color Box -->
<script src="pyry/Javascript/ColorBox/jquery.colorbox.js"></script>
<!-- Kanrisha Script -->
<script src="pyry/Javascript/kanrisha.js"></script>

<link rel="stylesheet" type="text/css" href="common/css/tips.css">
<script src="common/js/tips.js"></script>
</head>

<body>
	<%@ include file="../../common/jsp/password.jspf"%>

	<!-- 头部导航 -->
	<%@ include file="../common/header.jspf"%>


	<div class="wrapper contents_wrapper">




		<div class="contents" style="margin: 0 auto; float: none;">
			<!-- 		中间内容区 -->
			<div class="grid_wrapper">


				<div class="g_6 contents_header">
					<h3 class="i_16_dashboard tab_label">论文论文评优</h3>
					<div>
						<span class="label">对院系推荐的学生论文进行评优打分</span>
					</div>
				</div>
				<div class="g_6 contents_options">
					
				</div>

				<div class="g_12 separator">
					<span></span>
				</div>

				<!-- 内容主要区域 -->
				<div class="g_12" id="table_wTabs">
					<div class="widget_header wwOptions">
						<h4 class="widget_header_title wwIcon i_16_data">学生数据</h4>
						<ul class="w_Tabs">
							<li><a href="#table_wTabs-1" title="Tab 1">个人优秀</a></li>
							<li><a href="#table_wTabs-2" title="Tab 2">小组优秀</a></li>
						</ul>
					</div>
					<div style="width:830px;" class="widget_contents noPadding">
						<div id="table_wTabs-1">
							<table class="datatable tables">
								<thead>
									<tr>
										<th>院系(院内次序)</th>
										<th>学号</th>
										<th>目前分数</th>
										<th>您评分与否</th>
										<th>详情</th>
									</tr>
								</thead>
								<tbody>
									<%
										for (int i = 0, l = lws.size(), m = 0, n = rzs.size(); i < l; i++) {
									%>
									<tr>
										<td><%=yxs.get(lws.get(i).getYx())%>(<%=lws.get(i).getPm()%>)</td>
										<td><%=lws.get(i).getXuehao()%></td>
										<td><%=FloatUtil.floatSaveTwoD(lws.get(i).getXfs())%></td>
										<td>
											<%
												if (m < n && rzs.get(m).getXuehao().equals(lws.get(i).getXuehao())) {
											%> 您已评:<%=FloatUtil.floatSaveTwoD(rzs.get(m).getFs())%> <%
 	m++;
 		} else {
 %>您尚未打分<%
 	}
 %>
										</td>
										<td style="text-align: center;"><div
												class="simple_buttons">
												<input type="hidden" value="<%=lws.get(i).getXuehao()%>"
													class="hiddenVal" />
												<div onclick="pyDetail(this)">详情</div>
											</div></td>
									</tr>
									<%
										}
									%>
								</tbody>
							</table>
						</div>


						<div id="table_wTabs-2">
							<table class="resizeable_tables tables">
								<thead>
									<tr>
										<th>院系(院内次序)</th>
										<th>组号</th>
										<th>目前分数</th>
										<th>您评分与否</th>
										<th>详情</th>
									</tr>
								</thead>
								<tbody>
									<%
										for (int i = 0, l = zlws.size(), m = 0, n = zrzs.size(); i < l; i++) {
									%><tr>
										<td><%=yxs.get(zlws.get(i).getYx())%>(<%=zlws.get(i).getPm()%>)</td>
										<td><%=zlws.get(i).getZuhao()%></td>
										<td><%=zlws.get(i).getXfs()%></td>
										<td>
											<%
												if (m < n && zrzs.get(m).getZuhao() == zlws.get(i).getZuhao()) {
											%> 您已评:<%=zrzs.get(m).getFs()%> <%
 	m++;
 		} else {
 %> 您尚未打分 <%
 	}
 %>
										</td>
										<td style="text-align: center;"><div
												class="simple_buttons">
												<input type="hidden" value="<%=zlws.get(i).getZuhao()%>"
													class="hiddenVal" />
												<div onclick="pyzDetail(this)">详情</div>
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
	</div>

	<!-- 底部版权区 -->
	<%@ include file="../common/footer.jspf"%>

	<!-- 页面操作js -->
	<script src="pyry/Javascript/py.js"></script>

</body>
</html>