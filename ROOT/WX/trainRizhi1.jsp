<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>训练日志</title>
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/bootstrap.css" rel="stylesheet">
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/base.css" rel="stylesheet">
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/style.css" rel="stylesheet">
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/index.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/jquery-2.2.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/style.js"></script>
    <script type="text/javascript">
    function writeSport(){
    	url= "bodywx!trainRizhi.asp"
    	$('#sporForm').attr("action", url);
		$('#sporForm').submit();
    }
    function saveRecord(){
    	var parms = $('#sporForm').serialize();
    	$.ajax({type:'post',url:'bodywx!saveRecord.asp',data: parms, 
			success: function(msg){
				var _json = $.parseJSON(msg);
				var result = _json.msg;
				if(result == "okRecord"){
					alert("当前训练日志已成功保存！");
				}
			}
		});
    }
    </script>
</head>
<body>
<div class="trainRizhi container">
    <div class="message">
        <div class="pic fl"><img src="../picture/<s:property value="member.image"/>"></div>
        <div class="txt fl">
            <h6><s:property value="#session.member.name"/> </h6>
            <p><s:date name="#request.doneDate" format="yyyy-MM-dd"/></p>
        </div>
        <a href="bodywx!trainDate.asp"><img src="images/right_icon_03.png" class="right fr"></a>
    </div>
    <div class="sport_data">
        <a href="#">
        <div class="icon">
            <img src="images/train_icon1_03.png" class="fl">
            <h4 class="fl">运动数据</h4>
            <img src="images/right_icon_03.png" class="right fr">
        </div>
        </a>
        <div class="data_inner">
            <ul>
                <li><div class="radio"><img src="images/sport_icon_10.png"> </div><p>运动时间<span><s:property value="#request.times"/>分钟</span></p></li>
                <li><div class="radio"><img src="images/sport_icon_13.png"> </div><p></p><s:property value="#request.action"/><s:property value="#request.actionquan"/><s:property value="#request.unit"/> </li>
                <li><div class="radio"><img src="images/sport_icon_15.png"> </div><p>最高心率<span><s:property value="#request.heartrate"/>次/分钟</span></p></li>
            </ul>
        </div>
    </div>
    <div class="body_data">
    <a href="JavaScript:writeSport()" id="sport">
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
        <a href="JavaScript:saveRecord();" class="share">保存</a>
    </div>
    <form id="sporForm" action="bodywx.asp" method="post">
 	<input type="hidden" name="trainRecord.doneDate" value="<s:date name="#request.doneDate" format="yyyy-MM-dd"/>"/>
   	<input type="hidden" name="trainRecord.action" value="<s:property value="#request.action"/>"/>
   	<input type="hidden" name="trainRecord.actionQuan" value="<s:property value="#request.actionquan"/>"/>
   	<input type="hidden" name="trainRecord.times" value="<s:property value="#request.times"/>"/>
   	<input type="hidden" name="trainRecord.unit" value="<s:property value="#request.unit"/>"/>
   	<input type="hidden" name="trainRecord.heartRate" value="<s:property value="#request.heartrate"/>"/>
   	<input type="hidden" name="trainRecord.weight" value="<s:property value="#request.weight"/>"/>
   	<input type="hidden" name="trainRecord.waist" value="<s:property value="#request.waist"/>"/>
   	<input type="hidden" name="trainRecord.hip" value="<s:property value="#request.hip"/>"/>
   	<input type="hidden" name="trainRecord.height" value="<s:property value="#request.height"/>"/>
    </form>
</div>
</body>
</html>