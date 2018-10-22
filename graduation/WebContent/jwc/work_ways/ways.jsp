<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="../head.jsp"></jsp:include>
<script src="Javascript/jwc-byfs.js"></script>
<link rel="stylesheet" href="../Css/way.css" />
<div class="contents">
	<!-- 标记本页面名字，用于左侧菜单背景色变化 -->
	<input type="hidden" id="pageName" value="ways.jsp" />

	<div class="grid_wrapper">
		<div class="g_12 contents_header">
			<h3 class=" tab_label">毕业方式</h3>
		</div>
		<div class="g_12  menu_bar">
			<span class=" active_initialHeadTabs" onclick="initialTabClick(this)">新建毕设方式</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">已有毕设方式</span>
		</div>
		<div class="g_12">
			<div class="g_12">
				<div class="widget_header wwOptions">
					<h4 class="widget_header_title wwIcon i_16_data">新建毕业方式</h4>
				</div>
				<br> <span style="color: #9D9D9D">请输入毕业方式名字：</span><input
					id="byfs_name" type="text" /> <br>
				<div style="color: #9D9D9D">
					<span>请参考左边的标准流程:带<span style="color: red; font-size: 19pt">*</span>的模块必填。不带<span
						style="color: red; font-size: 19pt">*</span>的模块中的阶段要么全填，要么全不填。
					</span>
				</div>
				<br>
				<div class="widget_contents noPadding">
					<table class="tables byfsStep1">
						<thead>
							<tr>
								<th>模块</th>
								<th>供参考标准阶段</th>
								<th>设置阶段名称</th>
							</tr>
						</thead>
						<tbody id="template_byfs1">

						</tbody>
					</table>
					<div class="tables byfsStep1">
						&nbsp;&nbsp;&nbsp;&nbsp;起始时间：<input id="block11" type="date" />
						&nbsp;&nbsp;&nbsp; 结束时间：<input id="block12" type="date" />
					</div>
					<table class="tables byfsStep2">
						<thead>
							<tr>
								<th>模块</th>
								<th>供参考标准阶段</th>
								<th>设置阶段名称</th>
							</tr>
						</thead>
						<tbody id="template_byfs2">

						</tbody>
					</table>
					<div class="tables byfsStep2">
						&nbsp;&nbsp;&nbsp;&nbsp;起始时间：<input id="block21" type="date" />
						&nbsp;&nbsp;&nbsp; 结束时间：<input id="block22" type="date" />
					</div>
					<table class="tables byfsStep3">
						<thead>
							<tr>
								<th>模块</th>
								<th>供参考标准阶段</th>
								<th>设置阶段名称</th>
							</tr>
						</thead>
						<tbody id="template_byfs3">

						</tbody>
					</table>
					<div class="tables byfsStep3">
						&nbsp;&nbsp;&nbsp;&nbsp;起始时间：<input id="block31" type="date" />
						&nbsp;&nbsp;&nbsp; 结束时间：<input id="block32" type="date" />
					</div>
					<table class="tables byfsStep4">
						<thead>
							<tr>
								<th>模块</th>
								<th>供参考标准阶段</th>
								<th>设置阶段名称</th>
							</tr>
						</thead>
						<tbody id="template_byfs4">

						</tbody>
					</table>
					<div class="tables byfsStep4">
						&nbsp;&nbsp;&nbsp;&nbsp;起始时间：<input id="block41" type="date" />
						&nbsp;&nbsp;&nbsp; 结束时间：<input id="block42" type="date" />
					</div>
					<table class="tables byfsStep5">
						<thead>
							<tr>
								<th>模块</th>
								<th>供参考标准阶段</th>
								<th>设置阶段名称</th>
							</tr>
						</thead>
						<tbody id="template_byfs5">

						</tbody>
					</table>
					<div class="tables byfsStep5">
						&nbsp;&nbsp;&nbsp;&nbsp;起始时间：<input id="block51" type="date" />
						&nbsp;&nbsp;&nbsp; 结束时间：<input id="block52" type="date" />
					</div>
				</div>
				<br>
				<button style="color: #696969" class="byfsStepPre">上一步</button>
				<span>第 <span class="byfsStepNow">1</span>步/共<span>5</span>步
				</span>
				<button style="color: #696969" class="byfsStepNext">下一步</button>
				<button style="margin-left: 200px;" id="add_byfs" class="confirmBTN">确认新建毕业方式</button>
			</div>
		</div>
		<!-- 毕设详情显示板 -->
		<div id="wayDetailPanel"
			style="width: 600px; min-height: 450px; left: 250px; top: -30px; position: absolute; background-color: #E6E6FA;">
			<div class="g_12">
				<div class="widget_header">
					<h4 class="widget_header_title wwIcon i_16_tooltip">毕业方式详情</h4>
				</div>
				<div class="widget_contents noPadding ">
					<table class="tables  noObOLine">
						<thead>
							<tr>
								<th>流程编号</th>
								<th>流程名称</th>
							</tr>
						</thead>
						<tbody id="tb_waysSteps">

						</tbody>
					</table>
				</div>
				<br></br>
				<div
					style="color: #9D9D9D; margin-buttom: 20px; margin-left: 260px;">
					<input id="closeWayDetailPanel" type="submit" value="关闭" />
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../foot.jsp"></jsp:include>