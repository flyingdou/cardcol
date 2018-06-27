<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
<title>[健身卡]-卡库健身</title>
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
	<div class="text-center" style="margin-top:2%; font-size:16px;"><b>${pr.name}</b></div>
    <div style="margin-top:5%;">
    <div style="margin-left:5%;">
    	<span style="color:#3D3D3D"><b>甲方</b>：</span>
        <span style=" float:right; padding-right:20px; color:#999;">name</span>
        <hr style=" margin-top:10px;"/>
    </div>
    <div style="margin-left:5%;">
    	<span style="color:#555;"><b>乙方</b>：</span>
        <span style=" float:right; padding-right:20px; color:#999;">${pr.member.name}</span>
        <hr style=" margin-top:10px;"/>
    </div>
    <div style="margin-left:5%;">
    	<span style="color:#757575"><b>丙方</b>：</span>
        <span style=" float:right; padding-right:20px; color:#999;">卡库电商平台</span>
        <hr style=" margin-top:10px;"/>
    </div>
    <div style="margin-left:5%; line-height:40px; margin-bottom:20%;">
    	<p style="color:#999;">根据《合同法》法律法规的规定，甲乙丙三方在平等、自愿、公平、诚实信用的基础上，就健身服务的有关事宜订立合同如下：</p>
    	<b style="margin-left:2%; color:#484848">1、甲方承诺从" 获取的时间 "起，通过丙方购买乙方3月时小卡，截止日期为"+3"。<br /></b>
        <b style="margin-left:2%; color:#484848">2、该会员资格几名，仅限甲方本人使用：甲</b>
    </div>
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
