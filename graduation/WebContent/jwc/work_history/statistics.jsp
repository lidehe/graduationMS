<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="../head.jsp"></jsp:include>
<link rel="stylesheet" href="Css/menu_style.css" />
<script src="Javascript/history.js"></script>
<div class="contents">
	<!-- 标记本页面名字，用于左侧菜单背景色变化 -->
	<input type="hidden" id="pageName" value="history.jsp" />
    <input type="hidden" id="pageId" value="appraising" />
	<div class="grid_wrapper">
		<div class="g_12 contents_header">
			<h3 class=" tab_label">历史数据查询</h3>
		</div>

		<div class="g_12  menu_bar">
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">往届学生信息</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">往届抽检信息</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">往届评优信息</span>
			<span class=" active_initialHeadTabs" onclick="initialTabClick(this)">往届统计信息</span>
		</div>
		<div class="g_12">
			<span style="color: #9D9D9D">年份选择： </span><select id="yearChoose"
				style="color: #9D9D9D">
				<option value="0">请选择</option>
			</select>
		</div>
		<div class="grid_wrapper">
			<div class="widget_header">
				<h4 class="widget_header_title initialDataTile">优秀论文数量分配</h4>
			</div>
			<div class="widget_contents noPadding">
				<table class="tables noObOLine">
					<thead>
						<tr>
							<th>院系</th>
							<th>个人优秀论文数量限制</th>
							<th>小组优秀论文数量限制</th>
						</tr>
					</thead>
					<tbody id="countDistributeList">

					</tbody>
				</table>
			</div>
			<hr style="margin-top:50px;">
			<div class="widget_header" >
				<h4 class="widget_header_title initialDataTile">省级优秀论文</h4>
			</div>
			<div class="widget_contents noPadding">
				<table class="tables noObOLine">
					<thead>
						<tr>
							<th>学号</th>
							<th>姓名</th>
							<th>院系</th>
							<th>专业</th>
							<th>论文名称(点击可查看论文)</th>
						</tr>
					</thead>
					<tbody id="lwList">

					</tbody>
				</table>
			</div>
		</div>
		
		<!-- Separator -->
		<div class="g_12 separator">
			<span></span>
		</div>
	</div>
</div>
<jsp:include page="../foot.jsp"></jsp:include>