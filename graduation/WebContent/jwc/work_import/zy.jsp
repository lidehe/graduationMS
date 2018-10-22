<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="domain.Zhuanye"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>

<jsp:include page="../head.jsp"></jsp:include>
<script src="Javascript/import.js"></script>
<div class="contents">
	<!-- 标记本页面名字，用于左侧菜单背景色变化 -->
	<input type="hidden" id="pageName" value="import.jsp" />

	<div class="grid_wrapper">
		<div class="g_12 contents_header">
			<h3 class=" tab_label">系统数据管理</h3>
		</div>

		<div class="g_12  menu_bar">
			<span class=" initialHeadTabs"  
				onclick="initialTabClick(this)">信息模板下载</span> 
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">导入院系信息</span>
			<span class="  active_initialHeadTabs" onclick="initialTabClick(this)">导入专业信息</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">导入教师信息</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">导入学生信息</span>
		</div>

		<!-- 上传专业信息 -->
		<div class="g_12" >
			<!-- 上传文件-->
			<div class="g_12">
				<div class="widget_header">
					<h4 class="widget_header_title wwIcon">
						<span class="initial_remind">导入专业信息</span>
					</h4>
				</div>
				<!-- 隐藏的用于向后台传送所传的是哪个群体的文件 -->
				<input class="initial_inforSource" name="inforSource" type="hidden" value="专业信息"/>
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
					<h4 class="widget_header_title initialDataTile">已有专业列表</h4>
				</div>
				<div class="widget_contents noPadding">
					<table class=" tables noObOLine">
						<thead>
							<tr>
								<th>专业名称</th>
								<th>所属院系</th>
								<th>专业代码</th>
							</tr>
						</thead>
						<tbody id="t_list">
							<%
								List<Zhuanye> zhuanyes = (List) request.getSession().getAttribute("zyInfors");
								Integer pageNum = (Integer) request.getSession().getAttribute("pageNum");
								Integer totalPage = (Integer) request.getSession().getAttribute("totalPage");
								Map<Integer, String> yuanxiMap = (Map)request.getSession().getAttribute("yuanxiMap");
								if (zhuanyes != null)
									for (Zhuanye zy : zhuanyes) {
							%>
							<tr class="status_open tItem">
								<td><%=yuanxiMap.get(zy.getYx())%></td>
								<td><%=zy.getName()%></td>
								<td><%=zy.getDm()%></td>
							</tr>
							<%
								}
							%>
						</tbody>
					</table>
				</div>
				<div class="pageBTNS">
					<a href="../../PageSearch.do?pageNum=<%=pageNum%>&option=pre&requestFrom=pageZy">上一页</a>
					<span>第<%=pageNum%>页/共<%=totalPage%> 页
					</span> <a
						href="../../PageSearch.do?pageNum=<%=pageNum%>&option=next&requestFrom=pageZy">下一页</a>
				</div>

			</div>
		</div>
	</div>
</div>
<jsp:include page="../foot.jsp"></jsp:include>