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
<link type="text/css" rel="stylesheet" href="css/pulicstyle.css" />
<script type="text/javascript" src="script/jquery.form.js"></script>
<script type="text/javascript" src="script/jquery.parser.js"></script>
<script type="text/javascript" src="script/cyberplayer.min.js"></script>

<script type="text/javascript">
	$(function() {
		$('.xiugai1').dialog({
			autoOpen : false,
			width : 510,
			modal : true
		});
		$('.look1').dialog({
			autoOpen : false,
			width : 510,
			modal : true
		});
		$('.projects').change(function() {
			loadMask();
			var key = $(this).val();
			$('#projectId').val(key);
			$('#mode').val($(this).attr('mode'));
			$.ajax({
				type : 'post',
				url : 'action!loadAction.asp',
				data : 'projectId=' + key,
				success : function(msg) {
					$('#partcontainer').html(msg);
					removeMask();
				}
			});
		});
	});

	function onAdd() {
		var mode = $('#mode').val();
		$('.xiugai1').html('');
		$('.xiugai1').dialog('option', 'title', '新增动作');
		$('.xiugai1').dialog('open');
		$.ajax({
			type : 'post',
			url : 'action!edit.asp',
			data : 'projectId=' + $('#projectId').val() + '&mode=' + mode,
			success : function(msg) {
				$('.xiugai1').html(msg);
			}
		});
	}

	function onEdit() {
		var mode = $('#mode').val();
		var chk = $('input[name="ids"]:checked:first');
		if (!$(chk).val()) {
			alert('请先选择需要编辑的数据！');
			return;
		}
		$('.xiugai1').html('');
		$('.xiugai1').dialog('option', 'title', '编辑动作');
		$('.xiugai1').dialog('open');
		$.ajax({
			type : 'post',
			url : 'action!edit.asp',
			data : 'projectId=' + $('#projectId').val() + '&mode=' + mode + '&id=' + $(chk).val(),
			success : function(msg) {
				$('.xiugai1').html(msg);
			}
		});
	}

	function onClose() {
		$('.xiugai1').dialog('close');
	}
	function onDelete() {
		var chks = $('input[name="ids"]:checked');
		if (chks.length <= 0) {
			alert('请先选择需要删除的数据！');
			return;
		}
		if (confirm('是否确认删除所有选择的数据？')) {
			loadMask();
			var params = $('#form').serialize();
			$.ajax({
				type : 'post',
				url : 'action!delete.asp',
				data : params,
				success : function() {
					for (var i = 0; i < chks.length; i++) {
						$(chks[i]).parents('tr').remove();
					}
					removeMask();
				}
			});
		}
	}
	function onSave() {
		loadMask();
		$('#form1').form('submit', {
			url : 'action!save.asp',
			onSubmit : function(param) {
				param['projectId'] = $('#projectId').val();
				param['mode'] = $('#mode').val();
				return true;
			},
			success : function(msg) {
				alert("保存成功！");
				var title = $('.xiugai1').dialog('option', 'title');
				if (title == '新增动作') {
					$('input[name="action.name"]').val('');
					$('textarea[name="action.descr"]').val('');
					$('textarea[name="action.regard"]').val('');
				}
				$('#partcontainer').html(msg);
				removeMask();
			}
		});
	}
	function nextStep() {
		$('#form').attr('action', "action!next.asp");
		$('#form').submit();
	}
	function prevStep() {
		$('#form').attr('action', "action!prev.asp");
		$('#form').submit();
	}
	$(function() {
		$('#look1').dialog({
			autoOpen : false,
			width : 510,
			show : "blind",
			hide : "blind",
			modal : true
		});
	});
	function lookup(the) {
		$('.look1').html('');
		$('.look1').dialog('open');
		$.ajax({
			type : 'post',
			url : 'workout!look.asp',
			data : 'id=' + the,
			success : function(msg) {
				$('.look1').html(msg);
			}
		});

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
				<li>四.设定身体部位</li>
				<li class="last">五.设定训练动作</li>
				<li>六.完成</li>
			</ul>
		</div>
		<div id="center-1">
			<div class="configall">
				<h4>1、选择健身项目</h4>
				<div style="padding-right: 5px;">
					<s:set name="current" scope="request" />
					<s:iterator value="#request.projects" status="st" id="cur">
						<s:if test="projectId==id">
							<s:set name="current" value="#cur" />
						</s:if>
						<input type="radio" name="projectId"
							id="projectId<s:property value="id"/>" class="projects"
							<s:if test="id==projectId"> checked="checked"</s:if>
							value="<s:property value="id"/>"
							mode="<s:property value="mode"/>" />
						<label style="padding-left: 5px;"
							for="projectId<s:property value="id"/>"><s:property
								value="name" /></label>
					</s:iterator>
					<input type="hidden" name="projectId" id="projectId"
						value="<s:property value="projectId"/>" /> <input type="hidden"
						name="mode" id="mode" value="<s:property value="#current.mode"/>" />
				</div>
				<h4>2、设定该项目的动作</h4>
				<div id="partcontainer">
					<s:include value="/coach/action_content.jsp" />
				</div>
				<div class="saveoperate">
					<span><a href="javascript:onAdd();" class="adat">新增</a></span> <span><a
						href="javascript:onEdit();" class="asev">修改</a></span> <span><a
						href="javascript:onDelete();" class="adel">删除</a></span>
				</div>
			</div>
		</div>
		<div class="stepoperate">
			<a href="javascript:prevStep();" title="上一步" class="butlost">上一步</a>
			<a href="javascript:nextStep();" title="下一步" class="butnext">下一步</a>
		</div>
	</div>
	<s:include value="/share/footer.jsp" />
	<div class="xiugai1" title="修改健身动作"></div>
	<div class="look1" title="动作详情"></div>
</body>
</html>
