<%@page import="domain.Teacher"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="domain.Notices"%>
<%@page import="java.util.List"%>
<%@page import="domain.Notice"%>
<%@page import="factory.ServiceFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";//因为用了服务器端跳转；所以这边要重定向一下path；保证js和css可以正确载入

	Notice notice = ServiceFactory.getStudentServiceInstance()
			.getNotice(Integer.valueOf(request.getParameter("id")));
	List<Notices> noticess = ServiceFactory.getStudentServiceInstance().getNoticesAll(notice.getId());
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	Teacher teacher = (Teacher) session.getAttribute("teacher");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link rel="shortcut icon" href="zdls/Images/student.ico" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>公告详情</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<!-- The Main CSS File -->
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
<style type="text/css">
.dSpan {
	background: #0e83cd;
	transition: all 0.3s;
	cursor: pointer;
	padding: 5px 15px;
	display: inline-block;
	text-transform: uppercase;
	letter-spacing: 1px;
	font-weight: 700;
	outline: none;
	position: relative;
	font-family: inherit;
	font-size: inherit;
	border: 3px solid #fff;
	color: #fff;
}

.dSpan:hover {
	color: #0e83cd;
	background: #fff;
}
</style>
</head>

<body>

	<!-- 头部导航 -->
	<div class="top_panel">
		<div class="wrapper">
			<div class="user">
				<a href="home.do"><span class="label">返回</span></a>
			</div>
			<div class="top_links"></div>
		</div>
	</div>


	<div class="wrapper contents_wrapper">

		<div class="contents" style="float: none; margin: 0 auto;">
			<!-- 		中间内容区 -->
			<div class="grid_wrapper">
				<div class="line_grid" style="text-align: center;">
					<h2><%=notice.getTitle()%></h2>
					<%
						if (teacher != null && teacher.getNumber().equals(notice.getGonghao())) {
					%>
					<input type="hidden" id="hId" value="<%=notice.getId()%>" /> <span
						title="删除此公告" id="dB" class="dSpan" style="float: left;">删
						除</span>
					<%
						}
					%>
					<span style="float: right;"><%=(notice.getTime() == null ? "" : sdf.format(notice.getTime()))%></span>
				</div>
				<div class="line_grid">
					<div style="width: 790px; margin: 0 auto; min-height: 350px;"><%=notice.getText()%></div>
				</div>
				<%
					if (noticess != null && noticess.size() > 0) {
				%>
				<div class="line_grid">
					<div class="g_3">附件：</div>
					<div class="g_9">
						<a target="_blank"
							href="home.do?meth=attach&id=<%=noticess.get(0).getId()%>"><%=noticess.get(0).getFileName()%></a>
					</div>
					<%
						for (int i = 1, l = noticess.size(); i < l; i++) {
					%>
					<div class="g_3">&nbsp;</div>
					<div class="g_9">
						<a target="_blank"
							href="home.do?meth=attach&id=<%=noticess.get(i).getId()%>"><%=noticess.get(i).getFileName()%></a>
					</div>
					<%
						}
					%>
				</div>
				<%
					}
				%>
			</div>
		</div>
	</div>

	<!-- 底部版权区 -->
	<footer>
		<div class="wrapper">
			<span class="copyright"> &copy; Copyright &copy; 20xx.L All
				rights reserved. </span>
		</div>
	</footer>
	<script>
		$(function() {
			$("#dB").click(function() {
				if (confirm("确定删除此公告？")) {
					var id = $("#hId").val();
					$.ajax({
						url:"home.do?meth=delete",
						type:"post",
						data:{
							id:id
						},
						error:function(request){
							alert("connect error!");
							location.reload();
						},
						success:function(data){
							if(data.status == 200){
								showMsg(data.msg,"center","window.close();");
							}else{
								alert(data.msg);
							}
						}
						
					})
				}
			})
		});
	</script>

</body>
</html>