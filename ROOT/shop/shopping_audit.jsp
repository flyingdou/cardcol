<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragrma","no-cache");
response.setDateHeader("Expires",0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp"/>
<meta name="keywords" content="购物车" />
<meta name="description" content="购物车" />
<title>卡库网_购物车</title>
<link rel="stylesheet" type="text/css" href="css/shoppingcart1.css"/>
<script type="text/javascript" src="script/area.js"></script>
<script type="text/javascript" src="script/jquery.choosearea.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	changeCountMoney();
	var chooseAreaApp1 = new $.choosearea({
		selectDomId : {
			province : "province",
			city : "city",
			county : "county"
		},
		initAreaNames : {
			province : '<s:property value="#session.loginMember.province" escape="false"/>',
			city : '<s:property value="#session.loginMember.city" escape="false"/>',
			county : '<s:property value="#session.loginMember.county" escape="false"/>'	
		},
		data : data
	});
	$('input[type="radio"][name="order.alwaysAddr.id"]').change(function(){
		var val = $(this).val();
		if (val == 0) $('#otherAddr').css('display', 'block');
		else $('#otherAddr').css('display', 'none');
	});
});
function delShop(id){
	if(confirm("是否确认删除当前商品？")){
		$.ajax({type:'post',url:'shop!delete.asp',data:"shop.id="+id,
			success:function(msg){
				if(msg == "ok"){
					$("#a"+id).parent().parent().parent().parent().remove();
					changeCountMoney();
					changeShopCount();
				}else{
					alert(msg);
				}
			}
		});
	}
}
function changeShopCount(){
	var shopCount = 0;
	$("input[name='inputCount']").each(function(index){
		shopCount += parseInt($(this).val());
	});
	$(".ainsp").html(shopCount);
}
//保留2位小数，如：2，会在2后面补上00.即2.00   
function toDecimal2(x) {   
    var f = parseFloat(x);   
    if (isNaN(f)) {   
        return false;   
    }   
    var f = Math.round(x*100)/100;   
    var s = f.toString();   
    var rs = s.indexOf('.');   
    if (rs < 0) {   
        rs = s.length;   
        s += '.';   
    }   
    while (s.length <= rs + 2) {   
        s += '0';   
    }   
    return s;   
}   
function changeCountMoney(){
	var countMoney = 0;
	$("td[name='tdOrderMoney']").each(function(){
		countMoney += parseFloat($(this).html().substring(1));
	});
	$("#countMoney").html("￥"+toDecimal2(countMoney));
	$("#contractMoney").html("￥"+toDecimal2(countMoney));
}
function changeCount(type,id){
	var val = 0;
	if(type == "1"){
		val = -1;
	}else if(type =="2"){
		val = 1
	}
	var countVal = parseInt($("#inputCount"+id).val());
	var countValue = countVal+val;
	if(countValue>0){
		$("#inputCount"+id).val(countValue);
		changeMoney(id);
	}
}
function changeMoney(id){
	var countValue = $("#inputCount"+id).val();
	if(isNaN(countValue)){
		alert("请输入正确的数量！");
		$("#inputCount"+id).focus();
		$("#inputCount"+id).select();
		return;
	}
	var orderMoney = (countValue)*parseFloat($("#tdUnitPrice"+id).html().substring(1));
	$("#tdIntegral"+id).html(toDecimal2(orderMoney/10)+"分");
	$("#tdOrderMoney"+id).html("￥"+toDecimal2(orderMoney));
	changeCountMoney();
}
function saveAudit(){
	if ($('input[name="order.alwaysAddr.id"]:checked').val() == '0') {
		alert('请先选择一个有效的收货地址再提交订单！');
		return;
	}
	var productMember = "",productType="",isCoach=true,isSameCoach=true;
	$(".tr1").each(function(index){
		productType = $(this).children("td:eq(0)").children("input:eq(11)").val();
		if(productType == "3"){
			if("<s:property value="#session.loginMember.coach.id"/>"){
				if("<s:property value="#session.loginMember.coach.id"/>" && "<s:property value="#session.loginMember.coach.id"/>" != $(this).children("td:eq(0)").children("input:eq(10)").val()){
					isCoach = false;
					return false;
				}	
			}else{
				if(productMember == "") productMember = $(this).children("td:eq(0)").children("input:eq(10)").val();
				if(productMember != $(this).children("td:eq(0)").children("input:eq(10)").val()){
					isSameCoach=false;
					return false;
				}
			}
		}
	});
	if(!isCoach){
		alert("您已经有私教，请解除原来私教后再购买新的私教服务！");
		return;
	}
	if(!isSameCoach){
		alert("一次只能购买一个教练的课程！");
		return;
	}
	var id, integral,orderMoney , shipType = $("input[name='shipTypeT']:checked").val(), shipTimeType = $("input[name='shipTimeTypeT']:checked").val();
	$("input[name='inputCount']").each(function(index){
		id = $(this).attr("id").substring(10);
		integral = $("#tdIntegral"+id).html();
		integral = integral.substring(0,integral.length-1);
		orderMoney = $("#tdOrderMoney"+id).html().substring(1);
		$("input[name='shopList["+index+"].integral']").val(integral);
		$("input[name='shopList["+index+"].count']").val($(this).val());
		$("input[name='shopList["+index+"].orderMoney']").val(orderMoney);
	});
	$("#orderForm").attr("action","order!saveAudit.asp");
	$("#orderForm").submit();
}
function addAddr(type){
	if (!$("input[name='alwaysAddr.name']").val()) {
		alert('收货人不能为空且长度必须为50字以内,请确认！');
		return;
	}
	if (!$("input[name='alwaysAddr.addr']").val()) {
		alert("街道地址不能为空且长度必须为50字以内,请确认");
		return;
	}
	if (!$("input[name='alwaysAddr.zipCode']").val()) {
		alert("您输入的邮编长度非法,请确认");
		return;
	}
	if (!$("input[name='alwaysAddr.email']").val()) {
		alert('请输入邮箱地址！');
		return;
	}
	if (!$("input[name='alwaysAddr.moble']").val()) {
		alert('请输入手机号码！');
		return;
	}
	$("input[name='alwaysAddr.isAlways']").val(type);
	$.ajax({type:'post',url:'alwaysaddr!save.asp',data:$('#orderForm').serialize(),
		success:function(msg){
			var _json = $.parseJSON(msg);
			if (_json.success == true) {
				$("input[name='order.alwaysAddr.id']").attr("checked", false);
				var _htmls = "<input name='order.alwaysAddr.id' type='radio' checked='checked' value='" + _json.key + "'/>";
				_htmls += $("input[name='alwaysAddr.name']").val() + "\t" + $('#province').val() + $('#city').val() + $('#county').val() + $("input[name='alwaysAddr.addr']").val();
				_htmls += "<a style=\"padding-left:10px;\" href=\"javascript:deleteAddr('" + _json.key + "')\">删除</a>";
				$('#pAlwaysAddrs').children('div:first').before(_htmls);
			} else {
				alert(_json.message);
			}
		}
	});
}
function deleteAddr(id){
	$.ajax({type:'post',url:'alwaysaddr!delete.asp',data:"alwaysAddr.id="+id,
		success:function(msg){
			var _json = $.parseJSON(msg);
			if (_json.success === true) {
				$("#div"+id).remove();
			} else if (_json.message === 'reference') {
				alert("当前收货人信息已被其它订单引用，无法删除！");
			} else {
				alert(_json.message);
			}
		}
	});
}
$.fx.speeds._default = 1000;
$(function() {
	$( "#dialogProduct" ).dialog({
		autoOpen: false,
		show: "blind",
		hide: "explode",
		resizable: false
	});
});
function showProduct(productId){
	$.ajax({type:'post',url:'product!edit.asp',data:"product.id="+productId,
		success:function(msg){
			var product = $.parseJSON(msg)[0];
			$('#productName').html(product.name);
			var courseType = product.courseType;
			if(courseType == '1'){
				$('#productCourseType').html('计次收费');
				$('#productNum').html(product.num+'次');
			}else if(courseType == '2'){
				$('#productCourseType').html('计时收费');
				$('#productNum').html(product.num+'月');
			};
			$('#productCost').html(product.cost+'元');
			$('#productRemark').html(product.remark);
			$( "#dialogProduct" ).dialog( "open" );
		}
	});
}
function onCloseProduct(){
	$( "#dialogProduct" ).dialog( "close" );
}
function preShop(productId,memberId){
	if("<s:property value="#session.loginMember.id"/>" == ""){
		//alert("请先登录才能购买商品！");
		openLogin();
		return;
	}
	$('#productId').val(productId);
	$('#queryForm').attr('action','shop!preShop.asp');
	$('#queryForm').submit();
}
</script>
</head>
<body>
<s:form id ="queryForm" name="queryForm" method="post" action="shop.asp" theme="simple">
<s:hidden name="product.id" id="productId"/>
</s:form>
<s:include value="/share/header.jsp"/>
<div id="content">
	<div id="cont">
	  <div class="contop1">
	     <ul>
			<li class="last"><b>1.我的购物车</b></li>
			<li class="first"><b>2.填写并核对订单信息</b></li>
			<li><b>3.选择支付方式</b></li>
			<li><b>4.成功提交订单</b></li>
		 </ul>
	  </div>
	  <div class="contop2">
	   请确认以下信息，然后提交订单
	  </div>
	  <s:form id="orderForm" name="orderForm" action="order!saveAudit.asp" method="post" theme="simple">
	  <s:hidden name="alwaysAddr.id"/>
	  <s:hidden name="alwaysAddr.isAlways"/>
	  <div class="contop3">
	     <div class="contop3-1">
		   <h3 id="addrH3">收货人信息</h3>
		   <div id="pAlwaysAddrs" style=" margin-left:20px; margin-top:10px;">
			<s:iterator value="member.alwaysAddrs" status="stat" var="add">
				<div style="margin-top:7px;" id="div<s:property value="id"/>">
					<input <s:if test="#stat.first">checked="checked"</s:if> type="radio" name="order.alwaysAddr.id" value="<s:property value="id"/>"/>
					<s:property value="name"/>&nbsp;&nbsp;<s:property value="province"/><s:property value="city"/><s:property value="county"/><s:property value="addr"/>
					<a style=" padding-left:10px;" href="javascript:deleteAddr('<s:property value="id"/>')">删除</a>
				</div>
			</s:iterator>
				<div style="margin-top:7px;">
					<input type="radio" name="order.alwaysAddr.id" value="0" />&nbsp;其它
				</div>
		   </div>
		   <div id="otherAddr" style="display: none;">
			   <div class="p_div"><p>收　货　人：</p>
			     <input type="text" name="alwaysAddr.name" value="<s:property value='#session.loginMember.name'/>" maxlength="50" class="inp3-1"/>
			   </div>
			   <div class="p_div"><p>地　　　区：</p>
			   	 <select name="alwaysAddr.province" id="province" ></select>
				 <select name="alwaysAddr.city" id="city" ></select>
				 <select name="alwaysAddr.county" id="county" ></select>
			   </div>
			   <div class="p_div"><p>街 道 地 址：</p>
			      <input type="text" name="alwaysAddr.addr" value="<s:property value='#session.loginMember.address'/>" maxlength="50" value="" class="inp3-3"/>
			   </div>
			   <div class="p_div"><p>邮　　　编：</p>
			      <input type="text" name="alwaysAddr.zipCode" value="<s:property value='#session.loginMember.postal'/>" maxlength="6" class="inp3-1" style="width:150px; height:20px; border:1px solid #ccc;"/>
				  <span class="spap1">电子邮箱：
				  <input type="text" name="alwaysAddr.email" value="<s:property value='#session.loginMember.email'/>" maxlength="50" class="inp3-1"/>
				  </span>
			   </div>
			   <div class="p_div"><p>手　　　机：</p>
				   <input type="text" name="alwaysAddr.moble" value="<s:property value='#session.loginMember.mobilephone'/>" maxlength="11" class="inp3-1"/>
				   <span class="spap2">或者</span>
				   <span> 固定电话：
				   <input type="text" name="alwaysAddr.tell" value="<s:property value='#session.loginMember.tell'/>" maxlength="12" class="inp3-1"/></span>
			   </div>
			   <p class="last_p">
			     <a href="javascript:addAddr('1');">[添加到常用地址]</a>
			   </p>
		   </div>
		 </div>
	  </div>
	  <div class="contop4">
		      <div class="contop4-1">
		         <h3>配送方式和时间</h3>
				 <h4>送货方式</h4>
					 <p><input type="radio" name="order.shipType" value="1" checked="checked"/>
					 不需要配送</p>
					 <p><input type="radio" name="order.shipType" value="2"/>
					 快递送货上门</p>
				 <h4>送货时间</h4>
					   <p>
						  <input type="radio" name="order.shipTimeType" value="1" checked="checked"/>
						 工作日，双休日，节假日均可
					   </p>
					   <p>
						   <input type="radio" name="order.shipTimeType" value="2"/>
						 只双休日，节假日送货（工作日不送货）
					   </p>
					   <p>
						  <input type="radio" name="order.shipTimeType" value="3"/>
						 只工作日送货（双休日，节假日不送货）
					   </p>
			  </div>
	  </div>
	 <!--  
	  <div class="contop5">
	       <div class="contop5-1">
		     <h3>支付方式</h3>
			 <form id="form3" name="form3" method="post" action="">
			   <p><input type="radio" name="payTypeT" value="2" id="payTypeT"  checked="checked"/>
			   <img src="images/unionpay.png">
			   <span>银联在线支付服务是由中国银联提供的互联网支付服务，全面支持各大银行的信用卡、借记卡。 </span>
			   </p>
			   <p><input type="radio" name="payTypeT" value="1" id="payTypeT" />
			   <img src="images/alipay.png">
			   <span>支付宝支付是指通过支付宝支付储值账户进行的在线付款的支付方式。</span>
			   </p>  
			 </form>
		   </div>
	  </div>
	  -->
	  <input type="hidden" name="subject" id="subject"/>
	  <input type="hidden" name="total_fee" id="total_fee"/>
	  <input type="hidden" name="body" id="body"/>
	  <div class="contop6">
	      <h3>商品清单</h3>
		  <div class="contop6-1">
		    <table>
			     <tr>
				    <th class="th1">商品</th>
					<th class="th2">赠送积分</th>
					<th class="th2">单价</th>
					<th class="th2">数量</th>
					<th class="th2">小计(元)</th>
					<th class="th3">操作</th>
				 </tr>
				 <s:iterator value="shopList" status="stat">
				 <tr class="tr1">
				    <td>
				    <input type="hidden" name="shopList[<s:property value="#stat.index"/>].id" value="<s:property value="id"/>"/>
					<input type="hidden" name="shopList[<s:property value="#stat.index"/>].member.id" value="<s:property value="member.id"/>"/>
					<input type="hidden" name="shopList[<s:property value="#stat.index"/>].product.id" value="<s:property value="product.id"/>"/>
					<input type="hidden" name="shopList[<s:property value="#stat.index"/>].unitPrice" value="<s:property value="unitPrice"/>"/>
					<input type="hidden" name="shopList[<s:property value="#stat.index"/>].count" value="<s:property value="count"/>"/>
					<input type="hidden" name="shopList[<s:property value="#stat.index"/>].orderMoney" value="<s:property value="orderMoney"/>"/>
					<input type="hidden" name="shopList[<s:property value="#stat.index"/>].contractMoney" value="<s:property value="contractMoney"/>"/>
					<input type="hidden" name="shopList[<s:property value="#stat.index"/>].integral" value="<s:property value="integral"/>"/>
					<input type="hidden" name="shopList[<s:property value="#stat.index"/>].orderStartTime" value="<s:property value="orderStartTime"/>"/>
					<input type="hidden" name="shopList[<s:property value="#stat.index"/>].orderEndTime" value="<s:property value="orderEndTime"/>"/>
					<input type="hidden" name="shopList[<s:property value="#stat.index"/>].product.member.id" value="<s:property value="product.member.id"/>"/>
					<input type="hidden" name="shopList[<s:property value="#stat.index"/>].product.type" value="<s:property value="product.type"/>"/>
					<input type="hidden" name="shopList[<s:property value="#stat.index"/>].reportDay" value="<s:property value="reportDay"/>"/>
					   <p>卖家：<a href="javascript:goMemberHome('<s:property value="product.member.id"/>','<s:property value="product.member.role"/>');"><s:property value="product.member.nick"/></a></p>
					   <s:if test="product.type==1">
		               <p>套餐：<a href="javascript:preShop('<s:property value="product.id"/>','<s:property value="product.member.id"/>');" class="ap"><s:property value="product.name"/></a></p>
		               </s:if>
		               <s:if test="product.type==2">
		               <p>实物商品：<a href="#" class="ap"><s:property value="product.name"/></a></p>
		               </s:if>
		               <s:if test="product.type==3">
		               <p>教练费：
		               	<a href="javascript:showProduct('<s:property value="product.id"/>');" class="ap"><s:property value="product.name"/></a>
					   </p>
		               </s:if>
					   <s:if test="product.type==4">
		               <p>套餐：<a href="#" class="ap"><s:property value="product.name"/></a></p>
		               </s:if>
					</td>
					<td class="td1" id="tdIntegral<s:property value="id"/>"><s:property value="integral"/>分</td>
					<td class="td1-1" id="tdUnitPrice<s:property value="id"/>">￥<s:property value="unitPrice"/></td>
					<td class="td1">
					<s:if test="product.type==1 || product.type==3">
						 <a><img src="images/jian.png"/></a>
						 <input readonly="true" type="text" id="inputCount<s:property value="id"/>" name="inputCount" value="<s:property value="count"/>" class="td1inp" onchange="changeMoney('<s:property value="id"/>');"/>
						 <a><img src="images/jia.png"/></a>
					</s:if>
					<s:else>
						 <a href="javascript:changeCount('1','<s:property value="id"/>');"><img src="images/jian.png"/></a>
						 <input type="text" id="inputCount<s:property value="id"/>" name="inputCount" value="<s:property value="count"/>" class="td1inp" onchange="changeMoney('<s:property value="id"/>');"/>
						 <a href="javascript:changeCount('2','<s:property value="id"/>');"><img src="images/jia.png"/></a>
					</s:else>					</td>    
					<td class="td1-1" id="tdOrderMoney<s:property value="id"/>" name="tdOrderMoney">￥<s:property value="orderMoney"/></td>
					<td>
					  <!--<p><a href="#">移入收藏夹</a</p>-->
					  <p class="tdp"><span><a id="a<s:property value="id"/>" href="javascript:delShop('<s:property value="id"/>');">删除</a></span></p>
					</td>
				 </tr>
				 </s:iterator>
			</table>
		  </div>
		  <div class="contop6-2">
		     <p class="p2"><span class="span1"><b>商品总价:</b></span><b><span class="span2" id="countMoney">￥200.00</span></b></p>
			 <p class="p2"><span class="span1"><b>应付金额:</b></span><b><span class="span2" id="contractMoney">￥200.00</span></b></p>
			 <p class="p2"><a href="javascript:saveAudit()"><img src="images/tj.png"/></a></p>
		  </div>
	  </div>
	  </s:form>
	</div>
</div>
<s:include value="/share/footer.jsp"/>	
<div id="dialogProduct" title="课程收费信息">
	<p>
		名称：<span id="productName"></span>
	</p>
	<p>
		类型：<span id="productCourseType"></span>
	</p>
	<p>
		数量：<span id="productNum"></span>
	</p>
	<p>
		金额：<span id="productCost"></span>
	</p>
	<p>
		描述：<span id="productRemark"></span>
	</p>
	<p class="pa">
	   <input type="button" value="取消" onclick="onCloseProduct()" class="butok"/>
	</p>
</div>
</body>
</html>