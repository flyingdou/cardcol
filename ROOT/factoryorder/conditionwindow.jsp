<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>    
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">    
    <title>预约条件</title>    
    <link href="css/condition/main.css" rel="stylesheet" type="text/css" />       
    <link href="css/condition/dp.css" rel="stylesheet" />    
    <link href="css/condition/dropdown.css" rel="stylesheet" />    
    <link href="css/condition/colorselect.css" rel="stylesheet" />
    
	<script language="javascript" src="script/DatePicker/WdatePicker.js"></script>   
    <script src="script/checkInput.js" type="text/javascript"></script> 
    <script src="script/condition/jquery.js" type="text/javascript"></script>    
    <script src="script/condition/Plugins/Common.js" type="text/javascript"></script>
    <script src="script/condition/Plugins/jquery.form.js" type="text/javascript"></script>     
    <script src="script/condition/Plugins/jquery.validate.js" type="text/javascript"></script>     
    <script src="script/condition/Plugins/datepicker_lang_CN.js" type="text/javascript"></script>        
    <script src="script/condition/Plugins/jquery.datepicker.js" type="text/javascript"></script>     
    <script src="script/condition/Plugins/jquery.dropdown.js" type="text/javascript"></script>     
    <script src="script/condition/Plugins/jquery.colorselect.js" type="text/javascript"></script>    
     
    <script type="text/javascript">
        $(document).ready(function() {
        	if( $('#color').val() != "9"){
        		$('#save').hide();
        	}
        	getordermoney($("#factoryStartTime").val(),$("#factoryEndTime").val());
        });
        
        function isTime(str)
        {
        	var RegExpPtn=/^[0-1][0-9]:[0-5][0-9]$|^[2][0-4]:[0-5][0-9]$/;
        	if(!RegExpPtn.test(str)){
        		return false;
        	}
        	return true;
        }
        
        function compareTime(t1,t2){
        	var d1 = new Date(Date.parse("1970/01/01 "+t1));
        	var d2 = new Date(Date.parse("1970/01/01 "+t2));
        	if(d1>d2){
        		return false;
        	}else{
        		return true;
        	}
        }
        
        function GetDateDiff(startTime, endTime, diffType) {
			startTime = startTime.replace(/\-/g, "/");
			endTime = endTime.replace(/\-/g, "/");
			
			//将计算间隔类性字符转换为小写
			diffType = diffType.toLowerCase();
			var sTime =new Date(startTime); //开始时间
			var eTime =new Date(endTime); //结束时间
			//作为除数的数字
			var divNum =1;
			switch (diffType) {
				case"second":
				 divNum =1000;
				break;
				case"minute":
				 divNum =1000*60;
				break;
				case"hour":
				 divNum =1000*3600;
				break;
				case"day":
				 divNum =1000*3600*24;
				break;
				default:
				break;
			}
			return parseInt((eTime.getTime() - sTime.getTime()) / parseInt(divNum));
		}
        
        function validate(){
        	var flag = true;
        	var starttime = $('#starttime').val();
        	if(isEmpty(starttime)){
        		alert("请输入开始时间！");
        		return false;
        	}else{
        		if(isTime(starttime) == false){
        			alert("请输入正确的开始时间！");
            		return false;
        		}
        	}
        	var endtime = $('#endtime').val();
        	if(isEmpty(endtime)){
        		alert("请输入结束时间！");
        		return false;
        	}else{
        		if(isTime(endtime) == false){
        			alert("请输入正确的结束时间！");
        			return false;
        		}
        	}
        	
        	if(compareTime(starttime,endtime)==false){
        		alert("结束时间应大于开始时间！");
        		return false;
        	}
        	
        	return true;
        }
        
        function countPrice(){
        	var starttime = $('#starttime').val();
        	var endtime = $('#endtime').val();
        	if(isEmpty(endtime) && isEmpty(starttime)){
        		return false;
        	}
        	if(validate()==false){
        		return false;
        	}
        	if(compareTime($("#factoryStartTime").val(),starttime)==false){
        		alert("开始时间超过了能预定的时间范围！");
        		return false;
        	}
        	if(compareTime(endtime,$("#factoryEndTime").val())==false){
        		alert("开始时间超过了能预定的时间范围！");
        		return false;
        	}
        	getordermoney(starttime,endtime);
        }
        
        function getordermoney(starttime,endtime){
        	var minute = GetDateDiff("1970/01/01 "+starttime,"1970/01/01 "+endtime,"minute");
        	var hour = (minute/60).toFixed(2);
        	var arr_hour = hour.split(".");
        	if(parseInt(arr_hour[1])>50){
        		hour = parseInt(arr_hour[0])+1;
        	}else if(parseInt(arr_hour[1])>0){
        		hour = parseInt(arr_hour[0])+0.5;
        	}
        	$("#orderMoney").val((parseFloat($("#costs").val())*hour).toFixed(2));
        }
        
        function saveCondition(type){
        	if(validate()==false){
        		return false;
        	}
        	if($('#orderMoney').val() > 0){
//         		var url = 'order!submitProd.asp?prodType=4&id='+$('#factoryCostsid').val()
// 				+'&factoryMoney='+$('#orderMoney').val();
//         		$('#factoryStartTime').val($('#starttime').val());
//         		$('#factoryEndTime').val($('#endtime').val());
        		parent.location.href ="order!submitProd.asp?prodType=4&id="+$('#factoryCostsid').val()
				+"&factoryMoney="+$('#orderMoney').val()+"&factoryStartTime="+$('#starttime').val()+"&factoryEndTime="+$('#endtime').val();
        		CloseModelWindow();
        	}else{
	        	$.ajax({type:'post',url:'factoryorderwindow!saveAndUpdateCondition.asp', data:  $('#form').serialize(), 
	    			success:function(msg){
	    				var rtnmsg = eval('('+msg+')');
	    				if(rtnmsg.success == true){
	    					if(rtnmsg.message == "apply"){
	    						alert('您的申请已经成功提交，请等待俱乐部的审核！');
	    						CloseModelWindow(null,true);
	    					}else{
	    						alert('未知错误，请联系系统设计人员。');
	    					}
	    				}else{
	    					if(rtnmsg.message == "exist"){
	    						alert('您的申请场地已经被使用，请重新选择场地或时间！');
	    					}else{
	    						alert('未知错误，请联系系统设计人员。可能的原因为：' + rtnmsg.message);
	    					}
	    				}
	    			}
	    		});
        	}
        }
        
        function cancel(){
        	CloseModelWindow();
        }
    </script>
    
    <style type="text/css" media="screen"> 
	input{ width:50px;}
	
	input[disabled],input:disabled{
	 border:1px solid #DDD;
	 background-color:#F5F5F5;
	 color:#ACA899;
	}

	.css_radio{width:20px;}
	#main{padding-left:20px;}
	#cyclemode{font-weight:bold; color:blue; padding-left:24px; }
	
	</style>
  </head>
<body>
	<s:form id ="form" name="form" method="post" action="" theme="simple">
		<input type="hidden" id="factoryCostsid" name="factoryCosts.id" value="${factoryCosts.id}" />
		<input type="hidden" id="planDate" name="factoryCosts.planDate" value="${factoryCosts.planDate}" />
		<input type="hidden" id="factoryid" name="factoryCosts.factory.id" value="${factory.id}" />
		<input type="hidden" id="canEdit" name="factoryCosts.canEdit" value="${factoryCosts.canEdit}" />
		<input type="hidden" id="factoryStartTime" name="factoryStartTime" value="${start}" />
		<input type="hidden" id="factoryEndTime" name="factoryEndTime" value="${end}" />
		<input type="hidden" id="costs" value="${costs}" />
		<input type="hidden" id="color" value="${color}" />
	
		<div>
			<table id="main">
				<tr>
					<td><span>开始时间</span></td>
					<td><input id="starttime" name="factoryOrder.starttime" value="${start}" onblur="countPrice();return false;"/></td>
					<td><span>结束时间</span></td>
					<td><input id="endtime" name="factoryOrder.endtime" value="${end}" onblur="countPrice();return false;"/></td>
				</tr>
				<tr>
					<td><span>应付费用</span></td>
					<td colspan="3"><input type="text" id="orderMoney" name="factoryOrder.orderMoney" value="" readonly="readonly"  />元</td>
				</tr>
				<tr>
					<td colspan="4"><span>您所订场次售出后不可退改，请务必按时到场。如因您个人原因未能到场或迟到所造成的损失由您个人承担。</span></td>
				</tr>
			</table>
		</div>
	</s:form>
	<table width="100%">
		<tr>
			<td align="center">
				<s:if test="#request.costs>0"><button id="save" onclick="saveCondition()">购买</button></s:if>
				<s:else><button id="save" onclick="saveCondition()">预约</button></s:else>
				<button id="cancel" onclick="cancel()">取消</button>
			</td>
		</tr>
	</table>
</body>
</html>