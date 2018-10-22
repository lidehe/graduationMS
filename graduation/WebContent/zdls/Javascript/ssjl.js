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

	$("#LwButton")
			.click(
					function() {
						var $text = $("#Ltext").val();
						if ($text == null || $text == "" || $text.length > 400) {
							alert("内容错误！");
							return;
						}
						var $number = $("#stuNumber").val();
						$
								.ajax({
									url : "tssjl.do?meth=add",
									type : "post",
									data : {
										text : $text,
										xuehao : $number
									},
									error : function(response) {
										alert("connect error!");
										location.reload();
									},
									success : function(data) {
										if (data.status == 200) {
											showMsg("发送成功！", "top");
											var $con = '<div class="line_grid myline line_border_no"><div class="g_2">'
													+ '<span> </span></div>'
													+ '<div class="g_8 g_8M">'
													+ '<div class="message">'
													+ '<span class="label lwParagraph">'
													+ data.time
													+ ':'
													+ $text
													+ '</span></div></div><div class="g_2 g_2M">'
													+ '<img src="student/Images/Avatar/teacher.png" title="您"'
													+ 'lt="avatar" class="avatar" />'
													+ '</div></div>';
											$(".myDiv").prepend($con);
											$("#Ltext").val("");
										} else {
											alert(data.msg);
										}
									}
								});
					});
});

var $page = 1;
var $count = 0;

function requireMessage() {
	var $number = $("#stuNumber").val();
	$
			.ajax({
				url : "tssjl.do?meth=require",
				type : "post",
				data : {
					page : $page,
					xuehao : $number
				},
				error : function(response) {
					alert("connect error!");
					location.reload();
				},
				success : function(data) {
					if (data.status == 200) {
						$count = data.count;
						$(".myline").remove();
						var $ne = "";
						$
								.each(
										data.content,
										function() {
											if (this.status == 0
													|| this.status == 1) {
												$ne += '<div class="line_grid myline line_border_no">'
														+ '<div class="g_2 g_2M">'
														+ '<img src="student/Images/Avatar/student.png" title="学生"'
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
														+ '<img src="student/Images/Avatar/teacher.png" title="您"'
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