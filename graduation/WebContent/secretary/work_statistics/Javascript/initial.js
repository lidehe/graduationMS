//初始化页面顶部按钮
function initialTabClick(th_is){
	
	var $span=$(th_is);
	var choosedCard=$span.text();
	switch(choosedCard){
	case "选题分布":
		window.location.href="statistics.jsp";
		break;
	case "毕设进度":
		window.location.href="schedule.jsp";
		break;
	case "答辩状态":
		window.location.href="answer.jsp";
		break;
	case "毕设成绩":
		window.location.href="score.jsp";
		break;
	case "抽检结果":
		window.location.href="paperCheck.jsp";
		break;
	case "省优秀论文":
		window.location.href="appraising.jsp";
		break;
	}
}
