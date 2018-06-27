<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="瘦身减肥课程,肌肉健美课程,增强体质" />
<meta name="description"
	content="健身教练的商务中心是健身教练制定的健身套餐,健身课程，如：瘦身减肥课程,肌肉健身课程,增强体质课程等。" />
<title>评价管理</title>
<link href="css/club-sns.css" rel="stylesheet" type="text/css" />
<link href="css/member-checkin.css" rel="stylesheet" type="text/css" />
<link href="css/club-order.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="css/pulicstyle.css" />
<script type="text/javascript">
	$(document).ready(function() {
		//其他地方进入评价页面
		if ('<s:property value="goType"/>' == '1') {
			goAjax('appraise!query.asp', '11');
		}
	});
	$(function() {
		$('#xg').dialog({
			autoOpen : false,
			width : 450,
			show : "blind",
			hide : "blind",
			modal : true
		});
	});
	function reContent(id) {
		$('input[name="appraise.id"]').val(id);
		$('textarea[name="appraise.reContent"]').val("");
		$('#xg').dialog('open');
	}
	function onSave() {
		if ($('input[name="appraise.reContent"]').val() == '') {
			alert('回复内容不能为空！');
			$('input[name="appraise.reContent"]').focus();
			return;
		}
		var params = $('#reContentForm').serialize();
		$.ajax({
			type : 'post',
			url : 'appraise!saveReContent.asp',
			data : params,
			success : function(msg) {
				if (msg == "ok") {
					alert('当前回复内容已经成功保存！');
					$('#xg').dialog('close');
					queryByPage(1);
				} else {
					alert(msg);
				}
			}
		});
	}

	function delAppraise(delType, id) {
		$("#delType").val(delType);
		$("#appraiseId").val(id);
		$.ajax({
			type : "post",
			url : "appraise!deleteAppraise.asp",
			data : $('#queryForm').serialize(),
			/* success : function(msg) {
				//$("#right-2").html(msg);
			} */
		});
	}
	function onClose() {
		$("#xg").dialog('close');
	}

	function productDetail(productId, productType) {
		if (productType == 1) {
			$('#queryForm').attr('action', 'clublist!shoGo.asp?productId='+productId);
			$('#queryForm').submit();
		} else if(productType == 2){
			$('#queryForm').attr("action",'activewindow.asp?id='+productId);
			$('#queryForm').submit();	
		} else if (productType == 3) {
			$('#queryForm').attr("action", 'plan.asp?pid='+productId);
			$('#queryForm').submit();
		} else if (productType == 6) {
			$('#queryForm').attr("action", 'goods.asp?goodsId='+productId);
			$('#queryForm').submit();
		}
	}
</script>
</head>
<body>
	<s:include value="/share/header.jsp" />
	<div id="content">
	<s:include value="/order/nav.jsp" />
		<div id="right-2">
			<s:form id="queryForm" name="queryForm" method="post"
	action="appraise!query.asp" theme="simple">
	<s:hidden name="appraise.id" id="appraiseId" />
	<input type="hidden" id="delType" name="delType" />
	<h1>评价管理</h1>
	<div>
		<div class="top2"></div>
		<s:iterator value="pageInfo.items">
			<div class="diva">
				<div class="divb">
					<dl>
						<dt>
							<img
								src="<s:if test="fromImage == '' || fromImage == null">images/userpho.jpg</s:if><s:else>picture/<s:property value="fromImage"/></s:else>" />
						</dt>
						<dd>
							<p>
								<a
									href="javascript:goMemberHome('<s:property value="fromId"/>','<s:property value="fromRole"/>');"
									id="colotoa"><s:if test="fromId == #session.loginMember.id">我</s:if>
									<s:else>
										<s:property value="fromNick" />
									</s:else></a> <span class="spana">对<s:if
										test="orderType==\"1\" || orderType==\"3\" || orderType==\"6\"">
										<a
											href="javascript:productDetail(<s:property value="productId"/>,<s:property value="orderType"/>);"><s:property
												value="productName" /></a>
									</s:if>
									<s:else>
										<s:property value="productName" />
									</s:else>的评价
								</span> <span class="spanc"><s:date name="appDate"
										format="yyyy-MM-dd HH:mm:ss" /> <span class="spanb"> <s:if
											test="fromId == #session.loginMember.id">
											<a id="colotoa" class="delete_score"
												href="javascript:delAppraise('1','<s:property value="id"/>');">[删除]</a>
										</s:if> <s:else>
											<s:if test="reContent!=null">
											</s:if>
											<s:else>
												<a id="colotoa" class="delete_score"
													href="javascript:reContent('<s:property value="id"/>');">[回复]</a>
											</s:else>

										</s:else>
								</span> </span>
							</p>
							<!--回复是在没有回复是显示的，回复过后就消失的-->
							<p>
								<span class="make_score">评分：</span><span class="score"><s:property
										value="grade" />分</span><span><s:property value="content" /></span>
							</p>
						</dd>
					</dl>
				</div>
				<s:if test="reContent != null">
					<div class="divc">
						<dl>
							<dt>
								<a
									href="javascript:goMemberHome('<s:property value="toId"/>','<s:property value="toRole"/>');"
									id="colotoa"><img
									src="picture/<s:property value="toImage"/>" /></a>
							</dt>
							<dd>
								<p>
									<s:if test="toId == #session.loginMember.id">
										<a
											href="javascript:goMemberHome('<s:property value="toId"/>','<s:property value="toRole"/>');"
											id="colotoa">我</a>
									</s:if>
									<s:else>
										<a
											href="javascript:goMemberHome('<s:property value="toId"/>','<s:property value="toRole"/>');"
											id="colotoa"><s:property value="toNick" /></a>
									</s:else>
									对
									<s:if test="fromId == #session.loginMember.id">
										<a
											href="javascript:goMemberHome('<s:property value="fromId"/>','<s:property value="fromRole"/>');"
											id="colotoa">我</a>
									</s:if>
									<s:else>
										<a
											href="javascript:goMemberHome('<s:property value="fromId"/>','<s:property value="toRole"/>');"
											id="colotoa"><s:property value="fromNick" /></a>
									</s:else>
									<span class="spanb">的回复</span> <span class="spanc"><s:date
											name="reAppDate" format="yyyy-MM-dd HH:mm:ss" /><span
										class="spanb"> <s:if
												test="toId == #session.loginMember.id">
												<a id="colotoa" class="delete_score"
													href="javascript:delAppraise('2','<s:property value="id"/>');">[删除]</a>
											</s:if></span></span>
								</p>
								<p>
									<span><s:property value="reContent" /></span>
								</p>
							</dd>
						</dl>
					</div>
				</s:if>
			</div>
		</s:iterator>
	</div>
	<s:include value="/share/pageAjax.jsp" />
</s:form>
<div id="xg" class="xiugai" title="回复评价">
	<s:form name="reContentForm" id="reContentForm" theme="simple">
		<input type="hidden" name="appraise.id" />
		<div class="xiugai-3">
			<span>回复内容：</span>
			<textarea name="appraise.reContent" cols="30" class="textea"
				id="editor1"></textarea>
		</div>
		<div class="xiugai-5">
			<input type="button" name="button" class="save" value="回复"
				onclick="onSave();" /> <input type="button" name="button"
				class="closea" value="关闭" onclick="onClose();" />
		</div>
	</s:form>
</div>
		</div>
	</div>
	<s:include value="/share/footer.jsp" />
</body>
</html>