<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="divproduct1"></div>
<script language="javascript">
var panel;
var product1 = [
  		{name:'id',type: 'int'},
  		{name:'no',type:'string'},
  		{name:'name',type:'string'},
  		{name:'type',type:'string'},
  		{name:'proType',type:'string'},
  		{name:'audit', type: 'string'},
  		{name:'memberId', type: 'int'},
  		{name:'role', type: 'string'}, 
  		{name:'createTime', type: 'string'},
  		{name:'num',type:'int'},
  		{name:'wellNum',type:'int'},
  		{name:'memberNick',type:'string'} 
  	];
Ext.onReady(function(){
	var _funcs = <s:property value="#request.funcs"/>;
	panel = Ext.create('Ext.ux.GridFormPanel', {
		id: 'product1',
		formTitle: '商品信息',
		formWidth: 640,
		formHeight: 500,
		locked: false,
		funcs: _funcs,
		recommendTitleField: 'name',
		where: ['商品编号:',{xtype: 'textfield', id:'no', width: 80},
		        '商品名称:',{xtype:'textfield', id: 'name', width: 80}
		],
		columns: [
// 			{header:'商品编号', dataIndex:'no', width:100},
			{header:'商品名称', dataIndex:'name', width:100},
			{header:'商品类型',dataIndex:'type',width:100,renderer: rendProductType},
			{header:'套餐类型',dataIndex:'proType',width:80,renderer: rendProType},
			{header:'所属会员',dataIndex:'memberNick',width:80},
			{header:'审核状态',dataIndex:'audit',width:80,renderer: rendAuditStatus},
// 			{header:'训练次数',dataIndex:'num',width:80},
			{header:'训练有效期限(周/月)',dataIndex:'wellNum',width:80},
			{header:'创建时间',dataIndex:'createTime',width:80},
			{header:'',width: 100, renderer: rendOper}
		],
		bean: product1
	});
});

function rendProductType(val, cell, rs) {
	if (val == '1') return '健身套餐';
	else if (val == '2') return '实物商品';
	else if (val == '3') return '定制收费';
	else if (val == '4') return '高级会员套餐';
}
function rendProType(val, cell, rs) {
	if (val == '1') return '圈存(时效)';
	else if(val == '2') return '圈存(计次)';
	else if(val == '3') return '圈存(储值)';
	else if(val == '4') return '对赌(次数)';
	else if(val == '5') return '对赌(频率)';
	else if(val == '6') return '预付卡';
	else return "";
}
function rendOper(v, c, r) {
	return '<a href="javascript: showDetail(' + r.get('id') + ');">查看详细内容</a>';
}
function showDetail(_id) {
	Ext.Ajax.request({url: 'product1!showDetail.asp', method: 'post', params: 'id=' + _id, waitMsg: 'Loading...',
		success: function(resp) {
			var _html = resp.responseText;
			Ext.create('Ext.window.Window', {
				width: 800,
				height: 440,
				id: 'productauditwin',
				closeAction: 'destroy',
				title: '审核商品信息',
				autoScroll: true,
				items: [
				    {xtype: 'form', defaultType: 'textfield', autoScroll: true, border: false, frame: false, items: [
					    {id: 'productkeyId', inputType: 'hidden', value: _id},
					    {xtype: 'panel', html: _html}
				    ]}
				],
				buttonAlign: 'center',
				buttons: [{text: '通过', handler: onAudit}]
			}).show();
		}
	});
}
function onAudit() {
	Ext.Msg.confirm('审核', '当前商品信息是否确认审核通过?', function(btn){
		if (btn == 'yes') {
			var _id = Ext.getCmp('productkeyId').getValue();
			Ext.Ajax.request({url: 'product1!audit.asp', method: 'post', params: 'ids=' + _id + '&audit=1', 
				success: function(resp) {
					var json = Ext.util.JSON.decode(resp.responseText);
					if (json.success === true) {
						alert('当前商品信息已经成功审核！');
						Ext.getCmp('productauditwin').close();
						panel.onReload();
					} else {
						alert('当前商品信息审核失败！可能的原因为：' + json.desc);
					}
				}
			});
		}
	});
}
function rendAuditStatus(val, cell, rs) {
	if (val == '1') return '已审核';
	else return '待审核';
}
</script>


