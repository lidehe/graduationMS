/**
 * 
 */
$(function(){
	$("#sxcjpsbB").click(function(){
		var $text = $("#rzpy").val();
		var $fs = $("#fs").val() > 0 ? parseFloat($("#fs").val()) : 0;
		if($text == "" || $text == null || $fs < 0 || $fs > 100){
			alert("请填写完整！");
			return;
		}else{
			$("#sxcjpsbB").css("display","none").parent().find(".ajaxAnimation").css("display","inline-block");
			$.ajax({
				url : "pyry.do?meth=add",
				type : "post",
				data : {
					number : $("#sNum").val(),
					text : $text,
					fs : $fs
				},
				success : function(data) {
					if (data.status == 200) {
						showMsg(data.msg, "top");
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
	
	$("#sxcjpsbB1").click(function(){
		var $text = $("#rzpy").val();
		var $fs = $("#fs").val() > 0 ? parseFloat($("#fs").val()) : 0;
		if($text == "" || $text == null || $fs < 0 || $fs > 100){
			alert("请填写完整！");
			return;
		}else{
			$("#sxcjpsbB1").css("display","none").parent().find(".ajaxAnimation").css("display","inline-block");
			$.ajax({
				url : "pyry.do?meth=addz",
				type : "post",
				data : {
					zuhao : $("#sNum").val(),
					text : $text,
					fs : $fs
				},
				success : function(data) {
					if (data.status == 200) {
						showMsg(data.msg, "top");
					} else {
						alert(data.msg);
					}
					$("#sxcjpsbB1").css("display","inline-block").parent().find(".ajaxAnimation").css("display","none");
				},
				error : function(request) {
					alert("Connection error");
					location.reload();
				}
			});
		}
	});
	
	$("#sxcjpsbB2").click(function(){
		var $text = $("#rzpy").val();
		var $fs = $("#fs").val() > 0 ? parseFloat($("#fs").val()) : 0;
		if($text == "" || $text == null || $fs < 0 || $fs > 100){
			alert("请填写完整！");
			return;
		}else{
			$("#sxcjpsbB2").css("display","none").parent().find(".ajaxAnimation").css("display","inline-block");
			$.ajax({
				url : "ypyry.do?meth=add",
				type : "post",
				data : {
					zuhao : $("#sNum").val(),
					text : $text,
					fs : $fs
				},
				success : function(data) {
					if (data.status == 200) {
						showMsg(data.msg, "top");
					} else {
						alert(data.msg);
					}
					$("#sxcjpsbB2").css("display","inline-block").parent().find(".ajaxAnimation").css("display","none");
				},
				error : function(request) {
					alert("Connection error");
					location.reload();
				}
			});
		}
	});
	
	$("#sxcjpsbB3").click(function(){
		var $text = $("#rzpy").val();
		var $fs = $("#fs").val() > 0 ? parseFloat($("#fs").val()) : 0;
		if($text == "" || $text == null || $fs < 0 || $fs > 100){
			alert("请填写完整！");
			return;
		}else{
			$("#sxcjpsbB3").css("display","none").parent().find(".ajaxAnimation").css("display","inline-block");
			$.ajax({
				url : "ypyry.do?meth=addz",
				type : "post",
				data : {
					zuhao : $("#sNum").val(),
					text : $text,
					fs : $fs
				},
				success : function(data) {
					if (data.status == 200) {
						showMsg(data.msg, "top");
					} else {
						alert(data.msg);
					}
					$("#sxcjpsbB3").css("display","inline-block").parent().find(".ajaxAnimation").css("display","none");
				},
				error : function(request) {
					alert("Connection error");
					location.reload();
				}
			});
		}
	});
	
	
});