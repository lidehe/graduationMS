<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="../head.jsp"></jsp:include>
<link rel="stylesheet" href="Css/menu_style.css" />
<script type="text/javascript" src="Javascript/appraising.js"></script>
<div class="contents">

	<!-- 标记本页面名字，用于左侧菜单背景色变化 -->
	<input type="hidden" id="pageName" value="appraising.jsp" />

	<div class="grid_wrapper">
		<div class="g_12 contents_header">
			<h3 class=" tab_label">优秀论文</h3>
		</div>
		<div class="g_12  menu_bar">
			<span class=" active_initialHeadTabs" onclick="initialTabClick(this)">各院论文数量设定</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">校级优秀论文</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">省级优秀论文</span>
		</div>
		<!--页面标记，JS加载信息时，仅在需要的页面才会请求信息，可有效减少不必要的请求  -->
		<input type="hidden" id="pageWhitch" value="各院论文数量设定" />
		<div class="g_12">
			<div class="g_12">
				<!--论文数量输入提示  -->
				<span style="color: red">论文数量为十进制整数，且数量不超过100 </span>
			</div>
			<div class="g_12">
				<div class="widget_header">
					<h4 class="widget_header_title ">数量设置</h4>
				</div>
				<div class="widget_contents noPadding">
					<table class="tables ">
						<thead>
							<tr>
								<th>院系</th>

								<th>个人优秀论文总数</th>
								<th>小组优秀论文总数</th>
							</tr>
						</thead>
						<tbody id="tb_yuanxi">


						</tbody>
					</table>
				</div>
			</div>
			<div class="saveChange">
				<button id="saveCountChange" class="confirmBTN">保存更改</button>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../foot.jsp"></jsp:include>