function smartaAlert(text) {
	$divOut = $("<div id='smartaAlert'  style='position: fixed;top: 150px;left: 0px;right: 0px; margin: auto;width:350px;height:250px;border-radius:15px;background-color:#E0FFFF;'></div>")
	$divContent=$("<div id='content' align='center' style='width:280px;margin-left:35px;margin-top:32px;background-color:white;font-size:18px;'>"+ text + "</div>");
	$divButton=$("<div id='button' align='center' style='position:absolute;width:100%;height:80px;bottom:10px;'></div>");
	$button=$("<button  onclick='hideSmartAlert(this)' style='background-color:#97FFFF;font-size:16px;'>确定</button>");
	$divOut.append($divContent);
	$divOut.append($divButton);
	$divButton.append($button);
	$("body").append($divOut);
	$(".contents_wrapper").css("opacity","0.3");
	$(".top_panel").css("opacity","0.3");
	$("footer").css("opacity","0.3");
}
function hideSmartAlert(th_is){
	$this=$(th_is);
	$divOut=$this.parents("div");
	$divOut.remove();
	$(".contents_wrapper").css("opacity","1");
	$(".top_panel").css("opacity","1");
	$("footer").css("opacity","1");
}
$(function() {
	$(document).ready(function() {
		// 变换左边按钮背景色;
		$(".tab_nav").children("li").removeClass("active_tab");
		var idStr = $("#pageName").val();

		switch (idStr) {
		case "initial.jsp":
			$("#initial").addClass("active_tab")
			break;
		case "import.jsp":
			$("#import").addClass("active_tab")
			break;
		case "roleSet.jsp":
			$("#roleSet").addClass("active_tab")
			break;
		case "notice.jsp":
			$("#notice").addClass("active_tab")
			break;
		case "ways.jsp":
			$("#ways").addClass("active_tab")
			break;
		case "paperCheck.jsp":
			$("#paperCheck").addClass("active_tab")
			break;
		case "appraising.jsp":
			$("#appraising").addClass("active_tab")
			break;
		case "report.jsp":
			$("#report").addClass("active_tab")
			break;
		case "statistics.jsp":
			$("#statistics").addClass("active_tab")
			break;
		case "history.jsp":
			$("#history").addClass("active_tab")
			break;
		case "scoreTable.jsp":
			$("#scoreTable").addClass("active_tab")
			break;
		case "teacherAndStudentInfor.jsp":
			$("#teacherAndStudentInfor").addClass("active_tab")
			break;
		}
	});
});
