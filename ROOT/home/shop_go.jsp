<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><s:property value="product.name" /></title>
<s:include value="/share/meta.jsp"></s:include>
<!-- 增加样式 -->
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
<link type="text/css" href="css/bootstrap.css" rel="stylesheet" />
<link type="text/css" href="css/base.css" rel="stylesheet" />
<link type="text/css" href="css/planXq.css" rel="stylesheet" />
<link type="text/css" href="js/datepicker/skin/WdatePicker.css" 	rel="stylesheet" />
<script type="text/javascript" src="js/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="js/planXq.js"></script>
<script type="text/javascript" src="js/returnTop.js"></script>
<!-- 增加样式结束 -->

<link rel="stylesheet" type="text/css" href="css/pulic-1.css" />
<link rel="stylesheet" type="text/css" href="css/product.css" />
<link type="text/css" rel="stylesheet" href="script/jRating/jRating.jquery.css" />
<script type="text/javascript" src="script/jRating/jRating.jquery.js"></script>
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
	$(document).ready(function() {
		if ($("#flag").val() == "1") {
			var aDiv = $('#container_pro').find('.tab_container');
			$(aBtn).addClass('tab').siblings().removeClass("tab");
			aDiv.css('display', 'none');
			aDiv.eq($(aBtn).index()).css('display', 'block');
			$("#flag").val(2);
		}
	});

	$(function() {
		var aBtn = $('#container_pro .cont_pro').find('li');
		$("#dialog").dialog({
			autoOpen : false,
			show : "blind",
			hide : "explode",
			resizable : false
		});
		$('.onpicture').css('cursor', 'pointer');
		$(".grade").jRating({
			type : 'small',
			length : 10,
			isDisabled : true,
			decimalLength : 1,
			rateMax : 100
		});
		$(".avgCount").jRating({
			type : 'small',
			isDisabled : true,
			length : 5,
			decimalLength : 1,
			rateMax : 100
		});
		$('#altdiv2').dialog({
			autoOpen : false,
			width : 510,
			show : "blind",
			hide : "blind",
			modal : true,
			resizable : false
		});
		var aDiv = $('#container_pro').find('.tab_container');
		aBtn.click(function() {
			$(this).addClass('tab').siblings().removeClass("tab");
			aDiv.css('display', 'none');
			aDiv.eq($(this).index()).css('display', 'block');

		});

	})

	function changed(li) {
		var aBtnd = document.getElementById("sort");
		var lisd = aBtnd.getElementsByTagName("li");
		var aDivd = document.getElementById("container_pro")
				.getElementsByClassName("part");
		for (var i = 0; i < lisd.length; i++) {
			if (li == lisd[i]) {
				aDivd[i].style.display = "block";
				lisd[i].style.color = "#FF0000";
			} else {
				aDivd[i].style.display = "none";
				lisd[i].style.color = "#000000";
			}
		}
	}

	function onShop(productId, memberId) {
		window.location.href = "look.asp?pId=" + productId;
		/**
		jQuery('#productId').val(productId);
		jQuery('#queryForm').attr('action','look.asp');
		jQuery('#queryForm').submit();
		 */
	}

	function sendMessage(id, name, pid) {
		if (id && name) {
			$("select[name='msg.memberTo.id']").empty();
			if (pid)
				$('#msgparent').val(pid);
			$("<option value='"+id+"'>"+name+"</option>").appendTo("select[name='msg.memberTo.id']");
			$("textarea[name='msg.content']").val("");
			$("#dialog").dialog("open");
		} else {
			$
					.ajax({
						type : 'post',
						url : 'message!findMember.asp',
						data : '',
						success : function(msg) {
							$("select[name='msg.memberTo.id']").empty();//清空下拉框
							if (msg) {
								var memberArr, member;
								memberArr = msg.split(":");
								for (var i = 0; i < memberArr.length; i++) {
									member = memberArr[i].split(",");
									$("<option value='"+member[0]+"'>"+member[1]+"</option>").appendTo("select[name='msg.memberTo.id']");//添加下拉框的option
								}
							}
							$("textarea[name='msg.content']").val("");
							$("#dialog").dialog("open");
						}
					});
		}
	}

	function preShop(productId, memberId) {
		url = "clublist!shoGo.asp?productId=" + productId;
		$('#queryForm').attr('action', url);
		$('#queryForm').submit();
	}

	function onClosec() {
		$('#dialog').dialog('close');
	}

	function onSave() {
		if (!$("select[name='msg.memberTo.id']").val()) {
			alert("收件人不能为空，请确认！");
			$("select[name='msg.memberTo.id']").focus();
			$("select[name='msg.memberTo.id']").selet();
			return;
		}
		if (!$("textarea[name='msg.content']").val()) {
			alert("消息内容不能为空，请确认！");
			$("textarea[name='msg.content']").focus();
			$("textarea[name='msg.content']").selet();
			return;
		}
		$.ajax({
			type : 'post',
			url : 'message!save.asp',
			data : $('#msgForm').serialize(),
			success : function(msg) {
				if (msg == "ok") {
					alert("当前信息已成功发送！");
					onClosec();
				} else {
					alert(msg);
				}
			}
		});
	}

	function setPicture(a) {
		$("#image").attr("src", 'picture/' + a);
	}

	function onPicture() {
		if (!"<s:property value="#session.loginMember.id"/>") {
			openLogin();
			return;
		}
		$.ajax({
			url : 'clublist!loadPic.asp',
			type : 'post',
			data : 'productId=' + '<s:property value="product.id"/>',
			success : function(resp) {
				$('#altdiv2').html(resp);
				$('#altdiv2').dialog('open');
			}
		});
	}
	function saveShopProduct() {
		if (!"<s:property value="#session.loginMember.id"/>") {
			openLogin();
			return;
		}
		$.ajax({
			url : 'shop!saveShopProduct.asp',
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
	function shopProduct() {
		if (!"<s:property value="#session.loginMember.id"/>") {
			openLogin();
			return;
		}
		url = 'order!submitProd.asp?prodType=1&id=' + $("#productId").val();
		$('#startDate').val($('#orderStartTime').val());
		$('#queryForm').attr("action", url);
		$('#queryForm').submit();
	}

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
	
	//增加部分
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

<!-- 	内容区 -->

	<div id="divcontent">
	
<%-- 		<s:include value="/share/home-header_1.jsp" /> --%>
<!-- 		<div class="navdaohang"> -->
<%-- 			<s:action name="clublist!initLeftTopList" executeResult="true" --%>
<%-- 				ignoreContextParams="true" namespace="/" /> --%>
<!-- 		</div> -->
		
		<s:form id="queryForm" name="queryForm" method="post"
			action="clublist!shoGo.asp" theme="simple">
			<s:hidden name="product.id" id="productId" />
			<s:hidden name="product.member.id" id="memberId" />
			<s:hidden name="product.member.name" id="productName" />
			<s:hidden name="startDate" id="startDate" />
			<s:hidden name="product.proType" />
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
			
			<div class="dengl_top">
				<div class="product_header">
<!-- 					<div class="header_title"> -->
<!-- 						<a href="index.asp">网站首页</a>> <a href="clublist.asp">健身场馆 </a>> -->
<%-- 						<s:property value="product.name" /> --%>
<!-- 					</div> -->
<!-- 	样式开始处 -->
<!-- 					<div class="header_content  "> -->
<!-- 						图片部分 -->
<!-- 						<div class="header_pic"> -->
<!-- 							<div class="foucs"> -->
<!-- 								<div class="f_l hbtpc"> -->
<!-- 									<div id="focuscont"> -->
<!-- 										<p class="filt"></p> -->
<!-- 										<div class="focuscont" id="focuscont1"> -->
<!-- 											<p> -->
<!-- 												<a href="#"><img -->
<%-- 													src="picture/<s:property  --%>

<%-- value="product.image1"/>" --%>
<!-- 													alt="图片1"></a> -->
<!-- 											</p> -->

<!-- 										</div> -->
<!-- 										<div class="focuscont" id="focuscont2" style="display: none"> -->
<!-- 											<p> -->
<!-- 												<a href="#"><img -->
<%-- 													src="picture/<s:property value="product.image2"/>" --%>
<!-- 													alt="图片2"></a> -->
<!-- 											</p> -->

<!-- 										</div> -->
<!-- 										<div class="focuscont" id="focuscont3" style="display: none"> -->
<!-- 											<p> -->
<!-- 												<a href="#"><img -->
<%-- 													src="picture/<s:property value="product.image3"/>" --%>
<!-- 													alt="图片3"></a> -->
<!-- 											</p> -->
<!-- 										</div> -->

<!-- 									</div> -->
<!-- 									<ul id="focustab" class="focustab"> -->
<!-- 										<li class="foncuimg"><a class="act" href="#" name="1"><img -->
<%-- 												src="picture/<s:property value="product.image1"/>"></a></li> --%>
<!-- 										<li class="foncuimg"><a href="#" name="2"><img -->
<%-- 												src="picture/<s:property value="product.image2"/>"></a></li> --%>
<!-- 										<li><a href="#" name="3"><img -->
<%-- 												src="picture/<s:property value="product.image3"/>"></a></li> --%>

<!-- 									</ul> -->
<!-- 								</div> -->
<!-- 							</div> -->
<%-- 							<script> --%>
<!-- // 								initAutoFocus(); -->
<%-- 							</script> --%>

<!-- 						</div> -->
<!-- 					中间部分 -->
<!-- 						<div class="header_cont"> -->
<!-- 							<h1> -->
<%-- 								<s:property value="product.name" /> --%>
<!-- 							</h1> -->
<!-- 							<div class="header_info"> -->
<%-- 								<s:property value="#request.memo" /> --%>
<!-- 							</div> -->
<!-- 							<div class="header_jiage"> -->
<!-- 								<ul> -->
<!-- 									<li class="sprice"><div class="dt">价格:</div> -->
<!-- 										<div class="dd"> -->
<%-- 											<strong><s:property value="product.cost" /></strong> --%>
<!-- 										</div></li> -->
<!-- 									<li><div class="dt">评分:</div> -->
<!-- 										<div class="dd"> -->
<%-- 												<s:if test='#request.productAppraise[0].avgGrade>0'><s:property value='#request.productAppraise[0].avgGrade' /></s:if><s:else>0</s:else> --%>
<%-- 											<s:property value="#request.productAppraise[0].avgGrade" /> --%>
<!-- 											分 (已有 -->
<%-- 											<s:if test="#request.productAppraise[0].cnt>0"> --%>
<%-- 												<s:property value="#request.productAppraise[0].cnt" /> --%>
<%-- 											</s:if> --%>
<%-- 											<s:else>0</s:else> --%>
<!-- 											人评价) -->
<!-- 										</div></li> -->
<!-- 									<li><div class="dt">销量:</div> -->
<!-- 										<div class="dd"> -->
<%-- 											<s:if test="#request.productAppraise[0].saleNum>0"> --%>
<%-- 												<s:property value="#request.productAppraise[0].saleNum" /> --%>
<%-- 											</s:if> --%>
<%-- 											<s:else>0</s:else> --%>
<!-- 										</div></li> -->
<!-- 								</ul> -->
<!-- 							</div> -->

<!-- 							<div class="headerborder">请在以下健身合同中填写开始日期后完成支付。</div> -->
<!-- 							<div class="shopping"> -->
<!-- 								<a href="javascript:shopProduct();"><img -->
<!-- 									src="images/product_07.gif" /></a> <a -->
<!-- 									href="javascript:saveShopProduct();"><img -->
<!-- 									src="images/product_09.gif" /></a> -->
<!-- 							</div> -->

<!-- 						</div> -->
<!-- 					右边部分 -->
<!-- 						<div class="header_Author"> -->
<!-- 							<ul> -->
<%-- 								<li class="Author"><div class="author1">作<span></span>者:</div> --%>
<!-- 									<div class="author2"> -->
<!-- 										<a -->
<%-- 											href="javascript:goMemberHome('<s:property value="#request.memberAppraise[0].id"/>','<s:property value="#request.memberAppraise[0].role"/>');"><s:property --%>
<%-- 												value="#request.memberAppraise[0].name" /></a> --%>
<!-- 									</div></li> -->
<!-- 								<li class="Author"><div class="author1">综合评分:</div> -->
<!-- 									<div class="Authoravg"> -->
<!-- 										<div class="avgCount" -->
<%-- 											id="<s:if test='#request.memberAppraise[0].member_grade>0'><s:property value='#request.memberAppraise[0].member_grade' /></s:if><s:else>0</s:else>_appraise.member_grade"></div> --%>
<!-- 										<div class="zonhe"> -->
<%-- 											<font color="#f61313" class="p_fei"><s:if --%>
<%-- 													test='#request.memberAppraise[0].member_grade>0'> --%>
<%-- 													<s:property value='#request.memberAppraise[0].member_grade' /> --%>
<%-- 												</s:if> <s:else>0</s:else></font> --%>
<!-- 										</div> -->
<!-- 									</div></li> -->
<!-- 								<li class="Author"><div class="author1">服务数量:</div> -->
<!-- 									<div> -->
<%-- 										<s:if test='#request.memberAppraise[0].salesNum>0'> --%>
<%-- 											<s:property value='#request.memberAppraise[0].salesNum' /> --%>
<%-- 										</s:if> --%>
<%-- 										<s:else>0</s:else> --%>
<!-- 										人次 -->
<!-- 									</div></li> -->
<!-- 								<div class="header_phone"> -->
<!-- 									<a id="coloa" -->
<%-- 										href="javascript:goProductHome('<s:property value="product.member.id"/>');"><span --%>
<%-- 										class="autoe">更多作品</span></a> --%>
<!-- 									<ul> -->
<%-- 										<s:iterator value="#request.otherProducts" status="p"> --%>
<%-- 											<s:if test="#p.index<6"> --%>
<!-- 												<li><a -->
<%-- 													href="javascript:preShop('<s:property value="id"/>','<s:property value="member.id"/>');"><s:property --%>
<%-- 															value="name" /></a></li> --%>
<%-- 											</s:if> --%>
<%-- 										</s:iterator> --%>
<!-- 									</ul> -->
<!-- 								</div> -->
<!-- 								<div class="Author_active"> -->
<!-- 									<a -->
<%-- 										href="javascript:goMemberHome('<s:property value="#request.memberAppraise[0].id"/>','<s:property value="#request.memberAppraise[0].role"/>');">访问作者主页</a><a --%>
<%-- 										onclick="sendMessage('<s:property value="product.member.id"/>','<s:property value="product.member.name"/>','');" --%>
<!-- 										id="coloa" class="contrat">联系作者</a> -->
<!-- 								</div> -->
<!-- 							</ul> -->
<!-- 						</div> -->
<!-- 					</div> -->
					
				
<!-- 				</div> -->


<!-- 样式结束处 -->

<!-- 副本 -->
<!-- 	样式开始处 -->
					<div class="hot_book  clearfix ">
<!-- 						图片部分 -->
						<div class="col-md-6 col-lg-5">
								<div style="position: relative;">
									<img  class="middle"      src='picture/<s:property  value="product.image1" /> '   	alt="图片1"    />
									<p class="book_txt" 	style="position: absolute; left: 0; bottom: 0;width: 100%;"><s:property value="#request.memo" /></p>
								</div>
						
						
<!-- 							<div class="foucs"> -->
<!-- 								<div class="f_l hbtpc"> -->
<!-- 									<div id="focuscont"> -->
<!-- 										<p class="filt"></p> -->
<!-- 										<div class="focuscont" id="focuscont1"> -->
<!-- 											<p> -->
<!-- 												<a href="#"><img -->
<%-- 													src="picture/<s:property  value="product.image1"/>" --%>
<!-- 													alt="图片1"></a> -->
<!-- 											</p> -->

<!-- 										</div> -->
<!-- 										<div class="focuscont" id="focuscont2" style="display: none"> -->
<!-- 											<p> -->
<!-- 												<a href="#"><img -->
<%-- 													src="picture/<s:property value="product.image2"/>" --%>
<!-- 													alt="图片2"></a> -->
<!-- 											</p> -->

<!-- 										</div> -->
<!-- 										<div class="focuscont" id="focuscont3" style="display: none"> -->
<!-- 											<p> -->
<!-- 												<a href="#"><img -->
<%-- 													src="picture/<s:property value="product.image3"/>" --%>
<!-- 													alt="图片3"></a> -->
<!-- 											</p> -->
<!-- 										</div> -->

<!-- 									</div> -->
<!-- 									<ul id="focustab" class="focustab"> -->
<!-- 										<li class="foncuimg"><a class="act" href="#" name="1"><img -->
<%-- 												src="picture/<s:property value="product.image1"/>"></a></li> --%>
<!-- 										<li class="foncuimg"><a href="#" name="2"><img -->
<%-- 												src="picture/<s:property value="product.image2"/>"></a></li> --%>
<!-- 										<li><a href="#" name="3"><img -->
<%-- 												src="picture/<s:property value="product.image3"/>"></a></li> --%>

<!-- 									</ul> -->
<!-- 								</div> -->
<!-- 							</div> -->
<%-- 							<script> --%>
<!-- // 								initAutoFocus(); -->
<%-- 							</script> --%>
						</div>
<!-- 					中间部分 -->
						<div class="col-md-3 col-lg-4">
							<h4 class="boot_title">
								<s:property value="product.name" />
							</h4>
							<p class="book_message price">
								价格：¥<span>  <s:property value="product.cost" /></span>
							</p>
							<p class="book_message score">
								评分：<span><s:if test='#request.productAppraise[0].avgGrade>0'><s:property value='#request.productAppraise[0].avgGrade' /></s:if><s:else>0</s:else>
											<s:property value="#request.productAppraise[0].avgGrade" /></span>分 (已有	<s:if test="#request.productAppraise[0].cnt>0">
												<s:property value="#request.productAppraise[0].cnt" />
											</s:if>
											<s:else>0</s:else>人评价)
							</p>
							<p class="book_message sales">
								销量：<span><s:if test="#request.productAppraise[0].saleNum>0">
												<s:property value="#request.productAppraise[0].saleNum" />
											</s:if>
											<s:else>0</s:else></span>
							</p>
							<p  class="book_message">请在以下健身合同中填写开始日期后完成支付。</p>
							
							<div class="chose buy">
									<a href="javascript:shopProduct();">立 即 购 买</a>
								</div>
								<s:if test="planRelease.unitPrice==0"></s:if>
								<s:else>
									<a href="javascript:saveShopProduct();" style="color: #a4c546"><div
											class="chose">
											<span class="glyphicon glyphicon-shopping-cart"></span>&nbsp;&nbsp;加入购物车
										</div></a>
								</s:else>
<!-- 							<h1> -->
<%-- 								<s:property value="product.name" /> --%>
<!-- 							</h1> -->
<!-- 							<div class="header_info"> -->
<%-- 								<s:property value="#request.memo" /> --%>
<!-- 							</div> -->
<!-- 							<div class="header_jiage"> -->
<!-- 								<ul> -->
<!-- 									<li class="sprice"><div class="dt">价格:</div> -->
<!-- 										<div class="dd"> -->
<%-- 											<strong><s:property value="product.cost" /></strong> --%>
<!-- 										</div></li> -->
<!-- 									<li><div class="dt">评分:</div> -->
<!-- 										<div class="dd"> -->
<%-- 												<s:if test='#request.productAppraise[0].avgGrade>0'><s:property value='#request.productAppraise[0].avgGrade' /></s:if><s:else>0</s:else> --%>
<%-- 											<s:property value="#request.productAppraise[0].avgGrade" /> --%>
<!-- 											分 (已有 -->
<%-- 											<s:if test="#request.productAppraise[0].cnt>0"> --%>
<%-- 												<s:property value="#request.productAppraise[0].cnt" /> --%>
<%-- 											</s:if> --%>
<%-- 											<s:else>0</s:else> --%>
<!-- 											人评价) -->
<!-- 										</div></li> -->
<!-- 									<li><div class="dt">销量:</div> -->
<!-- 										<div class="dd"> -->
<%-- 											<s:if test="#request.productAppraise[0].saleNum>0"> --%>
<%-- 												<s:property value="#request.productAppraise[0].saleNum" /> --%>
<%-- 											</s:if> --%>
<%-- 											<s:else>0</s:else> --%>
<!-- 										</div></li> -->
<!-- 								</ul> -->
<!-- 							</div> -->

<!-- 							<div class="headerborder">请在以下健身合同中填写开始日期后完成支付。</div> -->
<!-- 							<div class="shopping"> -->
<!-- 								<a href="javascript:shopProduct();"><img -->
<!-- 									src="images/product_07.gif" /></a> <a -->
<!-- 									href="javascript:saveShopProduct();"><img -->
<!-- 									src="images/product_09.gif" /></a> -->
<!-- 							</div> -->




						</div>
<!-- 					右边部分 -->
						<div class="col-md-3 col-lg-3">
						<div  class="writer">
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
										服务数量：<span><s:if test='#request.memberAppraise[0].salesNum>0'>
											<s:property value='#request.memberAppraise[0].salesNum' />
										</s:if>
										<s:else>0</s:else>人次</span>
									</p>
									<h4>更多作品</h4>
									<ul>
										<s:iterator value="#request.otherProducts" status="p">
											<s:if test="#p.index<6">
												<li><a
													href="javascript:preShop('<s:property value="id"/>','<s:property value="member.id"/>');"><s:property
															value="name" /></a></li>
											</s:if>
										</s:iterator>
									</ul>
<!-- 									联系作者 -->
									<div class="Author_active">
										<a   style="font-size:14px;"
											href="javascript:goMemberHome('<s:property value="#request.memberAppraise[0].id"/>','<s:property value="#request.memberAppraise[0].role"/>');">访问作者主页</a>
										<a
											onclick="sendMessage('<s:property value="product.member.id"/>','<s:property value="product.member.name"/>','');"
											id="coloa" class="contrat">联系作者</a>
									</div>
						</div>
						
<!-- 							<ul> -->
<%-- 								<li class="Author"><div class="author1">作<span></span>者:</div> --%>
<!-- 									<div class="author2"> -->
<!-- 										<a -->
<%-- 											href="javascript:goMemberHome('<s:property value="#request.memberAppraise[0].id"/>','<s:property value="#request.memberAppraise[0].role"/>');"><s:property --%>
<%-- 												value="#request.memberAppraise[0].name" /></a> --%>
<!-- 									</div></li> -->
<!-- 								<li class="Author"><div class="author1">综合评分:</div> -->
<!-- 									<div class="Authoravg"> -->
<!-- 										<div class="avgCount" -->
<%-- 											id="<s:if test='#request.memberAppraise[0].member_grade>0'><s:property value='#request.memberAppraise[0].member_grade' /></s:if><s:else>0</s:else>_appraise.member_grade"></div> --%>
<!-- 										<div class="zonhe"> -->
<%-- 											<font color="#f61313" class="p_fei"><s:if --%>
<%-- 													test='#request.memberAppraise[0].member_grade>0'> --%>
<%-- 													<s:property value='#request.memberAppraise[0].member_grade' /> --%>
<%-- 												</s:if> <s:else>0</s:else></font> --%>
<!-- 										</div> -->
<!-- 									</div></li> -->
<!-- 								<li class="Author"><div class="author1">服务数量:</div> -->
<!-- 									<div> -->
<%-- 										<s:if test='#request.memberAppraise[0].salesNum>0'> --%>
<%-- 											<s:property value='#request.memberAppraise[0].salesNum' /> --%>
<%-- 										</s:if> --%>
<%-- 										<s:else>0</s:else> --%>
<!-- 										人次 -->
<!-- 									</div></li> -->
<!-- 								<div class="header_phone"> -->
<!-- 									<a id="coloa" -->
<%-- 										href="javascript:goProductHome('<s:property value="product.member.id"/>');"><span --%>
<%-- 										class="autoe">更多作品</span></a> --%>
<!-- 									<ul> -->
<%-- 										<s:iterator value="#request.otherProducts" status="p"> --%>
<%-- 											<s:if test="#p.index<6"> --%>
<!-- 												<li><a -->
<%-- 													href="javascript:preShop('<s:property value="id"/>','<s:property value="member.id"/>');"><s:property --%>
<%-- 															value="name" /></a></li> --%>
<%-- 											</s:if> --%>
<%-- 										</s:iterator> --%>
<!-- 									</ul> -->
<!-- 								</div> -->
<!-- 								<div class="Author_active"> -->
<!-- 									<a -->
<%-- 										href="javascript:goMemberHome('<s:property value="#request.memberAppraise[0].id"/>','<s:property value="#request.memberAppraise[0].role"/>');">访问作者主页</a><a --%>
<%-- 										onclick="sendMessage('<s:property value="product.member.id"/>','<s:property value="product.member.name"/>','');" --%>
<!-- 										id="coloa" class="contrat">联系作者</a> -->
<!-- 								</div> -->
<!-- 							</ul> -->
						</div>
			<!-- 						右边部分 -->
<!-- <div class="writer"> -->
<!-- 						<p> -->
<%-- 							作者：<span><a --%>
<%-- 								href="javascript:goMemberHome('<s:property value="#request.memberAppraise[0].id"/>','<s:property value="#request.memberAppraise[0].role"/>');"><s:property --%>
<%-- 										value="#request.memberAppraise[0].name" /></a></span> --%>
<!-- 						</p> -->
<!-- 						<p> -->
<%-- 							综合评分：<span><s:if --%>
<%-- 									test='#request.memberAppraise[0].member_grade>0'> --%>
<%-- 									<s:property value='#request.memberAppraise[0].member_grade' /> --%>
<%-- 								</s:if> <s:else>0</s:else></span> --%>
<!-- 						</p> -->
<!-- 						<p> -->
<%-- 							服务数量：<span><s:if --%>
<%-- 									test='#request.memberAppraise[0].salesNum>0'> --%>
<%-- 									<s:property value='#request.memberAppraise[0].salesNum' /> --%>
<%-- 								</s:if> <s:else>0</s:else>人次</span> --%>
<!-- 						</p> -->
<!-- 						<h4>更多作品</h4> -->
<!-- 						<ul> -->
<%-- 							<s:iterator value="#request.otherPlans" status="p1"> --%>
<%-- 								<s:if test="#p1.index<6"> --%>
<%-- 									<li><a href="plan.asp?pid=<s:property value="id"/>"><s:property --%>
<%-- 												value="planName" /></a></li> --%>
<%-- 								</s:if> --%>
<%-- 							</s:iterator> --%>
<!-- 						</ul> -->
<!-- 					</div> -->
<!-- 结合素 -->		
					
					
					
					</div>
				
				</div>


<!-- 样式结束处 -->

				<div class="product_side">
					<h2>相关计划</h2>
					<ul>
						<s:iterator value="#request.pList" status="p">
							<s:if test="#p.index<3">
								<li class="fore1">
									<div class="p-img">
										<a
											href="javascript:preShop('<s:property value="id"/>','<s:property value="member.id"/>');">
											<img src="picture/<s:property value="image1"/>" />
										</a>
									</div>
									<div class="p-name">
										<a
											href="javascript:preShop('<s:property value="id"/>','<s:property value="member.id"/>');">
											<s:property value="name" />
										</a>

									</div>
									<div class="p-pric">
										<strong><s:property value="cost" /></strong>
									</div>
								</li>
							</s:if>
						</s:iterator>
					</ul>
				</div>
				<div class="product_main">
					<div id="container_pro">
						<ul class="cont_pro">
							<li class="tab" style="border-left: 1px solid #e7e7e7;"><span>健身合同</span></li>
							<li><span>通用条款</span></li>
							<li><span>用户评价</span></li>
						</ul>
						<div id="news" class="tab_container"
							style="display: block; margin-top: 10px;">
							<div class="news_hetong">
								<s:if test="product.proType == 1 ">
									<s:include value="/shop/shop_edit1.jsp" />
								</s:if>
								<s:elseif test="product.proType == 2">
									<s:include value="/shop/shop_edit2.jsp" />
								</s:elseif>
								<s:elseif test="product.proType == 3">
									<s:include value="/shop/shop_edit3.jsp" />
								</s:elseif>
								<s:elseif test="product.proType == 4">
									<s:include value="/shop/shop_edit4.jsp" />
								</s:elseif>
								<s:elseif test="product.proType == 5">
									<s:include value="/shop/shop_edit5.jsp" />
								</s:elseif>
								<s:elseif test="product.proType == 6">
									<s:include value="/shop/shop_edit6.jsp" />
								</s:elseif>
								<s:else>
									<s:include value="/homeWindow/product_list.jsp" />
								</s:else>
							</div>
						</div>
						<div id="news" class="tab_container">

							<h3 class="hetong_title">健身合同通用条款</h3>
							<div class="news_hetong">
								<b>一、双方主要权利和义务</b>

								<p>1.甲方有权在办卡前向乙方详细了解健身卡的种类、费用、使用权限、包含项目等相关内容。</p>
								<P>2.甲方应当提供有关自己身份、健康状况方面的真实信息，按照乙方要求进行健康测评，并在可能影响合同履行的有关信息变化时及时告知乙方。</P>
								<P>3.甲方应当保证在健身时身体状况与所参加的健身项目相适应。</P>
								<P>4.甲方应当遵守乙方有关安全管理的规章制度，不得实施危害社会秩序、公共安全和他人健康的行为。</P>
								<P>5.甲方应当避免携带贵重物品或将贵重物品转交乙方代管；甲方应当妥善保管自己的随身物品，并在健身后按约定腾空公共更衣柜。</P>
								<P>6.甲方应当按照乙方指导或提示使用器械、设施及健身，对器械、设施有疑问或身体不适时应当立即停止健身并向乙方求助。</P>
								<P>7.乙方拥有对健身场所的正当经营管理权。</P>
								<P>8.乙方应当为甲方的身份、健康信息承担保密义务。</P>
								<p>9.乙方应当按照约定为甲方提供健身服务环境，无正当理由不得暂停营业或缩短营业时间。</p>
								<p>10.乙方应当在休闲健身场所内配备指导人员，向甲方告知或提示参加健身项目、使用器材或设施可能引发的风险及后果。</p>
								<p>11.乙方应当在健身场所内配备符合规定的救护救生设备及救护人员。</p>
								<p>12.乙方对健身器械、设施设备负有保养、维护责任，并确保健身器械、设施设备能够安全、正常使用。</p>
								<b>二、法律责任</b>
								<p>1.一方违反合同约定给对方或第三方造成损失的，均应当承担相应的赔偿责任。</p>
								<p>2.由于不可归责于乙方的第三方侵害等原因导致甲方人身、财产权益受到损害的，乙方不承担赔偿责任；但乙方未尽到安全保障义务的，应当承担相应的

									法律责任。</p>
								<b>三、合同解除</b>
								<p>1.乙方在健身卡有效期限内存在下列行为且双方协商不成时，甲方有权解除合同：</p>
								<p>（1）擅自提高承诺的服务价格；</p>
								<p>（2）缩短健身卡的有效期限；</p>
								<P>（3）减少承诺的卡内包含项目；</P>
								<P>（4）关闭单店健身卡所属门店；</P>
								<P>（5）增加健身卡使用的限制条件。</P>
								<P>2.当甲方有下列行为时，乙方有权解除合同，并按照《合同法》第97条处理善后事宜，但不得不合理地拒绝返还相应预付费用：</P>
								<P>（1）隐瞒患有严重危及自己或他人安全、健康疾病的；</P>
								<P>（2）严重违反乙方规章制度，经劝阻拒不改正的；</P>
								<p>（3）实施严重违反国家法律法规、危害社会秩序、公共安全和他人健康行为的。</p>
								<b>四、合同的效力和组成</b>
								<p>1.本合同自甲方付款之日起生效。</p>
								<p>2.未尽事宜可按照国家有关法律、规定或经甲方确认的《会员须知》、《入会协议》等书面文件执行，或由双方以书面形式予以补充。前述书面文件或

									书面补充中含有不合理地减轻或免除乙方责任，或不合理地加重甲方责任、排除甲方主要权利内容的，仍以本合同约定或有关法律法规为准。</p>

							</div>


						</div>

						<div id="news" class="tab_container">
							<div class="container_user">
								<span>总体评价:</span>
								<div class="user_xp" />
								<div class="avgCount"
									id="<s:if test='#request.productAppraise[0].avgGrade>0'><s:property value='#request.productAppraise[0].avgGrade' /></s:if><s:else>0</s:else>_appraise.avgGrade"></div>
							</div>
							<strong><s:if
									test="#request.productAppraise[0].avgGrade>0">
									<s:property value="#request.productAppraise[0].avgGrade" />
								</s:if> <s:else>0</s:else>分</strong>(已有
							<s:if test="#request.productAppraise[0].cnt>0">
								<s:property value="#request.productAppraise[0].cnt" />
							</s:if>
							<s:else>0</s:else>
							人评价)<span class="great">好评(<font color="#9c9a9c"><s:if
										test='#request.productAppraise[0].goodProp!=null'>
										<s:property value='#request.productAppraise[0].goodProp' />
									</s:if> <s:else>0</s:else></font>)
							</span> <span class="great">中评 (<font color="#9c9a9c"><s:if
										test='#request.productAppraise[0].generalProp!=null'>
										<s:property value='#request.productAppraise[0].generalProp' />
									</s:if> <s:else>0</s:else></font>)
							</span> <span class="great">差评 (<font color="#9c9a9c"><s:if
										test='#request.productAppraise[0].badProp!=null'>
										<s:property value='#request.productAppraise[0].badProp' />
									</s:if> <s:else>0</s:else></font>)
							</span>
						</div>

						<div id="sort">
							<ul class="part_pro">
								<li style="width: 100px; color: #FF0000;"
									onclick="changed(this)"><span>全部评价(<s:if
											test="#request.productAppraise[0].cnt>0">
											<s:property value="#request.productAppraise[0].cnt" />
										</s:if> <s:else>0</s:else>)
								</span></li>
								<li style="width: 100px;" onclick="changed(this)"><span>好评(<s:if
											test="#request.productAppraise[0].goodNum>0">
											<s:property value="#request.productAppraise[0].goodNum" />
										</s:if> <s:else>0</s:else>)
								</span></li>
								<li style="width: 100px;" onclick="changed(this)"><span>中评(<s:if
											test="#request.productAppraise[0].generalNum>0">
											<s:property value="#request.productAppraise[0].generalNum" />
										</s:if> <s:else>0</s:else>)
								</span></li>
								<li style="width: 100px;" onclick="changed(this)"><span>差评(<s:if
											test="#request.productAppraise[0].badNum>0">
											<s:property value="#request.productAppraise[0].badNum" />
										</s:if> <s:else>0</s:else>)
								</span></li>
								<li style="width: 10px;" onclick="changed(this)"><span>有晒单的评价(<s:if
											test="#request.productAppraise[0].picNum>0">
											<s:property value="#request.productAppraise[0].picNum" />
										</s:if> <s:else>0</s:else>)
								</span></li>
							</ul>
						</div>
						<!-- 全部评价 -->
						<div class="part">
							<s:iterator value="#request.productAppraise">
								<div class="user_spring">
									<div class="user_name">
										<a
											href="javascript:goMemberHome('<s:property value="fromId"/>','<s:property value="fromRole"/>');"><img
											src="picture/<s:property value="fromImage" />"
											style="width: 60px; height: 60px;" /><span><s:property
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
										<s:if
											test="(image1!=null&&image1!='')||(image2!=null&&image2!='')||(image3!=null&&image3!='')">
											<span style="margin-left: 540px;">上传的图片:</span>
											<div style="margin-left: 620px; margin-top: -20px">
												<s:if test="image1 != null && image1 != ''">
													<img style="border: 1px solid #dcdcdc" width="60px"
														height="60px" src="picture/<s:property value='image1'/>" />
												</s:if>
												<s:if test="image2 != null && image2 != ''">
													<img style="border: 1px solid #dcdcdc" width="60px"
														height="60px" src="picture/<s:property value='image2'/>" />
												</s:if>
												<s:if test="image3 != null && image3 != ''">
													<img style="border: 1px solid #dcdcdc" width="60px"
														height="60px" src="picture/<s:property value='image3'/>" />
												</s:if>
											</div>
										</s:if>
									</div>
								</div>
							</s:iterator>
						</div>

						<!-- 好评 -->
						<div class="part" style="display: none;">
							<s:iterator value="#request.productAppraise">
								<s:if test="grade>=80">
									<div class="user_spring">
										<div class="user_name">
											<a
												href="javascript:goMemberHome('<s:property value="fromId"/>','<s:property value="fromRole"/>');"><img
												src="picture/<s:property value="fromImage" />"
												style="width: 60px; height: 60px;" /><span><s:property
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
											<s:if
												test="(image1!=null&&image1!='')||(image2!=null&&image2!='')||(image3!=null&&image3!='')">
												<span style="margin-left: 540px;">上传的图片:</span>
												<div style="margin-left: 620px; margin-top: -20px">
													<s:if test="image1 != null && image1 != ''">
														<img style="border: 1px solid #dcdcdc" width="60px"
															height="60px" src="picture/<s:property value='image1'/>" />
													</s:if>
													<s:if test="image2 != null && image2 != ''">
														<img style="border: 1px solid #dcdcdc" width="60px"
															height="60px" src="picture/<s:property value='image2'/>" />
													</s:if>
													<s:if test="image3 != null && image3 != ''">
														<img style="border: 1px solid #dcdcdc" width="60px"
															height="60px" src="picture/<s:property value='image3'/>" />
													</s:if>
												</div>
											</s:if>
										</div>
									</div>
								</s:if>
							</s:iterator>
						</div>

						<!-- 中评 -->
						<div class="part" style="display: none;">
							<s:iterator value="#request.productAppraise">
								<s:if test="grade>=60 && grade<80">
									<div class="user_spring">
										<div class="user_name">
											<a
												href="javascript:goMemberHome('<s:property value="fromId"/>','<s:property value="fromRole"/>');"><img
												src="picture/<s:property value="fromImage" />"
												style="width: 60px; height: 60px;" /><span><s:property
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
											<s:if
												test="(image1!=null&&image1!='')||(image2!=null&&image2!='')||(image3!=null&&image3!='')">
												<span style="margin-left: 540px;">上传的图片:</span>
												<div style="margin-left: 620px; margin-top: -20px">
													<s:if test="image1 != null && image1 != ''">
														<img style="border: 1px solid #dcdcdc" width="60px"
															height="60px" src="picture/<s:property value='image1'/>" />
													</s:if>
													<s:if test="image2 != null && image2 != ''">
														<img style="border: 1px solid #dcdcdc" width="60px"
															height="60px" src="picture/<s:property value='image2'/>" />
													</s:if>
													<s:if test="image3 != null && image3 != ''">
														<img style="border: 1px solid #dcdcdc" width="60px"
															height="60px" src="picture/<s:property value='image3'/>" />
													</s:if>
												</div>
											</s:if>
										</div>
									</div>
								</s:if>
							</s:iterator>
						</div>

						<!-- 差评 -->
						<div class="part" style="display: none;">
							<s:iterator value="#request.productAppraise">
								<s:if test="grade<60">
									<div class="user_spring">
										<div class="user_name">
											<a
												href="javascript:goMemberHome('<s:property value="fromId"/>','<s:property value="fromRole"/>');"><img
												src="picture/<s:property value="fromImage" />"
												style="width: 60px; height: 60px;" /><span><s:property
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
											<s:if
												test="(image1!=null&&image1!='')||(image2!=null&&image2!='')||(image3!=null&&image3!='')">
												<span style="margin-left: 540px;">上传的图片:</span>
												<div style="margin-left: 620px; margin-top: -20px">
													<s:if test="image1 != null && image1 != ''">
														<img style="border: 1px solid #dcdcdc" width="60px"
															height="60px" src="picture/<s:property value='image1'/>" />
													</s:if>
													<s:if test="image2 != null && image2 != ''">
														<img style="border: 1px solid #dcdcdc" width="60px"
															height="60px" src="picture/<s:property value='image2'/>" />
													</s:if>
													<s:if test="image3 != null && image3 != ''">
														<img style="border: 1px solid #dcdcdc" width="60px"
															height="60px" src="picture/<s:property value='image3'/>" />
													</s:if>
												</div>
											</s:if>
										</div>
									</div>
								</s:if>
							</s:iterator>
						</div>

						<!-- 有晒单的评价-->
						<div class="part" style="display: none;">
							<s:iterator value="#request.productAppraise">
								<s:if
									test="(image1!=null&&image1!='')||(image2!=null&&image2!='')||(image3!=null&&image3!='')">
									<div class="user_spring">
										<div class="user_name">
											<a
												href="javascript:goMemberHome('<s:property value="fromId"/>','<s:property value="fromRole"/>');"><img
												src="picture/<s:property value="fromImage" />"
												style="width: 60px; height: 60px;" /><span><s:property
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
											<span style="margin-left: 540px;">上传的图片:</span>
											<div style="margin-left: 620px; margin-top: -20px">
												<s:if test="image1 != null && image1 != ''">
													<img style="border: 1px solid #dcdcdc" width="60px"
														height="60px" src="picture/<s:property value='image1'/>" />
												</s:if>
												<s:if test="image2 != null && image2 != ''">
													<img style="border: 1px solid #dcdcdc" width="60px"
														height="60px" src="picture/<s:property value='image2'/>" />
												</s:if>
												<s:if test="image3 != null && image3 != ''">
													<img style="border: 1px solid #dcdcdc" width="60px"
														height="60px" src="picture/<s:property value='image3'/>" />
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
		</s:form>
		<s:include value="/share/footer.jsp" />

	</div>
	<div id="dialog" title="发送消息">
		<form id="msgForm" name="msgForm" method="post"
			action="message!save.asp">
			<input type="hidden" name="msg.type" value="2" /> <input
				type="hidden" name="msg.parent" id="msgparent" />
			<div id="divId"></div>
			<p>
				<span>收件人：<select name="msg.memberTo.id" class="uidpwd" /></span>
			</p>
			<p class="message_content">
				<span>消息内容：</span> <span><textarea name="msg.content"
						class="send-message"
						onkeyup="this.value = this.value.substring(0, 140)"></textarea></span>
			</p>
			<p class="pa">
				<input type="button" value="确定" onclick="onSave();" class="butpa" />
				<input type="button" value="取消" onclick="onClosec()" class="butpa" />
			</p>
		</form>
	</div>
</body>
</html>