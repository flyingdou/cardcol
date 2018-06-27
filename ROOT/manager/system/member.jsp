<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="divmember1"></div>
<script type="text/javascript">
var _funcs = <s:property value="#request.funcs"/>;
var member1 = [
	{name:'id',type: 'int'},
	{name:'email',type:'string'},
	{name:'nick',type:'string'},
	{name:'name',type:'string'},
	{name:'sex',type:'string'},
	{name:'regDate',type:'string'},
	{name:'birthday',type:'string'},
	{name:'tell',type:'string'},
	{name:'mobilephone', type: 'string'},
	{name:'province',type:'string'},
	{name:'city',type:'string'},
	{name:'county',type: 'string'},
	{name:'address',type:'string'},
	{name:'role',type:'string'},
	{name:'image',type:'string'},
	{name:'speciality',type:'string'},
	{name:'mode',type:'string'},
	{name:'style',type:'string'},
	{name:'rate',type:'string'},
	{name:'workDate',type:'string'},
	{name:'startTime',type:'string'},
	{name:'endTime',type:'string'},
	{name:'grade',type:'string'},
	{name:'socure',type:'string'},
	{name:'hasValid', type:'string'},
	{name:'coachId', type: 'int'},
	{name:'coachName', type: 'string'},
	{name:'agencyId', type: 'int'},
	{name:'agencyName', type: 'string'},
	{name:'longitude', type: 'float'},
	{name:'latitude', type: 'float'},
	{name:'emailValid', type: 'string'},
	{name:'mobileValid', type:'string'}
];
var panel;
Ext.onReady(function(){
	var _lbars = [], _othBars = [], i = 0, j = 0;
	if (_funcs.btnValid && _funcs.btnValid === true)
		_lbars[i++] = {xtype:'splitbutton',text:'校验',iconCls:'x-button-query',handler: onValid,menu:[{text:'撤消校验',handler: onUndoValid}]};
	if (_funcs.btnOpen && _funcs.btnOpen === true)
		_lbars[i++] = {xtype:'splitbutton',text:'开通',iconCls:'x-button-query',handler: onOpen,menu:[{text:'撤消开通',handler: onUndoOpen}]};
	if (_funcs.btnLocation && _funcs.btnLocation === true)
		_othBars[j++] = {text:'地理位置',handler: setPosition};
	if (_funcs.btnMsg && _funcs.btnMsg === true)
		_othBars[j++] = {text:'系统消息', handler: showMsg};
	if (_funcs.btnSms && _funcs.btnSms === true)
		_othBars[j++] = {text:'短信群发', handler: showSms};
	if (_funcs.btnEmail && _funcs.btnEmail === true)
		_othBars[j++] = {text:'邮件群发', handler: showEmail};
	if (_funcs.btnClEmail && _funcs.btnClEmail === true)
		_othBars[j++] = {text:'清除邮件', handler: onCleanEmail};
	if (_funcs.btnClPhone && _funcs.btnClPhone === true) 
		_othBars[j++] = {text:'清除电话', handler: onCleanPhone};
	if (_funcs.btnSetMobile && _funcs.btnSetMobile === true)
		_othBars[j++] = {text:'设置手机', handler: onSetMobile};
	if (_funcs.btnMobile && _funcs.btnMobile === true)
		_othBars[j++] = {text:'验证手机', handler: onVerifyMobile};
	if (_othBars.length > 0)
		_lbars[i++] = {xtype:'splitbutton',text:'其它功能',iconCls:'x-button-query',menu: _othBars};
	panel = Ext.create('Ext.ux.GridFormPanel', {
		id: 'member1',
		formTitle: '会员信息',
		formWidth: 660,
		formHeight: 480,
		locked: true,
		hasLog: true,
		funcs: _funcs,
		lbars: _lbars,
		recommendTitleField: 'name',
		where: ['会员邮件',{xtype:'textfield',id:'email',width:80},
		    '会员名称:',{xtype:'textfield',id:'name',width: 80},
		    '手机号码:',{xtype:'textfield',id:'mobilephone',width: 80},
		    '会员角色:',{xtype:'combox',id:'role',loadUrl:'member1!findRole.asp',valueField:'code',root:'items', width:80}
		],
		columns: [
		    {header:'昵称',dataIndex:'name',width:100,locked: true},
			{header:'账号',dataIndex:'nick',locked:true,width:140},
		    {header:'角色类型',dataIndex:'role',width:100,renderer:rendType},
		    {header:'会员级别',dataIndex:'grade',width: 80,renderer: rendGrade},
		    {header:'注册时间',dataIndex:'regDate',width: 120},
			{header:'邮箱',dataIndex:'email',width:150},
			{header:'邮箱是否验证',dataIndex:'emailValid', width: 100, renderer: rendYesNo},
			{header:'联系电话',dataIndex:'tell',width:100},
			{header:'移动电话',dataIndex:'mobilephone', width: 100},
			{header:'手机是否验证',dataIndex:'mobileValid', width: 100, renderer: rendYesNo},
			{header:'联系地址',dataIndex:'address',width:150},
			{header:'性别',dataIndex:'sex',width:60},
			{header:'生日',dataIndex:'birthday',width: 100, renderer: rendDate},
			{header:'是否校验',dataIndex:'hasValid', width: 80,renderer: rendValid},
			{header:'私教',dataIndex: 'coachName', width: 100},
			{header:'省',dataIndex:'province',width: 100},
			{header:'市',dataIndex:'city',width: 100},
			{header:'推荐时间',dataIndex:'recommTime',width: 120},
			{header:'推荐标题',dataIndex:'recommTitle',width: 140},
			{header:'经度',dataIndex:'longitude',width: 140},
			{header:'纬度',dataIndex:'latitude',width: 140}
		],
		bean: member1
	});
	function rendGrade(v, c, r) {
		if (v == '1') {
			return '高级会员';
		}
		return '普通会员';
	}
	function rendValid(v) {
		if (v == '1') {
			return '已校验';
		} else if (v == '2') {
			return '校验失败'
		} else {
			return '未校验';
		}
	}
	function rendType(v){
		if (v == 'A')
			return '管理员';
		else if (v == 'S')
			return '教练';
		else if (v == 'E')
			return '俱乐部';
		else
			return '会员';
	}
});

function onValid() {
	var rs = panel.getRecords(), parms = '';
	for (var i = 0; i < rs.length; i++) {
		parms += 'ids=' + rs[i].get('id') + '&';
	}
	Ext.Ajax.request({url:'member1!onValid.asp',method: 'post', params: parms,
		success: function(resp){
			Ext.Msg.alert('提示', '所有选择会员的证书已经成功校验！');
			panel.onReload();
		}
	});
}

function onUndoValid() {
	var rs = panel.getRecords(), parms = '';
	for (var i = 0; i < rs.length; i++) {
		parms += 'ids=' + rs[i].get('id') + '&';
	}
	if (confirm('是否需要撤消当前所有选择的会员的校验？')) {
		Ext.Ajax.request({url:'member1!onUndoValid.asp',method: 'post', params: parms,
			success: function(resp){
				Ext.Msg.alert('提示', '所有选择会员的校验已经成功撤消！');
				panel.onReload();
			}
		});
	}
}
function onOpen(){
	var rs = panel.getRecords(), parms = '';
	if (!rs || rs.length <= 0) {
		Ext.Msg.alert('提示', '请选择需要开通的会员！');
		return;
	}
	for (var i = 0; i < rs.length; i++) {
		parms += 'ids=' + rs[i].get('id') + '&';
	}
	Ext.Msg.confirm('提示', '是否开通当前用户为高级会员？', function(btn) {
		if(btn=='yes') {
			Ext.Ajax.request({url:'member1!onOpen.asp',method: 'post', params: parms,
				success: function(resp){
					Ext.Msg.alert('提示', '所有选择会员已经成功开通为高级会员！');
					panel.onReload();
				}
			});
		}
	});
}
function onSetMobile(){
	var rs = panel.getRecords();
	if (!rs || rs.length <= 0) {
		Ext.Msg.alert('提示', '请选择需要设置手机的会员！');
		return;
	}
	var parms = '', mobile = '';
	for (var i = 0; i < rs.length; i++) {
		parms += 'ids=' + rs[i].get('id') + '&';
		if (i == 0) mobile = rs[i].get('mobilephone');
	}
	Ext.Msg.prompt('手机信息', '请输入该用户的新手机号码？', function(btn, text) {
		alert(text);
		if(btn=='ok') {
			Ext.Ajax.request({url:'member1!setMobile.asp',method: 'post', params: parms + 'mobiles=' + text,
				success: function(resp){
					Ext.Msg.alert('提示', '所有选择会员的手机号码已经成功被设置！');
					panel.onReload();
				}
			});
		}
	}, this, false, mobile);	
}
function onUndoOpen(){
	var rs = panel.getRecords(), parms = '';
	if (!rs || rs.length <= 0) {
		Ext.Msg.alert('提示', '请选择需要撤销的会员！');
		return;
	}
	for (var i = 0; i < rs.length; i++) {
		parms += 'ids=' + rs[i].get('id') + '&';
	}
	Ext.Msg.confirm('提示', '是否将当前用户更改为普通会员？', function(btn) {
		if(btn=='yes') {
			Ext.Ajax.request({url:'member1!onUndoOpen.asp',method: 'post', params: parms,
				success: function(resp){
					Ext.Msg.alert('提示', '所有选择会员已经成功撤销为普通会员！');
					panel.onReload();
				}
			});
		}
	});
}
function setPosition() {
	var rs = panel.getRecords(), parms = '';
	if (!rs || rs.length <= 0) {
		Ext.Msg.alert('提示', '请选择需要编辑的会员！');
		return;
	}
	window.showModalDialog("location.asp?id=" + rs[0].get('id'), null, "center:yes;resizable:yes;dialogWidth:880px;dialogHeight:480px;");
}
function onClose(){
	Ext.getCmp('memberRecommendWindow').close();
}
function showSms() {
	var rs = panel.getRecords();
	if (!rs || rs.length <= 0) {
		Ext.Msg.alert('提示', '请先选择需要发送信息的会员！');
		return;
	}
	var parms = '', r, mobile;
	for (var i = 0 ; i < rs.length; i++) {
		r = rs[i];
		mobile = r.get('mobilephone') == null ? '' : r.get('mobilephone');
		parms += (mobile == '' ? '' : mobile + ',');
	}
	Ext.create('Ext.window.Window', {
		id: 'smssendwin',
		title: '短信群发',
		width: 600,
		border: false,
		frame: true,
		modal: true,
		resizable: false,
		closeAction: 'destroy',
		items: [
			{xtype: 'form',	defaultType: 'textfield', defaults: {labelWidth: 60, labelAlign: 'right'}, border: false, frame: true,
				items:[
					{id: 'smsmobilephone', inputType:'hidden', value: parms},
					{xtype: 'textarea', id: 'smscontent', fieldLabel: '信息内容', anchor:'100%'}
				]
			}
		],
		buttonAlign: 'center',
		buttons: [
			{text:'发送', handler: sendSms},
			{text:'关闭', handler: function(){Ext.getCmp('smssendwin').close();}}
		]
	}).show();
}
function sendSms(){
	var _parms = 'mobiles=' + Ext.getCmp('smsmobilephone').getValue() + '&contents=' + Ext.getCmp('smscontent').getValue();
	Ext.Ajax.request({url: 'member1!sendSms.asp', params: _parms, method: 'post',
		success: function(resp){
			var msg = resp.responseText;
			if (msg == 'OK') {
				Ext.Msg.alert('提示', '当前信息已经成功发送到所有选择的会员的手机上！');
			} else {
				Ext.Msg.alert('提示', '短信发送失败，原因为：' + msg);
			}
		}
	});
}
function showMsg() {
	var rs = panel.getRecords();
	if (!rs || rs.length <= 0) {
		Ext.Msg.alert('提示', '请先选择需要发送信息的会员！');
		return;
	}
	var parms = '', r, id;
	for (var i = 0 ; i < rs.length; i++) {
		r = rs[i];
		id = r.get('id') == null ? '' : r.get('id');
		parms += (id == '' ? '' : id + ',');
	}
	Ext.create('Ext.window.Window', {
		id: 'msgsendwin',
		title: '系统消息群发',
		width: 600,
		border: false,
		frame: true,
		modal: true,
		resizable: false,
		closeAction: 'destroy',
		items: [
			{xtype: 'form',	defaultType: 'textfield', defaults: {labelWidth: 60, labelAlign: 'right'}, border: false, frame: true,
				items:[
					{id: 'ids', inputType:'hidden', value: parms},
					{xtype: 'textarea', id: 'msgcontent', fieldLabel: '信息内容', anchor:'100%'}
				]
			}
		],
		buttonAlign: 'center',
		buttons: [
			{text:'发送', handler: sendMsg},
			{text:'关闭', handler: function(){Ext.getCmp('msgsendwin').close();}}
		]
	}).show();
}
function sendMsg() {
	var _parms = 'mobiles=' + Ext.getCmp('ids').getValue() + '&contents=' + Ext.getCmp('msgcontent').getValue();
	Ext.Ajax.request({url: 'member1!sendMsg.asp', params: _parms, method: 'post',
		success: function(resp){
			var msg = resp.responseText;
			if (msg == 'OK') {
				Ext.Msg.alert('提示', '当前信息已经成功发送到所有选择的会员的手机上！');
			} else {
				Ext.Msg.alert('提示', '短信发送失败，原因为：' + msg);
			}
		}
	});
}

function showEmail() {
	var rs = panel.getRecords();
	if (!rs || rs.length <= 0) {
		Ext.Msg.alert('提示', '请先选择需要发送邮件的会员！');
		return;
	}
	var parms = '', r, email;
	for (var i = 0 ; i < rs.length; i++) {
		r = rs[i];
		email = r.get('email') == null ? '' : r.get('email');
		parms += (email == '' ? '' : email + ',');
	}
	Ext.create('Ext.window.Window', {
		id: 'emailsendwin',
		title: '邮件群发',
		width: 600,
		border: false,
		frame: true,
		modal: true,
		resizable: false,
		closeAction: 'destroy',
		items: [
			{xtype: 'form',	defaultType: 'textfield', defaults: {labelWidth: 60, labelAlign: 'right'}, border: false, frame: true,
				items:[
					{id: 'emails', inputType:'hidden', value: parms},
					{id:'emailsubject', fieldLabel:'主题', anchor: '100%'},
					{xtype: 'htmleditor', id: 'emailcontent', fieldLabel: '信息内容', anchor:'100%'}
				]
			}
		],
		buttonAlign: 'center',
		buttons: [
			{text:'发送', handler: sendEmail},
			{text:'关闭', handler: closeEmail}
		]
	}).show();
}
function sendEmail(){
	var _parms = 'mobiles=' + Ext.getCmp('emails').getValue() + '&contents=' + Ext.getCmp('emailcontent').getValue() + '&subject=' + Ext.getCmp('emailsubject').getValue();
	Ext.Ajax.request({url: 'member1!sendEmail.asp', params: _parms, method: 'post',
		success: function(resp){
			var msg = resp.responseText;
			if (msg == 'OK') {
				Ext.Msg.alert('提示', '当前信息已经成功发送到所有选择会员的邮箱中！');
			} else {
				Ext.Msg.alert('提示', '邮件发送失败，原因为：' + msg);
			}
		}
	});
}
function closeEmail(){
	Ext.getCmp('emailsendwin').close();
}
function onCleanEmail() {
	var rs = panel.getRecords();
	if (!rs || rs.length <= 0) {
		Ext.Msg.alert('提示', '请先选择需要清除邮件的会员！');
		return;
	}
	var parms = '', r;
	for (var i = 0 ; i < rs.length; i++) {
		r = rs[i];
		parms += 'ids=' + r.get('id') + '&';
	}
	parms += 'type=email';
	Ext.Ajax.request({url: 'member1!onClean.asp', params: parms, method: 'post',
		success: function(resp){
			var msg = resp.responseText;
			if (msg == 'OK') {
				Ext.Msg.alert('提示', '当前所有选择的会员的邮件信息已经成功清除！');
				panel.onReload();
			} else {
				Ext.Msg.alert('提示', '邮件清除失败，原因为：' + msg);
			}
		}
	});
}
function onCleanPhone() {
	var rs = panel.getRecords();
	if (!rs || rs.length <= 0) {
		Ext.Msg.alert('提示', '请先选择需要清除手机号码的会员！');
		return;
	}
	var parms = '', r;
	for (var i = 0 ; i < rs.length; i++) {
		r = rs[i];
		parms += 'ids=' + r.get('id') + '&';
	}
	parms += 'type=phone';
	Ext.Ajax.request({url: 'member1!onClean.asp', params: parms, method: 'post',
		success: function(resp){
			var msg = resp.responseText;
			if (msg == 'OK') {
				Ext.Msg.alert('提示', '当前所有选择的会员的手机号码已经成功清除！');
				panel.onReload();
			} else {
				Ext.Msg.alert('提示', '手机号码清除失败，原因为：' + msg);
			}
		}
	});
}
function onVerifyMobile() {
	var rs = panel.getRecords();
	if (!rs || rs.length <= 0) {
		Ext.Msg.alert('提示', '请先选择需要验证的手机号码的会员！');
		return;
	}
	var parms = '', r;
	for (var i = 0 ; i < rs.length; i++) {
		r = rs[i];
		parms += 'ids=' + r.get('id') + '&';
	}
	parms += 'type=phone';
	Ext.Ajax.request({url: 'member1!onVerifyMobile.asp', params: parms, method: 'post',
		success: function(resp){
			var msg = resp.responseText;
			if (msg == 'OK') {
				Ext.Msg.alert('提示', '当前所有选择的会员的手机号码已经成功校验！');
				panel.onReload();
			} else {
				Ext.Msg.alert('提示', '手机号码验证失败，原因为：' + msg);
			}
		}
	});
}
</script>
