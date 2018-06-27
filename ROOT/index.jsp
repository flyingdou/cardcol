<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
   <meta name="keywords" content="健身E卡通 -专家介绍—下载APP" />
      <meta name="description" content="健身E卡通 -专家介绍—下载APP" />
<title>健身E卡通</title>
<link type="text/css"  href="css/bootstrap.css" rel="stylesheet">
<link type="text/css"  href="css/base.css" rel="stylesheet">
<link type="text/css"  href="css/index.css" rel="stylesheet">
<script type="text/javascript" src="js/jquery-2.2.0.min.js"></script>
<script type="text/javascript" src="js/slider.js"></script>
<script type="text/javascript" src="js/index.js"></script>
<script type="text/javascript" src="js/returnTop.js"></script>
<script type="text/javascript">
function queryGoodsInfo(goodsId) {
	$('#sportsForm').attr("action", 'goods.asp?goodsId=' + goodsId);
	$('#sportsForm').submit();
}

</script>
</head>
<body>
<s:include value="/newpages/head.jsp"></s:include>
	<!--滚动区-->
	<div id="banner">
		<div class="flexslider" id="demo">
			<ul class="slides">
				<s:iterator value="#request.slides">
					<li><a href="<s:property value='link'/>"><img
							src="picture/<s:property value='icon'/>"></a></li>
				</s:iterator>
			</ul>
		</div>

	</div>

	<!--热门区-->
	<div class="container">
		
			<div class="hot row">
					<div class="col-md-4 ">
						<a href="javascript:queryGoodsInfo('<s:property value="#request.goodsList[0].id"/>');"><img
							src="picture/wangyan-h.jpg"></a>
						<div class="hot_txt">
							<p>
<%-- 								<s:if test="summary.length()>30"> --%>
<%-- 									<s:property value="summary.substring(0,30)" />...</s:if> --%>
<%-- 								<s:elseif test="summary.length()==0"> --%>
<!-- 									<br> -->
<%-- 								</s:elseif> --%>
<%-- 								<s:else> --%>
<%-- 									<s:property value="summary" /> --%>
<%-- 								</s:else> --%>
<!--                            王严老师是国内屈指可数的同时活跃在健身健美运动一线和教学理论 研究领域的专家。自己先后获得过5次北京健美冠 -->
									王严老师是活跃在健身健美运动一线和理论教学研究领域的专家。自己先后获得过5次北京健美冠军，培训了多位全国、亚洲和世界健美冠军。本系统根据王严老师多年研究、实践成果开发。
						</div>
					</div>
					<div class="col-md-4 ">
						<a href="javascript:queryGoodsInfo('<s:property value="#request.goodsList[1].id"/>');"><img
							src="picture/bracelet-h.jpg"></a>
						<div class="hot_txt">
							<p>
<%-- 								<s:if test="summary.length()>30"> --%>
<%-- 									<s:property value="summary.substring(0,30)" />...</s:if> --%>
<%-- 								<s:elseif test="summary.length()==0"> --%>
<!-- 									<br> -->
<%-- 								</s:elseif> --%>
<%-- 								<s:else> --%>
<%-- 									<s:property value="summary" /> --%>
<%-- 								</s:else> --%>
<!--                            卡库健身手环 -->
									卡库健身手环专为健身而生。与您的健身计划密切结合，运动状态下可以准确测量心率。运动时无需带手机，手环自动采集、上传数据
							</p>
						</div>
					</div>
					<div class="col-md-4 ">
						<a href="download1.jsp"><img
							src="picture/download-h.jpg"></a>
						<div class="hot_txt">
							<p>
<%-- 								<s:if test="summary.length()>30"> --%>
<%-- 									<s:property value="summary.substring(0,30)" />...</s:if> --%>
<%-- 								<s:elseif test="summary.length()==0"> --%>
<!-- 									<br> -->
<%-- 								</s:elseif> --%>
<%-- 								<s:else> --%>
<%-- 									<s:property value="summary" /> --%>
<%-- 								</s:else> --%>
                           下载卡库健身APP，体验更便捷的健身教练服务，获得个性化的健身方案。在卡库中发起、参加健身挑战，可以为您带来持续健身动力！
							</p>
						</div>
					</div>
			</div>
		
	</div>

	<!--下载区-->
	<!-- 
    <div class="container">
            <div class="load clearfix">
                <div class=" shouji"><img src="images/shouji1_03.png"></div>
                <div class="App ">
                    <div class="App_top">
                        <div class="App_text">
                            <h4>扫码下载卡库健身APP</h4>
                            <p>卡库健身 —— 专业的健身计划私人订制平台。您可以通过专家系统、智能引擎或教练在线服务等多种方式获得完全个性化的健身方案。</p>
                        </div>
                        <img src="images/erweima_03.png">
                    </div>
                    <ul class="clearfix">
                        <li><a href="download1.jsp"><img src="images/store1_06.png"> </a> </li>
                        <li><a href="download1.jsp"><img src="images/store_08.png"> </a> </li>
                    </ul>
                    <p class="adv">请到苹果应用商店或各安卓应用市场搜索<br>“卡库健身”</p>
                </div>
                <div class="test">
                        <div class="health">
                            <h3>健康测试</h3>
                        </div>
                        <hr>
                        <form>
                            输入你的身高：<input type="text" class="heighe"> CM<br>
                            输入你的身高：<input type="text" class="weight"> KG
                        </form>
                        <div class="enter"><a href="#">测试</a></div>
                    </div>
        </div>
    </div>
 -->
	<!--健身明星-->
	<div class="container">
		<div id="star">
			<div class="title clearfix">
				<div class="col-md-12">
					<h3>网络明星私教</h3>
					<span>在 线 私 人 订 制 健 身 方 案</span>
				</div>
			</div>
			<div class="row">
				<div class="star_pic">
					<s:iterator value="#request.notices" status="start">
						<s:if test="(#start.index<4)">
							<div class="pic col-md-3">
								<a href="<s:property value="link"/>"><img
									src="<s:if test="icon == null || icon == \"\"">images/img-defaul.jpg</s:if><s:else>picture/<s:property value="icon"/></s:else>"></a>
							</div>
						</s:if>
					</s:iterator>
				</div>
			</div>
		</div>
	</div>

	<!--健身挑战-->
	<div class="container">
		<div id="challenge">
			<div class="challenge_title">
				<h5>健身挑战</h5>
				<div class="line">
					<div class="line_inner"></div>
				</div>
				<p>设 定 健 身 目 标 , 激 发 运 动 热 情</p>
			</div>
			<div class="dome row">
				<s:iterator value="#request.activeList" status="start">
					<a href="<s:property value="link"/>"> <s:if
							test="(#start.index<4)">
							<div class="dome_pic col-lg-3 col-md-6">
								<img
									src="<s:if test="icon != null">picture/<s:property value="icon" /></s:if><s:else>images/img-defaul.jpg</s:else>">
								<h6>
									<s:property value="rName" />
								</h6>
								<p>
									目标：
									<s:if test="catagory==\"A\"">
										<s:property value="days" />天内体重增加<s:property value="value" />KG</s:if>
									<s:elseif test="catagory == \"B\"">
										<s:property value="days" />天内体重减少<s:property value="value" />KG</s:elseif>
									<s:elseif test="catagory == \"E\"">
										<s:property value="days" />天内运动<s:property value="value" />小时</s:elseif>
									<s:elseif test="catagory == \"D\"">
										<s:property value="days" />天内运动<s:property value="value" />次</s:elseif>
									<s:elseif test="target == \"G\"">
										<s:property value="content" />&nbsp;<s:property
											value="customTareget" />&nbsp;<s:property value="unit" />
									</s:elseif>
								</p>
								<p>
									奖励：
									<s:property value="award" />
								</p>
							</div>
						</s:if>
					</a>
				</s:iterator>
			</div>
		</div>
	</div>
<!--  热门计划-->
	<div class="container">
		<div class="title clearfix">
			<div class="col-md-12">
				<h3>热门健身计划</h3>
				<span>减脂、增肌、康复，总有一款适合你</span>
			</div>
		</div>

		<!--健美增肌-->
		<div class="row">
		<div class="jianmei">
			<div class="floor clearfix col-md-12">
				<span>1F</span>
				<h5>健美增肌</h5>
				<a href="planlist.asp">更多>></a>
			</div>
			<div class="row">
				<div class="col-md-3">
					<img
						src="<s:if test="#request.planRecommends[0].icon != null">picture/<s:property value="#request.planRecommends[0].icon" /></s:if><s:else>images/index3_03.gif</s:else>">
					<div class="plan_txt">
						<p>
							<s:property value="#request.planRecommends[0].summary"
								escape="false" />
						</p>
					</div>
					<div class="find">
						<a href="<s:property value="#request.planRecommends[0].link"/>">查看详情</a>
					</div>
				</div>
				<div class="col-md-3">
					<img
						src="<s:if test="#request.planRecommends[1].icon != null">picture/<s:property value="#request.planRecommends[1].icon" /></s:if><s:else>images/index3_03.gif</s:else>">
					<div class="plan_txt">
						<p>
							<s:property value="#request.planRecommends[1].summary"
								escape="false" />
						</p>
					</div>
					<div class="find">
						<a href="<s:property value="#request.planRecommends[1].link"/>">查看详情</a>
					</div>
				</div>
				<div class="col-md-3">
					<img
						src="<s:if test="#request.planRecommends[2].icon != null">picture/<s:property value="#request.planRecommends[2].icon" /></s:if><s:else>images/index3_03.gif</s:else>">
					<div class="plan_txt">
						<p>
							<s:property value="#request.planRecommends[2].summary"
								escape="false" />
						</p>
					</div>
					<div class="find">
						<a href="<s:property value="#request.planRecommends[2].link"/>">查看详情</a>
					</div>
				</div>
				<div class="col-md-3">
					<img
						src="<s:if test="#request.planRecommends[3].icon != null">picture/<s:property value="#request.planRecommends[3].icon" /></s:if><s:else>images/index3_03.gif</s:else>">
					<div class="plan_txt">
						<p>
							<s:property value="#request.planRecommends[3].summary"
								escape="false" />
						</p>
					</div>
					<div class="find">
						<a href="<s:property value="#request.planRecommends[3].link"/>">查看详情</a>
					</div>
				</div>
			</div>
		</div>
    </div>
		<!--瘦身减重-->
		<div class="row">
		<div class="jianmei">
			<div class="floor clearfix col-md-12">
				<span>2F</span>
				<h5>瘦身减重</h5>
				<a href="planlist.asp">更多>></a>
			</div>
			<div class=" row ">
				<div class="shoushen clearfix">
					<div class="col-md-3 col-lg-3">
						<a href="<s:property value="#request.planRecommends[0].link"/>">
						<img
							src="<s:if test="#request.cardList[0].icon != null">picture/<s:property value="#request.cardList[0].icon" /></s:if><s:else>images/index3_03.gif</s:else>"
							class="left"></a>
						<div class="ran">
                             <h6><s:property value="#request.cardList[0].title"
										escape="false" /></h6>
							<p style="color:#fff;"><br></p>
                         </div>	
					</div>
					<div class="col-md-6 col-lg-6">
						<div class="row">
							<div class="middle col-md-6">
								<p>
									<s:property value="#request.cardList[1].title"
										escape="false" />
									<br>
								</p>
								<a href="<s:property value="#request.planRecommends[1].link"/>"><img
									src="<s:if test="#request.cardList[1].icon != null">picture/<s:property value="#request.cardList[1].icon" /></s:if><s:else>images/index3_03.gif</s:else>"></a>
							</div>
							<div class="middle col-md-6">
								<p>
									<s:property value="#request.cardList[2].title"
										escape="false" />
									<br>
								</p>
								<a href="<s:property value="#request.planRecommends[2].link"/>"><img
									src="<s:if test="#request.cardList[2].icon != null">picture/<s:property value="#request.cardList[2].icon" /></s:if><s:else>images/index3_03.gif</s:else>"></a>
							</div>
						</div>
						<div class="row">
							<div class="middle col-md-6">
								<p>
									<s:property value="#request.cardList[3].title"
										escape="false" />
									<br>
								</p>
								<a href="<s:property value="#request.planRecommends[3].link"/>"><img
									src="<s:if test="#request  .cardList[3].icon != null">picture/<s:property value="#request.cardList[3].icon" /></s:if><s:else>images/index3_03.gif</s:else>"></a>
							</div>
							<div class="middle col-md-6">
								<p>
									<s:property value="#request.cardList[4].title"
										escape="false" />
									<br>
								</p>
								<a href="<s:property value="#request.planRecommends[4].link"/>"><img
									src="<s:if test="#request.cardList[4].icon != null">picture/<s:property value="#request.cardList[4].icon" /></s:if><s:else>images/index3_03.gif</s:else>"></a>
							</div>
						</div>
					</div>
					<div class="col-md-3 col-lg-3">
						<div class="right">
							<div class="bg_color"></div>
							<p>
								<s:property value="#request.cardList[5].title"
									escape="false" />
								<br>
							</p>
							<a href="<s:property value="#request.planRecommends[5].link"/>"><img
								src="<s:if test="#request.cardList[5].icon != null">picture/<s:property value="#request.cardList[5].icon" /></s:if><s:else>images/index3_03.gif</s:else>"></a>
						</div>
						<div class="right">
							<div class="bg_color"></div>
							<p>
								<s:property value="#request.cardList[6].title"
									escape="false" />
								<br>
							</p>
							<a href="<s:property value="#request.planRecommends[6].link"/>"><img
								src="<s:if test="#request.cardList[6].icon != null">picture/<s:property value="#request.cardList[6].icon" /></s:if><s:else>images/index3_03.gif</s:else>"></a>
						</div>
						<div class="right">
							<div class="bg_color"></div>
							<p>
								<s:property value="#request.cardList[7].title"
									escape="false" />
								<br>
							</p>
							<a href="<s:property value="#request.planRecommends[7].link"/>"><img
								src="<s:if test="#request.cardList[7].icon != null">picture/<s:property value="#request.cardList[7].icon" /></s:if><s:else>images/index3_03.gif</s:else>"></a>
						</div>
					</div>
				</div>
			</div>
			</div>
			</div>
			
			<!--运动康复-->
			<div class="row">
			<div class="jianmei">
				<div class="floor clearfix col-md-12">
					<span>3F</span>
					<h5>运动康复/提高运动表现</h5>
					<a href="planlist.asp">更多>></a>
				</div>
				<div class="row">
					<div class="col-md-3">
						<img
							src="<s:if test="#request.factoryRecommends[0].icon != null">picture/<s:property value="#request.factoryRecommends[0].icon" /></s:if><s:else>images/index3_03.gif</s:else>">
						<div class="plan_txt">
							<p> 
								<s:property value="#request.factoryRecommends[0].summary" />
							</p>
						</div>
						<div class="find">
							<a href="<s:property value="#request.factoryRecommends[0].link"/>">查看详情</a>
						</div>
					</div>
					<div class="col-md-3">
						<img
							src="<s:if test="#request.factoryRecommends[1].icon != null">picture/<s:property value="#request.factoryRecommends[1].icon" /></s:if><s:else>images/index3_03.gif</s:else>">
						<div class="plan_txt">
							<p>
								<s:property value="#request.factoryRecommends[1].summary" />
							</p>
						</div>
						<div class="find">
							<a href="<s:property value="#request.factoryRecommends[1].link"/>">查看详情</a>
						</div>
					</div>
					<div class="col-md-3">
						<img
							src="<s:if test="#request.factoryRecommends[2].icon != null">picture/<s:property value="#request.factoryRecommends[2].icon" /></s:if><s:else>images/index3_03.gif</s:else>">
						<div class="plan_txt">
							<p>
								<s:property value="#request.factoryRecommends[2].summary" />
							</p>
						</div>
						<div class="find">
							<a href="<s:property value="#request.factoryRecommends[3].link"/>">查看详情</a>
						</div>
					</div>
					<div class="col-md-3">
						<img
							src="<s:if test="#request.factoryRecommends[3].icon != null">picture/<s:property value="#request.factoryRecommends[3].icon" /></s:if><s:else>images/index3_03.gif</s:else>">
						<div class="plan_txt">
							<p>
								<s:property value="#request.factoryRecommends[3].summary" />
							</p>
						</div>
						<div class="find">
							<a href="<s:property value="#request.factoryRecommends[3].link"/>">查看详情</a>
						</div>
					</div>
				</div>
				</div>
			</div>
		</div>
		
		<!--健身达人榜-->
		<div class="container">
			<div class="title clearfix">
				<div class="col-md-12">
					<h3>健身达人榜</h3>
					<span>榜 样 的 力 量 是 无 穷 的</span>
				</div>
			</div>
		</div>
		<div class="list">
			<div class="container">
				<div class="top row">
					<s:iterator value="#request.doyenList" status="st">
						<s:if test="(#st.index<12)">
							<div class="top_pic col-md-1">
								<a
									href="javascript:goMemberHome('<s:property value="partake"/>','<s:property value="role"/>');"><img
									src="picture/<s:property value="image"/>"> </a>
								<p>
									<s:property value="name" />
								</p>
								<span>已健身<s:property value="workoutTimes" />次
								</span>
							</div>
						</s:if>
					</s:iterator>
				</div>
				<div class="row list_txt">
					<div class="col-md-4 col-md-offset-1">
						<p>
							<s:property value="#request.readers[0].summary" />
						</p>
					</div>
					<div class="col-md-4 col-md-offset-2">
						<p>
							<s:property value="#request.readers[1].summary" />
						</p>
					</div>
				</div>
				<div class="bot row">
					<div class="col-md-6">
						<a href="<s:property value='#request.readers[0].link'/>">
						<img
							src="<s:if test="#request.readers[0].icon != null">picture/<s:property value="#request.readers[0].icon" /></s:if><s:else>images/index3_03.gif</s:else>"></a>
						<p>
							<s:property value="#request.readers[0].title" />
							<br>
						</p>
					</div>
					<div class="col-md-6">
						<a href="<s:property value='#request.readers[1].link'/>">
						<img
							src="<s:if test="#request.readers[1].icon != null">picture/<s:property value="#request.readers[1].icon" /></s:if><s:else>images/index3_03.gif</s:else>"></a>
						<p>
							<s:property value="#request.readers[1].title" />
							<br>
						</p>
					</div>
				</div>
			</div>
		</div>
		<s:form id="sportsForm" name="sportsForm" method="post"
			action="index.asp" theme="simple">
			<input type="hidden" name="typeId" id="typeId" />
			<input type="hidden" name="typeName" id="typeName" />
		</s:form>
		<s:include value="/newpages/footer.jsp"></s:include>
</body>
</html>