<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="../head.jsp"></jsp:include>
<script src="Javascript/group.js"></script>
<script src="Javascript/initial.js"></script>
<div class="contents">
	<!-- 标记本页面名字，用于左侧菜单背景色变化 -->
	<input type="hidden" id="pageName" value="group.jsp" />
	<!--标记页面，用于js选择是否加载相关信息  -->
	<input type="hidden" id="whichPage" value="groupList" />

	<div class="grid_wrapper">
		<div class="g_12 contents_header">
			<h3 class=" tab_label">答辩组管理</h3>
		</div>
		<div class="g_12  menu_bar">
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">新建答辩组</span>
			<span class=" active_initialHeadTabs" onclick="initialTabClick(this)">已有答辩组</span>
		</div>
		<div class="g_12">
			<!--答辩小组管理  -->
			<div class="g_12">
				<!--已存在答辩小组  -->
				<div class="widget_header">
					<h4 class="widget_header_title">已有答辩组</h4>
				</div>
				<div class="widget_contents noPadding">
					<table class="tables noObOLine">
						<thead>
							<tr>
								<th>答辩组名称</th>
								<th>详情</th>
								<th>解散答辩组</th>
							</tr>
						</thead>
						<tbody id="groupsshow">

						</tbody>
					</table>
				</div>
			</div>
			<br>
			<div class="g_12">
				<div class="widget_header">
					<h4 class="widget_header_title"><span id="groupName"></span>&nbsp;成员详情</h4>
				</div>
				<div class="widget_contents noPadding">
					<table class="tables noObOLine">
						<thead>
							<tr>
								<th>工号</th>
								<th>姓名</th>
								<th>职务</th>
								<th></th>
							</tr>
						</thead>
						<tbody id="dbz_detaile">
						</tbody>
					</table>
				</div>
				<br>
			</div>

		</div>
	</div>
</div>
<jsp:include page="../foot.jsp"></jsp:include>