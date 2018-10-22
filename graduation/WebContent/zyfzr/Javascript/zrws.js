/**
 * 
 */
$(function(){
	$(".viewB").click(function(){
		var id = $(this).parent().find(".rwsId").val();
		window.open("zrws.do?meth=preview&id="+id);
	});
	
	$(".loadB").click(function(){
		var id = $(this).parent().find(".rwsId").val();
		window.open("zrws.do?meth=load&id="+id);
	});
});