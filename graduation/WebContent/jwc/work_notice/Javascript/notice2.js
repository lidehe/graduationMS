/**
 * 
 */
$(function() {
	$(window).resize(function() {
		setNoticeDivCss();// 设置居中和遮罩层大小
	});
	$(".btnclosealert").hover(function() {
		$(this).css("opacity", 1);
	}, function() {
		$(this).css("opacity", 0.2);
	}).click(function() {// 关闭
		clearClose();
	});
	$("#reNotice").click(function() {// 出现
		$('.theme-popover-mask').show();
		$('.jq_dvalert').show().stop().animate({
			"top" : 90 + $(document).scrollTop(),
			"opacity" : 1
		}, 350);
	});

	$(".reNoticeXi").click(function() {// 点击发布“校”公告
		releaseNotice("../../home.do?meth=addXi");
	});

	setNoticeDivCss();// 设置居中和遮罩层大小
	$('.jq_dvalert').css("top", 0 - $('.jq_dvalert').height());// 初始top
});

function releaseNotice(url) {
	var $title = $("#noticeTitle").val();
	var $text = $("#noticeText").val();
	if ($title == "" || $title == null || $text == "" || $text == null) {
		alert("请填写完整！");
		return;
	} else {
		if ($title.length > 80) {
			alert("标题超出长度范围！");
			return;
		} else {
			$(".simple_form_fj1").parents(".g_9").prev().remove();
			$(".simple_form_fj1").parents(".g_9").remove();
			$('.releaseNoticeButton').attr("disabled", true);
			var formData = new FormData($("#noticeForm")[0]);
			var xx = 0;
			$("input.simple_form_fj2").each(function(i){
				formData.append("file"+(xx++),$(this).prop('files')[0]);
			});
			$.ajax({
				url : url,
				type : "POST",
				data : formData,
				cache : false,
				contentType : false,
				processData : false,
				dataType:"json",
				error : function(data) {
					alert("connect error!");
					location.reload();
				},
				success : function(data) {
					if (data.status == 200) {
						clearClose();
						showMsg(data.msg, "top");
						// window.open("home.do");
					} else {
						alert(data.msg);
					}
					$('.releaseNoticeButton').attr("disabled", false);
				}
			});
		}
	}
}

function setNoticeDivCss() {
	var $width = $(document).width();
	if ($width > 903)
		$('.jq_dvalert').css("left", ($width - 903) / 2 + "px");
	else
		$('.jq_dvalert').css("left", "0");
	var $height = $(document).height();
	$(".theme-popover-mask").css("height", $height);
}

function clearClose() {
	$('.theme-popover-mask').hide();
	$('.jq_dvalert').stop().animate({
		"top" : 0 - $('.jq_dvalert').height()
	}, 150, function() {
		$('.jq_dvalert').hide();
	});
	// 再来个清空
	$(window.frames[0].document).find("body").text("")
	$("#noticeText").val("");
	$("#noticeTitle").val("");
	resetFile();
}

function resetFile() {
	$(".simple_form_fj1").val("");
	$(".simple_form_fj2").val("");
	$("#fjGrid")
			.text("")
			.html("")
			.html(
					'<div class="g_3">'
							+ '<span class="label">附件：</span>'
							+ '</div><div class="g_9"><input type="file" '
							+ 'accept=".pdf,.PDF,.zip,.ZIP,.rar,.RAR,.doc,.DOC,.docx,.DOCX"'
							+ ' class="simple_form_fj1" onchange="addInputFile(this)" /></div>');
	$(".simple_form_fj1").uniform();
}

function addInputFile(the) {
	var $this = $(the);
	if ($this.hasClass("simple_form_fj1")) {
		$this.removeClass("simple_form_fj1").addClass("simple_form_fj2");
		$("#fjGrid")
				.append(
						'<div class="g_3">'
								+ '<span class="label">附件：</span>'
								+ '</div><div class="g_9"><input type="file" '
								+ 'accept=".pdf,.PDF,.zip,.ZIP,.rar,.RAR,.doc,.DOC,.docx,.DOCX"'
								+ ' class="simple_form_fj1" onchange="addInputFile(this)" /></div>');
		$(".simple_form_fj1").uniform();
	}
}