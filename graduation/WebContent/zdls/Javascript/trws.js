/**
 * 
 */
$(function(){
	$(".uploadB").click(function(){
		var $th = $(this);
		var $wj = $th.parents("tr").find(".rwsInput");
		var $form = $th.parents("tr").find(".rwswj")[0];
		if($wj.val() == "" || $wj.val() == null){
			alert("请选择要上传的文件！");
		}else{
			$th.css("display", "none").parent().find(".createGajax").css("display", "inline-block");
			rwsUpload($th,$form);
		}
	});
});

function rwsUpload($th,$form){
	var formData = new FormData($form);
	$.ajax({
		url : "trws.do?meth=upload",
		type : 'POST',
		data : formData,
		async : false,
		cache : false,
		contentType : false,
		processData : false,
		success : function(data) {
			alert(data.msg);
			$th.html("").text("覆  盖");
			$th.css("display", "inline-block").parent().find(".createGajax").css("display", "none");
			$th.parents("tr").find(".rwsText").text("").html("").text("已上传");
			window.location.href = "trws.do";
		},
		error : function(data) {
			alert("connect error!");
			window.location.href = "trws.do";
		}
	});
}