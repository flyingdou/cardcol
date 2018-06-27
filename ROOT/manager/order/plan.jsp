<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="divplan1"></div>
<script type="text/javascript">
var panel;
var plan1 = [
	{name:'id',type: 'long'},
	{name:'memberName', type: 'string', mapping: 'member.name'},
	{name:'planName',type:'string'},
	{name:'planType',type:'string'},
	{name:'scene',type:'string'},
	{name:'applyObject', type: 'string'},
	{name:'apparatuses', type: 'string'},
	{name:'startDate', type: 'string'},
	{name:'endDate', type: 'string'},
	{name:'unitPrice', type: 'float'},
	{name:'briefing', type: 'string'},
	{name:'details', type: 'string'},
	{name:'image1', type: 'string'},
	{name:'image2',type:'string'},
	{name:'image3',type:'string'},
	{name:'status', type: 'string'},
	{name:'publishTime', type: 'string'},
	{name:'stickTime', type: 'string'},
	{name:'action', type: 'string'},
	{name:'value', type:'float'},
	{name:'audit',type:'string'}
];
Ext.onReady(function(){
	var _funcs = <s:property value="#request.funcs"/>;
	var lbars = [];
	if (_funcs.btnClose && _funcs.btnClose === true) 
		lbars[0] = {text:'关闭挑战', handler: changeStatus};
	panel = Ext.create('Ext.ux.GridFormPanel', {
		id: 'plan1',
		locked: false,
		funcs: <s:property value="#request.funcs"/>,
		lbars: lbars,
		recommendTitleField: 'planName',
		where: [
		    '计划名称:',{xtype: 'textfield', id:'planName', width: 80},
		    '制定人:',{xtype:'textfield', id: 'member.name', width: 80}
		],
		columns: [
			{header:'计划制定人', dataIndex:'memberName', width: 100},
			{header:'计划名称', dataIndex:'planName', width:100},
			{header:'计划类型', dataIndex:'planType', width:100,renderer: rendMode},
			{header:'适用场景',dataIndex:'scene',width:140, renderer: rendScene},
			{header:'适用对象',dataIndex:'applyObject',width:80, renderer:rendObject},
			{header:'所需器材',dataIndex:'apparatuses',width:80},
			{header:'保存范围从',dataIndex:'startDate', width: 80, renderer: rendDate},
			{header:'保存范围到',dataIndex:'endDate',width: 80, renderer: rendDate},
			{header:'销售价格',dataIndex:'unitPrice',width:120},
			{header:'发布时间',dataIndex:'publishTime',width:120},
			{header:'审核状态', dataIndex: 'audit', width: 80, renderer: rendStatus}
		],
		bean: plan1
	});
});
function rendMode(v, c, r){
	if (v === 'A') return '瘦身减重';
	else if (v === 'B') return '健美增肌';
	else if (v === 'C') return '运动康复';
	else if (v === 'D') return '提高运动表现';
	else return v;
}
function rendScene(v, c, r){
	var vs = v.split(','), names = '';
	for (var i = 0; i < vs.length; i++) {
		if (vs[i] === 'A') names = names + (names == '' ? '' : ',') + '办公室';
		else if (vs[i] === 'B') names = names + (names == '' ? '' : ',') + '健身房';
		else if (vs[i] === 'C') names = names + (names == '' ? '' : ',') + '家庭';
		else if (vs[i] === 'D') names = names + (names == '' ? '' : ',') + '户外';
		else names = names + (names == '' ? '' : ',') + vs[i];
	}
	return names;
}
function rendObject(v, c, r){
	if (v === 'A') return '初级';
	else if (v === 'B') return '中级';
	else if (v === 'C') return '高级';
	else return v;
}
function rendTarget(v, c, r){
	
}
function rendStatus(v, c, r){
	if(v === '1') 
		return '已审核';
	return '未审核';
}
</script>
