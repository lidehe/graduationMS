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
	$.ajax({
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
     //答辩结果分布统计图 pie_charts_thesis
	if(!!$(".pie_charts_thesis").offset()){
		$.plot($(".pie_charts_thesis"), [{ label: "无答辩资格：2人", data: 2 }, { label: "答辩通过：160人", data: 160 }, { label: "答辩不及格：6人", data: 6 }, { label: "等待答辩：30人", data: 30 }],
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
	
	//数据表分页功能
	$(".datatable_teacher").dataTable({
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
