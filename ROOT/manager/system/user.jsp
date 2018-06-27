<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="divuser1"></div>
<script type="text/javascript">
var user1 = [
	{name:'id',type: 'int'},
	{name:'code',type:'string'},
	{name:'name',type:'string'},
	{name:'tell',type:'string'},
	{name:'address',type:'string'},
	{name:'post',type:'string'},
	{name:'email',type:'string'},
	{name:'status',type:'string'},
	{name:'roleId',type:'int'},
	{name:'roleName',type:'string'},
	{name:'password',type:'string'}
];
var _member1 = [
	{name:'id',type: 'int'},
	{name:'email',type:'string'},
	{name:'nick',type:'string'},
	{name:'name',type:'string'},
	{name:'birthday',type:'string'},
	{name:'tell',type:'string'},
	{name:'mobilephone', type: 'string'},
	{name:'province',type:'string'},
	{name:'city',type:'string'},
	{name:'county',type: 'string'},
	{name:'role',type:'string'}
];
var _memCols1 = [
	{header:'昵称',dataIndex:'nick',width:100},
	{header:'名称',dataIndex:'name',width:140},
	{header:'角色类型',dataIndex:'role',width:100},
	{header:'邮箱',dataIndex:'email',width:150},
	{header:'联系电话',dataIndex:'tell',width:100},
	{header:'移动电话',dataIndex:'mobilephone', width: 100},
	{header:'生日',dataIndex:'birthday',width: 100, renderer: rendDate},
	{header:'私教',dataIndex: 'coachName', width: 100}
];
var panel = null;
Ext.onReady(function(){
	var _members = [
    	{name:'memberId', type: 'int', mapping: 'member.id'},
    	{name:'memberNick', type:'string', mapping: 'member.nick'},
    	{name:'memberName', type: 'string', mapping: 'member.name'},
    	{name:'memberEmail', type: 'string', mapping: 'member.email'}
    ];
    var _memCols = [
    	{header:'会员昵称',dataIndex:'memberNick',editor: {xtype:'selectfield',sourceIds: ['memberId', 'memberNick','memberName','memberEmail'], destIds:['id', 'nick', 'name', 'email'],title:'选择会员',isGrid: true, record: this, bean: _member1, columns: _memCols1,listUrl: 'member1!query.asp'},width: 100},
    	{header:'会员名称',dataIndex:'memberName',width: 100},
    	{header:'会员邮件',dataIndex:'memberEmail',width: 200}
    ];
	panel = new Ext.form.GridFormEdit({
		id: 'user1',
		formTitle: '管理员信息',
		formWidth: 500,
		formHeight: 380,
		locked: false,
		hasLog: true,
		showType: 3,
		configs: {layout: 'table', columns: 2},
		funcs: <s:property value="#request.funcs"/>,
		program: <s:property value="#request.program"/>,
		where: ['用户昵称',{xtype:'textfield',id:'code',width:80},'用户名称:',{xtype:'textfield',id:'name',width: 80}],
		columns: [
		    {header:'用户代号',dataIndex:'code',locked:true,width:100},
			{header:'用户名称',dataIndex:'name',width:140},
			{header:'所属角色',dataIndex:'roleName',width:150},
			{header:'联系电话',dataIndex:'tell',width:100},
			{header:'联系地址',dataIndex:'address',width:150},
			{header:'邮编',dataIndex:'post',width:60},
			{header:'电子邮件',dataIndex:'email',width:120},
			{header:'用户状态',dataIndex:'status',width: 100}
		],
		fields: [
			{id:'id',inputType:'hidden',name:'user.id'},
			{id:'password',inputType:'hidden',name:'user.password'},
			{id:'code',fieldLabel:'用户代号',name:'user.code',anchor: '100%',xtype:'text',allowBlank:false},
			{id:'name',fieldLabel:'用户名称',name:'user.name',anchor: '100%',allowBlank:false},
			{id:'pass2',fieldLabel:'登录密码',name:'password1',anchor: '100%',inputType:'password'},
			{id:'userpass1',fieldLabel:'确认密码',name:'password2',vtype:'password',initialPassField:'user1pass2',anchor: '100%',inputType:'password'},
			{id:'roleId',fieldLabel:'所属角色',hiddenName:'user.role.id',xtype:'combox',anchor:'100%',allowBlank: false,url:'role!loadAll'},
			{id:'tell',fieldLabel:'联系电话',name:'user.tell',anchor: '100%'},
			{id:'address',fieldLabel:'联系地址',name:'user.address',anchor: '100%'},
			{id:'post',fieldLabel:'邮编',name:'user.post',anchor: '100%'},
			{id:'email',fieldLabel:'电子邮件',name:'user.email',anchor: '100%'},
			{id:'status',fieldLabel:'用户状态',xtype:'radiogroup',anchor:'100%',allowBlank:false,items:[{name:'user.status',inputValue:'1',boxLabel:'启用'},{name:'user.status',inputValue:'0',boxLabel:'停用'}]},
			{id:'jsons',fieldLabel:'所有会员',xtype:'gridfield',anchor:'100%',colspan:2,link:'user1',bean:_members, columns:_memCols, height: 200}
		],
		showTitle: '所有会员',
		showBean: _members,
		showColumn: _memCols,
		bean: user1
	});
});
</script>
