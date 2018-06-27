<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:wb=“http://open.weibo.com/wb”>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>健身E卡通智能健身计划</title>
<s:include value="/share/meta.jsp" />
<link rel="stylesheet" type="text/css" href="css/pulic-1.css" />
<link rel="stylesheet" type="text/css" href="css/product.css" />
<link rel="stylesheet" type="text/css" href="css/product-cardcol.css" />
<link type="text/css" rel="stylesheet"
	href="script/jRating/jRating.jquery.css" />
<script type="text/javascript" src="script/jRating/jRating.jquery.js"></script>
<script language="javascript" src="script/DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	$(function() {
		var aBtn = $('#container_pro .cont_pro').find('li');
		var aDiv = $('#container_pro').find('.tab_container');
		aBtn.click(function() {
			$(this).addClass('tab').siblings().removeClass("tab");
			aDiv.css('display', 'none');
			aDiv.eq($(this).index()).css('display', 'block');
		});
		var aBtn2 = $('#container_pro2 .cont_pro2').find('li');
		var aDiv2 = $('#container_pro2').find('.tab_container2');
		aBtn2.click(function() {
			$(this).addClass('tab').siblings().removeClass("tab");
			aDiv2.css('display', 'none');
			aDiv2.eq($(this).index()).css('display', 'block');
		});
		var aBtn2 = $('#container_pro2 .cont_pro2').find('li');
		var aDiv2 = $('#container_pro2').find('.tab_container2');
		aBtn2.click(function() {
			$(this).addClass('tab').siblings().removeClass("tab");
			aDiv2.css('display', 'none');
			aDiv2.eq($(this).index()).css('display', 'block');
		});
		$("#dialog").dialog({
			autoOpen : false,
			show : "blind",
			hide : "explode",
			resizable : false
		});
		$("#dialogMsg").dialog({
			autoOpen : false,
			show : "blind",
			hide : "explode",
			resizable : false,
		});
		$(".avgCount").jRating({
			type : 'small',
			isDisabled : true,
			length : 5,
			decimalLength : 1,
			rateMax : 100
		});
	})

	function saveGoodsCardcol() {
		if (!"<s:property value="#session.loginMember.id"/>") {
			openLogin();
			return;
		}
		if (validateNotNull()) {
			$.ajax({
				url : 'shop!saveShopGoods.asp',
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
	}

	function goodsCardcol() {
		if (!"<s:property value="#session.loginMember.id"/>") {
			openLogin();
			return;
		}
		if (validateNotNull()) {
			url = 'order!submitProd.asp?type=1&prodType=6&id='
					+ $('#goodsId').val();
			$('#queryForm').attr("action", url);
			$('#queryForm').submit();
		}
	}

	function validateNotNull() {
		var notNull = true;
		if ($('input[name="setting.target"]:checked').length <= 0) {
			notNull = false;
		} else if ($('input[name="setting.currGymStatus"]:checked').length <= 0) {
			notNull = false;
		} else if ($('input[name="setting.strengthDate"]:checked').length <= 0) {
			notNull = false;
		} else if ($('input[name="setting.strengthDuration"]:checked').length <= 0) {
			notNull = false;
		} else if ($('input[name="setting.cardioDate"]:checked').length <= 0) {
			notNull = false;
		} else if ($('input[name="setting.cardioDuration"]:checked').length <= 0) {
			notNull = false;
		} else if ($('input[name="setting.favoriateCardio"]:checked').length <= 0) {
			notNull = false;
		}
		if (notNull === false) {
			alert('请到下面的计划详情中填写完整的身体数据!');
			return false;
		}
		if ($('#startDate').val() === '' || $('#startDate').val() === null) {
			alert('请到下面的计划详情中填写健身开始的日期!');
			return false;
		}
		return true;
	}

	function goMemberHome(memberId, role) {
		var url = "";
		if (role == "M") {
			url = "member.asp";
		}
		if (role == "E") {
			url = "club.asp";
		}
		if (role == "S") {
			url = "coach.asp";
		}
		//修改为openwindow
		url += "?member.id=" + memberId;
		window.open(url, "homeWindow" + memberId);
	}
	function preShop(planId, memberId) {
		window.location.href = "plan.asp?pid=" + planId;
	}
	function changeTips() {
		var currGymStatus = $('input[name="setting.currGymStatus"]:checked')
				.val();
		var target = $('input[name="setting.target"]:checked').val();
		if (currGymStatus == 'A') {
			if ($('input[name="setting.strengthDate"]:checked').length == 7) {
				$('#strenghTips').html('连续7天运动不利于身体健康，一周请至少安排1天休息。');
			} else {
				$('#strenghTips').html("根据您的个人情况，建议您每周进行2-3次力量训练。");
			}
			$('#cardioTips').html("根据您的个人情况，建议您每周进行5-6天有氧训练。");
		} else if (currGymStatus == 'B') {
			if ($('input[name="setting.strengthDate"]:checked').length == 7) {
				$('#strenghTips').html('连续7天运动不利于身体健康，一周请至少安排1天休息。');
			} else {
				$('#strenghTips').html("根据您的个人情况，建议您每周进行2-3次力量训练。");
			}
			if (target == '1') {
				$('#cardioTips').html("根据您的个人情况，建议您每周进行5-6天有氧训练。");
			} else if (target == '4') {
				$('#cardioTips').html("根据您的个人情况，建议您每周进行4天有氧训练。");
			} else {
				$('#cardioTips').html("根据您的个人情况，建议您每周进行3天有氧训练。");
			}
		} else if (currGymStatus == 'C') {
			if ($('input[name="setting.strengthDate"]:checked').length == 7) {
				$('#strenghTips').html('连续7天运动不利于身体健康，一周请至少安排1天休息。');
			} else {
				$('#strenghTips').html("根据您的个人情况，建议您每周进行3-4次力量训练。");
			}
			if (target == '1') {
				$('#cardioTips').html("根据您的个人情况，建议您每周进行5-6天有氧训练。");
			} else {
				$('#cardioTips').html("根据您的个人情况，建议您每周进行3天有氧训练。");
			}
		} else {
			if ($('input[name="setting.strengthDate"]:checked').length == 7) {
				$('#strenghTips').html('连续7天运动不利于身体健康，一周请至少安排1天休息。');
			} else {
				$('#strenghTips').html("根据您的个人情况，建议您每周进行4-6次力量训练。");
			}
			$('#cardioTips').html("根据您的个人情况，建议您每周进行5天有氧训练。");
		}
	}

	function validateSelectAll() {
		if ($('input[name="setting.strengthDate"]:checked').length == 7) {
			$('#strenghTips').html('连续7天运动不利于身体健康，一周请至少安排1天休息。');
		} else {
			if ($('#strenghTips').html().indexOf('连续7天运动不利于身体健康') > -1) {
				var currGymStatus = $(
						'input[name="setting.currGymStatus"]:checked').val();
				if (currGymStatus == 'C') {
					$('#strenghTips').html('根据您的个人情况，建议您每周进行3-4次力量训练。');
				} else if (currGymStatus == 'D') {
					$('#strenghTips').html('根据您的个人情况，建议您每周进行4-6次力量训练。');
				} else {
					$('#strenghTips').html('根据您的个人情况，建议您每周进行2-3次力量训练。');
				}
			}
		}
	}
</script>
<style type="text/css">
div {
	margin: 0 auto;
	padding: 0;
}

h1,h2,h3,h4,h5,h6,ul,ol,li,dl,dt,dd,form,img,p {
	margin: 0;
	padding: 0;
	border: none;
	list-style-type: none;
}

.foucs {
	width: 270px;
	height: 380px;
	position: relative;
}

#focuscont {
	width: 270px;
	height: 324px;
	position: absolute;
	left: 0;
	top: 0;
}

#focustab {
	width: 275px;
	height: 80px;
	position: absolute;
	right: 0;
	top: 280px;
}

#focustab img {
	height: 80px;
	width: 80px;
}

#focuscont .text {
	width: 232px;
	position: absolute;
	top: 325px;
	left: 0px;
	color: #000;
	font-weight: bold;
	font-size: 14px;
	padding: 0 10px;
	text-align: left;
}

.focuscont p img {
	width: 270px;
	height: 270px;
}

.filt {
	position: absolute;
	top: 325px;
	left: 0px;
	height: 20px;
	width: 232px;
}

#focustab li {
	width: 80px;
	height: 80px;
	float: left;
	margin-left: 5px;
}

#focustab .foncuimg {
	margin-right: 10px;
}

#focustab li a {
	display: block;
}

#focustab li a.act {
	background: url() no-repeat;
	z-index: 99;
}

#focustab li a.act img {
	border: none;
}
</style>

<script type="text/javascript">
	function initAutoFocus() {
		autoShiftFocus("focustab", "a", "focuscont", "div");
		function autoShiftFocus(tabsid, tabstagname, contentid, contenttagname) {
			var autotpc = setInterval(autoSlideTopic, 3000);
			var tabs = document.getElementById(tabsid).getElementsByTagName(
					tabstagname);
			var contents = document.getElementById(contentid)
					.getElementsByTagName(contenttagname);
			var cur_index_num = 0;
			if (tabstagname == "a") {
				for (var a = tabs.length - 1; a >= 0; a--) {
					tabs[a].onclick = function() {
						return false;
					}
				}
			}
			function getContentsArr() {
				var contentsarr = new Array();
				for (var z = 0; z < contents.length; z++) {
					if (contents[z].id.indexOf(contentid) >= 0) {
						contentsarr[contentsarr.length] = contents[z];
					}
				}
				return contentsarr;
			}
			var contarr = getContentsArr();
			function autoSlideTopic() {
				for (var a = tabs.length - 1; a >= 0; a--) {
					tabs[a].className = "";
				}
				for (var b = contarr.length - 1; b >= 0; b--) {
					contarr[b].style.display = "none";
				}
				contarr[cur_index_num].style.display = "block";
				tabs[cur_index_num].className = "act";
				var total_num = tabs.length;
				cur_index_num++;
				if (cur_index_num >= total_num) {
					cur_index_num = 0;
				}
			}
			for (var c = tabs.length - 1; c >= 0; c--) {
				tabs[c].onmouseover = function() {
					clearInterval(autotpc);
					changeTabs(this.name);
				}
				tabs[c].onmouseout = function() {
					autotpc = setInterval(autoSlideTopic, 3000);
				}
			}
			function changeTabs(num) {
				var thenum = num - 1;
				for (var n = tabs.length - 1; n >= 0; n--) {
					tabs[n].className = "";
				}
				tabs[thenum].className = "act";
				for (var m = contarr.length - 1; m >= 0; m--) {
					contarr[m].style.display = "none";
				}
				contarr[thenum].style.display = "block";
				cur_index_num = thenum;
			}
		}
	}
</script>




</head>
<body>
	<div id="top">
		<div id="top-content">
			<p class="left">
				<s:if test="#session.loginMember !=null">
					<a href="account.asp"><s:property
							value="#session.loginMember.name" /></a> 欢迎来到健身E卡通！<a
						href="login!logout.asp" id="coloa" target="_parent">退出</a>
				</s:if>
				<s:else>
        		 欢迎来到健身E卡通！<a id="coloa" href="login.asp" target="_parent">请登录</a>
					<span>|</span>
					<a href="register.asp" id="coloa" target="_parent">注册</a>
				</s:else>
			</p>
			<p class="right">
				<a href="index.asp" id="coloa" target="_parent">健身E卡通首页</a> <span>|</span><a
					href="login.asp" id="coloa" target="_parent">我的健身E卡通</a> <span>|</span><a
					href="message.asp" id="coloa" target="_parent">消息(<b
					class="msgb">0</b>)
				</a> <span>|</span><a href="shop!queryShopping.asp" id="coloa"
					target="_parent"><b class="aimgb">购物车</b><b class="ainsp">0</b>件</a>
				<span>|</span><a href="service.asp" id="coloa" target="_parent">服务中心</a>
				<span>|</span><a href="index.asp" id="coloa" target="_parent">网站导航</a>
			</p>
		</div>
	</div>

	<div id="header">
		<div id="logo">
			<blockquote>
				<p>
					<a href="index.asp" target="_parent"><img src="images/logo.png"
						alt="健身E卡通网" /></a>
				</p>
			</blockquote>
		</div>
		<div id="switch">
			<span class="city"><s:property value="#session.currentCity" /></span><a
				href="city.asp" id="coloa">[切换城市]</a>
		</div>
		<div id="ad">
			<img src="images/ad.png" />
		</div>
	</div>

	<div id="nav">
		<div id="nav_first">
			<a>健身E卡通服务</a>
		</div>
		<ul id="navitems">
			<li><a href="index.asp"
				<s:if test="#session.position == 0"> class="nav_a" </s:if>>首页</a></li>
			<li><a href="planlist.asp"
				<s:if test="#session.position == 4"> class="nav_a" </s:if>>健身计划</a></li>
			<li><a href="clublist.asp"
				<s:if test="#session.position == 2"> class="nav_a" </s:if>>健身场馆</a></li>
			<li><a href="coachlist.asp"
				<s:if test="#session.position == 3"> class="nav_a" </s:if>>健身教练</a></li>
			<li><a href="activelist.asp"
				<s:if test="#session.position == 1"> class="nav_a" </s:if>>健身挑战</a></li>
			<li><a href="map.asp"
				<s:if test="#session.position == 5"> class="hover"</s:if>>健身地图</a></li>
		</ul>
	</div>

	<div id="divcontent">
		<div class="navdaohang">
			<s:action name="clublist!initLeftTopList" executeResult="true" />
		</div>
		<s:form id="queryForm" name="queryForm" method="post"
			action="goods.asp" theme="simple">
			<s:hidden name="goods.id" id="goodsId" />

			<div class="dengl_top">
				<div class="product_header">
					<div class="header_title">
						<a href="index.asp"> 网站首页 </a> > <a href="planlist.asp">健身计划 </a>>
						<s:property value="goods.name" />
					</div>
					<div class="header_content">
						<div class="header_pic">
							<div class="foucs">
								<div class="f_l hbtpc">
									<div id="focuscont">
										<p class="filt"></p>
										<div class="focuscont" id="focuscont1">
											<p>
												<a href="#"><img
													src="picture/<s:property value="goods.image1"/>" alt="图片1"></a>
											</p>

										</div>
										<div class="focuscont" id="focuscont2" style="display: none">
											<p>
												<a href="#"><img
													src="picture/<s:property value="goods.image2"/>" alt="图片2"></a>
											</p>

										</div>
										<div class="focuscont" id="focuscont3" style="display: none">
											<p>
												<a href="#"><img
													src="picture/<s:property value="goods.image3"/>" alt="图片3"></a>
											</p>
										</div>

									</div>
									<ul id="focustab" class="focustab">
										<li class="foncuimg"><a class="act" href="#" name="1"><img
												src="picture/<s:property value="goods.image1"/>"></a></li>
										<li class="foncuimg"><a href="#" name="2"><img
												src="picture/<s:property value="goods.image2"/>"></a></li>
										<li><a href="#" name="3"><img
												src="picture/<s:property value="goods.image3"/>"></a></li>

									</ul>
								</div>
							</div>
							<script>
								initAutoFocus();
							</script>

						</div>
						<div class="header_cont">
							<h1>
								<s:property value="goods.name" />
							</h1>
							<div class="header_info">
								<s:property value="goods.summary" />
							</div>
							<div class="header_jiage">
								<ul>
									<li class="sprice"><div class="dt">价格:</div>
										<div class="dd">
											<strong><s:property value="goods.price" /></strong>
										</div></li>
									<li><div class="dt">评分:</div>
										<div class="dd">
											<s:if test='#request.goodsAppraise[0].avgGrade>0'><s:property value='#request.goodsAppraise[0].avgGrade' /></s:if><s:else>0</s:else>分
											(已有
											<s:if test='#request.goodsAppraise[0].cnt>0'>
												<s:property value='#request.goodsAppraise[0].cnt' />
											</s:if>
											<s:else>0</s:else>
											人评价)
										</div></li>
									<li><div class="dt">销量:</div>
										<div class="dd">
											<s:if test='#request.goodsAppraise[0].saleNum>0'>
												<s:property value='#request.goodsAppraise[0].saleNum' />
											</s:if>
											<s:else>0</s:else>
										</div></li>
								</ul>
							</div>
							<div class="header_jiage">
								<p>请在商品详情中填写您的身体数据，然后点击“立即购买”。</p>
							</div>
							<div class="shopping">
								<a href="javascript:goodsCardcol();"><img
									src="images/product_07.gif" /></a> <a
									href="javascript:saveGoodsCardcol();"><img
									src="images/product_09.gif" /></a>
							</div>
						</div>
						<div class="header_Author">
							<ul>
								<li class="Author"><div class="author1">作&nbsp;&nbsp;者:</div>
									<div class="author2">
										<a
											href="javascript:goMemberHome('<s:property value="#request.memberAppraise[0].id"/>','<s:property value="#request.memberAppraise[0].role"/>');"><b><s:property
												value="#request.memberAppraise[0].name" /></b></a>
									</div></li>
								<li class="Author"><div class="author1">综合评分:</div>
									<div class="Authoravg">
										<div class="avgCount"
											id="<s:if test='#request.memberAppraise[0].member_grade>0'><s:property value='#request.memberAppraise[0].member_grade' /></s:if><s:else>0</s:else>_appraise.member_grade"></div>
										<div class="zonhe">
											<font color="#f61313" class="p_fei"><s:if
													test='#request.memberAppraise[0].member_grade>0'>
													<s:property value='#request.memberAppraise[0].member_grade' />
												</s:if> <s:else>0</s:else></font>
										</div>
									</div></li>
								<li class="Author"><div class="author1">服务数量:</div>
									<div>
										<s:if test='#request.memberAppraise[0].salesNum>0'>
											<s:property value='#request.memberAppraise[0].salesNum' />
										</s:if>
										<s:else>0</s:else>
										人次
									</div></li>
								<div class="header_phone">
									<span class="autoe">作者简介</span>
									<div class="introdb">
										<%-- 										<s:property value="#request.memberAppraise[0].description" --%>
										<%-- 											escape="false" /> --%>
										健身E卡通智能健身计划引擎是中国健身行业知名门户网站——健身中国网www.jszg.net的研发团队历经多年研究的成果。本产品集各种健身健美运动理论成果之大成，能满足不同类型的健身爱好者多种健身目标的需求。可以有效达到瘦身减重、健美增肌、运动康复、提高运动表现的效果。
									</div>
								</div>
							</ul>
						</div>
					</div>
				</div>
				<div class="product_side">
					<h2>相关计划</h2>
					<ul>
						<s:iterator value="#request.planRecommends1" status="start">
							<s:if test="#start.index<=4">
								<li class="fore1">
									<div class="p-img">
										<a
											href="<s:if test="link != null && link!=\"\""><s:property value="link"/></s:if><s:else>plan.asp?pid=<s:property value="rId"/></s:else>"><img
											src="<s:if test="icon == null || icon == \"\"">images/tu4.jpg</s:if><s:else>picture/<s:property value="icon"/></s:else>" /></a>
									</div>
									<div class="p-name">
										<a href=""><s:property value='summary' /></a>
									</div> <!-- 							<div class="p-pric"> --> <%-- 								<strong>588.00</strong> --%>
									<!-- 							</div> -->
								</li>
							</s:if>
						</s:iterator>
					</ul>
				</div>
				<div class="product_main">
					<div id="container_pro">
						<ul class="cont_pro">
							<li class="tab" style="border-left: 1px solid #e7e7e7;"><span>计划详情</span></li>
							<li><span>用户评价</span></li>
						</ul>

						<div id="news" class="tab_container"
							style="display: block; margin-top: 0px;">
							<div
								style="border-left: 1px solid #dcdcdc; border-right: 1px solid #dcdcdc; border-bottom: 1px solid #dcdcdc; overflow: hidden; zoom: 1;">
								<div>
									<li>计划类型: <s:property value="goods.planType" />
									</li>
									<li>适用对象： <s:property value="goods.applyObject" />
									</li>
									<li>发布时间:<s:property value="goods.publishTime" /></li>
									<li>计划周期:<s:property value="goods.plancircle" />
									</li>
									<li>适用场景:<s:property value="goods.scene" /></li>
									<li>所需器材:<s:property value="goods.apparatuses" /></li>
								</div>
							</div>
							<div class="" id="infoDiv"
								style="margin-top: 8px; overflow: hidden; zoom: 1;">
								<div class="product_conment">
									<p class="procard">请根据您的实际情况填写以下项目，然后点击“立即购买”并完成支付。就可以获得一份健身E卡通智能健身计划引擎为您制订的3个月私人健身计划：
									</p>
									<h2>一、您的健身目标</h2>
									<div class="product_pci">
										<ul style="padding-left: 50px;">
											<li class="pickdd"><img src="images/weight.jpg"
												width="146" height="138" /> <input type="radio"
												name="setting.target" value="1"
												<s:if test="setting.target==1">checked="checked"</s:if>
												onclick="changeTips()" /> 减脂塑形</li>
											<li class="pickdd"><img src="images/Muscle.jpg"
												width="146" height="138" /> <input type="radio"
												name="setting.target" value="2"
												<s:if test="setting.target==2">checked="checked"</s:if>
												onclick="changeTips()" /> 健美增肌</li>
											<li><img src="images/fitness.jpg" width="146"
												height="138" /> <input type="radio" name="setting.target"
												value="3"
												<s:if test="setting.target==3">checked="checked"</s:if>
												onclick="changeTips()" /> 促进健康</li>
											<li><img src="images/sport.jpg" width="146" height="138" />
												<input type="radio" name="setting.target" value="4"
												<s:if test="setting.target==4">checked="checked"</s:if>
												onclick="changeTips()" />提高运动表现</li>
										</ul>
									</div>
									<h2>二、您的健身历史</h2>
									<div class="product_lishi">
										<s:radio
											list="#{'A':'&nbsp;从来没有进行健身运动','B':'&nbsp;参加健身运动少于6个月','C':'&nbsp;参加健身运动少于12个月','D':'&nbsp;已经很规律地进行了12个月以上的健身'}"
											name="setting.currGymStatus" onclick="changeTips()"></s:radio>
									</div>
									<h2>三、您的健身频率</h2>
									<div class="binlu">
										<div class="quetion border_que">
											<div class="questjin">
												<div class="quelist">
													1.每星期哪几天进行力量训练？ <span id="strenghTips"><s:if
															test="setting != null">
															<s:if test="(#request.strengthDates.size()==7)">连续7天运动不利于身体健康，一周请至少安排1天休息。</s:if>
															<s:else>
																<s:if test="setting.currGymStatus == \"C\"">根据您的个人情况，建议您每周进行3-4次力量训练。</s:if>
																<s:elseif test="setting.currGymStatus == \"D\"">根据您的个人情况，建议您每周进行4-6次力量训练。</s:elseif>
																<s:else>根据您的个人情况，建议您每周进行2-3次力量训练。</s:else>
															</s:else>
														</s:if></span>
												</div>
											</div>
											<div class="quexuan">
												<s:checkboxlist
													list="#{'1':'星期一','2':'星期二','3':'星期三','4':'星期四','5':'星期五','6':'星期六','7':'星期天' }"
													name="setting.strengthDate" value="#request.strengthDates"
													onclick="validateSelectAll()" />
											</div>
											<div class="questjin">
												<div class="quelist">2.每次力量训练用多长时间？</div>
											</div>
											<div class="quexuan">
												<s:radio
													list="#{'30':'&nbsp;30分钟','45':'&nbsp;45分钟','60':'&nbsp;60分钟','90':'&nbsp;90分钟','120':'&nbsp;120分钟'}"
													name="setting.strengthDuration"></s:radio>
											</div>
											<div class="questjin">
												<div class="quelist">
													3.每星期哪几天进行有氧训练？ <span id="cardioTips"><s:if
															test="setting != null">
															<s:if test="setting.target == \"1\"">根据您的个人情况，建议您每周进行5-6天有氧训练。</s:if>
															<s:elseif test="setting.target == \"2\"">
																<s:if test="setting.currGymStatus == \"A\"">根据您的个人情况，建议您每周进行5-6天有氧训练。</s:if>
																<s:elseif test="setting.currGymStatus == \"D\"">根据您的个人情况，建议您每周进行4天有氧训练。</s:elseif>
																<s:else>根据您的个人情况，建议您每周进行3天有氧训练。</s:else>
															</s:elseif>
															<s:elseif test="setting.target == \"3\"">
																<s:if test="setting.currGymStatus == \"A\"">根据您的个人情况，建议您每周进行5-6天有氧训练。</s:if>
																<s:else>根据您的个人情况，建议您每周进行3天有氧训练。</s:else>
															</s:elseif>
															<s:else>根据您的个人情况，建议您每周进行5天有氧训练。</s:else>
														</s:if></span>
												</div>
											</div>
											<div class="quexuan">
												<s:checkboxlist
													list="#{'1':'星期一','2':'星期二','3':'星期三','4':'星期四','5':'星期五','6':'星期六','7':'星期天' }"
													name="setting.cardioDate" value="#request.cardioDates" />
											</div>
											<div class="questjin">
												<div class="quelist">4.每次有氧训练用多长时间？</div>
											</div>
											<div class="quexuan">
												<s:radio
													list="#{'30':'&nbsp;30分钟','45':'&nbsp;45分钟','60':'&nbsp;60分钟','90':'&nbsp;90分钟','120':'&nbsp;120分钟'}"
													name="setting.cardioDuration"></s:radio>
											</div>
										</div>
									</div>
									<h2>四、您喜欢的有氧训练项目</h2>
									<div class="quest">
										<div class="quetion">
											<div id="youxi2">
												<ul>
													<s:iterator value="#request.actions" status="st" var="as">
														<li><img src="<s:property value="image"/>" width="72"
															height="67" /> <span><input type="checkbox"
																id="cardios<s:property value="#st.index"/>"
																name="setting.favoriateCardio"
																value='<s:property value="name"/>'
																<s:if test="setting.favoriateCardio.indexOf(#as.name)>=0">checked="checked"</s:if>></input>
																<label for="cardios<s:property value="#st.index"/>"><s:property
																		value="name" /></label> </span></li>
													</s:iterator>
												</ul>
											</div>
										</div>
									</div>
									<h2>
										五、健身训练开始日期 <input type="text" name="startDate" id="startDate"
											value="" readonly="readonly" style="width: 100px;" /> <img
											id="img_repeatStart" onclick="WdatePicker({el:'startDate'})"
											src="script/DatePicker/skin/datePicker.gif" align="absmiddle"
											width="16" height="22" style="cursor: pointer;">
									</h2>
								</div>
							</div>

						</div>
						<div id="news" class="tab_container">
							<div
								style="border-left: 1px solid #dcdcdc; border-right: 1px solid #dcdcdc; border-bottom: 1px solid #dcdcdc; overflow: hidden; zoom: 1;">
								<div class="container_user">
									<span>总体评价:</span>
									<div class="user_xp" />
									<div class="avgCount"
										id="<s:if test='#request.goodsAppraise[0].avgGrade>0'><s:property value='#request.goodsAppraise[0].avgGrade' /></s:if><s:else>0</s:else>_appraise.avgGrade"></div>
								</div>
								<strong><s:if
										test='#request.goodsAppraise[0].avgGrade>0'>
										<s:property value='#request.goodsAppraise[0].avgGrade' />
									</s:if> <s:else>0</s:else>分</strong>(已有
								<s:if test='#request.goodsAppraise[0].cnt>0'>
									<s:property value='#request.goodsAppraise[0].cnt' />
								</s:if>
								<s:else>0</s:else>
								人评价) <span class="great">好评(<font color="#9c9a9c"><s:if
											test='#request.goodsAppraise[0].goodProp!=null'>
											<s:property value='#request.goodsAppraise[0].goodProp' />
										</s:if> <s:else>0</s:else></font>)</span> <span
									class="great">中评 (<font color="#9c9a9c"><s:if
											test='#request.goodsAppraise[0].generalProp!=null'>
											<s:property value='#request.goodsAppraise[0].generalProp' />
										</s:if> <s:else>0</s:else></font>)</span>
								<span class="great">差评 (<font color="#9c9a9c"><s:if
											test='#request.goodsAppraise[0].badProp!=null'>
											<s:property value='#request.goodsAppraise[0].badProp' />
										</s:if> <s:else>0</s:else></font>)</span>
							</div>

							<div id="container_pro2">
								<ul class="cont_pro2">
									<li class="tab" style="border-left: 1px solid #e7e7e7;"><span>全部评价(<s:if
												test='#request.goodsAppraise[0].cnt>0'>
												<s:property value='#request.goodsAppraise[0].cnt' />
											</s:if> <s:else>0</s:else>)
									</span></li>
									<li><span>好评(<s:if
												test='#request.goodsAppraise[0].goodNum>0'>
												<s:property value='#request.goodsAppraise[0].goodNum' />
											</s:if> <s:else>0</s:else>)
									</span></li>
									<li><span>中评 (<s:if
												test='#request.goodsAppraise[0].generalNum>0'>
												<s:property value='#request.goodsAppraise[0].generalNum' />
											</s:if> <s:else>0</s:else>)
									</span></li>
									<li><span>差评 (<s:if
												test='#request.goodsAppraise[0].badNum>0'>
												<s:property value='#request.goodsAppraise[0].badNum' />
											</s:if> <s:else>0</s:else>)
									</span></li>
									<li class="tabli"><span>有晒单的评价(<s:if
												test='#request.goodsAppraise[0].picNum>0'>
												<s:property value='#request.goodsAppraise[0].picNum' />
											</s:if> <s:else>0</s:else>)
									</span></li>
								</ul>
								<div class="tab_container2" style="margin-top: 10px;">
									<s:iterator value="#request.goodsAppraise">
										<div class="user_spring">
											<div class="user_name">
												<a
													href="javascript:goMemberHome('<s:property value="fromId"/>','<s:property value="fromRole"/>');"><img
													src="picture/<s:property value="fromImage" />" style="width:60px;height:60px;" /><span><s:property
															value="fromName" /></span></a>
											</div>
											<div class="user_text">
												<div class="user_xpdate">
													<div class="user_title">
														<div class="avgCount"
															id="<s:property value="grade" />_appraise.avgCount"></div>
													</div>
								                    <font style="margin-left:10px" color="#f61313">
								                          <s:property value="grade"/>
								                    </font>
													<div class="date">
														<s:date name="appDate" format="yyyy-MM-dd HH:mm:ss" />
													</div>
												</div>
												<div class="user_wenzi">
													<p>
														<s:property value="content" />
													</p>
												</div>
												<s:if test="image1!=null&&image1!=''">
													<span style="margin-left:540px;">上传的图片:</span>
													<div style="margin-left:620px;margin-top:-20px;">
														<s:if test="image1 != null && image1 != ''">
															<img style="border:1px solid #dcdcdc" width="60px" height="60px"
																src="picture/<s:property value='image1'/>" />
														</s:if>
														<s:if test="image2 != null && image2 != ''">
															<img style="border:1px solid #dcdcdc" width="60px" height="60px"
																src="picture/<s:property value='image2'/>" />
														</s:if>
														<s:if test="image3 != null && image3 != ''">
															<img style="border:1px solid #dcdcdc" width="60px" height="60px"
																src="picture/<s:property value='image3'/>" />
														</s:if>
													</div>
												</s:if>
											</div>
										</div>
									</s:iterator>
								</div>
								<div class="tab_container2"
									style="display: none; margin-top: 10px;">
									<s:iterator value="#request.goodsAppraise">
										<s:if test="grade>=80">

											<div class="user_spring">
												<div class="user_name">
													<a
														href="javascript:goMemberHome('<s:property value="fromId"/>','<s:property value="fromRole"/>');"><img
														src="picture/<s:property value="fromImage" />" style="width:60px;height:60px;" /><span><s:property
																value="fromName" /></span></a>
												</div>
												<div class="user_text">
													<div class="user_xpdate">
														<div class="user_title">
															<div class="avgCount"
																id="<s:property value="grade" />_appraise.avgCount"></div>
														</div>
												<font style="margin-left:10px" color="#f61313">
								                          <s:property value="grade"/>
								                    </font>
														<div class="date">
															<s:date name="appDate" format="yyyy-MM-dd HH:mm:ss" />
														</div>
													</div>
													<div class="user_wenzi">
														<p>
															<s:property value="content" />
														</p>
													</div>
													<div style="margin-left:620px;margin-top:-20px;">
														<s:if test="image1 != null && image1 != ''">
															<img width="60px" height="60px"
																src="picture/<s:property value='image1'/>" />
														</s:if>
														<s:if test="image2 != null && image2 != ''">
															<img width="60px" height="60px"
																src="picture/<s:property value='image2'/>" />
														</s:if>
														<s:if test="image3 != null && image3 != ''">
															<img width="60px" height="60px"
																src="picture/<s:property value='image3'/>" />
														</s:if>
													</div>
												</div>
											</div>
										</s:if>
									</s:iterator>
								</div>
								<div class="tab_container2"
									style="display: none; margin-top: 10px;">
									<s:iterator value="#request.goodsAppraise">
										<s:if test="grade>=60 && grade<80">
											<div class="user_spring">
												<div class="user_name">
													<a
														href="javascript:goMemberHome('<s:property value="fromId"/>','<s:property value="fromRole"/>');"><img
														src="picture/<s:property value="fromImage" />"  style="width:60px;height:60px;"/><span><s:property
																value="fromName" /></span></a>
												</div>
												<div class="user_text">
													<div class="user_xpdate">
														<div class="user_title">
															<div class="avgCount"
																id="<s:property value="grade" />_appraise.avgCount"></div>
														</div>
												 <font style="margin-left:10px" color="#f61313">
								                          <s:property value="grade"/>
								                    </font>
														<div class="date">
															<s:date name="appDate" format="yyyy-MM-dd HH:mm:ss" />
														</div>
													</div>
													<div class="user_wenzi">
														<p>
															<s:property value="content" />
														</p>
													</div>
													<div style="margin-left:620px;margin-top:-20px;">
														<s:if test="image1 != null && image1 != ''">
															<img width="60px" height="60px"
																src="picture/<s:property value='image1'/>" />
														</s:if>
														<s:if test="image2 != null && image2 != ''">
															<img width="60px" height="60px"
																src="picture/<s:property value='image2'/>" />
														</s:if>
														<s:if test="image3 != null && image3 != ''">
															<img width="60px" height="60px"
																src="picture/<s:property value='image3'/>" />
														</s:if>
													</div>
												</div>
											</div>
										</s:if>
									</s:iterator>
								</div>
								<div class="tab_container2"
									style="display: none; margin-top: 10px;">
									<s:iterator value="#request.goodsAppraise">
										<s:if test="grade<60">
											<div class="user_spring">
												<div class="user_name">
													<a
														href="javascript:goMemberHome('<s:property value="fromId"/>','<s:property value="fromRole"/>');"><img
														src="picture/<s:property value="fromImage" />"  style="width:60px;height:60px;"/><span><s:property
																value="fromName" /></span></a>
												</div>
												<div class="user_text">
													<div class="user_xpdate">
														<div class="user_title">
															<div class="avgCount"
																id="<s:property value="grade" />_appraise.avgCount"></div>
														</div>
													<font style="margin-left:10px" color="#f61313">
								                          <s:property value="grade"/>
								                    </font>
														<div class="date">
															<s:date name="appDate" format="yyyy-MM-dd HH:mm:ss" />
														</div>
													</div>
													<div class="user_wenzi">
														<p>
															<s:property value="content" />
														</p>
													</div>
													<div style="margin-left:620px;margin-top:-20px;">
														<s:if test="image1 != null && image1 != ''">
															<img width="60px" height="60px"
																src="picture/<s:property value='image1'/>" />
														</s:if>
														<s:if test="image2 != null && image2 != ''">
															<img width="60px" height="60px"
																src="picture/<s:property value='image2'/>" />
														</s:if>
														<s:if test="image3 != null && image3 != ''">
															<img width="60px" height="60px"
																src="picture/<s:property value='image3'/>" />
														</s:if>
													</div>
												</div>
											</div>
										</s:if>
									</s:iterator>
								</div>
								<div class="tab_container2"
									style="display: none; margin-top: 10px;">
									<s:iterator value="#request.goodsAppraise">
										<s:if test="image1!=null && image1!=''">
											<div class="user_spring">
												<div class="user_name">
													<a
														href="javascript:goMemberHome('<s:property value="fromId"/>','<s:property value="fromRole"/>');"><img
														src="picture/<s:property value="fromImage" />"  style="width:60px;height:60px;"/><span><s:property
																value="fromName" /></span></a>
												</div>
												<div class="user_text">
													<div class="user_xpdate">
														<div class="user_title">
															<div class="avgCount"
																id="<s:property value="grade" />_appraise.avgCount"></div>
														</div>
													<font style="margin-left:10px" color="#f61313">
								                          <s:property value="grade"/>
								                    </font>
														<div class="date">
															<s:date name="appDate" format="yyyy-MM-dd HH:mm:ss" />
														</div>
													</div>
													<div class="user_wenzi">
														<p>
															<s:property value="content" />
														</p>
													</div>
													<div style="margin-left:620px;margin-top:-20px;">
														<s:if test="image1 != null && image1 != ''">
															<img width="60px" height="60px"
																src="picture/<s:property value='image1'/>" />
														</s:if>
														<s:if test="image2 != null && image2 != ''">
															<img width="60px" height="60px"
																src="picture/<s:property value='image2'/>" />
														</s:if>
														<s:if test="image3 != null && image3 != ''">
															<img width="60px" height="60px"
																src="picture/<s:property value='image3'/>" />
														</s:if>
													</div>
												</div>

											</div>
										</s:if>
									</s:iterator>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
	</div>
	</div>
	</s:form>
	<s:include value="/share/footer.jsp" />
	</div>
</body>
</html>