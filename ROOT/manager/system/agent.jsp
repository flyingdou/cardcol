<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="divagent"></div>
<script type="text/javascript">
var agent = [
	{name:'id',type: 'int'},
	{name:'userId',type:'int', mapping: 'user.id'},
	{name:'userName',type:'string',mapping:'user.name'},
	{name:'memberId', type: 'int', mapping: 'member.id'},
	{name:'memberNick', type:'string', mapping: 'member.nick'},
	{name:'memberName', type: 'string', mapping: 'member.name'},
	{name:'memberEmail', type: 'string', mapping: 'member.email'}
];
Ext.onReady(function(){
	new Ext.form.GridFormEdit({
		id: 'agent',
		formTitle: '代理商会员信息',
		formWidth: 660,
		formHeight: 380,
		locked: false,
		hasLog: true,
		funcs: <s:property value="#request.funcs"/>,
		where: ['用户代号',{xtype:'textfield',id:'user.code',width:80},'用户名称:',{xtype:'textfield',id:'user.name',width: 80}],
		columns: [
		    {header:'用户名称',dataIndex:'userName',width:100},
		    {header:'会员昵称',dataIndex:'memberNick',width: 100},
		    {header:'会员名称',dataIndex:'memberName',width: 100},
		    {header:'会员邮件',dataIndex:'memberEmail',width: 200}
		],
		fields: [
			{id:'id',inputType:'hidden',name:'um.id'},
			{id:'userId',inputType:'hidden',name:'um.user.id'},
			{id:'memberId',inputType:'hidden',name:'um.member.id'},
			{id:'userName',fieldLabel:'当前代理商',name:'um.user.name',anchor: '100%',xtype:'text',allowBlank:false,readOnly: true},
			{id:'template',fieldLabel:'所有会员',name:'template.template',xtype:'htmleditor',anchor: '100%',allowBlank:false}
		],
		bean: agent
	});
});
</script>
