<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="divrule"></div>
<script type="text/javascript">
var rule = [
  		{name:'id',type: 'int'},
  		{name:'userLevel',type:'string'},
  		{name:'duration',type:'int'},
  		{name:'frequency',type: 'int'},
  		{name:'dayIndex',type: 'int'},
  		{name:'part', type: 'string'},
  		{name:'sort', type: 'int'}
  	];
Ext.onReady(function(){
	Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
	Ext.create('Ext.ux.GridEditPanel', {
		id: 'rule',
		funcs: <s:property value="#request.funcs"/>,
		program: <s:property value="#request.program"/>,
		gridEdit: true,
		where: ['健康状态:',{xtype:'text',id:'userLevel',width: 80},'天数',{xtype:'text',id:'dayIndex',width:80}],
		columns: [
		    {header:'健康状态',editor:{xtype:'textfield',allowBlank:false},dataIndex:'userLevel',width:100},
		    {header:'频率',editor:{xtype:'number',allowBlank:false},dataIndex:'frequency',width:100},
			{header:'天数',editor:{xtype:'number'},dataIndex:'dayIndex',width:200},
			{header:'部位',editor:{xtype:'textfield'},dataIndex:'part',width:200},
			{header:'顺序',editor:{xtype:'number'},dataIndex:'sort',width: 100}
		],
		bean: rule
	});
});
</script>
