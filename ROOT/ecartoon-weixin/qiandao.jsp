<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":"+ request.getServerPort() + path + "/";
%>    
<!doctype html">
<html>
	<head>
		<title>签到</title>
		<meta charset="UTF-8">
		<base href="<%=basePath%>">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link href="ecartoon-weixin/css/mui.min.css" rel="stylesheet" />
		<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=22GR1VoZdYMI3SLs8eGRI2HmhvhC1BQQ"></script>
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
			
			#positionBox{
				width:130px!important;
				height:90px!important;
			}
			
			#selectPicture{
				left:0;
				right:0;
			}
			
			#popover{
				width:80%;
				margin:0 auto;
				background-color: #fff;
				border-radius:5px;
			}
			
			#popover > ul, #popover li{
				list-style: none;
				margin:0;
				padding:0;
			}
			
			 #popover li{
				 	border-bottom:1px solid #f3ecec;
				 	height:40px;
				 	line-height: 40px;
				 	padding:10px;
			 }
		</style>
	</head>
	<body>
		<div id="containerBox">
	    <div id="container"  style="width: 130px; height: 97.5px;"  ></div>
	  	<div id="tip" class="tip"></div>
	  </div>
		<div class="mui-content" id = "app">
			<ul class="mui-table-view">
				<li class="mui-table-view-cell mui-media">
					<a href="javascript:void(0);">
						<img class="mui-media-object mui-pull-left"  id = "memberImage" src="" style="border-radius:50%;"/>
						<div class="mui-media-body">
							${signDetail.jsons.memberName}
							<p class="mui-ellipsis">已健身<span class="cr">${signDetail.jsons.count == null ? 0 : signDetail.jsons.count}</span>次</p>
						</div>
					</a>
				</li>
			</ul>
			<div style="width: 100%;height: 2px;background: url(ecartoon-weixin/img/line.png)repeat-x scroll 0 0 /auto 2px;margin-top: 10px;">
			</div>
			<div>
				<ul class="mui-table-view">
					<li class="mui-table-view-cell mui-media">
						<a href="javascript:void(0);">
							<div class="mui-pull-left asd" id="positionBox">
							</div>
							<div class="mui-media-body" style="padding-top: 15px;color:#1e1e1e!important;padding-left: 20px;">
								${signDetail.jsons.clubName}
								<p class="mui-ellipsis" v-if="xx" id="productName">商品 : {{it.NAME}}</p>
								<p class="mui-ellipsis" style="margin-top:5px;">
									<span id="aaa" style="background: #FF4401;color:white;padding: 2px 4px;border-radius: 6px;">
										商品微调
									</span>
								</p>
							  <input type="hidden" :value="it.orderId" id="orderId" />
								<input type="hidden" :value="it.signType" id="signType" /> 
							</div>
						</a>
					</li>
					<li class="mui-table-view-cell">
						<a class="cg" style="font-size: 14px;">
							${signDetail.jsons.signDateymd}
							<span class="mui-pull-right">签到时间${signDetail.jsons.signDatehms}</span>
						</a>
					</li>
				</ul>
				<!--弹窗2-->
			</div>
			<div>
			<ul class="mui-table-view" style="margin-top:10px">
				<li class="mui-table-view-cell cg">
					<a class="javascript:void(0)" style="font-size:14px">
						身份验证
					</a>
				</li>
			</ul>
			<div class="showUser" style="padding-left: 10px;padding-top:10px;box-sizing: border-box;width: 100%;background: white;">
				<div class="userImg" style="width:100px;height: 100px;margin: 0px auto;background: #eaeaea;" onclick="selectOption()">
					<input type="file" name="memberHead" id="file" onchange="javascript:setImagePreview();" data-role="none" style="width: 100px;height: 100px;border:1px solid #eaeaea;opacity: 0;position: absolute;z-index: -1">
					<div id="localImag" style="position: absolute">
						<img src="ecartoon-weixin/img/shouye/qiandao/Upload-photos@2x.png" id="preview" width="100" height="100" style="diplay:none" />
					</div>
				</div>
				<div style="padding: 0 10px 10px 10px;text-align: center;font-size: 12px;" id="promptMessage">
					点击上传面部图片
				</div>
				<div id="selectPicture" class="mui-popover mui-popover-action mui-popover-bottom selectPicture">
					<ul class="mui-table-view">
						<li class="mui-table-view-cell">
							<a href="javascript:selectOption(2)">拍照</a>
						</li>
						<li class="mui-table-view-cell">
							<a href="javascript:selectOption(1)">相册</a>
						</li>
					</ul>
					<ul class="mui-table-view">
						<li class="mui-table-view-cell">
							<a href="javascript:selectOption(0)">取消</a>
						</li>
					</ul>
				</div>
			</div>
			<div style="padding-top: 10px;padding-left: 10px;margin-bottom: 50px;">
				<p style="margin-bottom: 0;">-第一次签到请上传您的面部照片(五官照片)</p>
				<p>-照片一旦上传不可修改</p>
			</div>
		 </div>
		 <!--弹窗1-->
		<div id="zhezhao" style="position: fixed;top:0;left:0;width:100%;height:100%;background-color: rgba(0,0,0,0.3);z-index:1;" hidden="hidden">
			<div id="popover" class="" style="position:fixed;top:20%;left: 12%;z-index: 1;">
				<div style="height: 40px;line-height: 40px;text-align: center;">
					选择商品
				</div>
				<ul class="" style="margin-bottom: 40px;overflow: scroll;max-height: 200px;">
					<li class="" v-for="(v,i) in ite" @click="choose(i)" style="position: relative;" class="popover-li">
						<!-- <a href="javascript:void(0)" style="position: relative;"> -->
							<p  v-if="yy" class="oName" style="float:left;height:19px;line-height: 19px;">{{v.NAME}}</p>
							<!-- <div style="float: right;"> -->
								<!-- <img class="radioImg" :src="radio_imgs[i]" style="width:18px;height:18px;"/> -->
								<input type="radio" class="orderName" name="orderName" style="float: right;" :value="v.orderId"/>
							<!-- </div> -->
							<input type="hidden"  class="osignType" :value = "v.signType" />
							<!-- <div style="width:100%;height:100%;position: absolute;top:0;left:0;z-index: 1;"></div> -->
						<!-- </a> -->
					</li>
				</ul>
				<div style="position:absolute;bottom: 0;left: 0;height: 40px;width: 100%;line-height: 40px;">
						<div id="false"style="width: 50%;float: left;text-align: center;border-right: 1px solid #f3ecec;">取消</div>
						<div id="true" style="width: 50%;float: left;text-align: center;color:red">确定</div>
						<div style="clear: both;"></div>
				</div>
		  </div>
		</div>
		</div>
		<div class="mui-backdrop" hidden="hidden">
			<div style="margin-top:10px;text-align: center;color:#fff;" id="loadingText"></div>
		</div>
		<div class="footer">
			<a id="sub">确定</a>
		</div>
		<script src="ecartoon-weixin/js/mui.min.js"></script>
		<script src="ecartoon-weixin/js/jquery.min.js"></script>
		<script src="ecartoon-weixin/js/ajaxfileupload.js"></script>
		<script src="ecartoon-weixin/js/vue.min.js" type="text/javascript"></script>
		<script type="text/javascript">
		 var image = "${signDetail.jsons.memberImage}";
		 if (image.indexOf("http") != -1){
		    $("#memberImage").attr({"src":image});	 
		 } else {
			$("#memberImage").attr({"src":"picture/"+image});
		 }
		</script>
		<script type="text/javascript">
		var sign = ${signDetail};
		var signS = sign.jsons.signI == null ? 0 : sign.jsons.signI;
		var signS0 = signS == 0 ? {orderId:0,signType:0} : signS[0];
		var xx = signS[0]==null?false:true;
		var yy = signS==null?false:true;
		var zz = false;
		if(sign.jsons.headPortrait == undefined || (signS!=undefined && sign.jsons.headPortrait == undefined)){
			zz = true;
		}
		var vue = new Vue({
			el:"#app",
			data:{
				it:signS0,
				ite:signS,
				v:${signDetail},
				isNull : sign.success,
				xx:xx,
				yy:yy,
				zz:zz,
				radio_imgs:[]
			},
			created:function(){
				if(sign.jsons.headPortrait != null && sign.jsons.headPortrait != ""){
					$("#preview").attr("src","picture/"+sign.jsons.headPortrait);
					$("#promptMessage").html("");
				}
				
				var arr = [];
				this.ite.forEach(function(item,i){
					if(i == 0){
						arr.push("ecartoon-weixin/img/radio-active.png");
					}else{
						arr.push("ecartoon-weixin/img/radio.png");
					}
				});
				this.radio_imgs = arr;
			},
			methods:{
				choose:function(i){
					vue.it = vue.ite[i];
					vue.radio_imgs.forEach(function(item,index){
						vue.radio_imgs[index] = "ecartoon-weixin/img/radio.png";
					});
					vue.radio_imgs[i] = "ecartoon-weixin/img/radio-active.png";
					/* $(".radio-img").attr("src","ecartoon-weixin/img/radio.png")
					$(".radio-img").eq(i).attr("src","ecartoon-weixin/img/radio-active.png");*/
				}
			}
		})
		if(sign.success == false){
			mui.alert(sign.message,'温馨提示','确定'); 
		}
	
		mui.init();
		document.getElementById("aaa").addEventListener('tap', function() {
			if(signS.length > 0){
				$("#zhezhao").show();
			}else{
				mui.alert("您没有有效订单，无法签到，请购买相应商品","提示","确定");
			} 
		});
		document.getElementById("false").addEventListener('tap', function() {
			$("#zhezhao").hide();
		});
		document.getElementById("true").addEventListener('tap', function() {
			$("#zhezhao").hide();
		});
		
		$(function(){
			$("#localImag").click(function(){
				if(sign.jsons.headPortrait == null || sign.jsons.headPortrait == ""){
					mui('#selectPicture').popover('toggle');
				}
			});
			
			$(".orderName").eq(0).attr("checked",true);
		});
		
		//签到
		document.getElementById("sub").addEventListener('tap', function() {
			// 显示遮罩等待
			$("#loadingText").html("正在进行签到,请稍后...");
			$(".mui-backdrop").show();
			if(sign.success){
				if(sign.jsons.headPortrait != null && sign.jsons.headPortrait != ""){
					var jsons = {
							signLat:sign.jsons.signLat,
							signLng:sign.jsons.signLng,
							orderId:$("#orderId").val(),
							signType:$("#signType").val(),
							clubId:sign.jsons.clubId,
							image:sign.jsons.headPortrait
					}
					if (jsons.orderId == 0){
						$(".mui-backdrop").hide();
						mui.alert("您没有有效订单，无法签到，请购买相应商品","提示","确定");
						return;
					}
					$.ajax({
						url:"esignwx!sign.asp",
						type:"post",
						data:{jsons:encodeURI(JSON.stringify(jsons))},
						dataType:"json",
						success:function(res){
							if(res.success){
								$(".mui-backdrop").hide();
								mui.alert('签到成功','温馨提示','确定'); 
							}else{
								$(".mui-backdrop").hide();
								mui.alert(res.message,'温馨提示','确定'); 
							}
						},
						error:function(e){
							alert(JSON.strinify(e));
						}
					});
				}else{
					$(".mui-backdrop").hide();
					mui.alert('请先上传面部头像','温馨提示','确定'); 
				}
			}else{
				$(".mui-backdrop").hide();
				mui.alert(sign.message,'温馨提示','确定');
			}
		});
		
		//切换商品
		function choose(i){
			$(".orderName").eq(i).prop("checked",true);
			var x = $(".oName").eq(i).html();//获取被选中的商品名称
			$("#productName").html(""+x);
			var orderId = $(".orderName").eq(i).val();//获取被选中的商品的订单Id
			$("#orderId").val(orderId);
			var signType = $(".osignType").eq(i).val();//获取被选中的商品的签到类型
			$("#signType").val(signType);
		}

		// 百度地图API功能
		var map = new BMap.Map("container");    // 创建Map实例
		map.centerAndZoom(new BMap.Point(sign.jsons.clubLng, sign.jsons.clubLat), 15);  // 初始化地图,设置中心点坐标和地图级别
		var point = new BMap.Point(sign.jsons.clubLng, sign.jsons.clubLat); //循环生成新的地图点  
    var marker = new BMap.Marker(point); //按照地图点坐标生成标记  
    map.addOverlay(marker); 
	  /*设置地图显示位置*/
    (function(){
    	$("#positionBox").css({"width":"160px","height":"97.5px"});
			$("#container").css({"top":"97px","left":"5px","z-index":"1"});
			$("#containerBox").css({"height":"0"});
		})();
	  
	  //选择上传图片的方式
	  function selectOption(type){
		  if(type == 0){
			  mui('#selectPicture').popover('hide');
		  }else if(type == 1){
			  $("#file").attr({"accept":"","capture":""});
			  $("#file").removeAttr("capture");
			  $("#file").click();
			  setTimeout(function(){
				  mui('#selectPicture').popover('hide');
			  },1000)
		  }else if(type == 2){
			  $("#file").attr({"accept":"image/*","capture":"camera"});
			  $("#file").click();
			  setTimeout(function(){
				  mui('#selectPicture').popover('hide');
			  },1000)
		  }
	  }
	  
		/*上传图片*/
		function setImagePreview() {
			// 读取文件
			var file = {
					path:$("#file")[0].files[0],
					size:$("#file")[0].files[0].size,
					suffix:$("#file")[0].value.split(".")[1].toUpperCase()
			}
			
			// 显示遮罩等待上传
			$("#loadingText").html("图片正在上传中,请稍后...");
			$(".mui-backdrop").show();
			setTimeout(function(){
				$(".mui-backdrop").hide();
				var reader = new FileReader();
			  reader.readAsDataURL(file.path);
			  reader.onloadend = function () {
				  $("#preview").attr("src", this.result);
					$("#promptMessage").html("");
				}
			},5000);
			
			// 上传文件
			if(file.size > 0){
				if(file.size >= (1024 * 1024 * 10)){
					alert("请选择小于10M的图片");
					$(".mui-backdrop").hide();
					return;
				}else if( file.suffix != "JPG" && file.suffix != "JPEG" && file.suffix != "PNG" && file.suffix != "GIF"){
					alert("图片类型必须是.gif,jpeg,jpg,png中的一种");
					$(".mui-backdrop").hide();
					return;
				}else{
					$.ajaxFileUpload({
						url:'esignwx!uploadImage.asp',
						type:'post',
						fileElementId:'file',
						success:function(res){
							res = JSON.parse($(res).text());
							sign.jsons.headPortrait = res.image;
							alert('上传成功');
						},
						error:function(e){
							//alert(JSON.stringify(e));
						}
					});
				}
			}
		}
	  </script>
	</body>
</html>