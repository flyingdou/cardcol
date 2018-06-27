<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="divaction1"></div>
<script type="text/javascript">
var action1 = [
	{name:'id',type: 'long'},
	{name:'partId', type:'long', mapping: 'part.id'},
	{name:'partName', type: 'string', mapping: 'part.name'},
	{name:'name', type: 'string'},
	{name:'coefficient',type:'string'},
	{name:'muscle',type: 'string'},
	{name:'descr', type: 'string'},
	{name:'regard', type: 'string'},
	{name:'image', type: 'string'},
	{name:'flash', type: 'string'},
	{name:'system', type: 'string'},
	{name:'member', type: 'long'},
	{name:'video', type: 'string'}
];
Ext.onReady(function(){
	Ext.create('Ext.ux.GridFormPanel', {
		id: 'action1',
		winTitle: '系统动作信息',
		winWidth: 500,
		winHeight: 380,
		locked: false,
		funcs: <s:property value="#request.funcs"/>,
		layouts: {type: 'table', columns: 2},
		program: <s:property value="#request.program"/>,
		where: [
		    '部位名称:',{xtype: 'textfield', id:'part.name', width: 80},
		    '动作名称:',{xtype:'textfield', id: 'name', width: 80}
		],
		fields:[
			{id:'name',fieldLabel:'动作名称',name:'action.name',anchor: '100%',xtype:'text',allowBlank:false},
			{id:'partId',fieldLabel:'部位名称',name:'action.part.id',xtype:'combox',anchor:'100%',allowBlank: false,loadUrl:'action1!loadPart.asp'},
			{id:'coefficient',fieldLabel:'卧推系数',name:'action.coefficient',anchor: '100%',allowBlank:false},
			{id:'muscle',fieldLabel:'肌肉属性',name:'action.muscle',anchor: '100%'},
			{id:'descr',colspan:2,fieldLabel:'描述',name:'action.descr',anchor: '100%', xtype:'textarea'},
			{id:'regard',colspan:2,fieldLabel:'注意事项',name:'action.regard',anchor: '100%', xtype:'textarea'},
			{id:'flash',fieldLabel:'演示动画',name:'action.flash',anchor: '100%'},
			{id:'video',fieldLabel:'演示视频',name:'action.video',anchor: '100%'},
			{id:'images',fieldLabel:'缩略图片',name:'file.files',xtype:'filefield', anchor: '100%'},
			{id:'file', fieldLabel:'视频上传',name:'file.file',xtype:'filefield', anchor: '100%'},
			{id:'id',inputType:'hidden',name:'action.id'},
			{id:'image',inputType:'hidden',name:'action.image'},
			{id:'system',inputType:'hidden', name: 'action.system'}
		],
		columns: [
			{header:'动作名称', dataIndex:'name', width: 100},
			{header:'部位名称', dataIndex:'partName', width:100},
			{header:'卧推系数', dataIndex:'coefficient', width:100},
			{header:'肌肉属性', dataIndex:'muscle',width:100},
			{header:'描述', dataIndex:'descr',width:80},
			{header:'注意事项',dataIndex:'regard',width:80},
			{header:'演示图片',dataIndex:'image', width: 80},
			{header:'演示动画',dataIndex:'flash',width: 80},
			{header:'演示视频',dataIndex:'video',width:120}
		],
		bean: action1
	});
});
</script>
