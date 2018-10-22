<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="../head.jsp"></jsp:include>
<link rel="stylesheet" href="Css/menu_style.css" />
<script src="Javascript/statistics.js"></script>
<div class="contents">
	<!-- 标记本页面名字，用于左侧菜单背景色变化 -->
	<input type="hidden" id="pageName" value="statistics.jsp" />
	<input type="hidden" id="pageSwitch" value="paperCheck.jsp" />

	<div class="grid_wrapper">

		<div class="g_12 contents_header">
			<h3 class=" tab_label">统计信息</h3>
		</div>

		<div class="g_12  menu_bar">
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">选题分布</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">毕设进度</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">答辩状态</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">毕设成绩</span>
			<span class=" active_initialHeadTabs" onclick="initialTabClick(this)">抽检结果</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">省优秀论文</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">师生交流</span>
		</div>
		<div class="g_12">
			<div class="g_12">
				<span style="color: #9D9D9D">选择院系： </span> <select id="yuanxi"
					style="color: #9D9D9D">
					<option value="0">请选择</option>
				</select>
			</div>
			<div class="grid_wrapper">
				<div class="widget_header">
					<h4 class="widget_header_title initialDataTile">抽检成绩分布统计</h4>
				</div>
				<div class="widget_contents noPadding">
					<table class="tables noObOLine">
						<thead>
							<tr>
								<th>院系</th>
								<th>分数等级</th>
								<th>该等级人数</th>
							</tr>
						</thead>
						<tbody id="result">

						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../foot.jsp"></jsp:include>