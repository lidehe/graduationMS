//初始化页面顶部按钮
function initialTabClick(th_is) {
	var $span = $(th_is);
	var choosedCard = $span.text();
	switch (choosedCard) {
	case "中期小结":
		window.location.href = "midReport.jsp";
		break;
	case "最终总结":
		window.location.href = "finalReport.jsp";
		break;
	}
}

// 加载本院系的中期小结
function loadMidReport(yuanxiId) {
	$
			.ajax({
				url : "../../Report.do",
				type : 'POST',
				data : {
					"method" : "getMid",
					"yuanxiId" : yuanxiId,
				},
				success : function(data) {
					// alert(data);
					if (data == "00") {
						//alert("尚未上传中期小结")
					} else {
						$("#midReportList").empty();
						var str = new Array();
						str = data.split(":");
						$tr = $("<tr></tr>");
						$td = $("<td><input type='hidden' value='"
								+ str[0]
								+ "' />"
								+ str[1]
								+ "</td><td><a href='../../Report.do?method=showZqxj&yuanxiId="+yuanxiId+
								"'>预览</a></td><td><a href='../../Report.do?method=loadZqxj&yuanxiId="+yuanxiId+
								"'>下载</a></td><td><button class='simple_buttons' onclick='reportDelete(this)'>删除小结</button></td>")
						$tr.append($td);
						$("#midReportList").append($tr);
					}

				},
			});
}

// 一加载本院系的最终总结
function loadFinalReport(yuanxiId) {
	$
			.ajax({
				url : "../../Report.do",
				type : 'POST',
				data : {
					"method" : "getFinal",
					"yuanxiId" : yuanxiId,
				},
				success : function(data) {
					// alert(data);
					if (data == "00") {
						//alert("尚未上传最终小结")
					} else {
						$("#finalReportList").empty();
						var str = new Array();
						str = data.split(":");
						$tr = $("<tr></tr>");
						$td = $("<td><input type='hidden' value='"
								+ str[0]
								+ "' />"
								+ str[1]
								+ "</td><td><a href='../../Report.do?method=showZhzj&yuanxiId="+yuanxiId+
								"'>预览</a></td><td><a href='../../Report.do?method=loadZhzj&yuanxiId="+yuanxiId+
								"'>下载</a></td><td><button class='simple_buttons' onclick='reportDelete(this)'>删除小结</button></td>")
						$tr.append($td);
						$("#finalReportList").append($tr);
					}

				},
			});
}

// 删除中期小结、最终总结
function reportDelete(th_is) {
	var $this = $(th_is);
	var $tr = $this.parents("tr");
	var reportId = $tr.children("td").eq(0).children("input").eq(0).val();
	$.ajax({
		url : "../../Report.do",
		type : 'POST',
		data : {
			"method" : "deleteReportById",
			"reportId" : reportId,
		},
		success : function(resData) {
			if (resData == "00") {
				alert("删除失败");
			} else {
				$tr.remove();
			}
		}
	});
}
$(function() {
	$(document).ready(function() {
		// 从session中的来的教师信息中的院系Id
		var yuanxiId = $("#thisYuanxiId").val();

		var whichPage = $("#midOrfinal").val();
		if (whichPage == "mid") {
			// 加载本院系的中期小结
			loadMidReport(yuanxiId);
		} else {
			// 加载本院系的最终总结
			loadFinalReport(yuanxiId);
		}

		// 上传文件文件
		$("#subExcelFile").click(function() {
			var inforSource = $("#fileType").val();
			var gonghao = $("#loginTeacherNumber").val();
			var formData = new FormData();
			var files = $("#upload").val();
			var point=files.lastIndexOf(".");
			var suffex=files.substring(point+1,files.length)
			if (suffex=="pdf"||suffex=="PDF") {
				// alert(inforSource);
				// 往formData里添加的顺序，就是服务器上读取的顺序，
				formData.append("inforSource", inforSource);
				formData.append("yuanxiId", yuanxiId);
				formData.append("gonghao", gonghao);
				formData.append("docFile", $("#upload")[0].files[0]);
				var url = "../../Report.do";
				$.ajax({
					url : url,
					type : 'POST',
					data : formData,
					// 告诉jQuery不要去处理发送的数据
					processData : false,
					// 告诉jQuery不要去设置Content-Type请求头
					contentType : false,
					beforeSend : function() {
					},
					success : function(data) {
						// 上传成功要刷新一下
						if (data == "zqxj") {
							window.location.href = "midReport.jsp";
						} else if (data == "zhzj") {
							window.location.href = "finalReport.jsp";
						}
					}
				});
			}else{
				alert("请上传pdf格式的文件!");
			}
		});
	});

});
