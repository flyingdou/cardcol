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
	$('#favform').attr('action','done.asp');
	$('#favform').submit();
}
function prevStep(){
	$('#favform').attr('action','bmi.asp');
	$('#favform').submit();
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
			   <li>四.设定靶心率</li>
			   <li class="last">五.设定有氧项目</li>
			   <li>六.完成</li>
			</ul>
		</div>
		<div id="center-1">	
			<s:form id="favform" name="favform" method="POST" theme="simple">
				<s:hidden name="autoGen" id="autoGen"/>
				<s:hidden name="setting.id"/>
				<s:hidden name="setting.target"/>
				<s:hidden name="setting.member"/>
				<s:hidden name="setting.height"/>
				<s:hidden name="setting.weight"/>
				<s:hidden name="setting.heart"/>
				<s:hidden name="setting.diseaseReport"/>
				<s:hidden name="setting.currGymStatus"/>
				<s:hidden name="setting.strengthDate"/>
				<s:hidden name="setting.strengthDuration"/>
				<s:hidden name="setting.cardioDate"/>
				<s:hidden name="setting.cardioDuration"/>
				<s:hidden name="setting.intensityMode"/>
				<s:hidden name="setting.bmiHigh"/>
				<s:hidden name="setting.bmiLow"/>
				<s:hidden name="setting.bmiMode"/>
				<div class="configall">
					<h4>1、请选择喜欢的有氧运动项目</h4>
					<p class="favorite_project">
						<s:checkboxlist list="#request.favoriteCardios" listKey="name" listValue="name" value="#request.list" name="setting.favoriateCardio"/>
					</p>
				</div>
			</s:form>
		</div>
		<div class="stepoperate">
			<a href="javascript:prevStep()" title="上一步" class="butlost">上一步</a>
			<a href="javascript:nextStep()" title="下一步" class="butnext">下一步</a>
		</div>
	</div>
	<s:include value="/share/footer.jsp" />
</body>
</html>
