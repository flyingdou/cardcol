<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
      <meta name="keywords" content="健身E卡通 课程简介" />
      <meta name="description" content="健身E卡通 课程简介" />
    <title>${c.courseInfo.name}-健身E卡通健身</title>
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
  <div style="width:100%; height:20px; background-color:#EBEBEB;;"></div>
   <div style="margin-top:30px;margin-left:4%;">
        	<div>
				<img src="picture/${c.coach.image}" class="img-circle" style="margin-top:-15px; width:80px; height:80px; margin-left:10px;">
            </div>
      		<div style="margin-left:120px; margin-top:-60px; color:#3c3c3c;">
        		<span><b>教 练 昵 称：${c.coachName}</b></span><br>
        		<span><b>发 布 时 间：${c.createTime}</b></span>
            </div>
   </div>
   <div style="width:100%; height:20px; background-color:#EBEBEB; margin-top:30px;"></div>
	<div>
    	<div style=" margin-top:10px;">
        	<div style="color:#3c3c3c; padding-top:10px; margin-left:5%;"><b>课 程 名 称</b></div>
            <div style=" margin-left:60%; margin-top:-20px; color:#999;">${c.courseInfo.name}</div>
            <hr/>
            <div style="color:#3c3c3c; margin-left:5%;"><b>课 程 时 间</b></div>
            <div style=" margin-left:60%; margin-top:-20px; color:#999;">${c.startTime} - ${c.endTime}</div>
            <hr/>
            <div style="color:#3c3c3c; margin-left:5%;"><b>课 程 地 点</b></div>
            <div style=" margin-left:60%; margin-top:-20px; color:#999;">${c.place}</div>
            <div> <img src="images/map.png" style="width:16px; height:20px; margin-left:40px; margin-top:10px;"></div>
        </div>
    </div>
    <div style="background-color:#EBEBEB; width:100%; height:300px; margin-top:20px;"></div>
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