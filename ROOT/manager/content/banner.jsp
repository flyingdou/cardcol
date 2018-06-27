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
<div id="divbanner"></div>
<script language="javascript">
	var banner = [
   		{name:'id',type: 'int'},
   		{name:'name',type:'string'},
   		{name:'type',type:'int'},
   		{name:'confine',type:'int'},
   		{name:'sDate',type:'string'},
   		{name:'eDate',type:'string'},
   		{name:'sectorId',type:'int',mapping:'sector.id'},
   		{name:'sectorName',type:'stirng',mapping:'sector.name'},
   		{name:'content',type:'string'},
   		{name:'icon',type:'string'},
   		{name:'link',type:'string'},
   		{name:'clickNum',type:'int'}
   	];
	Ext.onReady(function(){
		Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
		var typeStore = new Ext.data.Store({autoLoad:true,proxy:new Ext.data.HttpProxy({url:'user!dictionary.asp?code=system_banner_type'}),reader:new Ext.data.JsonReader({},['code','name'])});
		Ext.create('Ext.ux.GridFormPanel', {
			id: 'banner',
			upload: true,
			formTitle: '广告信息',
			formWidth: 640,
			formHeight: 500,
			configs: {layout:'table',columns:3},
			funcs: <s:property value="#request.funcs"/>,
			where: ['广告名称:',{xtype:'textfield',id:'name',width: 100}],
			columns: [
				{header:'广告名称',dataIndex:'name',locked: true,width:300},
				{header:'广告类型',dataIndex:'type',width:100,renderer:rendType},
				{header:'投放栏目',dataIndex:'sectorName',width:100},
				{header:'时间限制',dataIndex:'confine',width:60,renderer:rendConfine},
				{header:'开始时间',dataIndex:'sDate',width:80},
				{header:'结束时间',dataIndex:'eDate',width:80},
				{header:'点击次数',dataIndex:'clickNum',width:80}
			],
			fields: [
				{id:'id',inputType:'hidden',name:'banner.id'},
		  		{id:'icon',inputType:'hidden',name:'banner.icon'},
		  		{id:'site',inputType:'hidden',name:'banner.site'},
		  		{id:'clickNum',inputType:'hidden',name:'banner.clickNum'},
		  		{id:'name',colspan:2,fieldLabel:'广告名称',name:'banner.name',anchor: '100%',allowBlank:false},
				{id:'sectorId',fieldLabel:'投放栏目',xtype:'treecombo',anchor:'100%',hiddenName:'banner.sector.id',editable:false,loadUrl:'sector!loadByType?type=5',allowBlank:false},
				{id:'confine',fieldLabel:'投放期限',name:'banner.confine',xtype:'radiogroup',items:[{boxLabel:'永久',name:'banner.confine',inputValue:'1'},{boxLabel:'有效期',name:'banner.confine',inputValue:'0'}],allowBlank:false},
				{id:'sDate',fieldLabel:'开始时间',name:'banner.startDate',xtype:'datefield',anchor:'100%',vtype:'daterange',endDateField:'bannereDate',format:'Y-m-d'},
				{id:'eDate',fieldLabel:'结束时间',name:'banner.endDate',xtype:'datefield',anchor:'100%',vtype:'daterange',format:'Y-m-d',startDateField:'bannersDate'},
				{id:'type',colspan:3,fieldLabel:'广告类型',name:'banner.type',xtype:'combox',anchor:'40%',hiddenName:'banner.type',editable:false,keyId:'code',url:'user!dictionary?code=system_banner_type',listeners:{'select':changeState}},
				{id:'file',colspan:3,fieldLabel:'图片文件',inputType:'file',name:'file.file',anchor: '100%'},
				{id:'link',colspan:3,fieldLabel:'链接地址',name:'banner.link',anchor: '100%'},
				{id:'content',colspan:3,height:300,fieldLabel:'广告内容',xtype:'htmleditor',name:'banner.content',anchor:'100% 90%'}
			],
			bean: banner
		});
		function rendType(value,cell,record){
			var index = typeStore.find('code',value);
			var mrecord = typeStore.getAt(index);
			if (mrecord)
				return mrecord.get('name');
			return value;
		};
		function rendConfine(v){
			if (v == 1) return '永久';
			return '有效期';
		};
		function changeState(the ,rec ,index){
			var id = rec.get('code');
			if (id == 1 || id == 2) {
				Ext.getCmp('bannerfile').enable();
				Ext.getCmp('bannerlink').enable();
				Ext.getCmp('bannercontent').disable();
			} else {
				Ext.getCmp('bannerfile').disable();
				Ext.getCmp('bannerlink').enable();
				Ext.getCmp('bannercontent').enable();
			}
		}
	});
	</script>
</body>
</html>

