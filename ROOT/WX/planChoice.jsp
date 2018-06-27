<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta content="UTF-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>计划筛选</title>
<link type="text/css" href="css/bootstrap.css" rel="stylesheet" />
<link type="text/css" href="css/base.css" rel="stylesheet" />
<link type="text/css" href="css/style.css" rel="stylesheet" />
<link type="text/css" href="css/index.css" rel="stylesheet" />
<script type="text/javascript" src="js/jquery-2.2.0.min.js"></script>
<script type="text/javascript" src="js/imgs.js"></script>
<script>
	function onPlanQuery(type, code, name) {
		 if(type == "target") {
			$.ajax({
				type : "post",
				url : "planlistwx!onTerm.asp",
				data : "target=" + code,
				success : function(msg) {
					$("#part").html($(msg).find("#part").html());
				}
			});
		} else if (type == "applyObject") {
			$.ajax({
				type : "post",
				url : "planlistwx!onTerm.asp",
				data : "applyObject=" + code,
				success : function(msg) {
					$("#part").html($(msg).find("#part").html());
				}
			});
		} else if (type == "scene") {
			$.ajax({
				type : "post",
				url : "planlistwx!onTerm.asp",
				data : "scene=" + code,
				success : function(msg) {
					$("#part").html($(msg).find("#part").html());
				}
			});
		} else if(type == "plancircle") {
			$.ajax({
				type : "post",
				url : "planlistwx!onTerm.asp",
				data : "plancircle=" + code,
				success : function(msg) {
					$("#part").html($(msg).find("#part").html());
				}
			});
		} 
	}
</script>
</head>
<body>
	<div class="planChoice container">
		<ul>
			<li><a href="planlistwx!onTerm.asp" class="all">所有</a>
			<s:iterator value="#request.planTypeList">
					<a
						href="javascript:onPlanQuery('target','<s:property value="code"/>','<s:property value="name"/>');"><s:property
							value="name" /></a>
				</s:iterator></li>
			<li><a href="javascript:onQuery('applyObject','','');"
				class="all">所有</a>
			<s:iterator value="#request.applyObjectList">
					<a
						href="javascript:onPlanQuery('applyObject','<s:property value="code"/>','<s:property value="name"/>');"><s:property
							value="name" /></a>
				</s:iterator></li>
			<li><a href="javascript:onQuery('scene','','');" class="all">所有</a>
			<s:iterator value="#request.applySceneList">
					<a
						href="javascript:onPlanQuery('scene','<s:property value="code"/>','<s:property value="name"/>');"><s:property
							value="name" /></a>
				</s:iterator></li>
			<li><a href="javascript:onQuery('plancircle','','');"
				class="all">所有</a>
			<s:iterator value="#request.plancircleList">
					<a
						href="javascript:onPlanQuery('plancircle','<s:property value="code"/>','<s:property value="name"/>');"><s:property
							value="name" /></a>
				</s:iterator></li>
		</ul>
		<div class="table_list" id="part">
			<s:iterator value="pageInfo.items">
				<div class="list">
					<a href="plandetail.asp?id=<s:property value="id"/>">
						<div class="pic fl">
							<img src="../picture/<s:property value="image1"/>" alt="">
						</div>
						<div class="txt fl">
							<h6>
								<s:property value="plan_name" />
							</h6>
							<p>
								已有<span><s:if test="saleNum==0\\saleNum==null">0
                                   </s:if> <s:else>
										<s:property value="saleNum" />
									</s:else> </span>人参加
							</p>
							<p>计划说明</p>
						</div>
					</a>
				</div>
			</s:iterator>
		</div>
	</div>
</body>
</html>