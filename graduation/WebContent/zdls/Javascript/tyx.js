/**
 * 
 */
$(function(){
	$("#gyxB").click(function(){
		var $th = $(this);
		var $num = $th.parent().find(".hiddenVal").val();
		$th.css("display", "none").parent().find(".ajaxAnimation").css(
				"display", "inline-block");
		$.ajax({
			url : "tyxlw.do?meth=addg",
			type : "post",
			data : {
				xuehao : $num
			},
			error : function(request) {
				alert("Connection error");
				location.reload();
			},
			success : function(data) {
				if (data.status == 200) {
					showMsg(data.msg, "top");
					$th.parents("td").html("").text("").text("已推荐个人");
				} else {
					alert(data.msg);
				}
				$th.css("display", "inline-block").parent().find(
						".ajaxAnimation").css("display", "none");
			}
		});
	});
	
	$("#zyxB").click(function(){
		var $th = $(this);
		var $num = $th.parent().find(".hiddenVal").val();
		$th.css("display", "none").parent().find(".ajaxAnimation").css(
				"display", "inline-block");
		$.ajax({
			url : "tyxlw.do?meth=addz",
			type : "post",
			data : {
				xuehao : $num
			},
			error : function(request) {
				alert("Connection error");
				location.reload();
			},
			success : function(data) {
				if (data.status == 200) {
					showMsg(data.msg, "top");
					$th.parents("td").html("").text("").text("已推荐小组");
				} else {
					alert(data.msg);
				}
				$th.css("display", "inline-block").parent().find(
						".ajaxAnimation").css("display", "none");
			}
		});
	});
});