

//论文抽检结果页面--成绩查看按钮点击事件
function chengJiChakKan(th_is){
	
	var $this=$(th_is);
	var xuehao=$this.parents("tr").children("td").eq(2).text();
	//alert(xuehao);
	$.ajax({
		url : "../../RoleSet.do",
		type : 'POST',
		data : {
			"method":"findChengJi",
			"number":xuehao,
		},
		success : function(data) {
			if(data==null||data==""){
				alert("评阅工作暂未启动，无成绩可查");
			}else{
				//点击查看成绩后，显示成绩面板
				$("#chengJiPanel").show();
				//清空成绩面板里的成绩列表
				$("#tb_chengJi").empty();
				
				var list = eval('(' + data + ')');
				$.each(list,function(index,obj) {
					//把12345转换成优秀-不及格
					var dengJi="";
					if(obj.chengJi==1)
						dengJi="优秀";  
					if(obj.chengJi==2)
						dengJi="良好"; 
					if(obj.chengJi==3)
						dengJi="中等"; 
					if(obj.chengJi==4)
						dengJi="及格"; 
					if(obj.chengJi==5)
						dengJi="不及格"; 
					$tr=$("<tr class='status_open'></tr>");
					$td=$("<td>"+obj.teacher+"</td><td>"+dengJi+"</td><td>"+obj.pingyu+"</td>");
					$tr.append($td);
					$("#tb_chengJi").append($tr);
				});
			}
			
		}
	});
	
}

//抽检权限设置页面--点击一个抽检教师，就查询到Ta绑定了哪些院系
function findCjJsYuanxi(th_is){
	var $this=$(th_is);
	var gonghao=$this.children("td").eq(1).text();
	
	//把工号存放在“存放当前选中的抽检教师”的input之中
	$("#chosedCjJs").val(gonghao);
	
	//alert("查询+工号是"+$("#chosedCjJs").val());
	$(".cjJs").children("td").css("color","black");
	$this.children("td").css("color","red");
	
	$.ajax({
		async :false,
  		url : "../../PaperCheck.do",
  		type : 'POST',
  		data : {
  			"method":"isBinded",
  			"number":gonghao,
  		},
  		success : function(data) {
  			
  			var trList = $("#tb_bdYuanxi").children("tr");
  			
  			if(data!="未绑定任何院系"){
  				var idArr=new Array();
  	  			idArr=data.split(":");
  	  			for(var n=0;n<idArr.length-1;n++){
  	  				//alert(idArr[n]);
  	  				for (var j=0;j<trList.length;j++) {
  	  	  		    	var yuanxiId = trList.eq(j).children("td").eq(0).children(".yxId").val();
  	  	  		      //  alert(yuanxiId);
  	  	  	  			if(yuanxiId==idArr[n]){
  	  	  	  				trList.eq(j).children("td").eq(1).children("input[type='checkbox']").eq(0).attr("checked",true);
  	  	  	  			 }
  	  	  		      }
  	  			}
  			}else{
  				for (var j=0;j<trList.length;j++) {
	  	  	  		trList.eq(j).children("td").eq(1).children("input[type='checkbox']").eq(0).attr("checked",false);
	  	  		 }
  			}
  			
  			
  			
  		}
  	});
   
}
//抽检权限设置页面--检查一个教师和一个院系是否已经绑定，给findCjJsYuanxi函数调用


$(function() {
	$(document).ready(
			function() {
		
//抽检结果页面---成绩列表要隐藏 *********************************************************************************
				$("#chengJiPanel").hide();
				$("#closeChengJiPanel").click(function(){
					$("#chengJiPanel").hide();
				})


//抽检规则页面--设置抽检规则*******************设置抽检规则************************设置抽检规则******************设置抽检规则*******************设置抽检规则*******************设置抽检规则********
				// 隐藏抽检规则设置处的确认按钮
				$(".ruleConfirm").hide();

				// 点击开始筛选按钮
				$("#searchByTaileNumber").click(function() {
					$("#selectedResult").empty();
					var tailNumbers = $(".searchByTaileNumber").val();
					var exp=/^[0-9]\d{0,2}$/;
					if(!exp.test(tailNumbers)){
						alert("规则设置错误，请看标红处规则约定");
						$("#nowRule").text("");
					}else{
						// 提交设置的尾号
						$.ajax({
							url : "../../PaperCheck.do",
							type : 'POST',
							data : {
								"method" : "testRule",
								"tailNumbers" : tailNumbers,
							},
							success : function(data) {
								if (data == null || data == "") {
	                                alert("未查到任何数据")
								} else {
									//更新当前规则
									$("#nowRule").text(tailNumbers);
									$(".ruleConfirm").show();
									var list = eval('(' + data + ')');
									$.each(list, function(index, obj) {
										var $tr = $("<tr class=''><td>"+ obj.yuanxi + "</td><td>"+ obj.count + "</td></tr>")
										$("#selectedResult").append($tr);
									})
								}
							}
						});
					}
				});
//抽检规则页面-----------点击规则确认按钮，开始按规则把学生信息写入抽检表******************************************点击规则确认按钮，开始按规则把学生信息写入抽检表******************************************
				$("#ruleConfirm").click(function(){
					var tailNumbers = $("#nowRule").text();
					// 提交设置的尾号,后台开始向抽检库写入数据
					$.ajax({
						url : "../../PaperCheck.do",
						type : 'POST',
						data : {
							"method" : "confirmRule",
							"tailNumbers" : tailNumbers,
						},
						success : function(data) {
                              alert(data)
						}
					});
				});

//抽检权限设置页面---加载页面后，要请求所有抽检教师信息
				$.ajax({
					url : "../../PaperCheck.do",
					type : 'POST',
					data : {
						"method" : "findAllCjJs",
					},
					success : function(data) {
						$("#tb_cjJs").empty();
                         if(data=="尚未分配抽检人员，请先分配"){
                        	 alert(data)
                         }else{
                        	 var list = eval('(' + data + ')');
							$.each(list, function(index, obj) {
									var $tr = $("<tr class='cjJs' onclick='findCjJsYuanxi(this)'><td>"+ obj.yuanxi + "</td><td>"+ obj.gonghao + "</td><td>"+ obj.xingming + "</td></tr>")
									$("#tb_cjJs").append($tr);
							})
                         }
					}
				});

//抽检权限设置页面---一进来就加载院系，放到右侧院系列表中
				$.ajax({
					async : false,
					url : "../../Yuanxi.do",
					type : 'POST',
					data : {
						"method":"getAllYuanxi",
					},
					success : function(data) {
						//清除院系列表
						$("#tb_bdYuanxi").empty();
						var list = eval('(' + data + ')');
						$.each(list,function(index,object) {
							var obj=object;
							var yuanxiId=obj.id;
							var $tr=$("<tr class='status_open'></tr>");
							$td=$("<td><input class='yxId' type='hidden' value="+obj.id+" />"+obj.name+"</td><td><input type='checkbox' /></td>");
							$tr.append($td);
							$("#tb_bdYuanxi").append($tr);
						});
					}
				});
				
//抽检权限设置页面---点击确认绑定后，执行抽检人员与院系绑定		
				$("#bindConfirm	").click(function(){
					var trList = $("#tb_bdYuanxi").children("tr")
					var yxIdArry="";
					var gonghao=$("#chosedCjJs").val();
					//alert("提交+工号是"+gonghao);
				    for (var i=0;i<trList.length;i++) {
				    	var yuanxiId = trList.eq(i).children("td").eq(0).children(".yxId").val();
                        var isChecked=trList.eq(i).children("td").eq(1).children("input[type='checkbox']").eq(0).attr("checked");
				    	if(isChecked=="checked"){
				    		yxIdArry+=yuanxiId+":";
				    	}
				    	
				    }
				    //向后台传送工号和绑定的院系的ID
				    $.ajax({
		    		//async :false,
		      		url : "../../PaperCheck.do",
		      		type : 'POST',
		      		data : {
		      			"method":"confirmBind",
		      			"number":gonghao,
		      			"yuanxiId":yxIdArry,
		      		},
		      		success : function(data) {
		      			//添加完成后，找到更改绑定的筹建教师所在行，自动点击使页面刷新
		      			var trList = $("#tb_cjJs").children("tr")
					    for (var i=0;i<trList.length;i++) {//遍历抽检人员列表
	                        var opratedGonghao=trList.eq(i).children("td").eq(1).text();
					    	if(opratedGonghao==gonghao){//如果工号和刚刚提交的工号一样，就点击它
					    		//trList.eq(i).click();
					    	}
					    }
		      		}
		        	});
				    
				});
				
	});
});
