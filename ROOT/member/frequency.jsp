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
	$('#frequencyform').attr('action','bmi.asp');
	$('#frequencyform').submit();
}
function prevStep(){
	$('#frequencyform').attr('action','state.asp');
	$('#frequencyform').submit();
}
</script>
</head>
<body>
	<s:include value="/share/header.jsp" />
	<div id="content">
		<div class="my-setting">
			<ul id="nextstep">
			   <li  class="first">一.设定健身目标</li>
			   <li>二.身体与健身状况</li>
			   <li class="last">三.设定健身频率</li>
			   <li>四.设定靶心率</li>
			   <li>五.设定有氧项目</li>
			   <li>六.完成</li>
			</ul>
		</div>
		<div id="center-1">	
			<s:form id="frequencyform" name="frequencyform" method="post" theme="simple">
			<s:hidden name="autoGen" id="autoGen" />
			<s:hidden name="setting.id"/>
			<s:hidden name="setting.target"/>
			<s:hidden name="setting.member"/>
			<s:hidden name="setting.height"/>
			<s:hidden name="setting.weight"/>
			<s:hidden name="setting.heart"/>
			<s:hidden name="setting.currGymStatus"/>
			<s:hidden name="setting.diseaseReport"/>
			<s:hidden name="setting.favoriateCardio"/>
			<s:hidden name="setting.bmiMode"/>
			<s:hidden name="setting.bmiLow"/>
			<s:hidden name="setting.bmiHigh"/>
			<s:hidden name="setting.intensityMode"/>
				<div class="configall">	
					<h4>1、计划每周的哪几天进行力量训练？<span id="tishi">(提示:根据您的个人情况，建议您每周进行<s:property value="#request.llmsg"/>次力量训练。连续7天运动不利于身体健康，一周请至少安排1天休息.)</span></h4>
					<div class="goalcontent">
						<s:checkboxlist id="strengthDate" value="#request.list1" list="#{'1':'星期日','2':'星期一','3':'星期二','4':'星期三','5':'星期四','6':'星期五','7':'星期六'}" listKey="key" listValue="value" name="setting.strengthDate"/>
					</div>
					<h4>2、每次力量训练用多长时间？</h4>
					<div class="goalcontent">
						<s:radio cssStyle="margin-right:5px;" list="#{'30':'30分钟','45':'45分钟','60':'60分钟','90':'90分钟','120':'120分钟'}" listKey="key" listValue="value" id="strengthDuration" name="setting.strengthDuration" value="%{setting.strengthDuration}"/>
					</div>
					<h4>3、计划每周的哪几天进行有氧训练？<span id="tishi">(提示:根据您的个人情况，建议您每周进行<s:property value="#request.yyplmsg"/>天的有氧运动。)</span>
					</h4>
					<div class="goalcontent">
						<s:checkboxlist id="cardioDate" value="#request.list2" list="#{'1':'星期日','2':'星期一','3':'星期二','4':'星期三','5':'星期四','6':'星期五','7':'星期六'}" listKey="key" listValue="value" name="setting.cardioDate"/>
					</div>
					<h4>4、每次有氧训练用多长时间？<span id="tishi">(提示：根据您的个人情况，建议您每次进行<s:property value="#request.yysjmsg"/>分钟的有氧运动。)</span>		
					</h4>
					<div class="goalcontent">
						<s:radio cssStyle="margin-right:5px;" list="#{'20':'30分钟','40':'45分钟','60':'60分钟'}" id="cardioDuration" listKey="key" listValue="value" name="setting.cardioDuration" value="%{setting.cardioDuration}"/>
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
