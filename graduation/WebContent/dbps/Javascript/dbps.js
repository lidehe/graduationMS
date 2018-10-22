/**
 * 
 */
$(function() {
	$("#dbpsz").change(function() {
		if ($(this).val() != 0)
			loadDbz();
	});

	$("#studentNum").change(function() {
		var $num = $(this).val();
		if ($num == 0) {
			$("#dbpsbForm")[0].reset();
			$("#pypsbForm")[0].reset();
		} else {
			$(".ajaxLoad2").css("display", "inline-block");
			loadDbInfo($num);
			loadPyInfo($num);
			$(".ajaxLoad2").css("display", "none");
		}
	});

	$("#dbsbB").click(
			function() {
				$th = $(this);
				var $num = $("#studentNum").val();
				if ($num == 0)
					return;
				// var $yi = $(".dyi").val() > 0 ? parseFloat($(".dyi")
				// .val()) : 0;
				// var $er = $(".der").val() > 0 ? parseFloat($(".der")
				// .val()) : 0;
				// var $san = $(".dsan").val() > 0 ? parseFloat($(".dsan")
				// .val()) : 0;
				// var $si = $(".dsi").val() > 0 ? parseFloat($(".dsi")
				// .val()) : 0;
				// var $wu = $(".dwu").val() > 0 ? parseFloat($(".dwu")
				// .val()) : 0;
				// var $liu = $(".dliu").val() > 0 ? parseFloat($(".dliu")
				// .val()) : 0;
				// var $text = $(".dtxt").val();
				// var $text1 = $(".dtxt1").val();
				// var $zf = parseFloat($(".dzf").val());
				// if ($yi < 0 || $yi > 30 || $er < 0 || $er > 20 || $san < 0
				// || $san > 10 || $si < 0 || $si > 10 || $wu < 0
				// || $wu > 15 || $liu < 0 || $liu > 15) {
				// alert("请填写完整分数！");
				// return;
				// }
				// if ($text == "" || $text == null || $text1 == "" || $text1 ==
				// null) {
				// alert("请填写完答辩记录和评语！");
				// return;
				// }
				$th.css("display", "none").parent().find(".ajaxAnimation").css(
						"display", "inline-block");
				$.ajax({
					url : "dbz.do?meth=addDb",
					type : "post",
					data : $('#dbpsbForm').serialize(),
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
						$th.css("display", "inline-block").parent().find(
								".ajaxAnimation").css("display", "none");
					}
				})
			});

	$("#pypsbB").click(
			function() {
				$th = $(this);
				var $num = $("#studentNum").val();
				if ($num == 0)
					return;
				// var $yi = $(".pyi").val() > 0 ? parseFloat($(".pyi")
				// .val()) : 0;
				// var $er = $(".per").val() > 0 ? parseFloat($(".per")
				// .val()) : 0;
				// var $san = $(".psan").val() > 0 ? parseFloat($(".psan")
				// .val()) : 0;
				// var $si = $(".psi").val() > 0 ? parseFloat($(".psi")
				// .val()) : 0;
				// var $wu = $(".pwu").val() > 0 ? parseFloat($(".pwu")
				// .val()) : 0;
				// var $liu = $(".pliu").val() > 0 ? parseFloat($(".pliu")
				// .val()) : 0;
				// var $db = $(".pdb").val();
				// var $zf = parseFloat($(".pzf").val());
				// var $text = $(".ptxt").val();
				// if ($yi < 0 || $yi > 20 || $er < 0 || $er > 25 || $san < 0
				// || $san > 15 || $si < 0 || $si > 15 || $wu < 0
				// || $wu > 15 || $liu < 0 || $liu > 10) {
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
					url : "dbz.do?meth=addPy",
					type : "post",
					data : $('#pypsbForm').serialize(),
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
						$th.css("display", "inline-block").parent().find(
								".ajaxAnimation").css("display", "none");
					}
				})
			});

	// $(".score1").change(function() {
	// var $yi = $(".pyi").val() > 0 ? parseFloat($(".pyi").val()) : 0;
	// var $er = $(".per").val() > 0 ? parseFloat($(".per").val()) : 0;
	// var $san = $(".psan").val() > 0 ? parseFloat($(".psan").val()) : 0;
	// var $si = $(".psi").val() > 0 ? parseFloat($(".psi").val()) : 0;
	// var $wu = $(".pwu").val() > 0 ? parseFloat($(".pwu").val()) : 0;
	// var $liu = $(".pliu").val() > 0 ? parseFloat($(".pliu").val()) : 0;
	// $(".pzf").val($yi + $er + $san + $si + $wu + $liu);
	// });
	//	
	// $(".score").change(function() {
	// var $yi = $(".dyi").val() > 0 ? parseFloat($(".dyi").val()) : 0;
	// var $er = $(".der").val() > 0 ? parseFloat($(".der").val()) : 0;
	// var $san = $(".dsan").val() > 0 ? parseFloat($(".dsan").val()) : 0;
	// var $si = $(".dsi").val() > 0 ? parseFloat($(".dsi").val()) : 0;
	// var $wu = $(".dwu").val() > 0 ? parseFloat($(".dwu").val()) : 0;
	// var $liu = $(".dliu").val() > 0 ? parseFloat($(".dliu").val()) : 0;
	// $(".dzf").val($yi + $er + $san + $si + $wu + $liu);
	// });

});

function loadDbz() {
	$th = $("#dbpsz");
	var $zh = $th.val();
	$(".ajaxLoad1").css("display", "inline-block");
	$("#studentNum").attr("disabled", "disabled");
	$.ajax({
		url : "dbz.do?meth=load",
		type : "post",
		data : {
			zh : $zh
		},
		success : function(data) {
			if (data.status == 200) {
				var students = data.students;
				$("#dbpsbForm")[0].reset();
				$("#pypsbForm")[0].reset();
				var $select = $("#studentNum");
				$(".studentOption").remove();
				for ( var o in students) {
					var $studentO = $("<option class='studentOption' value='"
							+ students[o].number + "'>" + students[o].name
							+ "</option>");
					$select.append($studentO);
				}
				$("span.dbzcy").html("").text(data.teacherName);
			} else {
				alert(data.msg);
			}
			$("#studentNum").attr("disabled", false);
			$(".ajaxLoad1").css("display", "none");
		},
		error : function(request) {
			alert("Connection error");
			location.reload();
		}
	});
}

function loadDbInfo($num) {
	$.ajax({
		url : "dbz.do?meth=loadDb",
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
				$("#dbpsbForm")[0].reset();
				alert(data.msg);
				$("#studentNum").val(0);
				$("#uniform-studentNum").find("span").text("").html("--请选择--");
			} else if (data.status == 200) {
				$("#dbpsbForm")[0].reset();
				// $(".dxsxm").val(data.dxsxm);
				// $(".dzy").val(data.dzy);
				// $(".dbj").val(data.dbj);
				// $(".dktm").val(data.dktm);
				// $(".dzdjs").val(data.dzdjs);
				// $(".dyear").val(data.dyear);
				// $(".dmonth").val(data.dmonth);
				// $(".dday").val(data.dday);
				$("#dbsbB").text("").html("").html("提 交");
			} else if (data.status == 400) {
				setDbForm(data);
				$("#dbsbB").text("").html("").html("更 新");
			}
		}
	});
}

function setDbForm(data) {
	var i = 0;
	$.each(data.content, function() {
		if ($(".dbpsb").get(i).tagName == "input") {
			$(".dbpsb").get(i).val(this.fs);
		} else {
			$(".dbpsb").get(i).val(this.text);
		}
		i++;
	});
}

function loadPyInfo($num) {
	$.ajax({
		url : "dbz.do?meth=loadPy",
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
				$("#pypsbForm")[0].reset();
				// alert(data.msg);只需要alert一次就够了
			} else if (data.status == 200) {
				$("#pypsbForm")[0].reset();
				// $(".pxsxm").val(data.pxsxm);
				// $(".pzy").val(data.pzy);
				// $(".pbj").val(data.pbj);
				// $(".pktm").val(data.pktm);
				// $(".pdb").val(data.pdb);
				// $(".pzdjs").val(data.pzdjs);
				// $(".pyear").val(data.pyear);
				// $(".pmonth").val(data.pmonth);
				// $(".pday").val(data.pday);
				$("#pypsbB").text("").html("").html("提 交");
			} else if (data.status == 400) {
				setPyForm(data);
				$("#pypsbB").text("").html("").html("更 新");
			}
		}
	});
}

function setPyForm(data) {
	var i = 0;
	$.each(data.content, function() {
		if ($(".pypsb").get(i).tagName == "input") {
			$(".pypsb").get(i).val(this.fs);
		} else {
			$(".pypsb").get(i).val(this.text);
		}
		i++;
	})
}
