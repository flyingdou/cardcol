<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>挑战详情</title>
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/bootstrap.css" rel="stylesheet">
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/base.css" rel="stylesheet">
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/style.css" rel="stylesheet">
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/index.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/jquery-2.2.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/style.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/imgs.js"></script>
</head>
<body>
<s:iterator value="active">
<div class="challengeXp container">
    <div class="banner">
        <div class="bg_color">
            <div class="pic fl">
             <img src="../picture/<s:property value="image"/>" alt="">
            </div>
            <div class="txt fl">
                <h4><s:property value="name" /></h4>
                <p>发布人：<span><s:property value="creator.name" /></span></p>
                <p>参加人数：<span><s:property value="#request.count" />人</span></p>
            </div>
        </div>
    </div>
    <div class="challenge_list">
        <div class="list_one">
                <div class="icon"><img src="${pageContext.request.contextPath}/WX/images/D_icon_07.png"></div>
                <p>挑战目标<span><s:if test="category=='A'">
										<s:property value="days" />天内体重增加<s:property value="value" />KG</s:if>
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
										<s:if test="action != null">
											<s:property value="action"/>
										</s:if>
										<s:else>
											<s:property value="content"/>
										</s:else>
										<s:property value="value" />千米</s:elseif>
									<s:elseif test="category=='H'">
									    <s:property value="days" />天负荷<s:property value="value" />Kg
									</s:elseif></span></p>
        </div>
        <div class="list_one">
                <div class="icon"><img src="${pageContext.request.contextPath}/WX/images/D_icon_10.png"></div>
                <p>成功奖励<span><s:property value="award" /></span></p>
        </div>
        <div class="list_one">
                <div class="icon"><img src="${pageContext.request.contextPath}/WX/images/D_icon_12.png"></div>
                <p>失败惩罚<span>
                	向<s:property value="institution.name" />捐款 <s:property value="amerceMoney" />元
                </span></p>
        </div>
    </div>

    <div class="note">
        <h3>注意事项</h3>
        <s:property value="memo"/>
        <p>请将运动成绩记录到训练日志中，成绩将自动发送给裁判评判实性。如挑战成功，保证金将全额退还。</p>
    </div>

    <div class="bottom">
        <a href="#" class="share fl">分享</a>
        <a href="activelistwx!joinChallenge.asp?id=<s:property value="id" />"class="join fl">参加挑战</a>
    </div>
</div>
</s:iterator>
<div class="share_bg" style="display: none">
    <div class="share_show">
        <div class="inner_top clearfix">
            <h3>分享<span>即将获得</span>2元红包</h3>
            <h5>日志好精彩～赶紧分享吧</h5>
            <div class="icon_top">
                <a href="#"><img src="images/share_icon_03.png" alt=""></a>
                <p>qq好友</p>
            </div>
            <div class="icon_top">
                <a href="#"><img src="images/share_icon_05.png" alt=""></a>
                <p>新浪微博</p>
            </div>
            <div class="icon_top">
                <a href="#"><img src="images/share_icon_10.png" alt=""></a>
                <p>微信好友</p>
            </div>
            <div class="icon_top">
                <a href="#"><img src="images/share_icon_07.png" alt=""></a>
                <p>朋友圈</p>
            </div>
        </div>
        <div class="quXiao">取消</div>
    </div>
</div>
</body>
</html>