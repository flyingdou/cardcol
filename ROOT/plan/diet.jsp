<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<link rel="stylesheet" type="text/css" href="css/diet.css" />
<link type="text/css" rel="stylesheet" href="css/pulicstyle.css"/>
<meta name="keywords" content="饮食计划,饮食健康,减肥饮食计划,科学饮食,减肥瘦身," />
<meta name="description" content="健身教练的饮食计划中有教练为健身会员制定的科学的饮食,健康的饮食以及以减肥瘦身,增强肌肉,强身健体为目的的健身课程。" />
<title>饮食计划</title>
<script type="text/javascript">
var st = <s:property value="#request.monthStatus" escape="false"/>;
$(function() {
	$('.calendar').datepicker({autoSize: true, onChangeMonthYear: switchYearMonth, onSelect: switchDate});
	$('.buttomb').click(function() {$("#dialog").dialog('close');});
	$('.buttoma').click(function() {
		var parms = $('#copyform').serialize() + '&diet.id=' + $('#dietId').val() + '&member=' + $('#member').val() + '&planDate=' + $('#planDate').val();
		$.ajax({type: 'post', url: 'diet!copy.asp', data: parms,
			success: function(resp){
				var json = $.parseJSON(resp);
				st = json.message;
				setStatus();
				alert('当前计划已经成功复制到指定的日期中！');
			}
		});
	});
	$('.selectmember').change(function(){
		var member = $(this).val();
		$('#member').val(member);
		var date = $('#planDate').val();
		$.ajax({type: 'post', url: 'diet!switchMember.asp', data: 'member=' + member + '&planDate=' + date, 
			success: function(msg){
				$('#right').html(msg);
			}
		});
	});
	setStatus();
});
function setStatus() {
	var days = $(".calendar .ui-state-default"), val, j = 1;
	var currDate = $('.calendar').datepicker('getDate').getDate();
	for ( var i = 0; i < days.length; i++, j++) {
		var val = st["day" + j];
		if (val == 1) {
			$(days[i]).css('background', '#008000');
		} else if (val == 2) {
			$(days[i]).css('background', '#ff0000');
		} else {
			$(days[i]).css('background', '#e6e6e6 url(css/smoothness/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x');
		}
		if (j == currDate) {
			$(days[i]).css("background", "#fcfcfc");
			$(days[i]).css("color", "#000000");
		}
	}
}
function switchYearMonth(year, month, inst) {
	loadMask();
	var date = $('.calendar').datepicker("getDate").getDate();
	var planDate = year + '-' + month + '-' + date;
	$.ajax({type : 'post', url : 'diet!switchYearMonth.asp', data : 'planDate=' + planDate + '&member=' + $('#member').val(),
		success : function(resp) {
			st = resp;
			setStatus();
			removeMask();
		}
	});
}
function switchDate(dateText, inst) {
	loadMask();
	$('#planDate').val(dateText);
	$.ajax({type : 'post', url : 'diet!switchDate.asp', data : 'planDate=' + dateText + '&member=' + $('#member').val(),
		success : function(resp) {
			$('#right').html(resp);
			removeMask();
			setStatus();
		}
	});
}
function onSave(){
	loadMask();
	var params = $('#dietform').serialize();
	$.ajax({type : 'post', url : 'diet!save.asp', data : params, 
		success : function(data) {
			removeMask();
			$('#right').html(data);
			alert('当前数据已经成功保存')
		}
	});
}
function onDelete(){
	var key = $(this).attr('id'), $div = $(this).parents('div[id=' + key + ']');
	if (key == '') {
		$div.remove();
		return;
	}
	if(!confirm('是否确认删除当前饮食计划?')) return;
	$.ajax({url: 'diet!delete.asp', type: 'post', data: 'id=' + key, 
		success:function(resp){
			$div.remove();
			alert('当前饮食计划已经成功删除！');
		}
	});
}
function deleteDetail(){
	var $tr = $(this).parent().parent(), $id = $(this).attr('id');
	if ($id && $id != '') {
		if (confirm('是否确认删除当前数据？')){
			$.ajax({url: 'diet!deleteDetail.asp', type: 'post', data: 'id=' + $id, 
				success: function(msg) {
					if (msg == 'ok') {
						alert('当前食物已经成功删除！');
						$tr.remove();
					} else {
						alert(msg);
					}
				}
			});
		}
	}else {
		$tr.remove();
	}
}
function onEdit(){
	var $the = $(this), _key = $the.attr('id');
	$('#divmeal').find('select').each(function(){
		$(this).attr('disabled', true);
	});
	$the.parent().parent().find('select').each(function(){
		$(this).attr('disabled', false);
	});
	if (!_key) {
		//需要清除明细内容
		var i = 0;
		$('#table').find('tr').each(function(){
			if (i++ >0){
				$(this).remove();
			}
		});
	} else {
		//需要加载明细内容
		$.ajax({url: 'diet!findDetail.asp', type: 'post', data: 'id=' + _key,
			success: function(msg){
				$('#divdiet').html(msg);
			}
		});
	}
}
function onAdd(){
	$('#divmeal').find('select').each(function(){
		$(this).attr('disabled', true);
	});
	var _html = $('#mealSample').html().replace(new RegExp("XXXX", "gm"), "diet");
	$('#divmeal').append(_html);
	$('a[title="编辑"]').unbind('click').bind('click', onEdit);
	$('a[title="删除"]').unbind('click').bind('click', onDelete);
	var i = 0;
	$('#dietId').val('');
	$('#table').find('tr').each(function(){
		if (i++ >0){
			$(this).remove();
		}
	});
}
function onAddDetail(){
	var size = $('#table').find('tr').length - 1; 
	var html = $('#sampleDiet').html().replace(new RegExp("XXXX", "gm"), size + '');
	$('#table').find('tr:last').after(html);
	$('.inpremove[title="删除"]').bind('click', deleteDetail);
}
</script>
</head>
<body>
	<s:include value="/share/header.jsp"/>
	<div id="content">
		<div id="left" style="height:400px;">
			<div class="calendar"></div>
			<p>
				<input type="button" name="button" class="button1" /> 已完成 
				<input type="button" name="button" class="button2" /> 已计划
				<input type="button" name="button" class="button3" /> 未完成
			</p>
			<div style="margin-top:8px;">
				<s:if test="#session.loginMember.role == \"S\"">
					<span style="margin-left:5px;">选择会员</span>：<s:select list="#request.members" value="member" listKey="id" listValue="nick" cssClass="selectmember"/>
				</s:if>
			</div>
		</div>
		<div id="right">
			<s:include value="/plan/diet_meal.jsp"></s:include>
		</div>
	</div>
	<s:include value="/share/footer.jsp" />
	<table class="table1" cellspacing="1" cellpadding="0" border="0"  style="display:none;"  >
		<tbody id="sampleDiet" >
			<tr>
				<td>
					<input type="hidden" value="" name="diets[XXXX].id"/>
					<input type="text" value="" name="diets[XXXX].mealName"/>
				</td>
				<td><input type="text" value="" name="diets[XXXX].mealWeight"/></td>
				<td><input type="text" value="" name="diets[XXXX].mealKcal"/></td>
				<td><a href="#" title="删除" class="inpremove"><img src="images/x.png"/></a></td>
			</tr>
		</tbody>
	</table>
	<div id="dialog" title="饮食计划复制">
		<form id="copyform" name="copyform" method="post">
			<s:if test="#session.loginMember.role == \"S\"">
				<p>将本计划复制到其它会员</p>
				<p>
					<s:select name="toMember" theme="simple" list="#request.members" listKey="id" listValue="name" Style="min-width:90px;" />
				</p>
			</s:if>
			<p>将本计划复制到以下其它日期</p>
			<p>
				<input type="checkbox" name="weeks" value="1" id="checkbox"	class="inpa"/> <label for="checkbox">星期日</label>
				<input type="checkbox" name="weeks" value="2" id="checkbox2" class="inpa" /><label for="checkbox2">星期一</label>
				<input type="checkbox" name="weeks" value="3" id="checkbox3" class="inpa" /> <label for="checkbox3">星期二</label>
				<input type="checkbox" name="weeks" value="4" id="checkbox4" class="inpa" /> <label for="checkbox4">星期三</label>
				<br />
				<input type="checkbox" name="weeks" value="5" id="checkbox5" class="inpa" /> <label for="checkbox5">星期四</label> 
				<input type="checkbox" name="weeks" value="6" id="checkbox6" class="inpa" /> <label for="checkbox6">星期五</label>
				<input type="checkbox" name="weeks" value="7" id="checkbox7" class="inpa" /> <label for="checkbox7">星期六</label>
			</p>
			<p>选择时间:</p>
			<p style="margin-left: 30px;">
				开始时间:<input type="text" class="Data-Figure2" id="startDate" name="startDate" /> 
				结束时间:<input type="text" id="endDate" 	name="endDate" class="Data-Figure2" />
			</p>
			<p id="tishi">提示：连续7天运动不利于身体健康，一周至少安排1天休息</p>
		</form>
		<p>
			<button class="buttoma">确定</button>
			<button class="buttomb">取消</button>
		</p>
	</div>
</body>
</html>
