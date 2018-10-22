//初始化页面顶部按钮
function initialTabClick(th_is){
	var $span=$(th_is);
	var choosedCard=$span.text();
	switch(choosedCard){
	case "院内个人优秀论文":
		window.location.href="recommend.jsp";
		break;
	case "院内小组优秀论文":
		window.location.href="group.jsp";
		break;
	}
}


//点击添加个人到校级推荐**************************************************************************
function setXiaoPersonalYxlw(th_is){
	
	var trList = $("#tb_YPersonalPaperDone").children("tr");
	var recommended=$("#personalLimit").text();
	if(recommended=="0"){
		alert("请等待教务处分配院系优秀论数量后再操作");
	}else if(trList.length>=parseInt(recommended)){
		alert("已经达到推荐数量上限")
	}else{
		var yuanxiId=$("#thisYuanxiId").val();
		$this=$(th_is);
		$this.val("");
		var  $tr=$this.parents("tr");
		var xuehao=$tr.children("td").eq(0).text();
		$.ajax({
			url : "../../YuanAppraising.do",
			type : 'POST',
			data : {
				"method":"setXiaoPersonalYxlw",
				"xuehao":xuehao,
			},
			success : function(data) {
				if(data="11"){
					window.location.href="recommend.jsp";
				}else if(data=="22"){
					alert("校级评优工作已开启，您已无权执行该操作");
				}else{
					alert("添加到校级优秀论文推荐失败，请联系管理员")
				}
			}
		});
	}
}

//点击添加小组到校级推荐**************************************************************************
function setXiaoGroupYxlw(th_is){
	
	var trList = $("#tb_YGroupPaperDone").children("tr");
	var recommended=$("#groupLimit").text();
	if(recommended=="0"){
		alert("请等待教务处分配院系优秀论数量后再操作");
	}else if(trList.length>=parseInt(recommended)){
		alert("已经达到推荐数量上限")
	}else{
	  var yuanxiId=$("#thisYuanxiId").val();
	  $this=$(th_is);
	  $this.val("");
	  var  $tr=$this.parents("tr");
	  var id=$tr.children("td").eq(0).children("input").eq(0).val();
	  $.ajax({
		url : "../../YuanAppraising.do",
		type : 'POST',
		data : {
			"method":"setXiaoGrouplYxlw",
			"xuehao":id,
		},
		success : function(data) {
			if(data="11"){
				window.location.href="group.jsp";
			}else if(data=="22"){
				alert("校级评优工作已开启，您已无权执行该操作");
			}else{
				alert("添加到省级优秀论文推荐失败，请联系管理员")
			}
		}
	   });
	}
}

//点击取消推荐个人到校级**************************************************************************
function cancelXiaoPerosonalYxlw(th_is){
	  var yuanxiId=$("#thisYuanxiId").val();
	$this=$(th_is);
	$this.val("");
	var  $tr=$this.parents("tr");
	var xuehao=$tr.children("td").eq(0).text();
	$.ajax({
		url : "../../YuanAppraising.do",
		type : 'POST',
		data : {
			"method":"cancelXiaoPersonalYxlw",
			"xuehao":xuehao,
		},
		success : function(data) {
			if(data="11"){
				window.location.href="recommend.jsp";
			}else if(data=="22"){
				alert("校级评优工作已开启，您已无权执行该操作");
			}else{
				alert("添加到省级优秀论文推荐失败，请联系管理员")
			}
		}
	});
}

//点击取消推荐小组优秀到校级**************************************************************************
function cancelXiaoGroupYxlw(th_is){
	  var yuanxiId=$("#thisYuanxiId").val();
	$this=$(th_is);
	$this.val("");
	var  $tr=$this.parents("tr");
	var id=$tr.children("td").eq(0).children("input").eq(0).val();
	$.ajax({
		url : "../../YuanAppraising.do",
		type : 'POST',
		data : {
			"method":"cancelXiaoGrouplYxlw",
			"xuehao":id,
		},
		success : function(data) {
			if(data="11"){
				window.location.href="group.jsp";
			}else if(data=="22"){
				alert("校级评优工作已开启，您已无权执行该操作");
			}else{
				alert("添加到省级优秀论文推荐失败，请联系管理员")
			}
		}
	});
}

//请求未推荐到校里的个人优秀论文信息信息***********************************************************************************************************
function loadPersonalNotRecommend(yuanxiId){
	  var yuanxiId=$("#thisYuanxiId").val();
	$.ajax({
		url : "../../YuanAppraising.do",
		type : 'POST',
		data : {
			"method":"findYuanPersonalYxlw",
			"yuanxiId":yuanxiId,
		},
		success : function(data) {
			//返回json数据格式："{xuehao:,yuanxi:,lunwen:,xiaofenshu:yuanfenshu: yxlw.getYfs() }
			if(data=="00"){
				//alert("尚无个人优秀论文记录可查，请稍后再尝试！");
			}else{
				var list = eval('(' + data + ')');
				$.each(list,function(index,obj){
					var $tr=$("<tr></tr>");
					var $td=$("<td>"+obj.xuehao+"</td><td>"+obj.name+"</td><td>"+obj.zhuanye+"</td><td>"+obj.lunwen+"</td><td>"+obj.fenshu+"</td><td><span onclick='setXiaoPersonalYxlw(this)'>推荐</span></td>")
				    $tr.append($td);
					$("#tb_YPersonalPaper").append($tr);
				})
			}
		}
	});
}

//请求已经推荐到校里的个人优秀论文信息信息***********************************************************************************************************
function loadPersonalRecommeded(yuanxiId){
	  var yuanxiId=$("#thisYuanxiId").val();
	$.ajax({
		url : "../../YuanAppraising.do",
		type : 'POST',
		data : {
			"method":"findYuanPersonalRecommedYxlw",
			"yuanxiId":yuanxiId,
		},
		success : function(data) {
			//返回json数据格式："{xuehao:,yuanxi:,lunwen:,xiaofenshu:yuanfenshu: yxlw.getYfs() }
			if(data=="00"){
				//alert("尚无个人优秀论文记录可查，请稍后再尝试！");
			}else{
				var list = eval('(' + data + ')');
				$.each(list,function(index,obj){
					var $tr=$("<tr></tr>");
					var $td=$("<td>"+obj.xuehao+"</td><td>"+obj.name+"</td><td>"+obj.zhuanye+"</td><td>"+obj.lunwen+"</td><td>"+obj.fenshu+"</td><td><span onclick='cancelXiaoPerosonalYxlw(this)'>取消推荐</span></td>")
				    $tr.append($td);
					$("#tb_YPersonalPaperDone").append($tr);
				})
			}
		}
	});
}

//请求未推荐到校里的小组优秀论文信息信息***********************************************************************************************************
 function loadGroupNotRecommend(yuanxiId){
	  var yuanxiId=$("#thisYuanxiId").val();
	 $.ajax({
			url : "../../YuanAppraising.do",
			type : 'POST',
			data : {
				"method":"findYuangroupYxlw",
				"yuanxiId":yuanxiId,
			},
			success : function(data) {
				//返回json数据格式："{zuhao:,yuanxi:,xiaofenshu:yuanfenshu:
				if(data=="00"){
					//alert("尚无小组优秀论文记录，请稍后尝试");
				}else{
					var list = eval('(' + data + ')');
					$.each(list,function(index,obj){
						var $tr=$("<tr></tr>");
						var $td=$("<td><input type='text' value='"+obj.id+"'/>"+obj.zuhao+"</td><td>"+obj.fenshu+"</td><td>"+obj.yuanfenshu+"</td><td></td><td><span onclick='setXiaoGroupYxlw(this)'>推荐</span></td>")
					    $tr.append($td);
						$("#tb_YGroupPaper").append($tr);
					})
				}
			}
		});
 }

//请求已经推荐到校里的小组优秀论文信息信息***********************************************************************************************************
 function loadGroupRecommended(yuanxiId){
	  var yuanxiId=$("#thisYuanxiId").val();
	 $.ajax({
			url : "../../YuanAppraising.do",
			type : 'POST',
			data : {
				"method":"findYuanGroupRecommedYxlw",
				"yuanxiId":yuanxiId,
			},
			success : function(data) {
				//返回json数据格式："{zuhao:,yuanxi:,xiaofenshu:yuanfenshu:
				if(data=="00"){
					//alert("尚无小组优秀论文记录，请稍后尝试");
				}else{
					var list = eval('(' + data + ')');
					$.each(list,function(index,obj){
						var $tr=$("<tr></tr>");
						var $td=$("<td><input type='text' value='"+obj.id+"'/>"+obj.zuhao+"</td><td>"+obj.fenshu+"</td><td>"+obj.yuanfenshu+"</td><td><span onclick='cancelXiaoGroupYxlw(this)'>取消推荐</span></td>")
					    $tr.append($td);
						$("#tb_YGroupPaperDone").append($tr);
					})
				}
			}
		});
 }
 
 
$(function() {
	$(document).ready(function() {
		  var yuanxiId=$("#thisYuanxiId").val();
		  var pageSwitch=$("#pageSwitch").val();
//加载论文数量限制数据--该数据由教务处设置
		 $.ajax({
				url : "../../YuanAppraising.do",
				type : 'POST',
				data : {
					"method":"getYxPaperCount",
					"yuanxiId":yuanxiId,
				},
				success : function(resData) {
					var data = eval('(' + resData + ')')[0];
					$("#personalLimit").text(data.personalCount);
					$("#groupLimit").text(data.groupCount);
				}
			});
		
// 请求个人优秀论文信息信息*********************************************
		    if(pageSwitch=="recommend"){
		    	loadPersonalNotRecommend(yuanxiId);
		    	loadPersonalRecommeded(yuanxiId);
		    }
		
// 请求小组优秀论文信息信息**********************************************
		    if(pageSwitch=="group"){
		    	loadGroupNotRecommend(yuanxiId);
		    	loadGroupRecommended(yuanxiId);
		    }
			
			
	});
});
