<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="瘦身减肥课程,肌肉健美课程,增强体质" />
<meta name="description"
	content="健身教练的商务中心是健身教练制定的健身套餐,健身课程，如：瘦身减肥课程,肌肉健身课程,增强体质课程等。" />
<title>我收到的投诉</title>
<link href="css/club-order.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="css/pulicstyle.css" />
<script type="text/javascript">
	$.fx.speeds._default = 1000;
	$(function() {
			$( "#tousu1" ).dialog({
				autoOpen: false,
				show: "blind",
				hide: "explode",
				width: 802,
				height: 900,
				resizable: false
			});
	});
	function close(){
		$('#tousu1').dialog('close');
	}	
	$( ".opener" ).click(function() {
		var trObj = $(this).parent().parent();
		//$('#tousu1').find('span:eq(0)').html(trObj.children('td').eq(3).html());
		//$('#tousu1').find('span:eq(1)').html(trObj.children('td').eq(3).html());
		//$('#tousu1').find('span:eq(2)').html($(this).html());
		$('#spanNo').html(trObj.children('td').eq(2).html());
		$('#spanCompDate').html(trObj.children('td').eq(1).html());
		$('#spanStatus').html(trObj.children('td').eq(5).html());
		$('#spanMemberTo').html(trObj.children('td').eq(4).html());
		$('#spanMemberFrom').html(trObj.children('td').eq(3).html());
		$('#spanContent').html(trObj.children('input').eq(1).val());
		$('#spanAffix').attr("src","complaint/"+trObj.children('input').eq(3).val());
		$('#spanProcessResult').html(trObj.children('input').eq(2).val());
		$( "#tousu1" ).dialog( "open" );
		complaintId = trObj.children('input').eq(0).val();
		return false;
	});
	var complaintId = "";
	function changeStatus(status){
		loadMask();
		$.ajax({type:'post',url:'complaint!changeStatus.asp',data:"complaint.id="+complaintId+"&type="+$("input[name='type']").val()+"&complaint.status="+status,
			success:function(msg){
				removeMask();
				if(msg == "ok"){
					alert('当前状态已成功提交！');
					$( "#tousu1" ).dialog( "close" );
				}else{
					alert(msg);
				}
			}
		});
	}
	function seeComplaint(complaintId){
		loadMask();
		$.ajax({type:'post',url:'complaint!showComplaint.asp',data:"complaint.id="+complaintId,
			success:function(msg){
				removeMask();
				$("#right-2").html(msg);
			}
		});
	}
	function delComplaint(complaintId){
		if(window.confirm("确认关闭该投诉？")){
			loadMask();
			$.ajax({type:'post',url:'complaint!changeDelStatus.asp',data:"complaint.id="+complaintId+"&type="+$("input[name='type']").val()+"&query.delStatus="+$("input[name='query.delStatus']").val(),
				success:function(msg){
					removeMask();
					alert("当前投诉已成功关闭！");
					$("#right-2").html(msg);
				}
			});
		}
	}
	
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
		<s:form id ="queryForm" name="queryForm" method="post" action="complaint!query.asp" theme="simple">
	<s:hidden name="type"/>
	<s:hidden name="query.delStatus"/>
    <h1><s:if test="type==1">我收到的投诉</s:if><s:if test="type==2">我发起的投诉</s:if></h1>
    <div>
	  <div class="div1" style=" height:55px;">订单类型
         <s:select name="query.type" style="border:1PX solid #999999; height:20PX; margin-left:0px;" headerKey="" headerValue="" list="#{'1':'商品订单', '2': '活动订单', '3': '计划订单', '4': '场地订单', '5': '课程订单','6':'自动订单' }" listKey="key" listValue="value" />
         <input type="submit" name="button" value="查询" class="button" style="margin-left:20px;" onclick="onQuery();"/>
</div>
	  
      <div class="div2">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr class="end">
            <th id="tdtop" width="16%">编号</th>
            <th id="tdtop" width="18%">时间</th>
            <th id="tdtop" width="10%">订单类型</th>
            <th id="tdtop" width="15%">订单号</th>
            <th id="tdtop" width="10%">投诉方</th>
            <th id="tdtop" width="16%">被投诉方</th>
            <th id="tdtop" width="8%">投诉状态</th>
            <th id="tdtop" width="8%">操作</th>
          </tr>
          <s:iterator value="pageInfo.items" status="sta">
          <tr <s:if test="#sta.last">class="end"</s:if>>
          	<input name="complaintId" type="hidden" value="<s:property value="id"/>"/>
          	<input name="content" type="hidden" value="<s:property value="content"/>"/>
          	<input name="processResult" type="hidden" value="<s:property value="processResult"/>"/>
          	<input name="affix" type="hidden" value="<s:property value="affix"/>"/>
            <td class="left"> <div style="word-break:break-all;padding-left:10px;"><s:property value="complaintNo"/></div></td>
            <td> <div style="word-break:break-all;padding-left:10px;"><s:date name="compDate" format="yyyy-MM-dd HH:mm:ss"/></div></td>
            <td> <div style="word-break:break-all;padding-left:10px;"><s:property value="orderTypeName"/></div></td>
           
            <td>
            	<!-- 
            	<s:if test="type=='1'"><s:property value="order.no"/></s:if>
            	<s:elseif test="type=='3'"><s:property value="planOrder.no"/>
            	<s:elseif test="type=='4'"><s:property value="factOrder.no"/></s:elseif>
            	<s:elseif test="type=='5'"><s:property value="courseOrder.no"/></s:elseif>
            	</s:elseif><s:else><s:property value="ao.orderNo"/></s:else>
            	 -->
            	 <div style="word-break:break-all;padding-left:10px;"> <s:property value="orderNo"/></div>
            </td>
            <td> <div style="word-break:break-all;padding-left:10px;"><s:property value="memberFromName"/></div></td>
            <td> <div style="word-break:break-all;padding-left:10px;"><s:property value="memberToName"/></div></td>
            <td> <div style="word-break:break-all;padding-left:10px;">
            <s:if test="status==\"0\"">未处理</s:if>
          	<s:if test="status==\"1\"">处理中</s:if>
          	<s:if test="status==\"2\"">已处理</s:if>
          	</div>
            </td>
            <td> <div style="word-break:break-all;padding-left:10px;">
            <a id="colotoa" href="javascript:seeComplaint('<s:property value="complaintId"/>');">查看</a>
            <s:if test="type == 2">
            <a id="colotoa" href="javascript:delComplaint('<s:property value="complaintId"/>');">关闭</a>
            </s:if></div>
            </td>
          </tr>
          </s:iterator>
        </table>
      </div>
	  <s:include value="/share/pageAjax.jsp"/>
    </div>
</s:form>
		</div>
	</div>
</body>
</html>





