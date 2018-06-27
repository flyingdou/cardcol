<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="选择角色" />
<meta name="description" content="选择角色页面" />
<title>健身E卡通_会员首页</title>
<link href="css/index.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/register-2.css" />
<script type="text/javascript">
function selected(role) {
	$('#role').val(role);
	$('#regform').submit();
}
</script>
</head>
<body>
	<div id="all">
		<s:include value="/share/home-header.jsp" />
		<s:form name="regform" id="regform" theme="simple" action="account.asp">
			<s:hidden name="member.id" />
			<s:hidden name="member.role" id="role"/>
		</s:form>
<!----------------------------登录选择角色页面------------------------------>
	<div id="user">
		<div class="content1">
			<div class="secess_pic">
				<img src="images/zheceok.jpg">
			</div>
			<div class="secess_word">
				欢迎登录卡库网，请选择你的角色
			</div>
		</div>
		<div class="content2">
			<div class="member">
				<div class="member_img" >
					<img src="images/member_pic.jpg">
				</div>				
				<div class="instructions">
					<b>我是健身会员</b>
					<div>
						<p>我希望拥有健康的身体和良好的生活习惯！<br>我愿意接受专业的健身指导，并采用最适合自己的方式科学健身。</p>
					</div>			
					<button onclick="selected('M')">选择健身服务</button>					
				</div>
			</div>
			<div class="member">
				<div class="member_img">
					<img src="images/coach_pic.jpg">
				</div>				
				<div class="instructions">
					<b>我是健身教练</b>
					<div>
						<p>我是一名出色的健身教练！<br>我将以专业的能力和诚实正真之心来帮助我的会员达到最理想的训练效果。</p>
					</div>
					<button onclick="selected('S')">发布服务项目</button>
				</div>
			</div>
			<div class="member">
				<div class="member_img">
					<img src="images/club_pic.jpg">
				</div>				
				<div class="instructions">
					<b>我是健身俱乐部</b>
					<div>
						<p>我是一家独特的健身俱乐部！<br>我为会员提供最具特色的健身设施和服务，并坚持公平的运营方式保障客户的安全和健康。</p>
					</div>
					<button onclick="selected('E')">订制健身套餐</button>
				</div>
			</div>
		</div>
	</div>		
	<s:include value="/share/footer.jsp" />
</div>
</body>
</html>