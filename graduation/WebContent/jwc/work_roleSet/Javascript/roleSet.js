
//********************************************************************************************************************************************************************
//角色选择
function roleChoose(th_is){
	var $role=$(th_is);
	var role;
	role=$("#role option:selected").text();
	//smartaAlert(role);
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
	//smartaAlert($this.parents("tr").children("td").eq(1).text());
	var number=$this.parents("tr").children("td").eq(0).text();
	var name=$this.parents("tr").children("td").eq(1).text();
	
	//判断是否已经存在右侧了，如果存在，就不能添加到右侧，并提示
	var trList = $("#tb_chosedList").children("tr")
	var isExist=0;
    for (var i=0;i<trList.length;i++) {
      var chosedNum = trList.eq(i).children("td").eq(0).text();
      if(number==chosedNum){
    	  smartaAlert("已经添加到右侧列表了，不要重复添加");
    	  isExist=1;
    	  break;
      }
    }
	if(isExist==0){
		$this.css("color","red");
		$tr=$("<tr class='status_open'><td>"+number+"</td><td>"+name+"</td><td><button  class='simple_buttons' onclick='roleUnbind(this)'>取消</button></td></tr>");
		$("#tb_chosedList").append($tr);
	}
}
//全选，把一页教师全部加到右侧
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
		url : "../../RoleSet.do",
		type : 'POST',
		data : {
			"method":"deleteTeacherFromRole",
			"number":number,
			"JsID":jsId,
		},
		success : function(data) {
			smartaAlert(data);
		}
	});
}


function loadTeacher(yuanxiId,JsID,option,nowPage){
	//如果是按工号查询，就不需要分页按钮
	//如果院系ID为0，表示选择的是”选择“选项，则不需要查询
	if(yuanxiId==0){
		$("#tb_result").empty();
		$(".pageBTNS").hide();
	}else{
		$.ajax({
			url : "../../RoleSet.do",
			type : 'POST',
			data : {
				"method":"pageFindTearcherByYuanxiId",
				"yuanxiId":yuanxiId,
				"option":option,
				"JsID":JsID,
				"nowPage":nowPage,
			},
			success : function(data) {
				if(data=="00"){
					  smartaAlert("未查询到任何信息");
					  $("#tb_result").empty();
					  $(".pageBTNS").hide();
				  }else if(data=="lastPage"){
					smartaAlert("已经最后一页");
					$(".pageBTNS").show();
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
								$td=$("<td>"+obj.gonghao+"</td><td>"+obj.name+"</td><td><button  class='simple_buttons' onclick='roleBind(this)'>添加</button></td>");
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
		//请求角色信息,用于初始化角色管理页面***********************************************************************************************************
		$.ajax({
			url : "../../Jsgx.do",
			type : 'POST',
			data : {
				"method":"findAllJs",
			},
			success : function(data) {
				var list = eval('(' + data + ')');
				$.each(list,function(index,obj) {
					$td=$("<tr><td>"+obj.id+"</td><td>"+obj.name+"</td></tr>")
					$("#roleList").append($td);
						});
			},
			error : function(responseStr) {
				console.log("error");
			}
		});
		// 请求院系信息
		$.ajax({
			url : "../../Yuanxi.do",
			type : 'POST',
			data : {
				"method" : "getAllYuanxi"
			},
			success : function(data) {
				var list = eval('(' + data + ')');
				$.each(list, function(index, obj) {
					$op = $("<option value='" + obj.id + "'>"
							+ obj.name + "</option>")
					$("#yuanxi").append($op);
				});
			}
		});
		
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
					$op=$("<option value='"+obj.id+"'>"+obj.name+"</option>")
					//角色分配和角色改变两个页面都需要初始化角色信息
					$("#role").append($op);
					$("#searchByRole").append($op);
						});
			},
			error : function(responseStr) {
				console.log("error");
			}
		});
		
//角色选择发生变化，就在开头的变量中记录下来选择的角色的ID**********************************************************************************************************************
		var  JsID="0";//定义变量，存储角色ID,默认为"0";
		var  yuanxiId=$("#yuanxi").find("option:selected").val();
		$("#role").change(function(){
			JsID=$("#role").find("option:selected").val();
			JsName=$("#role").find("option:selected").text();
			$("#tb_result").empty();
			$("#tb_chosedList").empty();
			
			//角色后面添加角色描述信息；已选教师列表头部添加已选角色
			if(JsID==0){
				$("#chosedRule").text("");
				$("#ruleDiscription").text("");
			}else{
				$("#chosedRule").text(JsName);
				/*switch(JsID){
			    	case "3"://教学秘书
						$("#ruleDiscription").text("负责一个院系的毕业设计工作");
						break;
					case "4"://院系负责人
						$("#ruleDiscription").text("院系领导，如院长。查看本院系毕设信息、状况");
						break;
					case "5"://管理员
						$("#ruleDiscription").text("全校毕设工作管理人员");
						break;
					case "6"://校级评优人员
						$("#ruleDiscription").text("参与校级优秀论文的评选");
						break;
					case "7"://抽检人员
						$("#ruleDiscription").text("参与论文质量抽检");
						break;
					case "8"://答辩组人员	
						$("#ruleDiscription").text("毕设答辩教师组成员");
						break;
					case "9"://校领导、督导
						$("#ruleDiscription").text("校级领导，如校长，查看全校毕设信息、状况");
						break;
					}*/
				if(yuanxiId!=0){
					loadTeacher(yuanxiId,JsID,"上一页",1);
					$("#tb_result").empty();
					$("#tb_chosedList").empty();
				}
			}
		});

		
		
//如果是按工号查询，就不需要分页按钮**********************************************************************************************************************
		$(".pageBTNS").hide();
		$(".searchByNumber").focus(function(){
			$(".pageBTNS").hide();
			$("#tb_result").empty();
		});
		
		
		
		
//根据选中的角色ID和院系的ID,查询该院系下未分配该角色的教师信息，该院系已经分配了该角色的教师不列在其中******************************************************************************************
		$("#yuanxi").change(function(){
			if(JsID==0){
				smartaAlert("请选择角色");
			}else{
				yuanxiId=$("#yuanxi").find("option:selected").val();
				loadTeacher(yuanxiId,JsID,"上一页",1);
				$("#tb_chosedList").empty();
			}
		});
		
		
//根据院系分页查询教师**********************************************************************************************************************
	  $(".pageOption").click(function(){
		//每次查询前，都清除列表
		   yuanxiId=$("#yuanxi").find("option:selected").val();
		  var option=$(this).text();
		  var nowPage=$("#nowPage").text();
		  var allPage=$("#allPage").text();
		  loadTeacher(yuanxiId,JsID,option,nowPage);
	  });

//根据工号查询教师**********************************************************************************************************************
	    $("#searchByNumber").click(function(){
	    	//每次查询前，都清除列表
	    	$("#tb_result").empty();
	    	//工号
	    	var number=$(".searchByNumber").val();
	    	//smartaAlert($(".searchByNumber").val());
	    	if(number==""||number==null){
	    		smartaAlert("请先输入工号");
	    	}else{
	    		$.ajax({
					  url:"../../RoleSet.do",
					  type:"post",
					  data:{
						  "method":"searchByNumber",
						  "number":number,
					  },
					  success:function(resData){
						if(resData!=null&resData!=""){
							var data=eval('(' + resData + ')');
							$.each(data,function(index,ojb){
								$tr=$("<tr class='status_open'></tr>")
								$td=$("</td><td>"+ojb.gonghao+"</td><td>"+ojb.name+"</td><td><button  class='simple_buttons' onclick='roleBind(this)'>添加</button></td>");
								$tr.append($td);
								$("#tb_result").append($tr);
							});
							
						}else{
							smartaAlert("未查询到任何信息")
						}
							
					  }
				  });
	    	}
	    	 
	    });
	  
	    
	    
	    
//确定添加角色**********************************************************************************************************************
		$("#setRoles").click(function(){
			//遍历选中的教职工，提取工号，并且汇总成格式字符串，等待提交
			var trList = $("#tb_chosedList").children("tr")
			
			if(JsID=="0"){
				smartaAlert("请选择角色!")
			}else if(trList.length<=0){
				smartaAlert("请先添加职工!")
			}else{
			    var numberStr="";
			    for (var i=0;i<trList.length;i++) {
			      var chosedNum = trList.eq(i).children("td").eq(0).text();
			    	  numberStr+=chosedNum+":";
			      }
				//把取到的教师工号和角色ID传递到后台
				$.ajax({
					url : "../../RoleSet.do",
					type : 'POST',
					data : {
						"method":"setRoles",
						"numberStr":numberStr,
						"JsID":JsID,
					},
					success : function(data) {
						smartaAlert(data);
						$("#role").val(0);
						$("#yuanxi").val(0);
						$("#tb_result").empty();
						$("#tb_chosedList").empty();
						$(".pageBTNS").hide();
					}
				});
		    }
		});
		
		
		
//根据角色变化查询教师，从而操作该角色下的教职工***********************************************************************************************
		$("#searchByRole").change(function(){
			$("#tb_resultList").empty();
			//角色ID
			var jsId=$("#searchByRole option:selected").val();
			$.ajax({
				url : "../../RoleSet.do",
				type : 'POST',
				data : {
					"method":"searchTeacherByJsId",
					"JsID":jsId,
				},
				success : function(data) {
					//smartaAlert(data);
					if(data!=null&data!=""){
						//如果有数据，就显示分页按钮
						
						  var list = eval('(' + data + ')');
							var i=0;
							$.each(list,function(index,obj) {
									$tr=$("<tr class='status_open'></tr>")
									$td=$("</td><td>"+obj.gonghao+"</td><td>"+obj.name+"</td><td>"+obj.yuanxi+"</td><td><button  class='simple_buttons' onclick='deleteFromRole(this)'>删除</button></td>");
									$tr.append($td);
									$("#tb_resultList").append($tr);
							});
					  }else{
						  $(".pageBTNS").hide();
						  smartaAlert("未查询到任何信息")
					  }
				}
			});
			
		});
//导出到excel表格
		$("#exportToExcel").click(function(){
			var jsId=$("#searchByRole option:selected").val();
			if(jsId!=0){
			   $.ajax({
			    	url:"../../RoleSet.do",
			    	type : 'POST',
				    data : {
					   "method":"exportTeacherOfRules",
					   "JsID":jsId,
				    },
				    success:function(data){//返回的是“00”表示导出失败，如果是 文件名就下载
					  if(data=="00"){
						smartaAlert("未查到数据，无可导出数据");
					  }else{
						window.location.href="../../FileCache/"+data;
					 }
				 }
			    }) ;
		   }else{
			   smartaAlert("请先选择角色以筛选数据");
		   }
		});
		
		
	});
});
