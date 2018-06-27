<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!doctype html>
<html>

<head>					
<base href="<%=basePath%>">
<meta charset="UTF-8">
<title>关注北京健身E卡通</title>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />

<style type="text/css">
   body,html{
      margin: 0;
      padding: 0;
      width: 100%;
      height: 100%;
   }
   #body{
      background-color: #FA3922;
      text-align: center;
   }

   .img-div{
      text-align: center;
   }
   .dou-font{
      color: black;
      font-size: 13px;
   }
</style>
</head>

<body id = "body">
		
		    <div class = "img-div">
		        <img src="ecartoon-weixin/tGame/img/qrcode.jpg" style="width: 130px; height: 130px;" ><br/>
		        <p class = "dou-font" >长按二维码关注E卡通微信公众号后继续操作</p>
		    </div>
		
	<script src="ecartoon-weixin/js/jquery.min.js"></script>
	
    <script type="text/javascript">
      $(function(){
      var screenHeight =$("#body").height();
      var imgHeight = $(".img-div").eq(0).height();
      var toHeight = (screenHeight-imgHeight)/2;
      $(".img-div").css({"margin-top":(toHeight - 20)});
    	  
    	  
      })
    
    </script>


</body>

</html>