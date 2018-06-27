<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>健身卡详情</title>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="apple-mobile-web-app-capable" content="yes">
		<link  rel="stylesheet" href="${pageContext.request.contextPath}/eg/css/mui.min.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/eg/css/mui.picker.min.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery-1.7.2.min.js"></script>
		<style type="text/css">
			#packageName{
				text-align: center;
				color:black;
				font-size:15px;
			}
			.mui-table-view .mui-table-view-cell p>span:first-child{
				color:black;
				font-size:13px;
			}
			.mui-table-view .mui-table-view-cell p>span:nth-child(2){
				margin-left:50px;
				font-size:13px;
			}
			.fagui{
				font-size:12px;
				margin-bottom:10px; 
			}
			.select_dateImg{
				
			}
			/*日期显示框*/
			.date{
				line-height:26px;
				height:26px;
				border:1px solid #2AC845;
				border-radius:3px;
				color:black;
				font-size:13px;
				margin:0 5px;
			}
			#footer,footer{
				border:none;
				color:#fff;
				/*background-color:#F87D04;*/
			}
			.mui-bar-footer {
				
			    bottom: 0;
			    background-color: #FE9E3A;
			}
		</style>
		
	</head>
	<body>
		<footer class="mui-bar mui-bar-footer font-white">
			<h3 class="mui-title font-white" id="footer"><a href="javascript:gosubmit()">购买</a></h3>
		</footer>
		<div class="mui-content">
			<div class="mui-scroll-wrapper" style="bottom:45px;">
			    <div class="mui-scroll">
			    	<s:iterator value="pageInfo.items">
			    	<div class="banner">
				        <div class="bg_color">
				            <div class="pic fl">
				             <img src="${pageContext.request.contextPath}/picture/<s:property value="PROD_IMAGE"/>" alt="">
				            </div>
				            <div class="txt fl">
				                <p>摘要：<span><s:property value="PROD_SUMMARY" /></span></p>
				            </div>
				        </div>
				    </div>
			    	<ul class="mui-table-view">
			    		<li class="mui-table-view-cell">
			    			<p><span>套餐名称:</span><span id="jia_name"><s:property value="PROD_NAME"/></span></p>
			    		</li>
			    	</ul>
			    	<ul class="mui-table-view">
			    		<li class="mui-table-view-cell">
			    			<p><span>有效期:</span><span id="yi_club">
			    			<s:if test="PROD_PERIOD_UNIT='A'"><s:property value="PROD_PERIOD" />个月</s:if>
							<s:elseif test="PROD_PERIOD_UNIT='B'"><s:property value="PROD_PERIOD" />季</s:elseif>
							<s:elseif test="PROD_PERIOD_UNIT='C'"><s:property value="PROD_PERIOD" />年</s:elseif>
							<s:else><s:property value="PROD_PERIOD" />次</s:else>
			    			</span></p>
			    		</li>
			    	</ul>
			    	<ul class="mui-table-view">
			    		<li class="mui-table-view-cell">
			    			<p><span>价格:</span><span id="bing_platform"><s:property value="PROD_PRICE"/></span></p>
			    		</li>
			    	</ul>
			    	<ul class="mui-table-view">
			    		<li class="mui-table-view-cell">
			    			<p><span>描述:</span><span id="jia_name"><s:property value="PROD_CONTENT"/></span></p>
			    			<input type="input"  value="2016-12-31" id="resule"  style="line-height:26px;height:26px;border:1px solid #2AC845;border-radius:3px;color:black;font-size:13px;margin:0 5px;width:65%;"/>
			    			   <img  class="mui-col-sm-1" data-options='{"type":"date","beginYear":"2010","endYear":"2050"}' src="${pageContext.request.contextPath}/eg/images/logo1.png" class="select_dateImg" id="rili_img"   style="width:15px;height:25px;padding-top:13px;"/>
			    			</p>
			    			<p id="details"></p>
			    		</li>
			    	</ul>
			    	</s:iterator>
			    </div>
			</div>
			
		</div>
	</body>
    <script src="${pageContext.request.contextPath}/eg/js/mui.min.js"></script>
	<script type="text/javascript">
		mui('.mui-scroll-wrapper').scroll({
			deceleration:0.0005
		});
		
		function gosubmit(){
			var date=$("#resule").val();
			location.href="onlinecard!OrderInfo.asp?date="+date;
		}
			
	</script>
	<script src="${pageContext.request.contextPath}/eg/js/mui.picker.min.js"></script>
	<script>
				var rili_img=document.getElementById('rili_img');
				rili_img.addEventListener('tap',function(){
					var resule=document.getElementById('resule');
					var optionsJson = this.getAttribute('data-options') || '{}';
					var options = JSON.parse(optionsJson);
					var picker = new mui.DtPicker(options);
					picker.show(function(rs) {
							/*
							 * rs.value 拼合后的 value
							 * rs.text 拼合后的 text
							 * rs.y 年，可以通过 rs.y.vaue 和 rs.y.text 获取值和文本
							 * rs.m 月，用法同年
							 * rs.d 日，用法同年
							 * rs.h 时，用法同年
							 * rs.i 分（minutes 的第二个字母），用法同年
							 */
							resule.value=rs.text;
							picker.dispose();
						});
				});
		</script>
</html>