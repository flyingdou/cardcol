<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp"/>
<meta name="keywords" content="我的社区" />
<meta name="description" content="我的社区" />
<title>我的社区</title>
<link href="css/sns.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
var type = '<s:property value="query.type"/>';
$(function(){
	$('.fabw').click(function(){
		$('#form').attr('action', 'sns!edit.asp');
		$('#form').submit();
	});
	$('.edit').click(function(){
		var key = $(this).attr('key');
		$('#key').val(key);
		$('#form').attr('action', 'sns!edit.asp');
		$('#form').submit();
	});
	$('.delete').click(function(){
		var $the = $(this);
		var key = $the.attr('key');
		if (confirm('是否确认删除当前博文？')) {
			$.ajax({type:'post',url:'sns!delete.asp', data: 'id=' + key, 
				success:function(){
					$the.parents('div[id="blogrow"]').remove();
				}
			});
		}
	});
	$('.stick').click(function(){
		var $the = $(this);
		var key = $the.attr('key');
		var stick = $the.attr('stick') == '0' ? '1' : '0';
		$.ajax({type:'post',url:'sns!stick.asp', data: 'id=' + key + '&stick=' + stick, 
			success:function(){
				$the.html(stick == 1 ? "撤消置顶" : '置顶');
				$the.attr('stick', stick);
			}
		});
	});
});
</script>
</head>
<body>
	<s:include value="/share/header.jsp"/>
	<div id="content">
		<div id="left-1">
			<s:include value="/share/sns_nav.jsp"/>
		</div>
		<div id="right-2">
			<h1>服务项目</h1>
			<div>
				<div class="div1">
					<a class="fabw" target="_blank">添加</a>
				</div>
				<div class="div2">
					<s:form name="form" id="form" theme="simple" method="post" target="_blank">
					<s:hidden name="query.type" />
					<input type="hidden" name="id" id="key"/>
					<s:iterator value="pageInfo.items">
					<div id="blogrow">
						<ul class="fatbowen">
							<li class="first"><a href="service!view.asp?id=<s:property value="id"/>"><s:property value="name"/></a></li>
							<li>[<a class="edit" key="<s:property value="id"/>">编辑</a>]</li>
							<li>[<a class="delete" key="<s:property value="id"/>">删除</a>]</li>
							<li>[<a class="stick" key="<s:property value="id"/>" stick="<s:if test="stickTime == null">0</s:if><s:else>1</s:else>"><s:if test="stickTime == null">置顶</s:if><s:else>撤消置顶</s:else></a>]</li>
						</ul>
						<p class="line"></p>
					</div>
					</s:iterator>
					</s:form>
				</div>
				<div class="pagenumber1">
					<ul>
						<li><a href="#">1</a></li>
						<li><a href="#">2</a></li>
						<li><a href="#">3</a></li>
						<li><a href="#">4</a></li>
						<li class="end1"><a href="#" class="end1">下一页</a></li>
						<li class="end1">共4页</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<s:include value="/share/footer.jsp"/>
</body>
</html>