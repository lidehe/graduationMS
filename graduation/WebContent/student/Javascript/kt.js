/**
 * 课题选择页面的js集合
 */
$(function() {
	$("#ktButton").click(
			function() {
				var $name = $("#kName").val();
				var $text = $("#kText").val();
				var $th = $(this);
				if ($name == "" || $name == null || $text == ""
						|| $text == null) {
					alert("请填写完整！");
				} else {
					$th.css("display", "none").parent().find(".ajaxAnimation")
							.css("display", "inline-block");
					$.ajax({
						type : "post",
						url : "kt.do?meth=add",
						data : $('#ktForm').serialize(),// form序列化
						async : false,
						error : function(request) {
							alert("Connection error");
							window.location.href = "kt.do";
						},
						success : function(data) {
							if (data.status == 400) {
								alert("字数超出！");
								$th.css("display", "inline-block").parent()
										.find(".ajaxAnimation").css("display",
												"none");
							} else if (data.status == 202) {
								alert("你已经选择好课题，无法提交！");
								window.location.href = "kt.do";
							} else if (data.status == 200) {
								showMsg("成功申请课题！", "center", "window.location.href = \"kt.do\"");
							} else if (data.status == 500) {
								showMsg("不可申请，专业负责人尚未委派！","center","window.location.href = \"kt.do\"");
							} else {
								window.location.href = "kt.do";
							}
						}
					});
				}
			});
});

function chooseKt(the) {
	var $th = $(the);
	var ktId = $th.parent().find(".hiddenVal").val();
	$.ajax({
		type : "post",
		url : "kt.do?meth=check",
		data : {
			kt : ktId
		},
		error : function(request) {
			alert("Connection error");
			location.reload();
		},
		success : function(data) {
			if (data.status == 200) {
				sureKt(ktId);
			} else if (data.status == 400) {
				if (confirm("你与小组同学所选课题不一样，确定要选择这个课题吗？")) {
					sureKt(ktId);
				}
			} else
				window.location.href = "kt.do";
		}
	});
}

function sureKt(id) {
	$.ajax({
		type : "post",
		url : "kt.do?meth=choose",
		data : {
			kt : id
		},
		error : function(request) {
			alert("Connection error");
			location.reload();
		},
		success : function(data) {
			if (data.status == 200) {
						showMsg("成功选择课题！", "center",
								"window.location.href = \"ktbg.do\"");
			} else if (data.status == 202) {
				alert("不能重复选择课题！");
			} else if (data.status == 400) {
				alert("该课题未审核或人数已满！");
			} else if (data.status == 500) {
				alert("课题不存在！");
				window.location.href = "kt.do";
			}
		}
	});
}