<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="keywords" content="健身,健身挑战,健身计划,健身计划表,哑铃健身计划,减肥计划表,力量训练,有氧训练,柔韧训练,增肌,运动" />
    <meta name="description" content="健身，每天给自己的体重定一个小目标，比如先减个5公斤" />
    <title>健身挑战</title>
    <s:include value="/share/meta.jsp"/>
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/base.css" rel="stylesheet">
    <link href="css/challenge.css" rel="stylesheet">
    <script src="js/slider.js"></script>
    <script src="js/challenge.js"></script>
    <script src="js/returnTop.js"></script>
    <script type="text/javascript">
    function onQuery(type, code, name) {
		//挑战目标
		if (type == "target") {
			jQuery("#target").val(code);
			jQuery("#targetName").val(name);
		}
		queryByPage(1);
	} 
    
    </script>
</head>
<body>
<s:include value="/newpages/head.jsp"></s:include>
<div class="top_bg"></div>
<div class="banner"></div>


    <div class="container">
    <s:form id="queryForm" name="queryForm" method="post"
			action="activelist!onQuery.asp" theme="simple">
			<s:hidden name="target" id="target" />
			<s:hidden name="targetName" id="targetName" />
			<s:hidden name="mode" id="mode" />
			<s:hidden name="modeName" id="modeName" />
			<s:hidden name="circle" id="circle" />
			<s:hidden name="circleName" id="circleName" />
        <div class="row">
            <div class="left col-md-2">
                <div class="go_challenge"><h5><span>发起挑战</span></h5></div>
                <div class="challenge_txt">
                    <p>您可以发起健身挑战帮助自己或他人实现健身目标。挑战者如果没有达到目标，保证金将捐给以下慈善机构</p>
                    <span><a href="product.asp?type=3" style="color: white">GO</a></span>
                </div>
                <div class="charitable">
                    <p><span>合作慈善机构</span></p>
                </div>
                <div class="left_two">
                <s:iterator value="#request.institutions">
                    <img src="picture/<s:property value="icon"/>">
                    <p><s:property value="rName"/></p>
                </s:iterator>
                </div>
                <h5 class="about_plan">挑战者</h5>
              <div class="plan_list">
              <s:iterator value="#request.challenging" status="st">
              <s:if test="(#st.index<4)">
                    <div class="list">
                        <a href="javascript:goMemberHome('<s:property value="memberId"/>','<s:property value="memberRole"/>');">
                        <img src="<s:if test="image == '' || image == null">images/userpho.jpg</s:if><s:else>picture/<s:property value="image"/></s:else>"/></a>
                        <p><s:property value="memberName"/></p>
                        <h5><s:property value="activeName"/></h5>
                        <h5><s:property value="days"/>天内
	                        <s:if test="category==\"A\"">体重增加<s:property value="value"/>KG</s:if>
							<s:elseif test="category==\"B\"">体重减少<s:property value="value"/>KG</s:elseif>
							<s:elseif test="category==\"C\"">体重保持在<s:property value="value"/>%左右</s:elseif>
							<s:elseif test="category==\"D\"">运动<s:property value="value"/>次</s:elseif>
							<s:elseif test="category==\"E\"">运动<s:property value="value"/>小时</s:elseif>
							<s:elseif test="category==\"F\"">每周运动<s:property value="value"/>次</s:elseif>
							<s:elseif test="category==\"G\""><s:if test="action == null"></s:if><s:else><s:property value="action"/></s:else><s:property value="value"/>千米</s:elseif>
							<s:elseif test="category==\"H\"">力量运动负荷<s:property value="value"/>KG</s:elseif>
                        </h5>
                    </div>
              </s:if>      
              </s:iterator>      
                </div>
            </div>
            <div class="right col-md-10">
                <div class="row">
                    <div class="col-md-8 col-lg-9">
                        <div class="flexslider" id="play">
                            <ul class="slides">
                            <s:iterator value="#request.slides" status="st">
                            <s:if test="(#st.index<4)">
                                <li><a href="<s:property value="link"/>"><img src="picture/<s:property value="icon"/>"> </a> </li>
                            </s:if>
                            </s:iterator>
                            </ul>
                        </div>
                    </div>
                    <div class="col-md-4 col-lg-3">
                        <div class="online"><h5><span>在线数据</span></h5></div>
                        <div class="online_sj">
                            <div class="row">
                                <div class="col-md-6">
                                    <img src="images/icon_1_08.png">
                                    <p>保证金总额</p>
                                    <p>￥<span><s:property value="#request.money"/></span></p>
                                </div>
                                <div class="col-md-6">
                                    <img src="images/icon_2_11.png">
                                    <p>体重变化</p>
                                    <p><span><s:property value="#request.modeA"/>公斤</span></p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <img src="images/icon_4_20.png">
                                    <p>运动时间</p>
                                    <p><span><s:property value="#request.modeD"/>小时</span></p>
                                </div>
                                <div class="col-md-6">
                                    <img src="images/icon_5_23.png">
                                    <p>运动次数</p>
                                    <p><span><s:property value="#request.modeC"/>次</span></p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                        <div id="right-2" style="border:none">
							<s:include value="/home/activelist_middle.jsp" />
						</div>
            </div>
        </div>
        </s:form>
    </div>
<s:include value="/newpages/footer.jsp"></s:include>
</body>
</html>