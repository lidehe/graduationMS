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
			<!-- <span class="  initialHeadTabs" onclick="initialTabClick(this)">角色管理</span> -->
			<span class=" active_initialHeadTabs" onclick="initialTabClick(this)">人员角色设定</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">人员角色查询/更改</span>
		</div>
		<div class="g_12">
			<div class="g_12">
				<input type="hidden" class="requireFrom" value="jwc" /> <span
					style="color: #9D9D9D">角色选择： </span> <select id="role"
					style="color: #9D9D9D" onchange="roleChoose(this)">
					<option value="0">请选择</option>
				</select>&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:#FFBBFF;" id="ruleDiscription"></span>
			</div>
			<!--因行政人员没有院系信息，故仅当角色由教师担任时，显示出来  -->
			<div class="g_12 ">

				<span style="color: #9D9D9D">按院系检索： </span> <select id="yuanxi"
					style="color: #9D9D9D">
					<option value="0">请选择</option>
				</select>
			</div>
			<!--按工号搜索，仅当角色由行政人员担任时  -->
			<div class="g_12">
				<span style="color: #9D9D9D">按工号/姓名检索： </span> <input type="text"
					class="searchByNumber" placeholder="请输入工号/姓名" /> <input type="button"
					id="searchByNumber" value="搜索" />
			</div>
			<div class="g_12">
				<!--查询到的教职工列表  -->
				<div class="g_6">
					<div class="widget_header">
						<h4 class="widget_header_title ">查询结果</h4>
					</div>
					<div class="widget_contents noPadding ">
						<table class="tables  noObOLine">
							<thead>
								<tr>
									<th>工号</th>
									<th>姓名</th>
									<th><button onclick='chooseAllTeacher(this)' class='simple_buttons cancelTeacher'>全选</button></th>
								</tr>
							</thead>
							<tbody id="tb_result">


							</tbody>
						</table>
					</div>
					<div class="pageBTNS">
						<span class="pageOption">上一页</span>&nbsp;&nbsp;第<span id="nowPage">0</span>/<span id="totalPage">0</span>页&nbsp;&nbsp;<span class="pageOption">下一页</span>
					</div>
				</div>

				<!--选中的教职工列表  -->
				<div class="g_6">
					<div class="widget_header">
						<h4 class="widget_header_title ">选择的&nbsp;&nbsp;<span id="chosedRule"></span></h4>
					</div>
					<div class="widget_contents noPadding ">
						<table class="tables  noObOLine">
							<thead>
								<tr>
									<th>工号</th>
									<th>姓名</th>
									<th><button onclick='removeAllTeacher(this)' class='simple_buttons cancelTeacher'>全部取消</button></th>
								</tr>
							</thead>
							<tbody id="tb_chosedList">

							</tbody>
						</table>
					</div>
					<br></br>
					<div style="text-align: center; color: #9D9D9D">
						<button id="setRoles" class="confirmBTN">添加</button>
					</div>
				</div>
			</div>
		</div>

	</div>
</div>
<jsp:include page="../foot.jsp"></jsp:include>