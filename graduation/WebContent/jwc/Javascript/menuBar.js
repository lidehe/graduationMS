//初始化页面顶部按钮
function initialTabClick(th_is) {
	var $span = $(th_is);
	var choosedCard = $span.text();
	switch (choosedCard) {
	// *************************优秀论文模块
	case "各院论文数量设定":
		window.location.href = "countSet.jsp";
		break;
	case "校级优秀论文":
		window.location.href = "paperList.jsp";
		break;
	case "省级优秀论文":
		window.location.href = "SPaperList.jsp";
		break;

	// *************************历史信息模块
	case "往届学生信息":
		window.location.href="history.jsp";
		break;
	case "往届抽检信息":
		window.location.href="check.jsp";
		break;
	case "往届评优信息":
		window.location.href="appraising.jsp";
		break;
	case "往届统计信息":
		window.location.href="statistics.jsp";
		break;
	// *************************导入信息模块
	case "信息模板下载":
		window.location.href="template.jsp";
		break;
	case "导入院系信息":
		window.location.href="../../InitialPage.do?requestFrom=initial_yuanxi";
		break;
	case "导入专业信息":
		window.location.href="../../InitialPage.do?requestFrom=initial_zhuanye";
		break;
	case "导入教师信息":
	    window.location.href="js.jsp";
		break;
	case "导入学生信息":
		window.location.href="xs.jsp";
		break;
		
	// *************************公告模块
	case "发布公告":
		window.location.href="notice.jsp";
		break;
	case "公告管理":
		window.location.href="noticeList.jsp";
		break;
		
	// *************************论文抽检模块
	case "论文筛选规则":
		window.location.href="checkRule.jsp";
		break;
	case "抽检权限设定":
		window.location.href="teacherRule.jsp";
		break;
	case "论文抽检结果":
		window.location.href="../../PaperCheck.do?method=paperCheckInitial";
		break;
	case "抽检结果统计":
		window.location.href="resultStatistics.jsp";
		break;
		
	// *************************工作总结模块
	case "中期小结":
		window.location.href="midReport.jsp";
		break;
	case "最终总结":
		window.location.href="finalReport.jsp";
		break;
		
	// *************************人员设置模块
	case "人员角色设定":
		window.location.href = "roleSet.jsp";
		break;
	case "人员角色查询/更改":
		window.location.href = "roleChange.jsp";
		break;

	// *************************分数表模块
	case "评阅评审表":
		window.location.href="pypsb.jsp";
		break;
	case "答辩评审表":
		window.location.href="dbpsb.jsp";
		break;
	case "指导老师评审表":
		window.location.href="zdlspsb.jsp";
		break;
	case "实习成绩表":
		window.location.href="sxcj.jsp";
		break;
		
	// *************************统计信息模块
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
	case "师生交流":
		window.location.href="interact.jsp";
		break;
		
	// *************************师生信息模块
	case "教职工信息":
		window.location.href="teacherInfor.jsp";
		break;
	case "学生信息":
		window.location.href="studentInfor.jsp";
		break;
		
	// *************************毕设方式模块
	case "新建毕设方式":
		window.location.href="ways.jsp";
		break;
	case "已有毕设方式":
		window.location.href="wayList.jsp";
		break;
		
	}
}
