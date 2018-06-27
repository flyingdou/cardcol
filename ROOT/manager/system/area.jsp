<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="divarea1"></div>
<script type="text/javascript">
var area1 = [
	{name:'id',type: 'int'},
	{name:'name',type:'string'},
	{name:'parent',type:'int'},
	{name:'open',type:'string'},
	{name:'letter',type:'string'},
	{name:'hotCity', type:'string'},
	{name:'type',type: 'string'}
];
var yesnoStore = Ext.create('Ext.data.Store', {
    autoLoad: true,
    fields: ['id', 'name'],
    data : {items: [{id: '1', name: '是'}, {id: '0', name: '否'}]},
    proxy: {type: 'memory', reader: {type: 'json', root: 'items'}}
});
Ext.onReady(function(){
	Ext.create('Ext.ux.TreeFormPanel', {
		id: 'area1',
		treeTitle: '所有地区',
		formTitle: '地区信息',
		async: true,
		funcs: <s:property value="#request.funcs"/>,
		program: <s:property value="#request.program"/>,
		fields: [
			{id:'id',inputType:'hidden',name:'area.id'},
			{id:'parent',inputType:'hidden',name:'area.parent'},
			{id:'name',fieldLabel:'地区名称',name:'area.name',anchor: '50%',allowBlank:false, readOnly: true},
			{id:'open',fieldLabel:'是否开通',name:'area.open',anchor: '20%',xtype:'combox',store: yesnoStore},
			{id:'letter',fieldLabel:'拼音首字母',name:'area.letter',anchor: '20%',xtype:'text'},
			{id:'hotCity',fieldLabel:'热点城市',name:'area.hotCity',anchor: '20%',xtype:'combox',store: yesnoStore},
			{id:'type',fieldLabel:'类型',name:'area.type',anchor: '20%',xtype:'combox',loadUrl:'area1!findType.asp'}
		],
		bean: area1,
		loadUrl: 'area1!load'
	});
});
</script>
