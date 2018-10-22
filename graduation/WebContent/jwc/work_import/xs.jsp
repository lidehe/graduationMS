<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="domain.Student"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<jsp:include page="../head.jsp"></jsp:include>
<script src="Javascript/import.js"></script>
<div class="contents">
	<!-- 标记本页面名字，用于左侧菜单背景色变化 -->
	<input type="hidden" id="pageName" value="import.jsp" />

	<div class="grid_wrapper">
		<div class="g_12 contents_header">
			<h3 class=" tab_label">系统数据管理</h3>
		</div>

		<div class="g_12  menu_bar">
			<span class=" initialHeadTabs"  
				onclick="initialTabClick(this)">信息模板下载</span> 
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">导入院系信息</span>
			<span class="  initialHeadTabs" onclick="initialTabClick(this)">导入专业信息</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">导入教师信息</span>
			<span class=" active_initialHeadTabs" onclick="initialTabClick(this)">导入学生信息</span>
		</div>

		<!--上传学生文件  -->
		<div class="g_12" >
			<!-- 上传文件-->
			<div class="g_12">
				<div class="widget_header">
					<h4 class="widget_header_title wwIcon">
						<span class="initial_remind">导入学生信息</span>
					</h4>
				</div>
				<!-- 隐藏的用于向后台传送所传的是哪个群体的文件 -->
				<input class="initial_inforSource" name="inforSource" type="hidden" value="学生信息" />
				<div class="widget_contents noPadding">
					<div class="line_grid">
						<div class="g_12">
							<input id="upload" type="file" name="doc" class="simple_form" />
							<div class="field_notice">文件格式:xlsx   文件最大: 20Mb</div>
						</div>
					</div>
					<div class="line_grid">
						<div class="g_12" align="center">
							<button   id="subExcelFile">上传</button>
						</div>
					</div>
				</div>
			</div>
			<div align="center">
			  可前往&gt;&gt;<a href="../work_teacherAndStudentInfor/studentInfor.jsp">师生信息管理</a>&lt;&lt;&nbsp;查看和管理<span style="color:red;">学生</span>信息
			</div>
			<br>
		</div>
	</div>

</div>
<jsp:include page="../foot.jsp"></jsp:include>