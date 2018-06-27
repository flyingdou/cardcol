<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>参加挑战首页</title>
<link type="text/css" href=" ${pageContext.request.contextPath}/WX/css/bootstrap.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/WX/css/swiper.min.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/WX/css/base.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/WX/css/style.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/WX/css/index.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/jquery-2.2.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/swiper-3.4.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/style.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/join.js"></script>
<script type="text/javascript">
function query(target){
	$.ajax({
		type : "post",
		url : "activelistwx!activeAll.asp",
		data:"target="+target,
		success:function(msg){
			$("#part").html($(msg).find("#part").html());
	}
	});
}
	function search(){
		  var kw = $("#keyword").val();
			$.ajax({ 
			type:"post", 
			url:"activelistwx!activeAll.asp", 
			data:"keyword="+kw, 
			success:function(msg){
				$("#part").html($(msg).find("#part").html());
			} 
			});
	} 

</script>
</head>
<body>
<div class="join_challenge container">
	<div class="banner swiper-container">
        <div class="swiper-container">
            <ul class="swiper-wrapper">
            <s:iterator value="#request.map.code">
                <li class="swiper-slide">
                <a href="activelistwx!detail.asp?id=<s:property value="id"/>">
                <img src="${pageContext.request.contextPath}/picture/<s:property value="icon"/>" alt="">
                </a> </li>
            </s:iterator>
            </ul>
            	<s:property value="icon"/>
            <div class="swiper-pagination"></div>
        </div>
        <div class="search">
            <input type="search" value="" placeholder="搜索健身目标" id="keyword">
            <span class="glyphicon glyphicon-search search_icon" onclick="search()"></span>
        </div>
    </div>
	<div class="table_title">
        <ul class="clearfix">
            <li class="col-xs-6" ><a href="JavaScript:query('A');" >体重管理</a></li>
            <li class="col-xs-6" ><a href="JavaScript:query('D');">健身次数</a></li>
        </ul>
    </div>
	<div id="part">
			<div class="table_list">
				<s:iterator value="#request.map.items">
					<div class="list">
						<a href="activelistwx!detail.asp?id=<s:property value="id"/>">
							<div class="pic fl">
								<img src="../picture/<s:property value="active_image"/>">
							</div>
							<div class="txt fl">
								<h6>
									<s:property value="name" />
								</h6>
								<p>
									已有
									<s:if test="applyCount!=null">
										<s:property value="applyCount" />
									</s:if>
									<s:else>0</s:else>
									人参加
								</p>
								<p>
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
								</p>
							</div>
						</a>
					</div>
				</s:iterator>
			</div>
	</div>
</div>
<script type="text/javascript" src="js/imgs.js"></script>
</body>
</html>