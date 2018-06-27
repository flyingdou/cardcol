<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="divticket"></div>
<script type="text/javascript">
	var ticket = [ {
		name : 'id',
		type : 'int'
	}, {
		name : 'name',
		type : 'string'
	}, {
		name : 'price',
		type : 'float'
	}, {
		name : 'kind',
		type : 'string'
	}, {
		name : 'period',
		type : 'int'
	}, {
		name : 'scope',
		type : 'string'
	}, {
		name : 'activeCode',
		type : 'string'
	}, {
		name : 'effective',
		type : 'string'
	}, {
		name : 'limit_price',
		type : 'string'
	}, {
		name : 'limit_club',
		type : 'string'
	}, {
		name : 'image',
		type : 'string'
	} ];
	var rangeStore = new Ext.data.SimpleStore({
		data : [ [ '1', '健身卡' ], [ '2', '健身活动' ], [ '3', '健身计划' ],
				[ '4', '场地预订' ], [ '5', '课程预订' ], [ '6', '智能计划' ], ['7', 'e卡通'] ],
		fields : [ {
			name : 'id',
			type : 'string'
		}, {
			name : 'name',
			type : 'string'
		} ]
	});
	var yesnoStore = Ext.create('Ext.data.Store', {
		autoLoad : true,
		fields : [ 'id', 'name' ],
		data : {
			items : [ {
				id : '1',
				name : '是'
			}, {
				id : '0',
				name : '否'
			} ]
		},
		proxy : {
			type : 'memory',
			reader : {
				type : 'json',
				root : 'items'
			}
		}
	});
	var kindStore = Ext.create('Ext.data.Store', {
		autoLoad : true,
		fields : [ 'id', 'name' ],
		data : {
			items : [ {
				id : 'A',
				name : '现金券'
			}, {
				id : 'B',
				name : '分享券'
			} ]
		},
		proxy : {
			type : 'memory',
			reader : {
				type : 'json',
				root : 'items'
			}
		}
	});
	var clubStore = Ext.create('Ext.data.Store', {
		autoLoad : true,
		fields : ['id', 'name'],
		proxy : {
			type : 'ajax',
			url :'member1!queryAllClub.asp',
			reader : {
				type : 'json',
				root : 'items'
			}
		}
	});
	
	Ext.onReady(function() {
		Ext.create('Ext.ux.GridFormPanel', {
			id : 'ticket',
			winTitle : '优惠券信息',
			winWidth : 500,
			winHeight : 240,
			locked : false,
			hasLog : true,
			showType : 3,
			// 		configs: {layout: 'form'},
			funcs : <s:property value="#request.funcs"/>,
			program : <s:property value="#request.program"/>,
			columns : [ {
				header : '优惠券名称',
				dataIndex : 'name',
				width : 100
			}, {
				header : '金额(元)',
				dataIndex : 'price',
				width : 80
			}, {
				header : '种类',
				dataIndex : 'kind',
				width : 140,
				renderer: function(val){
					var _r = kindStore.findRecord('id', val);
					if (_r) return _r.get('name');
					else return val;
				}
			}, {
				header : '有效期(天)',
				dataIndex : 'period',
				width : 150
			}, {
				header : '适用范围',
				dataIndex : 'scope',
				width : 100,
				renderer: function(val){
					var _vals , _r, _names = '';
					_vals = val.split(',');
					for (var i = 0; i < _vals.length; i++) {
						_r = rangeStore.findRecord('id', _vals[i]);
						if (_r) _names += (_names === '' ? '' : ',') + _r.get('name');
					}
					return _names;
				}
			}, {
				header : '是否有效',
				dataIndex : 'effective',
				width : 80
			}, {
				header: '激活码',
				dataIndex: 'activeCode',
				width: 120
			}, {
				header: '价格限制',
				dataIndex: 'limit_price',
				width: 120,
				renderer: function(val){
					if(!val){
						return "无";
					} else {
						return val;
					}
				}
			}, {
				header: '适用俱乐部',
				dataIndex: 'limit_club',
				width: 120,
				renderer: function(val){
					if(!val){
						return "未选择适用俱乐部";
					} else {
						return val;
					}
				}
			}, {
				header: '优惠券图片',
				dataIndex: 'image',
				width: 120,
				renderer: function(val){
					if(!val){
						return "无";
					} else {
						return val;
					}
				}
			}],
			fields : [ {
				id : 'id',
				inputType : 'hidden',
				name : 'ticket.id'
			}, {
				id : 'activeCode',
				inputType : 'hidden',
				name : 'ticket.activeCode'
			}, {
				id : 'name',
				fieldLabel : '优惠券名称',
				name : 'ticket.name',
				anchor : '100%',
				allowBlank : false
			}, {
				id : 'price',
				fieldLabel : '优惠券金额',
				name : 'ticket.price',
				anchor : '100%',
				allowBlank : false
			}, {
				id : 'kind',
				fieldLabel : '种类',
				name : 'ticket.kind',
				anchor : '100%',
				xtype : 'combox',
				store : kindStore
			}, {
				id : 'period',
				fieldLabel : '有效期(天)',
				name : 'ticket.period',
				anchor : '100%'
			}, {
				id : 'scope',
				fieldLabel : '适用范围',
				name : 'ticket.scope',
				anchor : '100%',
				valueField : 'id',
				displayField : 'name',
				xtype : 'checkcombo',
				store : rangeStore,
				anchor : '100%'
			}, {
				id : 'effective',
				fieldLabel : '是否有效',
				name : 'ticket.effective',
				anchor : '100%',
				xtype : 'combox',
				store : yesnoStore
			},{
				id : 'limit_price',
				fieldLabel : '价格限制',
				name : 'param.limit_price',
				anchor : '100%'
			},{
				id : 'limit_club',
				fieldLabel : '适用俱乐部',
				name : 'param.limit_club',
				valueField : 'id',
				displayField : 'name',
				anchor : '100%',
				xtype : 'checkcombo',
				store : clubStore
			},{
				id : 'image',
				fieldLabel : '优惠券图片',
				name : 'image',
				xtype:'filefield',
				anchor : '100%'
			}],
			bean : ticket
		});
	});
</script>
