<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="../head.jsp"></jsp:include>
<script src="Javascript/appraising.js"></script>
<div class="contents">
	<!-- 标记本页面名字，用于左侧菜单背景色变化 -->
	<input type="hidden" id="pageName" value="recommend.jsp" />
     <input type="hidden" id="pageSwitch" value="recommend" />
	<div class="grid_wrapper">

		<div class="g_12 contents_header">
			<h3 class=" tab_label">论文推荐</h3>
		</div>
		<div class="g_12  menu_bar">
			<span class=" active_initialHeadTabs" onclick="initialTabClick(this)">院内个人优秀论文</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">院内小组优秀论文</span>
		</div>
		<div class="g_12">
			<!-- Simple Table -->
			<div class="g_12">
				<span class="contents_header">本院分配到的个人优秀论文数量：<span
					style="color: red;" id="personalLimit"></span> 篇
				</span>
			</div>
			<div class="g_12">
				<div class="widget_header">
					<h4 class="widget_header_title  ">院内个人优秀论文评选结果</h4>
				</div>
				<div style="height: 350px; overflow-y: scroll">
					<div class="widget_contents noPadding">
						<table class="tables ">
							<thead>
								<tr>
									<th>学号</th>
									<th>姓名</th>
									<th>专业</th>
									<th>论文名称</th>
									<th>院得分</th>
									<th>推荐到校级</th>
								</tr>
							</thead>
							<tbody id="tb_YPersonalPaper">


							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="g_12">
				<div class="widget_header">
					<h4 class="widget_header_title  ">已经推荐到学校的个人优秀论文</h4>
				</div>
				<div class="widget_contents noPadding">
					<table class="tables ">
						<thead>
							<tr>
								<th>学号</th>
								<th>姓名</th>
								<th>专业</th>
								<th>论文名称</th>
								<th>院得分</th>
								<th>取消推荐到校级</th>
							</tr>
						</thead>
						<tbody id="tb_YPersonalPaperDone">


						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../foot.jsp"></jsp:include>