<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<jsp:include page="../head.jsp"></jsp:include>
<script src="Javascript/roleSet.js"></script>
<div id="roleSet" class="contents">
	<!-- 标记本页面名字，用于左侧菜单背景色变化 -->
	<input type="hidden" id="pageName" value="roleSet.jsp" />

	<div class="grid_wrapper">
		<div class="g_12 contents_header">
			<h3 class=" tab_label">人员设置</h3>
		</div>
		<div class="g_12  menu_bar">
			<!-- <span class=" initialHeadTabs" onclick="initialTabClick(this)">角色管理</span> -->
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">人员角色设定</span>
			<span class=" active_initialHeadTabs" onclick="initialTabClick(this)">人员角色查询/更改</span>
		</div>
		<div class="g_12">
			<!-- 角色选择部分 -->
			<div class="g_12">
				<input type="hidden" class="requireFrom" value="jwc" /> <span
					style="color: #9D9D9D">按角色筛选： </span> <select id="searchByRole"
					style="color: #9D9D9D">
					<option value="0">请选择</option>
				</select>
			</div>

			<div class="g_12">
				<div class="widget_header">
					<h4 class="widget_header_title">该角色下的教职工列表</h4>
				</div>
				<div class="widget_contents noPadding ">
					<table class="tables  noObOLine">
						<thead>
							<tr>
								<th>工号</th>
								<th>姓名</th>
								<th>所属院系</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="tb_resultList">

						</tbody>
					</table>
				</div>
				<div align="center" style="margin-top:30px;">
				     <button class="confirmBTN" id="exportToExcel">导出到excel</button>
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../foot.jsp"></jsp:include>