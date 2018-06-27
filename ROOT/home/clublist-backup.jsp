<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="keywords" content="合作俱乐部" />
<meta name="description" content="合作俱乐部" />
<title><s:property value="#session.currentCity" />健身俱乐部</title>
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
<body>
<s:include value="/newpages/head.jsp"></s:include>
<div class="top_bg"></div>
	<div class="container">
		<s:form id="queryForm" name="queryForm" method="post"
			action="clublist!onQuery.asp" theme="simple">
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
			<div class="row">
				<!--左侧导航栏-->
				<div class="left col-md-2 ">
					<div class="left_one">
						<div class="left_list">
							<span><i class="glyphicon glyphicon-play"></i>所在地区</span>
							<div id="select1" class="inner_list">
								<span class="select-all"><a
									href="javascript:onClubQuery('area','');">全部</a></span>
								<s:iterator value="areaList">
									<span><a
										href="javascript:onClubQuery('area','<s:property value="name"/>');">
											<s:property value="name" />
									</a></span>
								</s:iterator>
							</div>
						</div>
						<div class="left_list">
							<span><i class="glyphicon glyphicon-play"></i>健身卡种</span>
							<div id="select2" class="inner_list">
								<span class="select-all"><a
									href="javascript:onProTypeQuery('','');">全部</a></span>
								<s:iterator value="proTypeList">
									<span><a
										href="javascript:onProTypeQuery('<s:property value="code"/>','<s:property value="name"/>');">
											<s:property value="name" />
									</a></span>
								</s:iterator>
							</div>
						</div>
						<div class="left_list">
							<span><i class="glyphicon glyphicon-play"></i>运动项目</span>
							<div id="select3" class="inner_list">
								<span class="select-all"><a
									href="javascript:onServiceQuery('','');">全部</a></span>
								<s:iterator value="typeList">
									<span><a
										href="javascript:onServiceQuery('<s:property value="id"/>','<s:property value="name"/>');">
											<s:property value="name" />
									</a></span>
								</s:iterator>
							</div>
						</div>
						<div class="left_list">
							<span><i class="glyphicon glyphicon-play"></i>场地预定</span>
							<div id="select4" class="inner_list">
								<span class="select-all"><a
									href="javascript:onClubQuery('changguangyuding','');">全部</a></span> <span><a
									href="javascript:onClubQuery('changguangyuding','1');">有</a></span> <span><a
									href="javascript:onClubQuery('changguangyuding','2');">无</a></span>
							</div>
						</div>
					</div>

					<div class="charitable">
						<p>
							<span>合作慈善机构</span>
						</p>
					</div>
					<div class="left_two">
						<img src="images/left_adv_03.png">
						<h5>什么是圈存健身卡?</h5>
						<p>会员与俱乐部之间按出勤数据结算健身费用的健身卡。会员出勤数越多，应支付的费用越少。</p>
					</div>
					<div class="left_two">
						<img src="images/left_adv_07.png">
						<h5>什么事对赌健身卡?</h5>
						<p>消费者在卡库网上购卡后，资金由银行托管，按月结算。可以规避预付消费的风险，维护买卖双方合法权益。</p>
					</div>
					<div class="left_two">
						<img src="images/left_adv_11.png">
						<h5>什么是预付健身卡?</h5>
						<p>消费者购卡后，资金一次性全部支付给商户，然后再按合同约定的条款接受商户服务的交易方式。</p>
					</div>
				</div>
				<!--右侧内容-->
				<div class="right col-md-10">
					<div class="row">
                    <div class="col-md-8 col-lg-8">
                        <ul class="slides ">
                        <s:iterator value="#request.factoryRecommends" status="st">
                        <s:if test="(#st.index)<5">
                            <li><a href="<s:property value='link'/>">
                            <img src="<s:if test="icon == null || icon == \"\"">images/img-defaul.jpg</s:if><s:else>picture/<s:property value="icon"/></s:else>"></a> </li></s:if></s:iterator>
                        </ul>
                    </div>
                    <ul class="col-md-4 col-lg-4">
                    <s:iterator value="#request.factoryRecommends" status="st">
                    <s:if test="(#st.index<5)">
                        <li class="store_address"><h5><s:property value="title"/></h5>
                        <p>
                        <s:if test="summary.length()>41">
                        <s:property value="summary.substring(0,40)"/>...</s:if>
                        <s:elseif test="summary.length()==0"><br></s:elseif>
                        <s:else><s:property value="summary"/></s:else>
                        </p></li></s:if></s:iterator>
                    </ul>
                </div>
					
					<div id="right-2" style="border: none;">
						<s:include value="/home/clublist_middle.jsp"></s:include>
					</div>
				</div>
			</div>
		</s:form>
	</div>
	<s:include value="/newpages/footer.jsp"></s:include>
</body>
</html>