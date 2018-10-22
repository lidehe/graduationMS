/**
 * 
 */
$(function() {
	$("#yxSelector").change(function() {
		if ($(this).val() != 0) {
			getYxCj($(this).val());
		}
	})

	$("#yx")
			.change(
					function() {
						var $yx = $(this).val();
						if ($yx != 0) {
							$(".ajaxLoad1").css("display", "inline-block");
							$("#zy").attr("disabled", "disabled");
							$
									.ajax({
										url : "dcld.do?meth=loadzy",
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

	$("#zy").change(function() {
		var $zy = $(this).val();
		if ($zy != 0) {
			$(".ajaxLoad2").css("display", "inline-block");
			$.ajax({
				url : "dcld.do?meth=loadstu",
				type : "post",
				data : {
					zy : $zy
				},
				dataType:"json",
				success : function(data) {
					if (data.status == 200) {
						var students = data.students;
						setTable(students);
						$(".ajaxLoad2").css("display", "none");
					} else {
						$(".ajaxLoad2").css("display", "none");
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
});

function getYxCj(yx) {
	$.ajax({
		url : "charts.do?meth=yxcj",
		type : "post",
		data : {
			yx : yx
		},
		dataType:"json",
		error : function(response) {
			alert("connect error!");
			location.reload();
		},
		success : function(data) {
			if (data.status == 200) {
				var div = $(".cj_pie_charts")[0];
				while (div.hasChildNodes()) // 当table下还存在子节点时
				{
					div.removeChild(div.firstChild);
				}
				if (!!$(".cj_pie_charts").offset()) {
					$.plot($(".cj_pie_charts"), [ {
						label : "不及格",
						data : data.bjg
					}, {
						label : "及格",
						data : data.jg
					}, {
						label : "中等",
						data : data.zd
					}, {
						label : "良好",
						data : data.lh
					}, {
						label : "优秀",
						data : data.yx
					} ], {
						colors : [ "#E82E36", "#F7810C", "#D6D048", "#00AADD",
								"#23C766" ],

						series : {
							pie : {
								show : true,
								tilt : 0.6,
								label : {
									show : true,
								}
							},
						},

						grid : {
							show : false,
						},

						legend : {
							show : true,
							margin : [ 0, -24 ],
							noColumns : 1,
							labelBoxBorderColor : null,
						},
					});
				} 
			}else if (data.status == 202) {
				var div = $(".cj_pie_charts")[0];
				while (div.hasChildNodes()) // 当table下还存在子节点时
				{
					div.removeChild(div.firstChild);
				}
				$(".cj_pie_charts").text("暂无成绩！");
			} else {
				alert(data.msg);
			}
		}
	});
}

function setTable(students) {
	var content = '<table class="datatable tables"><thead><tr><th>班级</th><th>学号</th><th>姓名</th><th>详情</th></tr></thead><tbody>';
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
				+ '"/><div onclick="xsDetail(this)">详 情</div></div></td></tr>';
	}

	content += '</tbody></table>';

	var table = $("#tableContent")[0];
	while (table.hasChildNodes()) // 当table下还存在子节点时
	{
		table.removeChild(table.firstChild);
	}

	$("#tableContent").append(content);
	$(".datatable")
			.dataTable(
					{
						"sDom" : "<'dtTop'<'dtShowPer'l><'dtFilter'f>><'dtTables't><'dtBottom'<'dtInfo'i><'dtPagination'p>>",
						"oLanguage" : {
							"sLengthMenu" : "每页条目 _MENU_",
						},
						"sPaginationType" : "full_numbers",
						"fnInitComplete" : function() {
							$(".dtShowPer select").uniform();
							$(".dtFilter input").addClass("simple_field").css({
								"width" : "auto",
								"margin-left" : "15px",
							});
						}
					});
	$(".stuTr").show();
}

function xsDetail(eleth){
	var $th = $(eleth);
	var number = $th.parent().find(".hiddenVal").val();
	window.open("dcld.do?meth=xsdetail&number=" + number);
}

function pyDetail1(eleth) {
	var $th = $(eleth);
	var number = $th.parent().find(".hiddenVal").val();
	window.open("dcld.do?meth=pydetail&number=" + number);
}

function pyzDetail1(eleth) {
	var $th = $(eleth);
	var number = $th.parent().find(".hiddenVal").val();
	window.open("dcld.do?meth=pyzdetail&zuhao=" + number);
}