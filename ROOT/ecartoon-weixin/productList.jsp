<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() +  request.getContextPath() + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<title>健身卡列表</title>
<link href="ecartoon-weixin/css/mui.min.css" rel="stylesheet" />
<style type="text/css">
body{
	background-color:#EDEDED;
}
#ectimg {
	width:100%;
}
#dou{
	margin-left:20px;
	margin-right:20px;
}
</style>
</head>
<body>
	<div id="app" >
		<div v-for="v in prodList" align="center" style="margin-bottom: 20px;" >
			  <div class="whiteBorder" style = "margin:10px 0;padding:30px 10px;background-color:#fff;" >
					<a :href="'eproductwx!oneCardDetail.asp?id='+v.id" style="text-decoration: none;"> 
						 <div id ="dou">
							<img :src="'picture/'+v.prodDetailImage" id=ectimg>
						 </div>
					</a>
			  </div>
		</div>
	</div>
</body>
<script src="ecartoon-weixin/js/vue.min.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js "></script>
<script type="text/javascript" src="http://www.ecartoon.com.cn/js/utils.elisa.js"></script>
<script src="ecartoon-weixin/js/jquery.min.js" type="text/javascript"
	charset="utf-8"></script>
<script>
new Vue({
	el:"#app",
	data:{
		prodList:${prodInfo.prodList}
	}
});

wxUtils.sign("ewechatwx!sign.asp");
wx.ready(function(){
	wxUtils.share({
		title : "健身E卡通—在线购卡",
		link : "<%=basePath%>eproductwx!findOneCardList.asp"+ location.search,
		img : "<%=basePath%>img/shareLogo.png",
		desc : "一卡在手，全城都有。健身E卡通为您打造健康生命第三空间……"
	});
});
</script>
</html>