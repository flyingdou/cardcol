<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="健身E卡通-我的设定" />
<meta name="description" content="健身E卡通-我的设定" />
<title>健身E卡通-我的设定</title>
<link href="css/user-config.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="css/pulicstyle.css"/>
<script type="text/javascript">
$(function(){
	$('.projects').change(function(){
		loadMask();
		var key = $(this).val();
		$.ajax({type:'post',url:'part!loadParts.asp',data: 'projectId=' + key, 
			success: function(msg) {
				$('#partcontainer').html(msg);
				removeMask();
			}
		});
	});
});
function onAdd(){
	var size = $('#partable').find('tr').length - 1;
	var html = $('#samples1').html().replace(new RegExp("XXXX", "gm"), size + "");
	$('#partable').append(html);
	$('input[name="parts['+size+'].project"]').val($('#projectId').val());
}
function onDelete(){
	var chks = $('input[name="ids"]:checked');
	if (chks.length <= 0) {
		alert('请先选择需要删除的数据！');
		return;
	}
	if(confirm('是否确认删除所有选择的数据？')){
		var params = $('#form').serialize();
		$.ajax({type:'post',url:'part!delete.asp',data: params,
			success: function(){
				for (var i = 0; i< chks.length; i++) {
					$(chks[i]).parents('tr').remove();
				}
			}
		});
	}
}
function onSave(){
	loadMask();
	var pid = $('input[name="projectId"]:checked').val();
	$('.bc0').each(function(){
		$(this).children('td:eq(0)').children('input:eq(1)').val(pid);
	});
	var params = $('#form').serialize();
	$.ajax({type:'post',url:'part!save.asp',data: params, 
		success:function(msg){
			$('#partcontainer').html(msg);
			alert('当前数据已经成功保存！');
			removeMask();
		}
	});
}
function nextStep(){
	$('#form').attr('action', "part!next.asp");
	$('#form').submit();
}
function prevStep(){
	$('#form').attr('action', "part!prev.asp");
	$('#form').submit();
}

</script>
</head>
<body>
	<s:include value="/share/header.jsp" />
	<div id="content">
		<div class="my-setting">
			<ul id="nextstep">
				<li class="first">一.设定风格</li>
				<li>二.设定课程</li>
				<li>三.设定健身项目</li>
				<li class="last">四.设定身体部位</li>
				<li>五.设定训练动作</li>
				<li>六.完成</li>
			</ul>
		</div>
		<div id="center-1">
			<div class="configall">
				<h4>1、选择健身项目</h4>
				<div class="partlab">
				 <s:radio list="#request.projects" listKey="id" listValue="name" name="projectId" theme="simple" cssClass="projects" />
				 </div>
				<h4>2、设定该项目的身体部位</h4>
				<div id="partcontainer">
					<s:include value="/coach/part_content.jsp"/>
				</div>
				<div class="saveoperate">
					<span><a href="javascript:onAdd();" class="adat">新增</a></span>
					<span><a href="javascript:onSave();" class="alter">保存</a></span>
					<span><a href="javascript:onDelete();" class="adel">删除</a></span>
				</div>
			</div>
		</div>
		<div class="stepoperate">
			<a href="javascript:prevStep();" title="上一步" class="butlost">上一步</a> <a
				href="javascript:nextStep();" title="下一步" class="butnext">下一步</a>
		</div>
	</div>
	<s:include value="/share/footer.jsp" />
	<table style="display:none;">
		<tbody id="samples1">
			<tr class="bc0">
				<td><input type="hidden" name="parts[XXXX].id"/>
					<input type="hidden" name="parts[XXXX].project.id"/>
					<input type="hidden" name="parts[XXXX].system" value="0"/>
					<input type="checkbox" name="ids"/>
				</td>
				<td><input type="text" name="parts[XXXX].name"/></td>
				<td><input type="text" name="parts[XXXX].mgroup"/></td>
				<td><input type="text" name="parts[XXXX].marea"/></td>
			</tr>
		</tbody>
	</table>
</body>
</html>