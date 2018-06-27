<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp"/>
<meta name="keywords" content="我的订单,订单管理" />
<meta name="description" content="我的订单" />
<title>健身E卡通_我的订单</title>
<link href="css/club-order.css" rel="stylesheet" type="text/css" />
<link href="css/pulicstyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
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
<s:include value="/share/window-header.jsp"/>
<div id="content" >
  <div id="left-1">
  <h1 class="order"style="background-image:none;background:#ff4401;color:white;font-weight:600">商务中心</h1>
  <div>
    <ul class="order">
      <li> <a href="#"><h1><b>商品管理</b></h1></a>
        <ul class="ordermanagement">
        	<li><a href="javascript:goAjax('');" id="coloa"></a></li>
          <s:if test="#session.toMember.role == \"E\"">
          <li><a href="javascript:goAjax('productwindow!query.asp?query.type=1&query.isClose=2');" id="coloa">健身卡</a></li>
          <!--  
          <li><a href="javascript:goAjax('productwindow!query.asp?query.type=2');" id="coloa">实物商品</a></li>
          -->
          </s:if>
          <s:if test="#session.toMember.role == \"S\"">
          <li><a href="javascript:goAjax('productwindow!query.asp?query.type=3&query.isClose=2');" id="coloa">健身卡</a></li>
          <li><a href="javascript:goAjax('plan!queryOther.asp');" id="coloa">健身计划</a></li>
          </s:if>
        </ul>
      </li>
      <li> <a href="#"><h1><b>健身挑战</b></h1></a>
        <ul class="ordermanagement">
        	<li><a href="javascript:goAjax('active.asp?external=1');" id="coloa">我发起的挑战</a></li>
        </ul>
      </li>
    </ul>
  </div>
  </div>
  <div id="right-2">
  	<s:if test="product.proType == 1"><s:include value="/shop/shop_edit1.jsp"/></s:if>
  	<s:elseif test="product.proType == 2"><s:include value="/shop/shop_edit2.jsp"/></s:elseif>
  	<s:elseif test="product.proType == 3"><s:include value="/shop/shop_edit3.jsp"/></s:elseif>
  	<s:elseif test="product.proType == 4"><s:include value="/shop/shop_edit4.jsp"/></s:elseif>
   	<s:elseif test="product.proType == 5"><s:include value="/shop/shop_edit5.jsp"/></s:elseif>
   	<s:elseif test="product.proType == 6"><s:include value="/shop/shop_edit6.jsp"/></s:elseif>
  	<s:else><s:include value="/homeWindow/product_list.jsp"/></s:else>
  </div>
</div>
<s:include value="/share/footer.jsp" />
</body>
</html>