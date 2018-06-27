<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="divonecard"></div>
<script type="text/javascript">
var panel, onecard = [
	{name:'id',type: 'long'},
	{name:'name',type:'string'},
	{name:'image',type:'string'},
	{name:'image1',type:'string'},
	{name:'type',type:'string'},
	{name:'period', type: 'int'},
	{name:'periodUnit', type: 'string'},
	{name:'price', type: 'float'},
	{name:'summary', type: 'string'},
	{name:'content',  type: 'string'},
	{name:'applyClubs', type:'object'},
	{name:'status',type:'string'}
];
Ext.onReady(function(){
	panel = Ext.create('Ext.ux.GridFormPanel', {
		id: 'onecard',
		funcs: <s:property value="#request.funcs"/>,
		program: <s:property value="#request.program"/>,
		recommendTitleField: 'name',
		winTitle: '一卡通商品维护',
		winWidth: 800,
		winHeight: 500,
		layouts: {type: 'table', columns: 2},
		where: [
		    '商品名称:',{xtype: 'textfield', id:'name', width: 80}
		],
		fields :  [
			{id:'name',fieldLabel:'商品名称',name:'product.name',anchor: '100%',xtype:'text',allowBlank:false},
			{id:'period',fieldLabel:'周期',name:'product.period',xtype:'number',anchor:'100%',allowBlank: false},
			{id:'periodUnit',fieldLabel:'周期单位',name:'product.periodUnit',anchor: '100%',allowBlank:false,valueField:'code',xtype:'combox',loadUrl:'onecard!dictionary' + Ext.ACTION_SUFFIX + '?code=product_period_unit'},
			{id:'price', fieldLabel:'单价',name:'product.price',xtype:'number',anchor:'30%',allowBlank:false},
			{id:'image', fieldLabel:'缩略图',name:'product.image',readOnly: true},
			{id:'images',fieldLabel:'图片上传',name:'file.file',xtype:'filefield', anchor: '100%'},
			{id:'image1', fieldLabel:'详情缩略图',name:'product.image1',readOnly: true},
			{id:'images1',fieldLabel:'图片上传',name:'file1.file',xtype:'filefield', anchor: '100%'},
			{id:'applyClubs', colspan:2,fieldLabel:'适用店面',name:'product.clubs', height: 160, xtype:'list2list', listUrl:'member1!query' + Ext.ACTION_SUFFIX + '?query.role=E', bean:[
				{name:'id',type: 'int'},
				{name:'email',type:'string'},
				{name:'nick',type:'string'},
				{name:'tell',type:'string'},
				{name:'mobilephone', type: 'string'},
				{name:'name',type:'string'}
			],columns:[
				{header:'昵称',dataIndex:'nick',width:100,items: {xtype: 'headtext', field: 'nick'}},
				{header:'名称',dataIndex:'name',width:140,items: {xtype: 'headtext', field: 'name'}},
				{header:'联系电话',dataIndex:'tell',width:100,items: {xtype:'headtext', field: 'tell'}},
				{header:'移动电话',dataIndex:'mobilephone', width: 100, items: {xtype:'headtext', field: 'mobilphone'}}
			],hasPage: true, params:{'query.role': 'E'}},
			{id:'summary',colspan:2,fieldLabel:'摘要',name: 'product.summary', xtype:'textarea', height:40},
			{id:'content',colspan:2,fieldLabel:'描述',anchor: '100%',xtype:'ckfield',hiddenName:'product.content',height: 180},
			{id:'id',inputType:'hidden',name:'product.id'},
			{id:'status',inputType:'hidden',name:'product.status'},
			{id:'type',inputType:'hidden',name:'product.type'}
		],
		columns: [
			{header: '', width: 40, renderer: function(v, c, r){
				var _htmls = '<a href="javascript:changeStatus({0}, \'{1}\')" id="oper{0}" keyId="{0}" type="{1}">{2}</a>';
				if (r.get('onecardstatus') === 'A') return Ext.String.format(_htmls, r.get('id'), 'B', '开通');
				return Ext.String.format(_htmls, r.get('id'), 'A', '关闭');
			}},
			{header:'商品名称', dataIndex:'name', width:300},
			{header:'周期', dataIndex:'period', width:80},
			{header:'周期单位',dataIndex:'periodUnit',width:100,renderer:  function(v, c, r) {
				if (v == 'A') return '月';
				else if (v === 'B') return '季';
				else if (v == 'C') return '年';
				else if (v == 'D') return '日';
			}},
			{header:'图片',dataIndex:'image',width:180},
			{header:'状态', dataIndex: 'status', width: 80, renderer: function(v,  c,  r) {
				if (v === 'B') return '开通';
				else return '关闭';
			}}
		],
		bean: onecard
	});
});
function changeStatus(_id, _type) {
	var _title = _type == 'B' ?  '开通' : '关闭';
	Ext.Msg.confirm(_title + '商品', '是否确认' + _title + '当前商品？', function(btn) {
		if (btn == 'yes') {
			Ext.Ajax.request({url:'onecard!changeStatus.asp',method: 'post', params: {id: _id, type: _type},
				success: function(resp){
					Ext.Msg.alert('提示', '当前商品已经成功' + _title + '！');
					panel.onReload();
				}
			});
		}
	});
}
</script>


