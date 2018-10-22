/**
 * 
 */
$(function(){
	$(".xzrsSelector").change(function(){
		var id = $(this).parents("tr").find(".hiddenId").val();
		var xzrs = $(this).val();
		if(xzrs > 0 && xzrs <= 10){
			$(".xzrsSelector").attr("disabled",true);
			$.ajax({
				url:"tkt.do?meth=setxzrs",
				type:"post",
				data:{
					id:id,
					xzrs:xzrs
				},
				error:function(request){
					alert("connect error!");
					location.reload();
				},
				success:function(data){
					$(".xzrsSelector").attr("disabled",false);
					if(data.status == 200){
						showMsg(data.msg,"top");
					}else{
						alert(data.msg);
					}
				}
			});
		}
	});
	
	
	$(".shenheB").click(function(){
		var $th = $(this);
		$th.css("display","none").parent().find(".createGajax").css("display","inline-block");
		var id = $th.parent().find(".hiddenId").val();
		$.ajax({
			url:"tkt.do?meth=sh",
			type:"post",
			data:{
				id:id
			},
			error: function(request) {
                alert("Connection error");
                location.reload();
            },
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
	
	
	$(".deleteB").click(function(){
		if(confirm("您确定删除此课题？")){
			var $th = $(this);
			$th.css("display","none").parent().find(".createGajax").css("display","inline-block");
			var id = $th.parent().find(".hiddenId").val();
			$.ajax({
				url:"tkt.do?meth=delete",
				type:"post",
				data:{
					id:id
				},
				error: function(request) {
                    alert("Connection error");
                    location.reload();
                },
				success:function(data){
					if(data.status == 200){
						//alert(data.msg);
						$th.parents("tr").remove();
					}else{
						alert(data.msg);
						$th.css("display","inline-block").parent().find(".createGajax").css("display","none");
					}
				}
			});
		}
	});
	
	$("#ktButton").click(function(){
		var $name = $("#kName").val();
		var $text = $("#kText").val();
		var $th = $(this);
		var $zy = $("#ktzy").val();
		var $xz = $("#xzrsForm").val();
		if(!($xz > 0 && $xz < 10)){
			alert("请填写完整！");
			return;
		}
		if($name == "" || $name == null || $text == "" || $text == null || $zy == "" || $zy == null){
			alert("请填写完整！");
			return;
		}else{
			$th.css("display","none").parent().find(".ajaxAnimation").css("display","inline-block");
			$.ajax({
				type: "post",
                url:"tkt.do?meth=add",
                data:$('#ktForm').serialize(),//form序列化
                async: false,
                error: function(request) {
                    alert("Connection error");
                    window.location.href = "tkt.do";
                },
                success: function(data) {
                   if(data.status == 200){
                	   //alert(data.msg);
                	   window.location.href = "tkt.do";
                   }else{
                	   $th.css("display","inline-block").parent().find(".ajaxAnimation").css("display","none");
                	   alert(data.msg);
                   }
                }
			});
		}
	});
	
});