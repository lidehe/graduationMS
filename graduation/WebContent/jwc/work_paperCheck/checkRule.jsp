<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="../head.jsp"></jsp:include>
<link rel="stylesheet" href="Css/menu_style.css" />
<script type="text/javascript" src="Javascript/paperChek.js"></script>
<div class="contents">
	<!-- 标记本页面名字，用于左侧菜单背景色变化 -->
	<input type="hidden" id="pageName" value="paperCheck.jsp" />

	<div class="grid_wrapper">
		<div class="g_12 contents_header">
			<h3 class=" tab_label">论文抽检</h3>
		</div>

		<div class="g_12  menu_bar">
			<span class=" active_initialHeadTabs" onclick="initialTabClick(this)">论文筛选规则</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">抽检权限设定</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">论文抽检结果</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">抽检结果统计</span>
		</div>
		<div class="g_12">
			<div class="g_12">
				<!--设置抽检论文学生的尾号  -->
				<span style="color: red">学号尾号为0-9的数字。可设置多个学号尾号，如"168"。（允许最多设置3个尾号）：
				</span>
				<div class="g_12">
					<input type="text" class="searchByTaileNumber"
						placeholder="请输入学生尾号" /> <input type="button"
						id="searchByTaileNumber" class="SearchBTN"value="开始筛选" />
				</div>
				<div class="g_12 ">
					当前规则：<span id="nowRule"></span>
				</div>
			</div>
			<div class="g_12">
				<div class="widget_header">
					<h4 class="widget_header_title">筛选结果</h4>
				</div>
				<div class="widget_contents noPadding">
					<table class="tables ">
						<thead>
							<tr>
								<th>院系</th>
								<th>抽查论文总数数</th>
							</tr>
						</thead>
						<tbody id="selectedResult">


						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div id="ruleConfirm">
			<button class="confirmBTN">确认论文筛选规则</button>
		</div>
	</div>
</div>
<jsp:include page="../foot.jsp"></jsp:include>