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
			<span class=" active_initialHeadTabs" onclick="initialTabClick(this)">往届学生信息</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">往届抽检信息</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">往届评优信息</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">往届统计信息</span>
		</div>
		<div class="g_12">
			<div class="g_12">
				<span style="color: #9D9D9D">年份选择： </span><select id="yearChoose"
					style="color: #9D9D9D">
					<option value="0">请选择</option>

				</select>
				<span style="color: #9D9D9D;margin-left:60px;">院系选择： </span><select id="yuanxiChoose"
					style="color: #9D9D9D">
					<option value="0">请选择</option>

				</select>
			</div>
			<div class="grid_wrapper">
				<!--毕设方式分布统计  -->
				<div class="widget_header">
					<h4 class="widget_header_title initialDataTile">已有学生列表</h4>
				</div>
				<div class="widget_contents noPadding">
					<table class="tables noObOLine">
						<thead>
							<tr>
								<th>学号</th>
								<th>姓名</th>
								<th>院系</th>
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
					style="width: 600px; min-height: 450px; left: 250px; top: -30px; position: absolute; background-color: blue;">
					<div class="g_12">
						<div class="widget_header">
							<h4 class="widget_header_title">毕业信息详情</h4>
						</div>
						<div class="widget_contents noPadding ">
							<table class="tables  noObOLine">
								<thead>
									<tr>
										<th>毕设信息构成项目</th>
										<th>项目内容(点击文档名称可预览文档)</th>
									</tr>
								</thead>
								<tbody id="tb_waysSteps">
									<tr>
										<td>课题：</td>
										<td id="ktName"></td>
									</tr>
									<tr>
										<td>开题报告：</td>
										<td id="ktbgName"></td>
									</tr>
									<tr>
										<td>论文：</td>
										<td id="lwName"></td>
									</tr>
									<tr>
										<td>指导老师：</td>
										<td id="teacherName"></td>
									</tr>
									<tr>
										<td>答辩成绩：</td>
										<td id="dbfs"></td>
									</tr>
									<tr>
										<td>实习成绩：</td>
										<td id="sxfs"></td>
									</tr>
									<tr>
										<td>论文成绩：</td>
										<td id="lwcj"></td>
									</tr>
									<tr>
										<td>总成绩：</td>
										<td id="zfcj"></td>
									</tr>
									<tr>
										<td></td>
										<td id="stuId"></td>
									</tr>
								</tbody>
							</table>
						</div>
						<br></br>
						<div
							style="color: #9D9D9D; margin-buttom: 20px; margin-left: 260px;">
							<input id="closeDetailPanel" type="submit" value="关闭" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../foot.jsp"></jsp:include>