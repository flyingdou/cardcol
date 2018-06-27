<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="我的社区" />
<meta name="description" content="我的社区" />
<title>我的社区</title>
<link  rel="stylesheet" type="text/css" href="css/sns-fatbowen.css" />
<script src="script/ckeditor/ckeditor.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	CKEDITOR.replace('editor1');
	$('.fenlei').dialog({autoOpen: false, width: 540,height: 260,modal:true});
	$('.cfenlei').click(function(){
		$('.fenlei').dialog('open');
	});
	$('#btnCreate').click(function(){
		if ($('input[name="category.name"]').val() == '') {
			alert('请输入分类名称后再创建！');
			return false;
		}
		var params = $('#form2').serialize();
		$.ajax({type:'post',url:'sns!saveCategory.asp',data: params,
			success: function(msg){
				var json = $.parseJSON(msg);
				if (json.success === false) {
					alert(json.message);
					return;
				}
				var $container = $('#categorycontainer');
				var html = $('#samples').html().replace(new RegExp("XXXX", "gm"), json.message.name + '<input type="hidden" value="' + json.message.id + '"/>');
				$container.append(html);
			}
		});
	});
	$('#btnCg').click(function(){
		
	});
	$('#btnView').click(function(){
		
	});
	$('#btnSend').click(function(){
		$('input[name="blog.status"]').val('1');
		$('input[name="blog.type"]').val($('input[name="query.type"]').val());
		$('#form').attr('action', 'sns!save.asp');
		$('#form').submit();
	});
});
</script>
</head>
<body>
	<s:include value="/share/header.jsp" />
	<div id="content">
		<h1>发博文</h1>
		<s:form name="form" id="form" theme="simple" method="post">
		<s:hidden name="query.type" id="type"/>
		<s:hidden name="blog.id"/>
		<s:hidden name="blog.member"/>
		<input type="hidden" name="blog.createTime" value="<s:date name="blog.createTime" format="yyyy-MM-dd hh:mm:ss" />"/>
		<s:hidden name="blog.status"/>
		<s:hidden name="blog.type"/>
		<p class="first">
			标题：<s:textfield name="blog.title" cssClass="title" /> 时间: <s:date name="blog.createTime" format="yyyy-MM-dd hh:mm:ss" />
		</p>
		<div class="bowen-content">
			<s:textarea cols="80" id="editor1" name="blog.content" rows="10"></s:textarea>
		</div>

		<p class="second">
			分类：<s:select list="#request.categorys" listKey="id" listValue="name" headerKey="" headerValue="" name="blog.category.id"/><a class="cfenlei">创建分类</a>
		</p>
		<p class="third">
			标签： <s:textfield name="blog.label" cssClass="tags" /> <span>标签用英文字母或空格分开</span>
		</p>
		<p class="fourth">
			<input type="button" name="button" class="button" value="预览博文" id="btnView"/>
			<input type="button" name="button" class="button" value="保存到草稿箱" id="btnCg"/>
		</p>
		<p class="fifth">
			<input type="button" name="button" class="button" value="发博文" id="btnSend"/>
		</p>
	</s:form>
	</div>
	<s:include value="/share/footer.jsp"/>

	<div class="fenlei" title="分类管理">
		<div class="fenlei-1">
			<s:form name="form2" id="form2" theme="simple">
			<ul>
				<li><input type="text" name="category.name" class="text" value="" /><input type="hidden" name="category.id"/></li>
				<li><input type="button" name="button" class="button" value="创建分类" id="btnCreate"/></li>
				<li>请用中文、英文或数字。</li>
			</ul>
			</s:form>
		</div>
		<div id="categorycontainer">
			<s:iterator value="#request.categorys">
			<div class="fenlei-2">
				<ul>
					<li class="first"><s:property value="name"/><s:hidden name="id"/></li>
					<li><a href="#">[编辑]</a></li>
					<li><a href="#">[删除]</a></li>
					<li><a href="#" class="up"></a></li>
					<li><a href="#" class="down"></a></li>
				</ul>
			</div>
			<p class="line"></p>
			</s:iterator>
		</div>
		<p class="input">
			<input type="button" class="button" value="保存设置" id="btnSave"/>
		</p>
	</div>
	<div id="samples" style="display:none;">
		<div class="fenlei-2">
			<ul>
				<li class="first">XXXX</li>
				<li><a onclick="javascript:onEdit();">[编辑]</a></li>
				<li><a onclick="javascript:onDelete();">[删除]</a></li>
				<li><a onclick="" class="up"></a></li>
				<li><a onclick="" class="down"></a></li>
			</ul>
		</div>
	</div>
</body>
</html>