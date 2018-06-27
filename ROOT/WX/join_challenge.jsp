<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>健身挑战</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<link rel="stylesheet"  href="${pageContext.request.contextPath}/eg/css/mui.min.css"  />
		<style>
			.mui-card .mui-control-content {
				padding: 10px;
			}
			.mui-control-content {
				height: 700px;		/*此处数据后期需修改*/
			}
			.mui-media-body .changer_name{
				color:black;
				font-size:13px;
				
			}
			.tackpart_p{
				font-size:12px;
			}
			.tackpart_m{
				font-size:12px;
			}
			.mui-active{
				color:orange!important;	
				background: rgba(0,0,0,0)!important;		}
				.mui-active .tzjs{
					border-bottom: 2px solid orange;
					box-sizing: border-box;
					color:orange;
				}
				.mui-control-item{
					color:black!important;
					border-left:none!important;
				}
				.tzjs{
					display: inline-block;
					height: 25px;
					line-height: 20px;
				}
				
		</style>
	</head>
		<script type="text/javascript">
		function query(target){
			$.ajax({
				type : "post",
				url : "activelistwx!activeAll.asp",
				data:"target=" + target,
				success:function(msg){
					debugger;
					$("#refresh").html($(msg).find("#refresh").html());
				}
			}); 
		}
		
		function showActiveDetail (id) {
			window.location.href="activelistwx!detail.asp?id=" + id
		}
	</script>
	<body>
		<div class="mui-content" style="top:45px;">
			<!--选择栏-->
	        <div style="padding: 10px 10px;background: white;margin-bottom: 5px;border-bottom:1px solid #eaeaea">
				<div id="segmentedControl" class="mui-segmented-control"style="outline:rgba(0,0,0,0);border:1px solid rgba(0,0,0,0);">
					<a class="mui-control-item mui-active"  href="#" class="tzjsa">
						<div class="tzjs" onclick="query('A')">体重管理</div>
					</a>
					<a class="mui-control-item" class="tzjsa" href="#" >
						<div class="tzjs"  onclick="query('D')">健身次数</div>
					</a>
				</div>
			</div>
			<!--列表页面-->
			<div id="refresh">
				<div id="item1" class="mui-control-content mui-active">
					<div id="scroll" class="mui-scroll-wrapper">
						<div class="mui-scroll">
						<ul class="mui-table-view" id="ul1">
							<!-- 挑战列表开始  -->
							<s:iterator value="#request.map.items">
							<li class="mui-table-view-cell mui-media" id="challenge_detail.html" onclick="showActiveDetail(<s:property value="id"/>)">
								<a href="javascript:;">
									<img class="mui-media-object mui-pull-left" src="" style="width:80px;height:60px;max-width: 80px;">
									<div class="mui-media-body">
										<p class="changer_name"><s:property value="name" /></p>
										<p class='mui-ellipsis-2  tackpart_p' id="tackpart_p">已有
											<span>
												<s:if test="applyCount!=null">
													<s:property value="applyCount" />
												</s:if>
												<s:else>0</s:else>
											</span>人参加</p>
										<p  class=" tackpart_m">目标：
										<span id="tackpart_m">
											<s:if test="category==\"A\"">
												<s:property value="days" />天内体重增加<s:property value="value" />KG</s:if>
											<s:elseif test="category==\"B\"">
												<s:property value="days" />天内体重减少<s:property value="value" />KG</s:elseif>
											<s:elseif test="category==\"D\"">
												<s:property value="days" />天内运动<s:property value="value" />次</s:elseif>
											<s:elseif test="category==\"E\"">
												<s:property value="days" />天内运动<s:property value="value" />小时</s:elseif>
											<s:elseif test="category==\"F\"">
												<s:property value="days" />天内每周运动<s:property value="value" />次</s:elseif>
											<s:elseif test="category==\"G\"">
												<s:property value="days" />天内
											<s:if test="action != null">
												<s:property value="action"/>
											</s:if>
											<s:else>
												<s:property value="content"/>
											</s:else>
												<s:property value="value" />千米</s:elseif>
											<s:elseif test="category==\"H\"">
										   		<s:property value="days" />天负荷<s:property value="value" />Kg
											</s:elseif>
										</span></p>
									</div>
								</a>
							</li>
							</s:iterator>
							<!-- 挑战列表结束  -->
						</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
		
	</body>
	<script src="${pageContext.request.contextPath}/eg/js/mui.min.js"></script>
	<script src="${pageContext.request.contextPath}/eg/js/jquery-1.11.1.min.js"></script>
	<script>
		mui('.mui-scroll-wrapper').scroll({
			deceleration:0.0005
		});
	</script>
</html>
