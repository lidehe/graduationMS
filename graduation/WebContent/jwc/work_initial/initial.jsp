<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<jsp:include page="../head.jsp"></jsp:include>
<script src="Javascript/roleSet.js"></script>
<script src="Javascript/initial.js"></script>

<div id="roleSet" class="contents">
	<!-- 标记本页面名字，用于左侧菜单背景色变化 -->
	<input type="hidden" id="pageName" value="initial.jsp" />

	<div class="grid_wrapper">
		<div class="g_12 contents_header">
			<h3 class=" tab_label">系统初始化</h3>
		</div>
		<div class="g_12  menu_bar">
			<span class="  active_initialHeadTabs"
				onclick="initialTabClick(this)">开启新一届的毕设工作</span>
		</div>
		<div class="g_12">
			<div class="g_12">
				<span style="color: #9D9D9D">即将开启的毕设工作的届数：如2020： </span> <input
					type="text" class="jieShu" placeholder="请输入届数" />
			</div>
			<div class="g_12">
				<div class="confirm" align="center">
					<button id="confirm" class="confirmBTN">确认开启</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 系统初始化倒计时面板 -->
	<div id="countdownPanel"
		style="border-radius:20px;font-family:微软雅黑;width: 600px; min-height: 150px; left: 200px; top: 70px; position: absolute; background-color: #E6E6FA;">
			<h4 style="color:red;text-align:center;font-size:20px;">系统初始化已经开始,完成后请重新登陆</h4>
			<h4 style="color:red;text-align:center;font-size:20px;">
				预计剩余时间：<span id="countDown"></span>&nbsp;秒
			</h4>
	</div>
</div>
<jsp:include page="../foot.jsp"></jsp:include>