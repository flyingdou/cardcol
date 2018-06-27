<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="h" uri="/html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<style type="text/css">
body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;}
</style>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.5&ak=377630d3da1d87a6b112edbc3671c8e5"></script>
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<title>地址解析</title>
</head>
<body>
<div id="content" style="height:10%;overflow: auto;"></div>
<div>
<div id="allmap" style="display: none;float: left; width: 70%; height:90%;overflow: auto;"></div>
<div id="msg" style="float:right; width: 30%;height: 90%;overflow: auto"></div>
</div>
</body>
</html>
<script type="text/javascript">
var city = "广州市";	//当前城市,动态取得当前城市
var currentPosition = "广东省广州市天河区车陂BRT";	//传入当前位置
var positions = [];
for (var i = 0; i < 1000; i++) {
	var obj = {"name": "广州市天河区百脑汇电脑城"};
	positions[i] = obj;
}
var map = new BMap.Map("allmap");
map.centerAndZoom(city, 12);
var myGeo = new BMap.Geocoder();
var point1, point2;
myGeo.getPoint(currentPosition, function(point) {
	if (point) {
		point1 = point;
		for (var i = 0 ; i < 1000; i++) {
			myGeo.getPoint(positions[i].name, function(point){
				  if (point) {
					  point2 = point;
					  $('#content').append("<div><span>test1</span><span>从广东省广州市天河区车陂BRT 到  广州市天河区百脑汇电脑城</span><span>距离</span><span>" + map.getDistance(point1,point2) + "</span>米<span><a href='javascript:doSearch();'>查看路线</a></span></div>");
				  }
			}, city);
		}
	}
}, city);
function doSearch() {
	$('#allmap').css('display', 'block');
	var transit = new BMap.TransitRoute(map, {
	  renderOptions: {map: map,panel:"msg"},
	  policy:BMAP_TRANSIT_POLICY_AVOID_SUBWAYS   //不乘地铁  
	    //BMAP_TRANSIT_POLICY_LEAST_TIME	最少时间。
	    //BMAP_TRANSIT_POLICY_LEAST_TRANSFER	最少换乘。
	    //BMAP_TRANSIT_POLICY_LEAST_WALKING	最少步行。
	    //BMAP_TRANSIT_POLICY_AVOID_SUBWAYS	不乘地铁。(自 1.2 新增)
	});
	transit.search(point1, point2);
}
</script>
