/**
 * 
 */
$(function(){
	$("#loginb").click(function(){
		login();
	});
	$("#cancel").click(function(){
		$("#username").val("");
		$("#userpwd").val("");
	});
	$("#userpwd").keyup(function(event){
		if(event.keyCode == 13)
			login();
	});
});

function login(){
	var $account = $("#username").val();
	var $password = $("#userpwd").val();
	if($account == "" || $password == ""){
		alert("请填写账号和密码！");
	}else{
		$("#loginb").val("登录ing");
		$(".Inp").attr("disabled",true); 
		$.ajax({
			type:"post",
			url:"login.do",
			data:{
				account:$account,
				password:$password
			},
			dataType:"json",
			error: function(request) {
                alert("Connection error");
                location.reload();
            },
			success:function(data){
				if(data.status == 2){
					alert("密码有误，请重新输入！");
				}else if(data.status == 3){
					alert("用户不存在！");
				}else{
					if(data && data.url1)
						window.open(data.url1);
					if(data != null && data.url != null)
						window.location.href=data.url;
					else
						window.location.href="index.html";
				}
				$("#loginb").val("登录");
				$(".Inp").attr("disabled",false); 
			}
		});
	}
}