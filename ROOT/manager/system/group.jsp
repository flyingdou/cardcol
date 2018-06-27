<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="divgroup"></div>
<script type="text/javascript">
var group = [
	{name:'id',type: 'int'},
	{name:'userLevel',type:'string'},
	{name:'purpose',type:'string'},
	{name:'monthIndex',type: 'int'},
	{name:'rulePart',type: 'string'},
	{name:'ruleSets', type: 'int'},
	{name:'ruleTimes', type: 'string'},
	{name:'ruleWeight', type: 'string'},
	{name:'ruleInterval', type: 'string'},
	{name:'ruleCatalogs', type: 'int'}
];
Ext.onReady(function(){
	Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
	Ext.create('Ext.ux.GridEditPanel', {
		id: 'group',
		funcs: <s:property value="#request.funcs"/>,
		program: <s:property value="#request.program"/>,
		gridEdit: true,
		where: ['健康状态:',{xtype:'text',id:'userLevel',width: 80},
		    '健身目的:',{xtype:'text',id:'purpose',width: 80},
		    '月索引:',{xtype:'text',id:'monthIndex',width:80}
		],
		columns: [
		    {header:'健康状态',editor:{xtype:'textfield',allowBlank:false},dataIndex:'userLevel',width:100},
		    {header:'健身目的',editor:{xtype:'textfield',allowBlank:false},dataIndex:'purpose',width:100},
			{header:'月索引',editor:{xtype:'number'},dataIndex:'monthIndex',width:80},
		    {header:'规则部门',editor:{xtype:'textfield',allowBlank:false},dataIndex:'rulePart',width:100},
			{header:'组数',editor:{xtype:'number'},dataIndex:'ruleSets',width:80},
			{header:'次数',editor:{xtype:'textfield'},dataIndex:'ruleTimes',width:100},
			{header:'重量',editor:{xtype:'textfield'},dataIndex:'ruleWeight',width: 100},
			{header:'间隔',editor:{xtype:'textfield'},dataIndex:'ruleInterval',width: 100},
			{header:'动作个数',editor:{xtype:'number'},dataIndex:'ruleCatalogs',width: 100}
		],
		bean: group
	});
});
</script>
