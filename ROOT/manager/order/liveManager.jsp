<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="divlive"></div>
<script type="text/javascript">
var panel;
var live = [
  		{name:'id',type: 'string'},
  		{name:'live_name',type: 'string'},
  		{name:'live_notice',type: 'string'},
  		{name:'name',type: 'string'},
  		{name:'live_history_time',type: 'string'},
  		{name:'live_state',type: 'string'}
  	];
Ext.onReady(function(){
	var _funcs = <s:property value="#request.funcs"/>;
	panel = Ext.create('Ext.ux.GridFormPanel', {
		id: 'live',
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
		 /*  {header:'商品编号', dataIndex:'id', width:100}, */
			{header:'商品名称', dataIndex:'live_name', width:100},
			{header:'商品类型',width:100,renderer: rendProductType},
			{header:'所属会员',dataIndex:'name',width:150},
			{header:'上次直播时间',dataIndex:'live_history_time',width:150},
			{header:'当前状态',dataIndex:'live_state',width:150,renderer: showStates},
			{header:'操作',dataIndex:'live_state',width: 100,renderer: rendOper}
		],
		bean: live
	});
});
function rendProductType(val, cell, rs) {
	return "健身直播";
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
function showStates(v, c, r){
	if(v == 0){
		return '未直播';
	} else if(v == 1) {
		return '直播中';
	} else if(v == 2) {
		return '已关闭';
	}
}
function rendOper(v, c, r) {
	if(v == 0 || v == 1){
		return '<a href="javascript: openOrClose(' + r.get('id') + ',0);">关闭</a>';
	} else {
		return '<a href="javascript: openOrClose(' + r.get('id') + ',1);">开启</a>';
	}
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

function openOrClose(id,type) {
	var prompt = type == 0 ? "确认关闭?" : "确认开启?";
	Ext.Msg.confirm('', prompt, function(btn){
		if (btn == 'yes') {
			Ext.Ajax.request({url: 'live!openOrClose.asp', method: 'post', params: 'id=' + id , 
				success: function(resp) {
					var json = JSON.parse(resp.responseText);
					if (json.success === true) {
						alert('操作成功！');
						// Ext.getCmp('productauditwin').close();
						panel.onReload();
					} else {
						alert('程序异常!');
					}
				}
			});
		}
	});
}
</script>