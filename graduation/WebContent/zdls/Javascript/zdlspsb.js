/**
 * 
 */
$(function() {
	$("#studentNum").change(function() {
		var $num = $(this).val();
		if ($num == 0) {
			$("#zdlspsbForm")[0].reset();
			return;
		}
		$(".ajaxLoad").css("display", "inline-block");
		$.ajax({// 指导老师评审表的请求
			url : "zdlspsb.do?meth=load",
			type : "post",
			data : {
				number : $num
			},
			error : function(request) {
				alert("Connection error");
				location.reload();
			},
			success : function(data) {
				if (data.status == 202) {
					$("#zdlspsbForm")[0].reset();
					$("#studentNum").val(0);
					$("#uniform-studentNum").find("span").text("").html("--请选择--");
					alert(data.msg);
				} else if (data.status == 200) {
					$("#zdlspsbForm")[0].reset();
					// $(".zxsxm").val(data.zxsxm);
					// $(".zzy").val(data.zzy);
					// $(".zbj").val(data.zbj);
					// $(".zktm").val(data.zktm);
					// $(".zzdjs").val(data.zzdjs);
					$(".zdb").val(data.zdb);
					$("#zdlspsbB").text("").html("").html("提 交");
				} else if (data.status == 400) {
					setForm(data);
					$("#zdlspsbB").text("").html("").html("更 新");
				}
			}
		});

		$.ajax({// 实习表的请求
			url : "zdlspsb.do?meth=loadS",
			type : "post",
			data : {
				number : $num
			},
			error : function(request) {
				alert("Connection error");
				location.reload();
			},
			success : function(data) {
				if (data.status == 202) {
					$("#sxcjpsbForm")[0].reset();
					// alert(data.msg);只需要一次alert就够了
				} else if (data.status == 200) {
					$("#sxcjpsbForm")[0].reset();
					// $(".szdls").val(data.szdls);
					// $(".sdate").val(data.sdate);
					// $(".sfzr").val(data.sfzr);
					// $(".sdate1").val(data.sdate1);
					$("#sxcjpsbB").text("").html("").html("提 交");
				} else if (data.status == 400) {
					setFormS(data);
					$("#sxcjpsbB").text("").html("").html("更 新");
				}
			}
		});
		$(".ajaxLoad").css("display", "none");
	});

	$("#zdlspsbB").click(
			function() {
				var $th = $(this);
				var $num = $("#studentNum").val();
				if ($num == 0)
					return;
				// var $yi = $(".zyi").val() > 0 ? parseFloat($(".zyi")
				// .val()) : 0;
				// var $er = $(".zer").val() > 0 ? parseFloat($(".zer")
				// .val()) : 0;
				// var $san = $(".zsan").val() > 0 ? parseFloat($(".zsan")
				// .val()) : 0;
				// var $si = $(".zsi").val() > 0 ? parseFloat($(".zsi")
				// .val()) : 0;
				// var $wu = $(".zwu").val() > 0 ? parseFloat($(".zwu")
				// .val()) : 0;
				// var $liu = $(".zliu").val() > 0 ? parseFloat($(".zliu")
				// .val()) : 0;
				// var $db = $(".zdb").val();
				// var $zf = parseFloat($(".zzf").val());
				// var $text = $(".ztxt").val();
				// if ($yi < 0 || $yi > 15 || $er < 0 || $er > 20
				// || $san < 0 || $san > 25 || $si < 0 || $si > 15
				// || $wu < 0 || $wu > 15 || $liu < 0 || $liu > 10) {
				// alert("请填写完整分数！");
				// return;
				// }
				// if ($db != "是" && $db != "否") {
				// alert("请重新填写是否参加答辩项！");
				// return;
				// }
				// if ($text == "" || $text == null) {
				// alert("请填写完评语！");
				// return;
				// }
				$th.css("display", "none").parent().find(".ajaxAnimation").css(
						"display", "inline-block");
				$.ajax({
					url : "zdlspsb.do?meth=addpsb",
					type : "post",
					data : $('#zdlspsbForm').serialize(),
					error : function(request) {
						alert("Connection error");
						location.reload();
					},
					success : function(data) {
						if (data.status == 201) {
							alert(data.msg);
							$("#isdb").val(0);
						} else if (data.status == 202) {
							alert(data.msg);
							$("#isdb").val(1);
						} else if (data.status == 200) {
							showMsg(data.msg, "top");
						} else {
							alert(data.msg);
						}
						$th.css("display", "inline-block").parent().find(
								".ajaxAnimation").css("display", "none");
					}
				});
			});

	$(".sxcjpsbB")
			.click(
					function() {
						var $th = $(this);
						var $num = $("#studentNum").val();
						if ($num == 0)
							return;
//						var $yi = $(".syi").val() > 0 ? parseFloat($(".syi")
//								.val()) : 0;
//						var $er = $(".ser").val() > 0 ? parseFloat($(".ser")
//								.val()) : 0;
//						var $san = $(".ssan").val() > 0 ? parseFloat($(".ssan")
//								.val()) : 0;
//						var $si = $(".ssi").val() > 0 ? parseFloat($(".ssi")
//								.val()) : 0;
//						var $wu = $(".swu").val() > 0 ? parseFloat($(".swu")
//								.val()) : 0;
//						var $zf = parseFloat($(".szf").val());
//						var $text = $(".stxt").val();
//						var $text1 = $(".stxt1").val();
//						if ($yi < 0 || $yi > 10 || $er < 0 || $er > 20
//								|| $san < 0 || $san > 20 || $si < 0 || $si > 40
//								|| $wu < 0 || $wu > 10) {
//							alert("请填写完整分数！");
//							return;
//						}
//						if ($text == "" || $text == null) {
//							alert("请填写完鉴定！");
//							return;
//						}
						$th.css("display", "none").parent().find(
								".ajaxAnimation")
								.css("display", "inline-block");
						$.ajax({
							url : "zdlspsb.do?meth=addSx",
							type : "post",
							data : $('#sxcjpsbForm').serialize(),
							error : function(request) {
								alert("Connection error");
								location.reload();
							},
							success : function(data) {
								if (data.status == 200) {
									showMsg(data.msg, "top");
								} else {
									alert(data.msg);
								}
								$th.css("display", "inline-block").parent()
										.find(".ajaxAnimation").css("display",
												"none");
							}
						});
					});

//	$(".score").change(function() {
//		var $yi = $(".zyi").val() > 0 ? parseFloat($(".zyi").val()) : 0;
//		var $er = $(".zer").val() > 0 ? parseFloat($(".zer").val()) : 0;
//		var $san = $(".zsan").val() > 0 ? parseFloat($(".zsan").val()) : 0;
//		var $si = $(".zsi").val() > 0 ? parseFloat($(".zsi").val()) : 0;
//		var $wu = $(".zwu").val() > 0 ? parseFloat($(".zwu").val()) : 0;
//		var $liu = $(".zliu").val() > 0 ? parseFloat($(".zliu").val()) : 0;
//		$(".zzf").val($yi + $er + $san + $si + $wu + $liu);
//	});
//
//	$(".score1").change(function() {
//		var $yi = $(".syi").val() > 0 ? parseFloat($(".syi").val()) : 0;
//		var $er = $(".ser").val() > 0 ? parseFloat($(".ser").val()) : 0;
//		var $san = $(".ssan").val() > 0 ? parseFloat($(".ssan").val()) : 0;
//		var $si = $(".ssi").val() > 0 ? parseFloat($(".ssi").val()) : 0;
//		var $wu = $(".swu").val() > 0 ? parseFloat($(".swu").val()) : 0;
//		$(".szf").val($yi + $er + $san + $si + $wu);
//	});
});

function setForm(data) {// 一份完整的数据回来之后要显示信息
	// $(".zxsxm").val(data.zxsxm);
	// $(".zzy").val(data.zzy);
	// $(".zbj").val(data.zbj);
	// $(".zktm").val(data.zktm);
	var i = 0;
	$.each(data.content, function() {
		if ($(".zdlspsb").get(i).tagName == "input") {
			$(".zdlspsb").get(i).val(this.fs);
		} else {
			$(".zdlspsb").get(i).val(this.text);
		}
		i++;
	});
	$("#isdb").val(data.zdb);
}

function setFormS(data) {
	// $(".sfzr").val(data.sfzr);
	$.each(data.content, function() {
		if ($(".sxcj").get(i).tagName == "input") {
			$(".sxcj").get(i).val(this.fs);
		} else {
			$(".sxcj").get(i).val(this.text);
		}
		i++;
	});
}