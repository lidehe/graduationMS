/**
 * 
 */
$(function(){
	$("#YjButton").click(function(){
		var $text = $("#Ltext").val();
		var $th = $(this);
		if($text == "" || $text == null){
			alert("请填写完整！");
		}else{
			$th.css("display","none").parent().find(".ajaxAnimation").css("display","inline-block");
			$.ajax({
				type: "post",
                url:"tlwcg.do?meth=addlwyj",
                data:$('#zdForm').serialize(),//form序列化
                async: false,
                error: function(request) {
                    alert("Connection error");
                    location.reload();
                },
                success: function(data) {
                   if(data.status == 200){
                	   //alert(data.msg);
                	   location.reload();
                   }else{
                	   alert(data.msg);
                	   $th.css("display","inline-block").parent().find(".ajaxAnimation").css("display","none");
                   }
                }
			});
		}
	});
});