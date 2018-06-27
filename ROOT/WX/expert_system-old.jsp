<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>专家系统</title>
<link type="text/css" href="${pageContext.request.contextPath}/WX/css/bootstrap.css" rel="stylesheet"/>
<link type="text/css" href="${pageContext.request.contextPath}/WX/css/sm.css" rel="stylesheet"/>
<link type="text/css" href="${pageContext.request.contextPath}/WX/css/base.css" rel="stylesheet"/>
<link type="text/css" href="${pageContext.request.contextPath}/WX/css/style.css" rel="stylesheet"/>
<link type="text/css" href="${pageContext.request.contextPath}/WX/css/index.css" rel="stylesheet"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/zepto.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/style.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/index.js"></script>
</head>
<body>
<div class="container">
<s:form id="queryForm" name="queryForm" method="post" action="goods.asp" theme="simple">
<s:hidden name="goods.id" id="goodsId" />
<input type="hidden" name="startDate" value="<s:date name="#request.startDate" format="yyyy-MM-dd" />"/>
<s:hidden name="member.sex"  value="" id="sex"/>
<s:hidden name="setting.target" value="" id="target" />
<s:hidden name="setting.height" value="" id="height"/>
<s:hidden name="setting.weight"  value="" id="weight"/>
<s:hidden name="setting.waistline" value="" id="waistline"/>
<s:hidden name="setting.maxwm"  value="" id="maxwm"/>
<s:hidden name="setting.favoriateCardio"  value="" id="favoriateCardio"/>
    <div class="expert_title">
        <ul class="clearfix">
            <li class="col-xs-3"><a href="#">健身目的</a><span class="firstNum">1</span></li>
            <li class="col-xs-4"><a href="#">身体数据</a><span>2</span></li>
            <li class="col-xs-5"><a href="#">喜欢有氧运动</a><span>3</span></li>
        </ul>
    </div>
    
    <div class="expert_list">
        <div class="challengeMd">
            <label class="fl first_label  label_one">
            <input type="radio" name="target" value="1" checked class="check_one"/></label>
            <img src="images/xitong_icon_03.png" alt=""style="width: 4rem;height: 4.5rem" />
            <h3>减脂塑形</h3>
        </div>
        <div class="challengeMd">
            <label class="fl label_one">
            
            <input type="radio"  name="target" value="2" class="check_one"/></label>
            <img src="images/xitong_icon_07.png" alt="" style="width: 4.8rem;height: 4.5rem"/>
			<h3>健美增肌</h3>
        </div>
        <div class="challengeMd ">
            <label class="fl label_one">
            <input type="radio"  name="target" value="3" class="check_one"/></label>
            <img src="images/xitong_icon_11.png" alt="" style="width: 4rem;height: 3.5rem"/>
            <h3>增强力量</h3>
        </div>
    </div>

    <div class="expert_list" style="display: none">
         <div class="body-message" id="sex">
            <img src="images/body_icon_03.png" style="width: 2rem;height: 1.8rem;">
            <span>性别</span>
            <input type="text" value="男" readonly class="first">
        </div>
        <div class="body-message">
        	<img src="images/body_icon_06.png" alt="" style="width: 2rem;height: 2.2rem;"/>
        	<span>身高</span>
        	 <span class="fr unit"><input type="text"  id="shgao" value="<s:property value="#request.setting.height"/>" readonly class="CM picker"/>CM</span>
        </div>
        <div class="body-message ">
            <img src="images/body_icon_08.png" alt="" style="width: 2rem;height: 2.5rem;"/>
            <span>体重</span>
            <span class="fr unit"><input type="text" id="tzong"  name="setting.weight" value="<s:property value="#request.setting.weight"/>" readonly class="KG picker"/>KG</span>
            
        </div>
        <div class="body-message">
            <img src="images/body_icon_12.png" alt="" style="width: 2.1rem;height: 2.1rem;"/>
            <span>腰围</span>
            <input type="text"  value="" id ="yaowei" readonly class="CM picker"/>
            <span class="fr unit">CM</span>
        </div>
        <div class="body-message">
            <img src="images/body_icon_16.png" alt="" style="width: 2.1rem;height: 2.3rem;"/>
            <span>最大卧推重量</span>
			<span class="fr unit">KG</span>
			<input type="text"  value="" id="wotui" readonly class="KG picker"/>
        </div>
    </div>

    <div class="expert_list" style="display: none;">
         <s:iterator value="#request.actions">
	       <div class="like_sport">
            	<div class="pic fl">
            	<img src="../<s:property value="image"/>" alt="" />
            	</div>
            	<span>
            	<s:property value="name"/>
            	</span>
            	<label class="fr label_two">
            	<input type="checkbox" name="favoriateCardio" value="<s:property value="id"/>" class="check_two"/>
            	</label>
        	</div>
        </s:iterator>
    </div>
    <div class="assign" id ="go">
        <a href="JavaScript:go();" class="go" >继续</a>
    </div>
    <div class="assign" id ="go1">
        <a href="JavaScript:goodsCardcol();" class="go1" >完成</a>
    </div>
    </s:form>
</div>
<div class="share_bg" style="display: none">
    <div id="inner_bg">
        <div class="sex">
            <p>男</p>
            <p>女</p>
        </div>
            <p class="out">取消</p>
    </div>
</div>
<div class="bg" ></div>
<script type="text/javascript" src="js/sm.js"></script>
<script type="text/javascript" src="js/write.js"></script>
<script>
 if($(".go").text()== "继续"){
	 $("#go1").hide();
}
 function go(){
	 if($(".go").text()== "继续"){
			 $("#go1").hide();
	 }else if($(".go").text()== "完成"){
		 $("#go").hide();
		 $("#go1").show();
	 }
	 var target = $("input[name='target']:checked").val();
		$("#target").val(target);
		if($(".first").val()== "男"){
			$("#sex").val("M")
		}else if($(".first").val()== "女"){
			$("#sex").val("F")
		}
		 var height = $("#shgao").val();
		 $("#height").val(height);
		 var weight = $("#tzong").val();
		 $("#weight").val(weight);
		  var waistline = $("#yaowei").val();
		 $("#waistline").val(waistline);
		 var maxwm = $("#wotui").val();
		 $("#maxwm").val(maxwm);
 }
 
 function goodsCardcol(){
	 obj = document.getElementsByName("favoriateCardio");
	    check_val = [];
	    for(k in obj){
	        if(obj[k].checked)
	            check_val.push(obj[k].value);
	    }
	    alert
	   $("#favoriateCardio").val(check_val)
	   url = 'orderswx!submitProd.asp?type=2&prodType=6&id='
			+ $('#goodsId').val();
	$('#queryForm').attr("action", url);
	$('#queryForm').submit();
}
</script>

</body>
</html>