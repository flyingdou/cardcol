<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp"/>
<title>健身E卡通—我的设定</title>
<meta name="keywords" content="健身E卡通—我的设定" />
<meta name="description" content="健身E卡通—我的设定" />
<link href="css/user-config.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="css/pulicstyle.css"/>
<script type="text/javascript">
function nextStep(){
	$('#form').attr('action', 'factory!next.asp');
	$('#form').submit();
}
</script>
</head>
<body>
	<s:include value="/share/header.jsp"></s:include>
	<div id="content">
		<div class="my-setting">
			<ul id="nextstep">
				<li class="first1">一.场地设定</li>
				<li>二.课程设定</li>
				<li>三.私教分成比例</li>
				<li>四.营业时间</li>
				<li>五.完成</li>
			</ul>
		</div>
		<div id="context">
		<s:include value="/club/factory_content.jsp"/>
		</div>
		<div class="stepoperate">
			<a href="javascript:nextStep();" title="下一步" class="butnext">下一步</a>
		</div>
	</div>
	
	<table style="display:none;">
		<tbody id="samples1">
			<tr class="bc0">
				<td width="28"><input type="checkbox" name="ids" value="" class="hasBorder" /></td>
				<td width="200"><input type="text" name="factorys[XXXX].name" class="input2" /></td>
				<td width="200"><s:select name="factorys[XXXX].project" theme="simple" list="#request.projects" listKey="id" listValue="name"/></td>
				<td width="200"><input type="text" name="factorys[XXXX].memo" class="input3" maxlength="200" /></td>
				<td width="100"><input type="checkbox" name="factorys[XXXX].applied"  value="1"  /></td>
			</tr>
		</tbody>
	</table>
	
	<s:include value="/share/footer.jsp"/>
</body>
</html>
