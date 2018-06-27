<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="this is my page">
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	</head>
	<script language="javascript">
	Ext.onReady(function(){
		Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
		var sector = [
       		{name:'id',type: 'int'},
       		{name:'name',type:'string'},
       		{name:'type',type:'int'},
       		{name:'position',type:'string'},
       		{name:'nameRule',type:'string'},
       		{name:'seoTitle',type:'string'},
       		{name:'keywords',type:'string'},
       		{name:'parent',type:'int'},
       		{name:'sort',type:'int'},
       		{name:'content',type:'string'}
       	];
		Ext.create('Ext.ux.TreeFormPanel', {
			id: 'sector',
			formTitle: '栏目信息',
			bean: sector,
			labelWidth: 50,
			async: true,
			configs: {layout:'table',columns: 2},
			funcs: <s:property value="#request.funcs"/>,
			fields: [
			    {id:'id',xtype:'hidden',name:'sector.id'},
			    {id:'parent',xtype:'hidden',name:'sector.parent'},
				{id:'name',fieldLabel:'栏目名称',name:'sector.name',anchor:'100%',allowBlank:false},
				{id:'type',xtype:'combox',fieldLabel:'栏目类型',keyId:'code',url:'sector!dictionary?code=sections_type_c',hiddenName:'sector.type',anchor:'100%'},
				{id:'position',fieldLabel:'栏目位置',name:'sector.position',anchor:'100%'},
				{id:'seoTitle',fieldLabel:'SEO标题',name:'sector.seoTitle',anchor:'100%'},
				{id:'keywords',fieldLabel:'关键字',name:'sector.keywords',anchor:'100%'},
				{id:'sort',fieldLabel:'顺序',xtype:'numberfield',name:'sector.sort',anchor:'100%'},
				{id: 'content',colspan:2,height: 200,fieldLabel:'栏目内容',xtype:'ckfield',hiddenName:'sector.content'}
			],
			loadUrl: 'sector!load'
		});
	});
	</script>
	<body>
		<div id="divsector"></div>
	</body>
</html>

