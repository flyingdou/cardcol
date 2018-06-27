<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
<title>${p.planName}-卡库健身</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
 	<style>
    	#div{
			width:100%;
			float:left;
			position:fixed; 
			bottom:0;	
		}
		#div2{
			width:100%;
			_position:absolute; 
			position:fixed !important; 
 	 		overflow:hidden;
			bottom:0px; 
			right:0px;
		}
		#bg{
			background:url(images/p1.png) no-repeat center center;
    		-webkit-background-size: cover;
   	 		-moz-background-size: cover;
    		-o-background-size: cover;
    		background-size: cover;
			height:200px;
		}
		#image{
			 padding-top:50px;
			 padding-left:20px; 
			 width:140px; 
			 height:140px; 	
		}
    </style>
</head>
<body>
<div id="bg" class="">
	<div >
		<img src="images/p1.png" class="img-responsive" id="image"/>
    </div>
    <div style="margin-left:170px; margin-top:-100px;">
    	<div style="line-height:30px; font-size:16px; color:#FFF">${p.planName}</div><br />
        <div style=" margin-top:-20px;">
       		<img src="images/x1.png" style="width:16px; height:16px;"/>
            <img src="images/x1.png" style="width:16px; height:16px;"/>
            <img src="images/x1.png" style="width:16px; height:16px;"/>
            <img src="images/x1.png" style="width:16px; height:16px;"/>
       		<img src="images/x2.png" style="width:16px; height:16px;"/>
        </div>
        <div style=" margin-top:-20px; margin-left:120px; color:#F60">${p.avgcount}</div>
        <div style="color:#CCC">${countperson}人 使 用 </div><br />
        <div style="color:#CCC; margin-top:-15px;">作 者 : ${p.member.name}</div><br />
    </div>
</div>
<div style="width:100%; height:15px; background-color:#EBEBEB;"></div>
<div>
	<div style="padding-left:3%; margin-top:5%;">计划类型</div>
    <div style="float:right; margin-right:3%; color:#999; margin-top:-20px; padding-right:20px;">${ptype}</div>
    <hr />
	<div style="padding-left:3%; margin-top:5%;">适用对象</div>
    <div style="float:right; margin-right:3%; color:#999; margin-top:-20px; padding-right:20px;">${papply}</div>
    <hr />
    <div style="padding-left:3%; margin-top:5%;">适用场景</span>
    <div style="float:right; margin-right:3%; color:#999; padding-right:20px;">${pscene}</div>
    <hr />
	<div style=" margin-top:5%;">所需器材</div>
    <div style="float:right; margin-right:3%; color:#999; margin-top:-20px; padding-right:20px;">${p.apparatuses}</div>
    <hr />
    <div style=" margin-top:5%;">计划周期</div>
    <div style="float:right; margin-right:3%; color:#999; margin-top:-20px; padding-right:20px;">${p.planDay}天</div>
    <hr />
	<div style=" margin-top:5%;">计划简介</div><br />
    <div style="height:60px; color:#999;">&nbsp;&nbsp;&nbsp;&nbsp;${p.briefing}</div>
</div>
<div style="width:100%; height:30px; background-color:#EBEBEB; margin-bottom:13%;"></div>
 	<a href="#downIOS" id="div">
	<div style="width:50%; height:100%;float:left">
    	<img src="images/iphone86.png" class="img-responsive" style="width:200%;">
    </div>
    </a>
    <a href="#downAndriod" id="div2">
    <div style="width:50%;height:100%;float:right">
    	<img src="images/android86.png" class="img-responsive" style="width:200%;">
    </div>
  	</a>
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>  
</body>
</html>
