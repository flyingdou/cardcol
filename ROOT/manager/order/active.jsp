<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="divactive1"></div>
<script type="text/javascript">
var panel;
var active1 = [
	{name:'id',type: 'long'},
	{name:'name',type:'string'},
	{name:'mode',type:'string'},
	{name:'days',type:'int'},
	{name:'teamNum', type: 'int'},
	{name:'target', type: 'string'},
	{name:'memberId', type: 'long', mapping: 'creator.id'},
	{name:'memberName', type: 'string', mapping: 'creator.name'},
	{name:'award', type: 'string'},
	{name:'csjg', type: 'long', mapping: 'institution.id'},
	{name:'csjgName', type: 'string', mapping: 'institution.name'},
	{name:'amerceMoney', type: 'float'},
	{name:'judgeMode',type:'string'},
	{name:'joinMode',type:'string'},
	{name:'category', type: 'string'},
	{name:'stickTime', type: 'string'},
	{name:'action', type: 'string'},
	{name:'value', type:'float'},
	{name:'status',type:'string'}
];
Ext.onReady(function(){
	var _funcs = <s:property value="#request.funcs"/>;
	var lbars = [];
	if (_funcs.btnClose && _funcs.btnClose === true) 
		lbars[0] = {text:'关闭挑战', handler: changeStatus};
	panel = Ext.create('Ext.ux.GridFormPanel', {
		id: 'active1',
		locked: false,
		funcs: <s:property value="#request.funcs"/>,
		lbars: lbars,
		recommendTitleField: 'name',
		where: [
		    '挑战名称:',{xtype: 'textfield', id:'name', width: 80},
		    '发起人:',{xtype:'textfield', id: 'creator.name', width: 80}
		],
		columns: [
			{header:'挑战名称', dataIndex:'name', width:100},
			{header:'挑战模式', dataIndex:'mode', width:100,renderer: rendMode},
			{header:'完成时间(天)',dataIndex:'days',width:100},
			{header:'团队人数',dataIndex:'teamNum',width:80},
			{header:'活动目标',dataIndex:'target',width:80, renderer: rendTarget},
			{header:'项目类型',dataIndex:'category', width: 80, renderer: rendValue},
			{header:'奖励',dataIndex:'award',width:80},
			{header:'慈善机构',dataIndex:'csjgName',width:120},
			{header:'惩罚金额',dataIndex:'amerceMoney',width:80},
			{header:'裁判方式',dataIndex:'judgeMode',width:80, renderer: rendJudge},
			{header:'参加方式',dataIndex:'joinMode', width: 80, renderer: rendJoin},
			{header:'置顶时间',dataIndex:'stickTime', width: 80},
			{header:'状态', dataIndex: 'status', width: 80, renderer: rendStatus}
		],
		bean: active1
	});
});

function changeStatus() {
	var _rs = panel.getRecords(), _parms = '';
	if (!_rs || _rs.length <= 0) {
		Ext.Msg.alert('提示', '请先选择需要关闭的挑战数据！');
		return;
	}
	for (var i = 0; i < _rs.length; i++) _parms += 'ids=' + _rs[i].get('active1id') + '&';
	if (confirm('是否关闭当前选择的挑战？')) {
		Ext.Ajax.request({url:'active1!onClose.asp',method: 'post', params: _parms,
			success: function(resp){
				Ext.Msg.alert('提示', '所有选择的挑战已经成功关闭！');
				panel.onReload();
			}
		});
	}
}
function rendStatus(v, c, r){
	if (v === 'B') return '开通';
	else return '关闭';
}
function rendMode(val, cell, rs) {
	if (val == 'A') return '个人挑战';
	else return '团体挑战';
}
function rendTarget(val, cell, rs) { 
	if (val == 'A') return '体重管理';
	else if (val === 'B') return '运动量';
	else if (val === 'C') return '次数/时间/频率';
}
function rendValue(v, c, r) {
	var val = r.get('active1value');
	if (v === 'A') return '体重增加' + val + 'kg';
	else if (v === 'B') return '体重减少' + val + 'kg';
	else if (v === 'C') return '体重保持在' + val + '%';
	else if (v === 'D') return '运动' + val + '次';
	else if (v === 'E') return '运动' + val + '小时';
	else if (v === 'F') return '每次运动' + val + '小时';
	else if (v === 'G') return r.get('active1action') + val + 'KM';
	else if (v === 'H') return '负荷运动' + val + 'KG';
	return '';
}
function rendJudge(val, cell, rs) {
	if (val == 'A') return '参加者选择裁判';
	else return '活动发起人为裁判';
}
function rendJoin(val, cell, rs) {
	if (val === 'A') return '所有对象';
	else return '仅限所属会员';
}
</script>


