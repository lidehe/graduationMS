<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="../head.jsp"></jsp:include>
<link rel="stylesheet" href="Css/menu_style.css" />
<script src="Javascript/initial.js"></script>
<script type="text/javascript" src="Javascript/paperChek.js"></script>
<div class="contents">
 <!-- 标记本页面名字，用于左侧菜单背景色变化 -->
    <input type="hidden" id="pageName" value="paperCheck.jsp"/>
    
	<div class="grid_wrapper">
		<div class="g_6 contents_header">
			<h3 class=" tab_label">论文抽检</h3>
		</div>
		
	   <div class="g_12  menu_bar">
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">论文筛选规则</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">抽检权限设定</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">论文抽检结果</span>
			<span class=" active_initialHeadTabs" onclick="initialTabClick(this)">抽检结果统计</span>
		</div>
		
		<div class="grid_wrapper">
			<div class="g_12">
				<div class="widget_header">
					<h4 class="widget_header_title wwIcon i_16_tooltip">论文抽检结果</h4>
				</div>
				<div class="widget_contents noPadding">
					<table class="tables ">
						<thead>
							<tr>
								<th>院系</th>
								<th>抽查总数</th>
								<th>优秀占比</th>
								<th>良好占比</th>
								<th>中等占比</th>
								<th>差占比</th>
							</tr>
						</thead>
						<tbody id="papercheckshow">
						</tbody>
					</table>
				</div>
			</div>
			<div class="g_12 separator">
				<span></span>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../foot.jsp"></jsp:include>