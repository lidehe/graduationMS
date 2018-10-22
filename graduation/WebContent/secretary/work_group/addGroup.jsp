<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="../head.jsp"></jsp:include>
<script src="Javascript/group.js"></script>
<script src="Javascript/initial.js"></script>
<div class="contents">
	<!-- 标记本页面名字，用于左侧菜单背景色变化 -->
	<input type="hidden" id="pageName" value="group.jsp" />
	<!--标记页面，用于js选择是否加载相关信息  -->
	<input type="hidden" id="whichPage" value="addGroup" />
	<div class="grid_wrapper">
		<div class="g_12 contents_header">
			<h3 class=" tab_label">答辩组管理</h3>
		</div>
		<div class="g_12  menu_bar">
			<span class=" active_initialHeadTabs" onclick="initialTabClick(this)">新建答辩组</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">已有答辩组</span>
		</div>
		<div class="g_12">
			<div class="g_12">
				<h5 style="color: #9D9D9D" class="">操作方式：选择三个教师组成答辩组，新建即可。</h5>
			</div>
			<div class="g_12">
				<button class="SearchBTN" id="allThisYx">本院未分组教师</button>
			</div>
			<!--按工号搜索，仅当角色由行政人员担任时  -->
			<div class="g_12">
				<span style="color: #9D9D9D">按工号检索： </span> <input type="text"
					class="searchByNumber" placeholder="请输入工号" /> <input type="button"
					id="searchByNumber" value="搜索" />
			</div>
			<!--答辩小组管理  -->
			<!-- 新建答辩小组 -->
			<div class="g_12">
				<div class="g_6">
					<div class="widget_header">
						<h4 class="widget_header_title">本院教师列表</h4>
					</div>
					<div class="widget_contents noPadding">
						<table class="tables noObOLine">
							<thead>
								<tr>
									<th>工号</th>
									<th>姓名</th>
									<th></th>
								</tr>
							</thead>
							<tbody id="tb_result">


							</tbody>
						</table>
					</div>
					<div class="pageBTNS">
						<span class="pageOption">上一页</span> <span id="nowPage">0</span> <span>/</span>
						<span id="allPage">0</span> <span class="pageOption">下一页</span>
					</div>
				</div>
				<div class="g_6">
					<div class="widget_header">
						<h4 class="widget_header_title">新建答辩组成员</h4>
					</div>
					<div class="widget_contents noPadding">
						<table class="tables noObOLine">
							<thead>
								<tr>
									<th>工号</th>
									<th>姓名</th>
									<th></th>
								</tr>
							</thead>
							<tbody id="g_list">

							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="g_12" style="color: red; margin-top: 40px;">
				<span>答辩秘书，帮助老师完成答辩工作的学生，可以不填写</span>
			</div>
			<div class="g_12" style="color: #515151; text-align: center">
				<input class="dbms" placeholder="答辩秘书名字" />&nbsp&nbsp<input
					class="dbms" placeholder="答辩秘书 名字" />&nbsp&nbsp<input class="dbms"
					placeholder="答辩秘书 名字" />&nbsp&nbsp<input class="dbms"
					placeholder="答辩秘书 名字" />
			</div>
			<div class="g_12" align="center">
				<button id="newGroup" class="confirmBTN">新建答辩组</button>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../foot.jsp"></jsp:include>