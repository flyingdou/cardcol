<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>挑战排行榜</title>
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
   <div style="width:100%; height:15px; background-color:#E7E7E7; "></div>
	<div>
    	<div>
        	<div>
            	<img src="${a.creator.image}" style="width:80px; height:80px; margin-left:10px; margin-top:20px;"/>
            </div>
            <div style="font-size:14px; color:#3c3c3c; margin-left:100px; margin-top:-80px;">${a.creator.name}</div>
            <div style="font-size:12px; color:#a0a0a0; margin-left:100px; margin-top: 40px;">${a.action}  ${addornot} ${a.value} ${sport}</div>
            <div style="font-size:12px; color:#999; float:right; padding-right:20px; margin-top:-14px;">${a.createTime}</div>
            <hr style=" margin-top:10px; margin-left:20px;">
        </div>
        <div style="margin-top:-30px;">
        	<div>
            	<img src="images/p1.png" style="width:80px; height:80px; margin-left:10px; margin-top:20px;"/>
            </div>
            <div style="font-size:14px; color:#3c3c3c; margin-left:100px; margin-top:-80px;">赵 青</div>
            <div style="font-size:12px; color:#a0a0a0; margin-left:100px; margin-top: 40px;">体 重 减 少 3 公 斤</div>
            <div style="font-size:12px; color:#999; float:right; padding-right:20px; margin-top:-14px;">2 0 1 6 - 8 - 6 </div>
            <hr style=" margin-top:10px; margin-left:20px;">
        </div>
        	<div style="margin-top:-30px;">
        	<div>
            	<img src="images/p1.png" style="width:80px; height:80px; margin-left:10px; margin-top:20px;"/>
            </div>
            <div style="font-size:14px; color:#3c3c3c; margin-left:100px; margin-top:-80px;">赵 青</div>
            <div style="font-size:12px; color:#a0a0a0; margin-left:100px; margin-top: 40px;">体 重 减 少 3 公 斤</div>
            <div style="font-size:12px; color:#999; float:right; padding-right:20px; margin-top:-14px;">2 0 1 6 - 8 - 6 </div>
            <hr style=" margin-top:10px; margin-left:20px;">
        </div>
        	<div style="margin-top:-30px;">
        	<div>
            	<img src="images/p1.png" style="width:80px; height:80px; margin-left:10px; margin-top:20px;"/>
            </div>
            <div style="font-size:14px; color:#3c3c3c; margin-left:100px; margin-top:-80px;">赵 青</div>
            <div style="font-size:12px; color:#a0a0a0; margin-left:100px; margin-top: 40px;">体 重 减 少 3 公 斤</div>
            <div style="font-size:12px; color:#999; float:right; padding-right:20px; margin-top:-14px;">2 0 1 6 - 8 - 6 </div>
            <hr style=" margin-top:10px; margin-left:20px;">
        </div>
    </div>
    <div style="width:100%; height:30px; background-color:#E7E7E7; margin-top:70px;"></div>
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
