<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="script/FormValidator/formValidator-4.1.1.js"></script>
<script type="text/javascript" src="script/FormValidator/formValidatorRegex.js"></script>
<script type="text/javascript" src="script/FormValidator/DateTimeMask.js"></script>
<script type="text/javascript" src="script/jquery.form.js"></script>
<script type="text/javascript" src="script/jquery.parser.js"></script>
<script type="text/javascript" src="script/uploadPreview.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	new uploadPreview({
		UpBtn : "txtImage1",
		ImgShow : "preview1"
	});
	new uploadPreview({
		UpBtn : "txtImage2",
		ImgShow : "preview2"
	});
	new uploadPreview({
		UpBtn : "txtImage3",
		ImgShow : "preview3"
	});
	$.formValidator.initConfig({theme:'Default',formID:"editForm",alertMessage:true,onError:function(msg){alert(msg)}});
	$("input[name='product.name']").formValidator().inputValidator({min:1,max:50,onError:"健身套餐名称不能为空且长度必须为50字以内,请确认"});
	$("input[name='product.num']").formValidator().inputValidator({min:1,max:20,onError:"训练次数不能为空,请确认"}).regexValidator({regExp:"^[1-9][0-9]*$",onError:"训练次数必须为正整数,请确认"});
	$("input[name='product.num1']").formValidator().inputValidator({min:1,max:20,onError:"训练次数不能为空,请确认"}).regexValidator({regExp:"^[1-9][0-9]*$",onError:"训练次数必须为正整数,请确认"});
	$("input[name='product.wellNum']").formValidator().inputValidator({min:1,max:20,onError:"持续周期不能为空,请确认"}).regexValidator({regExp:"^[1-9][0-9]*$",onError:"持续周期必须为正整数,请确认"});
	$("input[name='product.dutyCost']").formValidator().inputValidator({min:1,max:20,onError:"缺勤费用不能为空,请确认"}).regexValidator({regExp:"^[0-9]+(.[0-9]{1,2})?$",onError:"缺勤费用数值格式不正确,请确认"});
	$("input[name='product.overCost']").formValidator().inputValidator({min:1,max:20,onError:"缴纳费用不能为空,请确认"}).regexValidator({regExp:"^[0-9]+(.[0-9]{1,2})?$",onError:"缴纳费用数值格式不正确,请确认"});
	$("input[name='product.cost']").formValidator().inputValidator({min:1,max:20,onError:"收取费用不能为空,请确认"}).regexValidator({regExp:"^[0-9]+(.[0-9]{1,2})?$",onError:"收取费用数值格式不正确,请确认"});
	$("input[name='product.breachCost']").formValidator().inputValidator({min:1,max:20,onError:"违约金不能为空,请确认"}).regexValidator({regExp:"^[0-9]+(.[0-9]{1,2})?$",onError:"违约金数值格式不正确,请确认"});
	$("input[name='product.promiseCost']").formValidator().inputValidator({min:1,max:20,onError:"保证金不能为空,请确认"}).regexValidator({regExp:"^[0-9]+(.[0-9]{1,2})?$",onError:"保证金数值格式不正确,请确认"});
	$("textarea[name='product.freeProject']").formValidator().inputValidator({max:200,onError:"其它免费服务项目长度必须为200字以内,请确认"});
	$("textarea[name='product.costProject']").formValidator().inputValidator({max:200,onError:"其它另收费项目长度必须为200字以内,请确认"});
	$("textarea[name='product.remark']").formValidator().inputValidator({max:200,onError:"其它约定长度必须为200字以内,请确认"});
	document.getElementById("numa").value = document.getElementById("num").value;
	document.getElementById("num1a").value = document.getElementById("num1").value;
	//$('#startTime').datepicker({changeYear: true});
	//$('#endTime').datepicker({changeYear: true});
	$('.addimage').click(function(){
		var childs = $('#divimage').children(), index = childs.length + 1;
		if (childs.length >= 3) {
			alert('最多只能添加三张图片，请确认！');
			return;
		}
		$('#divimage').append('<div><input type="file" id="image' + index + '" name="image' + index + '" value="浏览..."  /><a href="javascript:deleteImage(' + index + ')">删除</a></div>');
	});
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
	if(!$.formValidator.pageIsValid('1')){
		return;
	}
	$(document).mask('请稍候，正在保存数据......');
	$('#editForm').form('submit', {
		url: 'product!save.asp',
		onSubmit: function(param) {
			return true;
		},
		success:function(msg){
			$(document).unmask();
			if(msg == "ok"){
				alert('您发布的健身卡已经进入到后台的审核，请耐心的等待！');
			}else{
				alert(msg);
			}
		}
	});
}
function setNum(order){
	if(order == 1){
		var minNum = document.getElementById("num").value;
		document.getElementById("numa").value = minNum;
	}
	if(order == 2){
		var minNum = document.getElementById("numa").value;
		document.getElementById("num").value = minNum;
	}
	if(order == 3){
		var maxNum = document.getElementById("num1").value;
		document.getElementById("num1a").value = maxNum;
	}
    if(order == 4){
    	var maxNum = document.getElementById("num1a").value;
    	document.getElementById("num1").value = maxNum;
	}	
}
function goBack(){
	window.location.href = "product!execute.asp";
}
function deleteImage(o) {
	$('#preview' + o).attr('src', 'images/img-defaul.jpg');
	$('#txtImage' + o).val('');
}
</script>
<s:form id="editForm" name="editForm" action="product!save.asp" method="post" theme="simple" enctype="multipart/form-data">
<s:hidden name="product.id" id="key"/>
<s:hidden name="product.no"/>
<s:hidden name="product.type"/>
<s:hidden name="product.proType" id="proType"/>
<s:hidden name="product.courseType"/>
<s:hidden name="product.member.id"/>
<s:hidden name="product.cardType"/>
<s:hidden name="product.cardNum"/>
<s:hidden name="product.isReg"/>
<s:hidden name="product.assignCost"/>
<s:hidden name="product.reportDay"/>
<s:hidden name="product.delayDay"/>
<s:hidden name="product.useType"/>
<s:hidden name="product.useRange"/>
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
		  <p>选择套餐类型：</p>
			 <ul id="tabfirst">
				<s:if test="product.id==null">
				<s:radio list="#request.types" listKey="code" listValue="name" name="proType" value="%{product.proType}" />
				</s:if>
				<s:else>
					<s:radio list="#request.types" listKey="code" listValue="name" name="proType" value="%{product.proType}" disabled="true" />				
				</s:else>
			 </ul>
		<p class="second">健身套餐名称：
			<s:textfield name="product.name" cssClass="text" maxlength="50"/>
		</p>
		<p class="second">上传缩略图：
			<div id="divimage" style="margin-top: 45px;">
						<div>
							<img id="preview1" width="60px" height="60px"
								<s:if test="product==null || product.image1 == null || product.image1 == ''">src="images/img-defaul.jpg"</s:if>
								<s:else> src="picture/<s:property value='product.image1'/>"</s:else>
								onclick="javascript: $('#txtImage1').click();" /> <img
								id="delImg1" width="15px" height="15px" src="images/del_img.png"
								onClick="deleteImage('1')"> <img id="preview2"
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
								onClick="deleteImage('3')">
							<s:file id="txtImage1" name="image1" style="display: none;" />
							<s:file id="txtImage2" name="image2" style="display: none;" />
							<s:file id="txtImage3" name="image3" style="display: none;" />
						</div>
					</div>
		</p>
	    </div>
		<div class="proedithi">
			<ul>
				<li>甲方：买方昵称</li>
				<li>乙方：<s:property value="#session.loginMember.nick"/></li>
				<li>丙方：健身E卡通</li>
			</ul>
		</div>
		<div class="proedifour">
			<p style="text-indent:2em">
				根据《中华人民共和国合同法》等有关法律法规的规定，甲乙丙三方在平等、自愿、公平和诚实信用的基础上，就健身服务的有关事宜协商订立合同如下：
			</p>
			<p>
				1.甲方自<input type="text" name="product.startTime" id="startTime" class="text1" value="由会员填写" readonly="true" style="background: #f5f5f5;"/>起成为乙方会员，会员资格的有效期限为 <s:textfield name="product.wellNum" cssClass="text2"/>月 ，截止日期为：<input type="text" name="product.endTime" id="endTime" class="text1" value="系统自动计算"  readonly="true" style="background: #f5f5f5;"/>。
			</p>
		    <p>2.双方同意根据甲方在合同有效期内实际到乙方俱乐部健身的总次数收取费用。健身次数小于等于<s:textfield id="num" name="product.num" cssClass="text2"  onblur="setNum(1)"/>次，收费为<s:textfield name="product.cost" cssClass="text2"/>元；
				健身次数大于<s:textfield id="numa"  name="numa" cssClass="text2"  onblur="setNum(2)"/>次小于<s:textfield id="num1"  name="product.num1" cssClass="text2" onblur="setNum(3)"/>次，收费为<s:textfield name="product.cost1" cssClass="text2"/>元；
				健身次数大于等于<s:textfield id="num1a" name="num1a" cssClass="text2"  onblur="setNum(4)" />次，收费为<s:textfield name="product.cost2" cssClass="text2"/>元。
		    </p> 
		    
			<p>
				3.甲方因自身原因提前终止合同，应向乙方支付违约金<s:textfield name="product.breachCost" cssClass="text2"/>元 。同时根据按本协议第二条最低健身次数约定的单价向乙方支付健身费用。
			</p>
			<p>
				4.甲方同意交纳本合同保证金<s:textfield name="product.promiseCost" cssClass="text2"/>元 。保证金由甲方交付给丙方。合同期满一周内，丙方根据合同履约记录与甲、乙双方进行合同结算。
			</p>			
			<p>
				5.如乙方因自身原因提前终止合同或服务，丙方确认该事实后的一周内进行结算。将保证金退还给甲方。
			</p>
			<p>
				6.本合同仅限甲方本人使用，不得转让他人。
			</p>
			<p>
				7.本合同
				<span>
				<input type="radio" name="product.promotionType" value="1" class="checkbox" <s:if test="product.promotionType==null || product.promotionType=='' || product.promotionType==1">checked="checked"</s:if>/>可以
				<input type="radio" name="product.promotionType" value="2" class="checkbox" <s:if test="product.promotionType==2">checked="checked"</s:if>/>不能
				</span>与乙方其他优惠促销活动同时使用。 
			</p>
			<p>
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
				（2）收费服务项目：
				<span>
				<s:checkboxlist name="product.costProjects" value="#request.costProjects1" list="#request.projectList" listKey="id" listValue="name" cssClass="checkbox"/>
				<s:textfield name="product.costProject" cssClass="text" maxlength="100"/>
				</span>           
				（以店内公示项目和价格为准）。
				</p>
			</p>
			<p>
				10.乙方向甲方告知调整营业时间、暂停营业等重要事项，应当采用店堂告示及<span><s:checkboxlist name="product.talkFunc" cssClass="checkbox" list="#{'1':'交易平台消息','2':'电子邮件','3':'短信','4':'传真'}" listKey="key" listValue="value"/></span>方式。
			</p>
			<p>
				11.其它约定：<s:textarea name="product.remark" cols="80" rows="4" cssStyle="vertical-align:top; border:1px solid #CCC;"  maxlength="200"/>
			</p>
			<p>
				12.双方均同意上述条款及《<a href="product!clause.asp" target="_blank">健身合同通用条款</a>》，三方通过本交易平台完成签约手续，甲方支付合同保证金后本合同正式生效。
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
				<input  type="button"  class="button" value="返回" onclick="goBack();"/>
			</p>
		</div>
	</div>
</s:form>