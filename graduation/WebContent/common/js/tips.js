/**
 * 
 */
function showMsg(text, position, fun) {
	var show = $('.show_msg1').length
	if (show > 0) {

	} else {
		var div = $('<div></div>');
		div.addClass('show_msg1');
		var span = $('<span style="display:inline-block;max-width:650px;"></span>');
		span.addClass('show_span1');
		span.appendTo(div);
		$(".show_span1").html(text);
		$('body').append(div);
	}
	$(".show_span1").html(text);
	$('.show_msg1').hide();
	if (position == 'bottom') {
		$(".show_msg1").css('bottom', '5%');
	} else if (position == 'center') {
		$(".show_msg1").css('top', '');
		$(".show_msg1").css('bottom', '50%');
	} else {
		$(".show_msg1").css('bottom', '95%');
	}
	$('.show_msg1').fadeIn(500);
	$('.show_msg1').fadeOut(4000);
	if (fun)
		setTimeout(fun, "4000");
}

$(function(){
	$("span.filename").text("请选择文件");
})