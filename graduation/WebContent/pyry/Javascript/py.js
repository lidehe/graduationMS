/**
 * 
 */
$(function(){
	
});
function pyDetail(eleth){
	var $th = $(eleth);
	var number = $th.parent().find(".hiddenVal").val();
	window.open("pyrz.do?meth=detail&number="+number);
}

function pyDetail1(eleth){
	var $th = $(eleth);
	var number = $th.parent().find(".hiddenVal").val();
	window.open("ypyrz.do?meth=detail&number="+number);
}

function pyzDetail(eleth){
	var $th = $(eleth);
	var number = $th.parent().find(".hiddenVal").val();
	window.open("pyrz.do?meth=detailz&zuhao="+number);
}

function pyzDetail1(eleth){
	var $th = $(eleth);
	var number = $th.parent().find(".hiddenVal").val();
	window.open("ypyrz.do?meth=detailz&zuhao="+number);
}