<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="健身课程,课程安排,瘦身课程,健美课程" />
<meta name="description" content="健身教练的课程表中有健身教练为健身会员制定的健身课程，根据健身目安排课程。" />
<title>签到管理</title>
<link href="css/club-checkin.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="css/pulicstyle.css"/>
<script src="js/vue.min.js"></script>
<style>
	.head{
		cursor:pointer;
	}
	
	.popup{
		position:fixed;
		top:0;
		left:0;
		z-index:1;
		width:100%;
		height:100%;
		background-color: rgba(0,0,0,0.3);
	}
	
	#dow{
		max-height: 300px;
	  overflow-y: scroll;
	}
	
	.signInfoBox{
		width:100%;
		margin:0 auto;
		text-align: center;
	}
	
	.signInfoBox-title{
		overflow: hidden;
		background-color:#f0f0f0;
	}
	
	.signInfoBox-title > div{
		width:50%;
		height:20px;
		line-height:20px;
		float:left;
	}
	
	.signInfoBox > ul, .signInfoBox li{
	  list-style: none;
	  position: relative;
	}
  .signInfoBox li{
		overflow: hidden;
		border-top:1px solid #bbb;
		background-color: #fff;
	} 
  .signInfoBox li > div{
		width:50%;
		height:20px;
		line-height:20px;
		float:left;
	} 
</style>
<script type="text/javascript">
$(document).ready(function(){
	$('#startDate').datepicker({changeYear: true});
	$('#endDate').datepicker({changeYear: true});
});
function onQuery(){
	queryByPage(1);
}
</script>
</head>
<body class="bodyScroll">
	<s:include value="/share/header.jsp"/>
	<div id="content">
	<s:include value="/course/nav.jsp" />
	<div id="right-2">
	<s:form id ="queryForm" name="queryForm" method="post" action="signin!query.asp" theme="simple">
    <h1>签到管理</h1>
    <div>
      <div class="date">用户
        <s:textfield name="query.name"/>
        签到起止日期
        <input type="text" name="query.startDate" id="startDate" class="date-1" value="<s:date name="query.startDate" format="yyyy-MM-dd"/>" />
      	 到
       	<input type="text" name="query.endDate" id="endDate" class="date-1" value="<s:date name="query.endDate" format="yyyy-MM-dd"/>" />
        <input type="button" name="button" value="查询" class="button1" onclick="onQuery();"/>
      </div>
      <div class="table">
        <table width="100%" border="0" cellpadding="0" cellspacing="0" >
          <tr class="end">
            <th id="tdtop">
            	<s:if test="type == 0">签到俱乐部</s:if>
            	<s:else>会员</s:else>
            </th>
            <th id="tdtop">签到时间</th>
            <th id="tdtop" >订单类型</th>
            <th id="tdtop" >商品名称</th>
            <th id="tdtop" class="right">签到订单</th>
          </tr>
          <s:iterator value="pageInfo.items" status="sta" var="i">
          <s:if test="#sta.last">
          <tr class="end">
          </s:if>
          <s:else>
          <tr>
          </s:else>
            <td class="left">
            	<s:if test="type == 0">
            		<s:property value="memberAuditName"/>
            	</s:if>
            	<s:else>
            		<s:property value="memberSignName"/>
            	</s:else>
            </td>
            <td><s:date name="signDate" format="yyyy-MM-dd"/></td>
            <td class="right"><s:property value="signType == 5 ? '课程订单': signType == 0 ? '线下会员订单' : 'E卡通订单' "/></td>
            <td class="right"><s:property value="productName"/></td>
            <td class="right orderNo head" onclick="alertBox(this)">
            	<span><s:property value="orderNo"/></span>
            	<input type="hidden" class="memberSign" value="<s:property value="memberSignId"/>" />
            	<input type="hidden" class="orderNo" value="<s:property value="orderNo"/>" />
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
	<s:include value="/share/footer.jsp" />
	 <div id="dow" title="打卡详情">
			<div class="signInfoBox">
				<div class="signInfoBox-title"></div>
				<ul class="signList"></ul>		
			</div>
	 </div>
<script>
	function alertBox(elem){
		// 獲取訂單號去後台查詢簽到數據
		var orderNo = $(elem).find(".orderNo").val();
		var memberSign = $(elem).find(".memberSign").val();
		if(orderNo == ''){
			alert("没有订单编号,无法查询签到数据!");
			return;
		}
		
		$.ajax({
			url:"signin!listSignInAndClubName.asp",
			type:"post",
			data:{
				orderNo:orderNo,
				memberSign:memberSign
			},
			dataType:"json",
			success:function(res){
				// 没有签到数据
				if(res.length <= 0){
					alert("没有签到数据");
					return;
				}
				// 渲染弹窗页面
				var title = "<div>签到日期	</div><div> 签到俱乐部</div>";
				$(".signInfoBox-title").html(title);
				$(".signInfoBox").css("border","1px solid #89c04d");
				// 綁定數據源
				var divs = "";
			  res.forEach(function(item){
				  var div = '<li><div style="border-right:1px solid #bbb;width:49.6%;">'+item.signDate+'</div>';
				  div += '<div>'+item.name+'</div></li>';
				  divs += div;
			  });
			  $(".signList").html(divs);
				
				// 顯示彈窗
				$("#dow").dialog({
		      autoOpen: false,
		      title:"打卡详情("+orderNo+")"
		    });
				$('#dow').dialog('open');
			},
			error:function(e){
				alert(JSON.stringify(e));
			}
		});
	}
</script>
</body>
</html>
