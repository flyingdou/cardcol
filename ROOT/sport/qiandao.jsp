<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link href="sport/css/mui.min.css" rel="stylesheet" />
		<style>
			.cr {
				color: #FF4401;
				font-size: 17px;
			}
			
			.asd {
				line-height: 80px!important;
				max-width: 106px!important;
				height: 80px!important;
			}
			
			.spwt {
				background: #ff4401;
				color: white;
				font-size: 12px;
				padding: 1px 6px;
				border-radius: 6px;
			}
			
			.cg {
				color: #999999!important;
			}
			
			.footer {
				position: fixed;
				bottom: 0;
				left: 0;
				width: 100%;
				height: 44px;
				padding: 0;
				margin: 0;
			}
			
			.footer a {
				width: 100%;
				height: 44px;
				display: block;
				background: #FF4401;
				line-height: 44px;
				color: white;
				text-align: center;
			}
		</style>
	</head>
	<body>
		<div class="mui-content" id = "app" v-if="isNull">
			<ul class="mui-table-view">
				<li class="mui-table-view-cell mui-media">
					<a href="javascript:void(0);">
						<img class="mui-media-object mui-pull-left" :src="'sport/'+v.memberImage" style="border-radius:50%;">
						<div class="mui-media-body">
							${signDetail.memberName}
							<p class="mui-ellipsis">已健身<span class="cr">${signDetail.count}</span>次</p>
						</div>
					</a>
				</li>
			</ul>
			<div style="width: 100%;height: 2px;background: url(sport/img/line.png)repeat-x scroll 0 0 /auto 2px;margin-top: 10px;">
			</div>
			<div>
				<ul class="mui-table-view">
					<li class="mui-table-view-cell mui-media">
						<a href="javascript:void(0);">
							<div class="mui-media-object mui-pull-left asd" style="width: 106px;height: 80px;"></div>
							<div class="mui-media-body" style="padding-top: 15px;color:#1e1e1e!important;">
								${signDetail.coachName}
								
								<input type="hidden" :value="it.orderId" id="orderId" />
								<input type="hidden" :value="it.signType" id="signType" />
								<input type="hidden" :value="v.signLat" id="signLat" />
								<input type="hidden" :value="v.signLng" id="signLng" />
								<input type="hidden" :value="v.coachId" id="coachId" />
								
								
								
								<p class="mui-ellipsis" v-if="xx" id="oo" >商品  {{it.NAME}}</p>
								
								<p class="mui-ellipsis">
									<span id="aaa" style="background: #FF4401;color:white;padding: 2px 4px;border-radius: 4px;">商品微调</span>
								</p>

							</div>
						</a>
					</li>

					<li class="mui-table-view-cell">
						<a class="mui-navigate-right cg" style="font-size: 14px;">
							${signDetail.signDateymd}
							<span class="mui-pull-right" id = "signTime" data-signTime="${signDetail.signDateymd}" >签到时间${signDetail.signDatehms}</span>
						</a>
					</li>

				</ul>
<!--弹窗1-->
				<div id="popover" class="mui-popover" style="left: 15%;">
					<div style="height: 40px;line-height: 40px;text-align: center;">
						选择商品
					</div>
					<ul class="mui-table-view"style="max-height: 200px;overflow-y: scroll;margin-bottom: 50px;" v-for="v in ite" >
						<li class="mui-table-view-cell">
							<label for="" v-if="yy" class="oName" >{{v.NAME}}</label><input type="radio" name="orderName" class="orderName"  style="float: right " :value="v.orderId" />
							<input type="hidden"  class="osignType" :value = "v.signType" />
							<span class="mui-clearfix"></span>
						</li>
					</ul>
					<div style="position:absolute;bottom: 0;left: 0;height: 40px;width: 100%;line-height: 40px;border-top:1px solid #999">
					<div id="false"style="width: 50%;float: left;text-align: center;border-right: 1px solid #999;">取消</div>
						<div id="true" style="width: 50%;float: left;text-align: center;color:red" onclick="choose()" >确定</div>
						<div style="clear: both;"></div>
					</div>
				</div>
			</div>
			
			<!-- 运动心率 -->
			<div class="mui-card">
				<div class="mui-card-content"> 
				
					<div align="left" style="padding: 10px;" >
					    <span style="width: 40%">输入最高运动心率</span><input type="text" id="heartRate" style="width: 50%"  /> <span style="width: 10%">次/分</span>  
					</div>
					<div align="center" style="padding-bottom: 10px;" >
					   <input value="从手环获取运动心率" type="button" onclick="getHeartRate()" />&nbsp;&nbsp;&nbsp;&nbsp;
					   <input value="购买运动手环" type="button" onclick="buy()" />
				    </div>
				    
				</div>
			</div>
		 
		</div>
		<div class="footer">
			<a id="sub" onclick="signOrder()" >确定</a>
		</div>

		<script src="sport/js/mui.min.js"></script>
		<script src="sport/js/jquery.min.js"></script>
		<script src="sport/js/vue.min.js"></script>
		
		<script type="text/javascript">
		
		function choose(){
			var i = $("input[name='orderName']").index($("input[name='orderName']:checked"));//被选中的input下标index
			var x = $(".oName").eq(i).html();//获取被选中的商品名称
			$("#oo").html("商品 "+x);
			var orderId = $(".orderId").eq(i).val();//获取被选中的商品的订单Id
			$("#orderId").val(orderId);
			var signType = $(".osignType").eq(i).val();//获取被选中的商品的签到类型
			$("#signType").val(signType);
			
		}
		
		
		function getHeartRate(){
			var traindate = $("#signTime").attr("data-signTime");
			var url = "smemberwx!ShowHeartRate.asp";
			$.post(url,{"traindate":traindate},function(data){
				var xx = eval('('+data+')');
				if(xx.success){
					var heartRate = xx.heartRates[xx.heartRates.length-1];
					$("#heartRate").val(heartRate);
					alert("ajax传回的值："+heartRate);
				}else{
					alert(xx.message+",请手动添加数据");
				}
			});
		}
		
		function buy(){
			loaction.href="#";//跳转到购买运动手环页面
			alert("此时将要跳转到购买页面");
		}
		
		function signOrder(){
			var signLat = $("#signLat").val();
			var signLng = $("#signLng").val();
			var orderId = $("#orderId").val();
			var coachId = $("#coachId").val();
			var signType = $("#signType").val();
			var heartRate = $("#heartRate").val();
			
			var jsons = '{"orderId":'+orderId+',"coachId":'+coachId+',"signLat":'+signLat+',"signLng":'+signLng+',"signType":'+signType+',"heartRate":'+heartRate+'}'
			var url = "ssignwx!sign.asp";
			
			$.post(url,{"jsons":jsons},function(data){
				var json = eval('('+data+')');
				if(json.message == "OK"){//签到成功
					    document.getElementById("sub").addEventListener('tap', function() {
						mui.alert('签到成功','温馨提示','确定') // 将id为list的元素放在想要弹出的位置
					});
				}else{//签到失败
					alert("签到失败，请重新尝试！");
				}
				
			});
			
		}
		
		
		
		
		
		var sign = ${signDetail};
		var signS = sign.signI == null ? 0 : sign.signI;
		var xx = signS[0]==null?false:true;
		var yy = signS==null?false:true;
		new Vue({
			el:"#app",
			data:{
				it:signS[0],
				ite:signS,
				v:${signDetail},
				isNull : sign.success,
				xx:xx,
				yy:yy
			}
		});
		if(sign.success == false){
			$(".footer").html("<a>"+sign.message+"</a>");
		}
	
		
		
		
		
		
		mui.init()
		document.getElementById("aaa").addEventListener('tap', function() {
			mui('#popover').popover('toggle'); // 将id为list的元素放在想要弹出的位置
		});
		document.getElementById("false").addEventListener('tap', function() {
			mui('#popover').popover('hide'); // 将id为list的元素放在想要弹出的位置
		});
		document.getElementById("true").addEventListener('tap', function() {
			mui('#popover').popover('hide'); // 将id为list的元素放在想要弹出的位置
		});
		
	



		
	  </script>
	</body>
</html>