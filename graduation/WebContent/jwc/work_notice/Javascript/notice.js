

//公告列表页面--查看公告详情
function noticeDetail(th_is){
	var $this=$(th_is);
	var noticeId=$this.parents("tr").children("td").eq(0).children("input").eq(0).val();
	$("#noticeDetail").show();
	$.ajax({
		url : "../../XiaoNotice.do",
		type : 'POST',
		data : {
			"method":"findOneNoticeById",
			"noticeId":noticeId,
		},
		success : function(resData) {
			if(resData=="00"||resData=="01"){
				smartaAlert("未查到该公告信息");
			}else{
				//显示公告面板
				$("#noticeDetail").show();
				//清空面板
				$("#title").empty();
			    $("#content").empty();
			    $("#time").empty();
			    
				var data = eval('(' + resData + ')');
				$("#attch").empty();
				$.each(data,function(index,obj){
					if(index==0){
						$("#title").append(obj.title);
					    $("#content").append(obj.content);
					    $("#time").append(obj.time);
					}else{
						$div=$("<div>附件"+index+":&nbsp;"+"<a href='../../XiaoNotice.do?method=viewAttch&attchId="+obj.attchId+"'>"+obj.attchName+"</a></div>");
						 $("#attch").append($div);
					}
				});
				
			}
		}
	});
}

//公告列表页面---删除公告
function noticeDelete(th_is){
	var $this=$(th_is);
	var $tr=$this.parents("tr");
	var noticeId=$tr.children("td").eq(0).children("input").eq(0).val();
	$.ajax({
		url : "../../XiaoNotice.do",
		type : 'POST',
		data : {
			"method":"deleteNoticeById",
			"noticeId":noticeId,
		},
		success : function(resData) {
			if(resData=="00"){
				smartaAlert("删除失败");
			}else if(resData=="01"){
				smartaAlert("未找到该公告");
				
			}else{
				$tr.remove();
			}
		}
	});
}



$(function() {$(document).ready(function() {
	
	//来此kanrisha.js的控制富文本编辑框的控件
	$(".wysiwyg").cleditor({width:"100%", height:"100%"});
    
	//隐藏/显示公告详情面板
	$("#noticeDetail").hide();
	$("#closeDetailPanel").click(function(){
		$("#noticeDetail").hide();
	});
	

//点击确定添加新的公告	****************************************************************************
	$("#confirm").click(function(){
		var title=$("#noticeTitle").val();
		var content=$("#noticeContent").val();
		if(title==""||content==""){
			smartaAlert("公告的内容和标题都是必须的")
		}else{
			$.ajax({
				url : "../../XiaoNotice.do",
				type : 'POST',
				data : {
					"method":"addNotice",
					"title":title,
					"content":content,
				},
				success : function(data) {
					if(data=="11"){
						smartaAlert("添加成功")
						window.location.href="notice.jsp";
					}else{
						smartaAlert("添加失败")
					}
				}
			});
		}
		
	});
//在公告列表页面加载校级公告*************************************************************************	
	var pageId=$("#pageId").val();
	if(pageId=="noticeList"){
		$.ajax({
			url : "../../XiaoNotice.do",
			type : 'POST',
			data : {
				"method":"findAllNotice",
			},
			success : function(data) {
				//清除院系列表
				$("#noticeList").empty();
				if(data=="00"){
					//smartaAlert("尚未发布任何公告");
				}else{
					var list = eval('(' + data + ')');
					$.each(list,function(index,object) {
						var obj=object;
						var yuanxiId=obj.id;
						var $tr=$("<tr class='status_open'></tr>");
						$td=$("<td><input type='hidden' value='"+obj.id+"' />"+obj.title+"</td><td>"+obj.year+"</td><td><button class='simple_buttons' onclick='noticeDetail(this)'>查看详情</button></td>" +
								"<td><button class='simple_buttons' onclick='noticeDelete(this)'>删除公告</button></td>");
						$tr.append($td);
						$("#noticeList").append($tr);
					});
				}
				
			}
		});
	  }
	
	
	});
});
