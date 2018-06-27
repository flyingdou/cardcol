<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>视频播放</title>
    <link type="text/css" href="css/bootstrap.css" rel="stylesheet">
    <link type="text/css" href="css/base.css" rel="stylesheet">
    <link type="text/css" href="css/index.css" rel="stylesheet">
    
    <script type="text/javascript" src="js/jquery-2.2.0.min.js"></script>
    <script type="text/javascript" src="../script/cyberplayer.min.js"></script>
    <script src="../script/audio.js?v=1100000" type="text/javascript"></script>
</head>
<body style="background-color: #51535a">
<div class="planVideo container">
    <div class="play">
        <%-- <div class="video_bg" style="display: none"></div>
        <video style="width: 100%;display: block;"  class="video" controls="controls"><!-- src="images/choupichong.mp4" -->
        	<source src="http://www.kphp.org/html5/kphp.mp4" type="video/mp4">
        	您的浏览器不支持 video 标签!!
        </video> --%>
		<div class="" style="margin-left: 20px" id = "playercontainer1"></div>
		<script type="text/javascript">
			var player = cyberplayer("playercontainer1").setup({ 
				width : "96%", height : 270, 
				backcolor : "#FFFFFF", 
				stretching : "uniform", 
				file : "C:\Users\Public\Videos\Sample Videos.mp4",							
				autoStart : true, 
				repeat : "always", 
				volume : 100,
				controlbar : "top", 
				ak : "vtpI8z2Q4G1KvSKunvQaRDKK", 
				sk : "uuB5rlXoAWxdcxUsRn3sN8PmWooKdeh1"
			}); 
		</script>
<!--
            <div class="play_paused">
                <img src="images/play_03.png" class="playImg">
                <img src="images/play_icon2_07.png"  class="pauseImg" style="display: none">
            </div>
            <div class="play_icon">
                <div class="voice"><img src="images/play_icon_03.png"></div>
                <div class="full"><img src="images/play_icon_05.png"></div>
            </div>
-->

    </div>
    <div class="play_txt">
        <div class="line">
            <div class="dashed"></div>
        </div>
        <div class="txt"><span class="Time">00:15 <s:property value="course.countdown"/></span><p class="fr">还剩下<i>2<s:property value="cycleCount"/></i>次循环</p></div>
        <div class="txt"><p>第一组&nbsp;&nbsp;&nbsp;重复<span>8</span>次</p><p class="fr">重量&nbsp;<span>40</span>kg</p></div>
    </div>

    <div class="playXq">
        <h4>动作描述</h4>
        <p><span></span>上举和下落时全身保持直立</p>
        <p><span></span>两臂保持直伸</p>
        <p><span></span>意念集中在三角肌前束</p>
        <h4>注意事项</h4>
        <p><span></span>上举和下落时全身保持直立</p>
        <p><span></span>两臂保持直伸</p>
        <p><span></span>意念集中在三角肌前束</p>
    </div>
</div>
</body>
</html>