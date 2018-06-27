<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="瘦身减肥课程,肌肉健美课程,增强体质" />
<meta name="description" content="健身教练的商务中心是健身教练制定的健身套餐,健身课程，如：瘦身减肥课程,肌肉健身课程,增强体质课程等。" />
<title>我收到的投诉</title>
<link href="css/club-order.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="css/pulicstyle.css" />
<script type="text/javascript" src="script/FormValidator/formValidator-4.1.1.js"></script>
<script type="text/javascript" src="script/FormValidator/formValidatorRegex.js"></script>
<script type="text/javascript" src="script/jquery.form.js"></script>
<script type="text/javascript" src="script/jquery.parser.js"></script>
<script type="text/javascript" src="script/uploadPreview.js"></script>
<script type="text/javascript">
	$(function() {
		new uploadPreview({
			UpBtn : "file",
			ImgShow : "preview"
		});
		$("#dialogOrder").dialog({
			autoOpen : false,
			show : "blind",
			hide : "explode",
			resizable : false,
			width : 860,
			modal : true
		});
		$.formValidator.initConfig({
			theme : 'Default',
			formID : "editForm",
			alertMessage : true,
			onError : function(msg) {
				alert(msg)
			}
		});
		$("#orderId").formValidator().inputValidator({
			min : 1,
			max : 20,
			onError : "请先选择需要投诉的订单"
		});
		$("input[name='complaint.telFrom']").formValidator().inputValidator({
			min : 1,
			max : 13,
			onError : "您输入的联系电话长度非法,请确认"
		}).regexValidator({
			regExp : "^[0-9]*$",
			onError : "您输入的联系电话格式不正确"
		});
		$("input[name='complaint.emailFrom']")
				.formValidator()
				.inputValidator({
					min : 6,
					max : 50,
					onError : "您输入的邮箱长度非法,请确认"
				})
				.regexValidator(
						{
							regExp : "^([\\w-.]+)@(([[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.)|(([\\w-]+.)+))(com|cn|net)(]?)$",
							onError : "您输入的邮箱格式不正确"
						});
		$("input[name='complaint.telTo']").formValidator().inputValidator({
			min : 1,
			max : 13,
			onError : "您输入的被投诉方的联系电话长度非法,请确认"
		}).regexValidator({
			regExp : "^[0-9]*$",
			onError : "您输入的被投诉方的联系电话格式不正确"
		});
		$("textarea[name='complaint.content']").formValidator().inputValidator(
				{
					min : 1,
					max : 200,
					onError : "投诉内容不能为空且长度必须为200字以内,请确认"
				});
	});
	function chooseOrder() {
		var params = $('#editForm').serialize();
		$.ajax({
			url : 'order!showOrdersForComplaint.asp',
			type : 'post',
			data : params,
			success : function(msg) {
				$('#dialogOrder').dialog({
					autoOpen : true
				});
				$('#dialogOrder').html(msg);
			}
		});
	}
	function saveCallBack(msg) {
		if (msg == "ok") {
			alert('当前投诉已成功提交！');
		} else {
			alert(msg);
		}
	}
	function save() {
		if (!$.formValidator.pageIsValid('1')) {
			return;
		}
		$(document).mask('请稍候，正在保存数据......');
		$('#editForm').form('submit', {
			url : 'complaint!save.asp',
			onSubmit : function(param) {
				return true;
			},
			success : function(msg) {
				if (msg == 'ok') {
					alert("您的投诉已提交！");
				}
			}
		});
		$(document).unmask();
	}

	//实时显示上传的图片
	function setImagePreview() {
		var docObj = document.getElementById("file");
		var imgObjPreview = document.getElementById("preview");
		if (docObj.files && docObj.files[0]) {
			//火狐下，直接设img属性 
			imgObjPreview.style.display = 'block';
			imgObjPreview.style.width = '130px';
			imgObjPreview.style.height = '100px';
			//imgObjPreview.src = docObj.files[0].getAsDataURL(); 
			//火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式 
			imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
		} else {
			//IE下，使用滤镜 
			docObj.select();
			var imgSrc = document.selection.createRange().text;
			var localImagId = document.getElementById("localImag");
			//必须设置初始大小 
			localImagId.style.width = "50px";
			localImagId.style.height = "50px";
			//图片异常的捕捉，防止用户修改后缀来伪造图片 
			try {
				localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
				localImagId.filters
						.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
			} catch (e) {
				alert("您上传的图片格式不正确，请重新选择!");
				return false;
			}
			imgObjPreview.style.display = 'none';
			document.selection.empty();
		}
		return true;
	}
</script>
</head>
<body>
	<s:include value="/share/header.jsp" />
	<div id="content">
	<s:include value="/order/nav.jsp" />
		<div id="right-2">
		<s:form id="editForm" name="editForm" action="complaint!save.asp"
	method="post" theme="simple" enctype="multipart/form-data">
	<input type="hidden" id="role"
		value="<s:property value="#session.loginMember.role"/>" />
	<s:hidden name="complaint.memberTo.id" id="memberToId" />
	<s:hidden name="complaint.memberTo.nick" id="memberToNick" />
	<s:hidden name="complaint.objId" id="objId" />
	<s:hidden name="complaint.status" value="0" />
	<s:hidden name="complaint.delStatus" value="0" />
	<s:hidden id="orderType" name="complaint.type" />
	<s:hidden id="orderId" name="complaint.orderId" />
	<!--  
<s:hidden name="complaint.fromStatus" value="0"/>
<s:hidden name="complaint.toStatus" value="0"/>
-->
	<h1>我要投诉</h1>
	<div>
		<ul class="recharge">
			<li>
				<p id="ptsbj">健身E卡通仅受理健身俱乐部和教练是否正常营业的相关投诉，并协助双方办理合同款项结算。健身E卡通不受理甲乙双方履约过程中产生的服务质量、安全责任事故、人身、财产损害事故或任何其它纠纷的投诉。
				</p>
			</li>
			<li class="table_a"><label>订单编号:</label> <s:textfield id="orderNo" name="complaint.orderNo"
					maxlength="13" readonly="true" /> <input type="button"  style="position: relative; top: 0px;margin-bottom: 15px;"
				name="button" id="button" class="inpul" value="选择订单"
				onclick="chooseOrder();" />
			</li>
			<li class="table_b"><label>被投诉方:</label> <s:textfield id="memberToName"
					name="complaint.memberTo.nick" maxlength="13" readonly="true" />
			</li>
			<li class="table_b"><label>你的联系电话:</label> <s:textfield name="complaint.telFrom" maxlength="13" />
			</li>
			<li  class="table_b"><label>你的邮箱:</label> <s:textfield name="complaint.emailFrom" maxlength="50" />
			</li>
			<li class="table_b"><label>被投诉方的联系电话: </label><s:textfield name="complaint.telTo"
					maxlength="13" />
			</li>
			<li class="table_b" >
			<label> 投诉内容:</label>
			<s:textarea
					name="complaint.content"
					cssStyle="width:600px; height:100px; border:1px solid #999;padding:10px;margin-bottom: 15px;"
					maxlength="200" /></li>
			<li class="table_b"><label>上传证据：</label> <span style="padding-left: 3px;"> <img
					id="preview" width="60px" height="60px" src="images/img-defaul.jpg"
					onclick="javascript: $('#file').click();" /> <s:file
						name="file.file" id="file" style="display: none;" />上传证据附件，单个最大1M(健身E卡通和被投诉方均可见)
			</span>
			</li>
		</ul>
		<p>
			<input name="button" type="button" id="btnSave" class="queding"   style="margin-left:146px;"
				value="提交" onclick="save();" />
		</p>
	</div>
</s:form>

<div id="dialogOrder" title="选择订单" style="display: none;"></div>
		</div>
	</div>
	
</body>
</html>





