<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>健身E卡通首页</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="apple-itunes-app" content="app-id=1218667055" />
<meta name="baidu-site-verification" content="G2lGaZihDf" />
<meta name="keywords" content="健身E卡通 -首页介绍—下载APP" />
<meta name="description" content="健身E卡通官网，销售健身卡，健身计划，找健身房,健身俱乐部上健身E卡通" />
<link rel="shortcut icon" href="ecartoon-weixin/img/ecartoonIcon.png" type="image/x-icon"/> 
<script src="js/jquery.min.js"></script>
<style type="text/css">
	html,body{
		margin: 0;
		padding: 0;
		width: 100%;
		height: 100%;
	}
	
	img{
		width: 100%;
		height: auto;
	}
	
	.banner{
		position: relative;
		height: 362px;
	}
	
	.nav{
		position: absolute;
		top: 0;
		left: 0;
		width: 98%;
		padding: 10px 1%;
		background-color: rgba(0,0,0,0.5);
		overflow: hidden;
		font-size: 14px;
	}
	
	.mobilephone{
		float: left;
		color: #FFF;
	}
	
	.nav-content{
		float: right;
	}
	
	.link{
		color: #FFF;
		text-decoration: none;
	}
	
	.link:hover{
		color: red;
	}
	
	.cards{
		margin: 0 auto;
		width: 1000px;
	}
	
	.cards-title{
		text-align: center;
	}
	
	.cards-title-1{
		font-size: 24px;
	}
	
	.cards-title-2{
		font-size: 14px;
	}
	
	.cards-content{
		margin-top: 40px;
	}
	
	.cards-content-row{
		margin-left: 20px;
		margin-bottom: 10px;
		overflow: hidden;
	}
	
	.card{
		float: left;
		position: relative;
		margin-left: 10px;
		width: 230px;
		height: 230px;
		background-color: #FBFBFB;
		border: 1px solid #D6D6D6;
		text-align: center;
	}
	
	.card:hover{
		background-color: #FFF;
		border: 1px solid red;
		cursor: pointer;
	}
	
	.card:hover  .card-button{
		color: #FFF;
		background-color: red;
	}
	
	.card-qrcode-image{
		margin: 0 auto;
		width: 230px;
		height: 230px;
	}
	
	.card-image{
		margin: 10px auto;
		width: 44px;
		height: 44px;
	}
	
	.card-name{
		font-size: 20px;
	}
	
	.card-remark{
		padding: 10px;
		text-align: left;
		font-size: 13px;
		color: #BBB;
	}
	
	.card-button{
		position: absolute;
		bottom: 10px;
		left: 32%;
		width: 80px;
		height: 20px;
		line-height: 20px;
		font-size: 12px;
		color: red;
		border: 1px solid red;
		border-radius: 5px;
		cursor: pointer;
	}
	
	.cards2{
		background-color: #F0F0F2;
	}
	
	.cards2-block{
		margin: 0 auto;
		padding: 10px 0;
		width: 1020px;
	}
	
	.cards2-title{
		text-align: center;
	}
	
	.cards2-title-1{
		font-size: 24px;
	}
	
	.cards2-title-2{
		font-size: 14px;
	}
	
	.cards2-content{
		margin-top: 40px;
	}
	
	.cards2-content-row{
		display: flex;
		justify-content: space-between;
		padding-bottom: 10px;
	}
	
	.card2{
		position: relative;
		width: 200px;
		height: 320px;
		background-color: #FBFBFB;
		text-align: center;
	}
	
	.card2-image{
		position: absolute;
		bottom: 10px;
		left: 28%;
		margin: 10px auto;
		width: 100px;
		height: 100px;
	}
	
	.card2-name{
		margin-top: 40px;
		font-size: 20px;
	}
	
	.card2-remark{
		margin-top: 20px;
		padding: 10px 30px;
		text-align: left;
		font-size: 13px;
		color: #BBB;
	}
	
	.cards3{
		position: relative;
		height: 362px;
		text-align: center;
		color: #FFF;
	}
	
	.card3-block{
		position: absolute;
		top: 10px;
		left: 0;
		width: 100%;
	}
	
	.card3-block2{
		margin: 0 auto;
		width: 1000px;
	}
	
	.cards3-title{
		margin-bottom: 10px;
	}
	
	.cards3-title-1{
		font-size: 24px;
	}
	
	.cards3-title-2{
		font-size: 14px;
	}
	
	.cards3-content{
		background-color: transparent;
	}
	
	.cards3-content-row{
		display: flex;
		justify-content: space-between;
		margin-top: 5%;
	}
	
	.card3{
		position: relative;
		width: 200px;
		background-color: #FBFBFB;
		text-align: center;
		background-color: transparent;
	}
	
	.card3-image{
		margin: 10px auto;
		width: 65px;
		height: 65px;
	}
	
	.card3-name{
		background-color: transparent;
	}
	
	.footer{
		margin-top: 20px;
		padding: 20px;
		overflow: hidden;
		background-color: #313131;
	}
	
	.footer-left{
		float: left;
		margin-left: 40px;
		color: #FFF;
	}
	
	.beian{
		color: #FFF;
		text-decoration: none;
	}
	
	.beian:hover{
		color: red;
	}
	
	.footer-right{
		float: right;
		color: #FFF;
	}
	
	.qrcodes{
		overflow: hidden;
	}
	
	.qrcode{
		float: left;
		margin-left: 20px;
	}
	
	.qrcode-img{
		margin: 0 auto;
		width: 115.2px;
		height: 115.2px;
	}
</style>
</head>
<body>
	<div class="wraper" id="wraper">
		<div class="banner">
			<img src="index-img/banner.png" class="banner-img"/>
			<div class="nav">
				<div class="mobilephone">
					联系电话: &nbsp;&nbsp;
					<span style="color:red;">13908653155</span>&nbsp;&nbsp;
					<span style="color:red;">18171294806</span>
				</div>
				<div class="nav-content">
					<a href="index.asp" class="link">首页</a> |
					<a href="login.asp" class="link">用户中心</a> |
					<a href="login.asp" class="link">登录注册</a>
				</div>
			</div>
		</div>
		<div class="cards">
			<div class="cards-title">
				<p class="cards-title-1">专业定制运动健身行业微信小程序</p>
				<p class="cards-title-2">适用健身俱乐部、瑜伽馆、健身工作室</p>
			</div>
			<div class="cards-content">
				<div class="cards-content-row">
					<div class="card">
						<div class="card-qrcode-image" hidden="hidden">
							<img src="index-img/gh_531298c75c4e_258.png">
						</div>
						<div class="card-content">
							<div class="card-image">
								<img src="index-img/dollarcoins.png">
							</div>
							<div class="card-name">在线售卡</div>
							<div class="card-remark">
								健身俱乐部发布健身卡，设定适用店面、开卡日期、约定双方权利义务。
								会员在小程序内使用微信支付购卡。简化交易流程，节约人力成本。
							</div>
							<div class="card-button">了解更多</div>
						</div>
					</div>
					<div class="card">
						<div class="card-qrcode-image" hidden="hidden">
							<img src="index-img/gh_531298c75c4e_258.png">
						</div>
						<div class="card-content">
							<div class="card-image">
								<img src="index-img/calendar2.png">
							</div>
							<div class="card-name">课程预约</div>
							<div class="card-remark">
								提供强大的团体课表编辑功能。周期课程设定极大提高课表制作效率。可以针对会员、
								非会员设置不同的购买/预约方式。实现客流量精细化运营。
							</div>
							<div class="card-button">了解更多</div>
						</div>
					</div>
					<div class="card">
						<div class="card-qrcode-image" hidden="hidden">
							<img src="index-img/gh_531298c75c4e_258.png">
						</div>
						<div class="card-content">
							<div class="card-image">
								<img src="index-img/awardribbon2.png">
							</div>
							<div class="card-name">健身挑战</div>
							<div class="card-remark">
								可以发布健身次数、体重管理等健身挑战活动，为会员树立明确的健身目标 ，将线下健身运动与线上会员运营紧密结合，增加会员粘性。
							</div>
							<div class="card-button">了解更多</div>
						</div>
					</div>
					<div class="card">
						<div class="card-qrcode-image" hidden="hidden">
							<img src="index-img/gh_531298c75c4e_258.png">
						</div>
						<div class="card-content">
							<div class="card-image">
								<img src="index-img/salesign.png">
							</div>
							<div class="card-name">网络营销</div>
							<div class="card-remark">
								提供“砍价活动，送人健康” 等线上营销工具，利用微信强大的社交传播功能性和无缝衔接
								分享优势，让员工和会员有机会参与销售，开展全员销售。
							</div>
							<div class="card-button">了解更多</div>
						</div>
					</div>
				</div>
				<div class="cards-content-row">
					<div class="card">
						<div class="card-qrcode-image" hidden="hidden">
							<img src="index-img/gh_531298c75c4e_258.png">
						</div>
						<div class="card-content">
							<div class="card-image">
								<img src="index-img/dollar.png">
							</div>
							<div class="card-name">领券购卡</div>
							<div class="card-remark">
								健身俱乐部根据需求发布优惠券，设定券的适用店面、满减金额、使用有效期等条件。
								用户获得的优惠券能够转赠给微信好友或分享到微信群。
							</div>
							<div class="card-button">了解更多</div>
						</div>
					</div>
					<div class="card">
						<div class="card-qrcode-image" hidden="hidden">
							<img src="index-img/gh_531298c75c4e_258.png">
						</div>
						<div class="card-content">
							<div class="card-image">
								<img src="index-img/v-card3.png">
							</div>
							<div class="card-name">签到排行</div>
							<div class="card-remark">
								会员使用小程序打卡，健身俱乐部可设置签到/签出的时间间隔。根据会员打卡数据自动
								生成健身次数排行榜，激发会员的健身动力。
							</div>
							<div class="card-button">了解更多</div>
						</div>
					</div>
					<div class="card">
						<div class="card-qrcode-image" hidden="hidden">
							<img src="index-img/gh_531298c75c4e_258.png">
						</div>
						<div class="card-content">
							<div class="card-image">
								<img src="index-img/mappin2.png">
							</div>
							<div class="card-name">会员足迹</div>
							<div class="card-remark">
								记录会员每次健身历程，会员可以对健身俱乐部的环境、服务、设施进行点评。为俱乐部收集用户意见，改进服务质量。
							</div>
							<div class="card-button">了解更多</div>
						</div>
					</div>
					<div class="card">
						<div class="card-qrcode-image" hidden="hidden">
							<img src="index-img/gh_531298c75c4e_258.png">
						</div>
						<div class="card-content">
							<div class="card-image">
								<img src="index-img/settings.png">
							</div>
							<div class="card-name">私人定制</div>
							<div class="card-remark">
								根据会员的身体数据自动生成四周健身计划，为会员提供基础健身指导，提高会员科学健身意识。
							</div>
							<div class="card-button">了解更多</div> 
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="cards2">
			<div class="cards2-block">
				<div class="cards2-title">
					<p class="cards2-title-1">微信小程序开启健身网络营销新时代</p>
					<p class="cards2-title-2">无需安装 ，入口丰富 ，共享微信海量用户数据</p>
				</div>
				<div class="cards2-content">
					<div class="cards2-content-row">
						<div class="card2">
							<div class="card2-name">速度快</div>
							<div class="card2-remark">
								无需下载安装，加载速度快，微信登录随时可用。
							</div>
							<div class="card2-image">
								<img src="index-img/rocket.png">
							</div>
						</div>
						<div class="card2">
							<div class="card2-name">成本低</div>
							<div class="card2-remark">
								一次开发，多端兼容，免除了对各种手机机型的适配。
							</div>
							<div class="card2-image">
								<img src="index-img/dollarbag.png">
							</div>
						</div>
						<div class="card2">
							<div class="card2-name">体验好</div>
							<div class="card2-remark">
								提供近乎原生APP的操作体验和流畅度。
							</div>
							<div class="card2-image">
								<img src="index-img/iphone.png">
							</div>
						</div>
						<div class="card2">
							<div class="card2-name">易获取</div>
							<div class="card2-remark">
								支持附近查找、扫码、微信搜索、公众号、好友推荐等50多个入口。
							</div>
							<div class="card2-image">
								<img src="index-img/xcx_05.png">
							</div>
						</div>
						<div class="card2">
							<div class="card2-name">玩法多</div>
							<div class="card2-remark">
								提供丰富的线上营销、服务功能。
							</div>
							<div class="card2-image">
								<img src="index-img/pacman.png">
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="cards3">
			<img src="index-img/wp-bg.png" class="card3-img">
			<div class="card3-block">
				<div class="card3-block2">
					<div class="cards3-title">
						<p class="cards3-title-1">微信小程序强大的线上、线下连接能力</p>
						<p class="cards3-title-2">微信不断增加小程序能力，更多入口持续开放</p>
					</div>
					<div class="cards3-content">
						<div class="cards3-content-row">
							<div class="card3">
								<div class="card3-image">
									<img src="index-img/main3-icon1.png">
								</div>
								<p class="card3-name">附近的小程序</p>
							</div>
							<div class="card3">
								<div class="card3-image">
									<img src="index-img/main3-icon2.png">
								</div>
								<p class="card3-name">微信搜索</p>
							</div>
							<div class="card3">
								<div class="card3-image">
									<img src="index-img/main3-icon3.png">
								</div>
								<p class="card3-name">扫码识别</p>
							</div>
							<div class="card3">
								<div class="card3-image">
									<img src="index-img/main3-icon5.png">
								</div>
								<p class="card3-name">微信主页置顶</p>
							</div>
							<div class="card3">
								<div class="card3-image">
									<img src="index-img/main3-icon4.png">
								</div>
								<p class="card3-name">公众号关联</p>
							</div>
						</div>
						<div class="cards3-content-row">
							<div class="card3">
								<div class="card3-image">
									<img src="index-img/main3-icon6.png">
								</div>
								<p class="card3-name">微信【发现】栏</p>
							</div>
							<div class="card3">
								<div class="card3-image">
									<img src="index-img/xcx_01.png">
								</div>
								<p class="card3-name">使用过的小程序</p>
							</div>
							<div class="card3">
								<div class="card3-image">
									<img src="index-img/main3-icon8.png">
								</div>
								<p class="card3-name">好友分享</p>
							</div>
							<div class="card3">
								<div class="card3-image">
									<img src="index-img/main3-icon9.png">
								</div>
								<p class="card3-name">微信首页置顶</p>
							</div>
							<div class="card3">
								<div class="card3-image">
									<img src="index-img/android.png">
								</div>
								<p class="card3-name">安卓桌面图标</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="footer">
			<div class="footer-left">
				<p>联系我们</p>
				<div style="margin-left: 60px;">
					<div>
						联系电话 : <br/>13908653155 &nbsp;&nbsp;18171294806
					</div>
					<div>
						联系QQ : <br/>917981712 &nbsp;&nbsp;421247365				
					</div>
					<div>
						客服微信: <br/>love-fit
					</div>
				</div>
				<p>
					<a href="http://www.miibeian.gov.cn" class="beian">
						鄂ICP备08004430号
					</a>
				</p>
			</div>
			<div class="footer-right">
				<p style="padding-left: 60px;">关注我们</p>
				<div class="qrcodes">
					<div class="qrcode">
						<div class="qrcode-img">
							<img src="index-img/qrcode_JSZG.png">
						</div>
						<p style="font-size: 14px;">关注健身中国微信公众号</p>
					</div>		
					<div class="qrcode">
						<div class="qrcode-img">
							<img src="index-img/gh_531298c75c4e_258.png">
						</div>
						<p style="font-size: 14px;">体验健身俱乐部小程序</p>
					</div>			
				</div>
			</div>
		</div>
	</div>
	<script>
		setTimeout( e => {
			$(".banner").css("height", $(".banner-img").height() + "px");
			$(".cards3").css("height", $(".card3-img").height() + "px");
		}, 100);
		

		var index = 0;
		(function () {
			$(".card-button").click( e => {
				
				$(".card-qrcode-image").eq(window.index).hide();
				$(".card-button").eq(window.index).parent().show();
				
				var index = $(".card-button").index($(e.target));
				$(e.target).parent().hide();
				$(".card-qrcode-image").eq(index).show();
				window.index = index;
			});
		})(); 

	</script>
</body>
</html>