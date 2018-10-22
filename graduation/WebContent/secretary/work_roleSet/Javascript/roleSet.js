
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
function chooseAllTeacher(){
	var trList=$("#tb_result").children("tr");
	for (var i=0;i<trList.length;i++) {
	    trList.eq(i).children("td").eq(2).children("button").click();
	   }
}

//从右侧备选栏中移除**********************************************************************************************************************
function roleUnbind(th_is){
	var $this=$(th_is);
	$this.parents("tr").remove();
	var gonghao =$this.parents("tr").children("td").eq(0).text();
	var trList=$("#tb_result").children("tr");
	for (var i=0;i<trList.length;i++) {
		if(gonghao==trList.eq(i).children("td").eq(0).text()){
			trList.eq(i).children("td").eq(2).children("button").css("color","#858585");
			break;
		}
	   }
}
//从右侧栏全部取消
function removeAllTeacher(){
	var trList=$("#tb_chosedList").children("tr");
	for (var i=0;i<trList.length;i++) {
	    trList.eq(i).children("td").eq(2).children("button").click();
	   }
}

//从角色筛选出来的教师列表中，删除某个教职工*************************************************************
function deleteFromRole(th_is){
	var $this=$(th_is);
	$this.parents("tr").remove();
	//工号
	var number=$this.parents("tr").children("td").eq(0).text();
	//角色ID
	var jsId=$("#searchByRole option:selected").val();
	$.ajax({
		url : "../../RoleManage.do",
		type : 'POST',
		data : {
			"method":"deleteTeacherFromRole",
			"number":number,
			"JsID":jsId,
		},
		success : function(data) {
			alert(data);
		}
	});
}


//加载教师
function loadTeacher(yuanxiId,JsID,option,nowPage){
	
	//如果院系ID为0，表示选择的是”选择“选项，则不需要查询
	if(yuanxiId==0||yuanxiId==""){
		$("#tb_result").empty();
		$(".pageBTNS").hide();
		alert("您是非教学人员，院系信息未空");
	}else{
		$.ajax({
			url : "../../RoleManage.do",
			type : 'POST',
			data : {
				"method":"pageFindTearcherByYuanxiId",
				"yuanxiId":yuanxiId,
				"JsID":JsID,
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
					//如果是按工号查询，就不需要分页按钮
						$(".pageBTNS").show();
					  var list = eval('(' + data + ')');
						var i=0;
						$.each(list,function(index,obj) {
							if(index==0){
								$("#nowPage").text(obj.nowPage);
								$("#totalPage").text(obj.totalPage);
							}else{
								$tr=$("<tr class='status_open'></tr>")
								$td=$("<td>"+obj.gonghao+"</td><td>"+obj.name+"</td><td><button class='simple_buttons'  onclick='roleBind(this)'>添加</button></td>");
								$tr.append($td);
								$("#tb_result").append($tr);
							}
						});
				  }
			}
		});
	}
}



$(function() {
	$(document).ready(function() {
		//从session中的来的教师信息中的院系Id
		  var yuanxiId=$("#thisYuanxiId").val();
		
//请求角色信息***********************************************************************************************************
		//请求源，因为教务处和教学秘书都会用到角色分配，但是两者所能管理的角色是不一样的，所以他们看的的角色有所不同
		var requireFrom="";
		requireFrom=$(".requireFrom").val();
		
		$.ajax({
			url : "../../Jsgx.do",
			type : 'POST',
			data : {
				"method":"findJsByRequireFrom",
				"requireFrom":requireFrom,//请求源
			},
			success : function(data) {
				var list = eval('(' + data + ')');
				$.each(list,function(index,obj) {
					if(obj.name=="专业负责人"||obj.name=="指导老师"||obj.name=="院级评优人员"){
						$op=$("<option value='"+obj.id+"'>"+obj.name+"</option>")
						//角色分配和角色改变两个页面都需要初始化角色信息
						$("#role").append($op);
						$("#searchByRole").append($op);
					}
				});
			},
			error : function(responseStr) {
				console.log("error");
			}
		});
		

//专业选择一开始隐藏，仅当分配的是专业负责人的时候，才显示他
				$("#zhuanyeChoose").hide();

		
//角色选择发生变化，就在开头的变量中记录下来选择的角色的ID，同时判断选中角色是否是专业负责人，如果是，则显示专业选择选择框，否则隐藏专业选择选择框**********************************************************************************************************************
		var  JsID="0";//定义变量，存储角色ID,默认为"0";
		var JsName="";
		$("#role").change(function(){
			//角色变化就清空(已选)择教师列表
			$("#tb_result").empty();
			$("#tb_chosedList").empty();
			$(".pageBTNS").hide();
			JsID=$("#role").find("option:selected").val();
			JsName=$("#role").find("option:selected").text();
			//角色后面添加角色描述信息；已选教师列表头部添加已选角色
			if(JsID==0){
				$("#chosedRule").text("");
				$("#ruleDiscription").text("");
			}else{
				$("#chosedRule").text(JsName);
//				switch(JsID){
//				case "1"://指导老师
//					$("#ruleDiscription").text("负责带领数名学生完成毕业设计工作");
//					break;
//				case "2"://专业负责人
//					$("#ruleDiscription").text("负责一个专业的毕业设计管理工作");
//					break;
//				case "10"://院级评优人员
//					$("#ruleDiscription").text("负责一个院系的毕业设计管理工作");
//					break;
//				}
			}
			
			op=$("<option value=0>请选择</option>")
			$("#zhuanye").empty();
			$("#zhuanye").append(op);
			if(JsName=="专业负责人"){
				
				//请求专业信息***********************************************
				$.ajax({
					url : "../../ZhuanyeWeb.do",
					type : 'POST',
					data : {
						"method":"findByYuanxiId",
						"yuanxiId":yuanxiId,
					},
					success : function(data) {
						var list = eval('(' + data + ')');
						$.each(list,function(index,obj) {
								$op=$("<option value='"+obj.id+"'>"+obj.name+"</option>")
								//角色分配和角色改变两个页面都需要初始化角色信息
								$("#zhuanye").append($op);
						});
					}
				});
				
				$("#zhuanyeChoose").show();
			}else{
				$("#zhuanyeChoose").hide();
			}
			
		});
		
		var zhuanyeId="";
		$("#zhuanye").change(function(){
			zhuanyeId=$("#zhuanye").find("option:selected").val();
		});

		
		
//如果是按工号查询，就不需要分页按钮**********************************************************************************************************************
		$(".pageBTNS").hide();
		$(".searchByNumber").focus(function(){
			$(".pageBTNS").hide();
			$("#tb_result").empty();
		});
		

		
		
//根据选中的院系的ID,查询教师信息，院系选择发生变化，就会自动加载该院系的老师******************************************************************************************
		$("#allThisYx").click(function(){
			//每次查询前，都清除列表
			$("#tb_result").empty();
			//如果是按工号查询，就不需要分页按钮
			$(".pageBTNS").show();
			loadTeacher(yuanxiId,JsID,"上一页",1);
		});
		
		
//根据院系分页查询教师**********************************************************************************************************************
	  $(".pageOption").click(function(){
		//每次查询前，都清除列表
		  var option=$(this).text();
		  var nowPage=$("#nowPage").text();
		  loadTeacher(yuanxiId,JsID,option,nowPage)
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
					  url:"../../RoleManage.do",
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
								$td=$("</td><td>"+obj.gonghao+"</td><td>"+obj.name+"</td><td><button style='color: #1C1C1C' onclick='roleBind(this)'>添加</button></td>");
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
	  
	    
	    
	    
//确定添加角色**********************************************************************************************************************
		$("#setRoles").click(function(){
			//遍历选中的教职工，提取工号，并且汇总成格式字符串，等待提交
			var trList = $("#tb_chosedList").children("tr")
			var zhuanyeId=$("#zhuanye").val();
			//alert("角色："+JsID+"zhuanye:"+zhuanyeId);
			if(JsID=="0"){
				alert("请选择角色!")
			}else if(trList.length<=0){
				alert("请先添加职工!")
			}else if(JsID==2&&zhuanyeId==0){
				alert("请选择专业");
			}else{
			    var numberStr="";
			    for (var i=0;i<trList.length;i++) {
			      var chosedNum = trList.eq(i).children("td").eq(0).text();
			    	  numberStr+=chosedNum+":";
			      }
				//把取到的教师工号和角色ID传递到后台
				$.ajax({
					url : "../../RoleManage.do",
					type : 'POST',
					data : {
						"method":"setRoles",
						"numberStr":numberStr,
						"zhuanyeId":zhuanyeId,
						"JsID":JsID,
					},
					success : function(data) {
						$("#role").val(0);
						$("#zhuanye").val(0);
						$("#tb_result").empty();
						$("#tb_chosedList").empty();
						$("#ruleDiscription").text("");
						$("#chosedRule").text("");
						$(".pageBTNS").hide();
						alert(data);
					}
				});
		    }
		});
		
//角色分配结果页面********##############################%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%&&&&&&&&&&&&&&&&&&&&&&&
		//一进来加载所有本院系角色分配结果
//		 if($(".active_initialHeadTabs").text()=="人员角色查询/更改"&&$("#searchByRole").val()==0){
//			  loadAllJsgx(yuanxiId);
//		  }
		
//根据角色变化查询教师，从而操作该角色下的教职工***********************************************************************************************
		$("#searchByRole").change(function(){
			//alert($("#searchByRole option:selected").text()+$("#searchByRole option:selected").val());
			//角色ID
			$("#tb_resultList").empty();
			var jsId=$("#searchByRole option:selected").val();
			
			var option=$(this).text();
			var nowPage=$("#nowPage").text();
			var allPage=$("#allPage").text();
			if(nowPage==0){
				option="initial";
			}
			$.ajax({
				url : "../../RoleManage.do",
				type : 'POST',
				data : {
					"method":"searchTeacherByJsId",
					"JsID":jsId,
				    "yuanxiId":yuanxiId,
				},
				success : function(data) {
					if(data!=null&data!=""){
						  var list = eval('(' + data + ')');
							var i=0;
							if(jsId==2){
								$.each(list,function(index,obj) {
									$tr=$("<tr class='status_open'></tr>")
									$td=$("</td><td>"+obj.gonghao+"</td><td>"+obj.name+"</td><td>"+obj.yuanxi+"&nbsp;:&nbsp;"+obj.zhuanye+"</td><td><button class='simple_buttons' onclick='deleteFromRole(this)'>删除</button></td>");
									$tr.append($td);
									$("#tb_resultList").append($tr);
								});
							}else{
								$.each(list,function(index,obj) {
									$tr=$("<tr class='status_open'></tr>")
									$td=$("</td><td>"+obj.gonghao+"</td><td>"+obj.name+"</td><td>"+obj.yuanxi+"</td><td><button class='simple_buttons' onclick='deleteFromRole(this)'>删除</button></td>");
									$tr.append($td);
									$("#tb_resultList").append($tr);
								});
							}
					  }else{
						  alert("该角色暂未分配教师");
					  }
				}
			});
			
		});
		
		//导出到excel表格
		$("#exportToExcel").click(function(){
			var jsId=$("#searchByRole option:selected").val();
			if(jsId!=0){
			   $.ajax({
			    	url:"../../RoleManage.do",
			    	type : 'POST',
				    data : {
					   "method":"exportTeacherOfRules",
					   "JsID":jsId,
					   "yuanxiId":yuanxiId,
				    },
				    success:function(data){//返回的是“00”表示导出失败，如果是 文件名就下载
					  if(data=="00"){
						alert("未查到数据，无可导出数据");
					  }else{
						window.location.href="../../FileCache/"+data;
					 }
				 }
			    }) ;
		   }else{
			   alert("请先选择角色以筛选数据");
		   }
		});		
		
	});
});
