<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<jsp:include page="../head.jsp"></jsp:include>
<script src="Javascript/report.js"></script>
<div id="roleSet" class="contents">
	<!-- 标记本页面名字，用于左侧菜单背景色变化 -->
	<input type="hidden" id="pageName" value="report.jsp" /> <input
		type="hidden" id="midOrfinal" value="final" />
	<div class="grid_wrapper">
		<div class="g_12 contents_header">
			<h3 class=" tab_label">中期小结/最终总结</h3>
		</div>
		<div class="g_12  menu_bar">
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">中期小结</span>
			<span class=" active_initialHeadTabs" onclick="initialTabClick(this)">最终总结</span>
		</div>
		<div class="g_12">
			<div class="g_12">
				<div class="widget_header">
					<h4 class="widget_header_title">最终总结</h4>
				</div>
				<div class="widget_contents noPadding">
					<table class="tables ">
						<thead>
							<tr>
								<th>院系</th>
								<th>报告</th>
								<th>上传时间</th>
								<th></th>
								<th></th>
								<th></th>
							</tr>
						</thead>
						<tbody id="finalReportList">

						</tbody>
					</table>
				</div>
				<br></br>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../foot.jsp"></jsp:include>