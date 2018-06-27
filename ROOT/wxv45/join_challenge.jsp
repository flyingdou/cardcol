<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link href="${pageContext.request.contextPath }/eg/css/mui.min.css" rel="stylesheet" />
<style>
.mui-table-view .mui-media-object {
	line-height: 60px;
	max-width: 80px;
	height: 60px;
}

.cr {
	color: #FF4401
}

.mui-navigate-right:after, .mui-push-right:after {
	right: 0px;
	content: '\e583';
}

.mui-media-body {
	font-size: 15px;
}

.mui-ellipsis {
	font-size: 13px;
}

.mui-navigate-right {
	font-size: 14px;
}

.cg {
	color: #8f8f94;
}

input[type='number']::-webkit-input-placeholder {
	color: #8f8f94;
	font-size: 13px;
}

.caaa {
	line-height: 40px !important;
	max-width: 40px !important;
	height: 40px !important;
}
}
</style>
</head>

<body>
	<ul class="mui-table-view">
		<s:iterator value="active">
			<li class="mui-table-view-cell mui-media">
				<a href="javascript:;">
					<img class="mui-media-object mui-pull-left" src="${pageContext.request.contextPath }/wxv45/images/jstzxq.png">
					<div class="mui-media-body">
						<s:property value="name" />
						<p class="mui-ellipsis">目标：
							<s:if test="target=='A'">
								体重减少
							</s:if>
							<s:elseif test="target=='B'">
								体重增加
							</s:elseif>
							<s:elseif test="target=='C'">
								运动时间
							</s:elseif>	
							<s:elseif test="#active.target=='D'">
								运动次数
							</s:elseif>	
							<s:elseif test="#active.target=='E'">
								<s:property value="customTareget" />
							</s:elseif>	
						</p>
						<p class="mui-ellipsis">发起人:<s:property value="creator.name" /></p>
					</div>
				</a>
			</li>

			<li class="mui-table-view-cell">
				<a class="mui-navigate-right">
					挑战开始时间
					<span class="mui-pull-right cg">2014-01-02</span>
				</a>
			</li>
			<li class="mui-table-view-cell">
				<a class="mui-navigate-right">
					当前体重
					<span class="mui-pull-right cg">
						<input type="number" name="" id="" value="" placeholder="kg"
							style="text-align: right; margin: 0; padding: 0; height: 21px; border: none; width: 150px;" />
					</span>
				</a>
			</li>
			<li class="mui-table-view-cell">
				<a class="mui-navigate-right">
					挑战保证金
					<span class="mui-pull-right cr"><s:property value="amerce_money"/>元</span>
				</a>
			</li>
		</s:iterator>
	</ul>
	<ul class="mui-table-view bgn">
		<li class="mui-table-view-cell mui-media">
			<a href="javascript:;">
				<img class="mui-media-object mui-pull-left caaa" src="${pageContext.request.contextPath }/wxv45/images/Look-happy.png">
				<div class="mui-media-body">
					<p class="mui-ellipsis" style="height: 40px; white-space: pre-line; font-size: 12px; line-height: 18px;">我自愿参加本挑战活动，无条件遵守活动规则，同意运营方根据挑战规则处置保证金。</p>
				</div>
			</a>
		</li>

	</ul>

	<div class="footer" style="width: 100%; position: fixed; bottom: 0; height: 40px;">
		<input type="button" name="" id="" value="支付保证金" style="color: white; background: #FF4401; width: 100%; height: 40px;" />
	</div>

	<script src="${pageContext.request.contextPath }/eg/js/mui.min.js"></script>
	<script type="text/javascript">
		mui.init()
	</script>
</body>

</html>