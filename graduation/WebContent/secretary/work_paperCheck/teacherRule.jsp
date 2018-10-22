<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="../head.jsp"></jsp:include>
<link rel="stylesheet" href="Css/menu_style.css" />
<script src="Javascript/initial.js"></script>
<script type="text/javascript" src="Javascript/paperChek.js"></script>
<div class="contents">
	<!-- 标记本页面名字，用于左侧菜单背景色变化 -->
	<input type="hidden" id="pageName" value="paperCheck.jsp" />

	<div class="grid_wrapper">
		<div class="g_6 contents_header">
			<h3 class=" tab_label">论文抽检</h3>
		</div>

		<div class="g_12  menu_bar">
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">论文筛选规则</span>
			<span class=" active_initialHeadTabs" onclick="initialTabClick(this)">抽检权限设定</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">论文抽检结果</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">抽检结果统计</span>
		</div>

		<div class="grid_wrapper">
			<div class="g_12">
				<!--设置抽检论文学生的尾号  -->
				<div class="g_12 ">
					<span style="color: red">绑定教师与学院，限定教师可评阅的论文所属院系 </span>
				</div>
				<div class="g_12 ">
					操作提示：<span>点击一个教师，右侧院系列表中，选中的表示已经绑定</span>
				</div>
			</div>
			<div class="g_12">
				<div class="g_8">
				<!-- 存放当前选中的抽检教师 -->
					<input type="hidden" id="chosedCjJs"/>
					<div class="widget_header">
						<h4 class="widget_header_title">抽检人员列表</h4>
					</div>
					<div style="height: 500px; overflow-y: scroll">
						<div class="widget_contents noPadding">
							<table class="tables ">
								<thead>
									<tr>
										<th>院系</th>
										<th>工号</th>
										<th>姓名</th>
									</tr>
								</thead>
								<tbody id="tb_cjJs">
									 
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="g_4">
					<div class="widget_header">
						<h4 class="widget_header_title">院系列表</h4>
					</div>
					<div class="widget_contents noPadding">
						<table class="tables ">
							<thead>
								<tr>
									<th>院系</th>
									<th></th>
								</tr>
							</thead>
							<tbody id="tb_bdYuanxi">
							
							</tbody>
						</table>
					</div>
					</div>
					<br>
					<div id="bindConfirm">
						<button>保存更改</button>
					</div>
				</div>
			</div>
		</div>

		<div class="g_12 separator">
			<span></span>
		</div>
	</div>
</div>
<jsp:include page="../foot.jsp"></jsp:include>