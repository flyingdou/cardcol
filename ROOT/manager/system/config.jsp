<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="divconfig"></div>
<script type="text/javascript">
Ext.onReady(function(){
	Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
	var form = new Ext.form.FormPanel({
		renderTo: 'divconfig',
		defaultType: 'textfield',
		frame: true,
		border: true,
		labelAlign: 'right',
		labelWidth: 80,
		fileUpload: true,
		buttons: [{text: '保存',handler: onSave, scope: this},{text:'关闭',handler: onClose, scope: this}],
		buttonAlign: 'center',
		height: tabs.getActiveTab().getInnerHeight(),
		autoScroll: true,
		items: [
		    {xtype: 'fieldset', title:'公司信息',defaultType:'textfield',layout: 'form',autoWidth: true,items:[
				{id: 'configid', name: 'config.id',inputType:'hidden'},
				{id: 'configicon',name: 'config.icon',inputType:'hidden'},
				{id: 'configbanner',name: 'config.banner',inputType:'hidden'},
				{id: 'configcompany', name: 'config.company',fieldLabel: '公司名称',anchor: '80%'},
				{id: 'configeCompany', name: 'config.eCompany',fieldLabel: '英文名称',anchor: '80%'},
				{id: 'configaddress', name: 'config.address', fieldLabel: '公司地址',anchor: '80%'},
				{id: 'configeAddress', name: 'config.eAddress', fieldLabel: '英文地址',anchor: '80%'},
				{id: 'configpost', name: 'config.post', fieldLabel: '邮编',anchor: '50%'},
				{id: 'configtell', name: 'config.tell',fieldLabel: '联系电话',anchor: '50%'},
				{id: 'configserviceTell', name: 'config.serviceTell',fieldLabel: '客服电话',anchor: '50%'},
				{id: 'configfax', name: 'config.fax', fieldLabel: '传真',anchor: '50%'},
				{id: 'configqq', name: 'config.qq', fieldLabel: 'QQ', anchor: '50%'},
				{id: 'configweburl', name: 'config.weburl', fieldLabel: '公司网址',anchor: '80%'},
				{id: 'configemail', name: 'config.email', fieldLabel: '企业邮箱',anchor: '80%'},
				{id: 'configicp', name: 'config.icp', fieldLabel: '备案号',anchor: '80%'},
				{id: 'configcopyRight', name: 'config.copyRight', fieldLabel: '版权',anchor: '80%'},
				{id: 'fileicon', name: 'file.file', xtype:'upload', fieldLabel: '网站LOGO',anchor: '80%'},
				{id: 'filebanner', name: 'file.files', xtype: 'upload', fieldLabel: '门户主广告',anchor: '80%'}
		    ]},
		    {xtype: 'fieldset',title:'邮箱设置',cls:'x-form-position-top',defaultType:'textfield',autoWidth: true,items:[
				{id: 'configsmtp', name: 'config.smtp', fieldLabel: 'SMTP服务器',anchor: '80%'},
				{id: 'configuser', name: 'config.user', fieldLabel: 'SMTP账户',anchor: '80%'},
				{id: 'configpassword', name: 'config.password', fieldLabel: 'SMTP密码',anchor: '80%'}
			]},
		    {xtype: 'fieldset',title:'支付宝信息',cls:'x-form-position-top',defaultType:'textfield',autoWidth: true,items:[
				{id: 'configaccount', name: 'config.account', fieldLabel: '支付宝账户',anchor: '80%'},
				{id: 'configpartner', name: 'config.partner', fieldLabel: '合作ID号',anchor: '80%'},
				{id: 'configverifyCode', name: 'config.verifyCode', fieldLabel: '校验码',anchor: '80%'}
		    ]}
		]
	});
	form.getForm().load({
		url: 'config!query.asp',
		waitTitle: '加载',
		waitMsg: '正在加载中.....',
		success: function(f, action){
			var items = action.result.items;
			if (items && items.length > 0) {
				var item = items[0];
				for (var o in item) {
					var obj = Ext.getCmp('config' + o);
					if (obj) obj.setValue(item[o]);
				}
			}
		},
		failure: function(f, action){
			var items = action.result.items;
			if (items && items.length > 0) {
				var item = items[0];
				for (var o in item) {
					var obj = Ext.getCmp('config' + o);
					if (obj) obj.setValue(item[o]);
				}
			}
		}
	});
	function onSave(){
		var f = form.getForm();
		if (f.isDirty()) {
			if (f.isValid()) {
				f.submit({url: 'config!save.asp',waitMsg: '正在保存数据...',scope: this,
					success: function(form, action){
						var resp = Ext.util.JSON.decode(action.response.responseText);
						Ext.getCmp('configid').setValue(resp.key);
						alert('当前数据已经成功保存！');
					},
					failure: function(form, action){
						alert('数据保存失败，可能原因为：' + action.result.desc ? action.result.des : '');
					}
				});
			}
		} else {
			alert('未修改任何数据，不需要保存！');
		}
	}
	function onClose(){
		tabs.remove(tabs.getActiveTab());
		form.destroy();
	}
});
</script>

