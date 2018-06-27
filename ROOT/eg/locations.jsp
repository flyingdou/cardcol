<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
    <title>自适应显示多个点标记</title>
    <link rel="stylesheet" href="http://cache.amap.com/lbs/static/main1119.css"/>
    <script src="http://cache.amap.com/lbs/static/es5.min.js"></script>
    <script src="http://webapi.amap.com/maps?v=1.3&key=您申请的key值"></script>
    <script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>
</head>
<body>
	<div id="container"></div>
	<input id="locations" type="hidden" value="<s:property value="pageInfo.items"/>" />
</body>
<script src="${pageContext.request.contextPath}/eg/js/jquery-1.11.1.min.js"></script>
<script>
		var locationArray =$.parseJSON($("#locations").val());
		var fristOneObj = locationArray[0];
		
	    var map = new AMap.Map('container', {
	        resizeEnable: true,
	        center: [fristOneObj.longitude, fristOneObj.latitude],
	        zoom: 13
	    });    
	    map.clearMap();  // 清除地图覆盖物
	    
	    var markerArray = new Array();
	    for (var i = 0; i < locationArray.length ; i++) {
	    	
	    	var longitude = locationArray[i].longitude;
	    	var latitude = locationArray[i].latitude;
	    	
	    	var obj = {
		        icon: 'http://webapi.amap.com/theme/v1.3/markers/n/mark_b' + parseInt(i + 1) + '.png',
		        position: [longitude, latitude]
		    }
	    	   
	    	markerArray.push(obj); 
	    }
	    	     
	    var markers = markerArray;
	    
	    // 添加一些分布不均的点到地图上,地图上添加三个点标记，作为参照
	    markers.forEach(function(marker) {
	        new AMap.Marker({
	            map: map,
	            icon: marker.icon,
	            position: [marker.position[0], marker.position[1]],
	            offset: new AMap.Pixel(-12, -36)
	        });
	    });
	    var center = map.getCenter();
	    
	    // 添加事件监听, 使地图自适应显示到合适的范围
	    AMap.event.addDomListener(document.getElementById('setFitView'), 'click', function() {
	        var newCenter = map.setFitView();
	    });
</script>
</html>