/**
 * 
 */
$(function() {
	requireMessage();

	$("#previous").click(function() {
		if ($page <= 1) {
			alert("已经是第一页了！");
			return;
		}
		$page--;
		requireMessage();
	});

	$("#next").click(function() {
		if ($page >= $count) {
			alert("已经是最后一页了！");
			return;
		}
		$page++;
		requireMessage();
	});
	
	$("#LwButton").click(function(){
		var $text = $("#Ltext").val();
		if($text == null || $text == "" || $text.length > 400){
			alert("内容错误！");
			return;
		}
		$.ajax({
			url:"ssjl.do?meth=add",
			type:"post",
			data:{
				text:$text
			},
			error:function(response){
				alert("connect error!");
				location.reload();
			},
			success:function(data){
				if(data.status == 200){
					$("#Ltext").val("");
					showMsg("发送成功！","top");
					var $con = '<div class="line_grid myline line_border_no"><div class="g_2">'
						+ '<span> </span></div>'
						+ '<div class="g_8 g_8M">'
						+ '<div class="message">'
						+ '<span class="label lwParagraph">'
						+ data.time
						+ ':'
						+ $text
						+ '</span></div></div><div class="g_2 g_2M">'
						+ '<img src="student/Images/Avatar/student.png" title="你"'
						+ 'lt="avatar" class="avatar" />'
						+ '</div></div>';
					$(".myDiv").prepend($con);
					$("#Ltext").val("");
				}else{
					alert(data.msg);
				}
			}
		});
	});
});

var $page = 1;
var $count = 0;

function requireMessage() {
	$
			.ajax({
				url : "ssjl.do?meth=require",
				type : "post",
				data : {
					page : $page
				},
				error : function(response) {
					alert("connect error!");
					location.reload();
				},
				success : function(data) {
					if (data.status == 200) {
						$count = data.count;
						$(".myline").remove();
						var $ne = '';
						var x = 0;
						$
								.each(
										data.content,
										function() {
											if (this.status == 2
													|| this.status == 3) {
												$ne += '<div class="line_grid myline line_border_no">'
														+ '<div class="g_2 g_2M">'
														+ '<img src="student/Images/Avatar/teacher.png" title="指导老师"'
														+ 'lt="avatar" class="avatar" />'
														+ '</div><div class="g_8 g_8M">'
														+ '<div class="message">'
														+ '<span class="label lwParagraph">'
														+ new Date(this.time.time).format("yyyy-MM-dd hh:mm:ss")
														+ ':'
														+ this.text
														+ '</span></div></div><div class="g_2">'
														+ '<span> </span></div></div>';
											} else {
												$ne += '<div class="line_grid myline line_border_no"><div class="g_2">'
														+ '<span> </span></div>'
														+ '<div class="g_8 g_8M">'
														+ '<div class="message">'
														+ '<span class="label lwParagraph">'
														+ new Date(this.time.time).format("yyyy-MM-dd hh:mm:ss")
														+ ':'
														+ this.text
														+ '</span></div></div><div class="g_2 g_2M">'
														+ '<img src="student/Images/Avatar/student.png" title="你"'
														+ 'lt="avatar" class="avatar" />'
														+ '</div></div>';
											}
										});
						$(".myDiv").append($ne);
					} else {
						showMsg(data.msg, "top");
						$count = data.count;
					}
				}
			});
}

function timeStampToDate($sj) {
	var unixTimestamp = new Date($sj);
	return unixTimestamp.toLocaleString();
}

Date.prototype.format = function(fmt)   
{  
  var o = {   
    "M+" : this.getMonth()+1,                 // 月份
    "d+" : this.getDate(),                    // 日
    "h+" : this.getHours(),                   // 小时
    "m+" : this.getMinutes(),                 // 分
    "s+" : this.getSeconds(),                 // 秒
    "q+" : Math.floor((this.getMonth()+3)/3), // 季度
    "S"  : this.getMilliseconds()             // 毫秒
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
}