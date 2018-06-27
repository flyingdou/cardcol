<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="健身E卡通-我的设定" />
<meta name="description" content="健身E卡通我的设定" />
<title>健身E卡通-我的设定</title>
<link rel="stylesheet" type="text/css" href="css/user-config.css" />
<script type="text/javascript">
function nextStep(){
	$('#targetform').attr('action','state.asp');
	$('#targetform').submit();
}
</script>
</head>
<body>
	<s:include value="/share/header.jsp" />
	<div id="content">
		<div class="my-setting">
			<ul id="nextstep">
				<li class="first1">一.设定健身目标</li>
				<li>二.身体与健身状况</li>
				<li>三.设定健身频率</li>
				<li>四.设定靶心率</li>
				<li>五.设定有氧项目</li>
				<li>六.完成</li>
			</ul>
		</div>
		<div id="center-1">
			<div class="configall" id="targetContent">
		 		<s:include value="/member/target_content.jsp"/>
			</div>
		</div>
		<div class="stepoperate">
			<a href="javascript:nextStep()" title="下一步" class="butnext">下一步</a>
		</div>
	</div>
	<s:include value="/share/footer.jsp" />
	<table id="sample1" style="display:none;" class="goaltable">
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
