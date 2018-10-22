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
 * 毕设总分毕设总分页面
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

/**
 * 师生交流页面--加载学生信息
 */

function loadStudent(yuanxiId){
	//院系发生变化，执行查询******************************************************************************************
		$(".pageBTNS").show();
		$.ajax({
			url : "../../Statistics.do",
			type : 'POST',
			data : {
				"method" : "pageFindeStudent",
				"nowPage":0,
				"yuanxiId":yuanxiId,
				"preOrNext":"initial",
			},
			success : function(data) {
				appendStudentInfo(data);
			}
		});
}

//师生交流页面--添加信息到页面
function appendStudentInfo(data){
	$("#studentList").empty();
	var list = eval('(' + data + ')');
	$.each(list, function(index, obj) {
		if(index==0){
			var nowPage=parseInt(obj.nowPage);
			$("#nowPage").text(nowPage);
			$("#totalPage").text(obj.totalPage);
		}else{
			var $tr = $("<tr ><td>"+ obj.stuNumber + "</td><td>"+ obj.stuName + "</td><td>"+ obj.zhuanyeName + "</td><td>"+ obj.stuClass + "</td><td><button class='simple_buttons' onclick='interactDetail(this)'>交流详情</button></td></td></tr>");
			$("#studentList").append($tr);
		}
		
	})
}

/**
 * 师生交流页面--加载交流记录
 */
function interactDetail(th_is){
	$this=$(th_is);
	var stuNumber=$this.parents("tr").children("td").eq(0).text();
	$.ajax({
		url : "../../Statistics.do",
		type : 'POST',
		data : {
			"method" : "FindeInteractRecord",
			"stuNumber":stuNumber,
		},
		success : function(data) {
			$("#detailPanel").show();
			$("#records").empty();
			var list = eval('(' + data + ')');
			$.each(list, function(index, obj) {
				var $tr = $("<tr><td>"+ obj.writer + "</td><td>"+ obj.reader + "</td><td style='text-align:left'>"+ obj.content + "<br><span style='color:red;'>"+ obj.jlTime+"</span></td></tr>");
				$("#records").append($tr);
			})
		}
	});
	
	
}



$(function() {
	$(document).ready(
			function() {
				// smartaAlert("角色管理页面");
				// 请求院系信息
				$.ajax({
					url : "../../Yuanxi.do",
					type : 'POST',
					data : {
						"method" : "getAllYuanxi"
					},
					success : function(data) {
						// smartaAlert(data);
						var list = eval('(' + data + ')');
						$.each(list, function(index, obj) {
							$op = $("<option value='" + obj.id + "'>"
									+ obj.name + "</option>")
							$("#yuanxi").append($op);
						});
					}
				});
				
				//根据选中的院系的ID,查询教师信息，院系选择发生变化，就会自动加载该院系的老师******************************************************************************************
				var  yuanxiId;
				$("#yuanxi").change(function(){
					yuanxiId=$("#yuanxi").find("option:selected").val();
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
					case "interact.jsp":
						loadStudent(yuanxiId);
						break;
					}
				});
				
				
				
				
				
//***************************师生交流页面，点击上一页、下一页执行查询**********************************************
				//记录详情面板
				$("#detailPanel").hide();
				$("#closeDetailPanel").click(function(){
					$("#detailPanel").hide();
				});
				$(".pageBTNS").hide();
				//点击上一页、下一页执行查询
				$(".preOrNext").click(function(){
					if($(this).text()=="上一页"){
						var nowPage=parseInt($("#nowPage").text());
						if(nowPage==1){
							smartaAlert("已经是第一页");
						}else{
							$.ajax({
								url : "../../Statistics.do",
								type : 'POST',
								data : {
									"method" : "pageFindeStudent",
									"nowPage":nowPage,
									"yuanxiId":yuanxiId,
									"preOrNext":"pre",
								},
								success : function(data) {
									appendStudentInfo(data);
								}
							});
						}
						
					}else if($(this).text()=="下一页"){
						var nowPage=parseInt($("#nowPage").text());
						var totalPage=parseInt($("#totalPage").text());
						if(nowPage>=totalPage){
							smartaAlert("已经是最后一页");
						}else{
							$.ajax({
								url : "../../Statistics.do",
								type : 'POST',
								data : {
									"method" : "pageFindeStudent",
									"nowPage":nowPage,
									"yuanxiId":yuanxiId,
									"preOrNext":"next",
								},
								success : function(data) {
									appendStudentInfo(data);
								}
							});
						}
						
					}
				});
				
				

	});
});
