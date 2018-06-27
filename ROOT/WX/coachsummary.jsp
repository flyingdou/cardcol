<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta content="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
    <title>教练简介</title>
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/bootstrap.css" rel="stylesheet"/>
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/swiper.min.css" rel="stylesheet"/>
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/base.css" rel="stylesheet"/>
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/style.css" rel="stylesheet"/>
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/index.css" rel="stylesheet"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/jquery-2.2.0.min.js" ></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/swiper-3.4.0.min.js" ></script>
    <script>
        $(function () {
            var swiper = new Swiper('.swiper-container', {
                slidesPerView: 3,
                paginationClickable: true,
                spaceBetween: 0
            });
        })
    </script>
</head>
<body>
<div class="teach_J container">
    <div class="teach_pic">
        <div class="pic"><img src="images/<s:property value="#request.m.image"/>" ></div>
        <p><s:property value="#request.m.name"/> </p>
        <p><s:property value="#request.m.city"/></p>
        <p><span>5000</span>关注&nbsp;&nbsp;&nbsp;<span>一万</span>粉丝</p>
    </div>

    <div class="diploma ">
        <h4>资质证书</h4>
        <div class="swiper-container">
            <ul class="swiper-wrapper">
            <s:if test="#request.certs.size > 0">
               <s:iterator value="#request.certs">
                <li class="swiper-slide"><a href="#"><img src="picture/<s:property value="fileName"/>"/> </a><span><s:property value="name"/></span> </li>
			   </s:iterator>
			   </s:if>
			   <s:else>
			   <p style="margin-left: 25%">该教练还没有上传资格证书！</p>
			   </s:else>
            </ul>
        </div>
    </div>
    <div class="teach_txt">
        <h4>简介</h4>
        <s:if test="member.description != ''">
      <s:property value="member.description" escape="false"/>
      </s:if>
      <else>
      该教练还未完善资料！
      </else>
    </div>
</div>
</body>
</html>