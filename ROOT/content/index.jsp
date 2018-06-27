<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns:wb="http://open.weibo.com/wb">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="健身E卡通客户服务中心" />
<meta name="description" content="健身E卡通客户服务中心" />
<title>健身E卡通客户服务中心</title>
<link rel="stylesheet" type="text/css" href="css/service-center.css" />
<link rel="stylesheet" type="text/css" href="css/public.css" />
<link rel="stylesheet" type="text/css" href="css/pulic-1.css" />
<script type="text/javascript" src="script/service.js"></script>
<script src="http://tjs.sjs.sinajs.cn/open/api/js/wb.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
function goComplaint(type){
	if("<s:property value="#session.loginMember.id"/>" == ""){
		openLogin();
		return;
	}
	window.location.href = "complaint!goComplaint.asp?type="+type;
}
</script>
</head>
<body>
	<!--<div id="header">header</div>-->
<div id="top">
	<div id="top-content">
		<p class="left">
			<s:if test="#session.loginMember != null">
				<a href="account.asp"><s:property value="#session.loginMember.name"/></a>　欢迎来到健身E卡通<a href="login!logout.asp" id="coloa">退出</a></s:if>
			<s:else>
				欢迎来到健身E卡通<a href="login.asp" id="coloa">请登录</a><span>|</span><a href="register.asp" id="coloa">注册</a>
			</s:else>
		</p>
                <p class="right">
			<a href="index.asp" id="coloa" target="_parent">健身E卡通首页</a>
			<span>|</span><a href="login.asp" id="coloa" target="_parent">我的健身E卡通</a>
			<span>|</span><a href="message.asp" id="coloa" target="_parent">消息(<b class="msgb">0</b>)</a>
			<span>|</span><a href="shop!queryShopping.asp" id="coloa" target="_parent"><b class="aimgb">购物车</b><b class="ainsp">0</b>件</a>
			<span>|</span><a href="service.asp" id="coloa" target="_parent">服务中心</a>
			<span>|</span><a href="index.asp" id="coloa" target="_parent">网站导航</a>
		</p>
	</div>
</div>
<div id="header">
	<div id="logo">
		<blockquote>
			<p>
				<a href="index.asp"><img src="images/logo.png" /></a>
			</p>
		</blockquote>
	</div>
	<div id="switch">
		<span class="city"><s:property value="#session.currentCity"/></span><a href="city.asp" id="coloa">[切换城市]</a>
	</div>
	<div id="ad">
		<img src="images/ad.png" />
	</div>
</div>
        <div id="navlogo">
	<div id="nav-content">
		<ul>
			<s:iterator value="#request.channels">
			<li><a <s:if test="channel == id">class="active"</s:if> href="<s:if test="id==1">service.asp</s:if><s:else>detail.asp?channel=<s:property value="id"/></s:else>"><s:property value="name"/></a></li>
			</s:iterator>
		</ul>
	</div>
	</div>
	<div class="main">
		<div class="main-left">
			<!--js图片上下滚动-->
			<div id="fader">
				<ul>
					<s:iterator value="#request.mainBannel">
					<li><s:if test="link==null || link==\"\"">
						<a href="detail.asp?channel=<s:property value="sectorId"/>&id=<s:property value="recomm_id"/>"><img src="picture/<s:property value="icon"/>" alt="<s:property value="title"/>" /></a>
						</s:if>
						<s:else>
						<a href="<s:property value="link"/>"><img src="picture/<s:property value="icon"/>" alt="<s:property value="title"/>" /></a>
						</s:else>
					</li>
					</s:iterator>
				</ul>
			</div>
			<script type="text/javascript">
				var fader = new Hongru.fader.init('fader',{id:'fader'});
			</script>
			<!--js图片上下滚动-->
		</div>
		<div class="main-center">
			<div style="width:415px; height:220px;margin-top:45px;">
				<s:iterator value="#request.focus" status="st">
				<div class="main-center1">
						<p class="main-centerp1">
							<b><s:property value="title"/></b>
						</p>
						<p class="main-centerp2">
							<s:if test="link==null || link == \"\"">
							<a href="detail.asp?channel=<s:property value="sectorId"/>&id=<s:property value="recomm_id"/>"><s:property value="summary"/></a>
							</s:if>
							<s:else>
							<a href="<s:property value="link"/>"><s:property value="summary"/></a>
							</s:else>
						</p>
				  </div>
				</s:iterator>
			</div>
			
		</div>
		<div class="main-right">
			<h4>客户服务中心</h4>
			<a href="javascript:goComplaint('1');"><div class="main-right1"></div></a> 
			<a href="javascript:goComplaint('2');"><div class="main-right2"></div></a>
			<div class="main-right3">
				<div class="main-right3-1">
					<li><span class="red"><s:property value="#session.systemConfig.serviceTell"/></span></li>
					<li><span class="red"><s:property value="#session.systemConfig.qq"/></span></li>
					<li><span class="red">健身E卡通官方微博</span><wb:follow-button uid="1112837783" type="red_1" width="67" height="24" ></wb:follow-button></li>
				</div>
				<div class="main-right3-2">
					<a href="#"></a><!--加关注-->
				</div>
			</div>
		</div>
	</div>
	<div class="cent">
		<div class="cent-left">
			<div class="cent-left-1">
				<p class="cent-left-1p1">
					<b>健身E卡通播报</b><span><a href="detail.asp?channel=7">更多>></a></span>
				</p>
				<p class="cent-div1p"><s:property value="#request.news.get(0).title"/></p>
				<div class="cent-left-1div">
					<img src="picture/<s:property value="#request.news.get(0).icon"/>" />
					<p>
						<s:if test="link == null || linke == \"\"">
						<a href="detail.asp?channel=<s:property value="#request.news.get(0).sectorId"/>&id=<s:property value="#request.news.get(0).recomm_id"/>"><s:property value="#request.news.get(0).summary"/></a>
						</s:if>
						<s:else>
						<a href="<s:property value="#request.news.get(0).link"/>"><s:property value="#request.news.get(0).summary"/></a>
						</s:else>
					</p>
				</div>
				<div class="cent-left-1-1">
					<ul>
						<s:iterator value="#request.news" status="st">
						<s:if test="#st.index > 0">
						<s:if test="link == null || link == \"\"">
						<li><a href="detail.asp?channel=<s:property value="#request.news.get(0).sectorId"/>&id=<s:property value="recomm_id"/>"><s:property value="title"/></a></li>
						</s:if>
						<s:else>
						<li><a href="<s:property value="link"/>"><s:property value="title"/></a></li>
						</s:else>
						</s:if>
						</s:iterator>
					</ul>
				</div>
			</div>
			<div class="cent-left-2">
				<iframe width="273" height="411" frameborder="0" scrolling="no"
					src="http://widget.weibo.com/distribution/comments.php?language=zh_cn&width=273&height=411&skin=1&dpc=1&url=http://open.weibo.com/widget/comments.php&titlebar=1&border=1&appkey=&dpc=1"></iframe>
			</div>
		</div>
		<div class="cent-right">
			<div class="cent-right-1">
				<s:iterator value="#request.mBannel">
				<s:if test="link ==null || link == \"\"">
				<a href="detail.asp?channel=<s:property value="sectorId"/>&id=<s:property value="recomm_id"/>"><img src="picture/<s:property value="icon"/>" /></a>
				</s:if>
				<s:else>
				<a href="<s:property value="link"/>"><img src="picture/<s:property value="icon"/>" /></a>
				</s:else>
				</s:iterator>
			</div>
			<div class="cent-right-2">
				<div class="cent-right-2-1">
					<div class="cent-right-2-1-1">
						<p class="cent-right-2-1p">
							<b>健身会员</b><span><a href="detail.asp?channel=4">更多>></a></span>
						</p>
						<div class="cent-div1">
							<p class="cent-div1p"><s:property value="#request.members.get(0).title"/></p>
							<div>
								<img src="picture/<s:property value="#request.members.get(0).icon"/>" />
								<p class="pp">
									<s:if test="#request.members.get(0).link == null || #request.members.get(0).link == \"\"">
									<a href="detail.asp?channel=<s:property value="#request.members.get(0).sectorId"/>&id=<s:property value="#request.members.get(0).recomm_id"/>"><s:property value="#request.members.get(0).summary"/></a>
									</s:if>
									<s:else>
									<a href="<s:property value="#request.members.get(0).link"/>"><s:property value="#request.members.get(0).summary"/></a>
									</s:else>
								</p>
							</div>
						</div>
						<div class="cent-div2">
							<ul>
								<s:iterator value="#request.members" status="st">
								<s:if test="#st.index > 0">
								<s:if test="link == null || link == \"\"">
								<li><a href="detail.asp?channel=<s:property value="sectorId"/>&id=<s:property value="recomm_id"/>"><s:property value="title"/></a></li>
								</s:if>
								<s:else>
								<li><a href="<s:property value="link"/>"><s:property value="title"/></a></li>
								</s:else>
								</s:if>
								</s:iterator>
							</ul>
						</div>
					</div>
					<div class="cent-right-2-1-2">
						<p class="cent-right-2-1p1">
							<b>俱乐部</b>&nbsp;&nbsp;<span><a href="detail.asp?channel=2">更多>></a></span>
						</p>
						<div class="cent-div1">
							<p class="cent-div1p"><s:property value="#request.clubs.get(0).title"/></p>
							<div>
								<img src="picture/<s:property value="#request.clubs.get(0).icon"/>" />
								<p class="pp">
									<s:if test="#request.clubs.get(0).link == null || #request.clubs.get(0).link == \"\"">
									<a href="detail.asp?channel=<s:property value="#request.clubs.get(0).sectorId"/>&id=<s:property value="#request.clubs.get(0).recomm_id"/>"><s:property value="#request.clubs.get(0).summary"/></a>
									</s:if>
									<s:else>
									<a href="<s:property value="link"/>"><s:property value="#request.clubs.get(0).summary"/></a>
									</s:else>
								</p>
							</div>
						</div>
						<div class="cent-div2">
							<ul>
								<s:iterator value="#request.clubs" status="st">
								<s:if test="#st.index > 0">
								<s:if test="link == null || link == \"\"">
								<li><a href="detail.asp?channel=<s:property value="sectorId"/>&id=<s:property value="recomm_id"/>"><s:property value="title"/></a></li>
								</s:if>
								<s:else>
								<li><a href="<s:property value="link"/>"><s:property value="title"/></a></li>
								</s:else>
								</s:if>
								</s:iterator>
							</ul>
						</div>
					</div>
					<div class="cent-right-2-1-2">
						<p class="cent-right-2-1p">
							<b>健身教练</b><span><a href="detail.asp?channel=3">更多>></a></span>
						</p>
						<div class="cent-div1">
							<p class="cent-div1p"><s:property value="#request.coachs.get(0).title"/></p>
							<div>
								<img src="picture/<s:property value="#request.coachs.get(0).icon"/>" />
								<p class="pp">
									<s:if test="#request.coachs.get(0).link == null || #request.coachs.get(0).link == \"\"">
									<a href="detail.asp?channel=<s:property value="#request.coachs.get(0).sectorId"/>&id=<s:property value="#request.coachs.get(0).recomm_id"/>"><s:property value="#request.coachs.get(0).summary"/></a>
									</s:if>
									<s:else>
									<a href="<s:property value="#request.coachs.get(0).link"/>"><s:property value="#request.coachs.get(0).summary"/></a>
									</s:else>
								</p>
							</div>
						</div>
						<div class="cent-div2">
							<ul>
								<s:iterator value="#request.coachs" status="st">
								<s:if test="#st.index > 0">
								<s:if test="link == null || link == \"\"">
								<li><a href="detail.asp?channel=<s:property value="sectorId"/>&id=<s:property value="recomm_id"/>"><s:property value="title"/></a></li>
								</s:if>
								<s:else>
								<li><a href="<s:property value="link"/>"><s:property value="title"/></a></li>
								</s:else>
								</s:if>
								</s:iterator>
							</ul>
						</div>
					</div>
				</div>
				<div class="cent-right-2-2">
					<div class="cent-right-2-2-1">
						<p class="cent-right-2-2p">
							<b>交易安全</b><span><a href="detail.asp?channel=5">更多>></a></span>
						</p>
						<div class="cent-divrig1">
							<ul>
								<s:iterator value="#request.sectorys" status="st">
								<li> <span style="float:left"><b>></b></span><a href="<s:if test="link == null || link == \"\"">detail.asp?channel=<s:property value="sectorId"/>&id=<s:property value="recomm_id"/></s:if><s:else><s:property value="link"/></s:else>"><s:property value="title"/></a></li>
								</s:iterator>
							</ul>
						</div>
					</div>
					<div class="cent-right-2-2-2">
						<p class="cent-right-2-2p">
							<b>健身E卡通规则</b><span><a href="detail.asp?channel=6">更多>></a></span>
							</h4>
							<div class="cent-divrig1">
								<ul>
									<s:iterator value="#request.rules" status="st">
									<li><span style="float:left">⊕</span><a href="<s:if test="link == null || link == \"\"">detail.asp?channel=<s:property value="sectorId"/>&id=<s:property value="recomm_id"/></s:if><s:else><s:property value="link"/></s:else>"> <s:property value="title"/></a></li>
									</s:iterator>
								</ul>
							</div>
					</div>
					<div class="cent-right-2-2-3">
						<s:iterator value="#request.rBannel">
						<s:if test="link == null || link == \"\"">
						<a href="detail.asp?channel=<s:property value="sectorId"/>&id=<s:property value="recomm_id"/>"><img src="picture/<s:property value="icon"/>" /></a>
						</s:if>
						<s:else>
						<a href="<s:property value="link"/>"><img src="picture/<s:property value="icon"/>" /></a>
						</s:else>
						</s:iterator>
					</div>
				</div>
			</div>
		</div>
	</div>
	<s:include value="/share/footer.jsp"/>
</body>
</html>
