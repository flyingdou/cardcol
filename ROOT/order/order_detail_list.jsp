<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="瘦身减肥课程,肌肉健美课程,增强体质" />
<meta name="description"
	content="健身教练的商务中心是健身教练制定的健身套餐,健身课程，如：瘦身减肥课程,肌肉健身课程,增强体质课程等。" />
<title>结算记录</title>
<link href="css/club-order.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="css/pulicstyle.css" />
<script type="text/javascript">
$(document).ready(function(){
	$('#startDate').datepicker({changeYear: true});
	$('#endDate').datepicker({changeYear: true});
	if($('#orderNoCheck').is(':checked')==true){
		$('#orderNoLi').css('display','block');
	}
	$('#allCheck').click(function(){
		$("input[name='orderQuery.order.no']").val('');
		$('#orderNoLi').css('display','none');
	});
	$('#orderNoCheck').click(function(){$('#orderNoLi').css('display','block');});
});
/* function onQuery(){
	queryByPage(1);
} */
</script>
</head>
<body>
	<s:include value="/share/header.jsp" />
	<div id="content">
	<s:include value="/order/nav.jsp" />
		<div id="right-2">
			<s:form id ="queryForm" name="queryForm" method="post" action="orderdetail!query.asp" theme="simple">
	    <h1>我的钱包</h1>
	    <div>
			 
			  <div class="transaction_records" style="overflow: hidden;">
				  <ul class="transaction1">
						<li>账户余额：<span class="lispato">￥<strong><s:property value="moneys.withdrawals"/></strong></span></li>
						<li>总收入：<span class="lispato">￥<strong><s:property value="moneys.income"/></strong></span></li>
						<li>总支出：<span class="lispato">￥<strong><s:property value="moneys.expenditure"/></strong></span></li>
						<%-- <li>您的已收款：<span class="lispato">￥<strong><s:property value="#request.stat.statData3"/></strong></span></li> --%>
						<%-- <li>您的账户余额：<span  class="lispato">￥<strong><s:property value="#request.stat.statData4"/></strong></span></li> --%>
				  </ul>
			  </div>
			  <div class="transaction_table" style="overflow: hidden;">
				  <ul class="transaction2">
					 <li style="line-height: 32px;">
					  <input id="allCheck" type="radio" name="type" value="1" <s:if test="type == 1">checked="true"</s:if> class="radio" style="margin-top:-3px;"/>
					  全部结算记录</li>
					<li>
					  <input id="orderNoCheck" type="radio" name="type" value="2" <s:if test="type == 2">checked="true"</s:if> class="radio" style="margin-top:-3px;"/>
					  按订单查看结算记录</li>
					<li id="orderNoLi" style="display:none;">输入订单号：
					  <s:textfield name="orderQuery.order.no" cssClass="number"/>
					</li>
				  </ul>
			  </div>
	      <div class="div1"> 交易起止日期
	        <input type="text" name="orderQuery.startDate" id="startDate" class="date-1" value="<s:date name="orderQuery.startDate" format="yyyy-MM-dd"/>" />
	                    到
	        <input type="text" name="orderQuery.endDate" id="endDate" class="date-1" value="<s:date name="orderQuery.endDate" format="yyyy-MM-dd"/>" />
			订单类型:<s:select name="orderQuery.orderType" list="#{'1':'E卡通订单','2':'挑战订单','3':'课程订单','4':'专家计划订单'}" listKey="value" listValue="value" headerKey="" headerValue="请选择" cssClass="date-2"/>
			<%-- 交易类型:<s:select name="orderQuery.detailType" list="#{'1':'预付款','2':'保证金','3':'违约金','4':'缺勤费用','5':'训练费用','6':'交易服务费','7':'交易手续费','8':'超勤费用','9':'退款','10':'提现', 'A':'挑战惩罚金'}" listKey="key" listValue="value" headerKey="" headerValue="请选择" cssClass="date-2"/> --%>
			<%-- 资金流向：<s:radio name="flowType" list="#{'1':'支出','2':'收入'}" listKey="key" listValue="value"/> --%>
	        <input type="submit" name="button" value="查询" class="button" onclick="onQuery();"/>
	      </div>
	      <div class="div2">
	        <table width="100%" border="0" cellpadding="0" cellspacing="0" >
	          <tr class="end">
	            <th id="tdtop">结算时间</th>
	            <th id="tdtop">订单编号</th>
	            <th id="tdtop">订单类型</th>
	            <th id="tdtop">商品名称</th>
	            <th id="tdtop">买方</th>
	            <th id="tdtop">收入</th>
	            <th id="tdtop">支出</th>
	          </tr>
	          <s:iterator value="pageInfo.items" status="sta">
	          <s:if test="#sta.last">
	          <tr class="end">
	          </s:if>
	          <s:else>
	          <tr>
	          </s:else>
	            <td class="left"> <div style="word-break:break-all;text-align: center;"><s:date name="balanceTime" format="yyyy-MM-dd"/></div></td>
	            <td> <div style="word-break:break-all;text-align: center;"><s:property value="orderNo"/></div></td>
	            <td> <div style="word-break:break-all;text-align: center;"><s:property value="balanceType == 1 ? 'E卡通订单' : balanceType == 2 ? '挑战订单' : balanceType == 3 ? '课程订单' : balanceType == 4 ? '专家系统订单' : '其他订单'"/></div></td>
	            <td> <div style="word-break:break-all;text-align: center;"><s:property value="prodName"/></div></td>
	            <td> <div style="word-break:break-all;text-align: center;"><s:property value="fromName"/></div></td>
							<td> <div style="word-break:break-all;text-align: center;"><s:property value="income"/></div></td>
							<td> <div style="word-break:break-all;text-align: center;"><s:property value="expenditure"/></div></td>
	          </tr>
	          </s:iterator>
	        </table>
	      </div>
	      	  <s:include value="/share/pageAjax.jsp"/>
	      	    </div>
</s:form>
		</div>
	</div>
	<s:include value="/share/footer.jsp" />
</body>
</html>