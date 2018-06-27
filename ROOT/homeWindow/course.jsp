<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link rel="stylesheet" type="text/css" href="css/ext3-all.css" />
<link rel="stylesheet" type="text/css" href="css/calendar.css" />
<script type="text/javascript" src="script/ext/ext-base.js"></script>
<script type="text/javascript" src="script/ext/ext-all.js"></script>
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
<script type="text/javascript" src="script/ext/ext-window.js?v=1000000"></script>
<script type="text/javascript" src="script/ext/CalendarPanel.js"></script>
<link href="css/club-sns.css" rel="stylesheet" type="text/css" />
<link href="css/member-checkin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
Ext.BLANK_IMAGE_URL='images/s.gif';
$(document).ready(function(){
	var panel = new Ext.calendar.MainPanel({
		role: <s:property value="#request.role"/>,
		funcs: <s:property value="#request.funcs"/>,
		workDays: '<s:property value="#request.workDays"/>',
		workTimes: '<s:property value="#request.workTimes"/>'
	});
	panel.initialize();	
});
</script>
<div class="css-panel" id="panels">
    <div id="calendar-ct"> </div>
</div>
