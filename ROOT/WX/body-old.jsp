<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>身体数据</title>
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/bootstrap.css" rel="stylesheet">
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/base.css" rel="stylesheet">
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/style.css" rel="stylesheet">
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/index.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/smoothness/template.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/smoothness/jquery-ui.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/jquery-2.2.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery-ui.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery-ui-lang.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/style.js"></script>
    <script type="text/javascript">
    function saveRecord(){
    	var parms = $('#sporForm').serialize();
    	var weight=$('#w').text();
    	var height=$('#s').text();
    	var hip=$('#t').text();
    	$.ajax({type:'post',
    		url:'bodydatawx!saveRecord.asp',
    		data: parms,
		success:function(msg){
			var _json = $.parseJSON(msg);
			var result = _json.msg;
			if(result == "okRecord"){
				alert("当前训练日志已成功保存！");
			}
		}
		});
    }
    </script>
</head>
<body>
<div class="trainRizhi container">
<s:iterator value="trainRecord">
	<form id="sporForm" action="bodydatawx!bodys.asp" method="post">
    <div class="message">
        <div class="pic fl">选择日期</div>
        <p id="date" onclick="show();"><input id="date1" type="text" disabled="disabled" value='<s:date name="#request.doneDate" format="yyyy-MM-dd"/>'/></p>
    </div>
    <s:if test="id == null">
    <div class="sport_data">
	        <div class="icon">
	            <img src="${pageContext.request.contextPath}/WX/images/train_icon1_03.png" class="fl">
	            <h4 class="fl">身体数据</h4>
	        </div>
	        <div class="data_inner">
				    <p>体重<span><input id="w" type="text" value=""/>KG</span></p><hr>
				    <p>腰围<span><input id="s" type="text" value=""/>CM</span></p><hr>
				    <p>臀围<span><input id="t" type="text" value=""/>CM</span></p><hr>
	        </div>
    </div>
    </form>
    <div class="body_data">
        <div class="icon">
            <img src="${pageContext.request.contextPath}/WX/images/train_icon2_06.png" class="fl">
            <h4>我的健康评估</h4>
            <img src="${pageContext.request.contextPath}/WX/images/right_icon_03.png" class="right fr" style="margin-top: -10%">
            <p style="margin-left: 70% ;margin-top: -10%" id="weight"></p>
        </div>
        <div class="pic fl">
            <img src="${pageContext.request.contextPath}/WX/images/bodyImg_22.png">
            <span>我的健康评估</span>
            <p>?</p>
        </div>
        <div class="txt fl">
            <h4>体质指数(BMI)</h4>
            <p id ="bmi1"></p>
            <div class="line"></div>
            <h4>腰臀比(WHR)</h4>
            <p id="whr1"></p>
        </div>
    </div>
    </s:if>
	<s:else>
		 <div class="sport_data">
	        <div class="icon">
	            <img src="${pageContext.request.contextPath}/WX/images/train_icon1_03.png" class="fl">
	            <h4 class="fl">身体数据</h4>
	        </div>
	        <div class="data_inner">
	            <ul>
	                <li><p>体重<span><s:property value="#request.weight"/>KG</span></p></li>
	                <li><p>腰围<span><s:property value="#request.waist"/>CM</span></p></li>
	                <li><p>臀围<span><s:property value="#request.hip"/>CM</span></p></li>
	                <li><p>最高心率<span><s:property value="#request.heartRate"/>次/分钟</span></p></li>
	            </ul>
	        </div>
		</div>
	        <div class="body_data">
		        <div class="icon">
		            <img src="${pageContext.request.contextPath}/WX/images/train_icon2_06.png" class="fl">
		            <h4>我的健康评估</h4>
		            <img src="${pageContext.request.contextPath}/WX/images/right_icon_03.png" class="right fr" style="margin-top: -10%">
		            <p style="margin-left: 70% ;margin-top: -10%" id="weight"></p>
		        </div>
		        <div class="pic fl">
		            <img src="${pageContext.request.contextPath}/WX/images/bodyImg_22.png">
		            <span>我的健康评估</span>
		            <p>?</p>
		        </div>
		        <div class="txt fl">
		            <h4>体质指数(BMI)</h4>
		            <p id ="bmi"></p>
		            <div class="line"></div>
		            <h4>腰臀比(WHR)</h4>
		            <p id="whr"></p>
		        </div>
		    </div>
    	</s:else>
</s:iterator>
    <div class="assign">
        <a href="JavaScript:saveRecord();" class="share">保存</a>
    </div>
    <%-- <form id="sporForm" action="bodydatawx.asp" method="post">
 	<input type="text" name="trainRecord.doneDate" value="<s:date name="#request.doneDate" format="yyyy-MM-dd"/>"/>
   	<input type="text" name="trainRecord.heartRate" value="<s:property value="#request.heartRate"/>"/>
   	<input type="text" name="trainRecord.weight" value="<s:property value="#request.weight"/>"/>
   	<input type="text" name="trainRecord.waist" value="<s:property value="#request.waist"/>"/>
   	<input type="text" name="trainRecord.hip" value="<s:property value="#request.hip"/>"/>
   	<input type="text" name="trainRecord.height" value="<s:property value="#request.height"/>"/>
    </form> --%>
    <div id="myplan" class="container myPlan" style="display: none;">
    	<div id="left-1"><div class="calendar"></div></div>
      	<%-- <s:textfield name="trainRecord.doneDate" id="doneDate"/> --%>
    </div>
    <form id="queryForm" action="bodydatawx!switchDate.asp" method="post">
      <input type="hidden" name="trainRecord.doneDate" value="" id="switchDate">
      </form>
</div>
<script>
<s:iterator value="pageInfo.items">
 var id1 ='<s:property value="id"/>';
 var height1 = '<s:property value="height"/>';
 var weight1 = '<s:property value="weight"/>';
 var waist1 = '<s:property value="waist"/>';
 var hip1 = '<s:property value="hip"/>';
 var heights1 = height1/100
 $("#id1").text(id1);
 $("#height1").text(height1);
 $("#waist1").text(waist1);
 $("#weight1").text(weight1);
 $("#hip1").text(hip1);
 if(heights1=="0"){
	 $("#bmi1").text("0.00");
 }else{
 $("#bmi1").text( (weight1/(heights1*heights1)).toFixed(2))
 }
 if(hip1 == '0'||waist1==''|| hip1==''){
	 $("#whr1").text("0.00");
 }else{
	 $("#whr1").text((waist1/hip1).toFixed(2))
 }
if(id1 != ''){
	 if(weight1==''){
		 $("#weight1").text("体重0KG");
		 $('#sport').removeAttr('href');
	 }else{
		 $("#weight1").text("体重"+ weight1 +"KG");
	 }
 }
</s:iterator>
</script>
<script type="text/javascript">
function show(){
	document.getElementById("myplan").style.display="block";
}
</script>
<script>
 var id ='<s:property value="trainRecord.id"/>';
 var height = '<s:property value="trainRecord.height"/>';
 var weight = '<s:property value="trainRecord.weight"/>';
 var waist = '<s:property value="trainRecord.waist"/>';
 var hip = '<s:property value="trainRecord.hip"/>';
 var heights = height/100
 if(heights=="0"){
	 $("#bmi").text("0.00");
 }else{
 $("#bmi").text( (weight/(heights*heights)).toFixed(2))
 }
 if(hip == '0'||waist==''|| hip==''){
	 $("#whr").text("0.00");
 }else{
	 $("#whr").text((waist/hip).toFixed(2))
 }
 if(id != ''){
	 if(weight==''){
		 $("#weight").text("体重0KG");
		 $('#sport').removeAttr('href');
	 }else{
		 $("#weight").text("体重"+ weight +"KG");
	 }
 }
</script>
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
	document.getElementById("myplan").style.display="none";
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
</body>
</html>