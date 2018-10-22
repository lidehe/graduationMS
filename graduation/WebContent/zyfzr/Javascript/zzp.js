/**
 * 
 */
$(function() {
	$("#zpB").click(function() {
		var $text = $("#Zzp").val();
		if ($text == null || $text == "") {
			showMsg("请填写完总评！", "top");
			return;
		}
		$.ajax({
			url : "zzp.do?meth=add",
			type : "post",
			data : {
				text : $text
			},
			error : function(data) {
				alert("Connection error");
				location.reload();
			},
			success : function(data) {
				if (data.status == 200) {
					showMsg(data.msg, "top");
				} else {
					showMsg(data.msg, "top");
					location.reload();
					if (data.status == 202) {
						$("#Zzp").val(data.text);
					}
				}
			}
		});
	});

	$("#huiF").click(
			function() {
				$(this).css("display", "none").parent().find(".ajaxAnimation")
						.css("display", "inline-block");
				$.ajax({
					url : "zzp.do?meth=hui",
					type : "post",
					error : function(data) {
						alert("Connection error");
						location.reload();
					},
					success : function(data) {
						if (data.status == 200) {
							showMsg("本次共统计了" + data.sum + "位学生，其中及格" + data.jg
									+ "位。", "center",
									"window.location.href=\"zzp.do\"");
						} else if (data.status == 201) {
							showMsg(data.msg, "center",
									"window.location.href=\"zzp.do\"");
						}
						$("#huiF").css("display", "inline-block").parent()
								.find(".ajaxAnimation").css("display", "none");
					}
				});
			});
});