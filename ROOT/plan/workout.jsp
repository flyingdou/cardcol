<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="健身,健身计划,哑铃健身计划,减肥计划表,力量训练,有氧训练,柔韧训练,增肌" />
<meta name="description" content="健身教练的健身计划中有健身教练为健身会员设定的健身计划,其中有减肥计划表,力量训练,有氧训练,柔韧训练,增肌等。" />
<title>健身计划</title>
<link rel="stylesheet" type="text/css" href="css/fitnessplan.css" />
<s:include value="/share/meta.jsp" />
<link rel="stylesheet" type="text/css" href="css/auto.css" />
<link type="text/css" rel="stylesheet" href="css/pulicstyle.css" />
<script type="text/javascript" src="script/jquery.flash.js"></script>
<script type="text/javascript" src="script/auto.js"></script>
<script src="script/ckeditor/ckeditor.js" type="text/javascript"></script>
<script type="text/javascript" src="script/DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="script/uploadPreview.js"></script>
<script src="script/checkInput.js" type="text/javascript"></script>
<script type="text/javascript" src="script/cyberplayer.min.js"></script>
<script src="script/audio.js?v=1100000" type="text/javascript"></script>
<script type="text/javascript">
$.fx.speeds._default = 1000;
var states = {}, fv;
var memberId = <s:property value="member"/>;
$(function() {
	$('.calendar').datepicker({autoSize: true,defaultDate:'<s:property value="course.planDate"/>',showMonthAfterYear:true,changeMonth:true,changeYear:true,onChangeMonthYear: switchYearMonth, onSelect: switchDate});
	$('.lookup').css('cursor', 'pointer');
	$('.ondelete').css('cursor', 'pointer');
	$('.onedit').css('cursor', 'pointer');
	$('.onmusic').css('cursor', 'pointer');
	$('#ptabs').tabs();
	$('#ptabs').removeClass('ui-widget-content ui-corner-all');
	$('#ptabs .ui-tabs-nav').removeClass('ui-widget-header ui-corner-all');
	$('#altdiv').dialog({autoOpen: false,width: 510, show: "blind", hide: "blind",modal:true,resizable: false});
	$('#altdiv2').dialog({autoOpen: false,width: 510, show: "blind", hide: "blind",modal:true,resizable: false});
	$('#dialog_altdiv1').dialog({autoOpen: false,width: 540, show: "blind", hide: "blind",modal:true,resizable: false});
	$('input[type="button"][name="add"]').click(function(){
		if (!$('SELECT[id^="courseInfoId"]:not(id$="XXXX")').val()) {
			alert('请先添加课程后再添加动作！');
			return;
		}
		var objs = $('input[type="checkbox"][id^="action"]:checked');
		if (!objs || objs.length <= 0){
			alert('请勾选需要添加的健身项目！');
			return;
		}
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
		$(document).mask('请稍候，正在保存数据......');
		var params = $('#planform').serialize();
		$.ajax({type: 'post', url: 'workout!save.asp', data: params, 
			success: function(html){
				$('#divdetail').html(html);	
				$(document).unmask();
				$('#actionTable').find('tr').each(function(){
					if ($(this).children('td:eq(0)').children().length >= 4){
						if ($(this).children('td:eq(0)').children('input:eq(1)').val() == sort) {
							id = $(this).children('td:eq(0)').children('input:eq(2)').val();
						}
					}
				});
			}
		});
		$(":checkbox").each(function(){
			$(this).attr("checked",false);
		});
		sortIcon();
});
	$('select[id="partId"]').change(function(){
		var key = $(this).val();
		$.ajax({url: 'workout!switchPart.asp',type: 'post', data: 'partId=' + key+'&projectId='+$('#projectId').val(), 
			success: function(xml){
				$('#actions').children('tr').remove();
				appendAction($.parseJSON(xml));
			}
		});
	});
	$('.selectmember').change(function(){
		$(document).mask('请稍候，正在加载页面......');
		var member = $(this).val();
		$('#member').val(member);
		$('#memberId').val(member);
		var date = $('#planDate').val();
		$.ajax({type: 'post', url: 'workout!switchMember.asp', data: 'member=' + member + '&planDate=' + date, 
			success: function(msg){				
				$(document).unmask();
				$('#divdetail').html(msg);
			}
		});
	});
	var editPlan = '<s:property value="editPlan"/>';
	if(editPlan != null && editPlan == '1'){
		$('#divdetail').hide();
		$('#divplanset').show();
		$.ajax({type: 'post', url: 'release.asp', data: {type: 1,'release.id':'<s:property value="planRelease.id"/>'}, 
			success: function(msg){
				$('#divplanset').html(msg);
			}
		});
	}
});
function lookup(the){
	var $td = $(the).parents('td').prev('td'), key = $td.children('input:eq(0)').val();
	var actionName = $td.children('input:eq(0)').attr("aname");
	$.ajax({url: 'workout!look.asp', type: 'post', data: 'id=' + key,
		success: function(msg) {
			$('#dialog_altdiv1').dialog("open");
			$('#dialog_altdiv1').html(msg);
			$('#dialog_altdiv1').dialog('option', 'title', actionName);
		}
	});
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
	$(document).mask('请稍候，正在加载数据......');
	var date = $('.calendar').datepicker("getDate").getDate();
	if (month.length < 2) month = '0' + month;
	if (date.length < 2) date = '0' + date;
	var planDate = year + '-' + month +'-'+ date;
	var params = 'planDate=' + planDate + '&member=' + $('#member').val();
	$.ajax({type : 'post',url : 'workout!switchYearMonth.asp',data : params, dataType: 'json',
		success : function(resp) {
			states = resp;
			setStatus();
			$('#planDate').val(planDate);
			$(document).unmask();
		}
	});
}
function switchDate(dateText, inst) {
	$(document).mask('请稍候，正在加载页面......');
	$.ajax({type : 'post', url : 'workout!switchDate.asp', data : 'planDate=' + dateText + '&member=' + $('#member').val(),
		success : function(resp) {
			$(document).unmask();
			$('#divdetail').show();
			$('#divplanset').hide();
			$('#planDate').val(dateText);
			$('#member').val(memberId);
			$('#memberId').val(memberId);
			$('#divdetail').html(resp);
			setStatus();
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
			$(days[i]).css("background","#fcfc00");
			$(days[i]).css("color","#000000");
		}
	}
}
function switchProject(pid){
	$('#projectId').val(pid);
	$.ajax({url: 'workout!switchProject.asp',type: 'post', data: 'projectId=' + pid,
		success: function(xml){
			var _jsons = $.parseJSON(xml);
			$('#partId').empty();
			$('#actions').children('tr').remove();
			$('#partId').append("<option value=''></option>");
			for (var i = 0; i < _jsons.length; i++) {
				var _json = _jsons[i];
				$('#partId').append("<option value=\""+ _json.id + "\">" + _json.name + "</option>");
				appendAction(_json.actions);
			}
		},
		error: function(xml){
			alert('error');
		}
	})
}
function appendAction(actions){
	for (var j = 0; j < actions.length; j++) {
		var _action = actions[j], htmls = '<tr><td>';
		htmls += '<input type="checkbox" id="action' + _action.id + '" name="button" class="button" value="';
		htmls += _action.id + '" partId="' + _action.pId + '" partName="' + _action.partName + '" descr="' + _action.descr;
		htmls += '" aName="' + _action.name + '" pId="' + _action.pId + '" pName="' + _action.pName;
		htmls += '" mode="' + _action.mode + '"/><label for="action' + _action.id + '">' + _action.name;
		htmls += '</label></td><td align="right"><a href="#" onclick="javascript:lookup(this)">查看</a></td></tr>';
		$('#actions').append(htmls);
	}
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

function rePic(){
	$('#altdiv2').dialog('close');
}

function onSave(){
	$(document).mask('请稍候，正在保存数据......');
	var params = $('#planform').serialize();
	$.ajax({type: 'post', url: 'workout!save.asp', data: params, 
		success: function(html){
// 			switchDate($('#planDate').val());
			$('#divdetail').html(html);
			$(document).unmask();
			alert("保存成功！");
		}
	});
}
function onCopy(){
	if ($('#startDate').val() == '') {
		alert('请输入复制的开始日期！');
		$('#startDate').focus();
		return;
	}
	if ($('#endDate').val() == '') {
		alert('请输入复制的结束日期！');
		$('#endDate').focus();
		return;
	}
	$(document).mask('请稍候，正在保存数据......');
	var pdate = $('#planDate').val();
	var params = $('#copyform').serialize();
	params += '&course.id=' + $('#courseId').val() + '&planDate=' + pdate + '&member=' + $('#member').val();
	$.ajax({type: 'post', url: 'workout!copy.asp', data: params, dataType: 'json',
		success: function(msg){
			$(document).unmask();
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
	$(document).mask('请稍候，正在清除数据......');
	var params = $('#cleanform').serialize();
	params += '&course.id=' + $('#courseId').val() + '&member=' + $('#member').val() + '&planDate=' + $('#planDate').val();
	$.ajax({type: 'post', url: 'workout!clean.asp', data: params, dataType: 'json',
		success: function(msg){
			$(document).unmask();
			if (msg.success === true) {
				alert('所选日期内的所有计划已经成功清除！');
				states = msg.message;
				setStatus();
				$("#clea").dialog( "close" );
				loadMask();
				$.ajax({type : 'post', url : 'workout!switchDate.asp', data : 'planDate=' + $('#planDate').val() + '&member=' + $('#member').val(),
					success : function(resp) {
						removeMask();
						$('#divdetail').html(resp);
					}
				});
			} else {
				alert(msg.message);
			}
		}
	});
}

function addCourse() {
	$('#divcourses').children('div').each(function(){
		$(this).children('select').attr('disabled', true);
	});
	var len = $('#divcourses').children('div').length;
	var html = $('#sampleCourse').html().replace(new RegExp("XXXX", "gm"), len + "");
	$('#divcourses').append(html);
	$('#planform > input').each(function() {
		var _id = "," + $(this).attr("id") + ",";
		if (",member,memberId,planDate,coachId,".indexOf(_id) == -1)
			$(this).val('');
	});
	$('#detail').css('display', 'block');
// 	$('#emptyMsg').css('display', 'none');
	$('#actionTable').children('tbody').empty();
	$('#courseId').val('');
	var select = $('#divcourses').children('div:eq(' + len + ')').children('select:eq(0)');
	changeTitle(select);
}

function editCourse(the){
	var $div = $(the).parent('div');
	loadCourse($div);
	var select = $div.children('select:eq(0)');
	changeTitle(select);
}

function loadCourse($div) {
	$('#divcourses').children('div').each(function(){
		$(this).children('select').attr('disabled', true);
	});
	$div.children('select').attr('disabled', false);
	var key = $div.attr('id');
	if (!key) {
		$('#courseId').val('');
		$('#actionTable').find('tr:not(:first)').remove();
		return;
	}
	$('#planform > input').each(function(){
		$(this).val($div.attr($(this).attr('id')));
	});
	$('#courseId').val(key);
	$(document).mask('请稍候，正在加载页面......');
	$.ajax({url:'workout!edit.asp', type:'post', data: 'id=' + key, 
		success: function(msg){
			$(document).unmask();
			$('#right-2').html(msg);
		}
	});
}
function delCourse(the){
	var $div = $(the).parent('div');
	var key = $div.attr('id');
	if (key) {
		$(document).mask('请稍候，正在删除课程......');
		if (confirm('您是否确认删除当前课程？')) {
			$.ajax({url:'workout!delete.asp', type:'post', data: 'id=' + key + '&member=' +$('#member').val() + '&planDate=' + $('#planDate').val(), 
				success: function(msg) {
					$('#divdetail').html(msg);
					$(document).unmask();
				},
				failure: function(msg) {
					alert(msg);
				}
			});
		}
	} else {
		switchDate($('#planDate').val());
	}
}
function changeTitle(the) {
	var txt = $(the).find('option:selected').text();
	$('#courseTitle').html(txt);
}

function onDelete(the){
	var $tr = $(the).parents('tr');
	var id = $tr.children('td:eq(0)').children('input:eq(2)').val();
	if (!id || id == '') {
		$tr.remove();
		return;
	}
	if (confirm('您是否删除当前动作？')) {
		$.ajax({url: 'workout!deleteWorkout.asp',type:'post',data: 'id=' + id, 
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
	var actionName = $tr.children('td:eq(1)').html();
	var sort = $tr.children('td:eq(0)').children('input:eq(1)').val();
	$(document).mask('请稍候，正在加载数据......');
	if (id == '') {
		var params = $('#planform').serialize();
		$.ajax({type: 'post', url: 'workout!save.asp', data: params, 
			success: function(html){
				$('#divdetail').html(html);	
				$('#actionTable').find('tr').each(function(){
					if ($(this).children('td:eq(0)').children().length >= 4){
						if ($(this).children('td:eq(0)').children('input:eq(1)').val() == sort) {
							id = $(this).children('td:eq(0)').children('input:eq(2)').val();
						}
					}
				});
				$.ajax({url: 'workout!loadGroup.asp',type:'post',data:'id=' + id,
					success: function(resp) {
						$(document).unmask();
						$('#altdiv').html(resp);
						$('#altdiv').dialog('open');
						$('#altdiv').dialog('option', 'title', actionName);
						$('#sxg').html(actionName);
					}
				});
			}
		});
	} else {
		$.ajax({url: 'workout!loadGroup.asp',type:'post',data:'id=' + id,
			success: function(resp) {
				$(document).unmask();
				$('#altdiv').html(resp);
				$('#altdiv').dialog('open');
				$('#altdiv').dialog('option', 'title', actionName);
				$('#sxg').html(actionName);
			}
		});
	}
}

function onMusic(the) {
	var id = $(the).parent().attr("id");
	$.ajax({url: 'workout!loadMusic.asp',type:'post',data:'id=' + id,
		success: function(resp) {
			$('#altdiv2').html(resp);
			$('#altdiv2').dialog('open');
		}
	});
}

function delMusic(the){
	var $div = $(the).parent('div');
	var key = $div.attr('id');
	if (key) {
		$(document).mask('请稍候，正在删除数据......');
		if (confirm('您是否确认删除当前课程的配乐？')) {
			$.ajax({url:'workout!deleteCourseMusic.asp', type:'post', data: 'id=' + key , 
				success: function(msg) {
					switchDate($('#planDate').val());
					$(document).unmask();
				},
				failure: function(msg) {
					alert(msg);
				}
			});
		}
	} else {
		switchDate($('#planDate').val());
	}
}

function saveGroup(){
	$(document).mask('请稍候，正在保存数据......');
	$('#tabgroup').find('tr').each(function(){
		if ($(this).children('td:eq(2)').children('input').length == 3) {
			$(this).children('td:eq(2)').children('input:eq(2)').val(workId);
		}
	});
	var params = $('#editform').serialize() + '&id=' + workId;
	$.ajax({type:'post',url:'workout!saveDetail.asp', data: params, 
		success:function(resp){
			$('#altdiv').html(resp);
			var key = $('#courseId').val();
			$.ajax({url:'workout!edit.asp', type:'post', data: 'id=' + key, 
				success: function(msg){
					$('#right-2').html(msg);
				}
			});
			$(document).unmask();
			alert('当前组次保存成功！');
		}
	});
}

function onAddGroup(the){
	var $tr = $(the).parents('tr');
	var len = $tr.parents('table').find('tr').length - 2;
	$tr.before(getYY(len));
}
function onDelGroup(the){
	var $tr = $(the).parents('tr');
	var mid = $tr.children('td:eq(0)').children('input:eq(1)').val();
	if (!mid) {
		$tr.remove();
		return;
	}
	if (confirm('您是否确认删除当前组次？')) {
		$.ajax({url: 'workout!deleteGroup.asp',type:'post',data:'id=' + mid,
			success: function(resp) {
				$tr.remove();
				alert('当前组次已经成功删除！');
			}
		});
	}
}

function getYY(i){//有氧
	var htmls = '<tr>';
	htmls+= '<td><input name="details['+i+'].planTimes"/></td><td><input name="details['+i+'].planDuration"/></td>';
	htmls+='<td><input name="details['+i+'].planWeight" /><input type="hidden" name="details['+i+'].id"/>';
	htmls+= '<input type="hidden" name="details['+i+'].workout.id"/></td><td><input name="details['+i+'].planDistance" /></td>';
	htmls+='<td><input name="details['+i+'].planIntervalSeconds" /></td>';
	htmls += '<td><a href="#" onclick="onDelGroup(this)"  id="delect"></a></td></tr>';
	return htmls;
}

function onPrev(key){
	$(document).mask('请稍候，正在加载数据......');
	var mid = null;
	var $trs = $('#actionTable').children('tbody').children('tr');
	var actionName = '';
	for (var i = 0 ; i < $trs.length ; i++) {
		if ($($trs[i]).children('td:eq(0)').children('input:eq(2)').val() == key) {
			break;
		} else {
			mid = $($trs[i]).children('td:eq(0)').children('input:eq(2)').val();
			actionName = $($trs[i]).children('td:eq(1)').html();
		}
	}
	if (mid == null) {
		alert('已经到了第一个动作，请看下一个动作！');
		$(document).unmask();
		return;
	}
	$.ajax({url: 'workout!loadGroup.asp',type:'post',data:'id=' + mid,
		success: function(resp) {
			$('#altdiv').html(resp);
			$('#altdiv').dialog('option', 'title', actionName);
			$('#sxg').html(actionName);
			$(document).unmask();
		}
	});
}
function onNext(key){
	$(document).mask('请稍候，正在保存数据......');
	var mid = null;
	var $trs = $('#actionTable').children('tbody').children('tr');
	var actionName = '';
	for (var i = $trs.length - 1; i >= 0 ; i--) {
		if ($($trs[i]).children('td:eq(0)').children('input:eq(2)').val() == key) {
			break;
		} else {
			mid = $($trs[i]).children('td:eq(0)').children('input:eq(2)').val();
			actionName = $($trs[i]).children('td:eq(1)').html();
		}
	}
	if (mid == null) {
		alert('已经到了最后一个动作，请看上一个动作！');
		$(document).unmask();
		return;
	}
	$.ajax({url: 'workout!loadGroup.asp',type:'post',data:'id=' + mid,
		success: function(resp) {
			$('#altdiv').html(resp);
			$('#altdiv').dialog('option', 'title', actionName);
			$('#sxg').html(actionName);
			$(document).unmask();
		}
	});
}
</script>
</head>
<body>
	<s:include value="/share/header.jsp" />
	<div id="content">
		<div id="left">
			<div id="left-1">
				<div class="calendar"></div>
				<p>
					<input type="button" name="button" class="button1" /> 已完成 <input
						type="button" name="button" class="button2" /> 已计划 <input
						type="button" name="button" class="button3" /> 未完成
				</p>
			</div>
			<div id="left-2">
				<h1>添加健身项目</h1>
				<div class="first" id="ptabs">
					<ul style="font-size: 12px;">
						<s:iterator value="#request.projects">
							<li><a href="#tabs1"
								onclick="javascript:switchProject(<s:property value="id"/>)"><s:property
										value="shortName" /></a></li>
						</s:iterator>
					</ul>
					<input type="hidden" id="projectId" value="<s:property value='#request.projects[0].id'/>" >
					<div class="power" id="tabs1" style="border: 1px solid #aaaaaa;">
						<p class="position">
							部位：
							<s:select list="#request.parts" listKey="id" listValue="name"
								headerKey="" headerValue="" id="partId" theme="simple" />
						</p>
						<div class="table">
							<table width=160>
								<tbody id="actions">
									<s:iterator value="#request.actions">
										<tr>
											<td><input type="checkbox" name="actionIds"
												class="button" value="<s:property value="id"/>"
												id="action<s:property value="id"/>"
												mode="<s:property value="part.project.mode"/>"
												pId="<s:property value="part.project.id"/>"
												pName="<s:property value="part.project.name"/>"
												partId="<s:property value="part.id"/>"
												partName="<s:property value="part.name"/>"
												aName="<s:property value="name"/>" /> <label
												for="action<s:property value="id"/>"><s:property
														value="name" /></label></td>
											<td align=right><a href="#"
												onclick="javascript:lookup(this)">查看</a></td>
										</tr>
									</s:iterator>
								</tbody>
							</table>
						</div>
					</div>
					<p class="add">
						<input type="button" name="add" class="add" value="添加" />
					</p>
				</div>
			</div>
		</div>
		<div id="divdetail">
			 <s:include value="/plan/workout_course.jsp"></s:include> 
		</div>
		<div id="divplanset" style="display: none;"></div>
	</div>
	<s:include value="/share/footer.jsp" />
		
	<div id="sampleCourse" style="display: none;">
		<div coachId='' memberId='' planDate='' costType='' courseShare=''
			doneDate=''>
			课程名称：<span cssStyle="margin-left:7px;"><s:select list="#request.courseinfos" name="course.courseInfo.id"
				listKey="id" listValue="name" theme="simple" id="courseInfoIdXXXX"
				cssStyle="margin-left:7px;border:1px solid #ccc; width:100px; height:20px; "
				onchange="changeTitle(this)"  /></span>
			<span>课程时间:</span>
			<s:select list="#request.times" name="course.startTime" listKey="key"
				listValue="value" theme="simple" id="startTimeXXXX" onchange="changeTitle1(this)"
				cssStyle="border:1px solid #ccc; width:80px; height:20px;" />
			<span>-</span>
			<s:select list="#request.times" name="course.endTime" listKey="key"
				listValue="value" theme="simple" id="endTimeXXXX" onchange="changeTitle2(this)"
				cssStyle="border:1px solid #ccc; width:80px; height:20px;" />
			 <a href="#" class="inpa1" onclick="delCourse(this)">删除</a> 
			 <!-- <a href="#" class="inpa1" onclick="javascript:onMusic(this);">配乐</a> -->
			 <a href="#" class="inpa1" onclick="editCourse(this)">编辑健身计划</a> 
			<br>
			循环次数：
			<s:select list="#{1:'1',2:'2',3:'3',4:'4',5:'5',6:'6',7:'7',8:'8',9:'9',10:'10'}" name="course.cycleCount" listKey="key" 
			listValue="value"  theme="simple" id="cycleCountXXXX" cssStyle="border:1px solid #ccc; width:100px; height:20px;"/>
			<span>播放倒计时:</span>
			<s:select list="#{1:'1',2:'2',3:'3',4:'4',5:'5',6:'6',7:'7',8:'8',9:'9',10:'10'}" name="course.countdown" listKey="key" 
			listValue="value"  theme="simple" id="countdownXXXX" cssStyle="border:1px solid #ccc; width:80px; height:20px;"/>
			<s:if test="music == null || music == ''">	<input type="button" value="配乐" onclick="javascript:onMusic(this);"></s:if>
			<s:else><input type="button" value="删除配乐" onclick="javascript:delMusic(this);"></s:else>	
		</div>
	</div>
	<table width="86%" cellspacing="1" cellpadding="0" border="0"
		class="table1" style="display: none;">
		<tbody id="samplePlan">
			<tr>
				<td><input type="hidden" name="workouts[XXXX].action.id"
					value="ACTIONID" /> <input type="hidden" name="workouts[XXXX].sort"
					value="SORT" /> <input type="hidden" name="workouts[XXXX].id"
					value="" /> <input type="hidden"
					name="workouts[XXXX].action.part.project.mode" value="" />
					<div></div></td>
				<td>ACTION</td>
				<td>TYPE</td>
				<td>请点击右侧编辑按钮编制计划内容</td>
				<td><a onclick="javascript:onEdit(this);" id="colotoa">编辑</a>
					<a onclick="javascript:onDelete(this);" id="colotoa">删除</a></td>
			</tr>
		</tbody>
	</table>
	<div id="dialog_altdiv1" title="动作详情" style="display: block;">
		<div class="altleft" style="margin-left: 3px; margin-top: -5px;">
			<div class="altleft1">
				<div class="action_img"></div>
			</div>
			<div class="action_anm"></div>
		</div>
		<div class="action_desc">动作描述：在本页面左侧“添加健身项目”面板中选择并添加健身项目（动作），形成健身计划表在本页面左侧“添加健身项目”面板中选择并添加健身项目（动作），形成健身计划表</div>
	</div>

	<div id="altdiv" title="编辑运动负荷" style="display: block;"></div>
	<div id="altdiv2" title="上传音乐" style="display: block;"></div>

	<div id="dialog" title="健身计划复制">
		<form id="copyform" name="copyform" method="post">
			<s:if test="#session.loginMember.role == \"S\"">
				<p>将本计划复制到其它会员</p>
				<p>
					<s:select name="toMember" theme="simple" list="#request.members"
						listKey="id" listValue="name" Style="min-width:90px;" />
				</p>
			</s:if>
			<p>将本计划复制到以下其它日期</p>
			<p>
				<input type="checkbox" name="weeks" value="1" id="checkbox"
					class="inpa" /> <label for="checkbox">星期日</label> <input
					type="checkbox" name="weeks" value="2" id="checkbox2" class="inpa" />
				<label for="checkbox2">星期一</label> <input type="checkbox"
					name="weeks" value="3" id="checkbox3" class="inpa" /> <label
					for="checkbox3">星期二</label> <input type="checkbox" name="weeks"
					value="4" id="checkbox4" class="inpa" /> <label for="checkbox4">星期三</label>
				<br /> <input type="checkbox" name="weeks" value="5" id="checkbox5"
					class="inpa" /> <label for="checkbox5">星期四</label> <input
					type="checkbox" name="weeks" value="6" id="checkbox6" class="inpa" />
				<label for="checkbox6">星期五</label> <input type="checkbox"
					name="weeks" value="7" id="checkbox7" class="inpa" /> <label
					for="checkbox7">星期六</label>
			</p>
			<p>选择时间:</p>
			<p style="margin-left: 30px;">
				开始时间:<input type="text" class="Data-Figure2" id="startDate"
					name="startDate" /> 结束时间:<input type="text" id="endDate"
					name="endDate" class="Data-Figure2" />
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
			<s:if test="#session.loginMember.role == \"S\"">
				<p>请先选择需要清除计划的会员</p>
				<p>
					<s:select name="toMember" theme="simple" list="#request.members"
						listKey="id" listValue="name" Style="min-width:90px;" />
				</p>
			</s:if>
			<p>请选择您需要清除计划的时间范围</p>
			<p style="margin-left: 30px;">
				开始时间:<input type="text" name="startDate" class="Data-Figure2"
					id="startDateb" /> 结束时间: <input type="text" id="endDateb"
					name="endDate" class="Data-Figure2" />
			</p>
		</form>
		<p>
			<button class="buttoma" onclick="onClean();">确定</button>
			<button class="buttomb" onclick="onCloseb()">取消</button>
		</p>
	</div>
	<div id="shar" title="健身计划分享">
		<p style="font-size: 14px; text-align: center;">您今天的健身计划将分享到卡库网的健身计划栏目</p>
		<p>
			<button class="buttoma" onclick="onShare();">确定</button>
			<button class="buttomb" onclick="onClosec()">取消</button>
		</p>
	</div>
</body>
</html>
