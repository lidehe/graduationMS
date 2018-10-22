<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<jsp:include page="../head.jsp"></jsp:include>
<script src="Javascript/roleSet.js"></script>
<link rel="stylesheet" href="Css/menu_style.css" />
<div id="roleSet" class="contents">
	<!-- 标记本页面名字，用于左侧菜单背景色变化 -->
	<input type="hidden" id="pageName" value="roleSet.jsp" />

	<div class="grid_wrapper">
		<div class="g_12 contents_header">
			<h3 class=" tab_label">人员设置</h3>
		</div>
		<div class="g_12  menu_bar">
			<span class=" active_initialHeadTabs" onclick="initialTabClick(this)">角色管理</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">人员角色设定</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">人员角色查询/更改</span>
		</div>
		<!-- 上传院系信息 -->
		<div class="g_12">
			<!-- 上传文件-->
			<div class="g_12">
				<div class="widget_header">
					<h4 class="widget_header_title wwIcon">
						<span class="initial_remind">添加新的角色</span>
					</h4>
				</div>
				<!-- 隐藏的用于向后台传送所传的是哪个群体的文件 -->
				<input class="initial_inforSource" name="inforSource" type="hidden"
					value="院系信息" />
				<div class="widget_contents noPadding">
					<div class="line_grid">
						<div class="g_12">
							<input id="upload" type="text" name="newRole" class="simple_form"
								placeholder="新角色名称" />
						</div>
					</div>
					<div class="line_grid">
						<div class="g_12">
							<button id="addNewRole" class="confirmBTN">添加...</button>
						</div>
					</div>
				</div>
			</div>
			<br>
			<div class="grid_wrapper">
				<div class="widget_header">
					<h4 class="widget_header_title initialDataTile">已有角色列表</h4>
				</div>
				<div class="widget_contents noPadding">
					<table class=" tables noObOLine">
						<thead>
							<tr>
								<th>角色ID</th>
								<th>角色名字</th>
								<!--  <th>操作</th>  -->
							</tr>
						</thead>
						<tbody id="roleList">

						</tbody>
					</table>
				</div>
				<div class="pageBTNS"></div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../foot.jsp"></jsp:include>