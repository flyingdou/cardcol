<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="divorder1"></div>
<script type="text/javascript">
	var panel;
	var order1 = [ 
		{name : 'id',type : 'long'}, 
		{name : 'no',type : 'string'}, 
		{name : 'payNo',type : 'string'}, 
		{name : 'orderType', type : 'string'},
		{name : 'orderDate', type : 'string'}, 
		{name : 'fromName', type : 'string'}, 
		{name : 'toName', type : 'string'},
		{name : 'orderType', type : 'string'},
		{name : 'name', type : 'string'},
		{name : 'proType', type : 'string'}, 
		{name : 'status', type : 'string'},
		{name : 'orderMoney',type : 'float'},
		{name : 'integral',	type : 'int'}
	];
	Ext.onReady(function() {
		panel = Ext.create('Ext.ux.GridFormPanel', {
			id : 'order1',
			locked : false,
			funcs : <s:property value="#request.funcs"/>,
			winTitle : '添加订单',
			winWidth : 760,
			winHeight : 400,
			layouts: {type: 'table', columns: 2},
			where : [ '订单编号:', {	xtype : 'textfield',id : 'no',width : 80},
				'交易号:', {	xtype : 'textfield',id : 'payNo',width : 80},
				'订单状态:', {xtype : 'combox',	id : 'status',loadUrl : 'order1!findStatus.asp',root : 'items',	width : 80},
				'订购日期:', {xtype : 'date',  id : 'orderDate', width : 80}
			],
			columns : [
				{header : '订单编号', dataIndex : 'no', width : 100},
				{header : '交易号', dataIndex : 'payNo', width : 120	},
				{header : '订购日期', dataIndex : 'orderDate', width : 100,renderer : rendDate},
				{header : '订购人', dataIndex : 'fromName', width : 180},
				{header : '供应商', dataIndex : 'toName', width : 240},
				{header : '订单类型',  dataIndex : 'orderType',width : 80},
				{header : '订单状态', dataIndex : 'status', width : 100,	renderer : rendOrderStatus},
				{header : '套餐名称', dataIndex : 'name', width : 160},
				{header : '套餐类型', dataIndex : 'proType', width : 80,renderer : rendProType},
				{header : '订单金额', dataIndex : 'orderMoney', width : 80},
				{header : '积分', dataIndex : 'integral', width : 80	}
			],
			fields : [
				{id:'id', name:'order.id', xtype:'hidden'},
				{id:'productId', name:'order.product.id', xtype:'hidden'}, 
				{id:'memberId', name:'order.member.id', xtype:'hidden'},
				{id:'productPrice',name:'order.unitPrice',xtype:'hidden'},
				{id : 'productName', fieldLabel : 'e卡通名称', name : 'order.product.name', colspan:2,allowBlank : false, xtype:'triggerfield',
					onTriggerClick: function(){
						var _win = new Ext.ux.SelectWindow({
							width: 800,
							height:500,
							isExt: true,
							title:'选择一卡通',
							columns:[
								{header: '', width: 40, renderer: function(v, c, r){
									var _htmls = '<a href="javascript:changeStatus({0}, \'{1}\')" id="oper{0}" keyId="{0}" type="{1}">{2}</a>';
									if (r.get('onecardstatus') === 'A') return Ext.String.format(_htmls, r.get('id'), 'B', '开通');
									return Ext.String.format(_htmls, r.get('id'), 'A', '关闭');
								}},
								{header:'商品名称', dataIndex:'name', width:300},
								{header:'周期', dataIndex:'period', width:80},
								{header:'周期单位',dataIndex:'periodUnit',width:100,renderer:  function(v, c, r) {
									if (v == 'A') return '月';
									else if (v === 'B') return '季';
									else if (v == 'C') return '年';
								}},
								{header:'图片',dataIndex:'teamNum',width:180},
								{header:'状态', dataIndex: 'status', width: 80, renderer: function(v,  c,  r) {
									if (v === 'B') return '开通';
									else return '关闭';
								}}
							],
							bean:[
								{name:'id',type: 'long'},
								{name:'name',type:'string'},
								{name:'image',type:'string'},
								{name:'type',type:'string'},
								{name:'period', type: 'int'},
								{name:'periodUnit', type: 'string'},
								{name:'price', type: 'float'},
								{name:'content',  type: 'string'},
								{name:'status',type:'string'}
							],
							listUrl:'onecard!query',
							findField:['id', 'name', 'price'],
							retuField:['order1productId', 'order1productName', 'order1productPrice']
						});
						_win.show();
					}
				},
				{id : 'orderStartTime', fieldLabel : '开卡日期', name : 'order.orderStartTime', xtype : 'date', anchor : '100%', allowBlank : false},
				{id : 'orderMoney',fieldLabel : '订单金额',name : 'order.orderMoney',anchor : '20%',xtype : 'number'},
				{id : 'fromName',fieldLabel : '会员',anchor : '100%',xtype:'textfield',colspan:2, readOnly: true},
				{id : 'jsons', colspan:2,fieldLabel:'选择会员',name:'jsons', height: 240, xtype:'list2list', listUrl:'member1!query' + Ext.ACTION_SUFFIX + '?query.role=E', bean:[
					{name:'id',type: 'int'},
					{name:'email',type:'string'},
					{name:'nick',type:'string'},
					{name:'tell',type:'string'},
					{name:'mobilephone', type: 'string'},
					{name:'name',type:'string'}
				],columns:[
					{header:'昵称',dataIndex:'nick',width:100,items: {xtype: 'headtext', field: 'nick'}},
					{header:'名称',dataIndex:'name',width:140,items: {xtype: 'headtext', field: 'name'}},
					{header:'联系电话',dataIndex:'tell',width:100,items: {xtype:'headtext', field: 'tell'}},
					{header:'移动电话',dataIndex:'mobilephone', width: 100, items: {xtype:'headtext', field: 'mobilphone'}}
				],hasPage: true, params: {'query.role': 'M'}},
			],
			bean : order1
		});
		function rendOrderType(v, c, r) {
			if (v == '1')
				return '商品订单';
			else if (v == '2')
				return '挑战订单';
		}
		function rendOrderStatus(v, c, r) {
			if (v == '0')
				return '未付款';
			else if (v == '1')
				return '已付款';
			else if (v == '2')
				return '已结束';
			else if (v == '3')
				return '已结算';
			return '';
		}
		function rendProType(val, cell, rs) {
			if (val == '1')
				return '对赌';
			else if (val == '2')
				return '按月（时效）付费';
			else if (val == '3')
				return '按月（计次）付费';
			else if (val == '4')
				return '预付费';
			else if (val == '5')
				return '对账2';
			else if (val == '6')
				return '储值卡';
			else if (val == '7')
				return '计次收费';
			else if (val == '8')
				return '计时收费';
		}
		function rendSZType(v, c, r) {
			if (v == '1')
				return '收入';
			else if (v == '2')
				return '支出';
			else
				return '';
		}
		function rendJYType(v, c, r) {
			if (v == '1')
				return '预付款';
			else if (v == '2')
				return '保证金';
			else if (v == '3')
				return '违约金';
			else if (v == '4')
				return '缺勤费用';
			else if (v == '5')
				return '训练费用';
			else if (v == '6')
				return '交易服务费';
			else if (v == '7')
				return '交易手续费';
			else if (v == '8')
				return '超勤费用';
			else if (v == '9')
				return '退款';
			else if (v == '0')
				return '提现';
			else if (v == 'A')
				return '挑战惩罚金';
			else
				return '';
		}
	});
</script>

