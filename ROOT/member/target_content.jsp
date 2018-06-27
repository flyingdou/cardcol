<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
function saveData(){
	var chks = $('input[name="setting.target"]:checked');
	if (chks.length <= 0) {
		alert('请先选择健身目的！');
		$('input[name="setting.target"]:eq(0)').focus();
		return;
	}
	loadMask();
	var params = $('#targetform').serialize();
	$.ajax({url:'target!save.asp',type:'post',data: params, 
		success: function(msg){
			removeMask();
			$('#targetContent').html(msg);
			alert('当前数据已经成功保存！');
		}
	});
}
</script>
  <style type="text/css">
		#livetip {
		  position: absolute;
		  background-color: #cfc;
		  padding: 4px;
		  border: 2px solid #9c9;
		  border-radius: 4px;
		  -webkit-border-radius: 4px;
		  -moz-border-radius: 4px;
		  max-width:200px;
		}
		#livetip strong {
		  display:none;
		}
		#livetip p {
		 margin:5px 0 5px 5px;
		 line-height:18px;
		}
		.tableTitle a { cursor:pointer;}
  </style>
<s:form id="targetform" name="targetform" action="" method="post" theme="simple">
	<s:hidden name="autoGen" id="autoGen" />
	<s:hidden name="setting.id" />
	<s:hidden name="setting.member" />
	<s:hidden name="setting.height" />
	<s:hidden name="setting.weight" />
	<s:hidden name="setting.heart" />
	<s:hidden name="setting.currGymStatus" />
	<s:hidden name="setting.diseaseReport" />
	<s:hidden name="setting.strengthDate" />
	<s:hidden name="setting.strengthDuration" />
	<s:hidden name="setting.cardioDate" />
	<s:hidden name="setting.cardioDuration" />
	<s:hidden name="setting.favoriateCardio" />
	<s:hidden name="setting.bmiMode" />
	<s:hidden name="setting.bmiLow" />
	<s:hidden name="setting.bmiHigh" />
	<s:hidden name="setting.intensityMode"/>
			<h4>1、请问以下哪一项最贴切地表述了你的健身目的？</h4>
			<div class="partlab">
				<s:radio list="#{'1':'瘦身减重','2':'促进健康','3':'肌肉健美','4':'达到最佳运动状态'}" listKey="key" listValue="value" name="setting.target" value="%{setting.target}"/>
			</div>
</s:form>
