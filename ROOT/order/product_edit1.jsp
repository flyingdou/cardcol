<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="script/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="script/FormValidator/formValidator-4.1.1.js"></script>
<script type="text/javascript" src="script/FormValidator/formValidatorRegex.js"></script>
<script type="text/javascript" src="script/FormValidator/DateTimeMask.js"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="script/jquery.form.js"></script>
<script type="text/javascript" src="script/jquery.parser.js"></script>
<script type="text/javascript" src="script/uploadPreview.js"></script>
<script type="text/javascript" src="script/ckeditor/ckeditor.js"></script>
<style type="text/css">
.proedit p.second input[type="checkbox"]{
width:15px;height:15px;margin:0px;padding:0}
</style>
<script type="text/javascript">
$(document).ready(function(){
	new uploadPreview({
		UpBtn : "txtImage1",
		ImgShow : "preview1"
	});
	/* new uploadPreview({
		UpBtn : "txtImage2",
		ImgShow : "preview2"
	});
	new uploadPreview({
		UpBtn : "txtImage3",
		ImgShow : "preview3"
	}); */
	$("[name='proType']").each(function(index){	
		$(this).click(function(){	
			loadMask();
			var liNode = $(this);			
			$("#proType").val($(this).val());
			$.ajax({type:"post",url:"product!editProduct.asp",data:$('#editForm').serialize(),
				success:function(msg){
					removeMask();
					$("#right-2").html(msg);
				}
			});
		});			
	});
	selectCheckbox("product.freeProjects","<s:property value="product.freeProjects"/>");
	selectCheckbox("product.costProjects","<s:property value="product.costProjects"/>");
	selectCheckbox("product.talkFunc","<s:property value="product.talkFunc"/>");
	$.formValidator.initConfig({theme:'Default',formID:"editForm",alertMessage:true,onError:function(msg){alert(msg)}});
	$("input[name='product.name']").formValidator().inputValidator({min:1,max:50,onError:"健身套餐名称不能为空且长度必须为50字以内,请确认"});
	$("input[name='product.wellNum']").formValidator().inputValidator({min:1,max:20,onError:"有效期限不能为空,请确认"}).regexValidator({regExp:"^[1-9][0-9]*$",onError:"有效期限必须为正整数,请确认"});
	/* $("input[name='product.assignCost']").formValidator().functionValidator({
		fun:function(val){
			if($("input[name='product.isReg']:checked").val() == "1" && $("input[name='product.assignType']:checked").val() == "1"){
				if(val){
					return true;
				}else{
					return "转让手续费不能为空,请确认";
				}
			}
			if(val){
				var regObj = new RegExp("^[0-9]+(.[0-9]{1,2})?$");
				if(regObj.test(val)){
					return true;
				}else{
					return "转让手续费数值格式不正确,请确认";
				}
			}
		},
		onerror:""}); */
	/* $("input[name='product.cost']").formValidator().inputValidator({min:1,max:20,onError:"合同款不能为空,请确认"}).regexValidator({regExp:"^[0-9]+(.[0-9]{1,2})?$",onError:"合同款数值格式不正确,请确认"}); */
	//$("input[name='product.reportDay']").formValidator().inputValidator({min:1,max:20,onError:"费用的结算日不能为空,请确认"}).regexValidator({regExp:"^[1-9][0-9]*$",onError:"费用的结算日必须为正整数,请确认"});
	/* $("input[name='product.breachCost']").formValidator().inputValidator({min:1,max:20,onError:"违约金不能为空,请确认"}).regexValidator({regExp:"^[0-9]+(.[0-9]{1,2})?$",onError:"违约金数值格式不正确,请确认"});
	$("textarea[name='product.freeProject']").formValidator().inputValidator({max:200,onError:"其它免费服务项目长度必须为200字以内,请确认"});
	$("textarea[name='product.costProject']").formValidator().inputValidator({max:200,onError:"其它另收费项目长度必须为200字以内,请确认"});
	$("textarea[name='product.remark']").formValidator().inputValidator({max:200,onError:"其它约定长度必须为200字以内,请确认"}); */
	$("textarea[name='product.remark']").formValidator().inputValidator({max:200,onError:"其它约定长度必须为200字以内,请确认"});
});
function deleteImage(index) {
	$('#image' + index).parent('div').remove();
}
function selectCheckbox(name,checkStr){
	checkStr = ", "+checkStr+", ";
	$("input[name='"+name+"']").each(function(){  
		if(checkStr.indexOf(", "+$(this).val()+", ")!=-1){
			$(this).attr("checked",true);
		} 
	});  
}
function saveProduct(){
	// 为了测试 所以把表单校验去掉
	/* if(!$.formValidator.pageIsValid('1')){
		return;
	} */
	/* $(document).mask('请稍候，正在保存数据......'); */
	if(textblur() && wellNumblur()){
	debugger;
	$('#editForm').form('submit', {
		url: 'product!save.asp',
		onSubmit: function(param) {
			return true;
		},
		success:function(msg){
			/* $(document).unmask(); */
			if(msg == "ok"){
				alert('您发布的健身卡已经进入到后台的审核，请耐心的等待！');
			}else{
				alert(msg);
			}
		}
	});

 	}
}
function goBack(){
	window.location.href = "product!execute.asp";
}
function deleteImage(o) {
	$('#preview' + o).attr('src', 'images/img-defaul.jpg');
	$('#txtImage' + o).val('');
}

/* $(function(){
	$("#textname").blur(function(){
		if($(this).val() == ""){
			$(".namespan").html("输入的内容不能为空");
		}else{
			$(".namespan").html("");
		}
	})
	$("#file1").focus(function(){
		$(".namespan").html("");
	})
}) */
//失去焦点
function textblur(){
	var textname = document.getElementById("textname").value.trim();
	var namespan = document.getElementById("namespan");
	var mark = false;
	if(textname == ""){
		namespan.innerHTML = '<font color="red">套餐名不能为空</font>';
		return mark;
	}else if(textname.length > 40){
		namespan.innerHTML = '<font color="red">长度必须为40字以内</font>';
		return mark;
	}else {
		namespan.innerHTML = '';
		return true;
	}
}
//获取焦点
function textfocus(){
	var namespan = document.getElementById("namespan");
	namespan.innerHTML = "";
}
//失去焦点
function wellNumblur(){
	var regExp = /^[1-9]\d*$/;
	var mark = false;
	var wellNum = document.getElementById("wellNum").value;
	var wellNumSpan = document.getElementById("wellNumSpan");
	if(!regExp.test(wellNum)){
		wellNumSpan.innerHTML = '<font color="red">请输入正整数</font>';
		return mark;
	} else {
		wellNumSpan.innerHTML = '';
		return true;
	}

}
//获取焦点
function wellNumfocus(){
	var wellNumSpan = document.getElementById("wellNumSpan");
	wellNumSpan.innerHTML = '';
}
//失去焦点
function costblur(){
	var regExp = /^[1-9]\d*$/;
	var mark = false;
	var wellNum = document.getElementById("cost").value;
	var wellNumSpan = document.getElementById("costSpan");
	if(!regExp.test(wellNum)){
		wellNumSpan.innerHTML = '<font color="red">请输入正整数</font>';
		return mark;
	} else {
		wellNumSpan.innerHTML = '';
		return true;
	}
}
//获取焦点
function costfocus(){
	var wellNumSpan = document.getElementById("costSpan");
	wellNumSpan.innerHTML = '';
}
</script>
<s:form id="editForm" name="editForm" action="product!save.asp" method="post" theme="simple" enctype="multipart/form-data">
<s:hidden name="product.id" id="key"/>
<s:hidden name="product.no"/>
<s:hidden name="product.type"/>
<s:hidden name="product.proType" id="proType"/>
<s:hidden name="product.courseType"/>
<s:hidden name="product.member.id"/>
<s:hidden name="product.num"/>
<s:hidden name="product.cardType"/>
<s:hidden name="product.cardNum"/>
<s:hidden name="product.dutyCost"/>
<s:hidden name="product.overCost"/>
<s:hidden name="product.promiseCost"/>
<s:hidden name="product.reportDay"/>
<s:hidden name="product.delayDay"/>
<s:hidden name="product.isClose"/>
<s:hidden name="product.topTime"/>
<s:hidden name="product.createTime"/>
<s:hidden name="product.audit"/>
<s:hidden name="product.image1"/>
<s:hidden name="product.image2"/>
<s:hidden name="product.image3"/>
   <h1>健身套餐</h1>
   <div class="proedit">
	  <div class="profrist">
	<%-- 	  <p>选择套餐类型：</p>
			 <ul id="tabfirst">
				<s:if test="product.id==null">
				<s:radio list="#request.types" listKey="code" listValue="name" name="proType" value="%{product.proType}" />
				</s:if>
				<s:else>
					<s:radio list="#request.types" listKey="code" listValue="name" name="proType" value="%{product.proType}" disabled="true" />				
				</s:else>
			 </ul> --%>
		<p class="second">健身套餐名称：
			<s:textfield id="textname" onblur="textblur();" onfocus="textfocus();" name="product.name" cssClass="text" maxlength="50"/>
			<span id="namespan"></span>
		</p>
		<p class="second"style="text-indent:1em">上传缩略图：
			<div id="divimage" style="margin-top: 45px;">
						<div>
							<img id="preview1" width="60px" height="60px"
								<s:if test="product==null || product.image1 == null || product.image1 == ''">src="images/img-defaul.jpg"</s:if>
								<s:else> src="picture/<s:property value='product.image1'/>"</s:else>
								onclick="javascript: $('#txtImage1').click();" /> 
							<img id="delImg1" width="15px" height="15px" src="images/del_img.png"
								onClick="deleteImage('1')"> 
								<!-- <img id="preview2"
								width="60px" height="60px"
								<s:if test="product==null || product.image2 == null || product.image2 == ''">src="images/img-defaul.jpg"</s:if>
								<s:else> src="picture/<s:property value='product.image2'/>"</s:else>
								onclick="javascript: $('#txtImage2').click();" /><img
								id="delImg1" width="15px" height="15px" src="images/del_img.png"
								onClick="deleteImage('2')"><img id="preview3" width="60px"
								height="60px"
								<s:if test="product==null || product.image3 == null || product.image3 == ''">src="images/img-defaul.jpg"</s:if>
								<s:else> src="picture/<s:property value='product.image3'/>"</s:else>
								onclick="javascript: $('#txtImage3').click();" /><img
								id="delImg1" width="15px" height="15px" src="images/del_img.png"
								onClick="deleteImage('3')"> -->
							<s:file id="txtImage1" name="image1" style="display: none;" />
							<%-- <s:file id="txtImage2" name="image2" style="display: none;" />
							<s:file id="txtImage3" name="image3" style="display: none;" /> --%>
						</div>
					</div>
		</p>
		 </div>
	<%-- 	<div class="proedithi">
			<ul>
				<li>甲方：买方昵称</li>
				<li>乙方：<s:property value="#session.loginMember.nick"/></li>
				<li>丙方：卡库网</li>
			</ul>
		</div> --%>
		<div class="proedifour">
	<!-- 		<p>
				根据《中华人民共和国合同法》等有关法律法规的规定，甲乙丙三方在平等、自愿、公平和诚实信用的基础上，就健身服务的有关事宜协商订立合同如下：
			</p> -->
			<p class="second"style="margin-left:10px;text-indent:1em">
					<!-- 1.甲方自<input type="text" name="product.startTime" id="startTime" class="text1" value="由会员填写"  readonly="true" style="background: #f5f5f5;"/>起成为乙方会员，会员资格的 -->
				套餐有效期:&nbsp;<s:textfield id="wellNum" onblur="wellNumblur();" onfocus="wellNumfocus();" name="product.wellNum" cssClass="text2"/> 天
				<span id="wellNumSpan"></span>
				&nbsp;&nbsp;
				<!-- 1.甲方自<input type="text" name="product.startTime" id="startTime" class="text1" value="由会员填写"  readonly="true" style="background: #f5f5f5;"/>起成为乙方会员，会员资格的 -->
				套餐价格:&nbsp;<s:textfield id="cost" onblur="costblur();" onfocus="costfocus();" name="product.cost" cssClass="text2"/> 元
				<span id="costSpan"></span>
					<!-- 。截止日期为：<input type="text" name="product.endTime" id="endTime" class="text1" value="系统自动计算"  readonly="true" style="background: #f5f5f5;"/>。甲方拥有在有效期限内按照乙方承诺不限次享受约定服务的权利，有效期限届满后会员资格自动终止。 -->
			</p>
			<p class="second"style="margin-left:10px;text-indent:1em">
					<!-- 1.甲方自<input type="text" name="product.startTime" id="startTime" class="text1" value="由会员填写"  readonly="true" style="background: #f5f5f5;"/>起成为乙方会员，会员资格的 -->
				商品描述:&nbsp;<s:textfield  name="product.freeProject" cssClass="text4"/>
				<span id="costSpan"></span>
					<!-- 。截止日期为：<input type="text" name="product.endTime" id="endTime" class="text1" value="系统自动计算"  readonly="true" style="background: #f5f5f5;"/>。甲方拥有在有效期限内按照乙方承诺不限次享受约定服务的权利，有效期限届满后会员资格自动终止。 -->
			</p>
			<%-- <p>
				2.<input type="radio" name="product.isReg" value="1" class="checkbox" <s:if test="product.isReg==null || product.isReg=='' || product.isReg==1">checked="checked"</s:if>/>该会员资格记名，仅限甲方本人使用；甲方将会员资格转让他人时，<s:radio list="#{'1':'需要','2':'不需要'}" listKey="key" listValue="value" cssClass="checkbox" name="product.assignType"/> 支付转让手续费 <s:textfield name="product.assignCost" cssClass="text2"/> 元。<br /> 
				  <input type="radio" name="product.isReg" value="2" class="checkbox-special" <s:if test="product.isReg==2">checked="checked"</s:if>/>该会员资格不记名，可由任何符合条件的持卡人使用。
			</p>
			<p>
				3.甲方应向乙方支付合同款共计 <s:textfield name="product.cost" cssClass="text2"/> 元。双方同意将该合同款交付给丙方托管，并由丙方每月 <input type="text" name="productReportDay" class="text1" value="系统自动计算" readonly="true" style="background: #f5f5f5;"/> 日进行上一个月甲乙双方应付费用的结算。
			</p>
			<p>
				4.甲方因自身原因提前终止合同，应向乙方支付违约金 <s:textfield name="product.breachCost" cssClass="text2"/>元。乙方收到违约金后向甲方退还甲方未消费的金额。
			</p>
			<p>
				5.乙方因自身原因提前终止合同或服务，丙方一周内组织双方确认该事实后，协助办理结算，将未履行的合同款退还甲方。
			</p>
			<p>
				6.本合同
				<span>
				<input type="radio" name="product.promotionType" value="1" class="checkbox" <s:if test="product.promotionType==null || product.promotionType=='' || product.promotionType==1">checked="checked"</s:if>/>可以
				<input type="radio" name="product.promotionType" value="2" class="checkbox" <s:if test="product.promotionType==2">checked="checked"</s:if>/>不能
				</span>与乙方其他优惠促销活动同时使用。
			</p> --%>
			<p class="second"style="margin-left:10px;text-indent:1em">
			适用俱乐部：&nbsp;<s:checkboxlist name="product.useRange" value="#request.useRange1" list="#request.clubList" listKey="id" listValue="name" cssClass="checkbox"/>
			</p>
			<%-- <p>
				8.乙方在非法定节假日暂停营业超过24小时或甲方按照约定办理暂停服务手续的，有效期限相应顺延。
			</p>
			<p>
				9.乙方承诺提供的服务内容：
				<p>
				（1）合同内免费服务项目：
				<span>
				<s:checkboxlist name="product.freeProjects" value="#request.freeProjects1" list="#request.projectList" listKey="id" listValue="name" cssClass="checkbox"/>
				<s:textfield name="product.freeProject" cssClass="text" maxlength="100"/>
				</span>
				</p>
				<p>
				（2）另收费项目：
				<span>
				<s:checkboxlist name="product.costProjects" value="#request.costProjects1" list="#request.projectList" listKey="id" listValue="name" cssClass="checkbox"/>
				<s:textfield name="product.costProject" cssClass="text" maxlength="100"/>
				</span>           
				（以店内公示项目和价格为准）。
				</p>
			</p>
			<p>
				10.乙方向甲方告知调整营业时间、暂停营业等重要事项，应当采用店堂告示及<span><s:checkboxlist name="product.talkFunc" cssClass="checkbox" list="#{'1':'交易平台消息','2':'电子邮件','3':'短信','4':'传真'}" listKey="key" listValue="value"/></span>方式。	 
			</p> --%>
			<p class="second" style="text-indent:1em">
				服务说明：&nbsp;<s:textarea id="editor1" name="product.remark" cols="60" rows="4" class="ckeditor" cssStyle="vertical-align:top; border:1px solid #CCC;" maxlength="200"/>
				<script type="text/javascript">CKEDITOR.replace( 'editor1' );</script>
			</p>
			<%-- <p>
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
			</p>	 --%>		
			<p class="end1">
				<input  type="button" name="button" class="button" style="background-image:none;text-align:center;background-color:#ff4011;color:white;text-indent:0;letter-spacing:0;line-height:28px"value="发布" onclick="saveProduct();"/>
<!-- 				<input  type="button"  class="button" value="返回" onclick="goBack();"/> -->
			</p>
		</div>
	</div>
	<script type="text/javascript">
		$(".checkbox").eq(0).attr("checked", true);
		$(".checkbox").click(function(){
			$(this).prop("checked", true);
		});
	</script>
</s:form>