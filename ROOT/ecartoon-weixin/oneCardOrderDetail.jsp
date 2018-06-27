<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":"+ request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>订单详情</title>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes">
<style>
	html,body{
		width:100%;
		height:100%;
		margin:0;
		padding:0;
	}
	
	.wraper{
		width:100%;
		height:100%;
		background-color: #f2f2f2;
	}
	
	ul,li,p{
		margin:0;
		padding:0;
		list-style:none;
	}
	
	/*E卡通图*/
	.oneCardBox{
		background-color: #fff;
		padding:10px 0;
	}
	
	.oneCardImg{
		width:90%;
		height:150px;
		margin:0 auto;
		background:url("ecartoon-weixin/img/cartoon-background@2x.png");
		background-size:100% 100%;
		border-radius:5px;
		color:#fff;
	}
	
	.cardName{
		padding-top:10px;
		padding-left:20px;
	}
	
	.cardPrice{
		padding-top:60px;
		padding-left:20px;
	}
	
	.cardNo{
		padding-top:10px;
		padding-left:20px;
		font-size:13px;
	}
	
	/*开卡时间, 出勤次数, 到期时间 */
	.cardDetailBox{
		background-color: #fff;
		padding:10px 0; 
	}
	
	.cardDetailBox li{
		overflow: hidden;
		margin-bottom:10px;
	}
	
	.div-table{
		width:33.3%;
		float:left;
		text-align: center;
	}
	
	.ft12{
		font-size: 12px;
	}
	
	.cl4401{
		color:#ff4401;
		font-weight: bold;
	}
	
	/*适用门店, 健身卡详情*/
	.applicationStoreBox{
		background-color: #fff;
	}
	
	.applicationStoreBox li{
		overflow: hidden;
		width:90%;
		margin:0 auto;
		border-top:1px solid #dfdfdf;
	}
	
	.div-cell{
		float:left;
		width:50%;
		height:40px;
		line-height:40px;
		font-size:13px;
	}
	
	.text-right{
		text-align: right;
		font-size:12px;
		color:#bbb
	}
	
	.icon-more{
		width:24px;
		height:24px;
		position: absolute;
		top:8px;
		right:0;
	}
	
	/*提示信息*/
	.message{
		text-align: center;
		padding: 10px 0;		
	}
	.message-img{
		width:40px;
		height:40px;
	}
	.message-text{
		font-size:13px;
		color:#a0a0a0;
		margin-top:5px;
	}
</style>
</head>
<body>
	<div class="wraper" id="orderDetail">
		<div class="oneCardBox">
			<div class="oneCardImg">
				<p class="cardName">{{cardDetail.PROD_NAME}}<p>
				<p class="cardPrice">￥{{cardDetail.PROD_PRICE == "0" ? "0" : cardDetail.PROD_PRICE.toFixed(2)}}</p>
				<p class="cardNo">订单编号 {{cardDetail.no}}</p>
			</div>
		</div>
		<div class="cardDetailBox">
			<ul>
				<li class="ft12">
					<div class="div-table">开卡时间</div>
					<div class="div-table">出勤次数</div>
					<div class="div-table">到期时间</div>
				</li>
				<li class="cl4401">
					<div class="div-table">{{cardDetail.orderStartTime}}</div>
					<div class="div-table">{{cardDetail.signNum}}</div>
					<div class="div-table">{{cardDetail.END_DATE}}</div>
				</li>
			</ul>
		</div>
		<div class="applicationStoreBox">
			<ul>
				<li>
					<div class="div-cell">适用店面</div>
					<div class="div-cell text-right" style="position: relative;" onclick="linked(0)">
						<span style="margin-right:25px;">{{cardDetail.count}}家</span>
						<img src="ecartoon-weixin/img/more.png" class="icon-more">
					</div>
				</li>
				<li>
					<div class="div-cell">健身卡详情</div>
					<div class="div-cell text-right" style="position: relative;" onclick="linked(1)">
						<img src="ecartoon-weixin/img/more.png" class="icon-more">
					</div>
				</li>
			</ul>
		</div>
		<div class="message">
			<img :src="message_img" class="message-img" />
			<p class="message-text">{{message_text}}</p>
		</div>
	</div>
	<script src="ecartoon-weixin/js/vue.min.js"></script>
	<script src="js/utils.elisa.js"></script>
	<script type="text/javascript">
		var vue = new Vue({
			el:"#orderDetail",
			data:{
				cardDetail:${orderDetail.oneCardOrderDetail},
				message_img:"ecartoon-weixin/img/Look-happy@2x.png",
				message_text:"您有很好的运动习惯,请继续保持!"
			},
			mounted:function(){
				var newDate = new Date();
				var days = DateDiff(newDate.format(),this.cardDetail.orderStartTime);
				var conclusion = this.cardDetail.signNum/days;
				if(conclusion >= (days * 0.3)){
					this.message_img = "ecartoon-weixin/img/Look-happy@2x.png";
					this.message_text = "您有很好的运动习惯，请继续保持！";
				}else if(conclusion <= (days * 0.3) && conclusion > 0){
					this.message_img = "ecartoon-weixin/img/Look-not-happy@2x.png";
					this.message_text = "您的健身频率偏低，要坚持运动哦！";
				}else if(conclusion <= 0){
					this.message_img = "ecartoon-weixin/img/Look-not-happy@2x.png";
					this.message_text = "请尽快开始您的健身之旅！";
				}
			}
		});
		
		// 跳转链接
		function linked(type){
			if(type == 0){
				location.href="ecartoon-weixin/mdfblocation.jsp?id="+vue.cardDetail.id;
			}else if(type == 1){
				location.href="ecartoon-weixin/prod_content.jsp?jsons="+encodeURI(JSON.stringify(vue.cardDetail));
			}
		}
	</script>
</body>
</html>