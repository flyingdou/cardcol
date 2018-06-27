var editor;
$(function(){
	editor = CKEDITOR.replace('editor1',{extraPlugins: 'uicolor',toolbar : [['Source','-','Preview','-','Cut','Copy','Paste','PasteText','-','SpellChecker', 'Scayt','Bold', 'Italic','Underline','Strike','Subscript','Format','Font','FontSize']]});
	$('.xiugai').dialog({autoOpen: false,width: 800,modal:true});
});
function onDown(the){
	var $tr = $(the).parents('tr');
	$tr.next('tr').after($tr);
	sorts();
}
function onUp(the){
	var $tr = $(the).parents('tr');
	$tr.prev('tr').before($tr);
	sorts();
}
function sorts(){
	var $table = $('#contianer');
	var trs = $table.find('tr'), $tr;
	var len = trs.length;
	for (var i = 1; i < len; i++) {
		$tr = $(trs[i]);
		$tr.children('td:eq(0)').children('input:eq(2)').val(i);
		if (i == 1) 
			$tr.children('td:last').html('<img src="images/allow5.gif" alt="down" onclick="onDown(this);"/>');
		else if (i == len - 1)
			$tr.children('td:last').html('<img src="images/allow4.gif" alt="up" onclick="onUp(this)" />');
		else
			$tr.children('td:last').html('<img src="images/allow5.gif" alt="down" onclick="onDown(this)"/><img src="images/allow4.gif" alt="up" onclick="onUp(this)" />');
	}
}
function onAdd(){
	$('input[name="project.id"]').val('');
	$('.xiugai').dialog('open');
}
function onEdit(){
	var curs = $('input[name="ids"]:checked');
	if (curs.length <= 0) {
		alert('请先选择需要编辑的数据！');
		return;
	}
	var cur = $(curs[0]);
	var $tr = cur.parents('tr');
	var $td = $tr.children('td:eq(0)');
	$('input[name="project.id"]').val(cur.val());
	$('input[name="project.member"]').val($td.children('input:eq(1)').val());
	$('input[name="project.sort"]').val($td.children('input:eq(2)').val());
	$('input[name="project.name"]').val($td.children('input:eq(4)').val());
	$('input[name="project.shortName"]').val($td.children('input:eq(5)').val());
	$('select[name="project.mode"]').val($td.children('input:eq(6)').val());
	CKEDITOR.instances['editor1'].setData($td.children('input:eq(7)').val());
	$('.xiugai').dialog('open');
}
function onDelete(){
	var chks = $('input[name="ids"][type="checkbox"]:checked');
	if (chks.length <= 0 ) {
		alert('请先选择需要删除的数据！');
		return;
	}
	if (confirm('是否确认删除当前所有选择的数据？')) {
		var params = $('#form').serialize();
		$.ajax({type:'post',url:'project!delete.asp',data: params, 
			success: function(){
				for (var i = 0 ; i< chks.length; i++) {
					$(chks[i]).parents('tr').remove();
				}	
			}
		});
	}
}
function onSave(){
	if ($('input[name="project.name"]').val() == '') {
		alert('项目名称不能为空！');
		$('input[name="project.name"]').focus();
		return;
	}
	if ($('input[name="project.shortName"]').val() == '') {
		alert('简称不能为空！');
		$('input[name="project.shortName"]').focus();
		return;
	}
	var len = $('#contianer').find('tr').length;
	$('input[name="project.sort"]').val(len);
	$('#form1').attr('action', 'project!save.asp');
	$('#form1').submit();
}
function loadData(jsons) {
	if (jsons.success === true) {
		alert(jsons.message);
		$.ajax({type:'post',url:'project!loadContent.asp',
			success: function(msg) {
				$('#projectcontainer').html(msg);
			}
		});
	} else {
		alert(jsons.message);
	}
}
function onClose(){
	$('.xiugai').dialog('close');
}
function prevStep(){
	sorts();
	$('#form').attr('action', 'project!prev.asp');
	$('#form').submit();
}
function nextStep(){
	sorts();
	$('#form').attr('action', 'project!next.asp');
	$('#form').submit();
}