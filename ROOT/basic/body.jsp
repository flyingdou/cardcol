<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="健身,健身计划,哑铃健身计划,减肥计划表,力量训练,有氧训练,柔韧训练,增肌" />
<meta name="description" content="健身教练的健身计划中有健身教练为健身会员设定的健身计划,其中有减肥计划表,力量训练,有氧训练,柔韧训练,增肌等。" />
<title>训练日志</title>
<s:include value="/share/meta.jsp"/>
<link rel="stylesheet" type="text/css" href="css/body.css"/>
<link rel="stylesheet" type="text/css" href="css/auto.css" />
<link type="text/css" rel="stylesheet" href="css/pulicstyle.css"/>
<script type="text/javascript" src="script/auto.js?v=2000000"></script>
<script type="text/javascript" src="script/jquery.form.js"></script>
<script type="text/javascript" src="script/jquery.parser.js"></script>
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
			  $.ajax({type : 'post', url : 'body!getMemberData.asp', data : 'id=' + $('#memberId').val(),dataType: 'json',
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
	$(document).mask('请稍候，正在加载数据......');
	$.ajax({type : 'post',url : 'body!switchYearMonth.asp',data : params, 
		success : function(resp) {
			$(document).unmask();
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
	$(document).mask('请稍候，正在加载数据......');
	$.ajax({type : 'post', url : 'body!switchDate.asp', data : 'body.analyDate=' + dateText + '&body.member=' + $('#memberId').val()+'&trainRecord.doneDate='+ dateText,
		success : function(resp) {
			$(document).unmask();
			$('#divdetail').html(resp);
			$('#analyDate').val(dateText);
			$('#doneDate').val(dateText);
			
		}
	});
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
</head>
<body>
	<s:include value="/share/header.jsp"/>
	<div id="content">
		<div id="left">
			<div id="left-1">
				<div class="calendar"></div>
			</div>
			 <%-- <div>
				<input type="text" id="intext" />
				<s:textfield name="trainRecord.doneDate" id="doneDate"/> 
				<s:date name="body.analyDate" format="yyyy-MM-dd" id="sdate"/>
			</div> --%> 
			<%--  <div style="margin-top:8px;">
				<s:if test="#session.loginMember.role == \"S\"">
					<span style="margin-left:5px;">选择会员</span>：<s:select list="#request.members" value="member" listKey="id" listValue="nick" cssClass="selectmember"/>
			    </s:if>
			    <s:if test="#session.loginMember.role == \"M\"">
					<span style="margin-left:5px;">选择会员</span>：<s:select list="#session.loginMember" value="member" listKey="id" listValue="nick" cssClass="selectmember"/>
			    </s:if> 
				<s:else>
					<a href="javascript:loadSetting(1)"><input type="button" class="btnw" value=""/></a>
                   	<a href="javascript:loadSetting(0);"> <input type="button" class="btne" value=""/></a>
				</s:else>
			</div>
			 <div class="shuju">
				<div class="con_top">基本数据</div>
				<s:form name="frmbasic" id="frmbasic" theme="simple">
				<div class="con_jiush">
					<p>目前身高:<s:textfield name="setting.height" cssClass="txtls" id="setheight"/><span>cm</span></p>
					<p>目前体重:<s:textfield name="setting.weight" cssClass="txtls" id="setweight"/><span>kg</span></p>
					<p>目前年龄:<s:textfield name="age" cssClass="txtls" id="setage"/></span><span>岁</span></p>
					<p>目前腰围:<s:textfield name="setting.waistline" id="setwaistline" cssClass="txtls"/><span>cm</span></p>
					<p>目前臀围:<s:textfield name="trainRecord.hip" id="sethip" cssClass="txtls"/><span>cm</span></p>
				</div>
				</s:form>
			</div> --%>
		</div>
		<div id="divdetail">
			
			<s:include value="/basic/body_detail.jsp"></s:include>
		</div>
		<div id="divplanset" style="display:none;margin-left:12px; margin-top:20px;float:left;">
		     
		</div>
	</div>
	<s:include value="/share/footer.jsp"/>
</body>
</html>
