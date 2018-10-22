<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="../head.jsp"></jsp:include>
<script src="Javascript/notice.js"></script>
<div class="contents">
	<!-- 标记本页面名字，用于左侧菜单背景色变化 -->
	<input type="hidden" id="pageName" value="notice.jsp" /> <input
		type="hidden" id="pageId" value="noticeAdd" />
	<div class="grid_wrapper">

		<div class="g_12 contents_header">
			<h3 class=" tab_label">校园公告</h3>
		</div>

		<div class="g_12  menu_bar">
			<span class=" active_initialHeadTabs" onclick="initialTabClick(this)">发布公告</span>
			<span class=" initialHeadTabs" onclick="initialTabClick(this)">公告管理</span>
		</div>


		<div style="min-height: 54px; padding: 20px;">
			<button title="关闭" class="btnclosealert"
				style="outline: 0px; float: right; margin-top: -10px; font-size: 18px; font-weight: bold; color: rgb(0, 0, 0); text-shadow: 0px 1px 0px rgb(255, 255, 255); border: 0px; background-color: transparent; cursor: pointer; opacity: 0.2; filter: alpha(opacity = 20); -moz-opacity: 0.2;">×</button>
			<form method="post" id="noticeForm" enctype="multipart/form-data">
				<div class="widget_contents noPadding">
					<div class="line_grid">
						<div class="g_12">
							<input id="noticeTitle" name="title" class="simple_field"
								type="text" placeholder="院系公告的标题" />
						</div>
					</div>
					<div class="line_grid">
						<div class="g_12">
							<textarea id="noticeText" name="text"
								class="simple_field wysiwyg"></textarea>
							<div class="field_notice">在上面的文本域里撰写正文</div>
						</div>
					</div>
					<div class="line_grid" id="fjGrid">
						<div class="g_3">
							<span class="label">附件：</span>
						</div>
						<div class="g_9">
							<input type="file" class="simple_form simple_form_fj1"
								accept=".pdf,.PDF,.zip,.ZIP,.rar,.RAR,.doc,.DOC,.docx,.DOCX"
								onchange="addInputFile(this)" />
						</div>
					</div>
				</div>
			</form>
		</div>
		<div
			style="overflow: auto; padding: 12px 14px; background-color: rgb(239, 243, 248);">
			<input class="reNoticeXi releaseNoticeButton" type="button"
				value=" ✓ 发布" style="float: right;">
		</div>
		<script src="Javascript/notice2.js"></script>
	</div>
</div>
<jsp:include page="../foot.jsp"></jsp:include>