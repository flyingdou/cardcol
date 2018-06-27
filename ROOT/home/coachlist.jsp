<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="keywords" content="健身教练,私人健身教练,网络健身教练,健身私教课,健身教练资格证书,健身私教课程,健身教练证书,设定健身课程" />
<meta name="description" content="私人健身教练,网络健身教练的健身课程和健身计划" />
<title><s:property value="#session.currentCity" />_健身教练</title>
<s:include value="/share/meta.jsp" /> 
<link type="text/css" href="css/bootstrap.css" rel="stylesheet">
<link type="text/css" href="css/base.css" rel="stylesheet">
<link type="text/css" href="css/teach.css" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="script/jRating/jRating.jquery.css" />
<script type="text/javascript" src="script/jRating/jRating.jquery.js"></script>
<script type="text/javascript" src="js/slider.js"></script>
<script type="text/javascript" src="js/teach.js"></script>
<script type="text/javascript" src="js/returnTop.js"></script>
<script type="text/javascript">
	var $ = jQuery;
	$(".avgGrade").jRating({
		length : 10,
		decimalLength : 1,
		rateMax : 100,
		isDisabled : true
	});
	$('#orderStartTime').datepicker({
		changeYear : true
	});

	function onQuery() {
		queryByPage(1);
	}
	function onServiceQuery(typeId, typeName) {
		jQuery("#typeId").val(typeId);
		jQuery("#typeName").val(typeName);
		onQuery();
	}
	function onCoachQuery(idObj, idVal) {
		jQuery("#" + idObj).val(idVal);
		onQuery();
	}
	function onQuery(type, code, name) {
		//所在地区
		if (type == "county") {
			jQuery("#county").val(name);
		}
		//教练专长
		if (type == "speciality") {
			jQuery("#speciality").val(code);
			jQuery("#specialityName").val(name);
		}
		//运动项目
		if (type == "course") {
			jQuery("#course").val(code);
			jQuery("#courseName").val(name);
		}
		//服务方式
		if (type == "mode") {
			jQuery("#mode").val(code);
			jQuery("#modeName").val(name);
		}
		//教练类型
		if (type == "style") {
			jQuery("#style").val(code);
			jQuery("#styleName").val(name);
		}
		//性别
		if (type == "sex") {
			jQuery("#sex").val(code);
			jQuery("#sexName").val(name);
		}
		queryByPage(1);
	}
	function onCancelQuery() {
		jQuery("#mode").val("");
		jQuery("#modeName").val("");
		jQuery("#sex").val("");
		jQuery("#sextName").val("");
		jQuery("#speciality").val("");
		jQuery("#specialityName").val("");
		jQuery("#style").val("");
		jQuery("#styleName").val("");
		jQuery("#county").val("");
		jQuery("#countyName").val("");
		jQuery("#course").val("");
		jQuery("#courseName").val("");
		jQuery("#sex").val("");
		jQuery("#sexName").val("");
		onQuery();
	}
	function preShop(productId, memberId, memberName) {
		if ("<s:property value="#session.loginMember.id"/>" == "") {
			openLogin();
			return;
		}
		if ("<s:property value="#session.loginMember.coach.id"/>"
				&& "<s:property value="#session.loginMember.coach.id"/>" != memberId) {
			alert("您已经有私教，请解除原来私教后再购买新的私教服务！");
			return;
		}
		$.ajax({
			type : 'post',
			url : 'product!edit.asp',
			data : "product.id=" + productId,
			success : function(msg) {
				var product = $.parseJSON(msg)[0];
				$('input[name="product.id"]').val(product.id);
				$('#productMember').html(memberName);
				$('#productName').html(product.name);
				var courseType = product.proType;
				if (courseType == '7') {
					$('#productCourseType').html('计次收费');
					$('#productNum').html(
							product.num + '次,有效期限' + product.wellNum + '月');
					$('#wellNum').val(product.wellNum);
				} else if (courseType == '8') {
					$('#productCourseType').html('计时收费');
					$('#productNum').html(product.num + '月');
					$('#wellNum').val(product.num);
				}
				;
				$('#productCost').html(product.cost + '元');
				$('#productRemark').html(product.remark);
				$("#dialogProduct").dialog("open");
			}
		});
	}
	function onCloseProduct() {
		$("#dialogProduct").dialog("close");
	}
	function saveShop() {
		if ($("#orderStartTime").val()) {
			$
					.ajax({
						type : 'post',
						url : 'shop!checkShop.asp',
						data : $('#productForm').serialize(),
						success : function(msg) {
							if (msg == "isShop") {
								alert("购物车中已存在该教练的课程收费，且时间与本课程收费有冲突，请修改课程收费开始时间或先到购物车中处理其他课程收费！");
								return;
							} else if (msg == "isOrder") {
								alert("订单中已存在该教练的课程收费，且时间与本课程收费有冲突，请修改课程收费开始时间或先到订单中处理其他课程收费！");
								return;
							} else if (msg == "ok") {
								$('#productForm').submit();
							} else {
								alert(msg);
								return;
							}
						}
					});
		} else {
			alert("请选择课程收费开始时间！");
			$("#orderStartTime").focus();
			$("#orderStartTime").select();
		}
	}
	function changeOrderEndTime() {
		var orderStartTime = $('#orderStartTime').val();
		var ost = new Date(orderStartTime);
		var wellNum = $('#wellNum').val();
		ost.setMonth(ost.getMonth() + parseInt(wellNum));
		var yearStr = ost.getFullYear();
		var monthStr = (ost.getMonth() + 1) > 9 ? (ost.getMonth() + 1) : "0"
				+ (ost.getMonth() + 1);
		var dateStr = ost.getDate() > 9 ? ost.getDate() : "0" + ost.getDate();
		$("#endTime").html(yearStr + "-" + monthStr + "-" + dateStr);
		$("#orderEndTime").val(yearStr + "-" + monthStr + "-" + dateStr);
		$("#shopReportDay").val(dateStr);
		$("#shopReportDaySpan").html(dateStr);
	}
	$(function() {
		$("#dialogProduct").dialog({
			width : 460,
			autoOpen : false,
			show : "blind",
			hide : "explode",
			modal : true,
			resizable : false
		});
	});
	
	//修改图片的样式
	$(function() {
		var width_zhi;
		$('.flexslider ').css({
			width:function(index,value){
				width_zhi=value;
			},
			height:function(index,value){
				var height_zhi=width_zhi;
				var rs=height_zhi.replace(/[^0-9]+/ig,'');
				var ban_pic=rs*0.35+"px";
// 				alert(ban_pic);
				return  ban_pic;
			}
		});
	});

</script>
</head>
<body>
	<s:include value="/newpages/head.jsp"></s:include>
	<div class="top_bg"></div>
	<div class="container">
		<s:form id="queryForm" name="queryForm" method="post"
			action="coachlist!onQuery.asp" theme="simple">
			<s:hidden name="shop.id" id="shopId" />
			<s:hidden name="product.id" id="productId" />
			<s:hidden name="member.id" id="memberId" />
			<s:hidden name="typeId" id="typeId" />
			<s:hidden name="typeName" id="typeName" />
			<s:hidden name="area" id="area" />
			<s:hidden name="grade" id="grade" />
			<s:hidden name="orderType" id="orderType" />
			<s:hidden name="county" id="county" />
			<s:hidden name="speciality" id="speciality" />
			<s:hidden name="specialityName" id="specialityName" />
			<s:hidden name="course" id="course" />
			<s:hidden name="courseName" id="courseName" />
			<s:hidden name="mode" id="mode" />
			<s:hidden name="modeName" id="modeName" />
			<s:hidden name="style" id="style" />
			<s:hidden name="styleName" id="styleName" />
			<s:hidden name="keyword" id="keyword" />
			<s:hidden name="sex" id="sex" />
			<s:hidden name="sexName" id="sexName" />
			<div class="row">
				<!--左侧导航栏-->
				<div class="left col-md-2">
					<div class="left_one">
						<div class="left_inner">
							<div class="left_list">
								<span><i class="glyphicon glyphicon-play"></i>所在地区</span>
								<div id="select1" class="inner_list">
									<span class="select-all"><a
										href="javascript:onQuery('county','','');">全部</a></span>
									<s:iterator value="#request.areaList">
										<a
											href="javascript:onQuery('county','<s:property value="id"/>','<s:property value="name"/>');"><span><s:property
													value="name" /></span></a>
									</s:iterator>
									>
								</div>
							</div>
						</div>
						<div class="left_inner">
							<div class="left_list">
								<span><i class="glyphicon glyphicon-play"></i>教练专长</span>
								<div id="select2" class="inner_list">
									<span class="select-all"><a
										href="javascript:onQuery('speciality','','');">全部</a></span>
									<s:iterator value="#request.specialityList">
										<a
											href="javascript:onQuery('speciality','<s:property value="code"/>','<s:property value="name"/>');"><span><s:property
													value="name" /></span></a>
									</s:iterator>
								</div>
							</div>
						</div>
						<div class="left_inner">
							<div class="left_list">
								<span><i class="glyphicon glyphicon-play"></i>运动项目</span>
								<div id="select3" class="inner_list">
									<span class="select-all"><a
										href="javascript:onQuery('course','','');">全部</a></span>
									<s:iterator value="#request.courseList">
										<a
											href="javascript:onQuery('course','<s:property value="id"/>','<s:property value="name"/>');"><span><s:property
													value="name" /></span></a>
									</s:iterator>
								</div>
							</div>
						</div>
						<div class="left_inner">
							<div class="left_list">
								<span><i class="glyphicon glyphicon-play"></i>服务方式</span>
								<div id="select4" class="inner_list">
									<span class="select-all"><a
										href="javascript:onQuery('mode','','');">全部</a></span>
									<s:iterator value="#request.serviceTypeList">
										<a
											href="javascript:onQuery('mode','<s:property value="code"/>','<s:property value="name"/>');"><span><s:property
													value="name" /></span></a>
									</s:iterator>
								</div>
							</div>
						</div>
						<div class="left_inner">
							<div class="left_list">
								<span><i class="glyphicon glyphicon-play"></i>教练类型</span>
								<div id="select5" class="inner_list">
									<span class="select-all"><a
										href="javascript:onQuery('style','','');">全部</a></span>
									<s:iterator value="#request.styleList">
										<a
											href="javascript:onQuery('style','<s:property value="code"/>','<s:property value="name"/>');"><span><s:property
													value="name" /></span></a>
									</s:iterator>
								</div>
							</div>
						</div>
						<div class="left_inner">
							<div class="left_list">
								<span><i class="glyphicon glyphicon-play"></i>教练性别</span>
								<div id="select6" class="inner_list">
									<span class="select-all"><a
										href="javascript:onQuery('sex','','');">全部</a></span> <span><a
										href="javascript:onQuery('sex','M','男性');">男性</a></span> <span><a
										href="javascript:onQuery('sex','F','女性');">女性</a></span>
								</div>
							</div>
						</div>
					</div>
					<div class="left_two">
						<h6>热门教练</h6>
						<s:iterator value="#request.HotCoach" status="st">
							<s:if test="(#st.index<4)">
								<div class="teach_list">
									<a
										href="javascript:goMemberHome('<s:property value="id"/>','<s:property value="role"/>');">
										<img src="picture/<s:property value="image"/>" />
									</a>
									<p>
										<s:property value="nick" />
										教练
									</p>
								</div>
							</s:if>
						</s:iterator>
					</div>
				</div>
				<!--右侧内容-->
				<div class="right col-md-10">
					<div class="row">
						<div class="col-md-12">
							<div class="flexslider" id="play">
								<ul class="slides">
									<s:iterator value="#request.coachRecommends" status="start">
										<s:if test="(#start.index<4)">
											<li><a href="<s:property value='link'/>"><img
													src="picture/<s:property value="icon"/>"></a></li>
										</s:if>
									</s:iterator>
								</ul>
							</div>
						</div>
					</div>
		    <div id="right-2" style="border: 0px;">
						<s:include value="/home/coachlist_middle.jsp" />
					</div>
				</div>
			</div>
		</s:form>
	</div>

	<s:include value="/newpages/footer.jsp"></s:include>
</body>
</html>