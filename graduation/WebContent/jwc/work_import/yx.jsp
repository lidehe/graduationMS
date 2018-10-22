<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="domain.Yuanxi"%>
<%@page import="java.util.List"%>

<jsp:include page="../head.jsp"></jsp:include>
<script src="Javascript/import.js"></script>
<div class="contents">
	<!-- 标记本页面名字，用于左侧菜单背景色变化 -->
	<input type="hidden" id="pageName" value="import.jsp" />

	<div class="grid_wrapper">
		<div class="g_12 contents_header">
			<h3 class=" tab_label">/// 系统数据管理 ///</h3>
		</div>

		<div class="g_12  menu_bar">
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">信息模板下载</span>
			<span class=" active_initialHeadTabs" onclick="initialTabClick(this)">导入院系信息</span>
			<span class="  initialHeadTabs" onclick="initialTabClick(this)">导入专业信息</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">导入教师信息</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">导入学生信息</span>
		</div>

		<!-- 上传院系信息 -->
		<div class="g_12">
			<!-- 上传文件-->
			<div class="g_12">
				<div class="widget_header">
					<h4 class="widget_header_title wwIcon">
						<span class="initial_remind">导入院系信息</span>
					</h4>
				</div>
				<!-- 隐藏的用于向后台传送所传的是哪个群体的文件 -->
				<input class="initial_inforSource" name="inforSource" type="hidden"
					value="院系信息" />
				<div class="widget_contents noPadding">
					<div class="line_grid">
						<div class="g_12">
							<input id="upload" type="file" name="doc" class="simple_form" />
							<div class="field_notice">文件格式:xlsx   文件最大: 20Mb</div>
						</div>
					</div>
					<div class="line_grid">
						<div class="g_12" align="center">
							<button id="subExcelFile">上传</button>
						</div>
					</div>
				</div>
			</div>
			<br>
			<div class="grid_wrapper">
				<div class="widget_header">
					<h4 class="widget_header_title initialDataTile">本校院系列表</h4>
				</div>
				<div class="widget_contents noPadding">
					<table class=" tables noObOLine">
						<thead>
							<tr>
								<th>院系名称</th>
								<!-- <th>创建年份</th> -->
								<!-- <th>状态</th> -->
							</tr>
						</thead>
						<tbody id="">
							<%
								List<Yuanxi> yuanxi = (List) request.getSession().getAttribute("yxInfors");
								Integer pageNum = (Integer) request.getSession().getAttribute("pageNum");
								Integer totalPage = (Integer) request.getSession().getAttribute("totalPage");
								if (yuanxi != null)
									for (Yuanxi yx : yuanxi) {
							%>
							<tr class="status_open tItem">
								<td><%=yx.getName()%></td>
								<%-- <td><%=yx.getYear()%></td> --%>
								<%-- <td><%=yx.getStatus()%></td> --%>
							</tr>
							<%
								}
							%>
						</tbody>
					</table>
				</div>
				<div class="pageBTNS">
					<a
						href="../../PageSearch.do?pageNum=<%=pageNum%>&option=pre&requestFrom=pageYx">上一页</a>
					<span>第<%=pageNum%>页/共<%=totalPage%> 页
					</span> <a
						href="../../PageSearch.do?pageNum=<%=pageNum%>&option=next&requestFrom=pageYx">下一页</a>
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../foot.jsp"></jsp:include>