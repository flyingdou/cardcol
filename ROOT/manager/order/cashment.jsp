<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="divcashment"></div>
<script language="javascript">
var panel;
var cashment = [
	{name:'id',type: 'int'},
	{name:'no',type:'string'},
	{name:'memberId', type:'long', mapping: 'member.id'},
	{name:'nick',type:'string',},
	{name:'name', type:'string'},
	{name:'pickMoney',type:'float'},
	{name:'balance',type:'float'},
	{name:'pickDate',type:'string'},
	{name:'pickAccount',type:'string'},
	{name:'flowType', type: 'string'},
	{name:'status', type: 'string'},
	{name:'remark', type: 'string'}
];
Ext.onReady(function(){
	panel = Ext.create('Ext.ux.GridFormPanel', {
		id: 'cashment',
		locked: false,
		funcs: <s:property value="#request.funcs"/>,
		fbars: [{text:'处理', handler: showHandler}],
		where: ['申请状态:',{xtype:'combox', id:'status', loadUrl: 'cashment!findStatus' + Ext.ACTION_SUFFIX, width: 80}],
		columns: [
			{header:'流水号', dataIndex:'no', width:100},
			{header:'会员昵称', dataIndex:'nick', width:100},
			{header:'会员名称',dataIndex:'name',width:100},
			{header:'提现金额',dataIndex:'pickMoney',width:80},
			{header:'账户余额',dataIndex:'balance',width:80},
			{header:'提现日期',dataIndex:'pickDate',width:120},/* renderer: rendDate */
			{header:'提现账户',dataIndex:'pickAccount',width:80},
// 			{header:'资金流向',dataIndex:'flowType',width:80,renderer: rendFlowType},
			{header:'状态',dataIndex:'status',width:80,renderer: rendStatus},
			{header:'备注',dataIndex:'remark',width:180},
			{header:'', width: 100, renderer: function(r, c, v) {
				return Ext.String.format('<a href="#" id="looktrade{0}" memberId="{1}">查看交易明细</a>',  r.get('id'), r.get('memberId'));
			}}
		],
		onLoadFinsh: function(){
			var _rends = Ext.query('a[id^=looktrade]');
			Ext.Array.each(_rends, function(item) {
				var _item = Ext.get(item.id), _memberId = _item.getAttribute('memberId');
				_item.on('click', function() {
					new Ext.ux.SelectWindow({
						title: '交易明细',
						width: 600,
						height: 400,
						isExt: true,
						bean: [],
						columns: [
							{header:'交易描述', dataIndex:'', width: 100},
							{header:'交易时间', dataIndex:'', width: 120},
							{header:'交易金额', dataIndex:'', width: 100},
							{header:'交易方式', dataIndex:'', width: 80, renderer: function(v, c, r) {
								if (!v) return '未确认';
								return v == '1' ? '支付宝' : v== '2' ? '微信' : v == '0' ? '银联' : v; 
							}},
							{header:'第三方交易号',datIndex:'', width: 100}
						],
						listUrl: 'cashment!queryTrade' + Ext.ACTION_SUFFIX
					}).show();
				});
			});
		},
		bean: cashment
	});
});
function rendFlowType(val, cell, rs) {
	if (val == '2') return '支出';
	else if (val == '1') return '收入';
	else return '';
}
function rendStatus(val, cell, rs) {
	if (val == '1') return '处理中';
	else if (val == '2') return '已完成';
	else return '失败'; 
}
function showHandler() {
	var rs = panel.getRecord();
	if (!rs) {
		alert('请先选择需要处理的数据！');
		return;
	}
	var id = rs.get('id');
	new Ext.Window({
		id: 'cashhandlerwindow',
		title: '取现申请处理',
		width: 300,
		border: false,
		frame: true,
		modal: true,
		closeAction: 'close',
		items: [{
			xtype:'form',
			id: 'cashhandlerform',
			defaultType: 'textfield',
			labelWidth: 60,
			labelAlign: 'right',
			border: false,
			frame: true,
			items: [
			    {name:'id',inputType:'hidden',value: id},
			    {fieldLabel:'处理状态', xtype:'combox', name:'audit', keyId: 'id', idProperty: 'id', loadUrl: 'cashment!findStatus' + Ext.ACTION_SUFFIX, anchor: '100%'}
			]
		}],
		buttonAlign: 'center',
		buttons: [
			{text:'确定',handler: onHandler},{text:'关闭',handler: onClose}          
		]
	}).show();
}
function onHandler() {
	var f = Ext.getCmp('cashhandlerform').getForm();
	if (f.isValid()) {
		f.submit({url:'cashment!handler.asp', method:'post',
			success: function(f, action) {
				alert('当前提现数据已经成功处理！');
				panel.onReload();
			}
		});
	}
}
function onClose() {
	Ext.getCmp('cashhandlerwindow').destroy();
}
</script>

