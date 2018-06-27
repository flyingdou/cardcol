<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragrma", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="购物车" />
<meta name="description" content="购物车" />
<title>健身E卡通_购物车</title>

<link rel="stylesheet" type="text/css" href="css/pulic-1.css" />
<link rel="stylesheet" type="text/css" href="css/order.css" />
<script type="text/javascript">
	$(document).ready(function() {
		$(".datime").datepicker({
			changeYear : true,
			minDate : new Date()
		});
	});

	function delShop(idType) {
		if (window.confirm("确认不购买该物品？")) {
			$('#queryForm').attr('action','shop!deleteProduct.asp?idType='+idType);
			$('#queryForm').submit();
		}
	}
	//保留2位小数，如：2，会在2后面补上00.即2.00   
	function toDecimal2(x) {
		var f = parseFloat(x);
		if (isNaN(f)) {
			return false;
		}
		var f = Math.round(x * 100) / 100;
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
	function changeCountMoney() {
		var countMoney = 0;
		$("td[class='tdOrderMoney']").each(
			function() {
				if ($(this).parent().children(0).children().prop("checked")) {
					$(this).prev().prev().prev().children(0).next().next().next().next().next().val(true);
					countMoney += parseFloat($(this).children().html().substring(1));
				}else{
					$(this).prev().prev().prev().children(0).next().next().next().next().next().val(false);
				}
			});
		$("#countMoney").html("￥" + toDecimal2(countMoney));
	}

	function changeMoney(id) {
		var countValue = $("#inputCount" + id).val();
		if (isNaN(countValue)) {
			alert("请输入正确的数量！");
			$("#inputCount" + id).focus();
			$("#inputCount" + id).select();
			return;
		}
		var orderMoney = (countValue) * parseFloat($("#tdUnitPrice" + id).html().substring(1));
		$("#tdIntegral" + id).html(toDecimal2(orderMoney / 10) + "分");
		$("#tdOrderMoney" + id).html("￥" + toDecimal2(orderMoney));
		changeCountMoney();
	}

	function submitShop() {
		var flag = 0;
		var checkIds = "";
		$(".cart_td_checkbox").each(
			function() {
				if ($(this).prop("checked")) {
					if ($(this).parent().next().next().next().children().children().next().val() == "") {
						alert("请填写开始日期!");
						$(this).parent().next().next().next().children().children(1).focus();
						flag = 0;
						return false;
					}
					flag = 1;
				}
				if ($(this).prop("checked") == false) {
					$(this).next().val("");
				}
			});
		if (flag == 1) {
			$("#editForm").submit();
		}
	}

	$.fx.speeds._default = 1000;
	$(function() {
		$("#dialogProduct").dialog({
			autoOpen : false,
			show : "blind",
			hide : "explode",
			resizable : false
		});
	});
	function showProduct(productId) {
		$.ajax({
			type : 'post',
			url : 'product!edit.asp',
			data : "product.id=" + productId,
			success : function(msg) {
				var product = $.parseJSON(msg)[0];
				$('#productName').html(product.name);
				var courseType = product.courseType;
				if (courseType == '1') {
					$('#productCourseType').html('计次收费');
					$('#productNum').html(product.num + '次');
				} else if (courseType == '2') {
					$('#productCourseType').html('计时收费');
					$('#productNum').html(product.num + '月');
				}
				;
				$('#productCost').html(product.cost + '元');
				$('#productRemark').html(product.remark);
				$("#dialogProduct").dialog("open");
			}
		});
	}
	function onCloseProduct() {
		$("#dialogProduct").dialog("close");
	}
	
	function productDetail(productId, productType){
		if(productType == 1){
			url = "clublist!shoGo.asp?productId="+productId;
			$('#queryForm').attr('action',url);
			$('#queryForm').submit();
		}else if(productType == 3){
			url = "plan.asp?pid="+productId;
			$('#queryForm').attr("action",url);
			$('#queryForm').submit();	
		}else if(productType == 6){
			url = "goods.asp?goodsId="+productId;
			$('#queryForm').attr("action",url);
			$('#queryForm').submit();	
		}
	}

	function selectAll(seleall) {
		if (seleall.checked) {
			$(".cart_td_checkbox").each(function() {
				$(this).prop("checked", true);
			});
		} else
			$(".cart_td_checkbox").each(function() {
				$(this).prop("checked", false);
			});
		changeCountMoney();

	}
	function seleone() {
		$("#seleall").prop("checked", false);
		changeCountMoney();

	}

	function goBindingMobile() {
		window.open("mobile.asp");
	}
</script>
</head>
<body>
	<s:form id="queryForm" name="queryForm" method="post" action="shop.asp"
		theme="simple">
		<s:hidden name="product.id" id="productId" />
		<s:hidden name="shopCount" id="shopCount" />
	</s:form>
	<s:include value="/share/home-header_1.jsp" />
	<s:form id="editForm" name="editForm" action="order.asp" method="post"
		theme="simple">
		<div class="order_content">
			<div class="cart_qq">
				<div class="order_gouwu">我的购物车</div>
				<div class="cart_nav">
					<ul>
						<li><span><b>1.</b>查看购物车</span></li>
						<li><b>2.</b>选择支付方式</li>
						<li><b>3.</b>购买成功</li>
					</ul>
				</div>
			</div>
			<div class="cart_table">
				<table width="900" cellspacing="0" cellpadding="0">
					<tr class="order_trd">
						<td width="98"><input type="checkbox" id="seleall"
							name="seleall" onclick="selectAll(this);" /> 全选</td>
						<td width="205" class="order_xm">项目</td>
						<td width="178">单价</td>
						<td width="238">开始日期</td>
						<td width="120">金额</td>
						<td width="59">&nbsp;</td>
					</tr>
					<s:iterator value="#request.shops" status="stat">
						<tr class="order_style bord_sty">
							<td class="cart_td"><input class="cart_td_checkbox"
								type="checkbox" onclick="seleone();" /></td>
							<td>
								<input type="hidden" name="shopDtos[<s:property value="#stat.index"/>].shop_id" value="<s:property value="id"/>" />
								<input type="hidden" name="shopDtos[<s:property value="#stat.index"/>].prod_id" value="<s:property value="pro_id"/>" /> 
								<input type="hidden" name="shopDtos[<s:property value="#stat.index"/>].prod_type" value="<s:property value="type"/>" /> 
								<input type="hidden" name="shopDtos[<s:property value="#stat.index"/>].prod_name" value="<s:property value="pro_name"/>" /> 
								<input type="hidden" name="shopDtos[<s:property value="#stat.index"/>].prod_price" value="<s:property value="price"/>" /> 
								<input type="hidden" name="shopDtos[<s:property value="#stat.index"/>].checked" value="" />
								
								<div class="shop_mai">商品：<a href="javascript:productDetail('<s:property value="pro_id"/>','<s:property value="type"/>');"><s:property value="pro_name" /></a></div>
								<div class="shop_mai">卖家：<a href="javascript:goMemberHome('<s:property value="member_id"/>','<s:property value="member_role"/>');"><s:property value="member_nick" /></a></div>
							</td>
							<td id="tdUnitPrice<s:property value="id"/>"><div class="danjia">￥<s:property value="price" /></div></td>
							<td><div class="data_time">
									<img src="images/order_rr.gif" class="pic_img" /><input type="text" name="shopDtos[<s:property value="#stat.index"/>].start_date" class="datime" />
								</div>
							</td>
							<td class="tdOrderMoney" id="tdOrderMoney<s:property value="id"/>"><div class="cart_price">￥<s:property value="PRICE" /></div>
							</td>
							<td><a id="a<s:property value="id"/>" class="detede" href="javascript:delShop('<s:property value="id"/>,<s:property value="type"/>');">删除</a></td>
						</tr>
					</s:iterator>
					<tr class="order_style">
						<td>&nbsp;</td>
						<td colspan="4"><div class="order_price">
								<strong>商品金额总计:</strong><span id="countMoney">￥0.00</span>
							</div></td>
						<td>&nbsp;</td>
					</tr>
				</table>
				<div class="order_foot">
					<div class="order_info">
						<s:if test="member.mobileValid=='1'">
							<p>您的订单信息将发送至手机号：<span><s:property value="#request.mobilephone" /></span></p>
						</s:if>
						<s:else>
							<p>您还没有绑定手机，<a href="javascript:goBindingMobile();">绑定手机号</a></p>
						</s:else>
					</div>
					<div class="dingdan">
						<a href="javascript:submitShop();"><img src="images/order_gg.gif" /></a>
					</div>
				</div>
			</div>
		</div>
	</s:form>
	<s:include value="/share/footer.jsp" />
</body>
</html>