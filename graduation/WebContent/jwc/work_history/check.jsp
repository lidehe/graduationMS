<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="../head.jsp"></jsp:include>
<link rel="stylesheet" href="Css/menu_style.css" />
<script src="Javascript/history.js"></script>
<div class="contents">
	<!-- 标记本页面名字，用于左侧菜单背景色变化 -->
	<input type="hidden" id="pageName" value="history.jsp" />

	<div class="grid_wrapper">

		<div class="g_12 contents_header">
			<h3 class=" tab_label">历史数据查询</h3>
		</div>

		<div class="g_12  menu_bar">
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">往届学生信息</span>
			<span class=" active_initialHeadTabs" onclick="initialTabClick(this)">往届抽检信息</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">往届评优信息</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">往届统计信息</span>
		</div>
		<div class="g_12">
			<div class="g_12">
				<span style="color: #9D9D9D">年份选择： </span><select id="yearChoose"
					style="color: #9D9D9D">
					<option value="0">请选择</option>

				</select>
			</div>
			<div class="g_12">
				<button id="checkBTN" class="SearchBTN">开始查询</button>
			</div>
			<div class="grid_wrapper">
				<div class="widget_header">
					<h4 class="widget_header_title initialDataTile">抽检规则</h4>
				</div>
				<div class="widget_contents noPadding">
					<table class="tables noObOLine">
						<thead>
							<tr>
								<th>数据项目</th>
								<th>数据内容</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>抽检学生尾号：</td>
								<td id="checkRule"></td>
							</tr>
						</tbody>
					</table>
				</div>
				<hr style="margin-top: 30px;">
				<div class="widget_header">
					<h4 class="widget_header_title initialDataTile">抽检结果</h4>
				</div>
				<div class="widget_contents noPadding">
					<table class="tables noObOLine">
						<thead>
							<tr>
								<th>院系</th>
								<th>成绩等级</th>
								<th>该等级下的人数</th>
							</tr>
						</thead>
						<tbody id="checkResult">

						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../foot.jsp"></jsp:include>