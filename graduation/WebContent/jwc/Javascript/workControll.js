
/**
 * 验证初始化功能是否可用
 * 一旦毕业工作正式开始，就不允许访问初始化页面
 * 那么什么时候可以在此访问初始化页面呢？？？因为最终还是要初始化的
 */
function  canVisitInitial(){
	var result=true;
	$.ajax({
    	url:"../../WorkControll.do",
    	type:"post",
    	async:false,//设置为同步查询，免得结果还没到页面就跳转了
	    data:{
     		"method":"canCheckPaper",
    	},
      	success:function(data){
		if(data=="00"){
			result= false;
			smartaAlert("");
		}
	 }
   });
	return result;
}


/**
 * 验证导入信息能否使用
 * 一旦毕业工作正式开始，就不允许访导入信息页面
 * ，但是真的开启之后就不许了吗？可能会再导入一些教师信息？？？
 */
function  canVisitImportInfor(){
	var result=true;
	$.ajax({
    	url:"../../WorkControll.do",
    	type:"post",
    	async:false,//设置为同步查询，免得结果还没到页面就跳转了
	    data:{
     		"method":"canCheckPaper",
    	},
      	success:function(data){
		if(data=="00"){
			result= false;
			smartaAlert("仍有部分学生未提交最终论文，抽检结果可能不准确，建议所有学生都提交后进行抽检");
		}
	 }
   });
	return result;
}



/**
 * 验证毕业方式能否修改
 * 一旦毕业工作正式开始，就不允许访问毕设方式修改页面
 * ，但是真的开启之后就不许修改了吗？有争议
 */
function  canVisitByfs(){
	var result=true;
	$.ajax({
    	url:"../../WorkControll.do",
    	type:"post",
    	async:false,//设置为同步查询，免得结果还没到页面就跳转了
	    data:{
     		"method":"canCheckPaper",
    	},
      	success:function(data){
		if(data=="00"){
			result= false;
			alert("所有学生提交最终论文后才能开始抽检工作");
		}
	 }
   });
	return result;
}




/**
 * 验证风否开启抽检工作
 * 仅当所有人都提交了最终成果的时候，才允许访问操作页面
 */
function  canPaperChech(){
	var result=true;
	$.ajax({
    	url:"../../WorkControll.do",
    	type:"post",
    	async:false,//设置为同步查询，免得结果还没到页面就跳转了
	    data:{
     		"method":"canCheckPaper",
    	},
      	success:function(data){
		if(data=="00"){
			result= false;
			smartaAlert("仍有部分学生未提交最终论文，抽检结果可能不准确，建议所有学生都提交后进行抽检");
		}
	 }
   });
	return result;
}
