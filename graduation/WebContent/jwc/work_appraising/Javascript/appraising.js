

//校优秀论文页面--点击添加到省级推荐**************************************************************************
function setShengYxlw(th_is){
	$this=$(th_is);
	$this.val("");
	var  $tr=$this.parents("tr");
	var xuehao=$tr.children("td").eq(0).text();
	$.ajax({
		url : "../../Appraising.do",
		type : 'POST',
		data : {
			"method":"setShengYxlw",
			"xuehao":xuehao,
		},
		success : function(data) {
			if(dataa="11"){
				$tr.remove();
			}else{
				smartaAlert("添加到省级优秀论文推荐失败，请联系管理员")
			}
		}
	});
}


//省级优秀论文页面--点击取消推荐到省级**************************************************************************
function cancelShengYxlw(th_is){
	$this=$(th_is);
	$this.val("");
	var  $tr=$this.parents("tr");
	var xuehao=$tr.children("td").eq(0).text();
	$.ajax({
		url : "../../Appraising.do",
		type : 'POST',
		data : {
			"method":"cancelShengYxlw",
			"xuehao":xuehao,
		},
		success : function(data) {
			if(dataa="11"){
				$tr.remove();
			}else{
				smartaAlert("添加到省级优秀论文推荐失败，请联系管理员")
			}
		}
	});
}



$(function() {
	$(document).ready(function() {
	//	alert($("#pageWhitch").val());
//论文数量限制页面--请求院系和院系论文数量限制信息，初始化页面
		if($("#pageWhitch").val()=="各院论文数量设定"){  
			$.ajax({
				url : "../../Appraising.do",
				type : 'POST',
				data : {
					"method":"getYxPaperCount",
				},
				success : function(data) {
					$("#tb_yuanxi").empty();
					var list = eval('(' + data + ')');
					$.each(list,function(index,obj){
					var $tr=$("<tr class=''><td>"+obj.yuanxi+"</td><td><input type='text' name='personalCount' onclick='numberSet(this)' value='"+obj.personalCount+"' /></td><td><input type='text' name='personalCount' onclick='numberSet(this)' value='"+obj.groupCount+"'/></td></tr>");
				    $("#tb_yuanxi").append($tr);
					})
				}
			});
		}
		
//论文数量限制设置页面--保存更改的数量***********************************************************	{
		$("#saveCountChange").click(function(){
			var trList = $("#tb_yuanxi").children("tr");
			//输入是否全部合法
			var isAllRight=1;
			//院系和院系论文数量限制格式为：计算机学院，19，1：文理学院，8，2：      
			var yuanxiAndCount="";
			
			for (var j=0;j<trList.length;j++) {
  	  	  		var persionalCount=trList.eq(j).children("td").eq(1).children("input").eq(0).val();
  	  	  		var groupCount=trList.eq(j).children("td").eq(2).children("input").eq(0).val();
  	  	  		var yuanxiName=trList.eq(j).children("td").text();
  	  	     	var exp=/^(0|[1-9][0-9]{0,1})$/;
			    if(!exp.test(persionalCount)){
			    	smartaAlert(yuanxiName+" 的个人优秀论文数量设置错误："+persionalCount+"，请重新输入");
			    	isAllRight=0;
			    }else if(!exp.test(groupCount)){
			    	isAllRight=0;
			    	smartaAlert(yuanxiName+" 的小组优秀论文数量设置错误："+groupCount+"，请重新输入");
			    }else{
			    	yuanxiAndCount+=yuanxiName+","+persionalCount+","+groupCount+":";
			    }
  	  		}
			//如果输入全部合法，就提交数据到h
			if(isAllRight==1){
				$.ajax({
					url:"../../Appraising.do",
					type:"post",
					data:{
						"method":"savePaperNewCount",
						"yuanxiAndCount":yuanxiAndCount,
					},
					success:function(data){
						if(data=="更新完成")
						window.location.href="countSet.jsp";
					}
				});
			}
			
		});
		
// 校级优秀论文页面-----请求个人优秀论文信息信息***********************************************************************************************************
		//如果是校级优秀论文页面，就请求，其他页面不请求，可以有效减少不必要的请求
		if($("#pageWhitch").val()=="校级优秀论文"){  
			$.ajax({
				url : "../../Appraising.do",
				type : 'POST',
				data : {
					"method":"findXiaoPersonalYxlw",
				},
				success : function(data) {
					//返回json数据格式："{xuehao:,yuanxi:,lunwen:,xiaofenshu:yuanfenshu: yxlw.getYfs() }
					$("#tb_XPersonalPaper").empty();
					if(data=="00"){
						//smartaAlert("尚无个人优秀论文记录可查，请稍后再尝试！");
					}else{
						var list = eval('(' + data + ')');
						$.each(list,function(index,obj){
							var $tr=$("<tr></tr>");
							var $td=$("<td>"+obj.xuehao+"</td><td>"+obj.yuanxi+"</td><td>"+obj.lunwen+"</td><td>"+obj.xiaofenshu+"</td><td>"+obj.yuanfenshu+"</td><td><span onclick='setShengYxlw(this)'>详情</span></td><td><span>推荐</span></td>")
						    $tr.append($td);
							$("#tb_XPersonalPaper").append($tr);
						})
					}
				}
			});
		}
		
// 校级优秀论文页面-----请求小组优秀论文信息信息***********************************************************************************************************
		if($("#pageWhitch").val()=="校级优秀论文"){ 
			$.ajax({
				url : "../../Appraising.do",
				type : 'POST',
				data : {
					"method":"findXiaogroupYxlw",
				},
				success : function(data) {
					//返回json数据格式："{zuhao:,yuanxi:,xiaofenshu:yuanfenshu:
					$("#tb_XGroupPaper").empty();
					if(data=="00"){
						//smartaAlert("尚无小组优秀论文记录，请稍后尝试");
					}else{
						var list = eval('(' + data + ')');
						$.each(list,function(index,obj){
							var $tr=$("<tr></tr>");
							var $td=$("<td>"+obj.zuhao+"</td><td>"+obj.yuanxi+"</td><td>"+obj.xiaofenshu+"</td><td>"+obj.yuanfenshu+"</td><td><span>详情</span></td><td><span onclick='setShengYxlw(this)'>推荐</span></td>")
						    $tr.append($td);
							$("#tb_XGroupPaper").append($tr);
						})
					}
				}
			});
		}
		
		省级优秀论文
		
		
// 省级优秀论文页面-----请求推荐到省级优秀论文信息信息***********************************************************************************************************
		if($("#pageWhitch").val()=="省级优秀论文"){ 
			$.ajax({
				url : "../../Appraising.do",
				type : 'POST',
				data : {
					"method":"findShengYxlw",
				},
				success : function(data) {
					//返回json数据格式："{xuehao:,yuanxi:,lunwen: yxlw.getYfs() }
					$("#tb_SPaper").empty();
					if(data=="00"){
						smartaAlert("请等待各级院系推送优秀论文后再尝试查询");
					}else{
						var list = eval('(' + data + ')');
						$.each(list,function(index,obj){
							var $tr=$("<tr></tr>");
							var $td=$("<td>"+obj.xuehao+"</td><td>"+obj.yuanxi+"</td><td>"+obj.lunwen+"</td><td><span>详情</span></td><td><span onclick='cancelShengYxlw(this)'>删除</span></td>")
						    $tr.append($td);
							$("#tb_SPaper").append($tr);
						})
					}
				}
			});
		}		
	});
});
