<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="divsettlement"></div>
<script language="javascript">
var panel;
var settlement = [
	{name:'id',type: 'string'},
	{name:'orderNo',type: 'string'},
	{name:'balanceType',type: 'string'},
	{name:'balanceTime',type:'string'},
	{name:'toName', type:'string'},
	{name:'fromName', type:'string'},
	{name:'orderMoney', type:'string'},
	{name:'income', type:'string'}
];
Ext.onReady(function(){
	panel = Ext.create('Ext.ux.GridFormPanel', {
		id: 'settlement',
		locked: false,
		funcs: '<s:property value="#request.funcs"/>',
		where: ['申请状态:',{xtype:'combox', id:'status', loadUrl: 'cashment!findStatus' + Ext.ACTION_SUFFIX, width: 80}],
		columns: [
			{header:'ID', dataIndex:'id', width:100},
			{header:'订单编号', dataIndex:'orderNo', width:100},
			{header:'订单类型', dataIndex:'balanceType', width:100,renderer:orderType},
			{header:'结算时间', dataIndex:'balanceTime', width:100},
			{header:'收款人',dataIndex:'toName',width:100},
			{header:'付款人',dataIndex:'fromName',width:80},
			{header:'订单金额',dataIndex:'orderMoney',width:120},
			{header:'收入',dataIndex:'income',width:80}
		],
		bean: settlement
	});
});

function orderType(v,c,r){
	if(v == 1){
		return "商品订单";
	} else if(v == 2){
	 	return "挑战订单";
	} else if(v == 3){
		return "计划订单";
	} else if(v == 4){
		return "专家系统订单";
	} else if(v == 5){
		return "课程订单";
	} else if(v == 6){
		return "专家系统订单";
	} else if(v == 7){
		return "专家系统订单";
	} else if(v == 8){
		return "E卡通订单";
	} else {
		return "未知类型";
	}
}
</script>