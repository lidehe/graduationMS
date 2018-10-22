/**
 * 
 */
$(function() {
	$("#lwcgB").click(
			function() {
				if ($("#wj").val() == null || $("#wj").val() == "") {
					alert("请选择上传的文件！");
				} else {
					$("#lwcgB").css("display", "none").parent().find(
							".addGBajax").css("display", "inline-block");
					lwcgUpload();
				}
			});
	
	
	$("#LwButton").click(function(){
		var $text = $("#Ltext").val();
		var $th = $(this);
		if($text == "" || $text == null){
			alert("请填写完整！");
		}else{
			$th.css("display","none").parent().find(".ajaxAnimation").css("display","inline-block");
			$.ajax({
				type: "post",
                url:"lwcg.do?meth=zd",
                data:$('#zdForm').serialize(),//form序列化
                async: false,
                error: function(request) {
                    alert("Connection error");
                    window.location.href = "lwcg.do";
                },
                success: function(data) {
                   if(data.status == 202){
                	   alert("字数超出！");
                	   $th.css("display","inline-block").parent().find(".ajaxAnimation").css("display","none");
                   }else if(data.status == 200){
                	   //alert("回复成功！");
                	   window.location.href = "lwcg.do";
                   }else if(data.status == 400){
                	   alert("你还没上传文件！");
                	   $th.css("display","inline-block").parent().find(".ajaxAnimation").css("display","none");
                   }else if(data.status == 500){
                	   alert("现在不允许添加！");
                	   window.location.href = "lwcg.do";
                   }else{
                	   window.location.href = "lwcg.do";
                   }
                }
			});
		}
	});
});
function lwcgUpload() {
	var formData = new FormData($("#lwcgwj")[0]);
	$.ajax({
		url : "upload.do?meth=lwcg",
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
			$("#lwcgB").css("display", "inline-block").parent().find(
					".addGBajax").css("display", "none");
			showMsg(data.msg,"center","window.location.href = \"lwcg.do\"");
		}
	});
}