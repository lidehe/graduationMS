/**
 * 
 */
$(function(){
	$(window).resize(function(){
		setNoticeDivCss1();//设置居中和遮罩层大小
	});
	
	$(".btnclosealert1").hover(function() {
		$(this).css("opacity", 1);
	}, function() {
		$(this).css("opacity", 0.2);
	}).click(function(){//关闭
		clearClose1();
	});
	
	$("#chPassword").click(function(){//出现
		$('.theme-popover-mask1').show();
		$('.jq_dvalert1').show().stop().animate( {"top" : 90 + $(document).scrollTop(),"opacity" : 1}, 350);
	});
	
	$("#doPassword").click(function(){//点击发布“指导老师”公告
		var $old = $("#oldPassword").val();
		var $new = $("#newPassword").val();
		if($old == "" || $old == null || $new == "" || $new == null){
			alert("请填写完整！");
			return;
		}else{
			if($old == $new){
				alert("两次密码不能一致！");
				return;
			}else{
				$.ajax({
					url:"login.do?method=change",
					type:"POST",
					data:{
						oldP:$old,
						newP:$new
					},
					dataType:"json",
					error:function(data){
						alert("connect error!");
						location.reload();
					},
					success:function(data){
						if(data.status == 200){
							clearClose1();
							showMsg(data.msg,"top");
							//window.open("home.do");
						}else{
							alert(data.msg);
						}
					}
				});
			}
		}
	});
	
	setNoticeDivCss1();//设置居中和遮罩层大小
	$('.jq_dvalert1').css("top",0 - $('.jq_dvalert1').height());//初始top
})

function setNoticeDivCss1(){
	var $width = $(document).width();
	if($width > 903)
		$('.jq_dvalert1').css("left",($width - 903)/ 2 + "px");
	else
		$('.jq_dvalert1').css("left","0");
	var $height = $(document).height();
	$(".theme-popover-mask1").css("height",$height);
}

function clearClose1(){
	$('.theme-popover-mask1').hide();
	$('.jq_dvalert1').stop().animate( {
		"top" : 0 - $('.jq_dvalert1').height()
	}, 150,function(){$('.jq_dvalert1').hide();});
	//再来个清空
	$("#oldPassword").val("");
	$("#newPassword").val("");
}