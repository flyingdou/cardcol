<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="divtickling"></div>
<script type="text/javascript">
	var tick = [
   		{name:'id',type: 'long'},
   		{name:'memberId', type: 'long', mapping:'member.id'},
   		{name:'memberName', type: 'string', mapping: 'member.nick'},
   		{name:'createTime', type: 'string'},
   		{name:'content',type:'string'},
   		{name:'link',type:'string'},
   		{name:'createTime',type:'string'},
   		{name:'status',type:'string'},
   		{name:'result', type: 'string'}
   	];
	Ext.onReady(function(){
		Ext.create('Ext.ux.GridFormPanel', {
			id: 'tickling',
			formTitle: '问题反馈信息',
			formWidth: 640,
			formHeight: 500,
			funcs: <s:property value="#request.funcs"/>,
			editText: '处理',
			labelWidth: 80,
			where: ['投诉编号:',{xtype: 'textfield', id:'no', width: 100}],
			columns: [
				{header:'反馈人', dataIndex:'memberName', width:120},
				{header:'反馈日期',dataIndex:'createTime',width:120},
				{header:'反馈内容',dataIndex:'content',width:200},
				{header:'联系方式',dataIndex:'link',width:180},
				{header:'是否处理',dataIndex:'status',width:80, renderer: rendStatus},
				{header:'结果',dataIndex:'result',width:300}
			],
			fields: [
				{id:'id',inputType:'hidden',name:'tick.id'},
		  		{id:'memberName',fieldLabel:'反馈人',anchor: '100%',allowBlank:false,readOnly: true},
		  		{id:'createTime',fieldLabel:'反馈时间',anchor: '100%',allowBlank:false,readOnly: true},
				{id:'content',colspan:2,height:100,fieldLabel:'反馈内容',xtype:'htmleditor',anchor:'100%',readOnly: true},
		  		{id:'link',fieldLabel: '联系方式',anchor: '100%',allowBlank:false,readOnly: true},
				{id:'status',fieldLabel:'处理状态',xtype:'combox',anchor:'100%',hiddenName:'tick.status',editable:false,loadUrl:'complaint1!loadStatus.asp'},
				{id:'result',colspan:2,height:100,fieldLabel:'处理结果',xtype:'htmleditor',name:'tick.result',anchor:'100%' }
			],
			bean: tick
		});
		function rendStatus(value,cellMeta,record){
			if (value == 1) return '处理中';
			else return '待处理';
		};
	});
	</script>
</body>
</html>

