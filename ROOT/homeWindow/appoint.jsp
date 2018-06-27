<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="健身课程，健身计划，评价管理" />
<meta name="description" content="卡库网_健身预约_评价管理" />
<title>卡库网_健身预约_评价管理</title>
<link href="css/club-checkin.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/pulicstyle.css" />
</head>
<body>
	<input type="hidden" name="countList1" value="<s:property value="#request.countList1"/>" />
	<input type="hidden" name="countList2" value="<s:property value="#request.countList2"/>" />
	<s:include value="/share/window-header.jsp"/>
	<div id="content">
		<div id="left-1">
			<h1 style="background-image:none;background:#ff4401;color:white;font-weight:600">健身预约</h1>
			<div>
				<ul class="order">
					<li>
						<h1>
							<b>课程管理</b>
						</h1>
						<ul class="ordermanagement">
							<li><a id="coloa" href="coursewindow.asp">课程表</a></li>
						</ul>
						<s:if test="#session.toMember.role == \"E\"">
							<ul class="ordermanagement">
								<li><a id="coloa" href="factoryorderwindow.asp">场地预约</a></li>
							</ul>
						</s:if>
					</li>
				</ul>
			</div>
		</div>
		<div id="right-2">
			<s:if test="#request.goType ==1">
			<s:include value="/homeWindow/course.jsp"/>
			</s:if>
			<s:elseif test="#request.goType == 2">
			<s:include value="/factoryorder/calendarwindow.jsp"/>
			</s:elseif>
		</div>
	</div>
	<s:include value="/share/footer.jsp" />
</body>
</html>