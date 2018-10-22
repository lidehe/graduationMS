

//学生信息页面--把查询来的学生信息添加到页面
function appendStudentInfo(data){
	$("#studentList").empty();
	var list = eval('(' + data + ')');
	$.each(list, function(index, obj) {
		if(index==0){
			var nowPage=parseInt(obj.nowPage);
			$("#nowPage").text(nowPage);
			$("#totalPage").text(obj.totalPage);
		}else{
			var $tr = $("<tr ><td><input type='hidden' value='"+obj.stuId+"'/>"+ obj.stuNumber + "</td><td>"+ obj.stuName + "</td><td>"+ obj.yuanxiName + "</td><td>"+ obj.zhuanyeName + "</td><td>"+ obj.className + "</td><td><button class='simple_buttons' onclick='stuDetail(this)'>毕设信息</button></td></td></tr>");
			$("#studentList").append($tr);
		}
		
	})
}

//学生信息页面--查看学生毕设详情
function stuDetail(th_is){
	$("#ktName").text("");
	$("#ktbgName").text("");
	$("#lwName").text("");
	$("#teacherName").text("");
	$("#dbfs").text("");
	$("#sxfs").text("");
	$("#lwcj").text("");
	$("#zfcj").text("");
	$this=$(th_is);
	var stuId=$this.parents("tr").children("td").eq(0).children("input").eq(0).val();
	var year= $("#yearChoose").find("option:selected").val();
	//smartaAlert("该学生的ID是："+stuId);
	$("#detailPanel").show();
	$.ajax({
		url : "../../History.do",
		type : 'POST',
		data : {
			"year" :year,
			"method" : "graduationDetail",
			"studentId":stuId,
		},
		success : function(data) {
			if (data == "[]") {
				smartasmartaAlert("查询失败")
			} else {
				var list = eval('(' + data + ')');
				var $a;
				$("#ktName").text(list[0].ktName);
				$a=$("<a href='../../DocumentView.do?year="+year+"&documentLevel=student&documentType=ktbg&documentName="+list[0].ktbgName+"' >《"+list[0].ktbgName+"》</a>");
				$("#ktbgName").append($a);
				$a=$("<a href='../../DocumentView.do?year="+year+"&documentLevel=student&documentType=lw&documentName="+list[0].lwName+"' >《"+list[0].lwName+"》</a>");
				$("#lwName").append($a);
				$("#teacherName").text(list[0].teacherName);
				$("#dbfs").text(list[0].dbfs);
				$("#sxfs").text(list[0].sxfs);
				$("#lwcj").text(list[0].lwcj);
				$("#zfcj").text(list[0].zfcj);
			}
		}
	});
	
}

$(function() {
	$(document).ready(
			function() {
//学生信息页面》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》
				//毕设信息详情面板
				$("#detailPanel").hide();
				$("#closeDetailPanel").click(function(){
					$("#detailPanel").hide();
				});
				$(".pageBTNS").hide();
				var year=0;//全局变量，用于记录选中的年份
                var yuanxiId;//全局变量，用于记录选中的学院
				// 加载历史年份***********************************************************************************************************************************
				$.ajax({
					url : "../../History.do",
					type : 'POST',
					data : {
						"method" : "findHistoryYear",
					},
					success : function(data) {
						if (data == "00") {
							smartasmartaAlert("系统使用未满一年，尚未产生历史数据")
						} else {
							var list = eval('(' + data + ')');
							$.each(list, function(index, obj) {
								var $op = $("<option value=" + obj.year + ">"
										+ obj.year + "</option>");
								$("#yearChoose").append($op);
							})
						}
					}
				});

				//年份发生变化，记录选中的年份信息变化，记录下来，同时查询该年的院系信息******************************************************************************************
				$("#yearChoose").change(function() {
					$("#studentList").empty();
					$(".pageBTNS").hide();
					year = $("#yearChoose").find("option:selected").val();
					$.ajax({
						url : "../../History.do",
						type : 'POST',
						data : {
							"year" : year,
							"method" : "findAllYuanxi",
						},
						success : function(data) {
							$("#yuanxiChoose").empty();
							var $op1=$("<option value='0'>请选择</option>");
							$("#yuanxiChoose").append($op1);
							if (data == "00") {
								smartasmartaAlert("系统使用未满一年，尚未产生历史数据")
							} else {
								var list = eval('(' + data + ')');
								$.each(list, function(index, obj) {
									var $op = $("<option value=" + obj.yuanxiId + ">"
											+ obj.yuanxiName + "</option>");
									$("#yuanxiChoose").append($op);
								})
							}
						}
					});
				});
				
				//院系发生变化，执行查询******************************************************************************************
				$("#yuanxiChoose").change(function() {
					$(".pageBTNS").show();
					 yuanxiId = $("#yuanxiChoose").find("option:selected").val();
					// 如果院系ID为0，表示选择的是”选择“选项，则不需要查询
					if (year == 0) {//如果年份为0，就是没选年份，要提示选择年份
						smartasmartaAlert("请选择年份")
					} else if(yuanxiId==0){//如果院系Id为0，就提示要选择院系
						smartaAlert("请选择院系");
					}else{
						$.ajax({
							url : "../../History.do",
							type : 'POST',
							data : {
								"method" : "pageSearchStudentByYuanxiId",
								"year" : year,
								"nowPage":0,
								"yuanxiId":yuanxiId,
								"preOrNext":"initial",
							},
							success : function(data) {
								appendStudentInfo(data);
								//smartaAlert(data);
							}
						});
					}
				});
				
				//点击上一页、下一页执行查询
				$(".preOrNext").click(function(){
					if($(this).text()=="上一页"){
						var nowPage=parseInt($("#nowPage").text());
						if(nowPage==1){
							smartaAlert("已经是第一页");
						}else{
							$.ajax({
								url : "../../History.do",
								type : 'POST',
								data : {
									"method" : "pageSearchStudentByYuanxiId",
									"year" : year,
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
								url : "../../History.do",
								type : 'POST',
								data : {
									"method" : "pageSearchStudentByYuanxiId",
									"year" : year,
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
//优秀论文信息页面》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》				
		 $("#appraisingBTN").click(function() {
			 if(year!=0){
				 $("#countDistributeList").empty();
					$.ajax({
						url : "../../History.do",
						type : 'POST',
						data : {
							"year" : year,
							"method" : "findAppraisingCountInfo",
						},
						success : function(data) {
							if (data == "00") {
								smartaAlert("系统使用未满一年，尚未产生历史数据")
							} else {
								var list = eval('(' + data + ')');
								$.each(list, function(index, obj) {
									var $tr = $("<tr><td>"+ obj.yuanxiName + "</td><td>"+ obj.grsl + "</td><td>"+ obj.xzsl + "</td></tr>");
									$("#countDistributeList").append($tr);
								})
							}
						}
					});
					
					$("#lwList").empty();
					$.ajax({
						url : "../../History.do",
						type : 'POST',
						data : {
							"year" : year,
							"method" : "findAppraisingShengjiInfo",
						},
						success : function(data) {
							if (data == "00") {
								smartaAlert("系统使用未满一年，尚未产生历史数据")
							} else {
								var list = eval('(' + data + ')');
								$.each(list, function(index, obj) {
									var $tr = $("<tr><td>"+ obj.stuNumber + "</td><td>"+ obj.stuName + "</td><td>"+ obj.yuanxiName + "</td><td>"+ obj.zhuanyeName + "</td>" +
											"<td><a href='../../DocumentView.do?year="+year+"&documentLevel=student&documentType=lw&documentName="+obj.lwName+"'>"+ obj.lwName + "</a></td></tr>");
									$("#lwList").append($tr);
								})
							}
						}
					});
			 }else{
				 smartaAlert("请选择年份");
			 }
				
		});
//抽检信息查询页面》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》
		 $("#checkBTN").click(function(){
			 if(year!=0){
				 //抽检规则查询
					$.ajax({
						url : "../../History.do",
						type : 'POST',
						data : {
							"year" : year,
							"method" : "findCheckRule",
						},
						success : function(data) {
							if (data == "00") {
								smartaAlert("系统使用未满一年，尚未产生历史数据")
							} else {
								$("#checkRule").html(data);
							}
						}
					});
					//抽检结果查询
					$("#checkResult").empty();
					$.ajax({
						url : "../../History.do",
						type : 'POST',
						data : {
							"year" : year,
							"method" : "findCheckResult",
						},
						success : function(data) {
							if (data == "00") {
								smartaAlert("系统使用未满一年，尚未产生历史数据")
							} else {
								var list = eval('(' + data + ')');
								$.each(list, function(index, obj) {
									var $tr = $("<tr><td>"+ obj.yuanxiName + "</td><td>"+ obj.dj + "</td><td>"+ obj.total + "</td></tr>");
									$("#checkResult").append($tr);
								})
							}
						}
					});
			 }else{
				 smartaAlert("请选择年份");
			 }
			 
			 
		 });
				
				
				
});
});
