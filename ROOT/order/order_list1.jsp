<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="瘦身减肥课程,肌肉健美课程,增强体质" />
<meta name="description"
	content="健身教练的商务中心是健身教练制定的健身套餐,健身课程，如：瘦身减肥课程,肌肉健身课程,增强体质课程等。" />
<title>订单管理</title>
<link href="css/club-order.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="css/pulicstyle.css" />
<script   type="text/javascript">
$(document).ready(function(){
	$('#startDate').datepicker({changeYear: true});
	$('#endDate').datepicker({changeYear: true});
	$("#dialogPay").dialog({autoOpen : false, show : "blind", hide : "explode", resizable : false, width : 1000, modal : true});
	$("#dialogProduct1").dialog({autoOpen : false, show : "blind", hide : "explode", resizable : true, width : 700, modal : true});
	$("#dialogPlan").dialog({autoOpen : false, show : "blind", hide : "explode", resizable : true, width : 700, modal : true});
});
function onQuery(){
	//queryByPage(1);
	
}  
function goAjax(url){
	$.ajax({type:"post",url:url,data:"",
		success:function(msg){
			$("#right-2").html(msg);
		}
	});
}
function delOrder(orderId, type){
	if(window.confirm("确认取消该订单？")){
		$('#orderId').val(orderId);
		$('#queryType').val(type);
		var parms = $('#queryForm').serialize() + "&type=" + type;
		$.ajax({type:'post',url:'order!delete.asp',data: parms,
			success:function(msg){
				//$("#right-2").html(msg);
				alert("当前订单已成功取消！");
			}
		});
	}
}
function payment(id,type){
	$('#orderId').val(id);
	$('#queryType').val(type);
	$("#queryForm").attr("action","order!payOrder.asp");
	$("#queryForm").submit();
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
function seeProduct(productId,otype, ptype,memberName, oid){
	if (otype == '1') {
		if(ptype=="1" || ptype=="2" || ptype=="3" || ptype=="4" || ptype=="5" || ptype == "6"){
			//preShop(productId);
			$.ajax({type:'post',url:'shop!preShop.asp',data:"product.id="+productId+"&goType=1",
				success:function(msg){
					$('#right-2').html(msg);
				}
			});
		}else if(ptype=="7" || ptype == '8'){
			showProduct(productId,memberName);
		}
	} else {
		$.ajax({type:'post',url:'active!view.asp',data:"id="+oid+"&goType=1",
			success:function(msg){
				$("#dialogProduct1").html(msg);
				$('#dialogProduct1').dialog('open');
			}
		});
	}
}
function seePlan(productId){
	$.ajax({type:'post',url:'plan!view.asp',data:"pid="+productId,
		success:function(msg){
			$("#dialogPlan").html(msg);
			$('#dialogPlan').dialog('open');
		}
	});
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
function showProduct(productId,memberName){
	$.ajax({type:'post',url:'product!edit.asp',data:"product.id="+productId,
		success:function(msg){
			var product = $.parseJSON(msg)[0];
			$('#productMember').html(memberName);
			$('#productName').html(product.name);
			var courseType = product.proType;
			if(courseType == '7'){
				$('#productCourseType').html('计次收费');
				$('#productNum').html(product.num+'次,有效期限'+product.wellNum+'月');
			}else if(courseType == '8'){
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

function productDetail(productId, productType) {
	if (productType == 1) {
		$('#queryForm').attr('action', 'clublist!shoGo.asp?productId='+productId);
		$('#queryForm').submit();
	} else if (productType == 2) {
		$('#queryForm').attr("action", 'activewindow.asp?id='+productId);
		$('#queryForm').submit();
	} else if (productType == 3) {
		$('#queryForm').attr("action", 'plan.asp?pid='+productId);
		$('#queryForm').submit();
	} else if (productType == 6) {
		$('#queryForm').attr("action", 'goods.asp?goodsId='+productId);
		$('#queryForm').submit();
	}
}

</script>
</head>
<body>
<s:form id ="queryForm" name="queryForm" method="post" action="order!query.asp" theme="simple">
<s:hidden name="order.id" id="orderId"/>
<s:hidden name="payNo" id="payNo"/>
<s:hidden name="queryType" id="queryType"/>
<s:hidden name="product.id" id="productId"/>
    <h1><s:if test="query.product.type=1">健身套餐订单</s:if><s:elseif test="query.product.type=2">实物商品订单</s:elseif><s:else>订单</s:else></h1>
    <div>
      <div class="div1"> 交易起止日期
      	<input type="text" name="query.startDate" id="startDate" class="date-1" value="<s:date name="query.startDate" format="yyyy-MM-dd"/>" />
       	 到
       	<input type="text" name="query.endDate" id="endDate" class="date-1" value="<s:date name="query.endDate" format="yyyy-MM-dd"/>" />
        <s:select name="query.status" list="#{'0':'未确认','1':'履约中','2':'已完成','3':'已暂停'}" listKey="key" listValue="value" headerKey="" headerValue="请选择" cssClass="date-2"/>
        <input type="submit" name="button" value="查询" class="button" onclick="onQuery();"/>
      </div>
      <div class="div2">
        <table width="100%" border="0" cellpadding="0" cellspacing="0"  style="white-space:normal">
          <tr class="end">
            <th id="tdtop" style="width:100px;">订单编号</th>
            <th id="tdtop" style="width:80px;">订单类型</th>
            <th id="tdtop" style="width:120px;">订单商品</th>
			<th id="tdtop" style="width: 70px;text-align:left;padding-left: 10px;">
				<s:if test="queryType==\"1\"">收货人</s:if>
				<s:else>发货人</s:else></th>
			<th id="tdtop" style="width:50px;">金额</th>
            <th id="tdtop" style="width:144px;">下单时间</th>
            <th id="tdtop" style="width:58px;">订单状态</th>
            <th  id="tdtop"  style="border-right:1px solid #fff;">操作</th>
          </tr>
          <s:iterator value="pageInfo.items" status="sta">
          <s:if test="#sta.last">
          <tr class="end">
          </s:if>
          <s:else>
          <tr>
          </s:else>
            <td class="left" style="width:100px;">
            	<div style="width:100px;word-break:break-all;float:left;padding-left:10px;">
           	 		<s:property value="no"/>
            	</div>
            </td>
            <td style="width:80px;white-space:normal;word-wrap：break-word;"><div style="width:80px;word-break:break-all;padding-left:10px;"><s:property value="orderType"/></div></td>
            <td style="width:120px;white-space:normal;word-wrap：break-word;padding-left:10px; "><div style="word-break:break-all;">
            <s:if test="type==\"1\" || type==\"2\"|| type==\"3\" || type==\"6\""><a style="float:left;padding-left:0px;" href="javascript:productDetail(<s:property value="productId"/>,<s:property value="type"/>);"><s:property value="name"/></a></s:if><s:else><s:property value="name"/></s:else>
            </div></td>
            <td style="width:70px;"><div style="word-break:break-all;padding-left: 10px;"><s:if test="queryType == \"1\""><s:property value="fromName"/></s:if><s:else><s:property value="toName"/></s:else><div></td>
            <td style="width:50px;white-space:normal;word-wrap：break-word "><div style="width:50px;word-break:break-all;padding-left: 10px;"><s:property value="orderMoney"/></div></td>
            <td style="width:58px;white-space:normal;word-wrap：break-word "><div style="word-break:break-all;padding-left:10px;"><s:date name="orderDate" format="yyyy-MM-dd HH:mm:ss"/></div></td>
            <td style="width:58px;">
	            <div style="word-break:break-all;padding-left:10px;">
		            <s:if test="status==\"0\"">未确认</s:if>
		          	<s:if test="status==\"1\"">履约中</s:if>
		          	<s:if test="status==\"2\"">已完成</s:if>
		          	<s:if test="status==\"3\"">已暂停</s:if>
		          	<s:if test="status==\"4\"">已确认</s:if>
		          	<s:if test="status==\"5\"">违约</s:if>
	          	</div>
            </td>
            <td class="right"><div style="word-break:break-all;padding-left:10px;">
            	<s:if test="status==\"0\"">
            	<s:if test="fromId == #session.loginMember.id">
            	<a id="colotoa" href="javascript:payment('<s:property value="id"/>', '<s:property value="type"/>')">付款</a>
	            <a id="colotoa" href="javascript:delOrder('<s:property value="id"/>', '<s:property value="type"/>');">取消订单</a>
	            </s:if>
	            </s:if>
	            <s:else>
	            <s:if test="productId != 1">
	            <s:if test="fromId == #session.loginMember.id">
	            	<s:if test="orderType == \"3\"">
	            		<a href="javascript:seePlan('<s:property value="productId"/>');">查看</a>
	            	</s:if>
	            	<s:elseif test="orderType == \"1\"||orderType == \"2\"">
	            		<a id="colotoa" href="javascript:seeProduct('<s:property value="productId"/>','<s:property value="orderType"/>', '<s:property value="proType"/>','<s:property value="toName"/>', '<s:property value="id"/>');">查看</a>
	            	</s:elseif>
	            	<a id="colotoa" href="javascript:goAjax('complaint!edit.asp?complaint.type=<s:property value="orderType"/>&complaint.objId=<s:property value="id"/>');">投诉</a>
	            </s:if>
	            </s:if>
	            </s:else>
	            </div>
            </td>
          </tr>
          </s:iterator>
        </table>
        <s:include value="/share/pageAjax.jsp"/>
      </div>
      
    </div>
</s:form>
<div id="dialogProduct" title="课程收费信息" style="display: none;">
	<div>
		服务商：<span id="productMember"></span>
	</div>
	<div>
		名称：<span id="productName"></span>
	</div>
	<div>
		类型：<span id="productCourseType"></span>
	</div>
	<div>
		数量：<span id="productNum"></span>
	</div>
	<div>
		金额：<span id="productCost"></span>
	</div>
	<div>
		描述：<span id="productRemark"></span>
	</div>
	<div>
	   <input type="button" value="取消" onclick="onCloseProduct()" class="butok"/>
	</div>
</div>
<div id="dialogPay" title="支付保证金" style="display: none;"></div>
<div id="dialogProduct1" title="商品信息" style="display: none;"></div>
<div id="dialogPlan" title="计划信息" style="display: none;"></div>
</body>
</html>					