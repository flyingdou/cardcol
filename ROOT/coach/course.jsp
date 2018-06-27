<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="健身教练，私人教练" />
<meta name="description" content="健身教练-我的设定" />
<title>健身教练-我的设定</title>
<link href="css/user-config.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="css/pulicstyle.css" />
<script type="text/javascript" src="script/jquery.form.js"></script>
<script type="text/javascript" src="script/jquery.parser.js"></script>
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
							var $td = $(selecteds[0]).parent('td');
							$('#courseId').val($td.children().eq(1).val());
							$('#courseName').val($td.children().eq(2).val());
							$('#courseType').val($td.children().eq(3).val());
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
			if (confirm('請確認是否需要刪除當前選擇的課程？')) {
				$.ajax({
					type : 'post',
					url : 'mycourse!delete.asp',
					data : params,
					success : function(resp) {
						if (resp == 'OK') {
							$(selecteds).parents('tr').remove();
							alert('當前所有選擇的課程已經成功刪除！');
						} else {
							alert(resp);
						}
					}
				});
			}
		});
	})
	function onDown(the) {
		var $tr = $(the).parents('tr');
		$tr.next('tr').after($tr);
		sorts();
	}
	function onUp(the) {
		var $tr = $(the).parents('tr');
		$tr.prev('tr').before($tr);
		sorts();
	}
	function sorts() {
		var $table = $('#listcontianer');
		var trs = $table.find('tr'), $tr;
		var len = trs.length;
		for (var i = 1; i < len; i++) {
			$tr = $(trs[i]);
			$tr.children('td:eq(0)').children('input:eq(6)').val(i);
			if (i == 1)
				$tr
						.children('td:last')
						.html(
								'<img src="images/allow5.gif" alt="down" onclick="onDown(this);"/>');
			else if (i == len - 1)
				$tr
						.children('td:last')
						.html(
								'<img src="images/allow4.gif" alt="up" onclick="onUp(this)" />');
			else
				$tr
						.children('td:last')
						.html(
								'<img src="images/allow5.gif" alt="down" onclick="onDown(this)"/><img src="images/allow4.gif" alt="up" onclick="onUp(this)" />');
		}
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
</script>
</head>
<body>
	<s:include value="/share/header.jsp" />
	<div id="content">
		<div class="my-setting">
			<ul id="nextstep">
				<li class="first">一.设定风格</li>
				<li class="last">二.设定课程</li>
				<li>三.设定健身项目</li>
				<li>四.设定身体部位</li>
				<li>五.设定训练动作</li>
				<li>六.完成</li>
			</ul>
		</div>
		<s:form id="form" name="form" action="" method="post" theme="simple">
			<div id="center-1">
				<div class="configall">
					<table id="listcontianer" class="conftable">
						<tr class="tableTitle">
							<td id="tdtop" width=3%></td>
							<td width=15% id="tdtop">课程名称</td>
							<td width=8% id="tdtop">课程类型</td>
							<td width=8% id="tdtop">运动强度</td>
							<td width=60% id="tdtop">课程简介</td>
							<td width=5% id="tdtop">操作</td>
						</tr>
						<s:iterator value="courses" status="st">
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
								<td><s:property value="name" /></td>
								<td><s:property value="type.name" /></td>
								<td><s:property value="intensity" /></td>
								<td
									style="text-align: left; padding-left: 10px; text-indent: 2em;"><s:property
										value="memo" escape="false" /></td>
								<td><s:if test="#st.first">
										<img src="images/allow5.gif" alt="down"
											onclick="onDown(this);" />
									</s:if> <s:elseif test="#st.last">
										<img src="images/allow4.gif" alt="up" onclick="onUp(this);" />
									</s:elseif> <s:else>
										<img src="images/allow5.gif" alt="down"
											onclick="onDown(this);" />
										<img src="images/allow4.gif" alt="up" onclick="onUp(this);" />
									</s:else></td>
							</tr>
						</s:iterator>
					</table>
					<div class="saveoperate">
						<span><a id="btnAdd" class="adat">新增</a></span> <span><a
							id="btnEdit" class="asev">修改</a></span> <span><a id="btnDel"
							class="adel">删除</a></span>
					</div>
				</div>
			</div>
		</s:form>
		<div class="stepoperate">
			<a href="javascript:prev();" title="上一步" class="butlost">上一步</a> <a
				href="javascript:next();" title="下一步" class="butnext">下一步</a>
		</div>
	</div>
	<div id="dow" title="课程编辑">
		<s:form name="editform" id="editform" theme="simple"
			enctype="multipart/form-data">
			<s:hidden name="course.id" id="courseId" />
			<s:hidden name="course.image" id="courseImage" />
			<div id="dow1">
				<p class="pleft">
					课程名称: <input type="text" value="" name="course.name"
						id="courseName" class="pltext" /> <span class="pleft1">课程类型:
						<s:select list="#request.courseTypes" listKey="id"
							listValue="name" name="course.type.id" id="typeId" />
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
					<span class="pdown1"><input type="button" value="保存"    style="text-align:center;"
						class="but" id="btnSave" /></span> <span class="pdown2"><input
						type="button" value="关闭" class="but" id="btnClose"    style="text-align:center;"  /></span>
				</p>
			</div>
		</s:form>
	</div>
	<s:include value="/share/footer.jsp" />
</body>
</html>
