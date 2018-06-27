function loadSetting(type) {
	loadMask();
	$('#divdetail').hide();
	$('#divplanset').show();
	$.ajax({type: 'post', url: 'auto.asp', data: {type: type}, 
		success: function(msg){
			removeMask();
			$('#divplanset').html(msg);
		}
	});
}
function autoGen(){
	loadMask();
	$.ajax({type: 'post', url: 'auto!autoGen.asp', 
		success: function(msg){
			removeMask();
			var $json = $.parseJSON(msg);
			if ($json.success === true) {
				if ($json.message == 0) {
					if (confirm('您的个人设定中尚有未完成的设定，是否马上进行设定后并自动生成计划?点击【确定】按钮到我的设定中进行设定，否则请点击【取消】按钮！')) {
						window.location.href = "setting.asp?autoGen=1";
					}
				} else if ($json.message == 1) {
					alert('您的计划已经自动生成！');
					window.location.href = 'workout.asp';
				} else if ($json.message == 2) {
					if (confirm('自动生成计划需要高级会员才能进行，您是否马上升级为高级会员？如果您要升级为高级会员，请点击【确定】，否则请点击【取消】!')) {
						$('#productId').val(1);
						$('#queryForm').attr('action','shop!save.asp');
						$('#queryForm').submit();
					}
				} else if ($json.message == -1) {
					alert('未知错误，请联系系统管理员！');
				}
			} else {
				alert('未知错误，可能的原因为：' + $json.message);
			}

		}
	});
}
function onGuide(_type, _step) {
	loadMask();
	var parms = $('#frmsetting').serialize();
	parms += '&type=' + _type + '&step=' + _step;
	$.ajax({type: 'post', url: 'auto!guide.asp', data: parms,
		success: function(msg){
			removeMask();
			$('#divplanset').html(msg);
		}
	});
}

function goodsCardcol(type){
	url= 'order!submitProd.asp?type='+type+'&prodType=6';
	$('#dateForm').attr("action",url);
	$('#dateForm').submit();
}

function validateSelectAll(){
	if ($('input[name="params.strengthDate"]:checked').length == 7) {
		$('#strenghTips').html('连续7天运动不利于身体健康，一周请至少安排1天休息。');
	}else{
		if($('#strenghTips').html().indexOf('连续7天运动不利于身体健康')>-1){
			var currGymStatus = '<s:property value="setting.currGymStatus"/>';
			if(currGymStatus == 'C'){
				$('#strenghTips').html('根据您的个人情况，建议您每周进行3-4次力量训练。');
			}else if(currGymStatus == 'D'){
				$('#strenghTips').html('根据您的个人情况，建议您每周进行4-6次力量训练。');
			}else{
				$('#strenghTips').html('根据您的个人情况，建议您每周进行2-3次力量训练。');
			}
		}
	}
}