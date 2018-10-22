<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="../head.jsp"></jsp:include>
<link rel="stylesheet" href="Css/menu_style.css" />
<script type="text/javascript" src="Javascript/appraising.js"></script>
<div class="contents">

	<!-- 标记本页面名字，用于左侧菜单背景色变化 -->
	<input type="hidden" id="pageName" value="appraising.jsp" />

	<div class="grid_wrapper">
		<div class="g_12 contents_header">
			<h3 class=" tab_label">优秀论文</h3>
		</div>
		<div class="g_12  menu_bar">
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">各院论文数量设定</span>
			<span class=" active_initialHeadTabs" onclick="initialTabClick(this)">校级优秀论文</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">省级优秀论文</span>
		</div>
		<!--页面标记，JS加载信息时，仅在需要的页面才会请求信息，可有效减少不必要的请求  -->
		<input type="hidden" id="pageWhitch" value="校级优秀论文" />
		<div class="g_12">
			<div class="g_12">
				<div class="widget_header">
					<h4 class="widget_header_title ">个人优秀论文</h4>
				</div>
				<div style="height: 400px; overflow-y: scroll">
					<div class="widget_contents noPadding">
						<table class="tables ">
							<thead>
								<tr>
									<th>学号</th>
									<th>院系</th>
									<th>论文名称</th>
									<th>校得分</th>
									<th>院得分</th>
									<th>详情</th>
									<th>推荐到省级</th>
								</tr>
							</thead>
							<tbody id="tb_XPersonalPaper">

							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="g_12">
				<div class="widget_header">
					<h4 class="widget_header_title ">小组优秀论文</h4>
				</div>
				<div style="height: 200px; overflow-y: scroll">
					<div class="widget_contents noPadding">
						<table class="tables ">
							<thead>
								<tr>
									<th>小组号</th>
									<th>院系</th>
									<th>校得分</th>
									<th>院得分</th>
									<th>详情</th>
									<th>推荐到省级</th>
								</tr>
							</thead>
							<tbody id="tb_XGroupPaper">

							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../foot.jsp"></jsp:include>