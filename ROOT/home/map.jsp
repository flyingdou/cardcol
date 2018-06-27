<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="健身俱乐部的位置，附近的健身乐部" />
<meta name="description" content="健身地图显示所有的健身俱乐部的位置，你可以看到你所在地区附近的健身俱乐部。" />
  <title>健身房,健身俱乐部分布图</title>
  <link type="text/css" href="css/bootstrap.css" rel="stylesheet">
 <link type="text/css" href="css/base.css" rel="stylesheet">
  <link rel="stylesheet" type="text/css" href="css/pulic-1.css">
  <link rel="stylesheet" type="text/css" href="css/smoothness/template.css">
<script type="text/javascript" src="script/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="script/jquery-ui.js"></script>
<script src="http://api.map.baidu.com/api?v=1.4" type="text/javascript"></script>
<style type="text/css">
a,.libox h3 u a,.map-second-content li {
	text-decoration: none;
}

a:hover,.libox h3 u a:hover {
	text-decoration: underline;
}

.map_page_main {
	width: 960px;
	margin: 37px auto;
}
/*
.map-first-content {
	width: 678px;
	float: left;
}
*/

.map-second-content {
	width: 282px;
	height: 28px;
	float: left;
	margin:15px auto auto 0px;
	background: url(images/compass.gif);
}

.map-second-content ul {
	float: left;
	list-style: none;
}

.map-second-content li {
	display: inline;
	line-height: 23px;
	color: #676BCE;
	font-size: 12px;
	padding: 4px 58px 0 58px;
	float: left;
	cursor: pointer;
	border-right: 1px #E5E5E5 solid;
}

.map-top {
	height: 35px;
	width: 960px;
	background: url(images/2.gif)
}

.tablist ul {
	font-size: 12px;
	list-style: none;
}

.tablist ul li {
	width: 281px;
	height: 120px;
	float: left;
	float: left;
	background: url(images/ico_map_10.gif) no-repeat;
	border-bottom: 1px #EBEBEB solid;
	cursor: pointer;
	border-right: 1px solid #EBEBEB;
}

.libox {
	width: 265px;
	height: 139px;
	float: left;
	padding: 7px 0 0 10px;
}

.libox h3 {
	float: left;
	width: 265px;
	font-size: 12px;
	font-weight: normal;
	color: #676BCE;
	margin: 6px auto 0px 0px;
}

.libox h3 span {
	float: left;
	padding: 2px 0 0 10px;
}

.libox h3 a b {
	float: left;
	width: 18px;
	color: white;
	text-align: center;
	padding-bottom: 8px;
	padding-left: 1px;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 14px;
}

.libox h3 span a,.libox h3 u a {
	color: #676BCE;
	float: left;
}

.libox h3 font {
	float: left;
	padding-top: 2px;
}

.libox h3 u {
	float: left;
	padding: 2px 8px 0 0;
}

.score_img {
	float: left;
	width: 235px;
	padding-left: 30px;
}

.score_img-first {
	float: left;
	width: 170px;
}

.score_img-first p {
	float: left;
	width: 170px;
	margin: 0px;
	padding: 2px 0px;
}

.score_img-second {
	float: right;
}

.score_img-first span {
	color: red;
	float: left;
}

.score_img-first a {
	float: left;
	color: #676BCE;
	padding: 2px 0 0 5px;
}

.score_img-first u {
	float: left;
	text-decoration: none;
}

.location {
	float: left;
	width: 235px;
	padding-left: 30px;
}

/**---------------------------*/
.showMessage {
	width: 380px;
	height: 130px;
	font-size: 12px;
}

.name h3 span {
	font-size: 16px;
	float: left;
}

.name {
	width: 350px;
	height: 30px;
	margin-left: 15px;
}

.name h3 a {
	text-decoration: none;
	color: #676BCE;
	float: left;
	font-size: 12px;
	line-height: 20px;
	cursor: pointer;
}

.name h3 a:hover {
	text-decoration: underline;
}

.club-img {
	width: 100px;
	margin-left: 12px;
	float: left;
}
/*
.map-first-content {
	width: 675px;
	height: 489px;
	float: left;
	border: 0px;
}
*/
#allmap {
	width: 100%;
	height: 504px;
	float: left;
	overflow: hidden;
	border: 1px solid #EBEBEB;
}

#l-map {
	height: 100%;
	width: 78%;
	float: left;
	border-right: 2px solid #bcbcbc;
}

#r-result {
	height: 100%;
	width: 20%;
	float: left;
}

 .header_bot ul li:nth-child(6) a{font-weight:bold;color: #95bc28;border-top:8px #95bc28 solid;}
#tab{position:relative;height:489px;}
#tab ul{position:absolute;right:-30px;width:25px;top:30px;text-align:center;}

.tabmap{position:absolute;right:-14px; top:16px;z-index:-22;}
.tab_menu{height:380px;}
.tab_menu li{width:25px;height:190px;font-size:14px;font-weight:bold;background-color:#8abc30;color:#ffffff;cursor:pointer}
.hide{display:none;}
.tab_menu .selected{background-color:#f47f1a;}
</style>
<script type="text/javascript">

	$(document).ready(
			function() {
				var $tab_li = $('#tab ul li');
				var index = 0;
				$tab_li.hover(function() {
					$(this).addClass('selected').siblings().removeClass(
							'selected');
					var move = $tab_li.index(this);
					if(index != move){
						$.ajax({
							url : 'map.asp',
							type : 'post',
							data : 'index=' + move,
							success : function(msg) {
								$('#right-2').html(msg);
								$('.tab_menu').find('li').eq(move).addClass('selected').siblings().removeClass("selected");
								index = move;
							}
						})
					}
				});
			});
	function goHome(memberId){
		if(index == 1){
			url = "coach.asp?member.id="+memberId;
			window.open(url,"homeWindow"+memberId);
		}else{
			url = "club.asp?member.id="+memberId;
			window.open(url,"homeWindow"+memberId);
		}
		
	}
</script>
</head>
 <body>
	<s:include value="/newpages/head.jsp" />
	<div class="top_bg"></div>
	<div class="map_page_main">
                 <div id="tab">
                     <ul class="tab_menu">
                        <li class="selected">健身俱乐部分布图</li>
                        <li>健身教练服务区域图</li>
                     </ul>
                     <div class="tab_box">
		        		<div id="right-2"> 
		        			<s:include value="/home/map_middle.jsp" />	
		        		</div>
                     </div>
                     <div class="tabmap"><img src="images/map3.png"height="489px"width:16px;/></div>
                 </div>
                
		<div class="map-second-content" style="display: none;">
			<ul>
				<li>默认</li>
				<li>评分</li>
			</ul>
		</div>
<!-- 		<div class="tablist" style="display: none;"> -->
<!-- 			<ul> -->
<%-- 				<s:iterator value="#request.clublist"> --%>
<!-- 				<li> -->
<!-- 					<div class="libox"> -->
<!-- 						<h3> -->
<%-- 							<a href=""><b>A</b></a><span><a href=""><s:property value="nick"/></a></span><font>&nbsp;-&nbsp;</font> --%>
<!-- 							<u class="detailword"><a href="">详情</a></u> -->
<!-- 						</h3> -->
<!-- 						<div class="score_img"> -->
<!-- 							<div class="score_img-first"> -->
<%-- 								<p><u>评分：</u><span><s:property value="avgGrade"/>分</span><a>查看评论</a></p> --%>
<%-- 								<p><u>电话：<s:property value="tell"/>,<s:property value="mobilephone"/></u></p> --%>
<%-- 								<p>地址：<s:property value="address"/></p> --%>
<!-- 							</div> -->
<!-- 							<div class="score_img-second"> -->
<%-- 								<img src="picture/<s:property value="image"/>" width="55" height="55" alt="俱乐部图片"> --%>
<!-- 							</div> -->
<!-- 						</div> -->
							
<!-- 					</div> -->
<!-- 				</li> -->
<%-- 				</s:iterator> --%>
<!-- 			</ul> -->
<!-- 		</div> -->
	</div> 
			<s:include value="/newpages/footer.jsp"></s:include>
 </body>
</html>