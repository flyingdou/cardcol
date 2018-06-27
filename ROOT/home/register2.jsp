<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<s:include value="/share/meta.jsp" />
<meta name="Generator" content="EditPlus">
<meta name="Author" content="">
<meta name="keywords" content="注册用户" />
<meta name="description" content="健身E卡通用于用户注册" />
<title>健身E卡通_注册成功</title>
<link rel="stylesheet" type="text/css" href="css/register-2.css" />
<style type="text/css">
.cneterzhec {
	width: 900px;
	height: 300px;
	margin: 0px auto;
	/*border:#999999 1px solid;*/
	margin-top: 50px;
	/*background:#FFFFFF;*/
}

.cneterzhec-1 {
	font-size: 14px;
	font-weight: bold;
	border-bottom: 1px #999999 solid;
	margin-top: 20px;
	margin-left: 15px;
	padding-bottom: 10px;
	margin-right: 15px;
}

.centdivser {
	width: 600px;
	height: 200px;
	margin:50px auto;
	
}
.centdivser p {
	color:#048f00;
}
.centdivsera {
	width:220px;
	height:50px;
	padding-left:50px;
	background:url(images/zheceok.jpg) no-repeat;
	margin:0 auto;
	font-size:22px; 
	text-align:center;
	font-weight:bold;
	line-height:50px;
}
.centdivserb {
	font-size:22px; 
	font-weight:bold;
	text-align:center;
	line-height:50px;
}

</style>
<script type="text/javascript">
var num = 5;
function changeTime() {
	if (num <= 0) {
		window.location.href = "index.asp";
	} else {
		num--;
		$('.number').html(num);
		setTimeout("changeTime()", 1000);
	}
}
$(function(){
	changeTime();
})
</script>
</head>
<body>
	<div id="all">
		<s:include value="/share/home-header.jsp" />
		<div class="cneterzhec">
			<div class="cneterzhec-1">注册<s:if test="message == \"OK\"">成功</s:if><s:else>失败</s:else></div>
			<div class="centdivser">
				<s:if test="message == \"OK\"">
				<div class="centdivsera">
					<p>亲爱的<s:property value="member.nick"/></p>
				</div>
				<div class="centdivserb">
					<p>恭喜您注册成功。</p>
				    <p>系统将在<span class="number">5</span>秒后自动为你跳转到您的首页，或者点击<a href="index.asp">这里</a>立即跳转</p>
				</div>
				
				</s:if>
				<s:elseif test="message == \"exist\"">
				  <div class="centdivserb">
				    <p>您输入的邮箱已经在健身E卡通<br/>网上注册过，请重新输入再注册！</p>
				  </div>
				</s:elseif>
			</div>
		</div>
		<s:include value="/share/footer.jsp" />
	</div>
</body>
</html>
