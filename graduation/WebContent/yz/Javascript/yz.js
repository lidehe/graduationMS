/**
 * 
 */
$(function(){
	$("#jdxcgB").click(
			function() {
				if ($("#wj").val() == null || $("#wj").val() == "") {
					alert("请选择上传的文件！");
				} else {
					$("#jdxcgB").css("display", "none").parent().find(
							".addGBajax").css("display", "inline-block");
					jdxcgUpload();
				}
			});
});
function Detail(data){
	var $th = $(data);
	var number = $th.parent().find(".hiddenVal").val();
	window.open("yz.do?meth=detail&number="+number);
}

function jdxcgUpload() {
	var formData = new FormData($("#jdxcgwj")[0]);
	$.ajax({
		url : "yz.do?meth=upload",
		type : 'POST',
		data : formData,
		async : false,
		cache : false,
		contentType : false,
		processData : false,
		success : function(data) {
			alert(data.msg);
			$("#jdxcgB").css("display", "inline-block").parent().find(
					".addGBajax").css("display", "none");
			window.location.href = "yz.do";
		},
		error : function(data) {
			alert("connect error!");
			window.location.href = "yz.do";
		}
	});
}