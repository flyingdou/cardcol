<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="健身E卡通-我的账户" />
<meta name="description" content="健身E卡通-我的账户" />
<title>健身E卡通-我的账户</title>
<style type="text/css">
.tablea {
	background:#ccc;
	width:720px;
	height:100%;
}
.tablea th {
	background:#c5dbec; 
	height:30px;
}
.tablea td {
	background:white;
	height:30px;
}
</style>
<link rel="stylesheet" type="text/css" href="css/user-account.css" />
<script src="script/ckeditor/ckeditor.js" type="text/javascript"></script>
<script language="javascript">

</script>
</head>
<body>
	<s:include value="/share/header.jsp"/>
	<div id="content">
		<s:include value="/basic/nav.jsp" />
		<div id="right-2">
		<h1 style="margin-left:0px; margin-top:0px;">我的积分</h1>
		
		<div>
		  你目前的积分为：<s:if test="integralCount == null">0</s:if><s:else><s:property value="integralCount"/></s:else>分
		  <p>积分记录</p>
		 <table class="tablea">
				<tr style="">
					<th>积分</th>
					<th>来源</th>
					<th>积分生效时间</th>
				</tr>
				<s:iterator value="integrals">
				<tr>
					<td><s:property value="integral"/></td>
					<td>
						<s:if test="inteSource == 1">注册</s:if>
						<s:if test="inteSource == 2">首次登录</s:if>
						<s:if test="inteSource == 3">购买套餐（私教课时）</s:if>
						<s:if test="inteSource == 4">完成一次训练</s:if>
						<s:if test="inteSource == 5">撰写一次评价</s:if>
						<s:if test="inteSource == 6">每登录一次</s:if>
						<s:if test="inteSource == 7">一周未登录（扣分）</s:if>
					</td>
					<td><s:date name="inteTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
				</s:iterator>
		 </table>
		<!-- <p style="height:20px; line-height:20px; margin-top:15px;">积分的获取方法</p>
		 <div style="width:600px; height:100%; line-height:20px;"> 
			1、注册获得积分：10分<br/>
			2、登录获得积分：1分/次<br/>
			3、购买套餐（私教课时），每10元1分；<br/>
			4、完成一次训练加2分；（签到和点击完成本次训练都可算作完成一次训练。一天的上限为一次。）<br/>
			5、撰写一次评价，加1分；<br/>
			6、每登录一次，加1分；<br/>
			7、扣分：一周未登录，扣1分。
		</div>
		<p style="height:20px; line-height:20px; margin-top:15px;">积分与等级关系</p>
		 <div style="width:600px; height:100%; line-height:20px; margin-bottom:20px;"> 
			1、三级运动员：0-100<br/>
			2、二级运动员：101-200<br/>
			3、一级运动员：201-300<br/>
			4、运动健将：301-400<br/>
			5、国际级运动健将：400以上<br/>
		</div>-->
		</div>
		
		</div>
	</div>
	<s:include value="/share/footer.jsp"/>
</body>
</html>
