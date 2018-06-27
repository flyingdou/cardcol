﻿<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="健身E卡通-我的设定" />
<meta name="description" content="健身E卡通-我的设定" />
<title>健身E卡通-我的设定</title>
<link href="css/coach-config.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
function onAdd(){
	var $ul = $('.time2-2');
	var size = $ul.find('li').length;
	var html = $('#samples').html().replace(new RegExp('XXXX', "gm"), size + '');
	$ul.append(html);
}

function onDelete(the){
	var $li = $(the).parents('li');
	var key = $li.children('input').val();
	alert(key);
	if(confirm('是否确认删除当前的工作时间？')){
		$.ajax({type:'post',url:'worktime!delete.asp',data: 'ids='+ key,
			success: function(){
				$li.remove();
			}
		});
	}
}
function onSave(){
	var params = $('#form').serialize();
	$.ajax({type:'post',url:'worktime!save.asp',data: params, 
		success:function(msg){
			$('#timecontainer').html(msg);
		}
	});
}
function nextStep(){
	$('#form').attr('action', "worktime!next.asp");
	$('#form').submit();
}
function prevStep(){
	$('#form').attr('action', "worktime!prev.asp");
	$('#form').submit();
}

</script>
</head>
<body>
	<s:include value="/share/header.jsp" />
	<div id="content">
		<div class="my-setting">
			<ul id="nextstep">
				<li>一.设定风格</li>
				<li class="first">二.设定工作时间</li>
				<li>三.设定健身项目</li>
				<li>四.设定身体部位</li>
				<li>五.设定训练动作</li>
				<li class="last">六.完成</li>
			</ul>
		</div>
		<form id="form" name="" action="" method="post">
			<div id="center-1">
				<h1>工作时间</h1>
				<div id="timecontainer">
					<s:include value="/coach/worktime_content.jsp"/>
				</div>
				<div class="setgoal">
					<div class="saveoperate">
						<span><a href="javascript:onAdd();">新增</a></span>
						<span><a href="javascript:onSave();">保存</a></span>
					</div>
				</div>
			</div>
		</form>
		<div class="stepoperate">
			<a href="javascript:prevStep();" title="上一步" class="butlost">上一步</a> <a
				href="javascript:nextStep();" title="下一步" class="butnext">下一步</a>
		</div>
	</div>
	<s:include value="/share/footer.jsp"/>
	
	<ul id="samples" style="display: none;">
		<li>从<select name="worktimes[XXXX].startTime">
				<option value="00:00">00:00</option>
				<option value="00:30">00:30</option>
				<option value="01:00">01:00</option>
				<option value="01:30">01:30</option>
				<option value="02:00">02:00</option>
				<option value="02:30">02:30</option>
				<option value="03:00">03:00</option>
				<option value="03:30">03:30</option>
				<option value="04:00">04:00</option>
				<option value="04:30">04:30</option>
				<option value="05:00">05:00</option>
				<option value="05:30">05:30</option>
				<option value="06:00">06:00</option>
				<option value="06:30">06:30</option>
				<option value="07:00">07:00</option>
				<option value="07:30">07:30</option>
				<option value="08:00" selected="selected">08:00</option>
				<option value="08:30">08:30</option>
				<option value="09:00">09:00</option>
				<option value="09:30">09:30</option>
				<option value="10:00">10:00</option>
				<option value="10:30">10:30</option>
				<option value="11:00">11:00</option>
				<option value="11:30">11:30</option>
				<option value="12:00">12:00</option>
				<option value="12:30">12:30</option>
				<option value="13:00">13:00</option>
				<option value="13:30">13:30</option>
				<option value="14:00">14:00</option>
				<option value="14:30">14:30</option>
				<option value="15:00">15:00</option>
				<option value="15:30">15:30</option>
				<option value="16:00">16:00</option>
				<option value="16:30">16:30</option>
				<option value="17:00">17:00</option>
				<option value="17:30">17:30</option>
				<option value="18:00">18:00</option>
				<option value="18:30">18:30</option>
				<option value="19:00">19:00</option>
				<option value="19:30">19:30</option>
				<option value="20:00">20:00</option>
				<option value="20:30">20:30</option>
				<option value="21:00">21:00</option>
				<option value="21:30">21:30</option>
				<option value="22:00">22:00</option>
				<option value="22:30">22:30</option>
				<option value="23:00">23:00</option>
				<option value="23:30">23:30</option>
		</select> 到<select name="worktimes[XXXX].endTime">
				<option value="00:00">00:00</option>
				<option value="00:30">00:30</option>
				<option value="01:00">01:00</option>
				<option value="01:30">01:30</option>
				<option value="02:00">02:00</option>
				<option value="02:30">02:30</option>
				<option value="03:00">03:00</option>
				<option value="03:30">03:30</option>
				<option value="04:00">04:00</option>
				<option value="04:30">04:30</option>
				<option value="05:00">05:00</option>
				<option value="05:30">05:30</option>
				<option value="06:00">06:00</option>
				<option value="06:30">06:30</option>
				<option value="07:00">07:00</option>
				<option value="07:30">07:30</option>
				<option value="08:00">08:00</option>
				<option value="08:30">08:30</option>
				<option value="09:00">09:00</option>
				<option value="09:30">09:30</option>
				<option value="10:00">10:00</option>
				<option value="10:30">10:30</option>
				<option value="11:00">11:00</option>
				<option value="11:30">11:30</option>
				<option value="12:00" selected="selected">12:00</option>
				<option value="12:30">12:30</option>
				<option value="13:00">13:00</option>
				<option value="13:30">13:30</option>
				<option value="14:00">14:00</option>
				<option value="14:30">14:30</option>
				<option value="15:00">15:00</option>
				<option value="15:30">15:30</option>
				<option value="16:00">16:00</option>
				<option value="16:30">16:30</option>
				<option value="17:00">17:00</option>
				<option value="17:30">17:30</option>
				<option value="18:00">18:00</option>
				<option value="18:30">18:30</option>
				<option value="19:00">19:00</option>
				<option value="19:30">19:30</option>
				<option value="20:00">20:00</option>
				<option value="20:30">20:30</option>
				<option value="21:00">21:00</option>
				<option value="21:30">21:30</option>
				<option value="22:00">22:00</option>
				<option value="22:30">22:30</option>
				<option value="23:00">23:00</option>
				<option value="23:30">23:30</option>


		</select> <a onclick="javascript:onDelete(this);">删除</a>
		</li>
	</ul>
</body>
</html>