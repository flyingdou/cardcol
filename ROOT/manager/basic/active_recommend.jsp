<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="divactiverecommend"></div>
<script type="text/javascript">
Ext.onReady(function(){
	Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
	Ext.create('Ext.ux.GridFormPanel', {
		id: 'activerecommend',
		panelStyle: <s:property value="#request.panelStyle"/>,
		winTitle: '内容推荐维护',
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
			{header:'原标题',dataIndex:'oldTitle',width:180},
			{header:'推荐标题',dataIndex:'title',width:180},
			{header:'推荐类型',dataIndex:'recommType', width: 100, renderer: rendRecommType},
			{header:'标题颜色',dataIndex:'color',width:60},
			{header:'缩略图',dataIndex:'icon1',width:120,hidden:true},
			{header:'推荐图片',dataIndex:'icon',width:120,hidden:true},
			{header:'推荐链接',dataIndex:'link',width:180},
			{header:'推荐时间',dataIndex:'recommDate',width:120},
			{header:'顺序',dataIndex:'sort',width: 60}
		],
		fields: [
			{id:'oldTitle',fieldLabel:'原名称',name:'recomm.oldTitle',readOnly:true,anchor:'96%'},
			{id:'title',fieldLabel:'推荐名称',name:'recomm.title',anchor: '96%'},
			{id:'color',fieldLabel:'标题颜色',xtype:'colorfield',value:'#333333', msgTarget: 'qtip', fallback: true,name:'recomm.color',width:100},
			{id:'sectorId',fieldLabel:'推荐栏目',xtype:'combotree',width:200,hiddenName:'recomm.sector',loadUrl:'sector!loadRecomm' + Ext.ACTION_SUFFIX, allowBlank:false},
			{id:'icon',name:'recomm.icon',fieldLabel:'原图片名',anchor:'60%',readOnly:true},
			{id:'filerecomm',fieldLabel:'推荐图片',xtype:'filefield',name:'file.file',anchor: '96%'},
			{id:'link',fieldLabel:'推荐链接',name:'recomm.link',anchor: '96%'},
			{id:'sort',xtype:'number',name:'recomm.sort', fieldLabel:'顺序', anchor: '96%'},
			{id:'summary',xtype:'htmleditor',name:'recomm.summary',fieldLabel:'摘要描述',anchor:'96% 10%'},
			{id:'id',inputType:'hidden',name:'recomm.id'},
			{id:'recommId',inputType:'hidden',name:'recomm.recommId'},
			{id:'recommType',inputType:'hidden', name: 'recomm.recommType'}
		],
		bean: [
		   	{name:'id',type: 'long'},
			{name:'recommId',type:'long'},
			{name:'oldTitle',type:'string'},
			{name:'sort', type:'int'},
			{name:'title',type:'string'},
			{name:'recommType', type: 'string'},
			{name:'color',type:'string'},
			{name:'sectorId',type:'long', mapping: 'sector'},
			{name:'icon',type:'string'},
			{name:'link',type:'string'},
			{name:'summary',type:'string'},
			{name:'recommDate',type:'string'}
		]
	});
	function rendRecommType(v, c, r){
		if (v === '1') return '健身卡推荐';
		else if (v === '2') return '活动推荐';
		else if (v === '3') return '健身计划推荐';
		else if (v === '7') return '会员推荐';
		else if (v === '8') return '文章推荐';
		else if (v === '9') return '广告推荐';
		else if (v === '0') return '一卡通';
		else return v;
	}
});
</script>

