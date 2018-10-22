/**
 * 
 */
function getYxCj(yx) {
	$.ajax({
		url : "charts.do?meth=yxcj",
		type : "post",
		data : {
			yx : yx
		},
		dataType : "json",
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

$(function() {
	$(".zrsProgress").progressbar({
		value : 100
	});
})

function Xt(width) {
	$(".pwXtAnimate").progressbar({
		value : 1,
		create : function() {
			$(".pwXtAnimate .ui-progressbar-value").animate({
				"width" : width
			}, {
				duration : 1000,
				step : function(now) {
					$(".paValue1").html(parseInt(now) + "%");
				},
				easing : "linear"
			})
		}
	});
}

function Rws(width) {
	$(".pwRwsAnimate").progressbar({
		value : 1,
		create : function() {
			$(".pwRwsAnimate .ui-progressbar-value").animate({
				"width" : width
			}, {
				duration : 1000,
				step : function(now) {
					$(".paValue2").html(parseInt(now) + "%");
				},
				easing : "linear"
			})
		}
	});
}

function Ktbg(width) {
	$(".pwKtbgAnimate").progressbar({
		value : 1,
		create : function() {
			$(".pwKtbgAnimate .ui-progressbar-value").animate({
				"width" : width
			}, {
				duration : 1000,
				step : function(now) {
					$(".paValue3").html(parseInt(now) + "%");
				},
				easing : "linear"
			})
		}
	});
}

function Lw(width) {
	$(".pwLwAnimate").progressbar({
		value : 1,
		create : function() {
			$(".pwLwAnimate .ui-progressbar-value").animate({
				"width" : width
			}, {
				duration : 1000,
				step : function(now) {
					$(".paValue4").html(parseInt(now) + "%");
				},
				easing : "linear"
			})
		}
	});
}

function Db(width) {
	$(".pwDbAnimate").progressbar({
		value : 1,
		create : function() {
			$(".pwDbAnimate .ui-progressbar-value").animate({
				"width" : width
			}, {
				duration : 1000,
				step : function(now) {
					$(".paValue5").html(parseInt(now) + "%");
				},
				easing : "linear"
			})
		}
	});
}