<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Salesup网络预约管理系统_在线团体课程表</title>
<meta name="keywords" content="在线团体课程表,在线排课,网络课程表,在线预约" />
<meta name="description" content="制作团体课程表并实现团体课程的在线预约。"  />
<s:include value="../include/header.jsp"></s:include>
<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="css/calendar.css" />
<script type="text/javascript" src="script/ext-base.js"></script>
<script type="text/javascript" src="script/ext-all.js"></script>
<script type="text/javascript" src="script/ext/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="script/ext/ext-calendar.js"></script>
<script type="text/javascript" src="script/ext/MonthDayDetailView.js"></script>
<script type="text/javascript" src="script/ext/WeekEventRenderer.js"></script>
<script type="text/javascript" src="script/ext/CalendarView.js"></script>
<script type="text/javascript" src="script/ext/MonthView.js"></script>
<script type="text/javascript" src="script/ext/DayHeaderView.js"></script>
<script type="text/javascript" src="script/ext/DayBodyView.js"></script>
<script type="text/javascript" src="script/ext/DayView.js"></script>
<script type="text/javascript" src="script/ext/WeekView.js"></script>
<script type="text/javascript" src="script/ext/ext-field.js"></script>
<script type="text/javascript" src="script/ext/ext-panel.js"></script>
<script type="text/javascript" src="script/ext/ext-window.js"></script>
<script type="text/javascript" src="script/ext/CalendarPanel.js"></script>
<script type="text/javascript" src="script/club-record.js"></script>
<script type="text/javascript" language="javascript">
var panel;
Ext.onReady(function(){
	panel = new Ext.calendar.MainPanel({
		type: '<s:property value="type"/>', 
		parameter: {houseId: '<s:property value="houseId"/>'},
		workDays: '<s:property value="#request.workDays"/>',
		workTimes: '<s:property value="#request.workTimes"/>'
	});
	panel.initialize();
});
function showData(id){
	panel.setParamValue({id: id ,name: ''});
	panel.setParameter({houseId: id});
}
</script>
</head>
<body>
<div class="container">
<div class="wrap">
	<s:include value="../include/banner.jsp" />
	<div id="tabs" style="border: 0px;">
		<ul class="css-tabs">
			<s:iterator value="#request.factorys">
			<li id="<s:property value="id"/>"><a href="#panels" onclick="showData(<s:property value="id"/>)"><s:property value="factory"/></a></li>
			</s:iterator>
		</ul>
		<div class="css-panel" id="panels">
		    <div id="calendar-ct"> </div>
		</div>
	</div>
	<s:include value="../include/footer.jsp" />
</div>
</div>
</body>
</html>
