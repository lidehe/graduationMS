//初始化页面顶部按钮
function initialTabClick(th_is){
	
	var $span=$(th_is);
	var choosedCard=$span.text();
	switch(choosedCard){
	case "发布公告":
		window.location.href="notice.jsp";
		break;
	case "公告管理":
		window.location.href="noticeList.jsp";
		break;
	}
}
