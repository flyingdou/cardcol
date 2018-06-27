<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="健身E卡通—我的设定" />
<meta name="description" content="健身E卡通—我的设定" />
<title>健身E卡通—我的设定</title>
<link href="css/user-config.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="css/pulicstyle.css" />
<script src="script/ckeditor/ckeditor.js" type="text/javascript"></script>
<script type="text/javascript" src="script/uploadPreview.js"></script>
<script type="text/javascript">
	var editor;
	$(function() {
		new uploadPreview({
			UpBtn : "file",
			ImgShow : "preview"
		});
		editor = CKEDITOR.replace('courseMemo', {
			extraPlugins : 'uicolor',
			toolbar : [ [ 'Source', '-', 'Preview', '-', 'Cut', 'Copy',
					'Paste', 'PasteText', '-', 'SpellChecker', 'Scayt', 'Bold',
					'Italic', 'Underline', 'Strike', 'Subscript', 'Format',
					'Font', 'FontSize' ] ]
		});
		$('#dow').dialog({
			autoOpen : false,
			width : 750
		});
		$('#btnSave').click(function() {
			if ($('#courseName').val() == '') {
				alert('请输入课程名称!');
				return;
			}
			$(document).mask('请稍候，正在保存数据......');
			$('#editform').attr('action', 'mycourse!save.asp');
			$('#editform').submit();
			$(document).unmask();
		});
		$('#btnClose').click(function() {
			$('#dow').dialog('close');
		});
		$('#btnAdd').click(function() {
			$('#editform')[0].reset();
			$('#dow').dialog('open');
		});
		$('#btnEdit')
				.click(
						function() {
							var selecteds = $('input[type="checkbox"][name="ids"]:checked');
							if (selecteds.length <= 0) {
								alert('请先选择需要编辑的数据！');
								return;
							}
							var $td = $(selecteds[0]).parents('td');
							$('#courseId').val($td.children().eq(1).val());
							$('#courseName').val($td.children().eq(2).val());
							$('#typeId').val($td.children().eq(3).val());
							$('#intensity').val($td.children().eq(4).val());
							$('#courseImage').val($td.children().eq(8).val());
							if ($('#courseImage').val() != null
									&& $('#courseImage').val() != '') {
								$('#preview').attr('src',
										'picture/' + $('#courseImage').val());
							} else {
								$('#preview').attr('src',
										'images/img-defaul.jpg');
							}
							CKEDITOR.instances['courseMemo'].setData($td
									.children().eq(5).val());
							$('#dow').dialog('open');
						});
		$('#btnDel').click(function() {
			var selecteds = $('input[type="checkbox"][name="ids"]:checked');
			if (selecteds.length <= 0) {
				alert('请先选择需要删除的数据！');
				return;
			}
			var params = '';
			for (var i = 0; i < selecteds.length; i++) {
				params += 'ids=' + $(selecteds[i]).val() + '&';
			}
			$.ajax({
				type : 'post',
				url : 'mycourse!delete.asp',
				data : params,
				success : function() {
					$(selecteds).parents('tr').remove();
				}
			});
		});
	})
	function sortIcon() {
		var $tr = $('#stuTgtIdxsContianer').find('tr');
		var len = $tr.length, $div;
		for (var i = 1; i < len; i++) {
			$div = $($tr[i]).children('td:eq(5)').children('div');
			$($tr[i]).children('td:eq(0)').children('input:eq(6)').val(i);
			if (i == 1) {
				if (i < len)
					$div
							.html("<img src='images/allow5.gif' onclick='javascript:onDown(this);'/>");
			} else {
				if (i < len - 1)
					$div
							.html("<img src='images/allow4.gif' onclick='javascript:onUp(this);'/><img src='images/allow5.gif' onclick='javascript:onDown(this);'/>");
				else
					$div
							.html("<img src='images/allow4.gif' onclick='javascript:onUp(this);'/>");
			}
		}
	}
	function onDown(the) {
		$(the).parents('tr').next('tr').after($(the).parents('tr'));
		sortIcon();
	}
	function onUp(the) {
		$(the).parents('tr').prev('tr').before($(the).parents('tr'));
		sortIcon();
	}
	function prev() {
		$('#form').attr('action', 'mycourse!prev.asp');
		$('#form').submit();
	}
	function next() {
		$('#form').attr('action', 'mycourse!next.asp');
		$('#form').submit();
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
	
	
	function queryByPage(currentPage){
		var params = {
				"pageInfo.currentPage": currentPage,
		}
		$.ajax({type:"post",url:"mycourse.asp",data:params,
			success:function(msg){
				$("body").html(msg);
			}
		});
	}
</script>
</head>
<body>
	<s:include value="/share/header.jsp" />
	<div id="content">
		<div class="my-setting">
			<ul id="nextstep">
				<li class="first">一.场地设定</li>
				<li class="last">二.课程设定</li>
				<li>三.私教分成比例</li>
				<li>四.营业时间</li>
				<li>五.完成</li>
			</ul>
		</div>
		<s:form id="form" name="form" action="" method="post" theme="simple">
			<div id="center-1">
				<div class="configall">
					<table id="stuTgtIdxsContianer" class="conftable" style="table-layout: fixed;">
						<tr class="tableTitle">
							<td nowrap id="tdtop" width=3%></td>
							<td nowrap id="tdtop" width=10%>课程名称</td>
							<td nowrap id="tdtop" width=10%>课程类型</td>
							<td nowrap id="tdtop" width=8%>运动强度</td>
							<td nowrap id="tdtop" width=63%>课程简介</td>
							<td nowrap id="tdtop" width=7%>操作</td>
						</tr>
						<s:iterator value="courses" status="st" id="c">
							<tr class="bc0">
								<td><input type="checkbox" name="ids"
									value="<s:property value="id"/>" /> <input type="hidden"
									name="courses[<s:property value="#st.index"/>].id"
									value="<s:property value="id"/>" /> <input type="hidden"
									name="courses[<s:property value="#st.index"/>].name"
									value="<s:property value="name"/>" /> <input type="hidden"
									name="courses[<s:property value="#st.index"/>].type.id"
									value="<s:property value="type.id"/>" /> <input type="hidden"
									name="courses[<s:property value="#st.index"/>].intensity"
									value="<s:property value="intensity"/>" /> <input
									type="hidden"
									name="courses[<s:property value="#st.index"/>].memo"
									value="<s:property value="memo"/>" /> <input type="hidden"
									name="courses[<s:property value="#st.index"/>].sort"
									value="<s:property value="sort"/>" /> <input type="hidden"
									name="courses[<s:property value="#st.index"/>].member.id"
									value="<s:property value="member.id"/>" /> <input
									type="hidden"
									name="courses[<s:property value="#st.index"/>].image"
									value="<s:property value="image"/>" /></td>
								<td nowrap><s:property value="name" /></td>
								<td nowrap><s:property value="type.name" /></td>
								<td nowrap><s:property value="intensity" /></td>
								<td style="text-align: left; padding-left: 10px;word-wrap: break-word;word-break: break-all;"><s:property value="memo" escape="false" /></td>
								<td nowrap><div>
										<s:if test="#st.first">
											<img src='images/allow5.gif'
												onclick='javascript:onDown(this);' />
										</s:if>
										<s:elseif test="#st.last">
											<img src='images/allow4.gif' onclick='javascript:onUp(this);' />
										</s:elseif>
										<s:else>
											<img src='images/allow5.gif'
												onclick='javascript:onDown(this);' />
											<img src='images/allow4.gif' onclick='javascript:onUp(this);' />
										</s:else>
									</div></td>
							</tr>
						</s:iterator>
					</table>
					<div class="saveoperate">
					<s:if test="pageInfo.currentPage > 1">
						<span class="stepoperate"><a href="javascript:queryByPage('<s:property value="pageInfo.currentPage-1"/>');" title="上一页" class="butlost">上一页</a></span>
						</s:if>
						<span><a id="btnAdd" class="adat">新增</a></span> <span><a
							id="btnEdit" class="alter">修改</a></span> <span><a id="btnDel"
							class="adel">删除</a></span>
							<s:if test="pageInfo.currentPage < pageInfo.totalPage">
							<span class="stepoperate"><a href="javascript:queryByPage('<s:property value="pageInfo.currentPage+1"/>');" title="下一页" class="butnext">下一页</a></span>
							</s:if>
					</div>
				</div>
			</div>
		<!-- 分页信息 -->
<s:hidden name="pageInfo.pageSize" id="pageSize"/>
<s:hidden name="pageInfo.currentPage" id="currentPage"/>
<s:hidden name="pageInfo.totalPage" id="totalPage"/>
<s:hidden name="pageInfo.totalCount" id="totalCount"/>
<s:hidden name="pageInfo.splitFlag" id="splitFlag"/>
<s:hidden name="pageInfo.order" id="order"/>
<s:hidden name="pageInfo.orderFlag" id="orderFlag"/>
		</s:form>
		<div class="stepoperate">
			<a href="javascript:prev();" title="上一步" class="butlost">上一步</a> <a
				href="javascript:next();" title="下一步" class="butnext">下一步</a>
		</div>
	</div>
	<div id="dow" title="课程编辑">
		<s:form name="editform" id="editform" theme="simple"
			enctype="multipart/form-data" method="post">
			<s:hidden name="course.id" id="courseId" />
			<s:hidden name="course.image" id="courseImage" />
			<div id="dow1">
				<p class="pleft">
					课程名称: <input type="text" value="" name="course.name"
						id="courseName" style="border: 1px solid #ccc;" /> <span
						class="pleft1">课程类型: <s:select list="#request.courseTypes"
							listKey="id" listValue="name" name="course.type.id" id="typeId" />
					</span> <span class="pleft2">运动强度: <s:select
							list="#{'1':'一级','2':'二级','3':'三级','4':'四级','5':'五级'}"
							name="course.intensity" id="intensity" /></span>
				</p>
				<p class="pleft">
					上传缩略图: <img id="preview" width="60px" height="60px"
						<s:if test="course==null || course.image == null || course.image == ''">src="images/img-defaul.jpg"</s:if>
						<s:else> src="picture/<s:property value='course.image'/>"</s:else>
						onclick="javascript: $('#file').click();" />
					<s:file id="file" name="image" style="display: none;" />
				</p>
				<p class="pcenter">
					<s:textarea name="course.memo" id="courseMemo" cols="30" rows="10" />
				</p>
				<p>
					<span class="pdown1"><input type="button" value="保存"
						class="but" id="btnSave" /></span> <span class="pdown2"><input
						type="button" value="关闭" class="but" id="btnClose" /></span>
				</p>
			</div>
		</s:form>
	</div>
	<s:include value="/share/footer.jsp" />
</body>
</html>
