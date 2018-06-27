<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="map-first-content" id="allmap"></div>	
<script type="text/javascript">
var map = new BMap.Map("allmap");
var lng = [], lat = [], point = [], marker = [], i = 0;
var index = '<s:property value="index"/>';
<s:iterator value="#request.list">
	point[i] = new BMap.Point(<s:property value="longitude"/>, <s:property value="latitude"/>);
	//map.centerAndZoom(point[i], 15);
	marker[i] = new BMap.Marker(point[i]);
    map.addOverlay(marker[i]);
    marker[i].setAnimation(BMAP_ANIMATION_BOUNCE);
    marker[i].setLabel(new BMap.Label('<s:property value="name"/>', {offset:new BMap.Size(20,-10)}));
    marker[i].addEventListener('mouseover', function() {
    	if(index == '1'){
    		var content =
       		 ' <div id="showMessage" class="showMessage">'+
       			'<div class="name">'+
       			'	<h3><span><s:property value="name"/></span><a onclick="javascript: goHome(<s:property value="id"/>);">去看看</a></h3>'+
       			'</div>'+
       			'<div class="club-img">'+
       			'	<img src="picture/<s:property value="image"/>" width="95" height="95" border="0" alt="教练">'+
       			'</div>'+
       			'	<div class="score_img-first" style="width:230px;margin-left:5px;">'+
       			'		<p style="width:230px;"><u>评分：</u><span><s:property value="member_grade"/>分</span></p>'+
       			'		<p style="width:230px;"><u>电话：<s:property value="tell"/></u></p>'+
       			'		<p style="width:230px;">地址：<s:property value="address"/></p>'+
       			'	</div>'	+	
       			'</div>'+
       		'</div>';
    	}else{
    		var content =
       		 ' <div id="showMessage" class="showMessage">'+
       			'<div class="name">'+
       			'	<h3><span><s:property value="name"/></span><a onclick="javascript: goHome(<s:property value="id"/>);">去看看</a></h3>'+
       			'</div>'+
       			'<div class="club-img">'+
       			'	<img src="picture/<s:property value="image"/>" width="95" height="95" border="0" alt="俱乐部">'+
       			'</div>'+
       			'	<div class="score_img-first" style="width:230px;margin-left:5px;">'+
       			'		<p style="width:230px;"><u>评分：</u><span><s:property value="member_grade"/>分</span></p>'+
       			'		<p style="width:230px;"><u>电话：<s:property value="tell"/></u></p>'+
       			'		<p style="width:230px;">地址：<s:property value="address"/></p>'+
       			'	</div>'	+	
       			'</div>'+
       		'</div>';
    	}
    	this.openInfoWindow(new BMap.InfoWindow(content));
    });
    i++;
</s:iterator>
map.centerAndZoom('<s:property value="#session.currentCity" escape="false"/>', 15);
map.enableScrollWheelZoom();
</script>