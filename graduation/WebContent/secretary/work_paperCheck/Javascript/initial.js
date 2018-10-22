//初始化页面顶部按钮
function initialTabClick(th_is){
	
	var $span=$(th_is);
	var choosedCard=$span.text();
	switch(choosedCard){
	case "论文筛选规则":
		window.location.href="checkRule.jsp";
		break;
	case "抽检权限设定":
		window.location.href="teacherRule.jsp";
		break;
	case "论文抽检结果":
		//window.location.href="checkResult.jsp";
		window.location.href="../../PaperCheck.do?method=paperCheckInitial";
		break;
	case "抽检结果统计":
		window.location.href="resultStatistics.jsp";
		//window.location.href="../../InitialPage.do?requestFrom=initial_yuanxi";
		break;
	}
}
