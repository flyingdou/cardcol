<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
<title>${a.name}-卡库健身</title>
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
		<img src="${a.creator.image}" class="img-responsive" id="image"/>
    </div>
    <div style="margin-left:170px; margin-top:-100px;">
    	<span style="line-height:30px; font-size:16px; color:#FFF">${a.name}</span><br />
        <span style="color:#CCC">发 布 人 ：${a.creator.name}</span><br />
        <span style="color:#CCC">裁 判 ：  ${a.judgeID}</span><br />
        <span style="color:#CCC">参 加 人 数 ： ${size} 人</span><br />
    </div>
</div>
<div style="width:100%; height:10px; background-color:#E4E4E4; margin-top:0px;"></div>
	<div style=" margin-top:10px;">
		<img src="images/mission.png"/ class="img-responsive" style="margin-left:50px; width:30px; height:30px;">
		<div style="margin-left: 30px; color:#3c3c3c; padding-top:5px;">挑 战 目 标</div>
        <div style=" margin-left:120px; margin-top:-35px; color:#707070">${a.days} 天 内 ${a.action}  ${addornot} ${a.value} ${sport} <hr /></div>
    </div>   
    <div>
    	<img src="images/award.png"/ class="img-responsive" style="margin-left:50px; width:30px; height:30px;">
		<div style="margin-left: 30px; color:#3c3c3c; padding-top:5px;">成 功 奖 励</div>
        <div style="margin-left:120px; margin-top:-35px; color:#707070">${a.award}<hr /></div>
    </div>
    <div style="margin-top:-10px;">
    	<img src="images/punish.png"/ class="img-responsive" style="margin-left:50px; width:27px; height:27px;">
		<div style="margin-left: 30px; color:#3c3c3c; padding-top:5px;">失 败 处 罚</div>
        <div style="margin-left:120px; margin-top:-35px; color:#707070">向 ${a.institution.name} 支 付 ${a.amerceMoney} 元 <hr /></div>
    </div>
    <div style="margin-top:-10px; height:100px;">
    	<img src="images/note.png"/ class="img-responsive" style="margin-left:50px; width:30px; height:30px;">
		<div style="margin-left: 30px; color:#3c3c3c; padding-top:5px;">注 意 事 项</div>
        <div style="margin-left:120px; margin-top:-35px; color:#707070">遵 守 竞 赛 规 则 ， 2333333333</div>
    </div>
    <div style="width:100%; height:20px; background-color:#EBEBEB; margin-bottom:13%;"></div>
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
