<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="健身E卡通-我的设定" />
<meta name="description" content="健身E卡通-我的设定" />
<title>健身E卡通-我的设定</title>
<link rel="stylesheet" type="text/css" href="css/user-config.css" />
<script type="text/javascript">
function nextStep(){
	$('#favform').attr('action','done.asp');
	$('#favform').submit();
}
function prevStep(){
	$('#favform').attr('action','bmi.asp');
	$('#favform').submit();
}
function showPlan() {
	window.location.href = "workout.asp";
}
function autoGen() {
	loadMask();
	$.ajax({type: 'post', url: 'workout!autoGen.asp', 
		success: function(msg){
			removeMask();
			var $json = $.parseJSON(msg);
			if ($json.success === true) {
				if ($json.message == 0) {
					if (confirm('您的个人设定中尚有未完成的设定，是否马上进行设定后并自动生成计划?点击【确定】按钮到我的设定中进行设定，否则请点击【取消】按钮！')) {
						window.location.href = "setting.asp?autoGen=1";
					}
				} else if ($json.message == 1) {
					alert('您的计划已经自动生成！点击确定按钮将转向健身计划页面！');
					window.location.href = "workout.asp";
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
function preShop(productId,memberId){
	if("<s:property value="#session.loginMember.id"/>" == ""){
		//alert("未登录用户不能购买商品！");
		openLogin();
		return;
	}
	if (confirm("确认要成为高级会员？")) {
		$('#productId').val(productId);
		$('#queryForm').attr('action','shop!save.asp');
		$('#queryForm').submit();
	}
}
</script>
</head>
<body>
<s:form id ="queryForm" name="queryForm" method="post" action="shop!save.asp" theme="simple">
<s:hidden name="member.id" id="memberId"/>
<s:hidden name="shop.id" id="shopId"/>
<s:hidden name="product.id" id="productId"/>
</s:form>
	<s:include value="/share/header.jsp" />
	<div id="content">
		<div class="my-setting">
			<ul id="nextstep">
			   <li class="first">一.设定健身目标</li>
			   <li>二.身体与健身状况</li>
			   <li>三.设定健身频率</li>
			   <li>四.设定靶心率</li>
			   <li>五.设定有氧项目</li>
			   <li class="last">六.完成</li>
			</ul>
		</div>
		<div id="center-1">	
			<!--<h2 class="context_title">完成</h2>-->
			<div class="my-sldiv">
                <div class="sldiv1" >
				      <img src="images/xiao.jpg"/>
				   </div>
				   <div class="sldiv2" >
				      <p class="sldiv2p1">你已经完成上述设定</p>
					  <p class="sldiv2p2">高级会员可以点击按钮“自动生成健身计划”[<a href="javascript:preShop('1','<s:property value="#session.loginMember.id"/>');">付费成为高级会员</a>]</p>
					  <p class="sldiv2p2">您也可以进入健身计划页面“手工制定健身计划”</p>
					  <button class="auto-create-plan" onclick="javascript:autoGen();">自动生成健身计划</button><button class="auto-create-plan" onclick="javascript:showPlan()">手工制定健身计划</button>
				   </div>
			</div>
		</div>
		<div class="stepoperate">
			<a href="javascript:prevStep();" title="上一步" style="display:none">上一步</a>
			<a href="member.asp" title="返回首页" class="butnext">返回首页</a>
		</div>
	</div>
	<s:include value="/share/footer.jsp" />
	<table id="sample1" style="display:none;" class="conftable">
		<tbody id="sample">
			<tr class="bcZZZZ">
				<td rowspan="2">
					<input type="checkbox" name="ids" value=""  class="checkbox"/>
					<input type="hidden" name="targets[XXXX].id" value="" />
					<input type="hidden" name="targets[XXXX].member" value="<s:property value="email"/>" />
				</td>
				<td rowspan="2" nowrap><input type="text" name="targets[XXXX].targetDate" value="" class="input" id="startDateXXXX"/></td>
				<td nowrap>目标</td>
				<td><input type="text" name="targets[XXXX].weight" value="" class="input2"/></td>
				<td><input type="text" name="targets[XXXX].chest" value="" class="input2"/></td>
				<td><input type="text" name="targets[XXXX].waist" value="" class="input2"/></td>
				<td><input type="text" name="targets[XXXX].hip" value="" class="input2"/></td>
				<td><input type="text" name="targets[XXXX].upperArm" value="" class="input2"/></td>
				<td><input type="text" name="targets[XXXX].thigh" value="" class="input2"/></td>
				<td><input type="text" name="targets[XXXX].fat" value="" class="input2"/></td>
		
				<td><input type="text" name="targets[XXXX].heart" value="" class="input2"/></td>
				<td><input type="text" name="targets[XXXX].bmi" value="" class="input2"/></td>
				<td><input type="text" name="targets[XXXX].waistHip" value="" class="input2"/></td>
			</tr>
			<tr class="bcZZZZ">
				<td nowrap>奖励</td>
				<td colspan="10"><input type="text" name="targets[XXXX].reward" value="" class="input3"/></td>
			</tr>
		</tbody>
	</table>
</body>
</html>
