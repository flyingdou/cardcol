<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%
   String path = request.getContextPath();
   String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!doctype html >
<html>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0"/>
<meta name="apple-mobile-web-app-capable" content="yes">

<link type="text/css" href="css/bootstrap.css" rel="stylesheet">
<link type="text/css" href="css/base.css" rel="stylesheet">
<link type="text/css" href="css/venue.css" rel="stylesheet">
<script type="text/javascript" src="js/venue.js"></script>
<title>关于我们</title>
<style>
    .dou-about{
       background: #f6f6f6;
       height: auto;
    }

    .about{
       width: 60%;
       margin-top: 70px;
       margin-left: auto;
       margin-right: auto;
    }
    .navigate{
       padding-top:15px;
       padding-bottom:15px;
       font-weight: 500; 
       font-size: 14px; 
       box-sizing: border-box; 
       width: 100%; 
       height: auto;
    }
    .about-font{
       width: 100%;
       text-align: center;
       background: white;
       padding:7% 0;
       margin-bottom: 100px;
    }
    
    
    .dou-font{
       width: 100%;
       text-align: center;
       overflow: hidden;
    }
    
    .about-title{
       width: 80%;
       height: auto;
       font-size: 25px;
       text-align: left;
       font-weight: bold;
       margin-left: 10%;
       margin-bottom: 15px;
       color: #010101;
    }
    
    
    .about-body{
       width: 80%;
       text-align: left;
       margin-left: auto;
       margin-right: auto;
       font-size: 20px;
       color: #010101;
    }
    
    .about-body-1{
       display:inline-block;
       margin-bottom: 15px;
    }
</style>
</head>
<body class="dou-about">
<!-- 引入页面头部导航栏 -->
<%@include file="/newpages/head.jsp" %>

<!-- 当前页面的内容，此处具体为关于我们的介绍 -->
<div class="about">
   <div class="navigate">
	   <a href="index.asp">首页</a>&nbsp;&gt;&nbsp;关于我们
   </div>
   
<div class="about-font">
   
   <div class="dou-font">
   <div class="about-title">关于我们</div>
	   <div class="about-body">
			 <span class="about-body-1">
			          &nbsp;&nbsp;健身E卡通，是发行健身通卡的第三方网络平台，本项目由由北京健身俱乐部联合发展委员会倡议发起，健易通信息科技（北京）有限公司负责运营，
			          研发单位为武汉好韵莱信息技术有限公司。
			 </span>
			 <br>
			 <span class="about-body-1">
					  &nbsp;&nbsp;便利性是消费者选择健身俱乐部的首要影响因素，消费门槛的高低、购买的灵活性对购买决策也有较大影响。健身E卡通解决了健身服务地域局限问题，
					  满足居家、办公不同地点的健身需求。同时还可以为消费者规避预付消费的风险，消费者持E卡通健身将更灵活、更方便、更畅爽。 
					  为了更好地满足健身者的需要，健身E卡通中还提供了与我国知名健身专家王严共同研发的智能健身计划引擎，可以根据用户的身体数据自动生成个性化的健身方案，指导科学健身。
			 </span>
			 <br>
			 <span class="about-body-1">
			          &nbsp;&nbsp;线下各健身俱乐部传统的营销方式以人员推销为主。简单、高压的工作没有吸引力，用工成本不断增加，人员招留困难。互联网上的大流量入口对体量较小的俱乐部没有合适的解决方案。
			          因此需要一个专门为健身俱乐部服务的网络平台。健身E卡通可以为俱乐部提供营销帮助，促进健身行业健康发展。
			 </span>
			 <br>
			 <span class="about-body-1">
			 		  &nbsp;&nbsp;随着健身卡收入在俱乐部总收入中比重逐渐降低；新兴的智能健身房普遍采用按月或按次消费模式；政府对预付费商业模式管控进一步升级。
			 		  健身俱乐部传统的年卡产品和非标准化、重销售的模式将会发生变化。
			 		  网络标准化产品的营销销售模式将兴起，各种金融手段开始应用于健身卡营销。
			 </span>
			 <br>
			 <span class="about-body-1">
					  &nbsp;&nbsp;随着网络的普及，“互联网+健身”的模式在满足消费者需求、规范商家内部管理等方面都将发挥积极作用。
			 </span>
			 <br>
	   </div>
	</div>
</div>
 
</div>

<!-- 引入页面底部 -->
<%@include file="/newpages/footer.jsp" %>
</body>
</html>