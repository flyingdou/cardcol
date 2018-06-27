<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="健身E卡通-我的设定" />
<meta name="description" content="健身E卡通-我的设定" />
<title>健身E卡通-我的设定</title>
<link rel="stylesheet" type="text/css" href="css/user-config.css" />
<script type="text/javascript">
function nextStep(){
	$('#stateform').attr('action','frequency.asp');
	$('#stateform').submit();
}
function prevStep(){
	$('#stateform').attr('action','target.asp');
	$('#stateform').submit();
}
</script>
</head>
<body>
	<s:include value="/share/header.jsp" />
	<div id="content">
		<div class="my-setting">
			<ul id="nextstep">
			   <li class="first">一.设定健身目标</li>
			   <li class="last" >二.身体与健身状况</li>
			   <li>三.设定健身频率</li>
			   <li>四.设定靶心率</li>
			   <li>五.设定有氧项目</li>
			   <li>六.完成</li>
			</ul>
		</div>
		<div id="center-1">
			<s:form id="stateform" name="stateform" method="post" theme="simple">
			<s:hidden name="autoGen" id="autoGen"/>
			<s:hidden name="setting.id"/>
			<s:hidden name="setting.target"/>
			<s:hidden name="setting.member"/>
			<s:hidden name="setting.strengthDate"/>
			<s:hidden name="setting.strengthDuration"/>
			<s:hidden name="setting.cardioDate"/>
			<s:hidden name="setting.cardioDuration"/>
			<s:hidden name="setting.favoriateCardio"/>
			<s:hidden name="setting.bmiMode"/>
			<s:hidden name="setting.bmiLow"/>
			<s:hidden name="setting.bmiHigh"/>
			<s:hidden name="setting.intensityMode"/>
			<!--<h2 class="context_title"></h2>-->
	    	<div class="configall">	
				<h4>1、目前的身体状况</h4>
				<div class="goalcontent">
					<span style="padding-left:30px;">身高：<s:textfield name="setting.height" id="height" cssClass="dinput" /><span style="padding-left:2px;">cm</span></span>　　
					<span>体重：<s:textfield name="setting.weight" id="weight" cssClass="dinput"/><span style="padding-left:2px;">kg</span></span>　　
					<span>静心率：<s:textfield name="setting.heart" id="heart" cssClass="dinput"/><span style="padding-left:2px;">次/分钟</span></span>
				</div>
				<h4>2、目前的健康状况</h4>
					<div>
						<s:checkboxlist value="#request.list" list="#{'1':'是否存在心脏疾病，只能做医生指定的运动？','2':'运动或不运动情况下是否有胸痛感觉？','3':'运动或不运动情况下是否有背痛感觉？','4':'是否曾因晕眩而失去平衡或意识？','5':'是否有骨骼或关节疾病？','6':'是否有高血压？','7':'是否知道自己有任何不适合运动的原因'}" listKey="key" theme="ethan" listValue="value" name="setting.diseaseReport" id="diseaseReport"/>
					</div>
				<h4>3、目前的健身状况</h4>
				<div class="goalsetting">
					<s:radio list="#{'A':'从来没有进行过健身运动','B':'参加健身运动少于6个月','C':'参加健身运动少于12个月','D':'已经很规律地进行了12个月以上的健身'}" listKey="key" listValue="value" id="status" value="%{setting.currGymStatus}" name="setting.currGymStatus"/>
				</div>
			</div>
			</s:form>
		</div>
		<div class="stepoperate">
			<a href="javascript:prevStep()" title="上一步" class="butlost">上一步</a>
			<a href="javascript:nextStep()" title="下一步" class="butnext">下一步</a>
		</div>
	</div>
	<s:include value="/share/footer.jsp" />
	<table id="sample1" style="display:none;" class="conftable">
		<tbody id="sample">
			<tr class="bcZZZZ">
				<td rowspan="2">
					<input type="checkbox" name="ids" value=""  class="checkbox"/>
					<input type="hidden" name="targets[XXXX].id" value="" />
					<input type="hidden" name="targets[XXXX].member" value="<s:property value="email"/>" />
				</td>
				<td rowspan="2" nowrap><input type="text" name="targets[XXXX].targetDate" value="" class="input" id="startDateXXXX"/></td>
				<td nowrap>目标</td>
				<td><input type="text" name="targets[XXXX].weight" value="" class="input2"/></td>
				<td><input type="text" name="targets[XXXX].chest" value="" class="input2"/></td>
				<td><input type="text" name="targets[XXXX].waist" value="" class="input2"/></td>
				<td><input type="text" name="targets[XXXX].hip" value="" class="input2"/></td>
				<td><input type="text" name="targets[XXXX].upperArm" value="" class="input2"/></td>
				<td><input type="text" name="targets[XXXX].thigh" value="" class="input2"/></td>
				<td><input type="text" name="targets[XXXX].fat" value="" class="input2"/></td>
		
				<td><input type="text" name="targets[XXXX].heart" value="" class="input2"/></td>
				<td><input type="text" name="targets[XXXX].bmi" value="" class="input2"/></td>
				<td><input type="text" name="targets[XXXX].waistHip" value="" class="input2"/></td>
			</tr>
			<tr class="bcZZZZ">
				<td nowrap>奖励</td>
				<td colspan="10"><input type="text" name="targets[XXXX].reward" value="" class="input3"/></td>
			</tr>
		</tbody>
	</table>
</body>
</html>
