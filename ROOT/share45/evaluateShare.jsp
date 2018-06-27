<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://apps.bdimg.com/libs/jquerymobile/1.4.5/jquery.mobile-1.4.5.min.css">
    <script src="${pageContext.request.contextPath}/share45/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/share45/js/jquery.mobile-1.4.5.min.js"></script>
         <script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1262657827'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s19.cnzz.com/stat.php%3Fid%3D1262657827' type='text/javascript'%3E%3C/script%3E"));</script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/share45/css/demo7.css"/>
    <link href="https://cdn.bootcss.com/emojione/2.2.2/assets/css/emojione.min.css" rel="stylesheet">
<link href="https://cdn.bootcss.com/emojione/2.2.2/assets/sprites/emojione.sprites.css" rel="stylesheet">
<script src="https://cdn.bootcss.com/emojione/2.2.2/lib/js/emojione.min.js"></script>
    <style type="text/css">
       ul li{
           list-style: none;
           float: left;
           display: inline-block;
       }
      
        
        #cnzz_stat_icon_1262657827{
        display:none}
.wxtip{background: rgba(0,0,0,0.8); text-align: center; position: fixed; left:0; top: 0; width: 100%; height: 100%; z-index: 998; display: none;}
.wxtip-icon{width: 52px; height: 67px; background: url(weixin-tip.png) no-repeat; display: block; position: absolute; right: 20px; top: 20px;}
.wxtip-txt{margin-top: 107px; color: #fff; font-size: 16px; line-height: 1.5;}
  
    </style>
</head>
<body>

<div data-role="page"class="pageCss ">

<div data-role="header" data-position="fixed" class="headerCss"data-tap-toggle="false">
        <a href="#" class="ui-btn ui-btn-left ui-corner-all ui-shadow ui-nodisc-icon ui-btn-icon-left headerLogo">
            <img src="${pageContext.request.contextPath}/share45/img/logo.png" alt=""width="88"/>
        </a>
        <h1></h1>
        <a href="javascript:downLoadAPP()"id="thisbtn2" class="ui-btn ui-corner-all ui-shadow  ui-btn-icon-left downLoadApp"style="background:inherit;border: 1px solid white!important;">立即下载APP</a>
    </div>

    <div data-role="main" class="ui-content main ">
        <ul data-role="listview" data-inset="true"class="listW noshadow noborder  uLH">
            <li class="ulC"style=" background:url('picture/${map.image1 }')no-repeat scroll 0 0px/100% auto;!important;"></li>
            <li  data-icon="false" class="noborder lishow">	
                <a href="#"class="noborder bg0">
                    <img src="picture/${map.image1 }"class="BodybuildingCss"width="96" >
                    <span class="h2Css "style="font-size: 16px!important;color:white">${map.cname}</span>
                    <p class="pCss font12">
                    <span class="star""> </span>
                    <span class=" star""> </span>
                    <span class=" star "">  </span>
                    <span class=" star "">  </span>
                    <span class=" star""> </span></p>
                    <p class="pCss font14"style="height: 16px!important;">设备<span>2</span>;环境<span>2</span>;服务<span>2</span></p>
                </a>
            </li>

            <li  data-icon="false" class="noborder  lishow1" style="width:95%!important;left:.1em!important;border-top:1px solid #ffffff!important;background-color:inherit;text-shadow: none;color: white;font-size: 14px ">
                 地址:${map.address}
            </li>
        </ul>
        <div class="fix"></div>


    </div>
    <ul data-role="listview" data-inset="true"class="peopleshow" >
        <div>
            <div class="imgBox"><img src="picture/${map.image2 }" alt=""/></div>
            <div class="nameBox">
                <h7 style="font-weight: 600" >${map.name}</h7>
                <div class="num">健身${count}次</div>
            </div>
            <div class="time">  <s:date name="#request.me.evalTime" format="yyyy-MM-dd HH:mm:ss"/></div>
        </div>
        <div style="clear: both"></div>
        <p>${content }</p>
        <div style="height: 70px;width: 100%;overflow: hidden">
            <div style="width: 300px"class="fd">
             <s:if test="#request.me.image1 !=null">
             <img src="picture/${me.image1 }" alt=""  width="90"style="padding-right: 5px;max-height: 65px;border:none!importent;"/>
            </s:if>
            <s:if test="#request.me.image2 !=null">
            <img src="picture/${me.image2 }" alt="" width="90"style="padding-right: 5px;max-height: 65px;border:none!importent;"/>
            </s:if>
             <s:if test="#request.me.image3 !=null">
            <img src="picture/${me.image3 }" alt="" width="90"style="padding-right: 5px;max-height: 65px;border:none!importent;"/>
            </s:if>
            </div>
            <div class="fd1" style="position: absolute;display:none;width:90%;right:5%;border:2px solid #f2f2f2;overflow-x:scroll;overflow-y: hidden;height: 225px;top:240px;margin-left:-1em!important;z-index:30">
                <div style="position: fixed;width: 30px;height: 30px;top:-5px;right:-5px;background-color: #f4f4f4;color: #999;text-align:center;font-size:24px;line-height: 30px;z-index:999;border-radius: 50%">x</div>
                <div style="width: 300%">
                     <s:if test="#request.me.image1 !=null">
             <img src="picture/${me.image1 }" alt="" width="33%"/>
            </s:if>
            <s:if test="#request.me.image2 !=null">
            <img src="picture/${me.image2 }" alt="" width="33%"/>
            </s:if>
             <s:if test="#request.me.image3 !=null">
            <img src="picture/${me.image3 }" alt=""width="33%"/>
            </s:if>
                </div>
            </div>
        </div>


        
        
        
    </ul>
    <div data-role="footer" data-position="fixed"class="footerCss"data-tap-toggle="false">
        <a href="javascript:downLoadAPP()" class="footerA"id="thisbtn1">下载E卡通，获取健身方案</a>
    </div>
</div>

    <div class="wxtip" id="JweixinTip">
<span class="wxtip-icon"></span>
<p class="wxtip-txt">点击右上角<br/>选择在浏览器中打开</p>
</div>
</body>
 <script>
      window.onload=function(){
        var num = ${me.totailtyScore/20}-1 ;
        $(".star").removeClass('redstar');
        $(".star").removeClass('whitestar');
        $(".star").eq(num+1).prevAll().css("background","url('${pageContext.request.contextPath}/share45/img/redstar.png')no-repeat scroll 0 0 /15px auto")
        $(".star").eq(num).nextAll().css("background","url('${pageContext.request.contextPath}/share45/img/whitestar.png')no-repeat scroll 0 0 /15px auto")
    }
      
      $(function(){
          $(".fd").click(function(){
              $(".fd1").css("display","block")
          });
          var i = 0;
          $('.fd1').on('click',function(){
              i++;setTimeout(function () {
                  i = 0;
              }, 500);
              if (i > 1) {
                  $(".fd1").css("display","none");
                  i = 0;
              }
          })

      })
      
      
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