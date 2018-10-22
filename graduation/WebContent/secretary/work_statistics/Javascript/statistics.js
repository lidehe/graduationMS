/**
 * 课题分布
 * @param yuanxiId
 */
function ktDistri(yuanxiId){
	$("#result").empty();
	$.ajax({
		url : "../../Statistics.do",
		type : 'POST',
		data : {
			"method":"getKtDistribute",
			"yuanxiId":yuanxiId,
		},
		success : function(data) {
				var list = eval('(' + data + ')');
				var i=0;
				$.each(list,function(index,obj) {
						$tr=$("<tr></tr>")
						$td=$("<td>"+obj.yuanxiName+"</td><td>"+obj.ktName+"</td><td>"+obj.stuCount+"</td>");
						$tr.append($td);
						$("#result").append($tr);
				});
		}
	});
}
/**
 * 毕设进度
 * @param yuanxiId
 */
function schedule(yuanxiId){
	$("#result").empty();
	$.ajax({
		url : "../../Statistics.do",
		type : 'POST',
		data : {
			"method":"getSchedule",
			"yuanxiId":yuanxiId,
		},
		success : function(data) {
				var list = eval('(' + data + ')');
				var i=0;
				$.each(list,function(index,obj) {
					    var percent=parseFloat(obj.percent)*100;
						$tr=$("<tr></tr>")
						$td=$("<td>"+obj.yuanxiName+"</td><td>"+obj.jinDu+"</td><td>"+obj.stuCount+"</td><td>"+percent+"%"+"</td>");
						$tr.append($td);
						$("#result").append($tr);
				});
		}
	});
}
/**
 * 毕设总分
 * @param yuanxiId
 */
function score(yuanxiId){
		$("#result").empty();
		$.ajax({
			url : "../../Statistics.do",
			type : 'POST',
			data : {
				"method":"getBSZF",
				"yuanxiId":yuanxiId,
			},
			success : function(data) {
					var list = eval('(' + data + ')');
					var i=0;
					$.each(list,function(index,obj) {
							$tr=$("<tr></tr>")
							$td=$("<td>"+obj.yuanxiName+"</td><td>"+obj.dengJi+"</td><td>"+obj.stuCount+"</td>");
							$tr.append($td);
							$("#result").append($tr);
					});
			}
		});
}
/**
 * 抽检结果
 * @param yuanxiId
 */
function paperCheck(yuanxiId){
	$("#result").empty();
	$.ajax({
		url : "../../Statistics.do",
		type : 'POST',
		data : {
			"method":"getCheckResult",
			"yuanxiId":yuanxiId,
		},
		success : function(data) {
				var list = eval('(' + data + ')');
				var i=0;
				$.each(list,function(index,obj) {
						$tr=$("<tr></tr>")
						$td=$("<td>"+obj.yuanxiName+"</td><td>"+obj.dengJi+"</td><td>"+obj.stuCount+"</td>");
						$tr.append($td);
						$("#result").append($tr);
				});
		}
	});
}
/**
 * 省优秀论文
 * @param yuanxiId
 */
function appraising(yuanxiId){
	$("#result").empty();
	$.ajax({
		url : "../../Statistics.do",
		type : 'POST',
		data : {
			"method":"getShengYXLW",
			"yuanxiId":yuanxiId,
		},
		success : function(data) {
				var list = eval('(' + data + ')');
				var i=0;
				$.each(list,function(index,obj) {
						$tr=$("<tr></tr>")
						$td=$("<td>"+obj.stuNumber+"</td><td>"+obj.stuName+"</td><td>"+obj.yuanxiName+"</td><td>"+obj.zhuanyeName+"</td><td>"+obj.stuClass+"</td><td>"+obj.lwName+"</td>");
						$tr.append($td);
						$("#result").append($tr);
				});
		}
	});
}
/**
 * 答辩状态
 * @param yuanxiId
 */
function answer(yuanxiId){
	$("#result").empty();
	$.ajax({
		url : "../../Statistics.do",
		type : 'POST',
		data : {
			"method":"getAnswerState",
			"yuanxiId":yuanxiId,
		},
		success : function(data) {
				var list = eval('(' + data + ')');
				var i=0;
				$.each(list,function(index,obj) {
						$tr=$("<tr></tr>")
						$td=$("<td>"+obj.yuanxiName+"</td><td>"+obj.answerState+"</td><td>"+obj.stuCount+"</td>");
						$tr.append($td);
						$("#result").append($tr);
				});
		}
	});
}




$(function() {
	$(document).ready(
			function() {
				//从session中的来的教师信息中的院系Id
				  var yuanxiId=$("#thisYuanxiId").val();
				//根据选中的院系的ID,查询教师信息，院系选择发生变化，就会自动加载该院系的老师******************************************************************************************
					//如果院系ID为0，表示选择的是”选择“选项，则不需要查询
					var pageNow=$("#pageSwitch").val();
					switch(pageNow){
					case "ktDistri.jsp":
						ktDistri(yuanxiId);
						break;
					case "schedule.jsp":
						schedule(yuanxiId);
						break;
					case "score.jsp":
						score(yuanxiId);
						break;
					case "paperCheck.jsp":
						paperCheck(yuanxiId);
						break;
					case "appraising.jsp":
						appraising(yuanxiId);
						break;
					case "answer.jsp":
						answer(yuanxiId);
						break;
						
					}
				
				

	});
});
