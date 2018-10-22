/**
 * 
 */
$(function(){
	$("#shenheB").click(function(){
		var $th = $(this);
		$th.css("display","none").parent().find(".createGajax").css("display","inline-block");
		var id = $th.parent().find(".hiddenId").val();
		$.ajax({
			url:"tktbg.do?meth=sh",
			type:"post",
			data:{
				id:id
			},
			error: function(request) {
                alert("Connection error");
                location.reload();
            },
            dataType:"json",
			success:function(data){
				if(data.status == 200){
					//alert(data.msg);
					$th.parents("td").html("").text("已通过");
				}else{
					alert(data.msg);
					$th.css("display","inline-block").parent().find(".createGajax").css("display","none");
				}
			}
		});
	});
})