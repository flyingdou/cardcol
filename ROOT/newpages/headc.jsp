<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
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
		<header>
		<div class="container">
			<div class="row">
				<div class="header_top clearfix">
					<div class="col-md-3 col-lg-3">
						<ul class="ul_left">
							<li class="icon"><span><img
									src="images/icon_01_06.png">917981712</span></li>
							<li class="icon"><span><img
									src="images/icon_2_08.png">027-86626480</span></li>
						</ul>
					</div>
					<div class="col-md-3 col-lg-3">
						<div class="search">
							<form id="search" name="search" action="" method="post">
								<!-- <input type="button" value="" class="sousu"
									onclick="return doQuery();"> -->
								<s:select id="selectQueryType" class="sear_pic" name="queryType"
									list="#{'0':'健身俱乐部','1':'健身教练','2':'健身计划'}" listKey="key"
									listValue="value" />
								<s:textfield name="keyword" cssClass="input" class="field" id="keyword"  onmouseover="this.focus();this.select();"
										onkeypress="if(event.keyCode==13) doQuery();"
										onclick="if(value==defaultValue){value='';this.style.color='#000'}" 
										onBlur="if(!value){value=defaultValue;this.style.color='#999'}"/>
										<a href="javascript:doQuery()"><img src="images/icon_04_10.png" class="search_inner"></a>
							</form>
													
						</div>
					</div>
					<div class="col-md-6 col-lg-6">
						<ul class="ul_right">
							<li><a href="index.asp" >卡库首页</a></li>
							<li><a href="login.asp" >我的卡库</a></li>
							<li><a href="message.asp" >消息</a></li>
							<li><a href="service.asp" >服务中心</a></li>
							<li><a href="shop!queryShopping.asp" ><img src="images/icon_11.png">购物车<span>0</span>件</a></li>
							<li><a  href="login.asp" ><img src="images/icon_14.png">会员登录</a></li>
							<li><a href="register.asp"  class="last_a"><img src="images/icon_16.png">注册</a></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="header_bot clearfix">
					<div class="col-md-2 col-lg-3">
						<a href="index.asp"><img src="images/logo_25.png" class="logo"></a>
					</div>
					<div class="col-md-3 col-md-offset-1 col-lg-offset-0 col-lg-3">
						<p>
							我&nbsp;在&nbsp;<span class="city"><s:property value="#session.currentCity"/></span>&nbsp;<span
								class="qiehuan"><a href="city.asp">【切换】</a></span>
						</p>
					</div>
					<div class="col-md-6 col-lg-6">
						<ul>
							<li><a href="index.asp"<s:if test="#session.position == 0"> </s:if>>首页</a></li>
							<li><a href="planlist.asp"<s:if test="#session.position == 4"> </s:if>>健身计划</a></li>
							<li><a href="clublist.asp"<s:if test="#session.position == 2"> </s:if>>健身场馆</a></li>
							<li><a href="coachlist.asp"<s:if test="#session.position == 3"> </s:if>>健身教练</a></li>
							<li><a href="activelist.asp"<s:if test="#session.position == 1"> </s:if>>健身挑战</a></li>
							<li><a href="map.asp"<s:if test="#session.position == 5"> </s:if>>健身地图</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		</header>