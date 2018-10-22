

//查看毕业设计详情
function toDetail(th_is){
	$("#tb_waysSteps").empty();
	var $this=$(th_is);
	$("#wayDetailPanel").show();
	//var byfsName=$this.parents("td").prev("td").html();
	var byfsId=$this.parents("tr").children("td").eq(0).children("input").eq(0).val();
	$.ajax({
		url : "../../Byfs.do",
		type : 'POST',
		data : {
			"method":"findById",
			"byfsId":byfsId,
		},
		success : function(data) {
			var list = eval('(' + data + ')');
			var index=0;
			if(list.yi!=""&&list.yi!="空"&&list.yi!="无"){
				index++;
				$tr=$("<tr><td rowspan='4'>模块一</td><td>"+list.yi +"</td></tr>")
				$("#tb_waysSteps").append($tr);
			}
            if(list.er!=""&&list.er!="空"&&list.er!="无"){
            	index++;
            	$tr=$("<tr><td>"+list.er +"</td></tr>")
				$("#tb_waysSteps").append($tr);
			}
            if(list.san!=""&&list.san!="空"&&list.san!="无"){
            	index++;
            	$tr=$("<tr><td>"+ list.san+"</td></tr>")
				$("#tb_waysSteps").append($tr);
            }
            if(list.si!=""&&list.si!="空"&&list.si!="无"){
            	index++;
            	 $tr=$("<tr><td>"+list.si +"</td></tr>")
				$("#tb_waysSteps").append($tr);
                }
            if(list.wu!=""&&list.wu!="空"&&list.wu!="无"){
            	index++;
            	 $tr=$("<tr><td rowspan='3'>模块二</td><td>"+list.wu +"</td></tr>")
				$("#tb_waysSteps").append($tr);
                }
            if(list.liu!=""&&list.liu!="空"&&list.liu!="无"){
            	index++;
                $tr=$("<tr> <td>"+list.liu +"</td></tr>")
				$("#tb_waysSteps").append($tr);
                }
            if(list.qi!=""&&list.qi!="空"&&list.qi!="无"){
            	index++;
            	 $tr=$("<tr> <td>"+ list.qi+"</td></tr>")
				$("#tb_waysSteps").append($tr);
                }
            if(list.ba!=""&&list.ba!="空"&&list.ba!="无"){
            	index++;
            	 $tr=$("<tr><td rowspan='3'>模块三</td><td>"+ list.ba+"</td></tr>")
				$("#tb_waysSteps").append($tr);
                }
            if(list.jiu!=""&&list.jiu!="空"&&list.jiu!="无"){
            	index++;
            	 $tr=$("<tr> <td>"+list.jiu +"</td></tr>")
				$("#tb_waysSteps").append($tr);
                }
            if(list.shi!=""&&list.shi!="空"&&list.shi!="无"){
            	index++;
            	 $tr=$("<tr> <td>"+list.shi +"</td></tr>")
				$("#tb_waysSteps").append($tr);
                }
            if(list.shiyi!=""&&list.shiyi!="空"&&list.shiyi!="无"){
            	index++;
                $tr=$("<tr><td rowspan='3'>模块四</td><td>"+ list.shiyi+"</td></tr>")
				$("#tb_waysSteps").append($tr);
                }
            if(list.shier!=""&&list.shier!="空"&&list.shier!="无"){
            	index++;
            	 $tr=$("<tr> <td>"+list.shier +"</td></tr>")
				$("#tb_waysSteps").append($tr);
                }
            if(list.shisan!=""&&list.shisan!="空"&&list.shisan!="无"){
            	index++;
            	 $tr=$("<tr> <td>"+list.shisan +"</td></tr>")
				$("#tb_waysSteps").append($tr);
                }
            if(list.shisi!=""&&list.shisi!="空"&&list.shisi!="无"){
            	index++;
            	 $tr=$("<tr><td rowspan='1'>模块五</td><td>"+list.shisi +"</td></tr>")
				$("#tb_waysSteps").append($tr);
                }
		},
		error : function(responseStr) {
			console.log("error");
		}
	});
	
}
/**
 * 删除毕业方式。后台检查时候有学生使用了该毕业方式，如果有，则不允许删除
 * @param th_is
 */
function deleteByfs(th_is){
	$this=$(th_is);
	var byfsId=$this.parents("tr").children("td").eq(0).children("input").eq(0).val();
	$.ajax({
		url : "../../Byfs.do",
		type : 'POST',
		data : {
			"method":"deleteByfs",
			"byfsId":byfsId,
		},
		success : function(data) {
			if(data=="00"){
				smartaAlert("该毕业方式已经被选用，无法删除");
			}else if(data=="11"){
				window.location.reload()
			}else if(data=="01"){
				smartaAlert("该毕业方式为基础模板，无法删除");
			}
		}
	});
}


$(function() {
	$(document).ready(function() {
//alert("毕业方式页面哟");
		
//毕设详情显示一开始要隐藏
$("#wayDetailPanel").hide();
$("#closeWayDetailPanel").click(function(){
	$("#wayDetailPanel").hide();
})


//请求已有毕业方式信息**************************************************************************************************
		$.ajax({
			url : "../../Byfs.do",
			type : 'POST',
			data : {
				"method":"findAll"
			},
			success : function(data) {
				var list = eval('(' + data + ')');
				$.each(list,function(index,obj) {
					$tr=$("<tr></tr>")
					$td=$("<td><input type='hidden' value='"+obj.id+"'/>"+obj.param1+"</td><td><button class='simple_buttons' onclick='toDetail(this)'>查看详情</button></td><td><button class='simple_buttons' onclick='deleteByfs(this)'>删除</button></td>");
					$tr.append($td);
					$("#tb_byfs").append($tr);
						});
			},
			error : function(responseStr) {
				console.log("error");
			}
		});
		
		
		
		
//加载毕业方式模板，方便用户参考填写**********************************************************************************
//先隐藏第2、3、4、5步的内容,同时隐藏新建毕业方式按钮
		$(".byfsStep2").hide();
		$(".byfsStep3").hide();
		$(".byfsStep4").hide();
		$(".byfsStep5").hide();
		$(".addByfs").hide();
//加载标准模板
		$.ajax({
			url : "../../Byfs.do",
			type : 'POST',
			data : {
				"method":"findOne",
				"byfsId":"1",
			},
			success : function(data) {
				if(data=="00"){
					smartaAlert("加载标准模板失败！")
				}else{
					var list = eval('(' + data + ')');
					//input的class属性中，in表示输入框，nessN表示第N个必须输入的模块，unnessN表示第N个不必输入的模块
						$tr1=$("<tr><td rowspan='4'><span style='color:red;font-size:16pt'>*</span>申报课题：</td><td>"+list[0].yi+"</td><td><textarea class='in ness1' wrap='wrap'></textarea></td></tr>")
						$tr2=$("<tr><td>"+list[0].er+"</td><td><textarea class='in ness1'  wrap='wrap'></textarea></td></tr>")
						$tr3=$("<tr><td>"+list[0].san+"</td><td><textarea class='in ness1' wrap='wrap'></textarea></td></tr>")
						$tr4=$("<tr><td>"+list[0].si+"</td><td><textarea class='in ness1' wrap='wrap'></textarea></td></tr>")
						$tr5=$("<tr class=''><td  rowspan='3'>开题：</td><td>"+list[0].wu+"</td><td><textarea class='in unness1'  wrap='wrap'></textarea></td></tr>")
						$tr6=$("<tr><td>"+list[0].liu+"</td><td><textarea class='in unness1'  wrap='wrap'></textarea></td></tr>")
						$tr7=$("<tr><td>"+list[0].qi+"</td><td><textarea class='in unness1'  wrap='wrap'></textarea></td></tr>")
					    $tr8=$("<tr><td rowspan='3'>阶段成果：</td><td>"+list[0].ba+"</td><td><textarea class='in unness2'  wrap='wrap'></textarea></td></tr>")
					    $tr9=$("<tr><td>"+list[0].jiu+"</td><td><textarea class='in unness2' wrap='wrap'></textarea></td></tr>")
					    $tr10=$("<tr><td>"+list[0].shi+"</td><td><textarea class='in unness2' wrap='wrap'></textarea></td></tr>")
					    $tr11=$("<tr><td rowspan='3'>初稿：</td><td>"+list[0].shiyi+"</td><td><textarea class='in unness3' wrap='wrap'></textarea></td></tr>")
					    $tr12=$("<tr><td>"+list[0].shier+"</td><td><textarea class='in unness3'  wrap='wrap'></textarea></td></tr>")
					    $tr13=$("<tr><td>"+list[0].shisan+"</td><td><textarea class='in unness3'  wrap='wrap'></textarea></td></tr>")
					    $tr14=$("<tr><td><span style='color:red;font-size:16pt'>*</span>最终</td><td>"+list[0].shisi+"</td><td><textarea class='in ness2' wrap='wrap'></textarea></td></tr>")
						
						$("#template_byfs1").append($tr1);
						$("#template_byfs1").append($tr2);
						$("#template_byfs1").append($tr3);
						$("#template_byfs1").append($tr4);
						$("#template_byfs2").append($tr5);
						$("#template_byfs2").append($tr6);
						$("#template_byfs2").append($tr7);
						$("#template_byfs3").append($tr8);
						$("#template_byfs3").append($tr9);
						$("#template_byfs3").append($tr10);
						$("#template_byfs4").append($tr11);
						$("#template_byfs4").append($tr12);
						$("#template_byfs4").append($tr13);
						$("#template_byfs5").append($tr14);
				}
				
			}
		});
//点击上一步和下一步***********************************************************************
		var i=1;
		//上一步
		$(".byfsStepPre").click(function(){
			$(".addByfs").hide();
			if(i==1){
				smartaAlert("已经是第一步了！");
			}else{
				i-=1;
				$(".byfsStepNow").text(i);
			}
			switch(i){
			case 1:
				$(".byfsStep1").show();
				$(".byfsStep2").hide();
				break;
            case 2:
            	$(".byfsStep2").show();
				$(".byfsStep3").hide();
				break;
		    case 3:
		    	$(".byfsStep3").show();
				$(".byfsStep4").hide();
	           break;
            case 4:
            	$(".byfsStep4").show();
				$(".byfsStep5").hide();
	           break;
			}
		});
		//下一步
		$(".byfsStepNext").click(function(){
			$("#template_byfs2").show();
			if(i==4){
				$(".addByfs").show();
			}
			if(i==5){
				smartaAlert("已经是最后一步了！");
			}else{
				i+=1;
				$(".byfsStepNow").text(i);
			}
			switch(i){
            case 2:
            	$(".byfsStep2").show();
				$(".byfsStep1").hide();
				break;
		    case 3:
		    	$(".byfsStep3").show();
				$(".byfsStep2").hide();
	           break;
            case 4:
            	$(".byfsStep4").show();
				$(".byfsStep3").hide();
	           break;
            case 5:
				$(".byfsStep5").show();
				$(".byfsStep4").hide();
				break;
			}
		});
		
		
//添加毕业方式*****************************************************************************************************
		///提交用户填写的数据的时候，可以通过非空判断，获取它的序号和值，分别存储在两个数组里，传到后台。
		//后台根据序号（要注意序号和数据库字段相差2个字段）判断出初始化哪个字段的值
		//至于字段是否按照模块填写要求（标*的全填，不标*的模块中的阶段要么全填，要么全不填，可在前段验证，也可在后台验证
		$("#add_byfs").click(function(){
			//alert("开始新建咯");
			
			var isRight=true;//用于控制是否能够提交数据到后台
			var nullValue=0;
			var hasValue=0;
			//检验模块一是否正确填写**************************************
			$(".ness1").each(function(){
				if($(this).val()==""){
			    nullValue++;
				}
			});
			if(nullValue!=0){
				smartaAlert("模块一为必选，其中的所有阶段都必须填写");
				isRight=false;
				return ;
			}else{
				if($("#block11").val()==""||$("#block12").val()==""){
					smartaAlert("请选择模块1的起始和结束时间");
					return ;
				}
			}
			
			//检验模块二是否正确填写**************************************
		    nullValue=0;
			hasValue=0;
			$(".unness1").each(function(){
				if($(this).val()==""){
					nullValue++;
				}else{
					hasValue++;
				}
			});
			if(!(nullValue==3||hasValue==3)){
				smartaAlert("模块二中的阶段必须为全填或全不填");
				isRight=false;
				return;
			}else{
				if(hasValue==3&&($("#block21").val()==""||$("#block22").val()=="")){
					smartaAlert("请选择模块2的起始和结束时间");
					return ;
				}
			}
			//检验模块三是否正确填写**************************************
			 nullValue=0;
			 hasValue=0;
			$(".unness2").each(function(){
				if($(this).val()==""){
					nullValue++;
				}else{
					hasValue++;
				}
			});
			if(!(nullValue==3||hasValue==3)){
				smartaAlert("模块三中的阶段必须为全填或全不填");
				isRight=false;
				return;
			}else{
				if(hasValue==3&&($("#block31").val()==""||$("#block132").val()=="")){
					smartaAlert("请选择模块3的起始和结束时间");
					return ;
				}
			}
			//检验模块四是否正确填写**************************************
			 nullValue=0;
			 hasValue=0;
			$(".unness3").each(function(){
				if($(this).val()==""){
					nullValue++;
				}else{
					hasValue++;
				}
			});
			if(!(nullValue==3||hasValue==3)){
				smartaAlert("模块四中的阶段必须为全填或全不填");
				isRight=false;
				return;
			}else{
				if(hasValue==3&&($("#block41").val()==""||$("#block42").val()=="")){
					smartaAlert("请选择模块4的起始和结束时间");
					return ;
				}
			}
			//检验模块五是否正确填写**************************************
			$(".ness2").each(function(){
				if($(this).val()==""){
					smartaAlert("模块五为必选，其中的所有阶段都必须填写");
					isRight=false;
					return;
				}else{
					if($("#block51").val()==""||$("#block52").val()==""){
						smartaAlert("请选择模块5的起始和结束时间");
						return ;
					}
				}
			});
			
			//判断是否输入了新方式的名字**************************************
			var istitle=$("#byfs_name").val()=="";
			if(istitle){
				smartaAlert("请输入新增的毕设方式的名称!");
				isRight=false;
			}
			//最终提交数据到后台的代码****************************************
			if(isRight){
			   var i=0;
			   var j=0;
			   var keys=[];//字段号，与数据库相差2
			   var vals=[];//字段值
			   //获取取值不为空的输入框
			   $(".in").each(function(){
				   if($(this).val()!=""){
					   keys[j]=i;   
					   vals[j]=$(this).val();
					   j++;
					   }
				   i++;
		    	});
			   var dates=[];
			   i=0;
			   //获取日期设置
			   $("input:[type='date']").each(function(){
					  dates[i]=$(this).val(); 
					  i++;
				   });
		    	//提交数据
		    	$.ajax({
			    	url:"../../Byfs.do",
			    	type:"post",
				    data:{
			     		"method":"add",
				 	     "byfs_name":$("#byfs_name").val(),
				    	"keys":keys,
				    	"vals":vals,
				    	"dates":dates,
			    	},
			        dataType:"json",
			        traditional: true,
			      	success:function(data){
			      		smartaAlert("您已成功添加新的毕业方式")
				 }
			   });
		    }
		});

	});
});
