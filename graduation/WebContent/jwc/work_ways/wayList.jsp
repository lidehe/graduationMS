<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="../head.jsp"></jsp:include>
<script src="Javascript/jwc-byfs.js"></script>
<div class="contents">
	<!-- 标记本页面名字，用于左侧菜单背景色变化 -->
	<input type="hidden" id="pageName" value="ways.jsp" />

	<div class="grid_wrapper">
		<div class="g_12 contents_header">
			<h3 class=" tab_label">毕业方式</h3>
		</div>
		<div class="g_12  menu_bar">
		     <span class=" initialHeadTabs" onclick="initialTabClick(this)">新建毕设方式</span>
			 <span class=" active_initialHeadTabs" onclick="initialTabClick(this)">已有毕设方式</span>
		</div>
		<div class="g_12">
			<div class="g_12">
				<div class="widget_header wwOptions">
					<h4 class="widget_header_title wwIcon i_16_data">已有毕业方式</h4>

				</div>
				<div class="widget_contents noPadding">
					<table class="tables">
						<thead>
							<tr>
								<th>毕业方式名称</th>
								<th></th>
								<th></th>
							</tr>
						</thead>
						<tbody id="tb_byfs">

						</tbody>
					</table>
				</div>
			</div>
		</div>
		<!-- 毕设详情显示板 -->
		<div id="wayDetailPanel"
			style="width: 630px; min-height: 450px; left: 250px; top: -30px; position: absolute; background-color: #E6E6FA;">
			<div class="g_12">
				<div class="widget_header" align="center">
					<h4 class="widget_header_title  ">毕业方式详情</h4>
				</div>
				<div class="widget_contents noPadding ">
					<table class="tables  noObOLine">
						<thead>
							<tr>
								<th>模块名称</th>
								<th>阶段名称</th>
							</tr>
						</thead>
						<tbody id="tb_waysSteps">

						</tbody>
					</table>
				</div>
				<br>
				<div
					style="color: #9D9D9D; margin-buttom: 20px; margin-left: 260px;">
					<input id="closeWayDetailPanel" type="submit" value="关闭" />
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../foot.jsp"></jsp:include>