<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>我的计划</title>
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/bootstrap.css" rel="stylesheet">
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/base.css" rel="stylesheet">
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/index.css" rel="stylesheet">
    <link rel="stylesheet" type="${pageContext.request.contextPath}/WX/text/css" href="../css/smoothness/template.css" />
	<link rel="stylesheet" type="${pageContext.request.contextPath}/WX/text/css" href="../css/smoothness/jquery-ui.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/jquery-2.2.0.min.js"></script>
    <script type="text/javascript" src="../script/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="../script/jquery-ui.js"></script>
	<script type="text/javascript" src="../script/jquery-ui-lang.js"></script>
</head>
<script type="text/javascript">
$(function() {
	/*  var formatDate = function (date) {  //js将date转字符串
	    var y = date.getFullYear();  
	    var m = date.getMonth() + 1;  
	    m = m < 10 ? '0' + m : m;  
	    var d = date.getDate();  
	    d = d < 10 ? ('0' + d) : d;  
	    return y + '-' + m + '-' + d;  
	};   */
	$('.calendar').datepicker({autoSize: true, defaultDate: '<s:date name="planDate" format="yyyy-MM-dd"/>',
		showMonthAfterYear:true,changeMonth:true,changeYear:true,onChangeMonthYear: switchYearMonth, onSelect: switchDate});
	//var defaultDate = $('.calendar').datepicker('option', 'defaultDate');
	//$('#plantDate').val(defaultDate);
	//switchDate(defaultDate,null);
	setStatus(<s:property value="#request.status" escape="false"/>);
});
function switchYearMonth(year, month, inst){
	var date = $('.calendar').datepicker('getDate').getDate();
	if (month.length < 2) month = '0' + month;
	if (date.length < 2) date = '0' + date;
	var planDate = year + '-' + month +'-'+ date;
	var params = 'course.planDate=' + planDate + '&course.coach.id=479';
	//$(document).mask('请稍候，正在加载数据......');
	$.ajax({type : 'post',url : 'workoutwx!switchYearMonth.asp',data : params, 
		success : function(resp) {
			//$(document).unmask();
			$('#myplan').show();
			//$('#myplan').html(resp);
			$('#plantDate').val(planDate);
		}
	});
}
function switchDate(dateText, inst){
	$('#myplan').show();
	//alert(dateText);
	//$(document).mask('请稍候，正在加载数据......');
	$.ajax({type : 'post', url : 'workoutwx!switchDate.asp', data : 'course.planDate=' + dateText,
		success : function(resp) {
			//$(document).unmask();
			$('#myplan').html(resp);
			$('#plantDate').val(dateText);
			
		}
	});
}
function setStatus(status) {
	var days = $(".calendar .ui-state-default"), val, j = 1;
	var curDate = $('.calendar').datepicker('getDate').getDate();
	for ( var i = 0; i < days.length; i++, j++) {
		val = status['day'+j];
		if (val === 1) {
			$(days[i]).css('background', '#008000');//白色
		} else if (val == 2) {
			$(days[i]).css('background', '#ff0000');//红色
		} else {
			$(days[i]).css('background', '#e6e6e6');//亚麻灰
		}
		if(j == curDate){
			$(days[i]).css("background","#fcfc00");//黄色
			$(days[i]).css("color","#000000");
		}
	}
}
/* function checkcourse(st){
	$('div .plan_nav:eq('+(st-1)+') p:eq(0) input').attr('id','courseInfoname'+st);
	$('div .plan_nav:eq('+(st-1)+') p:eq(1) input').attr('id','startTime'+st);
	$('div .plan_nav:eq('+(st-1)+') p:eq(2) input').attr('id','endTime'+st);
	$('div .plan_nav:eq('+(st-1)+') p:eq(3) input').attr('id','place'+st);
	//$('div .plan_nav p:eq('+(st-1)+') input:eq(2)').attr('id','membername'+st);
	$('div .plan_nav:eq('+(st-1)+') input:eq(0)').attr('id','courseid'+st);
	$('div .plan_nav:eq('+(st-1)+') input:eq(1)').attr('id','id'+st);
	//alert($('#place'+st).val());
	$('#startTime').val($('#startTime'+st).val());
	$('#endTime').val($('#endTime'+st).val());
	$('#courseInfoname').val($('#courseInfoname'+st).val());
	//$('#membername').val($('#membername'+st).val());
	$('#courseid').val($('#courseid'+st).val());
	$('#place').val($('#place'+st).val());
	//$('#flag').val(1);
	$('#id').val($('#id'+st).val());
	$('#checkform').submit();
} */ 
function checkcourse(cid){
	window.location.href="workoutwx!findPlanByCourse.asp?id="+cid;
}
</script>
<body style="background-color: #fff;">
  <div id="myplan" class="container myPlan">
    <div id="left-1"><div class="calendar"></div></div>
	<s:iterator value="#request.courses" status="st">
	
      <div class="plan_nav" >
          <%-- <div class="enter_play" onclick="checkcourse(<s:property value='#st.count'/>)"> --%>
            <div class="enter_play" onclick="checkcourse(<s:property value="id"/>)">
              <span>点击开始训练</span>
              <img src="images/play_09.png" class="plan">
              <img src="images/plan_bg_03.png" class="plan_bg">
          </div>
          <p>课程名称<span class="fr"><s:textfield name="courseInfo.name" id="courseInfoname<s:property value='#st.count'/>" size="11" style="text-align: right;" disabled="disabled"/></span></p>
          <p>开始时间<span class="fr"><s:textfield name="startTime" id='startTime<s:property value="#st.index"/>' size="4" disabled="disabled"/></span></p>
          <p>结束时间<span class="fr"><s:textfield id="endTime<s:property value='#st.count'/>" name="endTime" size="4" disabled="disabled"/></span></p>
          <p>课程地点<span class="fr"><s:textfield name="place" id="place<s:property value='#st.count'/>" style="text-align: right;" disabled="disabled"/></span></p>
          <s:hidden name="courseInfo.id" id="courseid<s:property value='#st.count'/>"/>
      	  <s:hidden name="id" id="id<s:property value='#st.count'/>"/>
      </div>
      <div class="note">
          <h5>课程备注</h5>
          <p><s:property value="memo"/></p>
      </div>
      
      </s:iterator>
      <s:form id="checkform" action="workoutwx!loadGroup.asp" method="post">
      		<s:hidden name="course.startTime" id="startTime"/>
      		<s:hidden name="course.endTime" id="endTime"/>
      		<s:hidden name="course.member.name" id="membername"/>
      		<s:hidden name="course.courseInfo.name" id="courseInfoname"/>
      		<s:hidden name="course.planDate" id="planDate"/>
      		<s:hidden name="courseid" id="courseid"/>
      		<s:hidden name="course.place" id="place"/>
      		<s:hidden name="course.id" id="id"/>
      </s:form>
     <%--  <s:textfield name="course.planDate" id="plantDate"/> --%>
      </div>
      
</body>
</html>