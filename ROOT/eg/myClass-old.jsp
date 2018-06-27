<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>我的课程</title>
    <meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">

	<link rel="stylesheet" href="${pageContext.request.contextPath}/eg/css/weui.min.css"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/eg/css/jquery-weui.css"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/eg/css/demos.css"/>
	
	
  </head>

<body ontouchstart>
	<!--日历表-->
  	<div class="weui-cells weui-cells_form" style="display:none;">
      <div class="weui-cell">
        <div class="weui-cell__hd"><label for="date3" class="weui-label">日期</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" id="date3" type="text" >
        </div>
      </div>
    </div>
    <div id="inline-calendar"></div>
  	<!--课程名称-->
  	<s:iterator value="pageInfo.items"> 
  	<div class="weui-cells">
	  <div class="weui-cell">
	    <!--<div class="weui-cell__hd"><img src=""></div>-->
	    <div class="weui-cell__bd">
	      <p>课程名称：</p>
	    </div>
	    <div class="weui-cell__ft"><s:property value="name"/></div>
	  </div>
	  <div class="weui-cell">
	    <!--<div class="weui-cell__hd"><img src=""></div>-->
	    <div class="weui-cell__bd">
	      <p>课程时间：</p>
	    </div>
	    <div class="weui-cell__ft"><s:property value="startTime"/>-<s:property value="endTime"/></div>
	  </div>
	  <div class="weui-cell">
	  	<div class="weui-cell__bd">
	  		<p>课程备注：</p>
	  		<textarea class="weui-textarea" placeholder='<s:property value="coach"/>' rows="3" disabled="disabled" style="background: #ffffff;"></textarea>
		    <div class="weui-textarea-counter"><span>0</span>/200</div>
	  	</div>
	  </div>
	</div></s:iterator>
	<!--悬浮在底部的课程开始播放按钮-->
	<!--<div class="weui-form-preview__ft">
	    <a class="weui-form-preview__btn weui-form-preview__btn_default" href="javascript:">辅助操作</a>
	    <button type="submit" class="weui-form-preview__btn weui-form-preview__btn_primary" href="javascript:">操作</button>
	</div>-->
	<!--底部单个的按钮-->
	<div class="weui-form-preview__ft">
	    <button type="submit" class="weui-form-preview__btn weui-form-preview__btn_primary" href="javascript:" id="star_btn"><span ></span>开始训练</button>
	</div>
	
  
  	
  	
  	
    <script src="${pageContext.request.contextPath}/eg/js/jquery-2.1.4.js"></script>
	<script src="${pageContext.request.contextPath}/eg/js/fastclick.js"></script>
	<script>
	  $(function() {
	    FastClick.attach(document.body);
	  });
	</script>
	<script src="${pageContext.request.contextPath}/eg/js/jquery-weui.js"></script>
	<script>
      $("#date").calendar({
        onChange: function (p, values, displayValues) {
          console.log(values, displayValues);
        }
      });
      $("#date2").calendar({
        value: ['2016-12-12'],
        dateFormat: 'yyyy年mm月dd日'  // 自定义格式的时候，不能通过 input 的value属性赋值 '2016年12月12日' 来定义初始值，这样会导致无法解析初始值而报错。只能通过js中设置 value 的形式来赋值，并且需要按标准形式赋值（yyyy-mm-dd 或者时间戳)
      });
      $("#date-multiple").calendar({
        multiple: true,
        onChange: function (p, values, displayValues) {
          console.log(values, displayValues);
        }
      });
      $("#date3").calendar({
        container: "#inline-calendar"    //顶部显示选择日期的值
      });
    </script>
    <script>
    	//点击开始训练时，进入到播放页面
    	var star_btn=document.getElementById('star_btn');
    	star_btn.addEventListener('click',function(){
    		location.href='video_1.html';
    	});
    	
    </script>
  </body>
</html>