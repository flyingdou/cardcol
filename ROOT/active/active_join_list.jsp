<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="瘦身减肥课程,肌肉健美课程,增强体质" />
<meta name="description"
	content="健身教练的商务中心是健身教练制定的健身套餐,健身课程，如：瘦身减肥课程,肌肉健身课程,增强体质课程等。" />
<title>我参加的挑战</title>
<link href="css/club-order.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="css/pulicstyle.css" />
<link rel="stylesheet" type="text/css" href="css/activ_new.css">
<style type="text/css">
.pagenumber1 ul{ padding-right:7px;}
</style>
<script type="text/javascript">
$(function(){
	$('.look').click(function(){
		loadMask();
		var _key = $(this).parents('tr').children('td:eq(0)').children('input:eq(0)').val(),
			_mode = $(this).parents('tr').children('td:eq(0)').children('input:eq(1)').val();
		$.ajax({url: 'joinactive!look.asp', type: 'post', data: 'id=' + _key + '&mode=' + _mode, 
			success: function(msg) {
				$("#right-2").html(msg);
				removeMask();
			}
		});
	});
	$('.delete').click(function(){
		loadMask();
		var _key = $(this).parents('tr').children('td:eq(0)').children('input:eq(0)').val();
		$.ajax({url: 'joinactive!onCancel.asp', type: 'post', data: 'id=' + _key, 
			success: function(msg) {
				$("#right-2").html(msg);
				removeMask();
			}
		});
	});
});
function goAjax(url){
	$.ajax({type:"post",url:url,data:"",
		success:function(msg){
			$("#right-2").html(msg);
		}
	});
}
</script>
</head>
<body>
	<s:include value="/share/header.jsp" />
	<div id="content">
	<s:include value="/order/nav.jsp" />
		<div id="right-2">
			<s:form name="queryForm" id="queryForm" action="joinactive.asp" method="post" theme="simple">
<div id="container">
	<h1>我参加的挑战</h1>
      <!--发起记录list-->
      <div class="t_lists" style="padding-left:0px; padding-top:0px;">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr  class="head_table"   style="text-align:center;">
            <td width="130" height="28"><strong>编号</strong></td>
            <td width="100"><strong>名称</strong></td>
            <td width="90"><strong>开始时间</strong></td>
            <td width="90" ><strong>结束时间</strong></td>
            <td width="70"><strong>类型</strong></td>
            <td width="70"><strong>状态</strong></td>
            <%-- <td width="60"><strong>结果</strong></td> --%>
            <td><strong>操作</strong></td>
          </tr>
          <s:iterator value="pageInfo.items" status="st">
          <tr class="top_right">
          	<td style=" height:25px;text-align: center;"><s:hidden name="id"/><s:hidden name="active.mode"/><s:property value="no"/></td>
          	<td style="text-align: center;"><s:property value="active.name"/></td>
          	<td style="text-align: center;"><s:date name="orderStartTime" format="yyyy-MM-dd"/></td>
          	<td style="text-align: center;"><s:date name="orderEndTime" format="yyyy-MM-dd"/></td>
          	<td style="text-align: center;">
          		<!--  挑战目标:A体重减少,B体重增加,D运动次数 -->
          		<s:if test="active.target == 'A'">体重减少</s:if>
          		<s:if test="active.target == 'B'">体重增加</s:if>
          		<s:if test="active.target == 'C' || active.target == 'D'">运动次数</s:if>
          	</td>
          	<td style="text-align: center;">
          		<!--  活动状态：0为进行中，1为成功，2为失败，3为已结束 -->
          		<s:if test="status == '0'">进行中</s:if>
          		<s:elseif test="status == '1'">成功</s:elseif>
          		<s:elseif test="status=='2'">失败</s:elseif>
          		<s:elseif test="status=='3'">已结束</s:elseif>
          	</td>
          	<%-- <td><s:if test="isWin == '0'">挑战中</s:if><s:elseif test="isWin=='1'">胜</s:elseif><s:elseif test="isWin=='2'">负</s:elseif></td> --%>
          	<td style="text-align: center;"><s:if test="status=='0'"><a class="delete">取消</a></s:if>&nbsp;<a class="ts" href="javascript:goAjax('complaint!edit.asp?complaint.type=2&complaint.objId=<s:property value="id"/>');">投诉</a>&nbsp;<a class="look">查看详情</a></td>
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



