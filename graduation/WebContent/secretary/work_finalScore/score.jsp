<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="../head.jsp"></jsp:include>
<link rel="stylesheet" href="Css/menu_style.css" />
<script src="Javascript/initial.js"></script>
<script src="Javascript/score.js"></script>
<div class="contents">
	<!-- 标记本页面名字，用于左侧菜单背景色变化 -->
	<input type="hidden" id="pageName" value="finalScore.jsp" />
	<div class="grid_wrapper">

		<div class="g_12 contents_header">
			<h3 class=" tab_label">学生成绩</h3>
		</div>

		<div class="g_12  menu_bar">
			<span class=" active_initialHeadTabs" onclick="initialTabClick(this)">毕设最终成绩</span>
		</div>
		<div class="g_12">
			<div class="g_12">
				<span style="color: #9D9D9D">选择专业： </span> <select id="zhuanye"
					style="color: #9D9D9D">
					<option value="0">请选择</option>
				</select> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span
					style="color: #9D9D9D">选择班级： </span> <select id="banji"
					style="color: #9D9D9D">
					<option value="0">请选择</option>
				</select>
			</div>
			<div class="grid_wrapper">
				<div class="widget_header">
					<h4 class="widget_header_title initialDataTile">学生毕设总分</h4>
				</div>
				<div class="widget_contents noPadding">
					<table class="tables noObOLine">
						<thead>
							<tr>
								<th>专业</th>
								<th>班级</th>
								<th>学号</th>
								<th>姓名</th>
								<th>分数</th>
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
				<div align="center" style="margin-top: 30px;">
					<button class="confirmBTN" id="exportToExcel">导出到excel</button>
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../foot.jsp"></jsp:include>