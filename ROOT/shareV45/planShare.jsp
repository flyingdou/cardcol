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
<!-- <script type="text/javascript">
	var cnzz_protocol = (("https:" == document.location.protocol) ? " https://"
			: " http://");
	document
			.write(unescape("%3Cspan id='cnzz_stat_icon_1262657827'%3E%3C/span%3E%3Cscript src='"
					+ cnzz_protocol
					+ "s19.cnzz.com/stat.php%3Fid%3D1262657827' type='text/javascript'%3E%3C/script%3E"));
</script> -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/share45/css/demo2.css"/>
<title>健身计划分享</title>
<style type="text/css">
.divImg {
	width: 100%;
	height: 170px;
	overflow: hidden;
}

#cnzz_stat_icon_1262657827 {
	display: none
}

.wxtip {
	background: rgba(0, 0, 0, 0.8);
	text-align: center;
	position: fixed;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	z-index: 998;
	display: none;
}

.wxtip-icon {
	width: 52px;
	height: 67px;
	background: url(weixin-tip.png) no-repeat;
	display: block;
	position: absolute;
	right: 20px;
	top: 20px;
}

.wxtip-txt {
	margin-top: 107px;
	color: #fff;
	font-size: 16px;
	line-height: 1.5;
}

.button {
	display: block;
	border-radius: 8px;
	padding: 10px;
	text-align: center;
	width: 100%;
	position : fixed;
	bottom:0;
	margin: 0 auto;
	position: absolute;
	font-size: 16px;
	color: #ffffff;
	background: url("shareV45/img/botton.png");
	border:0;
}
</style>
</head>
<body style="overflow:hidden;">
	<div data-role="page"  id="course" >
		<div data-role="header" data-position="fixed" class="headerCss" data-tap-toggle="false">
			<a href="#"
				class="ui-btn ui-btn-left ui-corner-all ui-shadow ui-nodisc-icon ui-btn-icon-left headerLogo">
				<img src="shareV45/img/cardcol-logo.png"
				alt="" width="88" />
			</a>
			<h1></h1>
			<a href="javascript:downLoadAPP()"
				class="ui-btn ui-corner-all ui-shadow  ui-btn-icon-left downLoadApp download">立即下载APP</a>
		</div>
		<div data-role="main" class="ui-content main .ui-page-theme-f">
			<div class="divImg">
				<img src="images/banner.png" alt="" width="100%"
					class="mainImgClass blur" />
			</div>
			<ul data-role="listview" data-inset="true"
				class="listW noshadow noborder ulPosition">
				<li data-icon="false" class="noborder"><a href="#"
					class="noborder bg0"> <img src="images/banner.png"
						class="BodybuildingCss">
						<h2 class="h2Css">{{plan.name}}</span>
						</h2>
						<p class="pCss">
							下载数量: <span>{{plan.goodsCount == null ? 0 : plan.goodsCount}}</span>
						</p>
						<p class="pCss">
							发布人: <span>{{plan.memberName}}</span>
						</p>
				</a></li>
			</ul>
			<div class="fix"></div>
			<ul data-role="listview" data-inset="true" class="listW noshadow noborder marginD">
				<li data-icon="false" class="noborder padd5">
					<p class="h2Css1 left">
						<span>计划类型</span>
					</p>
					<p class="pCSS right">{{plan.planType}}</p>
					<div class="fix"></div>
				</li>
				<div class="underL"></div>
				<li class="noborder padd5">
					<p class="h2Css1 left">
						<span>适用对象</span>
					</p>
					<p class="pCSS right">{{plan.applyObject}}</p>
					<div class="fix"></div>
				</li>
				<div class="underL"></div>
				<li class="noborder padd5">
					<p class="h2Css1 left">
						<span>适用场景</span>
					</p>
					<p class="pCSS right">{{plan.scene}}</p>
					<div class="fix"></div>
				</li>
				<div class="underL"></div>
				<li class="noborder padd5">
					<p class="h2Css1 left">
						<span>所需器材</span>
					</p>
					<p class="pCSS right">{{plan.apparatuses}}</p>
					<div class="fix"></div>
				</li>
				<div class="underL"></div>
				<li class="noborder padd5">
					<p class="h2Css1 left">
						<span>计划周期</span>
					</p>
					<p class="pCSS right">{{plan.plancircle == null ? 0 : plan.plancircle}}天</p>
					<div class="fix"></div>
				</li>
				<div class="underL"></div>
				<li class="noborder padd5">
					<p class="h2Css1 left">
						<span>价格</span>
					</p>
					<p class="pCSS right">￥{{plan.price == null ? 0 : plan.price}}</p>
					<div class="fix"></div>
				</li>
				<li class="noborder padd5" style="margin-top:10px;position: relative;height:30px;">
					<div class="left" style="width:100%;">
						<span>计划简介</span>
					</div>
					<div class="" style="width:100%;height:100px;position: absolute;top:25px;color:#999;font-size:12px;">
						 <span style="margin-left:5px;">{{plan.summary == '' || plan.summary == null ? '暂无简介' : plan.summary}}</span>
					</div>
				</li>
			</ul>
		</div>
		<!-- <div data-role="footer" data-position="fixed" class="footerCss"
			data-tap-toggle="false">
			<a href="" class="footerA" id="thisbtn2" id="thisbtn2">下载卡库健身APP，获取健身方案</a>
		</div> -->
	</div>
	<input type="button" class="button download" value="下载卡库健身APP()" onclick="downLoadAPP"/>
	<div class="wxtip" id="JweixinTip">
		<span class="wxtip-icon"></span>
		<p class="wxtip-txt">
			点击右上角<br />选择在浏览器中打开
		</p>
	</div>
	
<script src="shareV45/js/vue.min.js"></script>
<script src="shareV45/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript">
var data = ${plan == null ? 0 : plan};
var member = ${member == null ? 0 : member.id}
var bool = 0;
if(data.member == member){
	bool = 1;
}
var v = new Vue({
	el:"#course",
	data:{
		plan:data.item,
		isShow:bool
	}
});

//载入函数
$(function(){
	var data = ${plan == null ? 0 : plan};
	var dd = data.item;
	if(dd != 0){
		var planType = $("#planType");
		if(dd.planType = 'A'){
		    planType.val('A');
		    planType.html("瘦身减重");
		    v.plan.planType = "瘦身减重";
		}else if(dd.planType = 'B'){
			planType.val('B');
			planType.html("健美增肌");
			v.plan.planType = "健美增肌";
		}else if(dd.planType = 'C'){
			planType.val('C');
			planType.html("运动康复");
			v.plan.planType = "运动康复";
		}else if(dd.planType = 'D'){
			planType.val('D');
			planType.html("提高运动表现");
			v.plan.planType = "提高运动表现";
		}
		
		var applyObject = $("#applyObject");
		if(dd.applyObject = 'A'){
			applyObject.val('A');
			applyObject.html("初级");
			v.plan.applyObject = "初级";
		}else if(dd.applyObject = 'B'){
			applyObject.val('B');
			applyObject.html("中级");
			v.plan.applyObject = "中级";
		}else if(dd.applyObject = 'C'){
			applyObject.val('C');
			applyObject.html("高级");
			v.plan.applyObject = "高级";
		}
		
		var scene = $("#scene");
		if(dd.scene = 'A'){
			scene.val('A');
			scene.html("健身房");
			v.plan.scene = "健身房";
		}else if (dd.scene = 'B'){
			scene.val('B');
			scene.html("办公室");
			v.plan.scene = "办公室";
		}else if (dd.scene = 'C'){
			scene.val('C');
			scene.html("家庭");
			v.plan.scene = "家庭";
		}else if (dd.scene = 'D'){
			scene.val('D');
			scene.html("户外");
			v.plan.scene = "户外";
		}
	}
});
</script>
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
 }else{
	 /* location.href = "http://www.ecartoon.com.cn/app/ecartoonV1.apk"; */
 }
}
var btn1 = document.getElementById('thisbtn2');//下载一
weixinTip(btn1);
/* var btn2 = document.getElementById('thisbtn1'); //下载二
weixinTip(btn2);
var btn3 = document.getElementById('thisbtn'); //下载二
weixinTip(btn3); */

$(function(){
	$(".download").click(function(){
		weixinTip(this);
	});
});

function downLoadAPP(){
	var sUserAgent = navigator.userAgent.toLowerCase();
	var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";
	var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
	var bIsAndroid = sUserAgent.match(/android/i) == "android";
	if (bIsIpad || bIsIphoneOs) {
		location.href = "http://www.ecartoon.com.cn/app/cardcol.apk";
	}else{
		location.href = "http://www.ecartoon.com.cn/app/cardcol.apk";	
	}
}
</script>	
</html>