<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
  <title> 计时 </title>
  <meta name="Generator" content="EditPlus">
  <meta name="Author" content="">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
   <link href="css/club-order.css" rel="stylesheet" type="text/css" />
  <link href="css/public.css" rel="stylesheet" type="text/css" />
  <link href="css/smoothness/template.css" rel="stylesheet" type="text/css" />
 </head>

 <body>
 	<s:form theme="simple">
   <div class="proedit" style="margin:0px auto">
	  <div class="profrist">
		  <p>选择套餐类型：</p>圈存(时效)
			<p class="second">健身套餐名称：<s:property value="product.name"/></p>
		 </div>
		<div class="proedithi">
			<ul>
				<li>甲方：会员</li>
				<li>乙方：<s:property value="product.member.nick"/></li>
				<li>丙方：健身E卡通</li>
			</ul>
		</div>
		<div class="proedifour">
			<p>
				根据《中华人民共和国合同法》等有关法律法规的规定，甲乙丙三方在平等、自愿、公平和诚实信用的基础上，就健身服务的有关事宜协商订立合同如下：
			</p>
			<p>
				1.甲方自<input type="text" name="product.startTime" id="startTime" class="text1" value="由会员填写"  readonly="true" style="background: #f5f5f5;"/>起成为乙方会员，会员资格的有效期限为 <s:textfield name="product.wellNum" cssClass="text2"/>月。截止日期为：<input type="text" name="product.endTime" id="endTime" class="text1" value="系统自动计算"  readonly="true" style="background: #f5f5f5;"/>。甲方拥有在有效期限内按照乙方承诺不限次享受约定服务的权利，有效期限届满后会员资格自动终止。
			</p>
			<p>
				2.<s:if test="product.isReg == \"1\"">该会员资格记名，仅限甲方本人使用；甲方将会员资格转让他人时，<s:if test="product.assignCost!=null && product.assignCost!=''"></s:if>需要<s:else>不需要</s:else>支付转让手续费 <s:textfield name="product.assignCost" cssClass="text2"/>元。<br /></s:if> 
				 <s:else>该会员资格不记名，可由任何符合条件的持卡人使用。</s:else>
			</p>
			<p>
				3.甲方应向乙方支付合同款共计 <s:textfield name="product.cost" cssClass="text2"/>元。双方同意将该合同款交付给丙方托管，并由丙方每月 <input type="text" name="productReportDay" class="text1" value="系统自动计算" readonly="true" style="background: #f5f5f5;"/> 日进行上一个月甲乙双方应付费用的结算。
			</p>
			<p>
				4.甲方因自身原因提前终止合同，应向乙方支付违约金 <s:textfield name="product.breachCost" cssClass="text2"/>元。乙方收到违约金后向甲方退还甲方未消费的金额。
			</p>
			<p>
				5.乙方因自身原因提前终止合同或服务，丙方一周内组织双方确认该事实后，协助办理结算，将未履行的合同款退还甲方。
			</p>
			<p>
				6.本合同<span><s:if test="product.promotionType == \"1\"">可以</s:if><s:else>不可以</s:else></span>与乙方其他优惠促销活动同时使用。
			</p>
			<p>
				7.本合同使用范围：<br />
				  <input type="radio" name="product.useType" value="1" class="checkbox-special" <s:if test="product.useType==null || product.useType=='' || product.useType==1">checked="checked"</s:if>/>仅限在本单店有效。<br />
				  <input type="radio" name="product.useType" value="2" class="checkbox-special" <s:if test="product.useType==2">checked="checked"</s:if>/>在连锁门店通用，通用门店包括：<s:checkboxlist name="product.useRange" value="#request.useRange1" list="#request.clubList" listKey="id" listValue="nick" cssClass="checkbox"/>
			</p>
			<p>
				8.乙方在非法定节假日暂停营业超过24小时或甲方按照约定办理暂停服务手续的，有效期限相应顺延。
			</p>
			<p>
				9.乙方承诺提供的服务内容：
				<p>
				（1）合同内免费服务项目：
				<span>
				<s:checkboxlist name="product.freeProjects" list="#request.projectList" listKey="id" listValue="name" cssClass="checkbox" value="#request.freeProjects1"/>
				<s:textfield name="product.freeProject" cssClass="text"/>
				</span>
				</p>
				<p>
				（2）另收费项目： 
				<span>
				<s:checkboxlist name="product.costProjects" list="#request.projectList" listKey="id" listValue="name" cssClass="checkbox" value="#request.costProjects1"/>
				<s:textfield name="product.costProject" cssClass="text"/>
				</span>           
				（以店内公示项目和价格为准）。
				</p>
			</p>
			<p>
				10.乙方向甲方告知调整营业时间、暂停营业等重要事项，应当采用店堂告示及<span><s:checkboxlist name="product.talkFunc" cssClass="checkbox" list="#{'1':'交易平台短消息','2':'电子邮件','3':'短信','4':'传真'}" listKey="key" listValue="value"/></span>方式。	 交易平台短消息 电子邮件
			</p>
			<p>
				11.其它约定：<s:textarea name="product.remark" cols="80" rows="4" cssStyle="vertical-align:top; border:1px solid #CCC;" maxlength="200"/>
			</p>
			<p>
				12.双方均同意上述条款及《<a href="product!clause.asp" target="_blank">健身合同通用条款</a>》，三方通过本交易平台完成签约手续，甲方支付合同款后本合同正式生效。
			</p>
			<p>
				13.丙方的服务系统仅对甲方的考勤及乙方是否正常营业进行监控，协助双方办理合同款项结算。甲乙双方在合同履行过程中产生的服务质量、安全责任事故、人身、财产损害事故或任何其它合同履行过程中产生的纠纷，丙方不承担任何包括但不限于补偿、赔偿、担保等法律责任。
			</p>
			<p>
				14.丙方完成本合同的结算并向乙方支付甲方的应付款时，按应付款金额的<s:text name="pay.service.cost.e"/>%向乙方收取交易服务费。（交易服务费下限为1元/笔）
			</p>
			<p>
				15.丙方完成本合同的结算并向甲方办理退款手续时，按应退款金额的<s:text name="pay.service.cost.m"/>%向甲方收取交易手续费。（交易手续费下限为1元/笔）
			</p>
			<p>
				16.甲乙双方如产生争议，应向丙方提供证据，由丙方主持双方调解。调解达成协议的，丙方协助办理结算付/退款事宜；调解无法达成协议，丙方对托管资金进行冻结，双方均有权提起诉讼解决，冻结资金由司法机关处理。
			</p>
			
			<p class="end1">
				<input  type="button" name="button" class="button" value="发布" onclick="saveProduct();"/>
			</p>
		</div>
	</div>
	</s:form>
 </body>
</html>
