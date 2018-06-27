<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript"
	src="script/FormValidator/formValidator-4.1.1.js"></script>
<script type="text/javascript"
	src="script/FormValidator/formValidatorRegex.js"></script>
<script type="text/javascript">
	function commEdit(id, type) {
		//商户发布套餐、课程时，判断是否进行过手机验证。如果没有则强制要求用户必须手机验证。
		if ("<s:property value="#session.loginMember.mobileValid"/>" != "1") {
			var info = "";
			if (type == "1") {
				info = "套餐";
			} else if (type == "2") {
				info = "课程";
			} else if (type == "3") {

			}
			alert("商户发布" + info + "必须先到我的账户-->联系方式中进行手机验证！");
			window.location.href = "mobile.asp";
			return;
		}
		if (type == "3") {
			type = "1";
		}
		if (type == "1") {
			if (id) {
				$("#keyId").val(id);
			} else {
				$("#keyId").val("");
				$("#productType").val($("#type").val());
				$("#proType").val("1");
			}
			$(document).mask('请稍候，正在加载页面......');
			$.ajax({
				type : "post",
				url : "product!editProduct.asp",
				data : $('#queryForm').serialize(),
				success : function(msg) {
					$(document).unmask();
					$("#right-2").html(msg);
				}
			});
		} else {
			onEdit(id, type);
		}
	}
	function commDelete(id) {
		$("#keyId").val(id);
		if (confirm("是否确认删除当前数据？")) {
			$.ajax({
				type : "post",
				url : "product!delete.asp",
				data : $('#queryForm').serialize(),
				success : function(msg) {
					if (msg == "isFefByOrder") {
						alert("您选择的数据已被订单引用，无法删除！");
					} else {
						alert("当前数据已成功删除！");
						$("#right-2").html(msg);
					}

				}
			});
		}
	}
	function commClose(id, isClose) {
		$("#keyId").val(id);
		if (isClose == "1") {
			$("#isClose").val("2");
		} else {
			$("#isClose").val("1");
		}
		$.ajax({
			type : "post",
			url : "product!changeClose.asp",
			data : $('#queryForm').serialize(),
			success : function(msg) {
				$("#right-2").html(msg);
			}
		});
	}
	function commTop(id, topTime) {
		$("#keyId").val(id);
		if (topTime) {
			$("#isTop").val("2");
		} else {
			$("#isTop").val("1");
		}
		$.ajax({
			type : "post",
			url : "product!changeTopTime.asp",
			data : $('#queryForm').serialize(),
			success : function(msg) {
				$("#right-2").html(msg);
			}
		});
	}
	function onEdit(id, type) {
		if (id) {
			$
					.ajax({
						type : 'post',
						url : 'product!edit.asp',
						data : "product.id=" + id,
						success : function(msg) {
							var product = $.parseJSON(msg)[0];
							$('input[name="product.id"]').val(product.id);
							$('input[name="product.type"]').val(product.type);
							$('input[name="product.isClose"]').val(
									product.isClose);
							$('input[name="product.topTime"]').val(
									product.topTime);
							$('input[name="product.createTime"]').val(
									product.createTime);
							$('input[name="product.audit"]').val(product.audit);
							var courseType = product.courseType;
							$('select[name="product.courseType"]').val(
									courseType);
							if (courseType == "1") {
								$("#spanNum")
										.html(
												"次,有效期限<input type='text' class='shu_hou'  name='product.wellNum' value='"+product.wellNum+"'/>天");
							} else if (courseType == "2") {
								$("#spanNum").html("天");
							}
							$('input[name="product.name"]').val(product.name);
							$('input[name="product.num"]').val(product.num);
							$('input[name="product.cost"]').val(product.cost);
							$('textarea[name="product.remark"]').val(
									product.remark);
						}
					});
		} else {
			$('input[name="product.id"]').val("");
			$('input[name="product.type"]').val(type);
			$('input[name="product.isClose"]').val("");
			$('input[name="product.topTime"]').val("");
			$('input[name="product.createTime"]').val("");
			$('input[name="product.audit"]').val("1");
			$('select[name="product.courseType"]').val("1");
			$("#spanNum")
					.html(
							"次,有效期限<input type='text' class='shu_hou' name='product.wellNum'/>月");
			$('input[name="product.name"]').val("");
			$('input[name="product.num"]').val("");
			$('input[name="product.cost"]').val("");
			$('textarea[name="product.remark"]').val("");
		}
		if (type == '2') {
			$("#spanName").html("实物商品");
			$("#xiugai-2").css("display", "none");
			$("#xiugai-3").css("display", "none");
		} else {
			$("#spanName").html("名称");
			$("#xiugai-2").css("display", "block");
			$("#xiugai-3").css("display", "block");
		}
		$('#xiugai111').dialog('open');
	}
	function changeSpanNum() {
		var courseType = $("select[name='product.proType']").val();
		if (courseType == "7") {
			$("#spanNum")
					.html(
							"次,限时<input type='text' class='shu_hou' name='product.wellNum'/>月");
		} else if (courseType == "8") {
			$("#spanNum").html("月");
		}
	}
	function onSave() {
		/*
		if(!$.formValidator.pageIsValid('1')){
			return;
		}
		 */
		if (!$("input[name='product.name']").val()) {
			alert("名称不能为空,请确认");
			$("input[name='product.name']").focus();
			$("input[name='product.name']").select();
			return;
		}
		if (!$("input[name='product.num']").val()) {
			alert("数量不能为空,请确认");
			$("input[name='product.num']").focus();
			$("input[name='product.num']").select();
			return;
		} else {
			var regObj = new RegExp("^[1-9][0-9]*$");
			if (!regObj.test($("input[name='product.num']").val())) {
				alert("数量必须为正整数,请确认");
				$("input[name='product.num']").focus();
				$("input[name='product.num']").select();
				return;
			}
		}
		if ($("select[name='product.proType']").val() == "7") {
			var val = $("input[name='product.wellNum']").val();
			if (!val) {
				alert("计次课程有效期限不能为空,请确认");
				$("input[name='product.wellNum']").focus();
				$("input[name='product.wellNum']").select();
				return;
			}
			if (val) {
				var regObj = new RegExp("^[1-9][0-9]*$");
				if (!regObj.test(val)) {
					alert("计次课程有效期限必须为正整数,请确认");
					$("input[name='product.wellNum']").focus();
					$("input[name='product.wellNum']").select();
					return;
				}
			}
		}
		if (!$("input[name='product.cost']").val()) {
			alert("金额不能为空,请确认");
			$("input[name='product.cost']").focus();
			$("input[name='product.cost']").select();
			return;
		} else {
			var regObj = new RegExp("^[0-9]+(.[0-9]{1,2})?$");
			if (!regObj.test($("input[name='product.cost']").val())) {
				alert("金额数值格式不正确,请确认");
				$("input[name='product.cost']").focus();
				$("input[name='product.cost']").select();
				return;
			}
		}
		var params = $('#form').serialize();
		$.ajax({
			type : 'post',
			url : 'product!save.asp',
			data : params,
			success : function(msg) {
				if (msg == "ok") {
					if ($("input[name='product.type']").val() == "2") {
						alert('当前实物商品数据已经成功保存！');
					} else {
						alert('当前定制收费已经成功保存！');
					}
					onClose();
					queryByPage(1);
				} else {
					alert(msg);
				}
			}
		})
	}
	function onClose() {
		$('#xiugai111').dialog('close');
	}

	function preShop(productId) {
		url = "clublist!shoGo.asp?productId=" + productId;
		$('#queryForm').attr('action', url);
		$('#queryForm').submit();
	}
</script>
<s:form id="queryForm" name="queryForm" method="post"
	action="product!query.asp" theme="simple">
	<s:hidden name="query.type" id="type" />
	<s:hidden name="product.id" id="keyId" />
	<s:hidden name="product.type" id="productType" />
	<s:hidden name="product.proType" id="proType" />
	<s:hidden name="product.isClose" id="isClose" />
	<s:hidden name="isTop" id="isTop" />
	<h1>
		<s:if test="query.type==1">健身卡</s:if>
		<s:if test="query.type==2">实物商品</s:if>
		<s:if test="query.type==3">健身卡</s:if>
	</h1>
	<div>
		<div class="div1">
			<input name="button" type="button" class="buttonjs"
				onclick="commEdit('<s:property value="id"/>','<s:property value="query.type"/>');"
				value="<s:if test="query.type==1">定制套餐</s:if><s:if test="query.type==2">实物商品</s:if><s:if test="query.type==3">定制收费</s:if>" />
		</div>
		<div class="div2">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" style="text-align: center;">
				<tr class="end">
					<th>名称</th>
					<th>金额</th>
					<th>时间</th>
					<th>审核状态</th>
					<th>操作</th>
				</tr>
				<s:iterator value="pageInfo.items" status="sta">
					<tr <s:if test="#sta.last">class="end"</s:if>>
						<td class="left"><a style="color:#474344;" href="#" id="colotoa"> <s:if
									test="proType==1">[圈存(时效)]</s:if> <s:if test="proType==2">[圈存(计次)]</s:if>
								<s:if test="proType==3">[圈存(储值)]</s:if> <s:if test="proType==4">[对赌(次数)]</s:if>
								<s:if test="proType==5">[对赌(频率)]</s:if> <s:if test="proType==6">[预付卡]</s:if>
						</a> <a style="color:#474344;"href="javascript:preShop('<s:property value="id"/>');"
							id="colotoa"><s:property value="name" /></a></td>
						<td><s:if test="proType==4 || proType == 5">保证金:<s:property
									value="promiseCost" />
							</s:if>
							<s:else>金额:<s:property value="cost" />
							</s:else>元</td>
						<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
						<td><s:if test="audit == '1'">审核通过</s:if> <s:else>待审核</s:else>
						</td>
						<td>
							  <%-- <s:if test="audit != '1'"> --%>
								<a id="colotoa" href="javascript:commEdit('<s:property value="id" />','<s:property value="type"/>');">编辑</a>
					          	<%-- <a id="colotoa" href="javascript:commDelete('<s:property value="id"/>');">删除</a> --%>
					      <%--     </s:if> --%>
						
							<a id="colotoa"	href="javascript:commClose('<s:property value="id"/>','<s:property value="isClose"/>');">
							<s:if test="isClose==1">开启</s:if><s:if test="isClose==2">关闭</s:if>
							</a>
							<a id="colotoa" href="javascript:commTop('<s:property value="id"/>','<s:property value="topTime"/>');">
							<s:if test="topTime==null">置顶</s:if><s:else>取消置顶</s:else>
							
							</a>
						</td>
					</tr>
				</s:iterator>
			</table>
		</div>
		<s:include value="/share/pageAjax.jsp" />
	</div>
</s:form>
