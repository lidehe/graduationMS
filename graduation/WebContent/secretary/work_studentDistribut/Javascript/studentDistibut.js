
//初始化页面顶部按钮
function initialTabClick(th_is){
	var $span=$(th_is);
	var choosedCard=$span.text();
	switch(choosedCard){
	case "手动匹配":
		window.location.href="studentDistribut.jsp";
		break;
	case "快速匹配":
		window.location.href="importDistribut.jsp";
		break;
	case "匹配结果":
		window.location.href="distributResult.jsp";
		break;
	}
}



//********************************************************************************************************************************************************************
//角色选择
function roleChoose(th_is){
	var $role=$(th_is);
	var role;
	role=$("#role option:selected").text();
	//alert(role);
	if(role=="系统管理员"||role=="教学秘书"){
		$(".roleSearchByJobNumber").show();
		$(".roleSearchByYuanxi").hide();
	}else if(role!="系统管理员"&&role!="教学秘书"){
		$(".roleSearchByJobNumber").hide();
		$(".roleSearchByYuanxi").show();
	}
}



//添加到备选栏按钮，把帅选出来的教师添加到右侧备选栏**********************************************************************************************************************
function roleBind(th_is){
	var $this=$(th_is);
	//alert($this.parents("tr").children("td").eq(1).text());
	var number=$this.parents("tr").children("td").eq(0).text();
	var name=$this.parents("tr").children("td").eq(1).text();
	
	//判断是否已经存在右侧了，如果存在，就不能添加到右侧，并提示
	var trList = $("#tb_chosedList").children("tr")
	var isExist=0;
    for (var i=0;i<trList.length;i++) {
      var chosedNum = trList.eq(i).children("td").eq(0).text();
      if(number==chosedNum){
    	  alert("已经添加到右侧列表了，不要重复添加");
    	  isExist=1;
    	  break;
      }
    }
	if(isExist==0){
		$this.css("color","red");
		$tr=$("<tr class='status_open'><td>"+number+"</td><td>"+name+"</td><td><button class='simple_buttons' onclick='roleUnbind(this)'>取消</button></td></tr>");
		$("#tb_chosedList").append($tr);
	}
}

//全选，把一页学生全部加到右侧
function chooseAllStudent(){
	var trList=$("#tb_unBindedStudents").children("tr");
	for (var i=0;i<trList.length;i++) {
	    trList.eq(i).children("td").eq(3).children("button").click();
	   }
}

//从右侧备选栏中移除**********************************************************************************************************************
function roleUnbind(th_is){
	var $this=$(th_is);
	$this.parents("tr").remove();
	var xuehao =$this.parents("tr").children("td").eq(0).text();
	var trList=$("#tb_unBindedStudents").children("tr");
	for (var i=0;i<trList.length;i++) {
		if(xuehao==trList.eq(i).children("td").eq(0).text()){
			trList.eq(i).children("td").eq(3).children("button").css("color","#858585");
			break;
		}
	   }
}
//从右侧全部取消
function removeAllStudent(){
	var trList=$("#tb_chosedList").children("tr");
	for (var i=0;i<trList.length;i++) {
	    trList.eq(i).children("td").eq(2).children("button").click();
	   }
}



//导入文件进行匹配页面-----上传文件
function uploadExcelFile(){
	// 上传文件文件
	
	var files = $("#upload").val();
	var point=files.lastIndexOf(".");
	var suffex=files.substring(point+1,files.length)
	if (suffex=="xlsx"||suffex=="xls") {
		var formData = new FormData();
		formData.append("method", "importDistributeFile");
		formData.append("docFile", $("#upload")[0].files[0]);
		var url="../../StudentDistibute.do";
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
			success : function(data) {
				if(data=="00"){
					alert("文件上传失败");
				}else if(data.length>5){
					window.location.href="../../FileCache/"+data;
				}else{
					alert("导入成功");
				}
			}
		});
	}else{
		alert("请上传.xls或者.xlsx格式的excel文件!");
	}
}

//匹配结果页面----点击查看，显示该教师已经匹配的学生*************************************************************
function findBindedStudent(th_is){
	$("#teacherGonghao").val("");
	$("#tb_BindedStudents").empty();
	var $this=$(th_is);
	//教师工号
	var number=$this.parents("tr").children("td").eq(0).text();
	var teacherName=$this.parents("tr").children("td").eq(1).text();
	$.ajax({
		url : "../../StudentDistibute.do",
		type : 'POST',
		data : {
			"method":"findBindedStudent",
			"number":number,
		},
		success : function(data) {
			if(data!=null&data!=""){
				$("#teacherName").text(teacherName);
				$("#teacherGonghao").val(number);
				var list = eval('(' + data + ')');
				var i=0;
				$.each(list,function(index,obj) {
						$tr=$("<tr class='status_open'></tr>")
						$td=$("<td>"+obj.xuehao+"</td><td>"+obj.name+"</td><td>"+obj.banji+"</td><td><button class='simple_buttons cancelTeacher' onclick='unbindedStudent(this)'>解除绑定</button></td>");
						$tr.append($td);
						$("#tb_BindedStudents").append($tr);
				});
			}else{
				//showMsg("该教师还未绑定学生","");
				alert("该教师还未绑定学生")
			}
		}
	});
}
//匹配结果页面，解除学生与指导教师的绑定
function unbindedStudent(th_is){
	var $this=$(th_is);
	//学生学号
	var number=$this.parents("tr").children("td").eq(0).text();
	//该学生信息所在行
	var $tr=$this.parents("tr");
	$.ajax({
		url : "../../StudentDistibute.do",
		type : 'POST',
		data : {
			"method":"unBindStudentByNumber",
			"number":number,
		},
		success : function(data) {
			//if(data=="11"){
				alert("成功解除绑定");
				$tr.remove();
			//}else{
			//	alert("删除失败");
			//}
		}
	});
}

//加载教师信息--绑定页面
function loadTeacher(yuanxiId,option,nowPage){
	//如果是按工号查询，就不需要分页按钮
	$(".pageBTNS").show();
	
	//如果院系ID为0，表示选择的是”选择“选项，则不需要查询
	if(yuanxiId==0){
		$(".pageBTNS").hide();
	}else{
		$.ajax({
			url : "../../StudentDistibute.do",
			type : 'POST',
			data : {
				"method":"pageFindTearcherByYuanxiId",
				"yuanxiId":yuanxiId,
				"option":option,
				"nowPage":nowPage,
			},
			success : function(data) {
				 if(data=="00"){
					  alert("未查询到任何信息");
					  $(".pageBTNS").hide();
				  }else if(data=="lastPage"){
					alert("已经最后一页");
				  }else{
					$("#tb_result").empty();
					var list = eval('(' + data + ')');
					var i=0;
					$.each(list,function(index,obj) {
						if(index==0){
							$("#nowPage").text(obj.nowPage);
							$("#totalPage").text(obj.totalPage); 
						}else{
							$tr=$("<tr class='status_open'></tr>")
							$td=$("<td>"+obj.gonghao+"</td><td>"+obj.name+"</td><td>"+obj.studentCount+"</td><td><input type='radio'name='bindTeacher'/></td></td>");
							$tr.append($td);
							$("#tb_result").append($tr);
						}
					});
				}
			}
		});
	}
}

//加载教师信息--绑定结果页面
function loadTeacher222(yuanxiId,option,nowPage){
	//如果是按工号查询，就不需要分页按钮
	$(".pageBTNS").show();
	
	//如果院系ID为0，表示选择的是”选择“选项，则不需要查询
	if(yuanxiId==0){
		$(".pageBTNS").hide();
	}else{
		$.ajax({
			url : "../../StudentDistibute.do",
			type : 'POST',
			data : {
				"method":"pageFindTearcherByYuanxiId",
				"yuanxiId":yuanxiId,
				"option":option,
				"nowPage":nowPage,
			},
			success : function(data) {
				 if(data=="00"){
					  alert("未查询到任何信息");
					  $(".pageBTNS").hide();
				  }else if(data=="lastPage"){
					alert("已经最后一页");
				  }else{
					$("#tb_result").empty();
					var list = eval('(' + data + ')');
					var i=0;
					$.each(list,function(index,obj) {
						if(index==0){
							$("#nowPage").text(obj.nowPage);
							$("#totalPage").text(obj.totalPage); 
						}else{
							$tr=$("<tr class='status_open'></tr>")
							$td=$("<td>"+obj.gonghao+"</td><td>"+obj.name+"</td><td><button onclick='findBindedStudent(this)' class='simple_buttons cancelTeacher'>查看</button></td>");
							$tr.append($td);
							$("#tb_result").append($tr);
						}
					});
				}
			}
		});
	}
}

//加载学生信息
function loadStudent(option,nowPage){
	
	//如果是按工号查询，就不需要分页按钮
	zhuanyeId=$("#zhuanye").find("option:selected").val();
	//如果院系ID为0，表示选择的是”选择“选项，则不需要查询
	if(zhuanyeId==0){
		$("#tb_unBindedStudents").empty();
		$("#pageBTNS1").hide();
	}else{
		$("#pageBTNS1").show();
		$.ajax({
			url : "../../StudentDistibute.do",
			type : 'POST',
			data : {
				"method":"pageFindStudentByZhuanyeId",
				"zhuanyeId":zhuanyeId,
				"banji":$("#banji").val(),
				"option":option,
				"nowPage":nowPage,
			},
			success : function(data) {
				if(data=="00"){
					  alert("未查询到任何信息");
					  $("#tb_unBindedStudents").empty();
					  $("#pageBTNS1").hide();
				  }else if(data=="lastPage"){
					alert("已经最后一页");
				  }else{
					$("#tb_unBindedStudents").empty();
					var list = eval('(' + data + ')');
					var i=0;
					$.each(list,function(index,obj) {
						if(index==0){
							$(".nowPage").text(obj.nowPage);
							$(".totalPage").text(obj.totalPage);
						}else{
							$tr=$("<tr class='status_open'></tr>")
							$td=$("<td>"+obj.xuehao+"</td><td>"+obj.name+"</td><td>"+obj.banji+"</td><td><button class='simple_buttons'  onclick='roleBind(this)'>添加</button></td>");
							$tr.append($td);
							$("#tb_unBindedStudents").append($tr);
						}
					});
				}
			}
		});
	}
}


/**
 * 专业变化加载班级
 */
function loadBanji(zhuanyeId){
	$("#banji").empty();
	op = $("<option value='0'>请选择</option>");
	$("#banji").append(op);
	$.ajax({
		url : "../../Yuanxi.do",
		type : 'POST',
		data : {
			"method" : "findClassByZhuanyeId",
			"zhuanyeId":zhuanyeId,
			},
		success : function(data) {
			if(data!="00"){
				var list = eval('(' + data + ')');
				$.each(list, function(index, obj) {
					$op = $("<option value='" + obj.name + "'>"
							+ obj.name + "</option>");
					$("#banji").append($op);
				});				}
			}
	});
}
$(function() {
	$(document).ready(function() {
		//从session中的来的教师信息中的院系Id
		  var yuanxiId=$("#thisYuanxiId").val();

		  
//如果是按工号查询，就不需要分页按钮**********************************************************************************************************************
		$(".pageBTNS").hide();
		$(".pageBTNS1").hide();
		$(".searchByNumber").focus(function(){
			$(".pageBTNS").hide();
			$("#tb_result").empty();
		});
		$(".searchStudentByNumber").focus(function(){
			$("#pageBTNS1").hide();
			$("#tb_unBindedStudents").empty();
		});
		
		$(".searchTeacherByNumber").focus(function(){
			$(".pageBTNS").hide();
			$("#tb_result").empty();
		});

		
		
//查找本院系所有指导教师，院系根据登陆者所属院系来确定，从session中获取******************************************************************************************
		$("#allThisYx").click(function(){
			loadTeacher(yuanxiId,"上一页",1);
		});
		
		
		
//根据院系分页查询教师**********************************************************************************************************************
	  $(".pageOption").click(function(){
		  var option=$(this).text();
		  var nowPage=$("#nowPage").text();
		  loadTeacher(yuanxiId,option,nowPage);
	  });

//根据工号查询教师**********************************************************************************************************************
	    $("#searchByNumber").click(function(){
	    	//每次查询前，都清除列表
	    	$("#tb_result").empty();
	    	//工号
	    	var number=$(".searchByNumber").val();
	    	//alert($(".searchByNumber").val());
	    	if(number==""||number==null){
	    		alert("请先输入工号");
	    	}else{
	    		$.ajax({
					  url:"../../StudentDistibute.do",
					  type:"post",
					  data:{
						  "method":"searchByNumber",
						  "number":number,
					  },
					  success:function(resData){
						if(resData!=null&resData!=""){
							var data=eval('(' + resData + ')');
							$.each(data,function(index,obj){
								$tr=$("<tr class='status_open'></tr>")
								$td=$("</td><td>"+obj.gonghao+"</td><td>"+obj.name+"</td><td>"+obj.stuCount+"</td><td><input type='radio'name='bindTeacher'/></td>");
								$tr.append($td);
								$("#tb_result").append($tr);
							})
						}else{
							alert("未查询到任何信息")
						}
							
					  }
				  });
	    	}
	    	 
	    });
//请求专业信息***********************************************
		$.ajax({
			url : "../../ZhuanyeWeb.do",
			type : 'POST',
			data : {
				"method":"findByYuanxiId",
				"yuanxiId":yuanxiId,//请求源
			},
			success : function(data) {
				var list = eval('(' + data + ')');
				$.each(list,function(index,obj) {
						$op=$("<option value='"+obj.id+"'>"+obj.name+"</option>")
						//角色分配和角色改变两个页面都需要初始化角色信息
						$("#zhuanye").append($op);
				});
			},
			error : function(responseStr) {
				console.log("error");
			}
		});
		
		
	    $("#pageBTNS").hide();
	    $("#pageBTNS1").hide();
//按专业查询未分配学生信息,同时加载班级信息***************************************************************************************
	    var  zhuanyeId;
		$("#zhuanye").change(function(){
			//不含班级信息的结果
			loadStudent("上一页",1);
			//加载班级信息
			loadBanji($("#zhuanye").val());
		});
//班级变化时，重置查询结果
		$("#banji").change(function(){
			//含班级信息的结果
			loadStudent("上一页",1);
		});
		
//根据专业分页查询未分配学生信息**********************************************************
		 $(".pageOptionSTU").click(function(){
				  var option=$(this).text();
				  var nowPage=$(".nowPage").text();
				  loadStudent(option,nowPage);
			  });
	    
//按学号查询未分配的学生 ，***************************************************************************************
	    $("#searchStudentByNumber").click(function(){
	    	//每次查询前，都清除列表
			$("#tb_unBindedStudents").empty();
			//如果是按工号查询，就不需要分页按钮
			$("#pageBTNS1").hide();
			var number=$(".searchStudentByNumber").val();
			$("#tb_unBindedStudents").empty();
			$.ajax({
					url : "../../StudentDistibute.do",
					type : 'POST',
					data : {
						"method":"findUnbindedStudentBynumber",
						"number":number,
					},
					success : function(data) {
						//alert(data);
						if(data=="00"){
							alert("学号输入有误，未找到该学号的学生！");
						}else if(data=="11"){
							alert("该学生已经分配了指导教师！");
						}else{
							var list = eval('(' + data + ')');
							var i=0;
							$.each(list,function(index,obj) {
								$tr=$("<tr class='status_open'></tr>")
								$td=$("<td>"+obj.xuehao+"</td><td>"+obj.name+"</td><td>"+obj.banji+"</td><td><button class='simple_buttons'  onclick='roleBind(this)'>添加</button></td>");
								$tr.append($td);
								$("#tb_unBindedStudents").append($tr);
							});
						}
					},
					error : function(responseStr) {
						console.log("error");
					}
				});
	    	
	    });
	  
	    
	    
	    
//确定添加角色**********************************************************************************************************************
		$("#confirmBind").click(function(){
			//遍历选中的学生，提取学号，并且汇总成格式字符串，等待提交
			var trList = $("#tb_chosedList").children("tr");
			//教师工号
			var number=$("input[name='bindTeacher']:checked").parents("tr").children("td").eq(0).text();
			
			if(number==""){
				alert("请选择指导教师!")
			}else if(trList.length<=0){
				alert("请先添加待分配的学生!")
			}else{
			    var numberStr="";
			    for (var i=0;i<trList.length;i++) {
			      var chosedNum = trList.eq(i).children("td").eq(0).text();
			    	  numberStr+=chosedNum+":";
			      }
				//把取到的教师工号和角色ID传递到后台
				$.ajax({
					url : "../../StudentDistibute.do",
					type : 'POST',
					data : {
						"method":"setBind",
						"numberStr":numberStr,
						"number":number,
					},
					success : function(data) {
						$("#zhuanye").val(0);
						$("#tb_unBindedStudents").empty();
						$("#tb_chosedList").empty();
						$(".pageBTNS1").hide();
						alert(data);
						loadTeacher(yuanxiId,"上一页",1);
					}
				});
		    }
		});
		
//匹配结果页面%%%%%%%%%%%%%%%%%%%%%   匹配结果页面    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%	匹配结果页面  %%%%%%%%%%%%%%%%%%%   匹配结果页面   %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		
		
//根据角色变化查询教师，从而操作该角色下的教职工***********************************************************************************************
		$("#allThisYxTeacher").click(function(){
			loadTeacher222(yuanxiId,"上一页",1);
		});
		//根据院系分页查询教师**********************************************************************************************************************
		  $(".pageOptionResult").click(function(){
			  var option=$(this).text();
			  var nowPage=$("#nowPage").text();
			  loadTeacher222(yuanxiId,option,nowPage);
		  });

	//根据工号查询教师**********************************************************************************************************************
		    $("#searchTeacherByNumber").click(function(){
		    	//每次查询前，都清除列表
		    	$("#tb_result").empty();
		    	//工号
		    	var number=$(".searchTeacherByNumber").val();
		    	//alert($(".searchByNumber").val());
		    	if(number==""||number==null){
		    		alert("请先输入工号");
		    	}else{
		    		$.ajax({
						  url:"../../StudentDistibute.do",
						  type:"post",
						  data:{
							  "method":"searchByNumber",
							  "number":number,
						  },
						  success:function(resData){
							  if(resData!=null&resData!=""){
									var data=eval('(' + resData + ')');
									$.each(data,function(index,obj){
										$tr=$("<tr class='status_open'></tr>")
										$td=$("</td><td>"+obj.gonghao+"</td><td>"+obj.name+"</td><td><button class='simple_buttons cancelTeacher' onclick='findBindedStudent(this)'>查看</button></td>");
										$tr.append($td);
										$("#tb_result").append($tr);
									})
								}else{
									alert("未查询到任何信息")
								}
						  }
					  });
		    	}
		    });
		    
		    
		  //导出到excel表格
			$("#exportToExcel").click(function(){
				var jsId=$("#searchByRole option:selected").val();
				var gonghao=$("#teacherGonghao").val();
				if(gonghao==""){
					alert("改教师尚未分配学生，无数据可以导出")
			   }else{
				   $.ajax({
				    	url:"../../StudentDistibute.do",
				    	type : 'POST',
					    data : {
						   "method":"exportTeacherAndHisStudent",
						   "number":gonghao,
					    },
					    success:function(data){//返回的是“00”表示导出失败，如果是 文件名就下载
						  if(data=="00"){
							alert("系统异常，导出数据错误");
						  }else{
							window.location.href="../../FileCache/"+data;
						 }
					 }
				    });
			   }
			});
			
	});
});
