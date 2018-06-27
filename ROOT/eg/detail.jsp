<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>    
<!doctype html>
<html>

	<head>
		<meta charset="UTF-8">
		<title></title>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link href="${pageContext.request.contextPath}/eg/css/mui.min.css" rel="stylesheet" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/eg/css/app.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/eg/css/mui.picker.min.css" />

		<style>
			.fright {
				float: right;
			}
			
			.fleft {
				float: left;
			}
			
			.red {
				color: red
			}
			
			.cgrey {
				color: #a6a2a6
			}
			
			.corange {
				color: #fb8c00
			}
			.cgreen{
				color:green;
			}
			.font7 {
				font-weight: 700;
			}
			
			.font12 {
				font-size: 12px;
			}
			
			.header-img {
				width: 100%;
				height: 200px;
				background: url(../bodytest/images/game4_bg.png) scroll center center/100% auto;
				box-sizing: border-box;
				position: relative
			}
			
			.header-text {
				width: 100%;
				height: 50px;
				line-height: 25px;
				padding: 0 10px;
				font-weight: 700;
				position: absolute;
				bottom: 0;
				box-sizing: border-box;
				overflow: hidden;
				overflow: hidden;
				text-overflow: ellipsis;
				display: -webkit-box;
				-webkit-line-clamp: 2;
				-webkit-box-orient: vertical;
				font-size: 15px;
				color: white;
				text-overflow: ellipsis;
			}
			
			.content-say {
				width: 100%;
				box-sizing: border-box;
			}
			
			.content-bottombox {
				box-sizing: border-box;
				width: 100%;
				height: 30px;
				line-height: 30px;
				font-size: 14px;
				padding: 0 10px;
				border-bottom: 1px solid #eaeaea;
				background: white;
			}
			
			.jtnr {
				width: 100%;
				padding: 5px 10px;
				margin-top: 10px;
				background: white;
				font-size: 13px;
			}
		</style>
	</head>

	<body>
		<script type="text/javascript">
			window.onload=function(){
			var today = new Date()
            var y = today.getFullYear()
            var M = today.getMonth() + 1
            var d = today.getDate()
              M = jia(M)
            d = jia(d)
                function jia(i) {
                if (i < 10) {
                    i = "0" + i;
                }
                return i;
            }
           document.getElementById('result').innerHTML=y+"-"+M+"-"+d

		}
		</script>
		<form action="" method="post">
			<footer class="mui-bar mui-bar-footer font-white"style="border:none;background: white;">
				<div class="font12"style="line-height: 44px;">
					选择开卡时间<span id="result" class="ui-alert cgreen"style="text-indent: 2em;display: inline-block;">2017-06-16</span>
				    <button id="demo4" data-options="{&quot;type&quot;:&quot;date&quot;}" class="btn mui-btn mui-btn-block"style="width: 80px;position:absolute;left: 5.5em;top:0em;opacity: 0;height: 100%;"></button>
		             <a href="javascript:goSubmit()" style="width: 70px;color:white;background: orange;display:inline-block;height: 30px;line-height: 30px;float: right;margin-top:5px;margin-right: 10px;text-align: center;border-radius: 4px;">购买</a>
				</div>
			</footer>
			<div class="mui-content">
				<!-- 展示健身卡详情开始 -->
				<s:iterator value="pageInfo.items">
				<div class="header-img" style="background-image:url('${pageContext.request.contextPath}/picture/<s:property value="image1"/>')">
					<div class="header-text" style="">
						<!-- 摘要 -->
						<span><s:property value="freeProject"/></span>
					</div>
				</div>

				<div style="">
					<div class="content-bottombox mui-clearfix">
						套餐名称：
						<span class="fright cgrey">
							<s:property value="name"/>
						</span>
						<span id="jia_name"></span>
					</div>
					<div class="content-bottombox mui-clearfix">
						适用店面：
						<span class="fright cgrey">
							<a class="cgrey" style="text-decoration : none" href="${pageContext.request.contextPath}/onlinecard!stroesLocation.asp?stores=<s:property value="useRange" />">
								<span id="storeCount"></span>
							</a>
							<input id="storeStr" type="hidden"  value="<s:property value="useRange" />" />
						</span>
					</div>
					<div class="content-bottombox mui-clearfix">有效期：
						<span class="fright cgrey">
							<span id="yi_club">
				    			<%-- <s:if test="PROD_PERIOD_UNIT='A'"><s:property value="valid_period" />个月</s:if>
								<s:elseif test="PROD_PERIOD_UNIT='B'"><s:property value="PROD_PERIOD" />季</s:elseif>
								<s:elseif test="PROD_PERIOD_UNIT='C'"><s:property value="PROD_PERIOD" />年</s:elseif>
								<s:else><s:property value="PROD_PERIOD" />次</s:else> --%>
								<s:property value="valid_period" />个月
			    			</span>
						</span>
					</div>
					<div class="content-bottombox mui-clearfix">价格：<span class="fright corange font7">￥<s:property value="cost"/>元</span></div>
				</div>

				<div class="jtnr">
					<s:property value="costProject" />
					<!-- <div>
						<h5 class="corange">服务内容</h5>
						<p class="font12">测试用语测试用语测试用语测试用语测试用语测试用语测试用语测试用语测试用语测试用语测试用语</p>
					</div>
					<div>
						<h5 class="corange">适用人员</h5>
						<p class="font12">测试用语测试用语测试用语测试用语测试用语测试用语测试用语</p>
					</div>
					<div>
						<h5 class="corange">温馨提示</h5>
						<p class="font12">测试用语测试用语测试用语测试用语测试用语测试用语测试用语测试用语测试用语测试用语</p>
					</div> -->
				</div>
				</s:iterator>
				<!-- 展示健身卡详情结束 -->
			</div>
		</form>
	</body>
		<script src="${pageContext.request.contextPath}/eg/js/mui.min.js"></script>
		<script src="${pageContext.request.contextPath}/eg/js/mui.picker.min.js"></script>
		<script src="${pageContext.request.contextPath}/eg/js/jquery-1.11.1.min.js"></script>
		

	<script>
		mui.init();
		
		(function($) {
			$.init();
			var result = $('#result')[0];
			var btns = $('.btn');
			btns.each(function(i, btn) {
				btn.addEventListener('tap', function() {
					var optionsJson = this.getAttribute('data-options') || '{}';
					var options = JSON.parse(optionsJson);
					var id = this.getAttribute('id');
					/*
					 * 首次显示时实例化组件
					 * 示例为了简洁，将 options 放在了按钮的 dom 上
					 * 也可以直接通过代码声明 optinos 用于实例化 DtPicker
					 */
					var picker = new $.DtPicker(options);
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
						result.innerText =rs.text;
				     
						/* 
						 * 返回 false 可以阻止选择框的关闭
						 * return false;
						 */
						/*
						 * 释放组件资源，释放后将将不能再操作组件
						 * 通常情况下，不需要示放组件，new DtPicker(options) 后，可以一直使用。
						 * 当前示例，因为内容较多，如不进行资原释放，在某些设备上会较慢。
						 * 所以每次用完便立即调用 dispose 进行释放，下次用时再创建新实例。
						 */
						picker.dispose();
					});
				}, false);
			});
		})(mui);
	</script>
	<script>
	 	/*
	 	* 下订单 
	 	*/
		function goSubmit(){
			var date=$("#result").html();
			location.href="onlinecard!OrderInfo.asp?date="+date;
		}
		
		$(function(){
			var storeStr = $("#storeStr").val();
			var storeArray = storeStr.split(",");
			$("#storeCount").html( storeArray.length + " " + "家");
		})
	</script>
</html>