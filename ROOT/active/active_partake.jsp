<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link rel="stylesheet" type="text/css" href="css/activ_new.css">
<style type="text/css">
#dialogWeight div {
	line-height: 30px;
}

#dialogWeight div.button {
	text-align: center;
}

.join_r intput {
	line-height: 23px;
}
</style>
<script type="text/javascript">
	$(function() {
		var groupChecked;// 是否选择团队
		$('#dialogWeight').dialog({
			autoOpen : false,
			show : 'blind',
			hide : 'explode',
			resizable : false,
			width : 300,
			modal : true
		});
		$("#dialogPay").dialog({
			autoOpen : false,
			show : "blind",
			hide : "explode",
			resizable : false,
			width : 1000,
			modal : true
		});
		$("#joinDate").datepicker();
		$('#partake').click(function() {
			if (!"<s:property value="#session.loginMember.id"/>") {
				openLogin();
				return;
			}
			if ($('#activeMode').val() == 'B') {
				$(":radio").each(function() {
					if ($(this).attr('checked') == 'checked') {
						if ($(this).val() == '1') {
							if ($('#createTeam').val() != '') {
								groupChecked = true;
							} else {
								alert('请输入团队名称！');
							}
							return;
						} else {
							if ($('#teamId').val() != '') {
								groupChecked = true;
							} else {
								alert('请先选择团队在参与！');
							}
							return;
						}
					}
				});
				if (!groupChecked) {
					return;
				}
			}
			if ($('#activeMode').val() == 'A') {
				if ($('#weight').val() == '') {
					alert('请先维护您的目前体重！');
					return;
				}
			}
			if ($('#joinDate').val() == '') {
				alert('请输入活动开始时间！');
				$('#joinDate').focus();
				return;
			}
			if ($('#tmpJudge').val() == '') {
				alert('请输入裁判名称！');
				$('#tmpJudge').focus();
				return;
			}
			if ($('#members').val() == '输入邀请对象，多个用户名之前用英文逗号分隔')
				$('#members').val('');
			$('#partakeJudge').val($('#tmpJudge').val());
			$('#orderStartTime').val($('#joinDate').val());
			$('#partakeform').attr('action', 'joinactive!save.asp');
			$('#partakeform').attr('method', 'post');
			$('#partakeform').submit();
		});
		$('.bk_a').click(function() {
			$.ajax({
				url : 'account!findWeight.asp',
				success : function(msg) {
					var _json = $.parseJSON(msg);
					if (_json.success === true) {
						$('input[name="oldWeight"]').val(_json.weight);
						$('#dialogWeight').dialog({
							autoOpen : true
						});
					} else {
						alert(_json.message);
					}
				}
			});
		});
		$('#saveWeight').click(function() {
			var _weight = $('input[name="weight"]').val();
			$.ajax({
				url : 'account!saveWeight.asp',
				type : 'post',
				data : 'weight=' + _weight,
				success : function(msg) {
					var _json = $.parseJSON(msg);
					if (_json.success === true) {
						alert('您的最新体重数据已经成功更新！');
						$('#weight').val(_weight);
						$('#spanWeight').html(_weight);
						$('#dialogWeight').dialog('close');
					} else {
						alert(_json.message);
					}
				}
			});
		});
		$('input[name="createMode"]').click(function() {
			var val = $(this).val();
			if (val === '0') { //加入团队
				$('#judge').attr('disabled', true);
				$('#members').attr('disabled', true);
				$('.duidd').css('display', 'inline-block');
				// 			$.ajax({url: 'joinactive!loadOrder.asp', type: 'post', data: 'activeId=' + $('#activeId').val(), 
				// 				success: function(msg) {
				// 					var _jsons = $.parseJSON(msg);
				// 					if (_jsons.success === true) {
				// 						var _items = _jsons.items, _len = _items.length;
				// 						$('#teamId').empty();
				// 						$('<option value=""></option>').appendTo($('#teamId'));
				// 						for (var i = 0; i < _len; i++) {
				// 							$('<option value="' + _items[i].id + '">' + _items[i].name + '</option>').appendTo($('#teamId'));
				// 						}
				// 					}
				// 				}
				// 			});
			} else { //创建团队
				$('#judge').attr('disabled', false);
				$('#members').attr('disabled', false);
				$('.duidd').css('display', 'none');
			}
		});
		$('#teamId').change(function() {
			var val = $(this).val();
			$.ajax({
				url : 'joinactive!loadOrderInfo.asp',
				data : 'activeId=' + $('#activeId').val() + '&teamId=' + val,
				success : function(msg) {
					var _json = $.parseJSON(msg);
					if (_json.success === true) {
						$('#joinDate').val(_json.orderStartTime);
						$('#judge').val(_json.judge);
					} else {
						alert(_json.message);
					}
				}
			});
		});
	});
</script>
<s:form name="partakeform" id="partakeform" method="post" theme="simple">
	<s:hidden name="type" id="type" />
	<s:hidden name="partake.active.id" id="activeId" />
	<s:hidden name="partake.active.name" id="activeName" />
	<s:hidden name="partake.active.mode" id="activeMode" />
	<s:hidden name="partake.active.judgeMode" id="judgeMode" />
	<s:hidden name="partake.active.amerceMoney" id="amerceMoney" />
	<s:hidden name="partake.active.days" id="days" />
	<s:hidden name="partake.weight" id="weight" />
	<s:hidden name="partake.judge" id="partakeJudge" />
	<s:hidden name="partake.active.teamNum" id="teamNum" />
	<s:hidden name="partake.orderStartTime" id="orderStartTime" />
	<s:hidden name="type" id="type" />
	<div id="container">
		<h1>参加挑战</h1>
		<div style="padding-left: 10px; padding-right: 40px;">
			<!--left-->
			<div class="join_l">
				<p>
					<strong>活动名称：</strong>
				</p>
				<p>
					<strong>活动模式：</strong>
				</p>
				<s:if test="partake.active.mode=='B'">
					<p>&nbsp;</p>
					<p>
						<strong>邀请队友：</strong>
					</p>
				</s:if>
				<p>
					<strong>完成时间：</strong>
				</p>
				<p>
					<strong>活动开始时间：</strong>
				</p>
				<p>
					<strong>活动目标：</strong>
				</p>
				<p>
					<strong>奖励：</strong>
				</p>

				<p style="padding-top: 2px;">
					<strong>惩罚：</strong>
				</p>
				<p>
					<strong>裁判方式：</strong>
				</p>
				<p>&nbsp;</p>
				<p>
					<strong>参加对象：</strong>
				</p>
				<p>
					<strong>已参与人员：</strong>
				</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
			</div>
			<!--right-->
			<div class="join_r">
				<p>
					<s:property value="partake.active.name" />
				</p>
				<p>
					<s:if test="partake.active.mode=='A'">个人挑战</s:if>
					<s:else>团体挑战</s:else>
				</p>
				<s:if test="partake.active.mode=='B'">
					<p>
						<input name="createMode" type="radio" value="1" checked="checked"
							style="margin-top: 6px;" /> <span>创建团队</span> <span><s:textfield
								id="createTeam" name="name" cssClass="input_w"
								style="width:150px;line-height:21px;" /></span> <span
							style="padding-left: 35px;"><input name="createMode"
							type="radio" value="0" />加入团队</span> <span class="duidd"><s:select
								id="teamId" list="#request.teams" listKey="id" listValue="name"
								name="id" cssStyle="height:20px; border:1px solid #999999" /></span>
					<p style="padding-top: 10px;">
						<input style="line-height: 21px; padding-top: 3px;" name="members"
							type="text" class="input_log" id="members"
							value="输入邀请对象，多个用户名之前用英文逗号分隔" />&nbsp;
					</p>
				</s:if>
				</p>
				<p style="padding-top: 2px;">
					<s:property value="partake.active.days" />
					&nbsp;&nbsp;天
				</p>
				<p style="padding-top: 5px;">
					<input name="partake.orderStartTime" class="input_w"
						style="width: 120px; line-height: 26px;"
						value="<s:date name="partake.orderStartTime" format="yyyy-MM-dd"/>"
						id="joinDate" />
				</p>
				<p style="padding-top: 5px;">
					<s:if test="partake.active.category=='A'">体重减少<s:property
							value="partake.active.value" />公斤</s:if>
					<s:elseif test="partake.active.category=='B'">体重增加<s:property
							value="partake.active.value" />公斤</s:elseif>
					<%-- <s:elseif test="partake.active.category=='C'">体重管理-保持体重在<s:property
							value="partake.active.value" />%左右</s:elseif> --%>
					<s:elseif test="partake.active.category=='C'">次数/时间/频率-<s:property
							value="partake.active.days" />天运动<s:property
							value="partake.active.value" />次</s:elseif>
					<s:elseif test="partake.active.category=='D'">次数/时间/频率-<s:property
							value="partake.active.days" />天运动<s:property
							value="partake.active.value" />小时</s:elseif>
					<s:elseif test="partake.active.category=='E'">自定义-<s:property
							value="partake.active.days" />天中<s:property value="partake.active.content"/>
				 <s:if test="partake.active.evaluationMethod=='00'">单次最好成绩大于或等于</s:if><s:if test="partake.active.evaluationMethod=='01'">单次最好成绩小于或等于</s:if>
				 <s:if test="partake.active.evaluationMethod=='10'">累计成绩大于或等于</s:if><s:if test="partake.active.evaluationMethod=='11'">累计成绩小于或等于</s:if>
				 <s:if test="partake.active.evaluationMethod=='20'">最后一次成绩大于或等于</s:if><s:if test="partake.active.evaluationMethod=='21'">最后一次成绩小于或等于</s:if>
				 <s:property value="partake.active.customTareget"/><s:property value="partake.active.unit"/>   </s:elseif>
					<%-- <s:elseif test="partake.active.category=='F'">次数/时间/频率-每周运动<s:property
							value="partake.active.value" />次</s:elseif>
					<s:elseif test="partake.active.category=='G'">运动量-<s:property
							value="partake.active.action" />
						<s:property value="partake.active.value" />千米</s:elseif>
					<s:elseif test="partake.active.category=='H'">运动量-力量运动负荷<s:property
							value="partake.active.value" />公斤</s:elseif> --%>
					<span class="tishi" style="padding-right: 20px; color: #945F50;">您的当前体重为<span
						id="spanWeight"><s:property value="partake.weight" /></span>公斤&nbsp;&nbsp;
						<span class="bk_a" id="modifyweight"><a href="#"><b>修改</b></a></span></span>
				</p>
				<p>
					<s:property value="partake.active.award" />
				</p>
				<p style="padding-top: 2px;">
					向&nbsp;&nbsp;<span class="jcc"><s:property
							value="partake.active.institution.name" /></span> 捐款
					<s:property value="partake.active.amerceMoney" />
					元
				</p>
				<p>
					<s:if test="partake.active.judgeMode=='A'">输入裁判在卡库网上的用户名&nbsp;<s:textfield
							name="judge" value="%{partake.judge}" id="tmpJudge"
							cssClass="input_w" cssStyle="width: 200px;line-height:22px;" />
					</s:if>
					<s:elseif test="partake.active.judgeMode == 'B'">
						<s:textfield name="judge" value="%{partake.judge}" readonly="true"
							id="tmpJudge" />
					</s:elseif>
					<s:else>
						<s:radio name="judge" list="#request.active.judges" listKey="judge.name" listValue="judge.name"/>
					</s:else>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</p>
				<p class="hui_s">
					<span class="tishi" style="padding-right: 20px; color: #945F50;">裁判将收到挑战者的健身报告，由裁判确认报告中健身数据的真实性。</span>
				</p>
				<p class="hui_s">
					<s:if test="partake.active.joinMode =='A'">所有对象</s:if>
					<s:else>仅限所属会员</s:else>
				</p>
				<p class="join_user">
					<s:iterator value="#request.partakeMembers">
						<p class="p_flaos">
							<span>
								<s:if test="role == \"E\"">
									<a href="club.asp?member.id=<s:property value="id"/>" target="_blank"><s:property value="name" /></a>
								</s:if>
								<s:elseif test="role == \"C\"">
									<a href="coach.asp?member.id=<s:property value="id"/>" target="_blank"><s:property value="name" /></a>
								</s:elseif>
								<s:else>
									<a href="member.asp?member.id=<s:property value="id"/>" target="_blank"><s:property value="name" /></a>								
								</s:else>
							</span>
						</p>
					</s:iterator>
				</p>
				<p style="clear: both; padding-top: 70px;">
					<img src="images/order5.jpg" id="partake" height="31" width="90"
						style="padding-left: 150px; padding-top: 10px;" />
				<Div style="height: 20px;"></div>
			</div>
			<!--right end-->
		</div>
	</div>
</s:form>

<div id="dialogWeight" title="修改体重">
	<s:form id="weightform" theme="simple">
		<div>
			原体重(KG)：
			<s:textfield name="oldWeight" />
		</div>
		<div>
			最新体重(KG)：
			<s:textfield name="weight" />
		</div>
		<div class="button">
			<input type="button" id="saveWeight" value="修改体重" />
		</div>
	</s:form>
</div>
<div id="dialogPay" title="支付保证金" style="display: none;"></div>