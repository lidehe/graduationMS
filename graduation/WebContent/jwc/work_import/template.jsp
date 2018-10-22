<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="../head.jsp"></jsp:include>
<script src="Javascript/import.js"></script>
<div class="contents">
	<!-- 标记本页面名字，用于左侧菜单背景色变化 -->
	<input type="hidden" id="pageName" value="import.jsp" />

	<div class="grid_wrapper">
		<div class="g_12 contents_header">
			<h3 class=" tab_label">系统数据管理</h3>
		</div>

		<div class="g_12 menu_bar">
			<span class=" active_initialHeadTabs" 
				onclick="initialTabClick(this)">信息模板下载</span> 
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">导入院系信息</span>
			<span class="  initialHeadTabs" onclick="initialTabClick(this)">导入专业信息</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">导入教师信息</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">导入学生信息</span>
		</div>

		<!--模板信息下载  -->
		<div class="g_12" id="initial_template">
			<div class="g_12">
				<!-- 隐藏的用于向后台传送所传的是哪个群体的文件 -->
				<div class="widget_contents noPadding">
					<div class="g_6">
						<button class="widget_contents downloadTemplate">院系信息模板</button>
					</div>
					<div class="g_6">
						<button class="widget_contents downloadTemplate">专业信息模板</button>
					</div>
					<div class="g_6">
						<button class="widget_contents downloadTemplate">教师信息模板</button>
					</div>
					<div class="g_6">
						<button class="widget_contents downloadTemplate">学生信息模板</button>
					</div>
				</div>
			</div>
			<br>
		</div>
	</div>
</div>
<jsp:include page="../foot.jsp"></jsp:include>