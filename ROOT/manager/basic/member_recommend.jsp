<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="divmemberecommend"></div>
<script type="text/javascript">
Ext.onReady(function(){
	Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
	Ext.create('Ext.ux.GridFormPanel', {
		id: 'memberecommend',
		panelStyle: <s:property value="#request.panelStyle"/>,
		winTitle: '会员推荐',
		winWidth: 700,
		winHeight: 400,
		showLog: false,
		hasStick: true,
		reload: true,
		funcs: <s:property value="#request.funcs"/>,
		treeTitle: '所有推荐栏目',
		loadUrl: 'sector!loadRecomm',
		where: ['商品标题:',{xtype:'textfield',id:'title',width: 100}],
		labelWidth: 70,
		columns: [
		    {header:'主键ID号',dataIndex:'id',hidden:true,width:60},
			{header:'原名称',dataIndex:'oldtitle',width:150},
			{header:'推荐名称',dataIndex:'title',width:120},
			{header:'标题颜色',dataIndex:'color',width:60},
			{header:'缩略图',dataIndex:'icon1',width:120,hidden:true},
			{header:'推荐图片',dataIndex:'icon',width:120,hidden:true},
			{header:'推荐链接',dataIndex:'link',width:80},
			{header:'推荐时间',dataIndex:'recommDate',width:120}
		],
		fields: [
			{id:'id',inputType:'hidden',name:'recomm.id'},
			{id:'productId',inputType:'hidden',name:'recomm.recommId'},
			{id:'recommType',inputType:'hidden', name: 'recomm.recommType'},
			{id:'oldtitle',fieldLabel:'原名称',name:'recomm.product.name',readOnly:true,anchor:'96%'},
			{id:'title',fieldLabel:'推荐名称',name:'recomm.title',anchor: '96%'},
			{id:'color',fieldLabel:'标题颜色',xtype:'colorfield',value:'#333333', msgTarget: 'qtip', fallback: true,name:'recomm.color',width:100},
			{id:'sectorId',fieldLabel:'推荐栏目',xtype:'combotree',width:200,hiddenName:'recomm.sector',loadUrl:'sector!loadRecomm' + Ext.ACTION_SUFFIX, allowBlank:false},
			{id:'icon',name:'recomm.icon',fieldLabel:'原图片名',anchor:'60%',readOnly:true},
			{id:'filerecomm',fieldLabel:'推荐图片',xtype:'filefield',name:'file.file',anchor: '96%'},
			{id:'link',fieldLabel:'推荐链接',name:'recomm.link',anchor: '96%'},
			{id:'summary',xtype:'htmleditor',name:'recomm.summary',fieldLabel:'摘要描述',anchor:'96% 35%'}
		],
		bean: [
		   	{name:'id',type: 'long'},
			{name:'productId',type:'long',mapping:'member.id'},
			{name:'oldtitle',type:'string' ,mapping:'member.name'},
			{name:'title',type:'string' ,mapping:'title'},
			{name:'recommType', type: 'string'},
			{name:'color',type:'string'},
			{name:'icon1',type:'string' ,mapping:'member.image'},
			{name:'sectorId',type:'long', mapping: 'sector'},
			{name:'icon',type:'string'},
			{name:'link',type:'string'},
			{name:'summary',type:'string'},
			{name:'recommDate',type:'string'}
		]
	});
});
</script>

