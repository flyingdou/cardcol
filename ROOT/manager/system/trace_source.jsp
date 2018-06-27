<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="divtracesource"></div>
<script language="javascript">
var panel;
var tracesource = [
	{name:'openId',type: 'string'},
	{name:'scanDate',type: 'string'},
	{name:'source',type: 'string'},
	{name:'member',type:'string'},
	{name:'orderId', type:'string'},
	{name:'orderNo', type:'string'},
	{name:'orderStatus', type:'string'}
];
Ext.onReady(function(){
	panel = Ext.create('Ext.ux.GridFormPanel', {
		id: 'tracesource',
		locked: false,
		funcs: '<s:property value="#request.funcs"/>',
		where: ['来源:',{xtype: 'combox', id:'origin',loadUrl: 'tracesource!findStatus.asp', width: 80},
		        '开始时间:',{xtype:'date', id: 'startDate', width: 100},
		        '结束时间:',{xtype:'date', id: 'endDate', width: 100}
		],
		columns: [
			{header:'用户微信ID', dataIndex:'openId', width:200},
			{header:'进入时间', dataIndex:'scanDate', width:100},
			{header:'来源', dataIndex:'source', width:100,renderer:origin},
			{header:'是否注册',dataIndex:'member',width:100,renderer:isRegister},
			{header:'是否提交订单',dataIndex:'orderId',width:100,renderer:isSubmitOrder},
			{header:'订单编号',dataIndex:'orderNo',width:140,renderer:showOrderNo},
			{header:'是否成功支付',dataIndex:'orderStatus',width:120,renderer:isPayment}
		],
		bean: tracesource
	});
});

function origin(v, c, r){
	switch(v){
	  case "adwy1":
		  return "岳杨";
		  break;
	  case "adwy2":
		  return "陈瑶";
		  break;
	}
}

function isRegister(v, c, r){
	if(v > 0){
		return "是";
	} else {
		return "否";
	}
}

function isSubmitOrder(v, c, r){
	if(v > 0){
		return "是";
	} else {
		return "否";
	}
}

function showOrderNo(v, c, r){
	if(v > 0){
		return v;
	} else {
		return "无";
	}
}

function isPayment(v, c, r){
	if(v > 0){
		return "是";
	} else {
		return "否";
	}
}
</script>