<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>训练日志</title>
    <link type="text/css" href="css/bootstrap.css" rel="stylesheet">
    <link type="text/css" href="css/base.css" rel="stylesheet">
    <link type="text/css" href="css/style.css" rel="stylesheet">
    <link type="text/css" href="css/index.css" rel="stylesheet">
    <script type="text/javascript" src="js/jquery-2.2.0.min.js"></script>
    <script type="text/javascript" src="js/style.js"></script>
    <script type="text/javascript">
    function writeData(){
    	var val = $("#date").text();
    	$("#data").val(val);
    	$("#dataForm").submit();
    }
    </script>
</head>
<body>
<div class="trainRizhi container">
<s:iterator value="trainRecord">
<form id="dataForm" action="bodywx!writeData.asp" method="post" >
    <div class="message">
        <div class="pic fl"><img src="../picture/<s:property value="member.image"/>"></div>
        <div class="txt fl">
            <h6><s:property value="#session.member.name"/> </h6>
            <p id="date"><s:date name="#request.doneDate" format="yyyy-MM-dd"/></p>
            <input type="hidden" name="doneDate" value="" id="data">
        </div>
        <a href="bodywx!trainDate.asp"><img src="images/right_icon_03.png" class="right fr"></a>
    </div>
</form>    
    <s:if test="id == null">
	     <div class="sport_data">
	        <div class="icon">
	            <img src="images/train_icon1_03.png" class="fl">
	            <h4>运动数据</h4>
	        </div>
	        <div class="data_inner">
	            <div class="pic fl"><img src="images/train_icon4_07.png"></div>
	            <div class="txt fl">
	                <p>您没有任何运动数据，请<a href="JavaScript:writeData();">点击屏幕</a>填写</p>
	            </div>
	        </div>
	    </div>
    </s:if>
    <s:else>
    <div class="sport_data">
        <a href="bodywx!writeSportData.asp?id=<s:property value='id'/>">
        <div class="icon">
            <img src="images/train_icon1_03.png" class="fl">
            <h4 class="fl">运动数据</h4>
            <img src="images/right_icon_03.png" class="right fr">
        </div>
        </a>
        <div class="data_inner">
            <ul>
                <li><div class="radio"><img src="images/sport_icon_10.png"> </div><p>运动时间<span><s:property value="times"/>分钟</span></p></li>
                <li><div class="radio"><img src="images/sport_icon_13.png"> </div><p><s:if test="actionQuan == null"></s:if><s:else><s:property value="action"/><span><s:property value="actionQuan"/><s:property value="trainRecord.unit"/></s:else></span></p></li>
                <li><div class="radio"><img src="images/sport_icon_15.png"> </div><p>最高心率<span><s:property value="heartRate"/>次/分钟</span></p></li>
            </ul>
        </div>
    </div>
    </s:else>
    <div class="body_data">
    <a href="writeSportData1.jsp" id="sport">
        <div class="icon">
            <img src="images/train_icon2_06.png" class="fl">
            <h4>身体数据</h4>
            <img src="images/right_icon_03.png" class="right fr" style="margin-top: -10%">
            <p style="margin-left: 70% ;margin-top: -10%" id="weight"></p>
        </div>
        </a>
        <div class="pic fl">
            <img src="images/bodyImg_22.png">
            <span>我的体态</span>
            <p>?</p>
        </div>
        <div class="txt fl">
            <h4>体质指数(BMI)</h4>
            <p id ="bmi"></p>
            <div class="line"></div>
            <h4>腰臀比(WHR)</h4>
            <p id="whr"></p>
        </div>
    </div>
    <div class="assign">
        <a class="share">保存</a>
    </div>
    
</s:iterator>
</div>
<script>
 var id ='<s:property value="trainRecord.id"/>';
 var height = '<s:property value="trainRecord.height"/>';
 var weight = '<s:property value="trainRecord.weight"/>';
 var waist = '<s:property value="trainRecord.waist"/>';
 var hip = '<s:property value="trainRecord.hip"/>';
 var heights = height/100
 if(heights=="0"){
	 $("#bmi").text("0.00");
 }else{
 $("#bmi").text( (weight/(heights*heights)).toFixed(2))
 }
 if(hip == '0'||waist==''|| hip==''){
	 $("#whr").text("0.00");
 }else{
	 $("#whr").text((waist/hip).toFixed(2))
 }
 if(id != ''){
	 if(weight==''){
		 $("#weight").text("体重0KG");
		 $('#sport').removeAttr('href');
	 }else{
		 $("#weight").text("体重"+ weight +"KG");
	 }
 }
</script>
</body>
</html>