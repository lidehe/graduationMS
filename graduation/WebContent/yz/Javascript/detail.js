/**
 * 
 */
$(function(){
	$("#sxcjpsbB").click(function(){
		var $th = $(this);
		$th.css("display","none").parent().find(".ajaxAnimation").css("display","inline-block");
//		var $yi = $(".syi").val() > 0 ? parseFloat($(".syi")
//				.val()) : 0;
//		var $er = $(".ser").val() > 0 ? parseFloat($(".ser")
//				.val()) : 0;
//		var $san = $(".ssan").val() > 0 ? parseFloat($(".ssan")
//				.val()) : 0;
//		var $si = $(".ssi").val() > 0 ? parseFloat($(".ssi")
//				.val()) : 0;
//		var $wu = $(".swu").val() > 0 ? parseFloat($(".swu")
//				.val()) : 0;
//		if ($yi < 0 || $yi > 10 || $er < 0 || $er > 20
//				|| $san < 0 || $san > 20 || $si < 0 || $si > 40
//				|| $wu < 0 || $wu > 10) {
//			alert("请填写完整分数！");
//			return;
//		}
		$.ajax({
			url : "yz.do?meth=sxcj",
			type : "post",
			data : $('#sxcjpsbForm').serialize(),
			error : function(request) {
				alert("Connection error");
				location.reload();
			},
			success : function(data) {
				if (data.status == 200) {
					showMsg(data.msg,"top");
				} else {
					alert(data.msg);
				}
				$th.css("display", "inline-block").parent()
						.find(".ajaxAnimation").css("display",
								"none");
			}
		});
	});
	
	
	$("#zdlspsbB").click(function(){
		var $th = $(this);
		$th.css("display","none").parent().find(".ajaxAnimation").css("display","inline-block");
//		var $yi = $(".zyi").val() > 0 ? parseFloat($(".zyi")
//				.val()) : 0;
//		var $er = $(".zer").val() > 0 ? parseFloat($(".zer")
//				.val()) : 0;
//		var $san = $(".zsan").val() > 0 ? parseFloat($(".zsan")
//				.val()) : 0;
//		var $si = $(".zsi").val() > 0 ? parseFloat($(".zsi")
//				.val()) : 0;
//		var $wu = $(".zwu").val() > 0 ? parseFloat($(".zwu")
//				.val()) : 0;
//		var $liu = $(".zliu").val() > 0 ? parseFloat($(".zliu")
//				.val()) : 0;
//		if ($yi < 0 || $yi > 15 || $er < 0 || $er > 20
//				|| $san < 0 || $san > 25 || $si < 0 || $si > 15
//				|| $wu < 0 || $wu > 15 || $liu < 0 || $liu > 10) {
//			alert("请填写完整分数！");
//			return;
//		}
		$.ajax({
			url : "yz.do?meth=zdlspsb",
			type : "post",
			data :$('#zdlspsbForm').serialize(),
			error : function(request) {
				alert("Connection error");
				location.reload();
			},
			success : function(data) {
				if (data.status == 200) {
					showMsg(data.msg,"top");
				} else {
					alert(data.msg);
				}
				$th.css("display", "inline-block").parent()
						.find(".ajaxAnimation").css("display",
								"none");
			}
		});
	});
	
	$("#dbpsbSelect").change(function(){
		var $th = $(this);
		if($th.val() != "nul"){
			var gonghao = $th.val();
			var xuehao = $("#stuNumber").val();
			$.ajax({
				url : "yz.do?meth=loaddb",
				type : "post",
				data : {
					number : xuehao,
					gonghao : gonghao
				},
				error : function(request) {
					alert("Connection error");
					location.reload();
				},
				success:function(data){
					if(data.status == 200){
						var i = 0;
						$.each(data.content, function() {
							if ($(".dbpsb").get(i).tagName == "input") {
								$(".dbpsb").get(i).val(this.fs);
							} else {
								$(".dbpsb").get(i).val(this.text);
							}
							i++;
						});
					}else{
						alert(data.msg);
					}
				}
			});
		}
	});
	
	$("#pypsbSelect").change(function(){
		var $th = $(this);
		if($th.val() != "nul"){
			var gonghao = $th.val();
			var xuehao = $("#stuNumber").val();
			$.ajax({
				url : "yz.do?meth=loadpy",
				type : "post",
				data : {
					number : xuehao,
					gonghao : gonghao
				},
				error : function(request) {
					alert("Connection error");
					location.reload();
				},
				success:function(data){
					if(data.status == 200){
						var i = 0;
						$.each(data.content, function() {
							if ($(".pypsb").get(i).tagName == "input") {
								$(".pypsb").get(i).val(this.fs);
							} else {
								$(".pypsb").get(i).val(this.text);
							}
							i++;
						})
					}else{
						alert(data.msg);
					}
				}
			});
		}
	});
	
	$("#dbsbB").click(function(){
		var $th = $(this);
		var $select = $("#dbpsbSelect");
		if($select.val() != "nul"){
//			var dex = parseInt($select.val());
//				var $yi = $(".dyi").val() > 0 ? parseFloat($(".dyi")
//						.val()) : 0;
//				var $er = $(".der").val() > 0 ? parseFloat($(".der")
//						.val()) : 0;
//				var $san = $(".dsan").val() > 0 ? parseFloat($(".dsan")
//						.val()) : 0;
//				var $si = $(".dsi").val() > 0 ? parseFloat($(".dsi")
//						.val()) : 0;
//				var $wu = $(".dwu").val() > 0 ? parseFloat($(".dwu")
//						.val()) : 0;
//				var $liu = $(".dliu").val() > 0 ? parseFloat($(".dliu")
//						.val()) : 0;
//				if ($yi < 0 || $yi > 30 || $er < 0 || $er > 20 || $san < 0
//						|| $san > 10 || $si < 0 || $si > 10 || $wu < 0
//						|| $wu > 15 || $liu < 0 || $liu > 15) {
//					alert("请填写完整分数！");
//					return;
//				}
				$th.css("display", "none").parent().find(".ajaxAnimation").css(
						"display", "inline-block");
				$.ajax({
					url : "yz.do?meth=dbpsb",
					type : "post",
					data : $('#dbpsbForm').serialize(),
					error : function(request) {
						alert("Connection error");
						location.reload();
					},
					success : function(data) {
						if (data.status == 200) {
							showMsg(data.msg,"top");
						} else{
							alert(data.msg);
						}
						$th.css("display", "inline-block").parent().find(
								".ajaxAnimation").css("display", "none");
					}
				});
			}
		});
	
	$("#pypsbB").click(function(){
		var $th = $(this);
		var $select = $("#dbpsbSelect");
		if($select.val() != "nul"){
//			var dex = parseInt($select.val());
//				var $yi = $(".pyi").val() > 0 ? parseFloat($(".pyi")
//						.val()) : 0;
//				var $er = $(".per").val() > 0 ? parseFloat($(".per")
//						.val()) : 0;
//				var $san = $(".psan").val() > 0 ? parseFloat($(".psan")
//						.val()) : 0;
//				var $si = $(".psi").val() > 0 ? parseFloat($(".psi")
//						.val()) : 0;
//				var $wu = $(".pwu").val() > 0 ? parseFloat($(".pwu")
//						.val()) : 0;
//				var $liu = $(".pliu").val() > 0 ? parseFloat($(".pliu")
//						.val()) : 0;
//				if ($yi < 0 || $yi > 20 || $er < 0 || $er > 25 || $san < 0
//						|| $san > 15 || $si < 0 || $si > 15 || $wu < 0
//						|| $wu > 15 || $liu < 0 || $liu > 10) {
//					alert("请填写完整分数！");
//					return;
//				}
				$th.css("display", "none").parent().find(".ajaxAnimation").css(
						"display", "inline-block");
				$.ajax({
					url : "yz.do?meth=pypsb",
					type : "post",
					data : $('#pypsbForm').serialize(),
					error : function(request) {
						alert("Connection error");
						location.reload();
					},
					success : function(data) {
						if (data.status == 200) {
							showMsg(data.msg,"top");
						} else{
							alert(data.msg);
						}
						$th.css("display", "inline-block").parent().find(
								".ajaxAnimation").css("display", "none");
					}
				});
			}
		});
	
	
});