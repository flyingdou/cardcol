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
    <link rel="stylesheet" type="text/css" href="../${pageContext.request.contextPath}/WX/css/smoothness/template.css" />
	<link rel="stylesheet" type="text/css" href="../${pageContext.request.contextPath}/WX/css/smoothness/jquery-ui.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/jquery-2.2.0.min.js"></script>
    <script type="text/javascript" src="../script/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="../script/jquery-ui.js"></script>
	<script type="text/javascript" src="../script/jquery-ui-lang.js"></script>
</head>
<script type="text/javascript">
$(function() {
	$('.calendar').datepicker({autoSize: true,defaultDate:'<s:date name="body.analyDate" format="yyyy-MM-dd"/>',showMonthAfterYear:true,changeMonth:true,changeYear:true,onChangeMonthYear: switchYearMonth, onSelect: switchDate});
	/* $('#frmbasic').find('input,textarea').each(function(){
		$(this).attr('disabled', true);
	}); */
	//$('#doneDate').val('<s:date name="body.analyDate" format="yyyy-MM-dd"/>');
	switchDate($('#analyDate').val(), null);
	if ($('.selectmember')) {
		$('.selectmember').change(function(){  //人物变化的时候才有用
			$('#memberId').val($(this).val());
			switchDate($('#analyDate').val(), null);
			  $.ajax({type : 'post', url : 'bodywx!getMemberData.asp', data : 'id=' + $('#memberId').val(),dataType: 'json',
				success : function(resp) {
					if (resp.success === true) {
						$('#setheight').val(resp.height);
						$('#setweight').val(resp.weight);
						$('#setage').val(resp.age);
						$('#setwaistline').val(resp.waistline);
						$('#sethip').val(resp.hip);
						$('#recordTimes').val(resp.times);
						$('#recordActionQuan').val(resp.actionQuan);
						$('#recordUnit').val(resp.unit);
					} else {
						$('#setheight').val("");
						$('#setweight').val("");
						$('#setage').val("");
						$('#setwaistline').val("");
						$('#sethip').val("");
						$('#recordTimes').val("");
						$('#recordAction').val("");
						$('#recordActionQuan').val("");
						$('#recordUnit').val("");
						$('#modeValue1').val("");
					}
				}
			}); 
		});
	}
});
function switchYearMonth(year, month, inst){
	var date = $('.calendar').datepicker("getDate").getDate();
	if (month.length < 2) month = '0' + month;
	if (date.length < 2) date = '0' + date;
	var planDate = year + '-' + month +'-'+ date;
	var params = 'body.analyDate=' + planDate + '&body.member=' + $('#memberId').val();
	$.ajax({type : 'post',url : 'bodywx!switchYearMonth.asp',data : params, 
		success : function(resp) {
			$('#divdetail').show();
			$('#divplanset').hide();
			$('#divdetail').html(resp);
			$('#analyDate').val(planDate);
			$('#doneDate').val(planDate);
		}
	});
}
function switchDate(dateText, inst){
	$('#divdetail').show();
	$('#divplanset').hide();
	
	if(dateText == undefined ){
		return;
	}else{
		$("#switchDate").val(dateText);
		$("#queryForm").submit();
	}
	 /* $.ajax({type : 'post', url : 'bodywx!switchDate.asp', data : 'body.analyDate=' + dateText + '&body.member=' + $('#memberId').val()+'&trainRecord.doneDate='+ dateText,
		success : function(resp) {
			$('#divdetail').html(resp);
			$('#analyDate').val(dateText);
			$('#doneDate').val(dateText);
			
		}
	}); */
}
function setStatus(status) {
	var days = $(".calendar .ui-state-default"), val, j = 1;
	var curDate = $('.calendar').datepicker('getDate').getDate();
	for ( var i = 0; i < days.length; i++, j++) {
		val = status['day' + j];
		if (val === 1) {
			$(days[i]).css('background', '#008000');
		} else if (val == 2) {
			$(days[i]).css('background', '#ff0000');
		} else {
			$(days[i]).css('background', '#e6e6e6');
		}
		if(j == curDate){
			$(days[i]).css("background","#fcfc00");
			$(days[i]).css("color","#000000");
		}
	}
}
</script>
<body style="background-color: #fff;">
  <div id="myplan" class="container myPlan">
    <div id="left-1"><div class="calendar"></div></div>
      <s:textfield name="course.planDate" id="plantDate"/>
      </div>
      <form id="queryForm" action="bodywx!switchDate.asp" method="post">
      <input type="hidden" name="trainRecord.doneDate" value="" id="switchDate">
      </form>
</body>
</html>