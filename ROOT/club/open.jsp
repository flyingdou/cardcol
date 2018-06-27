<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="健身E卡通—我的设定" />
<meta name="description" content="健身E卡通—我的设定" />
<title>健身E卡通—我的设定</title>
<link href="css/user-config.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="css/pulicstyle.css"/>
<script type="text/javascript">
	function nextStep() {
		var signTime = $("#signTime").val();
		if(!signTime || signTime == '' || isNaN(signTime)){
			alert("签到间隔时间请输入数字!");
			return;
		}
		$('#form').attr('action', 'open!next.asp');
		$('#form').submit();
	}
	function prevStep() {
		$('#form').attr('action', 'open!prev.asp');
		$('#form').submit();
	}
</script>
<style type="text/css">
	select {
		width:60px;
		height:20px;
		border:1px solid #ccc;
		margin:0px 0px 0px 5px ;
	}
</style>
</head>
<body>
	<s:include value="/share/header.jsp"/>
	<div id="content">
		<div class="my-setting">
			<ul id="nextstep">
				<li class="first">一.场地设定</li>
				<li>二.课程设定</li>
				<li>三.私教分成比例</li>
				<li class="last">四.营业时间</li>
				<li>五.完成</li>
			</ul>
		</div>
		<s:form name="form" id="form" method="post" theme="simple">
		<div id="center-1">
			<div class="configall">
				<h4>1、营业日期</h4>
				<div>
					<s:checkboxlist id="workDate" value="#request.list" list="#{'1':'星期日','2':'星期一','3':'星期二','4':'星期三','5':'星期四','6':'星期五','7':'星期六'}" listKey="key" listValue="value" name="workDate"/>
				</div>
				<h4>2、营业时间</h4>
				<div class="open_time">
					<s:if test="worktimes.size <= 0">
					从<s:select name="worktimes[0].startTime" list="#request.times" listKey="key" listValue="value" cssStyle="border:1px solid #ccc; width:80px; height:20px; margin-left:5px;" /> 
					 到<s:select name="worktimes[0].endTime" list="#request.times" listKey="key" listValue="value" cssStyle="border:1px solid #ccc; width:80px; height:20px; margin-left:5px;" />
					</s:if>
					<s:else>
					<s:iterator value="worktimes" status="st">
					<li>从<s:select list="#request.times" listKey="key" listValue="value" name="%{'worktimes['+#st.index+'].startTime'}"/>
						到<s:select list="#request.times" listKey="key" listValue="value" name="%{'worktimes['+#st.index+'].endTime'}"/>
						<a onclick="javascript: onDelete(this);">删除</a>
						<s:hidden name="%{'worktimes['+#st.index+'].id'}"/>
					</li>
					</s:iterator>
					</s:else>
				</div>
				<h4>3、签到间隔时间(分钟)</h4>
				<div>
					<input id="signTime" type="text" name="signTime" value="${signTime}" style="width:60px;" /> 分钟
				</div>
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
