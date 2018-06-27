<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="瘦身减肥课程,肌肉健美课程,增强体质" />
<meta name="description"
	content="健身教练的商务中心是健身教练制定的健身套餐,健身课程，如：瘦身减肥课程,肌肉健身课程,增强体质课程等。" />
<title>我发起的挑战</title>
<link href="css/club-order.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="css/pulicstyle.css" />
<link rel="stylesheet" type="text/css" href="css/activ_new.css">
<style type="text/css">
.pagenumber1 ul{ padding-right:7px;}
</style>
<script type="text/javascript">
<!--
$(function(){
	/* $('.status').click(function(){
		loadMask();
		var $the = $(this);
		var key = $the.parents('tr').children('td:eq(0)').children("input:eq(0)").val();
		var status = $the.attr('status') === 'B' ? 'A' : 'B';
		$.ajax({url: 'active!changeStatus.asp', type: 'post', data: 'id=' + key + '&status=' + status, 
			success: function(msg){
				$('#right-2').html(msg);
				removeMask();
			}
		});
	}); */
	$('.status').click(function(){
		loadMask();
		var $the = $(this);
		var key = $the.parents('tr').children('td:eq(0)').children("input:eq(0)").val();
		var status = $the.attr('status') === 'B' ? 'A' : 'B';
		 window.location.href = "active!changeStatus.asp?id="+key+"&status="+ status;
	
	});
	$('.edit').click(function(){
		loadMask();
		var key = $(this).parents('tr').children('td:eq(0)').children("input:eq(0)").val();
		$.ajax({url: 'active!edit.asp', type: 'post', data: 'id=' + key, 
			success: function(msg){
				$("#right-2").html(msg);
				removeMask();
			}
		});
	});
	$('.partake').click(function(){
		loadMask();
		var key = $(this).parents('tr').children('td:eq(0)').children("input:eq(0)").val();
		$.ajax({url: 'joinactive!join.asp', type: 'post', data: 'id=' + key, 
			success: function(msg){
				$("#right-2").html(msg);
				removeMask();
			}
		});
	});
	$('.lookDetail').click(function(){
		loadMask();
		var key = $(this).parents('tr').children('td:eq(0)').children("input:eq(0)").val();
		$.ajax({url: 'active!look.asp', type: 'post', data: 'id=' + key, 
			success: function(msg){
				$("#right-2").html(msg);
				removeMask();
			}
		});
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
			<s:form name="queryForm" id="queryForm" method="post" action="active.asp" theme="simple">
<div id="container">
	<h1>我发起的挑战</h1>
	<s:if test="external == '0'"><div class="fa_btn_p"><a href="active!edit.asp"><img src="images/fa_btn.jpg" /></a></div></s:if>
      <!--发起记录list-->
      <div class="t_lists" style="padding-left:0px; padding-top:0px;">
        <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="t_lists_table">
          <tr class="head_table"  style="text-align:center;">
            <td width="120" ><strong>名称</strong></td>
            <td width="50" ><strong>完成时间</strong></td>
            <td width="90" ><strong>类型</strong></td>
            <%-- <td width="76" align="center"><strong>模式</strong></td> --%>
            <td width="70" ><strong>操作</strong></td>
          </tr>
          <s:iterator value="pageInfo.items">
          <tr class="top_right">
          	<td style="height:25px;text-align: center;" ><s:property value="name"/><s:hidden name="id" theme="simple"/></td>
           	<td style="text-align: center;"><s:property value="days"/>天</td>    <%--去掉此处的align="center" --%>
          	<td style="text-align: center;"><s:if test="target == 'A'">体重减少</s:if><s:elseif test="target=='B'">体重增加</s:elseif><s:elseif test="target=='C'">健身次数</s:elseif>
          	<s:elseif test="target=='D'">健身次数</s:elseif><s:elseif test="target=='E'">自定义目标</s:elseif></td>
          	<%-- <td align="center"><s:if test="mode == 'A'">个人挑战</s:if><s:else>团体挑战</s:else></td> --%>
          	<td style="border-right:0px;text-align: center;" class="pj" >
          		<s:if test="external == '0'">
          		 <a href="#" class="status" status="<s:property value="status"/>"><s:if test="status=='B'">关闭</s:if><s:else>开启</s:else></a> 
          		<s:if test="status=='A'"><a href="active!edit.asp?id=<s:property value="id"/>" class="edit">编辑</a></s:if>
          		</s:if>  
          		<s:if test="status == 'B'"><!-- <a href="#" class="partake">报名</a> --> 
          		 <a href="activewindow.asp?id=<s:property value="id"/>" >报名</a>  </s:if> 
          		<a href="active!look.asp?id=<s:property value="id"/>" class="lookDetail">查看详情</a>
          	</td>
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





