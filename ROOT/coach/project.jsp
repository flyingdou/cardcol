<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp"/>
<meta name="keywords" content="健身E卡通-我的设定" />
<meta name="description" content="健身E卡通-我的设定" />
<title>健身E卡通-我的设定</title>
<link href="css/user-config.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="css/pulicstyle.css"/>
<script src="script/ckeditor/ckeditor.js" type="text/javascript"></script>
<script type="text/javascript" src="script/project-setting.js" language="javascript"></script>
</head>
<body>
	<s:include value="/share/header.jsp"/>
	<div id="content">
		<div class="my-setting">
			<ul id="nextstep">
				<li class="first">一.设定风格</li>
				<li>二.设定课程</li>
				<li class="last">三.设定健身项目</li>
				<li>四.设定身体部位</li>
				<li>五.设定训练动作</li>
				<li >六.完成</li>
			</ul>
		</div>
		<div id="center-1">
			<div class="configall">
				<div id="projectcontainer">
					<s:include value="/coach/project_content.jsp"/>
				</div>
				<div class="saveoperate">
					<span><a href="javascript:onAdd();" class="adat">新增</a></span>
					<span><a href="javascript:onEdit();" class="asev">编辑</a></span>
					<span><a href="javascript:onDelete();" class="adel">删除</a></span>
				</div>
			</div>
		</div>
		<div class="stepoperate">
			<a href="javascript:prevStep();" title="上一步" class="butlost">上一步</a>
			<a href="javascript:nextStep();" title="下一步" class="butnext">下一步</a>
		</div>
	</div>
	<s:include value="/share/footer.jsp"/>
	<div class="xiugai" title="修改健身项目资料">
		<s:form name="form1" id="form1" theme="simple" target="saveframe">
		<s:hidden name="project.id" id="keyId"/>
		<div class="xiugai-1">
			<span>*</span>项目名称： <input name="project.name" class="text" /><span style="color:black; margin-left:100px;"><span>*</span>简称： <input name="project.shortName" class="text" /></span>
		</div>
		<div class="xiugai-4">
			<span>*</span>训练动作描述模式：<s:select list="#{'0':'有氧训练模式','1':'力量训练模式','2':'柔韧训练模式','3':'普拉提训练模式'}" listKey="key" listValue="value" name="project.mode"/>
		</div>
		<div class="xiugai-3">
			<span>训练描述内容：</span>
			<div style="margin-top:10px;">
			<textarea name="project.memo" cols="30" style="vertical-align: top;" id="editor1"></textarea></div>
		</div>
		<div class="xiugai-5">
			<input type="button" name="button" class="button" value="保存" onclick="onSave();"/>
			<input type="button" name="button" class="button" value="关闭" onclick="onClose();"/>
		</div>
		</s:form>
		<iframe name="saveframe" id="saveframe" style="display:none;"></iframe>
	</div>
</body>
</html>