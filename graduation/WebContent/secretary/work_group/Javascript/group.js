
//新建答辩组页面-----取消选中的答辩组成员
function cancleClick(th_is){
	var $tr=$(th_is).parents("tr");
	//refreshTeacher();//刷新教师列表
	$tr.remove();
}

//新建答辩组页面-----点击选中教师，添加到答辩组成员列表
function tItemClick(th_is){
	var $this=$(th_is);
	//alert($this.parents("tr").children("td").eq(1).text());
	var number=$this.parents("tr").children("td").eq(0).text();
	var name=$this.parents("tr").children("td").eq(1).text();
	
	//判断是否已经存在右侧了，如果存在，就不能添加到右侧，并提示
	var trList = $("#g_list").children("tr")
	var isExist=0;
    for (var i=0;i<trList.length;i++) {
      var chosedNum = trList.eq(i).children("td").eq(0).text();
      if(number==chosedNum){
    	  alert("已经添加到右侧列表了，不要重复添加");
    	  isExist=1;
    	  break;
      }
    }
    if(trList.length>=3){
    	alert("一组最多三个教师");
    }else if(isExist==0&&trList.length<=3){
		$this.css("color","red");
		$tr=$("<tr class='status_open'><td>"+number+"</td><td>"+name+"</td><td><button class='simple_buttons' onclick='removeFromGList(this)'>取消</button></td></tr>");
		$("#g_list").append($tr);
	}
}
//新建答辩组页面-------从新建组成员列表去掉一行***************************************************
function removeFromGList(th_is){
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

//已有答辩组页面--------查看答辩组详情，就是加载所有详细组员、答辩秘书等**************************
function dbzlDetail(th_is){
	$("#dbz_detaile").empty();
	var $this=$(th_is);
	var dbzlName=$this.parents("tr").children("td").eq(0).text();
	$("#groupName").text(dbzlName);
	//alert(dbzlName);
	$.ajax({
		url : "../../Group.do",
		type : 'POST',
		data : {
			"method":"findDbzlDtetail",
			"dbzlName":dbzlName,
		},
		success : function(data) {
			//alert(data);
			if(data==""||data==null){
				alert("网络错误，未取得服务器反馈");
			}else if(data=="11"){
				alert("该组没有教师");
			}else if(data=="00"){
				alert("根据该组名未找到答辩组");
			}else{
				var list = eval('(' + data + ')');
				$.each(list,function(index,obj) {
					if(index<list.length-1){
						if(obj.job=="组长"){
							$tr=$("<tr class='status_open'></tr>");
							$td=$("<td>"+obj.gonghao+"</td><td>"+obj.name+"</td><td>"+obj.job+"</td><td></td>");
							$tr.append($td);
							$("#dbz_detaile").append($tr);
						}else if(obj.job=="成员"){
							$tr=$("<tr class='status_open'></tr>");
							$td=$("<td>"+obj.gonghao+"</td><td>"+obj.name+"</td><td>"+obj.job+"</td><td><button class='simple_buttons' onclick='setZZ(this)'>设为组长</button></td>");
							$tr.append($td);
							$("#dbz_detaile").append($tr);
						}
						
					}else{
						if(obj.msName!=""){
							$tr=$("<tr class='status_open'></tr>");
							$td=$("<td>答辩组秘书有:</td><td colspan ='2'>"+obj.msName+"</td>");
							$tr.append($td);
							$("#dbz_detaile").append($tr);
						}
					}
				});
			}
		}
	});
}

//已有答辩组页面------删除答辩组
function deleteDbzl(th_is){
	var $this=$(th_is);
	var dbzlName=$this.parents("tr").children("td").eq(0).text();
	  $.ajax({
		  url : "../../Group.do",
		  type : 'POST',
		  data : {
			"method":"deleteGroup",
			"dbzlName":dbzlName,
		  },
		  success : function(data) {
			//alert(data);
			if(data=="00"){
				alert("删除失败！")
			}else{
				window.location.href="groupList.jsp";
			}
		  }
       });
}

//已有答辩组页面-------重新设置组长
function setZZ(th_is){
	var $this=$(th_is);
	var dbzlName=$("#groupName").text();
	var gonghao=$this.parents("tr").children("td").eq(0).text();
	  $.ajax({
		  url : "../../Group.do",
		  type : 'POST',
		  data : {
			"method":"changeZZ",
			"dbzlName":dbzlName,
            "gonghao":gonghao,
		  },
		  success : function(data) {
			 var trList=$("#groupsshow").children("tr");
			 for (var i=0;i<trList.length;i++) {
				if(dbzlName==trList.eq(i).children("td").eq(0).text()){
					trList.eq(i).children("td").eq(1).children("button").click();
					break;
				}
			}
		  }
       });
}

$(function() {$(document).ready(function() {
	
	//从session中的来的教师信息中的院系Id
	  var yuanxiId=$("#thisYuanxiId").val();
	  
//新建答辩组页面--------隐藏分页按钮	
		$(".searchByNumber").focus(function(){
			$(".pageBTNS").hide();
			$("#tb_result").empty();
		});
		
		var pageName=$("#whichPage").val();
//已有答辩组页面--------加载已有的答辩组********************************************		
		if(pageName=="groupList"){
		   $.ajax({
			  url : "../../Group.do",
			  type : 'POST',
			  data : {
				"method":"findAllDbzl",
				"yuanxiId":yuanxiId,
			  },
			  success : function(data) {
				//alert(data);
				if(data!="00"){
					var list = eval('(' + data + ')');
					$.each(list,function(index,obj) {
						$tr=$("<tr class='status_open'></tr>")
						$td=$("<td>"+obj.name+"</td><td><button class='simple_buttons' onclick='dbzlDetail(this)' >查看</button></td><td><button class='simple_buttons' onclick='deleteDbzl(this)'>删除</button></td>");
						$tr.append($td);
						$("#groupsshow").append($tr);
					});
				}else{
					alert("未查询到任何信息")
				}
			  }
	       });
         }
		
	$(".pageBTNS").hide();
  //查找本院系所有教师，院系根据登陆者所属院系来确定，从session中获取，暂时用固定的1，******************************************************************************************
	$("#allThisYx").click(function(){
		//每次查询前，都清除列表
		$("#tb_result").empty();
		//如果是按工号查询，就不需要分页按钮
		$(".pageBTNS").show();
		if(yuanxiId==0){
			$("#tb_result").empty();
			$(".pageBTNS").hide();
		}else{
			$.ajax({
				url : "../../Group.do",
				type : 'POST',
				data : {
					"method":"pageFindTearcherByYuanxiId",
					"yuanxiId":yuanxiId,
					"option":"initial",
					"nowPage":1,
				},
				success : function(data) {
					//alert(data);
					if(data!=null&data!=""){
						var list = eval('(' + data + ')');
						var i=0;
						$.each(list,function(index,obj) {
							if(index==0){
								$("#nowPage").text(obj.nowPage);
								$("#allPage").text(obj.totalPage);
							}else{
								$tr=$("<tr class='status_open'></tr>")
								$td=$("<td>"+obj.gonghao+"</td><td>"+obj.name+"</td><td><button class='simple_buttons' onclick='tItemClick(this)'>添加</button></td>");
								$tr.append($td);
								$("#tb_result").append($tr);
							}
						});
					}else{
						$(".pageBTNS").hide();
						alert("未查询到任何信息")
					}
				},
				error : function(responseStr) {
					console.log("error");
				}
			});
		}
	});
	
	
	
//根据院系分页查询教师**********************************************************************************************************************
  $(".pageOption").click(function(){
	//每次查询前，都清除列表
	  $("#tb_result").empty();
	  var option=$(this).text();
	  var nowPage=$("#nowPage").text();
	  var allPage=$("#allPage").text();
	  $.ajax({
		  url:"../../Group.do",
		  type:"post",
		  data:{
			  "method":"pageFindTearcherByYuanxiId",
			  "yuanxiId":yuanxiId,
			  "option":option,
			  "nowPage":nowPage,
		  },
		  success:function(data){
			//alert(data);
			  if(data!=null&data!=""){
				  var list = eval('(' + data + ')');
					var i=0;
					$.each(list,function(index,obj) {
						if(index==0){
							$("#nowPage").text(obj.nowPage);
							$("#allPage").text(obj.totalPage);
						}else{
							$tr=$("<tr class='status_open'></tr>")
							$td=$("<td>"+obj.gonghao+"</td><td>"+obj.name+"</td><td><button class='simple_buttons' onclick='tItemClick(this)'>添加</button></td>");
							$tr.append($td);
							$("#tb_result").append($tr);
						}
					});
			  }else{
				  alert("未查询到任何信息")
			  }
		  }
	  });
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
				  url:"../../Group.do",
				  type:"post",
				  data:{
					  "method":"searchByNumber",
					  "number":number,
				  },
				  success:function(resData){
					if(resData=="00"){
						alert("该工号无效，没有找到相关信息");
					}else if(resData=="11"){
						alert("该教师已经在别的组");
					}else{
						var data=eval('(' + resData + ')')[0];
						$tr=$("<tr class='status_open'></tr>")
						$td=$("<td>"+data.gonghao+"</td><td>"+data.name+"</td><td><button class='simple_buttons' onclick='tItemClick(this)'>添加</button></td>");
						$tr.append($td);
						$("#tb_result").append($tr);
					}
						
				  }
			  });
    	}
    	 
    });
    
//新建答辩组**************************************************   
    $("#newGroup").click(function(){
		var msNameInputs=$(".dbms");
		var msNames="";
		 for (var i=0;i<msNameInputs.length;i++) {
		      var chosedName = msNameInputs.eq(i).val();
		      if(chosedName!="")
		      msNames+=chosedName+":";
		      }
		
		//遍历选中的教师，提取工号，并且汇总成格式字符串，等待提交
		var trList = $("#g_list").children("tr");
		if(trList.length<=0){
			alert("请先添加待分组的教师!")
		}else{
		    var numberStr="";
		    for (var i=0;i<trList.length;i++) {
		      var chosedNum = trList.eq(i).children("td").eq(0).text();
		    	  numberStr+=chosedNum+":";
		      }
		   // alert(numberStr);
			//把取到的教师工号和角色ID传递到后台
			$.ajax({
				url : "../../Group.do",
				type : 'POST',
				data : {
					"method":"newGroup",
					"groupMemberNumber":numberStr,
					"msNames":msNames,
					"yuanxiId":yuanxiId,
				},
				success : function(data) {
					alert(data);
					$("#tb_result").empty();
					$("#g_list").empty();
					$(".pageBTNS").hide();
				}
			});
	    }
    	
    	
    });
    
    
	
	});
});
