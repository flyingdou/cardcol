<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="减肥健身计划，哑铃健身计划，健身训练计划，健身方法" />
<meta name="description" content="社区" />
<title>健身E卡通_健身计划</title>
<s:include value="/share/meta.jsp"/>
<link rel="stylesheet" type="text/css" href="css/fitnessplan.css" />
<script type="text/javascript" src="script/jquery.flash.js"></script>
<script type="text/javascript">
$.fx.speeds._default = 1000;
var states = {};
$(function() {
	$('.calendar').datepicker({autoSize: true,defaultDate:'<s:property value="course.planDate"/>',showMonthAfterYear:true,changeMonth:true,changeYear:true,onChangeMonthYear: switchYearMonth, onSelect: switchDate});
	$('.lookup').css('cursor', 'pointer');
	$('.ondelete').css('cursor', 'pointer');
	$('.onedit').css('cursor', 'pointer');
	$('#ptabs').tabs();
	$('#ptabs').removeClass('ui-widget-content ui-corner-all');
	$('#ptabs .ui-tabs-nav').removeClass('ui-widget-header ui-corner-all');
	$('#altdiv').dialog({autoOpen: false,width: 510, show: "blind", hide: "blind",modal:true});
	$('#dialog_altdiv1').dialog({autoOpen: false,width: 540, show: "blind", hide: "blind",modal:true,resizable: false});
	//$(".ui-dialog-titlebar").hide(); 
	$('input[type="button"][name="add"]').click(function(){
		if ($('#detail').css('display') == 'none') {
			alert('请先添加课程后再添加动作！');
			return;
		}
		var objs = $('input[type="checkbox"][id^="action"]:checked');
		if (!objs || objs.length <= 0) return;
		var $obj, htmls, len;
		for (var i = 0 ; i < objs.length; i++) {
			$obj = $(objs[i]);
			//if (isExist($obj.attr('value'))) continue;
			len = $('#actionTable').find('tr').length - 1;
			htmls = $('#samplePlan').html().replace(new RegExp("TYPE", "gm"), $obj.attr("pName"));
			htmls = htmls.replace(new RegExp("ACTIONID", "gm"), $obj.attr("value")).replace(new RegExp("SORT", "gm"), len);
			htmls = htmls.replace(new RegExp("ACTION", "gm"), $obj.attr("aName")).replace(new RegExp("XXXX", "gm"), len);
			$('#actionTable').find('tr:last').after(htmls);
		}
		sortIcon();
	});
	$('select[id="partId"]').change(function(){
		var key = $(this).val();
		$.ajax({url: 'workoutwindow!switchPart.asp',type: 'post', data: 'partId=' + key, dataType: 'xml',
			success: function(xml){
				$('#actions').children('tr').remove();
				appendAction(xml);
			}
		});
	});
	$('.selectmember').change(function(){
		var member = $(this).val();
		$('#member').val(member);
		$('#memberId').val(member);
		var date = $('#planDate').val();
		$.ajax({type: 'post', url: 'workoutwindow!switchMember.asp', data: 'member=' + member + '&planDate=' + date, 
			success: function(msg){
				$('#divdetail').html(msg);
			}
		});
	});
});
function lookup(the){
	var $td = $(the).parents('td').prev('td').children('input:eq(0)');
	$('.action_img').html("<img src='" + $td.attr('image') + "'/>");
	$('.action_desc').html('动作描述：' + $td.attr('descr'));
	$('.action_title').html($td.attr('aName'));
	$('.action_anm').html('');
	$('#dialog_altdiv1').dialog({autoOpen: true, title: $td.attr('aName')});
	$('.action_anm').flash({src: $td.attr('flash')})
}
function isExist(val) {
	$('#actionTable').find('tr').each(function(){
		if ($(this).children('td:eq(0)').children('input').length > 0) {
			var v = $(this).children('td').children('input:eq(0)').val();
			alert('val = ' + val + ' , v = ' + v);
			if (val == v) {
				return true;
			}
		}
	});
	return false;
}
function sortIcon(){
	var $tr = $('#actionTable').find('tr');
	var len = $tr.length, $div;
	for (var i = 1; i < len; i++) {
		$div = $($tr[i]).children('td:eq(0)').children('div');
		$($tr[i]).children('td:eq(0)').children('input:eq(1)').val(i);
		if (i == 1) {
			if (i < len) {
				$div.html("<img src='images/allow5.gif' onclick='javascript:onDown(this);'/>");
			}
		} else {
			if (i < len - 1) {
				$div.html("<img src='images/allow4.gif' onclick='javascript:onUp(this);'/><img src='images/allow5.gif' onclick='javascript:onDown(this);'/>");
			} else {
				$div.html("<img src='images/allow4.gif' onclick='javascript:onUp(this);'/>");
			}
		}
	}
}
function switchYearMonth(year, month, inst) {
	loadMask();
	var date = $('.calendar').datepicker("getDate").getDate();
	if (month.length < 2) month = '0' + month;
	if (date.length < 2) date = '0' + date;
	var planDate = year + '-' + month +'-'+ date;
	$('#planDate').val(planDate);
	var params = 'planDate=' + planDate + '&member=' + $('#member').val();
	$.ajax({type : 'post',url : 'workoutwindow!switchYearMonth.asp',data : params, dataType: 'json',
		success : function(resp) {
			removeMask();
			states = resp;
			setStatus();
		}
	});
}
function switchDate(dateText, inst) {
	loadMask();
	$('#planDate').val(dateText);
	$.ajax({type : 'post', url : 'workoutwindow!switchDate.asp', data : 'planDate=' + dateText + '&member=' + $('#member').val(),
		success : function(resp) {
			removeMask();
			$('#divdetail').html(resp);
			setStatus();
			//初始化时默认第一个
			$('#divcourses').children('div').each(function(index){
				if(index != 0){
					$(this).children('select').attr('disabled', true);
				}
			});
		}
	});
}
function setStatus() {
	var days = $(".calendar .ui-state-default"), val, j = 1;
	var curDate = $('.calendar').datepicker('getDate').getDate();
	for ( var i = 0; i < days.length; i++, j++) {
		val = states['day' + j];
		if (val == 1) {
			$(days[i]).css('background', '#008000');
		} else if (val == 2) {
			$(days[i]).css('background', '#ff0000');
		} else {
			$(days[i]).css('background', '#e6e6e6');
		}
		if(j == curDate){
			$(days[i]).css("background","#fcfcfc");
			$(days[i]).css("color","#000000");
		}
	}
}
function switchProject(pid){
	$.ajax({url: 'workoutwindow!switchProject.asp',type: 'post', data: 'projectId=' + pid,
		success: function(xml){
			$('#partId').empty();
			$('#actions').children('tr').remove();
			$('#partId').append("<option value=''></option>");
			$(xml).find('part').each(function(item){
				$('#partId').append("<option value=\""+ $(this).attr('id') + "\">" + $(this).attr('name') + "</option>");
			});
			appendAction(xml);
		},
		error: function(xml){
			alert('error');
		}
	})
}
function appendAction(xml){
	$(xml).find('action').each(function(){
		var htmls = '<tr><td>';
		htmls += '<input type="checkbox" id="action' + $(this).attr('id') + '" name="button" class="button" value="';
		htmls += $(this).attr('id') + '" partId="' + $(this).attr('pId') + '" partName="' + $(this).attr('partName');
		htmls += '" flash="' + $(this).attr('flash');
		htmls += '" aName="' + $(this).attr('name') + '" pId="' + $(this).attr('pId') + '" pName="' + $(this).attr('pName');
		htmls += '" image="' + $(this).attr('image') + '" video="' + $(this).attr('video') + '" descr="' + $(this).attr('descr');
		htmls += '" mode="' + $(this).attr('mode') + '"/><label for="action' + $(this).attr('id') + '">' + $(this).attr('name');
		htmls += '</label></td><td align="right"><a href="#" onclick="javascript:lookup(this)">查看</a></td></tr>';
		$('#actions').append(htmls);
	});
}
function onUp(the){
	$(the).parents('tr').prev('tr').before($(the).parents('tr'));
	sortIcon();
}
function onDown(the){
	$(the).parents('tr').next('tr').after($(the).parents('tr'));
	sortIcon();
}
function onClosea(){
	$('#dialog').dialog('close');
}
function onCloseb(){
	$('#clea').dialog('close');
}
function onClosec(){
	$('#shar').dialog('close');
}
function cloalt(){
$('#altdiv').dialog('close');
}
function onSave(){
	loadMask();
	var params = $('#planform').serialize();
	$.ajax({type: 'post', url: 'workoutwindow!save.asp', data: params, 
		success: function(html){
			removeMask();
			$('#divdetail').html(html);	
		}
	});
}
function onCopy(){
	loadMask();
	var pdate = $('#planDate').val();
	var params = $('#copyform').serialize();
	params += '&course.id=' + $('#courseId').val() + '&planDate=' + pdate + '&member=' + $('#memberId').val();
	$.ajax({type: 'post', url: 'workoutwindow!copy.asp', data: params, dataType: 'json',
		success: function(msg){
			removeMask();
			if (msg.success === true) {
				alert('当前计划已经成功复制到指定的日期中！');
				$("#dialog").dialog( "close" );
				states = msg.message;
				setStatus();
			} else {
				alert(msg.message);
			}
		}
	});
}
function onClean(){
	loadMask();
	var params = $('#cleanform').serialize();
	params += '&course.id=' + $('#courseId').val() + '&member=' + $('#memberId').val() + '&planDate=' + $('#planDate').val();
	$.ajax({type: 'post', url: 'workoutwindow!clean.asp', data: params, dataType: 'json',
		success: function(msg){
			if (msg.success === true) {
				alert('所选日期内的所有计划已经成功清除！');
				$("#clea").dialog( "close" );
				$.ajax({type : 'post', url : 'workoutwindow!switchDate.asp', data : 'planDate=' + $('#planDate').val() + '&member=' + $('#memberId').val(),
					success : function(resp) {
						$('#divdetail').html(resp);
						setStatus();
					}
				});
				states = msg.message;
				setStatus();
				removeMask();
			} else {
				alert(json.message);
			}
		}
	});
}
function onShare(){
	var param = 'id=' + $('#courseId').val();
	$.ajax({type: 'post', url: 'workoutwindow!share.asp', data: param,
		success: function(msg){
			if (msg == 'OK') {
				$('#courseShare').val(1);
				alert('当前计划已经成功分享！');
			} else {
				alert('分享计划出错，可能的原因为：' + msg);
			}
		}
	});
}
function autoGen(){
	loadMask();
	$.ajax({type: 'post', url: 'workoutwindow!autoGen.asp', 
		success: function(msg){
			removeMask();
			if (msg == 'OK') {
				alert('您的计划已经自己生成，请刷新本页面查看！');
			} else {
				alert('计划生成出错，可能的原因为：' + msg);
			}
		}
	});
}
function editCourse(the){
	loadMask();
	var $div = $(the).parents('div');
	var key = $div.attr('id');
	$('#divcourses').children('div').each(function(){
		$(this).children('select').attr('disabled', true);
	});
	$div.children('select').attr('disabled', false);
	$('#courseId').val(key);
	$('#costType').val($div.attr('costType'));
	$('#coachId').val($div.attr('coachId'));
	$('#doneDate').val($div.attr('doneDate'));
	$('#costs').val($div.attr('costs'));
	$('#memo').val($div.attr('memo'));
	$('#place').val($div.attr('place'));
	$('#color').val($div.attr('color'));
	$('#count').val($div.attr('count'));
	$('#joinNum').val($div.attr('joinNum'));
	$('#mode').val($div.attr('mode'));
	$('#modeType').val($div.attr('modeType'));
	$('#modeValue').val($div.attr('modeValue'));
	$('#weekOf').val($div.attr('weekOf'));
	$('#repeatWhere').val($div.attr('repeatWhere'));
	$('#repeatStart').val($div.attr('repeatStart'));
	$('#repeatNum').val($div.attr('repeatNum'));
	$('#repeatEnd').val($div.attr('repeatEnd'));
	$('#hasReminder').val($div.attr('hasReminder'));
	$('#reminder').val($div.attr('reminder'));
	$('#groupBy').val($div.attr('groupBy'));
	$.ajax({url:'workoutwindow!edit.asp', type:'post', data: 'id=' + key, 
		success: function(msg){
			$('#right-2').html(msg);
			removeMask();
		}
	});
}
function delCourse(the){
	var $div = $(the).parents('div');
	var key = $div.attr('id');
	if (key) {
		if (confirm('您是否确认删除当前课程？')) {
			$.ajax({url:'workoutwindow!delete.asp', type:'post', data: 'id=' + key, 
				success: function(msg) {
					if (msg == 'OK') {
						loadOtherCourse($div);
						alert('当前数据已经成功删除！');
					} else {
						alert('当前数据删除错误，可能的原因为：' + msg);
					}
				}
			});
		}
	} else {
		loadOtherCourse($div);
	}
}
function loadOtherCourse($div){
	$div.prev('div');
}
function onDelete(the){
	var $tr = $(the).parents('tr');
	var id = $tr.children('td:eq(0)').children('input:eq(2)').val();
	if (!id || id == '') {
		$tr.remove();
		return;
	}
	if (confirm('您是否删除当前动作？')) {
		$.ajax({url: 'workoutwindow!deleteworkout.asp',type:'post',data: 'id=' + id, 
			success: function(){
				$tr.remove();
				sortIcon();
			}
		})
	}
}
function onEdit(the) {
	var $tr = $(the).parents('tr');
	var id = $tr.children('td:eq(0)').children('input:eq(2)').val();
	var sort = $tr.children('td:eq(0)').children('input:eq(1)').val();
	if (id == '') {
		loadMask();
		var params = $('#planform').serialize();
		$.ajax({type: 'post', url: 'workoutwindow!save.asp', data: params, 
			success: function(html){
				removeMask();
				$('#divdetail').html(html);	
				$('#actionTable').find('tr').each(function(){
					if ($(this).children('td:eq(0)').children().length >= 4){
						if ($(this).children('td:eq(0)').children('input:eq(1)').val() == sort) {
							id = $(this).children('td:eq(0)').children('input:eq(2)').val();
						}
					}
				});
				$.ajax({url: 'workoutwindow!loadGroup.asp',type:'post',data:'id=' + id,
					success: function(resp) {
						$('#altdiv').html(resp);
						$('#altdiv').dialog('open');
					}
				});
			}
		});
	} else {
		$.ajax({url: 'workoutwindow!loadGroup.asp',type:'post',data:'id=' + id,
			success: function(resp) {
				$('#altdiv').html(resp);
				$('#altdiv').dialog('open');
			}
		});
	}
}
function doneCourse(){
	if ($('input[name="course.doneDate"]').val() == '') {
		alert('请先输入完成时间再确定！');
		$('input[name="course.doneDate"]').focus();
		return;
	}
	if ($('#planDate').val() > $('input[name="course.doneDate"]').val()) {
		$('input[name="course.doneDate"]').focus();
		alert('您输入的完成时间小于计划的时间，请重新输入！');
		return;
	}
	var params = $('#planform').serialize();
	$.ajax({type: 'post', url: 'workoutwindow!save.asp', data: params, 
		success: function(html){
			removeMask();
			$('#divdetail').html(html);
		}
	});
}
function saveGroup(){
	loadMask();
	$('#tabgroup').find('tr').each(function(){
		if ($(this).children('td:eq(0)').children('input').length == 3) {
			$(this).children('td:eq(0)').children('input:eq(2)').val(workId);
		}
	});
	var params = $('#editform').serialize() + '&id=' + workId;
	$.ajax({type:'post',url:'workoutwindow!saveDetail.asp', data: params, 
		success:function(resp){
			$('#altdiv').html(resp);
			var key = $('#courseId').val();
			$.ajax({url:'workoutwindow!edit.asp', type:'post', data: 'id=' + key, 
				success: function(msg){
					$('#right-2').html(msg);
				}
			});
			removeMask();
			alert('当前组次保存成功！');
		}
	});
}
function onAddGroup(the){
	var $tr = $(the).parents('tr');
	var len = $tr.parents('table').find('tr').length - 3;
	if (mode == 0) {
		$tr.before(getYY(len));
	} else if (mode == 1) {
		$tr.before(getLL(len));
	} else if (mode == 2) {
		$tr.before(getRR(len));
	} else {
		$tr.before(getPLT(len));
	}
}
function onDelGroup(the){
	var $tr = $(the).parents('tr');
	var mid = $tr.children('td:eq(0)').children('input:eq(1)').val();
	if (!mid) {
		$tr.remove();
		return;
	}
	if (confirm('您是否确认删除当前组次？')) {
		$.ajax({url: 'workoutwindow!deleteGroup.asp',type:'post',data:'id=' + mid,
			success: function(resp) {
				$tr.remove();
				alert('当前组次已经成功删除！');
			}
		});
	}
}

function getYY(i){//有氧
	var htmls = '<tr><td><input name="details['+i+'].planDistance" /><input type="hidden" name="details['+i+'].id"/>';
	htmls += '<input type="hidden" name="details['+i+'].workout.id"/></td><td><input name="details['+i+'].planDuration" /></td>';
	htmls += '<td><input name="details['+i+'].intensity"/></td><td><input name="details['+i+'].actualDistance"/></td>';
	htmls += '<td><input name="details['+i+'].actualDuration" /></td><td><a href="#" onclick="onDelGroup(this)"  id="delect"></a></td></tr>';
	return htmls;
}

function getLL(i){//力量 
	var htmls = '<tr><td><input name="details['+i+'].planWeight" /><input type="hidden" name="details['+i+'].id"/>';
	htmls += '<input type="hidden" name="details['+i+'].workout.id"/></td><td><input name="details['+i+'].planTimes" /></td>';
	htmls += '<td><input name="details['+i+'].planIntervalSeconds"/></td><td><input name="details['+i+'].actualWeight"/></td>';
	htmls += '<td><input name="details['+i+'].actualTimes" /></td><td><input name="details['+i+'].actualIntervalSeconds" /></td>';
	htmls += '<td><a href="#" onclick="onDelGroup(this)" id="delect"></a></td></tr>';
	return htmls;
}

function getRR(i){//柔韧
	var htmls = '<tr><td><input name="details['+i+'].planDuration" /><input type="hidden" name="details['+i+'].id"/>';
	htmls += '<input type="hidden" name="details['+i+'].workout.id"/></td><td><input name="details['+i+'].planTimes" /></td>';
	htmls += '<td><input name="details['+i+'].actualDuration"/></td><td><input name="details['+i+'].actualTimes"/></td>';
	htmls += '<td><a href="#" onclick="onDelGroup(this)" id="delect"></a></td></tr>';
	return htmls;
}

function getPLT(i){//普拉提
	
}
function onPrev(key){
	loadMask();
	var mid = null;
	var $trs = $('#actionTable').children('tbody').children('tr');
	for (var i = 0 ; i < $trs.length ; i++) {
		if ($($trs[i]).children('td:eq(0)').children('input:eq(2)').val() == key) {
			break;
		} else {
			mid = $($trs[i]).children('td:eq(0)').children('input:eq(2)').val();
		}
	}
	if (mid == null) {
		alert('已经到了第一个动作，请看下一个动作！');
		removeMask();
		return;
	}
	$.ajax({url: 'workoutwindow!loadGroup.asp',type:'post',data:'id=' + mid,
		success: function(resp) {
			$('#altdiv').html(resp);
			removeMask();
		}
	});
}
function onNext(key){
	loadMask();
	var mid = null;
	var $trs = $('#actionTable').children('tbody').children('tr');
	for (var i = $trs.length - 1; i >= 0 ; i--) {
		if ($($trs[i]).children('td:eq(0)').children('input:eq(2)').val() == key) {
			break;
		} else {
			mid = $($trs[i]).children('td:eq(0)').children('input:eq(2)').val();
		}
	}
	if (mid == null) {
		alert('已经到了最后一个动作，请看上一个动作！');
		removeMask();
		return;
	}
	$.ajax({url: 'workoutwindow!loadGroup.asp',type:'post',data:'id=' + mid,
		success: function(resp) {
			$('#altdiv').html(resp);
			removeMask();
		}
	});
}
$(document).ready(function(){
	//初始化时默认第一个
	$('#divcourses').children('div').each(function(index){
		if(index != 0){
			$(this).children('select').attr('disabled', true);
		}
	});
});
</script>
</head>
<body>
	<s:include value="/share/window-header.jsp"/>
	<div id="content">
		<div id="left">
			<div id="left-1">
				    <div class="calendar"></div>
				<p>
					<input type="button" name="button" class="button1" /> 已完成 
					<input type="button" name="button" class="button2" /> 已计划
					<input type="button" name="button" class="button3" /> 已分享
				</p>
			</div>
			<div style="margin-top:8px;">
				<s:if test="toMember.role == \"S\"">
					<span style="margin-left:5px;">选择会员</span>：<s:select list="#request.members" value="member" listKey="id" listValue="name" cssClass="selectmember"/>
				</s:if>
				<s:else>
					<s:if test="isChange ==  \"Y\"">
					<a href="#"><img src="images/auto.png" onclick="autoGen()"/></a>
					</s:if>
				</s:else>
			</div>
			<div id="left-2">
				<h1>添加健身项目</h1>
				<div class="first" id="ptabs">
					<ul style="font-size:12px;">
						<s:iterator value="#request.projects">
							<li><a href="#tabs1" onclick="javascript:switchProject(<s:property value="id"/>)"><s:property value="shortName" /></a></li>
						</s:iterator>
					</ul>

					<div class="power" id="tabs1" style="border: 1px solid #aaaaaa;">
						<p class="position">
							部位：<s:select list="#request.parts" listKey="id" listValue="name" headerKey="" headerValue="" id="partId" theme="simple"/>
						</p>
						<div class="table">
							<table width=160>
								<tbody id="actions">
									<s:iterator value="#request.actions">
									<tr>
										<td>
											<input type="checkbox" name="actionIds" class="button" value="<s:property value="id"/>" 
												id="action<s:property value="id"/>" mode="<s:property value="part.project.mode"/>" 
												pId="<s:property value="part.project.id"/>" pName="<s:property value="part.project.name"/>" 
												partId="<s:property value="part.id"/>" partName="<s:property value="part.name"/>" 
												aName="<s:property value="name"/>" image="<s:property value="image"/>" flash="<s:property value="flash"/>"
												video="<s:property value="video"/>" descr="<s:property value="descr"/>"/>
											<label for="action<s:property value="id"/>"><s:property value="name"/></label>
										</td>
										<td align=right><a class="lookup" onclick="lookup(this)">查看</a></td>
									</tr>
									</s:iterator>
								</tbody>
							</table>
						</div>
					</div>
					<s:if test="isChange ==  \"Y\"">
					<p class="add">
						<input type="button" name="add" class="add" value="添加" />
					</p>
					</s:if>
				</div>
			</div>
		</div>
		<div id="divdetail">
			<s:include value="/homeWindow/workout_course.jsp"></s:include>
		</div>
	</div>
	<s:include value="/share/footer.jsp"/>
	<div id="sampleCourse" style="display:none;">
		<div coachId='' memberId='' planDate='' costType='' courseShare='' doneDate=''>
			课程名称：<s:select list="#request.courseinfos" name="course.courseInfo.id" listKey="id" listValue="name" theme="simple" id="courseInfoIdXXXX"  cssStyle="border:1px solid #ccc; width:120px; height:20px;"/>
			<span>开始时间:</span><s:select list="#request.times" name="course.startTime" listKey="key" listValue="value" theme="simple" id="startTimeXXXX" cssStyle="border:1px solid #ccc; width:80px; height:20px;"/>
			<span>结束时间:</span><s:select list="#request.times" name="course.endTime" listKey="key" listValue="value" theme="simple" id="endTimeXXXX"  cssStyle="border:1px solid #ccc; width:80px; height:20px;"/>
			<s:if test="isChange ==  \"Y\"">
			<a href="#" class="inpa1" onclick="delCourse(this)">删除</a>
			</s:if> 
			<a href="#" class="inpa1" onclick="editCourse(this)">编辑健身计划</a>
		</div>
	</div>
	<table width="86%" cellspacing="1" cellpadding="0" border="0" class="table1" style="display:none;">
		<tbody id="samplePlan">      	
			<tr>
				 <td>
				 	<input type="hidden" name="workouts[XXXX].action.id" value="ACTIONID"/>
				 	<input type="hidden" name="workouts[XXXX].sort" value="SORT"/>
				 	<input type="hidden" name="workouts[XXXX].id" value=""/>
				 	<input type="hidden" name="workouts[XXXX].action.part.project.mode" value=""/>
					<div></div>
				</td>
				<td>ACTION</td>
				<td>TYPE</td>
				<td>DESCR</td>
				<s:if test="isChange ==  \"Y\"">
				<td>
				   <a onclick="javascript:onDelete(this);" id="colotoa">删除</a>
				   <a onclick="javascript:onEdit(this);" id="colotoa" style="padding-right:5px;" >编辑</a>
				</td>
				</s:if>
			</tr>
		</tbody>
	</table>
	<div id="dialog_altdiv1" title="动作详情" style="display:block;">
		<div class="altleft" style="margin-left:3px; margin-top:-5px;">
			<div class="altleft1">
				<div class="action_img" ></div>
			</div>
			<div class="action_anm">
			</div>
		</div>
		<div class="action_desc">动作描述：在本页面左侧“添加健身项目”面板中选择并添加健身项目（动作），形成健身计划表在本页面左侧“添加健身项目”面板中选择并添加健身项目（动作），形成健身计划表</div>
	</div>
	<div id="altdiv" title="训练动作详情" style="display:block;">

	</div>

	<div id="dialog" title="健身计划复制">
		<form id="copyform" name="copyform" method="post">
			<s:if test="toMember.role == \"S\"">
			<p>将本计划复制到其它会员</p>
			<p><s:select name="toMember" theme="simple" list="#request.members" listKey="id" listValue="name"/></p>
			</s:if>
			<p>将本计划复制到以下其它日期</p>
			<p>
				<input type="checkbox" name="weeks" value="1" id="checkbox" class="inpa" /> <label for="checkbox">星期日</label>
				<input type="checkbox" name="weeks" value="2" id="checkbox2" class="inpa" /> <label for="checkbox2">星期一</label>
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
				结束时间:<input type="text" id="endDate" name="endDate" class="Data-Figure2" />
			</p>
			<p id="tishi">提示：连续7天运动不利于身体健康，一周至少安排1天休息</p>
		</form>
		<p>
			<button class="buttoma" onclick="onCopy();">确定</button>
			<button class="buttomb" onclick="onClosea()">取消</button>
		</p>
	</div>
	
	<div id="clea" title="健身计划清除">
		<form id="cleanform" name="cleanform" method="post" action="">
			<s:if test="toMember.role == \"S\"">
			<p>请先选择需要清除计划的会员</p>
			<p><s:select name="toMember" theme="simple" list="#request.members" listKey="id" listValue="name"/></p>
			</s:if>
			<p>请选择您需要清除计划的时间范围</p>
			<p style="margin-left:30px;">
				开始时间:<input type="text" name="startDate" class="Data-Figure2" id="startDateb" />
				结束时间: <input type="text" id="endDateb" name="endDate" class="Data-Figure2"/>
			</p>
		</form>
		<p>
			<button class="buttoma" onclick="onClean();">确定</button>
			<button class="buttomb" onclick="onCloseb()">取消</button>
		</p>
	</div>
	
	<div id="shar" title="健身计划分享">
		<p style="font-size:14px; text-align:center;">您今天的健身计划将分享到卡库网的健身计划栏目</p>
		<p>
			<button class="buttoma" onclick="onShare();">确定</button>
			<button  class="buttomb" onclick="onClosec()">取消</button>
		</p>
	</div>
</body>
</html>
