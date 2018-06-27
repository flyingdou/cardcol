<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
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
			        	
			        	<!-- 动态展现管联俱乐部的健身卡 -->
			        	<s:iterator value="pageInfo.items">
							<li class="mui-table-view-cell mui-media" href="package.html">
								<a href="onlinecard!CardDetail.asp?cid=<s:property value="id" />&mid=<s:property value="mid" />">
									<img class="mui-media-object mui-pull-left pic" src="${pageContext.request.contextPath}/picture/<s:property value="image1"/>" >     
									<div class="mui-media-body">
										<h5 class="name"  id="card_name"><s:property value="name"/></h5>
										<p class='mui-ellipsis-2 jianjie' id="jianjie2"><span><s:property value="memberName"/></span><span style="color:red;text-align: right;float: right;"><s:property value="cost"/></span><span style="clear: both;"></span></p>
										<p class="mendian"><span><s:property value="freeProject"/></span></p>
									</div>
								</a>
							</li>
						</s:iterator>	
						<!-- 动态展现关联健身俱乐部健身卡结束 -->
						
					</ul>
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
		/* //跳转到详情页面
		$('.mui-table-view-cell').each(function(){
			$(".mui-table-view").click(function(){
				location.href="detail.jsp";
			});
		}); */
		
		//列表的图片的高度设计
		$(".mui-table-view-cell a img").css({
			height:function(index,value){
				return window.innerWidth*0.298*0.75;
			}
		});
	</script>
</html>
