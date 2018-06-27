<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="this is my page">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>花样百出12341234</title>
<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="css/buttons.css" />
</head>
<body oncontextmenu="return false;">
<div id="topdiv" style="height: 105px;width:100%;background-image:url(images/header_left.gif);">
	<table cellspacing="0" cellpadding="0" style="border:0px;width:100%;height:80px;">
		<tr>
			<td><img src="images/logo.gif" height="80"></img></td>
			<td width="30%">&nbsp;</td>
			<td width="300" align="right" valign="bottom" style="background-image:url(images/header_right.gif)">
					<a href="#" onclick="javascript:openWin();" style="color:#fff;">密码</a>&nbsp;&nbsp;
					<a href="logout.asp" style="color:#ffffff;">退出</a>
			</td>
		</tr>
	</table>
</div>
<script type="text/javascript" src="script/text.js"></script>
<script type="text/javascript" src="script/loading.js"></script>
<script type="text/javascript" src="script/ext-all.js"></script>
<script type="text/javascript" src="resources/script/cardcol-common.js"></script>
<script type="text/javascript" src="script/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="script/ext-lang.js"></script>
<script type="text/javascript">
var tabs = null, mainView, tabSize = 5, mainStyle = 0;
var menus = <s:property value="#request.models" escape="false" />,
	userName = '<s:property value="#session.loginUser.name"/>';
Ext.ACTION_SUFFIX = '.asp';
Ext.onReady(function(){
	mainView = Ext.create('Ext.ux.MainView', {
		mainStyle: 0,
		userName: userName,
		menus: menus
	});
});
function nodeClick(view, rs, item) {
	var exist = false, _leaf = rs.get('leaf'), _id = rs.get('id'), _title = rs.get('text'), 
		_code = rs.get('code'), _progId = 'program' + _id, _limit = rs.get('limit');
	if (_leaf === false) {
		view.expand(rs);
		return;
	}
	var _link = _code + Ext.ACTION_SUFFIX + '?programId=' + _id;
	mainView.setProgram({id: _id, code: _code, name: _title, url: _link});
}
</script>
<script type="text/javascript" src="script/initial.js"></script>
<script type="text/javascript" src="script/loaded.js"></script>
</body>
</html>

