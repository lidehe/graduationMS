<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<jsp:include page="../head.jsp"></jsp:include>
<script src="Javascript/stuAndTeach.js"></script>
<div id="roleSet" class="contents">
	<!-- 标记本页面名字，用于左侧菜单背景色变化 -->
	<input type="hidden" id="pageName" value="teacherAndStudentInfor.jsp" />
	<input type="hidden" id="pageSwitch" value="student" />

	<div class="grid_wrapper">
		<div class="g_12 contents_header">
			<h3 class=" tab_label">师生信息管理</h3>
		</div>
		<div class="g_12  menu_bar">
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">教职工信息</span>
			<span class=" active_initialHeadTabs" onclick="initialTabClick(this)">学生信息</span>
		</div>
		<div class="g_12">
			<div class="g_12 ">
				<span style="color: #9D9D9D">院系选择： </span> <select id="yuanxi"
					style="color: #9D9D9D">
					<option value="0">请选择</option>
				</select>
				<span style="color: #9D9D9D;margin-left:25px;">专业选择： </span> <select id="zhuanye"
					style="color: #9D9D9D">
					<option value="0">请选择</option>
				</select>
				<span style="color: #9D9D9D;margin-left:25px;">按学号/姓名检索： </span> <input type="text"
					class="searchByNumber" placeholder="请输入学号/姓名" /> <input type="button"
					id="searchStudentByNumber" value="搜索" />
			</div>
			<div class="g_12">
				<div class="grid_wrapper">
					<div class="widget_header">
						<h4 class="widget_header_title initialDataTile">学生列表</h4>
					</div>
					<div class="widget_contents noPadding">
						<table class="tables noObOLine">
							<thead>
								<tr>
									<th>学号</th>
									<th>姓名</th>
									<th>班级</th>
									<th></th>
								</tr>
							</thead>
							<tbody id="tb_result">

							</tbody>
						</table>
					</div>
					<div class="pageBTNS">
						<span class="pageOptionStudent">上一页</span>&nbsp;&nbsp;第<span id="nowPage">0</span>/<span id="totalPage">0</span>页&nbsp;&nbsp;<span class="pageOptionStudent">下一页</span>
					</div>
				</div>
			</div>
		</div>

	</div>
</div>
<jsp:include page="../foot.jsp"></jsp:include>