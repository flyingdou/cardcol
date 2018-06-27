<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<script type="text/javascript" src="script/jquery.form.js"></script>
<script type="text/javascript" src="script/jquery.parser.js"></script>
<meta name="keywords" content="我的账户" />
<meta name="description" content="我的账户" />
<title>健身E卡通-我的账户</title>
<link href="css/user-account.css" rel="stylesheet" type="text/css" />
<link href="css/pulicstyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	var oldVal = '';
	var compVal = '证书名称,证书编号,发证机构,发证日期,';
	$(function() {
		$('.a2').click(
				function() {
					var len = $('#divcontent').children('p').length / 2;
					var html = $('#samples').html().replace(
							new RegExp("XXXX", "gm"), len);
					$('#divcontent').append(html);
					$('#divcontent').children('p').children(
							'input[type="text"]').bind({
						focus : onFocus,
						blur : onBlur
					});
				});
		$('.a3').click(function() {
			$('#certform').form('submit', {
				url : 'cert!save.asp',
				onSubmit : function(param) {
					return true;
				},
				success : function(msg) {
					if (msg == 'OK') {
						alert('当前数据已经成功保存！');
						$('#certform').attr('action', 'cert.asp');
						$('#certform').submit();
					} else {
						alert(msg);
					}
				}
			});
		});
	});
	function onDelete(the) {
		var $p = $(the).parent('p');
		var val = $p.children('input:eq(0)').val();
		if (val == '') {
			$p.next('p').remove();
			$p.remove();
		} else {
			if (confirm('是否确认删除当前数据？')) {
				$.ajax({
					type : 'post',
					url : 'cert!delete.asp',
					data : 'id=' + val,
					success : function(msg) {
						if (msg == 'OK') {
							alert('当前数据已经成功删除！');
							$p.next('p').remove();
							$p.remove();
						} else {
							alert(msg);
						}
					}
				});
			}
		}
	}

	function onFocus() {
		if (compVal.indexOf($(this).val()) != -1) {
			oldVal = $(this).val();
			$(this).val('');
		}
	}
	function onBlur() {
		if ($(this).val() == '')
			$(this).val(oldVal);
	}
	//实时显示上传的图片
	function setImagePreview() {
		var docObj = document.getElementById("file");
		var imgObjPreview = document.getElementById("preview");
		if (docObj.files && docObj.files[0]) {
			//火狐下，直接设img属性 
			imgObjPreview.style.display = 'block';
			imgObjPreview.style.width = '130px';
			imgObjPreview.style.height = '130px';
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
		<s:include value="/basic/nav.jsp" />
		<div id="right-2">
			<div id="certcont">
				<h1>证书验证</h1>
				<s:form name="certform" id="certform" theme="simple"
					enctype="multipart/form-data" method="post" action="cert!save.asp">
					<div id="divcontent">
						<s:iterator value="certs" status="st">
							<p>
								<s:date name="" />
								<s:hidden name="%{'certs['+#st.index+'].id'}" />
								<s:hidden name="%{'certs['+#st.index+'].fileName'}" />
								<s:textfield name="%{'certs['+#st.index+'].name'}"
									cssClass="name-2" />
								<s:textfield name="%{'certs['+#st.index+'].no'}"
									cssClass="name-2" />
								<s:textfield name="%{'certs['+#st.index+'].authority'}"
									cssClass="name-2" />
								<input type="text"
									name="certs[<s:property value="#st.index"/>].authTime"
									class="name-2"
									value="<s:date name="authTime" format="yyyy-MM-dd"/>" /> <a
									href="#" class="a1" onclick="onDelete(this)">[删除]</a>
							</p>
							<p id="certificate" style="height: 60px;">

								<s:if test="fileName != null">
									<img src="picture/<s:property value="fileName"/>" />
								</s:if>
								<s:else>
					上传证书：<s:file class="name-3" name="file.files" />
									<img id="preview" />
								</s:else>
							</p>
						</s:iterator>
					</div>
				</s:form>

				<a href="#" class="a2" id="colotoa">[添加证书] </a><a href="#"
					class="a3" id="colotoa">[保存]</a>
				</p>
				<p class="certificate">
					<span class="span1">上传清晰的证书图片，支持jpg、png、gif格式，大小不超过2M/张。</span>
				</p>
			</div>
		</div>
	</div>
	<s:include value="/share/footer.jsp" />
	<div id="samples">
		<p>
			<input type="hidden" name="certs[XXXX].id" value="" /> <input
				type="text" name="certs[XXXX].name" class="name-2" value="证书名称" />
			<input type="text" name="certs[XXXX].no" class="name-2" value="证书编号" />
			<input type="text" name="certs[XXXX].authority" class="name-2"
				value="发证机构" /> <input type="text" name="certs[XXXX].authTime"
				class="name-2" value="发证日期" /> <a href="#" class="a1"
				onclick="onDelete(this)">[删除]</a>
		</p>
		<p>
			上传证书：
			<s:file class="name-3" name="file.files" id="file"
				onchange="setImagePreview();" />
		</p>
		<img id="preview" />
	</div>

</body>
</html>
