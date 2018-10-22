
//初始化页面顶部按钮
function initialTabClick(th_is){
	var $span=$(th_is);
	var choosedCard=$span.text();
	switch(choosedCard){
	case "人员角色设定":
		window.location.href="roleSet.jsp";
		//window.location.href="../../InitialPage.do?requestFrom=initial_yuanxi";
		break;
	case "人员角色查询/更改":
		window.location.href="roleChange.jsp";
		//window.location.href="../../InitialPage.do?requestFrom=initial_zhuanye";
		break;
	}
}

$(function() {
	$(document).ready(function() {
		
		//请求角色信息,用于初始化角色管理页面***********************************************************************************************************
		$.ajax({
			url : "../../Jsgx.do",
			type : 'POST',
			data : {
				"method":"findAllJs",
			},
			success : function(data) {
				var list = eval('(' + data + ')');
				$.each(list,function(index,obj) {
					$td=$("<tr><td>"+obj.id+"</td><td>"+obj.name+"</td></tr>")
					$("#roleList").append($td);
						});
			},
			error : function(responseStr) {
				console.log("error");
			}
		});
	});
});
