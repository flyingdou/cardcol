<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>优惠券</title>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="apple-mobile-web-app-capable" content="yes">
		<link  rel="stylesheet" href="../eg/css/mui.min.css" />
		<style type="text/css">
			.cou{
				background-image:url('images/coupon10.png');
				background-repeat: no-repeat;
				background-size:300px 100px;
				width:300px;
				height:100px;
				margin: 0 auto;
				position: relative;
			}
			.cou_img{
				width:300px;
				height:100px;
				margin: 0 auto;
			}
			/*日期颜色#FE9E3A*/
			.star_time{
				position: absolute;
			    top: 30px;
			    right: 15px;
			    /*border: 1px solid #111;*/
			    color: #FE9E3A;
			    font-size: 12px;
			}
			.end_time{
				position: absolute;
			    top: 50px;
			    right: 15px;
			    /*border: 1px solid #111;*/
			    color: #FE9E3A;
			    font-size: 12px;
			}
		</style>
	</head>
	<body>
		<div class="mui-content">
			<div class="mui-scroll-wrapper">
			    <div class="mui-scroll">
			    	<ul class="mui-table-view">
			    	<s:iterator value="#request.coupons">
			    		<li class="mui-table-view-cell">
			    			<div class="cou">
			    				<a href="#" onclick="history.back()"><img src="../eg/images/coupon5.png" class="cou_img"/></a>
			    				<div class="star_time"><s:property value="startDate"/></div>
			    				<div class="end_time"><s:property value="endDate"/></div>
			    			</div>
			    		</li>
			        </s:iterator>		
			    	</ul>
			    </div>
			</div>
			
		</div>
	</body>
	<script src="../eg/js/mui.min.js"></script>
	<script type="text/javascript">
		mui('.mui-scroll-wrapper').scroll({
			deceleration:0.0005
		})
	</script>
</html>
