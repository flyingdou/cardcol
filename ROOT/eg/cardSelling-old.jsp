<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>在线售卡</title>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="apple-mobile-web-app-capable" content="yes">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/eg/css/mui.min.css" />
		<style type="text/css">
		/*左部图片样式*/
		h5{
			color:black;
			font-size:12px;
		}
		.jiage{
			font-size: 14px;
		    line-height: 20px;
		    margin-top: 5%;
		    color: #fe9e3a;
			
		}
		/*售价样式*/
		.shoujia{
			color:#F87D04;
		}
		
		
		/*列表图片的样式*/
		.pic{
			width:29.8%;
		}
		.mui-table-view .mui-media-object {
		    line-height: 42px;
		     max-width: none; 
		}
		/*卡的名字*/
		.name{
			font-size:15px;
			margin-top: 4%;
			margin-bottom: 2%;
		}
		/*发卡门店样式*/
		.mendian{
			font-size: 13px;
			line-height: 20px;
    		margin-top: 1%;
		}
		</style>
		
		
		
	</head>
	<body>
		<div class="mui-content">
			<div class="mui-scroll-wrapper" >
			    <div class="mui-scroll">
			        <!--这里放置真实显示的DOM内容-->
			        <ul class="mui-table-view">
			        <s:iterator value="pageInfo.items">
						<li class="mui-table-view-cell mui-media">
							<a href="onlinecard!CardDetail.asp?pid=<s:property value="id"/>">
								<img class="mui-media-object mui-pull-left pic" src="picture/<s:property value="PROD_IMAGE"/>" >     
								<div class="mui-media-body">
									<h5 class="name" id="card_name"><s:property value="PROD_NAME"/></h5>
									<p class='mui-ellipsis-2 jiage' id="jianjie"><span><s:property value="name"/></span></p>
									<p  class="mendian"><span id="card_mendian"><s:property value="PROD_SUMMARY"/></span></p>
								</div>
							</a>
						</li>
					</s:iterator>	
			    </div>
			</div>
						
			
		</div>
		
		
	</body>
	<script src="${pageContext.request.contextPath}/eg/js/mui.min.js"></script>
	<script src="${pageContext.request.contextPath}/eg/js/jquery-1.11.1.min.js"></script>
	<script>
			//设置页面的滚动
			mui(".mui-scroll-wrapper").scroll({
				deceleration:0.0005
			})

		
		//列表的图片的高度设计
		$(".mui-table-view-cell a img").css({
			height:function(index,value){
				return window.innerWidth*0.298*0.75;
			}
		});
		
		
	</script>
</html>
