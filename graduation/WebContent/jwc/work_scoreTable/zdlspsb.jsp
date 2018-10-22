<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<jsp:include page="../head.jsp"></jsp:include>
<script src="Javascript/scoreTable.js"></script>
<div id="roleSet" class="contents">
	<!-- 标记本页面名字，用于左侧菜单背景色变化 -->
	<input type="hidden" id="pageName" value="scoreTable.jsp" /> <input
		type="hidden" id="pageSwitch" value="zdlspsb.jsp" />

	<div class="grid_wrapper">
		<div class="g_12 contents_header">
			<h3 class=" tab_label">成绩表设置</h3>
		</div>
		<div class="g_12  menu_bar">
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">评阅评审表</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">答辩评审表</span>
			<span class=" active_initialHeadTabs" onclick="initialTabClick(this)">指导老师评审表</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">实习成绩表</span>
		</div>
		<!-- 上传院系信息 -->
		<div class="g_12">
			<div class="g_12">
				<!--查询到的教职工列表  -->
				<div class="g_12">
					<div class="widget_header">
						<h4 class="widget_header_title ">指导老师评审表已有项目</h4>
					</div>
					<div class="widget_contents noPadding ">
						<table class="tables  noObOLine">
							<thead>
								<tr>
									<th>序号</th>
									<th>名称</th>
									<th>类型</th>
									<th>满分值</th>
									<th></th>
								</tr>
							</thead>
							<tbody id="tb_result">


							</tbody>
							<tbody id="tb_makeRow">
								<tr>
									<th>新增行：</th>
									<th><input id="partName" type="text"></th>
									<th><select id="partType"><option value="num">分值</option>
											<option value="text">评语</option></select></th>
									<th><input id="mfz" style="width: 40px;" type="text"></th>
									<th><button id="addPart" class="confirmBTN">添加</button></th>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../foot.jsp"></jsp:include>