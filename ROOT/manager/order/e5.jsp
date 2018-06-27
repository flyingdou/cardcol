<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
  <title> 对赌 </title>
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
		  <p>套餐类型：</p>对赌(频率)
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
			<p style="text-indent:2em">
				根据《中华人民共和国合同法》等有关法律法规的规定，甲乙丙三方在平等、自愿、公平和诚实信用的基础上，就健身服务的有关事宜协商订立合同如下：
			</p>
			<p>
				1.甲方承诺从<input type="text" name="product.startTime" id="startTime" class="text1" value="由会员填写" readonly="true" style="background: #f5f5f5;"/>起，每个星期到乙方俱乐部训练 <s:textfield name="product.num" cssClass="text2"/>次，共持续<s:textfield name="product.wellNum" cssClass="text2"/>周时间。截止日期为：<input type="text" name="product.endTime" id="endTime" class="text1" value="系统自动计算"  readonly="true" style="background: #f5f5f5;"/>。
			</p>
			<p>
				2.如果甲方在合同期内按承诺的频率完成了健身，乙方向甲方收取<s:textfield name="product.cost" cssClass="text2"/>元费用。
			</p>
			<p>
				3.甲方的健身频率不得低于承诺次数；每缺勤一次，甲方将向乙方缴纳缺勤费用 <s:textfield name="product.dutyCost" cssClass="text2"/>元。
			</p>
			<p>
				4.如果甲方健身超过承诺频率次数，每次应向乙方缴纳 <s:textfield name="product.overCost" cssClass="text2"/>元费用。但不支付违约金。此款当即从会员的保证金中扣除，会员应该于一周内补足保证金，否则视为违约。
			</p>			
			<p>
				5.甲方存在缺勤行为或因自身原因提前终止合同，应向乙方支付违约金 <s:textfield name="product.breachCost" cssClass="text2"/>元。
			</p>
			<p>
				6.甲方同意交纳本合同保证金  <s:textfield name="product.promiseCost" cssClass="text2"/>元。乙方收取的缺勤费和违约金总额不得超过甲方交纳的保证金上限。
			</p>
			<p>
				7.保证金由甲方交付给丙方。合同期满一周内，丙方根据合同履约记录与甲、乙双方进行合同结算。
			</p>
			<p>
				8.如乙方因自身原因提前终止合同或服务，丙方确认该事实后的一周内进行结算。将保证金退还给甲方。
			</p>
			<p>
				 9.本合同仅限甲方本人使用，不得转让他人。
			</p>
			<p>
				10.本合同<span><s:radio name="product.promotionType" cssClass="checkbox" list="#{'1':'可以','2':'不能'}" listKey="key" listValue="value" value="1"/></span>与乙方其他优惠促销活动同时使用。	 
			</p>
			<p>
				11.乙方在非法定节假日暂停营业超过24小时或甲方按照约定办理暂停服务手续的，有效期限相应顺延。
			</p>
			<p>
				12.乙方承诺提供的服务内容：
				<p>
				（1）合同内免费服务项目：
				<span>
				<s:checkboxlist name="product.freeProjects" value="#request.freeProjects1" list="#request.projectList" listKey="id" listValue="name" cssClass="checkbox"/>
				<s:textfield name="product.freeProject" cssClass="text"/>
				</span>
				</p>
				<p>
				（2）另收费项目：
				<span>
				<s:checkboxlist name="product.costProjects" value="#request.costProjects1" list="#request.projectList" listKey="id" listValue="name" cssClass="checkbox"/>
				<s:textfield name="product.costProject" cssClass="text"/>
				</span>           
				（以店内公示项目和价格为准）。
				</p>
			</p>
			<p>
				13.乙方向甲方告知调整营业时间、暂停营业等重要事项，应当采用店堂告示及<span><s:checkboxlist name="product.talkFunc" cssClass="checkbox" list="#{'1':'交易平台短消息','2':'电子邮件','3':'短信','4':'传真'}" listKey="key" listValue="value"/></span>方式。
			</p>
			<p>
				14.其它约定：<s:textarea name="product.remark" cols="80" rows="4" cssStyle="vertical-align:top; border:1px solid #CCC;"  maxlength="200"/>
			</p>
			<p>
				15.双方均同意上述条款及《<a href="product!clause.asp" target="_blank">健身合同通用条款</a>》，三方通过本交易平台完成签约手续，甲方支付合同保证金后本合同正式生效。
			</p>
			<p>
				16.丙方的服务系统仅对甲方的考勤及乙方是否正常营业进行监控，协助双方办理合同款项结算。甲乙双方在合同履行过程中产生的服务质量、安全责任事故、人身、财产损害事故或任何其它合同履行过程中产生的纠纷，丙方不承担任何包括但不限于补偿、赔偿、担保等法律责任。
			</p>
			<p>
				17.丙方完成本合同的结算并向乙方支付甲方的应付款时，按应付款金额的<s:text name="pay.service.cost.e"/>%向乙方收取交易服务费。（交易服务费下限为1元/笔）		
			</p>
			<p>
				18.丙方完成本合同的结算并向甲方办理退款手续时，按应退款金额的<s:text name="pay.service.cost.m"/>%向甲方收取交易手续费。（交易手续费下限为1元/笔）
			</p>
			<p>
				19.甲乙双方如产生争议，应向丙方提供证据，由丙方主持双方调解。调解达成协议的，丙方协助办理结算付/退款事宜；调解无法达成协议，丙方对托管资金进行冻结，双方均有权提起诉讼解决，冻结资金由司法机关处理。
			</p>

		</div>
	</div>
	</s:form>
 </body>
</html>
