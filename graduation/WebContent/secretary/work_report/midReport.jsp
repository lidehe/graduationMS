<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<jsp:include page="../head.jsp"></jsp:include>
<script src="Javascript/initial.js"></script>
<div id="roleSet" class="contents">
	<!-- 标记本页面名字，用于左侧菜单背景色变化 -->
	<input type="hidden" id="pageName" value="report.jsp" /> <input
		type="hidden" id="midOrfinal" value="mid" />
	<div class="grid_wrapper">
		<div class="g_12 contents_header">
			<h3 class=" tab_label">中期小结/最终总结</h3>
		</div>
		<div class="g_12  menu_bar">
			<span class=" active_initialHeadTabs" onclick="initialTabClick(this)">中期小结</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">最终总结</span>
		</div>
		<div class="g_12">
			<div class="g_12">
				<div class="widget_header">
					<h4 class="widget_header_title">已经提交的中期小结</h4>
				</div>
				<div class="widget_contents noPadding">
					<table class="tables ">
						<thead>
							<tr>
								<th>报告名称</th>
								<th></th>
								<th></th>
								<th></th>
							</tr>
						</thead>
						<tbody id="midReportList">

						</tbody>
					</table>
				</div>
			</div>
			<br></br>
			<!-- 上传文件-->
			<div class="g_12">
				<div class="widget_header">
					<h4 class="widget_header_title wwIcon">
						<span class="initial_remind">上传中期小结：如果已经存在，会替换已经上传的文件</span>
					</h4>
				</div>
				<div class="widget_contents noPadding">
					<div class="line_grid">
						<div class="g_12">
							<input type="hidden" value="mid" id="fileType"> <input
								id="upload" type="file" name="doc" class="simple_form" />
							<div class="field_notice">文件格式:pdf   文件最大: 20Mb</div>
						</div>
					</div>
					<div class="line_grid">
						<div class="g_12" align="center">
							<button id="subExcelFile">上传...</button>
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>
</div>
<jsp:include page="../foot.jsp"></jsp:include>