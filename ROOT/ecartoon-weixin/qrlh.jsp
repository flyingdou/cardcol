<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<title>请人流汗</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link rel="stylesheet" href="sport/css/mui.min.css" />
<style>
.bannerBox{
	background-color:#2c2c2c;
	color:#FFF;
}

.describeBox{
	margin-bottom:10px;
	padding:10px 0;
	height:80px;
	background-color:#fff; 
	overflow: hidden;
}
.describeBox > img{
	float:left;
	width:47px;
	height: 56px;
	margin-left:10px;
}
.describeBox > div{
	float:left;
	margin-left:10px;
	width:80%;
	height:56px;
	font-size: 12px;
	color:#0a0909;
}
/*健身卡列表*/
.productListBox{
	margin:0;
	padding:10px 0;
	background-color:#fff;
}
.productListBox-li{
	overflow: hidden;
	margin:0 10px;
	margin-bottom:10px;
	height:80px;
	border-bottom:1px solid #bbb;
	list-style: none;
}

.productListBox-radio{
	float: left;
	height:70px;
	line-height: 70px;
}
.productListBox-radio > img{
	width:24px;
	height:24px;
	margin-top:23px;
}

.productListBox-img{
	float: left;
	margin-left:10px;
}
.productListBox-img > img{
	width:94px;
	height:70px;
}

.productListBox-text{
	float: left;
	margin-left:10px;
	overflow: hidden;
	widht:210px;
}
</style>
</head>
<body>
	<div class="weui-cells" style="margin-bottom: 65px" id="app">
		<div class="bannerBox">
			<img src="ecartoon-weixin/img/Sweat.png" style="width:100%;height:100%;" />
		</div>
		<div class="describeBox">
			<img src="ecartoon-weixin/img/envelopes.png" />
			<div>
				好友通过您的分享购买健身卡可享受现金折扣，<br/>
				您也能获得红包奖励 。<br/>
				请选择下面的健身卡，然后点击右上角分享给朋友 。
			</div>
		</div>
		<ul class="productListBox">
			<li class="productListBox-li" v-for="(product,i) in prodList" onclick="changeProduct(this)">
				<div class="productListBox-radio" :index="i">
					<img src="ecartoon-weixin/img/Sweat-button2.png" class="selector">
				</div>
				<div class="productListBox-img">
					<img :src="'picture/'+product.prodImage">
				</div>
				<div class="productListBox-text">
					<p style="font-size: 15px;color:#0a0909;margin-bottom:5px;">
						{{product.prodName}}
					</p>
					<p style="font-size: 12px;color:#898989;margin-bottom:5px;">
						￥{{product.prodPrice == "0" ? "0.00" : product.prodPrice.toFixed(2)}}，有效期{{product.prodPeriodMonth}}天
					</p>
					<p style="width:200px;height:21px;overflow: hidden;margin-bottom:5px;font-size: 12px;color:#898989;">
						{{product.summary == "" ? '暂无描述' : (product.summary.length > 14 ? product.summary.substring(0,14) + '...' : product.summary)}}
					</p>
				</div>
			</li>
		</ul>
	</div>
</body>
<script src="ecartoon-weixin/js/jquery.min.js"></script>
<script src="ecartoon-weixin/js/vue.min.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="js/utils.elisa.js"></script>
<script>
	//页面初始化
	var prods = {
			data:${prodInfo.prodList == null ? 0 : prodInfo.prodList},
			index:1
	};
	
	var product = new Vue({
		el:"#app",
		data:{
			prodList:prods.data
		}
	});
	$(".selector").eq(prods.index).attr("src","ecartoon-weixin/img/Sweat-button1.png");
		
	// 分享初始化
	var shareInfo = {
		title : product.prodList[prods.index].prodName,
		desc : product.prodList[prods.index].summary,
		img :'<%=basePath%>picture/'+product.prodList[prods.index].prodImage,
		link : '<%=basePath%>egamewx!pleaseSweatDeatil.asp?id='+product.prodList[prods.index].id+'&shareMember=${member.id}'
	};
	wxUtils.sign("ewechatwx!sign.asp");
	wx.ready(function(){
		wxUtils.share(shareInfo);
	});
	
	// 改变分享的商品
	function changeProduct(elem){
		$(".selector").prop("src","ecartoon-weixin/img/Sweat-button2.png");
		$(elem).find(".selector").prop("src","ecartoon-weixin/img/Sweat-button1.png");
		prods.index = $(elem).find(".productListBox-radio").attr("index");
		// 修改分享参数
		shareInfo.title = product.prodList[prods.index].prodName;
		shareInfo.desc = product.prodList[prods.index].summary;
		shareInfo.img = '<%=basePath%>picture/'+product.prodList[prods.index].prodDetailImage;
		shareInfo.link = '<%=basePath%>egamewx!pleaseSweatDeatil.asp?id='+product.prodList[prods.index].id+'&shareMember=${member.id}';
		// 重置分享
		wxUtils.share(shareInfo);
	}
	
	// 查询是否有现金红包需要提醒
	$.ajax({
		url:'egamewx!isHaveBackMoney.asp',
		type:'post',
		data:{},
		dateType:'json',
		success:function(res){
			if(res.success){
				alert(res.msg);
			}
		},
		error:function(e){
			//alert(JSON.stringify(e));
		}
	});
</script>
</html>