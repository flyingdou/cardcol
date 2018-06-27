<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%-- <script type="text/javascript" src="http://qzonestyle.gtimg.cn/qzone/openapi/qc_loader.js" charset="utf-8" data-callback="true"></script> --%>
<script type="text/javascript">
var loginId = '<s:property value="#session.loginMember.id"/>';
//点击图片进入到首页方法
function goMemberHome(memberId,role){
	var url = "";
	jQuery('#memberId1').val(memberId);
	if(role == "M"){
		url = "member.asp";
	}
	if(role == "E"){
		url = "club.asp";
	}
	if(role == "S"){
		url = "coach.asp";
	}
	//jQuery('#memberForm1').attr('action',url);
	//jQuery('#memberForm1').submit();
	//修改为openwindow
	url += "?member.id="+memberId;
	window.open(url,"homeWindow"+memberId);
}
//点击图片进入到首页方法
function goMemberHome(memberId,role){
	var url = "";
	if(role == "M"){
		url = "member.asp";
	}
	if(role == "E"){
		url = "club.asp";
	}
	if(role == "S"){
		url = "coach.asp";
	}
	//jQuery('#memberId1').val(memberId);
	//jQuery('#memberForm1').attr('action',url);
	//jQuery('#memberForm1').submit();
	//修改为openwindow
	url += "?member.id="+memberId;
	window.open(url,"homeWindow"+memberId);
}
//点击更多进入到订单方法
function goProductHome(memberId){
	if(memberId == "<s:property value="#session.loginMember.id"/>"){
		window.location.href="product.asp";
	}else{
		var url = "productwindow.asp";
		url += "?member.id="+memberId;
		window.open(url,"homeWindow"+memberId);
	}
}
function onLogout(){
	if (confirm("是否确认退出当前系统?")) {
		if (QC.Login.check()) QC.Login.signOut();
		window.location.href = "login!logout.asp";
	}
}
</script>

<div id="top">
    <div id="top-content">
        <p class="left">
        	<s:if test="#session.loginMember !=null">
        		<a href="account.asp"><s:property value="#session.loginMember.name"/></a> 欢迎来到卡库！<a href="javascript:onLogout();" id="coloa" target="_parent">退出</a>
        	</s:if>
        	<s:else>
        		 欢迎来到E卡通！<a href="login.asp" target="_parent">请登录</a><span>|</span><a href="register.asp" id="coloa" target="_parent">注册</a>
        	</s:else>
        </p>
        <p class="right">
			<a href="index.asp" id="coloa" target="_parent">E卡通首页</a>
			<span>|</span><a href="login.asp" id="coloa" target="_parent">我的E卡通</a>
			<span>|</span><a href="message.asp" id="coloa" target="_parent">消息(<b class="msgb">0</b>)</a>
		<%-- 	<span>|</span><a href="shop!queryShopping.asp" id="coloa" target="_parent"><b class="aimgb">购物车</b><b class="ainsp">0</b>件</a>
			<span>|</span><a href="service.asp" id="coloa" target="_parent">服务中心</a>
			<span>|</span><a href="index.asp" id="coloa" target="_parent">网站导航</a> --%>
		</p>
    </div>
</div>

<s:form id ="memberForm1" name="memberForm1" method="post" action="coach.asp" theme="simple">
<input type="hidden" name="member.id" id="memberId1"/>
</s:form>
<div id="navbg">
<div id="nav">
 <h1><s:property value="#session.loginMember.name"/></h1>
<div id="nav-content">
	<ul>
		<li><a href="login.asp" <s:if test="#session.spath == 1">class="active"</s:if>>首页</a></li>
		<s:if test="#session.loginMember.role != \"E\"">
		<li><a href="body.asp" <s:if test="#session.spath == 9">class="active"</s:if>>训练日志</a></li>
		</s:if>
		<li><a href="course.asp" <s:if test="#session.spath == 2">class="active"</s:if>>健身预约</a></li>
		<s:if test="#session.loginMember.id != null && #session.loginMember.id != ''">
		<s:if test="#session.loginMember.role != \"E\"">
		<li><a href="workout.asp" <s:if test="#session.spath == 3">class="active"</s:if>>健身计划</a></li>
		<li><a href="diet.asp" <s:if test="#session.spath == 4">class="active"</s:if>>饮食计划</a></li>
		</s:if>
		<li><a href="product.asp" <s:if test="#session.spath == 5">class="active"</s:if>>商务中心</a></li>
		<li><a href="message.asp" <s:if test="#session.spath == 6">class="active"</s:if>>信息中心</a></li>
		<li><a href="account.asp" <s:if test="#session.spath == 7">class="active"</s:if>>我的账户</a></li>
		<s:if test="#session.loginMember.role != \"M\"">
		<li><a href="setting.asp" <s:if test="#session.spath == 8">class="active"</s:if>>我的设定</a></li>
		</s:if>
		<%-- <s:if test="#session.loginMember.role != \"E\"">
		<li><a href="body.asp" <s:if test="#session.spath == 9">class="active"</s:if>>体态分析</a></li>
		</s:if> --%>
		</s:if>
	</ul>
</div>
</div>
</div>

