<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function(){
    $('#orderStartTime').datepicker({changeYear: true});
});

function onCloseProduct(){
	$( "#dialogProduct" ).dialog( "close" );
}

function preShop(productId) {
	url = "clublist!shoGo.asp?productId="+productId;
	$('#queryForm').attr('action', url);
	$('#queryForm').submit();
}

$(function() {
	$( "#dialogProduct" ).dialog({
		width: 460,	
		autoOpen: false,
		show: "blind",
		hide: "explode",
		resizable: false
	});
});

</script>
<s:form id ="queryForm" name="queryForm" method="post" action="productwindow!query.asp" theme="simple">
<s:hidden name="member.id" id="memberId"/>
<s:hidden name="shop.id" id="shopId"/>
<s:hidden name="product.id" id="productId"/>
<s:hidden name="pId" id="pid"/>
    <h1>
    	<s:if test="query.type==1">健身套餐</s:if><s:if test="query.type==2">实物商品</s:if><s:if test="query.type==3">健身课程</s:if>
    </h1>
    <div>
      <div class="div2">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
		  <tr class="end">
			 <th>名称</th>
			 <th>金额</th>
			 <th>时间</th>
			 <th>操作</th>
		   </tr>
		   <s:iterator value="pageInfo.items" status="sta">
           <s:if test="#sta.last">
           <tr class="end">
           </s:if>
           <s:else>
           <tr>
           </s:else>
           <td class="left"> <div style="word-break:break-all;padding-left:10px;">
          	<a href="javascript:preShop('<s:property value="id"/>','<s:property value="member.id"/>');" id="colotoa">
          		<s:if test="proType==1">[圈存(时效)]</s:if>
          		<s:if test="proType==2">[圈存(计次)]</s:if>
          		<s:if test="proType==3">[圈存(储值)]</s:if>
          		<s:if test="proType==4">[对赌(次数)]</s:if>
          		<s:if test="proType==5">[对赌(频率)]</s:if>
          		<s:if test="proType==6">[预付卡]</s:if>
          		<s:property value="name"/>
          	</a></div>
          	</td>
          <td> <div style="word-break:break-all;padding-left:10px;"><s:if test="proType==4 || proType == 5">保证金:<s:property value="promiseCost"/></s:if><s:else>金额:<s:property value="cost"/></s:else>元</div></td>
		  <td> <div style="word-break:break-all;padding-left:10px;"><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/></div></td>
		  <td> <div style="word-break:break-all;padding-left:10px;">
          <s:if test="#session.toMember.role == \"E\"">
          <a id="colotoa" href="javascript:preShop('<s:property value="id"/>','<s:property value="member.id"/>');">购买</a>
          </s:if>
          <s:if test="#session.toMember.role == \"S\"">
          <a id="colotoa" href="javascript:preShop('<s:property value="id"/>','<s:property value="member.id"/>');">购买</a>
          </s:if></div>
		  </td>
		  </tr>
        </s:iterator>
		</table>
      </div>
      <s:include value="/share/pageAjax.jsp"/>
    </div>
</s:form>
    <div id="dialogProduct" title="购买课程收费">
		<s:form name="productForm" id="productForm" action="shop!save.asp" method="post" theme="simple">
		<input type="hidden" name="product.id"/>
		<input type="hidden" name="wellNum" id="wellNum"/>
		<s:hidden name="shop.orderEndTime" id="orderEndTime"/>
		<s:hidden name="shop.reportDay" id="shopReportDay"/>
		<div  style="line-height:32px;padding-top:5px;">
		服务商：<span id="productMember" style="color:#515151;"></span>
		</div>
		<div  style="line-height:32px;">
			名称：<span id="productName" style="color:#515151;"></span>
		</div>
		<div style="line-height:32px;">
		类型：<span id="productCourseType"></span>
		</div>
		<div style="line-height:32px;">
			数量：<span id="productNum" style="color:#515151;"></span>
		</div>
		<div style="line-height:32px;">
		金额：<span id="productCost" style="color:red;"></span>
		</div>
		<div style="line-height:32px;">
		描述：<span id="productRemark" style="color:#515151;"></span>
		</div>
		<div style="line-height:32px;">
		开始日期：<input style="border:1px solid #cccccc;line-height:23px; height:23px;"  type="text" name="shop.orderStartTime" id="orderStartTime" class="text1" value="<s:date name="shop.orderStartTime" format="yyyy-MM-dd"/>" onchange="changeOrderEndTime();"/>
		</div>
		<div style="line-height:32px;">
			结束日期：<span id="endTime"><s:date name="shop.orderEndTime" format="yyyy-MM-dd"/></span>
		</div>
		<div style="line-height:32px;">
			每月： <span id="shopReportDaySpan"><s:property value="shop.reportDay"/></span>号结算
		</div>
		<div>
		<div class="pa">
			<input type="button" name="button" class="butok" value="购买" onclick="preShop('<s:property value="product.id"/>');"/>
			<input type="button" name="button" class="butok" value="取消" onclick="onCloseProduct();"/>
		</div>
		</div>
		</s:form>
	</div>
