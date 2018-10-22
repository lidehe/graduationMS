/**
 * 
 */
$(function(){
	
	$(".sxcjpsbB").click(function(){
		var $th = $(this);
		var $num = $("#CheckedNumber").val();
		if ($num == 0)
			return;
//		var $yi = $(".syi").val() > 0 ? parseFloat($(".syi")
//				.val()) : 0;
//		var $er = $(".ser").val() > 0 ? parseFloat($(".ser")
//				.val()) : 0;
//		var $san = $(".ssan").val() > 0 ? parseFloat($(".ssan")
//				.val()) : 0;
//		var $si = $(".ssi").val() > 0 ? parseFloat($(".ssi")
//				.val()) : 0;
//		var $wu = $(".swu").val() > 0 ? parseFloat($(".swu")
//				.val()) : 0;
//		var $zf = parseFloat($(".szf").val());
//		var $text1 = $(".stxt1").val();
//		if ($yi < 0 || $yi > 10 || $er < 0 || $er > 20
//				|| $san < 0 || $san > 20 || $si < 0 || $si > 40
//				|| $wu < 0 || $wu > 10) {
//			alert("请填写完整分数！");
//			return;
//		}
//		if ($text1 == "" || $text1 == null) {
//			alert("请填写完鉴定！");
//			return;
//		}
		$th.css("display", "none").parent().find(
				".ajaxAnimation")
				.css("display", "inline-block");
		$.ajax({
			url : "zsx.do?meth=update",
			type : "post",
			data : $('#sxcjpsbForm').serialize(),
			error : function(request) {
				alert("Connection error");
				location.reload();
			},
			success : function(data) {
				if(data.status == 200){
					//setFormS(data);
					showMsg(data.msg,"top");
				}else{
					alert(data.msg);
				}
				$th.css("display", "inline-block").parent()
						.find(".ajaxAnimation").css("display",
								"none");
			}
		});
	});
	
//	$(".score1").change(function() {
//		var $yi = $(".syi").val() > 0 ? parseFloat($(".syi").val()) : 0;
//		var $er = $(".ser").val() > 0 ? parseFloat($(".ser").val()) : 0;
//		var $san = $(".ssan").val() > 0 ? parseFloat($(".ssan").val()) : 0;
//		var $si = $(".ssi").val() > 0 ? parseFloat($(".ssi").val()) : 0;
//		var $wu = $(".swu").val() > 0 ? parseFloat($(".swu").val()) : 0;
//		$(".szf").val($yi + $er + $san + $si + $wu);
//	});
});

function detailSx(the){
	var $th = $(the);
	var $name = $th.parents("tr").find(".stuName").text();
	var $num = $th.parents("td").find(".hiddenVal").val();
	$(".tipsMessage").text($name);
	$("#sxcjpsbForm")[0].reset();
	$("#CheckedNumber").val($num);
	$.ajax({//实习表的请求
		url : "zsx.do?meth=load",
		type : "post",
		data : {
			number : $num
		},
		error : function(request) {
			alert("Connection error");
			location.reload();
		},
		success:function(data){
			if (data.status == 202) {
				alert(data.msg);
			} else {
				setFormS(data);
			}
		}
	});
	$('html, body').animate({  
        scrollTop: $("#sxcjM").offset().top  
    }, 1000);
}

function setFormS(data){
	$.each(data.content, function() {
		if ($(".sxcj").get(i).tagName == "input") {
			$(".sxcj").get(i).val(this.fs);
		} else {
			$(".sxcj").get(i).val(this.text);
		}
		i++;
	});
}