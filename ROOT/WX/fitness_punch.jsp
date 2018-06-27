<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="HandheldFriendly" content="true" />
<meta name="MobileOptimized" content="320" />
<title>Hello H5+</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/common.js"></script>
<script type="text/javascript">
	var watchId;
	function geoInf(position) {
		var str = "";
		str += "地址：" + position.addresses + "\n";//获取地址信息
		str += "坐标类型：" + position.coordsType + "\n";
		var timeflag = position.timestamp;//获取到地理位置信息的时间戳；一个毫秒数；
		str += "时间戳：" + timeflag + "\n";
		var codns = position.coords;//获取地理坐标信息；
		var lat = codns.latitude;//获取到当前位置的纬度；
		str += "纬度：" + lat + "\n";
		var longt = codns.longitude;//获取到当前位置的经度
		str += "经度：" + longt + "\n";
		var alt = codns.altitude;//获取到当前位置的海拔信息；
		str += "海拔：" + alt + "\n";
		var accu = codns.accuracy;//地理坐标信息精确度信息；
		str += "精确度：" + accu + "\n";
		var altAcc = codns.altitudeAccuracy;//获取海拔信息的精确度；
		str += "海拔精确度：" + altAcc + "\n";
		var head = codns.heading;//获取设备的移动方向；
		str += "移动方向：" + head + "\n";
		var sped = codns.speed;//获取设备的移动速度；
		str += "移动速度：" + sped;
		console.log(JSON.stringify(position));
		alert(str);
		//此处为获取的位置信息
		outLine(str);
	}

	function getPos() {
		outSet("获取位置信息:");
		plus.geolocation.getCurrentPosition(geoInf, function(e) {
			outSet("获取位置信息失败：" + e.message);
		}, {
			geocode : false
		});
	}
	function watchPos() {
		if (watchId) {
			return;
		}
		watchId = plus.geolocation.watchPosition(function(p) {
			outSet("监听位置变化信息:");
			geoInf(p);
		}, function(e) {
			outSet("监听位置变化信息失败：" + e.message);
		}, {
			geocode : false
		});
	}
	function clearWatch() {
		if (watchId) {
			outSet("停止监听位置变化信息");
			plus.geolocation.clearWatch(watchId);
			watchId = null;
		}
	}
	// 通过定位模块获取位置信息
	function getGeocode() {
		outSet("获取定位位置信息:");
		plus.geolocation.getCurrentPosition(geoInf, function(e) {
			outSet("获取定位位置信息失败：" + e.message);
		}, {
			geocode : true
		});

	}
	document.addEventListener('plusready', function() {
		// 在这里调用5+ API 
		getGeocode();
	}, false);

	//		diliweizhi 	

	var ws = null, wo = null;
	var scan = null, domready = false, bCancel = false;
	// H5 plus事件处理
	function plusReady() {
		if (ws || !window.plus || !domready) {
			return;
		}
		// 获取窗口对象
		ws = plus.webview.currentWebview();
		wo = ws.opener();
		// 开始扫描
		ws.addEventListener('show', function() {
			scan = new plus.barcode.Barcode('bcid', [ plus.barcode.QR, plus.barcode.EAN8, plus.barcode.EAN13 ], {
				frameColor : '#00FF00',
				scanbarColor : '#00FF00'
			});
			scan.onmarked = onmarked;
			scan.start({
				conserve : true,
				filename : '_doc/barcode/'
			});
		});
		// 显示页面并关闭等待框
		ws.show('pop-in');
		wo.evalJS('closeWaiting()');
	}
	if (window.plus) {
		plusReady();
	} else {
		document.addEventListener('plusready', plusReady, false);
	}
	// 监听DOMContentLoaded事件
	document.addEventListener('DOMContentLoaded', function() {
		domready = true;
		plusReady();
	}, false);
	// 二维码扫描成功
	function onmarked(type, result, file) {
		switch (type) {
		case plus.barcode.QR:
			type = 'QR';
			break;
		case plus.barcode.EAN13:
			type = 'EAN13';
			break;
		case plus.barcode.EAN8:
			type = 'EAN8';
			break;
		default:
			type = '其它' + type;
			break;
		}
		result = result.replace(/\n/g, '');

		//分析扫描结果：是URL就跳转 ，不是就提示
		if (result.indexOf('http://') == 0 || result.indexOf('https://') == 0) {
			plus.nativeUI.confirm(result, function(i) {
				if (i.index == 0) {

					back();//返回上一页
					plus.runtime.openURL(result);
				} else {
					back();//返回上一页
				}
			}, '', [ '打开', '取消' ]);
		} else {
			back();//返回上一页
			plus.nativeUI.alert(result);
		}

		wo.evalJS("scaned('" + type + "','" + result + "','" + file + "');");
		back();
	}
	// 从相册中选择二维码图片 
	function scanSwitch() {
		if (bCancel) {
			scan.start({
				conserve : true,
				filename : '_doc/barcode/'
			});
			btCancel && (btCancel.innerText = '暂　停');
		} else {
			scan.cancel();
			btCancel && (btCancel.innerText = '开　始');
		}
		bCancel = !bCancel;
	}
</script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/WX/css/common.css" type="text/css" charset="utf-8" />
<style type="text/css">
#bcid {
	width: 100%;
	position: absolute;
	top: 0;
	bottom: -70px;
	text-align: center;
}

.tip {
	color: #FFFFFF;
	font-weight: bold;
	text-shadow: 0px -1px #103E5C;
}

footer {
	width: 100%;
	height: 44px;
	position: absolute;
	bottom: 0px;
	line-height: 44px;
	text-align: center;
	color: #FFF;
}

.fbt {
	width: 50%;
	height: 100%;
	background-color: #FFCC33;
	float: left;
}

.fbt:active {
	-webkit-box-shadow: inset 0 3px 5px rgba(0, 0, 0, 0.5);
	box-shadow: inset 0 3px 5px rgba(0, 0, 0, 0.5);
}
</style>
</head>
<body style="background-color: #000000;">
	<!-- 用戶信息展示开始 -->
	<s:iterator value="pageInfo.items">
		<div
			style="z-index: 999 !important; position: fixed; width: 100%; height: 100px; padding-top: 10px; background-color: white !important; overflow: hidden; padding-left: 1em; box-sizing: border-box;">
			<div style="height: 80px; border-radius: 50% !important; box-sizing: border-box; float: left; overflow: hidden;">
				<img src="../img/ui.png" alt="" width="80px" />
			</div>
			<div style="box-sizing: border-box; float: left; font-size: 13px; padding: 5px; color: #9f9f9f;">
				<span style="line-height: 40px !important;">
					<s:property value="name" />
				</span>
				<div style="">
					已经健身
					<span style="color: orangered">
						<s:property value="timeNum" />
					</span>
					次
				</div>
			</div>
		</div>
	</s:iterator>
	<!-- 用户信息展示结束 -->

	<div id="bcid" style="background: #eaeaea;">
		<div style="height: 30%;"></div>
		<p class="tip" style="background: black; margin-top: 80px;">...载入中...</p>
	</div>
	<div id="output" style="display: none">Geolocation可获取设备位置信息，包括经度、纬度、高度等信息。</div>
	<footer style="">
		<div class="fbt" style="background: black !important; outline: 1px solid wheat;" onclick="back()">取 消</div>
		<div id="btCancel" class="fbt" onclick="scanSwitch()" style="background: black; outline: 1px solid wheat;">暂 停</div>
	</footer>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/immersed.js"></script>
</html>
