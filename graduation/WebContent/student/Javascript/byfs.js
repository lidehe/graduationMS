/**
 * 毕业方式页面的js集合
 */
$(function() {
	$(".choose").click(
			function() {// 提交按钮ajax以及简单的验证
				var $th = $(this);
				var $checked = $th.parent().parent().find(".byfsCheckbox")
						.val();
				$(".choose").attr("disabled", true);
				if (!confirm("确定选择此方式？"))
					return;
				if (!isNaN($checked)) {
					$th.css("display", "none").parent().find(".chooseAjax")
							.css("display", "inline-block");
					$.ajax({
						type : "post",
						url : "bylx.do?ser=cho",
						data : {
							id : $checked
						},
						error : function(request) {
							alert("Connection error");
							location.reload();
						},
						success : function(data) {
							$th.css("display", "inline-block").parent().find(
									".chooseAjax").css("display", "none");
							if (data.status == 202) {
								alert("对不起，您已经选择过了毕业方式，不可更改！");
							} else if (data.status == 500) {
								alert("选择的毕业方式不存在！");
							} else if (data.status == 200) {
								data_ = data;
								showMsg("成功选择！", "center",
										"window.location.href = \"kt.do\"");
								// setTimeout("redirect()","3000");
							}
							$(".choose").attr("disabled", false);
						}
					});
				} else {
					alert("请勾选一个！");
				}

			});

	// 创建毕设小组的按钮
	$("#createG").click(
			function() {
				if (confirm("您确定要创建毕设小组吗？")) {
					$("#createG").css("display", "none").parent().find(
							".createGajax").css("display", "inline-block");
					$.ajax({
						type : "post",
						url : "xsz.do?meth=create",
						error : function(request) {
							alert("Connection error");
							location.reload();
						},
						success : function(data) {
							$("#createG").css("display", "inline-block")
									.parent().find(".createGajax").css(
											"display", "none");
							if (data.status == 200) {
								// alert("成功创建小组");
							} else if (data.status == 400) {
								alert("你已被添加到小组");
							}
							window.location.href = "bylx.do";
						}
					});
				}
			});

	// 准备添加成员的操作
	$("#addGB").click(function() {
		searchStudent();
	});
	$("#groupXH").keyup(function(event) {
		if (event.keyCode == 13)
			searchStudent();
	});

});

function searchStudent() {
	var $number = $("#groupXH").val();
	if ($number == "" || $number == null)
		alert("请输入学号！");
	else {
		$("#addGB").css("display", "none").parent().find(".addGBajax").css(
				"display", "inline-block");
		$.ajax({
			type : "post",
			url : "xsz.do?meth=search",
			data : {
				number : $number
			},
			error : function(request) {
				alert("Connection error");
				location.reload();
			},
			success : function(data) {
				confirmAdd(data);
			}
		});
	}
}

function confirmAdd(data) {
	if (data.status == 404) {
		alert("不存在该同学！");
		$("#addGB").css("display", "inline-block").parent().find(".addGBajax")
				.css("display", "none");
	} else if (data.status == 500) {
		alert("(" + data.xsxh + ")" + data.name + "同学已经有小组了！");
		$("#addGB").css("display", "inline-block").parent().find(".addGBajax")
				.css("display", "none");
	} else if (data.status == 200) {
		if (confirm("你确定把(" + data.xsxh + ")" + data.name + "同学添加到小组？")) {
			sureAdd(data.xsxh, data.name);
		}
	} else {
		window.location.href = "bylx.do";
	}
	$("#groupXH").val("");
}

function sureAdd(number, name) {
	$
			.ajax({
				type : "post",
				url : "xsz.do?meth=add",
				data : {
					number : number
				},
				error : function(request) {
					alert("Connection error");
					location.reload();
				},
				success : function(data) {
					if (data.status == 200) {
						// alert("添加成功！");
						$("#addGB").css("display", "inline-block").parent()
								.find(".addGBajax").css("display", "none");
						var $tr = "<tr><td>"
								+ number
								+ "</td><td>"
								+ name
								+ "</td><td style='text-align:center'>"
								+ "<input type='hidden' value='"
								+ number
								+ "' class='hiddenVal' />"
								+ "<input type='button' value='移 除' onclick='removeGroup(this)' class='submitIt simple_buttons removeGB' /></td></tr>";
						$("#groupTable").append($tr);
					} else if (data.status == 400) {
						alert(name + "同学已经抢先加入小组了");
					} else {
						window.location.href = "bylx.do";
					}
				}
			});
}

function removeGroup(the) {
	var $th = $(the);
	if (confirm("你确定移除该同学？")) {
		var $number = $th.parent().find(".hiddenVal").val();
		$.ajax({
			type : "post",
			url : "xsz.do?meth=remove",
			data : {
				number : $number
			},
			error : function(request) {
				alert("Connection error");
				location.reload();
			},
			success : function(data) {
				if (data.status == 200) {
					$th.parents("tr").remove();
				} else if (data.status == 400) {
					alert("该同学早已不在小组内！");
					$th.parents("tr").remove();
				} else {
					window.location.href = "bylx.do";
				}
			}
		});
	}
}
