/**
 * 
 */
$(function() {

});

function shenheK(the) {
	var $th = $(the);
	var id = $th.parent().find(".hiddenId").val();
	$.ajax({
		url : "zkt.do?meth=sh1",
		type : "post",
		data : {
			id : id
		},
		error : function(request) {
			alert("Connection error");
			location.reload();
		},
		success : function(data) {
			if (data.status == 200) {
				$th.parents("td").html("").text("已通过");
			} else {
				alert(data.msg);
			}
		}
	});
}

function refuseK(the) {
	var $th = $(the);
	var id = $th.parent().find(".hiddenId").val();
	$.ajax({
		url : "zkt.do?meth=sh2",
		type : "post",
		data : {
			id : id
		},
		error : function(request) {
			alert("Connection error");
			location.reload();
		},
		success : function(data) {
			if (data.status == 200) {
				$th.parents("td").html("").text("已拒绝");
			} else {
				alert(data.msg);
			}
		}
	});
}