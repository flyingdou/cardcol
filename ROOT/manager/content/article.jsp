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
<div id="divarticle"></div>
<script language="javascript">
	var article = [
   		{name:'id',type: 'int'},
   		{name:'title',type:'string'},
   		{name:'showTitle',type:'string'},
   		{name:'color',type:'string'},
   		{name:'icon',type:'string'},
   		{name:'orgin',type:'string'},
   		{name:'author',type:'string'},
   		{name:'sTime',type:'string'},
   		{name:'sector',type:'int'},
   		{name:'keywords',type:'string'},
   		{name:'seowords',type:'string'},
   		{name:'summary',type:'string'},
   		{name:'content',type:'string'},
   		{name:'audit',type:'int'},
   		{name:'affix',type:'string'},
   		{name:'stick',type:'int'},
   		{name:'front',type:'int'},
   		{name:'stickTime',type:'stickTime'},
   		{name:'recommend',type:'int'}
   	];
   	var grid ,recommendWin ,mform;
	Ext.onReady(function(){
		Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
		var funcs = <s:property value="#request.funcs"/>;
		grid = Ext.create('Ext.ux.GridFormPanel', {
			id: 'article',
			upload: true,
			formTitle: '文章信息',
			labelWidth: 80,
			formWidth: 700,
			formHeight: 440,
			configs: {layout: 'table',columns:3},
			hasStick: true,
			hasRecomm: true,
			hasAudit: true,
			funcs: funcs,
			treeTitle: '所有栏目',
			loadUrl: 'sector!loads',
			where: ['文章标题:',{xtype:'textfield',id:'title',width: 100}],
			fbars: {text:'推荐',handler: recommend,iconCls:'x-button-recommend',tooltip:'推荐文章到其它栏目'},
			columns: [
				{header:'标题',dataIndex:'title',locked: true,width:150, renderer: rendH5Show},
				{header:'显示标题',dataIndex:'showTitle',width:100},
				{header:'标题颜色',dataIndex:'color',width:60},
				{header:'缩略图',dataIndex:'icon',width:120,hidden:true},
				{header:'来源',dataIndex:'orgin',width:80},
				{header:'作者',dataIndex:'author',width:80},
				{header:'发布时间',dataIndex:'sTime',width:100},
				{header:'关键字',dataIndex:'keywords',width:150},
				{header:'SEO关键字',dataIndex:'seowords',width:150},
				{header:'是否审核',dataIndex:'audit',width:60,renderer:rendYesNo},
				{header:'置顶时间',dataIndex:'stickTime',width:100}
				
			],
			labelWidth: 70,
			fields: [
				{id:'id',inputType:'hidden',name:'article.id'},
				{id:'icon',inputType:'hidden',name:'article.icon'},
				{id:'audit',inputType:'hidden',name:'article.audit'},
				{id:'auditTime',inputType:'hidden',name:'article.auditTime'},
				{id:'stickTime',inputType:'hidden',name:'article.stickTime'},
				{id:'front',inputType:'hidden',name:'article.front'},
			    {id:'affix',inputType:'hidden',name:'article.affix'},
				{id:'title',colspan:3,fieldLabel:'标题',name:'article.title',anchor: '100%',allowBlank:false},
				{id:'showTitle',colspan:3,fieldLabel:'显示标题',name:'article.showTitle',anchor:'100%',allowBlank:false},
				{id:'color',fieldLabel:'标题颜色',xtype:'colorfield',value: '#FFFFFF', msgTarget: 'qtip', fallback: true,name:'article.color',anchor:'100%'},
				{id:'orgin',fieldLabel:'文章来源',name:'article.orgin',anchor:'100%'},
				{id:'author',fieldLabel:'文章作者',name:'article.author',anchor:'100%'},
				{id:'sector',fieldLabel:'文章栏目',xtype:'treecombo',listWidth:200,anchor:'100%',hiddenName:'article.sector',loadUrl:'sector!loads',allowBlank:false},
				{id:'file',colspan:2,fieldLabel:'缩略图',inputType:'file',name:'file.file',anchor:'100%'},
				{id:'file1',colspan:3,fieldLabel:'文章附件',inputType:'file',name:'file.files',anchor:'100%'},
				{id:'keywords',fieldLabel:'关键字',name:'article.keywords',anchor:'100%'},
				{id:'seowords',colspan:2,fieldLabel:'SEO关键字',name:'article.seowords',anchor: '100%'},
				{id:'summary',colspan:3,height:65,fieldLabel:'内容摘要',name:'article.summary',anchor: '100%',xtype:'textarea'},
				{id:'content',colspan:3,height:200,fieldLabel:'文章内容',xtype:'ckfield',hiddenName:'article.content'}
			],
			bean: article
		});
		function recommend(){
			var r = grid.getRecord();
			if (!r){
				alert('请先选择需要推荐的文章！');
				return;
			}
			if (r.get('articleaudit') != 1){
				alert('当前文章尚未审核，不能进行推荐！');
				return;
			}
			mform = new Ext.form.FormPanel({
				id: 'sas',
				frame: true,
				border: false,
				baseCls: 'x-plain',
				layout: 'form',
				fileUpload: true,
				labelAlign: 'right',
				labelWidth: 60,
				defaultType:'textfield',
				items: [
   					{id:'said',inputType:'hidden',name:'sa.id'},
   					{id:'saarticleId',inputType:'hidden',name:'sa.article.id',value:r.id},
   					{id:'satitle',fieldLabel:'文章标题',name:'sa.title',value:r.get('articletitle'),anchor:'100%',allowBlank:false},
   					{id:'sacolor',fieldLabel:'标题颜色',xtype:'colorfield',value:'#333333', msgTarget: 'qtip', fallback: true,name:'sa.color',anchor:'30%'},
   					{id:'sasectorId',fieldLabel:'推荐栏目',xtype:'treecombo',width:200,hiddenName:'sa.sector.id',loadUrl:'sector!loadRecomm',allowBlank:false},
   					{id:'safilerecomm',fieldLabel:'推荐图片',inputType:'file',name:'file.file',anchor: '100%'},
   					{id:'salink',fieldLabel:'推荐链接',name:'sa.link',anchor: '100%'},
   					{id:'sasummary',fieldLabel:'摘要描述',xtype:'htmleditor',name:'sa.summary',anchor: '100% 50%'}
   				]
			});
			recommendWin = new Ext.Window({
				title:'文章推荐',
				id: 'recommendWin',
				width: 600,
				closeAction: 'close',
				bodyStyle: 'padding:0px',
				plain: true,
				height: 400,
				modal: false,
				items: mform,
				buttonAlign: 'center',
				buttons:[{text:'确定',scope:this,handler:onRel},{text:'关闭',handler:onClose}]
			}).show();
			recommendWin.show();
		}
		function onRel(){
			if (mform){
				var f = mform.getForm();
				if (f.isValid()){
					f.submit({url: 'recommend!save' + Ext.ACTION_SUFFIX,waitMsg: '请稍候，正在保存数据....',scope: this,
						success:function(form ,action){
							alert('文章已经成功推荐！');
						}
					});
				}
			}
		}
		function onClose(){
			recommendWin.destroy();
		}
		function rendH5Show(v, c, r) {
			var str = '<a href="javascript: onH5Show({0})">{1}</a>';
			return Ext.String.format(str, r.get('articleid'), v);
		}
	});
	function onH5Show(id){
		var rs  = grid.grid.getStore().getById(id);
		new Ext.window.Window({
			title:  '文档查看',
			frame: false,
			modal: true,
			bodyStyle: 'background-color: #ffffff;padding: 20px;',
			width: Ext.getBody().getWidth() * 2 / 3,
			height: Ext.getBody().getHeight() * 2 / 3,
			html: '<div style="font-size: 12px;font-weight: bold;">地址: http://www.cardcol.com/h5article.asp?id=' + id + '</div><div>' + rs.get('articlecontent') + '</div>'
		}).show();
	}
	</script>
</body>
</html>

