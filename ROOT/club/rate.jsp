<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp"/>
<meta name="keywords" content="健身E卡通—我的设定" />
<meta name="description" content="健身E卡通—我的设定" />
<title>健身E卡通—我的设定</title>
<link href="css/user-config.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="css/pulicstyle.css"/>
<script type="text/javascript">
function nextStep(){
	$('#form').attr('action', 'rate!next.asp');
	$('#form').submit();
}
function prevStep(){
	$('#form').attr('action', 'rate!prev.asp');
	$('#form').submit();
}
</script>
</head>
<body>
	<s:include value="/share/header.jsp"/>
	<div id="content">
		<div class="my-setting">
			<ul id="nextstep">
				<li class="first">一.场地设定</li>
				<li>二.课程设定</li>
				<li class="last">三.私教分成比例</li>
				<li>四.营业时间</li>
				<li>五.完成</li>
			</ul>
		</div>
		<s:form id="form" name="form" action="" method="post" theme="simple">
			<div id="center-1">
					<div class="conrate">
						<h1 class="myrate">
							<p>本俱乐部收取网络私教营业收入的百分比：<s:textfield name="rate" cssClass="text1" /> %</p>
						</h1>
					</div>
			</div>
		</s:form>
		<div class="stepoperate">
			<a href="javascript:prevStep();" title="上一步" class="butlost">上一步</a> <a
				href="javascript:nextStep();" title="下一步" class="butnext">下一步</a>
		</div>
	</div>
	<s:include value="/share/footer.jsp"/>
</body>
</html>
