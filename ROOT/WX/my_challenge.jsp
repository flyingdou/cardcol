<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>我的挑战</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport"/>
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/bootstrap.css" rel="stylesheet"/>
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/base.css" rel="stylesheet"/>
   	<link type="text/css" href="${pageContext.request.contextPath}/WX/css/style.css" rel="stylesheet"/>
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/index.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/jquery-2.2.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/style.js"></script>
    <script type="text/javascript">
    function mychallenge(){
    	 $.ajax({
    		type : "post",
    		url : "activelistwx!query.asp",
    		success:function(msg){
    			$("#mychallenge").html($(msg).find("#mychallenge").html());
    	}
    	}); 
    }
    </script>
</head>
<body>
<div class="my_challenge container">
    <div class="table_title">
        <ul class="clearfix">
            <li class="col-xs-6"><a href="activejoinwx.asp">我的挑战</a></li>
            <li class="col-xs-6"><a href="JavaScript:mychallenge();">我的发起</a></li>
        </ul>
    </div>
    <div class="table_list">
    <s:iterator value="pageInfo.items">
        <div class="list">
            <a href="activelistwx!detail1.asp?id=<s:property  value="active.id"/>">
            <div class="pic fl"><img src="../picture/<s:property value="active.image"/>"/></div>
                <div class="txt fl">
                    <h6><s:property  value="active.name"/></h6>
                    <p>已有<span>0</span>人参加</p>
                    <p> <s:if test="active.category=='A'">
										<s:property value=" active.days" />天内体重增加<s:property value="active.value" />KG</s:if>
									<s:elseif test="active.category=='B'">
										<s:property value="active.days" />天内体重减少<s:property value="active.value" />KG</s:elseif>
									<s:elseif test="active.category=='D'">
										<s:property value="active.days" />天内运动<s:property value="active.value" />次</s:elseif>
									<s:elseif test="active.category=='E'">
										<s:property value="active.days" />天内运动<s:property value="active.value" />小时</s:elseif>
									<s:elseif test="active.category=='F'">
										<s:property value="active.days" />天内每周运动<s:property value="active.value" />次</s:elseif>
									<s:elseif test="active.category=='G'">
										<s:property value="active.days" />天内
										  <s:if test="active.action != ''">
											<s:property value="active.action"/>
										  </s:if>
										<s:else>
											<s:property value="active.content"/>
										</s:else>
										<s:property value="active.value" />千米</s:elseif></p>
                </div>
                <p class="fr challenge_time"><s:date name="orderStartTime" format="MM月dd日"/></p>
            </a>
        </div>
       </s:iterator>
    </div>
    <div class="table_list" style="display: none" id="mychallenge">
    <s:iterator value="pageInfo.items">
        <div class="list">
            <a href="activelistwx!detail2.asp?id=<s:property  value="id"/>">
                <div class="pic fl"><img src="../picture/<s:property value="image"/>" alt=""></div>
                <div class="txt fl">
                    <h6><s:property  value="name"/></h6>
                    <p>已有<span>0</span>人参加</p>
                    <p> <s:if test="category=='A'">
										<s:property value=" days" />天内体重增加<s:property value="value" />KG</s:if>
									<s:elseif test="category=='B'">
										<s:property value="days" />天内体重减少<s:property value="value" />KG</s:elseif>
									<s:elseif test="category=='D'">
										<s:property value="days" />天内运动<s:property value="value" />次</s:elseif>
									<s:elseif test="category=='E'">
										<s:property value="days" />天内运动<s:property value="value" />小时</s:elseif>
									<s:elseif test="category=='F'">
										<s:property value="days" />天内每周运动<s:property value="value" />次</s:elseif>
									<s:elseif test="category=='G'">
										<s:property value="days" />天内
										 <s:if test="action != ''">
											 <s:property value="action"/>
										</s:if>
										<s:else>
											<s:property value="content"/>
										</s:else> 
										<s:property value="value" />千米</s:elseif></p>
                </div>
                <p class="fr challenge_time"><s:date name="createTime" format="MM月dd日"/></p>
            </a>
        </div>
       </s:iterator>
    </div>
</div>
    <script type="text/javascript" src="js/imgs.js"></script>
</body>
</html>