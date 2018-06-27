<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="keywords" content="健身E卡通,健身计划" />
<meta name="description" content="健身E卡通，健身计划" />
    <title>健身计划</title>
    <s:include value="/share/meta.jsp"></s:include>
    <link type="text/css" href="css/bootstrap.css" rel="stylesheet">
    <link type="text/css" href="css/base.css" rel="stylesheet">
    <link type="text/css" href="css/plan.css" rel="stylesheet">
    <script type="text/javascript" src="js/slider.js"></script>
    <script type="text/javascript" src="js/jquery.page.js"></script>
  
    <script type="text/javascript" src="js/plan.js"></script>
    <script type="text/javascript" src="js/returnTop.js"></script>
    <script type="text/javascript">
     function onQuery() {
		queryByPage(1);
	}

	function onPlanQuery(type, code, name) {
		//计划类型
		if (type == "taget") {
			jQuery("#target").val(code);
			jQuery("#targetName").val(name);
		}
		//适用对象
		if (type == "applyObject") {
			jQuery("#applyObject").val(code);
			jQuery("#applyObjectName").val(name);
		}
		//适用场景
		if (type == "scene") {
			jQuery("#scene").val(code);
			jQuery("#sceneName").val(name);
		}
		//计划周期
		if (type == "plancircle") {
			jQuery("#plancircle").val(code);
			jQuery("#plancircleName").val(name);
		}
		onQuery();
	}
	//排序
	function onOrderQuery(idObj, idVal) {
		jQuery("#" + idObj).val(idVal);
		onQuery();
	}

	window.onload = function() {
		$(".plan_wenzi").each(function(index) {
			var pHtml = $(this).html();
			if (pHtml.length > 30) {
				pHtml = pHtml.substring(0, 100) + "...";
				$(this).html(pHtml);
			}
		});
	}

	function onCancelQuery() {
		jQuery("#target").val("");
		jQuery("#applyObject").val("");
		jQuery("#scene").val("");
		jQuery("#plancircle").val("");
		jQuery("#targetName").val("");
		jQuery("#applyObjectName").val("");
		jQuery("#sceneName").val("");
		jQuery("#plancircleName").val("");
		onQuery();
	}
	

    </script>
   
</head>
<body>
<s:include value="/newpages/head.jsp"></s:include>

<div class="top_bg"></div>
<!--内容区-->
    <div class="container">
     <s:form id="queryForm" name="queryForm" method="post"
					action="planlist!onQuery.asp" theme="simple">
					<s:hidden name="pid" id="pid" />
					<s:hidden name="typeId" id="typeId" />
					<s:hidden name="typeName" id="typeName" />
					<s:hidden name="area" id="area" />
					<s:hidden name="target" id="target" />
					<s:hidden name="applyObject" id="applyObject" />
					<s:hidden name="scene" id="scene" />
					<s:hidden name="plancircle" id="plancircle" />
					<s:hidden name="targetName" id="targetName" />
					<s:hidden name="applyObjectName" id="applyObjectName" />
					<s:hidden name="sceneName" id="sceneName" />
					<s:hidden name="plancircleName" id="plancircleName" />
					<s:hidden name="keyword" id="keyword"/>
					<s:hidden name="sex" id="sex" />
					<s:hidden name="goodsId" id="goodsId" />
        <div class="row">
    
<!--左侧导航栏-->
    <div class="left col-md-2 ">
               
                    <ul class="select">
                        <li>
                            <span><i class="glyphicon glyphicon-play"></i>计划类型</span>
                            <div id="select1" class="left_option">
                                <p class="select-all"><a href="javascript:onPlanQuery('taget','','');">全部</a> </p>
                                <s:iterator value="#request.planTypeList"><p>
                                <a href="javascript:onPlanQuery('taget','<s:property value="code"/>','<s:property value="name"/>');">
                                <s:property value="name"/></a> </p></s:iterator>
                            </div>
                        </li>
                        <li>
                            <span><i class="glyphicon glyphicon-play"></i>适用对象</span>
                            <div id="select2" class="left_option">
                                <p class="select-all"><a href="javascript:onPlanQuery('applyObject','','');">全部</a> </p>
                                <s:iterator value="#request.applyObjectList"><p>
                                <a href="javascript:onPlanQuery('applyObject','<s:property value="code"/>','<s:property value="name"/>');">
                                <s:property value="name"/></a> </p></s:iterator>
                            </div>
                        </li>
                        <li>
                            <span><i class="glyphicon glyphicon-play"></i>适用场景</span>
                            <div id="select3" class="left_option">
                                <p class="select-all"><a href="javascript:onPlanQuery('scene','','');">全部</a></p>
                                <s:iterator value="#request.applySceneList"><p>
                                <a href="javascript:onPlanQuery('scene','<s:property value="code"/>','<s:property value="name"/>');">
                                <s:property value="name"/></a> </p></s:iterator>
                            </div>
                        </li>
                        <li>
                            <span><i class="glyphicon glyphicon-play"></i>计划周期</span>
                            <div id="select4" class="left_option">
                                <p class="select-all"><a href="javascript:onPlanQuery('plancircle','','');">全部</a></p>
                                <s:iterator value="#request.plancircleList"><p>
                                <a href="javascript:onPlanQuery('plancircle','<s:property value="code"/>','<s:property value="name"/>');">
                                <s:property value="name"/></a> </p></s:iterator>
                            </div>
                        </li>
                    </ul>
                </div>
           

<!--右侧内容-->
            <div class="right col-md-10 ">
                <div class="row">
                       <div class="col-md-9 col-lg-9">
                         <div id="demo" class="flexslider">
                                 <ul class="slides">
                                         <s:iterator value="#request.slides">
                                                <li><a href="<s:property value="link"/>">
                                                <img src="<s:if test="icon == null || icon == \"\""><s:if test="image == null || image == \"\"">images/img-defaul.jpg</s:if><s:else>picture/<s:property value="image"/></s:else></s:if><s:else>picture/<s:property value="icon"/></s:else>"> </a> </li>
                                            </s:iterator>
                                            </ul>
                                        </div>
                                    </div>
                                    <div class="col-md-3 col-lg-3">
                                        <div class="banner_t">
                                            <a href="goods.asp?goodsId=<s:property value="#request.goodsList[0].id"/>"><img src="picture/wangyan-c.jpg"> </a>
                                            <p class="teach_p">
                                            王严老师是活跃在健身健美运动一线和理论教学研究领域的专家。自己先后获得过5次北京健美冠军，培训了多位全国、亚洲和世界健美冠军。本系统根据王严老师多年研究、实践成果开发。
<!--                                  王严老师是国内屈指可数的同时活跃在健身健美运 -->
<%--                                             <s:if test="#request.goodsList[0].summary.length()==0"><br></s:if> --%>
<%--                                             <s:elseif test="#request.goodsList[0].summary.length()>36"><s:property value="#request.goodsList[0].summary.substring(0,20)"/>...</s:elseif> --%>
<%--                                             <s:else><s:property value="summary"/></s:else> --%>
                                            </p>
                                        </div>
<!--                                         <div class="banner_t other"> -->
<%--                                             <a href="goods.asp?goodsId=<s:property value="#request.goodsList[1].id"/>"><img src="picture/<s:property value="#request.goodsList[1].image1"/>"> </a> --%>
<!--                                             <p class="teach_p"> -->
<%--                                             <s:if test="#request.goodsList[1].summary.length()==0"><br></s:if> --%>
<%--                                             <s:elseif test="#request.goodsList[1].summary.length()>36"><s:property value="#request.goodsList[1].summary.substring(0,20)"/>...</s:elseif> --%>
<%--                                             <s:else><s:property value="summary"/></s:else> --%>
<!--                                             </p> -->
<!--                                         </div> -->
                                    </div>
                                </div>
                
                            
              <div id="right-2" style="border: none;">               
             <s:include value="/home/planlist_middle.jsp" />
             </div>   
            </div> 
          </div>
          </s:form>
    </div>

<s:include value="/newpages/footer.jsp"></s:include>
</body>
</html>