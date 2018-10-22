//初始化页面顶部按钮
function initialTabClick(th_is){
	
	var $span=$(th_is);
	var choosedCard=$span.text();
	switch(choosedCard){
	case "毕设最终成绩":
		window.location.href="score.jsp";
		break;
	}
}
