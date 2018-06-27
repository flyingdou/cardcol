Ext.BLANK_IMAGE_URL = 'image.asp?code=defaults/s.gif';
Ext.ACTION_SUFFIX = '.asp';
var tabs = null;
Ext.onReady(function() {
	Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
	tabs = new Ext.TabPanel({
		id : 'mainPanel',
		region : 'center',
		frame : false,
		activeTab : 0,
		enableTabScroll : true,
		resizeTabs : true,
		minTabWidth : 95,
		layoutOnTabChange : true,
		deferredRender : true,
		tabWidth : 140,
		listeners : {
			tabchange : function(tab, p) {
				p.doLayout();
			},
			beforeremove : destroy
		},
		items : [ {
			title : '桌面',
			layout : 'fit',
			autoLoad : {
				url : 'desktop' + Ext.ACTION_SUFFIX,
				scripts : true,
				method : 'post'
			}
		} ]
	});
	var top = new Ext.Panel({
		region : 'north',
		border : false,
		height : 70,
		contentEl : 'north'
	});
	var accordion = new Ext.Panel({
		region : 'west',
		title : '当前用户：' + userName,
		collapsible : true,
		split : true,
		collapseMode : 'mini',
		layout : {
			type : 'accordion',
			animate : true
		},
		width : 210,
		minSize : 140,
		maxSize : 500,
		items : menus
	});
	new Ext.Viewport({
		layout : 'border',
		border : false,
		deferredRender : false,
		items : [ top, accordion, tabs ]
	});
});

function destroy(tab, panel) {
}


var m_win;
function openWin(){
	var user = new Ext.data.Record.create([
		{name: 'userid' ,type: 'int',mapping:'id'},
		{name: 'usercode' ,type: 'string',mapping:'code'},
		{name: 'username' ,type: 'string',mapping:'name'},
		{name: 'userpass' ,type: 'string',mapping:'password'}
	]);
	var reader = new Ext.data.JsonReader({} ,user);
	var form = new Ext.form.FormPanel({
		id: "mainform",
		baseCls: 'x-plain',
		labelWidth: 75,
		labelAlign: 'right',
		url: 'user!edit' + Ext.ACTION_SUFFIX,
		reader: reader,
		layout: 'form',
		defaultType: 'textfield',
		items: [{id: 'userid',xtype:'hidden',name: 'user.id'},
			{id:'usercode',readOnly:true,fieldLabel: '用户代号',name:'user.code',anchor:'70%',allowBlank: false},
		    {id:'username',readOnly:false,fieldLabel:'用户名称',name:'user.name',anchor:'98%',allowBlank: false},
		    {id:'pass0',inputType:'password',listeners:{blur:checkOld},fieldLabel:'原密码',anchor:'98%',allowBlank: false},
		    {id:'pass1',inputType:'password',fieldLabel:'新密码',name:'user.password',anchor:'98%',allowBlank: false},
		    {id:'pass2',inputType:'password',fieldLabel:'校验密码',anchor:'98%',allowBlank:false}]
	});
	form.form.load({
		url: 'user!loadUser' + Ext.ACTION_SUFFIX,
		waitMsg: 'Loading......'
	});
   	m_win = new Ext.Window({
		id: "mainwin",
		title: '用户密码修改',
		width: 400,
		height: 240,
		closeAction: 'close',
		autoScroll: 'auto',
		minWidth: 100,
		minHeight: 100,
		plain: true,
		bodyStyle: 'padding:5px',
		buttonAlign: 'center',
		items: form,
		modal: true,
        buttons: [{
        	text: '修改',
        	id: 'btnModify',
        	width: 60,
        	disabled: true,
        	scope: this,
        	handler: function(){
        		var f = form.getForm();
        		if (Ext.getCmp('pass1').getValue() != Ext.getCmp('pass2').getValue()) {
        			Ext.MessageBox.alert('保存', '两次输入的密码不一样，请重新输入！');
        			return;
        		}
                if(f.isValid()){
                    f.submit({
                    	url: 'user!savePassword' + Ext.ACTION_SUFFIX,
                        waitMsg: '保存数据...',
                        success: function(form ,action){
                    		Ext.MessageBox.alert('保存', '当前用户的密码修改成功！');
                    	},
                    	failure: function(form ,action){
                    		Ext.MessageBox.alert('保存', '当前数据保存失败！可能的原因是：' + action.result.desc);
                    	}
                    });
                } else {
                    Ext.MessageBox.alert('保存：','表单填写的数据有错误！');
                }
            }
        },{
            text:'重置',
            width: 60,
            handler:function(){
                form.getForm().reset();
            }
        },{
        	text: '关闭',
        	width: 60,
        	scope: this,
        	handler: function(){
        		m_win.destroy();
        	}
        }]
	});
   	m_win.show();
}

function checkOld(o) {
	var val = o.getValue();
	if (val == '') return;
	var no = Ext.getCmp('usercode').getValue();
	Ext.Ajax.request({
		method: 'post',
		url: 'user!check' + Ext.ACTION_SUFFIX + '?user.code=' + no,
		params: 'user.password=' + val,
		success: function(response){
			var msg = response.responseText;
			if (msg == 'OK')
				Ext.getCmp('btnModify').setDisabled(false);
			else {
				Ext.Msg.alert('修改密码', '原密码输入不正确，请重新输入!');
			}
		}
	});
}
