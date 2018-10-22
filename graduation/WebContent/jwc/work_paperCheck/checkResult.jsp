<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="domain.Cjk"%>
<jsp:include page="../head.jsp"></jsp:include>
<link rel="stylesheet" href="Css/menu_style.css" />
<script type="text/javascript" src="Javascript/paperChek.js"></script>
<div class="contents">
	<!-- 标记本页面名字，用于左侧菜单背景色变化 -->
	<input type="hidden" id="pageName" value="paperCheck.jsp" />

	<div class="grid_wrapper">
		<div class="g_12 contents_header">
			<h3 class=" tab_label">论文抽检</h3>
		</div>

		<div class="g_12  menu_bar">
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">论文筛选规则</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">抽检权限设定</span>
			<span class=" active_initialHeadTabs" onclick="initialTabClick(this)">论文抽检结果</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">抽检结果统计</span>
		</div>
		<div class="g_12">
			<div class="g_12">
				<div class="widget_header">
					<h4 class="widget_header_title">论文抽检结果</h4>
				</div>
				<div class="widget_contents noPadding">
					<table class="tables ">
						<thead>
							<tr>
								<th>院系</th>
								<th>专业</th>
								<th>学号</th>
								<th>课题</th>
								<th>等级</th>
							</tr>
						</thead>
						<tbody id="checkResult">
							<%
								List<Cjk> students = (List) request.getSession().getAttribute("cjkInfors");
								Integer pageNum = (Integer) request.getSession().getAttribute("pageNum");
								Integer totalPage = (Integer) request.getSession().getAttribute("totalPage");
								Map<Integer, String> yuanxiMap = (Map) request.getSession().getAttribute("yuanxiMap");
								Map<Integer, String> zhuanyeMap = (Map) request.getSession().getAttribute("zhuanyeMap");
								Map<Integer, String> chengjiMap = (Map) request.getSession().getAttribute("chengjiMap");
								Map<Integer, String> ktMap = (Map) request.getSession().getAttribute("ktMap");
								if (students != null && yuanxiMap != null && zhuanyeMap != null && chengjiMap != null && ktMap != null)
									for (Cjk cjk : students) {
							%>
							<tr class="status_open tItem">
								<td><%=yuanxiMap.get(cjk.getYx())%></td>
								<td><%=zhuanyeMap.get(cjk.getZy())%></td>
								<td><%=cjk.getXuehao()%></td>
								<td><%=ktMap.get(cjk.getKt())%></td>
								<td><span onclick='chengJiChakKan(this)'>查看成绩</span></td>
							</tr>
							<%
								}
							%>
						</tbody>
					</table>
				</div>
				<div class="pageBTNS">
					<a
						href="../../PaperCheck.do?method=pageSearch&pageNum=<%=pageNum%>&option=pre&requestFrom=pageXs">上一页</a>
					<span>第<%=pageNum%>页/共<%=totalPage%> 页
					</span> <a
						href="../../PaperCheck.do?method=pageSearch&pageNum=<%=pageNum%>&option=next&requestFrom=pageXs">下一页</a>
				</div>
			</div>
		</div>

		<!-- 论文抽检成绩列表面板 -->
		<div id="chengJiPanel"
			style="width: 600px; min-height: 450px; left: 250px; top: -30px; position: absolute; background-color: #E6E6FA;">
			<div class="g_12">
				<div class="widget_header">
					<h4 class="widget_header_title">成绩列表</h4>
				</div>
				<div class="widget_contents noPadding ">
					<table class="tables  noObOLine">
						<thead>
							<tr>
								<th>评阅教师</th>
								<th>成绩</th>
								<th>评语</th>
							</tr>
						</thead>
						<tbody id="tb_chengJi">

						</tbody>
					</table>
				</div>
				<br></br>
				<div
					style="color: #9D9D9D; margin-buttom: 20px; margin-left: 260px;">
					<input id="closeChengJiPanel" type="submit" value="关闭" />
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../foot.jsp"></jsp:include>