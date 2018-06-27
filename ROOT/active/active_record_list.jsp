<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="瘦身减肥课程,肌肉健美课程,增强体质" />
<meta name="description"
	content="健身教练的商务中心是健身教练制定的健身套餐,健身课程，如：瘦身减肥课程,肌肉健身课程,增强体质课程等。" />
<title>待审批的记录</title>
<link href="css/club-order.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="css/pulicstyle.css" />
<link rel="stylesheet" type="text/css" href="css/activ_new.css">
<style type="text/css">
.pagenumber1 ul{ padding-right:7px;}
</style>
<script type="text/javascript">
<!--
$(function(){
	$('.ts').click(function(){
		if (confirm('是否确认同意当前的活动记录数据？')) {
			loadMask();
			var _key = $(this).parents('tr').children('td:eq(0)').children('input:eq(0)').val();
			$.ajax({url: 'record!audit.asp', type: 'post', data: 'id=' + _key + '&audit=' + 1, 
				success: function(msg) {
					$("#right-2").html(msg);
					removeMask();
				}
			});
		}
	});
	$('.look').click(function(){
		if (confirm('是否确认不同意当前的活动记录数据？')) {
			loadMask();
			var _key = $(this).parents('tr').children('td:eq(0)').children('input:eq(0)').val();
			$.ajax({url: 'record!audit.asp', type: 'post', data: 'id=' + _key + '&audit=' + 2, 
				success: function(msg) {
					$("#right-2").html(msg);
					removeMask();
				}
			});
		}
	});
});
//-->
</script>

</head>
<body>
	<s:include value="/share/header.jsp" />
	<div id="content">
	<s:include value="/order/nav.jsp" />
		<div id="right-2">
			<s:form name="queryForm" id="queryForm" method="post" action="record.asp" theme="simple">
<div id="container">
	<h1>待审批的挑战结果数据</h1>
      <!--发起记录list-->
      <div class="t_lists" style="padding-left:0px; padding-top:0px;">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr  class="head_table"   style="text-align:center;">
            <td width="100" height="28"><strong>会员</strong></td>
            <td><strong>活动名称</strong></td>
            <td width="80"><strong>运动日期</strong></td>
            <td width="70" ><strong>运动时长</strong></td>
            <td width="70"><strong>有氧运动</strong></td>
            <td width="40"><strong>运动量</strong></td>
            <td width="60"><strong>力量运动</strong></td>
            <td  width="50"><strong>体重</strong></td>
            <td  width="50"><strong>状态</strong></td>
            <td  width="85"><strong>操作</strong></td>
          </tr>
          <s:iterator value="pageInfo.items" status="st" var="tr">
          <tr class="top_right">
          	<td style=" height:25px;"><s:hidden name="#tr.id"/><s:property value="partake.name"/></td>
          	<td><s:property value="activeOrder.active.name"/></td>
          	<td><s:date name="doneDate" format="yyyy-MM-dd"/></td>
			<td><s:property value="times" />分钟</td>
			<td><s:property value="#tr.action" /></td>
          	<td><s:property value="actionQuan" /></td>
          	<td><s:property value="strength" /></td>
          	<td><s:property value="weight" /></td>
          	<td><s:if test="confrim =='0'">待确认</s:if><s:elseif test="confrim=='1'">通过</s:elseif><s:else>不通过</s:else></td>
          	<td><s:if test="confrim =='0'"><a class="ts" href="#">通过</a> <a class="look">不通过</a></s:if></td>
          </tr>
          </s:iterator>
        </table>
      </div>
	  <div class="page_rigddd"><s:include value="/share/pageAjax.jsp"/></div>
</div>
</s:form>
		</div>
	</div>
	<s:include value="/share/footer.jsp" />
</body>
</html>






