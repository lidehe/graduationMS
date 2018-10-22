/**
 * 
 */
$(function() {
	$("#jdxcgB").click(
			function() {
				if ($("#wj").val() == null || $("#wj").val() == "") {
					alert("请选择上传的文件！");
				} else {
					$("#jdxcgB").css("display", "none").parent().find(
							".addGBajax").css("display", "inline-block");
					jdxcgUpload();
				}
			});

});

function jdxcgUpload() {
	var formData = new FormData($("#jdxcgwj")[0]);
	$.ajax({
		url : "upload.do?meth=jdxcg",
		type : 'POST',
		data : formData,
		async : false,
		cache : false,
		contentType : false,
		processData : false,
		success : function(data) {
			$("#jdxcgB").css("display", "inline-block").parent().find(
					".addGBajax").css("display", "none");
			showMsg(data.msg, "center", "window.location.href = \"jdxcg.do\"");
		},
		error : function(data) {
			alert("connect error!");
			window.location.href = "jdxcg.do";
		}
	});
}

function uploadJdxcgs(th) {
	var $th = $(th);
	var $wj = $th.parents(".jdxcgswj").find(".fjWj");
	var $form = $th.parents(".jdxcgswj");
	if ($wj.val() == null || $wj.val() == "") {
		alert("请选择上传的文件！");
	} else {
		$th.css("display", "none").parent().find(".addGBajax").css("display",
				"inline-block");
		var formData = new FormData($form[0]);
		$
				.ajax({
					url : "upload.do?meth=jdxcgs",
					type : 'POST',
					data : formData,
					async : false,
					cache : false,
					contentType : false,
					processData : false,
					success : function(data) {
						if (data.status == 200) {
							if (data.k == 1) {// 代表新的
								var $ne = '';
								$ne = '<div class="line_grid"><form class="jdxcgswj" enctype="multipart/form-data">'
										+ '<input type="hidden" name="jId" value="'
										+ data.id
										+ '" />'
										+ '<div class="g_4"><span class="label">'
										+ data.name
										+ '</span>'
										+ '</div><div class="g_4"><input type="file" name="wj"'
										+ 'accept=".pdf,.PDF,.zip,.ZIP,.rar,.RAR,.doc,.DOC,.docx,.DOCX"'
										+ 'class="simple_form fjWj simple_form1" />'
										+ '</div><div class="g_2" style="text-align: center;">'
										+ '<div class="simple_buttons">'
										+ '<div onclick="uploadJdxcgs(this)" class="jdxcgB1">覆&nbsp;盖</div>'
										+ '<div class="addGBajax" style="white-space:pre;">   </div>'
										+ '</div></div><div class="g_2" style="text-align: center;">'
										+ '<div class="simple_buttons">'
										+ '<div onclick="window.open(\'preview.do?meth=jdxcgsLoad&id='
										+ data.id
										+ '\');">查&nbsp;看</div></div></div></form></div>';
								$(".newLine").prepend($ne);
								showMsg(data.msgs + "<br/>" + data.msg,
										"center");
								$(".simple_form1").uniform();
							} else {// 这是覆盖原来的
								showMsg(data.msgs + "<br/>" + data.msg,
										"center");
							}
						} else if (data.status == 400) {// 上限了！
							alert(data.msg);
						} else {// 其他错误情况
							alert(data.msg);
						}
						$th.css("display", "inline-block").parent().find(
								".addGBajax").css("display", "none");
						$form[0].reset();
						$("span.filename").text("请选择文件");
					},
					error : function(data) {
						alert("出错：可能文件太大！");
						window.location.href = "jdxcg.do";
					}
				});
	}
}