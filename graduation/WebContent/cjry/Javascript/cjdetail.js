/**
 * 
 */
$(function(){
	$("#sxcjpsbB").click(function(){
		var $text = $("#rzpy").val();
		var $grade = $("#cGrade").val();
		if($text == "" || $text == null || ($grade != 0 && $grade != 1 && $grade != 2 &&$grade != 3)){
			alert("请填写完整！");
			return;
		}else{
			$("#sxcjpsbB").css("display","none").parent().find(".ajaxAnimation").css("display","inline-block");
			$.ajax({
				url : "cjry.do?meth=add",
				type : "post",
				data : {
					number : $("#sNum").val(),
					grade: $grade,
					text : $text
				},
				dataType:"json",
				success : function(data) {
					if (data.status == 200) {
						showMsg(data.msg, "top");
						$("#sxcjpsbB").text("").html("").html("更 新");
					} else {
						alert(data.msg);
					}
					$("#sxcjpsbB").css("display","inline-block").parent().find(".ajaxAnimation").css("display","none");
				},
				error : function(request) {
					alert("Connection error");
					location.reload();
				}
			});
		}
	});
});