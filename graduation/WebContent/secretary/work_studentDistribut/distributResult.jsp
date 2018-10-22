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
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">手动匹配</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">快速匹配</span>
			<span class=" active_initialHeadTabs" onclick="initialTabClick(this)">匹配结果</span>
		</div>
		<div class="g_12">
			<div class="g_12">
				<button class="SearchBTN" id="allThisYxTeacher">本院指导教师</button>
			</div>
			<!--按工号搜索，仅当角色由行政人员担任时  -->
			<div class="g_12">
				<span style="color: #9D9D9D">按工号检索： </span> <input type="text"
					class="searchTeacherByNumber" placeholder="请输入工号" /> <input
					type="button" id="searchTeacherByNumber" value="搜索" />
			</div>
			<div class="grid_wrapper">
				<div class="g_6">
					<div class="widget_header">
						<h4 class="widget_header_title">搜索到的教职工</h4>
					</div>
					<div class="widget_contents noPadding">
						<table class=" tables">
							<thead>
								<tr>
									<th>工号</th>
									<th>姓名</th>
									<th>已分配学生</th>
								</tr>
							</thead>
							<tbody id="tb_result">

							</tbody>
						</table>
					</div>
					<div class="pageBTNS">
						<span class="pageOptionResult">上一页</span>&nbsp;&nbsp;第<span
							id="nowPage">0</span>/<span id="totalPage">0</span>页&nbsp;&nbsp;
						<span class="pageOptionResult">下一页</span>
					</div>
				</div>
				<div class="g_6">
					<div class="widget_header">
						<h4 class="widget_header_title ">
							<span id="teacherName"></span>&nbsp;<input type="hidden" id="teacherGonghao">教师带的学生
						</h4>
					</div>
					<div class="widget_contents noPadding ">
						<table class="tables  noObOLine">
							<thead>
								<tr>
									<th>学号</th>
									<th>姓名</th>
									<th>班级</th>
									<th></th>
								</tr>
							</thead>
							<tbody id="tb_BindedStudents">


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
</div>
<jsp:include page="../foot.jsp"></jsp:include>