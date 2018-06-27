<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link href="css/club-checkin.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/ext3-all.css" />
<link rel="stylesheet" type="text/css" href="css/calendar.css" />
<link type="text/css" rel="stylesheet" href="script/jRating/jRating.jquery.css"/>
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
<script type="text/javascript" src="script/ext/ext-window.js"></script>
<script type="text/javascript" src="script/ext/CalendarPanel.js"></script>
<script type="text/javascript" src="script/jRating/jRating.jquery.js"></script>
<script type="text/javascript" src="script/jquery.raty.js"></script>
<script type="text/javascript">
var panel;
Ext.BLANK_IMAGE_URL='images/s.gif';
Ext.onReady(function() {
	panel = new Ext.calendar.MainPanel({
		role: <s:property value="#request.role"/>,
		funcs: <s:property value="#request.funcs"/>,
		workDays: '<s:property value="#request.workDays"/>',
		workTimes: '<s:property value="#request.workTimes"/>'
	});
	panel.initialize();
});
</script>
<div id="tabs">
	<div class="css-panel" id="panels" style="border: 1px solid #aaaaaa;">
	    <div id="calendar-ct" style="margin: 5px;"> </div>
	</div>
</div>
