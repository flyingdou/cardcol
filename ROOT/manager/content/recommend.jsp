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
<div id="divrecommend"></div>
<script language="javascript">
	var recommend = [
   		{name:'id',type: 'int'},
   		{name:'articleId',type:'int',mapping:'article.id'},
   		{name:'oldtitle',type:'string' ,mapping:'article.title'},
   		{name:'title',type:'string' ,mapping:'title'},
   		{name:'color',type:'string'},
   		{name:'icon1',type:'string' ,mapping:'article.icon'},
   		{name:'sectorId',type:'int',mapping:'sector.id'},
   		{name:'icon',type:'string'},
   		{name:'link',type:'string'},
   		{name:'summary',type:'string'},
   		{name:'orgin',type:'string' ,mapping:'article.orgin'},
   		{name:'author',type:'string' ,mapping:'article.author'},
   		{name:'issueTime',type:'string' ,mapping:'article.issueTime'},
   		{name:'keywords',type:'string' ,mapping:'article.keywords'},
   		{name:'seowords',type:'string' ,mapping:'article.seowords'},
		{name:'recommDate',type:'string'}
   	];
	Ext.onReady(function(){
		Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
		Ext.create('Ext.ux.GridFormPanel', {
			id: 'recommend',
			upload: true,
			formTitle: '文章推荐',
			formWidth: 700,
			formHeight: 400,
			showLog: false,
			hasStick: true,
			reload: true,
			funcs: <s:property value="#request.funcs"/>,
			treeTitle: '所有推荐栏目',
			loadUrl: 'sector!loadRecomm',
			where: ['文章标题:',{xtype:'textfield',id:'article.title',width: 100}],
			labelWidth: 70,
			columns: [
			    {header:'主键ID号',dataIndex:'id',hidden:true,width:60},
				{header:'原标题',dataIndex:'oldtitle',locked: true,width:150},
				{header:'推荐标题',dataIndex:'title',width:120},
				{header:'标题颜色',dataIndex:'color',width:60},
				{header:'缩略图',dataIndex:'icon1',width:120,hidden:true},
				{header:'推荐图片',dataIndex:'icon',width:120,hidden:true},
				{header:'推荐链接',dataIndex:'link',width:80},
				{header:'作者',dataIndex:'author',width:80},
				{header:'发布时间',dataIndex:'issueTime',width:120},
				{header:'推荐时间',dataIndex:'recommDate',width:120}
			],
			fields: [
				{id:'id',inputType:'hidden',name:'sa.id'},
				{id:'articleId',inputType:'hidden',name:'sa.article.id'},
				{id:'oldtitle',fieldLabel:'原标题',name:'sa.article.title',readOnly:true,anchor:'96%'},
				{id:'title',fieldLabel:'文章标题',name:'sa.title',anchor: '96%'},
				{id:'color',fieldLabel:'标题颜色',xtype:'colorfield',value:'#333333', msgTarget: 'qtip', fallback: true,name:'sa.color',width:100},
				{id:'sectorId',fieldLabel:'文章栏目',xtype:'treecombo',width:200,hiddenName:'sa.sector.id',loadUrl:'sector!loadRecomm',allowBlank:false},
				{id:'icon',name:'sa.icon',fieldLabel:'原图片名',anchor:'60%',readOnly:true},
				{id:'filerecomm',fieldLabel:'推荐图片',inputType:'file',name:'file.file',anchor: '96%'},
				{id:'link',fieldLabel:'推荐链接',name:'sa.link',anchor: '96%'},
				{id:'summary',xtype:'htmleditor',name:'sa.summary',fieldLabel:'摘要描述',anchor:'96% 35%'}
			],
			bean: recommend
		});
	});
	</script>
</body>
</html>

