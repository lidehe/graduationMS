
function countdown(time) {
	$("#countdownPanel").show();
	setInterval(function() {
		time-=1;
		if(time>=0){
			$("#countDown").text(time);
		}else if(time<0){
			$("#countdownPanel").hide();
		}
	}, 1000)
}

$(function() {
	$(document).ready(function() {
        $("#countdownPanel").hide();
		// 点击新一届毕设开启
		$("#confirm").click(function() {
			var url = "../../NewYear.do";
			var year=$(".jieShu").val();
			var exp=/^\d{4}$/;
			if(exp.test(year)){
				
				//启动倒计时
				countdown(15);
				$.ajax({
					url : url,
					type : 'POST',
					data : {
						"method" : "startNewYear",
						"year" : year,
					},
					success : function(data) {
						if (data == "00"){
							countdown(0);
							 smartaAlert("初始化失败，请尝试联系技术人员！")
						}else if (data == "01"){
							 countdown(0);
							 smartaAlert("新的年份必须大于当前届的年份！")
						}else if(data=="11"){
							window.location.href="../../login.do?method=logout";
						}
					}
				});
			}else{
				smartaAlert("请输入合法有效的年份");
			}
		});
	});

});
