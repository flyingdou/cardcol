<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="keywords" content="合作俱乐部" />
<meta name="description" content="合作俱乐部" />
<title><s:property value="#request.currentCity" />健身俱乐部
</title>
<s:include value="/share/meta.jsp"></s:include>
<link type="text/css" href="css/bootstrap.css" rel="stylesheet">
<link type="text/css" href="css/base.css" rel="stylesheet">
<link type="text/css" href="css/venue.css" rel="stylesheet">
<script type="text/javascript" src="js/venue.js"></script>
<script type="text/javascript" src="js/returnTop.js"></script>
<script type="text/javascript">
	function onQuery() {
		queryByPage(1);
	}
	function onServiceQuery(typeId, typeName) {
		jQuery("#typeId").val(typeId);
		jQuery("#typeName").val(typeName);
		onQuery();
	}
	function onClubQuery(idObj, idVal) {
		jQuery("#" + idObj).val(idVal);
		onQuery();
	}
	function onProTypeQuery(protype, protypename) {
		jQuery("#proType").val(protype);
		jQuery("#proTypeName").val(protypename);
		onQuery();

	}
	function onCancelQuery() {
		jQuery("#proType").val("");
		jQuery("#proTypeName").val("");
		jQuery("#typeId").val("");
		jQuery("#typeName").val("");
		jQuery("#area").val("");
		jQuery("#changguangyuding").val("");

		onQuery();
	}
	function preShop(productId) {
		url = "clublist!shoGo.asp?productId=" + productId;
		$('#queryForm').attr('action', url);
		$('#queryForm').submit();
	}

	function toCourseWindow(clubId) {
		if (!"<s:property value="#session.loginMember.id"/>") {
			openLogin();
			return;
		}
		$('#clubId').val(clubId);
		$('#queryForm').attr('action', 'coursewindow.asp');
		$('#queryForm').submit();
	}
	function toFactoryWindow(clubId) {
		if (!"<s:property value="#session.loginMember.id"/>") {
			openLogin();
			return;
		}
		$('#clubId').val(clubId);
		$('#queryForm').attr('action', 'factoryorderwindow.asp');
		$('#queryForm').submit();
	}
</script>
</head>
<body style="background: #f6f6f6;">
	<s:include value="/newpages/head.jsp"></s:include>
	<div class="container" style="padding-top: 70px">
		
			<div class="row">
				<div class="col-md-6" style="padding: 15px 15px; font-weight: 500; font-size: 14px; box-sizing: border-box">
					<a href="${pageContext.request.contextPath}/index.asp">首页</a>
					&nbsp;>&nbsp;俱乐部中心</div>
				<div class="col-md-6" style="padding: 15px 15px; font-size: 15px; box-sizing: border-box；text-align:right">
					<form action="clublist.asp">
						<input type="submit" value="搜索"
							style="background: #ff4401; color: white; padding: 4px 8px; float: right; height: 23px">
						<input type="text" placeholder="搜索俱乐部"  name="keyword" value='<s:property value='#request.keyword' />'
							style="padding: 2px 4px; min-width: 260px; float: right; border: 1px solid #bfbfbf; box-sizing: border-box; border-right: none; overflow: hidden">
						<div style="clear: both"></div>
						<input type="hidden" name="queryType" value="0">
					</form>
				</div>
				
			<s:form id="queryForm" name="queryForm" method="post" action="clublist!onQuery.asp" theme="simple">
						<s:hidden name="product.id" id="productId" />
						<s:hidden name="member.id" id="memberId" />
						<s:hidden name="typeId" id="typeId" />
						<s:hidden name="typeName" id="typeName" />
						<s:hidden name="area" id="area" />
						<s:hidden name="proType" id="proType" />
						<s:hidden name="proTypeName" id="proTypeName" />
						<s:hidden name="changguangyuding" id="changguangyuding" />
						<s:hidden name="grade" id="grade" />
						<s:hidden name="orderType" id="orderType" />
						<s:hidden name="keyword" id="keyword" />
						<s:hidden name="clubId" id="clubId" />
						
				<div class="row" style="padding: 0 30px">
					<!--左侧导航栏-->
					<div class="left col-md-12 " style="background: white; padding-right: 0">
						<div class="left_one">
							<div class="left_list">
								<div class="left col-md-2 ">
									<span>
										<i class="glyphicon glyphicon-play"></i>地区
									</span>
									<span class="select-all m20">
										<a style="background: #ff4401; padding: 0px 4px; border-radius: 2px" href="javascript:onClubQuery('area','');">全部</a>
									</span>
								</div>
								<div class="left col-md-10 " style="padding: 0 15px; box-sizing: border-box; overflow: hidden">

									<s:iterator value="areaList">
										<span class="m20" style="display: inline-block">
											<a href="javascript:onClubQuery('area','<s:property value="name"/>');">
												<s:property value="name" />
											</a>
										</span>
									</s:iterator>
									<!-- </div> -->
								</div>
								<div style="clear: both"></div>
							</div>

							<div class="left_list">
								<div class="left col-md-2 ">
									<span>
										<i class="glyphicon glyphicon-play"></i>项目
									</span>
									<span class="select-all m20">
										<a style="background: #ff4401; padding: 0px 4px; border-radius: 2px" href="javascript:onServiceQuery('','');">全部</a>
									</span>
								</div>
								<div class="left col-md-10 " style="padding: 0 15px; box-sizing: border-box; overflow: hidden">
									<s:iterator value="typeList">
										<span class="m20" style="display: inline-block">
											<a href="javascript:onServiceQuery('<s:property value="id"/>','<s:property value="name"/>');">
												<s:property value="name" />
											</a>
										</span>
									</s:iterator>
								</div>
								<div style="clear: both"></div>
							</div>
						</div>
						<!--右侧内容-->
						<div class="right col-md-12" style="padding-left: 0">
							<div id="right-2" style="border: none;">
								<s:include value="/home/clublist_middle.jsp"></s:include>
							</div>
						</div>
					</div>
				</div>
		</s:form>
	</div>
	<s:include value="/newpages/footer.jsp"></s:include>
</body>
</html>