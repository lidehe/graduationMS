
$(function() {
	$(document).ready(function() {
		
		// 上传文件文件
		$("#subExcelFile").click(function() {
			
			var files = $("#upload").val();
			var point=files.lastIndexOf(".");
			var suffex=files.substring(point+1,files.length)
			if (suffex=="xlsx"||suffex=="xls") {
				var formData = new FormData();
				var inforSource = $(".initial_inforSource").val();
				//smartaAlert(inforSource);
				formData.append("docFile", $("#upload")[0].files[0]);
				formData.append("inforSource", inforSource);
				
				var url="../../ImportInfor.do";
				$.ajax({
					url : url,
					type : 'POST',
					data : formData,
					// 告诉jQuery不要去处理发送的数据
					processData : false,
					// 告诉jQuery不要去设置Content-Type请求头
					contentType : false,
					beforeSend : function() {
						console.log("正在进行，请稍候");
					},
					success : function(responseStr) {
						//smartaAlert(responseStr);
						if (responseStr==""||responseStr==null) {
							//smartaAlert("信息导入成功");
						} else if(responseStr=="11"){
							switch(inforSource){
							  case "院系信息":
							    window.location.href="../../InitialPage.do?requestFrom=initial_yuanxi";
								 break;
							  case "专业信息":
								window.location.href="../../InitialPage.do?requestFrom=initial_zhuanye";
								break;
							  case "教师信息":
								  smartaAlert("导入教职工信息成功，可以师生信息管理中查看导入的信息")
								 break;
							  case "学生信息":
								  smartaAlert("导入学生信息成功，可以师生信息管理中查看导入的信息")
								 break;
							}
							
						}else if(responseStr=="01"){
							smartaAlert("请先导入院系信息");
						}
						else if(responseStr=="00"){
							smartaAlert("请先导入专业信息");
						}else {
							smartaAlert("导入信息存在错误，已经导出到excel,请参考该文件修改原文件中的信息，重新导入");
							window.location.href="../../FileCache/"+responseStr;
						}
					}
				});
			}else{
				smartaAlert("请上传.xls或者.xlsx格式的excel文件!");
			}
		});
	});
	//下载模板
	$(".downloadTemplate").click(function(){
		var tempName=$(this).text();
		switch(tempName){
		case "学生信息模板":
			window.location.href="../../OfficeFileTemplate/学生信息模板.xlsx";
			break;
		case "院系信息模板":
			window.location.href="../../OfficeFileTemplate/院系信息模板.xlsx";
			break;
		case "教师信息模板":
			window.location.href="../../OfficeFileTemplate/教师信息模板.xlsx";
			break;
		case "专业信息模板":
			window.location.href="../../OfficeFileTemplate/专业信息模板.xlsx";
			break;
		}
	})
	
});
