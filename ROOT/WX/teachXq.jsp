<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta content="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
 <title>教练详情</title>
 <link type="text/css" href="css/bootstrap.css" rel="stylesheet"/>
 <link type="text/css" href="css/base.css" rel="stylesheet" />
 <link type="text/css" href="css/style.css" rel="stylesheet"/>
 <link type="text/css" href="css/index.css" rel="stylesheet"/>
 <script type="text/javascript" src="js/jquery-2.2.0.min.js"></script>
 <script type="text/javascript" src="js/index.js"></script>
 <script type="text/javascript">
 function GetQueryString(name){
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null)
			return decodeURI(r[2]);
		return null;
	}
 
 function summary(){
	 var id = GetQueryString("id");
	 var url = "listcoachwx!summary.asp?id="+id;
	 window.location.href=url;
 }
 </script>
</head>
<body>
<div class="teachXq container">
   <div class="teach_message">
       <div class="bg">
           <div class="pic fl"><img src="../picture/<s:property value="member.image"/>"/></div>
           <div class="txt fl">
               <h6>
               <span><s:if test="member.avgGrade==''">0</s:if><s:else><s:property value="member.avgGrade"/></s:else></span>分</h6>
               <p><span><s:property value="member.countEmp"/></span>人已经评价</p>
           </div>
           <div class="rated fr">
               <div class="price"><a href="#">评价</a></div>
               <div class="teach"><a href="#">聘请教练</a></div>
           </div>
       </div>
   </div>

    <div class="call">
        <div class="inner_call">
            <a href="JavaScript:summary()">教练简介</a>
            <a href="#">联系TA</a>
        </div>
        <h4>服务项目</h4>
        <ul>
        <s:if test="member.courses.size > 0">
        <s:iterator value="#request.courses">
            <li><img src="../picture/<s:property value="image"/>"/><span><s:property value="name"/></span></li>
        </s:iterator>
        </s:if>
        <s:else>
             <p align="center">该教练还没有发布私教服务</p>
        </s:else>
        </ul>
    </div>

    <div class="teach_title">
        <ul>
            <li><a href="#">私教服务</a></li>
            <li><a href="#">健身计划</a></li>
        </ul>
    </div>
    <div class="teach_list">
    <s:if test="member.products.size>0">
    <s:iterator value="member.products">
        <div class="list">
            <a href="#">
                <div class="pic fl"><img src="../picture/<s:property value="image"/>" alt=""></div>
                <div class="txt fl">
                    <h6><s:property value="name" /></h6>
                   <!--  <p>已有<i>66</i>人购买</p> -->
                    <span>￥<s:property value="cost" /></span>
                </div>
            </a>
        </div>
        </s:iterator>
        </s:if>
        <s:else>
        	<p align="center">该教练还没有发布私教课程</p>
        </s:else>
    </div>
    <div class="teach_list" style="display: none">
    <s:if test="#request.plans.size > 0">
    <s:iterator value="#request.plans">
        <div class="list">
            <a href="plandetail.asp?id=<s:property value="planId"/>">
                <div class="pic fl"><img src="../picture/<s:property value="image1"/>" alt=""></div>
                <div class="txt fl">
                    <h6><s:property value="planName"/></h6>
                    <p><s:property value="briefing"/></p>
                    <span>￥<s:property value="price" /></span>
                </div>
            </a>
        </div>
        </s:iterator>
        </s:if>
        <s:else>
        <p align="center">该教练还没有发布健身计划</p>
        </s:else>
    </div>
</div>
</body>
</html>