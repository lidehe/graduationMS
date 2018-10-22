/**
 * 毕设总分毕设总分页面
 * @param yuanxiId
 */
function score(yuanxiId,option,nowPage){
	zhuanyeId=$("#zhuanye").find("option:selected").val();
	banji=$("#banji").find("option:selected").val();
	$("#result").empty();
	$.ajax({
		url : "../../Score.do",
		type : 'POST',
		data : {
			"method":"getBSZF",
			"yuanxiId":yuanxiId,
			"zhuanyeId":zhuanyeId,
			"nowPage":nowPage,
			"option":option,
			"banji":banji,
		},
		success : function(data) {
			if(data=="00"){
				$("#tb_result").empty();
				  //alert("未查询到任何信息");
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
							$td=$("<td>"+obj.zhuanyeName+"</td><td>"+obj.stuClass+"</td><td>"+obj.stuNumber+"</td><td>"+obj.stuName+"</td><td>"+obj.stuZf+"</td>");
							$tr.append($td);
							$("#tb_result").append($tr);
						}
					});
			  }
		}
	});
}
/**
 *院系变化加载专业
 */
function loadZhuanye(yuanxiId){
	if(yuanxiId==0){
		alert("你以非教学人员身份登陆，无院系信息，不可查询数据");
	}else{
		$.ajax({
			url : "../../Yuanxi.do",
			type : 'POST',
			data : {
				"method" : "findProfessionByYuanXiId",
				"yuanxiId":yuanxiId,
			},
			success : function(data) {
				// alert(data);
				if(data!="00"){
					var list = eval('(' + data + ')');
					$.each(list, function(index, obj) {
						$op = $("<option value='" + obj.id + "'>"
								+ obj.name + "</option>");
						$("#zhuanye").append($op);
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
	if(zhuanyeId==0){
		$("#banji").empty();
		op = $("<option value='0'>请选择</option>");
		$("#banji").append(op);
	}else{
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
					});
				}
			}
		});
	}
}

$(function() {
	$(document).ready(
			function() {
				 $(".pageBTNS").hide();
				//从session中的来的教师信息中的院系Id
			    var yuanxiId=$("#thisYuanxiId").val();
			    loadZhuanye(yuanxiId);
			    
			    //专业变化
				var zhuanyeId;
				$("#zhuanye").change(function(){
					zhuanyeId=$("#zhuanye").find("option:selected").val();
					score(yuanxiId,"上一页",1);
					loadBanji(zhuanyeId);
				});
				//班级变化
				$("#banji").change(function(){
					score(yuanxiId,"上一页",1);
				});
				//分页按钮
			    $(".pageOption").click(function(){
				//每次查询前，都清除列表
					var option=$(this).text();
					var nowPage=$("#nowPage").text();
					score(yuanxiId,option,nowPage);
				 });
			    
			    $("#exportToExcel").click(function(){
			    	var recordCount=$("#tb_result").children("tr").length;
			    	if(recordCount==0){
			    		alert("暂无数据可以导出");
			    	}else{
			    		zhuanyeId=$("#zhuanye").find("option:selected").val();
			    		banji=$("#banji").find("option:selected").val();
			    		$.ajax({
			    			url : "../../Score.do",
			    			type : 'POST',
			    			data : {
			    				"method":"exportToExcel",
			    				"yuanxiId":yuanxiId,
			    				"zhuanyeId":zhuanyeId,
			    				"banji":banji,
			    			},
			    			success : function(data) {
			    				if(data=="00"){
			    					alert("文件导出失败");
			    				  }else{
			    					  window.location.href="../../FileCache/"+data;
			    				  }
			    			}
			    		});
			    	}
			    });
			    
			    
	});
});
