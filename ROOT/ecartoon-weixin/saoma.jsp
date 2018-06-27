<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + path + "/";
%>
<!doctype html >
<html>
<head>
<title>扫码签到</title>
<base href="<%=basePath%>">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" charset="utf-8"/>
<script src="ecartoon-weixin/js/jquery.min.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<style type="text/css">
html,body{
	width:100%;
	height:100%;
	margin:0;
	padding:0;
}
body{
	background:url(ecartoon-weixin/img/background.png) no-repeat;
	background-size:100% 100%;
}
.icon1,.icon2,.icon3,.icon4,.button{
	text-align:center;
}
.marginTop{
	margin-top:30px;
}
.icon1{
	margin-top:50px;
}
.icon1>img{
	width:90px;
	height:45px;
}
.icon2{
	color:#fff;
	font-size:24px;
}
.icon3{
	color:#999;
	font-size:12px;
}
.button{
	width:98%;
	height:50px;
	border:0;
	background: url("ecartoon-weixin/img/buy_button.png");
	background-size:100% 100%;
	color:#fff;
	font-size:16px;
	line-height: 30px;
}
.userInfo{
	background-color:#FFF;
	height:75px;
	overflow: hidden;
}
.userInfo>img{
	width:40px;
	height:40px;
	float:left;
	margin-top:17.5px;
	margin-left:20px;
	margin-right:10px;
	border-radius:50%;
}
.userInfo>div{
	height:40px;
	float:left;
	margin-top:17.5px;
}
.shadow{
	height:10px;
	background-color: #EFEFF4;
}
</style>
</head>
<body>
<div class="userInfo">
	<img class="userPhoto" src="picture/${memberInfo.image}" />
	<div>
		<p style="font-size:15px;color:1e1e1e;margin:0;">${memberInfo.name}</p>
		<p style="font-size: 13px;color:#999;margin:0;">已健身<span style="font-size:15px;color:#ff4401;">${memberInfo.signNum}</span>次</p>
	</div>
</div>
<div class="shadow"></div>
<div class="icon1">
<img src="ecartoon-weixin/img/double.png" />
</div>
<div class="icon2 marginTop">
 	请扫描俱乐部的二维码
</div>
<div class="icon3 marginTop">
   请开启手机定位，并允许使用微信定位服务
</div>
<div class="icon4 marginTop">
  <input class="button" id="qrCode" type="button" value="开始扫码"/>
</div>
<script type="text/javascript" src="http://www.ecartoon.com.cn/js/utils.elisa.js"></script>
<script type="text/javascript"> 
var img = "${memberInfo.image == null ? 0 : memberInfo.image}";
if(img != "0" && img.indexOf('http') != -1){
	$(".userPhoto").attr("src",img);
}

(function(){
	$.ajax({
		url: "ewechatwx!sign.asp",
		Type:"post",
		data:{"url":location.href.split("#")[0]},
		dataType:"json",
		sync:false,
		success:function(sign){
			//微信sdk配置
			wx.config({
			    debug: false, 
			    appId: sign.appid, 
			    timestamp: sign.timestamp, 
			    nonceStr: sign.nonceStr, 
			    signature: sign.signature,
			    jsApiList: [        
			       "getLocation",   
			       "openLocation",  
			       "chooseImage",   
			       "uploadImage",   
			       "scanQRCode",     
			       "checkJsApi",
			       "chooseWXPay"
			    ] 
			});
		}
	});
	var qrCode = document.getElementById("qrCode");
	qrCode.onclick = function(){
	  wx.getLocation({
	    success: function (resx) {
			wx.scanQRCode({
			    needResult: 1, 
			    scanType: ["qrCode","barCode"], 
			    success: function (res) {
	    			var result = res.resultStr;
	    			var urls = result.split("&");
	    			for(var i = 0; i < urls.length; i++){
	    				if (urls[i].indexOf("id") != -1){
	    					result = urls[i].split("=")[1];
	    				}
	    			}
	    			location.href="esignwx!findOrder.asp?url="+result+"&signLat="+resx.latitude+"&signLng="+resx.longitude;
			    }
			});
	    },
	    cancel: function (res) {
	        alert('扫码签到，需获取您的地理位置信息');
	    }
	  });
	}
})();

wxUtils.sign("ewechatwx!sign.asp");
wx.ready(function(){
	wxUtils.share({
		title : "健身E卡通—扫码签到",
		link : "<%=basePath%>esignwx!saoma.asp"+ location.search,
		img : "<%=basePath%>img/shareLogo.png",
		desc : "一卡在手，全城都有。健身E卡通为您打造健康生命第三空间……"
	});
});
</script>
</body>
</html>