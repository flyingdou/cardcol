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
	$('#bmiform').attr('action','favorite.asp');
	$('#bmiform').submit();
}
function prevStep(){
	$('#bmiform').attr('action','frequency.asp');
	$('#bmiform').submit();
}
</script>
</head>
<body>
	<s:include value="/share/header.jsp" />
	<div id="content">
		<div class="my-setting">
			<ul id="nextstep">
			   <li class="first">一.设定健身目标</li>
			   <li>二.身体与健身状况</li>
			   <li>三.设定健身频率</li>
			   <li  class="last">四.设定靶心率</li>
			   <li>五.设定有氧项目</li>
			   <li>六.完成</li>
			</ul>
		</div>
		<div id="center-1">	
			<s:form id="bmiform" name="bmiform" method="POST" theme="simple">
				<s:hidden name="autoGen" id="autoGen" />
				<s:hidden name="setting.id"/>
				<s:hidden name="setting.target"/>
				<s:hidden name="setting.member"/>
				<s:hidden name="setting.height"/>
				<s:hidden name="setting.weight"/>
				<s:hidden name="setting.heart"/>
				<s:hidden name="setting.currGymStatus"/>
				<s:hidden name="setting.diseaseReport"/>
				<s:hidden name="setting.strengthDate"/>
				<s:hidden name="setting.strengthDuration"/>
				<s:hidden name="setting.cardioDate"/>
				<s:hidden name="setting.cardioDuration"/>
				<s:hidden name="setting.favoriateCardio"/>
				<s:hidden name="setting.intensityMode"/>
				<!--<h2 class="context_title">设定靶心率</h2>-->
				<div class="configall">	
					<h4>1、设定靶心率区间：</h4>
					   <div class="sevdmi">
							<div class="option">
								    <p>靶心率阈值上限= <s:textfield name="setting.bmiHigh" size="10" id="bmiHigh"/> %</p>
									<p>靶心率阈值下限= <s:textfield name="setting.bmiLow" size="10" id="bmiLow"/> %	</p>
							</div>
							<div id="tishi">
								健康人群正常情况下靶心率阈值为50%-85%。
								建议您向教练.医师等专业人士咨询后再设定。
							</div>
				       </div>
					<h4>2、选择运动强度评估方法：</h4>
						<div class="fourbim">
						  <div class="bmionti">
							<s:radio cssStyle="margin-right:5px;"  list="#{'0':'常用估值法','1':'卡尔沃宁（karvonen）法'}" listKey="key" listValue="value" name="setting.bmiMode" id="bmiMode" value="%{setting.bmiMode}"/>
						  </div>
						  <div  id="tishi">
							本系统给出的所有运动强度提示仅供身体健康的正常运动人群进行双脚着地的运动时参考，并非医学诊断性结健身者
							的运动强度是否合适与人的个体差异、运动方式及其它因素有关，请向医生、教练等专业人员咨询。				
					     </div>
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
