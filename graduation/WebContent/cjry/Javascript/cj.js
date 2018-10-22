/**
 * 
 */
$(function() {
	$("#yx")
			.change(
					function() {
						var $yx = $(this).val();
						if ($yx != 0) {
							$(".ajaxLoad1").css("display", "inline-block");
							$("#zy").attr("disabled", "disabled");
							$
									.ajax({
										url : "cjry.do?meth=loadzy",
										type : "post",
										data : {
											yx : $yx
										},
										dataType:"json",
										success : function(data) {
											if (data.status == 200) {
												var zys = data.zys;
												var $select = $("#zy");
												$(".zyOption").remove();
												for ( var o in zys) {
													var $zyO = $("<option class='zyOption' value='"
															+ zys[o].id
															+ "'>"
															+ zys[o].name
															+ "</option>");
													$select.append($zyO);
												}
											} else {
												showMsg(data.msg, "top");
											}
											$("#zy").attr("disabled", false);
											$(".ajaxLoad1").css("display",
													"none");
										},
										error : function(request) {
											alert("Connection error");
											location.reload();
										}
									});
						}
					});

	$("#zy")
			.change(
					function() {
						var $zy = $(this).val();
						if ($zy != 0) {
							$(".ajaxLoad2").css("display", "inline-block");
							$
									.ajax({
										url : "cjry.do?meth=loadstu",
										type : "post",
										data : {
											zy : $zy
										},
										dataType:"json",
										success : function(data) {
											if (data.status == 200) {
												var students = data.students;
												setTable(students);
												$(".ajaxLoad2").css("display","none");
											} else {
												$(".ajaxLoad2").css("display","none");
												showMsg(data.msg, "top");
											}
										},
										error : function(request) {
											alert("Connection error");
											location.reload();
										}
									});
						}
					});
	
	$("#searchB").click(function(){
		searchStudent();
	});
	$("#studentXH").keyup(function(event){
		if(event.keyCode == 13)
			searchStudent();
	});
});

function setTable(students){
	var content = '<table class="datatable tables"><thead><tr><th>专业</th><th>学号</th><th>姓名</th><th>详情</th></tr></thead><tbody>';
	for ( var o in students) {
		content += '<tr class="stuTr"><td>'
				+ students[o].zy
				+ '</td><td>'
				+ students[o].number
				+ '</td><td>'
				+ students[o].name
				+ '</td>'
				+ '<td style="text-align:center;"><div class="simple_buttons"><input type="hidden" class="hiddenVal" value="'
				+ students[o].number
				+ '"/><div onclick="cjDetail(this)">详 情</div></div></td></tr>';
	}

	content += '</tbody></table>';

	var table = $("#tableContent")[0];
	while (table.hasChildNodes()) // 当table下还存在子节点时
	{
		table.removeChild(table.firstChild);
	}

	$("#tableContent").append(content);
	$(".datatable").dataTable(
					{
						"sDom" : "<'dtTop'<'dtShowPer'l><'dtFilter'f>><'dtTables't><'dtBottom'<'dtInfo'i><'dtPagination'p>>",
						"oLanguage" : {
							"sLengthMenu" : "Show entries _MENU_",
						},
						"sPaginationType" : "full_numbers",
						"fnInitComplete" : function() {
							$(
									".dtShowPer select")
									.uniform();
							$(
									".dtFilter input")
									.addClass(
											"simple_field")
									.css(
											{
												"width" : "auto",
												"margin-left" : "15px",
											});
						}
					});
	$(".stuTr").show();
}

function searchStudent(){
	$("#yx").val(0);
	$("#zy").val(0);
	var key = $("#studentXH").val();
	if(key == "" || key == null){
		alert("请输入关键字！");
	}else{
		$(".ajaxLoad2").css("display", "inline-block");
		$.ajax({
			url : "cjry.do?meth=loadstuK",
			type : "post",
			data : {
				key : key
			},
			dataType:"json",
			success : function(data) {
				if (data.status == 200) {
					var students = data.students;
					setTable(students);
					$(".ajaxLoad2").css("display","none");
				} else {
					$(".ajaxLoad2").css("display","none");
					showMsg(data.msg, "top");
				}
			},
			error : function(request) {
				alert("Connection error");
				location.reload();
			}
		});
	}
}

function cjDetail(ta){
	var $th = $(ta);
	window.open("cjry.do?meth=detail&number="+$th.parent().find(".hiddenVal").val());
}
