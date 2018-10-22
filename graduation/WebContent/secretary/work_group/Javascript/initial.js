
//初始化页面顶部按钮
function initialTabClick(th_is){
	var $span=$(th_is);
	var choosedCard=$span.text();
	switch(choosedCard){
	case "新建答辩组":
		window.location.href="addGroup.jsp";
		//window.location.href="../../InitialPage.do?requestFrom=initial_yuanxi";
		break;
	case "已有答辩组":
		window.location.href="groupList.jsp";
		//window.location.href="../../InitialPage.do?requestFrom=initial_zhuanye";
		break;
	}
}
