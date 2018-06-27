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
        	if( $('#canEdit').val() != ""){
        		$('#save').hide();
        		$('#del').hide();
        	}else if($('#factoryCostsid').val() == 0 ){
        		$('#del').hide();
        	}else{}
        	
        	hasReminderChanged();
        	hasModeChanged();
        	showDiv();
        });
        
        function hasReminderChanged(){
        	if($("#hasReminder").attr("checked")==true){
        		$("#reminder").removeAttr("disabled");
        	}else{
        		$("#reminder").attr("disabled","disabled");
        	}
        }
        
        function hasModeChanged(){
        	if($("#hasMode").attr("checked")==true){
        		$("#cycleset").removeAttr("disabled");
        		$("#dayof").removeAttr("disabled");
        		$("#repeatNum").removeAttr("disabled");
        		$("#factoryCostsvalue").removeAttr("disabled");
        		$("#repeatStart").removeAttr("disabled");
        		$("#img_repeatStart").removeAttr("disabled");
        		$("#repeatEnd").removeAttr("disabled");
        		$("#img_repeatEnd").removeAttr("disabled");
        	}else{
        		$("#cycleset").attr("disabled","disabled");
        		$("#dayof").attr("disabled","disabled");
        		$("#repeatNum").attr("disabled","disabled");
        		$("#factoryCostsvalue").attr("disabled","disabled");
        		$("#repeatStart").attr("disabled","disabled");
        		$("#img_repeatStart").attr("disabled","disabled");
        		$("#repeatEnd").attr("disabled","disabled");
        		$("#img_repeatEnd").attr("disabled","disabled");
        	}
        }
        
        function showDiv(){
    		if($("#radioday").attr("checked")==true){
        		$('#div_day').show();
        		$('#div_week').hide();
        		$('#div_month').hide();
        	}else if($("#radioweek").attr("checked")==true){
        		$('#div_day').hide();
        		$('#div_week').show();
        		$('#div_month').hide();
        		var arrwookof = $("#weekOf").val().split(",");
        		for(var i=0;i<arrwookof.length;i++){
        			$("#"+arrwookof[i]).attr("checked","checked");
        		}
        	}else if($("#radiomonth").attr("checked")==true){
        		$('#div_day').hide();
        		$('#div_week').hide();
        		$('#div_month').show();
        	}else{
        		$('#div_day').show();
        		$('#div_week').hide();
        		$('#div_month').hide();
        	}
        }
        
        function isPrice(str){
        	var RegExpPtn = /^\d+(\.\d{2})?$/;
        	if(!RegExpPtn.test(str)){
        		return false;
        	}
        	return true;
        }
        
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
        	if(d1>=d2){
        		return false;
        	}else{
        		return true;
        	}
        }
        
        function compareDate(d1,d2){
        	var date1 = new Date(d1.replace("-","/"));
        	var date2 = new Date(d2.replace("-","/"));
        	if(date1>=date2){
        		return false;
        	}else{
        		return true;
        	}
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
        	
        	var costs2 = $("#costs2").val();
        	if(isEmpty(costs2)){
        		alert("请输入会员价！");
        		return false;
        	}else{
        		if(isPrice(costs2) == false){
        			alert("请输入正确的会员价！");
        			return false;
        		}
        	}
        	var costs1 = $("#costs1").val();
        	if(isEmpty(costs1)){
        		alert("请输入非会员价！");
        		return false;
        	}else{
        		if(isPrice(costs1) == false){
        			alert("请输入正确的非会员价！");
        			return false;
        		}
        	}
        	
        	if($("#hasMode").attr("checked")==true){
        		if($("#radioday").attr("checked")==true){
        			var dayof = $("#dayof").val(wookof);
        			if(isEmpty(dayof)){
    					flag = false;
    				}
        			else if(isNature(dayof)==false){
    					flag = false;
    				}
        		}else if($("#radioweek").attr("checked")==true){
        			var wookof = "";
        			for(var i=0;i<7;i++){
        				if($("#"+i).attr("checked")==true){
            				wookof += $("#"+i).val()+",";
            			}
        			}
        			if(isEmpty(wookof)){
        				flag = false;
        			}else{
        				$("#weekOf").val(wookof);
        			}
        		}else if($("#radiomonth").attr("checked")==true){
        			if($("#radioone").attr("checked")==true){
        				var fcvalue = $("#factoryCostsvalue").val();
        				if(isEmpty(fcvalue)){
        					flag = false;
        				}
        				else if(isNature(fcvalue)==false){
        					flag = false;
        				}
        				
        			}else if($("#radiomany").attr("checked")==true){
        				// do nothing
        			}else{
        				flag = false;
        			}
        		}else{
        			flag = false;
        		}
        		if(flag == false){
        			alert("请正确的完善周期设置!");
        			return false;
        		}
        		var repeatStart = $("#repeatStart").val();
       			if(isEmpty(repeatStart)){
               		alert("请输入开始日期！");
               		return false;
               	}

       			if($("#rad_repartnum").attr("checked")==true){
       				var repeatNum = $("#repeatNum").val();
       				if(isEmpty(repeatNum)){
                   		alert("请输入重复次数！");
                   		return false;
                   	}
       				if(isNature(repeatNum)==false){
       					alert("请输入正确的生产次数！");
    					return false;
    				}
       				$("#repeatEnd").val("");
       			}else if($("#rad_repartday").attr("checked")==true){
       				var repeatEnd = $("#repeatEnd").val();
       				if(isEmpty($("#repeatEnd").val())){
                   		alert("请输入结束日期！");
                   		return false;
                   	}
       				if(compareDate(repeatStart,repeatEnd)== false){
       					alert("结束日期应大于开始日期！");
       					return false;
       				}
       				
       				
       				$("#repeatNum").val("");
       			}else{
       				alert("请正确的完善周期设置!");
        			return false;
       			}
        	}
        	return true;
        }
        
        function saveCondition(){
        	if(validate()==false){
        		return false;
        	}
        	$.ajax({type:'post',url:'factoryorder!saveAndUpdateCondition.asp', data:  $('#form').serialize(), 
    			success:function(msg){
    				var rtnmsg = eval('('+msg+')');
    				if(rtnmsg.success == "ok"){
    					alert('您的设置已经成功！');
    				}else if(rtnmsg.success == false){
    					alert(rtnmsg.desc);
    				}else{
    					alert('未知错误，请联系系统设计人员。可能的原因为：' + rtnmsg.message);
    				}
    				CloseModelWindow(null,true);
    			}
    		});
        }
        
        function delCondition(){
        	if (confirm('您是否确认删除当前预约设置？')) {
        		$.ajax({type:'post',url:'factoryorder!deleteCondition.asp', data:  $('#form').serialize(), 
        			success:function(msg){
        				var rtnmsg = eval('('+msg+')');
        				if(rtnmsg.success == "ok"){
        					alert('您的设置已经成功删除！');
        				}else{
        					alert('未知错误，请联系系统设计人员。可能的原因为：' + rtnmsg.message);
        				}
        				CloseModelWindow(null,true);
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
	<input type="hidden" id="weekOf" name="factoryCosts.weekOf" value="${factoryCosts.weekOf}" />
	<div>
		<table id="main">
			<tr>
				<td><span>开始时间</span></td>
				<td><input id="starttime" name="factoryCosts.starttime" value="${factoryCosts.starttime}"/></td>
				<td><span>结束时间</span></td>
				<td><input id="endtime" name="factoryCosts.endtime" value="${factoryCosts.endtime}" /></td>
			</tr>
			<tr>
				<td><span>收费标准</span></td>
				<td colspan="3">
					<table>
						<tr>
							<td><span>会员价：</span></td><td><input id="costs2" name="factoryCosts.costs2" value="${factoryCosts.costs2}" /></td>
							<td><span>非会员价：</span></td><td><input id="costs1" name="factoryCosts.costs1" value="${factoryCosts.costs1}" /></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td><span>是否提醒</span></td>
				<td colspan="3">
					<input type="checkbox" id="hasReminder" name="factoryCosts.hasReminder" value="1" onchange="hasReminderChanged();" <s:if test="factoryCosts.hasReminder ==1">checked</s:if>  />
					<span>提前</span>
						<select id="reminder" name="factoryCosts.reminder">
							<option value="4" <s:if test="factoryCosts.reminder ==4">selected</s:if>>4小时</option>
							<option value="8" <s:if test="factoryCosts.reminder ==8">selected</s:if>>8小时</option>
							<option value="12" <s:if test="factoryCosts.reminder ==12">selected</s:if>>12小时</option>
							<option value="24" <s:if test="factoryCosts.reminder ==24">selected</s:if>>24小时</option>
						</select>
					<span>小时提醒</span>
				</td>
			</tr>
			<tr>
				<td><span>周期设置</span></td>
				<td colspan="3">
					<input type="checkbox" id="hasMode" name="factoryCosts.hasMode" value="1" onchange="hasModeChanged();" <s:if test="factoryCosts.hasMode ==1">checked</s:if> />
				</td>
			</tr>
		</table>
		<div>
		<fieldset id="cycleset">
			<legend id="cyclemode"><span>周期模式</span></legend>
			<table>
				<tr>
					<td valign="top">周期条件：</td>
					<td>
						<table>
							<tr>
								<td><input class="css_radio" type="radio" name="factoryCosts.mode" id="radioday" value="1" onclick="showDiv();" <s:if test="factoryCosts.mode ==1">checked</s:if>/>按日</td>
								<td rowspan="3" valign="top">
									<div id="div_day" >
										<table>
											<tr>
												<td>
													每<input id="dayof" name="factoryCosts.dayof" value="${factoryCosts.dayof }" />天
												</td>
											</tr>
										</table>
									</div>
									<div id="div_week">
										<table>
												<tr>
													<td>
														
														<input type="checkbox" id="2" name="one" value="2" /><span>星期一</span>
														<input type="checkbox" id="3" name="two" value="3" /><span>星期二</span>
														<input type="checkbox" id="4" name="three" value="4" /><span>星期三</span>
													</td>
												</tr>
												<tr>
													<td>	
														<input type="checkbox" id="5" name="four" value="5" /><span>星期四</span>
														<input type="checkbox" id="6" name="five" value="6" /><span>星期五</span>
														<input type="checkbox" id="7" name="six" value="7" /><span>星期六</span>
													</td>
												</tr>
												<tr>
													<td>
														<input type="checkbox" id="1" name="seven" value="1" /><span>星期日</span>
													</td>
												</tr>
										</table>
									</div>
									<div id="div_month" >
										<table>
											<tr>
												<td>
													<input class="css_radio" type="radio" name="factoryCosts.type" id="radioone" value="0" <s:if test="factoryCosts.type ==0">checked</s:if>  />每个月的第<input id="factoryCostsvalue" name="factoryCosts.value" value="${factoryCosts.value}" />天
												</td>
											</tr>
											<tr>
												<td>
													<input  class="css_radio" type="radio" name="factoryCosts.type" id="radiomany" value="1" <s:if test="factoryCosts.type ==1">checked</s:if> />每个月的第
													<select id="repeat1" name="factoryCosts.cycle1">
														<option value="1" <s:if test="factoryCosts.cycle1 ==1">selected</s:if>>第一个</option>
														<option value="2" <s:if test="factoryCosts.cycle1 ==2">selected</s:if>>第二个</option>
														<option value="3" <s:if test="factoryCosts.cycle1 ==3">selected</s:if>>第三个</option>
														<option value="4" <s:if test="factoryCosts.cycle1 ==4">selected</s:if>>第四个</option>
													</select>
													<select id="repeat2" name="factoryCosts.cycle2">
														<option value="2" <s:if test="factoryCosts.cycle2 ==2">selected</s:if>>星期一</option>
														<option value="3" <s:if test="factoryCosts.cycle2 ==3">selected</s:if>>星期二</option>
														<option value="4" <s:if test="factoryCosts.cycle2 ==4">selected</s:if>>星期三</option>
														<option value="5" <s:if test="factoryCosts.cycle2 ==5">selected</s:if>>星期四</option>
														<option value="6" <s:if test="factoryCosts.cycle2 ==6">selected</s:if>>星期五</option>
														<option value="7" <s:if test="factoryCosts.cycle2 ==7">selected</s:if>>星期六</option>
														<option value="1" <s:if test="factoryCosts.cycle2 ==1">selected</s:if>>星期日</option>
													</select>
												</td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
							<tr>
								<td><input class="css_radio" type="radio" name="factoryCosts.mode" id="radioweek" value="2" onclick="showDiv();" <s:if test="factoryCosts.mode ==2">checked</s:if> />按周</td>
							</tr>
							<tr>
								<td><input class="css_radio" type="radio" name="factoryCosts.mode" id="radiomonth" value="3" onclick="showDiv();" <s:if test="factoryCosts.mode ==3">checked</s:if> />按月</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td valign="top">重复范围：</td>
					<td>
						<table>
							<tr><td>开始日期：<input type="text" name="factoryCosts.repeatStart" id="repeatStart" value="${factoryCosts.repeatStart}" readonly="readonly" style="width:100px;" /> <img id="img_repeatStart" onclick="WdatePicker({el:'repeatStart'})" src="script/DatePicker/skin/datePicker.gif" align="absmiddle" width="16" height="22" style="cursor:pointer;"></td></tr>
							<tr><td><input class="css_radio" type="radio" name="factoryCosts.repeatWhere" id="rad_repartnum" value="1" <s:if test="factoryCosts.repeatWhere ==1">checked</s:if> />重复<input id="repeatNum" name="factoryCosts.repeatNum" value="${factoryCosts.repeatNum}" />次后结束</td></tr>
							<tr><td><input class="css_radio" type="radio" name="factoryCosts.repeatWhere" id="rad_repartday" value="2" <s:if test="factoryCosts.repeatWhere ==2">checked</s:if> />结束日期：<input type="text" name="factoryCosts.repeatEnd" id="repeatEnd" value="${factoryCosts.repeatEnd }" readonly="readonly" style="width:100px;" /> <img id="img_repeatEnd" onclick="WdatePicker({el:'repeatEnd'})" src="script/DatePicker/skin/datePicker.gif" align="absmiddle" width="16" height="22" style="cursor:pointer;"></td></tr>
						</table>
					</td>
				</tr>
			</table>
		</fieldset>
		</div>
	</div>
	</s:form>
	<table width="100%"><tr><td align="center">
	<button id="save" onclick="saveCondition()">确定</button>
	<button id="del" onclick="delCondition()">删除</button>
	<button id="cancel" onclick="cancel()">取消</button>
	</td></tr></table>
</body>
</html>