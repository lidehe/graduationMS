<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="../head.jsp"></jsp:include>
<script src="Javascript/studentDistibut.js"></script>
<div class="contents">
	<!-- 标记本页面名字，用于左侧菜单背景色变化 -->
	<input type="hidden" id="pageName" value="studentDistrbut.jsp" />

	<div class="grid_wrapper">
		<div class="g_8 contents_header">
			<h3 class=" tab_label">分配学生到指导教师</h3>
		</div>
		<div class="g_12  menu_bar">
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">手动匹配</span>
			<span class=" active_initialHeadTabs" onclick="initialTabClick(this)">快速匹配</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">匹配结果</span>
		</div>
		<div class="g_12">
			<div class="grid_wrapper">
				<div class="g_12">
					<h3 style="color: #9D9D9D" class="">
						<a href="../../OfficeFileTemplate/师生匹配信息导入模板.xlsx">点击下载导入匹配信息的excel模板</a>
					</h3>
					<div class="widget_header">
						<h4 class="widget_header_title wwIcon">
							<span class="initial_remind">导入师生匹配信息</span>
						</h4>
					</div>
					<div class="widget_contents noPadding">
						<div class="line_grid">
							<div class="g_12">
								<input id="upload" type="file" name="doc" class="simple_form" />
								<div class="field_notice">文件格式:xlsx   文件最大: 20Mb</div>
							</div>
						</div>
						<div class="line_grid">
							<div class="g_12" align="center">
								<button onclick="uploadExcelFile(this)" class="confirmBTN">上传...</button>
							</div>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>
</div>
<jsp:include page="../foot.jsp"></jsp:include>