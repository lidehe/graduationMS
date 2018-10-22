<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="../head.jsp"></jsp:include>
<script src="Javascript/studentDistibut.js"></script>
<div class="contents">
	<!-- 标记本页面名字，用于左侧菜单背景色变化 -->
	<input type="hidden" id="pageName" value="studentDistrbut.jsp" />

	<div class="grid_wrapper">
		<div class="g_12 contents_header">
			<h3 class=" tab_label">分配学生到指导教师</h3>
		</div>
		<div class="g_12  menu_bar">
			<span class=" active_initialHeadTabs" onclick="initialTabClick(this)">手动匹配</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">快速匹配</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">匹配结果</span>
		</div>
		<div class="g_12">
			<div class="g_12">
				<h5 style="color: #9D9D9D" class="">操作方式：选中一个教师，然后选择该教师带的学生，点击绑定。</h5>
			</div>
			<div class="g_12">
				<button id="allThisYx" class="SearchBTN">本院指导教师</button>
			</div>
			<!--按工号搜索，仅当角色由行政人员担任时  -->
			<div class="g_12">
				<span style="color: #9D9D9D">按工号/姓名检索： </span> <input type="text"
					class="searchByNumber" placeholder="请输入工号/姓名" /> <input type="button"
					id="searchByNumber" value="搜索" />
			</div>
			<div class="grid_wrapper">
				<div class="widget_header">
					<h4 class="widget_header_title">搜索到的教职工</h4>
				</div>
				<div class="widget_contents noPadding">
					<table class=" tables">
						<thead>
							<tr>
								<th>工号</th>
								<th>姓名</th>
								<th>已分配到的学生数</th>
								<th>为TA分配学生</th>
							</tr>
						</thead>
						<tbody id="tb_result">

						</tbody>
					</table>
				</div>
				<div class="pageBTNS">
					<span class="pageOption">上一页</span>&nbsp;&nbsp;第<span id="nowPage">0</span>/<span
						id="totalPage">0</span>页&nbsp;&nbsp;<span class="pageOption">下一页</span>
				</div>
				<hr>
				<div class="g_12">
					<h4 style="color: #9D9D9D">筛选待分配学生： </h4> 
					<input type="hidden"class="requireFrom" value="mishu" /> 
					专业：<select id="zhuanye" style="color: #9D9D9D"><option value="0">请选择</option></select>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;班级：<select id="banji" style="color: #9D9D9D;"><option value="0">请选择</option></select> 
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: #9D9D9D;">按学号/姓名检索： </span> 
					<input type="text"class="searchStudentByNumber" placeholder="请输入学号/姓名" /> <input type="button" id="searchStudentByNumber" value="搜索" />
				</div>
				<div class="g_12">
					<!--查询到的教职工列表  -->
					<div class="g_6">
						<div class="widget_header">
							<h4 class="widget_header_title ">可分配学生</h4>
						</div>
						<div class="widget_contents noPadding ">
							<table class="tables  noObOLine">
								<thead>
									<tr>
										<th>学号</th>
										<th>姓名</th>
										<th>班级</th>
										<th><button onclick='chooseAllStudent(this)'
												class='simple_buttons cancelTeacher'>全选</button></th>
									</tr>
								</thead>
								<tbody id="tb_unBindedStudents">


								</tbody>
							</table>
						</div>
						<div id="pageBTNS1">
							<span class="pageOptionSTU">上一页</span>&nbsp;&nbsp;第<span
								class="nowPage">1</span>/<span class="totalPage">0</span>页&nbsp;&nbsp;<span
								class="pageOptionSTU">下一页</span>
						</div>

					</div>

					<!--选中的教职工列表  -->
					<div class="g_6">
						<div class="widget_header">
							<h4 class="widget_header_title ">已选择</h4>
						</div>
						<div class="widget_contents noPadding ">
							<table class="tables  noObOLine">
								<thead>
									<tr>
										<th>工号</th>
										<th>姓名</th>
										<th><button onclick='removeAllStudent(this)'
												class='simple_buttons cancelTeacher'>全部取消</button></th>
									</tr>
								</thead>
								<tbody id="tb_chosedList">

								</tbody>
							</table>
						</div>
						<br></br>
						<div style="text-align: center;">
							<button id="confirmBind" class="confirmBTN">绑定</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="g_12 separator">
		<span></span>
	</div>
</div>
<jsp:include page="../foot.jsp"></jsp:include>