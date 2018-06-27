<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
</head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>王严专家系统详情</title>
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/bootstrap.css" rel="stylesheet">
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/FJL.css" rel="stylesheet">
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/FJL.picker.css" rel="stylesheet">
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/base.css" rel="stylesheet">
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/style.css" rel="stylesheet">
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/index.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/WX/js/jquery-2.2.0.min.js"></script>
</head>
<body style= " background: url('${pageContext.request.contextPath}/WX/images/systemXq_bg.png') no-repeat ;background-size: cover;">
<div class="body_bg">
    <div class="container system">
        <div class="return_icon"><img src="${pageContext.request.contextPath}/WX/images/return_03.png "></div>
        <div class="top clearfix">
            <div class="title fl">
                <h2 class="fl"><s:property value="goods.name" /></h2>
                <h3 class="fl">王严讲健身</h3>
            </div>
            <s:form id="queryForm" action="goodswx!movementtype.asp" method="post">
			 <s:hidden name="goodsId" id="goodsId" value='1'/>
            <div class="systemImg fr">
                <img src="${pageContext.request.contextPath}/WX/picture/<s:property value='goods.image1'/>" >
                <i class="glyphicon glyphicon-calendar "></i>
                <button id='demo2' data-options='{"type":"date","beginYear":2000,"endYear":2086}' class="date fr btn mui-btn mui-btn-block">请选择开始日期</button>
                <input type="hidden" name="date" value="" id="date"></input>
                <span class="price">￥<s:property value="goods.price" /></span>
                <a href="JavaScript:goodsWangyan()" class="buy fr">购买</a>
            </div>
            </s:form>
        </div>
        <h5>作者简介</h5>
        <p class="teachJs">王严老师从1981年开始进行健美锻炼并从事健身健美训练的研究、教学及健美比赛裁判工作。现任北京市健美协会副主席，国际级健美裁判员，中国健美协会裁判员、裁判委员会委员、培训委员会委员、教练委员会委员。著有《健身运动指导全书》、《青年健美ABC》和《想对健身者说》等专著。</p>
        <h5 class=>系统介绍</h5>
        <div class="list">
            <div class="line fl"></div>
            <h5><span></span>计划类型</h5>
            <p><i></i><s:property value="goods.planType" /></p>
            <h5><span></span>适用对象</h5>
            <p><i></i><s:property value="goods.applyObject" /></p>
            <h5><span></span>计划周期</h5>
            <p><i></i><s:property value="goods.plancircle" /></p>
            <h5><span></span>使用场景</h5>
            <p><i></i><s:property value="goods.scene" /></p>
            <h5><span></span>所需器材</h5>
            <p><i></i><s:property value="goods.apparatuses" /></p>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/WX/js/FJL.min.js"></script>
<script src="${pageContext.request.contextPath}/WX/js/FJL.picker.min.js"></script>
<script>
    (function($) {
        $.init();
        var result = $('#demo2')[0];
        var btns = $('.btn');
        btns.each(function(i, btn) {
            btn.addEventListener('tap', function() {
                var optionsJson = this.getAttribute('data-options') || '{}';
                var options = JSON.parse(optionsJson);
                var id = this.getAttribute('id');
                var picker = new $.DtPicker(options);
                picker.show(function(rs) {
                    result.innerText = rs.text;
                    picker.dispose();
                });
            }, false);
        });
    })(mui);
    function goodsWangyan(){
    	var date = $("#demo2").text();
    	var member = '<s:property value="#session.member" />'
    	if(date=="请选择开始日期"){
    		alert(date);
    	} else if(member==""){
    		window.location.href="login.jsp";
    	}else{
    		$("#date").val(date);
    		$("#queryForm").submit();
    	}
    	}
</script>
</body>
</html>