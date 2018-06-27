<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="瘦身减肥课程,肌肉健美课程,增强体质" />
<meta name="description"
	content="健身教练的商务中心是健身教练制定的健身套餐,健身课程，如：瘦身减肥课程,肌肉健身课程,增强体质课程等。" />
<title>提现记录</title>
<link href="css/club-order.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="css/pulicstyle.css" />
<script type="text/javascript" src="script/DateTimeMask.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('#startDate').datepicker({changeYear: true});
	$('#endDate').datepicker({changeYear: true});
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
			<s:form id ="queryForm" name="queryForm" method="post" action="pickdetail!queryList.asp" theme="simple">
	    <h1>提现记录</h1>
	    <div>
	      <div class="div1"> 提现起止日期
	        <input type="text" name="query.startDate" id="startDate" class="date-1" value="<s:date name="query.startDate" format="yyyy-MM-dd"/>" />
	                    到
	        <input type="text" name="query.endDate" id="endDate" class="date-1" value="<s:date name="query.endDate" format="yyyy-MM-dd"/>" />
	        <input type="submit" name="button" value="查询" class="button" style="margin-left:20px;" onclick="onQuery();"/>
	      </div>
	      <div class="div2">
	        <table width="100%" border="0" cellpadding="0" cellspacing="0" >
	          <tr class="end">
	            <th width="20%" id="tdtop">时间</th>
	            <th width="20%" id="tdtop">流水号</th>
	            <!-- <th id="tdtop">类型</th> -->
			<!-- 	<th id="tdtop">收/支</th> -->
				<th id="tdtop">金额</th>
				<th id="tdtop">提现账户</th>
	            <th id="tdtop">状态</th>
	            <th id="tdtop">备注</th>
	          </tr>
	          <s:iterator value="pageInfo.items" status="sta">
	          <s:if test="#sta.last">
	          <tr class="end">
	          </s:if>
	          <s:else>
	          <tr>
	          </s:else>
	            <td class="left"><div style="word-break:break-all;padding-left:10px;"><s:date name="pickDate" format="yyyy-MM-dd HH:mm:ss"/></div></td>
	            <td> <div style="word-break:break-all;padding-left:10px;"><s:property value="no"/></div></td>
	           <%--  <td> <div style="word-break:break-all;padding-left:10px;"><s:if test="type == 1">提现</s:if></div></td> --%>
				<%-- <td> <div style="word-break:break-all;padding-left:10px;">
						<s:if test="flowType == 1">支出</s:if>
						<s:if test="flowType == 2">收入</s:if>
						</div>
				</td> --%>
	            <td> <div style="word-break:break-all;padding-left:10px;"><s:property value="pickMoney"/></div></td>
	            <td> <div style="word-break:break-all;padding-left:10px;"><s:property value="pickAccount.name"/></div></td>
				<td> <div style="word-break:break-all;padding-left:10px;">
					<s:if test="status == 1">请求处理中</s:if>
					<s:if test="status == 2">成功</s:if>
					<s:if test="status == 3">失败</s:if>
					</div>
				</td>
				<td><div style="word-break:break-all;padding-left:10px;"><s:property value="remark"/></div></td>
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