<%@page import="domain.Cjbd"%>
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
	List<Cjbd> bds = ServiceFactory.getTeacherServiceInstance().getOneTeacherCjBd(teacher);
	List<Yuanxi> yx = ServiceFactory.getTeacherServiceInstance().getAllYx();
	HashMap<Integer, String> yxs = new HashMap<Integer, String>();
	for (Yuanxi yuanxi : yx) {
		yxs.put(yuanxi.getId(), yuanxi.getName());
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link rel="shortcut icon" href="cjry/Images/student.ico" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>抽检论文</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<!-- The Main CSS File -->
<link rel="stylesheet" href="cjpy/CSS/normalize.css" />
<link rel="stylesheet" href="cjpy/CSS/tipsy.css" />
<link rel="stylesheet" href="cjpy/CSS/colorpicker.css" />
<link rel="stylesheet" href="cjpy/CSS/jquery.cleditor.css" />
<link rel="stylesheet" href="cjpy/CSS/fullcalendar.css" />
<link rel="stylesheet" href="cjpy/CSS/colorbox.css" />
<link rel="stylesheet" href="cjry/CSS/style.css" />

<link rel="stylesheet" href="cjry/CSS/cj.css" />

<!-- jQuery -->
<script src="cjry/Javascript/jQuery/jquery-1.7.2.min.js"></script>

<!-- Flot -->
<script src="cjry/Javascript/Flot/jquery.flot.js"></script>
<script src="cjry/Javascript/Flot/jquery.flot.resize.js"></script>
<script src="cjry/Javascript/Flot/jquery.flot.pie.js"></script>
<!-- DataTables -->
<script src="cjry/Javascript/DataTables/jquery.dataTables.min.js"></script>
<!-- ColResizable -->
<script src="cjry/Javascript/ColResizable/colResizable-1.3.js"></script>
<!-- jQuryUI -->
<script src="cjry/Javascript/jQueryUI/jquery-ui-1.8.21.min.js"></script>
<!-- Uniform -->
<script src="cjry/Javascript/Uniform/jquery.uniform.js"></script>
<!-- Tipsy -->
<script src="cjry/Javascript/Tipsy/jquery.tipsy.js"></script>
<!-- Elastic -->
<script src="cjry/Javascript/Elastic/jquery.elastic.js"></script>
<!-- ColorPicker -->
<script src="cjry/Javascript/ColorPicker/colorpicker.js"></script>
<!-- SuperTextarea -->
<script src="cjry/Javascript/SuperTextarea/jquery.supertextarea.min.js"></script>
<!-- UISpinner -->
<script src="cjry/Javascript/UISpinner/ui.spinner.js"></script>
<!-- MaskedInput -->
<script src="cjry/Javascript/MaskedInput/jquery.maskedinput-1.3.js"></script>
<!-- ClEditor -->
<script src="cjry/Javascript/ClEditor/jquery.cleditor.js"></script>
<!-- Full Calendar -->
<script src="cjry/Javascript/FullCalendar/fullcalendar.js"></script>
<!-- Color Box -->
<script src="cjry/Javascript/ColorBox/jquery.colorbox.js"></script>
<!-- Kanrisha Script -->
<script src="cjry/Javascript/kanrisha.js"></script>

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
					<h3 class="i_16_dashboard tab_label">抽检学生的论文</h3>
					<div>
						<span class="label">可以直接搜索或按专业索引</span>
					</div>
				</div>
				<div class="g_6 contents_options">
					
				</div>

				<div class="g_12 separator">
					<span></span>
				</div>

				<!-- 内容主要区域 -->
				<div class="g_12">
					<div class="widget_header wwOptions">
						<h4 class="widget_header_title wwIcon i_16_tables">选择范围</h4>
						<!-- <div
							style="float: right; display: inline-block; height: 33px; padding-top: 5px;">
							<input class="simple_field1" type="text" placeholder="或者直接输入学号"
								id="studentXH">&nbsp;
							<div class="simple_buttons">
								<div id="searchB">查&nbsp;找</div>
								<div class="searchBajax">&nbsp;&nbsp;&nbsp;</div>
							</div>
						</div> -->
					</div>
					<div class="widget_contents noPadding">
						<div class="line_grid">
							<div class="g_3">
								<span class="label">院系：</span>
							</div>
							<div class="g_9">
								<select class="simple_form" id="yx">
									<option value="0" selected="selected">--请选择--</option>
									<%
										for (int i = 0, l = bds.size(); i < l; i++) {
									%>
									<option value="<%=bds.get(i).getYx()%>"><%=yxs.get(bds.get(i).getYx())%></option>
									<%
										}
									%>
								</select>
								<div class="ajaxLoad1" title="载入专业数据">&nbsp;&nbsp;</div>
							</div>
						</div>
						<div class="line_grid">
							<div class="g_3">
								<span class="label">专业：</span>
							</div>
							<div class="g_9">
								<select class="simple_form" id="zy">
									<option value="0" selected="selected">--请选择--</option>
								</select>
								<div class="ajaxLoad2" title="载入学生数据">&nbsp;&nbsp;</div>
							</div>
						</div>
					</div>
				</div>
				<div class="g_12 separator">
					<span></span>
				</div>


				<div class="g_12">
					<div class="widget_header wwOptions">
						<h4 class="widget_header_title wwIcon i_16_data">学生数据</h4>
					</div>
					<div class="widget_contents noPadding" id="tableContent">
						<table class="datatable tables">
							<thead>
								<tr>
									<th>专业</th>
									<th>学号</th>
									<th>姓名</th>
									<th>详情</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
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
	<script src="cjry/Javascript/cj.js"></script>

</body>
</html>