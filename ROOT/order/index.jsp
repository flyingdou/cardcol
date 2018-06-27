<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="瘦身减肥课程,肌肉健美课程,增强体质" />
<meta name="description"
	content="健身教练的商务中心是健身教练制定的健身套餐,健身课程，如：瘦身减肥课程,肌肉健身课程,增强体质课程等。" />
<title>商务中心</title>
<link href="css/club-order.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="css/pulicstyle.css" />
<script type="text/javascript">
	function goAjax(url) {
		$.ajax({
			type : "post",
			url : url,
			data : "",
			success : function(msg) {
				$("#right-2").html(msg);
			}
		});
	}
	$(function() {
		$("#xiugai111").dialog({
			autoOpen : false,
			width : 450,
			show : "blind",
			hide : "explode",
			resizable : false
		});
	});
</script>
</head>
<body>
	<s:include value="/share/header.jsp" />
	<div id="content">
	<s:include value="/order/nav.jsp" />
		<div id="right-2">
			<s:if test="type!=null">
				<s:if test="type==1">
					<s:include value="/order/complaint_edit.jsp" />
				</s:if>
				<s:elseif test="type==2">
					<s:include value="/order/complaint_list.jsp" />
				</s:elseif>
				<s:elseif test="type==3">
					<s:include value="/active/active_edit.jsp" />
				</s:elseif>
				<s:elseif test="type==4">
					<s:include value="/active/active_partake.jsp" />
				</s:elseif>
				<s:elseif test="type==5">
					<s:include value="/order/my_appraise.jsp" />
				</s:elseif>
				<s:elseif test="type==6">
					<s:include value="/order/plan_list.jsp" />
				</s:elseif>
			</s:if>
			<s:else>
				<s:if test="#session.loginMember.role == \"M\"">
					<s:include value="/order/order_list1.jsp" />
				</s:if>
				<s:else>
					<s:include value="/order/product_list.jsp" />
				</s:else>
			</s:else>
		</div>
	</div>
	<s:include value="/share/footer.jsp" />
	<div class="xiugai" id="xiugai111" title="定制收费">
		<s:form name="form" id="form" theme="simple">
			<input type="hidden" name="product.id" />
			<input type="hidden" name="product.type" />
			<input type="hidden" name="product.isClose" />
			<input type="hidden" name="product.topTime" />
			<input type="hidden" name="product.createTime" />
			<input type="hidden" name="product.audit" />
			<div class="xiugai-1" id="name">
				<span class="xiugai_mark1">*</span><span id="spanName">实物商品</span>：
				<input name="product.name" class="gaiinp" maxlength="8" /><span
					id="tishi" class="xiugai_alert">最多输入八个汉字</span>
			</div>
			<div class="xiugai-2" id="xiugai-2">
				<span class="xiugai_mark1">*</span>类型：
				<s:select name="product.proType"
					cssStyle=" width:82px; height:24px; border:1px solid #ccc;"
					list="#{'7':'计次收费','8':'计时收费'}" listKey="key" listValue="value"
					onchange="changeSpanNum();" />
			</div>
			<div class="xiugai-3" id="xiugai-3">
				<span class="xiugai_mark2">*</span>数量： <input name="product.num"
					class="shu_hou" /><span id="spanNum">次</span>
			</div>
			<div class="xiugai-4">
				<span class="xiugai_mark2">*</span>金额： <input name="product.cost"
					class="shu_hou" />元
			</div>
			<div class="xiugai-5">
				<span>描述：</span>
				<textarea name="product.remark" cols="30" class="gaitexea"
					id="editor1" maxlength="200"></textarea>
			</div>
			<div class="xiugai-6">
				<input type="button" name="button" class="sevr" value="保存"
					onclick="onSave();" /> <input type="button" name="button"
					class="close" value="关闭" onclick="onClose();" />
			</div>
		</s:form>
	</div>
</body>
</html>