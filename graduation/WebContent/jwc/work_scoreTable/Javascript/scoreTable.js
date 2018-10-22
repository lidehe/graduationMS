
function deletePart(th_is){
	var pageNow = $("#pageSwitch").val();
	var witchTable;
	switch (pageNow) {
	case "pypsb.jsp":
		witchTable = "pypsb";
		break;
	case "dbpsb.jsp":
		witchTable = "dbpsb";
		break;
	case "zdlspsb.jsp":
		witchTable = "zdlspsb";
		break;
	case "sxcj.jsp":
		witchTable = "sxcj";
		break;

	}
	var name=$(th_is).parents("tr").children("td").eq(0).text();
		// 请求删除成绩表项
		$.ajax({
			url : "../../ScoreTable.do",
			type : 'POST',
			data : {
				"method" : "deletePart",
				"witchTable" : witchTable,
				"name":name,
			},
			success : function(data) {
				window.location.href=witchTable+".jsp";
			}
		});
	
}




$(function() {
	$(document).ready(
			function() {
				
				var type = $("#partType").find("option:selected").val();// 新增项的类型：文本或是分值
				$("#partType").change(function() {
					type = $("#partType").find("option:selected").val();
					if (type == "num") {
						$("#mfz").show();
					} else {
						$("#mfz").hide();
					}
				});

				var pageNow = $("#pageSwitch").val();
				var witchTable;
				switch (pageNow) {
				case "pypsb.jsp":
					witchTable = "pypsb";
					break;
				case "dbpsb.jsp":
					witchTable = "dbpsb";
					break;
				case "zdlspsb.jsp":
					witchTable = "zdlspsb";
					break;
				case "sxcj.jsp":
					witchTable = "sxcj";
					break;

				}

				// 根据页面请求成绩表信息
				$.ajax({
					url : "../../ScoreTable.do",
					type : 'POST',
					data : {
						"method" : "loadPart",
						"witchTable" : witchTable,
					},
					success : function(data) {
						if(data=="00"){
							//smartaAlert("暂无数据，请添加后尝试");
						}else{
							var list = eval('(' + data + ')');
							$.each(list, function(index, obj) {
								$tr = $("<tr></tr>")
								$td=$("<td>"+(index+1)+"</td><td>"+obj.name+"</td><td>"+obj.type+"</td><td>"+obj.mfz+"</td><td><button class='simple_buttons' onclick='deletePart(this)'>删除</button> </td>")
								$tr.append($td);
								$("#tb_result").append($tr);
							});
						}
					}
				});

				// 确定添加角色**********************************************************************************************************************
				$("#addPart").click(function() {
					var checkResult=1;
					// 遍历选中的教职工，提取工号，并且汇总成格式字符串，等待提交
					// 把取到的教师工号和角色ID传递到后台
					var name = $("#partName").val();
					var mfz = $("#mfz").val();
					if (name == "") {
						smartaAlert("请输入名称");
						checkResult=0;
					} else if (type == "num") {// 如果类型是分值，就要判断是否是空的
						if (mfz == "") {
							smartaAlert("请输入满分值");
							checkResult=0;
						}
					}
					if(checkResult==1)
					$.ajax({
						url : "../../ScoreTable.do",
						type : 'POST',
						data : {
							"method" : "addPart",
							"witchTable":witchTable,
							"name" : name,
							"type" : type,
							"mfz":mfz,
						},
						success : function(data) {
							if(data=="00"){
								smartaAlert("该项目已存在，请不要重复添加");
							}else{
								window.location.href=witchTable+".jsp";
							}
						}
					});
				});

			});
});
