$(function() {
	$(document).ready(function() {
		//变换左边按钮背景色;
		$(".tab_nav").children("li").removeClass("active_tab");
		var idStr=$("#pageName").val();
		
		switch(idStr){
		case "group.jsp":
			$("#group").addClass("active_tab")
			break;
		case "paperCheck.jsp":
			$("#paperCheck").addClass("active_tab")
			break;
		case "roleSet.jsp":
			$("#roleSet").addClass("active_tab")
			break;
		case "recommend.jsp":
			$("#recommend").addClass("active_tab")
			break;
		case "notice.jsp":
			$("#notice").addClass("active_tab")
			break;
		case "report.jsp":
			$("#report").addClass("active_tab")
			break;
		case "appraising.jsp":
			$("#appraising").addClass("active_tab")
			break;
		case "studentDistrbut.jsp":
			$("#studentDistribut").addClass("active_tab")
			break;
		case "statistics.jsp":
			$("#statistics").addClass("active_tab")
			break;
		case "finalScore.jsp":
			$("#finalScore").addClass("active_tab")
			break;
		}
	});
});
