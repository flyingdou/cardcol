<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>健身E卡通-我的账户</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="健身E卡通-我的账户" />
<meta name="description" content="健身E卡通-我的账户" />
<link rel="stylesheet" type="text/css" href="css/user-account.css" />
<link rel="stylesheet" type="text/css" href="css/pulicstyle.css" />
<script type="text/javascript" src="script/jquery-1.7.2.min.js"></script>
<script charset="utf-8" src="http://map.qq.com/api/js?v=2.exp"></script>
<style type="text/css">
#allmap {width: 800px;height:450px;overflow: hidden;margin:0;}
#message{height: 10%;width: 100%;font-size: 14px;color: #ff0000; padding-top:10px;}
#lnglat{width: 300px;}
</style>
<s:include value="/share/meta.jsp" />
<script type="text/javascript">
$(function(){
	$("#divradius").dialog({autoOpen: false, show: "blind", hide: "explode", modal: true, resizable: false, width: 480});
});
<s:if test="#session.loginMember.role == \"S\"">
function setRadius(){
	if ($('#radius').val() !== '') {
	    radius = parseInt($('#radius').val());
	    var point = new BMap.Point(lng, lat)
	    marker = new BMap.Marker(point);
	    map.addOverlay(marker);
	    marker.setAnimation(BMAP_ANIMATION_BOUNCE); 
	    var label = new BMap.Label(nick, {offset:new BMap.Size(20,-10)});
	    marker.setLabel(label);
	    $('#lnglat').html('经度：' +'<input name="lng" type="text" value=' +lng +'>' + '&nbsp;&nbsp;纬度：' +'<input name="lat" type="text" value=' +lat +'>'+'&nbsp;&nbsp;');
	
	    circle = new BMap.Circle(point, radius);
	    map.addOverlay(circle);
	    $('#divradius').dialog('close');
	}
}
</s:if>
</script>
</head>
<body>
	<s:include value="/share/header.jsp"/>
	<div id="content">
		<s:include value="/basic/nav.jsp" />
		<div id="container" style="width: 800px;height: 100%;overflow: hidden;float: right;margin-top: 20px;
			 padding-bottom: 10px;background: White;border: 1px solid #CCCCCC;">
			<h1
				style="height: 30px; line-height: 30px; padding-left: 10px; color: #000;">地理位置</h1>
			<s:hidden theme="simple" id="id" name="member.id" />
			<div id="allmap"></div>
			<div id="message"><s:property value="member.name"/>的当前位置：<span id="lnglat">
				<s:if test="member.longitude != null && member.latitude != null">
				经度：<input name="lng" type="text" value="<s:property value="member.longitude"/>"/>&nbsp;&nbsp;&nbsp;&nbsp;
				纬度：<input name="lat" type="text"value="<s:property value="member.latitude"/>"/></s:if>
			</span><span><input type="button" value="保存" onclick="javascript:onSave();"/></span></div>
		</div>					
	</div>
	<s:include value="/share/footer.jsp"/>
	<s:if test="#session.loginMember.role == \"S\"">
	<div id="divradius" title="服务半径">
		<div>您的服务半径（米）：<input type="text" name="member.radius" id="radius"></input></div>
		<div><button onclick="setRadius()">确定</button></div>
	</div>
	</s:if>	
</body>
</html>
<script type="text/javascript">
var marker, circle, radius;
var nick = '<s:property value="member.name" escape="false"/>';
var city = '<s:property value="member.city" escape="false"/>';
if (city == '') city = '武汉';
var lng = <s:if test="member.longitude == null">0</s:if><s:else><s:property value="member.longitude"/></s:else>,
	lat = <s:if test="member.latitude == null">0</s:if><s:else><s:property value="member.latitude"/></s:else>;
if (lng == 0 || lat == 0) {
	/* var point = new BMap.Point(lng, lat);
	map.centerAndZoom(point, 15);
	marker = new BMap.Marker(point);
    map.addOverlay(marker);
    marker.setAnimation(BMAP_ANIMATION_BOUNCE);
    var label = new BMap.Label(nick, {offset:new BMap.Size(20,-10)});
    marker.setLabel(label);
	var radius = '<s:property value="member.radius" escape="false"/>';
	circle = new BMap.Circle(point, radius);
	map.addOverlay(circle); */
	lat = 30.53069759743673;
	lng = 114.31637048721313;
} 
var center = new qq.maps.LatLng(lat, lng);
var map = new qq.maps.Map("allmap", {
	center: center,
	zoom: 20
});
var marker = new qq.maps.Marker({
    position: center,
    map: map,
    animation:qq.maps.MarkerAnimation.BOUNCE
});
/* map.enableScrollWheelZoom();
map.addEventListener("click",function(e){
    lng = e.point.lng;
    lat = e.point.lat;
    if (marker) map.removeOverlay(marker);
    if (circle) map.removeOverlay(circle);
    <s:if test="#session.loginMember.role == \"S\"">
    $("#divradius").dialog('open');
    </s:if>
    <s:else>
    var point = new BMap.Point(lng, lat)
    marker = new BMap.Marker(point);
    map.addOverlay(marker);
    marker.setAnimation(BMAP_ANIMATION_BOUNCE); 
    var label = new BMap.Label(nick, {offset:new BMap.Size(20,-10)});
    marker.setLabel(label);
    $('#lnglat').html('经度：' +'<input name="lng" type="text" value=' +lng +'>' + '&nbsp;&nbsp;纬度：' +'<input name="lat" type="text" value=' +lat +'>'+'&nbsp;&nbsp;');
    </s:else>
}); */
var listener = qq.maps.event.addListener(
    map,
    'click',
    function(event) {
    	 lng = event.latLng.getLat();
    	 lat = event.latLng.getLng();
    	 <s:if test="#session.loginMember.role == \"S\"">
    	    $("#divradius").dialog('open');
    	    </s:if>
    	    <s:else>
    	   /*  var point = new BMap.Point(lng, lat)
    	    marker = new BMap.Marker(point);
    	    map.addOverlay(marker);
    	    marker.setAnimation(BMAP_ANIMATION_BOUNCE); 
    	    var label = new BMap.Label(nick, {offset:new BMap.Size(20,-10)});
    	    marker.setLabel(label); */
    	    marker.position.lat = lat;
    	    marker.position.lng = lng;
    	    var marker2 = new qq.maps.Marker({
    	        position: event.latLng,
    	        map: map,
    	        animation:qq.maps.MarkerAnimation.BOUNCE
    	    });
    	    marker = marker2;
    	    $('#lnglat').html('经度：' +'<input name="lng" type="text" value=' + lat +'>' + '&nbsp;&nbsp;纬度：' +'<input name="lat" type="text" value=' + lng +'>'+'&nbsp;&nbsp;');
    	    </s:else>
    }
);
	
function onSave() {
	var lng = $("input[name='lng']").val();
	var lat = $("input[name='lat']").val();
    var parms = 'id=' + $('#id').val() + '&member.longitude=' + lng + '&member.latitude=' + lat + '&member.radius=' + radius;
    $.ajax({url: 'locat!save.asp', type: 'post', data: parms, 
    	success: function(resp) {
    		alert('地理位置已经成功保存！');
    		 window.location.reload();
    	}
    });
}
</script>

