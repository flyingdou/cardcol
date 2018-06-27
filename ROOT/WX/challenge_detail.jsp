<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>可参加的挑战-挑战详情</title>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="apple-mobile-web-app-capable" content="yes">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/eg/css/mui.min.css"/>
		<style type="text/css">
			.title{
				height:150px;
				background-image:url(../images/shuijiao.jpg);
				background-repeat: no-repeat;
				background-position:100% auto;
				background-size:100% 100%;
			}
			.zhezhao{
				background-color:rgba(0,0,0,.7);
				position:absolute;
				top:0;
				width:100%;
				height:150px;
				z-index: 1111;
			}
			.yh_img{
				float:left;
				width:100px;
				height:75px;
				margin-left: 20px;
    			margin-top: 35px;
			}
			.xx{
				float:left;
				line-height:12px;
				margin:45px 20px;
				/*border:1px solid #111;*/
			}
			.xx .bt{
				color:white;
			}
			.mui-table-view{
				/*margin-top:20px;*/
				margin-bottom:20px;
			}
			.list{
				line-height:15px;
			}
			.list img{
				width:20px;
				height:20px;
				margin-right:5px;
			}
			.list .xm{
				color:#a0a0a0;
				position:absolute;
				top:13px;
				font-size:15px;
			}
			.list .zt{
				color:#999999;
				font-size:15px;
			}
			.zhuyi{
				font-size:15px;
				font-weight:bold;
				margin-bottom:10px;
				color:#666666;
			}
			.zhuyi_nr{
				font-size:15px;
				color:#999999;
				height: 120px;
			}
			
			
			/*分享图标*/
			.mui-bar .mui-icon {
    			font-size: 20px;
    			color:white;
    			}
			/*底部样式*/
			.fu{
				line-height:45px;
				width:50%;
				float:left;
				text-align:center;
				font-size:15px;
				background-color:#666666;
				
			}
			.mui-bar-footer span:nth-child(2){
				float:left;
				line-height:45px;
				width:50%;
				background-color:#FE9E3A;
				margin-right:0px;
				color:#FFFFFF;
				overflow:hidden;
				text-align:center;
				font-size:15px;
			}
			/*去掉底部左右两边的padding值*/
			.mui-bar {
			    padding-right: 0px;
			    padding-left: 0px;
			}
			#share{
				color:#FFFFFF;
			}
		.mui-table-view-cell:after{
			background: rgba(0,0,0,0);
		}
		</style>
	</head>
	<body>
		<!-- 挑战详情开始 -->
		<s:iterator value="active">
		<footer class="mui-bar mui-bar-footer" >
			<a id='submit_order' href="activelistwx!joinChallenge.asp?id=<s:property value="id" />" style="display:block;width: 100%;height:100%;text-align: center;;line-height: 44px;font-size:14px;color:white;background: orange;">
				参加挑战
			</a>
		</footer>
		<div class="mui-content">
			<div class="mui-scroll-wrapper" >
			    <div class="mui-scroll">
			    	<div class="title">
			    		<div class="zhezhao">
			    			<img src="../images/shuijiao.jpg" class="yh_img"/>
			    			<div class="xx">
			    				<p class="bt">发布人：<span id="name"><s:property value="creator.name" /></span></p>
			    				<p class="bt">裁判人：<span id="caipan"><!-- 活动发起人 --></span></p>
			    				<p class="bt">参加人数：<span id="number"><s:property value="#request.count" /></span>人</p>
			    			</div>
			    		</div>
			    	</div>
			        <!--这里放置真实显示的DOM内容-->
			        <!--挑战目标部分-->
					<ul class="mui-table-view">
						<li class="mui-table-view-cell list"style="position:relative;padding-right: 0;">
							<div style="display:inline-block;position:absolute;">	
								<img src="${pageContext.request.contextPath}/WX/images/mubiao.png"style="margin-left: 1em;margin-bottom: .5em;" />
							    <p><span class="">挑战目标</span></p>
							</div>
								<div class="mui-pull-right zt" style="width:100%;padding-top:15px;padding-left:70px;display:inline-block;float: left;"id="goal">
									<div style="width: 100%;border-bottom: 1px solid #eaeaea;height: 100%;;padding-bottom:1.5em;">
										<s:if test="category=='A'">
											<s:property value="days" />天内体重增加<s:property value="value" />KG</s:if>
										<s:elseif test="category=='B'">
											<s:property value="days" />天内体重减少<s:property value="value" />KG</s:elseif>
										<s:elseif test="category=='D'">
											<s:property value="days" />天内运动<s:property value="value" />次</s:elseif>
										<s:elseif test="category=='E'">
											<s:property value="days" />天内运动<s:property value="value" />小时</s:elseif>
										<s:elseif test="category=='F'">
											<s:property value="days" />天内每周运动<s:property value="value" />次</s:elseif>
										<s:elseif test="category=='G'">
											<s:property value="days" />天内
										<s:if test="action != null">
											<s:property value="action"/>
										</s:if>
										<s:else>
											<s:property value="content"/>
										</s:else>
											<s:property value="value" />千米</s:elseif>
										<s:elseif test="category=='H'">
									   		<s:property value="days" />天负荷<s:property value="value" />Kg
										</s:elseif>
									</div>
								</div>
						</li>
							<li class="mui-table-view-cell list"style="position:relative;padding-right: 0;">
							<div style="display:inline-block;position:absolute;">	
								<img src="${pageContext.request.contextPath}/WX/images/jiangbei.png"style="margin-left: 1em;margin-bottom: .5em;" />
							    <p><span class="">成功奖励</span></p>
							</div>
								<div class="mui-pull-right zt" style="width:100%;padding-top:15px;padding-left:70px;display:inline-block;float: left;"id="goal">
									<div style="width: 100%;border-bottom: 1px solid #eaeaea;height: 100%;;padding-bottom:1.5em;">
										<s:property value="award" />
									</div>
								</div>
						</li>
							<li class="mui-table-view-cell list"style="position:relative;padding-right: 0;">
							<div style="display:inline-block;position:absolute;">	
								<img src="${pageContext.request.contextPath}/WX/images/chengfa.png"style="margin-left: 1em;margin-bottom: .5em;" />
							    <p><span class="">失败惩罚</span></p>
							</div>
								<div class="mui-pull-right zt" style="width:100%;padding-top:15px;padding-left:70px;display:inline-block;float: left;"id="goal">
									<div style="width: 100%;border-bottom: 1px solid #eaeaea;height: 100%;;padding-bottom:1.5em;">
										向<s:property value="institution.name" />捐款 <s:property value="amerceMoney" />元
									</div>
								</div>
						</li>
							<li class="mui-table-view-cell list"style="position:relative;padding-right: 0;">
							<div style="display:inline-block;position:absolute;">	
								<img src="${pageContext.request.contextPath}/WX/images/zhuyishixiang.png" style="margin-left: 1em;margin-bottom: .5em;" />
							    <p><span class="">注意事项</span></p>
							</div>
								<div class="mui-pull-right zt" style="width:100%;padding-top:15px;padding-left:70px;display:inline-block;float: left;"id="goal">
									<div style="width: 100%;height: 100%;;padding-bottom:1.5em;">
										<s:property value="memo"/>
      									<p>请将运动成绩记录到训练日志中，成绩将自动发送给裁判评判实性。如挑战成功，保证金将全额退还。</p>
									</div>
								</div>
						</li>
			    </div>
			</div>
		</div>
	    </s:iterator>
	    <!-- 挑战详情结束 -->
	</body>
	<script src="${pageContext.request.contextPath}/eg/js/mui.min.js"></script>
	<script>
		mui('.mui-scroll-wrapper').scroll({
			deceleration:0.0005
		})
	</script>
</html>
