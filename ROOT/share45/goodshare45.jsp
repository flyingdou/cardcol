<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="https://apps.bdimg.com/libs/jquerymobile/1.4.5/jquery.mobile-1.4.5.min.css">
<link
	href="${pageContext.request.contextPath}/share45/css/bootstrap.min.css"
	type="text/css" rel="stylesheet" />
<script
	src="${pageContext.request.contextPath}/share45/js/jquery.min.js"></script>
<script
	src="${pageContext.request.contextPath}/share45/js/jquery.mobile-1.4.5.min.js"></script>
     <script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1262657827'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s19.cnzz.com/stat.php%3Fid%3D1262657827' type='text/javascript'%3E%3C/script%3E"));</script>

<%-- <link rel="stylesheet"
	href="${pageContext.request.contextPath}/share45/css/demo1.css" /> --%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/share45/css/newcss.css" />

    <style type="text/css">

        
        #cnzz_stat_icon_1262657827{
        display:none}

  .wxtip{background: rgba(0,0,0,0.8); text-align: center; position: fixed; left:0; top: 0; width: 100%; height: 100%; z-index: 998; display: none;}
.wxtip-icon{width: 52px; height: 67px; background: url(weixin-tip.png) no-repeat; display: block; position: absolute; right: 20px; top: 20px;}
.wxtip-txt{margin-top: 107px; color: #fff; font-size: 16px; line-height: 1.5;}
    </style>

<title>私人定制</title>
</head>
<body class="bodyS">

	<div data-role="page" class="pagem" style="background: url('${pageContext.request.contextPath}/share45/img/bgggg.png') no-repeat fixed 0 49px/ 100% 100%;padding-top:-49px!important;box-sizing: border-box!important; ">
		<div data-role="header" data-position="fixed" class="headerCss"
			data-tap-toggle="false">
			<a href="#"
				class="ui-btn ui-btn-left ui-corner-all ui-shadow ui-nodisc-icon ui-btn-icon-left headerLogo"id="thisbtn2">
				<img src="${pageContext.request.contextPath}/share45/img/logo.png"
				alt="" width="88" />
			</a>
			<h1></h1>
			<a href="javascript:downLoadAPP();"
				class="ui-btn ui-corner-all ui-shadow  ui-btn-icon-left downLoadApp"id="thisbtn1">立即下载APP</a>
		</div>
		 <span class="hmm">仅<span style="font-size: 32px">￥<span style=" font-size: 56px;font-weight: 500;">${p.price}</span>
            <span>.00</span>
        </span>
        </span>

        <a href="javascript:downLoadAPP();"style="border-radius: 0"><span class="downJ"id="thisbtn">下载E卡通，定制我的计划</span></a>


    <!--轮播-->
    <div id="myCarousel" class="carousel slide" style="margin-bottom: 0px!important;border: none!important;">
         <!--轮播（Carousel）指标 -->
        <ol class="carousel-indicators"style="position: fixed;bottom: 0">
            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
            <li data-target="#myCarousel" data-slide-to="1"></li>

        </ol>
        <!-- 轮播（Carousel）项目 -->

 <div class="carousel-inner backgroundo">

            <div style="margin: 10px 10px;overflow: hidden;box-sizing: border-box;position: absolute">
                <span style="display: block;float: left"><img src="picture/${p.image1}" alt=""width="40"height="40"/></span>
                <p class="nameWy">王严</p>
            </div>
            <div class="item  active" style="position: absolute;margin-top: 50px">
                <div class="itemA">
                <div class="fix"></div>
                <div class="sbzj">
                    <span class="font16A textshadowNo">专家介绍:</span>
                    <span class="font12  textshadowNo">${p.summary}</span>
                </div>
            </div>
            </div>
            <div class="item"style="position: absolute;margin-top: 50px">
                <div class="itemA">

                    <div class="fix"style="clear: both">
                    </div>
                    <div class="wWork">
                        <div class="wWork1 textshadowNo">专家介绍:</div>
                        <div class="jieshao textshadowNo">计划类型: ${gtype}<br> 
								适用对象: ${gapply}<br> 
								适用场景: ${gscene}<br>
								所需器材: ${p.apparatuses}<br> 
								计划周期: ${p.plancircle}<br></div>
                    </div>
                </div>

            </div>
        </div>



    </div>
    <!-- 轮播（Carousel）导航 -->




</div>

		
<%-- 
 	<div data-role="main" class="ui-content main .ui-page-theme-f"
			class="mainA">
			<img src="${pageContext.request.contextPath}/share45/img/bgggg.png"
				alt="" height="100%" class="bgggg" /> <span class="hmm">仅<span style="font-size: 32px">￥<span style=" font-size: 56px;font-weight: 500;">${p.price}</span>
			</span></span> <span class="downJ">下载E卡通，定制我的计划</span>

		</div>
		<!--轮播-->
		<div id="myCarousel" class="carousel slide ">
			<!-- 轮播（Carousel）指标 -->
			<ol class="carousel-indicators" style="position: fixed">
				<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
				<li data-target="#myCarousel" data-slide-to="1"></li>
			</ol> 
			<!-- 轮播（Carousel）项目 -->
			<div class="carousel-inner backgroundo">
				<div class="item active">
					<div class="itemA">
						<span style="display: block; float: left"><img
							src="picture/${p.image1}" alt="" width="40" height="40" /></span>
						<p class="nameWy">王严</p>
						<div class="fix"></div>
						<div class="sbzj">
							<span class="font16A">专家介绍:</span> <span class="font12">${p.summary}</span>
						</div>
					</div>
				</div>
				<div class="item">
					<div class="itemA">
						<span style="display: block; float: left"> <img
							src="picture/${p.image1}"
							alt="" width="40" height="40" />
						</span>
						<p class="nameWy">王严</p>
						<div class="fix" style="clear: both"></div>

						<div class="wWork">
							<div class="wWork1">产品简介：</div>
							<div class="jieshao">
								计划类型: ${gtype}<br> 
								适用对象: ${gapply}<br> 
								适用场景: ${gscene}<br>
								所需器材: ${p.apparatuses}<br> 
								计划周期: ${p.plancircle}<br>
							</div>
						</div>
					</div>
				</div>
			</div> --%>

<!-- 
		</div>
		轮播（Carousel）导航

	</div>

	</div> -->

    <div class="wxtip" id="JweixinTip">
<span class="wxtip-icon"></span>
<p class="wxtip-txt">点击右上角<br/>选择在浏览器中打开</p>
</div>


</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/share45/js/bootstrap.min.js"></script>
<script>
	$(function() {
		$("#myCarousel").carousel('cycle');
	})
	
	
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
var btn2 = document.getElementById('thisbtn1'); //下载二
weixinTip(btn2);
var btn3 = document.getElementById('thisbtn'); //下载二
weixinTip(btn3);

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