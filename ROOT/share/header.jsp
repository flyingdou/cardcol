<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(function(){
	$(".nav-content ul li").mouseover(function(){
		alert(1);
		$(".nav-content ul li").css("border","none")
	})
})
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
		window.location.href = "login!logout.asp";
	}
}
</script>

<div id="top">
    <div id="top-content">
        <p class="left">
        	<s:if test="#session.loginMember !=null">
        		<a href="account.asp"><s:property value="#session.loginMember.name"/></a> 欢迎来到E卡通！<a href="javascript:onLogout();" id="coloa" target="_parent">退出</a>
        	</s:if>
        	<s:else>
        		 欢迎来到E卡通！<a href="login.asp" target="_parent">请登录</a><span>|</span><a href="register.asp" id="coloa" target="_parent">注册</a>
        	</s:else>
        </p>
        <form id="subform" action="clublist.asp">
	        <p class="right">
	         <input type="text" name="keyword" placeholder="输入俱乐部关键字"style="text-indent:1em;font-size:12px">
	         <a href="#" onclick="searchClubs()" style="border:1px solid #666;padding:2px 5px">搜索俱乐部</a>		
				<a href="index.asp" id="coloa" target="_parent">E卡通首页</a>
				<span>|</span><a href="login.asp" id="coloa" target="_parent">我的E卡通</a>
				<span>|</span><a href="message.asp" id="coloa" target="_parent">消息(<b class="msgb">0</b>)</a>
			</p>
		</form>
    </div>
</div>

<s:form id ="memberForm1" name="memberForm1" method="post" action="coach.asp" theme="simple">
<input type="hidden" name="member.id" id="memberId1"/>
</s:form>
<div id="navbg"style="background-image:none;height:auto;;background:#ff4011">
<div id="nav">
<div id="nav-content"style="position:relative;width:100%;top:0;left:0;">
	<ul style="height:60px;box-sizing:border-box;width:960px;margin:0 auto;">
		<li style="height:25px;margin-top:15px;"><a href="login.asp" style="color:white"<s:if test="#session.spath == 1">class="active"</s:if>>首页</a></li>
		<%-- 
		<s:if test="#session.loginMember.role != \"E\"">
		<li style="height:25px;margin-top:15px;"><a href="body.asp" style="color:white"<s:if test="#session.spath == 9">class="active"</s:if>>训练日志</a></li>
		</s:if> 
		--%>
		<li style="height:25px;margin-top:15px;"><a href="course.asp" style="color:white"<s:if test="#session.spath == 2">class="active"</s:if>>健身预约</a></li>
		<s:if test="#session.loginMember.id != null && #session.loginMember.id != ''">
		<s:if test="#session.loginMember.role != \"E\"">
		<li style="height:25px;margin-top:15px;"><a href="workout.asp" style="color:white"<s:if test="#session.spath == 3">class="active"</s:if>>健身计划</a></li>
		<li style="height:25px;margin-top:15px;"><a href="diet.asp" style="color:white"<s:if test="#session.spath == 4">class="active"</s:if>>饮食计划</a></li>
		</s:if>
		<li style="height:25px;margin-top:15px;"><a href="product.asp" style="color:white"<s:if test="#session.spath == 5">class="active"</s:if>>商务中心</a></li>
		<li style="height:25px;margin-top:15px;"><a href="message.asp" style="color:white"<s:if test="#session.spath == 6">class="active"</s:if>>信息中心</a></li>
		<li style="height:25px;margin-top:15px;"><a href="account.asp" style="color:white"<s:if test="#session.spath == 7">class="active"</s:if>>我的账户</a></li>
		<s:if test="#session.loginMember.role != \"M\"">
		<li style="height:25px;margin-top:15px;"><a href="setting.asp" style="color:white"<s:if test="#session.spath == 8">class="active"</s:if>>我的设定</a></li>
		</s:if>
		</s:if>
	</ul>
</div>
</div>
</div>
<script>
	function searchClubs() {
		document.getElementById('subform').submit();
	}
</script>

