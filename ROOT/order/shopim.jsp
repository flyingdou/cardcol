<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp"/>
<meta name="keywords" content="购物车" />
<meta name="description" content="购物车" />
<title>健身E卡通_购物车</title>
<link rel="stylesheet" type="text/css" href="css/order.css"/>
<script type="text/javascript">
$(document).ready(function(){
	$(".datime").datepicker({changeYear: true,minDate:new Date()});
	$("#countMoney").html("￥" + toDecimal2(countMoney));
});

function saveAudit(){
	if($(".datime").val()==""){
		alert("日期不能为空！");
		$(".datime").focus();
		return ;
	}
	if($('#mobileSpan').html()!=null){
		$("#orderForm").attr("action","order!payProd.asp");
		$("#orderForm").submit();
	}else{
		alert("必须绑定手机号才能提交！");
	}
	
	
}
function goBindingMobile(){
	window.open("mobile.asp");
}

function productDetail(productId, productType){
	if(productType == 1){
		url = "clublist!shoGo.asp?productId="+productId;
		$('#orderForm').attr('action',url);
		$('#orderForm').submit();
	}else if(productType == 3){
		url = "plan.asp?pid="+productId;
		$('#orderForm').attr("action",url);
		$('#orderForm').submit();	
	}else if(productType == 6){
		url = "goods.asp?goodsId="+productId;
		$('#orderForm').attr("action",url);
		$('#orderForm').submit();	
	}
}
</script>
</head>
<body>
	<s:include value="/share/home-header_1.jsp" />
	<s:form id="orderForm" name="orderForm" action="order!saveAudit.asp" method="post" theme="simple">
		<input type="hidden" id="factoryStartTime" name="factoryStartTime" value="${factoryStartTime}" />
		<input type="hidden" id="factoryEndTime" name="factoryEndTime" value="${factoryEndTime}" />
		<input type="hidden" id="teamId" name="teamId" value="${teamId}" />
		<input type="hidden" id="createMode" name="createMode" value="${createMode}" />
		<input type="hidden" id="teamName" name="teamName" value="${teamName}" />
		<input type="hidden" id="members" name="members" value="${members}" />
		<input type="hidden" id="partakeJudge" name="judge" value="${judge}" />
		<div class="order_content">
     		<div class="public_nav">
        		<ul>
           			<li><span><b>1.</b>提交定单</span></li>
           			<li><b>2.</b>选择支付方式</li>
           			<li><b>3.</b>购买成功</li>
        		</ul>
     		</div>
     		<div class="order_table">
         		<table width="810" cellspacing="0" cellpadding="0">
  					<tr class="order_trd">
    					<td width="219" class="order_xm">项目</td>
    					<td width="192">单价</td>
    					<td width="258">开始日期</td>
    					<td width="139"><div class="order_price">金额</div></td>
  					</tr>
					<s:iterator value="#request.list" status="stat">
						<tr class="order_style bord_sty">
							<td>
								<s:if test="startDate!=''"><input type="hidden" name="shopDtos[<s:property value="#stat.index"/>].start_date" value="<s:property value="startDate"/>" /></s:if>
								<input type="hidden" name="shopDtos[<s:property value="#stat.index"/>].prod_id" value="<s:property value="pro_id"/>" /> 
								<input type="hidden" name="shopDtos[<s:property value="#stat.index"/>].prod_type" value="<s:property value="type"/>" /> 
								<input type="hidden" name="shopDtos[<s:property value="#stat.index"/>].prod_name" value="<s:property value="pro_name"/>" /> 
								<input type="hidden" name="shopDtos[<s:property value="#stat.index"/>].prod_price" value="<s:property value="price"/>" /> 
								<input type="hidden" name="shopDtos[<s:property value="#stat.index"/>].checked" value="" />
								<div class="shop_mai">商品：<a href="javascript:productDetail('<s:property value="pro_id"/>','<s:property value="type"/>');"><s:property value="pro_name"/></a></div>
    							<div class="shop_mai">卖家：<a href="javascript:goMemberHome('<s:property value="member_id"/>','<s:property value="member_role"/>');"><s:property value="member_nick"/></a></div></td>
							<td id="tdUnitPrice<s:property value="id"/>"><div class="danjia">￥<s:property value="price" /></div></td>
							<td><div class="data_time"><img src="images/order_rr.gif" class="pic_img" /><input type="text" name="shopDtos[<s:property value="#stat.index"/>].start_date" value = "<s:property value="startDate"/>" <s:if test="startDate!=''">disabled="disabled"</s:if> class="datime" /></div></td>
							<td class="tdOrderMoney" id="tdOrderMoney<s:property value="id"/>"><div class="cart_price">￥<s:property value="PRICE" /></div></td>
						</tr>
						<tr class="order_style bord_sty">
    						<td>&nbsp;</td>
    						<td>&nbsp;</td>
    						<td>&nbsp;</td>
    						<td>&nbsp;</td>
  						</tr>
  						<tr class="order_style">
    						<td colspan="4"><div class="order_price"><strong>商品金额总计:</strong><span>￥<s:property value="PRICE" /></span></div></td>
  						</tr>
					</s:iterator>
				</table>
    			<div class="order_foot">
       				<div class="order_info">
       					<s:if test="#request.mobilephone != null">
       						<p>您的订单信息将发送至手机号：<span id="mobileSpan"><s:property value="#request.mobilephone" /></span></p>
       					</s:if>
       					<s:else>
        					<p>您还没有绑定手机，<a href="javascript:goBindingMobile();">绑定手机号</a></p>
        				</s:else>
       				</div>
       				<div class="dingdan"><a href="javascript:saveAudit();"><img src="images/order_gg.gif" /></a></div>
     			</div>
     		</div>
		</div>
	</s:form>
<s:include value="/share/footer.jsp"/>	
</body>
</html>