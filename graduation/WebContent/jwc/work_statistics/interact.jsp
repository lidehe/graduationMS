<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="../head.jsp"></jsp:include>
<link rel="stylesheet" href="Css/menu_style.css" />
<script src="Javascript/statistics.js"></script>
<div class="contents">
	<!-- 标记本页面名字，用于左侧菜单背景色变化 -->
	<input type="hidden" id="pageName" value="statistics.jsp" /> <input
		type="hidden" id="pageSwitch" value="interact.jsp" />

	<div class="grid_wrapper">

		<div class="g_12 contents_header">
			<h3 class=" tab_label">统计信息</h3>
		</div>

		<div class="g_12  menu_bar">
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">选题分布</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">毕设进度</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">答辩状态</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">毕设成绩</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">抽检结果</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">省优秀论文</span>
			<span class=" active_initialHeadTabs" onclick="initialTabClick(this)">师生交流</span>
		</div>
		<div class="g_12">
			<div class="g_12">
				<span style="color: #9D9D9D">选择院系： </span> <select id="yuanxi"
					style="color: #9D9D9D">
					<option value="0">请选择</option>
				</select>
			</div>
			<div class="grid_wrapper">
				<!--毕设方式分布统计  -->
				<div class="widget_header">
					<h4 class="widget_header_title initialDataTile">该院系学生列表</h4>
				</div>
				<div class="widget_contents noPadding">
					<table class="tables noObOLine">
						<thead>
							<tr>
								<th>学号</th>
								<th>姓名</th>
								<th>专业</th>
								<th>班级</th>
								<th></th>
							</tr>
						</thead>
						<tbody id="studentList">

						</tbody>
					</table>
				</div>
				<div class="pageBTNS">
					<span class="preOrNext">上一页</span> <span>第 <span
						id="nowPage"></span> 页/共<span id="totalPage"></span> 页
					</span> <span class="preOrNext">下一页</span>
				</div>
				<!-- 毕设信息详情显示板 -->
				<div id="detailPanel"
					style="width: 800px; min-height: 700px; left: 120px; top: -60px; position: absolute; background-color: #4F94CD; border-radius: 15px;">
					<div align="center" style="margin-top:20px;">
						<span style="font-size: 24px; color: #e8bb09;">师生交流记录详情</span>
					</div>
					<div class="g_12">

						<div style="height: 500px; overflow-y: scroll">
							<div class="widget_contents noPadding ">
								<table class="tables  noObOLine">
									<thead>
										<tr>
											<th style="min-width: 80px;">提问方</th>
											<th style="min-width: 80px;">回答方</th>
											<th>内容</th>
										</tr>
									</thead>
									<tbody id="records">

									</tbody>
								</table>
							</div>

						</div>
					</div>
					<div style="color: #9D9D9D; margin-buttom: 50px;" align="center">
						<input id="closeDetailPanel" type="submit" value="关闭" />
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../foot.jsp"></jsp:include>