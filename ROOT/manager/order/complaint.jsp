<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="this is my page">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
</head>
<body>
<div id="divcomplaint1"></div>
<script language="javascript">
	var complaint1 = [
   		{name:'id',type: 'int'},
   		{name:'no',type:'string'},
   		{name:'compDate',type:'string'},
   		{name:'orderNo',type:'string'},
   		{name:'orderDate',type:'string'},
   		{name:'prodName',type:'string'},
   		{name:'unitPrice',type:'float'},
   		{name:'memberFromName',type:'string', mapping: 'memberFrom.nick'},
   		{name:'telFrom',type:'string'},
   		{name:'emailFrom',type:'string'},
   		{name:'memberToName', type: 'string', mapping: 'memberTo.nick'},
   		{name:'telTo', type: 'string'},
   		{name:'affix', type: 'string'},
   		{name:'content', type: 'string'},
   		{name:'status', type: 'string'},
   		{name:'fromStatus', type: 'string'},
   		{name:'toStatus', type: 'string'},
   		{name:'delStatus', type: 'string'},
   		{name:'type', type: 'string'},
   		{name:'processResult', type: 'string'}
   	];
	Ext.onReady(function(){
		Ext.create('Ext.ux.GridFormPanel', {
			id: 'complaint1',
			winTitle: '订单投诉信息',
			winWidth: 600,
			winHeight: 440,
			funcs: <s:property value="#request.funcs"/>,
			editText: '处理',
			layouts: {type: 'table',columns:2},
			labelWidth: 80,
			where: ['投诉编号:',{xtype: 'textfield', id:'no', width: 100}],
			columns: [
				{header:'投诉编号', dataIndex:'no', locked: true, width:100},
				{header:'投诉人', dataIndex:'memberFromName', width:100, locked: true},
				{header:'投诉日期',dataIndex:'compDate',width:100,locked: true},
				{header:'投诉人电话',dataIndex:'telFrom',width:80},
				{header:'投诉人邮箱',dataIndex:'emailFrom',width:120},
				{header:'被投诉人',dataIndex:'memberToName',width:80},
				{header:'被投诉人电话',dataIndex:'telTo',width: 100},
				{header:'相关证据', dataIndex: 'affix', width: 120,renderer: rendAffix},
				{header:'投诉内容',dataIndex:'content',width:200},
				{header:'订单编号',dataIndex:'orderNo',width:100},
				{header:'订单日期',dataIndex:'orderDate',width:80},
				{header:'产品名称',dataIndex:'prodName',width:180},
				{header:'单价',dataIndex:'unitPrice',width:80},
				{header:'数量',dataIndex:'count',width:80},
				{header:'处理状态',dataIndex:'status',width:80,renderer:rendStatus},
				{header:'投诉方状态',dataIndex:'fromStatus',width:80},
				{header:'被投诉方状态',dataIndex:'toStatus',width:80},
				{header:'删除状态',dataIndex:'delStatus',width:80},
				{header:'处理结果',dataIndex:'processResult',width:280}
			],
			fields: [
				{id:'id',inputType:'hidden',name:'complaint.id'},
				{id:'type',inputType:'hidden', name: 'complaint.type'},
		  		{id:'no',fieldLabel:'投诉编号',anchor: '100%',allowBlank:false,readOnly: true},
		  		{id:'memberFromName',fieldLabel:'投诉人',anchor: '100%',allowBlank:false,readOnly: true},
		  		{id:'compDate',fieldLabel:'投诉时间',anchor: '100%',allowBlank:false,readOnly: true},
		  		{id:'telFrom',fieldLabel: '投诉人电话',anchor: '100%',allowBlank:false,readOnly: true},
		  		{id:'emailFrom',fieldLabel:'投诉人邮件',anchor: '100%',allowBlank:false,readOnly: true},
		  		{id:'memberToName',fieldLabel:'被投诉人',anchor: '100%',allowBlank:false,readOnly: true},
		  		{id:'telTo',colspan:2,fieldLabel:'被投诉人电话',anchor: '100%',allowBlank:false,readOnly: true},
				{id:'content',colspan:2,height:100,fieldLabel:'投诉内容',xtype:'htmleditor',anchor:'100%',readOnly: true},
		  		{id:'orderNo',fieldLabel:'订单编号',anchor: '100%',allowBlank:false,readOnly: true},
		  		{id:'orderDate',fieldLabel:'订单日期',anchor: '100%',allowBlank:false,readOnly: true},
				{id:'prodName',fieldLabel:'产品名称',xtype:'text',anchor: '100%',readOnly: true},
				{id:'status',fieldLabel:'处理状态',xtype:'combox',anchor:'100%',name:'complaint.status',editable:false,loadUrl:'complaint1!loadStatus.asp'},
				{id:'processResult',colspan:2,height:100,fieldLabel:'处理结果',xtype:'htmleditor',name:'complaint.processResult',anchor:'100%' }
			],
			bean: complaint1
		});
		function rendStatus(value,cellMeta,record){
			if (value == '1') return '处理中';
			else if (value == '2') return '已处理';
			else return '待处理';
		};
		function rendAffix(v, c, r) {
			if (v){
				return '<a href="picture/' + v + '" target="_blank">' + v + '</a>';
			}
			return v;
		}
	});
	function onHandler() {
		
	}
	</script>
</body>
</html>

