/* 
	数据图形化插入js
*/

var kanrisha_method = {
	
	showTooltip: function (x, y, contents) {
		$('<div class="charts_tooltip">' + contents + '</div>').css( {
			position: 'absolute',
			display: 'none',
			top: y + 5,
			left: x + 5
		}).appendTo("body").fadeIn('fast');
	},

}

var km = kanrisha_method;

// Document Ready and the fun begin :) 
$(function () {
	//请求毕设结果分布信息***********************************************************************************************************
	$.ajax({
		url : "../Defense.do",
		type : 'POST',
		data : {
			
		},
		success : function(data) {
			$("#defenseshow").empty();
			//答辩状态，0就代表正常还未参加初次答辩的，1不参加初次答辩的，2初次未过等待下次答辩的，3最终答辩也未通过，4答辩通过了
			var list = eval('(' + data + ')');
			var notyet=list[0];
			var nochance=list[1];
			var waitnext=list[2];
			var failure=list[3];
			var pass=list[4];
			
			var $tr=$("<tr><td>"+notyet+"</td><td>"+pass+"</td><td>"+failure+"</td><td>"+nochance+"</td></tr>");
			
			$("#defenseshow").append($tr);
			
		    //答辩结果分布统计图 pie_charts_thesis
			if(!!$(".pie_charts_thesis").offset()){
				$.plot($(".pie_charts_thesis"), [{ label: "无答辩资格", data: nochance }, { label: "答辩通过", data: pass }, { label: "答辩不及格", data: failure }, { label: "等待答辩", data: notyet }],
					{
						colors: ["#545454","#76EE00", "#EE4000", "#EEE685"],
						series: {
							pie: {
				                show: true,
				                tilt: 0.6,
				                label: {
			                    	show: true,
			                	}
				            },
						},
						grid: {
							show: false,
						},
						legend: {
							show: true,
							margin: [0,-24],
							noColumns: 1,
							labelBoxBorderColor: null,
						},
					});
			}
		}
	});
	

});
