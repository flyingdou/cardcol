<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
var loginId = '<s:property value="#session.loginMember.id"/>';

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
function goWindow(memberId,role){
	var url = "";
	if(role == "M"){
		url = "member.asp";
	}
	if(role == "E" || role == 'I'){
		url = "club.asp";
	}
	if(role == "S"){
		url = "coach.asp";
	}
	window.location.href=url+"?member.id="+memberId+"&member.role="+role;
}
</script>
<div id="top">
    <div id="top-content">
        <p class="left">
        	<s:if test="#session.loginMember !=null">
        		<a href="account.asp"><s:property value="#session.loginMember.name"/></a> 欢迎来到卡库！<a href="login!logout.asp" id="coloa" target="_parent">退出</a>
        	</s:if>
        	<s:else>
        		 欢迎来到E卡通！<a id="coloa" href="login.asp" target="_parent">请登录</a><span>|</span><a href="register.asp" id="coloa" target="_parent">注册</a>
        	</s:else>
        </p>
        <p class="right">
         <input type="text"placeholder="输入俱乐部关键字"style="text-indent:1em;font-size:12px">
         <a href="#"style="border:1px solid #666;padding:2px 5px">搜索俱乐部</a>		
			<a href="index.asp" id="coloa" target="_parent">E卡通首页</a>
			<span>|</span><a href="login.asp" id="coloa" target="_parent">我的E卡通</a>
			<span>|</span><a href="message.asp" id="coloa" target="_parent">消息(<b class="msgb">0</b>)</a>
	<%-- 		<span>|</span><a href="shop!queryShopping.asp" id="coloa" target="_parent"><b class="aimgb">购物车</b><b class="ainsp">0</b>件</a>
			<span>|</span><a href="service.asp" id="coloa" target="_parent">服务中心</a>
			<span>|</span><a href="index.asp" id="coloa" target="_parent">网站导航</a> --%>
		</p>
    </div>
</div>


<s:form id ="memberForm1" name="memberForm1" method="post" action="coach.asp" theme="simple">
<input type="hidden" name="member.id" id="memberId1"/>
</s:form>

<div id="navbg"style="height:60px;background-image:none;background:#ff4401">
<div id="nav">
<%-- <h1><s:property value="#session.toMember.name"/></h1> --%>
<div id="nav-content"style="top:20px;left:0">
	<ul>
		<li><a style="color:white" href="javascript:goWindow('<s:property value="#session.toMember.id"/>','<s:property value="#session.toMember.role"/>');" <s:if test="#session.wpath == 1">class="active"</s:if>>首页</a></li>
		<li><a style="color:white" href="coursewindow.asp" <s:if test="#session.wpath == 2">class="active"</s:if>>健身预约</a></li>
<%-- 		<s:if test="#session.toMember.role != \"E\""> --%>
<!-- 		<li><a href="workoutwindow.asp" <s:if test="#session.wpath == 3">class="active"</s:if>>健身计划</a></li> -->
<%-- 		</s:if> --%>
		<s:if test="#session.toMember.role != \"M\"">
		<li><a style="color:white" href="productwindow.asp" <s:if test="#session.wpath == 4">class="active"</s:if>>商务中心</a></li>
		</s:if>
	</ul>
</div>
</div>
</div>

