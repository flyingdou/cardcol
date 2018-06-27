<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="keywords" content="健身E卡通,健身详情,计划详情" />
<meta name="description" content="根据自身喜好选择自身的健身计划，查看详情" />
<title>计划详情</title>
<link rel="stylesheet" type="text/css" href="css/smoothness/template.css" />
<link rel="stylesheet" type="text/css" href="css/smoothness/jquery-ui.css" />
<script type="text/javascript" src="script/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="script/common.js"></script> 
<script type="text/javascript" src="script/hoverIntent.js"></script>
<script type="text/javascript" src="script/superfish.js"></script> 
<script type="text/javascript" src="script/md5.js"></script>
<script type="text/javascript" src="script/jquery.overlay.min.js"></script>
<script type="text/javascript" src="script/jquery-ui.js"></script>
<script type="text/javascript" src="script/jquery-ui-lang.js"></script>
<link type="text/css" href="css/bootstrap.css" rel="stylesheet">
<link type="text/css" href="css/base.css" rel="stylesheet">
<link type="text/css" href="css/planXq.css" rel="stylesheet">
<link type="text/css" href="js/datepicker/skin/WdatePicker.css"
	rel="stylesheet" />
<script type="text/javascript" src="js/datepicker/WdatePicker.js"></script>
<link type="text/css" rel="stylesheet"
	href="script/jRating/jRating.jquery.css" />
<script type="text/javascript" src="script/jRating/jRating.jquery.js"></script>
<script type="text/javascript" src="js/planXq.js"></script>
<script type="text/javascript" src="js/returnTop.js"></script>
<script type="text/javascript">
	$(function() {
		$(".avgCount").jRating({
			type : 'small',
			isDisabled : true,
			step : false,
			length : 5,
			decimalLength : 1,
			rateMax : 100
		});
	})
	// 	function changed(li) {
	// 		var aBtnd = document.getElementById("sort");
	// 		var lisd = aBtnd.getElementsByTagName("li");
	// 		var aDivd = document.getElementById("container_pro")
	// 				.getElementsByClassName("part");
	// 		for (var i = 0; i < lisd.length; i++) {
	// 			if (li == lisd[i]) {
	// 				aDivd[i].style.display = "block";
	// 				lisd[i].style.color = "#FF0000";
	// 			} else {
	// 				aDivd[i].style.display = "none";
	// 				lisd[i].style.color = "#000000";
	// 			}
	// 		}
	// 	}

	// 	function preShop(productId, memberId) {
	// 		url = "plan.asp?pid=" + productId;
	// 		$('#queryForm').attr('action', url);
	// 		$('#queryForm').submit();
	// 	}

	// 	function save() {
	// 		if (!"<s:property value="#session.loginMember.id"/>") {
	// 			openLogin();
	// 			return;
	// 		}
	// 		if ("<s:property value="member.id"/>" == "<s:property value="#session.loginMember.id"/>") {
	// 			alert("自己不能给自己发表评价！");
	// 			return;
	// 		}
	// 		if (!$(".grade").attr("rate")) {
	// 			alert("请先评分！");
	// 			return;
	// 		} else {
	// 			$("input[name='planReleaseGrade.grade']").val(
	// 					$(".grade").attr("rate"));
	// 		}
	// 		if (!$("textarea[name='planReleaseGrade.content']").val()) {
	// 			alert("请先输入评价内容！");
	// 			$("textarea[name='planReleaseGrade.content']").focus();
	// 			$("textarea[name='planReleaseGrade.content']").select();
	// 			return;
	// 		}
	// 		var parms = $('#queryForm').serialize() + '&t1='
	// 				+ ($('#checkbox1').attr('checked') === 'checked' ? 1 : 0)
	// 				+ '&t2='
	// 				+ ($('#checkbox2').attr('checked') === 'checked' ? 1 : 0);
	// 		$
	// 				.ajax({
	// 					type : 'post',
	// 					url : 'plan!save.asp',
	// 					data : parms,
	// 					success : function(msg) {
	// 						if (msg == "ok") {
	// 							alert('评价成功,并已同步微博！');
	// 							window.location.href = "plan.asp?pid=<s:property value="planRelease.id"/>";
	// 						} else if (msg == "noSinaId") {
	// 							alert("当前最新身体数据已成功保存,同步微博失败：您还未关联微博账户！");
	// 							window.location.href = "plan.asp?pid=<s:property value="planRelease.id"/>";
	// 						} else if (msg == "okRecord") {
	// 							alert("当前最新身体数据已成功保存！");
	// 							window.location.href = "plan.asp?pid=<s:property value="planRelease.id"/>";
	// 						} else {
	// 							alert(msg);
	// 						}
	// 					}
	// 				});
	// 	}

	// 	function onClosec() {
	// 		$('#dialog').dialog('close');
	// 	}

	function saveShopPlan() {
		if (!"<s:property value="#session.loginMember.id"/>") {
			openLogin();
			return;
		}
		$.ajax({
			url : 'shop!saveShopPlan.asp',
			method : 'post',
			data : $('#queryForm').serialize(),
			success : function(msg) {
				if (msg == "ok") {
					alert("加入购物车成功！");
					countShop();
				} else {
					alert(msg);
				}
			}
		});

	}
	function shopPlan() {
		if (!"<s:property value="#session.loginMember.id"/>") {
			openLogin();
			return;
		}
		if ($('#startDate').val() == null | $('#startDate').val() == "") {
			alert("请选择计划开始日期！");
			return;
		}
		var _unitPrice = $('#unitPrice').val();
		if (_unitPrice == 0) {
			$(document).mask('请稍候,正在生成计划......');
			$.ajax({
				url : 'order!genPlan.asp',
				method : 'post',
				data : {
					'id' : $('#planReleaseId').val(),
					'startDate' : $('#startDate').val()
				},
				success : function(msg) {
					$(document).unmask();
					var json = $.parseJSON(msg);
					if (json.success === true) {
						alert("您的计划已经成功生成，请进入用户中心在我的卡库健身计划中查看计划！");
					} else {
						alert(json.message);
					}
				}
			});
		} else {
			url = 'order!submitProd.asp?prodType=3&id='
					+ $("#planReleaseId").val();
			$('#queryForm').attr("action", url);
			$('#queryForm').submit();
		}
	}

	function onPlanList(code, name) {
		var url = "planlist.asp";
		jQuery("#ptarget").val(code);
		jQuery("#ptargetName").val(name);
		$('#queryForm').attr('action', url);
		$('#queryForm').submit();
	}
	
	
	//调整图片显示大小
	$(function(){
			var width_zhi;
    		$(".middle").css({
    			width : function(index,value){
    				width_zhi=value;
    				return width_zhi;
    			},
    			height  : function(index,value){
    				var height_zhi=width_zhi;
    				var rc=height_zhi.replace(/[^0-9]+/ig,'');
    				var h_zhi=rc*0.75+"px";
    				return h_zhi;
    			}
    		});
	});
</script>

</head>
<body>

	<!--头部区-->
	<s:include value="/newpages/head.jsp"></s:include>
	<div class="top_bg"></div>
	<!--内容区  -->
	<div class="container">
	<s:form id="queryForm" name="queryForm" method="post" action="plan.asp" theme="simple">
			<s:hidden name="planRelease.id" id="planReleaseId" />
			<s:hidden name="member.id" id="memberId" />
			<s:hidden name="flag" id="flag" />
			<s:hidden name="typeId" id="typeId" />
			<s:hidden name="typeName" id="typeName" />
			<s:hidden name="area" id="area" />
			<s:hidden name="ptarget" id="ptarget" />
			<s:hidden name="ptargetName" id="ptargetName" />
			<s:hidden name="planRelease.unitPrice" id="unitPrice"/>
		<div class="row">
			<p class="title_nav">
				<span>网站首页&nbsp;&nbsp;>&nbsp;</span><span>健身计划&nbsp;></span>&nbsp;<span><a
					href="javascript:onPlanList('<s:property value="#request.planParam.code"/>','<s:property value="#request.planParam.name"/>');"><s:property
							value="#request.planParam.name" /> </a>&nbsp;></span>&nbsp;<span><s:property
						value="planRelease.planName" /></span>
			</p>
		</div>
		<div class="row">
			<div class="hot_book clearfix">
				<div class="col-md-6 col-lg-5">
					<div style="position: relative;">
<%-- 						<img src="picture/<s:property value="planRelease.image1"/> " class="middle"> --%>
						<img  class="middle"        src='picture/<s:property value="planRelease.image1"/> '    />
						<p class="book_txt"
							style="position: absolute; left: 0; bottom: 0;width: 100%;">
							<s:property value="planRelease.briefing" />
						</p>
					</div>
				</div>
				<div class="col-md-3 col-lg-4">
					<h4 class="boot_title">
						<s:property value="planRelease.planName" />
					</h4>
					<p class="book_message price">
						价格：¥<span> <s:if
								test="planRelease.unitPrice==0||planRelease.unitPrice==null">0</s:if>
							<s:else>
								<s:property value="planRelease.unitPrice" />
							</s:else></span>
					</p>
					<p class="book_message score">
						评分：<span><s:if test='#request.planAppraise[0].avgGrade>0'>
								<s:property value='#request.planAppraise[0].avgGrade' />
							</s:if> <s:else>0</s:else></span>分 (已有<i><s:if
								test="#request.planAppraise[0].cnt>0">
								<s:property value="#request.planAppraise[0].cnt" />
							</s:if> <s:else>0</s:else></i>人评价)
					</p>
					<p class="book_message sales">
						销量：<span><s:if test="#request.planAppraise[0].saleNum>0">
								<s:property value="#request.planAppraise[0].saleNum" />
							</s:if> <s:else>0</s:else></span>
					</p>
					<span class="book_message date">选择计划开始日期:</span><input
						class="date_input" name="startDate" id="startDate" type="text"
						onClick="WdatePicker()" readonly />
					<div class="chose buy">
						<a href="javascript:shopPlan();">立 即 购 买</a>
					</div>
					<s:if test="planRelease.unitPrice==0"></s:if>
					<s:else>
						<a href="javascript:saveShopPlan();" style="color: #a4c546">
						<div class="chose">
								<span class="glyphicon glyphicon-shopping-cart"></span>&nbsp;&nbsp;加入购物车
							</div></a>
					</s:else>
				</div>
				<div class="col-md-3 col-lg-3">
					<div class="writer">
						<p>
							作者：<span><a
								href="javascript:goMemberHome('<s:property value="#request.memberAppraise[0].id"/>','<s:property value="#request.memberAppraise[0].role"/>');"><s:property
										value="#request.memberAppraise[0].name" /></a></span>
						</p>
						<p>
							综合评分：<span><s:if
									test='#request.memberAppraise[0].member_grade>0'>
									<s:property value='#request.memberAppraise[0].member_grade' />
								</s:if> <s:else>0</s:else></span>
						</p>
						<p>
							服务数量：<span><s:if
									test='#request.memberAppraise[0].salesNum>0'>
									<s:property value='#request.memberAppraise[0].salesNum' />
								</s:if> <s:else>0</s:else>人次</span>
						</p>
						<h4>更多作品</h4>
						<ul>
							<s:iterator value="#request.otherPlans" status="p1">
								<s:if test="#p1.index<6">
									<li><a href="plan.asp?pid=<s:property value="id"/>"><s:property
												value="planName" /></a></li>
								</s:if>
							</s:iterator>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="book_list col-md-2">
				<h4 class="all_buy">大家都在买</h4>
				<div class="hot_buy">
					<a href="plan.asp?pid=<s:property value="#request.HotPlan[0].id"/>">
						<img
						src="picture/<s:property value="#request.HotPlan[0].image1" />">
					</a>
					<h5>
						<s:property value="#request.HotPlan[0].plan_name" />
					</h5>
					<p>
						￥<span> <s:if
								test="#request.HotPlan[0].unit_price==0||#request.HotPlan[0].unit_price==0">0</s:if>
							<s:else>
								<s:property value="#request.HotPlan[0].unit_price" />
							</s:else>
						</span>
					</p>
				</div>
				<h4 class="all_buy">相关计划</h4>
				<div class="hot_buy">
					<s:iterator value="#request.relativePlans" status="st">
						<s:if test="#st.index<3">
							<a href="plan.asp?pid=<s:property value="id"/>"> <img
								src="picture/<s:property value="image1" />"></a>
							<h5>
								<s:property value="planName" />
							</h5>
							<p>
								￥<span> <s:if test="unitPrice==0||unitPrice==null">0</s:if>
									<s:else>
										<s:property value="unitPrice" />
									</s:else></span>
							</p>
						</s:if>
					</s:iterator>
				</div>
			</div>
			<div class="plan_pandect col-md-10">
				<div class="row">
					<div class="col-md-12">
						<span class="plan">计划详情</span><span class="plan">用户评价</span>
						<div class="line_bg"></div>
					</div>
				</div>
				<div class="plan_nav details">
					<h5>
						计划参数：<span>Planning parameters</span>
					</h5>
					<div class="parameters row">
						<div class="col-md-3 col-md-offset-1">
							<p>
								计划类型：<span><s:if test="planRelease.planType == \"A\"">瘦身减重
				</s:if> <s:if test="planRelease.planType == \"B\"">健美增肌
				</s:if> <s:if test="planRelease.planType==\"C\"">运动康复
				</s:if> <s:if test="planRelease.planType == \"D\"">提高运动表现
				</s:if> <s:if test="planRelease.planType == \"E\"">其它
				</s:if></span>
							</p>
							<p>
								计划周期：<span><s:property value="period" />天</span>
							</p>
						</div>
						<div class="col-md-3 col-md-offset-1">
							<p>
								适用对象：<span><s:if test="planRelease.applyObject== \"A\"">初级
				</s:if> <s:if test="planRelease.applyObject==\"B\"">中级
				</s:if> <s:if test="planRelease.applyObject== \"C\"">高级
				</s:if></span>
							</p>
							<p>
								发布时间：<span><s:date name="planRelease.publishTime"
										format="yyyy年MM月dd日" /></span>
							</p>
						</div>
						<div class="col-md-3 col-md-offset-1">
							<p>
								适用场景：<span><s:property value="planRelease.scene" /></span>
							</p>
							<p>
								所需器材：<span><s:property value="planRelease.apparatuses" /></span>
							</p>
						</div>
					</div>
					<h5>
						计划简介：<span>Plan introduction</span>
					</h5>
					<p class="introduction">
						<s:property value="planRelease.details" escape="false" />
					</p>
				</div>
				<div class="plan_nav rated" style="display: none">
					<h5>总体评价：</h5>
					<font color="#f61313" class="p_fei">
						<s:if	test="#request.planAppraise[0].avgGrade>0">
							<s:property value="#request.planAppraise[0].avgGrade" />
						</s:if> <s:else>0</s:else>分</font> <span  class="pj">(已有	<s:if test="#request.planAppraise[0].cnt>0"><s:property value="#request.planAppraise[0].cnt" />	</s:if><s:else>0</s:else>	人评价)</span>
					
					<ul class="list clearfix">
						<li><a id="GetAll">全部评价<span> (<s:if
										test="#request.planAppraise[0].cnt>0">
										<s:property value="#request.planAppraise[0].cnt" />
									</s:if> <s:else>0 </s:else>)
							</span></a></li>
						<li id="GetNice"><a>好评<span>(<s:if
										test="#request.planAppraise[0].goodNum>0">
										<s:property value="#request.planAppraise[0].goodNum" />
									</s:if> <s:else>0 </s:else>)
							</span></a></li>
						<li id="GetPic"><a>晒图评价<span>(<s:if
										test="#request.planAppraise[0].picNum>0">
										<s:property value="#request.planAppraise[0].picNum" />
									</s:if> <s:else>0 </s:else>)
							</span></a></li>
					</ul>
					<script type="text/javascript">
						$(function() {
							$("#GetAll").click(function() {
								$("#AllComment").show();
								$("#NiceComment").hide();
								$("#PictureComment").hide();
							})

							$("#GetNice").click(function() {
								$("#NiceComment").show();
								$("#AllComment").hide();
								$("#PictureComment").hide();
							})

							$("#GetPic").click(function() {
								$("#PictureComment").show();
								$("#AllComment").hide();
								$("#NiceComment").hide();
							})
						})
					</script>
					<div id="AllComment">
						<ul class="list_nav">
							<s:iterator value="#request.planAppraise">
								<li class=" clearfix">
									<div class="huiyuan_icon">
										<a
											href="javascript:goMemberHome('<s:property value="fromId"/>','<s:property value="fromRole"/>');">
											<img src="picture/<s:property value="fromImage" />"> <span><s:property
													value="fromName" /></span>
										</a>
									</div>
									<div class="rated_nav ">
										<p>
											<s:property value="content" />
										</p>
										<s:if test="image1 != null && image1 != ''">
											<img src="picture/<s:property value='image1'/>" />
										</s:if>
										<s:if test="image2 != null && image2 != ''">
											<img src="picture/<s:property value='image2'/>" />
										</s:if>
										<s:if test="image3 != null && image3 != ''">
											<img src="picture/<s:property value='image3'/>" />
										</s:if>
										<p class="order_time">
											<s:date name="appDate" format="yyyy年MM月dd日 HH:mm:ss" />
										</p>
									</div>
								</li>
							</s:iterator>
						</ul>
					</div>
					<div id="NiceComment" style="display: none">
						<ul class="list_nav">
							<s:iterator value="#request.planAppraise">
								<s:if test="grade>=80">
									<li class=" clearfix">
										<div class="huiyuan_icon">
											<a
												href="javascript:goMemberHome('<s:property value="fromId"/>','<s:property value="fromRole"/>');">
												<img src="picture/<s:property value="fromImage" />"> <span><s:property
														value="fromName" /></span>
											</a>
										</div>
										<div class="rated_nav ">
											<p>
												<s:property value="content" />
											</p>
											<s:if test="image1 != null && image1 != ''">
												<img src="picture/<s:property value='image1'/>" />
											</s:if>
											<s:if test="image2 != null && image2 != ''">
												<img src="picture/<s:property value='image2'/>" />
											</s:if>
											<s:if test="image3 != null && image3 != ''">
												<img src="picture/<s:property value='image3'/>" />
											</s:if>
											<p class="order_time">
												<s:date name="appDate" format="yyyy年MM月dd日 HH:mm:ss" />
											</p>
										</div>
									</li>
								</s:if>
							</s:iterator>
						</ul>
					</div>
					<div id="PictureComment" style="display: none">
						<ul class="list_nav">
							<s:iterator value="#request.planAppraise">
								<s:if
									test="(image1!=null&&image1!='')||(image2!=null&&image2!='')||(image3!=null&&image3!='')">
									<li class=" clearfix">
										<div class="huiyuan_icon">
											<a
												href="javascript:goMemberHome('<s:property value="fromId"/>','<s:property value="fromRole"/>');">
												<img src="picture/<s:property value="fromImage" />"> <span><s:property
														value="fromName" /></span>
											</a>
										</div>
										<div class="rated_nav ">
											<p>
												<s:property value="content" />
											</p>
											<s:if test="image1 != null && image1 != ''">
												<img src="picture/<s:property value='image1'/>" />
											</s:if>
											<s:if test="image2 != null && image2 != ''">
												<img src="picture/<s:property value='image2'/>" />
											</s:if>
											<s:if test="image3 != null && image3 != ''">
												<img src="picture/<s:property value='image3'/>" />
											</s:if>
											<p class="order_time">
												<s:date name="appDate" format="yyyy年MM月dd日 HH:mm:ss" />
											</p>
										</div>
									</li>
								</s:if>
							</s:iterator>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</s:form>
	</div>
	<!--底部区-->
	<s:include value="/newpages/footer.jsp"></s:include>
</body>
</html>