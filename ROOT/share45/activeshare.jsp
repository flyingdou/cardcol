<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://apps.bdimg.com/libs/jquerymobile/1.4.5/jquery.mobile-1.4.5.min.css">
    <script src="${pageContext.request.contextPath}/share45/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/share45/js/jquery.mobile-1.4.5.min.js"></script>
    <script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1262657827'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s19.cnzz.com/stat.php%3Fid%3D1262657827' type='text/javascript'%3E%3C/script%3E"));</script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/share45/css/demo2.css"/>
    <title>挑战详情</title>
    <style type="text/css">
        .divImg{
            width: 100%;
            height: 170px;
            overflow: hidden;
        }
        
        #cnzz_stat_icon_1262657827{
        display:none}
        .wxtip{background: rgba(0,0,0,0.8); text-align: center; position: fixed; left:0; top: 0; width: 100%; height: 100%; z-index: 998; display: none;}
.wxtip-icon{width: 52px; height: 67px; background: url(weixin-tip.png) no-repeat; display: block; position: absolute; right: 20px; top: 20px;}
.wxtip-txt{margin-top: 107px; color: #fff; font-size: 16px; line-height: 1.5;}

    </style>
</head>
<body>

<div data-role="page"class="pageCss ">
    <div data-role="header" data-position="fixed" class="headerCss"data-tap-toggle="false">
        <a href="#" class="ui-btn ui-btn-left ui-corner-all ui-shadow ui-nodisc-icon ui-btn-icon-left headerLogo">
            <img src="${pageContext.request.contextPath}/share45/img/logo.png" alt=""width="88"/>
        </a>
        <h1></h1>
        <a href="javascript:downLoadAPP()" class="ui-btn ui-corner-all ui-shadow  ui-btn-icon-left downLoadApp">立即下载APP</a>
    </div>

    <div data-role="main" class="ui-content main .ui-page-theme-f">
        <div class="divImg"><img src="picture/${a.image}" alt=""  width="100%" class="mainImgClass blur"/></div>
        <ul data-role="listview" data-inset="true"class="listW noshadow noborder ulPosition">
        <li  data-icon="false" class="noborder">
            <a href="#"class="noborder bg0">
                <img src="picture/${a.image}"class="BodybuildingCss" >
                <h2 class="h2Css">${a.name}</span></h2>
                <p class="pCss">发布人: <span>${a.creator.name}</span> </p>
                <p class="pCss">参加人数: <span>${size}</span> </p>
                <div class="fix"></div>
            </a>
        </li>
        </ul>

<div class="fix"></div>
        <ul data-role="listview" data-inset="true"class="listW noshadow noborder marginD" >
            <li  data-icon="false" class="noborder padd5">
                <p class="h2Css1 left"><span class="spanCssIMG">运动目标</span></p>
                <p class="pCSS right">${a.days}天${addornot}${a.value}${sport}</p>
                <div class="fix"></div>
            </li>
            <div class="underL"></div>
            <li  data-icon="false" class="noborder padd5">
                <p class="h2Css1 left"><span class="spanCssIMG1">成功奖励</span></p>
                <p class="pCSS right">${a.award}</p>
                <div class="fix"></div>
            </li>
            <div class="underL"></div>
            <li  data-icon="false" class="noborder padd5">
                <p class="h2Css1 left"><span class="spanCssIMG2">失败惩罚</span></p>
                <p class="pCSS right">向${a.institution.name}捐款${a.amerceMoney}元</p>
                <div class="fix"></div>
            </li>
            <li  data-icon="false" class="noborder marginTop10">
                <p class="h2Css1">注意事项</p>
                <p class="pCSS2 ">${a.memo}<p>
            </li>
</ul>
</div>
<div data-role="footer" data-position="fixed"class="footerCss"data-tap-toggle="false">
    <a href="javascript:downLoadAPP()" class="footerA"id="thisbtn2"id="thisbtn2">下载E卡通，获取健身方案</a>
</div>
</div>
    <div class="wxtip" id="JweixinTip">
<span class="wxtip-icon"></span>
<p class="wxtip-txt">点击右上角<br/>选择在浏览器中打开</p>
</div>
    
</body>
<script>
var ua = navigator.userAgent;
var isWeixin =  !!/MicroMessenger/i.test(ua);
function weixinTip(ele){
var ua = navigator.userAgent;
var isWeixin = !!/MicroMessenger/i.test(ua);
if(isWeixin){
	ele.onclick=function(e){
		window.event? window.event.returnValue = false : e.preventDefault();
		document.getElementById('JweixinTip').style.display='block';
	}
	document.getElementById('JweixinTip').onclick=function(){
		this.style.display='none';
	}
}
}
var btn1 = document.getElementById('thisbtn2');//下载一
weixinTip(btn1);
/* var btn2 = document.getElementById('thisbtn1'); //下载二
weixinTip(btn2);
var btn3 = document.getElementById('thisbtn'); //下载二
weixinTip(btn3); */

function downLoadAPP(){
	var sUserAgent = navigator.userAgent.toLowerCase();
	var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";
	var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
	var bIsAndroid = sUserAgent.match(/android/i) == "android";
	if (bIsIpad || bIsIphoneOs) {
		location.href = "https://itunes.apple.com/cn/app/%E5%81%A5%E8%BA%ABe%E5%8D%A1%E9%80%9A/id1218667055?mt=8";
	}else{
		location.href = "http://www.ecartoon.com.cn/app/ecartoon-V1.4.apk";	
	}
}
</script>	
</html>