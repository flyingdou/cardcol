<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="https://apps.bdimg.com/libs/jquerymobile/1.4.5/jquery.mobile-1.4.5.min.css">
    <script src="${pageContext.request.contextPath}/share45/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/share45/js/jquery.mobile-1.4.5.min.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=1.4"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/library/SearchControl/1.4/src/SearchControl_min.js"></script>
         <script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1262657827'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s19.cnzz.com/stat.php%3Fid%3D1262657827' type='text/javascript'%3E%3C/script%3E"));</script>
    <link rel="stylesheet" href="http://api.map.baidu.com/library/SearchControl/1.4/src/SearchControl_min.css" />
    <link href="http://api.map.baidu.com/library/TrafficControl/1.4/src/TrafficControl_min.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="http://api.map.baidu.com/library/TrafficControl/1.4/src/TrafficControl_min.js"></script>
    	    <style type="text/css"> 
        #cnzz_stat_icon_1262657827{
        display:none}
        .wxtip{background: rgba(0,0,0,0.8); text-align: center; position: fixed; left:0; top: 0; width: 100%; height: 100%; z-index: 998; display: none;}
.wxtip-icon{width: 52px; height: 67px; background: url(weixin-tip.png) no-repeat; display: block; position: absolute; right: 20px; top: 20px;}
.wxtip-txt{margin-top: 107px; color: #fff; font-size: 16px; line-height: 1.5;}
    </style>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/share45/css/demo5.css"/>
<title>签到详情</title>
</head>
<body>

<div data-role="page"class="pageCss ">
    <div data-role="header" data-position="fixed" class="headerCss"data-tap-toggle="false">
        <a href="#" class="ui-btn ui-btn-left ui-corner-all ui-shadow ui-nodisc-icon ui-btn-icon-left headerLogo">
            <img src="${pageContext.request.contextPath}/share45/img/logo.png" alt=""width="88"/>
        </a>
        <h1></h1>
        <a href="javascript:downLoadAPP()" class="ui-btn ui-corner-all ui-shadow  ui-btn-icon-left downLoadApp"id="thisbtn1">立即下载APP</a>
    </div>

    <div data-role="main" class="ui-content main " style="background-color: #f8f8f8!important;">
        <ul data-role="listview" data-inset="true"class="listW noshadow noborder  uLH">
            <li class="ulC"style=" background:url('picture/${map['image']}')no-repeat scroll 0 0px/100% auto;!important;"></li>
            <li  data-icon="false" class="noborder lishow userLi ">
        <div class="userDiv">
                <img src="picture/${map['image']}"class="BodybuildingCss userImg">
        </div>
            </li>
            <li  data-icon="false" class="noborder padd5 lishow1 mt-12" >
                <p class="h2Css1 left h2css2 userP">
                    <span class="userSpan">
                        <span class="AllCity userAdress" style="font-size: 16px!important;">${map['name']}</span>
                           <span class="h2Css1 h2css2">
                                 <span class="spanCssIMG1A1"style="font-size: 14px!important;">${map['province']}-${map['county']}-${map['city']}</span>
                            </span>
                    </span>
                </p>
                <div class="fix"></div>
            </li>
        </ul>


        <div class="fix"></div>
            <li class="doneL"style="margin:12px 14px 10px 12px!important;height: 125px!important;background-color: #ffffff;border-radius:8px;box-shadow: 0 1px 3px #888888; display: block!important;box-sizing: border-box!important;position: relative">
            <span class="doneS">已完成健身</span>
              <span class="done"><img src="${pageContext.request.contextPath}/share45/img/done.jpg" alt=""height="60px"/></span>

            <div class="qdC">
                <img src="${pageContext.request.contextPath}/share45/img/Thirty-six.png" alt=""width="100"/>
                <div class="num">${count}</div>
            </div>
        <div class="thisTime">${map['signDate']}</div>
          </li>

        <li class="doneL1"id="doneL1">
          <div class="map"id="container"></div>

            <div class="Iamhear">我在这里，一起来健身吧! &nbsp;</div>
        </li>

    </div>

    <div data-role="footer" data-position="fixed"class="footerCss"data-tap-toggle="false">
        <a href="javascript:downLoadAPP()" class="footerA"id="thisbtn2"><span class="footerAspan"></span>下载E卡通</a>
    </div>
</div>

</body>

<script type="text/javascript">
    var map = new BMap.Map("container");    //创建地图容器
    var point = new BMap.Point(${map['longitude']},${map['latitude']});
    var marker = new BMap.Marker(point);  // 创建标注
    map.addOverlay(marker);//创建一个点
    map.centerAndZoom(point, 15);                       //设立中心点和地图级别，就是初始化地图
    var myLabel = new BMap.Label("<a style='color:white;text-decoration:none;white-space:normal;' >${map['cname']}</a>",//为lable填写内容
            {offset:new BMap.Size(-40,5),                  //label的偏移量，为了让label的中心显示在点上
                position:point});                                //label的位置
    myLabel.setStyle({                                   //给label设置样式，任意的CSS都是可以的
        fontSize:"12px",               //字号
        //边
       // height:"",                //高度
       width:"80px",                 //宽
        textAlign:"center",            //文字水平居中显示
        lineHeight:"18px", //行高，文字垂直居中显示
        //背景图片，这是房产标注的关键！
        border: "1px solid black",
        background:"#ff4401",
        cursor:"pointer"
    });
    myLabel.setTitle("我是文本标注label");               //为label添加鼠标提示
    map.addOverlay(myLabel);
    

    var ua = navigator.userAgent;
    var isWeixin =  !!/MicroMessenger/i.test(ua);
    function weixinTip(ele){
	var ua = navigator.userAgent;
	var isWeixin = !!/MicroMessenger/i.test(ua);
	if(isWeixin){
		ele.onclick=function(e){
			window.event? window.event.returnValue = false : e.preventDefault();
			document.getElementById('JweixinTip').style.display='block';
		}
		document.getElementById('JweixinTip').onclick=function(){
			this.style.display='none';
		}
	}
}
var btn1 = document.getElementById('thisbtn2');//下载一
weixinTip(btn1);
var btn2 = document.getElementById('thisbtn1'); //下载二
weixinTip(btn2);
/* var btn3 = document.getElementById('thisbtn'); //下载二
weixinTip(btn3); */
    
function downLoadAPP(){
	var sUserAgent = navigator.userAgent.toLowerCase();
	var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";
	var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
	var bIsAndroid = sUserAgent.match(/android/i) == "android";
	if (bIsIpad || bIsIphoneOs) {
		location.href = "https://itunes.apple.com/cn/app/%E5%81%A5%E8%BA%ABe%E5%8D%A1%E9%80%9A/id1218667055?mt=8";
	}else{
		location.href = "http://www.ecartoon.com.cn/app/ecartoon-V1.4.apk";	
	}
}
</script>
</html>