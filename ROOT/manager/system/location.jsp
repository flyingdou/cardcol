<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
body, html {width: 100%;height: 100%;overflow: hidden;margin:0;}
#allmap {width: 100%;height: 90%;overflow: hidden;margin:0;}
#l-map{height:100%;width:78%;float:left;border-right:2px solid #bcbcbc;}
#r-result{height:100%;width:20%;float:left;}
#message{height: 10%;width: 100%;font-size: 14px;color: #ff0000;}
#lnglat{width: 300px;}
</style>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.3"></script>
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<title>设置俱乐部的地理位置</title>
</head>
<body>
<s:hidden theme="simple" id="id" name="member.id" />
<div id="allmap"></div>
<div id="message"><s:property value="member.name"/>的当前位置：<span id="lnglat"></span><span><input type="button" value="保存" onclick="javascript:onSave();"/></span></div>
</body>
</html>
<script type="text/javascript">
var marker;
var nick = '<s:property value="member.name" escape="false"/>';
var city = '<s:property value="member.city" escape="false"/>';
if (city == '') city = '武汉';
var lng = <s:if test="member.longitude == null">0</s:if><s:else><s:property value="member.longitude"/></s:else>,
	lat = <s:if test="member.latitude == null">0</s:if><s:else><s:property value="member.latitude"/></s:else>;
var map = new BMap.Map("allmap");
if (lng > 0 && lat > 0) {
	var point = new BMap.Point(lng, lat);
	map.centerAndZoom(point, 15);
	marker = new BMap.Marker(point);
    map.addOverlay(marker);
    marker.setAnimation(BMAP_ANIMATION_BOUNCE);
    var label = new BMap.Label(nick, {offset:new BMap.Size(20,-10)});
    marker.setLabel(label);
} else {
	map.centerAndZoom(city, 15);	
}
map.enableScrollWheelZoom();
map.addEventListener("click",function(e){
    lng = e.point.lng;
    lat = e.point.lat;
    if (marker) map.removeOverlay(marker);
    marker = new BMap.Marker(new BMap.Point(lng, lat));
    //var infoWindow = new BMap.InfoWindow(sContent);  // 创建信息窗口对象
    map.addOverlay(marker);
    marker.setAnimation(BMAP_ANIMATION_BOUNCE); 
    var label = new BMap.Label(nick, {offset:new BMap.Size(20,-10)});
    marker.setLabel(label);
    $('#lnglat').html('经度：' + lng + '&nbsp;&nbsp;&nbsp;&nbsp;纬度：' + lat);
});
function onSave() {
    var parms = 'id=' + $('#id').val() + '&member.longitude=' + lng + '&member.latitude=' + lat;
    $.ajax({url: 'location!save.asp', type: 'post', data: parms, 
    	success: function(resp) {
    		alert('当前俱乐部的地理位置已经成功保存！');
    	}
    })
}
</script>
