/**
 * 
 */
$(function() {
	$("#ktbgB").click(
			function() {
				if ($("#wj").val() == null || $("#wj").val() == "") {
					alert("请选择上传的文件！");
				} else {
					$("#ktbgB").css("display", "none").parent().find(
							".addGBajax").css("display", "inline-block");
					doUpload();
				}
			});
});
function doUpload() {
	var formData = new FormData($("#ktbgwj")[0]);
	$.ajax({
		url : "upload.do?meth=ktbg",
		type : 'POST',
		data : formData,
		async : false,
		cache : false,
		contentType : false,
		processData : false,
		error: function(request) {
            alert("Connection error");
            location.reload();
        },
		success : function(data) {
			showMsg(data.msg,"center","window.location.href = \"ktbg.do\"");
			$("#ktbgB").css("display", "inline-block").parent().find(
					".addGBajax").css("display", "none");
		},
		error : function(data) {
			alert("connect error!");
			window.location.href = "ktbg.do";
		}
	});
}