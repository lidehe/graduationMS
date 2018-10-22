<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="../head.jsp"></jsp:include>
<link rel="stylesheet" href="Css/menu_style.css" />
<script src="Javascript/initial.js"></script>
<script src="Javascript/notice.js"></script>
<div class="contents">
	<!-- 标记本页面名字，用于左侧菜单背景色变化 -->
	<input type="hidden" id="pageName" value="notice.jsp" /> <input
		type="hidden" id="pageId" value="noticeList" />
	<div class="grid_wrapper">

		<div class="g_12 contents_header">
			<h3 class=" tab_label">本院公告</h3>
		</div>

		<div class="g_12  menu_bar">
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">发布公告</span>
			<span class=" active_initialHeadTabs" onclick="initialTabClick(this)">公告管理</span>
		</div>
		<div class="g_12">
			<!--毕设方式分布统计  -->
			<div class="g_12">
				<div class="widget_header">
					<h4 class="widget_header_title">已发布的公告</h4>
				</div>
				<div class="widget_contents noPadding">
					<table class="tables ">
						<thead>
							<tr>
								<th>标题</th>
								<th>发布时间</th>
								<th>详情</th>
								<th>删除</th>
							</tr>
						</thead>
						<tbody id="noticeList">

						</tbody>
					</table>
				</div>
			</div>
		</div>
		<!-- 公告详情面板 -->
		<div id="noticeDetail"
			style="width: 800px; min-height: 450px; left: 100px; top: 10px; position: absolute; background-color: #E6E6FA;">
			<div class="g_12">
				<div class="widget_header" id="title" align="center"></div>
				<div class="widget_contents noPadding" id="content" style="min-height: 150px;"></div>
				<br>
				附件：
				<div class="widget_contents" id="attch"></div>
				<!-- <div class="widget_contents" id="time">时间：</div> -->
				<br>
				<div  align="center">
					<input id="closeDetailPanel" type="submit" value="关闭" />
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../foot.jsp"></jsp:include>