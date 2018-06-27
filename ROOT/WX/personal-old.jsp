<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>个人信息</title>
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/bootstrap.css" rel="stylesheet">
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/base.css" rel="stylesheet">
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/style.css" rel="stylesheet">
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/index.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/jquery-2.2.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/style.js"></script>
</head>
<body>
<div class="personal container">
    <div class="personal_top">
        <div class="left fl">
            <label>
                <div class="pic"><img src="../picture/<s:property value="member.image"/>"></div>
                <input type="file" multiple="multiple" accept="image/*" class="file" id="file" style="display: none">
            </label>
            <span><s:property value="member.nick"/></span>
        </div>
        <div class="txt fl">
        <s:if test="pageInfo.items.size !=''">
         <s:iterator value="pageInfo.items">
            <p>健身次数：<span><s:property value="timeNum"/></span>次</p>
            <p>健身时间：<span><s:property value="time" /></span>小时</p>
            <p>课程完成率：<span><s:property value="finishrate" /></span>%</p>
         </s:iterator>
         </s:if>
         <s:else>
            <p>健身次数：<span>0</span>次</p>
            <p>健身时间：<span>0</span>小时</p>
            <p>课程完成率：<span>0</span>%</p>
         </s:else>
        </div>
    </div>

    <div class="personal_list">
        <a href="personalName.jsp">
            <span>昵称</span>
            <p><s:property value="member.name"/></p>
        </a>
        <a href="personalwx!pwd.asp">
            <span>密码</span>
            <p>></p>
        </a>
         <s:if test="member.mobileValid == 1">
         	<a>
            <span>手机号</span><p><s:property value="member.mobilephone"/></p>
        	</a>
         </s:if>
         <s:else>
         	<a href = "mobilewx.asp">
           		 <span>手机号</span><p>未绑定手机</p>
        	</a> 
         </s:else>
    </div>
    <div class="personal_list">
        <a href="personaSex.jsp">
            <span>性别</span>
           <p><s:if test="member.sex == \"M\"">男</s:if><s:elseif test="member.sex == \"F\"">女</s:elseif> </p>
        </a>
        <a href="personalAge.jsp">
            <span>出生日期</span>
            <p><s:date name="member.birthday" format="yyyy-MM-dd"/> </p>
        </a>
        <a href="personalAddress.jsp">
            <span>地区</span>
            <p><s:property value="member.province"/> <s:property value="member.city"/> <s:property value="member.county"/></p>
        </a>
    </div>

    <div class="personal_list last">
        <a href="personalheight.jsp">
            <span>身高</span>
            <p><s:property value="member.setting.height"/> CM</p>
        </a>
        <a href="personalRate.jsp">
            <span>静心率</span>
            <p><s:property value="member.setting.heart"/>次/分钟</p>
        </a>
        <a href="personalRate2.jsp">
            <span>靶心率阈值</span>
            <p><s:property value="member.setting.bmiLow"/>% ~ <s:property value="member.setting.bmiHigh"/>%</p>
        </a>
    </div>
    <div class="assign">
        <a href="loginwx.asp">退出登录</a>
    </div>
</div>
</body>
</html>