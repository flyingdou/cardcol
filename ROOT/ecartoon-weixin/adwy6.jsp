<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":"+ request.getServerPort() + path + "/";
%>

<!doctype html>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title>广告页</title>
<script type="text/javascript" src="ecartoon-weixin/js/vue.min.js"></script>
<script type="text/javascript" src="ecartoon-weixin/js/jquery.min.js"></script>

<style type="text/css">
	html,body{
		width: 100%;
		height: 100%;
		margin: 0;
		padding: 0;
	}
	
	.top{
		padding: 10px;
		overflow: hidden;
	}
	
	.left{
		float: left;
	}
	
	.left > img{
		width: 81px;
		height: 81px;
	}
	
	.right{
		position: relative;
		float: left;
		margin-left: 10px;
		width: 72%;
		height: 81px;
		font-size: 16px;
		color: #060606;
	}
	
	.right-content{
		position: absolute;
		bottom: 0;
		left: 0;
		display: flex;
		justify-content: space-between;
		align-items: center;
		width: 100%;
		font-size: 10px;
		color: #00A2EA;
	}
	
	.right-bolck{
		width: 60px;
		height: 20px;
		text-align: center;
		line-height: 20px;
		border: 1px solid #00A2EA;
	}
	
	.right-content img{
		margin-top: 3px;
		width: 23px;
		height: 23px;
	}
	
	.middle{
		position: relative;
		height: 299.5px;
	}
	
	.middle > img{
		width: 100%;
		height: 100%;
	}
	
	.middle-content{
		position: absolute;
		top: 20px;
		left: 20px;
		font-size: 12px;
		color: #FFF;
	}
	
	.middle-content > div{
		margin-bottom: 5px;
	}
	
	.bottom{
		padding: 10px;
	}
	
	.bottom-top{
		overflow: hidden;
	}
	
	.bottom-top-left{
		float: left;
	}
	
	.bottom-top-left > img{
		width: 100px;
		height; 70px;
	}
	
	.bottom-top-right{
		float: left;
		margin-left: 10px;
	}
	
	.bottom-bottom{
		margin-top: 10px;
		overflow: hidden;
	}
	
	.bottom-top-left{
		float: left;
		font-size: 12px;
		color: #676666;
	}
	
	.bottom-top-right{
		float: left;
		margin-left: 15px;
	}
	
	.bottom-top-right > img{
		width: 136px;
		heigth: 136px;
	}
	
	.bottom-bottom-right{
		display: flex;
		justify-content: center;
		align-items: center;
		width: 50%;
		height: 136px;
	}
</style>
</head>
<body>
	<div class="top">
		<div class="left">
			<img src="https://www.ecartoon.com.cn/picture/201806141705.png">
		</div>
		<div class="right">
			<div>&nbsp;&nbsp;王严健身专家系统</div>
			<div class="right-content">
				<div>
					<img src="https://www.ecartoon.com.cn/picture/201806141707.png">
				</div>
				<div class="right-bolck">
					增肌计划
				</div>
				<div class="right-bolck">
					减脂计划
				</div>
				<div class="right-bolck">
					体能训练
				</div>
			</div>
		</div>
	</div>
	<div class="middle">
		<img src="https://www.ecartoon.com.cn/picture/201806141703.png">
		<div class="middle-content">
			<div>适用对象:初级,中级</div>
			<div>计划周期:4周</div>
			<div>适用场景:健身房</div>
			<div>所需器材:各种力量训练器材</div>
		</div>
	</div>	
	<div class="bottom">
		<div class="bottom-top">
			<div class="bottom-top-left">
				<img src="https://www.ecartoon.com.cn/picture/201806141706.png">
			</div>
			<div class="bottom-top-right">
				<div style="font-size: 15px;color: #060606;">私人订制健身计划</div>
				<div style="font-size: 12px;color: #989696;margin-top: 5px;">
					&nbsp;&nbsp;&nbsp;&nbsp;知名健身专家王严多年研究成果开发，</br>
					根据您的健身目标、身体数据自动生成个</br>
					性健身计划。
				</div>
			</div>
		</div>
		<div class="bottom-bottom">
			<div class="bottom-top-left" style="margin-top: 20px;"> 
				&nbsp;&nbsp;&nbsp;微信搜索小程序“王严健</br>
				身专家系统”，或保存识别</br>
				右侧小程序码后，在微信中</br>
				识别使用本小程序。</br>
				关注“王严健身专家系统”</br>
				小程序即可获得30元红包。
			</div>
			<div class="bottom-top-right bottom-bottom-right">
				<img src="https://www.ecartoon.com.cn/picture/201806141704.png">
			</div>
		</div>
	</div>
</body>
</html>