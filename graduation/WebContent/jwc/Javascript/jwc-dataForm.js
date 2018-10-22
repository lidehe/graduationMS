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
	
	//alert("角色管理页面");
	//请求院系信息
/*	$.ajax({
		url : "../../Yuanxi.do",
		type : 'POST',
		data : {
			"method":"getAllYuanxi"
		},
		success : function(data) {
			//alert(data);
			var list = eval('(' + data + ')');
			$.each(list,function(index,obj) {
				$op=$("<option value='"+obj.id+"'>"+obj.name+"</option>")
				$("#yuanxi").append($op);
			});
		},
		error : function(responseStr) {
			console.log("error");
		}
	});
	*/
	

//柱状图部分
/* Charts ============================================ */
	
	if (!!$(".charts").offset() ) {
		var sin = [];
		var cos = [];

		for (var i = 0; i <= 20; i += 0.5){
			sin.push([i, Math.sin(i)]);
			cos.push([i, Math.cos(i)]);
		}

		// Display the Sin and Cos Functions
		$.plot($(".charts"), [ { label: "Cos", data: cos }, { label: "Sin", data: sin } ],
			{
				colors: ["#00AADD", "#FF6347"],

				series: {
					lines: {
							show: true,
							lineWidth: 2,
						   },
					points: {show: true},
					shadowSize: 2,
				},

				grid: {
					hoverable: true,
					show: true,
					borderWidth: 0,
					tickColor: "#d2d2d2",
					labelMargin: 12,
				},

				legend: {
					show: true,
					margin: [0,-24],
					noColumns: 0,
					labelBoxBorderColor: null,
				},

				yaxis: { min: -1.2, max: 1.2},
				xaxis: {},
			});

		// Tooltip Show
		var previousPoint = null;
		$(".charts").bind("plothover", function (event, pos, item) {
			if (item) {
				if (previousPoint != item.dataIndex) {
					previousPoint = item.dataIndex;
					$(".charts_tooltip").fadeOut("fast").promise().done(function(){
						$(this).remove();
					});
					var x = item.datapoint[0].toFixed(2),
						y = item.datapoint[1].toFixed(2);
					km.showTooltip(item.pageX, item.pageY, item.series.label + " of " + x + " = " + y);
				}
			}
			else {
				$(".charts_tooltip").fadeOut("fast").promise().done(function(){
					$(this).remove();
				});
				previousPoint = null;
			}
		});

		if (!!$(".v_bars").offset() && !!$(".h_bars").offset() && !!$(".realtime_charts").offset()) {
			// Display Some Vertican bars
			$.plot($(".v_bars"), [ { data: [ [00,20], [20,50], [40,90], [60,30], [80,80], [100,60]] }, { data: [ [10,30], [30,80], [50,50], [70,10], [90,70] ] } ],
				{
					colors: ["#F7810C", "#E82E36"],

					series: {
						lines: {
								show: false,
								lineWidth: 2,
							   },
						points: {show: false},
						shadowSize: 2,
						bars: {
							show: true,
							barWidth: 3,
							lineWidth: 1,
							fill: 0.8,
						}
					},

					grid: {
						hoverable: false,
						show: true,
						borderWidth: 0,
						tickColor: "#d2d2d2",
						labelMargin: 12,
					},

					legend: {
						show: false,
					},

					yaxis: { min: 0, max: 100},
					xaxis: { min: 0, max: 105},
				});

			// Display Some Vertical bars
			$.plot($(".h_bars"), [ { data: [ [20,20], [20,50], [40,00], [60,30], [80,80], [100,70]] }, { data: [ [10,10], [30,100], [50,40], [70,90], [90,60] ] } ],
				{
					colors: ["#F7810C", "#E82E36"],

					series: {
						lines: {
								show: false,
								lineWidth: 2,
							   },
						points: {show: false},
						shadowSize: 2,
						bars: {
							show: true,
							barWidth: 3,
							lineWidth: 1,
							fill: 0.8,
							horizontal: true,
						}
					},

					grid: {
						hoverable: false,
						show: true,
						borderWidth: 0,
						tickColor: "#d2d2d2",
						labelMargin: 12,
					},

					legend: {
						show: false,
					},

					yaxis: { min: 0, max: 100},
					xaxis: { min: 0, max: 105},
				});

			// Display the realtime Charts
			// Generate a random data
			var data = [], totalPoints = 300;
			function  getRandomData () {
				if (data.length > 0)
					data = data.slice(1);
				// do a random walk
				while (data.length < totalPoints) {
					var prev = data.length > 0 ? data[data.length - 1] : 50;
					var y = prev + Math.random() * 10 - 5;
					if (y < 0)
						y = 0;
					if (y > 100)
					y = 100;
					data.push(y);
				}
				// zip the generated y values with the x values
				var res = [];
				for (var i = 0; i < data.length; ++i)
					res.push([i, data[i]])
				return res;
			}
			var realtime = $.plot($(".realtime_charts"), [ getRandomData() ],
				{
					colors: ["#00AADD"],

					series: {
						lines: {
								show: true,
								lineWidth: 2,
								fill: 0.65,
							   },
						points: {show: false},
						shadowSize: 2,
					},

					grid: {
						show: true,
						borderWidth: 0,
						tickColor: "#d2d2d2",
						labelMargin: 12,
					},

					legend: {
						show: false,
					},

					yaxis: { min: 0, max: 105},
					xaxis: { min: 0, max: 250},
				}	
			);
			function realtime_function() {
				realtime.setData([ getRandomData() ]);
				realtime.draw();
				setTimeout(realtime_function, 700);
			}
			realtime_function();
		}
	}

	
	
/* 图表部分============================================ */
	// 毕业方式分布饼状图
	if(!!$(".pie_charts").offset()){
		$.plot($(".pie_charts"), [{ label: "普通毕业方式：80人", data: 80 }, { label: "专利毕业方式：15人", data: 15 }],
			{
				colors: ["#F7810C", "#00AADD"],

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
	
	// 进度分布饼状图
	if(!!$(".pie_charts_schedule").offset()){
		$.plot($(".pie_charts_schedule"), [ { label: "申报普通课题：80人", data: 80 }, { label: "课题审核：20人", data: 20 }, { label: "申报和审核课题：30人", data: 30 }],
			{
				colors: ["#F7810C", "#00AADD", "#E82E36"],
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
   /*  //答辩结果分布统计图 pie_charts_thesis
	if(!!$(".pie_charts_thesis").offset()){
		$.plot($(".pie_charts_thesis"), [{ label: "无答辩资格", data: 2 }, { label: "答辩通过", data: 160 }, { label: "答辩不及格", data: 6 }, { label: "等待答辩", data: 30 }],
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
	}*/
	
	
	$(".datatable_paperCheck").dataTable({
        "sDom": "<'dtTop'<'dtShowPer'><'dtFilter'f>><'dtTables't><'dtBottom'<'dtInfo'i><'dtPagination'p>>",
        "oLanguage": {
            "sLengthMenu": "Show entries _MENU_",
        },
        "sPaginationType": "full_numbers",
        "fnInitComplete": function(){
        	$(".dtShowPer select").uniform();
        	$(".dtFilter input").addClass("simple_field").css({
        		"width": "auto",
        		"margin-left": "15px",
        	});
        }
    });
	
	/* Tables ============================================ */
	// Set the DataTables
	$(".datatable").dataTable({
        "sDom": "<'dtTop'<'dtShowPer'l><'dtFilter'f>><'dtTables't><'dtBottom'<'dtInfo'i><'dtPagination'p>>",
        "oLanguage": {
            "sLengthMenu": "Show entries _MENU_",
        },
        "sPaginationType": "full_numbers",
        "fnInitComplete": function(){
        	$(".dtShowPer select").uniform();
        	$(".dtFilter input").addClass("simple_field").css({
        		"width": "auto",
        		"margin-left": "15px",
        	});
        }
    });

	
	
	// Table Resize-able
	$(".resizeable_tables").colResizable({
		liveDrag: true,
		minWidth: 40,
	});

	// Table with Tabs
	$( "#table_wTabs" ).tabs();
	
	// Check All Checkbox
	$(".tMainC").click(function(){
		var checked = $(this).prop("checked");
		var parent = $(this).closest(".twCheckbox");

		parent.find(".checker").each(function(){
			if (checked){
				$(this).find("span").addClass("checked");
				$(this).find("input").prop("checked", true);
			}else{
				$(this).find("span").removeClass("checked");
				$(this).find("input").prop("checked", false);
			}
		})
	});
	// Table Resize-able
	$(".resizeable_tables").colResizable({
		liveDrag: true,
		minWidth: 40,
	});

	// Table with Tabs
	$( "#table_wTabs" ).tabs();
	
	// Check All Checkbox
	$(".tMainC").click(function(){
		var checked = $(this).prop("checked");
		var parent = $(this).closest(".twCheckbox");

		parent.find(".checker").each(function(){
			if (checked){
				$(this).find("span").addClass("checked");
				$(this).find("input").prop("checked", true);
			}else{
				$(this).find("span").removeClass("checked");
				$(this).find("input").prop("checked", false);
			}
		})
	});

/* Forms ============================================= */
	$(".simple_form").uniform(); // Style The Checkbox and Radio
	$(".elastic").elastic();
	$(".twMaxChars").supertextarea({
	   	maxl: 140
	});

/* Spinner =========================================== */
	$(".spinner1").spinner();
	$(".spinner2").spinner({
		min: 0,
		max: 30,
	});
	$(".spinner3").spinner({
		min: 0,
		prefix: '$',
	});
	$(".spinner4").spinner().spinner("disable");
	$(".spinner5").spinner({'step':5});

/* ToolTip & ColorPicker & DatePicker ================ */
	$(".tooltip").tipsy({trigger: 'focus', gravity: 's', fade: true});
	$("#btTop").tipsy({gravity: 's'});
	$("#btTopF").tipsy({gravity: 's',fade: true});
	$("#btTopD").tipsy({gravity: 's',delayOut: 2000});
	$("#btLeft").tipsy({gravity: 'e'});
	$("#btRight").tipsy({gravity: 'w'});
	$("#btBottom").tipsy({gravity: 'n'});

	$(".fwColorpicker").ColorPicker({
		onSubmit: function(hsb, hex, rgb, el) {
			$(el).val(hex);
			$(el).ColorPickerHide();
		},
		onBeforeShow: function () {
			$(this).ColorPickerSetColor(this.value);
		},
	})
	.bind('keyup', function(){
		$(this).ColorPickerSetColor(this.value);
	});	

	$( ".pick_date" ).datepicker();



/* Progress ========================================== */
	
	$(".sProgress").progressbar({
		value: 40
	});

	$(".pwAnimate").progressbar({
		value: 1,
		create: function() {
			$(".pwAnimate .ui-progressbar-value").animate({"width":"100%"},{
				duration: 10000,
				step: function(now){
					$(".paValue").html(parseInt(now)+"%");
				},
				easing: "linear"
			})
		}
	});

	$(".pwuAnimate").progressbar({
		value: 1,
		create: function() {
			$(".pwuAnimate .ui-progressbar-value").animate({"width":"100%"},{
				duration: 30000,
				easing: 'linear',
				step: function(now){
					$(".pauValue").html(parseInt(now*10.24)+" Mb");
				},
				complete: function(){
					$(".pwuAnimate + .field_notice").html("<span class='must'>Upload Finished</span>");
				} 
			})
		}
	});

/* Tab Toggle ======================================== */
	
	$(".cwhToggle").click(function(){
		// Get Height
		var wC = $(this).parents().eq(0).find('.widget_contents');
		var wH = $(this).find('.widget_header_title');
		var h = wC.height();

		if (h == 0) {
			wH.addClass("i_16_downT").removeClass("i_16_cHorizontal");
			wC.css('height','auto').removeClass('noPadding');
		}else{
			wH.addClass("i_16_cHorizontal").removeClass("i_16_downT");
			wC.css('height','0').addClass('noPadding');
		}
	})

/* Dialog ============================================ */
	
	$.fx.speeds._default = 400; // Adjust the dialog animation speed

	$(".bDialog").dialog({
		autoOpen: false,
		show: "fadeIn",
		modal: true,
	});

	$(".dConf").dialog({
		autoOpen: false,
		show: "fadeIn",
		modal: true,
		buttons: {
			"Yeah!": function() {
				$( this ).dialog( "close" );
			},
			"Never": function() {
				$( this ).dialog( "close" );
			}
		}
	});

	$(".bdC").live("click", function(){ /* change click to live */
		$(".bDialog").dialog( "open" );
		return false;
	});

	$(".bdcC").live("click", function(){ /* change click to live */
		$(".dConf").dialog( "open" );
		return false;
	});

/* LightBox ========================================== */
	
	$('.lPreview a.lightbox').colorbox({rel:'gal'});

/* Drop Menu ========================================= */
	
	$(".drop_menu").parent().on("click", function(){
		var status = $(this).find(".drop_menu").css("display");
		if (status == "block"){
			$(this).find(".drop_menu").css("display", "none");
		}else{
			$(this).find(".drop_menu").css("display", "block");
		}
	});

	$(".top_tooltip").parent().on("hover", function(){
		var status = $(this).find(".top_tooltip").css("display");
		if (status == "block"){
			$(this).find(".top_tooltip").css("display", "none");
		}else{
			$(this).find(".top_tooltip").css("display", "block");
		}
	});

/* Inline Dialog ===================================== */

	$(".iDialog").on("click", function(){
		$(this).fadeOut("slow").promise().done(function(){
			$(this).parent().remove();
		});
	});
});
