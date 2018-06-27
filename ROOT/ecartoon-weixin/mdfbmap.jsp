<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head> 
	<base href="<%=basePath%>" >
    <title>门店分布</title>  
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <!--css-->  
    <!-- <link href="ecartoon-weixin/style/demo.css" rel="stylesheet" type="text/css" />   -->
    <!--javascript-->  
    <script src="ecartoon-weixin/js/jquery-2.1.1.min.js" type="text/javascript"></script> 
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=22GR1VoZdYMI3SLs8eGRI2HmhvhC1BQQ"></script>
</head>  
<body>  
    <div class="demo_main" style="width: 100%;height: auto;">  
        <!-- <fieldset class="demo_title">  
         	 门店分布
        </fieldset>  --> 
        <!-- <fieldset class="demo_content"> -->
            <div style="min-height: 1000px; width: 100%;" id="map">  
            </div>  
            <script type="text/javascript">  
                var markerArr = [
                   /*  { title: "门店一", point: "114.264531,30.157003"},  
                    { title: "门店二", point: "114.330934,30.113401"},  
                    { title: "门店三", point: "114.312213,30.147267"},  
                    { title: "门店四", point: "114.372867,30.134274"} */
                ];  
                

        		// 百度地图API功能
        		// 创建Map实例
        		// 初始化地图,设置中心点坐标和地图级别
        		// 循环生成新的地图点  
        		// 按照地图点坐标生成标记  
        		// 创建信息窗口对象
        		var clubList = ${club.clubList};
        		var map = new BMap.Map("map");    
        		map.centerAndZoom(new BMap.Point(${club.longitude}, ${club.latitude}), 14);  
        		var point = [],marker = [],info = [];
       			for(var i = 0;i < clubList.length;i++){
          			(function(i){
          				point[i] = new BMap.Point(clubList[i].longitude, clubList[i].latitude);
             			marker[i] = new BMap.Marker(point[i]);
             			map.addOverlay(marker[i]);
             			info[i] = new window.BMap.InfoWindow("<p style=’font-size:12px;lineheight:1.8em;’>"+ clubList[i].name + "</p>"); 
                  	marker[i].addEventListener("click", function(){   
                       this.openInfoWindow(info[i]);
                   });
          	   })(i);
          	}
             
            
  
           /*      var clubList = ${club.clubList};
                for(i = 0; i < clubList.length; i++){
                	var dd = {title:clubList[i].name, point:clubList[i].longitude+","+clubList[i].latitude}
                	markerArr.push({lon:clubList[i].longitude,lat:clubList[i].latitude});
                }
                function map_init() {  
                    var map = new BMap.Map("map"); // 创建Map实例  
                    var point = new BMap.Point(${club.longitude}, ${club.latitude}); //地图中心点，武汉市  
                    map.centerAndZoom(point, 16); // 初始化地图,设置中心点坐标和地图级别。  
                    map.enableScrollWheelZoom(true); //启用滚轮放大缩小  
                    
                    var point = new Array(); //存放标注点经纬信息的数组  
                    var marker = new Array(); //存放标注点对象的数组  
                    var info = new Array(); //存放提示信息窗口对象的数组  
                    for (var i = 0; i < markerArr.length; i++) {   */
                      /*   var p0 = markerArr[i].point.split(",")[0]; //  
                        var p1 = markerArr[i].point.split(",")[1]; //按照原数组的point格式将地图点坐标的经纬度分别提出来   */
                    /*     point[i] = new window.BMap.Point(markerArr[i].lon,markerArr[i].lat); //循环生成新的地图点  
                        marker[i] = new window.BMap.Marker(point[i]); //按照地图点坐标生成标记  
                        map.addOverlay(marker[i]);  */ 
                        /* marker[i].setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画   */
               /*          var label = new window.BMap.Label(markerArr[i].title, { offset: new window.BMap.Size(20, -10) });  
                        marker[i].setLabel(label);  
                        info[i] = new window.BMap.InfoWindow("<p style=’font-size:12px;lineheight:1.8em;’>" + markerArr[i].title + "</br></p>"); // 创建信息窗口对象
                        marker[i].addEventListener("click", function(){       
                            this.openInfoWindow(info[i]);
                        });
                    }  
                }  
                //异步调用百度js  
                 function map_load() {  
                    var load = document.createElement("script");  
                    load.src = "http://api.map.baidu.com/api?v=1.4&callback=map_init";  
                    document.body.appendChild(load);  
                }  
                window.onload = map_load; */  
            </script>  
    </div>  
</body>  
</html>