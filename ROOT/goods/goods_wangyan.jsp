<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:wb=“http://open.weibo.com/wb”>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>王严智能健身计划</title>
<s:include value="/share/meta.jsp" />
<link rel="stylesheet" type="text/css" href="css/product.css" />
<link rel="stylesheet" type="text/css" href="css/product-cardcol.css" />
<link type="text/css" rel="stylesheet"
	href="script/jRating/jRating.jquery.css" />
<!--2017-01-24 -->
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
<link type="text/css" href="css/bootstrap.css" rel="stylesheet"  />
<link type="text/css" href="css/base.css" rel="stylesheet">
<link type="text/css" href="css/planXq.css" rel="stylesheet">
<link type="text/css" href="js/datepicker/skin/WdatePicker.css"	rel="stylesheet" />
<%-- <script type="text/javascript" src="js/datepicker/WdatePicker.js"></script> --%>
<link type="text/css" rel="stylesheet"  	href="script/jRating/jRating.jquery.css" />
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
						alert("您的计划已经成功生成，请进入用户中心在我的健身E卡通健身计划中查看计划！");
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

<!--end-->
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

	});

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
			url = 'order!submitProd.asp?type=2&prodType=6&id='
					+ $('#goodsId').val();
			$('#queryForm').attr("action", url);
			$('#queryForm').submit();
		}
	}

	function validateNotNull() {
		var notNull = true;
		if ($('input[name="setting.target"]:checked').length <= 0) {
			notNull = false;
		} else if ($('input[name="setting.favoriateCardio"]:checked').length <= 0) {
			notNull = false;
		} else {
			$('#infoDiv').find('input:text[id!="startDate"]').each(function() {
				if ($(this).val() === '' || $(this).val() === null) {
					notNull = false;
				}
			});
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
<!-- //////////////////////////////////////////////////////////////// -->
<!--头部区-->
	<s:include value="/newpages/head.jsp"></s:include>
	<div class="top_bg"></div>
<!-- /////////////////////////////////////////////////////////////// -->



<!-- /////////////////////////////////////////////////////////// -->
	<!--  
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
	-->
<!-- //////////////////////////////////主体内容区以上部分////////////////////////////////////////////// -->
	<div id="divcontent"     class="container">
			
	
<!-- 	/////////////////////以上为导入部分///////////////////////////////////// -->
<!-- 		<div class="navdaohang"> -->
<%-- 			<s:action name="clublist!initLeftTopList" executeResult="true" /> --%>
<!-- 		</div> -->
		
		<s:form id="queryForm" name="queryForm" method="post"
			action="goods.asp" theme="simple">
			<s:hidden name="goods.id" id="goodsId" />
<!-- 			增加部分 -->
			<s:hidden name="planRelease.id" id="planReleaseId" />
			<s:hidden name="member.id" id="memberId" />
			<s:hidden name="flag" id="flag" />
			<s:hidden name="typeId" id="typeId" />
			<s:hidden name="typeName" id="typeName" />
			<s:hidden name="area" id="area" />
			<s:hidden name="ptarget" id="ptarget" />
			<s:hidden name="ptargetName" id="ptargetName" />
			<s:hidden name="planRelease.unitPrice" id="unitPrice"/>
<!-- 				增加部分结束			 -->
			
			
			<div class="dengl_top">
			
			
			
<!-- 			////////////////////////////////////////////原文件部分  开始/////////////////////////////////////////////// -->
				<div class="product_header">
					<div class="header_title">
						<a href="index.asp"> 网站首页 </a> > <a href="planlist.asp">健身计划 </a>>
						<s:property value="goods.name" />
					</div>





<!-- 				面包屑导航栏下面的三栏  -->
					<div class="hot_book clearfix">
<!-- 						图片部分 -->
						<div class="col-md-6 col-lg-5">
							<div style="position: relative;">
										<img class="middle"  src="picture/<s:property value="goods.image1"/>" alt="图片1"   />
										<p class="book_txt" style="position: absolute; left: 0; bottom: 0;">
											<s:property value="goods.summary" />
										</p>
									</div>
									
						
							<script>
								initAutoFocus();
							</script>

						</div>
						
						
<!-- 					中间部分 -->

						<div class="col-md-3 col-lg-4">
<!-- 							标题部分 -->
							<h4 class="boot_title">
										<s:property value="goods.name" />
									</h4>
							<p class="book_message price">
										价格：¥<span> 
												<s:property value="goods.price" />
											</span>
									</p>
									<p class="book_message score">
										评分：<span>
												<s:if test='#request.goodsAppraise[0].avgGrade>0'><s:property value='#request.goodsAppraise[0].avgGrade' /></s:if><s:else>0</s:else>
											 </span>分 (已有<i>
												<s:if test='#request.goodsAppraise[0].cnt>0'>
												<s:property value='#request.goodsAppraise[0].cnt' />
											</s:if>
											<s:else>0</s:else>
											 </i>人评价)
									</p>
									<p class="book_message sales">
										销量：<span>
												<s:if test='#request.goodsAppraise[0].saleNum>0'>
												<s:property value='#request.goodsAppraise[0].saleNum' />
											</s:if>
											<s:else>0</s:else>
											 </span>
									</p>						
									
									
									<div class="header_jiage">
										<p  style="padding: 10px 0; color: #adadad;">请在商品详情中填写您的身体数据，然后点击“立即购买”。</p>
									</div>
<!-- 									加入购物车与立即购买 -->
											<div class="chose buy">
										<a href="javascript:goodsCardcol();">立 即 购 买</a>
									</div>
										<a href="javascript:saveGoodsCardcol();"style="color: #a4c546">
										<div class="chose">
												<span class="glyphicon glyphicon-shopping-cart"></span>&nbsp;&nbsp;加入购物车
											</div></a>						
							
						</div>
<!-- 					右边部分 -->
						<div class="col-md-3 col-lg-3">
								<div class="writer">
										<p>
											作者：<span><a
											href="javascript:goMemberHome('<s:property value="#request.memberAppraise[0].id"/>','<s:property value="#request.memberAppraise[0].role"/>');"><s:property
												value="#request.memberAppraise[0].name" /></a></span>
										</p>
										<p>
											综合评分：<span  >
											<s:if
													test='#request.memberAppraise[0].member_grade>0'>
													<s:property value='#request.memberAppraise[0].member_grade' />
												</s:if> <s:else>0</s:else>
											
												 </span>
										</p>
										<p>
											服务数量：<span>
													<s:if test='#request.memberAppraise[0].salesNum>0'>
											<s:property value='#request.memberAppraise[0].salesNum' />
										</s:if>
										<s:else>0</s:else>
												 人次</span>
										</p>
										<h4>作者简介</h4>
										
										<div class="introdb"   style="padding-right: 10px; padding-bottom: 20px; font-size: 13px;color: #898989;">王严老师从1981年开始进行健美锻炼并从事健身健美训练的研究、教学及健美比赛裁判工作。现任北京市健美协会副主席，国际级健美裁判员，中国健美协会裁判员、裁判委员会委员、培训委员会委员、教练委员会委员。著有《健身运动指导全书》、《青年健美ABC》和《想对健身者说》等专著。</div> 
													
									</div>
<!-- 						/////////////////////////// -->
						
						
						
						
						
						</div>
					</div>
				</div>
<!-- 			////////////////////////////////////////	///原文件部分结束//////////////////////////////////////////////// -->
				
				
				
				
				
				<div class="product_side">
					<h2>相关计划</h2>
					<ul>
						<s:iterator value="#request.planRecommends2" status="start">
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
							<div class="" id="infoDiv" style="margin-top: 8px;">
								<div class="product_conment">
									<p class="procard">请根据您的实际情况填写以下项目，然后点击“立即购买”并完成支付。就可以获得一份王严健身专家系统为您制订的1个月私人健身计划：
									</p>
									<h2>一、您的健身目标</h2>
									<div class="product_pci">
										<ul style="padding-left: 135px;">
											<li class="pickdd"><img src="images/shap.jpg"
												width="146" height="138" /> <input type="radio"
												name="setting.target" value="1"
												<s:if test="setting.target==1">checked="checked"</s:if> />
												减脂塑形</li>
											<li class="pickdd"><img src="images/body.jpg"
												width="146" height="138" /> <input type="radio"
												name="setting.target" value="2"
												<s:if test="setting.target==2">checked="checked"</s:if> />
												健美增肌</li>
											<li><img src="images/power.jpg" width="146" height="138" />
												<input type="radio" name="setting.target" value="3"
												<s:if test="setting.target==3">checked="checked"</s:if> />
												增加力量</li>
										</ul>
									</div>

									<h2>二、您的身体数据</h2>
									<div class="product_shuju">
										<p class="prokk">
											<span  ><s:radio list="#{'M':'男性','F':'女性'}"     style=" margin-right:7px; position: relative; top: 3px;"  
													name="member.sex"></s:radio></span> <span class="pro_shgao"
												style="padding-left: 0px;">身高:<s:textfield
													name="setting.height"     style="width: 50px;border: 1px solid #ddd;line-height: 16px;"></s:textfield> cm
											</span> <span class="pro_shgao" style="padding-left: 0px;">体重:<s:textfield
													name="setting.weight"    style="width: 50px;border: 1px solid #ddd;line-height: 16px;"></s:textfield> kg
											</span>
										</p>
										<p class="prokk">
											<span class="body_shuji">腰围:<s:textfield
													name="setting.waistline"    style="width: 50px;border: 1px solid #ddd;line-height: 16px;"></s:textfield> cm
											</span> 最大卧推重量:<s:textfield name="setting.maxwm"    style="width: 50px;border: 1px solid #ddd;line-height: 16px;"></s:textfield>
											kg
										</p>
										<div class="wangye_pro">
											<div class="wang_pp">
												<p class="sj_title">最大卧推重量测试方法：</p>
												<p class="sj_cont">仰卧在平卧推长凳上，口鼻处于杠铃垂直下方，两脚分开踏在地上，双手抓握杠铃，握距略宽于肩，尽量使肘关节置于腕关节垂直下方，先将杠铃从推架上垂直向上慢慢扒起至两臂伸直，再水平移到两肩的垂直上方。吸气，两肘弯曲从两侧落下，将杠铃慢慢放下至杠铃接近或接触胸部，然后双臂用力将杠铃向上推起，至两臂伸直，推起时慢呼气。</p>
												<p class="sj_title">最大卧推重量指一次能推举的最大重量。请在他人保护下完成此测试。</p>
											</div>
											<div class="wangye_pic">
												<img src="images/3.gif" width="195" />
											</div>
										</div>
									</div>
									<h2>三、您喜欢的有氧训练项目</h2>
									<div class="quest">
										<div class="quetion">
											<div id="youxi2">
												<ul>
													<s:iterator value="#request.actions" status="st" var="as">
														<li><img src="<s:property value="image"/>" width="72"height="67" />
															 <span><input type="checkbox" id="cardios<s:property value="#st.index"/>" name="setting.favoriateCardio" value='<s:property value="name"/>'
																<s:if test="setting.favoriateCardio.indexOf(#as.name)>=0">checked="checked"</s:if>></input>
																<label for="cardios<s:property value="#st.index"/>"><s:property
																		value="name" /></label> </span>
														</li>
													</s:iterator>
												</ul>
											</div>
										</div>
									</div>
									<h2>
										四、健身训练开始日期 <input type="text" name="startDate" id="startDate"
											value="" readonly="readonly" style="width: 150px;border: 1px solid #ddd;color: gray;margin-left: 20px;height: 22px;padding-left: 10px;"   onclick="WdatePicker({el:'startDate'})" /> <img 
											id="img_repeatStart" onclick="WdatePicker({el:'startDate'})"
											src="script/DatePicker/skin/datePicker.gif" align="absmiddle"
											width="16" height="22" style="cursor: pointer;position: relative;left: -30px;top: -1px;">
									</h2>


								</div>
								<div class="shopping2"
									style="width: 176px; margin-left: auto; margin-right: auto;">
									<a href="javascript:goodsCardcol(); "><img
										src="images/product_07.gif" /></a>
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
													src="picture/<s:property value="fromImage" />"  style="width:60px;height:60px;" /><span><s:property
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
													<div style="margin-left:620px;margin-top:-20px">
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
														src="picture/<s:property value="fromImage" />" style="width:60px;height:60px;"/><span><s:property
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
													<div style="margin-left:620px;margin-top:-20px">
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
														src="picture/<s:property value="fromImage" />" style="width:60px;height:60px;"/><span><s:property
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
													<div style="margin-left:620px;margin-top:-20px">
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
												<div class="user_name" style="width:60px;height:60px;border:1px solid #dcdcdc">
													<a
														href="javascript:goMemberHome('<s:property value="fromId"/>','<s:property value="fromRole"/>');"><img style="width:60px;height:60px;border:1px solid #dcdcdc"
														src="picture/<s:property value="fromImage" />" style="width:60px;height:60px;"/><span><s:property
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
													<div style="margin-left:620px;margin-top:-20px">
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
														src="picture/<s:property value="fromImage" />" style="width:60px;height:60px;"/><span><s:property
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
													<div style="margin-left:620px;margin-top:-20px">
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