<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>团体课程预约</title>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="apple-mobile-web-app-capable" content="yes">
		<link  rel="stylesheet" href="${pageContext.request.contextPath}/eg/css/mui.min.css" />
		
		<style type="text/css">
			/*字体为微软雅黑*/
			body{
				font-family: "微软雅黑";
			}
			.mr{
				margin-right: 30px;
			}
			/*整体与顶部的距离为20px*/
			.mui-table-view{
    			margin-top: 20px;
			}
			/*定位图标*/
			.mui-icon {
			    font-size: 20px;
			}
			/*底部样式*/
			.fu{
				line-height:45px;
				width: 100%;
				float:left;
				text-align:center;
				font-size:13px;
				background-color:#FE9E3A;
				color: #ffffff;
			}
			/*去掉底部左右两边的padding值*/
			.mui-bar {
			    padding-right: 0px;
			    padding-left: 0px;
			}
		</style>
	</head>
	<body>
		<footer class="mui-bar mui-bar-footer">
			<span class="mui-col-sm-12 fu" id="go"><s:if test="<s:property value='num'/>==0">人已满</s:if><s:else>可预约</s:else></span>
		</footer>
		<div class="mui-content">
			<div class="mui-scroll-wrapper">
			    <div class="mui-scroll">
			    	<ul class="mui-table-view">
			    	<s:iterator value="pageInfo.items"></s:iterator>
			    		<li class="mui-table-view-cell">
			    			<span class="mui-pull-left mr">课程名称：</span><p><s:property value="name"/></p>
			    		</li>
			    		<li class="mui-table-view-cell">
			    			<span class="mui-pull-left mr">教&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;练：</span><p><s:property value="coach"/></p>
			    		</li>
			    		<li class="mui-table-view-cell">
			    			<span class="mui-pull-left mr">上课时间：</span><s:property value="startTime"/>-<s:property value="endTime"/></p>
			    		</li>
			    		<li class="mui-table-view-cell">
			    			<span class="mui-pull-left mr">课程地点：</span><p><s:property value="place"/><span class="mui-pull-right mui-icon mui-icon-location"></span></p>
			    		</li>
			    		<li class="mui-table-view-cell">
			    			<span class="mui-pull-left mr">人&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数：</span><p><s:property value="num"/></p>
			    		</li>
			    		<li class="mui-table-view-cell">
			    			<span class="mui-pull-left mr">价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格：</span><p><s:property value="costs"/></p>
			    		</li>
			    	</ul>
			    </div>
			</div>
			
		</div>
	</body>
	<script src="${pageContext.request.contextPath}/eg/js/mui.min.js"></script>
	<script type="text/javascript">
		mui('.mui-scroll-wrapper').scroll({
			deceleration:0.0005
		})
	</script>
</html>