<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<style type="text/css">
.navbar-header {
	margin-top: 10px !important;
}

input {
	font-size: 12px;
}

.navUla {
	line-height: 23px !important;
	border-radius: 6px;
	padding: 2px 4px !important;
	background-color: red;
	color: white;
}

.navUla:link {
	color: white
}

.navUla:visited {
	background-color: red !important;
	color: white !important;
}

.navUla:hover {
	background-color: red !important;
	color: white !important;
}

.navUla:active {
	background-color: red !important;
	color: white !important;
}

.ewDownloadBox {
	position: relative;
	width: 50%;
	height: auto;
	left: 30%;
	top: 70%;
}

.cheng {
	display: block;
	background-color: #ff4401;
	text-align: center;
	color: white;
	font-size: 23px;
	padding: 3px 8px;
	line-height: 40px;
	border: 2px solid white;
	margin-top: 25%;
	border-radius: 8px;
}

.ewmBox {
	overflow: hidden;
	box-sizing: border-box
}

@media ( max-width :770px ) {
	.navbar-header {
		position: fixed; ! important;
		top: 0px !important;
		left: 25px;
	}
	.navUla {
		position: fixed !important;
		top: 10px;
		right: 15px;
	}
}

@media ( max-width :810px ) {
	.ewDownloadBox {
		position: relative;
		width: 80%;
		height: auto;
		left: 10%;
		top: 70%;
	}
	.w50 {
		width: 100%;
	}
	.cheng {
		display: inline-block !important;
		width: 40%;
		margin-left: 8% !important;
	}
	.ewmBox {
		display: none !important;
	}
}

@media ( max-width :500px ) {
	.cheng {
		display: block;
		background-color: #ff4401;
		text-align: center;
		color: white;
		font-size: 12px !important;
		padding: 4px 2px !important;
		line-height: 20px;
		border: 1px solid white;
		margin-top: 25%;
		border-radius: 4px;
	}
}

.navUla {
	background: none;
	color: white;
	border-radius: 0;
}

.navUla:hover {
	background-color: rgba(0, 0, 0, 0) !important;
	color: red !important
}

.navUla:hover .navUlaa {
	background-color: rgba(0, 0, 0, 0) !important;
	color: white !important
}

.navLast {
	border: none
}

.banner_1 {
	width: 100%;
	height: 680px;
	position: relative;
}
</style>
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="bootstrap/css/bootstrap.min.css"></script>
<script type="text/javascript" src="js/jquery-2.2.0.min.js" ></script>
<script type="text/javascript">
	var loginId = '<s:property value="#session.loginMember.id"/>';
	//点击图片进入到首页方法
	function goMemberHome(memberId, role) {
		var url = "";
		jQuery('#memberId1').val(memberId);
		if (role == "M") {
			url = "member.asp";
		}
		if (role == "E") {
			url = "club.asp";
		}
		if (role == "S") {
			url = "coach.asp";
		}
		//jQuery('#memberForm1').attr('action',url);
		//jQuery('#memberForm1').submit();
		//修改为openwindow
		url += "?member.id=" + memberId;
		window.open(url, "homeWindow" + memberId);
	}
	//点击图片进入到首页方法
	function goMemberHome(memberId, role) {
		var url = "";
		if (role == "M") {
			url = "member.asp";
		}
		if (role == "E") {
			url = "club.asp";
		}
		if (role == "S") {
			url = "coach.asp";
		}
		//jQuery('#memberId1').val(memberId);
		//jQuery('#memberForm1').attr('action',url);
		//jQuery('#memberForm1').submit();
		//修改为openwindow
		url += "?member.id=" + memberId;
		window.open(url, "homeWindow" + memberId);
	}
	//点击更多进入到订单方法
	function goProductHome(memberId) {
		if (memberId == "<s:property value="#session.loginMember.id"/>") {
			window.location.href = "product.asp";
		} else {
			var url = "productwindow.asp";
			url += "?member.id=" + memberId;
			window.open(url, "homeWindow" + memberId);
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
	
	
	
	function loadPage() {
		var city = $("#currentCity").find("option:selected").html();
	    location.href = "clublist.asp?currentCity=" +encodeURI(encodeURI(city));
	}
	
	
	
	$(function(){
		var city = "${currentCity == null ? '武汉市' : currentCity}";
		var sel = $("#currentCity")[0].getElementsByTagName("option");
		for(var i = 0 ; i< sel.length;i++){
			var elem = sel[i];
			if(city.replace(" ","") == elem.innerHTML.replace(" ","")){
				elem.selected = "selected";
			}
		}
	})
	
	
</script>

<nav class="navbar navbar-fixed-top" style="margin: 0;background:rgba(1,1,1,.5)">


<div class="container" style="">

	<div class="navbar-header">
		<a href="#">
			<img src="img/logo.png" height="30" alt="" />
		</a>
		<span style="color: white; display: inline-block; height: 30px; line-height: 30px; margin-left: 2em;">
			服务热线：13908653155 </span>


		<span style="color: white; margin-left: 30px">

			<select
				style="background: rgba(0, 0, 0, 0); color: #ff4401; border: none; outline-color: rgba(0, 0, 0, 0) !important" id = "currentCity" onchange="loadPage()" >
				<!-- 城市列表开始 -->
				<s:iterator value="#request.openCitys">
					<option value="volvo" > <s:iterator value="value"><s:property value="name"/></s:iterator></option>
				</s:iterator>
				<!-- 城市列表结束 -->
			</select>
		</span>

	</div>

	<ul class="nav navbar-nav navbar-right navUl">
		<li style="margin-top: 12px !important; padding: 0px" class="kkk">
			<a href="index.asp" class="navUla">
				E卡通首页&nbsp;
				<span class="navUlaa">|</span>
			</a>
		</li>
		<%-- <li style="margin-top: 12px !important; padding: 0px">
			<a href="${pageContext.request.contextPath}/clublist.asp" class="navUla">
				俱乐部列表&nbsp;
				<span class="navUlaa">|</span>
			</a>
		</li> --%>
		<li style="margin-top: 12px !important; padding: 0px">
			<a href="login.asp" class="navUla">
				我的E卡通&nbsp;
				<span class="navUlaa">|</span>
			</a>
		</li>
		<li style="margin-top: 12px !important; padding: 0px">
			<a href="login.asp" class="navUla">
				消息&nbsp;
				<span class="navUlaa">|</span>
			</a>
		</li>
		<li style="margin-top: 12px !important; padding: 0px">
			<a href="login.asp" class="navUla navLast">登录/注册</a>
		</li>
	</ul>
</div>
<div class="fix"></div>
</nav>