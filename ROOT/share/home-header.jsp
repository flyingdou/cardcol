<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

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
function doQuery() {
	var type = $('#selectQueryType').val();
	var url = '';
	if (type == 0) {
		url = 'clublist.asp';
	} else if (type == 1) {
		url = 'coachlist.asp';
	} else {
		url = 'planlist.asp';
	}
	$('#search').attr('action', url);
	$('#search').submit();
}
</script>
<div id="top">
    <div id="top-content">
        <p class="left">
        	<s:if test="#session.loginMember !=null">
        		<a href="account.asp"><s:property value="#session.loginMember.name"/></a> 欢迎来到卡库！<a href="login!logout.asp" id="coloa" target="_parent">退出</a>
        	</s:if>
        	<s:else>
        		 欢迎来到E卡通！<a  id="coloa"  href="login.asp" target="_parent">请登录</a><span>|</span><a href="register.asp" id="coloa" target="_parent">注册</a>
        	</s:else>
        </p>
        <p class="right">
          <input type="text"placeholder="输入俱乐部关键字"style="text-indent:1em;font-size:12px">
         <a href="#"style="border:1px solid #666;padding:2px 5px">搜索俱乐部</a>		
			<a href="index.asp" id="coloa" target="_parent">E卡通首页</a>
			<span>|</span><a href="login.asp" id="coloa" target="_parent">我的E卡通</a>
			<span>|</span><a href="message.asp" id="coloa" target="_parent">消息(<b class="msgb">0</b>)</a>
<%-- 			<span>|</span><a href="shop!queryShopping.asp" id="coloa" target="_parent"><b class="aimgb">购物车</b><b class="ainsp">0</b>件</a>
			<span>|</span><a href="service.asp" id="coloa" target="_parent">服务中心</a>
			<span>|</span><a href="index.asp" id="coloa" target="_parent">网站导航</a> --%>
		</p>
    </div>
</div>
<div id="header">
	<div id="logo">
		<blockquote>
			<p>
				<a href="index.asp" target="_parent"><img src="images/logo.png" alt="卡库网" /></a>
			</p>
		</blockquote>
	</div>
	<div id="switch">
		<span class="city"><s:property value="#session.currentCity"/></span><a href="city.asp" id="coloa">[切换城市]</a>
	</div>
	<div id="ad">
		<img src="images/ad.png" />
	</div>
</div>
<s:form id ="memberForm1" name="memberForm1" method="post" action="coach.asp" theme="simple">
<input type="hidden" name="member.id" id="memberId1"/>
</s:form>
<div id="nav">
	<div id="nav-content">
		<div class="navigation">
			<ul>
			    <li>E卡通服务</li>  
				<li><a href="index.asp"<s:if test="#session.position == 0"> class="active" </s:if>>首页</a></li>
				<li><a href="planlist.asp"<s:if test="#session.position == 4"> class="active" </s:if>>健身计划</a></li>
				<li><a href="clublist.asp"<s:if test="#session.position == 2"> class="active" </s:if>>健身场馆</a></li>
				<li><a href="coachlist.asp"<s:if test="#session.position == 3"> class="active" </s:if>>健身教练</a></li>
				<li><a href="activelist.asp"<s:if test="#session.position == 1"> class="active" </s:if>>健身挑战</a></li>
				<li><a href="map.asp"<s:if test="#session.position == 5"> class="active"</s:if>>健身地图</a></li>
			</ul>
		</div>
		<div class="search">
			<div class="search">
				<s:form action="" method="post" name="search" id="search" theme="simple">
				<s:select id="selectQueryType" name="queryType" list="#{'0':'健身俱乐部','1':'健身教练','2':'健身计划'}" listKey="key" listValue="value"/>
					<s:textfield name="keyword" cssClass="input" id="keyword" 
						onmouseover="this.focus();this.select();" 
						onkeypress="if (event.keyCode==13) doQuery();"
						onclick="if(value==defaultValue){value='';this.style.color='#000'}" 
						onBlur="if(!value){value=defaultValue;this.style.color='#999'}"/>
					<span class="submit" onclick="return doQuery();"></span>
				</s:form>
			</div>
		</div>
	</div>
</div>
