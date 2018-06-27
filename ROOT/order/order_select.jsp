<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link href="css/club-order.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	$(function() {
		$('#startDate').datepicker({
			changeYear : true
		});
		$('#endDate').datepicker({
			changeYear : true
		});
		$('#btnCancel').click(function() {
			$('#dialogOrder').dialog('close');
		});
		$('#btnOk').click(function() {
			var val = $('input:radio[name="orderId"]:checked').val();
			if (val) {
				var orderArr = val.split(",");
				$("#orderId").val(orderArr[0]);
				$("#orderNo").val(orderArr[1]);
				$("#memberToId").val(orderArr[3]);
				$("#memberToName").val(orderArr[4]);
				$("#orderType").val(orderArr[5]);
				$('#dialogOrder').dialog('close');
			} else {
				alert("请选择一个订单！");
				return;
			}
		});
	});
	function onQuery() {
		queryByPage(1, 'dialogOrder');
	}

	function queryByPage(page, div) {
		var divId = div || 'right-2';
		$('#currentPage').val(page);
		if (page) {
			loadMask();
			$.ajax({
				type : "post",
				url : $('#queryForm').attr("action"),
				data : $('#queryForm').serialize(),
				success : function(msg) {
					removeMask();
					$("#" + divId).html(msg);
				}
			});
		}
	}
</script>
<s:form id="queryForm" name="queryForm" method="post"
	action="order!showOrdersForComplaint.asp" theme="simple">
	<div id="right-2" style="float: left; margin-left: 10px;">
		<h1>订单列表</h1>
		<div>
			<div class="div1">
				订单类型:
				<s:select name="query.orderType" id="complaintType"
					style="border:1PX solid #999999; height:20PX;margin-left:3px;"
					headerKey="" headerValue=""
					list="#{'1':'商品订单', '2': '活动订单', '3': '计划订单', '4': '场地订单', '5': '课程订单','6':'自动订单'}"
					listKey="key" listValue="value" />
				交易起止日期 <input type="text" name="query.startDate" id="startDate"
					class="date-1"
					value="<s:date name="query.startDate" format="yyyy-MM-dd"/>" /> 到
				<input type="text" name="query.endDate" id="endDate" class="date-1"
					value="<s:date name="query.endDate" format="yyyy-MM-dd"/>" />
				<!--  
        <s:select name="query.status" list="#{'0':'未确认','1':'履约中','2':'已完成','3':'已终止'}" listKey="key" listValue="value" headerKey="" headerValue="请选择" cssClass="date-2"/>
        -->
				<input type="button" name="button" value="查询" class="button"
					onclick="onQuery();" /> <input type="button" name="button"
					id="btnCancel" class="inpdiv2" value="取消" /> <input type="button"
					name="button" id="btnOk" class="inpdiv1" value="确定" />
			</div>
			<div class="div2">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr class="end">
						<th width=5%; id="tdtop"></th>
						<th id="tdtop"   width="150">订单编号</th>
						<th id="tdtop">订单商品</th>
						<th id="tdtop"   width="80">订单类型</th>
						<th id="tdtop">收货人</th>
						<th id="tdtop">金额</th>
						<th id="tdtop" style="border-right: 1px solid #fff;"   width="180">下单时间</th>
					</tr>
					<s:iterator value="pageInfo.items" status="sta">
						<s:if test="#sta.last">
							<tr class="end">
						</s:if>
						<s:else>
							<tr>
						</s:else>
						<td class="left"><input style="margin-left: 10px;"
							type="radio" name="orderId"
							value="<s:property value="id"/>,<s:property value="no"/>,<s:property value="fromId"/>,<s:property value="toId"/>,<s:property value="toName"/>,<s:property value="type"/>" /></td>
						<td> <div style="word-break:break-all;padding-left:10px;"><s:property value="no" /></div></td>
						<td> <div style="word-break:break-all;padding-left:10px;"><s:property value="name" /></div></td>
						<td> <div style="word-break:break-all;padding-left:10px;"><s:property value="orderTypeName" /></div></td>
						<td> <div style="word-break:break-all;padding-left:10px;"><s:property value="toName" /></div></td>
						<td> <div style="word-break:break-all;padding-left:10px;"><s:property value="orderMoney" /></div></td>
						<td class="right"> <div style="word-break:break-all;padding-left:10px;"><s:date name="orderDate"
								format="yyyy-MM-dd HH:mm:ss" /></div></td>
						</tr>
					</s:iterator>
				</table>
			</div>
			<div class="plan_nav">
				<div class="plan_fenye">
					<span class="plan_yema"> <s:property
							value="pageInfo.currentPage" />
					</span>/<span> <s:property value="pageInfo.totalPage" /></span>
				</div>
				<s:if test="pageInfo.currentPage > 1">
					<a
						href="javascript:queryByPage('<s:property value="pageInfo.currentPage-1"/>','dialogOrder');">
						<img src="images/plan_pre.gif" />
					</a>
				</s:if>
				<s:if test="pageInfo.currentPage < pageInfo.totalPage">
					<a
						href="javascript:queryByPage('<s:property value="pageInfo.currentPage+1"/>','dialogOrder');">
						<img src="images/plan_next.gif" />
					</a>
				</s:if>
			</div>
			<s:hidden name="pageInfo.pageSize" id="pageSize" />
			<s:hidden name="pageInfo.currentPage" id="currentPage" />
			<s:hidden name="pageInfo.totalPage" id="totalPage" />
			<s:hidden name="pageInfo.totalCount" id="totalCount" />
			<s:hidden name="pageInfo.splitFlag" id="splitFlag" />
			<s:hidden name="pageInfo.order" id="order" />
			<s:hidden name="pageInfo.orderFlag" id="orderFlag" />
		</div>
	</div>
</s:form>
