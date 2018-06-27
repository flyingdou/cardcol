<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
<title>训练日志-卡库健身</title>
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
    </style>
 </head>
<body>
  <div style="width:100%; height:20px; background-color:#E4E4E4;"></div>
  <div class="media" style=" margin-left:5%;">
   		<a class="pull-left" href="#">
        	<div>
      			<img class="media-object img-circle" src="/images/${tr.partake.image}" style=" width:60px; height:60px;">
        	</div>
   		</a>
    	<div style=" position:absolute; margin-left:80px; margin-top:10px; font-size:16px; color:#3c3c3c;">${tr.partake.nick}</div>
        <div style="font-size:14px; color:#3c3c3c; margin-top:35px; margin-left:80px;">${tr.doneDate}</div>
  </div>
  <div style="width:100%; height:15px; background-color:#F3F3F3; margin-top:15px;"></div>
  <div>
  		<div>
        	<div style="padding-left:20px;">
        		<img src="images/time.png" class=" img-responsive"  style="width:20px; height:20px; margin-top:20px;"/>
            </div>
            <div style="color:#3c3c3c; margin-left:45px; margin-top:-20px;">运 动 时 间</div>
            <div style="color:#999; margin-top:-20px; float:right; padding-right:20px;">${tr.times} 分 钟</div>
        </div>
        <hr style="margin-top:10px;">
        <div style="padding-left:20px;">
        	<div>
        		<img src="images/content.png" class=" img-responsive" style="width:20px; height:20px; margin-top:-10px;"/>
            </div>
            <div style="color:#3c3c3c; margin-left:25px; margin-top:-20px;">运 动 内 容</div>
            <div style="color:#999; float:right; padding-right:20px; margin-top:-20px;">${tr.action}${tr.actionQuan}</div>
        </div>
        <hr style="margin-top:10px;">
        <div style="padding-left:20px;">
        	<div>
        	<img src="images/heartrate.png" class=" img-responsive" style="width:20px; height:20px; margin-top:-10px;"/>
            </div>
            <div style="color:#3c3c3c; margin-left:25px; margin-top:-20px;">运 动 心 率</div>
            <div style="color:#999; float:right; padding-right:20px; margin-top:-20px;">${tr.heartRate} / 分 钟</div>
            <div style="color:#999; float:right; padding-right:20px; margin-top:10px; font-size:12px;">使 用 卡 库 手 环 自 动 获 取 运 动 数 据 , 健 身 更 科 学</div>
        </div>
        
  </div>
  <div style="width:100%; height:15px; background-color:#F3F3F3; margin-top:40px;"></div>
  <div>
  	<div style="color:#3c3c3c3; margin-left:20px; font-size:15px; margin-top:10px;">身 体 数 据</div>
    <div>
    	<div style=" margin-left:20px; margin-top:20px;">
    		<img src="images/BMI.png" class=" img-responsive" style="width:45px; height:45px;"/>
        </div>
        <div style="margin-left:80px; margin-top:-30px; color:#505050;">
        	<div style=" margin-top:-45px;">体 质 指 数 （ B M I ）</div>
        	<div style="color:#999; font-size:12px; margin-top:-20px; float:right; padding-right:20px;">${tr.bmi}</div><br>
        	<div style="color:#999; font-size:12px; margin-top:-15px;">${data}</div>
        </div>
    </div>
    <hr style="margin-top:10px;">
    <div>
    	<div style=" margin-left:20px; margin-top:-10px;">
    	<img src="images/WHR.png" class="img-responsive" style="width:45px; height:45px;"/>
        </div>
        <div style="margin-left:80px; margin-top:-30px; color:#505050;">
        	<div style=" margin-top:-45px;">腰 臀 比 （ W H R ）</div>
        	<div style="color:#999; font-size:12px; margin-top:-20px; float:right; padding-right:20px;">${tr.waistHip}</div><br>
        	<div style="color:#999; font-size:12px; margin-top:-15px;">${data1}</div>
        </div>
    </div>
    <hr>
  </div>
  <div style="width:100%; height:15px; background-color:#F3F3F3; margin-top:-20px;"></div>
  <div style="padding-left:3%; margin-bottom:20%;">
  	<div style=" padding-top:10px;">体态分析</div><br>
    <div style="color:#999; font-size:12px; margin-top:-10px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${titai}</div>
  </div>
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

