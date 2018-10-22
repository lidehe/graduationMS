/**
 * 重置师生密码
 * @param th_is
 */
function resetPassword(th_is){
	$this=$(th_is);
	var number=$this.parents("tr").children("td").eq(0).text();
	$.ajax({
		url : "../../AccountManager.do",
		type : 'POST',
		data : {
	        "number":number,
		},
		success : function(data) {
			smartaAlert("密码重置成功");
		}
	});
}

//加载教师信息
function loadTeacher(yuanxiId,option,nowPage){
	//如果是按工号查询，就不需要分页按钮
	$(".pageBTNS").show();
	
	var  yuanxiId;
	yuanxiId=$("#yuanxi").find("option:selected").val();
	//如果院系ID为0，表示选择的是”选择“选项，则不需要查询
	if(yuanxiId==0){
		$("#tb_result").empty();
		$(".pageBTNS").hide();
	}else{
		$.ajax({
			url : "../../BaseInformation.do",
			type : 'POST',
			data : {
				"method":"pageFindTeacher",
				"yuanxiId":yuanxiId,
				"option":option,
				"nowPage":nowPage,
			},
			success : function(data) {
				 if(data=="00"){
					$(".pageBTNS").hide();
					$("#tb_result").empty();
					smartaAlert("未查询到任何信息");
				  }else if(data=="lastPage"){
					smartaAlert("已经最后一页");
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
								$td=$("</td><td>"+obj.gonghao+"</td><td>"+obj.xingming+"</td><td><button  class='simple_buttons' onclick='resetPassword(this)'>重置密码</button></td>");
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
function loadStudent(yuanxiId,zhuanyeId,option,nowPage){
	   $(".pageBTNS").hide();
		$.ajax({
			url : "../../BaseInformation.do",
			type : 'POST',
			data : {
				"method":"pageFindStudent",
				"yuanxiId":yuanxiId,
				"zhuanyeId":zhuanyeId,
				"option":option,
				"nowPage":nowPage,
			},
			success : function(data) {
				 if(data=="00"){
					$("#tb_result").empty();
					smartaAlert("未查询到任何信息");
				  }else if(data=="lastPage"){
					smartaAlert("已经最后一页");
					$(".pageBTNS").show();
				  }else{
					  $(".pageBTNS").show();
					  $("#tb_result").empty();
					  var list = eval('(' + data + ')');
						var i=0;
						$.each(list,function(index,obj) {
							if(index==0){
								$("#nowPage").text(obj.nowPage);
								$("#totalPage").text(obj.totalPage);
							}else{
								$tr=$("<tr class='status_open'></tr>")
								$td=$("</td><td>"+obj.xuehao+"</td><td>"+obj.xingming+"</td><td>"+obj.banji+"</td><td><button  class='simple_buttons' onclick='resetPassword(this)'>重置密码</button></td>");
								$tr.append($td);
								$("#tb_result").append($tr);
							}
						});
				  }
			}
		});
}

$(function() {
	$(document).ready(function() {
		//当前是学生信息页面还是教师信息页面
		var page=$("#pageSwitch").val();
		
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
		
		
//根据选中的院系查询教师（教师信息页面）或加载专业信息（学生信息页面）******************************************************************************************
		$("#yuanxi").change(function(){
			var yuanxiId=$("#yuanxi").val();
			$("#tb_result").empty();
			$("#zhuanye").empty();
			var option=$("<option value=0>请选择</option>")
			$("#zhuanye").append(option);
			//如果是按工号查询，就不需要分页按钮
			$(".pageBTNS").hide();
			
			if(yuanxiId!=0){
				if(page=="student"){//在学生页面就加载专业信息
					// 请求院系信息
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
									$("#zhuanye").append($op);
							});
						}
					});
				}else if(page=="teacher"){//在教师页面就加载教师信息
					$(".pageBTNS").show();
					loadTeacher(yuanxiId,"上一页",1);
				}
			}
		});
		
//根据选中的专业查询学生信息
		$("#zhuanye").change(function(){
			var yuanxiId=$("#yuanxi").find("option:selected").val();
			var zhuanyeId=$("#zhuanye").find("option:selected").val();
			if(zhuanyeId==0){
				$("#tb_result").empty();
				$(".pageBTNS").hide();
			}else{
				loadStudent(yuanxiId,zhuanyeId,"上一页",1);
			}
			
		});
		
//根据院系分页查询教师**********************************************************************************************************************
	  $(".pageOptionTeacher").click(function(){
		//每次查询前，都清除列表
		  var yuanxiId=$("#yuanxi").find("option:selected").val();
		  var option=$(this).text();
		  var nowPage=$("#nowPage").text();
		  loadTeacher(yuanxiId,option,nowPage);
		    
	  });
//根据院系、专业 分页查询学生**********************************************************************************************************************
	  $(".pageOptionStudent").click(function(){
		//每次查询前，都清除列表
		  var yuanxiId=$("#yuanxi").find("option:selected").val();
		  var zhuanyeId=$("#zhuanye").find("option:selected").val();
		  var option=$(this).text();
		  var nowPage=$("#nowPage").text();
		  loadStudent(yuanxiId,zhuanyeId,option,nowPage);
		    
	  });
	  
//如果是按工号查询，就不需要分页按钮**********************************************************************************************************************
		$(".pageBTNS").hide();
		$(".searchByNumber").focus(function(){
			$("#yuanxi").val(0);
			$("#zhuanye").empty();
			var option=$("<option value=0>请选择</option>")
			$("#zhuanye").append(option);
			
			$(".pageBTNS").hide();
			$("#tb_result").empty();
		});
		
	  
	  
//根据工号查询教师**********************************************************************************************************************
	    $("#searchTeacherByNumber").click(function(){
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
							$.each(data,function(index,obj){
								$tr=$("<tr class='status_open'></tr>")
								$td=$("</td><td>"+obj.gonghao+"</td><td>"+obj.name+"</td><td><button  class='simple_buttons' onclick='resetPassword(this)'>重置密码</button></td>");
								$tr.append($td);
								$("#tb_result").append($tr);
							})
						}else{
							smartaAlert("未查询到任何信息")
						}
							
					  }
				  });
	    	}
	    	 
	    });
	  //根据工号查询教师**********************************************************************************************************************
	    $("#searchStudentByNumber").click(function(){
	    	//每次查询前，都清除列表
	    	$("#tb_result").empty();
	    	//工号
	    	var number=$(".searchByNumber").val();
	    	//smartaAlert($(".searchByNumber").val());
	    	if(number==""||number==null){
	    		smartaAlert("请先输入学号");
	    	}else{
	    		$.ajax({
					  url:"../../BaseInformation.do",
					  type:"post",
					  data:{
						  "method":"searchStudentByNumber",
						  "number":number,
					  },
					  success:function(resData){
						if(resData!=null&resData!=""){
							var data=eval('(' + resData + ')');
							$.each(data,function(index,obj){
								$tr=$("<tr class='status_open'></tr>")
								$td=$("</td><td>"+obj.xuehao+"</td><td>"+obj.xingming+"</td><td>"+obj.banji+"</td><td><button  class='simple_buttons' onclick='resetPassword(this)'>重置密码</button></td>");
								$tr.append($td);
								$("#tb_result").append($tr);
							})
						}else{
							smartaAlert("未查询到任何信息")
						}
							
					  }
				  });
	    	}
	    	 
	    });		
	    
	    
	});
});
