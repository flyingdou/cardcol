<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="divinstitution"></div>
<script type="text/javascript">
var institution = [
  		{name:'id',type: 'int'},
  		{name:'name',type:'string'},
  		{name:'template',type:'string'}
  	];
Ext.onReady(function(){
	Ext.create('Ext.ux.GridFormPanel', {
		id: 'institution',
		formTitle: '慈善机构信息',
		formWidth: 660,
		formHeight: 380,
		locked: false,
		hasLog: true,
		funcs: <s:property value="#request.funcs"/>,
		where: ['用户代号',{xtype:'textfield',id:'code',width:80},'用户名称:',{xtype:'textfield',id:'name',width: 80}],
		columns: [
		    {header:'模板名称',dataIndex:'name',locked:true,width:100}
		],
		fields: [
			{id:'id',inputType:'hidden',name:'template.id'},
			{id:'name',fieldLabel:'模板名称',name:'template.name',anchor: '100%',xtype:'text',allowBlank:false},
			{id:'template',fieldLabel:'模板明细',name:'template.template',xtype:'htmleditor',anchor: '100%',allowBlank:false}
		],
		bean: institution
	});
});
</script>
