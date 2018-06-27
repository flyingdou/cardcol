<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">

<meta name="HandheldFriendly" content="true" />
<meta name="MobileOptimized" content="320" />
<link href="css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" href="http://cache.amap.com/lbs/static/main1119.css"/>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script src="http://webapi.amap.com/maps?v=1.3&key=您申请的key值"></script>
<script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>
<script src="https://cdn.bootcss.com/vue/2.2.2/vue.min.js"></script>

<title></title>
<style>
* {
	margin: 0;
	padding: 0;
}

a {
	text-decoration: none !important;
}

.file {
	opacity: 0;
	filter: alpha(opacity = 0);
	position: absolute;
	left: 0;
	top: 0;
	display: block;
	width: 100px;
	height: 100px;
}

.pic {
	width: 100px;
	height: 100px;
	border: 1px dashed seagreen
}

.pic img {
	width: 100%;
	height: 100%
}
.modal-backdrop{
background:rgba(0,0,0,0)!important;
}
.modal-content {
-webkit-box-shadow:0 0 0 rgba(0,0,0,0)!important;
}
.btn-nocss{
  border:none;
  border-right:1px solid #f2f2f2!important;
  border-bottom-left-radius:6px!important;
  border-bottom-right-radius:0px!important;
  border-top-right-radius:0px!important;
}

.modal-footer .btn+.btn{
	margin-left:0px;
}

::-webkit-input-placeholder { /* WebKit browsers */
	color: #9f9f9f;
}

:-moz-placeholder { /* Mozilla Firefox 4 to 18 */
	color: #9f9f9f;
}

::-moz-placeholder { /* Mozilla Firefox 19+ */
	color: #9f9f9f;
}

:-ms-input-placeholder { /* Internet Explorer 10+ */
	color: #9f9f9f;
}

input[readonly="readonly"]::-webkit-input-placeholder {
	/* WebKit browsers */
	color: black;
}
</style>
</head>
<body style="background: #f2f2f2;">
	<div
		style="width: 100%;; height: 80px; position: fixed; top: 0; background: white; padding: 5px 0px 5px 10px; box-sizing: border-box; overflow: hidden;">
		<div style="width: 70px; height: 70px; float: left;">
			<img src="img/orientationl.png" width="70px" />
		</div>
		<div style="box-sizing: border-box; float: left; font-size: 13px; padding: 5px; color: #9f9f9f;">
			<span style="line-height: 35px !important; color: #000000">
				<s:property value='#session.member.name' />
			</span>
			<div style="">
				已经健身
					<span id="fitnessTimes" style="color: orangered"></span>
				次
			</div>
		</div>
		<div style="clear: both;"></div>
	</div>

	<div class="showClub"
		style="height: 120px; overflow: hidden; width: 100%; margin-top: 90px; padding-top: 10px; padding-left: 10px; background: white; box-sizing: border-box;">
		<div class="mapBox" style="float: left; width: 100px; height: 65px; margin-right: 10px; background: red;">
			<div id="container"  style="position: relative; width:100px ; height:65px;"></div>
		</div>
		<div style="float: left; margin-bottom: 10px;">
			<div id="clubName" style="font-size: 14px; font-weight: 700;"></div>
			<div style="font-size: 12px; margin: 5px 0 5px 0;">
				商品：
				<span id="productName"></span>
				<input type="hidden" id="productId">
			</div>
			<div
				style='color: white; background: red; text-align: center; padding: 2px; border-radius: 12px; box-sizing: border-box; width: 60px; font-size: 12px;'
				data-toggle="modal" data-target="#m1">商品微调</div>
		</div>
		<div style="clear: both;"></div>

		<div class="showTime"
			style="width: 100%; border-top: 1px solid #eaeaea; font-size: 12px; box-sizing: border-box; line-height: 34px; color: #9f9f9f">
			<div style="float: left; width: 100px;">2017-06-03</div>
			<div style="float: right; width: 200px; text-align: right; padding-right: 10px;">签到时间：15：00</div>
			<div style="clear: both"></div>
		</div>
	</div>

	<div class="modal modal-dialog  modal-sm" data-backdrop="true" data-show="true" tabindex="-1" data-keyboard="true"
		id="m1" style="margin: -120px 20px 0 20px; max-height: 400px;border：none">
		<div class="modal-content" data-show="true">
			<div class="modal-header" style="text-align: center; padding: 5px;">
				<h6>
					选择商品
				</h6>
			</div>
			<div class="modal-body" style="padding: 0px;">
				<div style="height: 200px; overflow-y: scroll;">
					<div id="oneCardProducts" class="radio" style="border-bottom: 1px solid #eaeaea; padding: 0px 0 9px 0;">
						<!-- 商品的动态展现开始 -->
						<label style="width: 100% !important" v-for="product in products">
							<span>
								{{ 	product.NAME }}
							</span>
							<input type="radio" name="optionsRadios" style="margin-left: 0px !important; right: 10px;" id="optionsRadios1"
								value="option1" checked>
							<span style="display:none">{{ product.id }}</span>
						</label>
						<!-- 产品的动态展现结束 -->
					</div>
				</div>
			</div>
			<div class="modal-footer" style="padding: 0;">
				<button type="button" class="btn btn-default btn-nocss"  data-dismiss="modal" style='text-align: center; line-height: 20px; border-right: 1px solid #eaeaea; box-sizing: border-box; width: 50%; height: 30px; font-size: 14px; font-weight: 100px; color: #555 !important; display: block; float: left;'>关闭</button>
				<button type="button" class="btn btn-default btn-nocss" onclick="ok()" data-dismiss="modal" style='line-height: 20px; text-align: center; width: 50%; height: 30px; display: block; float: left;'>
					确认
				</button>	
				<div style="clear: both"></div>
			</div>

		</div>
	</div>



	<div class="showUser"
		style="margin-top: 10px; padding-left: 10px; box-sizing: border-box; width: 100%; background: white;">
		<div
			style="height: 35px; width: 100%; box-sizing: border-box; border-bottom: 1px solid #eaeaea; line-height: 34px; font-size: 14px;">
			身份验证
			<span style="color: #9f9f9f; font-size: 12px;">(请向俱乐部前台初始本界面，由前台点击确认)</span>
		</div>
		<div class="userImg" style="width: 100px; height: 100px; margin: 20px auto; background: #eaeaea;">

			<input type="file" name="doc" id="doc" onchange="javascript:setImagePreview();" data-role="none"
				style="width: 100px; height: 100px; border: 1px solid #eaeaea; opacity: 0; position: absolute; z-index: 3">

			<div id="localImag" style="position: absolute">
				<img src="img/helloh5.jpg" id="preview" width="100px" height="100px" style="diplay: none" />
			</div>



		</div>
		<div style="padding: 0 10px 10px 10px; text-align: center; font-size: 12px;">持卡人面部照片</div>
	</div>


	<div class="footer"
		style="position: fixed; bottom: 0px; width: 100%; height: 40px; box-sizing: border-box; line-height: 40px; font-size: 13px;">
		<a href=""
			style="height: 40px; width: 50%; display: block; float: left; color: white; background: black; text-align: center;">取消</a>
			
		<a href=""
			style="width: 50%; height: 40px; display: block; float: left; color: white; background: red; text-align: center;">确定</a>
	</div>



	<input id="pageInfo" type="hidden" value="<s:property value='#request.pageInfo'/>" />
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/jquery-2.2.0.min.js"></script>
<script type="text/javascript">
	$(function() {
		var pageInfoObj = jQuery.parseJSON($("#pageInfo").val());
		pageInfoObj.items[0].NAME
		$("#fitnessTimes").html(pageInfoObj.count)// 指定健身次数
		$("#clubName").html(pageInfoObj.clubName)// 指定俱乐部名称
		$("#productName").html(pageInfoObj.items[0].NAME)// 商品名
		$("#productId").val(pageInfoObj.items[0].id)// 商品名
		
		var products = pageInfoObj.items;
		
		// 签到的产品
		new Vue({
			  el: '#oneCardProducts',
			  data: {
				  products
			  }
		})
		
		// 百度地图 开始
		 var map = new AMap.Map('container', {
		        resizeEnable: true,
		        center: [pageInfoObj.clubLng, pageInfoObj.clubLat],
		        zoom: 15
		    });
		    var marker = new AMap.Marker({
		        position: map.getCenter()
		    });
		    marker.setMap(map);
		// 百度地图 结束
	})
	
	function ok() {
		var productName = $(":checked").prev().html().trim();
		var productId = $(":checked").next().html().trim();
		
		$("#productName").html(productName)// 商品名
		$("#productId").val(productId)// 商品编号
	}
	
</script>

<script type="text/javascript">
	window.onload = function() {
		var file = document.getElementById('file');
		var pic = document.getElementById('pic');
	}

	function setImagePreview() {
		var docObj = document.getElementById("doc");

		var imgObjPreview = document.getElementById("preview");
		if (docObj.files && docObj.files[0]) {
			//火狐下，直接设img属性
			imgObjPreview.style.display = 'block';
			imgObjPreview.style.width = '100px';
			imgObjPreview.style.height = '100px';
			//imgObjPreview.src = docObj.files[0].getAsDataURL();

			//火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
			imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);

		} else {
			//IE下，使用滤镜
			docObj.select();
			var imgSrc = document.selection.createRange().text;
			var localImagId = document.getElementById("localImag");
			//必须设置初始大小
			localImagId.style.width = "300px";
			localImagId.style.height = "120px";
			//图片异常的捕捉，防止用户修改后缀来伪造图片
			try {
				localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
				localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
			} catch (e) {
				alert("您上传的图片格式不正确，请重新选择!");
				return false;
			}
			imgObjPreview.style.display = 'none';
			document.selection.empty();
		}
		return true;
	}
</script>

</html>
