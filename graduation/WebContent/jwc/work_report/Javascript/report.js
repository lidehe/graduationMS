
//删除中期小结、最终总结
function reportDelete(th_is){
	var $this=$(th_is);
	var $tr=$this.parents("tr");
	var reportId=$tr.children("td").eq(0).children("input").eq(0).val();
	$.ajax({
		url : "../../JwcReport.do",
		type : 'POST',
		data : {
			"method":"deleteReportById",
			"reportId":reportId,
		},
		success : function(resData) {
			if(resData=="00"){
				smartaAlert("删除失败");
			}else{
				$tr.remove();
			}
		}
	});
}


//加载全校的中期小结 
function loadMidReport(){
	$.ajax({
		url : "../../JwcReport.do",
		type : 'POST',
		data :{
			"method":"getMid",
		},
		success : function(data) {
			//smartaAlert(data);
			if(data=="00"){
				//smartaAlert("尚未上传中期小结")
			}else{
				var list = eval('(' + data + ')');
				$.each(list,function(index,obj) {
					var str=new Array();
					$tr=$("<tr></tr>");
					$td=$("<td><input type='hidden' value='"+obj.id+"' />"+obj.yuanxi+"</td><td>"+obj.name+"</td><td>"+obj.time+"</td><td><a href='../../JwcReport.do?method=showZqxj&zqxjId="+obj.id+"'>预览</a></td>" +
							"<td><a href='../../JwcReport.do?method=loadZqxj&zqxjId="+obj.id+
								"'>下载</a></td><td><button class='simple_buttons' onclick='reportDelete(this)'>删除小结</button></td>")
					$tr.append($td);
					$("#midReportList").append($tr);
				});
			}
		  
		},
	});
}

//加载全校的最终总结 
function loadFinalReport(){
	$.ajax({
		url : "../../JwcReport.do",
		type : 'POST',
		data :{
			"method":"getFinal",
		},
		success : function(data) {
			$("#finalReportList").empty();
			//smartaAlert(data);
			if(data=="00"){
				//smartaAlert("尚未上传最终小结")
			}else{
				var list = eval('(' + data + ')');
				$.each(list,function(index,obj) {
					var str=new Array();
					$tr=$("<tr></tr>");
					$td=$("<td><input type='hidden' value='"+obj.id+"' />"+obj.yuanxi+"</td><td>"+obj.name+"</td><td>"+obj.time+"</td><td><a href='../../JwcReport.do?method=showZhzj&zhzjId="+obj.id+"'>预览</a></td>" +
							"<td><a href='../../JwcReport.do?method=loadZhzj&zhzjId="+obj.id+
								"'>下载</a></td><td><button class='simple_buttons' onclick='reportDelete(this)'>删除小结</button></td>")
					$tr.append($td);
					$("#finalReportList").append($tr);
				});
			}
		},
	});
}

$(function() {
	$(document).ready(function() {
		// <input type="hidden" id="midOrfinal" value="mid"/>
		var whichPage=$("#midOrfinal").val();
		if(whichPage=="mid"){
			//加载本院系的中期小结 
			loadMidReport();
		}else{
			//加载本院系的最终总结
			loadFinalReport();
		}
	});

	
});
