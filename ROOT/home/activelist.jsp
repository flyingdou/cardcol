<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=IE8" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="健身,健身计划,健身计划表,哑铃健身计划,减肥计划表,力量训练,有氧训练,柔韧训练,增肌" />
<meta name="description" content="健身计划列表中有健身者分享好的健身计划,有针对性的健身课程，比如：瘦身计划,减肥计划,健美课程,塑身课程等。" />
<title>挑战活动</title>
<s:include value="/share/meta.jsp"></s:include>
<link href="css/activ.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/style_css.css" />
<script type="text/javascript">
$(function(){
	$('#imgQuery').click(function(){
		queryByPage(1);
	});
});
</script>

</head>
<body>
	<s:include value="/share/home-header.jsp" />
		<s:hidden name="typeId" id="typeId" />
		<s:hidden name="typeName" id="typeName" />
		<s:hidden name="area" id="area" />
		<s:hidden name="target" id="target" />
		<s:hidden name="sex" id="sex" />
		
		<div class="activ_mian">
			<!--left-->
			<div class="activ_left">
				<div class="l_tile">谁在进行健身挑战</div>
				<!--用户列表-->
				<s:iterator value="#request.challenging" var="c">
				<div class="activ_user">
					<div class="act_userimg">
						<a href="javascript:goMemberHome('<s:property value="#c.memberId"/>', 'M')"><img src="<s:if test="image == '' || image == null">images/userpho.jpg</s:if><s:else>picture/<s:property value="image"/></s:else>" style="width:64px;height:64px;" class="img1"/></a>
					</div>
					<div class="act_userinfo">
						<p class="act_name">
							<a href="javascript:goMemberHome('<s:property value="#c.memberId"/>', 'M')"><s:property value="#c.activeName"/></a>
						</p>
						<p class="act_cont"><s:property value="#c.days"/>天内
							<s:if test="#c.category == \"A\"">体重增加<s:property value="#c.value"/>kg</s:if>
							<s:elseif test="#c.category == \"B\"">体重减少<s:property value="#c.value"/>kg</s:elseif>
							<s:elseif test="#c.category == \"C\"">体重保持在<s:property value="#c.value"/>%</s:elseif>
							<s:elseif test="#c.category == \"D\"">运动<s:property value="#c.value"/>次</s:elseif>
							<s:elseif test="#c.category == \"E\"">运动<s:property value="#c.value"/>小时</s:elseif>
							<s:elseif test="#c.category == \"F\"">每周运动<s:property value="#c.value"/>次</s:elseif>
							<s:elseif test="#c.category == \"G\""><s:property value="#c.action"/><s:property value="#c.value"/>千米</s:elseif>
							<s:elseif test="#c.category == \"H\"">力量运动负荷<s:property value="#c.value"/>公斤</s:elseif>
							。</p>
					</div>
					<div class="clear"></div>
				</div>
				</s:iterator>
				<!--成功案例-->
				<div class="act_scuss">
					<div class="suc_hd">成功故事</div>
					<s:iterator value="#request.articles">
					<div class="suc_list">
						<div class="sus_tile"><s:property value="title"/></div>
						<div class="suc_img">
							<img src="picture/<s:if test="icon == null"><s:property value="article.icon"/></s:if><s:else><s:property value="icon"/></s:else>" />
						</div>
						<div class="suc_text"><s:property value="summary" escape="false"/></div>
						<div class="clear"></div>
					</div>
					</s:iterator>
				</div>
			</div>
			<!--mid-->
			<div class="activ_mid">
				<div class="m_tile">可参加的挑战</div>
				<s:form id="queryForm" name="queryForm" method="post" action="activelist!onQuery.asp" theme="simple">
				<s:hidden name="keyword" id="keyword"/>
				<!--条件-->
				<div class="t_tj">
				  <div class="flsdd" style="font-weight: bold;color: #945f50;padding-top:8px;">筛选：</div>
				  <div class="flsdd siai" style="padding-top:3px;"><s:select name="query.mode" list="#{'A':'个人挑战','B':'团体挑战'}" listKey="key" listValue="value" headerKey="" headerValue=""/></div>
				  <div class="flsdd siai" style="padding-top:3px;"><s:select name="query.target" list="#{'A':'体重管理','B':'运动量','C':'次数/时间/频率'}" listKey="key" listValue="value" headerKey="" headerValue="" id="select2"/></div>
				  <div class="flsdd"><img src="images/sur.jpg" width="50" height="26" id="imgQuery" /></div>
				  <div class="clear"></div>
				</div>
				<!--list-->
				<div id="right-2">
					<s:include value="/home/activelist_middle.jsp" />
				</div>
				</s:form>
			</div>
			
			<!--right-->
			<div class="activ_right">
				<div class="rt">
					<!--发起挑战-->
					<p class="fa_btn">
						<a href="product.asp?type=3"><img src="images/fa_btnd.jpg" /></a>
					</p>
					<p class="wen_r">
						&nbsp;&nbsp;&nbsp;&nbsp;您可以在这里发起健身挑战活动，以帮助自己或他人实现健身目标！ <br>
							&nbsp;&nbsp;&nbsp;&nbsp;挑战失败者将自愿向卡库网的合作慈善机构捐一小笔钱，算是对自己没能坚持下来的小小惩罚。
					</p>
					<!--我们的数据-->
					<div class="shu_head">我们的数据</div>
					<div class="shu_c">
						<div class="cum">¥<s:property value="#request.money"/></div>
						<div class="taxt">在线金额</div>
					</div>
					<div class="shu_c2">
						<div class="cum"><s:property value="#request.modeA"/>公斤</div>
						<div class="taxt">增减体重</div>
					</div>

					<div class="shu_c3">
						<div class="cum"><s:property value="#request.modeB"/>千米</div>
						<div class="taxt">运动距离</div>
					</div>
					<div class="shu_c4">
						<div class="cum"><s:property value="#request.modeC"/>次</div>
						<div class="taxt">运动次数</div>
					</div>
					<div class="shu_c5">
						<div class="cum"><s:property value="#request.modeD"/>小时</div>
						<div class="taxt">运动时间</div>
					</div>
					<!--合作慈善机构-->
					<div class="shu_head">合作慈善机构</div>
					<!--1合作-->
					<s:iterator value="#request.institutions">
					<div class="jg_list">
						<div class="hz_ll">
							<img style="width:60px; height:60px;" src="picture/<s:property value="image"/>" />
						</div>
						<div class="hz_rr"><s:property value="title"/></div>
					</div>
					</s:iterator>
					<div style="height: 11px;">&nbsp;</div>
				</div>
			</div>
		</div>
	<s:include value="/share/footer.jsp" />
</body>
</html>
